package vestap.adm.climate.estimation;

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
import vestap.sys.climate.estimation.EstimationDAO;
import vestap.sys.cmm.util.CreateExcelStyleComponent;
import vestap.sys.cmm.util.FileDownloadComponent;
import vestap.sys.sec.user.UserAccessService;

@Service("admEstimationService")
public class AdmEstimationServiceImpl  extends EgovAbstractServiceImpl implements AdmEstimationService  {

	@Resource(name = "userAccessService")
	private UserAccessService userAccessService;
	
	@Resource(name="estimationDAO")
	private EstimationDAO estimationDAO;
	
	@Resource(name="admEstimationDAO")
	private AdmEstimationDAO admEstimationDAO;
	
	@Resource(name = "createExcelStyleComponent")
	private CreateExcelStyleComponent excelStyleComp;

	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent fileDownloadComp;
	
	@Resource(name = "admEstimationService")
	private AdmEstimationService admEstimationService;
	
	@Override
	public List<EgovMap> selectWholeSgg(EgovMap map) {
		map = addAdjModel(map);
		return this.admEstimationDAO.selectWholeSgg(map);
	}

	@Override
	public List<EgovMap> selectWholeEmd(EgovMap map) {
		map = addAdjModel(map);
		return this.admEstimationDAO.selectWholeEmd(map);
	}

	@Override
	public List<EgovMap> selectEstimationResultEmdData(EgovMap map) {
		map = addAdjModel(map);
		return this.admEstimationDAO.selectEstimationResultEmdData(map);
	}
	
	@Override
	public List<EgovMap> selectEstimationIndiWeight(EgovMap map){
		map = addAdjModel(map);
		return this.admEstimationDAO.selectEstimationIndiWeight(map);
	}

	@Override
	public List<EgovMap> selectEstimationEmdIndiWeight(EgovMap map){
		map = addAdjModel(map);
		return this.admEstimationDAO.selectEstimationEmdIndiWeight(map);
	}

