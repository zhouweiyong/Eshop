package com.android.vstecs.eshop.control.main;

import android.widget.ImageView;


import com.android.vstecs.eshop.R;
import com.vstecs.android.funframework.ui.activity.BaseSplashActivity;

import java.io.File;

/**
 * @description：启动界面效果
 * @author samy
 * @date 2014年9月16日 上午11:25:04
 */
public class WelcomeAct extends BaseSplashActivity {

	@Override
	protected void setRootBackground(ImageView view) {
		view.setBackgroundResource(R.mipmap.welcome_bg);
		animationDuration = 2000;
	}

	@Override
	protected void initWidget() {
		super.initWidget();
	}

	/**
	 * 重写父类的redirectTo方法，没有检查版本控制
	 */
	@Override
	protected void redirectTo() {
		forwardStep();
	}

	/**
	 * 下一步跳转
	 * 
	 * @author FAN 创建于Dec 1, 2014
	 */
	private void forwardStep() {
		if (firstsInstall()) {// 首次安装
			skipActivity(this, GuideViewAct.class);
		} else {
			skipActivity(this, MainActivity.class);
		}
	}

	@Override
	protected boolean firstsInstall() {
		File files = getFilesDir();
		File installFile = new File(files, "install");

		int newVC = 0;
		try {
			newVC = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
		} catch (Exception e) {}

		boolean firstInstall = installFile.exists();
		if (!firstInstall) {// 文件夹不存在，则表示初次安装
			installFile.mkdirs();
			try {
				new File(installFile, newVC + "").createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			String[] fs = installFile.list();
			if (fs == null || fs.length == 0) {// 上一个版本为空
				try {
					new File(installFile, newVC + "").createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
			String lastV = fs[0];
			if (newVC > Integer.parseInt(lastV)) {
				try {
					new File(installFile, newVC + "").createNewFile();
					for (String vf : fs) {
						File temp = new File(installFile, vf);
						if (temp.exists()) temp.delete();
					}
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}