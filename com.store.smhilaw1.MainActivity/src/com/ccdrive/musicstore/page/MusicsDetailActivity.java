package com.ccdrive.musicstore.page;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ccdrive.musicstore.R;
import com.ccdrive.musicstore.bean.Music;
import com.ccdrive.musicstore.bean.PostMent;
import com.ccdrive.musicstore.content.Constant;
import com.ccdrive.musicstore.http.HttpRequest;
import com.ccdrive.musicstore.http.ImageDownloader;
import com.ccdrive.musicstore.play.PlayerActivity;
import com.ccdrive.musicstore.play.StreamingMediaPlayerControl;
import com.ccdrive.musicstore.play.VitamioPlayer;

public class MusicsDetailActivity extends Activity {
	private  int[] musiclistItem = { R.id.music_item_hor_01,
		R.id.music_item_hor_02, R.id.music_item_hor_03,
		R.id.music_item_hor_04, R.id.music_item_hor_05,
		R.id.music_item_hor_06, R.id.music_item_hor_07,
		R.id.music_item_hor_08, R.id.music_item_hor_09,
		R.id.music_item_hor_10 };
	private  int[] horItems = { R.id.item_hor_01, R.id.item_hor_02,
		R.id.item_hor_03, R.id.item_hor_04, R.id.item_hor_05,
		R.id.item_hor_06, R.id.item_hor_07, R.id.item_hor_08,
		R.id.item_hor_09, R.id.item_hor_10, R.id.item_hor_11,
		R.id.item_hor_12, R.id.item_hor_13, R.id.item_hor_14,
		R.id.item_hor_15 };

	
	static int ismusic = 000011; // 判断是否为music 还是mv
	static int isMv = 000012;
	private  StreamingMediaPlayerControl audioStreamer; // 播放器
	
