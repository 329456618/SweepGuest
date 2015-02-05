package com.bangxin.sweepguest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtils {
	private static SharedPreferences sp;// 都保存在这个sp中
	private static final String URL = "URL";

	/**
	 * 设置sp
	 * 
	 * @param context
	 */
	public static void setSP(Context context) {
		// sp = PreferenceManager.getDefaultSharedPreferences(context);
		sp = context.getSharedPreferences("saic", Context.MODE_PRIVATE);
	}

	public static void setURL(String url) {
		Editor e = sp.edit();
		e.putString(URL, url);
		e.commit();
	}

	public static String getURL() {
		return sp.getString(URL, "");
	}

}
