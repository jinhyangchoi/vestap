package vestap.sys.sec.user;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.adm.adm.management.AdmAdmManagementService;
import vestap.adm.climate.cumulative.AdmCumulativeDAO;
import vestap.adm.ems.service.AdmSndngMailRegistService;
import vestap.adm.ems.service.AdmSndngMailVO;
import vestap.adm.inventory.item.AdmItemInventoryService;
import vestap.adm.user.management.AdmUserManagementService;
import vestap.com.ComParamLogger;
import vestap.com.ComSecurityUserUtil;
import vestap.egov.cmm.util.EgovStringUtil;
import vestap.sys.cmm.util.EncryptionComponent;
import vestap.sys.cmm.util.UtilityComponent;

/**
 * 사용자 로그인, 회원가입, 세션 처리 등 -------------------------------------------------- 수정일
 * 수정자 수정내용 -------------------------------------------------- 2018.08.13
 * vestap개발 최초 작성 --------------------------------------------------
 * 
 * @author vestap 개발
 * @since 2018.08.13
 *
 */

@Controller
public class UserApplyController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserApplyController.class);
	
	@Resource(name = "admcumulativeDAO")
	private AdmCumulativeDAO admcumulativeDAO;
	
	@Resource(name = "userApplyService")
	private UserApplyService SERVICE;	
	
	@Resource(name = "encryptionComponent")
	private EncryptionComponent encryptionComponent;
	
	@Resource(name = "admUserManagementService")
	private AdmUserManagementService admUserManagementService;

	@Resource(name = "admItemInventoryService")
	private AdmItemInventoryService admItemInventoryService;
	
	@Resource(name="admAdmManagementService")
	private AdmAdmManagementService admAdmManagementService;
	
	/** EgovSndngMailRegistService */
	@Resource(name = "admSndngMailRegistService")
    private AdmSndngMailRegistService admSndngMailRegistService;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	// json 데이터로 응답을 보내기 위한 
	@Autowired
	MappingJackson2JsonView jsonView;

