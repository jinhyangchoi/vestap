package vestap.adm.ems.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

/**
 * 발송메일 내역을 조회하는 DAO 클래스
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
@Repository("admSndngMailDtlsDAO")
public class AdmSndngMailDtlsDAO extends EgovComAbstractDAO {

	/**
	 * 발송메일 목록을 조회한다.
	 * @param vo AdmSndngMailVO
	 * @return List
	 * @exception Exception
	 */
    public List selectSndngMailList(AdmSndngMailVO vo) throws Exception {
        return list("admSndngMailDtlsDAO.selectSndngMailList_D", vo);
    }

    /**
	 * 발송메일 총건수를 조회한다.
	 * @param vo AdmSndngMailVO
	 * @return int
	 * @exception
	 */
    public int selectSndngMailListTotCnt(AdmSndngMailVO vo) {
        return (Integer)selectOne("admSndngMailDtlsDAO.selectSndngMailListTotCnt_S", vo);
    }
}
