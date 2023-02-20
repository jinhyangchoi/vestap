package vestap.sys.custom.indicator;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;
import vestap.sys.sec.user.UserAccessVO;

@Repository("customIndicatorDAO")
public class CustomIndicatorDAO extends EgovComAbstractDAO {
	
	public UserAccessVO getUserInfo(String param) throws Exception {
		return selectOne("indicator.getUserInfo", param);
	}
	
	public List<Map<String, String>> getDistrictInfo(Map<String, Object> params) throws Exception {
		return selectList("indicator.getDistrictInfo", params);
	}
	
	public List<Map<String, String>> getFieldInfo() throws Exception {
		return selectList("indicator.getFieldInfo");
	}
	
	public int isIndicatorName(Map<String, Object> params) throws Exception {
		return selectOne("indicator.isIndicatorName", params);
	}
	
	public List<Map<String, String>> getIpccList(Map<String, Object> params) throws Exception {
		return selectList("indicator.getIpccList", params);
	}
	
	public int getIndicatorSequence() throws Exception {
		return selectOne("indicator.getIndicatorSequence");
	}
	
	public void insertIndicatorList(Map<String, Object> params) throws Exception {
		insert("indicator.insertIndicatorList", params);
	}
	
	public void insertIndicatorData(Map<String, Object> params) throws Exception {
		insert("indicator.insertIndicatorData", params);
	}
	
	public List<Map<String, String>> getIndicatorListView(Map<String, Object> params) throws Exception {
		return selectList("indicator.getIndicatorListView", params);
	}
	
	public int getIndicatorListViewTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("indicator.getIndicatorListViewTotCnt", params);
	}
	
	public String getReferenceName(String param) throws Exception {
		return selectOne("indicator.getReferenceName", param);
	}
	
	public Map<String, String> getUserIndicatorList(Map<String, Object> params) throws Exception {
		return selectOne("indicator.getUserIndicatorList", params);
	}
	
	public Map<String, String> getSytmIndicatorList(Map<String, Object> params) throws Exception {
		return selectOne("indicator.getSytmIndicatorList", params);
	}
	
	public Map<String, String> getScenIndicatorList(Map<String, Object> params) throws Exception {
		return selectOne("indicator.getScenIndicatorList", params);
	}
	
	public List<Map<String, String>> getIndicatorDataInfo(Map<String, Object> params) throws Exception {
		return selectList("indicator.getIndicatorDataInfo", params);
	}
	
	public int getIndicatorDataInfoTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("indicator.getIndicatorDataInfoTotCnt", params);
	}
	
	public void updateIndicatorList(Map<String, Object> params) throws Exception {
		update("indicator.updateIndicatorList", params);
	}
	
	public void updateIndicatorData(Map<String, Object> params) throws Exception {
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("valueList");
		
		for(int i = 0; i < list.size(); i++) {
			
			Map<String, Object> map = list.get(i);
			map.put("indi_id", params.get("indi_id"));
			
			update("indicator.updateIndicatorData", map);
		}
		
	}
	
	public void deleteIndicatorList(Map<String, Object> params) throws Exception {
		delete("indicator.deleteIndicatorList", params);
	}
	
	public void deleteIndicatorData(Map<String, Object> params) throws Exception {
		delete("indicator.deleteIndicatorData", params);
	}
	
	
}
