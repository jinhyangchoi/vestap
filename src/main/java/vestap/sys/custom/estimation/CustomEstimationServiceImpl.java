package vestap.sys.custom.estimation;

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

@Service("customEstimationService")
public class CustomEstimationServiceImpl extends EgovAbstractServiceImpl implements CustomEstimationService {
	
	@Resource(name = "customEstimationDAO")
	private CustomEstimationDAO DAO;
	
	@Resource(name = "createExcelStyleComponent")
	private CreateExcelStyleComponent excelStyleComp;
	
	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent fileDownloadComp;

	@Override
	public List<Map<String, String>> getReferenceInfo(Map<String, Object> params) throws Exception {
		return this.DAO.getReferenceInfo(params);
	}

	@Override
	public List<Map<String, String>> vulAssessment(Map<String, Object> params) throws Exception {
		return this.DAO.vulAssessment(params);
	}

	@Override
	public int vulAssessmentTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.vulAssessmentTotCnt(params);
	}

	@Override
	public List<Map<String, String>> getReferenceIndicator(Map<String, Object> params) throws Exception {
		return this.DAO.getReferenceIndicator(params);
	}

	@Override
	public Map<String, String> getSectorWeight(Map<String, Object> params) throws Exception {
		return this.DAO.getSectorWeight(params);
	}

	@Override
	public Map<String, String> getIndicatorInfo(Map<String, Object> params) throws Exception {
		return this.DAO.getIndicatorInfo(params);
	}

	@Override
	public List<Map<String, String>> getReferenceIndicatorInfo(Map<String, Object> params) throws Exception {
		return this.DAO.getReferenceIndicatorInfo(params);
	}

	@Override
	public int getReferenceIndicatorInfoTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getReferenceIndicatorInfoTotCnt(params);
	}

	@Override
	public void downloadEstimation(List<Map<String, String>> list, Map<String, Object> params, HttpServletResponse response) throws Exception {
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";
		
		Map<String, String> districtInfo = this.DAO.getDistrictInfoForExcel(params);
		Map<String, String> itemInfo = this.DAO.getItemInfoForExcel(params);
		
		/** workbook 생성 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** 시트 생성 */
		XSSFSheet sheet = workbook.createSheet("취약성 평가 결과");
		
		/** Row생성 */
		XSSFRow row = null;
		
		/** Cell 생성 */
		XSSFCell cell = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 5));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 5));
		
		/** 타이틀 */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue(districtInfo.get("sd_nm") + " " + districtInfo.get("sgg_nm") + " 의 " + itemInfo.get("item_nm") + " 평가 도출 내역");
		
		/** 평가 지역 */
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("평가 지역");
		
		cell = row.createCell(1);
		cell.setCellValue(districtInfo.get("sd_nm") + " " + districtInfo.get("sgg_nm"));
		
		/** 평가 분야 */
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("평가 분야");
		
		cell = row.createCell(4);
		cell.setCellValue(params.get("field").toString());
		
		/** 평가 항목 */
		row = sheet.createRow(3);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("평가 항목");
		
		cell = row.createCell(1);
		cell.setCellValue(itemInfo.get("item_nm"));
		
		/** 적용 기후 모델 */
		row = sheet.createRow(4);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("적용 기후 모델");
		
		cell = row.createCell(1);
		cell.setCellValue(params.get("model") + " " + params.get("rcp") + " " + params.get("year"));
		
		/** header */
		row = sheet.createRow(5);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("순위");
		
		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("행정구역 명칭");
		
		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("취약성 종합 지수");
		
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("기후 노출 부문");
		
		cell = row.createCell(4);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("기후 변화 민감도 부문");
		
		cell = row.createCell(5);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("적응 능력 부문");
		
		int rowCnt = 6;
		
		for(int i = 0; i < list.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("rnum")));
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("district_nm")));
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("ci_val")));
			
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("ce_val")));
			
			cell = row.createCell(4);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("cs_val")));
			
			cell = row.createCell(5);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("aa_val")));
			
			rowCnt++;
		}
		
		sheet.setColumnWidth(0, 4 * 256 * 5);
		sheet.setColumnWidth(1, 4 * 256 * 5);
		sheet.setColumnWidth(2, 4 * 256 * 5);
		sheet.setColumnWidth(3, 4 * 256 * 5);
		sheet.setColumnWidth(4, 4 * 256 * 5);
		sheet.setColumnWidth(5, 4 * 256 * 5);
		
		File file = new File(filePath);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		file = new File(filePath, fileName);
		
		FileOutputStream fileOs = new FileOutputStream(file);
		
		workbook.write(fileOs);
		fileOs.close();
		workbook.close();
		
		this.fileDownloadComp.indicatorInputFormDownload(response, fileName, itemInfo.get("item_nm").replaceAll("\\s", "_") + ".xlsx");
	}

	@Override
	public void downloadIndicatorData(Map<String, Object> params, HttpServletResponse response) throws Exception {
		
		Map<String, String> indicatorInfo = this.DAO.getIndicatorInfo(params);
		
		List<Map<String, String>> indicatorInfoList = this.DAO.getReferenceIndicatorInfo(params);
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";
		
		/** workbook 생성 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** 시트 생성 */
		XSSFSheet sheet = workbook.createSheet("취약성 평가 결과");
		
		/** Row생성 */
		XSSFRow row = null;
		
		/** Cell 생성 */
		XSSFCell cell = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));
		
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue(indicatorInfo.get("indi_nm").toString() + " (단위: " + indicatorInfo.get("indi_unit") + ")");
		
		/** header */
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("번호");
		
		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("행정구역 명칭");
		
		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("지표값");
		
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("연도");
		
		int rowCnt = 3;
		
		for(int i = 0; i < indicatorInfoList.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorInfoList.get(i).get("rnum")));
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorInfoList.get(i).get("district_nm")));
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorInfoList.get(i).get("indi_val")));
			
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorInfoList.get(i).get("indi_year")));
			
			rowCnt++;
		}
		
		if(!String.valueOf(params.get("case")).equals("N")) {
			
			sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 3));
			
			row = sheet.createRow(rowCnt);
			cell = row.createCell(0);
			cell.setCellValue(String.valueOf(params.get("case")));
		}
		
		sheet.setColumnWidth(0, 4 * 256 * 5);
		sheet.setColumnWidth(1, 4 * 256 * 5);
		sheet.setColumnWidth(2, 4 * 256 * 5);
		sheet.setColumnWidth(3, 4 * 256 * 5);
		
		File file = new File(filePath);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		file = new File(filePath, fileName);
		
		FileOutputStream fileOs = new FileOutputStream(file);
		
		workbook.write(fileOs);
		fileOs.close();
		workbook.close();
		
		this.fileDownloadComp.indicatorInputFormDownload(response, fileName, indicatorInfo.get("indi_nm").replaceAll("\\s", "_") + ".xlsx");
	}

	@Override
	public Map<String, String> getIndicatorListInfo(String param) throws Exception {
		return this.DAO.getIndicatorListInfo(param);
	}

	@Override
	public int isNullData(String param) throws Exception {
		return this.DAO.isNullData(param);
	}
	
	@Override
	public List<EgovMap> selectSectionList(EgovMap map) {
		return this.DAO.selectClimateOption(map);
	}
	
	@Override
	public List<EgovMap> selectModelList(EgovMap map) {
		return this.DAO.selectClimateOption(map);
	}
	
	@Override
	public List<EgovMap> selectYearList(EgovMap map) {
		return this.DAO.selectClimateOption(map);
	}
}
