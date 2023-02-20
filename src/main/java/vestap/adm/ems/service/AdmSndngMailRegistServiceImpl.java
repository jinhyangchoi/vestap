package vestap.adm.ems.service;

import java.util.List;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import javax.annotation.Resource;

import noNamespace.SndngMailDocument;
import vestap.egov.cmm.service.Globals;
import vestap.egov.cmm.util.EgovStringUtil;
import vestap.egov.cmm.util.EgovXMLDoc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 발송메일등록, 발송요청XML파일 생성하는 비즈니스 구현 클래스
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
 *  2011.07.27  서준식          메일 발송내역 DB 저장시 첨부파일이 없으면 NULL로 변경
 *  2011.12.06  이기하          메일 첨부파일이 기능 추가 
 *  </pre>
 */
@Service("admSndngMailRegistService")
@Transactional
public class AdmSndngMailRegistServiceImpl extends EgovAbstractServiceImpl implements AdmSndngMailRegistService {

	/** SndngMailRegistDAO */
    @Resource(name="admSndngMailRegistDAO")
    private AdmSndngMailRegistDAO admSndngMailRegistDAO;
    
    /** Message ID Generation */
    /*
    @Resource(name="admMailMsgIdGnrService")
    private EgovIdGnrService admMailMsgIdGnrService;
    */
    @Resource(name = "admSndngMailService")
    private AdmSndngMailService admSndngMailService;
    
    /**
	 * 발송할 메일을 등록한다
	 * @param vo SndngMailVO
	 * @return boolean
	 * @exception Exception
	 */
    public boolean insertSndngMail(AdmSndngMailVO vo) throws Exception {
    	
    	String recptnPersons = vo.getRecptnPerson().replaceAll(" ", "");
    	String [] recptnPersonList = recptnPersons.split(";");

    	for (int j = 0; j < recptnPersonList.length; j++) {
    		
    		// 1-0.메세지ID를 생성한다.
//        	String mssageId = admMailMsgIdGnrService.getNextStringId();

    		AdmSndngMailVO admSndngMailVO = new AdmSndngMailVO();
    		admSndngMailVO = admSndngMailRegistDAO.selectMssageId(admSndngMailVO);
    		int mssageId = admSndngMailVO.getMssageId();
    		
        	// 1-1.발송메일  데이터를 만든다.
        	AdmSndngMailVO mailVO = new AdmSndngMailVO();
        	mailVO.setMssageId(mssageId);					//메일 일련번호
        	mailVO.setDsptchPerson(vo.getDsptchPerson());	//받는사람
        	mailVO.setRecptnPerson(recptnPersonList[j]);	//받는사람
        	mailVO.setSj(vo.getSj());						//제목
        	mailVO.setEmailCn(vo.getEmailCn());				//내용
        	mailVO.setSndngResultCode("R");                 // 발송결과 요청 (R:요청, F:실패, C:완료)
        	
        	if(vo.getAtchFileId() == null || vo.getAtchFileId().equals("")){
        		mailVO.setAtchFileId(null);
        		mailVO.setFileStreCours(null);
        		mailVO.setOrignlFileNm(null);
        	} else {
        		mailVO.setAtchFileId(vo.getAtchFileId());
	        	mailVO.setFileStreCours(vo.getFileStreCours());
	        	mailVO.setOrignlFileNm(vo.getOrignlFileNm());
        	}

        	// 1-3.발송메일을 등록한다.
        	int result = admSndngMailRegistDAO.insertSndngMail(mailVO);
        	
        	// 1-4.메일을 발송한다.
        	boolean sendingMailResult = admSndngMailService.sndngMail(mailVO);
        	
        	if(!sendingMailResult) {
        		mailVO.setSndngResultCode("F");	// 발송결과 실패
            	admSndngMailRegistDAO.updateSndngMail(mailVO);	// 발송상태를 DB에 업데이트 한다.
        		return false;
        	}
        	
    	}
    	return true;
    }
    
    /**
	 * 발송할 메일을 XML파일로 만들어 저장한다.
	 * @param vo SndngMailVO
	 * @return boolean
	 * @exception Exception
	 */
    public boolean trnsmitXmlData(AdmSndngMailVO vo) throws Exception {
    	
    	// 1. 첨부파일 목록 (원파일명, 저장파일명)
    	String orignlFileList = "";
    	String streFileList = "";
    	
    	String atchFileId = EgovStringUtil.nullConvert(vo.getAtchFileId());
    	
    	if( !("").equals(atchFileId)  ){
    		List atchmnFileList = admSndngMailRegistDAO.selectAtchmnFileList(vo);
    		for (int i = 0; i < atchmnFileList.size(); i++) {
    			AdmAtchmnFileVO fileVO = (AdmAtchmnFileVO)atchmnFileList.get(i);
    			String orignlFile = fileVO.getOrignlFileNm();
    			String streFile = fileVO.getFileStreCours() + fileVO.getStreFileNm();
    			orignlFileList += orignlFile + ";";
    			streFileList += streFile + ";";
    		}    	
    	}
    	
    	// 2. XML데이터를 만든다.
    	SndngMailDocument mailDoc;
    	SndngMailDocument.SndngMail mailElement;
    	mailDoc = SndngMailDocument.Factory.newInstance();
    	mailElement = mailDoc.addNewSndngMail();
    	String mssageId = Integer.toString(vo.getMssageId()); 
    	mailElement.setMssageId(mssageId);
    	mailElement.setDsptchPerson(vo.getDsptchPerson());
    	mailElement.setRecptnPerson(vo.getRecptnPerson());
    	mailElement.setSj(vo.getSj());
    	mailElement.setEmailCn(vo.getEmailCn());
    	mailElement.setSndngResultCode(vo.getSndngResultCode());
    	mailElement.setOrignlFileList(orignlFileList);
    	mailElement.setStreFileList(streFileList);

    	// 2. XML파일로 저장한다.
    	String temp = Globals.MAIL_REQUEST_PATH;
    	String temp2;
    	String xmlFile = Globals.MAIL_REQUEST_PATH + vo.getMssageId() + ".xml";
        boolean result = EgovXMLDoc.getClassToXML(mailDoc, xmlFile);
        if (result == true) {
        	recptnXmlData(xmlFile);
        }
    	return result;
    }

    /**
	 * 발송메일 발송결과 XML파일을 읽어 발송결과코드에 수정한다.
	 * @param xml String
	 * @return boolean
	 * @exception Exception
	 */
    public boolean recptnXmlData(String xmlFile) throws Exception {
    	
    	// 1. XML파일에서 발송결과코드를 가져온다.
    	SndngMailDocument mailDoc = EgovXMLDoc.getXMLToClass(xmlFile);
    	SndngMailDocument.SndngMail mailElement = mailDoc.getSndngMail();
    	AdmSndngMailVO sndngMailVO = new AdmSndngMailVO();
    	sndngMailVO.setMssageId(sndngMailVO.getMssageId());
    	sndngMailVO.setSndngResultCode("C");	// 발송결과 완료
    	
    	// 2. DB에 업데이트 한다.
    	admSndngMailRegistDAO.updateSndngMail(sndngMailVO);
    	
    	return true;
    }
}
