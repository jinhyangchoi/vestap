package vestap.sys.sec.user;

import org.springframework.stereotype.Repository;

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

@Repository("userApplyDAO")
public class UserApplyDAO extends EgovComAbstractDAO {
	
	public int userApplyChk(UserApplyVO vo) throws Exception {
		return selectOne("userApplyDAO.userApplyChk", vo);
	}
	
	public int userApplyInsert(UserApplyVO vo) throws Exception {
		return insert("userApplyDAO.userApplyInsert",vo);
	}

	public UserApplyVO userApplyFileIdx(UserApplyVO vo) throws Exception {
		return selectOne("userApplyDAO.userApplyFileIdx", vo);
	}

	public void userApplyInsertFile(UserApplyVO vo) throws Exception {
		insert("userApplyDAO.userApplyInsertFile", vo);
	}

	public int userApplyUpdateFile(UserApplyVO vo) throws Exception {
		return update("userApplyDAO.userApplyUpdateFile", vo);
	}

	public int userApplyUpdate(UserApplyVO vo) throws Exception {
		return update("userApplyDAO.userApplyUpdate", vo);
	}

	public UserApplyVO userApplyExistFileChk(UserApplyVO vo) throws Exception {
		return selectOne("userApplyDAO.userApplyExistFileChk", vo);
	}

	public void userApplyDeleteFile(UserApplyVO vo) throws Exception {
		delete("userApplyDAO.userApplyDeleteFile", vo);
	}

}
