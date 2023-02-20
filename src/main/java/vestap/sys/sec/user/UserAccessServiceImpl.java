package vestap.sys.sec.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
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

@Service("userAccessService")
public class UserAccessServiceImpl extends EgovAbstractServiceImpl implements UserAccessService {
	
	@Resource(name = "userAccessDAO")
	private UserAccessDAO DAO;
	
	@Override
	public UserAccessVO getUserInfo(String param) throws Exception {
		return this.DAO.getUserInfo(param);
	}

	@Override
	public void updateUserAccess(String param) throws Exception {
		this.DAO.updateUserAccess(param);
	}

	/*@Override
	public void insertAccessLog(String param) {
		this.DAO.insertAccessLog(param);
	}*/
	
	@Override
	public void insertAccessLog(EgovMap map) {
		this.DAO.insertAccessLog(map);
	}
	
}
