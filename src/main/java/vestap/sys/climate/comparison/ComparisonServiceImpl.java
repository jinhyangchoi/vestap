package vestap.sys.climate.comparison;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.sys.climate.estimation.EstimationService;
import vestap.sys.sec.handler.VestapUserDetails;
import vestap.sys.sec.user.UserAccessService;
import vestap.sys.sec.user.UserAccessVO;

@Service("comparisonService")
public class ComparisonServiceImpl extends EgovAbstractServiceImpl implements ComparisonService{

	@Resource(name = "comparisonDAO")
	private ComparisonDAO comparisonDAO;
	
	@Resource(name = "estimationService")
	private EstimationService estimationService;

	@Resource(name = "userAccessService")
	private UserAccessService userAccessService;
	
	@Override
	public EgovMap selectComparisonSetting(EgovMap map) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		VestapUserDetails user = (VestapUserDetails)authentication.getDetails();
		String userDist = user.getUser_dist();
		String userAuth = user.getUser_auth();
		
		/*초기 세팅*/
		String fieldCode = "FC001";
		String itemCode = "TL000001";
		String modelCode = "CM001";
		String sectionCode = "RC002";
		String yearCode = "YC003";

		String type = (String) map.get("type");
		
		EgovMap resultMap = new EgovMap();
		
		UserAccessVO storedUser = this.userAccessService.getUserInfo(user.getUser_id());
		if(storedUser.getUser_item()!=null){
			//취약성이력이 있으면 매칭할 수 있도록 값 세팅
			fieldCode = storedUser.getUser_fld();
			if(!type.equals("CUSTOM")){
				itemCode = storedUser.getUser_item();
			}
			modelCode = storedUser.getUser_scr();
			sectionCode = storedUser.getUser_rcp();
			yearCode = storedUser.getUser_year();
		}
		
		/* 분야 세팅 */
		map.put("codeGroup", "FIELD");
		List<EgovMap> fieldList = this.estimationService.selectOptionlist(map);
		resultMap.put("fieldList", fieldList);
		resultMap.put("setField", fieldCode);
		
		/* 분야별 항목 세팅 */
		map.put("field", fieldCode);
		List<EgovMap> itemList = this.estimationService.selectItemlist(map);
		resultMap.put("itemList", itemList);
		resultMap.put("setItem", itemCode);
		
		/* RCP 세팅 */
		map.put("item", itemCode);
		List<EgovMap> sectionList = this.estimationService.selectClimateOption(map);
		resultMap.put("sectionList", sectionList);
		resultMap.put("setSection", sectionCode);
		
		/* 기후모델 세팅 */
		map.put("section", sectionCode);
		List<EgovMap> modelList = this.estimationService.selectClimateOption(map);
		resultMap.put("modelList", modelList);
		resultMap.put("setModel", modelCode);
		
		/* 연대 세팅 */
		map.put("model", modelCode);
		List<EgovMap> yearList = this.estimationService.selectClimateOption(map);
		resultMap.put("yearList", yearList);
		resultMap.put("setYear", yearCode);
		
		/* 행정구역 시도 세팅 */
		List<EgovMap> sidoList = this.estimationService.selectSidoList();
		resultMap.put("sidoList", sidoList);
		
		String sidoCode = userDist.substring(0, 2);
		if(userAuth.equals("A")) sidoCode = "11"; //관리자는 시도코드 고정
		//기초(시군구) 사용자
		map.put("sidoCode", sidoCode);
		resultMap.put("setSido", sidoCode);
		List<EgovMap> sigunguList = this.estimationService.selectSigungulist(map);
		resultMap.put("sigunguList", sigunguList);
		if(userAuth.equals("B"))
			resultMap.put("setSigungu", userDist);
		
		resultMap.put("author", userAuth);
		
		return resultMap;
	}
	
	@Override
	public List<EgovMap> selectComparisonSgg(EgovMap map) {
		map = addAdjModel(map);
		return this.comparisonDAO.selectComparisonSgg(map);
	}

	@Override
	public List<EgovMap> selectComparisonSd(EgovMap map) {
		map = addAdjModel(map);
		return this.comparisonDAO.selectComparisonSd(map);
	}
	
	private EgovMap addAdjModel(EgovMap map) {
		if(map.get("section").equals("RC001")) {
			if(map.get("model").equals("CM003")) {
				map.put("adjModel", "CM007");
			}else if(map.get("model").equals("CM007")) {
				map.put("adjModel", "CM003");
			}
		}else {
			if(map.get("model").equals("CM001")) {
				map.put("adjModel", "CM003");
			}else if(map.get("model").equals("CM003")) {
				map.put("adjModel", "CM001");
			}
		}
		return map;
	}
	

}
