package vestap.sys.climate.comparison;

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
 * 기후변화 취약성 > 지역간 비교분석
 * @author vestap 개발
 * @since 2018.11.14
 *
 */

@Controller
public class ComparisonController {
	private static final Logger logger = LoggerFactory.getLogger(ComparisonController.class);
	private String geoServerUrl = Common.geoServerUrl;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@Resource(name = "comparisonService")
	private ComparisonService comparisonService;
	
	@Resource(name = "estimationService")
	private EstimationService estimationService;
	
	@RequestMapping(value = "/member/base/climate/comparison/view.do")
	public ModelAndView comparisonView(ModelAndView mv) throws Exception {
		String viewName = "climate/comparison/view";
		mv.setViewName(viewName);

		EgovMap map = new EgovMap();
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가
		EgovMap resultMap = this.comparisonService.selectComparisonSetting(map); 
		
		mv.addAllObjects(resultMap);
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/comparison/itemList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView itemList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 분야 별 항목 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectItemlist(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/comparison/sectionList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sectionList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 기후모델별 RCP 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectSectionList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/comparison/modelList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView modelList2(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 기후모델별 RCP 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectModelList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/comparison/yearList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView yearList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 취약성평가 기후모델별 RCP 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectYearList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/comparison/sidoList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sidoList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 행정구역 시도 세팅 */
		List<EgovMap> list = this.estimationService.selectSidoList();
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/comparison/sigunguList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sigunguList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 행정구역 시군구 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectSigungulist(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/comparison/resultSgg.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultEmdTotal(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);	
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가 
		List<EgovMap> list = this.comparisonService.selectComparisonSgg(map); 
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/comparison/resultSd.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView resultSggTotal(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);	
		map.put("type", "BASIC"); //BASIC : 기본 취약성평가 , CUSTOM : 사용자정의 취약성평가
		List<EgovMap> list = this.comparisonService.selectComparisonSd(map); 
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/comparison/selectWfsLayer.do")
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

