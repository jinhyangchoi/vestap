package vestap.sys.custom.estimation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import vestap.egov.cmm.EgovWebUtil;
import vestap.sys.cmm.util.Common;
import vestap.sys.cmm.util.UtilityComponent;
import vestap.sys.custom.indicator.CustomIndicatorServiceImpl;
import vestap.sys.custom.item.CustomItemServiceImpl;
import vestap.sys.sec.handler.VestapUserDetails;

/**
 * 공지사항 게시판
 * --------------------------------------------------
 * 		수정일			수정자			수정내용
 * --------------------------------------------------
 * 	2018.11.10			vestap개발		최초 작성
 * --------------------------------------------------
 * @author vestap 개발
 * @since 2018.11.10
 *
 */

@Controller
public class CustomEstimationController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomEstimationController.class);
	private String geoServerUrl = Common.geoServerUrl;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@Resource(name = "customIndicatorService")
	private CustomIndicatorServiceImpl INDICATOR;
	
	@Resource(name = "customItemService")
	private CustomItemServiceImpl ITEM;
	
	@Resource(name = "customEstimationService")
	private CustomEstimationServiceImpl SERVICE;
	
	/**
	 * 사용자 정의 자체 취약성 평가 view
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member/base/custom/estimation/view.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String estimationView(ModelMap model, HttpServletRequest request) {
		
		String viewName = "custom/estimation/view";
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		String name = auth.getName();
		
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 10;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(null, LIMIT, 3);
		
		List<Map<String, String>> fieldList = null;
		List<Map<String, String>> itemList = null;
		List<Map<String, String>> scenList = null;
		List<Map<String, String>> yearList = null;
		List<Map<String, String>> rcpList = null;
		List<Map<String, String>> sdList = null;
		
		String sd = "11";
		String mdl = "CM003";
		String rcp = "RC001";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("userName", name);
		params.put("field_id", "FC001");
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		params.put("tableCategory", "sd");
		params.put("child_key", "MDL_ID");
		params.put("code", sd);
		
		try {
			
			/** 분야 정보 */
			fieldList = this.INDICATOR.getFieldInfo();
			
			/** 시나리오 모델 정보 */
			scenList = this.SERVICE.getReferenceInfo(params);
			
			params.put("child_key", "RCP_ID");
			params.put("depth_key_1", "MDL_ID");
			params.put("depth_val_1", mdl);
			
			/** RCP 정보 */
			rcpList = this.SERVICE.getReferenceInfo(params);
			
			params.put("child_key", "YEAR_ID");
			params.put("depth_key_2", "RCP_ID");
			params.put("depth_val_2", rcp);
			
			/** 년도 정보 */
			yearList = this.SERVICE.getReferenceInfo(params);
			
			/** 시도 코드,명칭 정보 */
			sdList = this.INDICATOR.getDistrictInfo(params);
			
			/** 분야에 따른 항목 정보 */
			itemList = this.ITEM.getItemList(params);
			
			pageInfo.setTotalRecordCount(this.ITEM.getItemListTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		model.addAttribute("viewName", this.utilComp.navDepth(viewName));
		model.addAttribute("fieldList", fieldList);
		model.addAttribute("scenList", scenList);
		model.addAttribute("yearList", yearList);
		model.addAttribute("rcpList", rcpList);
		model.addAttribute("sdList", sdList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("mdl", mdl);
		model.addAttribute("rcp", rcp);
		model.addAttribute("sd_id", sd);
		
		return viewName;
	}
	
	/**
	 * 시/도 또는 시/군/구 변경 시 하위 행정구역 select
	 * @param request
	 * @param district_cd
	 * @return
	 */
	@RequestMapping(value = "/member/base/custom/estimation/district.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView district(HttpServletRequest request,
			@RequestParam(value = "district_cd", required = true) String district_cd) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		VestapUserDetails details = (VestapUserDetails) auth.getDetails();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("code", district_cd);
		
		/**
		 * 2자리 코드 즉, 시/도 코드가 들어오면 하위인 시군구 코드를 select
		 * 5자리 코드 즉, 시/군/구 코드가 들어오면 하위인 읍면동 코드를 select
		 */
		if(district_cd.length() == 2) {
			
			params.put("tableCategory", "sgg");
			
		} else {
			params.put("tableCategory", "emd");
		}
		
		List<Map<String, String>> districtList = null;
		
		try {
			
			districtList = this.INDICATOR.getDistrictInfo(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("districtList", districtList);
		mav.addObject("userAuth", details.getUser_auth());
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/custom/estimation/vulAssessment.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView vulAssessment(HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "item_id", required = true) String item_id,
			@RequestParam(value = "mdl_id", required = true) String mdl_id,
			@RequestParam(value = "scen_year", required = true) String scen_year,
			@RequestParam(value = "scen_section", required = true) String scen_section,
			@RequestParam(value = "district_info", required = true) String district_info) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 5;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 5);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("mdl_id", mdl_id);
		params.put("sector_id", "SEC01");
		params.put("userName", name);
		params.put("district_info", district_info);
		params.put("item_id", item_id);
		params.put("scen_year", scen_year);
		params.put("scen_section", scen_section);
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		
		 if(district_info.length() == 2) {
				params.put("tableCategory", "sgg");
		}else {
			params.put("tableCategory", "emd");
		}
		
		List<Map<String, String>> vulAssessmentList = null;
		List<Map<String, String>> referenceIndiList = null;
		Map<String, String> sectorWeightList = null;
		Map<String, String> indicatorInfo = null;
		
		int totalCount = 0;
		
		try {
			
			referenceIndiList = this.SERVICE.getReferenceIndicator(params);
			
			sectorWeightList = this.SERVICE.getSectorWeight(params);
			
			vulAssessmentList = this.SERVICE.vulAssessment(params);
			
			totalCount = this.SERVICE.vulAssessmentTotCnt(params);
			
			pageInfo.setTotalRecordCount(totalCount);
			
			/**
			 * 기초자료 보기에서 초기 세팅을 해준다.
			 * 부문 별로 출력되는 지표 리스트인 referenceIndiList 의 첫번째 지표를 세팅
			 */
			if(referenceIndiList != null) {
				
				if(referenceIndiList.size() > 0) {
					
					String indi_id = referenceIndiList.get(0).get("indi_id");
					
					params.put("indi_id", indi_id);
					params.put("refCategory", indi_id.substring(0, 2));
					
					indicatorInfo = this.SERVICE.getIndicatorInfo(params);
				}
			}
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("referenceIndiList", referenceIndiList);
		mav.addObject("vulAssessmentList", vulAssessmentList);
		mav.addObject("sectorWeightList", sectorWeightList);
		mav.addObject("indicatorInfo", indicatorInfo);
		mav.addObject("totalCount", totalCount);
		mav.addObject("pageInfo", pageInfo);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/custom/estimation/refIndicator.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView refIndicator(HttpServletRequest request,
			@RequestParam(value = "item_id", required = true) String item_id,
			@RequestParam(value = "sector_id", required = true) String sector_id,
			@RequestParam(value = "scen_year", required = true) String scen_year,
			@RequestParam(value = "scen_section", required = true) String scen_section,
			@RequestParam(value = "mdl_id", required = true) String mdl_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("userName", name);
		params.put("item_id", item_id);
		params.put("sector_id", sector_id);
		params.put("scen_year", scen_year);
		params.put("scen_section", scen_section);
		params.put("mdl_id", mdl_id);
		
		List<Map<String, String>> referenceIndiList = null;
		
		try {
			
			referenceIndiList = this.SERVICE.getReferenceIndicator(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("referenceIndiList", referenceIndiList);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/custom/estimation/refIndicatorInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView refIndicatorInfo(HttpServletRequest request,
			@RequestParam(value = "page", required = true) String page,
			@RequestParam(value = "mdl_id", required = true) String mdl_id,
			@RequestParam(value = "indi_id", required = true) String indi_id,
			@RequestParam(value = "scen_year", required = true) String scen_year,
			@RequestParam(value = "scen_section", required = true) String scen_section,
			@RequestParam(value = "district_info", required = true) String district_info) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 10;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 5);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		params.put("userName", name);
		params.put("mdl_id", mdl_id);
		params.put("indi_id", indi_id);
		params.put("scen_year", scen_year);
		params.put("scen_section", scen_section);
		params.put("district_info", district_info);
		params.put("refCategory", indi_id.substring(0, 2));
		
		 if(district_info.length() == 2) {
				params.put("tableCategory", "sgg");
		}else {
			params.put("tableCategory", "emd");
		}
		
		Map<String, String> indicatorInfo = null;
		
		List<Map<String, String>> indicatorInfoList = null;
		
		String msg = "N";
		params.put("case", msg);
		
		try {
			
			if(indi_id.substring(0, 2).equals("IC")) {
				
				Map<String, String> infoMap = this.SERVICE.getIndicatorListInfo(indi_id);
				
				params.put("space_info", infoMap.get("indi_space"));
				
				if(district_info.length() == 4 && infoMap.get("indi_space").equals("SPA02")) {
					msg = "* 실제 읍면동 데이터가 없는 경우, 시군구 데이터 가공없이 그대로 사용";
					params.put("case", msg);
				} else if(district_info.length() == 4 && infoMap.get("indi_space").equals("SPA03")) {
					msg = "* 실제 읍면동 데이터가 없는 경우, 시도 데이터 가공없이 그대로 사용";
					params.put("case", msg);
				} else if(district_info.length() == 2 && infoMap.get("indi_space").equals("SPA03")) {
					msg = "* 실제 시군구 데이터가 없는 경우, 시도 데이터 가공없이 그대로 사용";
					params.put("case", msg);
				}
				
				int nullCnt = this.SERVICE.isNullData(indi_id);
				
				if(nullCnt > 0) {
					msg = "제공 되지 않는 값은 상위 행정구역의 값으로 사용";
					params.put("case", msg);
				}
			}
			
			indicatorInfo = this.SERVICE.getIndicatorInfo(params);
			
			indicatorInfoList = this.SERVICE.getReferenceIndicatorInfo(params);
			
			pageInfo.setTotalRecordCount(this.SERVICE.getReferenceIndicatorInfoTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("indicatorInfoList", indicatorInfoList);
		mav.addObject("indicatorInfo", indicatorInfo);
		mav.addObject("pageInfo", pageInfo);
		mav.addObject("msg", msg);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/custom/estimation/downloadEstimation.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadEstimation(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "rcp", required = true) String rcp,
			@RequestParam(value = "model", required = true) String model,
			@RequestParam(value = "mdl_id", required = true) String mdl_id,
			@RequestParam(value = "year", required = true) String year,
			@RequestParam(value = "field", required = true) String field,
			@RequestParam(value = "item_id", required = true) String item_id,
			@RequestParam(value = "scen_year", required = true) String scen_year,
			@RequestParam(value = "scen_section", required = true) String scen_section,
			@RequestParam(value = "district_info", required = true) String district_info) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("mdl_id", mdl_id);
		params.put("sector_id", "SEC01");
		params.put("userName", name);
		params.put("district_info", district_info);
		params.put("item_id", item_id);
		params.put("scen_year", scen_year);
		params.put("scen_section", scen_section);
		params.put("rcp", rcp);
		params.put("model", model);
		params.put("year", year);
		params.put("field", field);
		params.put("header", item_id.substring(0, 2));
		
		params.put("download", "Y");
		
		
		 if(district_info.length() == 2) {
				params.put("tableCategory", "sgg");
		}else {
			params.put("tableCategory", "emd");
		}
		
		List<Map<String, String>> vulAssessmentList = null;
		
		try {
			
			vulAssessmentList = this.SERVICE.vulAssessment(params);
			
			this.SERVICE.downloadEstimation(vulAssessmentList, params, response);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/mamber/base/custom/estimation/downloadIndicatorData.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void downloadIndicatorData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "mdl_id", required = true) String mdl_id,
			@RequestParam(value = "indi_id", required = true) String indi_id,
			@RequestParam(value = "scen_year", required = true) String scen_year,
			@RequestParam(value = "scen_section", required = true) String scen_section,
			@RequestParam(value = "district_info", required = true) String district_info) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("userName", name);
		params.put("mdl_id", mdl_id);
		params.put("indi_id", indi_id);
		params.put("scen_year", scen_year);
		params.put("scen_section", scen_section);
		params.put("district_info", district_info);
		params.put("refCategory", indi_id.substring(0, 2));
		params.put("download", "Y");
		
		 if(district_info.length() == 2) {
				params.put("tableCategory", "sgg");
		}else {
			params.put("tableCategory", "emd");
		}
		
		String msg = "N";
		params.put("case", msg);
		
		try {
			
			if(indi_id.substring(0, 2).equals("IC")) {
				
				Map<String, String> infoMap = this.SERVICE.getIndicatorListInfo(indi_id);
				
				params.put("space_info", infoMap.get("indi_space"));
				
				if(district_info.length() == 4 && infoMap.get("indi_space").equals("SPA02")) {
					msg = "원시자료: 읍면동 데이터가 없으므로 시군구 데이터를 이용합니다.";
					params.put("case", msg);
				} else if(district_info.length() == 4 && infoMap.get("indi_space").equals("SPA03")) {
					msg = "원시자료: 읍면동 데이터가 없으므로 시도 데이터를 이용합니다.";
					params.put("case", msg);
				} else if(district_info.length() == 2 && infoMap.get("indi_space").equals("SPA03")) {
					msg = "원시자료: 시군구 데이터가 없으므로 시도 데이터를 이용합니다.";
					params.put("case", msg);
				}
				
				int nullCnt = this.SERVICE.isNullData(indi_id);
				
				if(nullCnt > 0) {
					msg = "원시자료: 제공 되지 않는 값은 상위 행정구역의 값으로 대체 이용합니다.";
					params.put("case", msg);
				}
			}
			
			this.SERVICE.downloadIndicatorData(params, response);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/member/base/custom/estimation/selectWfsLayer.do")
	@ResponseBody
	public void selectWfsLayer(
		HttpServletRequest request, 
		HttpServletResponse response,
		@RequestParam(value = "GWC", defaultValue = "false") String geoWebCache,
		@RequestParam(value = "WP", defaultValue = "/vestap") String workspace  // 개발서버
		) {
		
		String sWMSURL = null;	
		
		if(geoWebCache.equalsIgnoreCase("false")) {
			sWMSURL = geoServerUrl + workspace + "/wfs";
		}
		EgovWebUtil.getProxyGet(sWMSURL, request, response);
	}
	
	@RequestMapping(value = "/member/base/custom/estimation/climateOption.do")
	@ResponseBody
	public ModelAndView climateOption(
			@RequestParam(value = "mdl_id", required = true) String mdl_id,
			@RequestParam(value = "rcp_id", required = false) String rcp_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("child_key", "RCP_ID");
		params.put("depth_key_1", "MDL_ID");
		params.put("depth_val_1", mdl_id);
		
		if(rcp_id != null) {
			
			params.put("child_key", "YEAR_ID");
			params.put("depth_key_2", "RCP_ID");
			params.put("depth_val_2", rcp_id);
		}
		
		List<Map<String, String>> optionList = null;
		
		try {
			
			optionList = this.SERVICE.getReferenceInfo(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("optionList", optionList);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/custom/estimation/page.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView page(
			@RequestParam(value = "page", required = true) String page,
			@RequestParam(value = "totalCount", required = true) String totalCount) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 5;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 5);
		
		pageInfo.setTotalRecordCount(Integer.parseInt(totalCount));
		
		mav.addObject("pageInfo", pageInfo);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/custom/estimation/sectionList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sectionList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 항목별 RCP 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.SERVICE.selectSectionList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/custom/estimation/modelList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView modelList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 RCP별 기후모델 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.SERVICE.selectModelList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/custom/estimation/yearList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView yearList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 기후모델별 연대 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.SERVICE.selectYearList(map);
		mv.addObject("list", list);
		return mv;
	}
	
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
	
}
