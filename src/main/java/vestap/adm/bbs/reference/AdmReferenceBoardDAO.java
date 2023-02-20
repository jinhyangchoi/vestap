package vestap.adm.bbs.reference;

import java.util.List;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;
import vestap.sys.bbs.cmm.BoardFileVO;
import vestap.sys.bbs.cmm.BoardVO;

@Repository("admReferenceBoardDAO")
public class AdmReferenceBoardDAO extends EgovComAbstractDAO {
	
	public List<BoardVO> getReferenceBoard(BoardVO vo) throws Exception {
		return selectList("reference.getReferenceList", vo);
	}
	
	public int getReferenceTotCnt(BoardVO vo) throws Exception {
		return selectOne("reference.getReferenceTotCnt", vo);
	}
	
	public BoardVO getReferenceContent(int param) throws Exception {
		return selectOne("reference.getReferenceContent", param);
	}
	
	public List<BoardFileVO> getReferenceFile(int param) throws Exception {
		return selectList("reference.getReferenceFile", param);
	}
	
	public BoardFileVO getReferenceFileInfo(BoardFileVO vo) throws Exception {
		return selectOne("reference.getReferenceFileInfo", vo);
	}
	
	public void setReferenceFileHit(BoardFileVO vo) throws Exception {
		update("reference.setReferenceFileHit", vo);
	}
	
	public void setReferenceHit(int param) throws Exception {
		update("reference.setReferenceHit", param);
	}
	
	public void setReferenceBoard(BoardVO vo) throws Exception {
		insert("reference.setReferenceBoard", vo);
	}
	
	public void setBoardFile(List<BoardFileVO> fileList) throws Exception {
		insert("reference.setBoardFile", fileList);
	}
	
	public void updateReferenceBoard(BoardVO vo) throws Exception {
		update("reference.updateReferenceBoard", vo);
	}
	
	public void deleteReference(int param) throws Exception {
		delete("reference.deleteReference", param);
	}
	
	public void deleteReferenceFile(int param) throws Exception {
		delete("reference.deleteReferenceFile", param);
	}
	
	public void deleteReferenceFileOne(BoardFileVO vo) throws Exception {
		delete("reference.deleteReferenceFileOne", vo);
	}
}
