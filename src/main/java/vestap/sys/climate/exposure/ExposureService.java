package vestap.sys.climate.exposure;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface ExposureService {

	EgovMap selectExposureSetting(EgovMap map) throws Exception;
	
	public List<EgovMap> selectAnalysis(EgovMap map);

	List<EgovMap> selectEnsembleData(EgovMap map);

	List<EgovMap> selectEnsembleRange(EgovMap map);

	List<EgovMap> selectScenarioList(EgovMap map);

}
