package vestap.adm.climate.estimation;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.egov.cmm.service.impl.EgovComAbstractDAO;

@Repository("admEstimationDAO")
public class AdmEstimationDAO extends EgovComAbstractDAO{

	public List<EgovMap> selectWholeSgg(EgovMap map) {
		return selectList("wholeEstimation.selectWholeSgg", map);
	}

	public List<EgovMap> selectWholeEmd(EgovMap map){
		return selectList("wholeEstimation.selectWholeEmd", map);
	}

	public List<EgovMap> selectEstimationResultEmdData(EgovMap map) {
		return selectList("wholeEstimation.selectEstimationResultEmdData", map);
	}

	public List<EgovMap> selectEstimationIndiWeight(EgovMap map){
		return selectList("wholeEstimation.selectEstimationIndiWeight", map);
	}

	public List<EgovMap> selectEstimationEmdIndiWeight(EgovMap map){
		return selectList("wholeEstimation.selectEstimationEmdIndiWeight", map);
	}
	
	public List<EgovMap> selectWholeSggExcel(EgovMap map) {
		return selectList("wholeEstimation.selectWholeSggExcel", map);
	}

	public List<EgovMap> selectWholeEmdExcel(EgovMap map) {
		return selectList("wholeEstimation.selectWholeEmdExcel", map);
	}
	
	public List<EgovMap> selectItemIndiList(EgovMap map) {
		return selectList("wholeEstimation.selectItemIndiList", map);
	}
	
	public List<EgovMap> selectItemIndiAllList(EgovMap map){
		return selectList("wholeEstimation.selectItemIndiAllList", map);
	}
	
	public List<EgovMap> selectindiitemYearList(EgovMap map) {
		return selectList("wholeEstimation.selectindiitemYearList", map);
	}
	
	public List<EgovMap> selectindiitemSDYearList(EgovMap map) {
		return selectList("wholeEstimation.selectindiitemSDYearList", map);
	}
	
	public List<EgovMap> selectDistrictList(){
		return selectList("wholeEstimation.selectDistrictList");
	}
	public List<EgovMap> selectDistrictEmdList(){
		return selectList("wholeEstimation.selectDistrictEmdList");
	}
	
}
