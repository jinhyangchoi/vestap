package vestap.sys.climate.estimation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.EgovWebUtil;
import vestap.sys.cmm.util.Common;
import vestap.sys.cmm.util.UtilityComponent;

/**
 * 기후변화 취약성 > 취약성평가 
 * @author vestap 개발
 * @since 2018.10.31
 *
 */

@Controller
public class EstimationController {

	private static final Logger logger = LoggerFactory.getLogger(EstimationController.class);
	@Value("${Globals.nearCoastSigunguCd}")
	private String nearCoastSigunguCd;
	private String geoServerUrl = Common.geoServerUrl;
	
	
	@Resource(name = "estimationService")
	private EstimationService estimationService;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@RequestMapping(value = "/member/base/climate/estimation/view.do")
	public ModelAndView estimationView(HttpServletRequest request,ModelAndView mv) throws Exception {
		String viewName = "climate/estimation/view";
		mv.setViewName(viewName);
		EgovMap map = this.paramMap(request);
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가
		EgovMap resultMap = this.estimationService.selectEstimationSetting(map); 
		mv.addAllObjects(resultMap);
		mv.addObject("exe", map.get("exe"));
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}
		
	@RequestMapping(value = "/member/base/climate/estimation/report.do")
	public ModelAndView report(HttpServletRequest request,ModelAndView mv) throws Exception {
		String viewName = "climate/estimation/report";
		mv.setViewName(viewName);
		EgovMap map = this.paramMap(request);
		EgovMap resultMap = this.estimationService.estimationReport(map); 
		mv.addAllObjects(resultMap);
		mv.addObject("param", map);
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}

