package vestap.sys.dbinfo.meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import vestap.sys.cmm.util.UtilityComponent;

@Controller
public class MetaDBinfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(MetaDBinfoController.class);
	
	@Resource(name = "metaDBinfoService")
	private MetaDBinfoServiceImpl SERVICE;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@RequestMapping(value = "/member/base/dbinfo/meta/list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String metaList(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "start_year", required = false) String start_year,
			@RequestParam(value = "end_year", required = false) String end_year,
			@RequestParam(value = "meta_offer", required = false) String meta_offer,
			@RequestParam(value = "meta_system", required = false) String meta_system,
			@RequestParam(value = "activeOffcanvas", required = false) String activeOffcanvas) {
		
		String viewName = "dbinfo/meta/list";
		
		/**
		 * params 세팅
		 */
		start_year = this.utilComp.setParameter(start_year);
		end_year = this.utilComp.setParameter(end_year);
		meta_offer = this.utilComp.setParameter(meta_offer);
		meta_system = this.utilComp.setParameter(meta_system);
		
		/**
		 * 검색어 확인
		 */
		String[] keywordArr = null;
		List<String> keywordList = null;
		
		if(keyword != null) {
			
			if(keyword.length() > 1) {
				
				keywordArr = keyword.split(" ");
				
				keywordList = new ArrayList<String>(Arrays.asList(keywordArr));
				
			} else {
				/**
				 * 검색어가 null 이 아니면서 길이가 0인 경우는
				 * JSP 페이지에 hidden 형태로 keyword가 있기 때문에 항상 길이가 0인 상태로 들어올 수 있다.
				 * 그때에는 null을 삽입
				 */
				keyword = null;
			}
		}
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 20;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 10);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		params.put("start_year", start_year);
		params.put("end_year", end_year);
		params.put("meta_offer", meta_offer);
		params.put("meta_system", meta_system);
		
		if(keywordList != null) {
			params.put("keywordList", keywordList);
		}
		
		List<Map<String, String>> metaList = null;
		List<String> offerOrgList = null;
		List<String> offerSystemList = null;
		Map<String, String> constructYearInfo = null;
		
		try {
			
			offerOrgList = this.SERVICE.getOfferOrg();
			
			offerSystemList = this.SERVICE.getOfferSystem(meta_offer);
			
			constructYearInfo = this.SERVICE.getConstructYear();
			
			metaList = this.SERVICE.getMetaList(params);
			
			pageInfo.setTotalRecordCount(this.SERVICE.getMetaListTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		model.addAttribute("viewName", this.utilComp.navDepth(viewName));
		model.addAttribute("offerOrgList", offerOrgList);
		model.addAttribute("offerSystemList", offerSystemList);
		model.addAttribute("constructYearInfo", constructYearInfo);
		model.addAttribute("metaList", metaList);
		model.addAttribute("pageInfo", pageInfo);
		
		model.addAttribute("start_year", start_year);
		model.addAttribute("end_year", end_year);
		model.addAttribute("meta_offer", meta_offer);
		model.addAttribute("meta_system", meta_system);
		
		if(keyword != null) {
			model.addAttribute("keyword", keyword);
			model.addAttribute("keywordList", keywordList);
		}
		
		if(activeOffcanvas != null) {
			model.addAttribute("activeOffcanvas", activeOffcanvas);
		}
		
		return viewName;
	}
	
	@RequestMapping(value = "/member/base/dbinfo/meta/getSystem.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView getSystem(@RequestParam(value = "meta_offer", required = false) String meta_offer) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		try {
			
			mav.addObject("offerSystemList", this.SERVICE.getOfferSystem(meta_offer));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/dbinfo/meta/metaInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView metaInfo(@RequestParam(value = "meta_id", required = false) String meta_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		try {
			
			mav.addObject("metaInfo", this.SERVICE.getMetaInfo(meta_id));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/dbinfo/meta/metaIndicator.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView metaIndicator(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "meta_id", required = false) String meta_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 10;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 5);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		params.put("meta_id", meta_id);
		
		List<Map<String, String>> indicatorList = null;
		
		try {
			
			indicatorList = this.SERVICE.getMetaIndicatorList(params);
			
			pageInfo.setTotalRecordCount(this.SERVICE.getMetaIndicatorListTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("indicatorList", indicatorList);
		mav.addObject("pageInfo", pageInfo);
		
		return mav;
	}
}
