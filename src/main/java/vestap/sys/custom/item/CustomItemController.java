package vestap.sys.custom.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import vestap.sys.cmm.util.UtilityComponent;
import vestap.sys.custom.indicator.CustomIndicatorServiceImpl;

/**
 * 공지사항 게시판
 * --------------------------------------------------
 * 		수정일			수정자			수정내용
 * --------------------------------------------------
 * 	2018.11.06			vestap개발		최초 작성
 * --------------------------------------------------
 * @author vestap 개발
 * @since 2018.11.06
 *
 */

@Controller
public class CustomItemController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomItemController.class);
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@Resource(name = "customIndicatorService")
	private CustomIndicatorServiceImpl INDICATOR;
	
	@Resource(name = "customItemService")
	private CustomItemServiceImpl SERVICE;
	
	@RequestMapping(value = "/member/base/custom/item/list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String itemList(ModelMap model, HttpServletRequest request) {
		
		String viewName = "custom/item/list";
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		String name = auth.getName();
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 20;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(null, LIMIT, 3);
		
		List<Map<String, String>> fieldList = null;
		List<Map<String, String>> indicatorGroupList = null;
		List<Map<String, String>> itemList = null;
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("userName", name);
		params.put("field_id", "FC001");
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		
		try {
			
			fieldList = this.INDICATOR.getFieldInfo();
			
			indicatorGroupList = this.SERVICE.getIndicatorGroup();
			
			itemList = this.SERVICE.getItemList(params);
			
			pageInfo.setTotalRecordCount(this.SERVICE.getItemListTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		model.addAttribute("viewName", this.utilComp.navDepth(viewName));
		model.addAttribute("fieldList", fieldList);
		model.addAttribute("indicatorGroupList", indicatorGroupList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("pageInfo", pageInfo);
		
		return viewName;
	}
	
	@RequestMapping(value = "/member/base/custom/item/fieldItem.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView fieldItem(HttpServletRequest request,
			@RequestParam(value = "page", required = true) String page,
			@RequestParam(value = "field_id", required = true) String field_id,
			@RequestParam(value = "keyword", required = false) String keyword) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 20;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 3);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("userName", name);
		params.put("field_id", field_id);
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		
		if(keyword != null) {
			if(keyword.replaceAll(" ", "").length() > 1) {
				params.put("keyword", keyword.trim());		//문자열의 앞,뒤 공백은 지운다.
			}
		}
		
		List<Map<String, String>> itemList = null;
		
		try {
			
			itemList = this.SERVICE.getItemList(params);
			
			pageInfo.setTotalRecordCount(this.SERVICE.getItemListTotCnt(params));
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("itemList", itemList);
		mav.addObject("pageInfo", pageInfo);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/custom/item/templateItem.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView templateItem(HttpServletRequest request,
			@RequestParam(value = "field_id", required = true) String field_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		String name = auth.getName();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("userName", name);
		params.put("field_id", field_id);
		
		List<Map<String, String>> itemList = null;
		
		try {
			
			itemList = this.SERVICE.getTemplateItemList(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("itemList", itemList);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/custom/item/itemInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView itemInfo(HttpServletRequest request,
			@RequestParam(value = "item_id", required = true) String item_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		String name = auth.getName();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("userName", name);
		params.put("item_id", item_id);
		
		if(item_id.substring(0, 2).equals("TL")) {
			
			params.put("listTable", "ITEM_LIST");
			params.put("dataTable", "ITEM_INDICATOR");
			
		} else if(item_id.substring(0, 2).equals("TU")) {
			
			params.put("listTable", "ITEM_LIST_USER");
			params.put("dataTable", "ITEM_INDICATOR_USER");
		}
		
		List<Map<String, String>> itemIndicatorList = null;
		
		Map<String, String> itemListInfo = null;
		
		try {
			
			itemIndicatorList = this.SERVICE.getItemIndicator(params);
			
			itemListInfo = this.SERVICE.getItemListInfo(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("itemIndicatorList", itemIndicatorList);
		mav.addObject("itemListInfo", itemListInfo);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/custom/item/indicator.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView indicator(HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "group_id", required = true) String group_id) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		String name = auth.getName();
		
		//한번에 보여줄 게시글의 개수
		final int LIMIT = 20;
		
		//페이지 컴포넌트(현재페이지, 한번에 보여줄 게시글의 개수, 한번에 보여줄 페이지의 개수)
		PaginationInfo pageInfo = this.utilComp.pagination(page, LIMIT, 5);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("userName", name);
		params.put("group_id", group_id);
		params.put("startRow", pageInfo.getFirstRecordIndex());
		params.put("endRow", pageInfo.getFirstRecordIndex() + pageInfo.getRecordCountPerPage());
		
		List<Map<String, String>> groupIndicatorList = null;
		
		try {
			
			/**
			 * 이 부분 테이블 정리 되면 변경 해야 됨
			 */
			if(group_id.contains("IG")) {
				
				if(group_id.equals("IG007")) {
					
					groupIndicatorList = this.SERVICE.getUserGroupIndicator(params);
					pageInfo.setTotalRecordCount(this.SERVICE.getUserGroupIndicatorTotCnt(params));
					
				} else {
					
					groupIndicatorList = this.SERVICE.getSytmGroupIndicator(params);
					pageInfo.setTotalRecordCount(this.SERVICE.getSytmGroupIndicatorTotCnt(params));
				}
				
			} else if(group_id.contains("SG")) {
				
				groupIndicatorList = this.SERVICE.getScenGroupIndicator(params);
				pageInfo.setTotalRecordCount(this.SERVICE.getScenGroupIndicatorTotCnt(params));
			}
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("groupIndicatorList", groupIndicatorList);
		mav.addObject("pageInfo", pageInfo);
		
		return mav;
	}
	
	@RequestMapping(value = "/member/base/custom/item/isItemName.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView isItemName(HttpServletRequest request,
			@RequestParam(value = "item_id", required = false) String item_id,
			@RequestParam(value = "itemName", required = true) String itemName) {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		String name = auth.getName();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("itemName", itemName);
		params.put("userName", name);
		params.put("item_id", item_id);
		
		int isName = 1;
		
		try {
			
			isName = this.SERVICE.isItemName(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		mav.addObject("isName", isName);
		
		return mav;
	}
	
	@RequestMapping(value = {"/member/base/custom/item/insert.do", "/member/base/custom/item/update.do"}, method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView itemInsert(HttpServletRequest request,
			@RequestParam Map<String, String> map) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		String name = auth.getName();
		
		/**
		 * 각 부문별 지표가 들어오는 형태
		 * name: indicator-exp-txt-IU000010, indicator-sen-txt-IU000010, indicator-adp-txt-IU000010
		 * 이것을 각각의 부문별로 LIST 화 한다.
		 */
		List<Map<String, Object>> indicatorList = new ArrayList<Map<String, Object>>();	/** 지표 리스트 (지표 & 부문 & 가중치) */
		
		String key = null;
		String exp = "indicator-exp-txt-";
		String sen = "indicator-sen-txt-";
		String adp = "indicator-adp-txt-";
		
		for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			
			key = iter.next();
			
			Map<String, Object> sectorMap = new HashMap<String, Object>();
			
			if(key.contains(exp)) {
				
				sectorMap.put("indi_id", key.replaceAll(exp, ""));
				sectorMap.put("weight", map.get(key));
				sectorMap.put("sector", "SEC01");
				
				indicatorList.add(sectorMap);
				
			} else if(key.contains(sen)) {
				
				sectorMap.put("indi_id", key.replaceAll(sen, ""));
				sectorMap.put("weight", map.get(key));
				sectorMap.put("sector", "SEC02");
				
				indicatorList.add(sectorMap);
				
			} else if(key.contains(adp)) {
				
				sectorMap.put("indi_id", key.replaceAll(adp, ""));
				sectorMap.put("weight", map.get(key));
				sectorMap.put("sector", "SEC03");
				
				indicatorList.add(sectorMap);
			}
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("item_id", map.get("item_id"));
		params.put("userName", name);
		params.put("indicatorList", indicatorList);
		params.put("item_nm", map.get("item-name"));
		params.put("item_account", map.get("item-account"));
		params.put("field_id", map.get("item-field"));
		params.put("ce_weight", map.get("climate-val-1"));
		params.put("cs_weight", map.get("climate-val-2"));
		params.put("aa_weight", map.get("climate-val-3"));
		
		try {
			
			if(request.getRequestURL().toString().contains("/member/base/custom/item/insert")) {
				System.out.println("***********************************************************************");
				System.out.println("******insert******");
				System.out.println(params);
				System.out.println("***********************************************************************");
				this.SERVICE.insertItem(params);
				
			} else {
				System.out.println("***********************************************************************");
				System.out.println("******update******");
				System.out.println(params);
				System.out.println("***********************************************************************");
				this.SERVICE.updateItem(params);
			}
			
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return new RedirectView("/member/base/custom/item/list.do");
	}
	
	@RequestMapping(value = "/member/base/custom/item/delete.do", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView itemDelete(HttpServletRequest request,
			@RequestParam(value = "item_id", required = true) String item_id) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		String name = auth.getName();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("item_id", item_id);
		params.put("userName", name);
		
		try {
			
			this.SERVICE.itemDelete(params);
			
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			e.printStackTrace();
		}
		
		return new RedirectView("/member/base/custom/item/list.do");
	}
}
