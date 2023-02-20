package vestap.sys.bbs.cmm;

import java.io.Serializable;

public class BoardFileVO implements Serializable {
	
	private static final long serialVersionUID = -6934005424616030741L;

	/** 게시판 순서 */
	private int RNUM;
	
	/** 게시판 IDX */
	private int bbs_idx;
	
	/** 게시판명 */
	private String bbs_nm;
	
	/** 원본 파일명 */
	private String org_file_nm;
	
	/** 저장 파일명 */
	private String std_file_nm;
	
	/** 파일 경로 */
	private String file_path;
	
	/** File 다운로드 수 */
	private int file_hit;


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

	public String getBbs_nm() {
		return bbs_nm;
	}

	public void setBbs_nm(String bbs_nm) {
		this.bbs_nm = bbs_nm;
	}

	public String getOrg_file_nm() {
		return org_file_nm;
	}

	public void setOrg_file_nm(String org_file_nm) {
		this.org_file_nm = org_file_nm;
	}

	public String getStd_file_nm() {
		return std_file_nm;
	}

	public void setStd_file_nm(String std_file_nm) {
		this.std_file_nm = std_file_nm;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public int getFile_hit() {
		return file_hit;
	}

	public void setFile_hit(int file_hit) {
		this.file_hit = file_hit;
	}
	
}
