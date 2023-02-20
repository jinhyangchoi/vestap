package vestap.adm.ems.service;

import java.util.List;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import vestap.egov.cmm.util.EgovStringUtil;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 발송메일 내역을 조회하는 비즈니스 구현 클래스
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
@Service("admSndngMailDtlsService")
@Transactional
public class AdmSndngMailDtlsServiceImpl extends EgovAbstractServiceImpl implements AdmSndngMailDtlsService {

    @Resource(name="admSndngMailDtlsDAO")
    private AdmSndngMailDtlsDAO admSndngMailDtlsDAO;
    
	@Resource(name = "admSndngMailDetailService")
    private AdmSndngMailDetailService admSndngMailDetailService;
    
	/**
	 * 발송메일 목록을 조회한다.
	 * @param vo AdmSndngMailVO
	 * @return List
	 * @exception Exception
	 */
    public List selectSndngMailList(AdmSndngMailVO vo) throws Exception {
        return admSndngMailDtlsDAO.selectSndngMailList(vo);
	}
    
    /**
	 * 발송메일 총건수를 조회한다.
	 * @param vo AdmSndngMailVO
	 * @return int
	 * @exception
	 */
    public int selectSndngMailListTotCnt(AdmSndngMailVO vo) throws Exception {
        return admSndngMailDtlsDAO.selectSndngMailListTotCnt(vo);
	}
    
    /**
	 * 발송메일을 삭제한다.
	 * @param vo SndngMailVO
	 * @exception
	 */
    public void deleteSndngMailList(AdmSndngMailVO vo) throws Exception {
        
    	// 1. 발송메일을 삭제한다.
    	int mssageId = vo.getMssageId();
    	
//    	String [] sbuf = EgovStringUtil.split(vo.getMssageId(), ",");
    	String [] sbuf = EgovStringUtil.split(Integer.toString(vo.getMssageId()), ",");
    	for (int i = 0; i < sbuf.length; i++) {
    		AdmSndngMailVO admSndngMailVO = new AdmSndngMailVO();
//    		admSndngMailVO.setMssageId(sbuf[i]);
    		admSndngMailVO.setMssageId(Integer.parseInt(sbuf[i]));
    		admSndngMailDetailService.deleteSndngMail(admSndngMailVO);
    	}
    	
    	// 2. 첨부파일을 삭제한다.
    	String [] fbuf = EgovStringUtil.split(vo.getAtchFileIdList(), ",");
    	for (int i = 0; i < fbuf.length; i++) {
    		AdmSndngMailVO admSndngMailVO = new AdmSndngMailVO();
    		admSndngMailVO.setAtchFileId(fbuf[i]);
    		admSndngMailDetailService.deleteAtchmnFile(admSndngMailVO);
    	}
	}
}
