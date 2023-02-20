package vestap.adm.ems.service;

import java.util.List;
/*
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.cop.ems.service.SndngMailVO;
*/
import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

/**
 * 발송메일을 상세 조회하는 DAO 클래스
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
 *  
 *  </pre>
 */
@Repository("admSndngMailDetailDAO")
public class AdmSndngMailDetailDAO extends EgovComAbstractDAO {

	/**
	 * 발송메일을 상세 조회한다.
	 * @param vo SndngMailVO
	 * @return SndngMailVO
	 * @exception Exception
	 */
    public AdmSndngMailVO selectSndngMail(AdmSndngMailVO vo) throws Exception {
        return (AdmSndngMailVO)selectOne("admSndngMailDetailDAO.selectSndngMail", vo);
    }

    /**
	 * 발송메일에 첨부된 파일목록을 조회한다.
	 * @param vo SndngMailVO
	 * @return List
	 * @exception
	 */
    public List selectAtchmnFileList(AdmSndngMailVO vo) {
    	return list("admSndngMailDetailDAO.selectAtchmnFileList", vo);
    }
    
    /**
	 * 발송메일을 삭제한다.
	 * @param vo SndngMailVO
	 * @exception
	 */
    public void deleteSndngMail(AdmSndngMailVO vo) throws Exception {
        delete("admSndngMailDetailDAO.deleteSndngMail", vo);
    }
    
    /**
	 * 첨부파일 목록을 삭제한다.
	 * @param vo SndngMailVO
	 * @exception
	 */
    public void deleteAtchmnFileList(AdmSndngMailVO vo) throws Exception {
        delete("admSndngMailDetailDAO.deleteAtchmnFileList", vo);
    }
}
