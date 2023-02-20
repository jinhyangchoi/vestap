package vestap.sys.climate.estimation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.adm.climate.estimation.AdmEstimationService;
import vestap.sys.cmm.util.CreateExcelStyleComponent;
import vestap.sys.cmm.util.FileDownloadComponent;
import vestap.sys.sec.handler.VestapUserDetails;
import vestap.sys.sec.user.UserAccessService;
import vestap.sys.sec.user.UserAccessVO;

/**
 * 기후변화 취약성 > 취약성평가
 * --------------------------------------------------
 * 		수정일			수정자			수정내용
 * --------------------------------------------------
 * 	2018.10.31			vestap개발		최초 작성
 * --------------------------------------------------
 * @author vestap 개발
 * @since 2018.10.31
 *
 */

@Service("estimationService")
public class EstimationServiceImpl extends EgovAbstractServiceImpl implements EstimationService {

	@Resource(name = "userAccessService")
	private UserAccessService userAccessService;
	
	@Resource(name="estimationDAO")
	private EstimationDAO estimationDAO;

	@Resource(name = "createExcelStyleComponent")
	private CreateExcelStyleComponent excelStyleComp;

	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent fileDownloadComp;

	@Resource(name = "admEstimationService")
	private AdmEstimationService admEstimationService;
	
	@Override
	public List<EgovMap> selectOptionlist(EgovMap map) {
		return this.estimationDAO.selectOptionlist(map);
	}

	@Override
	public List<EgovMap> selectItemlist(EgovMap map) {
		return this.estimationDAO.selectItemlist(map);
	}

	@Override
	public List<EgovMap> selectSidoList() {
		return this.estimationDAO.selectSidoList();
	}

	@Override
	public List<EgovMap> selectSigungulist(EgovMap map) {
		return this.estimationDAO.selectSigungulist(map);
	}

	@Override
	public EgovMap selectEstimationSetting(EgovMap map) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		VestapUserDetails user = (VestapUserDetails)authentication.getDetails();
		String userAuth = user.getUser_auth();
		
		/*초기 세팅*/
		String fieldCode = "FC001";
		String itemCode = "TL000001";
		String modelCode = "CM001";
		String sectionCode = "RC002";
		String yearCode = "YC003";
		String districtCode = user.getUser_dist();

		String type = (String) map.get("type");
		
		if(!type.equals("WHOLE")){
			if(userAuth.equals("A")) districtCode = "11"; //관리자는 시도코드 고정
		}
		
		EgovMap resultMap = new EgovMap();
		
		UserAccessVO storedUser = this.userAccessService.getUserInfo(user.getUser_id());
		if(storedUser.getUser_item()!=null){
			//취약성이력이 있으면 매칭할 수 있도록 값 세팅
			fieldCode = storedUser.getUser_fld();
			if(!type.equals("CUSTOM")){
				itemCode = storedUser.getUser_item();
			}
			modelCode = storedUser.getUser_scr();
			sectionCode = storedUser.getUser_rcp();
			yearCode = storedUser.getUser_year();
			districtCode = storedUser.getUser_district();
		}
		
		/* 분야 세팅 */
		map.put("codeGroup", "FIELD");
		List<EgovMap> fieldList = this.estimationDAO.selectOptionlist(map);
		resultMap.put("fieldList", fieldList);
		resultMap.put("setField", fieldCode);
		
		/* 분야별 항목 세팅 */
		map.put("field", fieldCode);
		List<EgovMap> itemList = this.estimationDAO.selectItemlist(map);
		resultMap.put("itemList", itemList);
		resultMap.put("setItem", itemCode);
		
		/* RCP 세팅 */
		map.put("item", itemCode);
		List<EgovMap> sectionList = this.estimationDAO.selectClimateOption(map);
		resultMap.put("sectionList", sectionList);
		resultMap.put("setSection", sectionCode);
		
