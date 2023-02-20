package vestap.adm.stat.access;

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
public class AdmAccessStatController {
	private static final Logger logger = LoggerFactory.getLogger(AdmAccessStatController.class);

	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;

	@Resource(name = "admAccessService")
	private AdmAccessService admAccessService;
	
	@RequestMapping(value = "/admin/stat/accesslog/view.do")
	public ModelAndView accessView(ModelAndView mv) throws Exception {
		String viewName = "admin/stat/accesslog/view";
		mv.setViewName(viewName);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(date);
		mv.addObject("now", now);
		mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}
	

	@RequestMapping(value = "/admin/stat/accesslog/logDateList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView logDateList(HttpServletRequest request, ModelAndView mv, @RequestParam(required = false) Map<String, String> params) throws Exception {
		mv.setViewName("jsonView");
		/* 리스트 선택 후 누적현황데이터 */

		EgovMap map = this.paramMap(request);
		List<EgovMap> logList = this.admAccessService.selectAccessLogList(map);

		mv.addObject("logList", logList);
		return mv;
	}

	/**
	*	사용자 접근 통계 - 엑셀 다운로드
	*/
	@RequestMapping(value = "/admin/stat/accesslog/downloadLogList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadLogList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();

		EgovMap map = this.paramMap(request);
		
		map.put("type", type);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		List<EgovMap> logList = null;
		
		try {
			System.out.println(map);
			logList = this.admAccessService.selectAccessLogList(map);
			
			this.admAccessService.downloadLogList(logList, map, response);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/admin/stat/accesslog/downloadMonthLog.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadMonthLog(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "year") String year,
			@RequestParam(value = "month") String month,
			@RequestParam(value = "day") String day){
		Authentication auth = (Authentication) request.getUserPrincipal();
		EgovMap map = this.paramMap(request);
		
		
		System.out.println(year+"-"+month+"-"+day);
		String start = year+"-01-01";
		String date = year+"-"+month+"-"+day;
		String lastYear = Integer.parseInt(year)-1 + "-01-01";
		
		map.put("date", date);
		map.put("start", start);
		System.out.println(map);
		List<EgovMap> logList = null;
		
		try {
			
			logList = this.admAccessService.selectMonthAccessLogList(map);
			this.admAccessService.downloadMonthLogList(logList, map, response);
			
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
