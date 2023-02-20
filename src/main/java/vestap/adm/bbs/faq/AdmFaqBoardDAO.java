package vestap.adm.bbs.faq;

import java.util.List;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;
import vestap.sys.bbs.cmm.BoardFileVO;
import vestap.sys.bbs.cmm.BoardVO;

@Repository("admFaqBoardDAO")
public class AdmFaqBoardDAO extends EgovComAbstractDAO {
	
	public List<BoardVO> getFaqBoard(BoardVO vo) throws Exception {
		return selectList("faq.getFaqList", vo);
	}
	
	public int getFaqTotCnt(BoardVO vo) throws Exception {
		return selectOne("faq.getFaqTotCnt", vo);
	}
	
	public BoardVO getFaqContent(int param) throws Exception {
		return selectOne("faq.getFaqContent", param);
	}
	
	public List<BoardFileVO> getFaqFile(int param) throws Exception {
		return selectList("faq.getFaqFile", param);
	}
	
	public BoardFileVO getFaqFileInfo(BoardFileVO vo) throws Exception {
		return selectOne("faq.getFaqFileInfo", vo);
	}
	
	public void setFaqFileHit(BoardFileVO vo) throws Exception {
		update("faq.setFaqFileHit", vo);
	}
	
	public void setFaqHit(int param) throws Exception {
		update("faq.setFaqHit", param);
	}
	
	public void setFaqBoard(BoardVO vo) throws Exception {
		insert("faq.setFaqBoard", vo);
	}
	
	public void setBoardFile(List<BoardFileVO> fileList) throws Exception {
		insert("faq.setBoardFile", fileList);
	}
	
	public void updateFaqBoard(BoardVO vo) throws Exception {
		update("faq.updateFaqBoard", vo);
	}
	
	public void deleteFaq(int param) throws Exception {
		delete("faq.deleteFaq", param);
	}
	
	public void deleteFaqFile(int param) throws Exception {
		delete("faq.deleteFaqFile", param);
	}
	
	public void deleteFaqFileOne(BoardFileVO vo) throws Exception {
		delete("faq.deleteFaqFileOne", vo);
	}
}
