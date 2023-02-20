package vestap.adm.user.management;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.adm.ems.service.AdmSndngMailVO;
import vestap.com.ComParamLogger;
import vestap.com.ComSecurityUserUtil;
import vestap.egov.cmm.util.EgovStringUtil;
import vestap.sys.cmm.util.EncryptionComponent;
import vestap.sys.cmm.util.UtilityComponent;
import vestap.sys.sec.handler.VestapUserDetails;
import vestap.sys.sec.user.UserApplyService;
import vestap.sys.sec.user.UserApplyVO;

@Controller
public class AdmUserManagementController {
//	private static final Logger logger = LoggerFactory.getLogger(AdmUserManagementController.class);
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@Resource(name = "encryptionComponent")
	private EncryptionComponent encryptionComponent;
	
	@Resource(name = "admUserManagementService")
	private AdmUserManagementService admUserManagementService;
	
	@Resource(name = "userApplyService")
	private UserApplyService userApplyService;
	
	@RequestMapping(value = "/admin/user/management/list.do")
	public ModelAndView managementList(ModelAndView mv) throws Exception {
		String viewName = "admin/user/management/list";
		boolean adminChk = ComSecurityUserUtil.hasAdminRole();
		if(adminChk == false) {
			viewName = "redirect:/loginPage.do";
		} else {
			viewName = "admin/user/management/list";
		}
		
		mv.setViewName(viewName);
		
		EgovMap map = new EgovMap();
		int pageLimit = 10; 
		map.put("pageLimit",pageLimit);
		
		ComParamLogger.paramToVO("AdmUserManagementController.java", "managementList", "map", map);
		
		List<EgovMap> userList = this.admUserManagementService.selectUserList(map);
		int pageCnt = this.admUserManagementService.selectPageCount(map);
		mv.addObject("userList", userList);
		mv.addObject("pageCnt", pageCnt);
		EgovMap resultMap = this.admUserManagementService.selectManagementSetting(map); 
		mv.addAllObjects(resultMap);
		
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}
	
	@RequestMapping(value = "/admin/user/management/searchAction.do")
	public ModelAndView memberSearch(ModelAndView mv, HttpServletRequest request) throws Exception {
		
		mv.setViewName("jsonView");
//		EgovMap map = this.paramMap(request);
		EgovMap map = ComParamLogger.requestGetParameterToEgovMap("AdmUserManagementController.java", "memberSearch", "req", request);
		int pageLimit = 10;
		map.put("pageLimit", pageLimit);
		
//		List<EgovMap> userList = this.admUserManagementService.selectSearchUserList(map);
//		int pageCnt = this.admUserManagementService.selectSearchPageCount(map);
		List<EgovMap> userList = this.admUserManagementService.selectUserList(map);
		int pageCnt = this.admUserManagementService.selectPageCount(map);
		
		mv.addObject("userList", userList);
		mv.addObject("pageCnt", pageCnt);
		
		return mv;
	}
	
	@RequestMapping(value = "/admin/user/management/idCheck.do")
	@ResponseBody
	public ModelAndView idCheck(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		int result = this.admUserManagementService.selectUserIdCheck(map);
		mv.addObject("chk", result);
		return mv;
	}
	
	@RequestMapping(value = "/admin/user/management/newPw.do")
	@ResponseBody
	public ModelAndView newPw(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		
//		String randomPw = randomPw(8);
		/*
		String randomPw = "";
		StringBuffer strBfRandomPw = new StringBuffer(); 
		strBfRandomPw.append(randomPw(8));
		strBfRandomPw.append("!");
		randomPw = strBfRandomPw.toString().trim();
		*/
		String randomPw = ComSecurityUserUtil.getRandomPasswd(8);
		
		mv.addObject("value", randomPw);
		return mv;
	}
	
	@RequestMapping(value = "/admin/user/management/sigunguList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sigunguList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 행정구역 시군구 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.admUserManagementService.selectSigungulist(map);
		mv.addObject("list", list);
		return mv;
	}