		/* 기후모델 세팅 */
		map.put("section", sectionCode);
		List<EgovMap> modelList = this.estimationDAO.selectClimateOption(map);
		resultMap.put("modelList", modelList);
		resultMap.put("setModel", modelCode);
		
		/* 연대 세팅 */
		map.put("model", modelCode);
		List<EgovMap> yearList = this.estimationDAO.selectClimateOption(map);
		resultMap.put("yearList", yearList);
		resultMap.put("setYear", yearCode);
		
		/* 행정구역 시도 세팅 */
		List<EgovMap> sidoList = this.estimationDAO.selectSidoList();
		resultMap.put("sidoList", sidoList);
		
		String sidoCode = districtCode.substring(0, 2);
		
		resultMap.put("setSido", sidoCode);
		
		map.put("sidoCode", sidoCode);
		List<EgovMap> sigunguList = this.estimationDAO.selectSigungulist(map);
		resultMap.put("sigunguList", sigunguList);
		resultMap.put("setSigungu", districtCode);
		
		resultMap.put("author", userAuth);
		
		/*상세보기 초기 세팅*/
		map.put("codeGroup", "SECTOR");
		List<EgovMap> sectorList = this.estimationDAO.selectOptionlist(map);
		resultMap.put("sectorList", sectorList);
		
