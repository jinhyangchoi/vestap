package vestap.sys.dbinfo.item;

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

@Service("itemDBinfoService")
public class ItemDBinfoServiceImpl extends EgovAbstractServiceImpl implements ItemDBinfoService {
	
	@Resource(name = "itemDBinfoDAO")
	private ItemDBinfoDAO DAO;
	
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
	public void downloadIndicatorInfo(Map<String, Object> params, HttpServletResponse response) throws Exception {
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";
		
		List<Map<String, String>> indicatorInfoList = this.DAO.getIndicatorInfo(params);
		
		Map<String, String> itemInfo = this.DAO.getItemInfo(params);
		
		/** workbook 생성 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** 시트 생성 */
		XSSFSheet sheet = workbook.createSheet("취약성 평가 결과");
		
		/** Row생성 */
		XSSFRow row = null;
		
		/** Cell 생성 */
		XSSFCell cell = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4));
		
		/** 타이틀 */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue(itemInfo.get("item_nm").toString());
		
		/** header */
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("번호");
		
		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("부문");
		
		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("지표명");
		
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("구축형태");
		
		cell = row.createCell(4);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("가중치");
		
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
	
	
}
