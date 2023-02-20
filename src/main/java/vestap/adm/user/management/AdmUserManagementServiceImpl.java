package vestap.adm.user.management;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.adm.ems.service.AdmSndngMailRegistDAO;
import vestap.adm.ems.service.AdmSndngMailService;
import vestap.adm.ems.service.AdmSndngMailVO;
import vestap.egov.cmm.util.EgovStringUtil;
import vestap.sys.climate.estimation.EstimationService;
import vestap.sys.cmm.util.FileDownloadComponent;
import vestap.sys.cmm.util.FileUploadUser;

@Service("admUserManagementService")
public class AdmUserManagementServiceImpl extends EgovAbstractServiceImpl implements AdmUserManagementService{

	@Resource(name = "admUserManagementDAO")
	private AdmUserManagementDAO admUserManagementDAO;
	
	@Resource(name = "estimationService")
	private EstimationService estimationService;
	
	@Resource(name = "fileDownloadComponent")
	private FileDownloadComponent downComp;
	
	@Resource(name = "fileUploadUser")
	private FileUploadUser fileUploadUser;
	
	/** SndngMailRegistDAO */
    @Resource(name="admSndngMailRegistDAO")
    private AdmSndngMailRegistDAO admSndngMailRegistDAO;
    
    /** Message ID Generation */
    @Resource(name = "admSndngMailService")
    private AdmSndngMailService admSndngMailService;
    
	@Override
	public List<EgovMap> selectUserList(EgovMap map) {
		return this.admUserManagementDAO.selectUserList(map);
	}

	@Override
	public int insertUser(EgovMap map) {
		return this.admUserManagementDAO.insertUser(map);
	}

	@Override
	public int updateUserPw(EgovMap map) {
		return this.admUserManagementDAO.updateUserPw(map);
	}

	@Override
	public int deleteUser(EgovMap map) {
		return this.admUserManagementDAO.deleteUser(map);
	}

	@Override
	public EgovMap selectManagementSetting(EgovMap map) {

		EgovMap resultMap = new EgovMap();
		
		/* 행정구역 시도 세팅 */
		List<EgovMap> sidoList = this.estimationService.selectSidoList();
		resultMap.put("sidoList", sidoList);
		
		return resultMap;
	}

	@Override
	public int selectUserIdCheck(EgovMap map) {
		return this.admUserManagementDAO.selectUserIdCheck(map);
	}

	@Override
	public List<EgovMap> selectSigungulist(EgovMap map) {
		 return this.estimationService.selectSigungulist(map);
	}


	@Override
	public int selectPageCount(EgovMap map) {
		return this.admUserManagementDAO.selectPageCount(map);
	}

	@Override
	public int updateUserInfo(EgovMap map) {
		return this.admUserManagementDAO.updateUserInfo(map);
	}
	
	@Override
	public EgovMap getUserInfo(EgovMap map){
		return this.admUserManagementDAO.getUserInfo(map);
	}
	
	@Override
	public List<EgovMap> selectSearchUserList(EgovMap map) {
		return this.admUserManagementDAO.selectSearchUserList(map);
	}
	
	@Override
	public int selectSearchPageCount(EgovMap map) {
		return this.admUserManagementDAO.selectSearchPageCount(map);
	}
	
	@Override
	public void userGovDocDownloadAction(HttpServletResponse response, EgovMap map) throws Exception {
		/**
		 * 파일 다운로드를 위해 파일 정보 불러오기
		 */
		this.downComp.userGovDocDownloadAction(response, map);
	}

	/**
	 * 202106  사용자승인상태 메일발송
	 * @param admSndngMailVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean insertAdmUserRegistSndngMail(AdmSndngMailVO vo) throws Exception {
		
		AdmSndngMailVO mssageVO = new AdmSndngMailVO();
		mssageVO = admSndngMailRegistDAO.selectMssageId(mssageVO);
		int mssageId = mssageVO.getMssageId();
		vo.setMssageId(mssageId);					//메일 일련번호
    	
    	// 1-1.발송메일  데이터를 만든다.
//    	AdmSndngMailVO mailVO = new AdmSndngMailVO();
    	
    	/* 컨트롤러에서 입력된 값
    	admSndngMailVO.setSj(userId+"님, 기후변화취약성 평가 지원 도구 시스템 사용자권한이 승인되었습니다.");	//제목
		admSndngMailVO.setRecptnPerson(userEmail);		                                //수신자
		admSndngMailVO.setUserId(userId);				                                //아이디
		admSndngMailVO.setUserPwReq(userPwReq);			                                //패스워드
		admSndngMailVO.setDsptchPerson(userPwReq);		                                //발신자
    	 */
    	
    	//메일내용만들기 S ############################################################
    	String userPwReq = EgovStringUtil.nullConvert(vo.getUserPwReq());
    	String useYnBefore = EgovStringUtil.nullConvert(vo.getUseYnBefore());
    	String useYn = EgovStringUtil.nullConvert(vo.getUseYn());
    	
    	StringBuffer strBufEmailCn = new StringBuffer();
    	strBufEmailCn.append("기후변화취약성 평가 지원 도구 시스템입니다.\n\n");
    	strBufEmailCn.append("신청해주신 아이디 "+vo.getRcverId()+" 변경되었습니다.\n");
    	
    	if( !("").equals(userPwReq) ){
    		strBufEmailCn.append("임시 비밀번호는 "+vo.getUserPwReq()+"입니다.\n");
    	}
    	
    	if( !(useYnBefore).equals(useYn) ){
    		if( ("Y").equals(useYn) ){
    			strBufEmailCn.append("현재 사용가능 상태입니다.\n\n");
    		} else if( ("N").equals(useYn) ){
    			strBufEmailCn.append("현재 사용불가 상태입니다.\n\n");
    		} else if( ("D").equals(useYn) ){
    			strBufEmailCn.append("현재 승인대기 상태입니다.\n\n");
    		}
    	}
    	strBufEmailCn.append("\n감사합니다.");
    	//메일내용만들기 E ############################################################
    	
    	vo.setEmailCn(strBufEmailCn.toString());	//내용
    	vo.setSndngResultCode("R");                 // 발송결과 요청 (R:요청, F:실패, C:완료)
    	
    	// 1-3.발송메일을 DB등록
    	int insertSndngMailResult = admSndngMailRegistDAO.insertSndngMail(vo);
    	
    	// 1-4.메일을 발송액션
    	boolean sendingMailResult = admSndngMailService.sndngMail(vo);
    	
    	if(!sendingMailResult) {
    		vo.setSndngResultCode("F");	// 발송결과 실패
        	admSndngMailRegistDAO.updateSndngMail(vo);	// 발송상태를 DB에 업데이트 한다.
    		return false;
    	}
    	
    	return true;
	}

}
