package vestap.adm.sys.estimation;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("sysEstimationDAO")
public class SysEstimationDAO extends EgovComAbstractDAO{

	public List<Map<String, String>> getReferenceInfo(Map<String, Object> params) throws Exception {
		return selectList("sysEstimation.getReferenceInfo", params);
	}
	
	public List<EgovMap> selectEstimationResultDetail(EgovMap map) {
		return selectList("sysEstimation.selectEstimationResultDetail", map);
	}
	
	public EgovMap selectEstimationCalcFormula(EgovMap map) {
		return selectOne("sysEstimation.selectEstimationCalcFormula", map);
	}
	
	public List<EgovMap> selectItemlist(EgovMap map) {
		return selectList("sysEstimation.selectItemlist", map);
	}
	
	public List<EgovMap> selectClimateOption(EgovMap map) {
		return selectList("sysEstimation.selectClimateOption", map);
	}
	
	public List<EgovMap> selectWholeSgg(EgovMap map) {
		return selectList("sysEstimation.selectWholeSgg", map);
	}
	
	public List<EgovMap> selectEstimationResultSdData(EgovMap map) {
		return selectList("sysEstimation.selectEstimationResultSdData", map);
	}
	
	public List<EgovMap> selectEstimationResultSggData(EgovMap map) {
		return selectList("sysEstimation.selectEstimationResultSggData", map);
	}
	
	public EgovMap selectEstimationIndiInfo(EgovMap map) {
		return selectOne("sysEstimation.selectEstimationIndiInfo", map);
	}
}
