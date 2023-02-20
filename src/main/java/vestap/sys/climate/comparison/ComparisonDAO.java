package vestap.sys.climate.comparison;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("comparisonDAO")
public class ComparisonDAO extends EgovComAbstractDAO{

	public List<EgovMap> selectComparisonSgg(EgovMap map) {
		return selectList("comparison.selectComparisonSgg", map);
		
	}

	public List<EgovMap> selectComparisonSd(EgovMap map) {
		return selectList("comparison.selectComparisonSd", map);
	}
	
	

}
