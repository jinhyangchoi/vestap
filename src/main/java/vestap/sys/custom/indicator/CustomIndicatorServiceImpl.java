package vestap.sys.custom.indicator;

import java.io.*;
import java.util.*;
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
import vestap.sys.sec.handler.VestapUserDetails;
import vestap.sys.sec.user.UserAccessVO;

@Service("customIndicatorService")
@Transactional
public class CustomIndicatorServiceImpl extends EgovAbstractServiceImpl implements CustomIndicatorService {
	
	@Resource(name = "customIndicatorDAO")
	private CustomIndicatorDAO DAO;
	
	@Resource(name = "createExcelStyleComponent")
	private CreateExcelStyleComponent excelStyleComp;
	
	@Resource(name = "fileUploadComponent")
	private FileUploadComponent fileUploadComp;
	
	@Override
	public UserAccessVO getUserInfo(String param) throws Exception {
		return this.DAO.getUserInfo(param);
	}
	
	@Override
	public List<Map<String, String>> getDistrictInfo(Map<String, Object> params) throws Exception {
		return this.DAO.getDistrictInfo(params);
	}

	@Override
	public List<Map<String, String>> getFieldInfo() throws Exception {
		return this.DAO.getFieldInfo();
	}
	
	@Override
	public int isIndicatorName(Map<String, Object> params) throws Exception {
		return this.DAO.isIndicatorName(params);
	}

	@Override
	public List<Map<String, String>> getIpccList(Map<String, Object> params) throws Exception {
		return this.DAO.getIpccList(params);
	}

	@Override
	public int getIndicatorSequence() throws Exception {
		return this.DAO.getIndicatorSequence();
	}

	@Override
	@Transactional
	public void insertIndicatorSelf(Map<String, Object> params) throws Exception {
		
		int seq = this.DAO.getIndicatorSequence();
		
		String idx = "IU" + String.format("%06d", seq);
		
		params.put("indi_id", idx);
		
		if(params.get("userAuth").toString().equals("W")) {
			params.put("tableCategory", "sgg");
		} else if(params.get("userAuth").toString().equals("B")) {
			params.put("tableCategory", "emd");
		}
		
		/** ????????? ???????????? ?????? */
		List<Map<String, String>> districtList = this.DAO.getDistrictInfo(params);
		
		List<Map<String, Object>> insertValueList = new ArrayList<Map<String, Object>>();
		
		/** ????????? ???????????? ????????? ????????? ????????? ?????? */
		for(int i = 0; i < districtList.size(); i++) {
			
			String key = districtList.get(i).get("district_cd");
			
			if(params.containsKey(key)) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("district_cd", key);
				map.put("district_val", params.get(key));
				
				insertValueList.add(map);
				
			}
		}
		
		params.put("valueList", insertValueList);
		
		this.DAO.insertIndicatorList(params);
		
