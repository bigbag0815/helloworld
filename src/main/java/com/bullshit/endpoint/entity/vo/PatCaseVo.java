package com.bullshit.endpoint.entity.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.bullshit.endpoint.entity.Cases;
import com.bullshit.endpoint.entity.ErrInfo;

@XmlRootElement
public class PatCaseVo {
	private String rsStatus;

	/**
	 * @return the rsStatus
	 */
	public String getRsStatus() {
		return this.rsStatus;
	}

	/**
	 * @param rsStatus
	 *            the rsStatus to set
	 */
	public void setRsStatus(String rsStatus) {
		this.rsStatus = rsStatus;
	}

	/**
	 * @return the patcase
	 */
	public Cases getPatcase() {
		return this.Patcase;
	}

	/**
	 * @param patcase
	 *            the patcase to set
	 */
	public void setPatcase(Cases patcase) {
		this.Patcase = patcase;
	}

	/**
	 * @return the errInfo
	 */
	public ErrInfo getErrInfo() {
		return this.errInfo;
	}

	/**
	 * @param errInfo
	 *            the errInfo to set
	 */
	public void setErrInfo(ErrInfo errInfo) {
		this.errInfo = errInfo;
	}

	private Cases Patcase;
	private ErrInfo errInfo;

}
