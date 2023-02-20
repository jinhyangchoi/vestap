package vestap.sys.cmm.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import vestap.sys.bbs.cmm.BoardFileVO;

@Component("fileUploadComponent")
public class FileUploadComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadComponent.class);
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	/**
	 * 게시판 다중 파일 업로드: LIST
	 * @param uploadFileList
	 * @param savePath
	 * @throws Exception
	 */
	public List<BoardFileVO> boardFileUpload(List<MultipartFile> uploadFileList, String savePath, String boardNm, int idx, List<String> matchName) {
		
		Map<String, String> nameMap = new HashMap<String, String>();
		
		if(matchName != null) {
			
			for(int i = 0; i < matchName.size(); i++) {
				nameMap.put(matchName.get(i), null);
			}
		}
		
		List<BoardFileVO> fileList = new ArrayList<BoardFileVO>();
		
		MultipartFile multiFile = null;
		String orgFileNm = null;
		String orgFileEx = null;
		String stdFileNm = null;
		
		File file = null;
		
		/**
		 * savePath 경로가 없으면 생성
		 */
		this.utilComp.makeDirectory(savePath);
		
		for(int i = 0; i < uploadFileList.size(); i++) {
			
			multiFile = uploadFileList.get(i);
			
			if(!multiFile.isEmpty()) {
				
				orgFileNm = multiFile.getOriginalFilename();
				orgFileEx = orgFileNm.substring(orgFileNm.lastIndexOf(".") + 1);
				stdFileNm = this.utilComp.getRandomUUID() + "." + orgFileEx;
				
				file = new File(savePath + stdFileNm);
				
				try {
					
					if(nameMap.containsKey(orgFileNm)) {
						
						multiFile.transferTo(file);
						
						BoardFileVO vo = new BoardFileVO();
						
						vo.setBbs_idx(idx);
						vo.setBbs_nm(boardNm);
						vo.setFile_path(savePath);
						vo.setOrg_file_nm(orgFileNm);
						vo.setStd_file_nm(stdFileNm);
						
						fileList.add(vo);
					}
					
				} catch (IllegalStateException e) {
					
					logger.error(this.utilComp.stackTrace(e));
					fileList = new ArrayList<BoardFileVO>();
					
				} catch (IOException e) {
					
					logger.error(this.utilComp.stackTrace(e));
					fileList = new ArrayList<BoardFileVO>();
					
				} catch (Exception e) {
					
					logger.error(this.utilComp.stackTrace(e));
					fileList = new ArrayList<BoardFileVO>();
				}
				
			} else {
				fileList = new ArrayList<BoardFileVO>();
			}
		}
		
		return fileList;
	}
	
	public String indicatorFileUpload(MultipartFile uploadFile, String savePath) {
		
		String orgFileNm = null;
		String orgFileEx = null;
		String stdFileNm = null;
		
		if(!uploadFile.isEmpty()) {
			
			orgFileNm = uploadFile.getOriginalFilename();
			orgFileEx = orgFileNm.substring(orgFileNm.lastIndexOf(".") + 1);
			stdFileNm = this.utilComp.getRandomUUID() + "." + orgFileEx;
			
			File file = new File(savePath);
			
			if(!file.isDirectory()) {
				file.mkdirs();
			}
			
			file = new File(savePath + stdFileNm);
			
			try {
				
				uploadFile.transferTo(file);
				
			} catch (IllegalStateException e) {
				
				logger.error(this.utilComp.stackTrace(e));
				
			} catch (IOException e) {
				
				logger.error(this.utilComp.stackTrace(e));
				
			} catch (Exception e) {
				
				logger.error(this.utilComp.stackTrace(e));
			}
		}
		
		return stdFileNm;
	}
}
