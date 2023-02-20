package vestap.adm.stat.estimation;

import java.util.Date;
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

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.sys.cmm.util.UtilityComponent;

@Controller
public class AdmEstimationStatController {
	private static final Logger logger = LoggerFactory.getLogger(AdmEstimationStatController.class);

	@Resource(name = "admEstimationStatService")
	private AdmEstimationStatService admEstimationStatService;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@RequestMapping(value = "/admin/stat/estimationlog/view.do")
	public ModelAndView estimationView(ModelAndView mv) throws Exception {
		String viewName = "admin/stat/estimationlog/view";
		
		EgovMap map = new EgovMap();

		/* 분야 세팅 */
		map.put("codeGroup", "FIELD");
		List<EgovMap> fieldList = this.admEstimationStatService.selectOptionlist(map);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(date);
		
		mv.setViewName(viewName);
		mv.addObject("now", now);
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		mv.addObject("fieldList", fieldList);
		return mv;
	}

	@RequestMapping(value = "/admin/stat/estimationlog/List.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView logDateList(HttpServletRequest request, ModelAndView mv, @RequestParam(required = false) Map<String, String> params) throws Exception {
		mv.setViewName("jsonView");
		/* 리스트 선택 후 누적현황데이터 */

		EgovMap map = this.paramMap(request);

		List<EgovMap> list = this.admEstimationStatService.selectEstimationList(map);
		List<EgovMap> totalList = this.admEstimationStatService.selectEstimationTotalList(map);

		mv.addObject("list", list);
		mv.addObject("totalList", totalList);
		return mv;
	}

	/**
	*	사용자 접근 통계 - 엑셀 다운로드
	*/
	@RequestMapping(value = "/admin/stat/estimationlog/downloadLogList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadLogList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "fieldId", required = true) String fieldId,
			@RequestParam(value = "titleText", required = true) String titleText,
			@RequestParam(value = "fieldNm", required = true) String fieldNm) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();

		EgovMap map = this.paramMap(request);
		
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("fieldId", fieldId);

		List<EgovMap> list = null;
		List<EgovMap> totalList = null;
		
		try {
			
			list = this.admEstimationStatService.selectEstimationList(map);
			totalList = this.admEstimationStatService.selectEstimationTotalList(map);
			this.admEstimationStatService.downloadLogList(list, totalList, titleText, fieldNm, map, response);
			
		} catch (Exception e) {
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
