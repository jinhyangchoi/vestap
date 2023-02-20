package vestap.sys.bbs.cmm;

import java.io.Serializable;
import java.util.List;

public class BoardVO implements Serializable {
	
	private static final long serialVersionUID = 5437541685982621538L;
	
	/** 사용자 등급 */
	private String userAuth;
	
	/** 페이징: 시작 Row*/
	private int startRow;
	
	/** 페이징: 마지막 Row*/
	private int endRow;
	
	/** 게시판 검색어*/
	private String keyword;
	
	/** 게시판 검색어 카테고리*/
	private String category;

	/** 게시판 rnum*/
	private int RNUM;
	
	/** 게시판 index*/
	private int bbs_idx;
	
	/** 게시판 조회수*/
	private int bbs_hit;
	
	/** 게시판 제목*/
	private String bbs_title;

	/** 게시판 제목2*/
	private String bbs_title2;
	
	/** 게시판 작성자*/
	private String bbs_writer;
	
	/** 게시판 내용*/
	private String bbs_content;
	
	/** 게시판 작성일*/
	private String bbs_regdate;
	
	private List<String> keywordList;

	/** 게시판 답글 원본 번호*/
	private int bbs_ref;
	
	/** 게시판 답글 순서*/
	private int bbs_depth;

	/** 게시판 삭제유무*/
	private String bbs_use;
	
	public String getUserAuth() {
		return userAuth;
	}

	public void setUserAuth(String userAuth) {
		this.userAuth = userAuth;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	public int getRNUM() {
		return RNUM;
	}

	public void setRNUM(int RNUM) {
		this.RNUM = RNUM;
	}
	public int getBbs_idx() {
		return bbs_idx;
	}

	public void setBbs_idx(int bbs_idx) {
		this.bbs_idx = bbs_idx;
	}

	public int getBbs_hit() {
		return bbs_hit;
	}

	public void setBbs_hit(int bbs_hit) {
		this.bbs_hit = bbs_hit;
	}

	public String getBbs_title() {
		return bbs_title;
	}

	public void setBbs_title(String bbs_title) {
		this.bbs_title = bbs_title;
	}

	public String getBbs_title2() {
		return bbs_title2;
	}

	public void setBbs_title2(String bbs_title2) {
		this.bbs_title2 = bbs_title2;
	}

	public String getBbs_writer() {
		return bbs_writer;
	}

	public void setBbs_writer(String bbs_writer) {
		this.bbs_writer = bbs_writer;
	}

	public String getBbs_content() {
		return bbs_content;
	}

	public void setBbs_content(String bbs_content) {
		this.bbs_content = bbs_content;
	}

	public String getBbs_regdate() {
		return bbs_regdate;
	}

	public void setBbs_regdate(String bbs_regdate) {
		this.bbs_regdate = bbs_regdate;
	}

	public List<String> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(List<String> keywordList) {
		this.keywordList = keywordList;
	}
	
	public int getBbs_ref() {
		return bbs_ref;
	}

	public void setBbs_ref(int bbs_ref) {
		this.bbs_ref = bbs_ref;
	}
	public int getBbs_depth() {
		return bbs_depth;
	}

	public void setBbs_depth(int bbs_depth) {
		this.bbs_depth = bbs_depth;
	}

	public String getBbs_use() {
		return bbs_use;
	}

	public void setBbs_use(String bbs_use) {
		this.bbs_use = bbs_use;
	}
}
