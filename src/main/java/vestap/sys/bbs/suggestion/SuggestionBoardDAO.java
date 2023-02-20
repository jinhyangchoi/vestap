package vestap.sys.bbs.suggestion;

import java.util.List;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;
import vestap.sys.bbs.cmm.BoardFileVO;
import vestap.sys.bbs.cmm.BoardVO;

@Repository("suggestionBoardDAO")
public class SuggestionBoardDAO extends EgovComAbstractDAO {
	
	public int isUserBoard(BoardVO vo) throws Exception {
		return selectOne("suggestion.isUserBoard", vo);
	}
	
	public List<BoardVO> getSuggestionBoard(BoardVO vo) throws Exception {
		return selectList("suggestion.getSuggestionBoard", vo);
	}
	
	public int getSuggestionTotCnt(BoardVO vo) throws Exception {
		return selectOne("suggestion.getSuggestionTotCnt", vo);
	}
	
	public BoardVO getSuggestionContent(BoardVO vo) throws Exception {
		return selectOne("suggestion.getSuggestionContent", vo);
	}
	
	public List<BoardFileVO> getSuggestionFile(int param) throws Exception {
		return selectList("suggestion.getSuggestionFile", param);
	}
	
	public BoardFileVO getSuggestionFileInfo(BoardFileVO vo) throws Exception {
		return selectOne("suggestion.getSuggestionFileInfo", vo);
	}
	
	public void setSuggestionFileHit(BoardFileVO vo) throws Exception {
		update("suggestion.setSuggestionFileHit", vo);
	}
	
	public void setSuggestionHit(BoardVO vo) throws Exception {
		update("suggestion.setSuggestionHit", vo);
	}
	
	public void setSuggestionBoard(BoardVO vo) throws Exception {
		insert("suggestion.setSuggestionBoard", vo);
	}
	
	public void setBoardFile(List<BoardFileVO> fileList) throws Exception {
		insert("suggestion.setBoardFile", fileList);
	}
	
	public void updateSuggestionBoard(BoardVO vo) throws Exception {
		update("suggestion.updateSuggestionBoard", vo);
	}
	
	public void deleteSuggestion(BoardVO vo) throws Exception {
		delete("suggestion.deleteSuggestion", vo);
	}
	
	public void deleteSuggestionFile(int param) throws Exception {
		delete("suggestion.deleteSuggestionFile", param);
	}
	
	public void deleteSuggestionFileOne(BoardFileVO vo) throws Exception {
		delete("suggestion.deleteSuggestionFileOne", vo);
	}
}
