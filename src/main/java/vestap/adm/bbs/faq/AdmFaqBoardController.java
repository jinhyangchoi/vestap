package vestap.adm.bbs.faq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import vestap.sys.bbs.cmm.BoardFileVO;
import vestap.sys.bbs.cmm.BoardVO;
import vestap.sys.cmm.util.UtilityComponent;
import vestap.sys.sec.handler.VestapUserDetails;

/**
 * FAQ
 * --------------------------------------------------
 * 		수정일			수정자			수정내용
 * --------------------------------------------------
 * 	2018.10.30			vestap개발		최초 작성
 * --------------------------------------------------
 * @author vestap 개발
 * @since 2018.10.30
 *
 */

@Controller
public class AdmFaqBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdmFaqBoardController.class);
	
	@Resource(name = "admFaqBoardService")
	private AdmFaqBoardServiceImpl SERVICE;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@RequestMapping(value = "/admin/board/faq/list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String faqList(ModelMap model, @RequestParam(required = false) Map<String, String> params) {
		
		String viewName = "admin/board/faq/list";
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 20;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(params.get("page"), LIMIT, 10);
		
		//게시글 목록 불러오기에 필요한 변수 설정
		BoardVO vo = new BoardVO();
		
		vo.setEndRow(LIMIT);
		vo.setStartRow(pageInfo.getFirstRecordIndex());
		vo.setEndRow(pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		vo.setKeywordList(null);
		
		String keyword = null;
		String[] keywordArr = null;
		
		if(params.get("keyword") != null) {
			
			keyword = params.get("keyword");
			
			if(keyword.length() > 0) {
				
				keywordArr = keyword.split(" ");
				
				vo.setKeywordList(new ArrayList<String>(Arrays.asList(keywordArr)));
				vo.setCategory(params.get("category"));
			}
		}
		
		List<BoardVO> list = null;
		
		try {
			
			list = this.SERVICE.getFaqBoard(vo);
			pageInfo.setTotalRecordCount(this.SERVICE.getFaqTotCnt(vo));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
		}

		model.addAttribute("setKeyword", params.get("keyword"));
		model.addAttribute("setCategory", params.get("category"));
		
		model.addAttribute("boardList", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("viewName", this.utilComp.navDepth(viewName));
		model.addAttribute("keywordList", keywordArr);
		
		if(keyword != null) {
			model.addAttribute("keyword", keyword);
		}
		
		return viewName;
	}
	
	@RequestMapping(value = "/admin/board/faq/content.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView faqContent(@RequestParam(value = "idx", required = true) String idx, HttpServletRequest request) {

		Authentication auth = (Authentication) request.getUserPrincipal();
		VestapUserDetails details = (VestapUserDetails) auth.getDetails();
		
		String name = auth.getName();
		String userAuth = details.getUser_auth();
		ModelAndView mav = new ModelAndView("jsonView");
		
		try {
			
			BoardVO vo = this.SERVICE.getFaqContent(Integer.parseInt(idx));
			List<BoardFileVO> files = this.SERVICE.getFaqFile(Integer.parseInt(idx));
			
			mav.addObject("content", vo);
			mav.addObject("files", files);
			mav.addObject("name", name);
			mav.addObject("userAuth", userAuth);

		} catch (NumberFormatException e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/board/faq/write.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String faqWrite(ModelMap model) {
		
		String viewName = "admin/board/faq/write";
		
		model.addAttribute("viewName", this.utilComp.navDepth(viewName));
		
		return viewName;
	}
	
	@RequestMapping(value = "/admin/board/faq/insert.do", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView faqInsert(ModelMap model,
			BoardVO vo, HttpServletRequest request,
			@RequestParam(value = "matchName", required = false) List<String> matchName,
			@RequestParam(value = "uploadFile", required = false) List<MultipartFile> uploadFile) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		if(name != null) {
			
			vo.setBbs_writer(name);
			
			try {
				
				this.SERVICE.setFaqBoard(vo, uploadFile, matchName);
				
				
			} catch (Exception e) {
				logger.error(this.utilComp.stackTrace(e));
			}
			
		} else {
			logger.error("[vestap.sys.bbs.faq.faqDelete] 로그인 되지 않은 사용자가 접근하였습니다.");
		}
		
		return new RedirectView("/admin/board/faq/list.do");
	}
	
	@RequestMapping(value = "/admin/board/faq/download.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void faqDownload(
			HttpServletResponse response,
			@RequestParam(value = "bbs_idx", required = true) String bbs_idx,
			@RequestParam(value = "std_file_nm", required = true) String std_file_nm) {
		
		BoardFileVO vo = new BoardFileVO();
		
		vo.setBbs_idx(Integer.parseInt(bbs_idx));
		vo.setStd_file_nm(std_file_nm);
		
		try {
			
			this.SERVICE.faqBoardDownload(response, vo);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/admin/board/faq/modify.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String faqModify(ModelMap model, @RequestParam(value = "idx", required = true) String idx) {
		
		String viewName = "admin/board/faq/modify";
		
		try {
			
			BoardVO vo = this.SERVICE.getFaqContent(Integer.parseInt(idx));
			List<BoardFileVO> files = this.SERVICE.getFaqFile(Integer.parseInt(idx));
			
			model.addAttribute("idx", idx);
			model.addAttribute("content", vo);
			model.addAttribute("files", files);
			model.addAttribute("viewName", this.utilComp.navDepth(viewName));
			
		} catch (NumberFormatException e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return viewName;
	}
	
	@RequestMapping(value = "/admin/board/faq/update.do", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView faqUpdate(
			BoardVO vo, HttpServletResponse response,
			@RequestParam(value = "idx", required = true) String idx,
			@RequestParam(value = "matchName", required = false) List<String> matchName,
			@RequestParam(value = "uploadFile", required = false) List<MultipartFile> uploadFile,
			@RequestParam(value = "deleteStdFile", required = false) List<String> deleteStdFile) {
		
		try {
			
			this.SERVICE.faqBoardUpdate(response, vo, idx, uploadFile, matchName, deleteStdFile);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return new RedirectView("/admin/board/faq/list.do");
	}
	
	@RequestMapping(value = "/admin/board/faq/delete.do", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView faqDelete(@RequestParam(value = "idx", required = true) String bbs_idx) {
		
		try {
			
			this.SERVICE.deleteFaq(Integer.parseInt(bbs_idx));
			
		} catch (NumberFormatException e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return new RedirectView("/admin/board/faq/list.do");
	}
}
