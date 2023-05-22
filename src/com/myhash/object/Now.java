package com.myhash.object;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Now {
	final static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	final static SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd a kk.mm");
	
	public static String dateTime() {
		return time.format(new Date());
	}
	
	public static String date() {
		return date.format(new Date());
	}
	
}
