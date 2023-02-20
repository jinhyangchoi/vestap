package vestap.adm.sys.item;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("sysItemService")
@Transactional
public class SysItemServiceImpl extends EgovAbstractServiceImpl implements SysItemService{

	@Resource(name = "sysItemDAO")
	private SysItemDAO DAO;
	
	@Override
	public List<Map<String, String>> getIndicatorGroup() throws Exception {
		return this.DAO.getIndicatorGroup();
	}
	
	@Override
	public List<Map<String, String>> getItemList(Map<String, Object> params) throws Exception {
		return this.DAO.getItemList(params);
	}
	
	@Override
	public int getItemListTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getItemListTotCnt(params);
	}
	
	@Override
	public List<Map<String, String>> getItemIndicator(Map<String, Object> params) throws Exception {
		return this.DAO.getItemIndicator(params);
	}
	
	@Override
	public Map<String, String> getItemListInfo(Map<String, Object> params) throws Exception {
		return this.DAO.getItemListInfo(params);
	}
	
	@Override
	public int isItemName(Map<String, Object> params) throws Exception {
		return this.DAO.isItemName(params);
	}
	
	@Override
	@Transactional
	public void insertItem(Map<String, Object> params) throws Exception {
		
		int seq = this.DAO.getItemSequence();
		
		String idx = "TL" + String.format("%06d", seq);
		System.out.println(idx);
		System.out.println(idx);
		System.out.println(idx);
		System.out.println(idx);
		System.out.println(idx);
		System.out.println(idx);
		System.out.println(idx);
		System.out.println(idx);
		System.out.println(idx);
		System.out.println(idx);
		params.put("item_id", idx);
		
		/** ITEM_LIST_USER */
		this.DAO.insertItemList(params);
		
		/** ITEM_INDICATOR_USER */
		this.DAO.insertItemIndicator(params);
		
		/** CLIMATE_OPTION_SYS */
		this.DAO.insertClimateOption(params);
		
	}
	
	@Override
	@Transactional
	public void updateItem(Map<String, Object> params) throws Exception {
		
		/** ITEM_INDICATOR_SYS의 데이터 삭제 */
		this.DAO.deleteItemIndicator(params);
		
		/** CLIMATE_OPTION_SYS의 데이터 제거 */
		this.DAO.deleteClimateOption(params);
		
		/** ITEM_LIST_SYS의 데이터 수정 */
		this.DAO.updateItemList(params);
		
		/** ITEM_INDICATOR_SYS의 데이터 삽입 */
		this.DAO.insertItemIndicator(params);
		
		/** CLIMATE_OPTION_SYS 제거 후 삽입 */
		this.DAO.insertClimateOption(params);
	}
	
	@Override
	@Transactional
	public void itemDelete(Map<String, Object> params) throws Exception {
		
		/** 
		 * 순서에 맞게 호출 해야 함
		 * deleteIndicatorData는 deleteIndicatorList 에서 indi_id가 사용자와 일치 하는지 확인 후 삭제 한다.
		 * */
		
		/** CLIMATE_OPTION_SYS의 데이터 삭제 */
		this.DAO.deleteClimateOption(params);
		
		/** ITEM_INDICATOR_SYS의 데이터 삭제 */
		this.DAO.deleteItemIndicator(params);
		
		/** ITEM_LIST_SYS의 데이터 삭제 */
		this.DAO.deleteItemList(params);
		
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
	public List<Map<String, String>> getTemplateItemList(Map<String, Object> params) throws Exception {
		return this.DAO.getTemplateItemList(params);
	}
}
