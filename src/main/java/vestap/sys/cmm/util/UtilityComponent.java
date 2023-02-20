package vestap.sys.cmm.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import org.apache.log4j.Logger;

@Component("utilityComponent")
public class UtilityComponent {
	
	protected final static Logger utilityComponentLogger = Logger.getRootLogger();
	
	/**
	 * 페이징 처리
	 * @param pageNum
	 * @param limit
	 * @param pageSize
	 * @return
	 */
	public PaginationInfo pagination(String pageNum, int limit, int pageSize) {
		
		int page = 1;
		
		if(pageNum != null) {
			page = Integer.parseInt(pageNum);
		}
		
		PaginationInfo pageInfo = new PaginationInfo();
		
		pageInfo.setPageSize(pageSize);
		pageInfo.setCurrentPageNo(page);
		pageInfo.setRecordCountPerPage(limit);
		
		return pageInfo;
	}
	
	/**
	 * Log Message
	 * @param e
	 * @return
	 */
	public String stackTrace(Exception e) {
		
		Writer writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		
		return writer.toString();
	}
	
	/**
	 * 좌측 주 메뉴에 현재 VIEW의 위치를 알려준다.
	 * @param viewName
	 * @return
	 */
	public Map<String, String> navDepth(String viewName) {
		
		System.out.println("\n@@@@ navDepth S @@@@");
		
		String[] viewArr = viewName.split("/");
		int cnt=0;
		for(String test : viewArr){
			System.out.println(test);
			cnt++;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("depth1", viewArr[0]);
		map.put("depth2", viewArr[1]);
		map.put("depth3", viewArr[2]);
		
		utilityComponentLogger.debug("\n@@@@ navDepth E @@@@");
		
		return map;
	}

	/**
	 * Random 문자열 생성
	 * @return
	 */
	public String getRandomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	

	/**
	 * Directory 생성
	 * @param path
	 */
	public void makeDirectory(String path) {
		
		File file = new File(path);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
	}
	
	/**
	 * 검색 시 hidden 태그를 이용함에 따라
	 * SQL의 다이나믹 쿼리를 통행 null 체크를 해주는 데 이를 위해서 필요한 검사 실행
	 * @param params
	 * @param key
	 * @param value
	 */
	public String setParameter(String param) {
		
		if(param == null || param.length() < 1) {
			param = "all";
		}
		
		return param;
	}
	
	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
	}
	
	public static boolean isNullOrEmpty(String arg) {
		if ((arg == null) || (arg.equals(""))) {
			return true;
		}
		return false;
	}
}
