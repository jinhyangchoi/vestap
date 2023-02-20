package vestap.adm.bbs.notice;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import vestap.sys.bbs.cmm.BoardFileVO;
import vestap.sys.bbs.cmm.BoardVO;
import vestap.sys.cmm.util.FileDownloadComponent;
import vestap.sys.cmm.util.FileUploadComponent;

@Service("AdmNoticeBoardService")
@Transactional
public class AdmNoticeBoardServiceImpl extends EgovAbstractServiceImpl implements AdmNoticeBoardService {
	
	@Resource(name = "admNoticeBoardDAO")
	private AdmNoticeBoardDAO DAO;
	
	@Resource(name = "fileUploadComponent")
	private FileUploadComponent fileComp;
	
	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent downComp;
	
	public List<BoardVO> getNoticeBoard(BoardVO vo) throws Exception {
		return this.DAO.getNoticeBoard(vo);
	}

	@Override
	public int getNoticeTotCnt(BoardVO vo) throws Exception {
		return this.DAO.getNoticeTotCnt(vo);
	}

	@Override
	@Transactional
	public BoardVO getNoticeContent(int param) throws Exception {
		
		this.DAO.setNoticeHit(param);
		
		return this.DAO.getNoticeContent(param);
	}
	
	@Override
	public List<BoardFileVO> getNoticeFile(int param) throws Exception {
		return this.DAO.getNoticeFile(param);
	}
	
	@Override
	@Transactional
	public void setNoticeBoard(BoardVO vo, List<MultipartFile> uploadFile, List<String> matchName) throws Exception {
		
		String savePath = "C:/vestap/file/board/notice/";
		
		/**
		 * 게시물 입력 정보
		 */
		this.DAO.setNoticeBoard(vo);
		
		/**
		 * 파일 업로드
		 */
		List<BoardFileVO> fileList = this.fileComp.boardFileUpload(uploadFile, savePath, "notice", vo.getBbs_idx(), matchName);
		
		/**
		 * 업로드 된 파일이 있다면 DB에 입력
		 */
		if(fileList.size() > 0) {
			
			this.DAO.setBoardFile(fileList);
		}
	}

	@Override
	@Transactional
	public void noticeBoardDownload(HttpServletResponse response, BoardFileVO vo) throws Exception {
		
		/**
		 * 파일 다운로드를 위해 파일 정보 불러오기
		 */
		vo = this.DAO.getNoticeFileInfo(vo);
		
		/**
		 * 파일 다운로드 수 증가
		 */
		this.DAO.setNoticeFileHit(vo);
		
		/**
		 * 파일 다운로드
		 */
		this.downComp.boardFileDownload(response, vo);
	}
	
	@Override
	@Transactional
	public void noticeBoardUpdate(HttpServletResponse response, BoardVO vo, String idx, List<MultipartFile> uploadFile, List<String> matchName,
			List<String> deleteStdFile) throws Exception {
		
		String savePath = "C:/vestap/file/board/notice/";
		
		vo.setBbs_idx(Integer.parseInt(idx));
		
		/**
		 * 게시물 수정 사항 입력
		 */
		this.DAO.updateNoticeBoard(vo);
		
		/**
		 * 파일 업로드
		 */
		List<BoardFileVO> fileList = this.fileComp.boardFileUpload(uploadFile, savePath, "notice", vo.getBbs_idx(), matchName);
		
		/**
		 * 업로드 된 파일이 있다면 DB에 입력
		 */
		if(fileList.size() > 0) {
			
			this.DAO.setBoardFile(fileList);
		}
		
		/**
		 * 원본 게시물에 첨부 된 파일 삭제 선택 시 파일 삭제
		 */
		if(deleteStdFile != null) {
			
			for(int i = 0; i < deleteStdFile.size(); i++) {
				
				BoardFileVO filesVO = new BoardFileVO();
				
				filesVO.setBbs_idx(Integer.parseInt(idx));
				filesVO.setStd_file_nm(deleteStdFile.get(i));
				
				/**
				 * 해당 파일의 정보 불러오기
				 */
				filesVO = this.DAO.getNoticeFileInfo(filesVO);
				
				/**
				 * 파일을 DB에서 삭제
				 */
				this.DAO.deleteNoticeFileOne(filesVO);
				
				/**
				 * 파일 삭제
				 */
				File file = new File(filesVO.getFile_path() + filesVO.getStd_file_nm());
				
				if(file.isFile()) {
					file.delete();
				}
			}
		}
	}

	@Override
	@Transactional
	public void deleteNotice(int param) throws Exception {
		
		/**
		 * 게시물에 첨부 된 파일 정보 불러오기(DB에서 삭제 하기 전)
		 */
		List<BoardFileVO> fileList = this.DAO.getNoticeFile(param);
		
		/**
		 * 게시물에 첨부 된 파일 삭제
		 */
		this.DAO.deleteNoticeFile(param);
		
		/**
		 * 게시물 삭제
		 */
		this.DAO.deleteNotice(param);
		
		/**
		 * 파일 삭제
		 */
		for(int i = 0; i < fileList.size(); i++) {
			
			File file = new File(fileList.get(i).getFile_path() + fileList.get(i).getStd_file_nm());
			
			if(file.isFile()) {
				file.delete();
			}
		}
	}
}
