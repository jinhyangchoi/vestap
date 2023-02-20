package vestap.sys.climate.exposure;

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
 * --------------------------------------------------
 * 		수정일			수정자			수정내용
 * --------------------------------------------------
 * 	2018.09.12			vestap개발		최초 작성
 * --------------------------------------------------
 * @author vestap 개발
 * @since 2018.11.14
 *
 */

@Controller
public class ExposureController {
	private static final Logger logger = LoggerFactory.getLogger(ExposureController.class);
	private String geoServerUrl = Common.geoServerUrl;
	private String vworldAddressUrl = "http://api.vworld.kr/req/address";
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@Resource(name = "exposureService")
	private ExposureService exposureService;
	
	@Resource(name = "estimationService")
	private EstimationService estimationService;
	
	@RequestMapping(value = "/member/base/climate/exposure/view.do")
	public ModelAndView estimationView(ModelAndView mv) throws Exception {
		String viewName = "climate/exposure/view";
		mv.setViewName(viewName);
		
		EgovMap map = new EgovMap();
		EgovMap resultMap = this.exposureService.selectExposureSetting(map); 		

		mv.addAllObjects(resultMap);
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/exposure/indiList.do")
	@ResponseBody
	public ModelAndView indiList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 행정구역 시군구 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.exposureService.selectScenarioList(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/exposure/sigunguList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sigunguList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		/* 행정구역 시군구 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.estimationService.selectSigungulist(map);
		mv.addObject("list", list);
		return mv;
	}
	
	
	@RequestMapping(value = "/member/base/climate/exposure/ensembleData.do")
	@ResponseBody
	public ModelAndView ensembleData(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		
		/* 앙상블 그래프 데이터*/
		EgovMap map = this.paramMap(request);
		map.put("district", "11010");//임시 행정구역 세팅
		List<EgovMap> list = this.exposureService.selectEnsembleData(map);
		mv.addObject("list", list);
		return mv;
	}
	@RequestMapping(value = "/member/base/climate/exposure/ensembleRange.do")
	@ResponseBody
	public ModelAndView ensembleRange(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		
		/* 앙상블 범위 그래프 데이터*/
		EgovMap map = this.paramMap(request);
		map.put("district", "11010");//임시 행정구역 세팅
		List<EgovMap> list = this.exposureService.selectEnsembleRange(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/exposure/selectWfsLayer.do")
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
	
	@RequestMapping(value = "/member/base/climate/exposure/selectAnalysis.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectAnalysis(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.exposureService.selectAnalysis(map); //21~30, 21~40, 21~50 분석값
		
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/member/base/climate/exposure/selectAddressInfo.do", method = RequestMethod.GET)
	@ResponseBody
	public void selectAddressInfo(HttpServletRequest request,HttpServletResponse response) {
		
		EgovWebUtil.getProxyGet(vworldAddressUrl, request, response);
	}
}

