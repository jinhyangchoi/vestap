package vestap.adm.climate.cumulative;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.sys.cmm.util.CreateExcelStyleComponent;
import vestap.sys.cmm.util.FileDownloadComponent;
import vestap.sys.sec.handler.VestapUserDetails;
import vestap.sys.sec.user.UserAccessService;
import vestap.sys.sec.user.UserAccessVO;

@Service("admcumulativeService")
public class AdmCumulativeServiceImpl extends EgovAbstractServiceImpl implements AdmCumulativeService {
	
	@Resource(name = "userAccessService")
	private UserAccessService userAccessService;
	
	@Resource(name = "admcumulativeDAO")
	private AdmCumulativeDAO admcumulativeDAO;
	
	@Resource(name = "createExcelStyleComponent")
	private CreateExcelStyleComponent excelStyleComp;

	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent fileDownloadComp;
	
	@Override
	public List<EgovMap> selectOptionlist(EgovMap map) {
		return this.admcumulativeDAO.selectOptionlist(map);
	}
	
	@Override
	public List<EgovMap> selectItemlist(EgovMap map) {
		return this.admcumulativeDAO.selectItemlist(map);
	}

	@Override
	public List<EgovMap> selectSidoList() {
		return this.admcumulativeDAO.selectSidoList();
	}

	@Override
	public List<EgovMap> selectSigungulist(EgovMap map) {
		return this.admcumulativeDAO.selectSigungulist(map);
	}

	@Override
	public List<EgovMap> selectCumulativeList(EgovMap map) {
		return this.admcumulativeDAO.selectCumulativeList(map);
	}

	@Override
	public List<EgovMap> selectCumulativeTotal(EgovMap map) {
		return this.admcumulativeDAO.selectCumulativeTotal(map);
	}

	@Override
	public List<EgovMap> selectCumulativeFindIndiNm(EgovMap map) {
		return this.admcumulativeDAO.selectCumulativeFindIndiNm(map);
	}
	
	@Override
	public List<EgovMap> selectCumulativeMetaInfo(EgovMap map) {
		return this.admcumulativeDAO.selectCumulativeMetaInfo(map);
	}

	@Override
	public List<EgovMap> selectCumulativeRelation(EgovMap map) {
		return this.admcumulativeDAO.selectCumulativeRelation(map);
	}

	@Override
	public List<EgovMap> selectCumulativeComment(EgovMap map) {
		return this.admcumulativeDAO.selectCumulativeComment(map);
	}
	
	@Override
	public EgovMap selectCumulativeSetting(EgovMap map) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		VestapUserDetails user = (VestapUserDetails)authentication.getDetails();
		String userDist = user.getUser_dist();
		String userAuth = user.getUser_auth();
		String fieldCode = "FC001";
		String itemCode = "EM000001";
		String type = (String) map.get("type");
		
		EgovMap resultMap = new EgovMap();
		
		UserAccessVO storedUser = this.userAccessService.getUserInfo(user.getUser_id());
		
		/* 분야 세팅 */
		map.put("codeGroup", "FIELD");
		List<EgovMap> fieldList = this.admcumulativeDAO.selectOptionlist(map);
		resultMap.put("fieldList", fieldList);
		resultMap.put("setField", fieldCode);
		
		/* 분야별 항목 세팅 */
		map.put("field", fieldCode);
		List<EgovMap> itemList = this.admcumulativeDAO.selectItemlist(map);
		resultMap.put("itemList", itemList);
		resultMap.put("setItem", itemCode);
			
		
		/* 연대 세팅 */
		map.put("codeGroup", "SCEN_YEAR");
		List<EgovMap> yearList = this.admcumulativeDAO.selectOptionlist(map);
		resultMap.put("yearList", yearList);
		
		/* 행정구역 시도 세팅 */
		List<EgovMap> sidoList = this.admcumulativeDAO.selectSidoList();
		resultMap.put("sidoList", sidoList);
		
		String sidoCode = userDist.substring(0, 2);
		if(userAuth.equals("A")) sidoCode = "00"; //관리자는 시도코드 고정
		//기초(시군구) 사용자
		map.put("sidoCode", sidoCode);
		resultMap.put("setSido", sidoCode);
		List<EgovMap> sigunguList = this.admcumulativeDAO.selectSigungulist(map);
		resultMap.put("sigunguList", sigunguList);
		
		resultMap.put("author", userAuth);
		
		/*상세보기 초기 세팅*/
		map.put("codeGroup", "WEIGHT_GROUP");
		List<EgovMap> weigGroupList = this.admcumulativeDAO.selectOptionlist(map);
		resultMap.put("weigGroupList", weigGroupList);
		
		return resultMap;
	}


	@Override
	public void downloadCumulative(List<EgovMap> list, List<EgovMap> findNm,  String sido, String sigungu, EgovMap map, HttpServletResponse response) throws Exception {
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";	//서버에 저장되는 파일네임
		
		/** workbook 생성 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** 시트 생성 */
		XSSFSheet sheet = workbook.createSheet("누적현황 데이터");
		
		/** Row생성 */
		XSSFRow row = null;
		
		/** Cell 생성 */
		XSSFCell cell = null;
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 2));
		
		/** 타이틀 */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
		cell.setCellValue(sido + " " + sigungu + " 의 누적현황 데이터");
		
		/** header */
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue("연도 ");

		int cellCnt = 1;
		for(int i = 0; i < findNm.size(); i++) {
		cell = row.createCell(cellCnt);
		cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
		cell.setCellValue(String.valueOf(findNm.get(i).get("indiNm"))+"("+String.valueOf(findNm.get(i).get("indiUnit"))+")");

		cellCnt++;
		}
		
		int rowCnt = 3;
		
		for(int i = 0; i < list.size(); i++) {
			
			row = sheet.createRow(rowCnt);
			
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("indiYear")));
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("indiFirst")));
			if(findNm.size() > 1) {
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationValue(workbook));
			cell.setCellValue(String.valueOf(list.get(i).get("indiSecond")));
			}
			rowCnt++;
		}
		
		sheet.setColumnWidth(0, 4 * 256 * 5);
		sheet.setColumnWidth(1, 4 * 256 * 8);
		sheet.setColumnWidth(2, 4 * 256 * 8);
		
		File file = new File(filePath);
		
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		file = new File(filePath, fileName);
		
		FileOutputStream fileOs = new FileOutputStream(file);
		
		workbook.write(fileOs);
		fileOs.close();
		workbook.close();
		
		this.fileDownloadComp.indicatorInputFormDownload(response, fileName, "누적현황_데이터.xlsx");
	}

}
