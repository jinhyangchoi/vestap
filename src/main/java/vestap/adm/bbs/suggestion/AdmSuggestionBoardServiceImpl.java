package vestap.adm.bbs.suggestion;

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

@Service("admSuggestionBoardService")
@Transactional
public class AdmSuggestionBoardServiceImpl extends EgovAbstractServiceImpl implements AdmSuggestionBoardService {
	
	@Resource(name = "admSuggestionBoardDAO")
	private AdmSuggestionBoardDAO DAO;
	
	@Resource(name = "fileUploadComponent")
	private FileUploadComponent fileComp;
	
	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent downComp;
	
	@Override
	public int isUserBoard(BoardVO vo) throws Exception {
		return this.DAO.isUserBoard(vo);
	}
	
	public List<BoardVO> getSuggestionBoard(BoardVO vo) throws Exception {
		return this.DAO.getSuggestionBoard(vo);
	}

	@Override
	public int getSuggestionTotCnt(BoardVO vo) throws Exception {
		return this.DAO.getSuggestionTotCnt(vo);
	}

	@Override
	@Transactional
	public BoardVO getSuggestionContent(BoardVO vo) throws Exception {
		
		this.DAO.setSuggestionHit(vo);
		
		return this.DAO.getSuggestionContent(vo);
	}
	
	@Override
	public BoardVO getSuggestionModify(BoardVO vo) throws Exception {
		return this.DAO.getSuggestionContent(vo);
	}
	
	@Override
	public List<BoardFileVO> getSuggestionFile(int param) throws Exception {
		return this.DAO.getSuggestionFile(param);
	}
	
	@Override
	@Transactional
	public void setSuggestionBoard(BoardVO vo, List<MultipartFile> uploadFile, List<String> matchName) throws Exception {
		
		String savePath = "C:/vestap/file/board/suggestion/";
		
		/**
		 * 게시물 입력 정보
		 */
		this.DAO.setSuggestionBoard(vo);
		
		/**
		 * 파일 업로드
		 */
		List<BoardFileVO> fileList = this.fileComp.boardFileUpload(uploadFile, savePath, "suggestion", vo.getBbs_idx(), matchName);
		
		/**
		 * 업로드 된 파일이 있다면 DB에 입력
		 */
		if(fileList.size() > 0) {
			
			this.DAO.setBoardFile(fileList);
		}
	}

	@Override
	@Transactional
	public void suggestionBoardDownload(HttpServletResponse response, BoardFileVO vo) throws Exception {
		
		/**
		 * 파일 다운로드를 위해 파일 정보 불러오기
		 */
		vo = this.DAO.getSuggestionFileInfo(vo);
		
		/**
		 * 파일 다운로드 수 증가
		 */
		this.DAO.setSuggestionFileHit(vo);
		
		/**
		 * 파일 다운로드
		 */
		this.downComp.boardFileDownload(response, vo);
	}
	
	@Override
	@Transactional
	public void suggestionBoardUpdate(HttpServletResponse response, BoardVO vo, String idx, List<MultipartFile> uploadFile, List<String> matchName,
			List<String> deleteStdFile) throws Exception {
		
		String savePath = "C:/vestap/file/board/suggestion/";
		
		vo.setBbs_idx(Integer.parseInt(idx));
		
		/**
		 * 게시물 수정 사항 입력
		 */
		this.DAO.updateSuggestionBoard(vo);
		
		/**
		 * 파일 업로드
		 */
		List<BoardFileVO> fileList = this.fileComp.boardFileUpload(uploadFile, savePath, "suggestion", vo.getBbs_idx(), matchName);
		
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
				filesVO = this.DAO.getSuggestionFileInfo(filesVO);
				
				/**
				 * 파일을 DB에서 삭제
				 */
				this.DAO.deleteSuggestionFileOne(filesVO);
				
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
	public void deleteSuggestion(BoardVO vo) throws Exception {
		
		/**
		 * 게시물에 첨부 된 파일 정보 불러오기(DB에서 삭제 하기 전)
		 */
		List<BoardFileVO> fileList = this.DAO.getSuggestionFile(vo.getBbs_idx());
		
		/**
		 * 게시물에 첨부 된 파일 삭제
		 */
		this.DAO.deleteSuggestionFile(vo.getBbs_idx());
		
		/**
		 * 게시물 삭제
		 */
		this.DAO.deleteSuggestion(vo);
		
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
	

	@Override
	@Transactional
	public void setSuggestionRefBoard(BoardVO vo, List<MultipartFile> uploadFile, List<String> matchName) throws Exception {
		
		String savePath = "C:/vestap/file/board/suggestion/";
		
		/**
		 * 게시물 입력 정보
		 */
		this.DAO.setSuggestionRefBoard(vo);
		
		/**
		 * 파일 업로드
		 */
		List<BoardFileVO> fileList = this.fileComp.boardFileUpload(uploadFile, savePath, "suggestion", vo.getBbs_idx(), matchName);
		
		/**
		 * 업로드 된 파일이 있다면 DB에 입력
		 */
		if(fileList.size() > 0) {
			
			this.DAO.setBoardFile(fileList);
		}
	}

}
