package vestap.adm.sys.estimation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Transactional
public interface SysEstimationService {
	//
	List<Map<String, String>> getReferenceInfo(Map<String, Object> params) throws Exception;
	
	public List<EgovMap> selectEstimationResultDetail(EgovMap map);
	
	public EgovMap selectEstimationCalcFormula(EgovMap map);
	
	public List<EgovMap> selectItemlist(EgovMap map);
	
	public List<EgovMap> selectModelList(EgovMap map);
	
	public List<EgovMap> selectSectionList(EgovMap map);

	public List<EgovMap> selectYearList(EgovMap map);
	
	List<EgovMap> selectWholeSgg(EgovMap map);
	
	public EgovMap selectEstimationIndiInfo(EgovMap map);
	
	public List<EgovMap> selectEstimationResultSdData(EgovMap map);
	
	public List<EgovMap> selectEstimationResultSggData(EgovMap map);
}
