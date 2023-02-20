package vestap.test;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.util.EgovStringUtil;

import org.apache.log4j.Logger;

public class RealChanceParamLogger {
	
//	 protected static final Log comParamLogger = LogFactory.getLog(ComParamUtil.class);
//	 private static final Logger comParamLogger = LoggerFactory.getLogger(ComParamUtil.class); //logger로 사용할 경우, 하위 logger들과 충돌 때문에 사용안함
//	 private static final Logger comParamLogger = Logger.getLogger(RealChanceParamLogger.class); //현재 클래스의 depth에서 apache.log4j를 사용할 수 없어서 사용 안함
//	private static final Logger comParamLogger = LoggerFactory.getLogger(RealChanceParamLogger.class);
//	protected static final Logger comParamLogger = Logger.getLogger(RealChanceParamLogger.class);
	 
	/**
	 * request의 모든 값을 확인
	 * 
	 * @param reqClassName
	 * @param nowMethod
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> requestGetParameterToMap(String reqClassName, String nowMethod, String req, HttpServletRequest request) throws Exception {
		System.out.println("\n## request## 시작---------------------------------------------------------------");
		System.out.println("#	클래스명:[" + reqClassName + "]	" + "메소드명:[" + nowMethod + "]	"+ "req:[request]" + "requestGetParameterToMap	시작	#");
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Enumeration<?> params = request.getParameterNames();
		
		int cnt = 0;
		while (params.hasMoreElements()) {
			String keyName = (String) params.nextElement();
			System.out.println("#	Parameter" + cnt + " # " + keyName + " : [" + request.getParameter(keyName) + "]");
			rtnMap.put(keyName, request.getParameter(EgovStringUtil.nullConvert(keyName)));
			cnt++;
		}
		
		System.out.println("#	클래스명:[" + reqClassName + "]	" + "메소드명:[" + nowMethod + "]	"+ "req:[request]" + "requestGetParameterToMap	종료	#");
		System.out.println("## request## 종료---------------------------------------------------------------");
		return rtnMap;
	}
	
	/**
	 * VO의 모든 값을 확인
	 * 
	 * @param reqClassName
	 * @param nowMethod
	 * @param comParamUtilVO
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object paramToVO(String reqClassName, String nowMethod, String nowVO, Object comParamUtilVO) throws IllegalArgumentException, IllegalAccessException {
		
		System.out.println("\n## paramToVO 시작---------------------------------------------------------------");
		System.out.println("#	클래스명:[" + reqClassName + "]	" + "메소드명:[" + nowMethod + "]	" + "VO명:[" + nowVO + "]		paramToVO	시작	#");
		Object obj = comParamUtilVO;
		
		int i = 0;
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			String keyName = field.getName();
			Object keyValue = field.get(obj);
			
			if (keyValue == null || ("").equals(keyValue.toString())) {
				keyValue = "";
			}
			
			System.out.println("#	paramToVO #	VO명:[" + nowVO + "]	[" + i + "] keyName:[" + keyName + "] # keyValue:[" + keyValue + "]");
			
			i++;
		}
		System.out.println("#	클래스명:[" + reqClassName + "]	" + "메소드명:[" + nowMethod + "]	" + "VO명:[" + nowVO + "]		paramToVO	종료	#");
		System.out.println("## paramToVO 종료---------------------------------------------------------------");
		return comParamUtilVO;
	}
	
	public static List listToMap(List requestList) throws Exception {
		System.out.println("\n## ListUtil 시작---------------------------------------------------------------");
		List rtnList = new ArrayList();
		try {
			Iterator resultIterator = requestList.iterator();
			int i = 0;
			while (resultIterator.hasNext()) {
				ListOrderedMap oderKeyMap = (ListOrderedMap) resultIterator.next();
				System.out.println("### ListUtil 1for문 ###" + "Row[" + i + "]의 모든 값 oderKeyMap ==" + oderKeyMap);
				// String idRd = String.valueOf(oderKeyMap.get("id"));
				// int id = Integer.parseInt(idRd);
				// System.out.println("### ListUtil 1for문 ### id == " + id);
				EgovMap colMap = new EgovMap();
				int j = 0;
				Set key = oderKeyMap.keySet();
				for (Iterator iterator = key.iterator(); iterator.hasNext();) {
					String keyName = (String) iterator.next();
					Object valueName = (Object) oderKeyMap.get(keyName);
					System.out.println("### ListUtil 2for문 ###" + "Row[" + i + "]의 colMap[" + j + "] ###" + keyName + " = " + valueName);
					colMap.put(keyName, valueName);
					j++;
				}
				rtnList.add((EgovMap) colMap);
				i++;
			}
		} catch (Exception e) {
			System.out.println("## ListUtil Exception : \n" + e);
		}
		System.out.println("## ListUtil 종료---------------------------------------------------------------");
		return rtnList;
	}
}
