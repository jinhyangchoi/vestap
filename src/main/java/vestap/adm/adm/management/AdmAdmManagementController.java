package vestap.adm.adm.management;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.com.ComSecurityUserUtil;
import vestap.sys.bbs.notice.NoticeBoardServiceImpl;
import vestap.sys.cmm.util.EncryptionComponent;
import vestap.sys.cmm.util.UtilityComponent;

@Controller
public class AdmAdmManagementController {
	private static final Logger logger = LoggerFactory.getLogger(AdmAdmManagementController.class);

	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;

	@Resource(name = "encryptionComponent")
	private EncryptionComponent encryptionComponent;

	@Resource(name = "admAdmManagementService")
	private AdmAdmManagementService admAdmManagementService;

	@Resource(name = "noticeBoardService")
	private NoticeBoardServiceImpl noticeBoardService;

	/**
	 * 관리자 계정 관리 - 전체조회
	 */
	@RequestMapping(value = "/admin/admin/management/list.do")
	public ModelAndView manageMentList(ModelAndView mv) throws Exception {
		String viewName = "admin/admin/management/list";
		mv.setViewName(viewName);

		EgovMap map = new EgovMap();
		int pageLimit = 10;
		map.put("pageLimit", pageLimit);

		List<EgovMap> adminList = this.admAdmManagementService.selectAdminList(map);
		int pageCnt = this.admAdmManagementService.selectPageCount(map);
		mv.addObject("adminList", adminList);
		mv.addObject("pageCnt", pageCnt);

		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}

	/**
	 * 관리자 계정 관리 - 계정 중복 확인
	 */
	@RequestMapping(value = "/admin/admin/management/idCheck.do")
	@ResponseBody
	public ModelAndView idCheck(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		int result = this.admAdmManagementService.selectAdminIdCheck(map);
		mv.addObject("chk", result);

		return mv;

	}

	/**
	 * 관리자 계정 관리 - 비밀번호 생성(발급)
	 */
	@RequestMapping(value = "/admin/admin/management/newPw.do")
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

