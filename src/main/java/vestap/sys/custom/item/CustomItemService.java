package vestap.sys.custom.item;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomItemService {
	
	List<Map<String, String>> getItemList(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getTemplateItemList(Map<String, Object> params) throws Exception;
	
	int getItemListTotCnt(Map<String, Object> params) throws Exception;
	
	Map<String, String> getItemListInfo(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getItemIndicator(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getIndicatorGroup() throws Exception;
	
	List<Map<String, String>> getUserGroupIndicator(Map<String, Object> params) throws Exception;
	
	int getUserGroupIndicatorTotCnt(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getSytmGroupIndicator(Map<String, Object> params) throws Exception;
	
	int getSytmGroupIndicatorTotCnt(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getScenGroupIndicator(Map<String, Object> params) throws Exception;
	
	int getScenGroupIndicatorTotCnt(Map<String, Object> params) throws Exception;
	
	int isItemName(Map<String, Object> params) throws Exception;
	
	@Transactional
	void insertItem(Map<String, Object> params) throws Exception;
	
	@Transactional
	void updateItem(Map<String, Object> params) throws Exception;
	
	@Transactional
	void itemDelete(Map<String, Object> params) throws Exception;
}
