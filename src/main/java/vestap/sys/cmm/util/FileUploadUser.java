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

import vestap.sys.sec.user.UserApplyVO;

@Component("fileUploadUser")
public class FileUploadUser {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadUser.class);
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	/**
	 * 게시판 다중 파일 업로드: LIST
	 * @param uploadFileList
	 * @param savePath
	 * @throws Exception
	 */
	public List<UserApplyVO> userApplyFileUpload(List<MultipartFile> uploadFileList, String fileCatgnm, String savePath, List<String> matchName) {
		Map<String, String> nameMap = new HashMap<String, String>();
		
		if(matchName != null) {
			for(int i = 0; i < matchName.size(); i++) {
				nameMap.put(matchName.get(i), null);
			}
		}
		
		List<UserApplyVO> fileList = new ArrayList<UserApplyVO>();
		
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
			
//			if(!multiFile.isEmpty()) {
			if(multiFile != null) {
				
				orgFileNm = multiFile.getOriginalFilename();
				orgFileEx = orgFileNm.substring(orgFileNm.lastIndexOf(".") + 1);
				stdFileNm = this.utilComp.getRandomUUID() + "." + orgFileEx;
				
				file = new File(savePath + stdFileNm);
				
				try {
					
						multiFile.transferTo(file);
						
						UserApplyVO vo = new UserApplyVO();
						
//						vo.setFile_idx(file_idx);
						vo.setFileCatgnm(fileCatgnm);
						vo.setOrgFileNm(orgFileNm);
						vo.setStdFileNm(stdFileNm);
						vo.setFilePath(savePath);
						
						fileList.add(vo);
					
				} catch (IllegalStateException e) {
					
					logger.error(this.utilComp.stackTrace(e));
					fileList = new ArrayList<UserApplyVO>();
					
				} catch (IOException e) {
					
					logger.error(this.utilComp.stackTrace(e));
					fileList = new ArrayList<UserApplyVO>();
					
				} catch (Exception e) {
					
					logger.error(this.utilComp.stackTrace(e));
					fileList = new ArrayList<UserApplyVO>();
				}
				
			} else {
				fileList = new ArrayList<UserApplyVO>();
			}
		}
		
		return fileList;
	}
	
}
