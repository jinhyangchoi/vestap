package vestap.adm.management.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import vestap.sys.cmm.util.CreateExcelStyleComponent;
import vestap.sys.cmm.util.FileUploadComponent;

@Service("managementDataService")
@Transactional
public class ManagementDataServiceImpl extends EgovAbstractServiceImpl implements ManagementDataService{

	@Resource(name = "managementDataDAO")
	private ManagementDataDAO DAO;
	
	@Resource(name = "createExcelStyleComponent")
	private CreateExcelStyleComponent excelStyleComp;
	
	@Resource(name = "fileUploadComponent")
	private FileUploadComponent fileUploadComp;
	
	
//	데이터 관리 
	@Override
	public List<Map<String, String>> getDataListView(Map<String, Object> params) throws Exception {
		return this.DAO.getDataListView(params);
	}
	
	@Override
	public int getDataListViewTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getDataListViewTotCnt(params);
	}
	
	@Override
	public Map<String, String> getDataIndicatorList(Map<String, Object> params) throws Exception {
		return this.DAO.getDataIndicatorList(params);
	}
	
	@Override
	public List<Map<String, String>> getDataIndicatorDataInfo(Map<String, Object> params) throws Exception {
		return this.DAO.getDataIndicatorDataInfo(params);
	}
	
	
	@Override
	@Transactional
	public String updateDataIndicatorFile(Map<String, Object> params, MultipartFile uploadFile) throws Exception {
		
		if(params.get("indicator-space").equals("SPA02")) {
			
			params.put("tableCategory", "sgg");
			
		} else if(params.get("indicator-space").equals("SPA01")) {
			
			params.put("tableCategory", "emd");
			
		} else if(params.get("indicator-space").equals("SPA03")){
			
			params.put("tableCategory", "sd");
		}
		
		
		List<Map<String, String>> districtList = this.DAO.getDataDistrictInfo(params);
		
		Map<String, Object> excelData = new HashMap<String, Object>();
		
		for(int i = 0; i < districtList.size(); i++) {
			
			excelData.put(districtList.get(i).get("district_cd"), null);
		}
		
		String result = null;
		
		String savePath = "C:/vestap/file/indicator/upload/";
		
		String fileName = this.fileUploadComp.indicatorFileUpload(uploadFile, savePath);
		
		File file = new File(savePath);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		file = new File(savePath, fileName);
		
		FileInputStream fis = new FileInputStream(file);
		
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		XSSFRow row = null;
		
		XSSFCell cell = null;
		
		int totalRows = sheet.getPhysicalNumberOfRows();
		
		List<Map<String, Object>> insertValueList = new ArrayList<Map<String, Object>>();
		
		for(int i = 0; i < totalRows; i++) {
			
			row = sheet.getRow(i);
			
			/** i > 1: 3번째 row부터 지표값이 있으므로 그 이전에는 무시한다. */
			if(row != null && i > 1) {
				
				int totalCells = row.getPhysicalNumberOfCells();
				
				String key = null;
				
				for(int j = 0; j < totalCells; j++) {
					
					cell = row.getCell(j);
					
					if(cell != null) {
						
						if(j == 0) {
							
							if(cell.getCellTypeEnum() == CellType.STRING) {
								
								key = cell.getStringCellValue();
								
								if(!excelData.containsKey(key)) {
									
									//행정구역 코드가 일치하지 않아 입력이 불가능 합니다.
									result = "code01";
									break;
								}
								
							} else {
								//행정구역 코드가 변경 되어 입력이 불가능 합니다.
								result = "code02";
								break;
							}
							
						} else if(j == 2) {
							
							if(cell.getCellTypeEnum() == CellType.NUMERIC) {
								
								if(excelData.containsKey(key)) {
									
									excelData.put(key, cell.getNumericCellValue());
									
									Map<String, Object> map = new HashMap<String, Object>();
									
									map.put("district_cd", key);
									map.put("district_val", cell.getNumericCellValue());
									
									insertValueList.add(map);
									
								} else {
									//행정구역 코드가 없어 입력이 불가능 합니다.
									result = "code03";
									break;
								}
								
							} else {
								//지표값에는 숫자만 입력 가능합니다.
								result = "code04";
								break;
							}
							
						}
					} else {
						//템플릿 파일이 변경 되어 입력 할 수 없습니다.
						result = "code05";
						break;
					}
				}
				
				if(result != null) {
					break;
				}
			}
		}
		
		if(excelData.containsValue(null)) {
			
			if(result == null) {
				result = "누락 된 지표값이 있습니다.";
			}
		}
		
		if(result == null) {
			
			params.put("valueList", insertValueList);
			
			this.DAO.updateDataIndicatorList(params);
			
			this.DAO.insertDataIndicatorData(params);
		}
		
		workbook.close();
		
		file.delete();
		
		return result;
	}
	
	@Override
	public String dataIndicatorDownload(Map<String, Object> params) throws Exception {
		
		
		
		
		List<Map<String, String>> districtList = this.DAO.getDataDistrictInfo(params);
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";
		if(districtList.size() > 0) {
			
			/** workbook 생성 */
			XSSFWorkbook workbook = new XSSFWorkbook();
			
			/** 시트 생성 */
			XSSFSheet sheet = workbook.createSheet("지표생성");
			
			
			/** Row생성 */
			XSSFRow row = null;
			
			/** Cell 생성 */
			XSSFCell cell = null;
			
			int rowCnt = 0;
			
			XSSFCellStyle style_info = this.excelStyleComp.indicator_info(workbook);
			XSSFCellStyle style_title = this.excelStyleComp.indicator_title(workbook);
			XSSFCellStyle style_value = this.excelStyleComp.indicator_value(workbook);
			XSSFCellStyle style_valueTop = this.excelStyleComp.indicator_value_top(workbook);
			XSSFCellStyle style_valueBottom = this.excelStyleComp.indicator_value_bottom(workbook);
			
			/** 최상단 정보 문구 생성*/
			row = sheet.createRow(rowCnt);
			rowCnt++;
			cell = row.createCell(0);
			cell.setCellStyle(style_info);
			cell.setCellValue("이 파일은 지표 생성 시 행정구역 별 지표 값을 입력 하여 업로드 하는 파일이므로 이 파일을 변경 할 경우 정상적으로 입력 되지 않습니다.");
			
			/** 타이틀 생성 */
			row = sheet.createRow(rowCnt);
			rowCnt++;
			cell = row.createCell(0);
			cell.setCellStyle(style_title);
			cell.setCellValue("행정구역 코드");
			
			cell = row.createCell(1);
			cell.setCellStyle(style_title);
			cell.setCellValue("행정구역 명칭");
			
			cell = row.createCell(2);
			cell.setCellStyle(style_title);
			cell.setCellValue("지표값");
			
			int col_1 = 0;
			int col_2 = 0;
			
			for(int i = 0; i < districtList.size(); i++) {
				
				row = sheet.createRow(rowCnt);
				rowCnt++;
				
				cell = row.createCell(0);
				cell.setCellValue(districtList.get(i).get("district_cd"));
				
				if(col_1 < districtList.get(i).get("district_cd").length()) {
					col_1 = districtList.get(i).get("district_cd").length();
				}
				
				cell = row.createCell(1);
				cell.setCellValue(districtList.get(i).get("district_nm"));
				
				if(col_2 < districtList.get(i).get("district_nm").length()) {
					col_2 = districtList.get(i).get("district_nm").length();
				}
				
				cell = row.createCell(2);
				
				if(i == 0) {
					
					cell.setCellStyle(style_valueTop);
					
				} else if(i == (districtList.size() - 1)) {
					
					cell.setCellStyle(style_valueBottom);
					
				} else {
					
					cell.setCellStyle(style_value);
				}
					
				cell.setCellValue("");
			}
			
			sheet.setColumnWidth(0, col_1 * 256* 5);
			sheet.setColumnWidth(1, col_2 * 256 * 5);
			sheet.setColumnWidth(2, 5 * 256 * 5);
			
			File file = new File(filePath);
			
			if(!file.isDirectory()) {
				file.mkdirs();
			}
			
			file = new File(filePath, fileName);
			
			FileOutputStream fileOs = new FileOutputStream(file);
			
			workbook.write(fileOs);
			fileOs.close();
			workbook.close();
		}
		
		return fileName;
	}
	
	//메타관리
	
	@Override
	public List<Map<String, String>> getMetaListView(Map<String, Object> params) throws Exception {
		return this.DAO.getMetaListView(params);
	}
	
	@Override
	public int getMetaListViewTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getMetaListViewTotCnt(params);
	}
	
	@Override
	public Map<String, String> getMetaIndicatorList(Map<String, Object> params) throws Exception {
		return this.DAO.getMetaIndicatorList(params);
	}
	
	@Override
	public void updateMetaIndicatorList(Map<String, Object> params) throws Exception {
		 this.DAO.updateMetaIndicatorList(params);
	}
}
