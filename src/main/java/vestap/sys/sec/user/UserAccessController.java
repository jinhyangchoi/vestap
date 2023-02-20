package vestap.sys.sec.user;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.adm.adm.management.AdmAdmManagementService;
import vestap.adm.climate.cumulative.AdmCumulativeDAO;
import vestap.adm.inventory.item.AdmItemInventoryService;
import vestap.adm.user.management.AdmUserManagementService;
import vestap.sys.cmm.util.EncryptionComponent;
import vestap.sys.cmm.util.UtilityComponent;

/**
 * 사용자 로그인, 회원가입, 세션 처리 등 -------------------------------------------------- 수정일
 * 수정자 수정내용 -------------------------------------------------- 2018.08.13
 * vestap개발 최초 작성 --------------------------------------------------
 * 
 * @author vestap 개발
 * @since 2018.08.13
 *
 */

@Controller
public class UserAccessController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAccessController.class);
	
	@Resource(name = "admcumulativeDAO")
	private AdmCumulativeDAO admcumulativeDAO;
	
	@Resource(name = "userAccessService")
	private UserAccessService SERVICE;	
	
	@Resource(name = "encryptionComponent")
	private EncryptionComponent encryptionComponent;
	
	@Resource(name = "admUserManagementService")
	private AdmUserManagementService admUserManagementService;

	@Resource(name = "admItemInventoryService")
	private AdmItemInventoryService admItemInventoryService;
	
	@Resource(name="admAdmManagementService")
	private AdmAdmManagementService admAdmManagementService;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
//	###########################################################################################################################
	
	@RequestMapping(value = "/loginPage.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(Model model,HttpServletRequest request) {
		return "user/login";
	}

	@RequestMapping(value = "/loginSuccessPage.do", method = { RequestMethod.GET, RequestMethod.POST })
	public RedirectView loginSuccess(HttpServletRequest request) {
		return new RedirectView("/member/base/main.do");
	}

	@RequestMapping(value = "/closeSession.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String closeSession(ModelMap model) {
		return "user/login";
	}

	@RequestMapping(value = "/overlapSession.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String overlapSession(ModelMap model) {
		return "exception/overlap-session";
	}

	@RequestMapping(value = "/accessDenied.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String accessDenied(ModelMap model) {
//		return "exception/access-denied";
		return "redirect:/loginPage.do";
	}
	
	@RequestMapping(value = "/pwInquiry.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String pwInquiry(Model model,HttpServletRequest request) {
		return "user/pwInquiry";
	}
	
	@RequestMapping(value = "/user/pwInquiry/idCheck.do")
	@ResponseBody
	public ModelAndView idCheck(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		int result = this.admUserManagementService.selectUserIdCheck(map);
		map.put("data", map.get("userId"));
		EgovMap userInfo = this.admUserManagementService.getUserInfo(map);
		System.out.println(userInfo);
		mv.addObject("chk", result);
		mv.addObject("userInfo",userInfo);
		return mv;
	}
	
	@RequestMapping(value = "/user/pwInquiry/updateAction.do")
	public ModelAndView memberUpdateAction(ModelAndView mv, HttpServletRequest request) throws Exception {
		String viewName = "redirect:/loginPage.do";
		mv.setViewName(viewName);
		EgovMap map = this.paramMap(request);
		System.out.println("\n================================updateAction===============================");
		
		if(map.get("newPw").toString().length()>0){
			String password = encryptionComponent.sha256(map.get("newPw").toString());
			map.put("upUserPw", password);
		}
		
		System.out.println(map);
		this.admUserManagementService.updateUserInfo(map);
		//mv.addObject("viewName", this.utilComp.navDepth(viewName));
		return mv;
	}
	
	@RequestMapping(value = "/survey.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String survey(Model model,HttpServletRequest request) {
		return "user/survey";
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

}
