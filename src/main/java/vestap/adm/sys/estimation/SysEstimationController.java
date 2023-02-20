package vestap.adm.sys.estimation;

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
import vestap.adm.sys.indicator.SysIndicatorServiceImpl;
import vestap.adm.sys.item.SysItemServiceImpl;
import vestap.egov.cmm.EgovWebUtil;
import vestap.sys.cmm.util.Common;
import vestap.sys.cmm.util.UtilityComponent;
import vestap.sys.sec.handler.VestapUserDetails;

@Controller
public class SysEstimationController {
	
	private static final Logger logger = LoggerFactory.getLogger(SysEstimationController.class);
	private String geoServerUrl = Common.geoServerUrl;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@Resource(name = "sysIndicatorService")
	private SysIndicatorServiceImpl INDICATOR;

	@Resource(name = "sysItemService")
	private SysItemServiceImpl ITEM;
	
	@Resource(name = "sysEstimationService")
	private SysEstimationServiceImpl SERVICE;
	
	/**
	 * 사용자 정의 자체 취약성 평가 view
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/system/estimation/view.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String estimationView(ModelMap model, HttpServletRequest request) {
		
		String viewName = "admin/system/estimation";
		
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
			System.out.println(sdList);
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
	
	@RequestMapping(value = "/admin/system/estimation/resultDetail.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultDetail(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		map.put("type", "WHOLE"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가, WHOLE : 전국 취약성평가
		List<EgovMap> list = this.SERVICE.selectEstimationResultDetail(map); //취약성평가에 사용된 지표 목록
		EgovMap info = this.SERVICE.selectEstimationCalcFormula(map); //취약성평가 산출식
		mv.addObject("list", list);
		mv.addObject("info", info);
		return mv;
	}
	
	@RequestMapping(value = "/admin/system/estimation/selectWholeSgg.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectWholeSgg(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);	
		List<EgovMap> list = this.SERVICE.selectWholeSgg(map); 
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/admin/system/estimation/itemList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView itemList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 분야 별 항목 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.SERVICE.selectItemlist(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/admin/system/estimation/sectionList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sectionList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 항목별 RCP 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.SERVICE.selectSectionList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/admin/system/estimation/modelList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView modelList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 RCP별 기후모델 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.SERVICE.selectModelList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/admin/system/estimation/yearList.do", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/admin/system/estimation/selectWfsLayer.do")
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
	
	/** 취약성평가 - 기초자료 정보 시군구 */
	@RequestMapping(value = "/admin/system/estimation/resultSggData.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultSggData(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가 
		List<EgovMap> list = this.SERVICE.selectEstimationResultSggData(map); //기초자료 정보
		EgovMap info = this.SERVICE.selectEstimationIndiInfo(map); //지표정보
		mv.addObject("list", list);
		mv.addObject("info", info);
		return mv;
	}
	
	/** 취약성평가 - 기초자료 정보 시도 */
	@RequestMapping(value = "/admin/system/estimation/resultSdData.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultSdData(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가 
		List<EgovMap> list = this.SERVICE.selectEstimationResultSdData(map); //기초자료 정보
		EgovMap info = this.SERVICE.selectEstimationIndiInfo(map); //지표정보
		mv.addObject("list", list);
		mv.addObject("info", info);
		return mv;
	}
}
