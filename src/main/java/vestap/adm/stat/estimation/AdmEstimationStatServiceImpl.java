package vestap.adm.stat.estimation;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
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
import vestap.sys.sec.user.UserAccessService;

/**
 * 기후변화 취약성 > 취약성평가 -------------------------------------------------- 수정일 수정자
 * 수정내용 -------------------------------------------------- 2018.10.31 vestap개발
 * 최초 작성 --------------------------------------------------
 * 
 * @author vestap 개발
 * @since 2018.10.31
 *
 */

@Service("admEstimationStatService")
public class AdmEstimationStatServiceImpl extends EgovAbstractServiceImpl implements AdmEstimationStatService {

	@Resource(name = "userAccessService")
	private UserAccessService userAccessService;

	@Resource(name = "admEstimationStatDAO")
	private AdmEstimationStatDAO admEstimationStatDAO;

	@Resource(name = "createExcelStyleComponent")
	private CreateExcelStyleComponent excelStyleComp;

	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent fileDownloadComp;
	
	@Override
	public List<EgovMap> selectOptionlist(EgovMap map) {
		return this.admEstimationStatDAO.selectOptionlist(map);
	}

	@Override
	public List<EgovMap> selectEstimationList(EgovMap map) {
		return this.admEstimationStatDAO.selectEstimationList(map);
	}
	
	@Override
	public List<EgovMap> selectEstimationTotalList(EgovMap map) {
		return this.admEstimationStatDAO.selectEstimationTotalList(map);
	}


	@Override
	public void downloadLogList(List<EgovMap> list, List<EgovMap> totalList, String titleText, String fieldNm,  EgovMap map, HttpServletResponse response) throws Exception {
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";	//서버에 저장되는 파일네임
		/** workbook 생성 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** 시트 생성 */
		XSSFSheet sheet = workbook.createSheet("취약성평가  조회 통계");
		
		/** Row생성 */
		XSSFRow row = null;
		
		/** Cell 생성 */
		XSSFCell cell = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));
		
		/** 타이틀 */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue("취약성평가 조회 통계 - " + titleText);
		
		/** header */
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("취약성평가 항목 ");

		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("전체 사용자 취약성평가 진행 횟수 ");

		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("광역 지자체 취약성평가 진행 횟수 ");

		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("기초 지자체 취약성평가 진행 횟수 ");
		
		/** 전체 데이터 합 */
		row = sheet.createRow(3);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationFirstValue(workbook));
		cell.setCellValue(fieldNm);

		for(int i = 0; i < totalList.size();i++) {
		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationFirstValue(workbook));
		cell.setCellValue(String.valueOf(totalList.get(i).get("totalSum")));
		
		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationFirstValue(workbook));
		cell.setCellValue(String.valueOf(totalList.get(i).get("totalWide")));

		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationFirstValue(workbook));
		cell.setCellValue(String.valueOf(totalList.get(i).get("totalBase")));
		}
		
		int rowCnt = 4;
		
		for(int i = 0; i < list.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("itemNm")));

			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("sumTotal")));
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("sumWide")));

			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("sumBase")));
			
			rowCnt++;
		}
		
		sheet.setColumnWidth(0, 4 * 256 * 12);
		sheet.setColumnWidth(1, 4 * 256 * 8);
		sheet.setColumnWidth(2, 4 * 256 * 8);
		sheet.setColumnWidth(3, 4 * 256 * 8);
		
		File file = new File(filePath);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		file = new File(filePath, fileName);
		
		FileOutputStream fileOs = new FileOutputStream(file);
		
		workbook.write(fileOs);
		fileOs.close();
		workbook.close();
		
		this.fileDownloadComp.indicatorInputFormDownload(response, fileName, "취약성평가_조회_통계.xlsx");
	}
	
}
