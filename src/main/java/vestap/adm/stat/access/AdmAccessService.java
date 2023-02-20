package vestap.adm.stat.access;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 기후변화 취약성 > 취약성평가
 * --------------------------------------------------
 * 		수정일			수정자			수정내용
 * --------------------------------------------------
 * 	2018.10.31			vestap개발		최초 작성
 * --------------------------------------------------
 * @author vestap 개발
 * @since 2018.10.31
 *
 */

public interface AdmAccessService {

	public List<EgovMap> selectAccessLogList(EgovMap map);
	
	public List<EgovMap> selectMonthAccessLogList(EgovMap map);
	
	void downloadLogList(List<EgovMap> logList, EgovMap map, HttpServletResponse response) throws Exception;
	
	void downloadMonthLogList(List<EgovMap> logList, EgovMap map, HttpServletResponse response) throws Exception;
	
}
