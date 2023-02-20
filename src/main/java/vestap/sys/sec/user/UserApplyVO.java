package vestap.sys.sec.user;

import java.io.Serializable;

/**
 * @Class Name : LoginVO.java
 * @Description : Login VO class
 * @Modification Information
 * @
 * @  수정일         수정자                   수정내용
 * @ -------    --------    ---------------------------
 * @ 2009.03.03    박지욱          최초 생성
 *
 *  @author 공통서비스 개발팀 박지욱
 *  @since 2009.03.03
 *  @version 1.0
 *  @see
 *  
 */
public class UserApplyVO implements Serializable{
	
	private static final long serialVersionUID = -8274004534207618049L;
	
	/** 유저아이디 */
	private String userId;
	/** 유저비밀번호 */
	private String userPw;
	/** 유저이름 */
	private String userNm;
	/** 이름 */
	private String userRcp;
	/** 이름 */
	private String userScr;
	/** 이름 */
	private String userFld;
	/** 유저권한 */
	private String userAuth;
	/** 유저지역 */
	private String userDist;
	/** 유저 */
	private String userItem;
	/** 유저 */
	private String userYear;
	/** 유저액세스 */
	private String userAccess;
	/** 유저지역 */
	private String userDistrict;
	/** 유저IP */
	private String userIp;
	/** 유저이메일주소 */
	private String userEmail;
	/** 공무원여부 */
	private String userGovYn;
	/** 사용여부 */
	private String useYn;
	/** 접수일시 */
	private String rcptDt;
	/** 승인일시 */
	private String aprvDt;
	/** 승인일시 */
	private String fileAtchYn;
	/** 파일 IDX */
	private int fileIdx;
	/** 게시판명 */
	private String fileCatgnm;
	/** 원본 파일명 */
	private String orgFileNm;
	/** 저장 파일명 */
	private String stdFileNm;
	/** 파일 경로 */
	private String filePath;
	/** 최초등록자 */
	private String frstRegUser;
	/** 최초등록일자 */
	private String frstRegDt;
	/** 유저소속 */
	private String userOrgNm;
	/** 접속만료일 */
	private String userExpire;
	/** 사용여부 */
//	private String use_yn;
	/** 최종수정일시 */
	private String mdfcnDt;
	/** 업로드 공문파일 */
	private String uploadFile;
	/** 이메일수신동의여부 */
	private String emlRcptnAgreYn;
	/** 기존파일명 */
	private String beforeFileNm;
	/** 보안서약동의여부 */
	private String securityAgreeYn;
	/** 계정 사용목적 코드 */
	private String purposeUseCd;
	/** 계정 사용목적 내용 */
	private String purposeUseNm;
	
	public String getPurposeUseNm() {
		return purposeUseNm;
	}
	public void setPurposeUseNm(String purposeUseNm) {
		this.purposeUseNm = purposeUseNm;
	}
	public String getSecurityAgreeYn() {
		return securityAgreeYn;
	}
	public void setSecurityAgreeYn(String securityAgreeYn) {
		this.securityAgreeYn = securityAgreeYn;
	}
	public String getPurposeUseCd() {
		return purposeUseCd;
	}
	public void setPurposeUseCd(String purposeUseCd) {
		this.purposeUseCd = purposeUseCd;
	}
	public String getBeforeFileNm() {
		return beforeFileNm;
	}
	public void setBeforeFileNm(String beforeFileNm) {
		this.beforeFileNm = beforeFileNm;
	}
	public String getEmlRcptnAgreYn() {
		return emlRcptnAgreYn;
	}
	public void setEmlRcptnAgreYn(String emlRcptnAgreYn) {
		this.emlRcptnAgreYn = emlRcptnAgreYn;
	}
	public String getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getMdfcnDt() {
		return mdfcnDt;
	}
	public void setMdfcnDt(String mdfcnDt) {
		this.mdfcnDt = mdfcnDt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getUserRcp() {
		return userRcp;
	}
	public void setUserRcp(String userRcp) {
		this.userRcp = userRcp;
	}
	public String getUserScr() {
		return userScr;
	}
	public void setUserScr(String userScr) {
		this.userScr = userScr;
	}
	public String getUserFld() {
		return userFld;
	}
	public void setUserFld(String userFld) {
		this.userFld = userFld;
	}
	public String getUserAuth() {
		return userAuth;
	}
	public void setUserAuth(String userAuth) {
		this.userAuth = userAuth;
	}
	public String getUserDist() {
		return userDist;
	}
	public void setUserDist(String userDist) {
		this.userDist = userDist;
	}
	public String getUserItem() {
		return userItem;
	}
	public void setUserItem(String userItem) {
		this.userItem = userItem;
	}
	public String getUserYear() {
		return userYear;
	}
	public void setUserYear(String userYear) {
		this.userYear = userYear;
	}
	public String getUserAccess() {
		return userAccess;
	}
	public void setUserAccess(String userAccess) {
		this.userAccess = userAccess;
	}
	public String getUserDistrict() {
		return userDistrict;
	}
	public void setUserDistrict(String userDistrict) {
		this.userDistrict = userDistrict;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserGovYn() {
		return userGovYn;
	}
	public void setUserGovYn(String userGovYn) {
		this.userGovYn = userGovYn;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getRcptDt() {
		return rcptDt;
	}
	public void setRcptDt(String rcptDt) {
		this.rcptDt = rcptDt;
	}
	public String getAprvDt() {
		return aprvDt;
	}
	public void setAprvDt(String aprvDt) {
		this.aprvDt = aprvDt;
	}
	public String getFileAtchYn() {
		return fileAtchYn;
	}
	public void setFileAtchYn(String fileAtchYn) {
		this.fileAtchYn = fileAtchYn;
	}
	public int getFileIdx() {
		return fileIdx;
	}
	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}
	public String getFileCatgnm() {
		return fileCatgnm;
	}
	public void setFileCatgnm(String fileCatgnm) {
		this.fileCatgnm = fileCatgnm;
	}
	public String getOrgFileNm() {
		return orgFileNm;
	}
	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}
	public String getStdFileNm() {
		return stdFileNm;
	}
	public void setStdFileNm(String stdFileNm) {
		this.stdFileNm = stdFileNm;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFrstRegUser() {
		return frstRegUser;
	}
	public void setFrstRegUser(String frstRegUser) {
		this.frstRegUser = frstRegUser;
	}
	public String getFrstRegDt() {
		return frstRegDt;
	}
	public void setFrstRegDt(String frstRegDt) {
		this.frstRegDt = frstRegDt;
	}
	public String getUserOrgNm() {
		return userOrgNm;
	}
	public void setUserOrgNm(String userOrgNm) {
		this.userOrgNm = userOrgNm;
	}
	public String getUserExpire() {
		return userExpire;
	}
	public void setUserExpire(String userExpire) {
		this.userExpire = userExpire;
	}
	
}
