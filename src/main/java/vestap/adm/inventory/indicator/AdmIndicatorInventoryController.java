package vestap.adm.inventory.indicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import vestap.sys.climate.estimation.EstimationService;
import vestap.sys.cmm.util.UtilityComponent;
import vestap.sys.custom.indicator.CustomIndicatorServiceImpl;
import vestap.sys.custom.item.CustomItemServiceImpl;

@Controller
public class AdmIndicatorInventoryController {
	private static final Logger logger = LoggerFactory.getLogger(AdmIndicatorInventoryController.class);

	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;

	@Resource(name = "customIndicatorService")
	private CustomIndicatorServiceImpl INDICATOR;
	
	@Resource(name = "customItemService")
	private CustomItemServiceImpl ITEM;
	
	@Resource(name = "admIndicatorInventoryService")
	private AdmIndicatorInventoryServiceImpl SERVICE;

	@Resource(name = "estimationService")
	private EstimationService estimationService;
	
	@RequestMapping(value = "/admin/inventory/indicator/list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String indicatorList(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "ipcc_1", required = false) String ipcc_1,
			@RequestParam(value = "ipcc_2", required = false) String ipcc_2,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "item_id", required = false) String item_id,
			@RequestParam(value = "field_id", required = false) String field_id,
			@RequestParam(value = "indi_group", required = false) String indi_group,
			@RequestParam(value = "sigungu", required = false) String sigungu,
			@RequestParam(value = "sido", required = false) String sido,
			@RequestParam(value = "startdate", required = false) String startdate,
			@RequestParam(value = "enddate", required = false) String enddate,
			@RequestParam(value = "activeOffcanvas", required = false) String activeOffcanvas) {
		
		String viewName = "admin/inventory/indicator/list";
		List<EgovMap> sigunguList = null;

		EgovMap map = new EgovMap();
		List<EgovMap> sidoList = this.estimationService.selectSidoList();

		if(sido == null || sido.equals("")) {
			sido = ""; 
		}
		map.put("sidoCode", sido);
		
		//시도가 전국일때 시군구리스트 보내주지않음
		if(!sido.equals("")) {
			sigunguList = this.estimationService.selectSigungulist(map);
		}
		//
		
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
				
				keywordList = new ArrayList<String>(Arrays.asList(keywordArr));
				
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

		model.addAttribute("setSido", sido);
		model.addAttribute("setSigungu", sigungu);
		
		List<Map<String, String>> fieldList = null;
		List<Map<String, String>> indicatorGroupList = null;
		List<Map<String, String>> ipccList = null;
		List<Map<String, String>> indicatorList = null;
		List<Map<String, String>> sdList = null;
		List<Map<String, String>> sggList = null;

		if(sigungu == null || sigungu.equals("")){
			sigungu = sido;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("ipcc", "IPCC1");
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		params.put("ipcc_1", ipcc_1);
		params.put("ipcc_2", ipcc_2);
		params.put("item_id", item_id);
		params.put("field_id", field_id);
		params.put("indi_group", indi_group);
		params.put("sidoCode", sigungu);
		
		if(startdate == ""){
			startdate = null;
		}
		
		if(enddate ==""){
			enddate = null;
		}
		
		
		params.put("startdate", startdate);
		params.put("enddate", enddate);
						
		System.out.println("*******************************");
		System.out.println(params);
		System.out.println("*******************************");
		if(keywordList != null) {
			params.put("keywordList", keywordList);
		}
		
		try {
			
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
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("sigunguList", sigunguList);
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		
		
		if(keyword != null) {
			model.addAttribute("keyword", keyword);
			model.addAttribute("keywordList", keywordList);
		}
		
		if(activeOffcanvas != null) {
			model.addAttribute("activeOffcanvas", activeOffcanvas);
		}
		
		return viewName;
	}
	
	@RequestMapping(value = "/admin/inventory/indicator/getItem.do", method = {RequestMethod.GET, RequestMethod.POST})
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
	
	@RequestMapping(value = "/admin/inventory/indicator/getIpcc.do", method = {RequestMethod.GET, RequestMethod.POST})
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
	
	@RequestMapping(value = "/admin/inventory/indicator/indicatorInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView indicatorInfo(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "indi_id", required = true) String indi_id,
			@RequestParam(value = "district_info", required = false) String district_info) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
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

		 if(district_info.length() == 2) {
				params.put("tableCategory", "sgg");
		}else {
			params.put("tableCategory", "emd");
		}

		Map<String, String> indicatorListInfo = null;
		List<Map<String, String>> indicatorDataList = null;
		List<Map<String, String>> metaList = null;
		
		try {
			
			indicatorListInfo = this.SERVICE.getIndicatorListInfo(params);
			
			indicatorDataList = this.SERVICE.getIndicatorDataInfo(params);

			//metaList = this.SERVICE.getMetaInfoList(indi_id);
			
			pageInfo.setTotalRecordCount(this.SERVICE.getIndicatorDataInfoTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("indicatorListInfo", indicatorListInfo);
		mav.addObject("indicatorDataList", indicatorDataList);
		//mav.addObject("metaList", metaList);
		mav.addObject("pageInfo", pageInfo);
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/inventory/indicator/districtSgg.do", method = {RequestMethod.GET, RequestMethod.POST})
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
	
	@RequestMapping(value = "/admin/inventory/indicator/indicatorItem.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView indicatorItem(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "indi_id", required = false) String indi_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
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
	
	@RequestMapping(value = "/admin/inventory/indicator/downloadIndicator.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadIndicator(HttpServletResponse response,
			@RequestParam(value = "indi_id", required = true) String indi_id,
			@RequestParam(value = "district_info", required = true) String district_info) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("indi_id", indi_id);
		params.put("district_info", district_info);
		params.put("header", indi_id.substring(0, 2));
		params.put("download", "Y");
		
		 if(district_info.length() == 2) {
				params.put("tableCategory", "sgg");
		}else {
			params.put("tableCategory", "emd");
		}
		
		try {
			this.SERVICE.downloadIndicator(response, params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/admin/inventory/indicator/AreaStatisticsList.do", method = {RequestMethod.GET})
	@ResponseBody
	public ModelAndView AreaStatisticsList(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		//String sido= StringUtils.replace(request.getParameter("SIDO"), "", "").toString();
		
		EgovMap map = this.paramMap(request);
		//map.put("field_id", request.getParameter("field_id"));
		System.out.println("************************************");
		System.out.println(map);
		System.out.println("************************************");
		List<Map<String, String>> list = this.SERVICE.areaStatisticsList(map);
		mav.addObject("list", list);
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/inventory/indicator/eventDetailAreaStatisticsList.do", method = {RequestMethod.GET})
	@ResponseBody
	public ModelAndView eventDetailAreaStatisticsList(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		//String sigungu= StringUtils.replace(request.getParameter("LOCAL"), "", "").toString();
		
				
		Map<String, Object> params = new HashMap<String, Object>();
		
		//params.put("LOCAL", sigungu);
		EgovMap map = this.paramMap(request);
		
		System.out.println("::::::::::::::::::::::::::::::::: "+map);
		
		List<Map<String, String>> list = null;
		
		try {
			
			//list = this.SERVICE.eventDetailAreaStatisticsList(params);
			list = this.SERVICE.detailAreaStatisticsList(map);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("list", list);
	
		return mav;
	}
	
	@RequestMapping(value = "/admin/inventory/indicator/downloadInventoryindicator.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadInventoryindicator(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="keyword", required = false) String keyword,
			@RequestParam(value="field", required = false) String field,
			@RequestParam(value="sido", required = false) String sido,
			@RequestParam(value="sigungu", required = false) String sigungu,
			@RequestParam(value="startdate", required = false) String startdate,
			@RequestParam(value="enddate", required = false) String enddate) {
	
		EgovMap map = this.paramMap(request);
		System.out.println("**********************************");
		System.out.println("indi downdload");
		System.out.println(map);
		System.out.println("**********************************");
		List<EgovMap> list = null;
		List<Map<String, String>> cntList = null;
		
		try{
			list = this.SERVICE.downloadInvenIndi(map);
			//System.out.println(list);
			cntList = this.SERVICE.downloadInvenIndiCnt(map);
			System.out.println("****************************************");
			System.out.println("******************cntList***************");
			System.out.println(cntList.get(1));
			//this.SERVICE.downloadInventory(list, cntList, sido, response);
			this.SERVICE.downloadInventory(list,cntList, sido, response);
			
		}catch(Exception e){
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
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
