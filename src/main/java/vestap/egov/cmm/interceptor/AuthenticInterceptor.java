package vestap.egov.cmm.interceptor;

import vestap.egov.cmm.LoginVO;
import vestap.egov.cmm.util.EgovUserDetailsHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 인증여부 체크 인터셉터
 * @author 공통서비스 개발팀 서준식
 * @since 2011.07.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일            수정자               수정내용
 *  -------    --------    ---------------------------
 *  2011.07.01  서준식             최초 생성
 *  2011.09.07  서준식            인증이 필요없는 URL을 패스하는 로직 추가
 *  </pre>
 */


public class AuthenticInterceptor extends HandlerInterceptorAdapter {           
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
	 * 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		this.logger.info("\n_____________________ Authenticlnterceptor________ START _____________________________");
		String currentUrl = request.getRequestURI();
		System.out.println("currentUrl:"+currentUrl);
		
		boolean isPermittedURL = false;
		System.out.println("currentUrl");
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		if (loginVO != null) {
			return true;
		} else if (!isPermittedURL) {
//				ModelAndView modelAndView = new ModelAndView("redirect:/uat/uia/egovLoginUsr.do");
			System.out.println("\n_____________________ Authenticlnterceptor________ return False _____________________________");
			ModelAndView modelAndView = new ModelAndView("redirect:/loginPage.do");
			throw new ModelAndViewDefiningException(modelAndView);
		} else {
			return true;
		}
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.info("\n_____________________________  END  _____________________________"); 
		logger.debug("\n_____________________________  END  _____________________________");
		System.out.println("\n_____________________________  END  _____________________________");
		this.logger.info("\n_____________________ AuthenticInterceptor.java END _____________________________");
	}
	
}
