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
		
		/** 사용자 행정구역 정보 */
		List<Map<String, String>> districtList = this.DAO.getDistrictInfo(params);
		
		List<Map<String, Object>> insertValueList = new ArrayList<Map<String, Object>>();
		
		/** 사용자 행정구역 정보와 지표값 데이터 세팅 */
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
		
		/** 사용자 행정구역 정보 */
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
		 * insertValueList == null 인 경우는
		 * 값이 바뀐게 없다는 의미
		 * 따라서, 제목 설명 단위 등의 정보만 바꿨다는 의미
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
		 * 순서에 맞게 호출 해야 함
		 * deleteIndicatorData는 deleteIndicatorList 에서 indi_id가 사용자와 일치 하는지 확인 후 삭제 한다.
		 * */
		
		this.DAO.deleteIndicatorData(params);
		
		this.DAO.deleteIndicatorList(params);
	}
}
