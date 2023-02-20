package vestap.sys.dbinfo.item;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface ItemDBinfoService {
	
	List<Map<String, String>> getItemList(Map<String, Object> params) throws Exception;
	
	int getItemListTotCnt(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getIndicatorInfo(Map<String, Object> params) throws Exception;
	
	void downloadIndicatorInfo(Map<String, Object> params, HttpServletResponse response) throws Exception;
	
	Map<String, String> getItemInfo(Map<String, Object> params) throws Exception;
}
