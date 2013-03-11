package com.store.content;

import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import com.store.bean.SoftwareBean;


public class CommUtil {
	
	public static ArrayList<String> list_left;
	HttpResponse response;
	HttpGet request;
	HttpEntity responseEntity;
	HttpClient httpClient ;
	int size = 10;
	ArrayList<SoftwareBean> softlist;
		//调用接口
	public ArrayList<String> GetLawRuleList(int left_type,int library) {
		// TODO Auto-generated method stub 
		return null;
	}
	
	public ArrayList<SoftwareBean> GetSoftwareList(int left_type) {
		// TODO Auto-generated method stub
		softlist = new ArrayList<SoftwareBean>();
		if(left_type == Constant.ANIME){
			for(int i = 0;i<10;i++){
				SoftwareBean shop  = new SoftwareBean();
				shop.setName("pptv");
				shop.setInfo("影音-pptv");
				shop.setVersion("发布日期 2013.01.01");
				softlist.add(shop);
			}
		}
		if(left_type == Constant.MUSIC){
			for(int i = 0;i<10;i++){
				SoftwareBean shop  = new SoftwareBean();
				shop.setName("推箱子");
				shop.setInfo("游戏-推箱子");
				shop.setVersion("发布日期 2012.01.01");
				softlist.add(shop);
			}
		}
		if(left_type == Constant.TV || left_type == Constant.FLFG){
			for(int i = 0;i<10;i++){
				SoftwareBean shop  = new SoftwareBean();
				shop.setName("酷狗音乐");
				shop.setInfo("音乐-酷狗");
				shop.setVersion("发布日期 2012.05.01");
				softlist.add(shop);
			}
		}
		return  softlist;
	}
	
	public ArrayList<String> GetRightTitle(int left_type) {
		// TODO Auto-generated method stub
		list_left = new ArrayList<String>();
		if(left_type == Constant.FLFG){
			String[] flfgStr = { "影音播放", "娱乐"};
			for (int i = 0; i < flfgStr.length; i++) {
			list_left.add(flfgStr[i]);
			}
		}
		if(left_type == Constant.AL){
			String[] alStr = { "PPTV最新版", "推箱子"};
			for (int i = 0; i < alStr.length; i++) {
			list_left.add(alStr[i]);
			}
		}
		if(left_type == Constant.FLWSYS){
			String[] flwsysStr = { "网易新闻"};
			for (int i = 0; i < flwsysStr.length; i++) {
			list_left.add(flwsysStr[i]);
			}
		}
		return  list_left;
	}
}
