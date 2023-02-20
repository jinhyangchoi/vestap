package vestap.adm.climate.cumulative;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface AdmCumulativeService {
	
	public List<EgovMap> selectOptionlist(EgovMap map);

	public List<EgovMap> selectItemlist(EgovMap map);

	public List<EgovMap> selectSidoList();

	public List<EgovMap> selectSigungulist(EgovMap map);

	public List<EgovMap> selectCumulativeList(EgovMap map);

	public EgovMap selectCumulativeSetting(EgovMap map) throws Exception;

	public List<EgovMap> selectCumulativeTotal(EgovMap map);

	public List<EgovMap> selectCumulativeFindIndiNm(EgovMap map);

	public List<EgovMap> selectCumulativeMetaInfo(EgovMap map);

	public List<EgovMap> selectCumulativeRelation(EgovMap map);
	
	public List<EgovMap> selectCumulativeComment(EgovMap map);

	void downloadCumulative(List<EgovMap> list, List<EgovMap> findNm, String sido, String sigungu, EgovMap map, HttpServletResponse response) throws Exception;
	
}
