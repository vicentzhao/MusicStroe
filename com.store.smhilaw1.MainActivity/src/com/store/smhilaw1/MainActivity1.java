package com.store.smhilaw1;



//import io.vov.vitamio.MediaPlayer.OnInfoListener;
//
//import io.vov.vitamio.widget.MediaController;
//import io.vov.vitamio.widget.VideoView;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.store.adapter.MusicAdapter;
import com.store.bean.Music;
import com.store.bean.OrderBean;
import com.store.bean.PayOrderBean;
import com.store.bean.PostMent;
import com.store.bean.SoftwareBean;
import com.store.bean.VersionInfo;
import com.store.content.CommUtil;
import com.store.content.Constant;
import com.store.download.MediaPlayerListener;
import com.store.download.ThreadForRunnable;
import com.store.http.HttpRequest;
import com.store.http.HttpRequest.OnBitmapHttpResponseListener;
import com.store.http.HttpRequest.OnHttpResponseListener;
import com.store.http.ImageDownloader;
import com.store.suffix.CryptUtil;
import com.store.util.JsonUtil;
import com.store.util.UpdateVersion;
import com.store.util.XmlParse;

public class MainActivity1 extends FragmentActivity implements LeftSelectedListener, RightSelectedListener,OnClickListener{
	private static int left_type = Constant.FLFG;
	// private static RightSecond rSecond;
	private static String TAG ="MusicStore";
	private static boolean isSecondRFlag = false;// 右边第二级是否被选中
	public static String DETAIL_ID = null;
	private static DetailFragment detailF;
	private static RightFragment rFragment;
	private static RelativeLayout leftlayout;
	private static HttpRequest http;
	public static ArrayList<SoftwareBean> softlist;
	public static ArrayList<OrderBean> softOrderlist;//当前可订购列表
	public static ArrayList<PayOrderBean> payOrderlist;//订单
	private static String right_grid_id =null;
	public static String FILE_PATH =null;
	public static View mainView;
	public static View itemView;
	public static Button myMusic,store,serch;
	public static MusicAdapter musicAdapter;
	public static ArrayList<HashMap<String, String>> musicTestArrayList;
	private static AQuery aQuery;
	private static SharedPreferences.Editor editor;
	public  static boolean   isFragment =true;
	private boolean isfirst =true;
	private static MediaPlayer mp;
	static ArrayList<Music> musicList;
	static ArrayList<SoftwareBean> musicAppList;
	static Dialog builder;
    private static View viewForsoftDetail;
    private static View viewFormusicdetail;
	private static boolean isFristInit;
	private static boolean isplay=false;
	private static boolean needResume;
	 boolean  isUpdate =false;;
	public static String appDownPath;
	private static ArrayList<SoftwareBean> musicDetailList;
	private static int isWhatLeft=100013;
	private static int isWhatRight=100011;
	static LayoutInflater inflater;
	private static musicTryplayAsyncTask musicTryTask;
	private static Button classify, the_news, recommend, movie, teleplay, anime,softInstallButton,
	music, record,soft;
	SharedPreferences  sp;
	private static  int[] horItems = {R.id.item_hor_01,R.id.item_hor_02,R.id.item_hor_03,
			R.id.item_hor_04,R.id.item_hor_05,R.id.item_hor_06,
			R.id.item_hor_07,R.id.item_hor_08,R.id.item_hor_09,
			R.id.item_hor_10,R.id.item_hor_11,R.id.item_hor_12,R.id.item_hor_13,R.id.item_hor_14,R.id.item_hor_15};
	
	private static  int[] musiclistItem = {R.id.music_item_hor_01,R.id.music_item_hor_02,R.id.music_item_hor_03,
		R.id.music_item_hor_04,R.id.music_item_hor_05,R.id.music_item_hor_06,
		R.id.music_item_hor_07,R.id.music_item_hor_08,R.id.music_item_hor_09,
		R.id.music_item_hor_10};
//	private static  int[] recommendItem = {R.id.music1,R.id.music2,R.id.music3,
//		R.id.music4,R.id.music5,R.id.music6,
//		R.id.music7,R.id.music8,R.id.music9,
//		R.id.music10};
	private static int[] orderRadioItem={R.id.rad1,R.id.rad2,R.id.rad3,R.id.rad4};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_main_1920);
		FILE_PATH =getPackageName();
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()       
        .detectNetwork()  
        .penaltyLog()       
        .build());       
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()    
        .penaltyLog()       
        .penaltyDeath()       
        .build());  
        sp =getPreferences(MODE_PRIVATE);
        aQuery= new AQuery(MainActivity1.this);
        initView();
        editor = sp.edit(); 
        checkVersion();
	}
	public void initView(){
		myMusic = (Button)findViewById(R.id.myMusic);
		store = (Button)findViewById(R.id.store);
		serch = (Button)findViewById(R.id.serch_button);
		myMusic.setOnClickListener(this);
		store.setOnClickListener(this);
		serch.setOnClickListener(this);
		 inflater= LayoutInflater.from(this);
		 builder= new Dialog(MainActivity1.this);
		 builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
		int h ;
		View softDetail = inflater.inflate(R.layout.soft_detail, null);
		softInstallButton =(Button)softDetail.findViewById(R.id.install);
		softInstallButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity1.this, "开始下载", 1).show();
				System.out.println("我已经开始下载");
			}
		});
		myMusic.setText(getResources().getString(R.string.my_music));
		myMusic.setSelected(true);
		myMusic.setTextColor(Color.YELLOW);
		store.setText(getResources().getString(R.string.music_store));
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.myMusic:
			myMusic.setSelected(true);
			store.setTextColor(Color.WHITE);
			myMusic.setTextColor(Color.YELLOW);
			store.setSelected(false);
			isWhatRight =Constant.MYMUSIC;
			if(isWhatLeft ==Constant.MUSICAPP){
				setAppStoreList(Constant.MYMUSIC_APP);
			}else if(isWhatLeft==Constant.MUSICCHAPTER){
			    setMusicChapterList(Constant.MYMUSIC_CHAPTER);
			}else if(isWhatLeft==Constant.MUSICMV){
				setMusicMvList(Constant.MYMUSIC_MV);
			}
			break;
		case R.id.store:
			myMusic.setSelected(true);
			store.setTextColor(Color.YELLOW);
			myMusic.setTextColor(Color.WHITE);
			store.setSelected(false);
			isWhatRight =Constant.MUSICSTORE;
			if(isWhatLeft ==Constant.MUSICAPP){
				setAppStoreList(Constant.MUSICSTORE_APP);
			}else if(isWhatLeft==Constant.MUSICCHAPTER){
				 setMusicChapterList(Constant.MUSICSTORE_CHAPTER);
			}else if(isWhatLeft==Constant.MUSICMV){
				setMusicMvList(Constant.MUSICSTORE_MV);
			}
			myMusic.setSelected(false);
			store.setSelected(true);
			break;
		case R.id.serch_button:
			Toast.makeText(this, "暂无功能", Toast.LENGTH_SHORT).show();
			break;
			default :
				break;
		}
	}
	// 左侧栏碎片
	public static class LeftFragment extends Fragment implements
			OnClickListener {
		OnLeftSelectedListener oLeftSelectedListener;
		View view;
		FragmentTransaction fragmentTransaction;
		// ！！！可恢复状态时用
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}
		public interface OnLeftSelectedListener {
			public void onLeftSelected(int left_type);
		}
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			try {
				oLeftSelectedListener = (OnLeftSelectedListener) activity;
			} catch (Exception e) {
				// 抛一个自己定义 的异常
				throw new ClassCastException(activity.toString()
						+ "must implement OnLeftSelectedListener");
			}
		}
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			// 用getActivity找到对应控件
			classify = (Button) getActivity().findViewById(R.id.flfg);
			the_news = (Button) getActivity().findViewById(R.id.al);
			recommend = (Button) getActivity().findViewById(R.id.flwsys);
			movie = (Button) getActivity().findViewById(R.id.movie);
			teleplay = (Button) getActivity().findViewById(R.id.teleplay);
			anime = (Button) getActivity().findViewById(R.id.anime);
			record = (Button) getActivity().findViewById(R.id.record);
			soft = (Button) getActivity().findViewById(R.id.soft);
			classify.setOnClickListener(this);
			the_news.setOnClickListener(this);
			recommend.setOnClickListener(this);
			movie.setOnClickListener(this);
			teleplay.setOnClickListener(this);
			anime.setOnClickListener(this);
