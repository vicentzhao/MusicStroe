package com.store.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.store.content.Constant;
import com.store.http.FileCache;
import com.store.util.JsonUtil;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class VideoDownload {
	Context con;
	private String downloadUrl;// 下载路径
	File dFile1;
	FileOutputStream outs1;
	private long fileSize = 0;// 原始文件大小
	Handler handler;

	/**
	 * 获取文件大小
	 * 
	 * @return
	 */
	public long getFileSize() {
		return fileSize;
	}

	public VideoDownload(Context con, String url, Handler handler) {
		this.con = con;
		this.downloadUrl = url;
		this.handler = handler;
	}

	//更新进度条(当可以通过getContentLength来获取文件长度的情况下)
	public void updateProgressBar(int readLen,long total) {
		Message msg = new Message();
		msg.what = 1;
		msg.getData().putInt("size", readLen);
		msg.getData().putLong("total", total);
		//通过主线程的handler通知主线程更新UI进度条的进度
		handler.sendMessage(msg);
	}
	
	public void finishProgressBar() {
		Message msg = new Message();
		msg.what = 2;
		//通过主线程的handler通知主线程更新UI进度条的进度
		handler.sendMessage(msg);
	}

	/* 自定义setDataSource，由线程启动 */
	public void setDataSource1() throws Exception {

		URL myURL = new URL("http://www.vocy.com/download/yichang.apk");
		//无SD卡时内存存储
		File myFileTemp = new File(con.getFilesDir(), getFileName(downloadUrl)+  
				 getFileExtension(downloadUrl));   
		downloadFile(myURL, myFileTemp);
        Constant.FILE_NAME = myFileTemp;
        finishProgressBar(); 
	} 
	
	public static void downloadFile(URL source, File destination) {
		try {
			FileUtils.copyURLToFile(source, destination, 6000,3000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	// 取得加载文件的后缀名
	public static String getFileExtension(String strFileName) {
		File myFile = new File(strFileName);
		String strFileExtension = myFile.getName();
		strFileExtension = (strFileExtension.substring(strFileExtension
				.lastIndexOf(".") + 1)).toLowerCase();  

		if (strFileExtension == "") {
			/* 若无法顺利取得扩展名，默认为.dat */
			strFileExtension = "dat";
		}
		return strFileExtension;
	}
 
	// 取得加载文件名
	public static String getFileName(String strFileName) {  
			File myFile = new File(strFileName);
			String strFileExtension = myFile.getName();
			strFileExtension = (strFileExtension.substring(strFileExtension
					.lastIndexOf("\\") + 1,strFileExtension.lastIndexOf(".")+1)).toLowerCase();

			if (strFileExtension == "") {
				/* 若无法顺利取得扩展名，默认为.dat */
				strFileExtension = "dat";
			}
			return strFileExtension;
		}
}
