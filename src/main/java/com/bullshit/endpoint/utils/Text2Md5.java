package com.bullshit.endpoint.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Text2Md5 {

	public static String getMD5Text(String plainText) {
		
		StringBuffer buf = new StringBuffer("");
		MessageDigest md;
		
		try {
			md = MessageDigest.getInstance("MD5");

			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

//			System.out.println("result: " + buf.toString());
//
//			System.out.println("result: " + buf.toString().substring(8, 24));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString().substring(8, 24);
	}
}
