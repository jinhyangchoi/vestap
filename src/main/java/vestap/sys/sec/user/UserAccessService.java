package vestap.sys.sec.user;

import egovframework.rte.psl.dataaccess.util.EgovMap;

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

public interface UserAccessService {
	
	UserAccessVO getUserInfo(String id) throws Exception;
	
	void updateUserAccess(String param) throws Exception;

	//void insertAccessLog(String user_id);
	void insertAccessLog(EgovMap map);
}
