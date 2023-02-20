package vestap.sys.dbinfo.indicator;

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
import vestap.sys.cmm.util.CreateExcelStyleComponent;
import vestap.sys.cmm.util.FileDownloadComponent;

@Service("indicatorDBinfoService")
public class IndicatorDBinfoServiceImpl extends EgovAbstractServiceImpl implements IndicatorDBinfoService {
	
	@Resource(name = "indicatorDBinfoDAO")
	private IndicatorDBinfoDAO DAO;

	@Resource(name = "createExcelStyleComponent")
	private CreateExcelStyleComponent excelStyleComp;
	
	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent fileDownloadComp;
	
	@Override
	public List<Map<String, String>> getIndicatorGroup() throws Exception {
		return this.DAO.getIndicatorGroup();
	}

	@Override
	public List<Map<String, String>> getIndicatorList(Map<String, Object> params) throws Exception {
		return this.DAO.getIndicatorList(params);
	}

	@Override
	public int getIndicatorListTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getIndicatorListTotCnt(params);
	}

	@Override
	public Map<String, String> getIndicatorListInfo(Map<String, Object> params) throws Exception {
		return this.DAO.getIndicatorListInfo(params);
	}

	@Override
	public List<Map<String, String>> getIndicatorDataInfo(Map<String, Object> params) throws Exception {
		return this.DAO.getIndicatorDataInfo(params);
	}

	@Override
	public int getIndicatorDataInfoTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getIndicatorDataInfoTotCnt(params);
	}

	@Override
	public List<Map<String, String>> getDistrictSd() throws Exception {
		return this.DAO.getDistrictSd();
	}

	@Override
	public List<Map<String, String>> getDistrictSgg(String param) throws Exception {
		return this.DAO.getDistrictSgg(param);
	}
	
	@Override
	public List<Map<String, String>> getIndicatorItem(Map<String, Object> params) throws Exception {
		return this.DAO.getIndicatorItem(params);
	}
	
	@Override
	public int getIndicatorItemTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getIndicatorItemTotCnt(params);
	}

	@Override
	public List<Map<String, String>> getMetaInfoList(String param) throws Exception {
		return this.DAO.getMetaInfoList(param);
	}

	@Override
	public void downloadIndicator(HttpServletResponse response, Map<String, Object> params) throws Exception {
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";
		
		Map<String, String> indicatorListInfo = this.DAO.getIndicatorListInfo(params);
		List<Map<String, String>> indicatorDataList = this.DAO.getIndicatorDataInfo(params);
		
		String district_cd = params.get("district_info").toString();
		String district_nm = "";
		
		if(district_cd.length() == 2) {
			
			district_nm = this.DAO.getDistrictSdInfo(district_cd).get("district_nm");
			
		} else if(district_cd.length() == 4) {
			
			district_nm = this.DAO.getDistrictSdInfo(district_cd.substring(0, 2)).get("district_nm");
			district_nm = district_nm + " " + this.DAO.getDistrictSggInfo(district_cd).get("district_nm");
		}
		
		int construct_meth_len = indicatorListInfo.get("indi_construct_meth").split("<br/>").length;
		
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
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 2));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 5));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 5));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 5));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 2, 3));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 4, 5));
		
		/** 타이틀 */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue(indicatorListInfo.get("indi_nm") + "\n" + "(" + district_nm + ")");
		
		/** IPCC 1 */
		row = sheet.createRow(2);
		
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("IPCC WG I");
		
		cell = row.createCell(1);
		cell.setCellValue(indicatorListInfo.get("ipcc_large_nm"));
		
		/** IPCC 2 */
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("IPCC WG II");
		
		cell = row.createCell(4);
		cell.setCellValue(indicatorListInfo.get("ipcc_small_nm"));
		
		/** 단위 */
		row = sheet.createRow(3);
		
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("단위");
		
		cell = row.createCell(1);
		cell.setCellValue(indicatorListInfo.get("indi_unit"));
		
		/** 지표 구축/가공 방법*/
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("지표 구축/가공 방법");
		
		cell = row.createCell(4);
		cell.setCellValue(indicatorListInfo.get("indi_construct_nm"));
		
		
		/** 지표 자료 설명 */
		row = sheet.createRow(4);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("지표 자료 설명");
		
		cell = row.createCell(1);
		cell.setCellValue(indicatorListInfo.get("indi_account"));
		
		/** 구축방법 */
		row = sheet.createRow(5);
		row.setHeight((short) (construct_meth_len * 150 * 5));
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("구축 방법");
		
		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.wrapText(workbook));
		cell.setCellValue(indicatorListInfo.get("indi_construct_meth").replaceAll("<br/>", "\n"));
		
		/** header */
		row = sheet.createRow(6);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("번호");
		
		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("행정구역 코드");
		
		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("행정구역 명칭");
		
		cell = row.createCell(4);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("지표값");
		
		int rowCnt = 7;
		
		for(int i = 0; i < indicatorDataList.size(); i++) {
			
			sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 2, 3));
			sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 4, 5));
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorDataList.get(i).get("rnum")));
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorDataList.get(i).get("district_cd")));
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorDataList.get(i).get("district_nm")));
			
			cell = row.createCell(4);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorDataList.get(i).get("indi_val")));
			
			rowCnt++;
		}
		
		if(!String.valueOf(params.get("case")).equals("N")) {
			
			sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 5));
			
			row = sheet.createRow(rowCnt);
			cell = row.createCell(0);
			cell.setCellValue(String.valueOf(params.get("case")));
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
		
		this.fileDownloadComp.indicatorInputFormDownload(response, fileName, indicatorListInfo.get("indi_nm").replaceAll("\\s", "_") + ".xlsx");
	}

	@Override
	public String getIndicatorSki(String param) throws Exception {
		return this.DAO.getIndicatorSki(param);
	}

	@Override
	public String getScenarioSki(String param) throws Exception {
		return this.DAO.getScenarioSki(param);
	}

	@Override
	public List<Map<String, String>> getIndicatorSkiDistrictSd(String param) throws Exception {
		return this.DAO.getIndicatorSkiDistrictSd(param);
	}

	@Override
	public List<Map<String, String>> getScenarioSkiDistrictSd(String param) throws Exception {
		return this.DAO.getScenarioSkiDistrictSd(param);
	}

	@Override
	public List<Map<String, String>> getIndicatorSkiDistrictSgg(Map<String, Object> params) throws Exception {
		return this.DAO.getIndicatorSkiDistrictSgg(params);
	}

	@Override
	public List<Map<String, String>> getScenarioSkiDistrictSgg(Map<String, Object> params) throws Exception {
		return this.DAO.getScenarioSkiDistrictSgg(params);
	}
}
