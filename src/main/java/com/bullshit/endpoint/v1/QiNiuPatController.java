package com.bullshit.endpoint.v1;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bullshit.endpoint.constants.Constants;
import com.bullshit.endpoint.entity.Cases;
import com.bullshit.endpoint.entity.ErrInfo;
import com.bullshit.endpoint.entity.vo.PatCaseVo;
import com.bullshit.endpoint.entity.vo.QiNiuPatCaseReq;
import com.bullshit.endpoint.service.PatBusinessLogic;
import com.bullshit.endpoint.utils.DateUtil;
import com.bullshit.endpoint.utils.StringUtil;

@Component
@Path("/v1/qiniu/pat")
public class QiNiuPatController {
	Logger log = LoggerFactory.getLogger(QiNiuPatController.class);
	
	@Autowired
	PatBusinessLogic patLogic;
	
	/* 病人填写病例(只存储图片名,图片存储在七牛云存储服务器上) */
	@POST
	@Path("/cases/uploads")
	@Produces(MediaType.APPLICATION_JSON)
	public PatCaseVo loadFiles (QiNiuPatCaseReq qiNiuPatCaseReq) {
		String patId = qiNiuPatCaseReq.getPatId();
		String description = qiNiuPatCaseReq.getDescription();
		String patPicUrl1 = StringUtils.defaultString(qiNiuPatCaseReq.getPatPicUrl1(), "");
		String patPicUrl2 = StringUtils.defaultString(qiNiuPatCaseReq.getPatPicUrl2(), "");
		String patPicUrl3 = StringUtils.defaultString(qiNiuPatCaseReq.getPatPicUrl3(), "");
		
		PatCaseVo patCaseVo = new PatCaseVo();
		
		if (StringUtils.isEmpty(patId)) {
			/** "patId is required";*/
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
		
		try {
			Cases patCase = new Cases();
			patCase.setPatId(patId);
			patCase.setPatReport(description);
			patCase.setPatPicUrl1(patPicUrl1);
			patCase.setPatPicUrl2(patPicUrl2);
			patCase.setPatPicUrl3(patPicUrl3);
			patCase.setCaseId(patId + System.currentTimeMillis());
			patCase.setCtime(DateUtil.getCurrentDate());
			patCase.setMtime(DateUtil.getCurrentDate());
			patLogic.savePatCase(patCase);
			
			patCase.setPatPicUrl1(StringUtil.urlPathEdit(patCase.getPatPicUrl1(), Constants.QINIU_PREFIX_URL));
			patCase.setPatPicUrl2(StringUtil.urlPathEdit(patCase.getPatPicUrl2(), Constants.QINIU_PREFIX_URL));
			patCase.setPatPicUrl3(StringUtil.urlPathEdit(patCase.getPatPicUrl3(), Constants.QINIU_PREFIX_URL));
			
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
}
