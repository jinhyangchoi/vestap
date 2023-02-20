package vestap.sys.sec.user;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

/**
 * 사용자 로그인, 회원가입, 세션 처리 등
 * --------------------------------------------------
 * 		수정일			수정자			수정내용
 * --------------------------------------------------
 * 	2018.08.13			vestap개발		최초 작성
 * --------------------------------------------------
 * @author vestap 개발
 * @since 2018.08.13
 *
 */

@Repository("userAccessDAO")
public class UserAccessDAO extends EgovComAbstractDAO {
	
	protected UserAccessVO getUserInfo(String param) throws Exception {
		return selectOne("user.getUserInfo", param);
	}
	
	protected void updateUserAccess(String param) throws Exception {
		update("user.updateUserAccess", param);
	}

	/*public void insertAccessLog(String param) {
		insert("user.insertAccessLog",param);
	}*/
	
	public void insertAccessLog(EgovMap map) {
		insert("user.insertAccessLog",map);
	}
}
