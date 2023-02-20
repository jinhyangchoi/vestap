package vestap.adm.bbs.notice;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vestap.sys.bbs.cmm.BoardFileVO;
import vestap.sys.bbs.cmm.BoardVO;

@Transactional
public interface AdmNoticeBoardService {
	
	List<BoardVO> getNoticeBoard(BoardVO vo) throws Exception;
	
	int getNoticeTotCnt(BoardVO vo) throws Exception;
	
	@Transactional
	BoardVO getNoticeContent(int param) throws Exception;
	
	List<BoardFileVO> getNoticeFile(int param) throws Exception;
	
	@Transactional
	void setNoticeBoard(BoardVO vo, List<MultipartFile> uploadFile, List<String> matchName) throws Exception;
	
	@Transactional
	void noticeBoardDownload(HttpServletResponse response, BoardFileVO vo) throws Exception;
	
	@Transactional
	void noticeBoardUpdate(HttpServletResponse response, BoardVO vo, String idx, List<MultipartFile> uploadFile, List<String> matchName, List<String> deleteStdFile) throws Exception;
	
	@Transactional
	void deleteNotice(int param) throws Exception;
}
