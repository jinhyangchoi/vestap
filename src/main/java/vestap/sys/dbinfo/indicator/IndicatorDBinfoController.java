package vestap.sys.dbinfo.indicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import vestap.sys.cmm.util.UtilityComponent;
import vestap.sys.custom.estimation.CustomEstimationServiceImpl;
import vestap.sys.custom.indicator.CustomIndicatorServiceImpl;
import vestap.sys.custom.item.CustomItemServiceImpl;

@Controller
public class IndicatorDBinfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndicatorDBinfoController.class);

	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@Resource(name = "customIndicatorService")
	private CustomIndicatorServiceImpl INDICATOR;
	
	@Resource(name = "customItemService")
	private CustomItemServiceImpl ITEM;
	
	@Resource(name = "customEstimationService")
	private CustomEstimationServiceImpl ESTIMATION;
	
	@Resource(name = "indicatorDBinfoService")
	private IndicatorDBinfoServiceImpl SERVICE;
	
	
	@RequestMapping(value = "/member/base/dbinfo/indicator/list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String indicatorList(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "ipcc_1", required = false) String ipcc_1,
			@RequestParam(value = "ipcc_2", required = false) String ipcc_2,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "item_id", required = false) String item_id,
			@RequestParam(value = "field_id", required = false) String field_id,
			@RequestParam(value = "indi_group", required = false) String indi_group,
			@RequestParam(value = "activeOffcanvas", required = false) String activeOffcanvas) {
		
		String viewName = "dbinfo/indicator/list";
		
		ipcc_1 = this.utilComp.setParameter(ipcc_1);
		ipcc_2 = this.utilComp.setParameter(ipcc_2);
		item_id = this.utilComp.setParameter(item_id);
		field_id = this.utilComp.setParameter(field_id);
		indi_group = this.utilComp.setParameter(indi_group);
		
		/**
		 * 검색어 확인
		 */
		String[] keywordArr = null;
		List<String> keywordList = null;
		
		if(keyword != null) {
			
			if(keyword.length() > 1) {
				
				keywordArr = keyword.split(" ");
				
				for(int i = 0; i < keywordArr.length; i++) {
					if(keywordArr[i].length() > 1) {
						
						if(keywordList == null) {
							keywordList = new ArrayList<String>();
						}
						
						keywordList.add(keywordArr[i]);
					}
				}
				
			} else {
				/**
				 * 검색어가 null 이 아니면서 길이가 0인 경우는
				 * JSP 페이지에 hidden 형태로 keyword가 있기 때문에 항상 길이가 0인 상태로 들어올 수 있다.
				 * 그때에는 null을 삽입
				 */
				keyword = null;
			}
		}
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 20;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 10);
		
		
		List<Map<String, String>> fieldList = null;
		List<Map<String, String>> indicatorGroupList = null;
		List<Map<String, String>> ipccList = null;
		List<Map<String, String>> indicatorList = null;
		List<Map<String, String>> sdList = null;
		List<Map<String, String>> sggList = null;
		List<Map<String, String>> scenList = null;
		List<Map<String, String>> rcpList = null;
		List<Map<String, String>> yearList = null;
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("ipcc", "IPCC1");
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		params.put("ipcc_1", ipcc_1);
		params.put("ipcc_2", ipcc_2);
		params.put("item_id", item_id);
		params.put("field_id", field_id);
		params.put("indi_group", indi_group);
		params.put("child_key", "MDL_ID");
		
		if(keywordList != null) {
			params.put("keywordList", keywordList);
		}
		
		try {
			
			/** 시나리오 모델 정보 */
			scenList = this.ESTIMATION.getReferenceInfo(params);
			
			params.put("child_key", "RCP_ID");
			params.put("depth_key_1", "MDL_ID");
			params.put("depth_val_1", scenList.get(0).get("option_id"));
			
			/** RCP 정보 */
			rcpList = this.ESTIMATION.getReferenceInfo(params);
			
			params.put("child_key", "YEAR_ID");
			params.put("depth_key_2", "RCP_ID");
			params.put("depth_val_2", rcpList.get(0).get("option_id"));
			
			/** 년도 정보 */
			yearList = this.ESTIMATION.getReferenceInfo(params);
			
			sdList = this.SERVICE.getDistrictSd();
			
			sggList = this.SERVICE.getDistrictSgg("11");
			
			fieldList = this.INDICATOR.getFieldInfo();
			
			indicatorGroupList = this.SERVICE.getIndicatorGroup();
			
			ipccList = this.INDICATOR.getIpccList(params);
			
			indicatorList = this.SERVICE.getIndicatorList(params);
			
			pageInfo.setTotalRecordCount(this.SERVICE.getIndicatorListTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		model.addAttribute("viewName", this.utilComp.navDepth(viewName));
		model.addAttribute("fieldList", fieldList);
		model.addAttribute("indicatorGroupList", indicatorGroupList);
		model.addAttribute("ipccList", ipccList);
		model.addAttribute("indicatorList", indicatorList);
		model.addAttribute("item_id", item_id);
		model.addAttribute("field_id", field_id);
		model.addAttribute("ipcc_1", ipcc_1);
		model.addAttribute("ipcc_2", ipcc_2);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("indi_group", indi_group);
		model.addAttribute("sdList", sdList);
		model.addAttribute("sggList", sggList);
		model.addAttribute("scenList", scenList);
		model.addAttribute("rcpList", rcpList);
		model.addAttribute("yearList", yearList);
		
		if(keyword != null) {
			model.addAttribute("keyword", keyword);
			model.addAttribute("keywordList", keywordList);
		}
		
		if(activeOffcanvas != null) {
			model.addAttribute("activeOffcanvas", activeOffcanvas);
		}
		
		return viewName;
	}
	
	@RequestMapping(value = "/member/base/dbinfo/indicator/getItem.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView getItem(@RequestParam(value = "field_id", required = true) String field_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("field_id", field_id);
		
		List<Map<String, String>> itemList = null;
		
		try {
			
			itemList = this.ITEM.getTemplateItemList(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("itemList", itemList);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/dbinfo/indicator/getIpcc.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView getIpcc(@RequestParam(value = "ipcc_id", required = true) String ipcc_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("ipcc", "IPCC2");
		params.put("parent", ipcc_id);
		
		List<Map<String, String>> ipccList = null;
		
		try {
			
			ipccList = this.INDICATOR.getIpccList(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("ipccList", ipccList);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/dbinfo/indicator/indicatorInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView indicatorInfo(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "indi_id", required = true) String indi_id,
			@RequestParam(value = "mdl_id", required = false) String mdl_id,
			@RequestParam(value = "rcp_id", required = false) String rcp_id,
			@RequestParam(value = "year_id", required = false) String year_id,
			@RequestParam(value = "district_info", required = false) String district_info) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		boolean isSki = false;
		
		List<Map<String, String>> districtList_sd = null;
		List<Map<String, String>> districtList_sgg = null;
		
		/**
		 * 스키장 관련 지표 자료는 기존의 지표 데이터 방식과 맞지 않으므로 따로 처리함
		 */
		try {
			
			System.out.println("indi_id: " + indi_id);
			System.out.println("district_info: " + district_info);
			if(indi_id.equals("IC000395") || indi_id.equals("IC000398") || indi_id.equals("IC000399") || indi_id.equals("IC000400")
					|| indi_id.equals("IC000401") || indi_id.equals("SC002048") || indi_id.equals("SC002050")) {
				
				if(indi_id.startsWith("IC")) {
					
					if(district_info == null) {
						district_info = this.SERVICE.getIndicatorSki(indi_id);
					}
					
					districtList_sd = this.SERVICE.getIndicatorSkiDistrictSd(indi_id);
					
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("indi_id", indi_id);
					params.put("district_info", district_info);
					
					districtList_sgg = this.SERVICE.getIndicatorSkiDistrictSgg(params);
					
				} else {
					
					if(district_info == null) {
						district_info = this.SERVICE.getScenarioSki(indi_id);
					}
					
					districtList_sd = this.SERVICE.getScenarioSkiDistrictSd(indi_id);
					
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("indi_id", indi_id);
					params.put("district_info", district_info);
					
					districtList_sgg = this.SERVICE.getScenarioSkiDistrictSgg(params);
				}
				
				isSki = true;
			} else {
				if(district_info == null) {
					district_info = "11";
					System.out.println("3: " + district_info);
				}
			}
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 10;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 5);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("indi_id", indi_id);
		params.put("district_info", district_info);
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		params.put("header", indi_id.substring(0, 2));
		params.put("mdl_id", mdl_id);
		params.put("rcp_id", rcp_id);
		params.put("year_id", year_id);
		
		if(district_info.length() == 2) {
			params.put("tableCategory", "sgg");
		}
		else  {
			params.put("tableCategory", "emd");
		} 
		
		Map<String, String> indicatorListInfo = null;
		List<Map<String, String>> indicatorDataList = null;
		List<Map<String, String>> metaList = null;
		
		String msg = "N";
		params.put("case", msg);
		
		try {
			
			if(indi_id.substring(0, 2).equals("IC")) {
				
				Map<String, String> infoMap = this.ESTIMATION.getIndicatorListInfo(indi_id);
				
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
				
				int nullCnt = this.ESTIMATION.isNullData(indi_id);
				
				if(nullCnt > 0) {
					msg = "제공 되지 않는 값은 상위 행정구역의 값으로 사용";
					params.put("case", msg);
				}
			}
			
			indicatorListInfo = this.SERVICE.getIndicatorListInfo(params);
			
			indicatorDataList = this.SERVICE.getIndicatorDataInfo(params);
			
			metaList = this.SERVICE.getMetaInfoList(indi_id);
			
			pageInfo.setTotalRecordCount(this.SERVICE.getIndicatorDataInfoTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("district_info", district_info);
		mav.addObject("isSki", isSki);
		mav.addObject("districtList_sd", districtList_sd);
		mav.addObject("districtList_sgg", districtList_sgg);
		mav.addObject("indicatorListInfo", indicatorListInfo);
		mav.addObject("indicatorDataList", indicatorDataList);
		mav.addObject("metaList", metaList);
		mav.addObject("pageInfo", pageInfo);
		mav.addObject("msg", msg);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/dbinfo/indicator/districtSgg.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView districtSgg(@RequestParam(value = "district_info", required = false) String district_info) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		List<Map<String, String>> sggList = null;
		
		try {
			
			sggList = this.SERVICE.getDistrictSgg(district_info);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("sggList", sggList);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/dbinfo/indicator/indicatorItem.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView indicatorItem(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "indi_id", required = false) String indi_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 10;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 5);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		params.put("indi_id", indi_id);
		
		List<Map<String, String>> itemList = null;
		
		try {
			
			itemList = this.SERVICE.getIndicatorItem(params);
			
			pageInfo.setTotalRecordCount(this.SERVICE.getIndicatorItemTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("itemList", itemList);
		mav.addObject("pageInfo", pageInfo);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/dbinfo/indicator/downloadIndicator.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadIndicator(HttpServletResponse response,
			@RequestParam(value = "mdl_id", required = true) String mdl_id,
			@RequestParam(value = "rcp_id", required = true) String rcp_id,
			@RequestParam(value = "year_id", required = true) String year_id,
			@RequestParam(value = "indi_id", required = true) String indi_id,
			@RequestParam(value = "district_info", required = true) String district_info) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("indi_id", indi_id);
		params.put("district_info", district_info);
		params.put("header", indi_id.substring(0, 2));
		params.put("download", "Y");
		params.put("mdl_id", mdl_id);
		params.put("rcp_id", rcp_id);
		params.put("year_id", year_id);
		
		if(district_info.length() == 2) {
			params.put("tableCategory", "sgg");
		}else{
			params.put("tableCategory", "emd");
		} 
		
		String msg = "N";
		params.put("case", msg);
		
		try {
			
			if(indi_id.substring(0, 2).equals("IC")) {
				
				Map<String, String> infoMap = this.ESTIMATION.getIndicatorListInfo(indi_id);
				
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
				
				int nullCnt = this.ESTIMATION.isNullData(indi_id);
				
				if(nullCnt > 0) {
					msg = "원시자료: 제공 되지 않는 값은 상위 행정구역의 값으로 대체 이용합니다.";
					params.put("case", msg);
				}
			}
			
			this.SERVICE.downloadIndicator(response, params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}
	
	
	
	
}
