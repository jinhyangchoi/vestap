package vestap.adm.climate.cumulative;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.sys.cmm.util.UtilityComponent;

@Controller
public class AdmCumulativeController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdmCumulativeController.class);
	
	@Resource(name ="admcumulativeService")
	private AdmCumulativeService admcumulativeService;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@RequestMapping(value = "/admin/climate/cumulative/view.do")
	public ModelAndView estimationView(HttpServletRequest request, @RequestParam(required = false) Map<String, String> params, ModelAndView mv) throws Exception {
		String viewName = "admin/cumulative/view";
		mv.setViewName(viewName);
		EgovMap map = new EgovMap();
		System.out.println(map);
		EgovMap resultMap = this.admcumulativeService.selectCumulativeSetting(map); 
		List<EgovMap> list = this.admcumulativeService.selectCumulativeList(map);
		
		mv.addAllObjects(resultMap);
		mv.addObject("list", list);
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/cumulative/keyword.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView itemKeyword(HttpServletRequest request, @RequestParam(required = false) Map<String, String> params, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap map = new EgovMap();

		String keyword = null;
		String[] keywordArr = null;
		
		if(params.get("keyword") != null) {
			
			keyword = params.get("keyword");
			if(keyword.length() > 0) {
				
				keywordArr = keyword.split(" ");
				
				map.put("keywordList",(new ArrayList<String>(Arrays.asList(keywordArr))));
			}
		}
		
		List<EgovMap> list = this.admcumulativeService.selectCumulativeList(map);
		
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/cumulative/cumulativeMain.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView cumulativeMain(HttpServletRequest request, ModelAndView mv, @RequestParam(required = false) Map<String, String> params) throws Exception {
		mv.setViewName("jsonView");
		/* 리스트 선택 후 누적현황데이터 */

		EgovMap map = this.paramMap(request);
		
		List<EgovMap> list = this.admcumulativeService.selectCumulativeTotal(map);

		List<EgovMap> findNm = this.admcumulativeService.selectCumulativeFindIndiNm(map);

		mv.addObject("list", list);
		mv.addObject("findNm", findNm);
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/cumulative/cumulativeDetail.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView cumulativeDetail(HttpServletRequest request, ModelAndView mv, @RequestParam(required = false) Map<String, String> params) throws Exception {
		mv.setViewName("jsonView");
		/* 누적현황정보 디테일.. */

		EgovMap map = this.paramMap(request);
		
		List<EgovMap> metaInfo = this.admcumulativeService.selectCumulativeMetaInfo(map);
		List<EgovMap> relation = this.admcumulativeService.selectCumulativeRelation(map);
		List<EgovMap> comment = this.admcumulativeService.selectCumulativeComment(map);
		
		mv.addObject("metaInfo", metaInfo);
		mv.addObject("relation", relation);
		mv.addObject("comment", comment);
		return mv;
	}

	@RequestMapping(value = "/admin/climate/cumulative/itemList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView itemList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");
		

		/* 취약성평가 분야 별 항목 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.admcumulativeService.selectItemlist(map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/admin/climate/cumulative/sigunguList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sigunguList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("jsonView");

		/* 행정구역 시군구 세팅 */
		EgovMap map = this.paramMap(request);
		List<EgovMap> list = this.admcumulativeService.selectSigungulist(map);
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
	/**
	*	누적 현황 - 엑셀 다운로드
	*/
	@RequestMapping(value = "/admin/climate/cumulative/downloadCumulative.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadEstimation(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "getIndi1", required = true) String getIndi1,
			@RequestParam(value = "getIndi2", required = true) String getIndi2,
			@RequestParam(value = "sidoCode", required = true) String sidoCode,
			@RequestParam(value = "sido", required = true) String sido,
			@RequestParam(value = "sigungu", required = true) String sigungu) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();

		EgovMap map = this.paramMap(request);
		
		map.put("getIndi1", getIndi1);
		map.put("getIndi2", getIndi2);
		map.put("sidoCode", sidoCode);

		List<EgovMap> list = null;
		List<EgovMap> findNm = null;
		
		try {
			
			list = this.admcumulativeService.selectCumulativeTotal(map);
			findNm = this.admcumulativeService.selectCumulativeFindIndiNm(map);
			
			this.admcumulativeService.downloadCumulative(list, findNm, sido, sigungu, map, response);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}

}
