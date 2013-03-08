package com.store.download;

import java.io.File;

import com.store.content.Constant;
import com.store.smhilaw1.MainActivity;

import android.os.Environment;

public class DirectoryHelper{
	public void getSdCardPath(){
		File sdcardDir = Environment.getExternalStorageDirectory(); 
		String path = sdcardDir.getParent() + sdcardDir.getName();
		Constant.filePath = "/data/data/"+MainActivity.FILE_PATH+"/shuzifaxing/";
		createFile();
	}

	public void createFile(){
		try{
			// 1.判断是否存在sdcard
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())){
				// 目录
				File path = new File(Constant.filePath);
				if (!path.exists()){
					// 2.创建目录，可以在应用启动的时候创建
					path.mkdirs();
				}

			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
