package com.example.drss.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

import com.example.drss.R;

public class WelcomeActivity extends Activity {

	boolean isFirstIn;
//	public static String sMallId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		// 获取SharedPreferences对象
		SharedPreferences sp = WelcomeActivity.this.getSharedPreferences("SP", MODE_PRIVATE);
		isFirstIn = sp.getBoolean("isFirstIn", true);
		if (isFirstIn) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					goGuide();
				}
			}, 2000);
			Editor editor = sp.edit();
			editor.putBoolean("isFirstIn", false);
			editor.commit();
		} else {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					goHome();
				}
			}, 2000);
		}
	}


	private void goHome() {
		Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
		WelcomeActivity.this.startActivity(intent);
		WelcomeActivity.this.finish();
	}

	private void goGuide() {
		Intent intent = new Intent(WelcomeActivity.this, GuideViewPagerActivity.class);
		WelcomeActivity.this.startActivity(intent);
		WelcomeActivity.this.finish();
	}
}
