package vestap.sys.custom.item;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("customItemDAO")
public class CustomItemDAO extends EgovComAbstractDAO {
	
	public List<Map<String, String>> getItemList(Map<String, Object> params) throws Exception {
		return selectList("item.getItemList", params);
	}
	
	public List<Map<String, String>> getTemplateItemList(Map<String, Object> params) throws Exception {
		return selectList("item.getTemplateItemList", params);
	}
	
	public int getItemListTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("item.getItemListTotCnt", params);
	}
	
	public Map<String, String> getItemListInfo(Map<String, Object> params) throws Exception {
		return selectOne("item.getItemListInfo", params);
	}
	
	public List<Map<String, String>> getItemIndicator(Map<String, Object> params) throws Exception {
		return selectList("item.getItemIndicator", params);
	}
	
	public List<Map<String, String>> getIndicatorGroup() throws Exception {
		return selectList("item.getIndicatorGroup");
	}
	
	public List<Map<String, String>> getUserGroupIndicator(Map<String, Object> params) throws Exception {
		return selectList("item.getUserGroupIndicator", params);
	}
	
	public int getUserGroupIndicatorTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("item.getUserGroupIndicatorTotCnt", params);
	}
	
	public List<Map<String, String>> getSytmGroupIndicator(Map<String, Object> params) throws Exception {
		return selectList("item.getSytmGroupIndicator", params);
	}
	
	public int getSytmGroupIndicatorTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("item.getSytmGroupIndicatorTotCnt", params);
	}
	
	public List<Map<String, String>> getScenGroupIndicator(Map<String, Object> params) throws Exception {
		return selectList("item.getScenGroupIndicator", params);
	}
	
	public int getScenGroupIndicatorTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("item.getScenGroupIndicatorTotCnt", params);
	}
	
	public int isItemName(Map<String, Object> params) throws Exception {
		return selectOne("item.isItemName", params);
	}
	
	public int getItemSequence() throws Exception {
		return selectOne("item.getItemSequence");
	}
	
	public void insertItemList(Map<String, Object> params) throws Exception {
		insert("item.insertItemList", params);
	}
	
	public void insertItemIndicator(Map<String, Object> params) throws Exception {
		insert("item.insertItemIndicator", params);
	}
	
	public void insertClimateOption(Map<String, Object> params) throws Exception {
		insert("item.insertClimateOption", params);
	}
	
	public void deleteClimateOption(Map<String, Object> params) throws Exception {
		delete("item.deleteClimateOption", params);
	}
	
	public void updateItemList(Map<String, Object> params) throws Exception {
		update("item.updateItemList", params);
	}
	
	public void deleteItemList(Map<String, Object> params) throws Exception {
		delete("item.deleteItemList", params);
	}
	
	public void deleteItemIndicator(Map<String, Object> params) throws Exception {
		delete("item.deleteItemIndicator", params);
	}
}
