package vestap.adm.bbs.reference;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vestap.sys.bbs.cmm.BoardFileVO;
import vestap.sys.bbs.cmm.BoardVO;

@Transactional
public interface AdmReferenceBoardService {
	
	List<BoardVO> getReferenceBoard(BoardVO vo) throws Exception;
	
	int getReferenceTotCnt(BoardVO vo) throws Exception;
	
	@Transactional
	BoardVO getReferenceContent(int param) throws Exception;
	
	List<BoardFileVO> getReferenceFile(int param) throws Exception;
	
	@Transactional
	void setReferenceBoard(BoardVO vo, List<MultipartFile> uploadFile, List<String> matchName) throws Exception;
	
	@Transactional
	void referenceBoardDownload(HttpServletResponse response, BoardFileVO vo) throws Exception;
	
	@Transactional
	void referenceBoardUpdate(HttpServletResponse response, BoardVO vo, String idx, List<MultipartFile> uploadFile, List<String> matchName, List<String> deleteStdFile) throws Exception;
	
	@Transactional
	void deleteReference(int param) throws Exception;
}
