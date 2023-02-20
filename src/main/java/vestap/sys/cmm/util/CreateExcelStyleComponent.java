package vestap.sys.cmm.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component("createExcelStyleComponent")
public class CreateExcelStyleComponent {
	
	/**
	 * 지표 생성 엑셀 파일 최상단 정보 문구
	 * @param workbook
	 * @return
	 */
	public XSSFCellStyle indicator_info(XSSFWorkbook workbook) {
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		XSSFFont font = workbook.createFont();
		
		font.setColor(IndexedColors.DARK_RED.index);
		font.setBold(true);
		
		cellStyle.setFont(font);
		
		return cellStyle;
	}
	
	/**
	 * 지표 생성 엑셀 파일 컬럼명
	 * @param workbook
	 * @return
	 */
	public XSSFCellStyle indicator_title(XSSFWorkbook workbook) {
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		XSSFFont font = workbook.createFont();
		
		font.setColor(IndexedColors.WHITE.index);
		font.setBold(true);
		
		cellStyle.setFont(font);
		
		/** setFillForegroundColor: 배경색 */
		cellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		return cellStyle;
	}
	
	/**
	 * 지표 생성 엑셀 파일 지표값 첫번째
	 * @param workbook
	 * @return
	 */
	public XSSFCellStyle indicator_value_top(XSSFWorkbook workbook) {
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		cellStyle.setTopBorderColor(IndexedColors.DARK_RED.index);
		cellStyle.setBorderRight(BorderStyle.MEDIUM);
		cellStyle.setRightBorderColor(IndexedColors.DARK_RED.index);
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		cellStyle.setLeftBorderColor(IndexedColors.DARK_RED.index);
		
		return cellStyle;
	}
	
	/**
	 * 지표 생성 엑셀 파일 지표값 마지막
	 * @param workbook
	 * @return
	 */
	public XSSFCellStyle indicator_value_bottom(XSSFWorkbook workbook) {
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		cellStyle.setBottomBorderColor(IndexedColors.DARK_RED.index);
		cellStyle.setBorderRight(BorderStyle.MEDIUM);
		cellStyle.setRightBorderColor(IndexedColors.DARK_RED.index);
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		cellStyle.setLeftBorderColor(IndexedColors.DARK_RED.index);
		
		return cellStyle;
	}
	
	/**
	 * 지표 생성 엑셀 파일 지표값 중간
	 * @param workbook
	 * @return
	 */
	public XSSFCellStyle indicator_value(XSSFWorkbook workbook) {
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setBorderRight(BorderStyle.MEDIUM);
		cellStyle.setRightBorderColor(IndexedColors.DARK_RED.index);
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		cellStyle.setLeftBorderColor(IndexedColors.DARK_RED.index);
		
		return cellStyle;
	}
	
	/**
	 * 취약성 평가 헤더
	 * @param workbook
	 * @return
	 */
	public XSSFCellStyle estimationTitle(XSSFWorkbook workbook) {
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		XSSFFont font = workbook.createFont();
		
		font.setColor(IndexedColors.BLACK.index);
		font.setBold(true);
		
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		return cellStyle;
	}
	
	/**
	 * 취약성 평가 기본 정보 제목
	 * @param workbook
	 * @return
	 */
	public XSSFCellStyle estimationInfoTitle(XSSFWorkbook workbook) {
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		cellStyle.setLeftBorderColor(IndexedColors.DARK_RED.index);
		
		cellStyle.setBorderRight(BorderStyle.MEDIUM);
		cellStyle.setRightBorderColor(IndexedColors.DARK_RED.index);
		
		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		cellStyle.setTopBorderColor(IndexedColors.DARK_RED.index);
		
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		cellStyle.setBottomBorderColor(IndexedColors.DARK_RED.index);
		
		cellStyle.setFillForegroundColor(IndexedColors.DARK_RED.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		XSSFFont font = workbook.createFont();
		
		font.setColor(IndexedColors.WHITE.index);
		font.setBold(true);
		
		cellStyle.setFont(font);
		
		return cellStyle;
	}
	
	/**
	 * 취약성 평가 타이틀
	 * @param workbook
	 * @return
	 */
	public XSSFCellStyle estimationTop(XSSFWorkbook workbook) {
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		XSSFFont font = workbook.createFont();
		
		font.setColor(IndexedColors.DARK_BLUE.index);
		font.setBold(true);
		
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setWrapText(true);
		
		return cellStyle;
	}
	
	/**
	 * 취약성 평가 타이틀
	 * @param workbook
	 * @return
	 */
	public XSSFCellStyle estimationFirstValue(XSSFWorkbook workbook) {
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		XSSFFont font = workbook.createFont();
		
		font.setBold(true);
		
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setWrapText(true);
		
		return cellStyle;
	}
	
	/**
	 * 취약성 평가 값
	 * @param workbook
	 * @return
	 */
	public XSSFCellStyle estimationValue(XSSFWorkbook workbook) {
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		return cellStyle;
	}
	
	public XSSFCellStyle wrapText(XSSFWorkbook workbook) {
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		return cellStyle;
	}
}
