package com.bangxin.sweepguest;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zxing.CaptureActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private TextView main_text;
	private Button main_but;
	private int sum = 0;
	private AlertDialog dialog;
	private EditText url;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SPUtils.setSP(this);
		main_text = (TextView) findViewById(R.id.main_text);
		main_but = (Button) findViewById(R.id.main_but);
		main_text.setOnClickListener(this);
		main_but.setOnClickListener(this);
		initDiaog();
		
	}

	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.main_but:
			Intent intent = new Intent(this, CaptureActivity.class);
			startActivity(intent);
			break;
		case R.id.main_text:
			Main_text();
			break;
		default:
			break;
		}
	}

	private void Main_text() {
		sum++;
		// Toast.makeText(this, String.valueOf(sum), Toast.LENGTH_SHORT).show();
		if (sum == 1) {
			// 清零
			new Timer().schedule(new TimerTask() {
				public void run() {
					sum = 0;
				}
			}, 2 * 1000);
		} else if (sum == 5) {
			// 显示
			showdialog();
		}
	}

	private void showdialog() {
		url.setText(SPUtils.getURL());
		dialog.show();
	}

	private void initDiaog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("修改URL");
		// 加载自定义布局
		View layout = LayoutInflater.from(this).inflate(R.layout.main_dialog,
				null);
		url = (EditText) layout.findViewById(R.id.main_dialog);
		// 绑定
		builder.setView(layout);
		// 确定按钮
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String urlHttp = url.getText().toString();
				// Pattern pattern = Pattern
				// .compile("http://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*");
				// Matcher matcher = pattern.matcher(urlHttp);
				// if (matcher.find()) {
				SPUtils.setURL(urlHttp);
				// } else {
				// Toast.makeText(MainActivity.this, "输入网址..", 1).show();
				// }
			}
		});
		// 取消按钮
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 关闭
				dialog.dismiss();
			}
		});
		dialog = builder.create();
	}
}
