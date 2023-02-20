package vestap.adm.ems.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vestap.adm.ems.service.AdmSndngMailDetailService;
import vestap.adm.ems.service.AdmSndngMailVO;
import vestap.com.ComParamLogger;
import vestap.egov.cmm.EgovWebUtil;
import vestap.egov.cmm.service.Globals;
import vestap.sys.cmm.util.UtilityComponent;

/**
 * 발송메일을 상세 조회하는 컨트롤러 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.12
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.12  박지욱          최초 생성
 *  2011.10.10	이기하			보안점검 후속조치(교차접속 스크립트 공격 취약성 방지(파라미터 문자열 교체), HTTP 응답분할 방지)
 *
 *  </pre>
 */
@Controller
public class AdmSndngMailDetailController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdmSndngMailDetailController.class);

	/** EgovSndngMailDetailService */
	@Resource(name = "admSndngMailDetailService")
    private AdmSndngMailDetailService admSndngMailDetailService;

    @Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
    
    /**
	 * 발송메일을 상세 조회한다.
	 * @param admSndngMailVO AdmSndngMailVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/admin/ems/selectSndngMailDetail.do")
	public ModelAndView selectSndngMailDetail(@ModelAttribute("admSndngMailVO") AdmSndngMailVO admSndngMailVO, ModelMap model) throws Exception {
    	
    	ComParamLogger.paramToVO("AdmSndngMailDetailController", "selectSndngMailDetail", "admSndngMailVO", admSndngMailVO);
    	
    	/*
    	if (admSndngMailVO == null) {
    		viewName = "redirect:/loginPage.do";
    	}
    	*/
    	
    	int startRow  = admSndngMailVO.getStartRow();
    	int endRow    = admSndngMailVO.getEndRow();
//    	int pageIndex = admSndngMailVO.getPageIndex();
    	int currentPageNo = admSndngMailVO.getCurrentPageNo();
    	String searchCondition = admSndngMailVO.getDetailFormSearchCondition();
    	String searchKeyword = admSndngMailVO.getDetailFormSearchKeyword();
    	String searchResultCode = admSndngMailVO.getDetailFormSearchResultCode();
    	
    	// 1. 발송메일을 상세 조회한다.
    	AdmSndngMailVO resultMailVO = admSndngMailDetailService.selectSndngMail(admSndngMailVO);
    	resultMailVO.setDetailFormSearchCondition(searchCondition);
    	resultMailVO.setDetailFormSearchKeyword(searchKeyword);
    	resultMailVO.setDetailFormSearchResultCode(searchResultCode);
    	resultMailVO.setStartRow(startRow);
    	resultMailVO.setEndRow(endRow);
    	resultMailVO.setPageIndex(currentPageNo);
    	
    	// 2. 결과 리턴
        model.addAttribute("resultInfo", resultMailVO);
        
        String viewName = "";
        if (resultMailVO.getMssageId() !=0) {
        	// 발송메일 상세조회 화면 이동
        	viewName = "admin/ems/selectSndngMailDetail";
        } else {
        	// 오류 페이지 이동
        	viewName = "redirect:/loginPage.do";
        }
        
        ModelAndView mv = new ModelAndView();
        mv.addObject("viewName", this.utilComp.navDepth(viewName));
        mv.setViewName(viewName);
        
        return mv;
	}

    /**
	 * 발송메일을 삭제한다.
	 * @param admSndngMailVO AdmSndngMailVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/admin/ems/deleteSndngMail.do")
	public String deleteSndngMail(@ModelAttribute("admSndngMailVO") AdmSndngMailVO admSndngMailVO,
			ModelMap model) throws Exception {

//    	if (admSndngMailVO == null || admSndngMailVO.getMssageId() == null || admSndngMailVO.getMssageId().equals("")) {
    	if (admSndngMailVO == null) {
    		return "redirect:/loginPage.do";
    	}

    	// 1. 발송메일을 삭제한다.
    	admSndngMailDetailService.deleteSndngMail(admSndngMailVO);

    	// 2. 첨부파일을 삭제한다.
    	admSndngMailDetailService.deleteAtchmnFile(admSndngMailVO);

        // 3. 발송메일 목록 페이지 이동
    	return "redirect:/admin/ems/selectSndngMailList.do";
	}

    /**
	 * 발송메일 내용조회로 돌아간다.
	 * @param admSndngMailVO AdmSndngMailVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/admin/ems/backSndngMailDetail.do")
	public String backSndngMailDtls(@ModelAttribute("admSndngMailVO") AdmSndngMailVO admSndngMailVO, ModelMap model) throws Exception {

    	return "redirect:/admin/ems/selectSndngMailList.do";
	}

    /**
	 * XML형태의 발송요청메일을 조회한다.
	 * @param admSndngMailVO AdmSndngMailVO
	 * @exception Exception
	 */
    @RequestMapping(value="/admin/ems/selectSndngMailXml.do")
	public void selectSndngMailXml(@ModelAttribute("admSndngMailVO") AdmSndngMailVO admSndngMailVO,
			HttpServletResponse response,
			ModelMap model) throws Exception {
    	String xmlFile = Globals.MAIL_REQUEST_PATH + admSndngMailVO.getMssageId() + ".xml";
    	File uFile = new File(EgovWebUtil.filePathBlackList(xmlFile));
    	int fSize = (int) uFile.length();

    	// 2011.10.10 보안점검 후속 조치
    	if (fSize > 0) {
            String mimetype = "application/x-msdownload;charset=UTF-8";

            //response.setBufferSize(fSize);
            response.setContentType(mimetype);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + uFile.getName() + "\"");
            response.setContentLength(fSize);

            BufferedInputStream in = null;
	    try {
		in = new BufferedInputStream(new FileInputStream(uFile));
		FileCopyUtils.copy(in, response.getOutputStream());
	    } finally {
			if (in != null) {
			    try {
				in.close();
			    } catch (Exception ignore) {
			    	LOGGER.debug("IGNORED: {}", ignore.getMessage());
	
			    }
			}
	    }
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } else {
            response.setContentType("application/x-msdownload");
	    PrintWriter printwriter = response.getWriter();
	    printwriter.println("<html>");
	    printwriter.println("<br><br><br><h2>Could not get file name:<br>" + EgovWebUtil.clearXSSMinimum(xmlFile) + "</h2>");
	    printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
	    printwriter.println("<br><br><br>&copy; webAccess");
	    printwriter.println("</html>");
	    printwriter.flush();
	    printwriter.close();
        }
	}
}