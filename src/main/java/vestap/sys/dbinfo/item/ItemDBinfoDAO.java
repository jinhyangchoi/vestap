package vestap.sys.dbinfo.item;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("itemDBinfoDAO")
public class ItemDBinfoDAO extends EgovComAbstractDAO {
	
	public List<Map<String, String>> getItemList(Map<String, Object> params) throws Exception {
		return selectList("dbinfoItem.getItemList", params);
	}
	
	public int getItemListTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("dbinfoItem.getItemListTotCnt", params);
	}
	
	public List<Map<String, String>> getIndicatorInfo(Map<String, Object> params) throws Exception {
		return selectList("dbinfoItem.getIndicatorInfo", params);
	}
	
	public Map<String, String> getItemInfo(Map<String, Object> params) throws Exception {
		return selectOne("dbinfoItem.getItemInfo", params);
	}
}
