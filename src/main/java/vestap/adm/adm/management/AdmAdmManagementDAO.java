package vestap.adm.adm.management;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("admAdmManagementDAO")
public class AdmAdmManagementDAO extends EgovComAbstractDAO{
	
	public List<EgovMap> selectAdminList(EgovMap map){
		return selectList("adminManagement.selectAdminList", map);
	}
	
	public int selectPageCount(EgovMap map){
		return selectOne("adminManagement.selectPageCount", map);
	}
	
	public int selectAccessPageCnt(EgovMap map){
		return selectOne("adminManagement.selectAccessPageCnt", map);
	}
	
	public int selectReqestLogCnt(EgovMap map){
		return selectOne("adminManagement.selectReqestLogCnt", map);
	}
	
	public int selectAdminIdCheck(EgovMap map){
		return selectOne("adminManagement.selectAdminIdCheck", map);
	}
	
	public int insertAdmin(EgovMap map){
		return insert("adminManagement.insertAdmin",map);
	}
	
	public int deleteAdmin(EgovMap map){
		return delete("adminManagement.deleteAdmin",map);
	}
	
	public EgovMap getAdminInfo(EgovMap map){
		return selectOne("adminManagement.getAdminInfo",map);
	}
	
	public List<EgovMap> selectAdminAccessLogList(EgovMap map){
		return selectList("adminManagement.selectAdminAccessLogList", map);
	}
	
	public List<EgovMap> selectAdminIdList(EgovMap map){
		return selectList("adminManagement.selectAdminIdList",map);
	}
	
	public int insertRequestLog(EgovMap map){
		return insert("adminManagement.insertRequestLog",map);
	}
	
	public List<EgovMap> selectAdminRequestLogList(EgovMap map){
		return selectList("adminManagement.selectAdminRequestLogList", map);
	}
	
	public List<EgovMap> selectRefcode(){
		return selectList("adminManagement.selectRefcode");
	}
	
	public EgovMap getNoticeIdxNm(EgovMap map){
		return selectOne("adminManagement.getNoticeIdxNm",map);
	}
	
	public EgovMap getFaqIdxNm(EgovMap map){
		return selectOne("adminManagement.getFaqIdxNm",map);
	}
	
	public EgovMap getSuggestionIdxNm(EgovMap map){
		return selectOne("adminManagement.getSuggestionIdxNm",map);
	}
	
	public EgovMap getReferenceIdxNm(EgovMap map){
		return selectOne("adminManagement.getReferenceIdxNm",map);
	}
}
