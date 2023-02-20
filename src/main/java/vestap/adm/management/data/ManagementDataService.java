package vestap.adm.management.data;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface ManagementDataService {
	
//	데이터 관리
	
	List<Map<String, String>> getDataListView(Map<String, Object> params) throws Exception;
	
	int getDataListViewTotCnt(Map<String, Object> params) throws Exception;
	
	Map<String, String> getDataIndicatorList(Map<String, Object> params) throws Exception;
	
	List<Map<String, String>> getDataIndicatorDataInfo(Map<String, Object> params) throws Exception;
	
	
	@Transactional
	String updateDataIndicatorFile(Map<String, Object> params, MultipartFile uploadFile) throws Exception;
	
	String dataIndicatorDownload(Map<String, Object> params) throws Exception;
// 메타 관리	
	List<Map<String, String>> getMetaListView(Map<String, Object> params) throws Exception;
	
	int getMetaListViewTotCnt(Map<String, Object> params) throws Exception;
	
	Map<String, String> getMetaIndicatorList(Map<String, Object> params) throws Exception;
	
	void updateMetaIndicatorList(Map<String, Object> params)throws Exception;
}