//			music.setOnClickListener(this);
			record.setOnClickListener(this);
			soft.setOnClickListener(this);
			classify.setSelected(true);
		}
		// 将fragment加入activity
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			FragmentManager fragmentManager = getFragmentManager();
			if (fragmentTransaction == null) {
				fragmentTransaction = fragmentManager.beginTransaction();
			}
			if (rFragment == null && detailF == null) {
				rFragment = new RightFragment();
				fragmentTransaction.add(R.id.right, rFragment);
			} else {
				fragmentManager.popBackStack();
				fragmentTransaction.replace(R.id.right, rFragment);
			}
			fragmentTransaction.commit();
			if (container == null) {
				// return null;
			}
			leftlayout = new RelativeLayout(getActivity());
			view = inflater.inflate(R.layout.fragment_left_title1920, container,
					false);
			leftlayout.addView(view);
			return leftlayout;
		}
		// !!!!保存状态
		@Override
		public void onPause() {
			super.onPause();
		}

		@Override
		public void onClick(View v) {
			isSecondRFlag = false;
			if (v == classify) {
				classify.setSelected(true);
				the_news.setSelected(false);
				recommend.setSelected(false);
				movie.setSelected(false);
				teleplay.setSelected(false);
				anime.setSelected(false);
//				music.setSelected(false);
				record.setSelected(false);
				soft.setSelected(false);
				left_type = Constant.FLFG;
			} else if (v == the_news) {
				classify.setSelected(false);
				the_news.setSelected(true);
				recommend.setSelected(false);
				movie.setSelected(false);
				teleplay.setSelected(false);
				anime.setSelected(false);
//				music.setSelected(false);
				record.setSelected(false);
				soft.setSelected(false);
				left_type = Constant.AL;
			} else if (v == recommend) {
				classify.setSelected(false);
				the_news.setSelected(false);
				recommend.setSelected(true);
				movie.setSelected(false);  
				teleplay.setSelected(false);
				anime.setSelected(false);
//				music.setSelected(false);
				record.setSelected(false);
				soft.setSelected(false);
				left_type = Constant.FLWSYS;
			} 
			oLeftSelectedListener.onLeftSelected(left_type);
		}
		
	}
	/**
	 * 右侧栏碎片
	 * 
	 * @author sl
	 * 
	 */
	public static class RightFragment extends Fragment implements
			OnItemClickListener,OnHttpResponseListener {
		private CommUtil commUtil;
		ArrayList<String> a = new ArrayList<String>();
		OnRightSelectedListener oRightSelectedListener;
		private MediaPlayer mMediaPlayer;
		public interface OnRightSelectedListener {
			public void oRightSelected(int left_type);
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			http = new HttpRequest();
			http.setHttpResponseListener(this);
			RelativeLayout layout = new RelativeLayout(getActivity());
			itemView= inflater.inflate(R.layout.design_sketch_hor, container,false);
			initHorizontalView();
			layout.addView(itemView);
			 setDefalutView();
			return layout;
		}
		public void update(){
			
		}
	
  
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int idx,
				long arg3) {
			isSecondRFlag = true; 
		}
		public void updateRightFragmentBaseLeft(int left_type) {
			myMusic.setSelected(true);
			store.setSelected(false);
			HashMap<String, String> hashMap ;
			if(left_type == Constant.FLFG){
				isWhatLeft =Constant.MUSICAPP;
				if(isWhatRight==Constant.MYMUSIC){
					setAppStoreList(Constant.MYMUSIC_APP);
				}
				else if(isWhatRight==Constant.MUSICSTORE){
					setAppStoreList(Constant.MUSICSTORE_APP);
				}
			}
				else if(left_type == Constant.AL){
					isWhatLeft =Constant.MUSICCHAPTER;
					if(isWhatRight==Constant.MYMUSIC){
						
					 setMusicChapterList(Constant.MYMUSIC_CHAPTER);
					}
					else if(isWhatRight==Constant.MUSICSTORE){
						setMusicChapterList(Constant.MUSICSTORE_CHAPTER);
					}
			}else if(left_type == Constant.FLWSYS){
				isWhatLeft =Constant.MUSICMV;
               if(isWhatRight==Constant.MYMUSIC){
					setMusicMvList(Constant.MYMUSIC_MV);
				}
				else if(isWhatRight==Constant.MUSICSTORE){
					setMusicMvList(Constant.MUSICSTORE_MV);
				}
			}
		}
		@Override
		public void response(int responseCode, int what, String value, Object object) {
		}
		
	}
	// 右边第二级碎片
	public static class DetailFragment extends Fragment implements
			OnClickListener,OnHttpResponseListener,OnBitmapHttpResponseListener {
		private EditText userName,passWord;
		private PopupWindow pop;
		private Button install, download,login,cancel,pay,listentest;
		private ListView order_list;
		private RadioGroup radioGroup;
		private RadioButton price1,price2,price3;
		private TextView sfotName,softInfo,softVersion;
		private ImageView logo;
		private ProgressBar myProgressBar;//显示进度条的ProgressBar
		TextView resultView;
		private MediaPlayer mMediaPlayer;
		Thread thread;
		
		private  boolean isFinish = false;
		//子线程发送消息给UI线程来更新ProgressBar的进度
		private Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (!Thread.currentThread().isInterrupted()) {
					switch (msg.what) {
					case 1:
						int size = msg.getData().getInt("size");
						long total = msg.getData().getLong("total");
						int current = (int) (((float)size/ (float)total) * 100);
						myProgressBar.setMax(100);
						myProgressBar.setProgress(current);// 设置当前刻度
						resultView.setText(current + "%");
						if (myProgressBar.getMax() == myProgressBar.getProgress() && !isFinish) {
							current=0;
							isFinish = true;
						}
						break;
					case -1:
						String error = msg.getData().getString("error");
						Toast.makeText(getActivity(), error+"", 1).show();
						myProgressBar.setVisibility(View.GONE);
						resultView.setVisibility(View.GONE);
						break;
						
					case 2:
						if(getActivity() == null || getActivity().equals(""))return;
						Toast.makeText(getActivity(), R.string.success,
								Toast.LENGTH_LONG).show();
						if(Constant.FILE_NAME != null && Constant.FILE_NAME.toString().substring(Constant.FILE_NAME.toString().length()-3,
								Constant.FILE_NAME.toString().length()).equals("apk")){
							openFile(Constant.FILE_NAME);
						}
						myProgressBar.setVisibility(View.GONE);
						resultView.setVisibility(View.GONE);  
						break;
					}
				}
				super.handleMessage(msg);
			}
		};
		
		private void openFile(File file) {
			String cmd = "chmod 777 " +file;
			 try {
			 Runtime.getRuntime().exec(cmd);
			 } catch (Exception e) {
			e.printStackTrace();
			 }  
			 
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),
                            "application/vnd.android.package-archive");
            startActivity(intent);
    }



		// ！！！可恢复状态时用
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);

		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			// 用getActivity找到对应控件
		}

		// 将fragment加入activity
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			// 利用inflater返回根布局给activity
			View view = null;
			http = new HttpRequest();
			http.setHttpResponseListener(this);
			http.setbitmapHttpResponseListener(this);
			RelativeLayout layout = new RelativeLayout(getActivity());
			view = inflater.inflate(R.layout.resource_detail, container,
					false);
			radioGroup = (RadioGroup)view.findViewById(R.id.price);
			price1 = (RadioButton)view.findViewById(R.id.price1);
			price2 = (RadioButton)view.findViewById(R.id.price2);
			price3 = (RadioButton)view.findViewById(R.id.price3);
			radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListenerImpl());
			
			sfotName = (TextView) view.findViewById(R.id.soft_name); 
			softInfo = (TextView) view.findViewById(R.id.soft_info); 
			softVersion = (TextView) view.findViewById(R.id.soft_version); 
			myProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
			resultView = (TextView) view.findViewById(R.id.result);
			logo = (ImageView) view.findViewById(R.id.logo);
			install = (Button) view.findViewById(R.id.install);
			download = (Button) view.findViewById(R.id.download); 
			pay = (Button) view.findViewById(R.id.pay_order); 
			listentest = (Button) view.findViewById(R.id.listentest);  
			install.setOnClickListener(this); 
			download.setOnClickListener(this);
			pay.setOnClickListener(this);
			listentest.setOnClickListener(this);
			if(left_type == Constant.MOVIE ){
				if(right_grid_id != null){
					http.querySingleMovie(right_grid_id,Constant.SINGLE_RECORD, null);
					http.queryListDowanloadSoft(right_grid_id,HttpRequest.URL_QUERY_LIST_MOVIE,Constant.DOWNLOAD_LIST, null);
					listentest.setVisibility(View.VISIBLE);
					listentest.setText("试播");
				}
			}else if ( left_type == Constant.AL) {
				if(right_grid_id != null){
					http.querySingleNewspaper(right_grid_id,Constant.SINGLE_RECORD, null);
					http.queryListDowanloadSoft(right_grid_id,HttpRequest.URL_QUERY_LIST_NEWSPAPER,Constant.DOWNLOAD_LIST, null);
					download.setEnabled(false);
				}

			}else if (left_type == Constant.FLWSYS) {
				if(right_grid_id != null){
					http.querySinglePrint(right_grid_id,Constant.SINGLE_RECORD, null);
					http.queryListDowanloadSoft(right_grid_id,HttpRequest.URL_QUERY_LIST_PRINT,Constant.DOWNLOAD_LIST, null);
					download.setEnabled(false);
				}

			}else if (left_type == Constant.FLFG) {
				if(right_grid_id != null){
					http.querySingleBook(right_grid_id,Constant.SINGLE_RECORD, null);
					http.queryListDowanloadSoft(right_grid_id,HttpRequest.URL_QUERY_LIST_BOOK,Constant.DOWNLOAD_LIST, null);
					download.setEnabled(false);
				}

			}else if (left_type == Constant.ANIME) {
				if(right_grid_id != null){
					http.querySingleANIME(right_grid_id,Constant.SINGLE_RECORD, null);
					http.queryListDowanloadSoft(right_grid_id,HttpRequest.URL_QUERY_LIST_ANIME,Constant.DOWNLOAD_LIST, null);
					download.setEnabled(false);
				}

			}else if (left_type == Constant.TV) {
				if(right_grid_id != null){
					http.querySingleTv(right_grid_id,Constant.SINGLE_RECORD, null);
					http.queryListDowanloadSoft(right_grid_id,HttpRequest.URL_QUERY_LIST_TV,Constant.DOWNLOAD_LIST, null);
					download.setEnabled(false);
					listentest.setVisibility(View.VISIBLE);
					listentest.setText("试播");
				}

			}else if (left_type == Constant.MUSIC) {
				if(right_grid_id != null){
					http.querySingleMusic(right_grid_id,Constant.SINGLE_RECORD, null);
					http.queryListDowanloadSoft(right_grid_id,HttpRequest.URL_QUERY_LIST_MUSIC,Constant.DOWNLOAD_LIST, null);
					listentest.setVisibility(View.VISIBLE);
				}

			}else if (left_type == Constant.RECRD) {
				if(right_grid_id != null){
					http.querySingleRecord(right_grid_id,Constant.SINGLE_RECORD, null);
					http.queryListDowanloadSoft(right_grid_id,HttpRequest.URL_QUERY_LIST_RECORD,Constant.DOWNLOAD_LIST, null);
					download.setEnabled(false);
				}
			}else if(left_type == Constant.SOFT){
				install.setText("安装");
//				download.setEnabled(false);
				if(right_grid_id != null){
					http.querySingleSoft(right_grid_id,Constant.SINGLE_RECORD, null);
					http.queryListDowanloadSoft(right_grid_id,HttpRequest.URL_QUERY_LIST_SOFT,Constant.DOWNLOAD_LIST, null);
				}
			}
			layout.addView(view);
			return layout;
		}
		
		private class OnCheckedChangeListenerImpl implements OnCheckedChangeListener{

			  public void onCheckedChanged(RadioGroup group, int checkedId) {
			   if(price1.getId()==checkedId){
				   
			   }else if(price2.getId()==checkedId){
				   
			   }else if(price3.getId()==checkedId){
				   
			   }
			  }
		}

		// !!!!保存状态
		@Override
		public void onPause() {
			super.onPause();
		}
		
		//新起一个线程进行文件下载
		public void downLoadVideo(String downLoadPth) {
			Toast.makeText(getActivity(), R.string.start_download,
					Toast.LENGTH_LONG).show();
			mMediaPlayer = new MediaPlayer();
			// Sets the audio stream type for this MediaPlayer
			mMediaPlayer.setAudioStreamType(2);
			MediaPlayerListener listener = new MediaPlayerListener();
			listener.setAllListener(mMediaPlayer);
            
			HttpRequest.DOWNLOAD_ID = downLoadPth;
			ThreadForRunnable threadR = new ThreadForRunnable(getActivity(), myProgressBar,
					handler);
//			new Thread(threadR).start();
			thread = new Thread(threadR);  
			thread.start();
		}



		@Override
		public void response(int responseCode, int what, Bitmap bm) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void response(int responseCode, int what, String value,
				Object object) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}

		
