package com.bullshit.endpoint.utils;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.PutPolicy;

public class QiNiuUptoken {
	
	public static String getUptoken() throws Exception {
		
		Config.ACCESS_KEY = "Klw8b_ljH7R3CUkMhkJJsNYR74rS9f9KxLnIlTwr";
		Config.SECRET_KEY = "xmmqvTciqGlZUQZRGRgWhpYaW3AcCbHwSFTcHCSC";
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		// 请确保该bucket已经存在
		String bucketName = "ziyue991";
		PutPolicy putPolicy = new PutPolicy(bucketName);
		String uptoken = putPolicy.token(mac);
		return uptoken;
	}
}
