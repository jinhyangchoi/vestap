package vestap.egov.cmm.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EnvInterceptor extends HandlerInterceptorAdapter {
	Logger logger = LogManager.getLogger(EnvInterceptor.class);

	@Value("${Globals.nearCoastSigunguCd}")
	private String nearCoastSigunguCd;

	@Value("${Globals.geoserverUrl}")
	private String geoserverUrl;


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if(modelAndView != null
				&& modelAndView.hasView()
				&& modelAndView.getView() == null
				&& modelAndView.getViewName() != null){
			// 페이지 요청시에만
			logger.debug("enter EnvInterceptor / " + request.getRequestURL());
			request.setAttribute("NEAR_COAST_SIGUNGU_CD",nearCoastSigunguCd);
			request.setAttribute("GEOSERVER_URL",geoserverUrl);
		}
		super.postHandle(request, response, handler, modelAndView);
	}
}