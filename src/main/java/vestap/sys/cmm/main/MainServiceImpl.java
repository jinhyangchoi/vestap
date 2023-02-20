package vestap.sys.cmm.main;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import vestap.sys.bbs.cmm.BoardVO;
import vestap.sys.sec.handler.VestapUserDetails;
import vestap.sys.sec.user.UserAccessService;
import vestap.sys.sec.user.UserAccessVO;

@Service("mainService")
@Transactional
public class MainServiceImpl extends EgovAbstractServiceImpl implements MainService {
	
	@Resource(name = "userAccessService")
	private UserAccessService userAccessService;
	
	@Resource(name = "mainDAO")
	private MainDAO DAO;
	
	@Override
	@Transactional
	public List<Map<String, Object>> test() {
		
		List<Map<String, Object>> list = this.DAO.test();
		
		return list;
	}

	public List<BoardVO> getNoticeBoard(BoardVO vo) throws Exception {
		return this.DAO.getNoticeBoard(vo);
	}

	public List<BoardVO> getFaqBoard(BoardVO vo) throws Exception {
		return this.DAO.getFaqBoard(vo);
	}
	
	public List<BoardVO> getReferenceBoard(BoardVO vo) throws Exception {
		return this.DAO.getReferenceBoard(vo);
	}

	@Override
	public List<EgovMap> selectEstimationLogList(EgovMap map) {
		return this.DAO.selectEstimationLogList(map);
	}

	@Override
	public void moveEstimation(EgovMap map) {
		this.DAO.moveEstimation(map);
	}
	
	@Override
	public void moveEstimationNew(EgovMap map) throws Exception {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		VestapUserDetails user = (VestapUserDetails)authentication.getDetails();		
		UserAccessVO storedUser = this.userAccessService.getUserInfo(user.getUser_id());
		
		String userId = user.getUser_id();
		
		//초기세팅
		String fieldCode = "FC001";
		String itemCode = "TL000001";
		String modelCode = "CM001";
		String sectionCode = "RC002";
		String yearCode = "YC003";
		String districtCode = user.getUser_dist();
		
		map.put("userId", userId);
		map.put("model", modelCode);
		map.put("section", sectionCode);
		map.put("year", yearCode);
		map.put("sidoCode", districtCode);
		
		System.out.println("************************************mainserverimpl/**************************");
		System.out.println(map);
		System.out.println("************************************mainserverimpl/**************************");
		this.DAO.moveEstimationNew(map);
	}
	
}
