package vestap.adm.sys.indicator;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;
import vestap.sys.sec.user.UserAccessVO;

@Repository("sysIndicatorDAO")
public class SysIndicatorDAO extends EgovComAbstractDAO{

	public UserAccessVO getUserInfo(String param) throws Exception {
		return selectOne("indicator.getUserInfo", param);
	}
	
	public List<Map<String, String>> getDistrictInfo(Map<String, Object> params) throws Exception {
		return selectList("sysIndicator.getDistrictInfo", params);
	}
	
	public List<Map<String, String>> getDistrictSdInfo(Map<String, Object> params) throws Exception {
		return selectList("sysIndicator.getDistrictSdInfo", params);
	}
	
	public List<Map<String, String>> getFieldInfo() throws Exception {
		return selectList("indicator.getFieldInfo");
	}
	
	public List<Map<String, String>> getIndicatorListView(Map<String, Object> params) throws Exception {
		return selectList("sysIndicator.getIndicatorListView", params);
	}
	
	public int getIndicatorListViewTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("sysIndicator.getIndicatorListViewTotCnt", params);
	}
	
	public List<Map<String, String>> getIpccList(Map<String, Object> params) throws Exception {
		return selectList("indicator.getIpccList", params);
	}
	
	public List<Map<String, String>> getIndicatorDataInfo(Map<String, Object> params) throws Exception {
		return selectList("sysIndicator.getIndicatorDataInfo", params);
	}
	
	public Map<String, String> getSysIndicatorList(Map<String, Object> params) throws Exception {
		return selectOne("indicator.getSysIndicatorList", params);
	}
	
	public Map<String, String> getSytmIndicatorList(Map<String, Object> params) throws Exception {
		return selectOne("sysIndicator.getSytmIndicatorList", params);
	}
	
	public Map<String, String> getScenIndicatorList(Map<String, Object> params) throws Exception {
		return selectOne("sysIndicator.getScenIndicatorList", params);
	}
	
	public int getIndicatorDataInfoTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("sysIndicator.getIndicatorDataInfoTotCnt", params);
	}
	
	public void updateIndicatorList(Map<String, Object> params) throws Exception {
		update("sysIndicator.updateIndicatorList", params);
	}
	
	public void updateIndicatorData(Map<String, Object> params) throws Exception {
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("valueList");
		
		for(int i = 0; i < list.size(); i++) {
			
			Map<String, Object> map = list.get(i);
			map.put("indi_id", params.get("indi_id"));
			
			update("sysIndicator.updateIndicatorData", map);
		}
		
	}
	
	public int getIndicatorSequence() throws Exception {
		return selectOne("sysIndicator.getIndicatorSequence");
	}
	
	public void insertIndicatorList(Map<String, Object> params) throws Exception {
		insert("sysIndicator.insertIndicatorList", params);
	}
	
	public void insertIndicatorData(Map<String, Object> params) throws Exception {
		insert("sysIndicator.insertIndicatorData", params);
	}
	
	public void deleteIndicatorData(Map<String, Object> params) throws Exception {
		delete("sysIndicator.deleteIndicatorData", params);
	}
	
	public void deleteIndicatorList(Map<String, Object> params) throws Exception {
		delete("sysIndicator.deleteIndicatorList", params);
	}
	
	public int isIndicatorName(Map<String, Object> params) throws Exception {
		return selectOne("sysIndicator.isIndicatorName", params);
	}
	

}
