package vestap.adm.ems.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * 발송메일 내역을 조회하는 비즈니스 인터페이스 클래스
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
@Transactional
public interface AdmSndngMailDtlsService {
	
	/**
	 * 발송메일 목록을 조회한다.
	 * @param vo AdmSndngMailVO
	 * @return List
	 * @exception Exception
	 */
	List selectSndngMailList(AdmSndngMailVO vo) throws Exception;
	
	/**
	 * 발송메일 총건수를 조회한다.
	 * @param vo AdmSndngMailVO
	 * @return int
	 * @exception
	 */
	int selectSndngMailListTotCnt(AdmSndngMailVO vo) throws Exception;
	
	/**
	 * 발송메일을 삭제한다.
	 * @param vo SndngMailVO
	 * @exception
	 */
	void deleteSndngMailList(AdmSndngMailVO vo) throws Exception;
}