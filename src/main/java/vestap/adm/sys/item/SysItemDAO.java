package vestap.adm.sys.item;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("sysItemDAO")
public class SysItemDAO extends EgovComAbstractDAO {

	public List<Map<String, String>> getIndicatorGroup() throws Exception {
		return selectList("item.getIndicatorGroup");
	}
	
	public List<Map<String, String>> getItemList(Map<String, Object> params) throws Exception {
		return selectList("sysItem.getItemList", params);
	}
	
	public int getItemListTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("sysItem.getItemListTotCnt", params);
	}
	
	public List<Map<String, String>> getItemIndicator(Map<String, Object> params) throws Exception {
		return selectList("sysItem.getItemIndicator", params);
	}
	
	public Map<String, String> getItemListInfo(Map<String, Object> params) throws Exception {
		return selectOne("sysItem.getItemListInfo", params);
	}
	
	public int isItemName(Map<String, Object> params) throws Exception {
		return selectOne("sysItem.isItemName", params);
	}
	
	public void updateItemList(Map<String, Object> params) throws Exception {
		update("sysItem.updateItemList", params);
	}
	
	public void deleteItemIndicator(Map<String, Object> params) throws Exception {
		delete("sysItem.deleteItemIndicator", params);
	}
	
	public void insertItemList(Map<String, Object> params) throws Exception {
		insert("sysItem.insertItemList", params);
	}
	
	public void insertItemIndicator(Map<String, Object> params) throws Exception {
		insert("sysItem.insertItemIndicator", params);
	}
	
	public void insertClimateOption(Map<String, Object> params) throws Exception {
		insert ("sysItem.insertClimateOption", params);
	}
	
	public void deleteClimateOption(Map<String, Object> params) throws Exception {
		delete("sysItem.deleteClimateOption", params);
	}
	
	public int getItemSequence() throws Exception {
		return selectOne("sysItem.getItemSequence");
	}
	
	public void deleteItemList(Map<String, Object> params) throws Exception {
		delete("sysItem.deleteItemList", params);
	}
	
	public List<Map<String, String>> getUserGroupIndicator(Map<String, Object> params) throws Exception {
		return selectList("sysItem.getUserGroupIndicator", params);
	}
	
	public int getUserGroupIndicatorTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("sysItem.getUserGroupIndicatorTotCnt", params);
	}
	
	public List<Map<String, String>> getSytmGroupIndicator(Map<String, Object> params) throws Exception {
		return selectList("sysItem.getSytmGroupIndicator", params);
	}
	
	public int getSytmGroupIndicatorTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("sysItem.getSytmGroupIndicatorTotCnt", params);
	}
	
	public List<Map<String, String>> getScenGroupIndicator(Map<String, Object> params) throws Exception {
		return selectList("sysItem.getScenGroupIndicator", params);
	}
	
	public int getScenGroupIndicatorTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("sysItem.getScenGroupIndicatorTotCnt", params);
	}
	
	public List<Map<String, String>> getTemplateItemList(Map<String, Object> params) throws Exception {
		return selectList("sysItem.getTemplateItemList", params);
	}
}
