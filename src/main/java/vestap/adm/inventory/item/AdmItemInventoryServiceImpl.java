package vestap.adm.inventory.item;

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

@Service("admItemInventoryService")
public class AdmItemInventoryServiceImpl extends EgovAbstractServiceImpl implements AdmItemInventoryService {
	
	@Resource(name = "admItemInventoryDAO")
	private AdmItemInventoryDAO DAO;
	
	@Resource(name = "createExcelStyleComponent")
	private CreateExcelStyleComponent excelStyleComp;
	
	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent fileDownloadComp;
	
	@Override
	public List<Map<String, String>> getItemList(Map<String, Object> params) throws Exception {
		return this.DAO.getItemList(params);
	}

	@Override
	public int getItemListTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getItemListTotCnt(params);
	}

	@Override
	public List<Map<String, String>> getIndicatorInfo(Map<String, Object> params) throws Exception {
		return this.DAO.getIndicatorInfo(params);
	}

	@Override
	public List<EgovMap> selectSidoList() {
		return this.DAO.selectSidoList();
	}

	@Override
	public List<EgovMap> selectSigungulist(EgovMap map) {
		return this.DAO.selectSigungulist(map);
	}


	@Override
	public void downloadIndicatorInfo(Map<String, Object> params, HttpServletResponse response) throws Exception {
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";
		
		List<Map<String, String>> indicatorInfoList = this.DAO.getIndicatorInfo(params);
		
		Map<String, String> itemInfo = this.DAO.getItemInfo(params);
		
		/** workbook ?????? */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** ?????? ?????? */
		XSSFSheet sheet = workbook.createSheet("????????? ?????? ??????");
		
		/** Row?????? */
		XSSFRow row = null;
		
		/** Cell ?????? */
		XSSFCell cell = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4));
		
		/** ????????? */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue(itemInfo.get("item_nm").toString());
		
		/** header */
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("??????");
		
		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("??????");
		
		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("?????????");
		
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("????????????");
		
		cell = row.createCell(4);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("?????????");
		
		int rowCnt = 3;
		
		for(int i = 0; i < indicatorInfoList.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorInfoList.get(i).get("rnum")));
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorInfoList.get(i).get("sector_nm")));
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorInfoList.get(i).get("indi_nm")) + "(" + String.valueOf(indicatorInfoList.get(i).get("indi_unit")) + ")");
			
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorInfoList.get(i).get("indi_construct_nm")));
			
			cell = row.createCell(4);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(indicatorInfoList.get(i).get("indi_val_weight")));
			
			rowCnt++;
		}
		
		sheet.setColumnWidth(0, 2 * 256 * 5);
		sheet.setColumnWidth(1, 4 * 256 * 5);
		sheet.setColumnWidth(2, 10 * 256 * 5);
		sheet.setColumnWidth(3, 5 * 256 * 5);
		sheet.setColumnWidth(4, 2 * 256 * 5);
		
		File file = new File(filePath);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		file = new File(filePath, fileName);
		
		FileOutputStream fileOs = new FileOutputStream(file);
		
		workbook.write(fileOs);
		fileOs.close();
		workbook.close();
		
		this.fileDownloadComp.indicatorInputFormDownload(response, fileName, itemInfo.get("item_nm").toString().replaceAll("\\s", "_") + ".xlsx");
	}

	@Override
	public Map<String, String> getItemInfo(Map<String, Object> params) throws Exception {
		return this.DAO.getItemInfo(params);
	}
	
	// ????????? ?????? ?????????(?????????) (JSON) (2019.10.20,?????????)
	@Override
	public List<Map<String, String>> eventAreaStatisticsList(Map<String, Object> params) throws Exception {
		return this.DAO.eventAreaStatisticsList(params);
	}
	
	// ????????? ?????? ?????????(????????????) (JSON) (2019.10.28,?????????)
		@Override
		public List<Map<String, String>> eventDetailAreaStatisticsList(Map<String, Object> params) throws Exception {
			return this.DAO.eventDetailAreaStatisticsList(params);
		}
	
	@Override
	public List<EgovMap> downloadInventoryItem(EgovMap map){
		return this.DAO.downloadInventoryItem(map);
	}
	
	@Override
	public List<Map<String, String>> downloadInventoryItemCnt(EgovMap map){
		return this.DAO.downloadInventoryItemCnt(map);
	}
	
	@Override
	public void downloadInventory(List<EgovMap> list, List<Map<String, String>> cntList,  String sido, String sigungu, HttpServletResponse response) throws Exception {
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";	//????????? ???????????? ????????????
		
		/** workbook ?????? */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** ?????? ?????? */
		XSSFSheet sheet = workbook.createSheet("??????????????? ??????");
		XSSFSheet sheet1 = workbook.createSheet("??????");
		
		/** Row?????? */
		XSSFRow row = null;
		XSSFRow row1 = null;
		
		/** Cell ?????? */
		XSSFCell cell = null;
		XSSFCell cell1 = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 2));
		
		/** ????????? */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue(sido + " " + sigungu + " ????????? ?????? ??????");
		
		/** header */
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("?????? ");
		
		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("????????? ");
		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("?????????/?????? ");
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("??????(??????)?????? ");
		cell = row.createCell(4);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("?????????");
		
		int rowCnt = 3;
		
		for(int i = 0; i < list.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("rnum")));
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("itemNm")));
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("fieldNm")));
			
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("itemRegdate")));
			
			cell = row.createCell(4);
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
		cell = row1.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("????????? ");
		cell = row1.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("?????? ");
		
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
		
		this.fileDownloadComp.indicatorInputFormDownload(response, fileName, "????????????_?????????????????????.xlsx");
	}
}
