package com.store.http;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.store.bean.AppBean;
import com.store.util.AuthoSharePreference;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class ApkLoadTask extends AsyncTask<String, AppBean, Void> {
	private Context context;

	public ApkLoadTask(Context mContext) {
		context = mContext;
	}

	protected Void doInBackground(String... params) {

		PackageManager pManager = context.getPackageManager();
		List<PackageInfo> appList = getAllApps(context);
		for (int i = 0; i < appList.size(); i++) {

			PackageInfo pinfo = appList.get(i);
			ApplicationInfo appInfo = pinfo.applicationInfo;
			// 判断是不是手机预安装的程序     <= 0（非预安装程序）
			if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {

				Drawable icon = pManager
						.getApplicationIcon(pinfo.applicationInfo);
				String appName = (String) pManager.getApplicationLabel(
						pinfo.applicationInfo).toString();
				String PackageName = pinfo.packageName.toString();
				String appDir = pinfo.applicationInfo.publicSourceDir;
				int appSize = Integer.valueOf((int) new File(appDir).length());
				String size = sizeFormat(appSize);
				
				AppBean item = new AppBean();
				item.setAppName(appName);
				item.setIcon(icon);
				item.setPackgeName(PackageName);
				item.setSize(size);
				if (appDir.substring(appDir.indexOf("/") + 1,
						appDir.indexOf("/", 1)).equals("mnt")) {
					item.setLocaltion("安装位置：SDcard");
				} else {
					item.setLocaltion("安装位置：手机内存");
				}
				publishProgress(item);
			}
		}
		return null;
	}

	protected void onPreExecute() {
	}

	public void onProgressUpdate(AppBean... files) {
		if (isCancelled())
			return;
		AppBean bean = files[0];
		AuthoSharePreference.appList.add(bean);
	}

	public static List<PackageInfo> getAllApps(Context context) {
		List<PackageInfo> apps = new ArrayList<PackageInfo>();
		PackageManager pManager = context.getPackageManager();
		// 获取手机内所有应用
		List<PackageInfo> paklist = pManager.getInstalledPackages(0);
		for (int i = 0; i < paklist.size(); i++) {
			PackageInfo pak = (PackageInfo) paklist.get(i);
			apps.add(pak);
		}
		return apps;
	}

	public String sizeFormat(long size) {
		DecimalFormat df = new DecimalFormat("###.##");
		float f;
		if (size < 1024 * 1024) {
			f = (float) ((float) size / (float) 1024);
			return (df.format(new Float(f).doubleValue()) + "KB");
		} else {
			f = (float) ((float) size / (float) (1024 * 1024));
			return (df.format(new Float(f).doubleValue()) + "MB");
		}
	}

}
