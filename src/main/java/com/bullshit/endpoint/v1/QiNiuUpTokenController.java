package com.bullshit.endpoint.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bullshit.endpoint.entity.ErrInfo;
import com.bullshit.endpoint.entity.vo.QiNiuTokenVo;
import com.bullshit.endpoint.exception.ApiException;
import com.bullshit.endpoint.utils.QiNiuUptoken;

@Component
@Path("/v1/qiniu")
public class QiNiuUpTokenController {
	Logger log = LoggerFactory.getLogger(QiNiuUpTokenController.class);
	
	/* 获得七牛与存储Token*/
	@GET
	@Path("/getuptoken")
	@Produces(MediaType.APPLICATION_JSON)
	public QiNiuTokenVo getUpToken ()  throws ApiException{
		
		QiNiuTokenVo qiNiuTokenVo = new QiNiuTokenVo();
		
		try {
			qiNiuTokenVo.setToken(QiNiuUptoken.getUptoken());
			qiNiuTokenVo.setRsStatus("ok");
		} catch (Exception e) {
			e.printStackTrace();
			qiNiuTokenVo.setRsStatus("ng");
			qiNiuTokenVo.setErrInfo(new ErrInfo("500", e.getMessage()));
		}
		
		return qiNiuTokenVo;
	}
}