//		
//        public void login(){
//        	LayoutInflater inflater = LayoutInflater.from(getActivity());  
//    		View view = inflater.inflate(R.layout.login, null);
//    		login = (Button) view.findViewById(R.id.login);
//    		cancel = (Button)view.findViewById(R.id.cancel);
//    		userName = (EditText) view.findViewById(R.id.login_username); 
//    		passWord = (EditText) view.findViewById(R.id.login_pas);
//    		login.setOnClickListener(this);  
//    		cancel.setOnClickListener(this);
//    		pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
//    				LayoutParams.WRAP_CONTENT);
//    		pop.setBackgroundDrawable(new BitmapDrawable());
//    		pop.setFocusable(true);
//    		mainView = inflater.inflate(R.layout.resource_detail, null);
//    		pop.showAtLocation(mainView, Gravity.CENTER, 0, 0);
//        }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}  
	// 实现activity基于left传值更新rightFragment
	@Override
	public void onLeftSelected(int left_type) {
		if (rFragment == null) {
			rFragment = (RightFragment)
			// getSupportFragmentManager().findFragmentById(R.id.right);
			getSupportFragmentManager().findFragmentByTag("rightF");
		}
		FragmentManager fragmentManager = rFragment.getFragmentManager();
		fragmentManager.popBackStack();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.right, rFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
		if (rFragment != null) {
			rFragment.updateRightFragmentBaseLeft(left_type);
		}
	}

	@Override
	public void oRightSelected(int left_type) {
	}
	 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(isfirst){
		if(keyCode ==KeyEvent.KEYCODE_DPAD_LEFT){
			myMusic.setFocusable(true);
			myMusic.requestFocus();
			myMusic.setSelected(true);
		}
		if(keyCode ==KeyEvent.KEYCODE_DPAD_RIGHT){
			myMusic.setFocusable(true);
			myMusic.requestFocus();
			myMusic.setSelected(true);
			
		}
		if(keyCode ==KeyEvent.KEYCODE_DPAD_UP){
			myMusic.setFocusable(true);
			myMusic.requestFocus();
			myMusic.setSelected(true);
		}
		if(keyCode ==KeyEvent.KEYCODE_DPAD_DOWN){
			myMusic.setFocusable(true);
			myMusic.requestFocus();
			myMusic.setSelected(true);
		}
		isfirst =false;
		}
		
		if(!recommend.isFocused()&!classify.isFocused()&!the_news.isFocused()){
			isFragment =false;
		}
		if(recommend.isFocused()||classify.isFocused()||the_news.isFocused()){
			isFragment =true;
		}
			if(classify.isFocused()){
				editor.clear();
				editor.putString("from", "one");
				editor.commit();
			}
			if(the_news.isFocused()){
				editor.clear();
				editor.putString("from", "two");
				editor.commit();
			}
			if(recommend.isFocused()){
				SharedPreferences.Editor edit = sp.edit ();
				editor.putString("from", "three");
				editor.commit();
			}
			if(keyCode==KeyEvent.KEYCODE_DPAD_LEFT){
				 if(itemView.findViewById(R.id.item_hor_01).isFocused()||itemView.findViewById(R.id.item_hor_06).isFocused()||itemView.findViewById(R.id.item_hor_11).isFocused()){
					 String focuse = sp.getString("from","none");
						if(focuse.equals("one")){
							classify.setFocusable(true);
							classify.requestFocus();
						}
						if(focuse.equals("three")){
							recommend.setFocusable(true);
							recommend.requestFocus();
						}
						if(focuse.equals("two")){
							the_news.setFocusable(true);
							the_news.requestFocus();
						}
				 }
			}
		return super.onKeyDown(keyCode, event);
	}
	private void initInfoView(){
//		filmNameText = (TextView) find(R.id.field_filmname);
//		pageIndex = (TextView) find(R.id.field_page_index);
//		preBtn = (Button) find(R.id.page_pre);
//		preBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				prePage();
//			}
//		});
//		nextBtn = (Button) find(R.id.page_next);
//		nextBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				nextPage();
//			}
//		});
	}
	private static void initHorizontalView(){
	//	initInfoView();
		itemView.findViewById(R.id.item_hor_05).setNextFocusRightId(R.id.item_hor_06);
		itemView.findViewById(R.id.item_hor_10).setNextFocusRightId(R.id.item_hor_11);
		itemView.findViewById(R.id.item_hor_11).setNextFocusDownId(R.id.item_hor_11);
		itemView.findViewById(R.id.item_hor_12).setNextFocusDownId(R.id.item_hor_12);
		itemView.findViewById(R.id.item_hor_13).setNextFocusDownId(R.id.item_hor_13);
		itemView.findViewById(R.id.item_hor_14).setNextFocusDownId(R.id.item_hor_14);
		itemView.findViewById(R.id.item_hor_15).setNextFocusDownId(R.id.item_hor_15);
		
	}
	
