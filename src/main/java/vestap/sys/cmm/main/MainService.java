package vestap.sys.cmm.main;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.sys.bbs.cmm.BoardVO;

@Transactional
public interface MainService {
	
	@Transactional
	public List<Map<String, Object>> test();
	
	List<BoardVO> getNoticeBoard(BoardVO vo) throws Exception;

	List<BoardVO> getFaqBoard(BoardVO vo) throws Exception;

	List<BoardVO> getReferenceBoard(BoardVO vo) throws Exception;

	public List<EgovMap> selectEstimationLogList(EgovMap map);

	public void moveEstimation(EgovMap map);
	
	public void moveEstimationNew(EgovMap map) throws Exception;
}
