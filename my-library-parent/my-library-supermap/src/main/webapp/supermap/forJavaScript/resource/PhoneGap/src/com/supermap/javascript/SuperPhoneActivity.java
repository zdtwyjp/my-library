package com.supermap.javascript;

import org.apache.cordova.DroidGap;
import org.json.JSONException;

import android.app.ActivityManager;
import android.os.Bundle;

import com.supermap.RequestControl;

// 继承自PhoneGap的DroidGap类，实现WebView和WebKit的相互通信
public class SuperPhoneActivity extends DroidGap {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setIntegerProperty("splashscreen", R.drawable.splash);

		// 加载clouddemo文件夹下的index页面
		super.loadUrl("file:///android_asset/www/index.html",
				5000);
		try {
			RequestControl.init(this);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// PhoneGap缺陷，需要再次强制退出，释放内存
	public void onDestroy() {
		finish();
		super.onDestroy();

		ActivityManager activityManger = (ActivityManager) this
				.getSystemService(ACTIVITY_SERVICE);
		try {
			activityManger.restartPackage("com.supermap.javascript");
			activityManger.restartPackage("com.supermap");
			activityManger.restartPackage("com.supermap.plugins");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.exit(0);
	}
}