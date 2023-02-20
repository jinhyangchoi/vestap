package vestap.sys.dbinfo.meta;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("metaDBinfoDAO")
public class MetaDBinfoDAO extends EgovComAbstractDAO {
	
	public List<Map<String, String>> getMetaList(Map<String, Object> params) throws Exception {
		return selectList("dbinfoMeta.getMetaList", params);
	}
	
	public int getMetaListTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("dbinfoMeta.getMetaListTotCnt", params);
	}
	
	public List<String> getOfferOrg() throws Exception {
		return selectList("dbinfoMeta.getOfferOrg");
	}
	
	public List<String> getOfferSystem(String param) throws Exception {
		return selectList("dbinfoMeta.getOfferSystem", param);
	}
	
	public Map<String, String> getConstructYear() throws Exception {
		return selectOne("dbinfoMeta.getConstructYear");
	}
	
	public Map<String, String> getMetaInfo(String param) throws Exception {
		return selectOne("dbinfoMeta.getMetaInfo", param);
	}
	
	public List<Map<String, String>> getMetaIndicatorList(Map<String, Object> params) throws Exception {
		return selectList("dbinfoMeta.getMetaIndicatorList", params);
	}
	
	public int getMetaIndicatorListTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("dbinfoMeta.getMetaIndicatorListTotCnt", params);
	}
}
