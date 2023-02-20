package vestap.adm.stat.estimation;

import java.util.*;

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

public interface AdmEstimationStatService {

	public List<EgovMap> selectOptionlist(EgovMap map);

	public List<EgovMap> selectEstimationList(EgovMap map);

	public List<EgovMap> selectEstimationTotalList(EgovMap map);

	void downloadLogList(List<EgovMap> list, List<EgovMap> totalList, String titleText, String fieldNm,  EgovMap map, HttpServletResponse response) throws Exception;
	
}
