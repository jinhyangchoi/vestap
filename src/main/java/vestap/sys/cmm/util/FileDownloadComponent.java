package vestap.sys.cmm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.sys.bbs.cmm.BoardFileVO;

@Component("fileDownloadComponent")
public class FileDownloadComponent {
	
	/**
	 * 게시판 업로드 파일 다운로드
	 * @param response
	 * @param vo
	 * @throws Exception
	 */
	public void boardFileDownload(HttpServletResponse response, BoardFileVO vo) throws Exception {
		
		String fileName = URLEncoder.encode(vo.getOrg_file_nm(), "UTF-8");
		fileName = fileName.replaceAll("%28", "\\(");
		fileName = fileName.replaceAll("%29", "\\)");
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\"");
		
		File file = new File(vo.getFile_path() + vo.getStd_file_nm());
		
		byte[] bytestream = new byte[2048];
		
		FileInputStream fileStream = new FileInputStream(file);
		
		int readBytes = 0, sumBytes = 0;
		
		OutputStream outStream = response.getOutputStream();
		
		while((readBytes = fileStream.read(bytestream)) != -1) {
			
			outStream.write(bytestream, 0, readBytes);
			
			sumBytes += readBytes;
			
			if(sumBytes > (1024 * 1024)) {
				outStream.flush();
			}
		}
		
		fileStream.close();
		outStream.flush();
		outStream.close();
	}
	
	/**
	 * 지표 생성 엑셀 폼 다운로드
	 * @param response
	 * @param tempName
	 * @throws Exception
	 */
	public void indicatorInputFormDownload(HttpServletResponse response, String tempName, String fileName) throws Exception {
		
		String filePath = "C:/vestap/file/download/temp/";
		
		fileName = URLEncoder.encode(fileName, "UTF-8");
		fileName = fileName.replaceAll("%28", "\\(");
		fileName = fileName.replaceAll("%29", "\\)");
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\"");
		
		File file = new File(filePath, tempName);
		
		byte[] bytestream = new byte[2048];
		
		FileInputStream fileStream = new FileInputStream(file);
		
		int readBytes = 0, sumBytes = 0;
		
		OutputStream outStream = response.getOutputStream();
		
		while((readBytes = fileStream.read(bytestream)) != -1) {
			
			outStream.write(bytestream, 0, readBytes);
			
			sumBytes += readBytes;
			
			if(sumBytes > (1024 * 1024)) {
				outStream.flush();
			}
		}
		
		fileStream.close();
		outStream.flush();
		outStream.close();
		
		file.delete();
	}

	public void userGovDocDownloadAction(HttpServletResponse response, EgovMap map)  throws Exception {
		
		String fileName = URLEncoder.encode(map.get("orgFileNm").toString(), "UTF-8");
		fileName = fileName.replaceAll("%28", "\\(");
		fileName = fileName.replaceAll("%29", "\\)");
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\"");
		
		File file = new File(map.get("filePath").toString() + map.get("stdFileNm").toString());
		
		byte[] bytestream = new byte[2048];
		FileInputStream fileStream = new FileInputStream(file);
		
		OutputStream outStream = response.getOutputStream();
		int readBytes = 0, sumBytes = 0;
		
		while((readBytes = fileStream.read(bytestream)) != -1) {
			outStream.write(bytestream, 0, readBytes);
			sumBytes += readBytes;
			
			if(sumBytes > (1024 * 1024)) {
				outStream.flush();
			}
		}
		fileStream.close();
		outStream.flush();
		outStream.close();
	}
	
}
