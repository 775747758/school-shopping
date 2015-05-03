package com.school.shopping.rongyun;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;

import org.json.JSONObject;

public class Token {
	
	public static String getRYToken(String uname,String name){
		
		String key = "bmdehs6pdcios";
		String secret = "3SYXDBEukp";
		SdkHttpResult result = null;
		JSONObject a;
		String resultStr=null;
		try {
			result = ApiHttpClient.getToken(key, secret, uname, name,
					"http://img.taopic.com/uploads/allimg/110812/1820-110Q20K24526.jpg", FormatType.json);
			a = new JSONObject(result.toString()).getJSONObject("result");
			resultStr=a.getString("token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultStr;
	}

}