	@RequestMapping(value = "/admin/user/management/enroll.do")
	@ResponseBody
	public ModelAndView enrollUser(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		
		EgovMap map = this.paramMap(request);
		String password = (String)map.get("userPw");
		map.remove("userPw");
		map.put("userPw", encryptionComponent.sha256(password));
		int result = this.admUserManagementService.insertUser(map);
		mv.addObject("chk", result);
		
		return mv;
	}
	
	@RequestMapping(value = "/admin/user/management/pwChange.do")
	@ResponseBody
	public ModelAndView pwChange(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
//		String randomPw = randomPw(8);
		/*
		String randomPw = "";
		StringBuffer strBfRandomPw = new StringBuffer(); 
		strBfRandomPw.append(randomPw(8));
		strBfRandomPw.append("!");
		randomPw = strBfRandomPw.toString().trim();
		*/
		EgovMap map=  this.paramMap(request);
		String randomPw = ComSecurityUserUtil.getRandomPasswd(8);
		map.put("password", encryptionComponent.sha256(randomPw));
		int result = this.admUserManagementService.updateUserPw(map);
		mv.addObject("chk", result);
		mv.addObject("value", randomPw);
		return mv;
	}
	
	@RequestMapping(value = "/admin/user/management/deleteUser.do")
	@ResponseBody
	public ModelAndView deleteUser(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("jsonView");
		EgovMap map= this.paramMap(request);
		int result = this.admUserManagementService.deleteUser(map);
		mv.addObject("chk", result);
		return mv;
	}
	
