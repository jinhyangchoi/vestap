package vestap.egov.cmm.interceptor;
 
import vestap.adm.adm.management.AdmAdmManagementService;
import vestap.com.ComParamLogger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.sys.bbs.notice.NoticeBoardServiceImpl;
import vestap.sys.cmm.util.UtilityComponent;
 
/**
 * 사용자IP 체크 인터셉터
 * @author 유지보수팀 이기하
 * @since 2013.03.28
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일     수정자          수정내용
 *  ----------  --------    ---------------------------
 *  2013.03.28	이기하          최초 생성 
 *  </pre>
 */

public class IpObtainInterceptor extends HandlerInterceptorAdapter {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UtilityComponent utilComp;
	
	@Resource(name="admAdmManagementService")
	private AdmAdmManagementService admAdmManagementService;
	
	@Resource(name = "noticeBoardService")
	private NoticeBoardServiceImpl noticeBoardService;
 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		this.logger.info("\n_____________________ IpObtainInterceptor.java START _____________________________");
		
//		String clientIp = request.getRemoteAddr();
		Authentication auth = (Authentication)request.getUserPrincipal();
		EgovMap map = new EgovMap();
		EgovMap param = new EgovMap();
		
		/**
		 * 로그인 계정정보를 Map에 담는다
		 */
		if(auth != null){
			Object principal = auth.getPrincipal();
			
			String name = principal.toString();
			map.put("user_id", name);
		}
		
		String contextPath = request.getContextPath();
		String reqUri = request.getRequestURI().replaceAll("\\.do", "").substring(contextPath.length());
		String req_param = request.getQueryString();
		String[] uris = reqUri.split("/");
		String depth1 = "";
		String depth2 = "";
		String depth3 = "";
		String depth4 = "";
		String depth5 = "";

		if (uris.length > 1)
			depth1 = uris[1];
		if (uris.length > 2)
			depth2 = uris[2];
		if (uris.length > 3)
			depth3 = uris[3];
		if (uris.length > 4)
			depth4 = uris[4];
		if (uris.length > 5) {
			depth5 = uris[5];
		}
		request.setAttribute("depth1", depth1);
		request.setAttribute("depth2", depth2);
		request.setAttribute("depth3", depth3);
		request.setAttribute("depth4", depth4);
		request.setAttribute("depth5", depth5);
		
		/*                
		 * request_log 글제목 req_param 에 넣기          
		 */
		/*
		 * reqParam 분리
		 * userId=abc&userNm=def => {userId, abc} , {userNm = def}
		 */
		
		
		if (req_param != null) {
			String[] params = req_param.split("&");

			if (depth3.equals("notice") || depth4.equals("notice")) {

				for (int i = 0; i < params.length; i++) {

					if (params[i].contains("idx")) {
						String[] idx = params[i].split("=");
						String num = idx[1];
						param.put("idx", Integer.parseInt(num));
						EgovMap name = this.admAdmManagementService.getNoticeIdxNm(param);

						System.out.println("****************************************");
						System.out.println(name);
						req_param = req_param + "&title=" + name.get("bbsTitle");
						System.out.println("****************************************");
					}
				}
			} else if (depth3.equals("faq") || depth4.equals("faq")) {

				for (int i = 0; i < params.length; i++) {

					if (params[i].contains("idx")) {
						String[] idx = params[i].split("=");
						String num = idx[1];
						param.put("idx", Integer.parseInt(num));
						EgovMap name = this.admAdmManagementService.getFaqIdxNm(param);

						System.out.println("****************************************");
						System.out.println(name);
						req_param = req_param + "&title=" + name.get("bbsTitle");
						System.out.println("****************************************");
					}
				}

			} else if (depth3.equals("suggestion") || depth4.equals("suggestion")) {

				for (int i = 0; i < params.length; i++) {

					if (params[i].contains("idx")) {
						String[] idx = params[i].split("=");
						String num = idx[1];
						param.put("idx", Integer.parseInt(num));
						EgovMap name = this.admAdmManagementService.getSuggestionIdxNm(param);

						System.out.println("****************************************");
						System.out.println(name);
						req_param = req_param + "&title=" + name.get("bbsTitle");
						System.out.println("****************************************");
					}
				}

			} else if (depth3.equals("reference") || depth4.equals("reference")) {

				for (int i = 0; i < params.length; i++) {

					if (params[i].contains("idx")) {
						String[] idx = params[i].split("=");
						String num = idx[1];
						param.put("idx", Integer.parseInt(num));
						EgovMap name = this.admAdmManagementService.getReferenceIdxNm(param);

						System.out.println("****************************************");
						System.out.println(name);
						req_param = req_param + "&title=" + name.get("bbsTitle");
						System.out.println("****************************************");
					}
				}

			} else if (depth4.equals("enroll") || depth4.equals("pwChange")) {

				for (int i = 0; i < params.length; i++) {

					if (params[i].contains("userId") || params[i].contains("data")) {

						String[] userid = params[i].split("=");
						String id = userid[1];
						req_param = req_param + "&userId=" + id;

					}

				}
			}
		}
		
		try {

			map.put("req_site", contextPath.replaceAll("/", ""));        
			map.put("req_url", request.getRequestURI().substring(contextPath.length()));
			map.put("req_param", req_param);
			map.put("url_depth1", depth1);
			map.put("url_depth2", depth2);
			map.put("url_depth3", depth3);
			map.put("url_depth4", depth4);
			map.put("url_depth5", depth5);
			map.put("req_ip", getRequesterIP(request));
			map.put("req_sessionid", request.getSession().getId());
//			System.out.println(map);
			ComParamLogger.paramToVO("IpObtainInterceptor.java", "preHandle", "map", map);
			HttpSession session = request.getSession();
			System.out.println("\n**************************session ********************");  
			if(auth != null && !(request.getRequestURI().substring(contextPath.length())).contains("selectWfsLayer.do")){
				if(session.getAttribute(reqUri) == null || session.getAttribute(reqUri) != "true") { //세션정보 받아서 비교 
					
					this.admAdmManagementService.insertRequestLog(map);
					
					session.setAttribute(reqUri, "true"); //세션정보 변경후 저장
				}
				
			}
		} catch (Exception e) {
			this.logger.info(e.getMessage());
		}
		
		return true;
	}
	
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("\n_____________________________  END  _____________________________"); 
		logger.debug("\n_____________________________  END  _____________________________");
		System.out.println("\n_____________________________  END  _____________________________");
		this.logger.info("\n_____________________ IpObtainInterceptor.java END _____________________________");
	}
	
	public String getRequesterIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if ((utilComp.isNullOrEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((utilComp.isNullOrEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((utilComp.isNullOrEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if ((utilComp.isNullOrEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if ((utilComp.isNullOrEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
}
