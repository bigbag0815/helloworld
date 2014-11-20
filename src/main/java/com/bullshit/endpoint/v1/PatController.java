package com.bullshit.endpoint.v1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.bullshit.endpoint.entity.Cases;
import com.bullshit.endpoint.entity.ErrInfo;
import com.bullshit.endpoint.entity.vo.PatCaseVo;
import com.bullshit.endpoint.exception.ApiException;
import com.bullshit.endpoint.service.PatBusinessLogic;
import com.bullshit.endpoint.utils.DateUtil;
import com.bullshit.endpoint.utils.FileUtil;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;

@Component
@Path("/v1/pat")
public class PatController {
	Logger log = LoggerFactory.getLogger(PatController.class);
	
	@Autowired
	PatBusinessLogic patLogic;
	
	/* 病人填写病例 */
	@POST
	@Path("/cases/uploads")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public PatCaseVo loadFiles (
		    @FormDataParam("pat_id") String userId,
		    @FormDataParam("description") String description,
			FormDataMultiPart form)  throws ApiException{
		
		PatCaseVo patCaseVo = new PatCaseVo();
		
		if (StringUtils.isEmpty(userId)) {
			/** "user_id is required";*/
			patCaseVo.setRsStatus("ng");
			patCaseVo.setErrInfo(new ErrInfo("101", "请输入用户名。"));
			return patCaseVo;
		}
		if (StringUtils.isEmpty(description)) {
			/* "description is required";*/
			patCaseVo.setRsStatus("ng");
			patCaseVo.setErrInfo(new ErrInfo("102", "请输入说明信息。"));
			return patCaseVo;
		}
		
		List<FormDataBodyPart> fileList = form.getFields("file");
		if ((!CollectionUtils.isEmpty(fileList)) && fileList.size() > 3) {
			patCaseVo.setRsStatus("ng");
			patCaseVo.setErrInfo(new ErrInfo("103", "请最多入力三张图片。"));
		}
		
		try {
			List<String> locationlist = new ArrayList<String>();
			String currentTime = String.valueOf(System.currentTimeMillis());
			int idx = 1;
			for (FormDataBodyPart part : fileList) {
				/**文件流 * */
			    InputStream is = part.getValueAs(InputStream.class);
			    /** header 中的文件信息*/
			    FormDataContentDisposition detail = part.getFormDataContentDisposition();
			    /** Media type * */
			    // MediaType type = part.getMediaType();
			    
			    if (StringUtils.isNotEmpty(detail.getFileName())) {
				    /**
				     * 这个地方到时候修改成linux系统上的一个位置
				     */
				    String newFileName = userId + currentTime + idx
				    		+ detail.getFileName().substring(detail.getFileName().lastIndexOf("."));
				    idx ++;
					String uploadedFileLocation = "/bullshitpic/" + newFileName;
					
					// save it
					FileUtil.saveFile(is, uploadedFileLocation);
					// String output = "File uploaded to : " + uploadedFileLocation;
					locationlist.add(uploadedFileLocation);
				}
			}
	
			Cases patCase = new Cases();
			patCase.setPatId(userId);
			patCase.setPatReport(description);
			if (locationlist.size() > 0 && StringUtils.isNotEmpty(locationlist.get(0))) {
				patCase.setPatPicUrl1(locationlist.get(0));
			}
			if (locationlist.size() > 1 &&  StringUtils.isNotEmpty(locationlist.get(1))) {
				patCase.setPatPicUrl2(locationlist.get(1));
			}
			if (locationlist.size() > 2 && StringUtils.isNotEmpty(locationlist.get(2))) {
				patCase.setPatPicUrl3(locationlist.get(2));
			}
			patCase.setCaseId(userId + System.currentTimeMillis());
			patCase.setCtime(DateUtil.getCurrentDate());
			patCase.setMtime(DateUtil.getCurrentDate());
			patLogic.savePatCase(patCase);
			
			patCaseVo.setRsStatus("ok");
			patCaseVo.setPatCase(patCase);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.toString());
			patCaseVo.setRsStatus("ng");
			patCaseVo.setErrInfo(new ErrInfo("500", e.getMessage()));
		}
		
		return patCaseVo;
	}
	
//	/* 病人填写病例 */
//	@POST
//	@Path("/cases/upload")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String loadFile (
//		@FormDataParam("user_id") String user_id,
//		@FormDataParam("description") String description,
//		@FormDataParam("file") InputStream uploadedInputStream,
//		@FormDataParam("file") FormDataContentDisposition fileDetail)  throws Exception{
//		
//		System.out.println("user_id："+user_id);
//		System.out.println("description："+description);
//		
//		String uploadedFileLocation = "D:/bullshit/" + fileDetail.getFileName();
//		// save it
//		FileUtil.saveFile(uploadedInputStream, uploadedFileLocation);
//		
//		String output = "File uploaded to : " + uploadedFileLocation;
//		return uploadedFileLocation;
//	}

}
