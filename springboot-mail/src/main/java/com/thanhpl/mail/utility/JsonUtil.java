package com.thanhpl.mail.utility;

import com.google.gson.Gson;

public class JsonUtil {
	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
}
