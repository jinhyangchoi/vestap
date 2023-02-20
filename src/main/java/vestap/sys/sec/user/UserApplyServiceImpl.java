package vestap.sys.sec.user;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import vestap.adm.user.management.AdmUserManagementDAO;
import vestap.egov.cmm.util.EgovStringUtil;
import vestap.sys.cmm.util.FileUploadUser;

/**
 * 사용자 로그인, 회원가입, 세션 처리 등
 * --------------------------------------------------
 * 		수정일			수정자			수정내용
 * --------------------------------------------------
 * 	2018.08.13			vestap개발		최초 작성
 * --------------------------------------------------
 * @author vestap 개발
 * @since 2018.08.13
 *
 */

@Service("userApplyService")
//@Transactional
public class UserApplyServiceImpl extends EgovAbstractServiceImpl implements UserApplyService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "userApplyDAO")
	private UserApplyDAO DAO;
	
	@Resource(name = "admUserManagementDAO")
	private AdmUserManagementDAO admUserManagementDAO;
	
	@Resource(name = "fileUploadUser")
	private FileUploadUser fileUploadUser;
	
	@Override
	public int userApplyChk(UserApplyVO vo) throws Exception {
		return this.DAO.userApplyChk(vo);
	}
	
	@Override
	public int userApplyInsert(UserApplyVO vo, List<MultipartFile> uploadFiles, List<String> matchName) throws Exception {
		String savePath = "C:/vestap/file/userApply/";
		
		/* 게시물 입력 정보 */
//		this.DAO.userApplyInsert(vo);
		
		/* 파일 업로드 */
		List<UserApplyVO> fileList = this.fileUploadUser.userApplyFileUpload(uploadFiles, "userApply", savePath, matchName);
		
		/* 업로드 된 파일이 있다면 DB에 입력 */
		if(fileList.size() > 0) {
			
			/* 기존파일 삭제처리 S */
			/* insert에 실패한 유저는 user_file테이블에 이미 파일을 가지고 있기 때문에 에러발생을 처리하기 위함 (업로드 하는 파일이 있으니, 기존파일을 삭제해야 한다.) */
			UserApplyVO getUserFileIdxVO = new UserApplyVO();
			getUserFileIdxVO = this.DAO.userApplyFileIdx(vo);
			int fileIdx = getUserFileIdxVO.getFileIdx();
			vo.setFileIdx(fileIdx);
			vo.setFileCatgnm("userApply");
//			this.DAO.userApplyDeleteFile(vo);	/* 파일충돌을 피해 USER_FILE테이블의 file_idx가  USER_INFO테이블의 file_idx보다 높은 숫자이면 강제적으로 맞춰준다. */
			
			UserApplyVO userApplyDeleteVO = new UserApplyVO();
			userApplyDeleteVO = this.DAO.userApplyExistFileChk(vo);
			
			if(userApplyDeleteVO != null) {
				
				String dVObeforeStdFileNm = userApplyDeleteVO.getStdFileNm();
				File beforeStdFile = new File(savePath+"/"+dVObeforeStdFileNm);
				
				if( beforeStdFile.exists() ){
					beforeStdFile.delete();
				}
				this.DAO.userApplyDeleteFile(vo);	/* 파일충돌을 피해 USER_FILE테이블의 userApply카테고리파일의 user가 입력한 모든 파일을 지운다. */
			}
			/* 기존파일 삭제처리 E */
			
			String frstRegUser = EgovStringUtil.nullConvert(vo.getUserId().toLowerCase());
			vo.setFileCatgnm(fileList.get(0).getFileCatgnm());
			vo.setFrstRegUser(frstRegUser);
			vo.setFilePath(fileList.get(0).getFilePath());
			vo.setOrgFileNm(fileList.get(0).getOrgFileNm());
			vo.setStdFileNm(fileList.get(0).getStdFileNm());
			vo.setFileAtchYn("Y");
			this.DAO.userApplyInsertFile(vo);
		
		} else {
			vo.setFileAtchYn("N");
		}
		
		return this.DAO.userApplyInsert(vo);
	}

	@Override
	public UserApplyVO userApplyFileIdx(UserApplyVO vo) throws Exception {
		return this.DAO.userApplyFileIdx(vo);
	}
	
	@Override
	@Transactional
	public int userApplyUpdate(UserApplyVO vo, List<MultipartFile> uploadFiles, List<String> newFileName) throws Exception {
		String savePath = "C:/vestap/file/userApply/";
		
//		this.admUserManagementService.updateUserInfo(map);
		
		//파일첨부상태체크(기존파일:beforeFileNm, 신규파일:newFileName)
		if(vo.getBeforeFileNm() != null || newFileName != null) {
			vo.setFileAtchYn("Y");
		} else {
			vo.setFileAtchYn("N");
		}
		
		String fileAtchYnChk = vo.getFileAtchYn();
		String beforeOrgFileNm = EgovStringUtil.nullConvert(vo.getOrgFileNm());
		String beforeStdFileNm = EgovStringUtil.nullConvert(vo.getStdFileNm());
		int beforeFileIdx = vo.getFileIdx();
		vo.setFileCatgnm("userApply");
		if(newFileName != null ) {
			
			/* 파일 업로드 */
			List<UserApplyVO> fileList = this.fileUploadUser.userApplyFileUpload(uploadFiles, "userApply", savePath, newFileName);
			
			vo.setFileCatgnm(fileList.get(0).getFileCatgnm());
			vo.setOrgFileNm(fileList.get(0).getOrgFileNm());
			vo.setStdFileNm(fileList.get(0).getStdFileNm());
			vo.setFilePath(fileList.get(0).getFilePath());
			
			/* 업로드 진행중 파일이 있다면 DB에 입력 */
			if(fileList.size() > 0) {
				
				/* 업로드 하는 파일이 있으니, 기존파일을 삭제해야 한다.*/
				UserApplyVO userApplyDeleteVO = new UserApplyVO();
				userApplyDeleteVO = this.DAO.userApplyExistFileChk(vo);
				
				if(userApplyDeleteVO != null) {
					
					String dVObeforeStdFileNm = userApplyDeleteVO.getStdFileNm();
					File beforeStdFile = new File(savePath+"/"+dVObeforeStdFileNm);
					
					if( beforeStdFile.exists() ){
						beforeStdFile.delete();
					}
					this.DAO.userApplyDeleteFile(vo);	/* PK충돌을 피해 USER_FILE테이블의 userApply카테고리파일의 user가 입력한 모든 파일을 지운다. */
				}
				this.DAO.userApplyInsertFile(vo);
			}
			
		} else {
			
			
			if ( ("N").equals(fileAtchYnChk)) {
				logger.debug("기존파일과 신규파일이 존재하지 않는 상태");
				File beforeStdFileDeleteReady = new File(savePath+"/"+beforeStdFileNm);
				if( beforeStdFileDeleteReady.exists() ){
					beforeStdFileDeleteReady.delete();
				}
				this.DAO.userApplyDeleteFile(vo);
				vo.setFileIdx(beforeFileIdx);
			} else {
				logger.debug("신규파일이 존재하지 않아 기존파일을 유지합니다.");
			}
			
		}
		
		/* 입력, 수정을 체크하고 user_info테이블의 파일키도 업데이트 S */
		UserApplyVO userApplyUpdateVO = new UserApplyVO();
		userApplyUpdateVO = this.DAO.userApplyExistFileChk(vo);
		
		if(userApplyUpdateVO != null) {
			int currentFileIdx = 0;
			currentFileIdx = userApplyUpdateVO.getFileIdx();
			vo.setFileIdx(currentFileIdx);
		}
		
		/* 회원정보 업데이트 */
		return this.DAO.userApplyUpdate(vo);
	}

}
