package com.bullshit.endpoint.entity.vo;

import com.bullshit.endpoint.entity.ErrInfo;

public class BaseVo {

	private String rsStatus;
	private ErrInfo errInfo;

	public String getRsStatus() {
		return rsStatus;
	}

	public void setRsStatus(String rsStatus) {
		this.rsStatus = rsStatus;
	}

	public ErrInfo getErrInfo() {
		return errInfo;
	}

	public void setErrInfo(ErrInfo errInfo) {
		this.errInfo = errInfo;
	}
}
