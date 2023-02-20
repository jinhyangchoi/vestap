package vestap.adm.sys.indicator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import vestap.sys.cmm.util.FileDownloadComponent;
import vestap.sys.cmm.util.UtilityComponent;
import vestap.sys.sec.handler.VestapUserDetails;
import vestap.sys.sec.user.UserAccessVO;

@Controller
public class SysIndicatorController {
	
	private static final Logger logger = LoggerFactory.getLogger(SysIndicatorController.class);
	
	@Resource(name = "sysIndicatorService")
	private SysIndicatorServiceImpl SERVICE;

	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent fileDownloadComp;
	
	@RequestMapping(value = "/admin/system/indicator/list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String indicatorList(ModelMap model, HttpServletRequest request) {
		
		String viewName = "admin/system/indicator";
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 20;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination("1", LIMIT, 3);
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		UserAccessVO userVO = null;
		
		List<Map<String, String>> districtList = null;
		List<Map<String, String>> fieldList = null;
		List<Map<String, String>> ipccList_1 = null;
		List<Map<String, String>> ipccList_2 = null;
		List<Map<String, String>> indicatorList = null;
		List<Map<String, String>> districtSdList = null;
		
		/**
		 * DEFAULT
		 * 지표 등록 접속 시 기본 설정
		 */
		String ipcc = "DL001";
		String field = "FC001";
		String fieldName = "건강";
		
		try {
			
			userVO = this.SERVICE.getUserInfo(name);
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			String userAuth = userVO.getUser_auth();
			
			if(userAuth.equals("W")) {
				params.put("tableCategory", "sgg");
			} else if(userAuth.equals("B")) {
				params.put("tableCategory", "emd");
			} else if(userAuth.equals("A")){
				params.put("tableCategory", "adm");
			}
			
			params.put("code", userVO.getUser_dist());
			params.put("ipcc", "IPCC1");
			
			districtList = this.SERVICE.getDistrictInfo(params);
			districtSdList = this.SERVICE.getDistrictSdInfo(params);
			System.out.println(districtList);
			fieldList = this.SERVICE.getFieldInfo();
			
			params.put("field", field);
			params.put("userName", name);
			params.put("startRow", pageInfo.getFirstRecordIndex());
			params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
			
			indicatorList = this.SERVICE.getIndicatorListView(params);
			pageInfo.setTotalRecordCount(this.SERVICE.getIndicatorListViewTotCnt(params));
			
			ipccList_1 = this.SERVICE.getIpccList(params);
			
			params.put("ipcc", "IPCC2");
			params.put("parent", ipcc);
			
			ipccList_2 = this.SERVICE.getIpccList(params);
		} catch(Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		model.addAttribute("viewName", this.utilComp.navDepth(viewName));
		model.addAttribute("districtList", districtList);
		model.addAttribute("districtSdList", districtSdList);
		model.addAttribute("fieldList", fieldList);
		model.addAttribute("indicatorList", indicatorList);
		model.addAttribute("ipccList_1", ipccList_1);
		model.addAttribute("ipccList_2", ipccList_2);
		model.addAttribute("fieldName", fieldName);
		model.addAttribute("indicatorPage", pageInfo);
		
		if(request.getParameter("fileActive") != null) {
			model.addAttribute("fileActive", request.getParameter("fileActive"));
		}
		
		return viewName;
	}
	
	/** 
	 * 지표 리스트 페이징 이동시
	 **/
	@RequestMapping(value = "/admin/system/indicator/fieldIndicator.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView fieldIndicator(HttpServletRequest request,
			@RequestParam(value = "page", required = true) String page,
			@RequestParam(value = "keyword", required = false) String keyword) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 20;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 3);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("userName", name);
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		
		if(keyword != null) {
			if(keyword.replaceAll(" ", "").length() > 1) {
				params.put("keyword", keyword.trim());		//문자열의 앞,뒤 공백은 지운다.
			}
		}
		
		List<Map<String, String>> indicatorList = null;
		
