package vestap.adm.ems.web;

import java.io.File;
import vestap.adm.ems.service.AdmSndngMailRegistService;
import vestap.adm.ems.service.AdmSndngMailVO;
import vestap.com.ComParamLogger;
import vestap.com.ComSecurityUserUtil;
import vestap.egov.cmm.service.EgovFileMngService;
import vestap.egov.cmm.service.EgovFileMngUtil;
import vestap.egov.cmm.util.EgovStringUtil;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 발송메일등록, 발송요청XML파일 생성하는 컨트롤러 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.12
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      	수정자          수정내용
 *  ----------     --------    ---------------------------
 *  2009.03.12  	박지욱          최초 생성 
 *  2011.12.06  	이기하          메일 첨부파일이 기능 추가  
 *  
 *  </pre>
 */
@Controller
public class AdmSndngMailRegistController {

	/** EgovSndngMailRegistService */
	@Resource(name = "admSndngMailRegistService")
    private AdmSndngMailRegistService admSndngMailRegistService;
	
	/** EgovFileMngService */
	@Resource(name="EgovFileMngService")
	private EgovFileMngService fileMngService;	
	
	/** EgovFileMngUtil */
	@Resource(name="EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
    /** 파일구분자 */
    static final char FILE_SEPARATOR = File.separatorChar;
    
    /**
	 * 발송메일 등록화면으로 들어간다
	 * @param admSndngMailVO AdmSndngMailVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/admin/ems/insertSndngMailView.do")
    public String insertSndngMailView(@ModelAttribute("admSndngMailVO") AdmSndngMailVO admSndngMailVO, ModelMap model) throws Exception {
    	
		boolean adminChk = ComSecurityUserUtil.hasAdminRole();
		if(adminChk == false) {
			return "redirect:/loginPage.do";
		}
		
    	model.addAttribute("resultInfo", admSndngMailVO);
    	
    	return "admin/ems/insertSndngMailView";
    }
    
    /**
	 * 발송메일을 등록한다
	 * @param multiRequest MultipartHttpServletRequest
	 * @param admSndngMailVO AdmSndngMailVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/admin/ems/insertSndngMail.do")
	public String insertSndngMail(final MultipartHttpServletRequest multiRequest,
			@ModelAttribute("admSndngMailVO") AdmSndngMailVO admSndngMailVO,
			ModelMap model, HttpServletRequest request) throws Exception {
    	
    	boolean adminChk = ComSecurityUserUtil.hasAdminRole();
		if(adminChk == false) {
			return "redirect:/loginPage.do";
		}
		
    	ComParamLogger.requestGetParameterToEgovMap("AdmSndngMailRegistController.java", "insertSndngMail", "req", request);
    	
    	String sj = EgovStringUtil.nullConvert(request.getParameter("sj").toString());
    	String emailCn = EgovStringUtil.nullConvert(request.getParameter("emailCn").toString());
    	String posblAtchFileNumber = EgovStringUtil.nullConvert(request.getParameter("posblAtchFileNumber").toString());
    	
    	String link = "N";
    	if (admSndngMailVO != null && admSndngMailVO.getLink() != null && !admSndngMailVO.getLink().equals("")) {
    		link = admSndngMailVO.getLink();
    	}
    	
    	SecurityContext context = SecurityContextHolder.getContext();		// 시큐리티 컨텍스트 객체
		Authentication authentication = context.getAuthentication();		// 인증 객체
		String user = (String) authentication.getPrincipal();               // 로그인한 사용자정보
		/*
    	List<FileVO> _result = new ArrayList<FileVO>();	// 2012.11 KISA 보안조치
    	String _atchFileId = "";
    	final Map<String, MultipartFile> files = multiRequest.getFileMap();
    	if(!files.isEmpty()){
	    	_result = fileUtil.parseFileInf(files, "MSG_", 0, "", ""); 
	    	_atchFileId = fileMngService.insertFileInfs(_result);  //파일이 생성되고나면 생성된 첨부파일 ID를 리턴한다.
    	}
		  
		String orignlFileList = "";
		for (int i = 0; i < _result.size(); i++) {
			FileVO fileVO = _result.get(i);
			orignlFileList = fileVO.getOrignlFileNm();
		}
		*/
    	if (admSndngMailVO != null) {
//    		admSndngMailVO.setAtchFileId(_atchFileId);
    		admSndngMailVO.setDsptchPerson(user);
    		admSndngMailVO.setSj(sj);
    		admSndngMailVO.setEmailCn(emailCn);
//    		admSndngMailVO.setOrignlFileNm(orignlFileList);
		}
    	
    	// 발송메일을 등록한다.
    	boolean result = admSndngMailRegistService.insertSndngMail(admSndngMailVO);
    	
    	if (result) {
    		if (link.equals("N")) {
    			return "redirect:/admin/ems/selectSndngMailList.do";
    		} else {
    			model.addAttribute("closeYn", "Y");
    	    	return "admin/ems/adminEmsMailRegist";
    		}
    	} else {
    		return "egovframework/com/cmm/EgovError";
    	}
	}
    
    /**
	 * 발송메일 내용조회로 돌아간다.
	 * @param admSndngMailVO SndngMailVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/admin/ems/backSndngMailRegist.do")
	public String backSndngMailRegist(@ModelAttribute("admSndngMailVO") AdmSndngMailVO admSndngMailVO, ModelMap model) throws Exception {
    	
    	boolean adminChk = ComSecurityUserUtil.hasAdminRole();
		if(adminChk == false) {
			return "redirect:/loginPage.do";
		}
		
    	return "redirect:/admin/ems/selectSndngMailList.do";
	}
    
}