package vestap.adm.bbs.faq;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vestap.sys.bbs.cmm.BoardFileVO;
import vestap.sys.bbs.cmm.BoardVO;

@Transactional
public interface AdmFaqBoardService {
	
	List<BoardVO> getFaqBoard(BoardVO vo) throws Exception;
	
	int getFaqTotCnt(BoardVO vo) throws Exception;
	
	@Transactional
	BoardVO getFaqContent(int param) throws Exception;
	
	List<BoardFileVO> getFaqFile(int param) throws Exception;
	
	@Transactional
	void setFaqBoard(BoardVO vo, List<MultipartFile> uploadFile, List<String> matchName) throws Exception;
	
	@Transactional
	void faqBoardDownload(HttpServletResponse response, BoardFileVO vo) throws Exception;
	
	@Transactional
	void faqBoardUpdate(HttpServletResponse response, BoardVO vo, String idx, List<MultipartFile> uploadFile, List<String> matchName, List<String> deleteStdFile) throws Exception;
	
	@Transactional
	void deleteFaq(int param) throws Exception;
}
