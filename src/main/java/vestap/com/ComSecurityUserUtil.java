package vestap.com;

import java.util.Collection;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import vestap.egov.cmm.util.EgovStringUtil;

public class ComSecurityUserUtil {
	
	protected final static Logger comSecurityUserUtilLogger = Logger.getRootLogger();
	
	/**
	 * 202106
	 * 로그인여부와 관계없이 사용자가 Authentication에 ROLE_ADMIN의 권한을 가지고 있는지 확인함
	 * @return	authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent()
	 */
	public static boolean hasAdminRole() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecurityContext context = SecurityContextHolder.getContext();		// 시큐리티 컨텍스트 객체
		Authentication authentication = context.getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		if(authorities == null) {
			return false;
		}
		
		comSecurityUserUtilLogger.debug("\ncomSecurityUserUtilLogger > hasAdminRole() :"+authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent());
		
		return authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent();
	}
	
	
	/**
	 * 202106
	 * 현재 로그인한 사용자의 id를 확인
	 * @return	user
	 */
	public static String getUser() {
		SecurityContext context = SecurityContextHolder.getContext();		// 시큐리티 컨텍스트 객체
		Authentication authentication = context.getAuthentication();		// 인증 객체
		
		if ( authentication != null ) {
			String user = (String) EgovStringUtil.nullConvert(authentication.getPrincipal());               // 로그인한 사용자정보
			comSecurityUserUtilLogger.debug("\ncomSecurityUserUtilLogger > getUser :"+ user);
			return user;
		} else {
			return "";
		}
	}
	
	public static String getRandomPasswd(int pwdLength) {
		char pwCollectionSpCha[] = new char[] { '!', '@', '#', '$', '%', '^', '&', '*'};
		char pwCollectionNum[] = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', };
		char pwCollectionAll[] = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E',
				'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
				'v', 'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&', '*'};
		
		return getRandPw(1, pwCollectionSpCha) + getRandPw(6, pwCollectionAll) + getRandPw(1, pwCollectionNum);
	}

	public static String getRandPw(int size, char[] pwCollection) {
		String ranPw = "";
		for (int i = 0; i < size; i++) {
			int selectRandomPw = (int) (Math.random() * (pwCollection.length));
			ranPw += pwCollection[selectRandomPw];
		}
		return ranPw;
	}

}