//	public void initItems(int[] itemIds) {
////		AQuery aq = new AQuery(contentView);
//		for(int i=0;i<itemIds.length;i++){
//			aq.find(itemIds[i]).clicked(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					MovieBrief movie = (MovieBrief) v.getTag();
//					Intent i = new Intent(context,ItemDetailPage.class);
//					i.putExtra("id",movie.getId());
//					context.startActivity(i);
//				}
//			}).focusChanged(new OnFocusChangeListener() {
//				
//				@Override
//				public void onFocusChange(View v, boolean hasFocus) {
//					if(hasFocus){
//						focusViewId = v.getId();
//						MovieBrief movie = (MovieBrief) v.getTag();
//						updateInfomation(movie);
//					}
//				}
//			}).invisible();
//		}
//		aq.find(itemIds[0]).keyed(new OnKeyListener() {
//			
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				if( event.getAction() == KeyEvent.ACTION_DOWN){
//					if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
//						if(!loading)
//							prePage();
//						return true;
//					}
//				}
//				return false;
//			}
//		});
//		aq.find(itemIds[itemIds.length-1]).keyed(new OnKeyListener() {
//			
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				if( event.getAction() == KeyEvent.ACTION_DOWN){
//					
//					if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
//						if(!loading)
//							nextPage();
//						return true;
//					}
//				}
//				return false;
//			}
//		});
//	}
	   public static void initDialog(String s) {
		   
		   View view = null;
		   if("three".equals(s)){
			view= inflater.inflate(R.layout.music_detail1, null);
		   }
		   if("one".equals(s)){
			   view= inflater.inflate(R.layout.soft_detail, null);
		   }
		   if("three".equals(s)){
			   view= inflater.inflate(R.layout.music_detail1, null);
		   }
			builder.setContentView(view);
			Window dialogWindow = builder.getWindow();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			dialogWindow.setGravity(Gravity.CENTER);
			lp.width = 800; 
			lp.height = 640; 
			dialogWindow.setAttributes(lp);
		}
	   
		/**
		 * setAllMusic 音乐
		 * @param list
		 */
		public static void setMusicChapterInfo(ArrayList<Music> list) {
			for (int i = 0; i < horItems.length; i++) {
				itemView.findViewById(horItems[14-i]).setVisibility(View.VISIBLE);
			}
			if(list.size()<15){
				int j = 15-list.size();
				for (int i = 0; i <j; i++) {
					itemView.findViewById(horItems[14-i]).setVisibility(View.INVISIBLE);
				}
			}
			for (int i = 0; i < list.size(); i++) {
				aQuery.find(horItems[i]).find(R.id.ItemTitle).text(list.get(i).getName());
				String url = list.get(i).getImage_path();
				String  URL_QUERY_SINGLE_IMAGE =HttpRequest.WEB_ROOT + "download.action?token=myadmin&inputPath=";
				String uslPath =URL_QUERY_SINGLE_IMAGE+url;
				aQuery.find(horItems[i]).find(R.id.ItemIcon).image(uslPath);
			}
		}
		 //Set softrecommend 软件推荐
	    private static void setSoftrecommend(ArrayList<SoftwareBean> list,final View view){

	    	ImageDownloader Downloader = new ImageDownloader(aQuery.getContext());
	    	for (int i = 0; i <5; i++) {
	    		view.findViewById(horItems[i]).setVisibility(View.VISIBLE);
			}
	    	int j  =0 ;
	    	if(list.size()<5){
	    		for (int s = 0; s < 5-list.size(); s++) {
	    			view.findViewById(horItems[14-s]).setVisibility(View.GONE);
				}
   	}
	    	for (int i = 0; i < list.size(); i++) {
	    		final String image_path_boots=list.get(i).getImage_path();
	    		final int h =horItems[i];
				 SoftwareBean sb = list.get(i);
				 final String name =list.get(i).getName();
				 final String path_root =list.get(i).getDownload_path();
				 String image_path = sb.getImage_path();
				 final String title = sb.getName();
				 String turePath = HttpRequest.URL_QUERY_SINGLE_IMAGE+image_path;
				 Downloader.download(turePath, ((ImageView)view.findViewById(horItems[i]).findViewById(R.id.ItemIcon)));
				 ((TextView)view.findViewById(horItems[i]).findViewById(R.id.ItemTitle)).setText(title);
				 view.findViewById(horItems[i]).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						appDownPath =HttpRequest.URL_QUERY_DOWNLOAD_URL+path_root;
						 ((TextView)viewForsoftDetail.findViewById(R.id.appname)).setText(name);
						String path =HttpRequest.URL_QUERY_SINGLE_IMAGE+image_path_boots;
						ImageView  imageView =(ImageView)viewForsoftDetail.findViewById(R.id.appimage);
						ImageDownloader downloader = new ImageDownloader(aQuery.getContext());
						downloader.download(path, imageView);
					}
				});
			}
	    }
	    //音乐推荐
	    private static void setMusicrecommend(ArrayList<Music> list,final View view){
	    	final ArrayList<String>  myPathlist = new ArrayList<String>();
	    	ImageDownloader Downloader = new ImageDownloader(aQuery.getContext());
	    	for (int i = 0; i <5; i++) {
	    		view.findViewById(horItems[i]).setVisibility(View.VISIBLE);
	    	}
	    	int j  =0 ;
	    	if(list.size()<5){
	    		for (int s = 0; s < 5-list.size(); s++) {
	    			view.findViewById(horItems[4-s]).setVisibility(View.INVISIBLE);
	    		}
	    	}
	    	for (int i = 0; i < list.size(); i++) {
	    		final Music sb = list.get(i);
	    		String image_path = sb.getImage_path();
	    		final String title = sb.getName();
	    		final String turePath = HttpRequest.URL_QUERY_SINGLE_IMAGE+image_path;
	    		Downloader.download(turePath, ((ImageView)view.findViewById(horItems[i]).findViewById(R.id.ItemIcon)));
	    		((TextView)view.findViewById(horItems[i]).findViewById(R.id.ItemTitle)).setText(title);
	    		 myPathlist.add(image_path);
	    		 view.findViewById(horItems[i]).setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							  
							 ((TextView)view.findViewById(R.id.albumname)).setText(title);
							ImageView  imageView =(ImageView)view.findViewById(R.id.albumimage);
							ImageDownloader downloader = new ImageDownloader(aQuery.getContext());
							downloader.download(turePath, imageView);
							setRecommedMusicInfo(sb.getId(),view,sb);
						}
					});
	    	}
	    	
	    }

	    /**
	     * 试播,订购，下载
		 * set music play
		 * @param path
		 */
