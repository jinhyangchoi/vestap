package vestap.adm.adm.management;

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

@Service("admAdmManagementService")
public class AdmAdmManagementServiceImpl extends EgovAbstractServiceImpl implements AdmAdmManagementService{

	@Resource(name = "admAdmManagementDAO")
	private AdmAdmManagementDAO admAdmManagementDAO;
	
	@Resource(name = "createExcelStyleComponent")
	private CreateExcelStyleComponent excelStyleComp;
	
	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent fileDownloadComp;
	
	@Override
	public List<EgovMap> selectAdminList(EgovMap map){
		return this.admAdmManagementDAO.selectAdminList(map);
	}
	
	@Override
	public int selectPageCount(EgovMap map){
		return this.admAdmManagementDAO.selectPageCount(map);
	}
	
	@Override
	public int selectAccessPageCnt(EgovMap map ){
		return this.admAdmManagementDAO.selectAccessPageCnt(map);
	}
	
	@Override
	public int selectReqestLogCnt(EgovMap map){
		return this.admAdmManagementDAO.selectReqestLogCnt(map);
	}
	
	@Override
	public int selectAdminIdCheck(EgovMap map){
		return this.admAdmManagementDAO.selectAdminIdCheck(map);
	}
	
	@Override
	public int insertAdmin(EgovMap map){
		return this.admAdmManagementDAO.insertAdmin(map);
	}
	
	@Override
	public int deleteAdmin(EgovMap map){
		return this.admAdmManagementDAO.deleteAdmin(map);
	}
	
	@Override
	public EgovMap getAdminInfo(EgovMap map){
		return this.admAdmManagementDAO.getAdminInfo(map);
	}
	
	@Override
	public List<EgovMap> selectAdminAccessLogList(EgovMap map){
		return this.admAdmManagementDAO.selectAdminAccessLogList(map);
	}
	
	@Override
	public List<EgovMap> selectAdminIdList(EgovMap map){
		return this.admAdmManagementDAO.selectAdminIdList(map);
	}
	
	@Override
	public void downloadAdminAccessLogList(List<EgovMap> logList, EgovMap map, HttpServletResponse response) throws Exception {
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";	//????????? ???????????? ????????????
		
		/** workbook ?????? */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** ?????? ?????? */
		XSSFSheet sheet = workbook.createSheet("????????????");
		
		/** Row?????? */
		XSSFRow row = null;
		
		/** Cell ?????? */
		XSSFCell cell = null;
		
		/** 
		 * Cell ??????
		 * CellRangeAddress(??????, ????????????, ??????, ????????????) 
		 * */
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4));
		
		/** ????????? */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue("????????????");
		
		/** 
		 * header (?????? ????????? ?????? ?????? ip ?????? ??????) 
		 * */
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("?????? ");

		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("????????? ");

		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("??? ???");

		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("??????IP");
		
		cell = row.createCell(4);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("?????? ??????");
		
		int rowCnt = 3;
		
		for(int i = 0; i < logList.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("no")));
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("userId")));
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("userNm")));
			
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("accessIp")));
			
			cell = row.createCell(4);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("logDate")));
			
			rowCnt++;
		}
		
		sheet.setColumnWidth(0,  4 * 256 * 4);
		sheet.setColumnWidth(1,  4 * 256 * 4);
		sheet.setColumnWidth(2,  4 * 256 * 4);
		sheet.setColumnWidth(3,  4 * 256 * 8);
		sheet.setColumnWidth(4,  4 * 256 * 8);
		
		File file = new File(filePath);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		file = new File(filePath, fileName);
		
		FileOutputStream fileOs = new FileOutputStream(file);
		
		workbook.write(fileOs);
		fileOs.close();
		workbook.close();
		
		this.fileDownloadComp.indicatorInputFormDownload(response,fileName, "????????????.xlsx");
	}
	
	@Override
	public int insertRequestLog(EgovMap map){
		return this.admAdmManagementDAO.insertRequestLog(map);
	}
	
	
	@Override
	public List<EgovMap> selectRequestLogList(EgovMap map){
		return this.admAdmManagementDAO.selectAdminRequestLogList(map);
	}
	
	@Override
	public void downloadAdminRequestLogList(List<EgovMap> logList, EgovMap map, HttpServletResponse response) throws Exception {
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";	//????????? ???????????? ????????????
		
		/** workbook ?????? */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** ?????? ?????? */
		XSSFSheet sheet = workbook.createSheet("????????????");
		
		/** Row?????? */
		XSSFRow row = null;
		
		/** Cell ?????? */
		XSSFCell cell = null;
		
		/** 
		 * Cell ??????
		 * CellRangeAddress(??????, ????????????, ??????, ????????????) 
		 * */
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 6));
		
		/** ????????? */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue("????????????");
		
		/** 
		 * header (?????? ????????? ?????? ?????? ip ?????? ??????) 
		 * */
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("?????? ");

		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("????????? ");
		
		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("?????? ??????");

		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("URL");

		cell = row.createCell(4);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("??????1");
		
		cell = row.createCell(5);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("??????2");
		
		cell = row.createCell(6);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("??????");
		
		int rowCnt = 3;
		
		for(int i = 0; i < logList.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("no")));
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("userId")));
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("logDate")));
			
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("urlDepth2")));
			
			cell = row.createCell(4);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("urlDepth3")));
			
			cell = row.createCell(5);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(logList.get(i).get("urlDepth4")));
			
			rowCnt++;
		}
		
		sheet.setColumnWidth(0,  4 * 256 * 4);
		sheet.setColumnWidth(1,  4 * 256 * 4);
		sheet.setColumnWidth(2,  4 * 256 * 8);
		sheet.setColumnWidth(3,  4 * 256 * 8);
		sheet.setColumnWidth(4,  4 * 256 * 8);
		sheet.setColumnWidth(5,  4 * 256 * 4);
		
		File file = new File(filePath);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		file = new File(filePath, fileName);
		
		FileOutputStream fileOs = new FileOutputStream(file);
		
		workbook.write(fileOs);
		fileOs.close();
		workbook.close();
		
		this.fileDownloadComp.indicatorInputFormDownload(response,fileName, "????????????.xlsx");
	}
	
	@Override
	public List<EgovMap> selectRefcode(){
		return this.admAdmManagementDAO.selectRefcode();
	}
	
	@Override
	public EgovMap getNoticeIdxNm(EgovMap map){
		return this.admAdmManagementDAO.getNoticeIdxNm(map);
	}
	
	@Override
	public EgovMap getFaqIdxNm(EgovMap map){
		return this.admAdmManagementDAO.getFaqIdxNm(map);
	}
	
	@Override
	public EgovMap getSuggestionIdxNm(EgovMap map){
		return this.admAdmManagementDAO.getSuggestionIdxNm(map);
	}
	
	@Override
	public EgovMap getReferenceIdxNm(EgovMap map){
		return this.admAdmManagementDAO.getReferenceIdxNm(map);
	}
}
