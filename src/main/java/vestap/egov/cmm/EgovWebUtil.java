package vestap.egov.cmm;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 교차접속 스크립트 공격 취약성 방지(파라미터 문자열 교체)
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    	--------    ---------------------------
 *   2011.10.10  한성곤          최초 생성
 *	 2017-02-07  이정은          시큐어코딩(ES) - 시큐어코딩 경로 조작 및 자원 삽입[CWE-22, CWE-23, CWE-95, CWE-99]
 * </pre>
 */

public class EgovWebUtil {
	public static String clearXSSMinimum(String value) {
		if (value == null || value.trim().equals("")) {
			return "";
		}

		String returnValue = value;

		returnValue = returnValue.replaceAll("&", "&amp;");
		returnValue = returnValue.replaceAll("<", "&lt;");
		returnValue = returnValue.replaceAll(">", "&gt;");
		returnValue = returnValue.replaceAll("\"", "&#34;");
		returnValue = returnValue.replaceAll("\'", "&#39;");
		returnValue = returnValue.replaceAll(".", "&#46;");
		returnValue = returnValue.replaceAll("%2E", "&#46;");
		returnValue = returnValue.replaceAll("%2F", "&#47;");
		return returnValue;
	}

	public static String clearXSSMaximum(String value) {
		String returnValue = value;
		returnValue = clearXSSMinimum(returnValue);

		returnValue = returnValue.replaceAll("%00", null);

		returnValue = returnValue.replaceAll("%", "&#37;");

		// \\. => .

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\
		returnValue = returnValue.replaceAll("\\./", ""); // ./
		returnValue = returnValue.replaceAll("%2F", "");

		return returnValue;
	}

	public static String filePathBlackList(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\

		return returnValue;
	}

	/**
	 * 행안부 보안취약점 점검 조치 방안.
	 *
	 * @param value
	 * @return
	 */
	public static String filePathReplaceAll(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("/", "");
		returnValue = returnValue.replaceAll("\\", "");
		returnValue = returnValue.replaceAll("\\.\\.", ""); // ..
		returnValue = returnValue.replaceAll("&", "");

		return returnValue;
	}
	
	public static String fileInjectPathReplaceAll(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		
		returnValue = returnValue.replaceAll("/", "");
		returnValue = returnValue.replaceAll("\\..", ""); // ..
		returnValue = returnValue.replaceAll("\\\\", "");// \
		returnValue = returnValue.replaceAll("&", "");

		return returnValue;
	}	

	public static String filePathWhiteList(String value) {
		return value;
	}

	 public static boolean isIPAddress(String str) {
		Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");

		return ipPattern.matcher(str).matches();
    }

	 public static String removeCRLF(String parameter) {
		 return parameter.replaceAll("\r", "").replaceAll("\n", "");
	 }

	 public static String removeSQLInjectionRisk(String parameter) {
		 return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("%", "").replaceAll(";", "").replaceAll("-", "").replaceAll("\\+", "").replaceAll(",", "");
	 }

	 public static String removeOSCmdRisk(String parameter) {
		 return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("|", "").replaceAll(";", "");
	 }
	 
	 public static void getProxy(String type, String reqUrl, HttpServletRequest request, HttpServletResponse response) {
			HttpURLConnection httpURLConnection = null;
			InputStream is = null; 
			OutputStream os = null;
			InputStream ris = null; 
			OutputStream ros = null; 
			
			try {
				StringBuffer sbURL = new StringBuffer();
				sbURL.append(reqUrl);
				
				URL targetUrl = new URL(sbURL.toString());
				httpURLConnection = (HttpURLConnection)targetUrl.openConnection(); 
				httpURLConnection.setDoInput(true);
				
				if("GET".equals(type.toUpperCase())) {
					httpURLConnection.setDoOutput(false); 
					httpURLConnection.setRequestMethod("GET");
				}else{
					httpURLConnection.setDoOutput(true); 
					httpURLConnection.setRequestMethod("POST");
				}
				
				int length = 5000;
				int bytesRead = 0;
				ros = response.getOutputStream(); 
				response.setContentType(httpURLConnection.getContentType());
				ris = httpURLConnection.getInputStream();
				
				byte[] resBytes = new byte[length]; 
				bytesRead = 0; 
				while ((bytesRead = ris.read(resBytes, 0, length)) > 0) { 
					ros.write(resBytes, 0, bytesRead);
				}
			}catch(Exception e) { 
				
				response.setStatus(500); 
			} finally { 
				try { if(is != null) { is.close(); } } catch(Exception e) {} 
				try { if(os != null) { os.close(); } } catch(Exception e) {} 
				try { if(ris != null) { ris.close(); }  } catch(Exception e) {} 
				try { if(ros != null) { ros.close(); } } catch(Exception e) {} 
				if(httpURLConnection != null) { httpURLConnection.disconnect(); }
			}
		}
		
