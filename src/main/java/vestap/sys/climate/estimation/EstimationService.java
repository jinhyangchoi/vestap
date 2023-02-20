package vestap.sys.climate.estimation;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 기후변화 취약성 > 취약성평가
 * --------------------------------------------------
 * 		수정일			수정자			수정내용
 * --------------------------------------------------
 * 	2018.10.31			vestap개발		최초 작성
 * --------------------------------------------------
 * @author vestap 개발
 * @since 2018.10.31
 *
 */

public interface EstimationService {

	public List<EgovMap> selectOptionlist(EgovMap map);

	public List<EgovMap> selectItemlist(EgovMap map);

	public List<EgovMap> selectSidoList();

	public List<EgovMap> selectSigungulist(EgovMap map);

	public EgovMap selectEstimationSetting(EgovMap map) throws Exception;

	public List<EgovMap> selectEstimationResultEmdTotal(EgovMap map);
	
	public List<EgovMap> selectEstimationResultDetail(EgovMap map);

	public EgovMap selectEstimationCalcFormula(EgovMap map);

	public List<EgovMap> selectEstimationResultEmdData(EgovMap map);

	public EgovMap selectEstimationIndiInfo(EgovMap map);

	public List<EgovMap> selectEstimationResultSggTotal(EgovMap map);
	
	public List<EgovMap> selectEstimationIndiWeight(EgovMap map);
	
	public List<EgovMap> selectEstimationEmdIndiWeight(EgovMap map);

	public List<EgovMap> selectEstimationResultSggData(EgovMap map);
	
	public List<EgovMap> selectEstimationResultSdTotal(EgovMap map);
	
	public List<EgovMap> selectEstimationResultSdData(EgovMap map);
	
	public void insertEstimationLog(EgovMap map);

	public List<EgovMap> selectIndicatorlist(EgovMap map);

	public List<EgovMap> selectModelList(EgovMap map);

	public List<EgovMap> selectSectionList(EgovMap map);

	public List<EgovMap> selectYearList(EgovMap map);
	
	public List<EgovMap> selectClimateOption(EgovMap map);

	public void excelDownload(EgovMap map, HttpServletResponse response) throws Exception;

	public void subEstimation(EgovMap map);

	public EgovMap estimationReport(EgovMap map);

	public EgovMap selectEmdIndiData(EgovMap map);

	public EgovMap selectSggIndiData(EgovMap map);

	public List<EgovMap> selectWholeEstimation(EgovMap map);
	
	public List<EgovMap> selectWholeIndiWeight(EgovMap map);

	public List<EgovMap> selectEstimationResultWholeData(EgovMap map);

	public EgovMap selectWholeIndiData(EgovMap map);
	
	/* 추가된 부분 인천광역시 세트 */

	public List<EgovMap> selectItemlist();

	public List<EgovMap> selectCombinelist(EgovMap map);

	public void resultDownload(EgovMap map) throws Exception;

}
