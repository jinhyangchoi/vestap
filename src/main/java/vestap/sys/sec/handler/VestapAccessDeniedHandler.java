package vestap.sys.sec.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import vestap.com.ComSecurityUserUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;

/**
 * 사용권한이 없는 사용자가 접근 시도 했을 경우 처리
 * --------------------------------------------------
 * 		수정일			수정자			수정내용
 * --------------------------------------------------
 * 	2018.08.13			vestap개발		최초 작성
 * --------------------------------------------------
 * @author vestap 개발
 * @since 2018.08.13
 *
 */
public class VestapAccessDeniedHandler implements AccessDeniedHandler {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
		
		String reqUrl = request.getRequestURI();	// 호출URL
		logger.debug("\n@@@@ VestapAccessDeniedHandler S @@@@\n"+reqUrl);
		/*
		SecurityContext context = SecurityContextHolder.getContext();		// 시큐리티 컨텍스트 객체
		Authentication authentication = context.getAuthentication();		// 인증 객체
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String HandlerPrincipal = (String) authentication.getPrincipal();	// 로그인한 사용자정보
		*/
		SecurityContext context = SecurityContextHolder.getContext();		// 시큐리티 컨텍스트 객체
		Authentication authentication = context.getAuthentication();		// 인증 객체
		
		boolean adminChk = false;
		
		if(authentication != null) {
			adminChk = ComSecurityUserUtil.hasAdminRole();
		}
		
		if ( adminChk == true ) {
			
			if( ("/admin/user/management/detailView.do").equals(reqUrl)  ) {
				request.getRequestDispatcher("/admin/user/management/detailView.do").forward(request, response);
			} else if ( ("/admin/ems/insertSndngMail.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/admin/ems/insertSndngMail.do").forward(request, response);
			} else if (  ("/admin/ems/insertSndngMailView.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/admin/ems/insertSndngMailView.do").forward(request, response);
			} else if ( ("/admin/ems/backSndngMailRegist.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/admin/ems/backSndngMailRegist.do").forward(request, response);
			} else if ( ("/user/userApplyInsertView.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/user/userApplyInsertView.do").forward(request, response);	
			} else if ( ("/user/userApplyInsert.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/user/userApplyInsert.do").forward(request, response);
			} else if ( ("/user/newPw.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/user/newPw.do").forward(request, response);
			} else if ( ("/admin/ems/selectSndngMailDetail.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/admin/ems/selectSndngMailDetail.do").forward(request, response);
			} else if ( ("/admin/ems/selectSndngMailList.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/admin/ems/selectSndngMailList.do").forward(request, response);
			} else if ( ("/admin/ems/deleteSndngMailList.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/admin/ems/deleteSndngMailList.do").forward(request, response);
			} else if ( ("/admin/user/management/list.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/admin/user/management/list.do").forward(request, response);
			}
			else {
				request.getRequestDispatcher("/accessDenied.do").forward(request, response);
			}
			
		} else {
			
			if (("/user/newPw.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/user/newPw.do").forward(request, response);	
			} else if ( ("/user/userApplyInsertView.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/user/userApplyInsertView.do").forward(request, response);	
			} else if ( ("/user/userApplyInsert.do").equals(reqUrl) ) {
				request.getRequestDispatcher("/user/userApplyInsert.do").forward(request, response);
			}
			else {
				request.getRequestDispatcher("/accessDenied.do").forward(request, response);
			}
		}
		logger.debug("\n@@@@ VestapAccessDeniedHandler E @@@@");
	}
	
}