		return resultMap;
	}
	
	@Override
	public List<EgovMap> selectEstimationResultEmdTotal(EgovMap map) {
		map = addAdjModel(map);
		return this.estimationDAO.selectEstimationResultEmdTotal(map);
	}

	@Override
	public List<EgovMap> selectEstimationResultSggTotal(EgovMap map) {
		map = addAdjModel(map);
		return this.estimationDAO.selectEstimationResultSggTotal(map);
	}
	
	@Override
	public List<EgovMap> selectEstimationIndiWeight(EgovMap map) {
		map = addAdjModel(map);
		return this.estimationDAO.selectEstimationIndiWeight(map);
	}
	
	@Override
	public List<EgovMap> selectEstimationEmdIndiWeight(EgovMap map){
		map = addAdjModel(map);
		return this.estimationDAO.selectEstimationEmdIndiWeight(map);
	}

	@Override
	public List<EgovMap> selectEstimationResultSdTotal(EgovMap map) {
		map = addAdjModel(map);
		return this.estimationDAO.selectEstimationResultSdTotal(map);
	}
	
	@Override
	public List<EgovMap> selectEstimationResultDetail(EgovMap map) {
		map = addAdjModel(map);
		return this.estimationDAO.selectEstimationResultDetail(map);
	}
	

	@Override
	public EgovMap selectEstimationCalcFormula(EgovMap map) {
		return this.estimationDAO.selectEstimationCalcFormula(map);
	}

	@Override
	public List<EgovMap> selectEstimationResultEmdData(EgovMap map) {
		map = addAdjModel(map);
		return this.estimationDAO.selectEstimationResultEmdData(map);
	}
	

	@Override
	public List<EgovMap> selectEstimationResultSggData(EgovMap map) {
		map = addAdjModel(map);
		return this.estimationDAO.selectEstimationResultSggData(map);
	}
	
	@Override
	public List<EgovMap> selectEstimationResultSdData(EgovMap map) {
		map = addAdjModel(map);
		return this.estimationDAO.selectEstimationResultSdData(map);
	}

	@Override
	public EgovMap selectEstimationIndiInfo(EgovMap map) {
		return this.estimationDAO.selectEstimationIndiInfo(map);
	}

	@Override
	public void insertEstimationLog(EgovMap map) {
		Object exe = map.get("exe");
		if(exe!=null && exe.equals("do")) {
			return;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) authentication.getPrincipal();
		map.put("userId",userId);
		
		String sido = (String) map.get("sido");
		map.remove("sido");

		String sigungu = (String) map.get("sigungu");
		map.remove("sigungu");
		
		String districtCd = sido.length()>sigungu.length()?sido:sigungu;
		map.put("districtCd",districtCd);
		
		this.estimationDAO.insertEstimationLog(map);//사용자가 설정한 취약성평가 기록 저장
		this.estimationDAO.updateUserInfo(map);//사용자가 설정한 취약성평가 옵션 업데이트
	}

	@Override
	public List<EgovMap> selectIndicatorlist(EgovMap map) {
		return this.estimationDAO.selectIndicatorlist(map);
	}

	@Override
	public List<EgovMap> selectModelList(EgovMap map) {
		return this.estimationDAO.selectClimateOption(map);
	}
	
	@Override
	public List<EgovMap> selectSectionList(EgovMap map) {
		return this.estimationDAO.selectClimateOption(map);
	}
	
	@Override
	public List<EgovMap> selectYearList(EgovMap map) {
		return this.estimationDAO.selectClimateOption(map);
	}
	
	@Override
	public List<EgovMap> selectClimateOption(EgovMap map) {
		return this.estimationDAO.selectClimateOption(map);
	}
	
	@Override
	public void excelDownload(EgovMap map, HttpServletResponse response) throws Exception {
		map = addAdjModel(map);
		String type = map.get("type").toString().toUpperCase();
		String auth = null;
		String sheetName = null;
		String excelFileName = null;
		String sidoNm = map.get("sidoNm").toString();
		String sigunguNm = map.get("sigunguNm").toString();
		String item = map.get("item").toString();
		String itemNm = map.get("itemNm").toString();
		String fieldNm = map.get("fieldNm").toString();
		String modelNm = map.get("modelNm").toString();
		String sectionNm = map.get("sectionNm").toString();
		String yearNm = map.get("yearNm").toString();
		String indiNm = map.get("indiNm").toString();
		EgovMap info = null;
		
		if(map.get("sigungu")!=null) {//일반 취약성평가
			if(!((String)map.get("sido")).equals("")) {
				if(!((String)map.get("sigungu")).equals("")) {
					//시군구
					auth = "B";
				}else {
					//시도
					auth = "W";
				}
			}else {
				auth = "A";
			}
		}
		
		if(map.get("resultType")!=null) {//전국 취약성평가
			if(map.get("resultType").toString().equals("SD")) {
				auth = "A";
			}else {
				auth = "W";
			}
		}
		if(item.equals("TL000054")) {//신규 취약성항목(공통)
			auth = "W";
		}

		System.out.println("엑셀 다운로드  : "+ auth);
		map.put("auth", auth);
		
		List<EgovMap> list = null;
		
		switch (type) {
		case "TOTAL":
			if(!item.equals("TL000054")) {
				if(auth.equals("B")) {
					list = this.estimationDAO.selectEstimationResultEmdTotal(map);
				}else if(auth.equals("W")) {
					list = this.estimationDAO.selectEstimationResultSggTotal(map);
				}else {
				}
			}else {
				list = this.estimationDAO.selectWholeEstimation(map);
			}
			sheetName = "종합지수";
			break;
		case "INDI":
			if(!item.equals("TL000054")) {
				if(auth.equals("B")) {
					list = this.estimationDAO.selectEstimationResultEmdData(map);
				}else if(auth.equals("W")) {
					list = this.estimationDAO.selectEstimationResultSggData(map);
				}else {
					list = this.estimationDAO.selectEstimationResultSdData(map);
				}
			}else {
				list = this.estimationDAO.selectEstimationResultWholeData(map);
			}
			info = this.estimationDAO.selectEstimationIndiInfo(map); //지표정보
			sheetName = "기초자료 정보";
			
			break;
		}
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";	//서버에 저장되는 파일네임
		
		/** workbook 생성 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** 시트 생성 */
		XSSFSheet sheet = workbook.createSheet(sheetName);
		
		/** Row생성 */
		XSSFRow row = null;
		
		/** Cell 생성 */
		XSSFCell cell = null;
		
		switch (type) {
		case "TOTAL":
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 5));
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 5));
			
			/** 타이틀 */
			row = sheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
			cell.setCellValue(sidoNm + " " + sigunguNm + " 의 " + itemNm + " 평가 도출 내역");
			
			/** 평가 지역 */
			row = sheet.createRow(2);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
			cell.setCellValue("평가 지역");
			
			cell = row.createCell(1);
			cell.setCellValue(sidoNm + " " + sigunguNm);
			
			/** 평가 분야 */
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
			cell.setCellValue("평가 리스크");
			
			cell = row.createCell(4);
			cell.setCellValue(fieldNm);
			
			/** 평가 항목 */
			row = sheet.createRow(3);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
			cell.setCellValue("평가 항목");
			
			cell = row.createCell(1);
			cell.setCellValue(itemNm);
			
			/** 적용 기후 모델 */
			row = sheet.createRow(4);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
			cell.setCellValue("적용 기후 모델");
			
			cell = row.createCell(1);
			cell.setCellValue(modelNm + " " + sectionNm + " " + yearNm);
			
			/** header */
			row = sheet.createRow(5);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("순위");
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("행정구역 명칭");
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("취약성 종합 지수");
			
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("기후 노출 부문");
			
			cell = row.createCell(4);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("기후 변화 민감도 부문");
			
			cell = row.createCell(5);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("적응 능력 부문");
			
			int rowCnt = 6;
			
			for(int i = 0; i < list.size(); i++) {
				
				row = sheet.createRow(rowCnt);
				
				cell = row.createCell(0);
				cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
				cell.setCellValue(String.valueOf(list.get(i).get("no")));
				
				cell = row.createCell(1);
				cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
				if(auth.equals("B")) {
					cell.setCellValue(String.valueOf(list.get(i).get("emdNm")));
				}else if(auth.equals("W")) {
					cell.setCellValue(String.valueOf(list.get(i).get("sggNm")));
				}else {
					cell.setCellValue(String.valueOf(list.get(i).get("sdNm")));
				}
				
				cell = row.createCell(2);
				cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
				cell.setCellValue(String.valueOf(list.get(i).get("estiValue")));
				
				cell = row.createCell(3);
				cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
				cell.setCellValue(String.valueOf(list.get(i).get("climValue")));
				
				cell = row.createCell(4);
				cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
				cell.setCellValue(String.valueOf(list.get(i).get("sensValue")));
				
				cell = row.createCell(5);
				cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
				cell.setCellValue(String.valueOf(list.get(i).get("adapValue")));
				
				rowCnt++;
			}
			
			sheet.setColumnWidth(0, 4 * 256 * 5);
			sheet.setColumnWidth(1, 4 * 256 * 5);
			sheet.setColumnWidth(2, 4 * 256 * 5);
			sheet.setColumnWidth(3, 4 * 256 * 5);
			sheet.setColumnWidth(4, 4 * 256 * 5);
			sheet.setColumnWidth(5, 4 * 256 * 5);
			
			excelFileName=itemNm;
			break;
			case "INDI":
				sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4));

				row = sheet.createRow(0);
				cell = row.createCell(0);
				cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
				cell.setCellValue(info.get("indiNm").toString() + " (단위: " + info.get("indiUnit") + ")");
				
				/** header */
				row = sheet.createRow(2);
				cell = row.createCell(0);
				cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
				cell.setCellValue("번호");
				
				cell = row.createCell(1);
				cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
				cell.setCellValue("행정구역 코드");
				
				cell = row.createCell(2);
				cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
				cell.setCellValue("행정구역 명칭");
				
				cell = row.createCell(3);
				cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
				cell.setCellValue("지표값");
				
				cell = row.createCell(4);
				cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
				cell.setCellValue("연도");
				
				rowCnt = 3;
				
				for(int i = 0; i < list.size(); i++) {
					
					row = sheet.createRow(rowCnt);
					
					cell = row.createCell(0);
					cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
					cell.setCellValue(String.valueOf(list.get(i).get("no")));
					
					cell = row.createCell(1);
					cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
					if(auth.equals("B")) {
						cell.setCellValue(String.valueOf(list.get(i).get("emdCd")));
					}else if(auth.equals("W")) {
						cell.setCellValue(String.valueOf(list.get(i).get("sggCd")));
					}else {
						cell.setCellValue(String.valueOf(list.get(i).get("sdCd")));
					}
					cell = row.createCell(2);
					cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
					if(auth.equals("B")) {
						cell.setCellValue(String.valueOf(list.get(i).get("emdNm")));
					}else if(auth.equals("W")) {
						cell.setCellValue(String.valueOf(list.get(i).get("sggNm")));
					}else {
						cell.setCellValue(String.valueOf(list.get(i).get("sdNm")));
					}
					cell = row.createCell(3);
					cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
					cell.setCellValue(String.valueOf(list.get(i).get("indiVal")));
					
					cell = row.createCell(4);
					cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
					cell.setCellValue(String.valueOf(list.get(i).get("indiYear")));
					
					rowCnt++;
				}
				
				sheet.setColumnWidth(0, 4 * 256 * 5);
				sheet.setColumnWidth(1, 4 * 256 * 5);
				sheet.setColumnWidth(2, 4 * 256 * 5);
				sheet.setColumnWidth(3, 4 * 256 * 5);
				excelFileName = indiNm;
			break;
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
		
		this.fileDownloadComp.indicatorInputFormDownload(response, fileName, excelFileName.replaceAll("\\s", "_")+".xlsx");
		
	}

	@Override
	public void subEstimation(EgovMap map) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		VestapUserDetails user = (VestapUserDetails)authentication.getDetails();
		map.put("userId", user.getUser_id());
		this.estimationDAO.subEstimation(map);
	}

	@Override
	public EgovMap estimationReport(EgovMap map) {
		
		EgovMap resultMap = new EgovMap();
		
		/*상세보기 초기 세팅*/
		EgovMap info = this.estimationDAO.selectEstimationInfo(map);
		if(map.get("item").toString().equals("TL000054")) {
			info.remove("sidoNm");
			info.remove("sigunguNm");
			info.put("sidoNm", "전국");
		}
		
		resultMap.put("info", info);
		
		/*상세보기 초기 세팅*/
		map.put("codeGroup", "SECTOR");
		List<EgovMap> sectorList = this.estimationDAO.selectOptionlist(map);
		map.remove("codeGroup");
		resultMap.put("sectorList", sectorList);
		
		return resultMap;
	}

	@Override
	public EgovMap selectEmdIndiData(EgovMap map) {
		EgovMap resultMap = new EgovMap();
		map = addAdjModel(map);
		List<EgovMap> header = this.estimationDAO.selectIndiHeader(map);
		List<EgovMap> body = this.estimationDAO.selectEmdIndiBody(map);
		resultMap.put("header", header);
		resultMap.put("body", body);
		return resultMap;
	}

	@Override
	public EgovMap selectSggIndiData(EgovMap map) {
		EgovMap resultMap = new EgovMap();
		map = addAdjModel(map);
		List<EgovMap> header = this.estimationDAO.selectIndiHeader(map);
		List<EgovMap> body = this.estimationDAO.selectSggIndiBody(map);
		resultMap.put("header", header);
		resultMap.put("body", body);
		return resultMap;
	}

	@Override
	public List<EgovMap> selectWholeEstimation(EgovMap map) {
		return this.estimationDAO.selectWholeEstimation(map);
	}
	
	@Override
	public List<EgovMap> selectWholeIndiWeight(EgovMap map){
		return this.estimationDAO.selectWholeIndiWeight(map);
	}

	@Override
	public List<EgovMap> selectEstimationResultWholeData(EgovMap map) {
		return this.estimationDAO.selectEstimationResultWholeData(map);
	}

	@Override
	public EgovMap selectWholeIndiData(EgovMap map) {
		EgovMap resultMap = new EgovMap();
		List<EgovMap> header = this.estimationDAO.selectIndiHeader(map);
		List<EgovMap> body = this.estimationDAO.selectWholeIndiBody(map);
		resultMap.put("header", header);
		resultMap.put("body", body);
		return resultMap;
	}

	/* 추가된 부분 인천광역시 세트 */
	
	@Override
	public List<EgovMap> selectItemlist() {
		return this.estimationDAO.selectItemlist();
	}

	@Override
	public List<EgovMap> selectCombinelist(EgovMap map) {
		return this.estimationDAO.selectCombinelist(map);
	}

	@Override
	public void resultDownload(EgovMap map){
		
		
/*		List<EgovMap> list = this.estimationDAO.selectEstimationResultEmdTotal(map);*/
		
		EgovMap info = this.estimationDAO.selectEstimationInfo(map); //종합정보
		
		String itemNm = info.get("itemNm").toString();
		String fieldNm = info.get("fieldNm").toString();
		String modelNm = info.get("modelNm").toString();
		String sectionNm = info.get("sectionNm").toString();
		String yearNm = info.get("yearNm").toString();
		String sidoNm = info.get("sidoNm").toString();
		String sigunguNm = info.get("sigunguNm").toString();
		/*
		String excelFileName = null;
		String sheetName = "종합지수";
		
		/** workbook 생성 
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** 시트 생성 
		XSSFSheet sheet = workbook.createSheet(sheetName);
		
		/** Row생성 
		XSSFRow row = null;
		
		/** Cell 생성 
		XSSFCell cell = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 5));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 5));
		
		/** 타이틀 
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue(sidoNm + " " + sigunguNm + " 의 " + itemNm + " 평가 도출 내역");
		
		/** 평가 지역 
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("평가 지역");
		
		cell = row.createCell(1);
		cell.setCellValue(sidoNm + " " + sigunguNm);
		
		/** 평가 분야 
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("평가 리스크");
		
		cell = row.createCell(4);
		cell.setCellValue(fieldNm);
		
		/** 평가 항목 
		row = sheet.createRow(3);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("평가 항목");
		
		cell = row.createCell(1);
		cell.setCellValue(itemNm);
		
		/** 적용 기후 모델 
		row = sheet.createRow(4);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
		cell.setCellValue("적용 기후 모델");
		
		cell = row.createCell(1);
		cell.setCellValue(modelNm + " " + sectionNm + " " + yearNm);
		
		/** header 
		row = sheet.createRow(5);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("순위");
		
		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("행정구역 명칭");
		
		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("취약성 종합 지수");
		
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("기후 노출 부문");
		
		cell = row.createCell(4);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("기후 변화 민감도 부문");
		
		cell = row.createCell(5);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("적응 능력 부문");
		
		int rowCnt = 6;
		
		for(int i = 0; i < list.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("no")));
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("emdNm")));
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("estiValue")));
			
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("climValue")));
			
			cell = row.createCell(4);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("sensValue")));
			
			cell = row.createCell(5);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("adapValue")));
			
			rowCnt++;
		}
		
		sheet.setColumnWidth(0, 4 * 256 * 5);
		sheet.setColumnWidth(1, 4 * 256 * 5);
		sheet.setColumnWidth(2, 4 * 256 * 5);
		sheet.setColumnWidth(3, 4 * 256 * 5);
		sheet.setColumnWidth(4, 4 * 256 * 5);
		sheet.setColumnWidth(5, 4 * 256 * 5);
		
		excelFileName=sidoNm + " " + sigunguNm + " 의 " + itemNm + " 평가 도출 내역";

		fieldNm = fieldNm.replaceAll("/", "_");
		*/
		//String filePath = "C:/vestap/"+modelNm+"/"+sectionNm+"/"+yearNm+"/"+sidoNm+"/"+sigunguNm+"/";
		String filePath = "C:/vestap/"+"사회경제지표"+"/"+sidoNm+"/"+sigunguNm+"/";
		//+fieldNm+"/";
		//+itemNm+"/";
		/*
		String fileName = excelFileName+".xlsx";	//서버에 저장되는 파일네임
		
		filePath = filePath.replaceAll(" ", "_");
		fileName = fileName.replaceAll(" ", "_");
		
		File file = new File(filePath);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		
		file = new File(filePath, fileName);
		
		try {
			FileOutputStream fileOs = new FileOutputStream(file);
		
			workbook.write(fileOs);
			fileOs.close();
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		List<EgovMap> indiList = this.estimationDAO.selectIndicatorId(map);
		for(int i=0;i<indiList.size();i++) {
			String indiId = indiList.get(i).get("indiId").toString();
			String sectorId = indiList.get(i).get("sectorId").toString();
			map.put("indi", indiId);
			map.put("sector", sectorId);
			if(!sectorId.equals("SEC01"))
			this.resultDataDownload(map,filePath);
			map.remove("indi");
			map.remove("sector");
		}
	}

	private void resultDataDownload(EgovMap map, String orgfilePath) {

		String excelFileName = null;
		String sheetName = null;
		
		List<EgovMap> list = this.estimationDAO.selectEstimationResultEmdData(map);
		EgovMap info = this.estimationDAO.selectEstimationIndiInfo(map); //지표정보
		String indiNm = info.get("indiNm").toString();
		sheetName = "기초자료 정보";
		
		
		/** workbook 생성 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** 시트 생성 */
		XSSFSheet sheet = workbook.createSheet(sheetName);
		
		/** Row생성 */
		XSSFRow row = null;
		
		/** Cell 생성 */
		XSSFCell cell = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));

		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue(info.get("indiNm").toString() + " (단위: " + info.get("indiUnit") + ")");
		
		/** header */
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("번호");
		
		cell = row.createCell(1);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("행정구역 명칭");
		
		cell = row.createCell(2);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("지표값");
		
		cell = row.createCell(3);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("연도");
		
		int rowCnt = 3;
		
		for(int i = 0; i < list.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("no")));
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("emdNm")));
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("indiVal")));
			
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("indiYear")));
			
			rowCnt++;
		}
		
		sheet.setColumnWidth(0, 4 * 256 * 5);
		sheet.setColumnWidth(1, 4 * 256 * 5);
		sheet.setColumnWidth(2, 4 * 256 * 5);
		sheet.setColumnWidth(3, 4 * 256 * 5);
		excelFileName = indiNm.replaceAll("/", "_");
		
		String filePath = orgfilePath;//+"/"+"기초자료정보/";
		String sector = map.get("sector").toString();
		switch(sector) {
		case "SEC01" :
			filePath += "기후노출/";
			break;
		case "SEC02" :
			filePath += "기후변화 민감도/";
			break;
		case "SEC03" :
			filePath += "적응능력/";
			break;
		}
		String fileName = excelFileName + ".xlsx";	//서버에 저장되는 파일네임
		
		filePath = filePath.replaceAll(" ", "_");
		fileName = fileName.replaceAll(" ", "_");
		
		File file = new File(filePath);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		
		file = new File(filePath, fileName);
		if(!file.exists()) {
			try {
				FileOutputStream fileOs = new FileOutputStream(file);
				workbook.write(fileOs);
				fileOs.close();
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private EgovMap addAdjModel(EgovMap map) {
		if(map.get("section").equals("RC001")) {
			if(map.get("model").equals("CM003")) {
				map.put("adjModel", "CM007");
			}else if(map.get("model").equals("CM007")) {
				map.put("adjModel", "CM003");
			}
		}else {
			if(map.get("model").equals("CM001")) {
				map.put("adjModel", "CM003");
			}else if(map.get("model").equals("CM003")) {
				map.put("adjModel", "CM001");
			}
		}
		return map;
	}

}
