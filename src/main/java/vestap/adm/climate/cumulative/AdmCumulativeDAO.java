package vestap.adm.climate.cumulative;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("admcumulativeDAO")
public class AdmCumulativeDAO extends EgovComAbstractDAO{
	
	public List<EgovMap> selectOptionlist(EgovMap map) {
		return selectList("admcumulative.selectOptionlist", map);
	}

	public List<EgovMap> selectItemlist(EgovMap map) {
		return selectList("admcumulative.selectItemlist", map);
	}

	public List<EgovMap> selectSidoList() {
		return selectList("admcumulative.selectSidoList");
	}

	public List<EgovMap> selectSigungulist(EgovMap map) {
		return selectList("estimation.selectSigungulist", map);
	}
	public List<EgovMap> selectCumulativeList(EgovMap map) {
		return selectList("admcumulative.selectCumulativeList", map);
	}
	public List<EgovMap> selectCumulativeTotal(EgovMap map) {
		return selectList("admcumulative.selectCumulativeTotal", map);
	}
	public List<EgovMap> selectCumulativeFindIndiNm(EgovMap map) {
		return selectList("admcumulative.selectCumulativeFindIndiNm", map);
	}
	public List<EgovMap> selectCumulativeMetaInfo(EgovMap map) {
		return selectList("admcumulative.selectCumulativeMetaInfo", map);
	}
	public List<EgovMap> selectCumulativeRelation(EgovMap map) {
		return selectList("admcumulative.selectCumulativeRelation", map);
	}
	public List<EgovMap> selectCumulativeComment(EgovMap map) {
		return selectList("admcumulative.selectCumulativeComment", map);
	}
}
