package com.store.download;

import com.store.http.HttpRequest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;

public class ThreadForRunnable implements Runnable {
	String TAG = "***********************";

	Context con;
	ProgressBar myProgressBar;
	Handler handler;

	public ThreadForRunnable(Context con, ProgressBar myProgressBar,
			Handler handler) {
		this.con = con;
		this.myProgressBar = myProgressBar;
		this.handler = handler;
	}

	@Override
	public void run() {

		String url = HttpRequest.URL_QUERY_DOWNLOAD_URL+HttpRequest.DOWNLOAD_ID;
		try {
			// 在线程运行中，调用自定义函数抓下文件
			VideoDownload vd = new VideoDownload(con, url, handler); 
			vd.setDataSource1();
			//vd.setDataSource1();// 下特定视频文件
			//vd.setDataSource2();//下载exe文件
			//vd.setDataSource();// 下载apk文件
			//vd.videoGet();
			myProgressBar.setMax((int) vd.getFileSize());// 设置进度条的最大刻度
		} catch (Exception e) {
			Message msg = new Message();
			msg.what = -1;
			msg.getData().putString("error", "下载失败");
			handler.sendMessage(msg);
			Log.e(TAG, e.toString());
		}

	}

}
