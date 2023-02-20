package vestap.adm.sys.estimation;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.sys.cmm.util.CreateExcelStyleComponent;
import vestap.sys.cmm.util.FileDownloadComponent;

@Service("sysEstimationService")
public class SysEstimationServiceImpl extends EgovAbstractServiceImpl implements SysEstimationService{
	
	@Resource(name = "sysEstimationDAO")
	private SysEstimationDAO DAO;
	
	@Resource(name = "createExcelStyleComponent")
	private CreateExcelStyleComponent excelStyleComp;
	
	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent fileDownloadComp;
	
	@Override
	public List<Map<String, String>> getReferenceInfo(Map<String, Object> params) throws Exception {
		return this.DAO.getReferenceInfo(params);
	}
	
	@Override
	public List<EgovMap> selectEstimationResultDetail(EgovMap map) {
		return this.DAO.selectEstimationResultDetail(map);
	}
	
	@Override
	public EgovMap selectEstimationCalcFormula(EgovMap map) {
		return this.DAO.selectEstimationCalcFormula(map);
	}
	
	@Override
	public List<EgovMap> selectItemlist(EgovMap map) {
		return this.DAO.selectItemlist(map);
	}
	
	@Override
	public List<EgovMap> selectModelList(EgovMap map) {
		return this.DAO.selectClimateOption(map);
	}
	
	@Override
	public List<EgovMap> selectSectionList(EgovMap map) {
		return this.DAO.selectClimateOption(map);
	}
	
	@Override
	public List<EgovMap> selectYearList(EgovMap map) {
		return this.DAO.selectClimateOption(map);
	}
	
	@Override
	public List<EgovMap> selectWholeSgg(EgovMap map) {
		return this.DAO.selectWholeSgg(map);
	}
	
	@Override
	public List<EgovMap> selectEstimationResultSdData(EgovMap map) {
		map = addAdjModel(map);
		return this.DAO.selectEstimationResultSdData(map);
	}
	
	@Override
	public List<EgovMap> selectEstimationResultSggData(EgovMap map) {
		map = addAdjModel(map);
		return this.DAO.selectEstimationResultSggData(map);
	}
	
	@Override
	public EgovMap selectEstimationIndiInfo(EgovMap map) {
		return this.DAO.selectEstimationIndiInfo(map);
	}
	
	private EgovMap addAdjModel(EgovMap map) {
		if(map.get("section").equals("RC001")) {
			if(map.get("model").equals("CM003")) {
				map.put("adjModel", "CM007");
			}else if(map.get("model").equals("CM007")) {
				map.put("adjModel", "CM003");
			}
		}else {
			if(map.get("model").equals("CM001")) {
				map.put("adjModel", "CM003");
			}else if(map.get("model").equals("CM003")) {
				map.put("adjModel", "CM001");
			}
		}
		return map;
	}
}
