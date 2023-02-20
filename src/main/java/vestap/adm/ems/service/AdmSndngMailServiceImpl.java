package vestap.adm.ems.service;

import javax.annotation.Resource;

import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import org.apache.log4j.Logger;
/**
 * 메일 솔루션과 연동해서 이용해서 메일을 보내는 서비스 구현 클래스
 * @since 2011.09.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011.09.09  서준식       최초 작성
 *  2011.12.06  이기하       메일 첨부파일이 기능 추가
 *  2013.05.23  이기하       메일 첨부파일이 없을 때 로직 추가
 *
 *  </pre>
 */
@Service("admSndngMailService")
public class AdmSndngMailServiceImpl extends EgovAbstractServiceImpl implements AdmSndngMailService {

//	첨부파일 미사용시
//	@Resource(name="EMSMailSender")
//    private MailSender emsMailSender;

	@Resource(name="admMultiPartEmail")
    private AdmMultiPartEmail admMultiPartEmail;

	/** SndngMailRegistDAO */
    @Resource(name="admSndngMailRegistDAO")
    private AdmSndngMailRegistDAO admSndngMailRegistDAO;
    
    private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 메일을 발송한다
	 * @param vo SndngMailVO
	 * @return boolean
	 * @exception Exception
	 */
	public boolean sndngMail(AdmSndngMailVO admSndngMailVO) throws Exception {

		String recptnPerson   = (admSndngMailVO.getRecptnPerson() == null) ? "" : admSndngMailVO.getRecptnPerson();		// 수신자
		String subject        = (admSndngMailVO.getSj() == null) ? "" : admSndngMailVO.getSj();							// 메일제목
		String emailCn        = (admSndngMailVO.getEmailCn() == null) ? "" : admSndngMailVO.getEmailCn();				// 메일내용

		try {
			// 메일을 전송합니다
			admMultiPartEmail.send(recptnPerson, subject, emailCn);

			Throwable t = new Throwable();

		} catch (MailParseException ex) {
			admSndngMailVO.setSndngResultCode("F"); // 발송결과 실패
			admSndngMailRegistDAO.updateSndngMail(admSndngMailVO); // 발송상태를 DB에 업데이트 한다.
			logger.error("Sending Mail Exception : {} [failure when parsing the message]", ex.getCause());
			return false;
		} catch (MailAuthenticationException ex) {
			admSndngMailVO.setSndngResultCode("F"); // 발송결과 실패
			admSndngMailRegistDAO.updateSndngMail(admSndngMailVO); // 발송상태를 DB에 업데이트 한다.
			logger.error("Sending Mail Exception : {} [authentication failure]", ex.getCause());
			return false;
		} catch (MailSendException ex) {
			admSndngMailVO.setSndngResultCode("F"); // 발송결과 실패
			admSndngMailRegistDAO.updateSndngMail(admSndngMailVO); // 발송상태를 DB에 업데이트 한다.
			logger.error("Sending Mail Exception : {} [failure when sending the message]", ex.getCause());
			return false;
		} catch (Exception ex) {
			admSndngMailVO.setSndngResultCode("F"); // 발송결과 실패
			admSndngMailRegistDAO.updateSndngMail(admSndngMailVO); // 발송상태를 DB에 업데이트 한다.
			logger.error("Sending Mail Exception : {} [unknown Exception]", ex.getCause());
			logger.debug(ex.getMessage());
			return false;
		}

		return true;
	}

}
