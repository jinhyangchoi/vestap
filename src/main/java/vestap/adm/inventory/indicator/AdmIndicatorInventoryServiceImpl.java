package vestap.adm.inventory.indicator;

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

@Service("admIndicatorInventoryService")
public class AdmIndicatorInventoryServiceImpl extends EgovAbstractServiceImpl implements AdmIndicatorInventoryService {
	
	@Resource(name = "admIndicatorInventoryDAO")
	private AdmIndicatorInventoryDAO DAO;

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
		
		int construct_nm_len = indicatorListInfo.get("indi_construct_nm").split("<br/>").length;
		
		/** workbook 생성 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** 시트 생성 */
		XSSFSheet sheet = workbook.createSheet("취약성 평가 결과");
		
		/** Row생성 */
		XSSFRow row = null;
		
		/** Cell 생성 */
		XSSFCell cell = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 5));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 2));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 5));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 2));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 4, 5));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 5));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 2, 3));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 4, 5));
		/** 타이틀 */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue(indicatorListInfo.get("indi_nm") + "\n" + "( 지자체 : " + district_nm + ")");

		/** 리스크 항목 */
		row = sheet.createRow(2);
		
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("리스크 ");
		
		cell = row.createCell(1);
		if(indicatorListInfo.get("field_nm") == null ) {
			cell.setCellValue("아직 사용되지 않은 지표입니다.");
		} else {
			cell.setCellValue(indicatorListInfo.get("field_nm"));
		}
		
		/** IPCC 1 */
		row = sheet.createRow(3);
		
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
		row = sheet.createRow(4);
		
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("단위");
		
		cell = row.createCell(1);
		cell.setCellValue(indicatorListInfo.get("indi_unit"));
		
		/** 지표 자료 설명 */
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("지표 자료 설명");
		
		cell = row.createCell(4);
		cell.setCellValue(indicatorListInfo.get("indi_account"));
		
		/** 구축방법 */
		row = sheet.createRow(5);
		row.setHeight((short) (construct_nm_len * 150 * 5));
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("구축 방법");
		
		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.wrapText(workbook));
		cell.setCellValue(indicatorListInfo.get("indi_construct_nm").replaceAll("<br/>", "\n"));
		
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
		cell.setCellValue("값");
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
	public List<Map<String, String>> areaStatisticsList(Map<String, Object> params) throws Exception{
		return this.DAO.areaStatisticsList(params);
		
	}
	
	@Override
	public List<Map<String, String>> detailAreaStatisticsList(Map<String, Object> params) throws Exception{
		return this.DAO.detailAreaStatisticsList(params);
		
	}
	
	@Override
	public List<EgovMap> downloadInvenIndi(EgovMap map){
		return this.DAO.downloadInvenIndi(map);
	}

	@Override
	public List<Map<String, String>> downloadInvenIndiCnt(EgovMap map) {
		return this.DAO.downloadInvenIndiCnt(map);
	}
	
	@Override
	public void downloadInventory(List<EgovMap> list, List<Map<String, String>> cntList, String sido, HttpServletResponse response) throws Exception {
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";	//서버에 저장되는 파일네임
		
		/** workbook 생성 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** 시트 생성 */
		XSSFSheet sheet = workbook.createSheet("취약성평가 지표데이터");
		XSSFSheet sheet1 = workbook.createSheet("통계");
		
		/** Row생성 */
		XSSFRow row = null;
		XSSFRow row1 = null;
		
		/** Cell 생성 */
		XSSFCell cell = null;
		XSSFCell cell1 = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 2));
		
		/** 타이틀 */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue(sido + " " +" 취약성 평가 지표");
		
		/** header */
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("번호 ");
		
		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("지표명 ");
		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("등록(수정)날짜 ");
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("지자체");
		
		int rowCnt = 3;
		
		for(int i = 0; i < list.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("rnum")));
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("indiNm")));
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("itemRegdate2")));
			
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("districtNm")));
			
			rowCnt++;
		}
		
		sheet.setColumnWidth(0, 4 * 256 * 4);
		sheet.setColumnWidth(1, 4 * 256 * 10);
		sheet.setColumnWidth(2, 4 * 256 * 5);
		sheet.setColumnWidth(3, 4 * 256 * 5);
		sheet.setColumnWidth(4, 4 * 256 * 5);
		
		row1 = sheet1.createRow(0);
		cell= row1.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("지자체 ");
		cell = row1.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("통계 ");
		
		int rowCnt1 = 1;
		
		for(int i = 0; i < cntList.size(); i++) {
			
			row1 = sheet1.createRow(rowCnt1);
			
			cell = row1.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(cntList.get(i).get("district_nm_union")));
			
			cell = row1.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(cntList.get(i).get("total")));
			
			rowCnt1++;
		}
		
		File file = new File(filePath);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		file = new File(filePath, fileName);
		
		FileOutputStream fileOs = new FileOutputStream(file);
		
		workbook.write(fileOs);
		fileOs.close();
		workbook.close();
		
		this.fileDownloadComp.indicatorInputFormDownload(response, fileName, "인벤토리_취약성평가지표.xlsx");
		
	}
}
