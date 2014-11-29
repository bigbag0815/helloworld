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

import com.bullshit.endpoint.entity.Account;
import com.bullshit.endpoint.entity.ErrInfo;
import com.bullshit.endpoint.entity.vo.AccountVo;
import com.bullshit.endpoint.entity.vo.QiNiuAccountReq;
import com.bullshit.endpoint.service.AccessBusinessLogic;
import com.bullshit.endpoint.utils.DateUtil;

@Component
@Path("/v1/qiniu/acc")
public class QiNiuAccountController {
	Logger log = LoggerFactory.getLogger(QiNiuAccountController.class);
	
	@Autowired
	AccessBusinessLogic accessLogic;
	
	/* 用户上传个人头像 */
	@POST
	@Path("/upload/avatar")
	@Produces(MediaType.APPLICATION_JSON)
	public AccountVo loadAvatar (QiNiuAccountReq qiNiuAccountReq) {
		String accountId = qiNiuAccountReq.getId();
		String imageUrl = StringUtils.defaultString(qiNiuAccountReq.getImageUrl(), "");
		
		AccountVo accountVo = new AccountVo();
		
		if (StringUtils.isEmpty(accountId)) {
			/** "patId is required";*/
			accountVo.setRsStatus("ng");
			accountVo.setErrInfo(new ErrInfo("101", "请输入用户名。"));
			return accountVo;
		}
		if (StringUtils.isEmpty(imageUrl)) {
			/* "description is required";*/
			accountVo.setRsStatus("ng");
			accountVo.setErrInfo(new ErrInfo("102", "请输入说明信息。"));
			return accountVo;
		}
		
		try {
			Account account = new Account();
			account.setId(accountId);
			account.setImageurl(imageUrl);
			account.setMtime(DateUtil.getCurrentDate());

			if (accessLogic.updateAccount(account) > 0) {
				Account acc = accessLogic
						.getAccountInfo(account.getId());
				accountVo.setAccount(acc);
				accountVo.setRsStatus("ok");
			}else {
				accountVo.setRsStatus("ng");
				accountVo.setErrInfo(new ErrInfo("301", "该用户信息更新失败"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.toString());
			accountVo.setRsStatus("ng");
			accountVo.setErrInfo(new ErrInfo("500", e.getMessage()));
		}
		
		return accountVo;
	}
}