	/**
	 * 관리자 계정 관리 - 비밀번호 생성(발급)
	 */
	private String randomPw(int length) {
		String baseCharacter = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int index = (int) (Math.random() * baseCharacter.length());
			sb.append(baseCharacter.charAt(index));
		}
		return sb.toString();
	}

	/**
	 * 관리자 계정 관리 - 계정 등록
	 */
	@RequestMapping(value = "/admin/admin/management/enroll.do")
	@ResponseBody
	public ModelAndView enrollAdmin(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);

		String password = (String) map.get("userPw");
		map.remove("userPw");
		map.put("userPw", encryptionComponent.sha256(password));
		int result = this.admAdmManagementService.insertAdmin(map);
		mv.addObject("chk", result);
		return mv;
	}

	/**
	 * 관리자 계정 관리 - 계정 삭제
	 */
	@RequestMapping(value = "/admin/admin/management/deleteAdmin.do")
	@ResponseBody
	public ModelAndView deleteAdmin(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		int result = this.admAdmManagementService.deleteAdmin(map);
		mv.addObject("chk", result);

		return mv;
	}

	/**
	 * 관리자 접속 통계 - 전체조회
	 */
	@RequestMapping(value = "/admin/admin/accesslog/list.do")
	public ModelAndView adminAccessList(ModelAndView mv) throws Exception {
		
		String viewName = "admin/admin/accesslog/list";
		mv.setViewName(viewName);

		EgovMap map = new EgovMap();
		int pageLimit = 10;
		map.put("pageLimit", pageLimit);

		List<EgovMap> adminLogList = this.admAdmManagementService.selectAdminAccessLogList(map);
		List<EgovMap> adminIdList = this.admAdmManagementService.selectAdminIdList(map);
		int accessPageCnt = this.admAdmManagementService.selectAccessPageCnt(map);
		mv.addObject("adminLogList", adminLogList);
		mv.addObject("adminIdList", adminIdList);
		mv.addObject("pageCnt", accessPageCnt);

		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}

	/**
	 * 관리자 접속 통계 - 기간설정조회
	 */
	@RequestMapping(value = "/admin/admin/accesslog/DateList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView logDateList(HttpServletRequest request, ModelAndView mv,
			@RequestParam(required = false) Map<String, String> params) throws Exception {
		
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		int pageLimit = 10;
		map.put("pageLimit", pageLimit);

		List<EgovMap> adminLogList = this.admAdmManagementService.selectAdminAccessLogList(map);
		int accessPageCnt = this.admAdmManagementService.selectAccessPageCnt(map);

		mv.addObject("adminLogList", adminLogList);
		mv.addObject("pageCnt", accessPageCnt);

		return mv;
	}

	/**
	 * 관리자 접속 통계 - 엑셀 다운로드
	 */
	@RequestMapping(value = "/admin/admin/accesslog/downloadLogList.do")
	public void downloadLogList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "adminId", required = true) String adminId,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate) {

		EgovMap map = this.paramMap(request);

		map.put("adminId", adminId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		List<EgovMap> logList = null;

		try {

			logList = this.admAdmManagementService.selectAdminAccessLogList(map);

			this.admAdmManagementService.downloadAdminAccessLogList(logList, map, response);

		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}

	/**
	 * 관리자 작업 이력 - 전체
	 */
	@RequestMapping(value = "/admin/admin/requestlog/list.do")
	public ModelAndView adminRequestList(ModelAndView mv, HttpServletRequest request) throws Exception {
		String viewName = "admin/admin/requestlog/list";
		mv.setViewName(viewName);

		EgovMap map = this.paramMap(request);

		int pageLimit = 10;
		map.put("pageLimit", pageLimit);
		List<EgovMap> requestLogList = this.admAdmManagementService.selectRequestLogList(map);

		List<EgovMap> adminIdList = this.admAdmManagementService.selectAdminIdList(map);
		List<EgovMap> refCode = this.admAdmManagementService.selectRefcode();

		int requestPageCnt = this.admAdmManagementService.selectReqestLogCnt(map);

		mv.addObject("adminId", map.get("adminId"));
		mv.addObject("requestLogList", requestLogList);
		mv.addObject("refCode", refCode);
		mv.addObject("adminIdList", adminIdList);
		mv.addObject("pageCnt", requestPageCnt);
		mv.addObject("viewName", this.utilComp.navDepth(viewName));

		for (int i = 0; i < requestLogList.size(); i++) {

			if (requestLogList.get(i).get("reqParam") != null) {
				if (!(requestLogList.get(i).get("reqParam")).toString().contains("csrf")) {
					System.out.println(requestLogList.get(i));
					String requestMap = requestLogList.get(i).get("reqParam").toString();
					String[] params = requestMap.split("&");
					String details = "";
					for (int j = 0; j < params.length; j++) {

						String[] param = params[j].split("=");
						if (param.length > 1) {
							EgovMap parameter = new EgovMap();
							String key = param[0];
							String value = param[1];

							System.out.println("key : " + key + " / value : " + value);
							if (key.equals("adminId") || key.equals("adminid") || key.equals("userId")) {
								details = "계정 :" + value + " ";

							} /*
								 * else if(key.equals("idx")){
								 * if(requestLogList.get(i).get("urlDepth3").toString().equals("건의사항")){
								 * 
								 * }else if(requestLogList.get(i).get("urlDepth3").toString().equals("공지사항")){
								 * BoardVO vo = this.noticeBoardService.getNoticeContent(Integer.parseInt(value));
								 * details = "제목 :" + vo.getBbs_title() + " "; }
								 * 
								 * }
								 */
							 else if (key.equals("title")) {
								details = "제목 : " + value + " ";
							} else {
								// details = key+" : " + value + " ";
							}
						}

					}
					System.out.println(details);
					requestLogList.get(i).put("updateParam", details);
					System.out.println("-------------------------------------------------------");
				}

			}

		}

		// System.out.println(requestLogList);
		return mv;
	}

	/**
	 * 관리자 작업 이력 - 기간설정조회
	 */
	@RequestMapping(value = "/admin/admin/requestlog/DateList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView requestlogDateList(HttpServletRequest request, ModelAndView mv,
			@RequestParam(required = false) Map<String, String> params) throws Exception {
		mv.setViewName("jsonView");

		EgovMap map = this.paramMap(request);
		int pageLimit = 10;
		map.put("pageLimit", pageLimit);

		List<EgovMap> requestLogList = this.admAdmManagementService.selectRequestLogList(map);
		int requestPageCnt = this.admAdmManagementService.selectReqestLogCnt(map);

		mv.addObject("requestLogList", requestLogList);
		mv.addObject("pageCnt", requestPageCnt);

		return mv;
	}

	/**
	 * 관리자 작업 이력 - 엑셀 다운로드
	 */
	@RequestMapping(value = "/admin/admin/requestlog/downloadRequestLogList.do")
	public void downloadRequestLogList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "adminId", required = true) String adminId,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate) {

		EgovMap map = this.paramMap(request);

		map.put("adminId", adminId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		List<EgovMap> logList = null;

		try {

			logList = this.admAdmManagementService.selectRequestLogList(map);

			this.admAdmManagementService.downloadAdminRequestLogList(logList, map, response);

		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/admin/admin/requestlog/refcode.do", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView refCode(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");

		List<EgovMap> refCode = this.admAdmManagementService.selectRefcode();
		mav.addObject("refCode", refCode);

		return mav;
	}

	private EgovMap paramMap(HttpServletRequest request) {
		EgovMap paramMap = new EgovMap();

		Set keySet = request.getParameterMap().keySet();
		Iterator<?> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			paramMap.put(key, request.getParameter(key));
			logger.info("request.getParameter(\"" + key + "\", \"" + request.getParameter(key) + "\")");
		}

		return paramMap;
	}

}
