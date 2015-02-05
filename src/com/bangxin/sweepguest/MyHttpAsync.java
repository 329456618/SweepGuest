package com.bangxin.sweepguest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class MyHttpAsync extends AsyncTask<Void, Void, Void> {
	private String strid;
	public MyHttpAsync(String strid) {
		super();
		this.strid = strid;
	}
	
	protected Void doInBackground(Void... params) {
		try {
			System.out.println(submitByGet(SPUtils.getURL(), strid));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public synchronized static String submitByGet(String path, String strid)
			throws IOException {
		String result = null;
		// 加入随机数,也就是现在时间
		// get请求方式
		// 路径:http://10.0.2.2:9080/lsn19_server/userAction!login.do?user.name=sa&user.pwd=123
		// 将参数拼接到URL中
		StringBuffer buff = new StringBuffer(path);
		// 加入随机数
		buff.append(strid).append("&currentTime=")
				.append(System.currentTimeMillis());
		System.out.println(buff.toString());
		URL url = new URL(buff.toString());
		// 打开连接,获取一个连接对象
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			// 获取服务器响应内容
			// 请求成功
			// BufferedReader reader = new BufferedReader(new InputStreamReader(
			// conn.getInputStream()));
			// result = reader.readLine();
			result = convertStreamToString(conn.getInputStream());
		}
		return result;
	}

	// 读出内容
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
