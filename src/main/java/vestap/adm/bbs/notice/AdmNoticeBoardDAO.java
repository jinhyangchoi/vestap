package vestap.adm.bbs.notice;

import java.util.List;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;
import vestap.sys.bbs.cmm.BoardFileVO;
import vestap.sys.bbs.cmm.BoardVO;

@Repository("admNoticeBoardDAO")
public class AdmNoticeBoardDAO extends EgovComAbstractDAO {
	
	public List<BoardVO> getNoticeBoard(BoardVO vo) throws Exception {
		return selectList("notice.getNoticeList", vo);
	}
	
	public int getNoticeTotCnt(BoardVO vo) throws Exception {
		return selectOne("notice.getNoticeTotCnt", vo);
	}
	
	public BoardVO getNoticeContent(int param) throws Exception {
		return selectOne("notice.getNoticeContent", param);
	}
	
	public List<BoardFileVO> getNoticeFile(int param) throws Exception {
		return selectList("notice.getNoticeFile", param);
	}
	
	public BoardFileVO getNoticeFileInfo(BoardFileVO vo) throws Exception {
		return selectOne("notice.getNoticeFileInfo", vo);
	}
	
	public void setNoticeFileHit(BoardFileVO vo) throws Exception {
		update("notice.setNoticeFileHit", vo);
	}
	
	public void setNoticeHit(int param) throws Exception {
		update("notice.setNoticeHit", param);
	}
	
	public void setNoticeBoard(BoardVO vo) throws Exception {
		insert("notice.setNoticeBoard", vo);
	}
	
	public void setBoardFile(List<BoardFileVO> fileList) throws Exception {
		insert("notice.setBoardFile", fileList);
	}
	
	public void updateNoticeBoard(BoardVO vo) throws Exception {
		update("notice.updateNoticeBoard", vo);
	}
	
	public void deleteNotice(int param) throws Exception {
		delete("notice.deleteNotice", param);
	}
	
	public void deleteNoticeFile(int param) throws Exception {
		delete("notice.deleteNoticeFile", param);
	}
	
	public void deleteNoticeFileOne(BoardFileVO vo) throws Exception {
		delete("notice.deleteNoticeFileOne", vo);
	}
}
