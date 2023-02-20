package vestap.adm.adm.management;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface AdmAdmManagementService {

	List<EgovMap> selectAdminList(EgovMap map);
	
	int selectPageCount(EgovMap map);
	
	int selectAccessPageCnt(EgovMap map);
	
	int selectReqestLogCnt(EgovMap map);
	
	int selectAdminIdCheck(EgovMap map);
	
	int insertAdmin(EgovMap map);
	
	int deleteAdmin(EgovMap map);
	
	EgovMap getAdminInfo(EgovMap map);
	
	List<EgovMap> selectAdminAccessLogList(EgovMap map);
	
	List<EgovMap> selectRequestLogList(EgovMap map);
	
	List<EgovMap> selectAdminIdList(EgovMap map);
	
	void downloadAdminAccessLogList(List<EgovMap> logList, EgovMap map, HttpServletResponse response) throws Exception;
	
	int insertRequestLog(EgovMap map);
	
	void downloadAdminRequestLogList(List<EgovMap> logList, EgovMap map, HttpServletResponse response) throws Exception;
	
	List<EgovMap> selectRefcode();
	
	EgovMap getNoticeIdxNm(EgovMap map);
	
	EgovMap getFaqIdxNm(EgovMap map);
	
	EgovMap getSuggestionIdxNm(EgovMap map);
	
	EgovMap getReferenceIdxNm(EgovMap map);
	
}