		public static void getProxyPost(String reqUrl, HttpServletRequest request, HttpServletResponse response) {
			HttpURLConnection httpURLConnection = null;
			InputStream is = null; 
			OutputStream os = null;
			InputStream ris = null; 
			OutputStream ros = null; 
			
			try {
				
				StringBuilder sbTargetURL = new StringBuilder();
				sbTargetURL.append(reqUrl);
				Enumeration<String> e = request.getParameterNames();
				
				StringBuffer param = new StringBuffer();
				
				while(e.hasMoreElements()){
					String paramKey = e.nextElement();
					param.append(paramKey+"="+(String)request.getParameter(paramKey)+"&");
				}
				
				String query = param.toString();
				
				query = query.substring(0, query.lastIndexOf("&"));
				URL targetUrl = new URL(sbTargetURL.toString());      
				httpURLConnection = (HttpURLConnection)targetUrl.openConnection(); 
				httpURLConnection.setDoInput(true);
				httpURLConnection.setDoOutput(true); 
				httpURLConnection.setRequestMethod("POST");
				is = new ByteArrayInputStream(query.getBytes("UTF-8"));
				
				os = httpURLConnection.getOutputStream(); 
				int length = 5000; 
				byte[] reqBytes = new byte[length]; 
				int bytesRead = 0; 
				while((bytesRead = is.read(reqBytes, 0, length)) > 0) { 
					os.write(reqBytes, 0, bytesRead);
				} 
				
				ros = response.getOutputStream(); 
				response.setContentType(httpURLConnection.getContentType());
				ris = httpURLConnection.getInputStream();
				
				byte[] resBytes = new byte[length]; 
				bytesRead = 0; 
				while ((bytesRead = ris.read(resBytes, 0, length)) > 0) { 
					ros.write(resBytes, 0, bytesRead); 
				}
			}catch(Exception e) { 
				
				response.setStatus(500); 
			} finally { 
				try { if(is != null) { is.close(); } } catch(Exception e) {} 
				try { if(os != null) { os.close(); } } catch(Exception e) {} 
				try { if(ris != null) { ris.close(); }  } catch(Exception e) {} 
				try { if(ros != null) { ros.close(); } } catch(Exception e) {} 
				if(httpURLConnection != null) { httpURLConnection.disconnect(); }
			}
		}
		
		public static void getProxyGet(String reqUrl, HttpServletRequest request, HttpServletResponse response) {
			HttpURLConnection httpURLConnection = null;
			InputStream is = null; 
			OutputStream os = null;
			InputStream ris = null; 
			OutputStream ros = null; 
			try {
				StringBuffer sbURL = new StringBuffer();
				sbURL.append(reqUrl);
				if(request.getQueryString() != null && !"".equals(request.getQueryString())){
					sbURL.append("?");
					sbURL.append(request.getQueryString());
				}
				
				URL targetUrl = new URL(sbURL.toString());
				System.out.println("*******************************************************");
				System.out.println("******************getProxyGet************************");
				System.out.println(targetUrl);
				System.out.println("*******************************************************");
				System.out.println("*******************************************************");

				httpURLConnection = (HttpURLConnection)targetUrl.openConnection(); 
				httpURLConnection.setDoInput(true);
				httpURLConnection.setDoOutput(false); 
				httpURLConnection.setRequestMethod("GET");
				
				int length = 5000;
				int bytesRead = 0;
				ros = response.getOutputStream(); 
				response.setContentType(httpURLConnection.getContentType());
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8"); 
				ris = httpURLConnection.getInputStream();
				
				byte[] resBytes = new byte[length]; 
				bytesRead = 0; 
				while ((bytesRead = ris.read(resBytes, 0, length)) > 0) { 
					ros.write(resBytes, 0, bytesRead);
				}
			}catch(Exception e) { 
				
				response.setStatus(500); 
			} finally { 
				try { if(is != null) { is.close(); } } catch(Exception e) {} 
				try { if(os != null) { os.close(); } } catch(Exception e) {} 
				try { if(ris != null) { ris.close(); }  } catch(Exception e) {} 
				try { if(ros != null) { ros.close(); } } catch(Exception e) {} 
				if(httpURLConnection != null) { httpURLConnection.disconnect(); }
			}
		}

}