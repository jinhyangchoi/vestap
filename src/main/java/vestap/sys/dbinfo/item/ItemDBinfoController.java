package vestap.sys.dbinfo.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import vestap.sys.custom.indicator.CustomIndicatorServiceImpl;

@Controller
public class ItemDBinfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(ItemDBinfoController.class);
	
	@Resource(name = "customIndicatorService")
	private CustomIndicatorServiceImpl INDICATOR;
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@Resource(name = "itemDBinfoService")
	private ItemDBinfoServiceImpl SERVICE;
	
	/**
	 * 항목 목록
	 * @param model
	 * @param request
	 * @param page
	 * @param field
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value = "/member/base/dbinfo/item/list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String itemList(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "field", required = false) String field,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "activeOffcanvas", required = false) String activeOffcanvas) {
		
		String viewName = "dbinfo/item/list";
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 20;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 10);
		
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
		
		if(field == null) {
			field = "all";
		} else {
			if(field.length() < 1) {
				field = "all";
			}
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(keywordList != null) {
			params.put("keywordList", keywordList);
		}
		
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		params.put("field", field);
		
		List<Map<String, String>> fieldList = null;
		List<Map<String, String>> itemList = null;
		
		try {
			
			fieldList = this.INDICATOR.getFieldInfo();
			
			itemList = this.SERVICE.getItemList(params);
			
			pageInfo.setTotalRecordCount(this.SERVICE.getItemListTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		model.addAttribute("viewName", this.utilComp.navDepth(viewName));
		model.addAttribute("fieldList", fieldList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("field", field);
		
		if(keyword != null) {
			model.addAttribute("keyword", keyword);
			model.addAttribute("keywordList", keywordList);
		}

		
		if(activeOffcanvas != null) {
			model.addAttribute("activeOffcanvas", activeOffcanvas);
		}
		
		return viewName;
	}
	
	/**
	 * 항목 상세보기
	 * @param item_id
	 * @param sector_id
	 * @return
	 */
	@RequestMapping(value = "/member/base/dbinfo/item/indicatorInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView indicatorInfo(
			@RequestParam(value = "item_id", required = true) String item_id,
			@RequestParam(value = "sector_id", required = true) String sector_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("item_id", item_id);
		params.put("sector_id", sector_id);
		
		List<Map<String, String>> indicatorInfoList = null;
		
		try {
			
			indicatorInfoList = this.SERVICE.getIndicatorInfo(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("indicatorInfoList", indicatorInfoList);
		
		return mav;
	}
	
	/**
	 * 항목 상세보기 다운로드
	 * @param response
	 * @param item_id
	 */
	@RequestMapping(value = "/member/base/dbinfo/item/downloadIndicatorInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadIndicatorInfo(HttpServletResponse response,
			@RequestParam(value = "item_id", required = true) String item_id) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("item_id", item_id);
		params.put("sector_id", "all");
		
		try {
			
			this.SERVICE.downloadIndicatorInfo(params, response);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/member/base/dbinfo/item/itemInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView itemInfo(@RequestParam(value = "item_id", required = true) String item_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("item_id", item_id);
		
		try {
			
			mav.addObject("itemInfo", this.SERVICE.getItemInfo(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return mav;
	}
}
