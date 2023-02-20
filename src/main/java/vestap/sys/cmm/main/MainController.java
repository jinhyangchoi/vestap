package vestap.sys.cmm.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import vestap.sys.bbs.cmm.BoardVO;
import vestap.sys.climate.estimation.EstimationService;
import vestap.sys.cmm.util.UtilityComponent;

@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Resource(name = "mainService")
	private MainService SERVICE;
	
	@Resource(name = "estimationService")
	private EstimationService estimationService;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	
	@RequestMapping(value = "/")
	public RedirectView org(Model model) {
		return new RedirectView("/loginPage.do"); 
	}
	
	@RequestMapping(value = "/member/base/main.do")
	public String main(Model model, @RequestParam(required = false) Map<String, String> params) {
		
		//List<Map<String, Object>> list = this.SERVICE.test();
		//공지사항
				//한번에 보여줄 게시글의 개수
				final int LIMIT = 5;
				
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
				
				List<BoardVO> noticeList = null;
				List<BoardVO> faqList = null;
				List<BoardVO> referenceList = null;
				
				try {
					
					noticeList = this.SERVICE.getNoticeBoard(vo);
					faqList = this.SERVICE.getFaqBoard(vo);
					referenceList = this.SERVICE.getReferenceBoard(vo);
					
				} catch (Exception e) {
					logger.error(this.utilComp.stackTrace(e));
				}
				
				model.addAttribute("noticeList", noticeList);
				model.addAttribute("faqList", faqList);
				model.addAttribute("referenceList", referenceList);
				
				if(keyword != null) {
					model.addAttribute("keyword", keyword);
				}
				
				//선택된 부문 취약성평가 항목 리스트
				
				
				//최근 취약성평가 조회
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				EgovMap map = new EgovMap();
				String userId = authentication.getName();
				map.put("userId", userId);
				map.put("field", "FC001");
				List<EgovMap> estiLogList = this.SERVICE.selectEstimationLogList(map);
				List<EgovMap> list = this.estimationService.selectItemlist(map);
				model.addAttribute("logList", estiLogList);
				model.addAttribute("itemList", list);
				System.out.println(list);
		return "main";
	}
	
	
	@RequestMapping(value = "/member/base/moveEstimation.do")
	@ResponseBody
	public ModelAndView moveEstimation(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		this.SERVICE.moveEstimation(map);
		
		return mv;
	}
	
	@RequestMapping(value = "/member/base/moveEstimationNew.do")
	@ResponseBody
	public ModelAndView moveEstimationNew(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		this.SERVICE.moveEstimationNew(map);
		
		return mv;
	}
	
	@RequestMapping(value = "/member/base/selectRisk.do")
	@ResponseBody
	public ModelAndView selectRisk(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("jsonView");
		EgovMap map = this.paramMap(request);
		//this.SERVICE.moveEstimation(map);
		
		System.out.println(map);
		List<EgovMap> list = this.estimationService.selectItemlist(map);
		
		mv.addObject("itemList", list);
		
		return mv;
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
