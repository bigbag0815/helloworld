package com.bullshit.endpoint.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	public static String urlPathEdit(String url, String prefixUrl){
		if (StringUtils.isBlank(url)) {
			return "";
		}
		
		return prefixUrl + url;
	}
}
