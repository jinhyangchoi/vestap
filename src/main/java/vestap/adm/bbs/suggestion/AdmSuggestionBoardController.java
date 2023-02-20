package vestap.adm.bbs.suggestion;

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
public class AdmSuggestionBoardController {
	
private static final Logger logger = LoggerFactory.getLogger(AdmSuggestionBoardController.class);
	
	@Resource(name = "admSuggestionBoardService")
	private AdmSuggestionBoardServiceImpl SERVICE;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@RequestMapping(value = "/admin/board/suggestion/list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String suggestionList(ModelMap model, HttpServletRequest request, @RequestParam(required = false) Map<String, String> params) {
		
		String viewName = "admin/board/suggestion/list";
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		VestapUserDetails details = (VestapUserDetails) auth.getDetails();
		
		String name = auth.getName();
		
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
		vo.setBbs_writer(name);
		vo.setUserAuth(details.getUser_auth());
		
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
			
			list = this.SERVICE.getSuggestionBoard(vo);
			pageInfo.setTotalRecordCount(this.SERVICE.getSuggestionTotCnt(vo));

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
	
	@RequestMapping(value = "/admin/board/suggestion/content.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView suggestionContent(HttpServletRequest request, @RequestParam(value = "idx", required = true) String idx ) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		VestapUserDetails details = (VestapUserDetails) auth.getDetails();
		
		
		String name = auth.getName();
		
		BoardVO vo = new BoardVO();
		
		vo.setBbs_idx(Integer.parseInt(idx));
		vo.setBbs_writer(name);
		vo.setUserAuth(details.getUser_auth());
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		try {
			
			int isUser = this.SERVICE.isUserBoard(vo);

			BoardVO content = null;
			
			List<BoardFileVO> files = null;
			
			if(isUser > 0) {
				
				content = this.SERVICE.getSuggestionContent(vo);
				
				files = this.SERVICE.getSuggestionFile(Integer.parseInt(idx));
			}

			
			mav.addObject("content", content);
			mav.addObject("files", files);
			mav.addObject("userAuth", details.getUser_auth());
			mav.addObject("name", name);

		} catch (NumberFormatException e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/board/suggestion/write.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String suggestionWrite(ModelMap model) {
		
		String viewName = "admin/board/suggestion/write";
		
		model.addAttribute("viewName", this.utilComp.navDepth(viewName));
		
		return viewName;
	}
	
	@RequestMapping(value = "/admin/board/suggestion/insert.do", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView suggestionInsert(ModelMap model,
			BoardVO vo, HttpServletRequest request,
			@RequestParam(value = "matchName", required = false) List<String> matchName,
			@RequestParam(value = "uploadFile", required = false) List<MultipartFile> uploadFile) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		if(name != null) {
			
			vo.setBbs_writer(name);
			
			try {
				
				this.SERVICE.setSuggestionBoard(vo, uploadFile, matchName);
				
				
			} catch (Exception e) {
				logger.error(this.utilComp.stackTrace(e));
			}
			
		} else {
			logger.error("[vestap.sys.bbs.suggestion.suggestionDelete] 로그인 되지 않은 사용자가 접근하였습니다.");
		}
		
		return new RedirectView("/admin/board/suggestion/list.do");
	}
	
	@RequestMapping(value = "/admin/board/suggestion/download.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void suggestionDownload(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "bbs_idx", required = true) String bbs_idx,
			@RequestParam(value = "std_file_nm", required = true) String std_file_nm) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		BoardVO checkVO = new BoardVO();
		
		checkVO.setBbs_idx(Integer.parseInt(bbs_idx));
		checkVO.setBbs_writer(name);
		
		int isUser = 0;
		
		try {
			
			isUser = this.SERVICE.isUserBoard(checkVO);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		if(isUser > 0) {
			
			BoardFileVO vo = new BoardFileVO();
			
			vo.setBbs_idx(Integer.parseInt(bbs_idx));
			vo.setStd_file_nm(std_file_nm);
			
			try {
				
				this.SERVICE.suggestionBoardDownload(response, vo);
				
			} catch (Exception e) {
				logger.error(this.utilComp.stackTrace(e));
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "/admin/board/suggestion/modify.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String suggestionModify(ModelMap model, HttpServletRequest request, @RequestParam(value = "idx", required = true) String idx) {
		
		String viewName = "admin/board/suggestion/modify";
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		BoardVO vo = new BoardVO();
		
		vo.setBbs_idx(Integer.parseInt(idx));
		vo.setBbs_writer(name);
		
		try {
			
			int isUser = this.SERVICE.isUserBoard(vo);
			
			BoardVO content = null;
			
			List<BoardFileVO> files = null;
			
			if(isUser > 0) {
				
				content = this.SERVICE.getSuggestionModify(vo);
				
				files = this.SERVICE.getSuggestionFile(Integer.parseInt(idx));
			}
			
			model.addAttribute("idx", idx);
			model.addAttribute("content", content);
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
	
	@RequestMapping(value = "/admin/board/suggestion/update.do", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView suggestionUpdate(
			BoardVO vo, HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idx", required = true) String idx,
			@RequestParam(value = "matchName", required = false) List<String> matchName,
			@RequestParam(value = "uploadFile", required = false) List<MultipartFile> uploadFile,
			@RequestParam(value = "deleteStdFile", required = false) List<String> deleteStdFile) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		vo.setBbs_writer(name);
		vo.setBbs_idx(Integer.parseInt(idx));
		
		try {
			
			int isUser = this.SERVICE.isUserBoard(vo);
			
			if(isUser > 0) {
				this.SERVICE.suggestionBoardUpdate(response, vo, idx, uploadFile, matchName, deleteStdFile);
			}
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return new RedirectView("/admin/board/suggestion/list.do");
	}
	
	@RequestMapping(value = "/admin/board/suggestion/delete.do", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView suggestionDelete(HttpServletRequest request, @RequestParam(value = "idx", required = true) String bbs_idx) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		BoardVO vo = new BoardVO();
		
		vo.setBbs_idx(Integer.parseInt(bbs_idx));
		vo.setBbs_writer(name);
		
		try {
			
			int isUser = this.SERVICE.isUserBoard(vo);
			
			if(isUser > 0) {
				this.SERVICE.deleteSuggestion(vo);
			}
			
		} catch (NumberFormatException e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return new RedirectView("/admin/board/suggestion/list.do");
	}

	/**
	 * 답글 페이지 이동
	 * @param model
	 * @param request
	 * @param idx
	 * @return
	 */
	@RequestMapping(value = "/admin/board/suggestion/RefWrite.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String suggestionRefWrite(ModelMap model,HttpServletRequest request,
			@RequestParam(value = "idx", required = true) String idx) {

		String viewName = "admin/board/suggestion/refWrite";
		Authentication auth = (Authentication) request.getUserPrincipal();
		VestapUserDetails details = (VestapUserDetails) auth.getDetails();
		
		BoardVO vo = new BoardVO();
				
		vo.setBbs_idx(Integer.parseInt(idx));
		vo.setUserAuth(details.getUser_auth());
		
		try {
			int isUser = this.SERVICE.isUserBoard(vo);
			
			BoardVO content = null;
			
			List<BoardFileVO> files = null;
			
			if(isUser > 0) {
				
				content = this.SERVICE.getSuggestionModify(vo);
				
				files = this.SERVICE.getSuggestionFile(Integer.parseInt(idx));
			}
			
			model.addAttribute("idx", idx);
			model.addAttribute("content", content);
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
	
	/**
	 * 답글 저장
	 * @param model
	 * @param vo
	 * @param request
	 * @param idx
	 * @param depth
	 * @param matchName
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping(value = "/admin/board/suggestion/RefInsert.do", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView suggestionRefUpdate(ModelMap model,
			BoardVO vo, HttpServletRequest request,
			@RequestParam(value = "idx", required = true) String idx,
			@RequestParam(value = "depth", required = true) int depth,
			@RequestParam(value = "matchName", required = false) List<String> matchName,
			@RequestParam(value = "uploadFile", required = false) List<MultipartFile> uploadFile) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		if(name != null) {
			vo.setBbs_writer(name);
			vo.setBbs_ref(Integer.parseInt(idx));
			vo.setBbs_depth(depth+1);
			try {
					this.SERVICE.setSuggestionRefBoard(vo, uploadFile, matchName);
				
			} catch (Exception e) {
				logger.error(this.utilComp.stackTrace(e));
			}
			
		} else {
			logger.error("[vestap.sys.bbs.suggestion.suggestionDelete] 로그인 되지 않은 사용자가 접근하였습니다.");
		}
		
		return new RedirectView("/admin/board/suggestion/list.do");
	}
	
}
