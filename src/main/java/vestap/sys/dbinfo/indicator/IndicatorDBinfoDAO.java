package vestap.sys.dbinfo.indicator;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("indicatorDBinfoDAO")
public class IndicatorDBinfoDAO extends EgovComAbstractDAO {
	
	public List<Map<String, String>> getIndicatorGroup() throws Exception {
		return selectList("dbinfoIndicator.getIndicatorGroup");
	}
	
	public List<Map<String, String>> getIndicatorList(Map<String, Object> params) throws Exception {
		return selectList("dbinfoIndicator.getIndicatorList", params);
	}
	
	public int getIndicatorListTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("dbinfoIndicator.getIndicatorListTotCnt", params);
	}
	
	public Map<String, String> getIndicatorListInfo(Map<String, Object> params) throws Exception {
		return selectOne("dbinfoIndicator.getIndicatorListInfo", params);
	}
	
	public List<Map<String, String>> getIndicatorDataInfo(Map<String, Object> params) throws Exception {
		return selectList("dbinfoIndicator.getIndicatorDataInfo", params);
	}
	
	public int getIndicatorDataInfoTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("dbinfoIndicator.getIndicatorDataInfoTotCnt", params);
	}
	
	public List<Map<String, String>> getDistrictSd() throws Exception {
		return selectList("dbinfoIndicator.getDistrictSd");
	}
	
	public List<Map<String, String>> getDistrictSgg(String param) throws Exception {
		return selectList("dbinfoIndicator.getDistrictSgg", param);
	}
	
	public List<Map<String, String>> getIndicatorItem(Map<String, Object> params) throws Exception {
		return selectList("dbinfoIndicator.getIndicatorItem", params);
	}
	
	public int getIndicatorItemTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("dbinfoIndicator.getIndicatorItemTotCnt", params);
	}
	
	public List<Map<String, String>> getMetaInfoList(String param) throws Exception {
		return selectList("dbinfoIndicator.getMetaInfoList", param);
	}
	
	public  Map<String, String> getDistrictSdInfo(String param) throws Exception {
		return selectOne("dbinfoIndicator.getDistrictSdInfo", param);
	}
	
	public  Map<String, String> getDistrictSggInfo(String param) throws Exception {
		return selectOne("dbinfoIndicator.getDistrictSggInfo", param);
	}
	
	public Map<String, String> getDistrictEmdInfo(String param) throws Exception {
		return selectOne("dbinfoIndicator.getDistrictEmdInfo", param);
	}
	
	public String getIndicatorSki(String param) throws Exception {
		return selectOne("dbinfoIndicator.getIndicatorSki", param);
	}
	
	public String getScenarioSki(String param) throws Exception {
		return selectOne("dbinfoIndicator.getScenarioSki", param);
	}
	
	public List<Map<String, String>> getIndicatorSkiDistrictSd(String param) throws Exception {
		return selectList("dbinfoIndicator.getIndicatorSkiDistrictSd", param);
	}
	
	public List<Map<String, String>> getScenarioSkiDistrictSd(String param) throws Exception {
		return selectList("dbinfoIndicator.getScenarioSkiDistrictSd", param);
	}
	
	public List<Map<String, String>> getIndicatorSkiDistrictSgg(Map<String, Object> params) throws Exception {
		return selectList("dbinfoIndicator.getIndicatorSkiDistrictSgg", params);
	}
	
	public List<Map<String, String>> getScenarioSkiDistrictSgg(Map<String, Object> params) throws Exception {
		return selectList("dbinfoIndicator.getScenarioSkiDistrictSgg", params);
	}
}
