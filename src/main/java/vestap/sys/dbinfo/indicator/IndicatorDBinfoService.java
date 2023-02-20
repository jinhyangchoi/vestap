package vestap.sys.dbinfo.indicator;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface IndicatorDBinfoService {
	
	List<Map<String, String>> getIndicatorGroup() throws Exception;
	
	List<Map<String, String>> getIndicatorList(Map<String, Object> params) throws Exception;
	
	int getIndicatorListTotCnt(Map<String, Object> params) throws Exception;
	
	Map<String, String> getIndicatorListInfo(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getIndicatorDataInfo(Map<String, Object> params) throws Exception;
	
	int getIndicatorDataInfoTotCnt(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getDistrictSd() throws Exception;
	
	List<Map<String, String>> getDistrictSgg(String param) throws Exception;
	
	List<Map<String, String>> getIndicatorItem(Map<String, Object> params) throws Exception;
	
	int getIndicatorItemTotCnt(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getMetaInfoList(String param) throws Exception;
	
	void downloadIndicator(HttpServletResponse response, Map<String, Object> params) throws Exception;
	
	String getIndicatorSki(String param) throws Exception;
	
	String getScenarioSki(String param) throws Exception;
	
	List<Map<String, String>> getIndicatorSkiDistrictSd(String param) throws Exception;
	
	List<Map<String, String>> getScenarioSkiDistrictSd(String param) throws Exception;
	
List<Map<String, String>> getIndicatorSkiDistrictSgg(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getScenarioSkiDistrictSgg(Map<String, Object> params) throws Exception;
}
