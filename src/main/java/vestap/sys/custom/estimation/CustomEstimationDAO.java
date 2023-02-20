package vestap.sys.custom.estimation;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("customEstimationDAO")
public class CustomEstimationDAO extends EgovComAbstractDAO {
	
	public List<Map<String, String>> getReferenceInfo(Map<String, Object> params) throws Exception {
		return selectList("customEstimation.getReferenceInfo", params);
	}
	
	public List<Map<String, String>> vulAssessment(Map<String, Object> params) throws Exception {
		
		return selectList("customEstimation.vulAssessment", params);
	}
	
	public int vulAssessmentTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("customEstimation.vulAssessmentTotCnt", params);
	}
	
	public List<Map<String, String>> getReferenceIndicator(Map<String, Object> params) throws Exception {
		return selectList("customEstimation.getReferenceIndicator", params);
	}
	
	public Map<String, String> getSectorWeight(Map<String, Object> params) throws Exception {
		return selectOne("customEstimation.getSectorWeight", params);
	}
	
	public Map<String, String> getIndicatorInfo(Map<String, Object> params) throws Exception {
		return selectOne("customEstimation.getIndicatorInfo", params);
	}
	
	public List<Map<String, String>> getReferenceIndicatorInfo(Map<String, Object> params) throws Exception {
		return selectList("customEstimation.getReferenceIndicatorInfo", params);
	}
	
	public int getReferenceIndicatorInfoTotCnt(Map<String, Object> params) throws Exception {
		return selectOne("customEstimation.getReferenceIndicatorInfoTotCnt", params);
	}
	
	public Map<String, String> getDistrictInfoForExcel(Map<String, Object> params) throws Exception {
		return selectOne("customEstimation.getDistrictInfoForExcel", params);
	}
	
	public Map<String, String> getItemInfoForExcel(Map<String, Object> params) throws Exception {
		return selectOne("customEstimation.getItemInfoForExcel", params);
	}
	
	public Map<String, String> getIndicatorListInfo(String param) throws Exception {
		return selectOne("customEstimation.getIndicatorListInfo", param);
	}
	
	public int isNullData(String param) throws Exception {
		return selectOne("customEstimation.isNullData", param);
	}
	
	public List<EgovMap> selectClimateOption(EgovMap map) {
		return selectList("customEstimation.selectClimateOption", map);
	}
}
