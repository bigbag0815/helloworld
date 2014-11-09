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

import com.bullshit.endpoint.entity.DocSurgeryPlan;
import com.bullshit.endpoint.entity.ErrInfo;
import com.bullshit.endpoint.entity.vo.DocSurgeryPlanVo;
import com.bullshit.endpoint.exception.ApiException;
import com.bullshit.endpoint.service.DocBusinessLogic;
import com.bullshit.endpoint.utils.DateUtil;
import com.bullshit.endpoint.utils.FileUtil;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;

@Component
@Path("/v1/doc")
public class DocController {
	Logger log = LoggerFactory.getLogger(DocController.class);

	@Autowired
	DocBusinessLogic docLogic;
	
	/* 医生上传手术日程表 */
	@POST
	@Path("/plan/uploads")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public DocSurgeryPlanVo loadFiles (
		    @FormDataParam("doc_id") String userId,
		    @FormDataParam("description") String description,
			FormDataMultiPart form)  throws ApiException{
		
		DocSurgeryPlanVo docSurgeryPlanVo = new DocSurgeryPlanVo();

		if (StringUtils.isEmpty(userId)) {
			/** "user_id is required";*/
			docSurgeryPlanVo.setRsStatus("ng");
			docSurgeryPlanVo.setErrInfo(new ErrInfo("101", "请输入用户名。"));
			return docSurgeryPlanVo;
		}
		if (StringUtils.isEmpty(description)) {
			/* "description is required";*/
			docSurgeryPlanVo.setRsStatus("ng");
			docSurgeryPlanVo.setErrInfo(new ErrInfo("102", "请输入说明信息。"));
			return docSurgeryPlanVo;
		}
		
		List<FormDataBodyPart> fileList = form.getFields("file");
		if ((!CollectionUtils.isEmpty(fileList)) && fileList.size() > 1) {
			docSurgeryPlanVo.setRsStatus("ng");
			docSurgeryPlanVo.setErrInfo(new ErrInfo("103", "请最多入力一张图片。"));
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
	
			DocSurgeryPlan docSurgeryPlan = new DocSurgeryPlan();
			docSurgeryPlan.setPlanId(userId + System.currentTimeMillis());
			docSurgeryPlan.setDocId(userId);
			if (locationlist.size() > 0 && StringUtils.isNotEmpty(locationlist.get(0))) {
				docSurgeryPlan.setSurgeryPlanPicUrl(locationlist.get(0));
			}
			docSurgeryPlan.setDescription(description);
			docSurgeryPlan.setCtime(DateUtil.getCurrentDate());
			docSurgeryPlan.setMtime(DateUtil.getCurrentDate());
			
			docLogic.saveSurgeryPlan(docSurgeryPlan);
			
			docSurgeryPlanVo.setRsStatus("ok");
			docSurgeryPlanVo.setDocSurgeryPlan(docSurgeryPlan);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.toString());
			docSurgeryPlanVo.setRsStatus("ng");
			docSurgeryPlanVo.setErrInfo(new ErrInfo("500", e.getMessage()));
		}
		
		return docSurgeryPlanVo;
	}
}
