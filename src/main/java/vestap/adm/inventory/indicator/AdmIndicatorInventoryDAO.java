package vestap.adm.inventory.indicator;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("admIndicatorInventoryDAO")
public class AdmIndicatorInventoryDAO extends EgovComAbstractDAO {
	
	public List<Map<String, String>> getIndicatorGroup() throws Exception {
		return selectList("inventoryIndicator.getIndicatorGroup");
	}
	
	public List<Map<String, String>> getIndicatorList(Map<String, Object> params) throws Exception {
		return selectList("inventoryIndicator.getIndicatorList", params);
	}
	
	public int getIndicatorListTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("inventoryIndicator.getIndicatorListTotCnt", params);
	}
	
	public Map<String, String> getIndicatorListInfo(Map<String, Object> params) throws Exception {
		return selectOne("inventoryIndicator.getIndicatorListInfo", params);
	}

	public List<Map<String, String>> getIndicatorDataInfo(Map<String, Object> params) throws Exception {
		return selectList("inventoryIndicator.getIndicatorDataInfo", params);
	}
	
	public int getIndicatorDataInfoTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("inventoryIndicator.getIndicatorDataInfoTotCnt", params);
	}
	
	public List<Map<String, String>> getDistrictSd() throws Exception {
		return selectList("inventoryIndicator.getDistrictSd");
	}
	
	public List<Map<String, String>> getDistrictSgg(String param) throws Exception {
		return selectList("inventoryIndicator.getDistrictSgg", param);
	}
	
	public List<Map<String, String>> getIndicatorItem(Map<String, Object> params) throws Exception {
		return selectList("inventoryIndicator.getIndicatorItem", params);
	}
	
	public int getIndicatorItemTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("inventoryIndicator.getIndicatorItemTotCnt", params);
	}
	
	public List<Map<String, String>> getMetaInfoList(String param) throws Exception {
		return selectList("inventoryIndicator.getMetaInfoList", param);
	}
	
	public  Map<String, String> getDistrictSdInfo(String param) throws Exception {
		return selectOne("inventoryIndicator.getDistrictSdInfo", param);
	}
	
	public  Map<String, String> getDistrictSggInfo(String param) throws Exception {
		return selectOne("inventoryIndicator.getDistrictSggInfo", param);
	}
	
	public Map<String, String> getDistrictEmdInfo(String param) throws Exception {
		return selectOne("inventoryIndicator.getDistrictEmdInfo", param);
	}
	
	public List<Map<String, String>> areaStatisticsList(Map<String, Object> parmas) throws Exception{
		return selectList("inventoryIndicator.areaStatisticsList", parmas);
	}
	public List<Map<String, String>> detailAreaStatisticsList(Map<String, Object> parmas) throws Exception{
		return selectList("inventoryIndicator.detailAreaStatisticsList", parmas);
	}
	
	public List<EgovMap> downloadInvenIndi(EgovMap map){
		return selectList("inventoryIndicator.downloadInvenIndi",map);
	}
	public List<Map<String, String>> downloadInvenIndiCnt(EgovMap map){
		return selectList("inventoryIndicator.downloadInvenIndiCnt", map);
	}
	
}
