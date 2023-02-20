package vestap.adm.user.management;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;
import vestap.sys.sec.user.UserApplyVO;

@Repository("admUserManagementDAO")
public class AdmUserManagementDAO extends EgovComAbstractDAO{

	public List<EgovMap> selectUserList(EgovMap map) {
		return selectList("userManagement.selectUserList",map);
	}

	public int updateUserPw(EgovMap map) {
		return update("userManagement.updateUserPw",map);
	}

	public int deleteUser(EgovMap map) {
		return delete("userManagement.deleteUser",map);
	}

	public int selectUserIdCheck(EgovMap map) {
		return selectOne("userManagement.selectUserIdCheck",map);
	}

	public int insertUser(EgovMap map) {
		return insert("userManagement.insertUser",map);
	}

	public int selectPageCount(EgovMap map) {
		return selectOne("userManagement.selectPageCount",map);
	}

	public int updateUserInfo(EgovMap map) {
		return update("userManagement.updateUserInfo",map);
	}
	
	public EgovMap getUserInfo(EgovMap map){
		return selectOne("userManagement.getUserInfo",map);
	}
	
	public List<EgovMap> selectSearchUserList(EgovMap map) {
		return selectList("userManagement.selectSearchUserList",map);
	}
	
	public int selectSearchPageCount(EgovMap map) {
		return selectOne("userManagement.selectSearchPageCount",map);
	}

}
