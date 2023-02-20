package vestap.sys.bbs.cmm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.property.EgovPropertyService;
import vestap.sys.cmm.util.UtilityComponent;

@Controller
public class BbsCmmController {
	
	private static final Logger logger = LoggerFactory.getLogger(BbsCmmController.class);
	
	@Resource(name = "utilityComponent")
	private UtilityComponent utilComp;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	

	@RequestMapping({ "/ck/common/multi_upload.do" })
	@ResponseBody
	public String multiUpload(HttpServletRequest request, HttpServletResponse response,
			MultipartHttpServletRequest uploadfiles) {
		
		if(uploadfiles==null){
			logger.info("97 : uploadfiles : null");
		}else{
			logger.info("99 : uploadfiles : not null");
		}
		/*
		if(multiFiles==null){
			logger.info("11-97 : uploadfiles : null");
		}else{
			logger.info("11-99 : uploadfiles : not null");
			for(int i = 0; i < multiFiles.size(); i++) {
				System.out.println("files: " + multiFiles.get(i).getOriginalFilename());
			}
		}
		*/
		int imgMaxN = this.propertiesService.getInt("imgMaxN");
		
		logger.info("104 : imgMaxN : "+imgMaxN);
		
		long imgMaxSize = this.propertiesService.getLong("imgMaxSize");
		
		logger.info("108 : imgMaxSize : "+imgMaxSize);
		
		long isMaxByteSize = imgMaxSize * 1024 * 1024;
		
		logger.info("112 : isMaxByteSize : "+isMaxByteSize);
		
		List<MultipartFile> fileList = uploadfiles.getFiles("imgfile[]");
	
		logger.info("127 : fileList : "+fileList.toString());

		JSONObject jsonResult = new JSONObject();
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		if (fileList.isEmpty()) {
			
			jsonMap.put("status", "error");
			jsonMap.put("message", "파일이 첨부되지 않았습니다.");
			
			jsonResult = new JSONObject(jsonMap);
			
			logger.info("135 : fileList.isEmpty() : "+fileList.isEmpty());
			
			return jsonResult.toString();
		}
		
		logger.info("140 : fileList.isEmpty() : "+fileList.isEmpty());
		
		if (fileList.size() > imgMaxN) {
			
			jsonMap.put("status", "error");
			jsonMap.put("message", "이미지는 한번에 최대 " + imgMaxN + "개까지 업로드할 수 있습니다.");
			
			jsonResult = new JSONObject(jsonMap);
			
			logger.info("146 : fileList.size() : "+fileList.size());
			
			return jsonResult.toString();
		}

		logger.info("151 : fileList.size() : "+fileList.size());
		
		for (MultipartFile upload : fileList) {
			if (upload.isEmpty()) {
				
				jsonMap.put("status", "error");
				jsonMap.put("message", "파일이 첨부되지 않았습니다.");
				
				jsonResult = new JSONObject(jsonMap);
				
				logger.info("158 : upload.isEmpty() : "+upload.isEmpty());
				
				return jsonResult.toString();
			}
		}
		
		logger.info("164 : upload.isEmpty() : not empty");
		
		for (MultipartFile upload : fileList) {
			if (upload.getSize() > isMaxByteSize) {
				
				jsonMap.put("status", "error");
				jsonMap.put("message", "이미지 하나의 최대 크기는 " + imgMaxSize + "MB입니다");
				
				jsonResult = new JSONObject(jsonMap);
				
				logger.info("169 : upload.getSize() : "+upload.getSize());
				
				return jsonResult.toString();
			}
		}
		
		logger.info("177 : upload.getSize() : OK");
		
		JSONArray fileNames = new JSONArray();
		JSONArray fileCryptos = new JSONArray();
		
		String fileName = null;
		
		for (MultipartFile upload : fileList) {
			
			String[] allowExtension = { "jpg", "jpeg", "png", "gif" };
			logger.info("183 : allowExtension : "+allowExtension.toString());
			String[] data = upload.getOriginalFilename().split(".");
			logger.info("184 : fileExtension : "+data.toString());
			String fileExtension = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf(".")+1);
			logger.info("185 : fileExtension : "+fileExtension);
			
			int returnFlag = 0;
			
			for (int i = 0; i < allowExtension.length; i++) {
				logger.info("190 : allowExtension["+i+"] : "+allowExtension[i]);
				if (allowExtension[i].equals(fileExtension.toLowerCase())) {
					returnFlag++;
					logger.info("193 : returnFlag : "+returnFlag);
				}
			}
			if (returnFlag != 1) {
				
				jsonMap.put("status", "error");
				jsonMap.put("message", upload.getOriginalFilename() + " 업로드 할 수 없는 확장자를 가진 파일입니다.");
				
				jsonResult = new JSONObject(jsonMap);
				
				logger.info("200 : returnFlag : "+returnFlag);
				
				return jsonResult.toString();
			}

			DateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			Date nowDate = new Date();
			fileName = sdFormat.format(nowDate);
			fileName = (new StringBuilder(String.valueOf(fileName))).append("." + fileExtension).toString();
			
			logger.info("210 : fileName : "+fileName);
			
			OutputStream out = null;
			
			try {
				
				byte bytes[] = upload.getBytes();
				String realDir = "C:\\vestap\\file\\board\\images\\";
				
				this.utilComp.makeDirectory(realDir);
				
				/*
				if (UtilityComponent.isWindows())
					realDir = "C:\\vestap\\file\\board\\images\\";
				else
					realDir = "/var/web/data/ckeditor/";
				*/
				String uploadPath = (new StringBuilder(String.valueOf(realDir))).append(fileName).toString();
				
				logger.info("218 : uploadPath : "+uploadPath);
				
				File uploadFile = new File(uploadPath);
				
				logger.info("222 : uploadFile : "+uploadFile);
				
				out = new FileOutputStream(uploadFile);
				out.write(bytes);

				String fileType = new MimetypesFileTypeMap().getContentType(uploadFile);
				
				logger.info("229 : fileType : "+fileType);
				
				fileNames.add(upload.getOriginalFilename());
				fileCryptos.add(fileName);
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null)
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		String realUrl = (new StringBuilder(
				String.valueOf(request.getRequestURL().toString().replace(request.getRequestURI().toString(), ""))))
						//.append("/data/ckeditor/").toString();
						.append("/ck/bbs/images.do?fileName=").toString();
		
		jsonMap.put("url", realUrl);
		jsonMap.put("status", "OK");
		jsonMap.put("name", fileNames);
		jsonMap.put("crypto", fileCryptos);
		
		jsonResult = new JSONObject(jsonMap);
		
		logger.info("267 : jsonResult.toString() : "+jsonResult.toString());
		
		return jsonResult.toString();
	}
	
	@RequestMapping(value = "/ck/bbs/images.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void bbsImages(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "fileName", required = true) String fileName) {
		
		String url = "C:\\vestap\\file\\board\\images\\";
		String fileEx = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		try {
			
			byte fileByte[] = FileUtils.readFileToByteArray(new File(url + fileName));
			
			response.setContentType("image/" + fileEx);
			
			if(fileEx.toLowerCase().equals("jpg") || fileEx.toLowerCase().equals("gif")) {
				response.setContentType("image/jpeg");
			}
			
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8")+"\";");
			response.getOutputStream().write(fileByte);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (IOException e) {
			logger.error(this.utilComp.stackTrace(e));
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
		}
	}
}
