package vestap.adm.ems.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import vestap.adm.ems.service.AdmSndngMailDtlsService;
import vestap.adm.ems.service.AdmSndngMailVO;
import vestap.com.ComParamLogger;
import vestap.egov.cmm.EgovMessageSource;
import vestap.sys.cmm.util.UtilityComponent;

/**
 * 발송메일 내역을 조회하는 컨트롤러 클래스
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
 *  
 *  </pre>
 */
@Controller
public class AdmSndngMailDtlsController {
	
	/** AdmSndngMailDtlsService */
	@Resource(name = "admSndngMailDtlsService")
    private AdmSndngMailDtlsService admSndngMailDtlsService;
	
	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
    
    private Logger logger = Logger.getLogger(this.getClass());
    
    /**
	 * 발송메일 내역을 조회한다
	 * @param AdmSndngMailVO admSndngMailVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/admin/ems/selectSndngMailList.do")
	public ModelAndView selectSndngMailList(@ModelAttribute("admSndngMailVO") AdmSndngMailVO admSndngMailVO, ModelMap model, @RequestParam(required = false) Map<String, String> params) throws Exception {
    	
    	ComParamLogger.paramToVO("AdmSndngMailDtlsController", "selectSndngMailList", "admSndngMailVO", admSndngMailVO);
    	
		int startRow  = admSndngMailVO.getStartRow();
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 20;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		String pageNum = params.get("page");
		PaginationInfo pageInfo = this.utilComp.pagination(pageNum, LIMIT, 10);
		
		if(startRow ==0) {
			admSndngMailVO.setStartRow(pageInfo.getFirstRecordIndex());
			admSndngMailVO.setEndRow(pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		}
//        int totCnt = admSndngMailDtlsService.selectSndngMailListTotCnt(admSndngMailVO);
		pageInfo.setTotalRecordCount(admSndngMailDtlsService.selectSndngMailListTotCnt(admSndngMailVO));
		model.addAttribute("startRow", pageInfo.getFirstRecordIndex());
		model.addAttribute("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		model.addAttribute("pageInfo", pageInfo);
		
		// 발송메일 내역 조회
        List sndngMailList = admSndngMailDtlsService.selectSndngMailList(admSndngMailVO);
        model.addAttribute("resultList", sndngMailList);
        model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));
        
        //검색조건 유지
        String searchCondition  = admSndngMailVO.getSearchCondition();
		String searchKeyword    = admSndngMailVO.getSearchKeyword();
		String searchResultCode = admSndngMailVO.getSearchResultCode();
		model.addAttribute("searchCondition", searchCondition);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("searchResultCode", searchResultCode);
		
		String viewName = "admin/ems/selectSndngMailList";
		ModelAndView mv = new ModelAndView();
        mv.addObject("viewName", this.utilComp.navDepth(viewName));
        mv.setViewName(viewName);
        
        return mv;
	}
    
    /**
	 * 발송메일을 삭제한다.
	 * @param admSndngMailVO AdmSndngMailVO
	 * @return String
	 * @exception
	 */
    @RequestMapping(value="/admin/ems/deleteSndngMailList.do")
	public String deleteSndngMailList(@ModelAttribute("sndngMailVO") AdmSndngMailVO admSndngMailVO, ModelMap model) throws Exception {
    	
//    	if (admSndngMailVO == null || admSndngMailVO.getMssageId() == null || admSndngMailVO.getMssageId().equals("")) {
    	if (admSndngMailVO == null) {
    		return "egovframework/com/cmm/egovError";
    	}
    	
    	// 1. 발송메일을 삭제한다.
    	admSndngMailDtlsService.deleteSndngMailList(admSndngMailVO);
    	
        // 2. 발송메일 목록 페이지 이동
    	return "redirect:/admin/ems/selectSndngMailList.do";
	}
}