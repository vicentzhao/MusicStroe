package com.store.smhilaw1;

import java.util.zip.Inflater;

import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class PlayActivity extends Activity{
	private boolean isplay;
	private boolean needResume;
	 VideoView mVideoView;
	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoview);
		 String ms = "http://192.168.1.32:8080/index/download.action?token=908d05b6-15ee-4b0a-8aa8-be639ed4332c|k212F6D6E742F6469676974616C2F6D75736963736F757263652F31332F30332F32302F3133363337343430343030383730303030312E6D7034|k21E681B0E4BCBCE4BDA0E79A84E6B8A9E69F9420";

		Intent intent = getIntent();
		String path=intent.getStringExtra("extra");
		Intent i = new Intent();
		i.putExtra("path", path);
		mVideoView = (VideoView)findViewById(R.id.surface_view);
		mVideoView.setVideoPath(ms);
		mVideoView.setVideoQuality(io.vov.vitamio.MediaPlayer.VIDEOQUALITY_HIGH);
		mVideoView.setMediaController(new MediaController(PlayActivity.this));
		mVideoView.setBufferSize(1024);
		
//		    
//			 this.setOnKeyListener(new OnKeyListener() {
//				@Override
//				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//					// TODO Auto-generated method stub
//					if(keyCode==KeyEvent.KEYCODE_BACK){
//						System.out.println("监听到返回键");
//						if(isplay){
//							if(mVideoView!=null){
//								mVideoView.stopPlayback();
//							}
//						}
//						  return false;
//					}
//					return false;
//				}
//			});
			 mVideoView.setOnInfoListener(new OnInfoListener() {
				@Override
				public boolean onInfo(io.vov.vitamio.MediaPlayer arg0, int arg1, int arg2) {
					final ProgressDialog Dialog = ProgressDialog.show(PlayActivity.this, "正在加载。。", "正在加载请稍后。。");
					switch (arg1) {
			        case io.vov.vitamio.MediaPlayer.MEDIA_INFO_BUFFERING_START:
			            //开始缓存，暂停播放
			            if (mVideoView.isPlaying()) {
			            	mVideoView.pause();
			                needResume = true;
			            }
			           Dialog.show();
			            break;
			        case io.vov.vitamio.MediaPlayer.MEDIA_INFO_BUFFERING_END:
			            //缓存完成，继续播放
			            if (needResume)
			            	mVideoView.start();
			            Dialog.dismiss();
			            break;
			        case io.vov.vitamio.MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
			            //显示 下载速度
			            System.out.println("下载的速度===="+arg2);
			            break;
			        }
			        return true;
				}
			});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			System.out.println("监听到返回键");
			if(isplay){
				if(mVideoView!=null){
					mVideoView.stopPlayback();
				}
			}
			  return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