	@RequestMapping(value = "/member/base/climate/estimation/itemList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView itemList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 분야 별 항목 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectItemlist(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/estimation/sectionList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sectionList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 기후모델별 RCP 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectSectionList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/estimation/modelList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView modelList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 기후모델별 RCP 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectModelList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/estimation/yearList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView yearList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 기후모델별 RCP 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectYearList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	
	@RequestMapping(value = "/member/base/climate/estimation/sigunguList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sigunguList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 행정구역 시군구 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectSigungulist(map);
		mv.addObject("list", list);
		return mv;
	}
	

	@RequestMapping(value = "/member/base/climate/estimation/resultEmdTotal.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultEmdTotal(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);	
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가 
		System.out.println("***************************************************************");
		System.out.println("EstimationResultEmdTotal");
		System.out.println("***************************************************************");
		List<EgovMap> list = this.estimationService.selectEstimationResultEmdTotal(map); 
		List<EgovMap> radarList = this.estimationService.selectEstimationEmdIndiWeight(map); //지표별 지수 데이터(방사형그래프)
		this.estimationService.insertEstimationLog(map);//취약성평가 기록 저장
		mv.addObject("list", list);
		mv.addObject("radarList", radarList);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/estimation/resultSggTotal.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultSggTotal(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);	
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가
		map.put("nearCoastSigunguCdStr", "'"+String.join("','",nearCoastSigunguCd.split(","))+"'");
		List<EgovMap> list = this.estimationService.selectEstimationResultSggTotal(map); 
		List<EgovMap> radarList = this.estimationService.selectEstimationIndiWeight(map); //지표별 지수 데이터(방사형그래프)
		this.estimationService.insertEstimationLog(map);//취약성평가 기록 저장
		mv.addObject("list", list);
		mv.addObject("radarList", radarList);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/estimation/resultWhole.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultWhole(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);	
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가 
		List<EgovMap> list = this.estimationService.selectWholeEstimation(map); 
		List<EgovMap> radarList = this.estimationService.selectWholeIndiWeight(map);
		this.estimationService.insertEstimationLog(map);//취약성평가 기록 저장
		mv.addObject("list", list);
		mv.addObject("radarList", radarList);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/subEstimation.do")
	@ResponseBody
	public ModelAndView subEstimation(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		this.estimationService.subEstimation(map);
		
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/estimation/resultDetail.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultDetail(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가 
		List<EgovMap> list = this.estimationService.selectEstimationResultDetail(map); //취약성평가에 사용된 지표 목록
		EgovMap info = this.estimationService.selectEstimationCalcFormula(map); //취약성평가 산출식
		mv.addObject("list", list);
		mv.addObject("info", info);
		return mv;
	}

	
	@RequestMapping(value = "/member/base/climate/estimation/resultEmdData.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultEmdData(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가 
		List<EgovMap> list = this.estimationService.selectEstimationResultEmdData(map); //기초자료 정보
		EgovMap info = this.estimationService.selectEstimationIndiInfo(map); //지표정보
		mv.addObject("list", list);
		mv.addObject("info", info);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/estimation/resultSggData.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultSggData(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가 
		List<EgovMap> list = this.estimationService.selectEstimationResultSggData(map); //기초자료 정보
		EgovMap info = this.estimationService.selectEstimationIndiInfo(map); //지표정보
		mv.addObject("list", list);
		mv.addObject("info", info);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/estimation/resultWholeData.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultWholeData(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가 
		List<EgovMap> list = this.estimationService.selectEstimationResultWholeData(map); //기초자료 정보
		EgovMap info = this.estimationService.selectEstimationIndiInfo(map); //지표정보
		mv.addObject("list", list);
		mv.addObject("info", info);
		return mv;
	}
	@RequestMapping(value = "/member/base/climate/estimation/selectEmdIndiData.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectEmdIndiData(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		EgovMap resultMap = this.estimationService.selectEmdIndiData(map); 
		mv.addAllObjects(resultMap);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/estimation/selectSggIndiData.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectSggIndiData(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		EgovMap resultMap = this.estimationService.selectSggIndiData(map); 
		mv.addAllObjects(resultMap);
		return mv;
	}	
	
	@RequestMapping(value = "/member/base/climate/estimation/selectWholeIndiData.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectWholeIndiData(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		EgovMap resultMap = this.estimationService.selectWholeIndiData(map); 
		mv.addAllObjects(resultMap);
		return mv;
	}	
	
	@RequestMapping(value = "/member/base/climate/estimation/excelDownload.do", method = RequestMethod.GET)
	public void excelDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		EgovMap map = this.paramMap(request);
		this.estimationService.excelDownload(map, response);
	}
	
	@RequestMapping(value = "/member/base/climate/estimation/selectWfsLayer.do")
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

		response.setContentType("application/json;charset=utf-8");
		EgovWebUtil.getProxyGet(sWMSURL, request, response);
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
	
	
	/* 추가된 부분 인천광역시 세트 */
	
	
	@RequestMapping(value = "/member/base/climate/estimation/resultDownload.do", method = RequestMethod.GET)
	public String resultDownload(HttpServletRequest request, HttpServletResponse response) {
		
		EgovMap map = new EgovMap();
		String item = null;
		String model = request.getParameter("model");
		String section = null;
		String year = null;
		String sidoCode = request.getParameter("sido");
		String sigunguCode = null;
		
		if(sidoCode!=null) {
		
		//항목 리스트
		List<EgovMap> itemList = this.estimationService.selectItemlist();
		
		//시나리오 조합 목록
		map.put("model", model);
		List<EgovMap> combineList = this.estimationService.selectCombinelist(map);
		
		
		map.put("sidoCode", sidoCode);
		List<EgovMap> sigunguList = this.estimationService.selectSigungulist(map);

		map.put("sido", sidoCode);
		
		logger.info("------------------------------START------------------------------");
		for(int i=0;i<itemList.size();i++) {
			item = itemList.get(i).get("itemId").toString();
			map.put("item", item);

			//for(int c=0;c<combineList.size();c++) {
			for(int c=0;c<1;c++) {
						
				section = combineList.get(c).get("rcpId").toString();
				year = combineList.get(c).get("yearId").toString();
				map.put("section", section);
				map.put("year", year);

				for(int s=0;s<sigunguList.size();s++) {
					
					sigunguCode = sigunguList.get(s).get("districtCd").toString();
					map.put("sigungu", sigunguCode);
					
					logger.info((i+1)+"-"+(c+1)+"-"+(s+1)+". item = "+item +", section = "+section+", year = "+year+", sigungu = "+sigunguCode);

					try {
						this.estimationService.resultDownload(map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					map.remove("sigungu");
				}
				map.remove("section");
				map.remove("year");
			}			
			map.remove("item");
		}
		logger.info("-------------------------------END-------------------------------");
		
		}
		return "redirect:/member/base/climate/estimation/view.do";
		
	}
	
	
}
