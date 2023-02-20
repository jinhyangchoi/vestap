package vestap.sys.sec.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.sys.cmm.util.EncryptionComponent;
import vestap.sys.cmm.util.UtilityComponent;
import vestap.sys.sec.user.UserAccessService;
import vestap.sys.sec.user.UserAccessVO;

/**
 * 사용자의 로그인 처리
 * --------------------------------------------------
 * 		수정일			수정자			수정내용
 * --------------------------------------------------
 * 	2018.08.13			vestap개발		최초 작성
 * --------------------------------------------------
 * @author vestap 개발
 * @since 2018.08.13
 *
 */
public class VestapAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(VestapAuthenticationProvider.class);
	
	@Autowired
	private UtilityComponent utilComp;
	
	@Autowired
	private UserAccessService SERVICE;
	
	@Autowired
	private EncryptionComponent encComp;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
		WebAuthenticationDetails wad = null;
		String userIPAddress         = null;
		
		String id = authToken.getName();
		String password = authToken.getCredentials().toString();
		
		try {
			
			password = this.encComp.sha256(authToken.getCredentials().toString());
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			throw new BadCredentialsException("사용자 인증에 문제가 발생 했습니다.");
		}
		
		UserAccessVO userVO = null;
		
		try {
			
			userVO = this.SERVICE.getUserInfo(id);
			
			this.SERVICE.updateUserAccess(id);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			throw new BadCredentialsException("사용자 인증에 문제가 발생 했습니다.");
		}
		
		// 사용자의 상태 조건1: 사용상태 Y, 조건2:공무원은 승인일자부터 5년제한, 비공무원은 승인일자부터 1년제한 | 삭제사용자는 종료
		// /egovframework/sqlmap/mapper/user/user-SQL.xml getUserInfo
		if(userVO != null) {
			
			if(userVO.getUser_pw().equals(password)) {
				
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				
				String auth = userVO.getUser_auth();
				
				/**
				 * 권한 확인
				 * A: 관리자
				 * W: 광역(시도) 사용자
				 * B: 기초(시군구) 사용자
				 */
				if(auth.equals("A")) {
					authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
					
				} else if(auth.equals("W")) {
					authorities.add(new SimpleGrantedAuthority("ROLE_WIDE"));
					
				} else if(auth.equals("B")) {
					authorities.add(new SimpleGrantedAuthority("ROLE_BASE"));
				}
				
				VestapUserDetails details = new VestapUserDetails();
				details.setUser_id(userVO.getUser_id());
				details.setUser_nm(userVO.getUser_nm());
				details.setUser_rcp(userVO.getUser_rcp());
				details.setUser_scr(userVO.getUser_scr());
				details.setUser_fld(userVO.getUser_fld());
				details.setUser_auth(userVO.getUser_auth());
				details.setUser_dist(userVO.getUser_dist());
				details.setUser_district(userVO.getUser_district());
				details.setUser_access(userVO.getUser_access());
				details.setUser_email(userVO.getUser_email());
				details.setUser_org_nm(userVO.getUser_org_nm());
				details.setAuthorities(authorities);
				details.setUser_gov_yn(userVO.getUser_gov_yn());
				details.setUse_yn(userVO.getUse_yn());
				details.setNow(userVO.getNow());
				details.setAprv_dt(userVO.getAprv_dt());
				details.setUser_expire(userVO.getUser_expire());
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password, authorities);
				wad = (WebAuthenticationDetails)authentication.getDetails();
				userIPAddress = wad.getRemoteAddress();
				authenticationToken.setDetails(details);
				
				EgovMap map = new EgovMap();
				map.put("userId", userVO.getUser_id());
				map.put("userIp", userIPAddress);
				this.SERVICE.insertAccessLog(map);
				
				return authenticationToken;
				
			} else {
//				throw new BadCredentialsException("사용하지 않는 아이디거나, 잘못된 비밀번호 입니다.");
				throw new BadCredentialsException("사용하지 않는 아이디,비밀번호,사용상태,사용기간을 확인해주세요.");
			}
			
		} else {
			throw new UsernameNotFoundException("사용하지 않는 아이디,비밀번호,사용상태,사용기간을 확인해주세요.");
		}
		
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	private EgovMap paramMap(HttpServletRequest request){
		EgovMap paramMap = new EgovMap();

		Set keySet = request.getParameterMap().keySet();
		Iterator<?> iter = keySet.iterator();
		while(iter.hasNext()){
			String key = (String) iter.next();
			paramMap.put(key, request.getParameter(key));
			logger.info("request.getParameter(\""+key+"\", \""+request.getParameter(key)+"\")");
		}
		
		return paramMap;
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
