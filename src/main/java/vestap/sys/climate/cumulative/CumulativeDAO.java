package vestap.sys.climate.cumulative;

import java.util.List;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 기후변화 취약성 > 취약성평가 -------------------------------------------------- 수정일 수정자
 * 수정내용 -------------------------------------------------- 2018.10.31 vestap개발
 * 최초 작성 --------------------------------------------------
 * 
 * @author vestap 개발
 * @since 2018.10.31
 *
 */

@Repository("cumulativeDAO")
public class CumulativeDAO extends EgovComAbstractDAO {

	public List<EgovMap> selectOptionlist(EgovMap map) {
		return selectList("cumulative.selectOptionlist", map);
	}

	public List<EgovMap> selectItemlist(EgovMap map) {
		return selectList("cumulative.selectItemlist", map);
	}

	public List<EgovMap> selectSidoList() {
		return selectList("cumulative.selectSidoList");
	}

	public List<EgovMap> selectSigungulist(EgovMap map) {
		return selectList("estimation.selectSigungulist", map);
	}
	public List<EgovMap> selectCumulativeList(EgovMap map) {
		return selectList("cumulative.selectCumulativeList", map);
	}
	public List<EgovMap> selectCumulativeTotal(EgovMap map) {
		return selectList("cumulative.selectCumulativeTotal", map);
	}
	public List<EgovMap> selectCumulativeFindIndiNm(EgovMap map) {
		return selectList("cumulative.selectCumulativeFindIndiNm", map);
	}
	public List<EgovMap> selectCumulativeMetaInfo(EgovMap map) {
		return selectList("cumulative.selectCumulativeMetaInfo", map);
	}
	public List<EgovMap> selectCumulativeRelation(EgovMap map) {
		return selectList("cumulative.selectCumulativeRelation", map);
	}
	public List<EgovMap> selectCumulativeComment(EgovMap map) {
		return selectList("cumulative.selectCumulativeComment", map);
	}
}
