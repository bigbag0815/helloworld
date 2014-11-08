package com.bullshit.endpoint.v1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import com.bullshit.endpoint.entity.Cases;
import com.bullshit.endpoint.exception.ApiException;
import com.bullshit.endpoint.service.PatBusinessLogic;
import com.bullshit.endpoint.utils.DateUtil;
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
	public Cases loadFiles (
		    @FormDataParam("user_id") String user_id,
		    @FormDataParam("description") String description,
			FormDataMultiPart form)  throws Exception{
		
		Cases patcase=new Cases();
		
		if (StringUtils.isEmpty(user_id)) {
			/** "user_id is required";*/
			return patcase;
		}
		if (StringUtils.isEmpty(description)) {
			/* "description is required";*/
			return patcase;
		}
		
		List<FormDataBodyPart> fileList= form.getFields("file");
		List<String> locationlist=new ArrayList<String>();
		for (FormDataBodyPart part : fileList) {
			  /**文件流 * */
		      InputStream is=part.getValueAs(InputStream.class);
		      /** header 中的文件信息*/
		      FormDataContentDisposition detail=part.getFormDataContentDisposition();
		      /** Media type * */
		      MediaType type=part.getMediaType();
		      /**
		       * 这个地方到时候修改成linux系统上的一个位置
		       * */
				String uploadedFileLocation = "D:/bullshit/" + detail.getFileName();
				if (StringUtils.isNotEmpty(detail.getFileName())) {
					 System.out.println("File Name："+detail.getFileName());
					 System.out.println("File Type："+detail.getType());
					 String newFileName=user_id+"_"+String.valueOf(System.currentTimeMillis())+detail.getFileName().substring(detail.getFileName().lastIndexOf("."));
					 System.out.println("New FileName："+newFileName);
					// save it
					saveFile(is, uploadedFileLocation);
					String output = "File uploaded to : " + uploadedFileLocation;
					locationlist.add(uploadedFileLocation);
				}
		}
		System.out.println("user_id："+user_id);
		System.out.println("description："+description);
		patcase.setPatId(user_id);
		patcase.setPatReport(description);
		if (locationlist.size() > 0 && StringUtils.isNotEmpty(locationlist.get(0))) {
			patcase.setPatPicUrl1(locationlist.get(0));
		}
		if (locationlist.size() > 1 &&  StringUtils.isNotEmpty(locationlist.get(1))) {
			patcase.setPatPicUrl2(locationlist.get(1));
		}
		if (locationlist.size() > 2 && StringUtils.isNotEmpty(locationlist.get(2))) {
			patcase.setPatPicUrl3(locationlist.get(2));
		}
		patcase.setCaseId(user_id+System.currentTimeMillis());
		patcase.setCtime(DateUtil.getCurrentDate());
		patcase.setMtime(DateUtil.getCurrentDate());
		System.out.println("============================"+locationlist.toString());
		patLogic.savePatCase(patcase);
		return patcase;
	}
	
	/* 病人填写病例 */
	@POST
	@Path("/cases/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String loadFile (
		@FormDataParam("user_id") String user_id,
		@FormDataParam("description") String description,
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail)  throws ApiException{
		
		System.out.println("user_id："+user_id);
		System.out.println("description："+description);
		
		String uploadedFileLocation = "D:/bullshit/" + fileDetail.getFileName();
		// save it
		saveFile(uploadedInputStream, uploadedFileLocation);
		
		
		String output = "File uploaded to : " + uploadedFileLocation;
		return uploadedFileLocation;
	}
	
	private void saveFile(InputStream uploadedInputStream,	String location) throws ApiException {
		try {
			OutputStream outpuStream = new FileOutputStream(new File(location));
			int read = 0;
			byte[] bytes = new byte[1024];
			outpuStream = new FileOutputStream(new File(location));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
