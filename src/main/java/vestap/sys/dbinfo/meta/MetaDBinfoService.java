package vestap.sys.dbinfo.meta;

import java.util.List;
import java.util.Map;

public interface MetaDBinfoService {
	
	List<Map<String, String>> getMetaList(Map<String, Object> params) throws Exception;
	
	int getMetaListTotCnt(Map<String, Object> params) throws Exception;
	
	List<String> getOfferOrg() throws Exception;
	
	List<String> getOfferSystem(String param) throws Exception;
	
	Map<String, String> getConstructYear() throws Exception;
	
	Map<String, String> getMetaInfo(String params) throws Exception;
	
	List<Map<String, String>> getMetaIndicatorList(Map<String, Object> params) throws Exception;
	
	int getMetaIndicatorListTotCnt(Map<String, Object> params) throws Exception;
}
