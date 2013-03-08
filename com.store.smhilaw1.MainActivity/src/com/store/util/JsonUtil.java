package com.store.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.store.bean.OrderBean;
import com.store.bean.PayOrderBean;
import com.store.bean.SoftwareBean;
import com.store.suffix.CryptUtil;

public class JsonUtil {
	
	public static ArrayList<SoftwareBean> getProductList(String str){
		ArrayList<SoftwareBean> list = new ArrayList<SoftwareBean>();
		SoftwareBean bean = null;
		if(str == null)return list;
		try {
			JSONArray jsonA = new JSONArray(str);
			for(int i =0;i<jsonA.length();i++){
				JSONObject jsonO = jsonA.getJSONObject(i);
				bean = new SoftwareBean();
				bean.setImage_path(jsonO.getString("PIC"));
				bean.setName(jsonO.getString("PUBNAME"));
				bean.setInfo(jsonO.getString("PHID"));
				bean.setVersion(jsonO.getString("QUA"));
				bean.setId(jsonO.getString("ID"));
				bean.setDownload_path(jsonO.getString("ID"));
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<OrderBean> getOrderList(String str){
		ArrayList<OrderBean> list = new ArrayList<OrderBean>();
		OrderBean bean = null;
		if(str == null)return list;
		try {
			JSONArray jsonA = new JSONArray(str);
			for(int i =0;i<jsonA.length();i++){
				JSONObject jsonO = jsonA.getJSONObject(i);
				bean = new OrderBean();
				String name = jsonO.getString("title");
				String version = jsonO.getString("price");
				bean.setName(name);	  
				bean.setVersion(version);	
				bean.setSid(jsonO.getString("sid"));
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static boolean getPayResult(String str){
		boolean result = false;
		if(str == null)return result;
		try {
				JSONObject jsonO = new JSONObject(str);
				if(jsonO.getString("STATE") != null && jsonO.getString("STATE").equals("0") ){
					result = true;
				}else{
					result = false;
				}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static ArrayList<PayOrderBean> getOrderNum(String str){
		ArrayList<PayOrderBean> list = new ArrayList<PayOrderBean>();
		PayOrderBean bean = null;
		if(str == null)return list;
		try {
			JSONObject json = new JSONObject(str);
			JSONArray jsonA = json.getJSONArray("list");
			for(int i =0;i<jsonA.length();i++){
				JSONObject jsonO = jsonA.getJSONObject(i);
				bean = new PayOrderBean();
				String name = jsonO.getString("pname");
				String price = jsonO.getString("PRICE");
				bean.setName(name);	  
				bean.setPrice(price);	
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static void setLogin(String str,Context con){
		try {
			JSONObject json = new JSONObject(str);
			String token = json.getString("TOKEN");
			if(token != null){
				AuthoSharePreference.putToken(con, token);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String subStr(String str,String key){
		   String temp =str.toLowerCase();
		   key=key.toLowerCase();
		   if(temp.indexOf(key) == -1){
	    	  return "-1";
		   }
	       temp=temp.substring(temp.indexOf(key)+key.length());
    	   temp=temp.substring(0,temp.indexOf(","));
    	   temp=temp.replace("=", "").trim();
    	   temp=temp.replace("[", "").trim();
    	   temp=temp.replace("]", "").trim();
    	   return temp;
	}

}
