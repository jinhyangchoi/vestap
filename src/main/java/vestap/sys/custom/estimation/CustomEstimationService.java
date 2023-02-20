package vestap.sys.custom.estimation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Transactional
public interface CustomEstimationService {
	
	List<Map<String, String>> getReferenceInfo(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> vulAssessment(Map<String, Object> params) throws Exception;
	
	int vulAssessmentTotCnt(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getReferenceIndicator(Map<String, Object> params) throws Exception;
	
	Map<String, String> getSectorWeight(Map<String, Object> params) throws Exception;
	
	Map<String, String> getIndicatorInfo(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getReferenceIndicatorInfo(Map<String, Object> params) throws Exception;
	
	int getReferenceIndicatorInfoTotCnt(Map<String, Object> params) throws Exception;
	
	void downloadEstimation(List<Map<String, String>> list, Map<String, Object> params, HttpServletResponse response) throws Exception;
	
	void downloadIndicatorData(Map<String, Object> params, HttpServletResponse response) throws Exception;
	
	Map<String, String> getIndicatorListInfo(String param) throws Exception;
	
	int isNullData(String param) throws Exception;
	
	public List<EgovMap> selectSectionList(EgovMap map);
	
	public List<EgovMap> selectModelList(EgovMap map);
	
	public List<EgovMap> selectYearList(EgovMap map);
}