		try {
			
			indicatorList = this.SERVICE.getIndicatorListView(params);
			pageInfo.setTotalRecordCount(this.SERVICE.getIndicatorListViewTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("indicatorList", indicatorList);
		mav.addObject("indicatorPage", pageInfo);
		
		return mav;
	}
	
	
	//지표 파일업로드 >> 엑셀파일 템플릿 파일 다운로드
	@RequestMapping(value = "/admin/system/indicator/indicatorUpload.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void indicatorUpload(HttpServletRequest request, HttpServletResponse response) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		VestapUserDetails details = (VestapUserDetails) auth.getDetails();
		
		try {
			
			String fileName = this.SERVICE.indicatorDownload(details);
			
			this.fileDownloadComp.indicatorInputFormDownload(response, fileName, "vestapValue.xlsx");
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/admin/system/indicator/indicatorData.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView indicatorData(
			HttpServletRequest request,
			@RequestParam(value = "page", required = true) String page,
			@RequestParam(value = "indi_id", required = true) String indi_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		VestapUserDetails details = (VestapUserDetails) auth.getDetails();
		
		String name = auth.getName();
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 14;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 5);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("indi_id", indi_id);
		params.put("userName", name);
		params.put("isPage", "Y");
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		
		if(details.getUser_auth().equals("W")) {
			
			params.put("tableCategory", "sgg");
			
		} else if(details.getUser_auth().equals("B")) {
			
			params.put("tableCategory", "emd");
		} else{
			
			params.put("tableCategory", "adm");
		}
		
		List<Map<String, String>> indicatorInfoList = null;
		Map<String, String> indicatorListInfo = null;
		
		try {
			
			indicatorInfoList = this.SERVICE.getIndicatorDataInfo(params);
			
			if(indi_id.substring(0, 2).equals("IU")) {
				
				indicatorListInfo = this.SERVICE.getSysIndicatorList(params);
				
			} else if(indi_id.substring(0, 2).equals("IC")) {
				
				indicatorListInfo = this.SERVICE.getSytmIndicatorList(params);
				
			} else if(indi_id.substring(0, 2).equals("SC")) {
				
				indicatorListInfo = this.SERVICE.getScenIndicatorList(params);
			}
			
			pageInfo.setTotalRecordCount(this.SERVICE.getIndicatorDataInfoTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("indicatorInfoList", indicatorInfoList);
		mav.addObject("indicatorListInfo", indicatorListInfo);
		mav.addObject("indicatorPageInfo", pageInfo);
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/system/indicator/indicatorUpdateData.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView indicatorUpdateData(
			HttpServletRequest request,
			@RequestParam(value = "indi_id", required = true) String indi_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		VestapUserDetails details = (VestapUserDetails) auth.getDetails();
		
		String name = auth.getName();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("indi_id", indi_id);
		params.put("userName", name);
		params.put("isPage", "N");
		
		if(details.getUser_auth().equals("W")) {
			
			params.put("tableCategory", "sgg");
			
		} else if(details.getUser_auth().equals("B")) {
			
			params.put("tableCategory", "emd");
		} else{
			
			params.put("tableCategory", "adm");
		}
		
		List<Map<String, String>> indicatorDataList = null;
		Map<String, String> indicatorListInfo = null;
		
		try {
			/** 지표 데이터 */
			indicatorDataList = this.SERVICE.getIndicatorDataInfo(params);
			
			/** 지표 리스트 */
			if(indi_id.substring(0, 2).equals("IU")) {
				
				indicatorListInfo = this.SERVICE.getSysIndicatorList(params);
				
			} else if(indi_id.substring(0, 2).equals("IC")) {
				
				indicatorListInfo = this.SERVICE.getSytmIndicatorList(params);
				
			} else if(indi_id.substring(0, 2).equals("SC")) {
				
				indicatorListInfo = this.SERVICE.getScenIndicatorList(params);
			}
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("indicatorDataList", indicatorDataList);
		mav.addObject("indicatorListInfo", indicatorListInfo);
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/system/indicator/update.do", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView indicatorUpdate(HttpServletRequest request,
			@RequestParam Map<String, Object> params) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		VestapUserDetails details = (VestapUserDetails) auth.getDetails();
		
		String name = auth.getName();
		
		params.put("userName", name);
		
		if(details.getUser_auth().equals("W")) {
			params.put("tableCategory", "sgg");
		} else if(details.getUser_auth().equals("B")) {
			params.put("tableCategory", "emd");
		} else {
			params.put("tableCategory", "adm");
		}
		
		try {
			
			this.SERVICE.indicatorUpdate(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return new RedirectView("/admin/system/indicator/list.do");
	}
	
	@RequestMapping(value = "/admin/system/indicator/insert.do", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView indicatorInsert(
			@RequestParam Map<String, Object> params,
			@RequestParam(value = "indicatorUploadFile", required = false) MultipartFile file,
			HttpServletRequest request) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		VestapUserDetails details = (VestapUserDetails) auth.getDetails();
		
		params.put("userAuth", details.getUser_auth());
		params.put("userName", auth.getName());
		params.put("code", details.getUser_dist());
		/**
		 * 시스템 정의 지표: TP003
		 * 사용자 정의 지표: TP002
		 * 시스템 사용 지표: TP001
		 */
		params.put("type_idx", "TP003");
		params.put("indi_group", "IG007");
		params.put("indi_space", "SPA02");
		
		String result = null;
		
		/**
		 * indicatorSelect
		 * sf: 직접 입력
		 * fu: 파일 업로드로 입력
		 */
		
		System.out.println("*******************************************************************************");
		System.out.println("*******************************************************************************");
		System.out.println(params);
		System.out.println("*******************************************************************************");
		System.out.println("*******************************************************************************");
		
		if(params.get("indicatorSelect").toString().equals("sf")) {
			
			try {
				
				this.SERVICE.insertIndicatorSelf(params);
				
			} catch (Exception e) {
				logger.error(this.utilComp.stackTrace(e));
				e.printStackTrace();
			}
			
		} else {
			
			try {
				
				result = this.SERVICE.insertIndicatorFile(params, details, file);
				
				
				if(result != null) {
					logger.error(result);
				}
				
			} catch (Exception e) {
				logger.error(this.utilComp.stackTrace(e));
				e.printStackTrace();
			}
		}
		
		if(result != null) {
			return new RedirectView("/admin/system/indicator/list.do?fileActive=" + result);
		} else {
			return new RedirectView("/admin/system/indicator/list.do");
		}
	}
	
	@RequestMapping(value = "/admin/system/indicator/delete.do", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView indicatorDelete(HttpServletRequest request,
			@RequestParam(value = "indi_id", required = true) String indi_id) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("indi_id", indi_id);
		params.put("userName", name);
		
		try {
			
			this.SERVICE.indicatorDelete(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return new RedirectView("/admin/system/indicator/list.do");
	}
	
	@RequestMapping(value = "/admin/system/indicator/isIndiName.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView isIndiName(HttpServletRequest request,
			@RequestParam(value = "indiName", required = true) String indiName,
			@RequestParam(value = "indi_id", required = false) String indi_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		String name = auth.getName();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("indiName", indiName);
		params.put("user", name);
		
		if(indi_id != null) {
			params.put("indi_id", indi_id);
		}
		
		int isName = 1;
		
		try {
			
			isName = this.SERVICE.isIndicatorName(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("isName", isName);
		
		return mav;
	}
	
	
}
