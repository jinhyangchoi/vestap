package vestap.sys.sec.user;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 사용자 로그인, 회원가입, 세션 처리 등
 * --------------------------------------------------
 * 		수정일			수정자			수정내용
 * --------------------------------------------------
 * 	2018.08.13			vestap개발		최초 작성
 * --------------------------------------------------
 * @author vestap 개발
 * @since 2018.08.13
 *
 */
//@Transactional
public interface UserApplyService {
	
	int userApplyChk(UserApplyVO vo) throws Exception;
	
//	@Transactional
	int userApplyInsert(UserApplyVO vo, List<MultipartFile> uploadFiles, List<String> matchName) throws Exception;

	UserApplyVO userApplyFileIdx(UserApplyVO vo) throws Exception;

	int userApplyUpdate(UserApplyVO vo, List<MultipartFile> uploadFiles, List<String> newFileName) throws Exception;

}
