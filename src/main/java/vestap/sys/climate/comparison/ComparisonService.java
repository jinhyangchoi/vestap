package vestap.sys.climate.comparison;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface ComparisonService {

	EgovMap selectComparisonSetting(EgovMap map) throws Exception;

	List<EgovMap> selectComparisonSgg(EgovMap map);
	
	List<EgovMap> selectComparisonSd(EgovMap map);

}
