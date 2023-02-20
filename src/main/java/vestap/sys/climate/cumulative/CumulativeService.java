package vestap.sys.climate.cumulative;

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

public interface CumulativeService {

	public List<EgovMap> selectOptionlist(EgovMap map);

	public List<EgovMap> selectItemlist(EgovMap map);

	public List<EgovMap> selectSidoList();

	public List<EgovMap> selectSigungulist(EgovMap map);

	public List<EgovMap> selectCumulativeList(EgovMap map);

	public EgovMap selectCumulativeSetting(EgovMap map) throws Exception;

	public List<EgovMap> selectCumulativeTotal(EgovMap map);

	public List<EgovMap> selectCumulativeFindIndiNm(EgovMap map);

	public List<EgovMap> selectCumulativeMetaInfo(EgovMap map);

	public List<EgovMap> selectCumulativeRelation(EgovMap map);
	
	public List<EgovMap> selectCumulativeComment(EgovMap map);

	void downloadCumulative(List<EgovMap> list, List<EgovMap> findNm, String sido, String sigungu, EgovMap map, HttpServletResponse response) throws Exception;
	
}