	@Override
	public List<EgovMap> selectWholeSggExcel(EgovMap map){
		map = addAdjModel(map);
		return this.admEstimationDAO.selectWholeSggExcel(map);
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
	
	@Override
	public void excelDownload(EgovMap map, HttpServletResponse response) throws Exception {
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

		if(map.get("sigungu")!=null) {//?????? ???????????????
			
			System.out.println(map.get("sigungu"));
			
			if(!((String)map.get("sido")).equals("")) {
				
				if(!((String)map.get("sigungu")).equals("")) {
					//?????????
					auth = "B";
				}else {
					//??????
					auth = "W";
				}
			}else {
				auth = "A";
			}
		}

		if(map.get("resultType")!=null) {//?????? ???????????????
			if(map.get("resultType").toString().equals("SD")) {
				auth = "A";
			}else {
				auth = "W";
			}
		}
		if(item.equals("TL000054")) {//?????? ???????????????(??????)
			auth = "W";
		}
		System.out.println("?????? ????????????  : "+ auth);
		map.put("auth", auth);
		
		List<EgovMap> list = null;
		List<EgovMap> excelData = null;
		
		switch (type) {
		case "TOTAL":
			if(!item.equals("TL000054")) {
				if(auth.equals("B")) {
					System.out.println("?????? ??????  : 111");
					list = this.estimationDAO.selectEstimationResultEmdTotal(map);
				}else if(auth.equals("W")) {
					System.out.println("?????? ??????  : 222");
					map = addAdjModel(map);
					list = this.admEstimationDAO.selectWholeSgg(map);
				}else {
					
				}
			}else {
				System.out.println("?????? ??????  : 444");
				list = this.estimationDAO.selectWholeEstimation(map);
			}
			sheetName = "????????????";
			break;
		case "INDI":
			System.out.println("**************************************************");
			System.out.println("**************************************************");
			System.out.println(map.get("resultType"));
			System.out.println("**************************************************");
			System.out.println("**************************************************");

			if(map.get("resultType").equals("SGG")){
				list = this.admEstimationDAO.selectWholeSggExcel(map);
			}else if(map.get("resultType").equals("EMD")){
				list = this.admEstimationDAO.selectWholeEmdExcel(map);
			}

			info = this.estimationDAO.selectEstimationIndiInfo(map); //????????????
			sheetName = "???????????? ??????";
			
			break;
		}
		
		String filePath = "C:/vestap/file/download/temp/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";	//????????? ???????????? ????????????
		
		/** workbook ?????? */
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		/** ?????? ?????? */
		XSSFSheet sheet = workbook.createSheet(sheetName);
		
		/** Row?????? */
		XSSFRow row = null;
		
		/** Cell ?????? */
		XSSFCell cell = null;

		switch (type) {
		case "TOTAL":
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 5));
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 5));
			
			/** ????????? */
			row = sheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
			cell.setCellValue(sidoNm + " " + sigunguNm + " ??? " + itemNm + " ?????? ?????? ??????");
			
			/** ?????? ?????? */
			row = sheet.createRow(2);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
			cell.setCellValue("?????? ??????");
			
			cell = row.createCell(1);
			cell.setCellValue(sidoNm + " " + sigunguNm);
			
			/** ?????? ?????? */
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
			cell.setCellValue("?????? ?????????");
			
			cell = row.createCell(4);
			cell.setCellValue(fieldNm);
			
			/** ?????? ?????? */
			row = sheet.createRow(3);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
			cell.setCellValue("?????? ??????");
			
			cell = row.createCell(1);
			cell.setCellValue(itemNm);
			
			/** ?????? ?????? ?????? */
			row = sheet.createRow(4);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationInfoTitle(workbook));
			cell.setCellValue("?????? ?????? ??????");
			
			cell = row.createCell(1);
			cell.setCellValue(modelNm + " " + sectionNm + " " + yearNm);
			
			/** header */
			row = sheet.createRow(5);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("??????");
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("???????????? ??????");
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("????????? ?????? ??????");
			
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("?????? ?????? ??????");
			
			cell = row.createCell(4);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("?????? ?????? ????????? ??????");
			
			cell = row.createCell(5);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("?????? ?????? ??????");
			
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
			System.out.println("*****************************************************************************");
			System.out.println("*****************************************************************************");
			System.out.println("*****************************************************************************");
			System.out.println(map);
			System.out.println("*****************************************************************************");
			System.out.println("*****************************************************************************");
			System.out.println("*****************************************************************************");

			List<EgovMap> districtList = null;
			if(map.get("resultType").equals("SGG")){
				districtList = this.admEstimationDAO.selectDistrictList();

			}else if(map.get("resultType").equals("EMD")){
				districtList = this.admEstimationDAO.selectDistrictEmdList();
			}
			List<EgovMap> itemIndiList = this.admEstimationDAO.selectItemIndiAllList(map);

			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
			
			row = sheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
			cell.setCellValue(String.valueOf(itemNm));
			
			row = sheet.createRow(2);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("??????");
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("?????? ?????????");
			
			cell = row.createCell(2);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("???????????????");
			
			cell = row.createCell(3);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("???????????? ?????????");
			
			rowCnt = 3;
			
			for(int i = 0; i<itemIndiList.size(); i++){
				row = sheet.createRow(rowCnt);
				cell = row.createCell(0);
				if(String.valueOf(itemIndiList.get(i).get("sectorId")).equals("SEC01")){
					cell.setCellValue("????????????");
					//sheet.addMergedRegion(new CellRangeAddress(3, 3+i, 1, 1));
				}else if(String.valueOf(itemIndiList.get(i).get("sectorId")).equals("SEC02")){
					cell.setCellValue("?????????");
				}else{
					cell.setCellValue("????????????");
				}
				
				cell = row.createCell(1);
				cell.setCellValue(String.valueOf(itemIndiList.get(i).get("sectorWeight")));
				
				cell = row.createCell(2);
				cell.setCellValue(String.valueOf(itemIndiList.get(i).get("indiNm")));
				
				cell = row.createCell(3);
				cell.setCellValue(String.valueOf(itemIndiList.get(i).get("indiValWeight")));
				rowCnt++;
			}

			////??????2 (???????????? ?????????)
			sheetName = "???????????? ?????????";
			sheet = workbook.createSheet(sheetName);
			
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));

			row = sheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationTop(workbook));
			cell.setCellValue(itemNm);
			
			/** header */
			row = sheet.createRow(2);
			cell = row.createCell(0);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("???????????? ??????");
			
			cell = row.createCell(1);
			cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
			cell.setCellValue("???????????? ??????");
			
			int cellCnt = 3;
			//??????????????? x?????????
			for(int i = 0; i<itemIndiList.size(); i++){
				cell = row.createCell(cellCnt);
				cell.setCellStyle(this.excelStyleComp.estimationTitle(workbook));
				cell.setCellValue(String.valueOf(itemIndiList.get(i).get("indiNm")));
				sheet.setColumnWidth(cellCnt, 4 * 256 * 5);
				cellCnt++;
			}

			rowCnt = 3;
			cellCnt = 3;
			
			for(int i = 0; i<districtList.size(); i++){
				row = sheet.createRow(rowCnt);
				cell = row.createCell(0);
				cell.setCellValue(String.valueOf(districtList.get(i).get("districtCd")));
				
				cell = row.createCell(1);
				cell.setCellValue(String.valueOf(districtList.get(i).get("districtSd")));
				
				cell = row.createCell(2);
				cell.setCellValue(String.valueOf(districtList.get(i).get("districtNm")));
				
				cellCnt = 3;
				
				for(int j = 0; j<list.size(); j++){
					if(String.valueOf(districtList.get(i).get("districtCd")).equals(String.valueOf(list.get(j).get("districtCd")))){

						System.out.println(list.get(i));
						cell = row.createCell(cellCnt);
						cell.setCellValue(String.valueOf(String.format("%.2f", list.get(j).get("indiVal"))));
						
						cellCnt++;
					}
				}
				rowCnt++;
			}

			sheet.setColumnWidth(0, 4 * 256 * 5);
			sheet.setColumnWidth(1, 4 * 256 * 5);
			sheet.setColumnWidth(2, 4 * 256 * 5);
			sheet.setColumnWidth(3, 4 * 256 * 5);
			sheet.setColumnWidth(4, 4 * 256 * 5);
			sheet.setColumnWidth(5, 4 * 256 * 5);
			sheet.setColumnWidth(6, 4 * 256 * 5);
			//excelFileName = indiNm;
			excelFileName = itemNm;
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
	public List<EgovMap> selectItemIndiList(EgovMap map){
		return this.admEstimationDAO.selectItemIndiList(map);
	}
	
	@Override
	public List<EgovMap> selectindiitemYearList(EgovMap map){
		return this.admEstimationDAO.selectindiitemYearList(map);
	}
	
	@Override
	public List<EgovMap> selectindiitemSDYearList(EgovMap map){
		return this.admEstimationDAO.selectindiitemSDYearList(map);
	}
}
