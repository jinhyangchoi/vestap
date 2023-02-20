package vestap.sys.climate.exposure;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("exposureDAO")
public class ExposureDAO extends EgovComAbstractDAO{

	public List<EgovMap> selectOptionlist(EgovMap map) {
		return selectList("exposure.selectOptionlist", map);
	}

	public List<EgovMap> selectItemlist(EgovMap map) {
		return selectList("exposure.selectItemlist", map);
	}

	public List<EgovMap> selectSidoList() {
		return selectList("exposure.selectSidoList");
	}

	public List<EgovMap> selectSigungulist(EgovMap map) {
		return selectList("exposure.selectSigungulist", map);
	}
	
	public List<EgovMap> selectAnalysis(EgovMap map) {
		return selectList("exposure.selectAnalysis",map);
	}

	public List<EgovMap> selectEnsembleData(EgovMap map) {
		return selectList("exposure.selectEnsembleData",map);
	}

	public List<EgovMap> selectEnsembleRange(EgovMap map) {
		return selectList("exposure.selectEnsembleRange",map);
	}

	public List<EgovMap> selectScenarioList(EgovMap map) {
		return selectList("exposure.selectScenarioList",map);
	}

	public List<EgovMap> selectExposureOption(EgovMap map) {
		return selectList("exposure.selectExposureOption",map);
	}
}
