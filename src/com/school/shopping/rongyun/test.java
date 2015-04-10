package com.school.shopping.rongyun;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;
import io.rong.util.HttpUtil;

import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;


public class test {

	public static void main(String[] args) {
		//http://img.taopic.com/uploads/allimg/110812/1820-110Q20K24526.jpg
		String key = "bmdehs6pdcios";
		String secret = "3SYXDBEukp";
		SdkHttpResult result = null;
		JSONObject a;
		try {
			result = ApiHttpClient.getToken(key, secret, "1", "文杰",
					"http://img.taopic.com/uploads/allimg/110812/1820-110Q20K24526.jpg", FormatType.json);
			a = new JSONObject(result.toString()).getJSONObject("result");
			 System.out.println(a.getString("token"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 

	}

}