	private  int whatisplay = 1; // 判断哪个音乐播放。用来暂停或者
//tv item
//private static int[] tvlistItem = { R.id.i_text_episode_text1,
//	R.id.i_text_episode_text2, R.id.i_text_episode_text3,
//	R.id.i_text_episode_text4, R.id.i_text_episode_text5,
//	R.id.i_text_episode_text6, R.id.i_text_episode_text7,
//	R.id.i_text_episode_text8, R.id.i_text_episode_text9,
//	R.id.i_text_episode_text10 };

private static int[] orderRadioItem = { R.id.rad1, R.id.rad2, R.id.rad3,
	R.id.rad4 };
	private AQuery aQuery;
	private LayoutInflater inflater;
	static int isFilm = 000011; // 判断是否为music 还是mv
	static int isTv = 000012;
	private String whatTrueOrder =""; //订阅的内容
	int isWhatLeft;
	int isWhatRight;
	private ProgressDialog pd;
	int bo;
	private String orderId;
	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_detail1);
		Intent t =getIntent();
		int id =t.getIntExtra("id", 0);
		int j = 0;
		for (int i = 0; i < horItems.length; i++) {
			if (id == horItems[i]) {
				j = i;
			}
		}
		 aQuery =new AQuery(MusicsDetailActivity.this);
		 final ArrayList<Music> list =(ArrayList<Music>) t.getSerializableExtra("list");
		 isWhatRight =t.getIntExtra("isWhatRight", 0);
		  isWhatLeft =t.getIntExtra("isWhatLeft", 0);
		 bo =t.getIntExtra("iswhat", 0);
		 orderId =t.getStringExtra("orderId");
		final Button btn_orderall = (Button) (findViewById(R.id.btn_order_allmusic));
		if(isWhatRight==Constant.MYMUSIC){
			btn_orderall.setVisibility(View.INVISIBLE);
		}
		else{
			btn_orderall.setVisibility(View.VISIBLE);
		}
		// final ListView listview =(ListView)
		// view.findViewById(R.id.music_detail_list);
		for (int i = 0; i < musiclistItem.length; i++) {
			findViewById(musiclistItem[i]).setVisibility(View.GONE);
		}
		final ProgressDialog Dialog = ProgressDialog.show(aQuery.getContext(),
				"缓冲中。。", "正在缓冲请稍后。。");
		ProDiaglogDimiss(Dialog);
		final String musicId = list.get(j).getId();
		String web_url = HttpRequest.URL_QUERY_LIST_MUSIC + musicId;
		aQuery.ajax(web_url, String.class, new AjaxCallback<String>() {// 这里的函数是一个内嵌函数如果是函数体比较复杂的话这种方法就不太合适了
					@Override
					public void callback(String url, String json,
							AjaxStatus status) {
						if (json != null) {
							ArrayList<Music> musicDetialList = new ArrayList<Music>();
							System.out.println("下载的数据" + "====" + json);
							Dialog.dismiss();
							try {
								JSONArray ja = new JSONArray(json);
								for (int i = 0; i < ja.length(); i++) {
									Music music = new Music();
									JSONObject jb = ja.getJSONObject(i);
									String sid = jb.getString("sid");
									String musicpath = jb.getString("filepath");
									String title = jb.getString("title");
									music.setDownload_path(musicpath);
									music.setId(sid);
									music.setName(title);
									musicDetialList.add(music);
								}

								// MusicAdapter myMusicAdapter = new
								// MusicAdapter(aQuery.getContext(),
								// musicDetialList,isWhatRight);
								// listview.setAdapter(myMusicAdapter);

								for (int i = 0; i < musicDetialList.size(); i++) {
									final String path = musicDetialList.get(i)
											.getDownload_path();
									final Music music =musicDetialList.get(i);
									findViewById(musiclistItem[i])
											.setVisibility(View.VISIBLE);
									final int viewid = musiclistItem[i];
									TextView tv = (TextView) (findViewById(musiclistItem[i])
											.findViewById(R.id.name));
									final String name = musicDetialList.get(i)
											.getName();
									tv.setText(name);
									findViewById(musiclistItem[i])
											.setOnClickListener(
													new OnClickListener() {
														String appDownPathtrue = HttpRequest.URL_QUERY_DOWNLOAD_URL
																+ path;

														@Override
														public void onClick(
																View v) {
															if (bo == ismusic) {
																setMusicPilot(
																		appDownPathtrue,
																		viewid);
															} else {
																setMVPilot(music);
															}
														}
													});
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// successful ajax call, show status code and json
							// content
						} else {
							Dialog.dismiss();
							Toast.makeText(aQuery.getContext(),
									"Error:" + status.getCode(),
									Toast.LENGTH_LONG).show();
						}
					}
				});
		String musicssPath = HttpRequest.URL_QUERY_SINGLE_MUSIC + musicId;
		aQuery.ajax(musicssPath, String.class, new AjaxCallback<String>() {
			@Override
			public void callback(String url, String json, AjaxStatus status) {
				if (json != null) {
					System.out.println("下载的数据" + "=======" + json);
					final String image_path_boot;
					try {
						final ArrayList<String> pathList = new ArrayList<String>();
						ArrayList<String> nameList = new ArrayList<String>();
						final ArrayList<PostMent> postMentList = new ArrayList<PostMent>();
						JSONObject jb = new JSONObject(json);
						image_path_boot = jb.getString("PIC");
						String name = jb.getString("PNAME");
						String note = jb.getString("PNOTE");
						String artist = "";
						String company = "";
						String language = "";
						String pubdate = "";
						if (!jb.isNull("COMPANY")) {
							artist = jb.getString("COMPANY");
						}
						if (!jb.isNull("COMPANY")) {
							company = jb.getString("COMPANY");
						}
						if (!jb.isNull("LANGUAGE")) {
							language = jb.getString("LANGUAGE");
						}
						if (!jb.isNull("PUBDATE")) {
							pubdate = jb.getString("PUBDATE");
						}
						JSONArray postOb = jb.getJSONArray("potype");
						for (int i = 0; i < postOb.length(); i++) {

							PostMent pm = new PostMent();
							pm.setType(postOb.getJSONObject(i)
									.getString("TYPE"));
							pm.setId(postOb.getJSONObject(i).getString("PUBID"));
							pm.setPrice(postOb.getJSONObject(i).getString(
									"PRICE"));
							postMentList.add(pm);
						}
						btn_orderall.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								setOrder(postMentList, orderId);

							}
						});
						((TextView)findViewById(R.id.musicdetail_text))
								.setText(name);
						((TextView)findViewById(R.id.albumname)).setText(name);
						((TextView)findViewById(R.id.albuminfo)).setText(note);
						((TextView)findViewById(R.id.artist)).setText(aQuery
								.getContext().getResources()
								.getString(R.string.artist)
								+ " : " + artist);
						((TextView) findViewById(R.id.Issuedate)).setText(aQuery
								.getContext().getResources()
								.getString(R.string.pubdate)
								+ " ： " + pubdate);
						((TextView)findViewById(R.id.language)).setText(aQuery
								.getContext().getResources()
								.getString(R.string.language)
								+ " ： " + language);
						((TextView)findViewById(R.id.company)).setText(aQuery
								.getContext().getResources()
								.getString(R.string.company)
								+ " ： " + company);
						setMusicrecommend(list);
						String path = HttpRequest.URL_QUERY_SINGLE_IMAGE
								+ image_path_boot;
						ImageView imageView = (ImageView)findViewById(R.id.albumimage);
						ImageDownloader downloader = new ImageDownloader(aQuery
								.getContext());
						downloader.download(path, imageView);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Dialog.dismiss();
				} else {
					Dialog.dismiss();
					Toast.makeText(aQuery.getContext(),
							"Error:" + status.getCode(), Toast.LENGTH_LONG)
							.show();
				}
			}
		});
	  }
	  public static void DiaglogDimiss(Dialog dialog){
		  dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					 if (keyCode == KeyEvent.KEYCODE_BACK){
					          // Perform action on key press
						 dialog.dismiss();
					          return true;
					        }
					return false;
				}
			});
	  }
	  public static void ProDiaglogDimiss(ProgressDialog dialog){
		  dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			  @Override
			  public boolean onKey(DialogInterface dialog, int keyCode,
					  KeyEvent event) {
				  if (keyCode == KeyEvent.KEYCODE_BACK){
					  // Perform action on key press
					  dialog.dismiss();
					  return true;
				  }
				  return false;
			  }
		  });
	  }
	  
		// set pilot play UI
		public  void setMusicPilot(final String path, int viewid) {

			try {
				if (whatisplay == 1) {
					whatisplay = viewid;
					final SeekBar progressBar = (SeekBar) (findViewById(viewid)
							.findViewById(R.id.progress_bar));
					progressBar.setVisibility(View.VISIBLE);
					final TextView playTime = (TextView) (findViewById(viewid)
							.findViewById(R.id.playTime));
//					audioStreamer = new StreamingMediaPlayerControl(aQuery.getContext(),
//							progressBar, playTime);
					audioStreamer.startStreaming(path);
				} else if (whatisplay == viewid) {
					whatisplay = viewid;
					if (audioStreamer.getMediaPlayer().isPlaying()) {
						audioStreamer.getMediaPlayer().pause();
					} else if (!audioStreamer.getMediaPlayer().isPlaying()) {
						audioStreamer.getMediaPlayer().start();
						audioStreamer.startPlayProgressUpdater();
					}
				} else if (whatisplay != viewid) {
					final SeekBar progressBarold = (SeekBar) (findViewById(whatisplay)
							.findViewById(R.id.progress_bar));
					progressBarold.setVisibility(View.INVISIBLE);
					whatisplay = viewid;
					if (audioStreamer.getMediaPlayer().isPlaying()) {
						audioStreamer.stopplay();
					}
					final SeekBar progressBar = (SeekBar) (findViewById(viewid)
							.findViewById(R.id.progress_bar));
					progressBar.setVisibility(View.VISIBLE);
					final TextView playTime = (TextView) (findViewById(viewid)
							.findViewById(R.id.playTime));
//					audioStreamer = new StreamingMediaPlayerControl(aQuery.getContext(),
//							progressBar, playTime);
					audioStreamer.startStreaming(path);
				}

			} catch (Exception e) {
			}

		}
		
		// setmvplay
		public  void setMVPilot(final Music music) {
			Intent it;
			if(Constant.useVitamio){
				it =  new Intent(aQuery.getContext(),VitamioPlayer.class);
			}else{
				 it =  new Intent(aQuery.getContext(),PlayerActivity.class);
			}
			
			it.putExtra("clientControll", false);
			it.putExtra("movie",music);
			// i.setClass(aQuery.getContext(), Simplayer.class);
			aQuery.getContext().startActivity(it);
		}
		
		/**
		 * subscription
		 *  加入到购物车中
		 * @param postMentList
		 * @param id
		 */
		private  void setOrder(final ArrayList<PostMent> postMentList, final String id) {
			String[]  myRadio = new String[postMentList.size()];
			for (int i = 0; i < postMentList.size(); i++) {
				String type = postMentList.get(i).getType();
				String price = postMentList.get(i).getPrice();
				myRadio[i] = type + "/" + price;

			}
			final View view = inflater.inflate(R.layout.orderstype, null);
			for (int i = 0; i < orderRadioItem.length; i++) {
				view.findViewById(orderRadioItem[i]).setVisibility(View.INVISIBLE);
			}
			for (int i = 0; i < postMentList.size(); i++) {
				view.findViewById(orderRadioItem[i]).setVisibility(View.INVISIBLE);
				RadioButton btn_rb = (RadioButton) view
						.findViewById(orderRadioItem[i]);
				String typeE = postMentList.get(i).getType();
				if(i==0){
					
					whatTrueOrder=typeE;
				}
				if (typeE.equals("day")) {
					btn_rb.setText("day/" + postMentList.get(i).getPrice());
					btn_rb.setVisibility(View.VISIBLE);
				}
				if (typeE.equals("month")) {
					btn_rb.setText("month/" + postMentList.get(i).getPrice());
					btn_rb.setVisibility(View.VISIBLE);
				}
				if (typeE.equals("quarter")) {
					btn_rb.setText("quarter/" + postMentList.get(i).getPrice());
					btn_rb.setVisibility(View.VISIBLE);
				}
				if (typeE.equals("year")) {
					btn_rb.setText("year/" + postMentList.get(i).getPrice());
					btn_rb.setVisibility(View.VISIBLE);
				}
			}
		
			final Dialog dl = new Dialog(aQuery.getContext());
			dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dl.setContentView(view);
			Window dialogWindow = dl.getWindow();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			dialogWindow.setGravity(Gravity.CENTER);
			lp.width = 600;
			lp.height = 400;
			dialogWindow.setAttributes(lp);
			dl.show();
			RadioGroup group = (RadioGroup) view.findViewById(R.id.radioGroup);
			group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					int checkedRadioButtonId = group.getCheckedRadioButtonId();
					// 根据ID获取RadioButton的实例
					RadioButton RadioButtonrb = (RadioButton) view
							.findViewById(checkedRadioButtonId);
					String s = RadioButtonrb.getText().toString();
					String whatOrder = s.substring(0, s.lastIndexOf("/"));
					whatTrueOrder = whatOrder;
					Toast.makeText(aQuery.getContext(), whatOrder, 1).show();

				}
			});
			view.findViewById(R.id.btn_order_confrm).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							new AsyncTask<Void, Void, String>(){

								@Override
								protected void onPostExecute(String result) {
									pd.dismiss();
									if(result.equals("true")){
										Toast.makeText(aQuery.getContext(), "加入购物车成功", 1).show();
									}else{
										
										Toast.makeText(aQuery.getContext(), "加入购物车失败，请重试", 1).show();
									}
									
									super.onPostExecute(result);
									
								}

								@Override
								protected void onPreExecute() {
									pd =new ProgressDialog(aQuery.getContext());
									pd.show();
									super.onPreExecute();
								}
								@Override
								protected String doInBackground(Void... params) {
									String url = null;
									if(isWhatLeft==Constant.MUSICCHAPTER){
										url = HttpRequest.URL_QUERY_SINGLE_ORDER_MUSIC + id
											+ HttpRequest.URL_ADD + whatTrueOrder;
									}else if(isWhatLeft==Constant.MUSICMV){
										url= HttpRequest.URL_QUERY_LIST_PAY_ALL + id
												+ HttpRequest.URL_ADD + whatTrueOrder;
									}
									String result = null;
									System.out.println("订购的地址为：===" + url);
									try {
										HttpGet request = new HttpGet(url);
										// 绑定到请求 Entry
										// 发送请求
										HttpResponse response = new DefaultHttpClient()
												.execute(request);
										// 得到应答的字符串，这也是一个 JSON 格式保存的数据
										result = EntityUtils.toString(response.getEntity());
										dl.dismiss();
									} catch (Exception e) {
										dl.dismiss();
										// TODO Auto-generated catch block
										e.printStackTrace();

									}
									return result;
								}
								
							}.execute();
							

						}
					});
			findViewById(R.id.btn_order_cancle).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dl.dismiss();
						}
					});
		}
		// 音乐推荐
		private  void setMusicrecommend(ArrayList<Music> list) {
			final ArrayList<String> myPathlist = new ArrayList<String>();
			ImageDownloader Downloader = new ImageDownloader(aQuery.getContext());
			for (int i = 0; i < 5; i++) {
				findViewById(horItems[i]).setVisibility(View.VISIBLE);
			}
			int j = 0;
			if (list.size() < 5) {
				for (int s = 0; s < 5 - list.size(); s++) {
					findViewById(horItems[4 - s])
							.setVisibility(View.INVISIBLE);
				}
			}
			for (int i = 0; i < ((list.size() <= 5) ? list.size() : 5); i++) {
				final Music sb = list.get(i);
				String image_path = sb.getImage_path();
				final String title = sb.getName();
				final String turePath = HttpRequest.URL_QUERY_SINGLE_IMAGE
						+ image_path;
				Downloader.download(
						turePath,
						((ImageView) findViewById(horItems[i]).findViewById(
								R.id.ItemIcon)));
				((TextView)findViewById(horItems[i]).findViewById(
						R.id.ItemTitle)).setText(title);
				myPathlist.add(image_path);
				findViewById(horItems[i]).setOnClickListener(
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								((TextView)findViewById(R.id.albumname))
										.setText(title);
								ImageView imageView = (ImageView) 
										findViewById(R.id.albumimage);
								ImageDownloader downloader = new ImageDownloader(
										aQuery.getContext());
								downloader.download(turePath, imageView);
								setRecommedMusicInfo(sb.getId(),  sb);
							}
						});
			}

		}
		// loading dialog for recommedmusic
		public  void setRecommedMusicInfo(String musicId, 
				Music music) {
			for (int i = 0; i < musiclistItem.length; i++) {
				findViewById(musiclistItem[i]).setVisibility(View.GONE);
			}
			final ProgressDialog Dialog = ProgressDialog.show(aQuery.getContext(),
					"Loading。。", "please wait moment。。");
			Dialog.show();
			ProDiaglogDimiss(Dialog);
			String web_url = HttpRequest.URL_QUERY_LIST_MUSIC + musicId;
			aQuery.ajax(web_url, String.class, new AjaxCallback<String>() {
				@Override
				public void callback(String url, String json, AjaxStatus status) {
					if (json != null) {
						final ArrayList<Music> musicDetialList = new ArrayList<Music>();
						System.out.println("下载的数据" + "====" + json);
						Dialog.dismiss();
						try {
							JSONArray ja = new JSONArray(json);
							for (int i = 0; i < ja.length(); i++) {
								Music music = new Music();
								JSONObject jb = ja.getJSONObject(i);
								String sid = jb.getString("sid");
								String musicpath = jb.getString("filepath");
								String title = jb.getString("title");
								music.setDownload_path(musicpath);
								music.setId(sid);
								music.setName(title);
								musicDetialList.add(music);
							}
							for (int i = 0; i < musicDetialList.size(); i++) {
								final String path = musicDetialList.get(i)
										.getDownload_path();
								final Music music =musicDetialList.get(i);
								final int viewid = musiclistItem[i];
								findViewById(musiclistItem[i]).setVisibility(
										View.VISIBLE);
								TextView tv = (TextView) (findViewById(musiclistItem[i])
										.findViewById(R.id.name));
								final String name = musicDetialList.get(i)
										.getName();
								tv.setText(name);
								findViewById(musiclistItem[i])
										.setOnClickListener(new OnClickListener() {
											String appDownPathtrue = HttpRequest.URL_QUERY_DOWNLOAD_URL
													+ path;

											@Override
											public void onClick(View v) {
												if (isWhatLeft == Constant.MUSICCHAPTER) {
													setMusicPilot(appDownPathtrue,
															 viewid);
												} else if (isWhatLeft == Constant.MUSICMV) {
													setMVPilot(music);
												}
											}
										});
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Dialog.dismiss();
						// successful ajax call, show status code and json content
					} else {
						Toast.makeText(aQuery.getContext(),
								"Error:" + status.getCode(), Toast.LENGTH_LONG)
								.show();
					}
				}
			});
			String musicssPath = HttpRequest.URL_QUERY_SINGLE_MUSIC + musicId;
			aQuery.ajax(musicssPath, String.class, new AjaxCallback<String>() {
				@Override
				public void callback(String url, String json, AjaxStatus status) {
					if (json != null) {
						final String image_path_boot;
						try {
							final ArrayList<String> pathList = new ArrayList<String>();
							ArrayList<String> nameList = new ArrayList<String>();
							final ArrayList<PostMent> postMentList = new ArrayList<PostMent>();
							JSONObject jb = new JSONObject(json);
							image_path_boot = jb.getString("PIC");
							String name = jb.getString("PNAME");
							String note = jb.getString("PNOTE");
							String artist = "";
							String company = "";
							String language = "";
							String pubdate = "";
							if (!jb.isNull("COMPANY")) {
								artist = jb.getString("COMPANY");
							}
							if (!jb.isNull("COMPANY")) {
								company = jb.getString("COMPANY");
							}
							if (!jb.isNull("LANGUAGE")) {
								language = jb.getString("LANGUAGE");
							}
							if (!jb.isNull("PUBDATE")) {
								pubdate = jb.getString("PUBDATE");
							}
							JSONArray postOb = jb.getJSONArray("potype");
							for (int i = 0; i < postOb.length(); i++) {

								PostMent pm = new PostMent();
								pm.setType(postOb.getJSONObject(i)
										.getString("TYPE"));
								pm.setId(postOb.getJSONObject(i).getString("PUBID"));
								pm.setPrice(postOb.getJSONObject(i).getString(
										"PRICE"));
								postMentList.add(pm);
							}
							((TextView) findViewById(R.id.musicdetail_text)).setText(name);
							((TextView) findViewById(R.id.albumname)).setText(name);
							((TextView)findViewById(R.id.albuminfo)).setText(note);
							((TextView) findViewById(R.id.artist)).setText(aQuery
									.getContext().getResources()
									.getString(R.string.artist)
									+ " : " + artist);
							((TextView) findViewById(R.id.Issuedate)).setText(aQuery
									.getContext().getResources()
									.getString(R.string.pubdate)
									+ " ： " + pubdate);
							((TextView)findViewById(R.id.language)).setText(aQuery
									.getContext().getResources()
									.getString(R.string.language)
									+ " ： " + language);
							((TextView)findViewById(R.id.company)).setText(aQuery
									.getContext().getResources()
									.getString(R.string.company)
									+ " ： " + company);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						Dialog.dismiss();
					} else {
						Dialog.dismiss();
						Toast.makeText(aQuery.getContext(),
								"Error:" + status.getCode(), Toast.LENGTH_LONG)
								.show();
					}
				}
			});
		}
}
