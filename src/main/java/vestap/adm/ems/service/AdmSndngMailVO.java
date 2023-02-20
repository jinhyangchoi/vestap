package vestap.adm.ems.service;

/**
 * 발송메일 VO 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.12
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.12  박지욱          최초 생성 
 *  2011.12.06  이기하          첨부파일경로(fileStreCours), 첨부파일이름(orignlFileNm) 추가
 *  
 *  </pre>
 */
public class AdmSndngMailVO {
	
    /** 현재페이지 */
    private int pageIndex = 1;
    private int currentPageNo = 1;
    
    /** 페이지갯수 */
    private int pageUnit = 10;
    
    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;
    
	/** 메세지ID */
//	private String mssageId;
	private int mssageId;
	/** 발신자 */
	private String dsptchPerson;
	/** 수신자 */
	private String recptnPerson;
	/** 수신자ID */
	private String rcverId;
	/** 수신자명 */
	private String rcverNm;
	/** 제목 */
	private String sj;
	/** 발송결과코드ID */
	private String sndngResultCode;
	/** 발송결과코드명 */
	private String sndngResultNm;
	/** 메일내용 */
	private String emailCn;
	/** 첨부파일ID */
	private String atchFileId;
	/** 첨부파일경로 */
	private String fileStreCours;
	/** 첨부파일이름 */
	private String orignlFileNm;
	/** 발신일자 */
	private String sndngDe;
	/** 첨부파일ID 리스트 */
	private String atchFileIdList;
	/** 발송요청XML내용 */
	private String xmlContent;
	/** 팝업링크여부(Y/N) */
	private String link;
	/** 사용자ID */
	private String userId;
	/** 사용자PASSWD */
	private String userPwReq;
	/** 사용자명 */
	private String userNm;
	/** 현재 사용상태 */
	private String useYn;
	/** 이전 사용상태 */
	private String useYnBefore;
	/** 이전 사용상태 */
	private String sndr;
	/** 수신자이메일 */
	private String rcver;
	
	/** 페이징: 시작 Row*/
	private int startRow;
	
	/** 페이징: 마지막 Row*/
	private int endRow;
	
	/** 게시판 검색어*/
	private String keyword;
	
	/** 검색조건 */
    private String searchCondition = "";
    
    /** 검색조건 */
    private String searchResultCode = "";
    
    /** 검색Keyword */
    private String searchKeyword = "";
    
    /** 검색사용여부 */
    private String searchUseYn = "";
    private int RNUM;

    private String detailFormSearchCondition = "";
    private String detailFormSearchKeyword ="";
    private String detailFormSearchResultCode ="";
    
	public String getDetailFormSearchResultCode() {
		return detailFormSearchResultCode;
	}

	public void setDetailFormSearchResultCode(String detailFormSearchResultCode) {
		this.detailFormSearchResultCode = detailFormSearchResultCode;
	}

	public String getSearchResultCode() {
		return searchResultCode;
	}

	public void setSearchResultCode(String searchResultCode) {
		this.searchResultCode = searchResultCode;
	}

	public String getDetailFormSearchCondition() {
		return detailFormSearchCondition;
	}

	public void setDetailFormSearchCondition(String detailFormSearchCondition) {
		this.detailFormSearchCondition = detailFormSearchCondition;
	}

	public String getDetailFormSearchKeyword() {
		return detailFormSearchKeyword;
	}

	public void setDetailFormSearchKeyword(String detailFormSearchKeyword) {
		this.detailFormSearchKeyword = detailFormSearchKeyword;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getRNUM() {
		return RNUM;
	}

	public void setRNUM(int rNUM) {
		RNUM = rNUM;
	}

	public int getMssageId() {
		return mssageId;
	}

	public void setMssageId(int mssageId) {
		this.mssageId = mssageId;
	}

	public String getDsptchPerson() {
		return dsptchPerson;
	}

	public void setDsptchPerson(String dsptchPerson) {
		this.dsptchPerson = dsptchPerson;
	}

	public String getRecptnPerson() {
		return recptnPerson;
	}

	public void setRecptnPerson(String recptnPerson) {
		this.recptnPerson = recptnPerson;
	}

	public String getRcverId() {
		return rcverId;
	}

	public void setRcverId(String rcverId) {
		this.rcverId = rcverId;
	}

	public String getRcverNm() {
		return rcverNm;
	}

	public void setRcverNm(String rcverNm) {
		this.rcverNm = rcverNm;
	}

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public String getSndngResultCode() {
		return sndngResultCode;
	}

	public void setSndngResultCode(String sndngResultCode) {
		this.sndngResultCode = sndngResultCode;
	}

	public String getSndngResultNm() {
		return sndngResultNm;
	}

	public void setSndngResultNm(String sndngResultNm) {
		this.sndngResultNm = sndngResultNm;
	}

	public String getEmailCn() {
		return emailCn;
	}

	public void setEmailCn(String emailCn) {
		this.emailCn = emailCn;
	}

	public String getAtchFileId() {
		return atchFileId;
	}

	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}

	public String getFileStreCours() {
		return fileStreCours;
	}

	public void setFileStreCours(String fileStreCours) {
		this.fileStreCours = fileStreCours;
	}

	public String getOrignlFileNm() {
		return orignlFileNm;
	}

	public void setOrignlFileNm(String orignlFileNm) {
		this.orignlFileNm = orignlFileNm;
	}

	public String getSndngDe() {
		return sndngDe;
	}

	public void setSndngDe(String sndngDe) {
		this.sndngDe = sndngDe;
	}

	public String getAtchFileIdList() {
		return atchFileIdList;
	}

	public void setAtchFileIdList(String atchFileIdList) {
		this.atchFileIdList = atchFileIdList;
	}

	public String getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwReq() {
		return userPwReq;
	}

	public void setUserPwReq(String userPwReq) {
		this.userPwReq = userPwReq;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getUseYnBefore() {
		return useYnBefore;
	}

	public void setUseYnBefore(String useYnBefore) {
		this.useYnBefore = useYnBefore;
	}

	public String getSndr() {
		return sndr;
	}

	public void setSndr(String sndr) {
		this.sndr = sndr;
	}

	public String getRcver() {
		return rcver;
	}

	public void setRcver(String rcver) {
		this.rcver = rcver;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getSearchUseYn() {
		return searchUseYn;
	}

	public void setSearchUseYn(String searchUseYn) {
		this.searchUseYn = searchUseYn;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

}