//	###########################################################################################################################
	
	private EgovMap paramMap(HttpServletRequest request){
		EgovMap paramMap = new EgovMap();

		Set keySet = request.getParameterMap().keySet();
		Iterator<?> iter = keySet.iterator();
		while(iter.hasNext()){
			String key = (String) iter.next();
			paramMap.put(key, request.getParameter(key));
			logger.info("request.getParameter(\""+key+"\", \""+request.getParameter(key)+"\")");
		}
		return paramMap;
	}
		
	@RequestMapping(value = "/user/userApplyInsertView.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String userApplyView(Model model, HttpServletRequest request) throws Exception {
		
		boolean adminChk = ComSecurityUserUtil.hasAdminRole();
		
		/* 행정구역 시도 세팅 */
		List<EgovMap> sidoList = this.admcumulativeDAO.selectSidoList();
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("adminChk", adminChk);
		
		return "user/userApplyInsertView";
	}
	
	@RequestMapping(value = "/user/sigunguList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sigunguList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 행정구역 시군구 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.admItemInventoryService.selectSigungulist(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/user/userIdCheck.do")
	@ResponseBody
	public ModelAndView userIdCheck(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		int result = this.admAdmManagementService.selectAdminIdCheck(map);
		mv.addObject("chk", result);
		
		return mv;
	}
	
	@RequestMapping(value = "/user/newPw.do")
	@ResponseBody
	public ModelAndView newPw(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		
//		String randomPw = randomPw(8);
//		String randomPw = "";
		/*
		StringBuffer strBfRandomPw = new StringBuffer(); 
		strBfRandomPw.append(randomPw);
		randomPw = strBfRandomPw.toString().trim();
		 */
		
		String randomPw = ComSecurityUserUtil.getRandomPasswd(8);
		mv.addObject("value", randomPw);
		return mv;
	}
	
	private String randomPw(int length) {
		String baseCharacter = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<length;i++) {
			int index = (int)(Math.random()*baseCharacter.length());
			sb.append(baseCharacter.charAt(index));			
		}
		return sb.toString();
	}
	
	@RequestMapping(value = "/user/userApplyInsert.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView userApplyInsert(
		   MultipartHttpServletRequest uploadfiles,
		   HttpServletRequest request,
		   HttpServletResponse response,
		   UserApplyVO vo,
		   ModelMap modal,
           @RequestParam(value = "matchName", required = false) List<String> matchName
           ) throws Exception {
		
			// 응답용 객체를 생성하고, jsonView 를 사용하도록 합니다.
			ModelAndView model = new ModelAndView();
			model.setView(jsonView);
			
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			ComParamLogger.requestGetParameterToMap("UserApplyController.java", "userApplyInsert", "request", request);
			/*
			#	Parameter0 # userGovYn : [N]
			#	Parameter1 # userNm : [이름]
			#	Parameter2 # userOrgNm : [소속]
			#	Parameter3 # sido : [11]
			#	Parameter4 # emlRcptnAgreYn : [Y]
			#	Parameter5 # userAuth : [W]
			#	Parameter6 # userDist : [11]
			#	Parameter7 # userPw : [GwDBYb10!]
			#	Parameter8 # emlSndngYn : [Y]
			#	Parameter9 # userEmail : [realchance257@gmail.com]
			#	Parameter10 # useYn : []
			#	Parameter11 # userId : [a1]
			*/
			
			String userId         = EgovStringUtil.nullConvert(request.getParameter("userId")).toLowerCase();
			String userPwReq      = EgovStringUtil.nullConvert(request.getParameter("userPw"));
			String userPw         = encryptionComponent.sha256(userPwReq);
			String userDist       = EgovStringUtil.nullConvert(request.getParameter("userDist"));
			String userOrgNm      = EgovStringUtil.nullConvert(request.getParameter("userOrgNm"));
			String userIp         = getRequesterIP(request);
			String emlRcptnAgreYn = EgovStringUtil.nullConvert(request.getParameter("emlRcptnAgreYn"));
			String userEmail      = EgovStringUtil.nullConvert(request.getParameter("userEmail"));
			String emlSndngYn     = EgovStringUtil.nullConvert(request.getParameter("emlSndngYn"));
			String useYn          = EgovStringUtil.nullConvert(request.getParameter("useYn"));
			String currentUser    = EgovStringUtil.nullConvert(ComSecurityUserUtil.getUser());
			
			vo.setUserId(userId);
			vo.setUserPw(userPw);
			vo.setUserDist(userDist);
			vo.setUserDistrict(userDist);
			vo.setUserOrgNm(userOrgNm);
			vo.setUserIp(userIp);
			vo.setFrstRegUser(userId);
			vo.setEmlRcptnAgreYn(emlRcptnAgreYn);
			vo.setUseYn(useYn);
			
			//if:관리자가 로그인해서 입력한 경우, 관리자를 입력 | else:화면에서 받아온 userId를 입력
			if( currentUser == null ){
				vo.setFrstRegUser(currentUser);
			} else {
				vo.setFrstRegUser(userId);
			}
			
			// 현재시간을 기준해서 5분전에 입력한 데이터가 있는지 확인
			int beforeInsertCheck = SERVICE.userApplyChk(vo);
			
			if(beforeInsertCheck !=0) {
				model.addObject("userPwReq", userPwReq);
				model.addObject("resultYn", "0");
				model.addObject("beforeInsertCheck", "fail");
				
			} else {
				List<MultipartFile> uploadFiles = uploadfiles.getFiles("uploadFileOne");
				int result = SERVICE.userApplyInsert(vo, uploadFiles, matchName);	//파일업로드와 DB Insert처리
				
				//메일발송처리가 Y인 경우
				if( ("Y").equals(emlSndngYn)) {
					/*
					수신자 recptnPerson	"realchance257@gmail.com"	
					제목 sj	"22"
					내용 emailCn
					*/
					// 발송메일을 등록한다.
					AdmSndngMailVO admSndngMailVO = new AdmSndngMailVO();
					admSndngMailVO.setDsptchPerson(userEmail);
		    		admSndngMailVO.setSj(userId);				//제목
		    		admSndngMailVO.setEmailCn(userId);			//내용
			    	boolean emlSndngResult = admSndngMailRegistService.insertSndngMail(admSndngMailVO);
				}
				model.addObject("userPwReq", userPwReq);
				model.addObject("resultYn", result);
			}
			
			return model;
	}
	
	public String getRequesterIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if ((utilComp.isNullOrEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((utilComp.isNullOrEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((utilComp.isNullOrEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if ((utilComp.isNullOrEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if ((utilComp.isNullOrEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
