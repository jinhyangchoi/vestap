package vestap.adm.bbs.suggestion;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vestap.sys.bbs.cmm.BoardFileVO;
import vestap.sys.bbs.cmm.BoardVO;

@Transactional
public interface AdmSuggestionBoardService {
	
	int isUserBoard(BoardVO vo) throws Exception;
	
	List<BoardVO> getSuggestionBoard(BoardVO vo) throws Exception;
	
	int getSuggestionTotCnt(BoardVO vo) throws Exception;
	
	@Transactional
	BoardVO getSuggestionContent(BoardVO vo) throws Exception;
	
	BoardVO getSuggestionModify(BoardVO vo) throws Exception;
	
	List<BoardFileVO> getSuggestionFile(int param) throws Exception;
	
	@Transactional
	void setSuggestionBoard(BoardVO vo, List<MultipartFile> uploadFile, List<String> matchName) throws Exception;
	
	@Transactional
	void suggestionBoardDownload(HttpServletResponse response, BoardFileVO vo) throws Exception;
	
	@Transactional
	void suggestionBoardUpdate(HttpServletResponse response, BoardVO vo, String idx, List<MultipartFile> uploadFile, List<String> matchName, List<String> deleteStdFile) throws Exception;
	
	@Transactional
	void deleteSuggestion(BoardVO vo) throws Exception;
	

	@Transactional
	void setSuggestionRefBoard(BoardVO vo, List<MultipartFile> uploadFile, List<String> matchName) throws Exception;
	
}