	private EgovMap paramMap(HttpServletRequest request){
		logger.debug("\n@@@@ paramMap Start");
		EgovMap paramMap = new EgovMap();

		Set keySet = request.getParameterMap().keySet();
		Iterator<?> iter = keySet.iterator();
		while(iter.hasNext()){
			String key = (String) iter.next();
			paramMap.put(key, request.getParameter(key));
			logger.debug("request.getParameter(\""+key+"\", \""+request.getParameter(key)+"\")");
		}
		logger.debug("\n@@@@ paramMap End");
		return paramMap;
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
	
	@RequestMapping(value = "/admin/user/management/setPw.do")
	public ModelAndView setPw(ModelAndView mv, HttpServletRequest request) throws Exception {
		String viewName = "redirect:/admin/user/management/list.do";
		mv.setViewName(viewName);
		EgovMap map= this.paramMap(request);
		if(map.get("id")!=null && map.get("pw")!=null) {
		String data = map.get("id").toString();
		String password = encryptionComponent.sha256(map.get("pw").toString());
		map.put("data", data);
		map.put("password", password);
		this.admUserManagementService.updateUserPw(map);
		}
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}
	
	@RequestMapping(value = "/admin/user/management/getUserInfo.do")
	public ModelAndView getUserInfo(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("jsonView");
		EgovMap map= this.paramMap(request);
		EgovMap result = this.admUserManagementService.getUserInfo(map);
		mv.addObject("chk", result);
		return mv;
	}
	
	@RequestMapping(value = "/admin/user/management/setUserInfo.do")
	public ModelAndView setUserInfo(ModelAndView mv, HttpServletRequest request) throws Exception {
		String viewName = "redirect:/admin/user/management/list.do";
		mv.setViewName(viewName);
		EgovMap map= this.paramMap(request);
		if(map.get("id")!=null && map.get("type")!=null && map.get("val")!=null) {
		this.admUserManagementService.updateUserInfo(map);
		}
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}
	
	@RequestMapping(value = "/admin/user/management/updateAction.do")
	public ModelAndView updateAction(ModelAndView mv, HttpServletRequest request) throws Exception {
		String viewName = "redirect:/admin/user/management/list.do";
		mv.setViewName(viewName);
		EgovMap map = this.paramMap(request);
		logger.debug("\n================================updateAction===============================");
		
		System.out.println(map);
		this.admUserManagementService.updateUserInfo(map);
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}
	
	//메뉴 => 사용자가 본인정보 확인
	@RequestMapping(value = "/member/base/user/info/view.do")
	public ModelAndView memberInfoView(ModelAndView mv, Authentication authentication) throws Exception {
		String viewName = "user/info/view";
		mv.setViewName(viewName);
		
		EgovMap map = new EgovMap();		
		logger.debug("\n==========================================사용자정보==========================================");
		logger.debug(authentication.getPrincipal());
		map.put("data", authentication.getPrincipal());
		
		EgovMap result = this.admUserManagementService.getUserInfo(map);
		System.out.println("#### result\n"+result);
		
		EgovMap resultMap = this.admUserManagementService.selectManagementSetting(map);
		
		mv.addAllObjects(resultMap);
		mv.addObject("userInfo", result);
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		
		return mv;
	}
	
	@RequestMapping(value = "/member/base/user/info/download.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void userGovDocDownload(
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {
		
		try {
			
		boolean authChk = false;
		
		//정보조회
		EgovMap map = new EgovMap();
		map = ComParamLogger.requestGetParameterToEgovMap("AdmUserManagementController.java", "userGovDocDownload", "req", request);
		map.put("data", map.get("userId"));
		
		EgovMap result = this.admUserManagementService.getUserInfo(map);
		String  resultUserAuth = (String) result.get("userAuth");
		String  resultUserId = (String) result.get("userId");
		
		//시도하는 사람의 권한과 아이디 확인 S
		Authentication auth = (Authentication) request.getUserPrincipal();
		VestapUserDetails details = (VestapUserDetails) auth.getDetails();
		
		String attemptUserAuth = details.getUser_auth();
		String attemptUserId = details.getUser_id();
				
		if( ("A").equals(attemptUserAuth) ) {
			authChk = true;
		} else if ( (resultUserId).equals(attemptUserId) ) {
			authChk = true;
		} else {
			authChk = false;
		}
		
		if (authChk == true) {
			this.admUserManagementService.userGovDocDownloadAction(response, result);
		}
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/member/base/user/info/sigunguList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView memberSigunguList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 행정구역 시군구 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.admUserManagementService.selectSigungulist(map);
		mv.addObject("list", list);
		
		return mv;
	}
	
	@RequestMapping(value = "/member/base/user/info/updateAction.do")
	public ModelAndView memberUpdateAction(
           MultipartHttpServletRequest uploadfiles,
           HttpServletRequest request,
           ModelAndView mv,
           @RequestParam(value = "matchName", required = false) List<String> newFileName
           ) throws Exception {
		
		logger.debug("\n================================updateAction===============================");
		ComParamLogger.requestGetParameterToMap("AdmUserManagementController", "memberUpdateAction", "req", request);
		/*
		#	Parameter0 # _csrf : [8ea287c3-9c18-4105-8712-e23a5d33d927]
		#	Parameter1 # userNm : [건축도시공간연구소(김용국박사님)]
		#	Parameter2 # useYnBefore : [Y]
		#	Parameter3 # userDistrictSido : []
		#	Parameter4 # aprvDt : [2015년 05월 20일 PM 05:20]
		#	Parameter5 # userAuth : [W]
		#	Parameter6 # userDist : [29]
		#	Parameter7 # userDistSigungu : []
		#	Parameter8 # userDistrict : []
		#	Parameter9 # userGovYn : [Y]
		#	Parameter10 # userDistrictFull : []
		#	Parameter11 # sido : [29]
		#	Parameter12 # userListPage : [1]
		#	Parameter13 # userPw : []
		#	Parameter14 # emlSndngYn : [N]
		#	Parameter15 # userEmail : []
		#	Parameter16 # useYn : [Y]
		#	Parameter17 # orgFileNm : [996E5D335A149EC207 (1).jpg]
		#	Parameter18 # userDistFull : [29]
		#	Parameter19 # userOrgNm : []
		#	Parameter20 # fileIdx : [15]
		#	Parameter21 # userId : [auri02]
		#	Parameter22 # recordCountPerPage : []
		#	Parameter23 # emlRcptnAgreYn : [N]
		#	Parameter24 # userDistSido : [29]
		#	Parameter25 # userDistrictSigungu : []
		#	Parameter26 # userListLimit : [35]
		#	Parameter27 # stdFileNm : [86714d872015418996f62feab4f4296d.jpg]
		*/
		String userId          = EgovStringUtil.nullConvert(request.getParameter("userId"));
		String userNm          = EgovStringUtil.nullConvert(request.getParameter("userNm"));
		String userPwReq       = EgovStringUtil.nullConvert(request.getParameter("userPw"));
		String userPw          = encryptionComponent.sha256(userPwReq);
		String userOrgNm       = EgovStringUtil.nullConvert(request.getParameter("userOrgNm"));
		String userDist        = EgovStringUtil.nullConvert(request.getParameter("userDist"));
		String userDistrict    = EgovStringUtil.nullConvert(request.getParameter("userDistrict"));
		String userEmail       = EgovStringUtil.nullConvert(request.getParameter("userEmail"));
		String useYnBefore     = EgovStringUtil.nullConvert(request.getParameter("useYnBefore"));		//이전 사용상태
		String useYn           = EgovStringUtil.nullConvert(request.getParameter("useYn"));				//변경된 사용상태
		String aprvDt          = EgovStringUtil.nullConvert(request.getParameter("aprvDt"));				//최종승인일
		String userAuth        = EgovStringUtil.nullConvert(request.getParameter("userAuth"));
//		String beforeFileNm    = request.getParameter("matchName");
		String beforeFileNm    = request.getParameter("orgFileNm");
		int fileIdx            = EgovStringUtil.zeroConvert(request.getParameter("fileIdx"));
		String emlRcptnAgreYn  = EgovStringUtil.nullConvert(request.getParameter("emlRcptnAgreYn"));
		String emlSndngYn      = EgovStringUtil.nullConvert(request.getParameter("emlSndngYn"));
		String orgFileNm       = EgovStringUtil.nullConvert(request.getParameter("orgFileNm"));
		String stdFileNm       = EgovStringUtil.nullConvert(request.getParameter("stdFileNm"));
		String securityAgreeYn = EgovStringUtil.nullConvert(request.getParameter("securityAgreeYn"));
		String purposeUseCd    = EgovStringUtil.nullConvert(request.getParameter("purposeUseCd"));
		String purposeUseNm    = EgovStringUtil.nullConvert(request.getParameter("purposeUseNm"));
		
		/*
		 * 기존에 있는 파일명은 HttpServletRequest request를 통해 existFileNm 으로 들어옴
		 * 신규파일명은 @RequestParam(value = "matchName", required = false) List<String> matchName 으로 들어옴
		 */
		UserApplyVO vo = new UserApplyVO();
		
		
		if( !(useYnBefore).equals(useYn) ){
    		if( ("Y").equals(useYn) ){
    			vo.setAprvDt("Y");
    		}
    	}
		
		vo.setUserId(userId);
		vo.setUserNm(userNm);
		if(userPwReq.length()> 1){
			vo.setUserPw(userPw);
		}
		vo.setUserDist(userDist);
		vo.setUserDistrict(userDistrict);
		vo.setUserEmail(userEmail);
		vo.setUseYn(useYn);
		vo.setUserOrgNm(userOrgNm);
		vo.setUserAuth(userAuth);
		vo.setFileIdx(fileIdx);
		vo.setFrstRegUser(userId);
		vo.setEmlRcptnAgreYn(emlRcptnAgreYn);
		vo.setOrgFileNm(orgFileNm);
		vo.setStdFileNm(stdFileNm);
		vo.setBeforeFileNm(beforeFileNm);
		vo.setSecurityAgreeYn(securityAgreeYn);
		vo.setPurposeUseCd(purposeUseCd);
		vo.setPurposeUseNm(purposeUseNm);
		
		List<MultipartFile> uploadFiles = uploadfiles.getFiles("uploadFileOne");
		int userApplyUpdateResult = userApplyService.userApplyUpdate(vo, uploadFiles, newFileName);
		mv.addObject("userApplyUpdateResult", userApplyUpdateResult);
		
		//관리자 권한체크
		boolean adminYn = ComSecurityUserUtil.hasAdminRole();
		boolean admMailResult = false;
		String viewName = "";
		if(adminYn == true){
			
			//관리자가 메일발송을 Y로 
			if( ("Y").equals(emlSndngYn) ){
				AdmSndngMailVO admSndngMailVO = new AdmSndngMailVO();
				String dsptchPerson = EgovStringUtil.nullConvert(ComSecurityUserUtil.getUser());
				admSndngMailVO.setDsptchPerson(dsptchPerson);		                            //발신자ID
				admSndngMailVO.setRcverId(userId);                                              //수신자ID
				admSndngMailVO.setRcverNm(userNm);                                              //수신자ID
				admSndngMailVO.setRecptnPerson(userEmail);		                                //수신자이메일
				admSndngMailVO.setSj(userNm+"님, 기후변화취약성 평가 지원 도구 시스템 사용자권한이 업데이트 되었습니다.");	//제목
				admSndngMailVO.setUseYnBefore(useYnBefore);		                                //이전사용상태
				admSndngMailVO.setUseYn(useYn);		                                            //현재사용상태
//				admSndngMailVO.setEmailCn(emailCn);		                                        //메일내용은 impl에 구현
				admSndngMailVO.setUserPwReq(userPwReq);			                            	//패스워드
				
				// 발송메일을 등록한다.
		    	admMailResult = this.admUserManagementService.insertAdmUserRegistSndngMail(admSndngMailVO);
		    	
		    	if (admMailResult == true){
		    		mv.addObject("admMailResult", admMailResult);
		    	}
			}
			viewName = "forward:/admin/user/management/list.do";
		} else {
			viewName = "redirect:/member/base/main.do";
		}
		mv.setViewName(viewName);
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		
		return mv;
	}
	
	@RequestMapping(value = "/admin/user/management/sort.do")
	public ModelAndView memberSort(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		
		EgovMap map = this.paramMap(request);
		int pageLimit = 10; 
		map.put("pageLimit",pageLimit);
		System.out.println(map);
		
		List<EgovMap> userList = this.admUserManagementService.selectUserList(map);
		int pageCnt = this.admUserManagementService.selectPageCount(map);
		
		mv.addObject("userList", userList);
		mv.addObject("pageCnt", pageCnt);
		mv.addAllObjects(map);
		
		return mv;
	}
	
	//메뉴 => 사용자가 본인정보 확인
	@RequestMapping(value = "/admin/user/management/detailView.do")
	public ModelAndView adminUserManagementDetailView(ModelAndView mv, HttpServletRequest request) throws Exception {
		
		ComParamLogger.requestGetParameterToMap("AdmUserManagementController.java", "adminUserManagementDetailView", "req", request);
		
		boolean adminChk = ComSecurityUserUtil.hasAdminRole();
		String viewName = "user/info/view";
		if(adminChk == false) {
			viewName = "redirect:/loginPage.do";
		} else {
			viewName = "user/info/view";
		}
		
		String listFormUserId = EgovStringUtil.isNullToString(request.getParameter("listFormUserId"));
		String userListPage   = EgovStringUtil.isNullToString(request.getParameter("userListPage"));
		String userListLimit  = EgovStringUtil.isNullToString(request.getParameter("userListLimit"));
		
		EgovMap map = new EgovMap();
		logger.debug("==========================================사용자정보==========================================");
		map.put("data", listFormUserId);
		
		EgovMap result = this.admUserManagementService.getUserInfo(map);
		logger.debug("result\n"+result);
		
		EgovMap resultMap = this.admUserManagementService.selectManagementSetting(map);
		mv.addAllObjects(resultMap);
		mv.addObject("userInfo", result);
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		mv.addObject("userListLimit", userListLimit);
		mv.addObject("userListPage", userListPage);
		mv.addObject("adminChk", adminChk);
		
		mv.setViewName(viewName);
		return mv;
	}
}
