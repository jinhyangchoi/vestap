package vestap.adm.stat.access;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

@Service("admAccessService")
public class AdmAccessStatServiceImpl extends EgovAbstractServiceImpl implements AdmAccessService {

	@Resource(name = "userAccessService")
	private UserAccessService userAccessService;

	@Resource(name = "admAccessStatDAO")
	private AdmAccessStatDAO admAccessStatDAO;

	@Resource(name = "createExcelStyleComponent")
	private CreateExcelStyleComponent excelStyleComp;

	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent fileDownloadComp;
	
	@Override
	public List<EgovMap> selectAccessLogList(EgovMap map) {
		return this.admAccessStatDAO.selectAccessLogList(map);
	}
	
	@Override
	public List<EgovMap> selectMonthAccessLogList(EgovMap map){
		return this.admAccessStatDAO.selectMonthAccessLogList(map);
	}

	@Override
	public void downloadLogList(List<EgovMap> logList, EgovMap map, HttpServletResponse response) throws Exception {
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";	//서버에 저장되는 파일네임
		
		/** workbook 생성 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** 시트 생성 */
		XSSFSheet sheet = workbook.createSheet("접속통계");
		
		/** Row생성 */
		XSSFRow row = null;
		
		/** Cell 생성 */
		XSSFCell cell = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));
		
		/** 타이틀 */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue("접속통계");//사용자 권한별 접속통계.
		
		/** header */
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("번호 ");

		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("날짜 ");

		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("광역 지자체 사용자 접속 횟수 ");

		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("기초 지자체 사용자 접속 횟수 ");

		int rowCnt = 3;
		
		for(int i = 0; i < logList.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("rnum")));
			if(logList.get(i).get("logMonth") == null) {
				cell = row.createCell(1);
				cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
				cell.setCellValue(String.valueOf(logList.get(i).get("logYear")));
					
			}else if(logList.get(i).get("logDay") == null) {
				cell = row.createCell(1);
				cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
				cell.setCellValue(String.valueOf(logList.get(i).get("logYear"))+"-"+String.valueOf(logList.get(i).get("logMonth")));
			}
			else {
				cell = row.createCell(1);
				cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
				cell.setCellValue(String.valueOf(logList.get(i).get("logYear"))+"-"+String.valueOf(logList.get(i).get("logMonth"))+"-"+String.valueOf(logList.get(i).get("logDay")));
			}
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("sumWide")));

			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("sumBase")));
			
			rowCnt++;
		}
		
		sheet.setColumnWidth(0, 4 * 256 * 4);
		sheet.setColumnWidth(1, 4 * 256 * 4);
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
		
		this.fileDownloadComp.indicatorInputFormDownload(response, fileName, "접속통계.xlsx");
	}
	
	@Override
	public void downloadMonthLogList(List<EgovMap> logList, EgovMap map, HttpServletResponse response) throws Exception {
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";	//서버에 저장되는 파일네임
		
		/** workbook 생성 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** 시트 생성 */
		XSSFSheet sheet = workbook.createSheet("접속목록");
		
		/** Row생성 */
		XSSFRow row = null;
		
		/** Cell 생성 */
		XSSFCell cell = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
		
		/** header */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("접속시간");

		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("ID ");

		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("이름");
		
		int rowCnt = 1;
		
		String pattern = "yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		
		Calendar cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(date),Integer.parseInt("01")-1,1); 
		String lastDay = date + "-" + "01" + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String jan = lastDay;
		
		cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(date),Integer.parseInt("02")-1,1); 
		lastDay = date + "-" + "02" + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String feb = lastDay;
		
		cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(date),Integer.parseInt("03")-1,1); 
		lastDay = date + "-" + "03" + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String mar = lastDay;
		
		cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(date),Integer.parseInt("04")-1,1); 
		lastDay = date + "-" + "04" + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String apr = lastDay;
		
		cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(date),Integer.parseInt("05")-1,1); 
		lastDay = date + "-" + "05" + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String may = lastDay;
		
		cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(date),Integer.parseInt("06")-1,1); 
		lastDay = date + "-" + "06" + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String jun = lastDay;
		
		cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(date),Integer.parseInt("07")-1,1); 
		lastDay = date + "-" + "07" + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String jul = lastDay;
		
		cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(date),Integer.parseInt("08")-1,1); 
		lastDay = date + "-" + "08" + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String aug = lastDay;
		
		cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(date),Integer.parseInt("09")-1,1); 
		lastDay = date + "-" + "09" + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String sept = lastDay;
		
		cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(date),Integer.parseInt("10")-1,1); 
		lastDay = date + "-" + "10" + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String oct = lastDay;
		
		cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(date),Integer.parseInt("11")-1,1); 
		lastDay = date + "-" + "11" + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String nov = lastDay;
		
		cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(date),Integer.parseInt("12")-1,1); 
		lastDay = date + "-" + "12" + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String dec = lastDay;
		
		int janCnt = 0;
		int febCnt = 0;
		int marCnt = 0;
		int aprCnt = 0;
		int mayCnt = 0;
		int junCnt = 0;
		int julCnt = 0;
		int augCnt = 0;
		int septCnt = 0;
		int octCnt = 0;
		int novCnt = 0;
		int decCnt = 0;
		
		
		for(int i = 0; i < logList.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("accessDate")));
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("userId")));
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("districtNm")));

			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("userNm")));
			
			
			if(String.valueOf(logList.get(i).get("accessDate")).compareTo(jan) <= 0 ){
				janCnt++;
			}else if(String.valueOf(logList.get(i).get("accessDate")).compareTo(feb) <= 0 ){
				febCnt++;
			}else if(String.valueOf(logList.get(i).get("accessDate")).compareTo(mar) <= 0 ){
				marCnt++;
			}else if(String.valueOf(logList.get(i).get("accessDate")).compareTo(apr) <= 0 ){
				aprCnt++;
			}else if(String.valueOf(logList.get(i).get("accessDate")).compareTo(may) <= 0 ){
				mayCnt++;
			}else if(String.valueOf(logList.get(i).get("accessDate")).compareTo(jun) <= 0 ){
				junCnt++;
			}else if(String.valueOf(logList.get(i).get("accessDate")).compareTo(jul) <= 0 ){
				julCnt++;
			}else if(String.valueOf(logList.get(i).get("accessDate")).compareTo(aug) <= 0 ){
				augCnt++;
			}else if(String.valueOf(logList.get(i).get("accessDate")).compareTo(sept) <= 0 ){
				septCnt++;
			}else if(String.valueOf(logList.get(i).get("accessDate")).compareTo(oct) <= 0 ){
				octCnt++;
			}else if(String.valueOf(logList.get(i).get("accessDate")).compareTo(nov) <= 0 ){
				novCnt++;
			}else if(String.valueOf(logList.get(i).get("accessDate")).compareTo(dec) <= 0 ){
				decCnt++;
			}
			
			
			rowCnt++;
		}
		sheet.setColumnWidth(0, 4 * 256 * 4);
		sheet.setColumnWidth(1, 4 * 256 * 4);
		sheet.setColumnWidth(2, 4 * 256 * 8);
		sheet.setColumnWidth(3, 4 * 256 * 8);
		
		sheet = workbook.createSheet("접속횟수");
		
		row = sheet.createRow(0);
		
		for(int i=0; i<12; i++){
			cell = row.createCell(i);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue((i+1)+"월");
			
			if(i==11){
				cell = row.createCell(i+1);
				cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
				cell.setCellValue(map.get("year")+"년 누적");
			}
		}
		
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(janCnt);
		cell = row.createCell(1);
		cell.setCellValue(febCnt);
		cell = row.createCell(2);
		cell.setCellValue(marCnt);
		cell = row.createCell(3);
		cell.setCellValue(aprCnt);
		cell = row.createCell(4);
		cell.setCellValue(mayCnt);
		cell = row.createCell(5);
		cell.setCellValue(junCnt);
		cell = row.createCell(6);
		cell.setCellValue(julCnt);
		cell = row.createCell(7);
		cell.setCellValue(augCnt);
		cell = row.createCell(8);
		cell.setCellValue(septCnt);
		cell = row.createCell(9);
		cell.setCellValue(octCnt);
		cell = row.createCell(10);
		cell.setCellValue(novCnt);
		cell = row.createCell(11);
		cell.setCellValue(decCnt);
		cell = row.createCell(12);
		cell.setCellValue(janCnt+febCnt+marCnt+aprCnt+mayCnt+junCnt+julCnt+augCnt+septCnt+octCnt+novCnt+decCnt);
		
		
		File file = new File(filePath);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		file = new File(filePath, fileName);
		
		FileOutputStream fileOs = new FileOutputStream(file);
		
		workbook.write(fileOs);
		fileOs.close();
		workbook.close();
		
		this.fileDownloadComp.indicatorInputFormDownload(response, fileName, map.get("year") + "년" + map.get("month") + "월" + map.get("day") + "일_기준_월별_통계.xlsx");

	}
	
}
