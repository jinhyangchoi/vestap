package vestap.adm.inventory.indicator;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface AdmIndicatorInventoryService {
	
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
	
	List<Map<String, String>> areaStatisticsList(Map<String, Object> params) throws Exception;
	List<Map<String, String>> detailAreaStatisticsList(Map<String, Object> params) throws Exception;
	
	List<EgovMap> downloadInvenIndi(EgovMap map);
	List<Map<String, String>> downloadInvenIndiCnt(EgovMap map);
	
	void downloadInventory(List<EgovMap> list, List<Map<String, String>>cntList, String sido, HttpServletResponse response) throws Exception;
	
}
