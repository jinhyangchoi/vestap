package vestap.sys.sec.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class VestapLogoutHandler extends SimpleUrlLogoutSuccessHandler {

	@Override

	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {

		if (authentication != null) {
			HttpSession session = request.getSession();
			session.invalidate();
		}
		setDefaultTargetUrl("/closeSession.do");

		super.onLogoutSuccess(request, response, authentication);

	}

}
