package vestap.sys.custom.indicator;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vestap.sys.sec.handler.VestapUserDetails;
import vestap.sys.sec.user.UserAccessVO;

@Transactional
public interface CustomIndicatorService {
	
	UserAccessVO getUserInfo(String param) throws Exception;
	
	List<Map<String, String>> getDistrictInfo(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getFieldInfo() throws Exception;
	
	int isIndicatorName(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getIpccList(Map<String, Object> params) throws Exception;
	
	int getIndicatorSequence() throws Exception;
	
	@Transactional
	void insertIndicatorSelf(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getIndicatorListView(Map<String, Object> params) throws Exception;
	
	int getIndicatorListViewTotCnt(Map<String, Object> params) throws Exception;
	
	String getReferenceName(String param) throws Exception;
	
	Map<String, String> getUserIndicatorList(Map<String, Object> params) throws Exception;
	
	Map<String, String> getSytmIndicatorList(Map<String, Object> params) throws Exception;
	
	Map<String, String> getScenIndicatorList(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getIndicatorDataInfo(Map<String, Object> params) throws Exception;
	
	int getIndicatorDataInfoTotCnt(Map<String, Object> params) throws Exception;
	
	String indicatorDownload(VestapUserDetails details) throws Exception;
	
	@Transactional
	String insertIndicatorFile(Map<String, Object> params, VestapUserDetails details, MultipartFile uploadFile) throws Exception;
	
	@Transactional
	void indicatorUpdate(Map<String, Object> params) throws Exception;
	
	@Transactional
	void indicatorDelete(Map<String, Object> params) throws Exception;
}
