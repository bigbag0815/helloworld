package com.bullshit.endpoint.utils;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
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
	
	public static String getUptokenByKey(String key) throws Exception {
		
		Config.ACCESS_KEY = "Klw8b_ljH7R3CUkMhkJJsNYR74rS9f9KxLnIlTwr";
		Config.SECRET_KEY = "xmmqvTciqGlZUQZRGRgWhpYaW3AcCbHwSFTcHCSC";
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		// 请确保该bucket已经存在
		String bucketName = "ziyue991";
		PutPolicy putPolicy = new PutPolicy(bucketName);
		putPolicy.scope = bucketName+":"+key;
		String uptoken = putPolicy.token(mac);
		return uptoken;
	}
	
//	public static void main(String[] args) {
//		try {
////			System.out.println(getUptokenByKey("1416492818675"));
//			
//			String uptoken = getUptokenByKey("1416492818675");
//			String localFile ="C:/Users/ydw/Pictures/6e336dfb06604df588d10058e5073d93.jpg";
//			PutExtra extra = new PutExtra();
//			PutRet ret = IoApi.putFile(uptoken, "1416492818675", localFile, extra);
//			System.out.println(ret.ok());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