//		public static void setMusicCross(final String path,final String sid){
//			Log.e(TAG, "下载的路径是"+path);
//			String decryptURL = CryptUtil.decryptURL(path);
//			final String subpath = decryptURL.substring(decryptURL.lastIndexOf(".")+1, decryptURL.length()-1);
//			final View musiccrossView = inflater.inflate(R.layout.musiccross, null);
//			final Dialog dl = new Dialog(aQuery.getContext());
//			dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dl.setContentView(musiccrossView);
//			Window dialogWindow = dl.getWindow();
//			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//			dialogWindow.setGravity(Gravity.CENTER);
//			lp.width = 400; 
//			lp.height =200; 
//			dialogWindow.setAttributes(lp);
//			dl.show();
//			//试播
//			((Button)musiccrossView.findViewById(R.id.btn_shibo)).setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					if("mp3".equals(subpath)){
//						setMusicPilot(path);
//					}else if("mp4".equals(subpath)){
//						setMVPilot(path);
//					}
////					setMusicPilot(path);
//				}
//			});
//			//下载
//			((Button)musiccrossView.findViewById(R.id.btn_musicdown)).setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					setMusicDown(sid);
//				}
//			});
//			//订阅单曲
//			((Button)musiccrossView.findViewById(R.id.btn_musciorder)).setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					//setMusicPilot(path);
//				}
//			});
//		 
//		}
		/**
		 * setSoftDetial param
		 * 软件详细界面
		 */
		public static void setSoftDetail(int id,final ArrayList<SoftwareBean> list) {
			final Handler hdHandler = new Handler();
			 int j =0;
			 
			for (int i = 0; i < horItems.length; i++) {
				if(id==horItems[i]){
					j=i;
				}
		}
			final String appId =list.get(j).getId();
			String appPath =HttpRequest.URL_QUERY_SINGLE_SOFT+appId;
			final ProgressDialog Dialog = ProgressDialog.show(aQuery.getContext(), "缓冲中。。", "正在缓冲请稍后。。");
			builder.setContentView(viewForsoftDetail);
			Window dialogWindow = builder.getWindow();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			dialogWindow.setGravity(Gravity.CENTER);
			lp.width = 800; 
			lp.height = 640; 
			dialogWindow.setAttributes(lp);
			Dialog.show();
			String web_url=HttpRequest.URL_QUERY_LIST_SOFT+appId;
			 aQuery.ajax(web_url, String.class, new AjaxCallback<String>() {//这里的函数是一个内嵌函数如果是函数体比较复杂的话这种方法就不太合适了
	                @Override
	                public void callback(String url, String json, AjaxStatus status) {
	                        //得通过对一个url访问返回的数据存放在JSONObject json中 可以通过json.getContext()得到
	                        if(json != null){
	                        	Dialog.dismiss();
	                        	try {
									JSONArray ja = new JSONArray(json);
									for (int i = 0; i < ja.length(); i++) {
										JSONObject jb = ja.getJSONObject(i);
									final String appDownPaths= jb.getString("filepath");
				                        String version = jb.getString("title");
				                        appDownPath =HttpRequest.URL_QUERY_DOWNLOAD_URL+appDownPaths+"&"+"多米";
				                        viewForsoftDetail.findViewById(R.id.install).setOnClickListener(new OnClickListener() {
											@Override
											public void onClick(View v) {
												// TODO Auto-generated method stub
												System.out.println("我已经被监听了");
												String web_url=HttpRequest.URL_QUERY_LIST_SOFT+appId;
												new AsyncTask<Void, Void, Void>(){
													@Override
													protected Void doInBackground(
															Void... params) {
														UpdateVersion updateVersion  = UpdateVersion.instance(aQuery.getContext(), hdHandler,true);
													updateVersion.setUpdateUrl(appDownPath);
	                                    				updateVersion.run();
														return null;
													}
												}.execute();
											}
										});
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                                //successful ajax call, show status code and json content
	                        }else{
	                        	Dialog.dismiss();
	                      Toast.makeText(aQuery.getContext(), "Error:" +status.getCode(),Toast.LENGTH_LONG).show();
	                          }
	                    }
	            });
			 aQuery.ajax(appPath, String.class, new AjaxCallback<String>() {
	                @Override
	                public void callback(String url, String json, AjaxStatus status) {
	                        //得通过对一个url访问返回的数据存放在JSONObject json中 可以通过json.getContext()得到
	                        if(json != null){
	                        	System.out.println("下载的数据"+"===="+json);
	                         final String image_path_boot;
							try {
								final ArrayList<String> pathList = new ArrayList<String>();
	                        	ArrayList<String> nameList = new ArrayList<String>();
								JSONObject  jb = new JSONObject(json);
								 image_path_boot = jb.getString("PIC");
								 String name =jb.getString("PNAME");
								 builder.show();
								 ((TextView)viewForsoftDetail.findViewById(R.id.appname)).setText(name);	
								 setSoftrecommend(list, viewForsoftDetail);
									String path =HttpRequest.URL_QUERY_SINGLE_IMAGE+image_path_boot;
									ImageView  imageView =(ImageView)viewForsoftDetail.findViewById(R.id.appimage);
									ImageDownloader downloader = new ImageDownloader(aQuery.getContext());
									downloader.download(path, imageView);
							} catch (JSONException e) {
								e.printStackTrace();
							}
	                        	Dialog.dismiss();
	                        }else{
	                        	Dialog.dismiss();
	                      Toast.makeText(aQuery.getContext(), "Error:" +status.getCode(),Toast.LENGTH_LONG).show();
	                          }
	                    }
	            });
		}
		/**
		 * setMusicDetial param
		 * 音乐详细界面
		 */
		public static void setMusicDetial(int id,final ArrayList<Music> list,final View view,final String orderId) {
 			int j =0;
			for (int i = 0; i < horItems.length; i++) {
				if(id==horItems[i]){
					j=i;
				}
			}
//			final ListView listview =(ListView) view.findViewById(R.id.music_detail_list);
			final Button btn_orderall=(Button)(viewFormusicdetail.findViewById(R.id.btn_order_allmusic));
			builder.setContentView(viewFormusicdetail);
			Window dialogWindow = builder.getWindow();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			dialogWindow.setGravity(Gravity.CENTER);
			lp.width = 800; 
			lp.height = 640; 
			dialogWindow.setAttributes(lp);
			for (int i = 0; i < musiclistItem.length; i++) {
				view.findViewById(musiclistItem[i]).setVisibility(View.GONE);
			}
			final ProgressDialog Dialog = ProgressDialog.show(aQuery.getContext(), "缓冲中。。", "正在缓冲请稍后。。");
				final String musicId =list.get(j).getId();
				String web_url=HttpRequest.URL_QUERY_LIST_MUSIC+musicId;
				 aQuery.ajax(web_url, String.class, new AjaxCallback<String>() {//这里的函数是一个内嵌函数如果是函数体比较复杂的话这种方法就不太合适了
		                @Override
		                public void callback(String url, String json, AjaxStatus status) {
		                        if(json != null){
		                        	 ArrayList<Music>   musicDetialList = new ArrayList<Music>();
		                        	System.out.println("下载的数据"+"===="+json);
		                        	Dialog.dismiss();
		                        	try {
										JSONArray ja = new JSONArray(json);
										for (int i = 0; i < ja.length(); i++) {
											Music music  = new Music();
											JSONObject jb = ja.getJSONObject(i);
											String sid  =jb.getString("sid");
											String musicpath= jb.getString("filepath");
											String title =jb.getString("title");
											music.setDownload_path(musicpath);
											music.setId(sid);
											music.setName(title);
											musicDetialList.add(music);
		                        		}
										
//										MusicAdapter myMusicAdapter = new MusicAdapter(aQuery.getContext(), musicDetialList,isWhatRight);
//										listview.setAdapter(myMusicAdapter);
										
										for (int i = 0; i < musicDetialList.size(); i++) {
											final String path =	musicDetialList.get(i).getDownload_path();
										   view.findViewById(musiclistItem[i]).setVisibility(View.VISIBLE);
											TextView tv =(TextView)(view.findViewById(musiclistItem[i]).findViewById(R.id.name));
											final String name =musicDetialList.get(i).getName();
											tv.setText(name);
											view.findViewById(musiclistItem[i]).setOnClickListener(new OnClickListener() {
												String appDownPathtrue  =HttpRequest.URL_QUERY_DOWNLOAD_URL+path;
												@Override
												public void onClick(View v) {
													setMusicPilot(appDownPathtrue, name);
												}
											});
											}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		                                //successful ajax call, show status code and json content
		                        }else{
		                        	Dialog.dismiss();
		                      Toast.makeText(aQuery.getContext(), "Error:" +status.getCode(),Toast.LENGTH_LONG).show();
		                          }
		                    }
		            });
				 String musicssPath = HttpRequest.URL_QUERY_SINGLE_MUSIC+musicId;
				 aQuery.ajax(musicssPath, String.class, new AjaxCallback<String>() {
		                @Override
		                public void callback(String url, String json, AjaxStatus status) {
		                        if(json != null){
		                        	System.out.println("下载的数据"+"======="+json);
		                         final String image_path_boot;
								try {
									 final ArrayList<String> pathList = new ArrayList<String>();
		                        	ArrayList<String> nameList = new ArrayList<String>();
		                        	final ArrayList<PostMent> postMentList = new ArrayList<PostMent>();
									JSONObject  jb = new JSONObject(json);
									 image_path_boot = jb.getString("PIC");
									 String name =jb.getString("PNAME");
									 String note =jb.getString("PNOTE");
									 String artist="";
									 String company="";
									 String language="";
									 String pubdate ="";
									 if(!jb.isNull("COMPANY")){
										 artist =jb.getString("COMPANY"); 
											}
											if(!jb.isNull("COMPANY")){
											 company=jb.getString("COMPANY");
											}
											if(!jb.isNull("LANGUAGE")){
											 language = jb.getString("LANGUAGE");
											}
											if(!jb.isNull("PUBDATE")){
											 pubdate =jb.getString("PUBDATE");
											}
									 JSONArray postOb = jb.getJSONArray("potype");
									 for (int i = 0; i < postOb.length(); i++) {
										 
										 PostMent pm = new PostMent();
										 pm.setType(postOb.getJSONObject(i).getString("TYPE"));
										 pm.setId(postOb.getJSONObject(i).getString("PUBID"));
										 pm.setPrice(postOb.getJSONObject(i).getString("PRICE"));
										 postMentList.add(pm);
									}
										btn_orderall.setOnClickListener(new OnClickListener() {
											
											@Override
											public void onClick(View v) {
//												 AlertDialog.Builder alert = new AlertDialog.Builder(
//													       aQuery.getContext());
//													      alert.setTitle("订购确认")
//													        .setMessage("是否全部订阅")
//													        .setPositiveButton("订阅",
//													          new DialogInterface.OnClickListener() {
//													           public void onClick(
//													             DialogInterface dialog,
//													             int which) {
//													           }
//													          })
//													        .setNegativeButton("取消",
//													          new DialogInterface.OnClickListener() {
//													           public void onClick(
//													             DialogInterface dialog,
//													             int which) {
//													            dialog.dismiss();
//													           }
//													          });
//													      //m_Dialog.dismiss();
//													      alert.create().show();
												setOrder(postMentList,orderId);
												
											}
										});
									builder.show();
									 ((TextView)viewFormusicdetail.findViewById(R.id.albumname)).setText(name);							
									 ((TextView)viewFormusicdetail.findViewById(R.id.albuminfo)).setText(note);		
									 ((TextView)viewFormusicdetail.findViewById(R.id.artist)).setText(aQuery.getContext().getResources().getString(R.string.artist)+" : "+artist);		
									 ((TextView)viewFormusicdetail.findViewById(R.id.Issuedate)).setText(aQuery.getContext().getResources().getString(R.string.pubdate)+" ： "+pubdate);		
									 ((TextView)viewFormusicdetail.findViewById(R.id.language)).setText(aQuery.getContext().getResources().getString(R.string.language)+" ： "+language);		
									 ((TextView)viewFormusicdetail.findViewById(R.id.company)).setText(aQuery.getContext().getResources().getString(R.string.company)+" ： "+company);		
									 setMusicrecommend(list, viewFormusicdetail);
										String path =HttpRequest.URL_QUERY_SINGLE_IMAGE+image_path_boot;
										ImageView  imageView =(ImageView)viewFormusicdetail.findViewById(R.id.albumimage);
										ImageDownloader downloader = new ImageDownloader(aQuery.getContext());
										downloader.download(path, imageView);
								} catch (JSONException e) {
									e.printStackTrace();
								}
		                        	Dialog.dismiss();
		                        }else{
		                        	Dialog.dismiss();
		                      Toast.makeText(aQuery.getContext(), "Error:" +status.getCode(),Toast.LENGTH_LONG).show();
		                          }
		                    }
		            });
		}
		
		/**
		 * setAllSoftinfo
		 * @param softList
		 * 设定软件列表信息
		 */
		public static void setSoftInfo(ArrayList<SoftwareBean> list) {
			for (int i = 0; i < horItems.length; i++) {
				itemView.findViewById(horItems[14-i]).setVisibility(View.VISIBLE);
			}
			if(list.size()<15){
				int j = 15-list.size();
				for (int i = 0; i <j; i++) {
					itemView.findViewById(horItems[14-i]).setVisibility(View.INVISIBLE);
				}
			}
			for (int i = 0; i < list.size(); i++) {
				aQuery.find(horItems[i]).find(R.id.ItemTitle).text(list.get(i).getName());
				String url = list.get(i).getImage_path();
				String  URL_QUERY_SINGLE_IMAGE = HttpRequest.WEB_ROOT + "download.action?token=myadmin&inputPath=";
				String uslPath =URL_QUERY_SINGLE_IMAGE+url;
				aQuery.find(horItems[i]).find(R.id.ItemIcon).image(uslPath);
			}
			
		}
		//音乐app
		public static void  setAppStoreList(String path){
			viewForsoftDetail= inflater.inflate(R.layout.soft_detail, null);
				builder.setContentView(viewForsoftDetail);
				Window dialogWindow = builder.getWindow();
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				dialogWindow.setGravity(Gravity.CENTER);
				lp.width = 800; 
				lp.height = 640; 
				dialogWindow.setAttributes(lp);
			final ProgressDialog Dialog = ProgressDialog.show(aQuery.getContext(), "loading。。", "please wait a moment。。");
			aQuery.ajax(path, String.class, new AjaxCallback<String>() {//这里的函数是一个内嵌函数如果是函数体比较复杂的话这种方法就不太合适了
                @Override
                public void callback(String url, String json, AjaxStatus status) {
                        if(json != null){
                        	musicAppList =  new ArrayList<SoftwareBean>();
                        	musicAppList = JsonUtil.getProductList(json);
                        	setSoftInfo(musicAppList);
                        	Dialog.dismiss();
                         //successful ajax call, show status code and json content
                        }else{
                        	Dialog.dismiss();
                        	if(status.getCode()==-101){
                      Toast.makeText(aQuery.getContext(), "Error:" +status.getCode()+aQuery.getContext().getResources().getString(R.string.checknetwork),Toast.LENGTH_LONG).show();
                        	}else{
                                Toast.makeText(aQuery.getContext(), "Error:" +status.getCode()+aQuery.getContext().getResources().getString(R.string.unknownError),Toast.LENGTH_LONG).show();
                        	}
                          }
                    }
            });
			  for (int i = 0; i < horItems.length; i++) {
		        	itemView.findViewById(horItems[i]).setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
                            System.out.println("item被点击了");
							
							setSoftDetail(v.getId(),musicAppList);
							int id = v.getId();
						}
					});
			  }
		};
		
		//default UI
		public static void  setDefalutView(){
			initDialog("three");
			
			isWhatRight=Constant.MYMUSIC;
			viewFormusicdetail = inflater.inflate(R.layout.music_detail1, null);
			final ProgressDialog Dialog = ProgressDialog.show(aQuery.getContext(), "Loading。。", "please wait a moment。。");
			 String url = Constant.MYMUSIC_APP;
		        aQuery.ajax(url, String.class, new AjaxCallback<String>() {
		                @Override
		                public void callback(String url, String json, AjaxStatus status) {
		                        if(json != null){
		                        	musicList = new ArrayList<Music>();
		                        	musicList = JsonUtil.getMusicList(json);
		                        	setMusicChapterInfo(musicList);
		                        	Dialog.dismiss();
		                        }else{
		                        	Dialog.dismiss();
		                      Toast.makeText(aQuery.getContext(), "Error:" +status.getCode(),Toast.LENGTH_LONG).show();
		                          }
		                        if(musicList!=null){
		                        	for (int i = 0; i < horItems.length; i++) {
		                        		final String musicId = musicList.get(i).getId();
		                        		itemView.findViewById(horItems[i]).setOnClickListener(new OnClickListener() {
		                        			@Override
		                        			public void onClick(View v) {
		                        				setMusicDetial(v.getId(), musicList,viewFormusicdetail,musicId);
		                        			}
		                        		});
		                        	}
		                        }
		                    }
		            });
		}
		//set pilot play  UI
		public static void  setMusicPilot(final String path,String name){
//			
			final Dialog dl = new Dialog(aQuery.getContext());
			final View view = inflater.inflate(R.layout.musicplay, null);
			dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dl.setContentView(view);
			Window dialogWindow = dl.getWindow();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			dialogWindow.setGravity(Gravity.CENTER);
			lp.width = 400; 
			lp.height =300; 
			dialogWindow.setAttributes(lp);
			dl.show();
			 final ImageButton iv =	(ImageButton) view.findViewById(R.id.btn_play);
			 ((TextView)view.findViewById(R.id.btn_playname)).setText(name);
				mp = new MediaPlayer();
				view.findViewById(R.id.btn_stop).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					  mp.stop();
					  dl.dismiss();
					}
				});
				 view.findViewById(R.id.btn_play).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(isplay){
							 mp.pause();
							 iv.setImageResource(R.drawable.desktop_playbt_b);
							 isplay=false;
						}
						else if(!isplay){
							mp.start();
							 iv.setImageResource(R.drawable.desktop_pausebt_b);
							isplay =true;
						}
					}
				});
				 dl.setOnKeyListener(new OnKeyListener() {
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
						// TODO Auto-generated method stub
						if(keyCode==KeyEvent.KEYCODE_BACK){
							if(isplay){
								if(mp!=null){
									mp.stop();
								}
							}
							  return false;
						}
						return false;
					}
				});
				 
				 if (musicTryTask != null && musicTryTask.getStatus() == AsyncTask.Status.RUNNING) {
					 musicTryTask.cancel(true);  //  如果Task还在运行，则先取消它
			        }else{
			        	musicTryTask = new musicTryplayAsyncTask(iv);
			        }
				 musicTryTask.execute(path);
				
		}
		
		//setmvplay
		public static void  setMVPilot(final String path){
			
			Intent i  = new Intent();
			i.putExtra("path", path);
			i.setClass(aQuery.getContext(), PlayActivity.class);
			aQuery.getContext().startActivity(i);
		
		}
		//setdown（music，mv）
		public static void setMusicDown(String sid){
			//downcheck
			String path =HttpRequest.URL_QUERY_DOWNLOAD_CHECK+"sid="+sid+"&type=music";
			final ProgressDialog Dialog = ProgressDialog.show(aQuery.getContext(), "Checking。。", "please wait a moment。。");
			final Handler handler = new Handler();
			Dialog.show();
			aQuery.ajax(path, String.class, new AjaxCallback<String>(){

				@Override
				public void callback(String url, String json,
						AjaxStatus status) {
					if(json!=null){
					System.out.println(json);
					Dialog.dismiss();
					try {
					 					JSONObject jb  = new JSONObject(json);
					if("true".equals(jb.get("success"))){
						String filepath = jb.getString("filepath");
						String filename =jb.getString("filename");
						final String path =HttpRequest.URL_QUERY_DOWNLOAD_URL+filepath+"&"+"duomi";
						new AsyncTask<Void, Void, Void>(){
							@Override
							protected Void doInBackground(
									Void... params) {
								UpdateVersion updateVersion  = UpdateVersion.instance(aQuery.getContext(), handler,false);
								updateVersion.setUpdateUrl(path);
								updateVersion.run();
								return null;
							}
						}.execute();
						
					}
					else{
						String errmessage  =jb.getString("errmessage");
						if(errmessage!=null){
							Toast.makeText(aQuery.getContext(), errmessage, 1).show();
							
						}else{
							Toast.makeText(aQuery.getContext(), aQuery.getContext().getResources().getString(R.string.unknownError) , 1).show();
						}
					}
					}
					catch (Exception e) {
						e.printStackTrace();
					}

				}
				
				else{
                	Dialog.dismiss();
                	if(status.getCode()==-101){
                   Toast.makeText(aQuery.getContext(), aQuery.getContext().getResources().getString(R.string.checknetwork) ,Toast.LENGTH_LONG).show();
                	}else{
                		 Toast.makeText(aQuery.getContext(), "Error:" +status.getCode(),Toast.LENGTH_LONG).show();
                	}
                  }
				}
			});
		}
		
		
		//setMusicChapterlist
		public static void  setMusicChapterList(String path){
			initDialog("three");
			viewFormusicdetail = inflater.inflate(R.layout.music_detail1, null);
			final ProgressDialog Dialog = ProgressDialog.show(aQuery.getContext(), "loading。。", "please wait。。");
			final ArrayList<Music> music_chapterList = new ArrayList<Music>();
		        aQuery.ajax(path, String.class, new AjaxCallback<String>() {
		                @Override
		                public void callback(String url, String json, AjaxStatus status) {
		                        if(json != null){
		                        	musicList = new ArrayList<Music>();
		                        	musicList = JsonUtil.getMusicList(json);
		                        	for (int i = 0; i < musicList.size(); i++) {
										if(musicList.get(i).getType().equals("1")){
											music_chapterList .add(musicList.get(i));
										}
									}
		                        	setMusicChapterInfo( music_chapterList);
		                        	Dialog.dismiss();
		                        }else{
		                        	Dialog.dismiss();
		                      Toast.makeText(aQuery.getContext(), "Error:" +status.getCode(),Toast.LENGTH_LONG).show();
		                          }
		                        if(music_chapterList!=null){
		                        	for (int i = 0; i < horItems.length; i++) {
		                        		final String orderId = music_chapterList.get(i).getId();
		                        		itemView.findViewById(horItems[i]).setOnClickListener(new OnClickListener() {
		                        			
		                        			@Override
		                        			public void onClick(View v) {
		                        				setMusicDetial(v.getId(), music_chapterList,viewFormusicdetail, orderId);
		                        			}
		                        		});
		                        	}
		                        }
		                    }
		            });
		}
		//setMV list
		public static void setMusicMvList(String path){
			initDialog("three");
			viewFormusicdetail = inflater.inflate(R.layout.music_detail1, null);
			final ProgressDialog Dialog = ProgressDialog.show(aQuery.getContext(), "loading。。", "please wait a moment。。");
			final ArrayList<Music> mvlist = new ArrayList<Music>();
		        aQuery.ajax(path, String.class, new AjaxCallback<String>() {
		                @Override
		                public void callback(String url, String json, AjaxStatus status) {
		                        if(json != null){
		                        	musicList = new ArrayList<Music>();
		                        	musicList = JsonUtil.getMusicList(json);
		                        	for (int i = 0; i < musicList.size(); i++) {
										if(musicList.get(i).getType().equals("0")){
											mvlist.add(musicList.get(i));
										}
		                        	}
		                        	setMusicChapterInfo( mvlist);
		                        	Dialog.dismiss();
		                        }else{
		                        	Dialog.dismiss();
		                      Toast.makeText(aQuery.getContext(), "Error:" +status.getCode(),Toast.LENGTH_LONG).show();
		                          }
		                        if(mvlist.size()!=0){
		                        	for (int i = 0; i < horItems.length; i++) {
		                        		final String orderid = mvlist.get(i).getId();
		                        		itemView.findViewById(horItems[i]).setOnClickListener(new OnClickListener() {
		                        			public void onClick(View v) {
		                        				setMusicDetial(v.getId(), mvlist,viewFormusicdetail,orderid);
		                        			}
		                        		});
		                        	}
		                        }
		                    }
		            });
		}

       //loading dialog for recommedmusic
		public static void setRecommedMusicInfo(String musicId,final View view,Music music){
			 
			for (int i = 0; i < musiclistItem.length; i++) {
				view.findViewById(musiclistItem[i]).setVisibility(View.GONE);
			}
			 final ProgressDialog Dialog = ProgressDialog.show(aQuery.getContext(), "Loading。。", "please wait moment。。");
			 Dialog.show();
			
			String web_url=HttpRequest.URL_QUERY_LIST_MUSIC+musicId;
			 aQuery.ajax(web_url, String.class, new AjaxCallback<String>() {
	                @Override
	                public void callback(String url, String json, AjaxStatus status) {
	                        if(json != null){
	                        	 final ArrayList<Music>   musicDetialList = new ArrayList<Music>();
		                        	System.out.println("下载的数据"+"===="+json);
		                        	Dialog.dismiss();
		                        	try {
										JSONArray ja = new JSONArray(json);
										for (int i = 0; i < ja.length(); i++) {
											Music music  = new Music();
											JSONObject jb = ja.getJSONObject(i);
											String sid  =jb.getString("sid");
											String musicpath= jb.getString("filepath");
											String title =jb.getString("title");
											music.setDownload_path(musicpath);
											music.setId(sid);
											music.setName(title);
											musicDetialList.add(music);
		                        		}
										for (int i = 0; i < musicDetialList.size(); i++) {
										final String path =	musicDetialList.get(i).getDownload_path();
									   view.findViewById(musiclistItem[i]).setVisibility(View.VISIBLE);
										TextView tv =(TextView)(view.findViewById(musiclistItem[i]).findViewById(R.id.name));
										final String name =musicDetialList.get(i).getName();
										tv.setText(name);
										view.findViewById(musiclistItem[i]).setOnClickListener(new OnClickListener() {
											String appDownPathtrue  =HttpRequest.URL_QUERY_DOWNLOAD_URL+path;
											@Override
											public void onClick(View v) {
												setMusicPilot(appDownPathtrue, name);
											}
										});
										}
										
//										ListView listview = (ListView) view.findViewById(R.id.music_detail_list);
//										MusicAdapter myMusicAdapter = new MusicAdapter(aQuery.getContext(), musicDetialList,isWhatRight);
//										listview.setAdapter(myMusicAdapter);
//									 for (int i = 0; i < musictitleList.size(); i++) {
//										 final String web_path =musicpathList.get(i);
//										 final String sid = musicsidList.get(i);
//										 ((Button)view.findViewById(musiclistItem[i])).setVisibility(View.VISIBLE);
//										 ((Button)view.findViewById(musiclistItem[i])).setText(musictitleList.get(i));
//										 ((Button)view.findViewById(musiclistItem[i])).setOnClickListener(new OnClickListener() {
//											@Override
//											public void onClick(View v) {
//												String appDownPathtrue  =HttpRequest.URL_QUERY_DOWNLOAD_URL+web_path+"&"+"duomi";
//												setMusicCross(appDownPathtrue,sid);
//											}
//										});
//								}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                        	Dialog.dismiss();
	                                //successful ajax call, show status code and json content
	                        }else{
	                      Toast.makeText(aQuery.getContext(), "Error:" +status.getCode(),Toast.LENGTH_LONG).show();
	                          }
	                    }
	            });
			 String musicssPath = HttpRequest.URL_QUERY_SINGLE_MUSIC+musicId;
			 aQuery.ajax(musicssPath, String.class, new AjaxCallback<String>() {
	                @Override
	                public void callback(String url, String json, AjaxStatus status) {
	                        if(json != null){
	                         final String image_path_boot;
							try {
								 final ArrayList<String> pathList = new ArrayList<String>();
	                        	ArrayList<String> nameList = new ArrayList<String>();
	                        	final ArrayList<PostMent> postMentList = new ArrayList<PostMent>();
								JSONObject  jb = new JSONObject(json);
								 image_path_boot = jb.getString("PIC");
								 String name =jb.getString("PNAME");
								 String note =jb.getString("PNOTE");
								 String artist="";
								 String company="";
								 String language="";
								 String pubdate ="";
								 if(!jb.isNull("COMPANY")){
									 artist =jb.getString("COMPANY"); 
										}
										if(!jb.isNull("COMPANY")){
										 company=jb.getString("COMPANY");
										}
										if(!jb.isNull("LANGUAGE")){
										 language = jb.getString("LANGUAGE");
										}
										if(!jb.isNull("PUBDATE")){
										 pubdate =jb.getString("PUBDATE");
										}
								 JSONArray postOb = jb.getJSONArray("potype");
								 for (int i = 0; i < postOb.length(); i++) {
									 
									 PostMent pm = new PostMent();
									 pm.setType(postOb.getJSONObject(i).getString("TYPE"));
									 pm.setId(postOb.getJSONObject(i).getString("PUBID"));
									 pm.setPrice(postOb.getJSONObject(i).getString("PRICE"));
									 postMentList.add(pm);
								}
								builder.show();
								 ((TextView)viewFormusicdetail.findViewById(R.id.albumname)).setText(name);							
								 ((TextView)viewFormusicdetail.findViewById(R.id.albuminfo)).setText(note);		
								 ((TextView)viewFormusicdetail.findViewById(R.id.artist)).setText(aQuery.getContext().getResources().getString(R.string.artist)+" : "+artist);		
								 ((TextView)viewFormusicdetail.findViewById(R.id.Issuedate)).setText(aQuery.getContext().getResources().getString(R.string.pubdate)+" ： "+pubdate);		
								 ((TextView)viewFormusicdetail.findViewById(R.id.language)).setText(aQuery.getContext().getResources().getString(R.string.language)+" ： "+language);		
								 ((TextView)viewFormusicdetail.findViewById(R.id.company)).setText(aQuery.getContext().getResources().getString(R.string.company)+" ： "+company);		
							} catch (JSONException e) {
								e.printStackTrace();
							}
	                        	Dialog.dismiss();
	                        }else{
	                        	Dialog.dismiss();
	                      Toast.makeText(aQuery.getContext(), "Error:" +status.getCode(),Toast.LENGTH_LONG).show();
	                          }
	                    }
	            });
		}
		//subscription
		 static void setOrder(final ArrayList<PostMent> postMentList,final String id ){
			 String[] myRadio =new String[postMentList.size()] ;
			 for (int i = 0; i < postMentList.size(); i++) {
				String type =postMentList.get(i).getType();
				String price = postMentList.get(i).getPrice();
				myRadio[i] =type+"/"+price; 
				 
			}
//			 final View view = inflater.inflate(R.layout.orderstype, null);
//			 for (int i = 0; i <orderRadioItem.length; i++) {
//				 view.findViewById(orderRadioItem[i]).setVisibility(View.INVISIBLE);
//			}
//			 for (int i = 0; i <postMentList.size(); i++) {
//				 view.findViewById(orderRadioItem[i]).setVisibility(View.INVISIBLE);
//				 RadioButton btn_rb=(RadioButton) view.findViewById(orderRadioItem[i]);
//				 String typeE =postMentList.get(i).getType();
//				 if(typeE.equals("day")){
//				 btn_rb.setText("day/"+postMentList.get(i).getPrice());
//				 btn_rb.setVisibility(View.VISIBLE);
//				 }
//				 if(typeE.equals("month")){
//					 btn_rb.setText("month/"+postMentList.get(i).getPrice());
//					 btn_rb.setVisibility(View.VISIBLE);
//				 }
//				 if(typeE.equals("quarter")){
//					 btn_rb.setText("quarter/"+postMentList.get(i).getPrice());
//					 btn_rb.setVisibility(View.VISIBLE);
//				 }
//				 if(typeE.equals("year")){
//					 btn_rb.setText("year/"+postMentList.get(i).getPrice());
//					 btn_rb.setVisibility(View.VISIBLE);
//				 }
//			}
//			 final Dialog dl = new Dialog(aQuery.getContext());
//				dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				dl.setContentView(view);
//				Window dialogWindow = dl.getWindow();
//				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//				dialogWindow.setGravity(Gravity.CENTER);
//				lp.width = 600; 
//				lp.height =200; 
//				dialogWindow.setAttributes(lp);
//				dl.show();
//				RadioGroup group = (RadioGroup) view.findViewById(R.id.radioGroup);
//				group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//					@Override
//					public void onCheckedChanged(RadioGroup group, int checkedId) {
//						// TODO Auto-generated method stub
//						
//						
//					}
//				});
 
				 new AlertDialog.Builder(aQuery.getContext())  
				.setTitle("请选择")  
				.setIcon(android.R.drawable.ic_dialog_info)                  
				.setSingleChoiceItems(myRadio, 0,   
				new DialogInterface.OnClickListener() {  
				  public void onClick(DialogInterface dialog, int which) {  
				         dialog.dismiss();  
				         Toast.makeText(aQuery.getContext(), which+"", 1).show();
				         String type = postMentList.get(which).getType();
				        String url = HttpRequest.URL_QUERY_LIST_PAY_ALL+id+HttpRequest.URL_ADD+type;
				        String result;
						  System.out.println("订购的地址为：==="+url);
						try {
							HttpGet request = new HttpGet(url);
								// 绑定到请求 Entry 
								// 发送请求 
								HttpResponse response = new DefaultHttpClient().execute(request); 
								// 得到应答的字符串，这也是一个 JSON 格式保存的数据  
								 result = EntityUtils.toString(response.getEntity());
								System.out.println("返回的数据============="+result);
							Toast.makeText(aQuery.getContext(), "返回的数据============="+result, 1).show();

						} catch (Exception e) {
							
							// TODO Auto-generated catch block
							e.printStackTrace();
						
						}     
				     }  
				   }  
				 )  
				 .setNegativeButton("取消", null)  
				 .show();
		 }
		 //check version
		 private void checkVersion() {
			 final Handler hd = new Handler();
				// TODO Auto-generated method stub
				 new AsyncTask<Void, Void, String>(){
					@Override
					protected void onPostExecute(String result) {
						if(isUpdate){
						 setUpdateDiago(path,desc);
						}
						super.onPostExecute(path);
					}
					String path;
					String desc;
					private ArrayList<VersionInfo> versionInfoList;
					@Override
					protected String doInBackground(Void... params) {
						try {
							URL url =  new URL(HttpRequest.URL_UPDATE);
							URLConnection con = url.openConnection();
							InputStream is = con.getInputStream();
							XmlParse xp = new XmlParse();
							versionInfoList = xp.getVersionInfo(is);
							for (int i = 0; i < versionInfoList.size(); i++) {
								if("FigureStore".equals(versionInfoList.get(i).getName())){
									String updateVersion =versionInfoList.get(i).getVersion();
								    PackageManager packageManager = getPackageManager();
					                // getPackageName()是你当前类的包名，0代表是获取版本信息
					                PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
					                                0);
					                String version = packInfo.versionName;
					                System.out.println("version============"+version);
					                if(!version.equals(updateVersion)){
					                	isUpdate =true;
					                }
					                path= HttpRequest.URL_UPDATE_ROOT+versionInfoList.get(i).getFilepath();
					                desc =versionInfoList.get(i).getDescription();
					                System.out.println("下载的地址为"+path);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return path;
					}
				 }.execute();
			}
		 // appearUpdateDialog
		 void setUpdateDiago(final String path,String desc){
				final Handler hd = new Handler();
							
								// TODO Auto-generated method stub
								Dialog dialog = new AlertDialog.Builder(aQuery.getContext())
								.setTitle(getResources().getString(R.string.findnewversion))
								.setMessage(getResources().getString(R.string.vserioncontent)+"\n"+desc)
								.setPositiveButton(
										getResources().getString(R.string.confirm),
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,
													int which) {
												new AsyncTask<Void, Void, Void>(){
													@Override
													protected Void doInBackground(
															Void... params) {
														UpdateVersion uv = UpdateVersion.instance(aQuery.getContext(),hd , true);
														uv.setUpdateUrl(path);
														uv.run();
														return null;
													}
													
												}.execute();
											}
										})
								.setNegativeButton(
										getResources().getString(R.string.cancle),
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,
													int whichButton) {
												dialog.dismiss();
											}
										}).create();
								dialog.show();
							}
		 public static class  musicTryplayAsyncTask extends AsyncTask<String,Void,Void>{
			 
			 private ImageButton myBtn;
			public musicTryplayAsyncTask(ImageButton iv) {
				myBtn=iv;
			}
			
			@Override
			protected Void doInBackground(String... params) {
				try {
					String path =params[0];
					mp.setDataSource(aQuery.getContext(), Uri.parse(path));
					mp.prepare();
				} catch (Exception e) {
					e.printStackTrace();
				}
				mp.start();
				isplay =true;
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				 myBtn.setImageResource(R.drawable.desktop_pause_b);
				super.onPostExecute(result);
			}
		
		 }
			
}
