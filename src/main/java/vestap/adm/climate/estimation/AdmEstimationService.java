package vestap.adm.climate.estimation;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface AdmEstimationService {

	List<EgovMap> selectWholeSgg(EgovMap map);

	List<EgovMap> selectWholeEmd(EgovMap map);

	List<EgovMap> selectEstimationResultEmdData(EgovMap map);

	List<EgovMap> selectEstimationIndiWeight(EgovMap map);

	List<EgovMap> selectEstimationEmdIndiWeight(EgovMap map);
	
	List<EgovMap> selectWholeSggExcel(EgovMap map);
	
	public void excelDownload(EgovMap map, HttpServletResponse response) throws Exception;
	
	public List<EgovMap> selectItemIndiList(EgovMap map);
	
	public List<EgovMap> selectindiitemYearList(EgovMap map);
	
	public List<EgovMap> selectindiitemSDYearList(EgovMap map);
}
