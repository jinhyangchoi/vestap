package vestap.sys.cmm.main;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;
import vestap.sys.bbs.cmm.BoardVO;

@Repository("mainDAO")
public class MainDAO extends EgovComAbstractDAO {
	
	public List<Map<String, Object>> test() {
		return selectList("commons.test");
	}
	
	public void delete(int param) {
		delete("commons.deleteTest", param);
		throw new RuntimeException("강제 오류!!!");
	}
	
	public List<BoardVO> getNoticeBoard(BoardVO vo) throws Exception {
		return selectList("notice.getNoticeList", vo);
	}
	public List<BoardVO> getFaqBoard(BoardVO vo) throws Exception {
		return selectList("faq.getFaqList", vo);
	}
	public List<BoardVO> getReferenceBoard(BoardVO vo) throws Exception {
		return selectList("reference.getReferenceList", vo);
	}

	public List<EgovMap> selectEstimationLogList(EgovMap map) {
		return selectList("stat.selectEstimationLogList", map);
	}

	public void moveEstimation(EgovMap map) {
		update("estimation.moveEstimation", map);
	}
	
	public void moveEstimationNew(EgovMap map) {
		update("estimation.moveEstimationNew", map);
	}
	
	
}
