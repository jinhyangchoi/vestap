package vestap.sys.dbinfo.meta;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("metaDBinfoService")
public class MetaDBinfoServiceImpl extends EgovAbstractServiceImpl implements MetaDBinfoService {
	
	@Resource(name = "metaDBinfoDAO")
	private MetaDBinfoDAO DAO;
	
	@Override
	public List<Map<String, String>> getMetaList(Map<String, Object> params) throws Exception {
		return this.DAO.getMetaList(params);
	}
	
	@Override
	public int getMetaListTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getMetaListTotCnt(params);
	}

	@Override
	public List<String> getOfferOrg() throws Exception {
		return this.DAO.getOfferOrg();
	}

	@Override
	public List<String> getOfferSystem(String param) throws Exception {
		return this.DAO.getOfferSystem(param);
	}

	@Override
	public Map<String, String> getConstructYear() throws Exception {
		return this.DAO.getConstructYear();
	}

	@Override
	public Map<String, String> getMetaInfo(String param) throws Exception {
		return this.DAO.getMetaInfo(param);
	}

	@Override
	public List<Map<String, String>> getMetaIndicatorList(Map<String, Object> params) throws Exception {
		return this.DAO.getMetaIndicatorList(params);
	}

	@Override
	public int getMetaIndicatorListTotCnt(Map<String, Object> params) throws Exception {
		return this.DAO.getMetaIndicatorListTotCnt(params);
	}
}
