package vestap.adm.climate.estimation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import vestap.egov.cmm.EgovWebUtil;
import vestap.sys.climate.estimation.EstimationService;
import vestap.sys.cmm.util.Common;
import vestap.sys.cmm.util.UtilityComponent;

/**
 * 기후변화 취약성 > 취약성평가 
 * @author vestap 개발
 * @since 2018.10.31
 *
 */

@Controller
public class AdmEstimationController {

	private static final Logger logger = LoggerFactory.getLogger(AdmEstimationController.class);
	private String geoServerUrl = Common.geoServerUrl;
	
	@Resource(name = "estimationService")
	private EstimationService estimationService;

	@Resource(name = "admEstimationService")
	private AdmEstimationService admEstimationService;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@RequestMapping(value = "/admin/climate/estimation/view.do")
	public ModelAndView estimationView(ModelAndView mv) throws Exception {
		String viewName = "admin/climate/estimation/view";
		mv.setViewName(viewName);
		EgovMap map = new EgovMap();
		map.put("type", "WHOLE"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가 , WHOLE : 전국 취약성평가
		EgovMap resultMap = this.estimationService.selectEstimationSetting(map); 
		mv.addAllObjects(resultMap);
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}

	@RequestMapping(value = "/admin/climate/estimation/itemList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView itemList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 분야 별 항목 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectItemlist(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/estimation/sectionList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sectionList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 기후모델별 RCP 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectSectionList(map);
		List<EgovMap> indiList = this.admEstimationService.selectItemIndiList(map);
		mv.addObject("list", list);
		mv.addObject("indiList", indiList);
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/estimation/modelList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView modelList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 기후모델별 RCP 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectModelList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/estimation/yearList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView yearList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 기후모델별 RCP 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectYearList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/estimation/sigunguList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sigunguList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 행정구역 시군구 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectSigungulist(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/estimation/selectWholeSgg.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectWholeSgg(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("jsonView");
		EgovMap map = this.paramMap(request);	
		
		if(request.getParameter("indiItemStr") != null){
			String indiitem[] = request.getParameter("indiItemStr").split("[|]");
			List<EgovMap> indiList = new ArrayList<EgovMap>();
			EgovMap indi = new EgovMap();
			for(int i = 0; i< indiitem.length; i++){
				indi = new EgovMap();
				String paramString[];
				paramString = indiitem[i].split(",");
				
				indi.put("indiId", paramString[0]);
				indi.put("indiYear", paramString[1]);
				
				indiList.add(indi);
			}
			map.put("indiItem", indiList);
			map.remove("indiItemStr");
		}
		List<EgovMap> list = this.admEstimationService.selectWholeSgg(map);
		List<EgovMap> radarList = this.admEstimationService.selectEstimationIndiWeight(map);
		mv.addObject("list", list);
		mv.addObject("radarList", radarList);
		return mv;
	}

	@RequestMapping(value = "/admin/climate/estimation/selectWholeEmd.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectWholeEmd(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("jsonView");
		EgovMap map = this.paramMap(request);

		if(request.getParameter("indiItemStr") != null){
			String indiitem[] = request.getParameter("indiItemStr").split("[|]");
			List<EgovMap> indiList = new ArrayList<EgovMap>();
			EgovMap indi = new EgovMap();
			for(int i = 0; i< indiitem.length; i++){
				indi = new EgovMap();
				String paramString[];
				paramString = indiitem[i].split(",");

				indi.put("indiId", paramString[0]);
				indi.put("indiYear", paramString[1]);

				indiList.add(indi);
			}
			map.put("indiItem", indiList);
			map.remove("indiItemStr");
		}
		List<EgovMap> list = this.admEstimationService.selectWholeEmd(map);
		List<EgovMap> radarList = this.admEstimationService.selectEstimationEmdIndiWeight(map);
		mv.addObject("list", list);
		mv.addObject("radarList", radarList);
		return mv;
	}

	
	@RequestMapping(value = "/admin/climate/estimation/resultWhole.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultWhole(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);	
		List<EgovMap> list = this.estimationService.selectWholeEstimation(map); 
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/estimation/resultDetail.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultDetail(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		map.put("type", "WHOLE"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가, WHOLE : 전국 취약성평가
		List<EgovMap> list = this.estimationService.selectEstimationResultDetail(map); //취약성평가에 사용된 지표 목록
		EgovMap info = this.estimationService.selectEstimationCalcFormula(map); //취약성평가 산출식
		mv.addObject("list", list);
		mv.addObject("info", info);
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/estimation/resultSggData.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultSggData(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		
		map.put("type", "WHOLE"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가
		
		if(request.getParameter("indiItemStr") != null){
			System.out.println(request.getParameter("indiItemStr"));
			String indiitem[] = request.getParameter("indiItemStr").split("[|]");
			List<EgovMap> indiList = new ArrayList<EgovMap>(); 
			for(int i = 0; i< indiitem.length; i++){
				EgovMap indi = new EgovMap();
				String paramString[] = indiitem[i].split(",");
				indi.put("indiId", paramString[0]);
				indi.put("indiYear", paramString[1]);
				
				indiList.add(indi);
			}
			map.put("indiItem", indiList);
			map.remove("indiItemStr");
		}
		List<EgovMap> list = this.estimationService.selectEstimationResultSggData(map); //기초자료 정보
		EgovMap info = this.estimationService.selectEstimationIndiInfo(map); //지표정보
		mv.addObject("list", list);
		mv.addObject("info", info);
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/estimation/resultEmdData.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultEmdData(HttpServletRequest request, ModelAndView mv){
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);

		map.put("type", "WHOLE"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가 , WHOLE : 전국 취약성평가

		if(request.getParameter("indiItemStr") != null){
			System.out.println(request.getParameter("indiItemStr"));
			String indiitem[] = request.getParameter("indiItemStr").split("[|]");
			List<EgovMap> indiList = new ArrayList<EgovMap>();
			for(int i = 0; i< indiitem.length; i++){
				EgovMap indi = new EgovMap();
				String paramString[] = indiitem[i].split(",");
				indi.put("indiId", paramString[0]);
				indi.put("indiYear", paramString[1]);

				indiList.add(indi);
			}
			map.put("indiItem", indiList);
			map.remove("indiItemStr");
		}

		List<EgovMap> list = this.admEstimationService.selectEstimationResultEmdData(map); //기초자료 정보
		EgovMap info = this.estimationService.selectEstimationIndiInfo(map); //지표정보
		mv.addObject("list", list);
		mv.addObject("info", info);

		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/estimation/resultSdData.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultSdData(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가 
		List<EgovMap> list = this.estimationService.selectEstimationResultSdData(map); //기초자료 정보
		EgovMap info = this.estimationService.selectEstimationIndiInfo(map); //지표정보
		mv.addObject("list", list);
		mv.addObject("info", info);
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
	
	@RequestMapping(value = "/admin/climate/estimation/excelDownload.do", method = RequestMethod.GET)
	public void excelDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		EgovMap map = this.paramMap(request);
		
		if(request.getParameter("indiItemStr") != null){
			String indiitem[] = request.getParameter("indiItemStr").split("[|]");
			List<EgovMap> indiList = new ArrayList<EgovMap>();
			EgovMap indi = new EgovMap();
			for(int i = 0; i< indiitem.length; i++){
				indi = new EgovMap();
				String paramString[];
				paramString = indiitem[i].split(",");
				
				indi.put("indiId", paramString[0]);
				indi.put("indiYear", paramString[1]);
				
				indiList.add(indi);
			}
			map.put("indiItem", indiList);
			map.remove("indiItemStr");
		}
		System.out.println("*********************************");
		System.out.println("**********Controller**Map********");
		System.out.println(map);
		System.out.println("*********************************");
		System.out.println("*********************************");
		this.admEstimationService.excelDownload(map, response);
	}
	
	@RequestMapping(value = "/admin/climate/estimation/itemindiyearList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView itemindiyearList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 전국취약성평가 평가항목별 사회경제지표 과거데이터연도 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.admEstimationService.selectindiitemYearList(map);
		
		if(list.size()==0){
			list = this.admEstimationService.selectindiitemSDYearList(map);
		}
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/estimation/selectWfsLayer.do")
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

}
