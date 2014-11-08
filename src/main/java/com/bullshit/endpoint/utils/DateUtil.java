package com.bullshit.endpoint.utils;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtil {

	public static Date getCurrentDate(){
		return new Timestamp(System.currentTimeMillis());
	}
}
