package vestap.adm.user.management;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.adm.ems.service.AdmSndngMailVO;

public interface AdmUserManagementService {

	List<EgovMap> selectUserList(EgovMap map);

	int insertUser(EgovMap map);

	int updateUserPw(EgovMap map);

	int deleteUser(EgovMap map);

	EgovMap selectManagementSetting(EgovMap map);

	int selectUserIdCheck(EgovMap map);

	List<EgovMap> selectSigungulist(EgovMap map);

	int selectPageCount(EgovMap map);

	int updateUserInfo(EgovMap map);
	
	EgovMap getUserInfo(EgovMap map);
	
	List<EgovMap> selectSearchUserList(EgovMap map);
	
	int selectSearchPageCount(EgovMap map);
	
	@Transactional
	void userGovDocDownloadAction(HttpServletResponse response, EgovMap map) throws Exception;

	/**
	 * 202106	사용자승인상태 메일발송
	 * @param admSndngMailVO
	 * @return
	 * @throws Exception
	 */
	boolean insertAdmUserRegistSndngMail(AdmSndngMailVO vo) throws Exception;

}
