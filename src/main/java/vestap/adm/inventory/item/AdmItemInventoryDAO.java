package vestap.adm.inventory.item;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("admItemInventoryDAO")
public class AdmItemInventoryDAO extends EgovComAbstractDAO {
	
	public List<Map<String, String>> getItemList(Map<String, Object> params) throws Exception {
		return selectList("inventory.getItemList", params);
	}
	
	public int getItemListTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("inventory.getItemListTotCnt", params);
	}
	
	public List<Map<String, String>> getIndicatorInfo(Map<String, Object> params) throws Exception {
		return selectList("inventory.getIndicatorInfo", params);
	}
	
	public Map<String, String> getItemInfo(Map<String, Object> params) throws Exception {
		return selectOne("inventory.getItemInfo", params);
	}

	public List<EgovMap> selectSidoList() {
		return selectList("inventory.selectSidoList");
	}
	
	public List<EgovMap> selectSigungulist(EgovMap map) {
		return selectList("inventory.selectSigungulist", map);
	}
	
	// 지역별 통계 리스트(시도별) (JSON) (2019.10.20,최진원)
	public List<Map<String, String>> eventAreaStatisticsList(Map<String, Object> params) throws Exception {
		return selectList("inventory.eventAreaStatisticsList", params);
	}

	// 지역별 통계 리스트(시군구별) (JSON) (2019.10.28,최진원)
		public List<Map<String, String>> eventDetailAreaStatisticsList(Map<String, Object> params) throws Exception {
			return selectList("inventory.eventDetailAreaStatisticsList", params);
		}
		
	public List<EgovMap> downloadInventoryItem(EgovMap map){
		return selectList("inventory.downloadInventoryItem", map);
	}
	
	public List<Map<String, String>> downloadInventoryItemCnt(EgovMap map){
		return selectList("inventory.eventAreaStatisticsList",map);
	}
	
}
