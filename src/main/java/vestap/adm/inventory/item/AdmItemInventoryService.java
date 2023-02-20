package vestap.adm.inventory.item;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface AdmItemInventoryService {
	
	List<Map<String, String>> getItemList(Map<String, Object> params) throws Exception;
	
	int getItemListTotCnt(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getIndicatorInfo(Map<String, Object> params) throws Exception;
	
	void downloadIndicatorInfo(Map<String, Object> params, HttpServletResponse response) throws Exception;
	
	Map<String, String> getItemInfo(Map<String, Object> params) throws Exception;

	public List<EgovMap> selectSidoList();

	public List<EgovMap> selectSigungulist(EgovMap map);
	
	// 지역별 통계 리스트(시도별) (JSON) (2019.10.20,최진원)
	List<Map<String, String>> eventAreaStatisticsList(Map<String, Object> params) throws Exception;
	
	// 지역별 통계 리스트(시군구별) (JSON) (2019.10.28,최진원)
	List<Map<String, String>> eventDetailAreaStatisticsList(Map<String, Object> params) throws Exception;
	
	List<EgovMap> downloadInventoryItem(EgovMap map);
	
	List<Map<String, String>> downloadInventoryItemCnt(EgovMap map);
	
	void downloadInventory(List<EgovMap> list, List<Map<String, String>> cntList, String sido, String sigungu, HttpServletResponse response) throws Exception;
	
}