		this.DAO.insertIndicatorData(params);
	}

	@Override
	public List<Map<String, String>> getIndicatorListView(Map<String, Object> params) throws Exception {
		return this.DAO.getIndicatorListView(params);
	}
	
	@Override
	public int getIndicatorListViewTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getIndicatorListViewTotCnt(params);
	}

	@Override
	public String getReferenceName(String param) throws Exception {
		return this.DAO.getReferenceName(param);
	}
	
	@Override
	public Map<String, String> getUserIndicatorList(Map<String, Object> params) throws Exception {
		return this.DAO.getUserIndicatorList(params);
	}

	@Override
	public Map<String, String> getSytmIndicatorList(Map<String, Object> params) throws Exception {
		return this.DAO.getSytmIndicatorList(params);
	}

	@Override
	public Map<String, String> getScenIndicatorList(Map<String, Object> params) throws Exception {
		return this.DAO.getScenIndicatorList(params);
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
	public String indicatorDownload(VestapUserDetails details) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("code", details.getUser_dist());
		
		if(details.getUser_auth().equals("W")) {
			
			params.put("tableCategory", "sgg");
			
		} else if(details.getUser_auth().equals("B")) {
			
			params.put("tableCategory", "emd");
		}
		
		List<Map<String, String>> districtList = this.DAO.getDistrictInfo(params);
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";
		System.out.println(filePath);System.out.println("/////////////////////"+fileName);
		if(districtList.size() > 0) {
			
			/** workbook ?????? */
			XSSFWorkbook workbook = new XSSFWorkbook();
			
			/** ?????? ?????? */
			XSSFSheet sheet = workbook.createSheet("????????????");
			
			
			/** Row?????? */
			XSSFRow row = null;
			
			/** Cell ?????? */
			XSSFCell cell = null;
			
			int rowCnt = 0;
			
			XSSFCellStyle style_info = this.excelStyleComp.indicator_info(workbook);
			XSSFCellStyle style_title = this.excelStyleComp.indicator_title(workbook);
			XSSFCellStyle style_value = this.excelStyleComp.indicator_value(workbook);
			XSSFCellStyle style_valueTop = this.excelStyleComp.indicator_value_top(workbook);
			XSSFCellStyle style_valueBottom = this.excelStyleComp.indicator_value_bottom(workbook);
			
			/** ????????? ?????? ?????? ??????*/
			row = sheet.createRow(rowCnt);
			rowCnt++;
			cell = row.createCell(0);
			cell.setCellStyle(style_info);
			cell.setCellValue("??? ????????? ?????? ?????? ??? ???????????? ??? ?????? ?????? ?????? ?????? ????????? ?????? ??????????????? ??? ????????? ?????? ??? ?????? ??????????????? ?????? ?????? ????????????.");
			
			/** ????????? ?????? */
			row = sheet.createRow(rowCnt);
			rowCnt++;
			cell = row.createCell(0);
			cell.setCellStyle(style_title);
			cell.setCellValue("???????????? ??????");
			
			cell = row.createCell(1);
			cell.setCellStyle(style_title);
			cell.setCellValue("???????????? ??????");
			
			cell = row.createCell(2);
			cell.setCellStyle(style_title);
			cell.setCellValue("?????????");
			
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

	@Override
	@Transactional
	public String insertIndicatorFile(Map<String, Object> params, VestapUserDetails details, MultipartFile uploadFile) throws Exception {
		
		if(details.getUser_auth().equals("W")) {
			
			params.put("tableCategory", "sgg");
			
		} else if(details.getUser_auth().equals("B")) {
			
			params.put("tableCategory", "emd");
		}
		
		params.put("code", details.getUser_dist());
		
		List<Map<String, String>> districtList = this.DAO.getDistrictInfo(params);
		
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
			
			/** i > 1: 3?????? row?????? ???????????? ???????????? ??? ???????????? ????????????. */
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
									
									//???????????? ????????? ???????????? ?????? ????????? ????????? ?????????.
									result = "code01";
									break;
								}
								
							} else {
								//???????????? ????????? ?????? ?????? ????????? ????????? ?????????.
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
									//???????????? ????????? ?????? ????????? ????????? ?????????.
									result = "code03";
									break;
								}
								
							} else {
								//??????????????? ????????? ?????? ???????????????.
								result = "code04";
								break;
							}
							
						}
					} else {
						//????????? ????????? ?????? ?????? ?????? ??? ??? ????????????.
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
				result = "?????? ??? ???????????? ????????????.";
			}
		}
		
		if(result == null) {
			
			int seq = this.DAO.getIndicatorSequence();
			
			String idx = "IU" + String.format("%06d", seq);
			
			params.put("indi_id", idx);
			
			params.put("valueList", insertValueList);
			
			this.DAO.insertIndicatorList(params);
			
			this.DAO.insertIndicatorData(params);
		}
		
		workbook.close();
		
		file.delete();
		
		return result;
	}

	@Override
	@Transactional
	public void indicatorUpdate(Map<String, Object> params) throws Exception {
		
		/** ????????? ???????????? ?????? */
		List<Map<String, String>> districtValueList = this.DAO.getIndicatorDataInfo(params);
		List<Map<String, Object>> insertValueList = null;
		
		String code = null;
		String value = null;
		
		for(int i = 0; i < districtValueList.size(); i++) {
			
			code = districtValueList.get(i).get("district_cd");
			value = String.valueOf(districtValueList.get(i).get("indi_val"));
			
			if(params.containsKey(code)) {
				
				if(!params.get(code).toString().equals(value)) {
					
					if(insertValueList == null) {
						insertValueList = new ArrayList<Map<String, Object>>();
					}
					
					Map<String, Object> valueMap = new HashMap<String, Object>();
					
					valueMap.put("district_cd", code);
					valueMap.put("district_val", params.get(code));
					
					insertValueList.add(valueMap);
				}
			}
		}
		
		params.put("valueList", insertValueList);
		
		/**
		 * insertValueList == null ??? ?????????
		 * ?????? ????????? ????????? ??????
		 * ?????????, ?????? ?????? ?????? ?????? ????????? ???????????? ??????
		 */
		if(insertValueList != null) {
			this.DAO.updateIndicatorData(params);
		}
		
		this.DAO.updateIndicatorList(params);
	}

	@Override
	@Transactional
	public void indicatorDelete(Map<String, Object> params) throws Exception {
		
		/** 
		 * ????????? ?????? ?????? ?????? ???
		 * deleteIndicatorData??? deleteIndicatorList ?????? indi_id??? ???????????? ?????? ????????? ?????? ??? ?????? ??????.
		 * */
		
		this.DAO.deleteIndicatorData(params);
		
		this.DAO.deleteIndicatorList(params);
	}
}
