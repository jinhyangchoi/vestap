package vestap.adm.ems.service;

import java.io.File;
/*
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cop.ems.service.EgovSndngMailDetailService;
import egovframework.com.cop.ems.service.SndngMailVO;
import egovframework.com.utl.sim.service.EgovFileTool;
*/
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import vestap.egov.cmm.service.EgovFileMngService;
import vestap.egov.cmm.service.FileVO;
import vestap.egov.cmm.service.Globals;
import vestap.egov.cmm.util.EgovFileTool;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 발송메일을 상세 조회하는 비즈니스 구현 클래스
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
@Service("admSndngMailDetailService")
public class AdmSndngMailDetailServiceImpl extends EgovAbstractServiceImpl implements AdmSndngMailDetailService {
	
	// 파일구분자
    static final char FILE_SEPARATOR     = File.separatorChar;
  
    @Resource(name="admSndngMailDetailDAO")
    private AdmSndngMailDetailDAO admSndngMailDetailDAO;
    
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService egovFileMngService;
    
    /**
	 * 발송메일을 상세 조회한다.
	 * @param vo SndngMailVO
	 * @return SndngMailVO
	 * @exception Exception
	 */
    public AdmSndngMailVO selectSndngMail(AdmSndngMailVO vo) throws Exception {
    	
    	// 1. 발송메일 정보를 조회한다.
    	AdmSndngMailVO resultMailVO = admSndngMailDetailDAO.selectSndngMail(vo);
    	
        return resultMailVO;
	}
    
    /**
	 * 발송메일을 삭제한다.
	 * @param vo SndngMailVO
	 * @exception
	 */
    public void deleteSndngMail(AdmSndngMailVO vo) throws Exception {

    	// 1. 발송메일을 삭제한다.
    	admSndngMailDetailDAO.deleteSndngMail(vo);
    	
	}
    
    /**
	 * 첨부파일을 삭제한다.
	 * @param vo SndngMailVO
	 * @exception
	 */
    public void deleteAtchmnFile(AdmSndngMailVO vo) throws Exception {
        
    	// 1. 첨부파일 목록을 삭제한다. (이삼섭 책임 제공)
    	FileVO fileVO = new FileVO();
    	fileVO.setAtchFileId(vo.getAtchFileId());
    	egovFileMngService.deleteAllFileInf(fileVO);
	}
}
