package com.store.download;

import java.io.File;

import com.store.content.Constant;
import com.store.smhilaw1.MainActivity1;

import android.os.Environment;

public class DirectoryHelper{
	public void getSdCardPath(){
		File sdcardDir = Environment.getExternalStorageDirectory(); 
		String path = sdcardDir.getParent() + sdcardDir.getName();
		Constant.filePath = "/data/data/"+MainActivity1.FILE_PATH+"/shuzifaxing/";
		createFile();
	}

	public void createFile(){
		try{
			// 1.�ж��Ƿ����sdcard
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())){
				// Ŀ¼
				File path = new File(Constant.filePath);
				if (!path.exists()){
					// 2.����Ŀ¼��������Ӧ��������ʱ�򴴽�
					path.mkdirs();
				}

			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
