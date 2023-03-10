package vestap.sys.custom.item;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("customItemService")
@Transactional
public class CustomItemServiceImpl extends EgovAbstractServiceImpl implements CustomItemService {
	
	@Resource(name = "customItemDAO")
	private CustomItemDAO DAO;
	
	@Override
	public List<Map<String, String>> getItemList(Map<String, Object> params) throws Exception {
		return this.DAO.getItemList(params);
	}
	
	@Override
	public List<Map<String, String>> getTemplateItemList(Map<String, Object> params) throws Exception {
		return this.DAO.getTemplateItemList(params);
	}

	@Override
	public int getItemListTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getItemListTotCnt(params);
	}
	
	@Override
	public Map<String, String> getItemListInfo(Map<String, Object> params) throws Exception {
		return this.DAO.getItemListInfo(params);
	}

	@Override
	public List<Map<String, String>> getItemIndicator(Map<String, Object> params) throws Exception {
		return this.DAO.getItemIndicator(params);
	}
	
	@Override
	public List<Map<String, String>> getIndicatorGroup() throws Exception {
		return this.DAO.getIndicatorGroup();
	}

	@Override
	public List<Map<String, String>> getUserGroupIndicator(Map<String, Object> params) throws Exception {
		return this.DAO.getUserGroupIndicator(params);
	}

	@Override
	public int getUserGroupIndicatorTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getUserGroupIndicatorTotCnt(params);
	}

	@Override
	public List<Map<String, String>> getSytmGroupIndicator(Map<String, Object> params) throws Exception {
		return this.DAO.getSytmGroupIndicator(params);
	}

	@Override
	public int getSytmGroupIndicatorTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getSytmGroupIndicatorTotCnt(params);
	}

	@Override
	public List<Map<String, String>> getScenGroupIndicator(Map<String, Object> params) throws Exception {
		return this.DAO.getScenGroupIndicator(params);
	}

	@Override
	public int getScenGroupIndicatorTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getScenGroupIndicatorTotCnt(params);
	}

	@Override
	public int isItemName(Map<String, Object> params) throws Exception {
		return this.DAO.isItemName(params);
	}

	@Override
	@Transactional
	public void insertItem(Map<String, Object> params) throws Exception {
		
		int seq = this.DAO.getItemSequence();
		
		String idx = "TU" + String.format("%06d", seq);
		System.out.println(idx);
		System.out.println(idx);
		System.out.println(idx);
		System.out.println(idx);
		params.put("item_id", idx);
		
		/** ITEM_LIST_USER */
		this.DAO.insertItemList(params);
		
		/** ITEM_INDICATOR_USER */
		this.DAO.insertItemIndicator(params);
		
		/** CLIMATE_OPTION_USER */
		this.DAO.insertClimateOption(params);
		
	}

	@Override
	@Transactional
	public void updateItem(Map<String, Object> params) throws Exception {
		
		/** ITEM_INDICATOR_USER??? ????????? ?????? */
		this.DAO.deleteItemIndicator(params);
		
		/** CLIMATE_OPTION_USER??? ????????? ?????? */
		this.DAO.deleteClimateOption(params);
		
		/** ITEM_LIST_USER??? ????????? ?????? */
		this.DAO.updateItemList(params);
		
		/** ITEM_INDICATOR_USER??? ????????? ?????? */
		this.DAO.insertItemIndicator(params);
		
		/** CLIMATE_OPTION_USER??? ????????? ?????? ??? ????????? ??? ?????? */
		this.DAO.insertClimateOption(params);
	}

	@Override
	@Transactional
	public void itemDelete(Map<String, Object> params) throws Exception {
		
		/** 
		 * ????????? ?????? ?????? ?????? ???
		 * deleteIndicatorData??? deleteIndicatorList ?????? indi_id??? ???????????? ?????? ????????? ?????? ??? ?????? ??????.
		 * */
		
		/** ITEM_INDICATOR_USER??? ????????? ?????? */
		this.DAO.deleteItemIndicator(params);
		
		/** ITEM_LIST_USER??? ????????? ?????? */
		this.DAO.deleteItemList(params);
	}
}
