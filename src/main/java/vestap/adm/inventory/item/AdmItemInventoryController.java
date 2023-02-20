package vestap.adm.inventory.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.json.JsonObject;
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


import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import vestap.sys.climate.cumulative.CumulativeService;
import vestap.sys.climate.estimation.EstimationService;
import vestap.sys.cmm.util.UtilityComponent;
import vestap.sys.custom.indicator.CustomIndicatorServiceImpl;




@Controller
public class AdmItemInventoryController {
	private static final Logger logger = LoggerFactory.getLogger(AdmItemInventoryController.class);

	@Resource(name = "customIndicatorService")
	private CustomIndicatorServiceImpl INDICATOR;

	@Resource(name = "cumulativeService")
	private CumulativeService cumulativeService;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;

	@Resource(name = "admItemInventoryService")
	private AdmItemInventoryService SERVICE;

	@Resource(name = "estimationService")
	private EstimationService estimationService;
	
	/**
	 * 항목 목록
	 * @param model
	 * @param request
	 * @param page
	 * @param field
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value = "/admin/inventory/item/list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String itemList(ModelMap model, HttpServletRequest request, ModelAndView mv,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "field", required = false) String field,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "sigungu", required = false) String sigungu,
			@RequestParam(value = "sido", required = false) String sido,
			//검색 조건에 '시작일','종료일' 추가(2019.10.30, 최진원)
			@RequestParam(value = "startdate", required = false) String startdate,
			@RequestParam(value = "enddate", required = false) String enddate,
			@RequestParam(value = "activeOffcanvas", required = false) String activeOffcanvas) throws Exception {
		
		String viewName = "admin/inventory/item/list";
		EgovMap map = new EgovMap();
		List<EgovMap> sidoList = this.estimationService.selectSidoList();
		
		if(sido == null || sido.equals("")) {
			sido = ""; //관리자는 시도코드 고정
		}
		
		map.put("sidoCode", sido);
		List<EgovMap> sigunguList = null;
		if(!sido.equals("")) {
			sigunguList = this.SERVICE.selectSigungulist(map);
			//sigunguList = this.estimationService.selectSigungulist(map);
		}
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 20;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 10);
		
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
		
		if(field == null) {
			field = "all";
		} else {
			if(field.length() < 1) {
				field = "all";
			}
		}
		
		//시작일, 종료일 날짜 검색어 확인(2019.10.30~31, 최진원)
		if((startdate == ""  && enddate == "") || (startdate == null && enddate == null)) {
			startdate = "";
			enddate = "";
			System.out.println("::::::::::::::::::::::::::::::::: "+"날짜 둘 다 미선택");
		} else if((startdate != "" && enddate == "") || (startdate != null && enddate == null)) {
			enddate = "";
			System.out.println("::::::::::::::::::::::::::::::::: "+"날짜 시작일만 선택");
		} else if((startdate == "" && enddate != "") || (startdate == null && enddate != null)) {
			startdate = "";
			System.out.println("::::::::::::::::::::::::::::::::: "+"날짜 종료일만 선택");
		} else {
			if((startdate != "" && enddate != "") || (startdate != null && enddate != null)) {
				System.out.println("::::::::::::::::::::::::::::::::: "+"날짜 둘 다 선택");
			}
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(keywordList != null) {
			params.put("keywordList", keywordList);
		}

		model.addAttribute("setSido", sido);
		model.addAttribute("setSigungu", sigungu);
		
		if(sigungu == null || sigungu.equals("")){
			sigungu = sido;
		}

		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		params.put("field", field);
		params.put("sidoCode", sigungu);
		//시작일, 종료일 날짜 조건 추가(2019.10.30, 최진원)
		params. put("startdate", startdate);
		params. put("enddate", enddate);
		
		
		
		List<Map<String, String>> fieldList = null;
		List<Map<String, String>> itemList = null;
		
		try {
			
			fieldList = this.INDICATOR.getFieldInfo();
			
			itemList = this.SERVICE.getItemList(params);
			
			pageInfo.setTotalRecordCount(this.SERVICE.getItemListTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		model.addAttribute("viewName", this.utilComp.navDepth(viewName));
		model.addAttribute("fieldList", fieldList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("field", field);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("sigunguList", sigunguList);
		//시작일, 종료일 날짜 조건 추가(2019.10.30, 최진원)
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		
		if(keyword != null) {
			model.addAttribute("keyword", keyword);
			model.addAttribute("keywordList", keywordList);
		}
		System.out.println("****************************************************");
		System.out.println("keyword : " + keyword);
		System.out.println("field : " + field);
		System.out.println("sido : " + sido);
		System.out.println("sigungu : " + sigungu);
		System.out.println("startdate : " + startdate);
		System.out.println("enddate : " + enddate);
		System.out.println("****************************************************");
		

		if(activeOffcanvas != null) {
			model.addAttribute("activeOffcanvas", activeOffcanvas);
		}
		
		return viewName;
	}
	
	/**
	 * 항목 상세보기
	 * @param item_id
	 * @param sector_id
	 * @return
	 */
	@RequestMapping(value = "/admin/inventory/item/indicatorInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView indicatorInfo(
			@RequestParam(value = "item_id", required = true) String item_id,
			@RequestParam(value = "sector_id", required = true) String sector_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("item_id", item_id);
		params.put("sector_id", sector_id);
		
		List<Map<String, String>> indicatorInfoList = null;
		
		try {
			
			indicatorInfoList = this.SERVICE.getIndicatorInfo(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("indicatorInfoList", indicatorInfoList);
		
		return mav;
	}
	
	/**
	 * 항목 상세보기 다운로드
	 * @param response
	 * @param item_id
	 */
	@RequestMapping(value = "/admin/inventory/item/downloadIndicatorInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadIndicatorInfo(HttpServletResponse response,
			@RequestParam(value = "item_id", required = true) String item_id) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("item_id", item_id);
		params.put("sector_id", "all");
		
		try {
			
			this.SERVICE.downloadIndicatorInfo(params, response);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/admin/inventory/item/itemInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView itemInfo(@RequestParam(value = "item_id", required = true) String item_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("item_id", item_id);
		
		try {
			
			mav.addObject("itemInfo", this.SERVICE.getItemInfo(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return mav;
	}

	@RequestMapping(value = "/admin/inventory/item/sigunguList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sigunguList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 행정구역 시군구 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.SERVICE.selectSigungulist(map);
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
	
	
	/*
	 * 지역별 통계 리스트(시도별) (JSON) (2019.10.20,최진원)
	 */
	@RequestMapping(value = "/admin/inventory/item/eventAreaStatisticsList.do", method = {RequestMethod.GET})
	@ResponseBody
	public ModelAndView eventAreaStatisticsList(HttpServletRequest request,
			@RequestParam(value = "field", required = false) String field,
			@RequestParam(value = "startdate", required = false) String startdate,
			@RequestParam(value = "enddate", required = false) String enddate,
			@RequestParam(value = "keyword", required = false) String keyword) {
		
		String sido= StringUtils.replace(request.getParameter("SIDO"), "", "").toString();
		
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> params = new HashMap<String, Object>();
		System.out.println("keyword : " + keyword);
		params.put("sido", sido);
		params.put("field", field);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("keyword", keyword);
		
		System.out.println("::::::::::::::::::::::::::::::::: "+params);
		
		List<Map<String, String>> eventAreaStatisticsList_Total = null;
		
		try {
			
			eventAreaStatisticsList_Total = this.SERVICE.eventAreaStatisticsList(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("eventAreaStatisticsList_Total", eventAreaStatisticsList_Total);
		mav.setViewName("jsonView");
	
		return mav;
	}
	
	
	/*
	 * 지역별 통계 리스트(시군구별) (JSON) (2019.10.28,최진원)
	 */
	@RequestMapping(value = "/admin/inventory/item/eventDetailAreaStatisticsList.do", method = {RequestMethod.GET})
	@ResponseBody
	public ModelAndView eventDetailAreaStatisticsList(HttpServletRequest request,
			@RequestParam(value = "field", required = false) String field,
			@RequestParam(value = "startdate", required = false) String startdate,
			@RequestParam(value = "enddate", required = false) String enddate,
			@RequestParam(value = "keyword", required = false) String keyword) {
		
		String sido= StringUtils.replace(request.getParameter("sido"), "", "").toString();
		
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("sido", sido);
		params.put("field", field);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("keyword", keyword);
		System.out.println("::::::::::::::::::::::::::::::::: "+params);
		
		List<Map<String, String>> eventDetailAreaStatisticsList_Total = null;
		
		try {
			
			eventDetailAreaStatisticsList_Total = this.SERVICE.eventDetailAreaStatisticsList(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("eventDetailAreaStatisticsList_Total", eventDetailAreaStatisticsList_Total);
		mav.setViewName("jsonView");
	
		return mav;
	}
	
	@RequestMapping(value = "/admin/inventory/item/downloadInventoryItem.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadInventoryItem(HttpServletRequest request,  HttpServletResponse response,
			@RequestParam(value="keywordList", required = false) String keyword,
			@RequestParam(value="field", required = false) String field,
			@RequestParam(value="sido", required = false) String sido,
			@RequestParam(value="sigungu", required = false) String sigungu,
			@RequestParam(value="startdate", required = false) String startdate,
			@RequestParam(value="enddate", required = false) String enddate) {
		
		EgovMap map = this.paramMap(request);
		
		System.out.println("***********************************");
		System.out.println("***********************************");
		System.out.println("download Inventory Item");
		System.out.println(map);
		System.out.println("***********************************");
		System.out.println("***********************************");
		List<EgovMap> list = null;
		List<Map<String, String>> cntList = null;
		
		try{
			list = this.SERVICE.downloadInventoryItem(map);
			if(sigungu == "" || sigungu == null){
				cntList = this.SERVICE.downloadInventoryItemCnt(map);
				System.out.println(cntList);
			}else{
				//cntList = this.SERVICE.downloadInvetoryItemCntsgg(map);
			}
			
			System.out.println(list.get(1));
			this.SERVICE.downloadInventory(list, cntList, sido, sigungu, response);
			
		} catch(Exception e){
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
	}
	
}
