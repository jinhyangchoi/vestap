package vestap.sys.climate.exposure;

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

@Service("exposureService")
public class ExposureServiceImpl extends EgovAbstractServiceImpl implements ExposureService{

	@Resource(name="exposureDAO")
	private ExposureDAO exposureDAO;
	
	@Resource(name = "userAccessService")
	private UserAccessService userAccessService;
	
	@Resource(name = "estimationService")
	private EstimationService estimationService;
	
	@Override
	public EgovMap selectExposureSetting(EgovMap map) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		VestapUserDetails user = (VestapUserDetails)authentication.getDetails();
		String userAuth = user.getUser_auth();

		/*초기 세팅*/
		String fieldCode = "FC001";
		String itemCode = "TL000001";
		String modelCode = "CM001";
		String sectionCode = "RC002";
		String yearCode = "YC003";

		EgovMap resultMap = new EgovMap();
		
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
		
		/* 항목별 지표 목록 세팅 */
		map.put("item", itemCode);
		map.put("sector", "SEC01");
		List<EgovMap> indiList = this.selectScenarioList(map);
		resultMap.put("indiList", indiList);
		
		/* 기후모델 세팅 */
		List<EgovMap> modelList = this.exposureDAO.selectExposureOption(map);
		resultMap.put("modelList", modelList);
		resultMap.put("setModel", modelCode);
		
		/* RCP 세팅 */
		map.put("model", modelCode);
		List<EgovMap> sectionList = this.exposureDAO.selectExposureOption(map);
		resultMap.put("sectionList", sectionList);
		resultMap.put("setSection", sectionCode);
		
		/* 연대 세팅 */
		map.put("section", sectionCode);
		List<EgovMap> yearList = this.exposureDAO.selectExposureOption(map);
		resultMap.put("yearList", yearList);
		resultMap.put("setYear", yearCode);
		
		/*앙상블 옵션 세팅*/
		map.put("codeGroup", "EXPO_YEAR");
		List<EgovMap> expoYearList = this.estimationService.selectOptionlist(map);
		resultMap.put("expoYearList", expoYearList);
		
		map.put("codeGroup", "VARIABLE");
		List<EgovMap> varList = this.estimationService.selectOptionlist(map);
		resultMap.put("varList", varList);
		
		/* 행정구역 시도 세팅 */
		List<EgovMap> sidoList = this.estimationService.selectSidoList();
		resultMap.put("sidoList", sidoList);
		
		resultMap.put("author", userAuth);
		
		return resultMap;
	}
	
	@Override
	public List<EgovMap> selectAnalysis(EgovMap map) {
		return this.exposureDAO.selectAnalysis(map);
	}

	@Override
	public List<EgovMap> selectEnsembleData(EgovMap map) {
		return this.exposureDAO.selectEnsembleData(map);
	}

	@Override
	public List<EgovMap> selectEnsembleRange(EgovMap map) {
		return this.exposureDAO.selectEnsembleRange(map);
	}

	@Override
	public List<EgovMap> selectScenarioList(EgovMap map) {
		return this.exposureDAO.selectScenarioList(map);
	}
	
}
