package vestap.com;

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

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.util.EgovStringUtil;

import org.apache.log4j.Logger;

public class ComParamLogger {
	
	protected final static Logger comParamLogger = Logger.getRootLogger();
	
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
		comParamLogger.debug("\n@@ request@@ 시작---------------------------------------------------------------");
		comParamLogger.debug("#	클래스명:[" + reqClassName + "]	" + "메소드명:[" + nowMethod + "]	"+ "req:[request]" + "requestGetParameterToMap	시작	#");
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Enumeration<?> params = request.getParameterNames();
		
		int cnt = 0;
		while (params.hasMoreElements()) {
			String keyName = (String) params.nextElement();
			if (cnt==0) {
				System.out.println("\n#	Parameter" + cnt + " # " + keyName + " : [" + request.getParameter(keyName) + "]");
			} else {
				System.out.println("#	Parameter" + cnt + " # " + keyName + " : [" + request.getParameter(keyName) + "]");
			}
			rtnMap.put(keyName, request.getParameter(EgovStringUtil.nullConvert(keyName)));
			cnt++;
		}
		
		comParamLogger.debug("#	클래스명:[" + reqClassName + "]	" + "메소드명:[" + nowMethod + "]	"+ "req:[request]" + "requestGetParameterToMap	종료	#");
		comParamLogger.debug("\n@@ request@@ 종료---------------------------------------------------------------");
		return rtnMap;
	}
	
	/**
	 * request의 모든 값을 확인
	 * 
	 * @param reqClassName
	 * @param nowMethod
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static EgovMap requestGetParameterToEgovMap(String reqClassName, String nowMethod, String req, HttpServletRequest request) throws Exception {
		comParamLogger.debug("\n@@ request@@ 시작---------------------------------------------------------------");
		comParamLogger.debug("#	클래스명:[" + reqClassName + "]	" + "메소드명:[" + nowMethod + "]	"+ "req:[request]" + "requestGetParameterToMap	시작	#");
		
		EgovMap egovMap = new EgovMap();
		Enumeration<?> params = request.getParameterNames();
		
		int cnt = 0;
		while (params.hasMoreElements()) {
			String keyName = (String) params.nextElement();
			
			if (cnt==0) {
				System.out.println("\n#	Parameter" + cnt + " # " + keyName + " : [" + request.getParameter(keyName) + "]");
			} else {
				System.out.println("#	Parameter" + cnt + " # " + keyName + " : [" + request.getParameter(keyName) + "]");
			}
			egovMap.put(keyName, request.getParameter(EgovStringUtil.nullConvert(keyName)));
			cnt++;
		}
		
		comParamLogger.debug("#	클래스명:[" + reqClassName + "]	" + "메소드명:[" + nowMethod + "]	"+ "req:[request]" + "requestGetParameterToMap	종료	#");
		comParamLogger.debug("\n@@ request@@ 종료---------------------------------------------------------------");
		return egovMap;
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
		
		comParamLogger.debug("\n@@ paramToVO 시작---------------------------------------------------------------");
		comParamLogger.debug("#	클래스명:[" + reqClassName + "]	" + "메소드명:[" + nowMethod + "]	" + "VO명:[" + nowVO + "]		paramToVO	시작	#");
		Object obj = comParamUtilVO;
		
		int cnt = 0;
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			String keyName = field.getName();
			Object keyValue = field.get(obj);
			
			if (keyValue == null || ("").equals(keyValue.toString())) {
				keyValue = "";
			}
			
			if (cnt==0) {
				System.out.println("\n#	paramToVO #	VO명:[" + nowVO + "]	[" + cnt + "] keyName:[" + keyName + "] # keyValue:[" + keyValue + "]");
			} else {
				System.out.println("#	paramToVO #	VO명:[" + nowVO + "]	[" + cnt + "] keyName:[" + keyName + "] # keyValue:[" + keyValue + "]");
			}
			
			cnt++;
		}
		comParamLogger.debug("#	클래스명:[" + reqClassName + "]	" + "메소드명:[" + nowMethod + "]	" + "VO명:[" + nowVO + "]		paramToVO	종료	#");
		comParamLogger.debug("\n@@ paramToVO 종료---------------------------------------------------------------");
		return comParamUtilVO;
	}
	
	public static List listToMap(List requestList) throws Exception {
		comParamLogger.debug("\n@@ ListUtil 시작---------------------------------------------------------------");
		List rtnList = new ArrayList();
		try {
			Iterator resultIterator = requestList.iterator();
			int i = 0;
			while (resultIterator.hasNext()) {
				ListOrderedMap oderKeyMap = (ListOrderedMap) resultIterator.next();
				comParamLogger.debug("@@# ListUtil 1for문 @@#" + "Row[" + i + "]의 모든 값 oderKeyMap ==" + oderKeyMap);
				// String idRd = String.valueOf(oderKeyMap.get("id"));
				// int id = Integer.parseInt(idRd);
				// comParamLogger.debug("@@# ListUtil 1for문 @@# id == " + id);
				EgovMap colMap = new EgovMap();
				int j = 0;
				Set key = oderKeyMap.keySet();
				for (Iterator iterator = key.iterator(); iterator.hasNext();) {
					String keyName = (String) iterator.next();
					Object valueName = (Object) oderKeyMap.get(keyName);
					System.out.println("@@# ListUtil 2for문 @@#" + "Row[" + i + "]의 colMap[" + j + "] @@#" + keyName + " = " + valueName);
					colMap.put(keyName, valueName);
					j++;
				}
				rtnList.add((EgovMap) colMap);
				i++;
			}
		} catch (Exception e) {
			comParamLogger.debug("@@ ListUtil Exception : \n" + e);
		}
		comParamLogger.debug("\n@@ ListUtil 종료---------------------------------------------------------------");
		return rtnList;
	}
	
}
