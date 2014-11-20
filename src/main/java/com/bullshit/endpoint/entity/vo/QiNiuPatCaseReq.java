package com.bullshit.endpoint.entity.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QiNiuPatCaseReq {
	private String patId;
	private String description;
	private String patPicUrl1;
	private String patPicUrl2;
	private String patPicUrl3;
	
	public String getPatId() {
		return patId;
	}
	
	public void setPatId(String patId) {
		this.patId = patId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPatPicUrl1() {
		return patPicUrl1;
	}
	
	public void setPatPicUrl1(String patPicUrl1) {
		this.patPicUrl1 = patPicUrl1;
	}
	
	public String getPatPicUrl2() {
		return patPicUrl2;
	}
	
	public void setPatPicUrl2(String patPicUrl2) {
		this.patPicUrl2 = patPicUrl2;
	}
	
	public String getPatPicUrl3() {
		return patPicUrl3;
	}
	
	public void setPatPicUrl3(String patPicUrl3) {
		this.patPicUrl3 = patPicUrl3;
	}
	
}
