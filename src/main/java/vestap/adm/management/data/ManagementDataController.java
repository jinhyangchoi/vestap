package vestap.adm.management.data;

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
import vestap.adm.sys.indicator.SysIndicatorServiceImpl;
import vestap.sys.cmm.util.FileDownloadComponent;
import vestap.sys.cmm.util.UtilityComponent;
import vestap.sys.sec.handler.VestapUserDetails;
import vestap.sys.sec.user.UserAccessVO;

@Controller
public class ManagementDataController {
	
	private static final Logger logger = LoggerFactory.getLogger(ManagementDataController.class);
	
	@Resource(name = "managementDataService")
	private ManagementDataServiceImpl SERVICE;
	
	@Resource(name = "sysIndicatorService")
	private SysIndicatorServiceImpl SYSSERVICE;

	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent fileDownloadComp;
	
	
	@RequestMapping(value = "/admin/management/data/list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String dataList(ModelMap model, HttpServletRequest request) {
		
		String viewName = "admin/management/data";
		
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
			
			userVO = this.SYSSERVICE.getUserInfo(name);
			
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
			
			districtList = this.SYSSERVICE.getDistrictInfo(params);
			districtSdList = this.SYSSERVICE.getDistrictSdInfo(params);
			System.out.println(districtList);
			fieldList = this.SYSSERVICE.getFieldInfo();
			
			params.put("field", field);
			params.put("userName", name);
			params.put("startRow", pageInfo.getFirstRecordIndex());
			params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
			
			indicatorList = this.SERVICE.getDataListView(params);
			pageInfo.setTotalRecordCount(this.SERVICE.getDataListViewTotCnt(params));
			
			ipccList_1 = this.SYSSERVICE.getIpccList(params);
			
			params.put("ipcc", "IPCC2");
			params.put("parent", ipcc);
			
			ipccList_2 = this.SYSSERVICE.getIpccList(params);
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
	@RequestMapping(value = "/admin/management/data/fieldIndicator.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView dataFieldIndicator(HttpServletRequest request,
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
			
			indicatorList = this.SERVICE.getDataListView(params);
			pageInfo.setTotalRecordCount(this.SERVICE.getDataListViewTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("indicatorList", indicatorList);
		mav.addObject("indicatorPage", pageInfo);
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/management/data/indicatorUpdateData.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView DataIndicatorUpdateData(
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
		
	
			params.put("tableCategory", "adm");
		
		Map<String, String> indicatorListInfo = null;
		List<Map<String, String>> indicatorMetaList = null;

		try {
			/** 지표 데이터 */
			indicatorMetaList = this.SERVICE.getDataIndicatorDataInfo(params);
//			지표 데이터
				indicatorListInfo = this.SERVICE.getDataIndicatorList(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		mav.addObject("indicatorMetaList", indicatorMetaList);
		mav.addObject("indicatorListInfo", indicatorListInfo);
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/management/data/update.do", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView DataIndicatorUpdate(HttpServletRequest request,
			@RequestParam(value = "indicatorUploadFile", required = false) MultipartFile file,
			@RequestParam Map<String, Object> params) {
		
		String result = null;
		
		try {
			
			result=this.SERVICE.updateDataIndicatorFile(params,file);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		if(result != null){		
			return new RedirectView("/admin/management/data/list.do?fileActive="+result);
		} else{
			return new RedirectView("/admin/management/data/list.do");
		}
	}
	
	//지표 파일업로드 >> 엑셀파일 템플릿 파일 다운로드
		@RequestMapping(value = "/admin/management/data/indicatorUpload.do", method = {RequestMethod.GET, RequestMethod.POST})
		public void dataIndicatorUpload(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, Object> params) {
			
			if(params.get("indicator-space").equals("SPA02")) {
				
				params.put("tableCategory", "sgg");
				
			} else if(params.get("indicator-space").equals("SPA01")) {
				
				params.put("tableCategory", "emd");
				
			} else if(params.get("indicator-space").equals("SPA03")){
				
				params.put("tableCategory", "sd");
			}
			
			try {
				
				String fileName = this.SERVICE.dataIndicatorDownload(params);
				
				this.fileDownloadComp.indicatorInputFormDownload(response, fileName, params.get("tableCategory")+"VestapValue.xlsx");
				
			} catch (Exception e) {
				logger.error(this.utilComp.stackTrace(e));
				e.printStackTrace();
			}
		}
		//메타관리
		@RequestMapping(value = "/admin/management/meta/list.do", method = {RequestMethod.GET, RequestMethod.POST})
		public String metaList(ModelMap model, HttpServletRequest request) {
			
			String viewName = "admin/management/meta";
			
			//한번에 보여줄 게시글의 개수
			final int LIMIT = 20;
			
			//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
			PaginationInfo pageInfo = this.utilComp.pagination("1", LIMIT, 3);
			
			Authentication auth = (Authentication) request.getUserPrincipal();
			
			String name = auth.getName();
			
			
			List<Map<String, String>> metaList = null;
			
			
			
			try {
				
				
				Map<String, Object> params = new HashMap<String, Object>();
				
				params.put("startRow", pageInfo.getFirstRecordIndex());
				params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
				
				metaList = this.SERVICE.getMetaListView(params);
				pageInfo.setTotalRecordCount(this.SERVICE.getMetaListViewTotCnt(params));
				
			} catch(Exception e) {
				logger.error(this.utilComp.stackTrace(e));
				e.printStackTrace();
			}
			
			model.addAttribute("viewName", this.utilComp.navDepth(viewName));
			model.addAttribute("metaList", metaList);
			model.addAttribute("metaPage", pageInfo);
			
			return viewName;
		}
		
		@RequestMapping(value = "/admin/management/meta/metaUpdateData.do", method = {RequestMethod.GET, RequestMethod.POST})
		@ResponseBody
		public ModelAndView MetaUpdateData(
				HttpServletRequest request,
				@RequestParam(value = "meta_id", required = true) String meta_id) {
			
			ModelAndView mav = new ModelAndView("jsonView");
			
			Authentication auth = (Authentication) request.getUserPrincipal();
			
			VestapUserDetails details = (VestapUserDetails) auth.getDetails();
			
			String name = auth.getName();
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			
			params.put("meta_id", meta_id);
			params.put("userName", name);
			params.put("isPage", "N");
			
		
				params.put("tableCategory", "adm");
			
			Map<String, String> metaListInfo = null;

			try {
//				메타 데이터
				metaListInfo = this.SERVICE.getMetaIndicatorList(params);
				
			} catch (Exception e) {
				logger.error(this.utilComp.stackTrace(e));
				e.printStackTrace();
			}
			mav.addObject("metaListInfo", metaListInfo);
			
			return mav;
		}
		/** 
		 * 메타 리스트 페이징 이동시
		 **/
		@RequestMapping(value = "/admin/management/meta/fieldIndicator.do", method = {RequestMethod.GET, RequestMethod.POST})
		@ResponseBody
		public ModelAndView metaFieldIndicator(HttpServletRequest request,
				@RequestParam(value = "page", required = true) String page,
				@RequestParam(value = "keyword", required = false) String keyword) {
			
			ModelAndView mav = new ModelAndView("jsonView");
			
			//한번에 보여줄 게시글의 개수
			final int LIMIT = 20;
			
			//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
			PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 3);
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("startRow", pageInfo.getFirstRecordIndex());
			params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
			
			if(keyword != null) {
				if(keyword.replaceAll(" ", "").length() > 1) {
					params.put("keyword", keyword.trim());		//문자열의 앞,뒤 공백은 지운다.
				}
			}
			
			List<Map<String, String>> metaList = null;
			
			try {
				
				metaList = this.SERVICE.getMetaListView(params);
				pageInfo.setTotalRecordCount(this.SERVICE.getMetaListViewTotCnt(params));
				
			} catch (Exception e) {
				logger.error(this.utilComp.stackTrace(e));
				e.printStackTrace();
			}
			
			mav.addObject("metaList", metaList);
			mav.addObject("metaPage", pageInfo);
			
			return mav;
		}
		
		@RequestMapping(value = "/admin/management/meta/update.do", method = {RequestMethod.GET, RequestMethod.POST})
		public RedirectView MetaIndicatorUpdate(HttpServletRequest request,
								@RequestParam Map<String, Object> params) {
			
			try {
				
				this.SERVICE.updateMetaIndicatorList(params);
				
			} catch (Exception e) {
				logger.error(this.utilComp.stackTrace(e));
				e.printStackTrace();
			}
			
				return new RedirectView("/admin/management/meta/list.do");
		}
}
