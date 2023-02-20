package vestap.adm.management.data;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("managementDataDAO")
public class ManagementDataDAO extends EgovComAbstractDAO{

	
	
//	데이터 관리
	public List<Map<String, String>> getDataListView(Map<String, Object> params) throws Exception {
		return selectList("managementData.getDataListView", params);
	}
	
	public int getDataListViewTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("managementData.getDataViewTotCnt", params);
	}
	
	public Map<String, String> getDataIndicatorList(Map<String, Object> params) throws Exception {
		return selectOne("managementData.getDataIndicatorList", params);
	}
	
	public List<Map<String, String>> getDataIndicatorDataInfo(Map<String, Object> params) throws Exception {
		return selectList("managementData.getDataIndicatorDataInfo", params);
	}
	
	public List<Map<String, String>> getDataDistrictInfo(Map<String, Object> params) throws Exception {
		return selectList("managementData.getDataDistrictInfo", params);
	}
	
	public void updateDataIndicatorList(Map<String, Object> params) throws Exception {
		update("managementData.updateDataIndicatorList", params);
	}
	
	public void insertDataIndicatorData(Map<String, Object> params) throws Exception {
		insert("managementData.insertDataIndicatorData", params);
	}
	
	//메타
	public List<Map<String, String>> getMetaListView(Map<String, Object> params) throws Exception {
		return selectList("managementData.getMetaListView", params);
	}
	
	public int getMetaListViewTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("managementData.getMetaViewTotCnt", params);
	}
	
	public Map<String, String> getMetaIndicatorList(Map<String, Object> params) throws Exception {
		return selectOne("managementData.getMetaIndicatorList", params);
	}
	
	public void updateMetaIndicatorList(Map<String, Object> params) throws Exception {
		update("managementData.updateMetaIndicatorList", params);
	}
}
