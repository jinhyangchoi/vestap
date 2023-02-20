package vestap.sys.climate.estimation;

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

@Repository("estimationDAO")
public class EstimationDAO extends EgovComAbstractDAO {

	public List<EgovMap> selectOptionlist(EgovMap map) {
		return selectList("estimation.selectOptionlist", map);
	}

	public List<EgovMap> selectItemlist(EgovMap map) {
		return selectList("estimation.selectItemlist", map);
	}

	public List<EgovMap> selectSidoList() {
		return selectList("estimation.selectSidoList");
	}

	public List<EgovMap> selectSigungulist(EgovMap map) {
		return selectList("estimation.selectSigungulist", map);
	}

	public List<EgovMap> selectEstimationResultEmdTotal(EgovMap map) {
		return selectList("estimation.selectEstimationResultEmdTotal", map);
	}

	public List<EgovMap> selectEstimationResultSggTotal(EgovMap map) {
		return selectList("estimation.selectEstimationResultSggTotal", map);
	}
	
	public List<EgovMap> selectEstimationIndiWeight(EgovMap map) {
		return selectList("estimation.selectEstimationIndiWeight", map);
	}
	
	public List<EgovMap> selectEstimationEmdIndiWeight(EgovMap map){
		return selectList("estimation.selectEstimationEmdIndiWeight", map);
	}

	public List<EgovMap> selectEstimationResultSdTotal(EgovMap map) {
		return selectList("estimation.selectEstimationResultSdTotal", map);
	}
	
	public List<EgovMap> selectEstimationResultDetail(EgovMap map) {
		return selectList("estimation.selectEstimationResultDetail", map);
	}

	public EgovMap selectEstimationCalcFormula(EgovMap map) {
		return selectOne("estimation.selectEstimationCalcFormula", map);
	}

	public List<EgovMap> selectEstimationResultEmdData(EgovMap map) {
		return selectList("estimation.selectEstimationResultEmdData", map);
	}

	public List<EgovMap> selectEstimationResultSggData(EgovMap map) {
		return selectList("estimation.selectEstimationResultSggData", map);
	}

	public List<EgovMap> selectEstimationResultSdData(EgovMap map) {
		return selectList("estimation.selectEstimationResultSdData", map);
	}

	public EgovMap selectEstimationIndiInfo(EgovMap map) {
		return selectOne("estimation.selectEstimationIndiInfo", map);
	}

	public void insertEstimationLog(EgovMap map) {
		insert("estimation.insertEstimationLog", map);
	}

	public void updateUserInfo(EgovMap map) {
		update("estimation.updateUserInfo", map);
	}
	
	public List<EgovMap> selectIndicatorlist(EgovMap map) {
		return selectList("estimation.selectIndicatorlist", map);
	}

	public List<EgovMap> selectClimateOption(EgovMap map) {
		return selectList("estimation.selectClimateOption", map);
	}

	public void subEstimation(EgovMap map) {
		update("estimation.subEstimation", map);
	}

	public EgovMap selectEstimationInfo(EgovMap map) {
		return selectOne("estimation.selectEstimationInfo", map);
	}

	public List<EgovMap> selectIndiHeader(EgovMap map) {
		return selectList("estimation.selectIndiHeader", map);
	}

	public List<EgovMap> selectEmdIndiBody(EgovMap map) {
		return selectList("estimation.selectEmdIndiBody", map);
	}

	public List<EgovMap> selectSggIndiBody(EgovMap map) {
		return selectList("estimation.selectSggIndiBody", map);
	}

	public List<EgovMap> selectWholeEstimation(EgovMap map) {
		return selectList("estimation.selectWholeEstimation", map);
	}
	
	public List<EgovMap> selectWholeIndiWeight(EgovMap map) {
		return selectList("estimation.selectWholeIndiWeight", map);
	}

	public List<EgovMap> selectEstimationResultWholeData(EgovMap map) {
		return selectList("estimation.selectEstimationResultWholeData", map);
	}

	public List<EgovMap> selectWholeIndiBody(EgovMap map) {
		return selectList("estimation.selectWholeIndiBody", map);
	}
	
	/* 추가된 부분 인천광역시 세트 */

	public List<EgovMap> selectItemlist() {
		return selectList("estimation.selectItemlistAll");
	}

	public List<EgovMap> selectCombinelist(EgovMap map) {
		return selectList("estimation.selectCombinelist", map);
	}

	public List<EgovMap> selectIndicatorId(EgovMap map) {
		return selectList("estimation.selectIndicatorId", map);
	}

}
