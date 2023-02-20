package vestap.adm.stat.estimation;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

/**
 * 관리자 - 접속통계 -------------------------------------------------- 수정일 수정자
 * 수정내용 -------------------------------------------------- 2018.11.19 페이지 생성
 * 최초 작성 --------------------------------------------------
 * 
 * @author vestap 개발
 * @since 2018.11.19
 *
 */

@Repository("admEstimationStatDAO")
public class AdmEstimationStatDAO extends EgovComAbstractDAO {

	public List<EgovMap> selectAccessLogList(EgovMap map) {
		return selectList("stat.selectAccessLogList", map);
	}

	public List<EgovMap> selectOptionlist(EgovMap map) {
		return selectList("stat.selectOptionlist", map);
	}

	public List<EgovMap> selectEstimationList(EgovMap map) {
		return selectList("stat.selectEstimationList", map);
	}
	
	public List<EgovMap> selectEstimationTotalList(EgovMap map) {
		return selectList("stat.selectEstimationTotalList", map);
	}
}
