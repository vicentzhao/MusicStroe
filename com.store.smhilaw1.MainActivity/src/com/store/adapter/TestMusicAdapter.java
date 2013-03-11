package com.store.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.store.bean.SoftwareBean;
import com.store.http.HttpRequest;
import com.store.http.ImageDownloader;
import com.store.smhilaw1.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TestMusicAdapter extends BaseAdapter
{
	// 定义Context
	private Context		mContext;
	ArrayList<HashMap<String,String>> list;
	private LayoutInflater inflater;
	private ImageDownloader imageDownloader;
	HttpRequest http;
	// 定义整型数组 即图片源
	private Integer[]	mImageIds	= 
	{ 
			R.drawable.qq, 
			R.drawable.duomi, 
			R.drawable.qq, 
			R.drawable.duomi, 
			R.drawable.qq, 
			R.drawable.duomi, 
			R.drawable.qq, 
			R.drawable.duomi, 
	};

	public TestMusicAdapter(Context c,ArrayList<HashMap<String,String>> list)
	{
		mContext = c;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
		http = new HttpRequest();
		imageDownloader = new ImageDownloader(mContext);
	}

	// 获取图片的个数
	public int getCount()
	{
		return list.size();
	}

	// 获取图片在库中的位置
	public Object getItem(int position)
	{
		return position;
	}

	// 获取图片ID
	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.gridview_item, null);
			holder.info = (TextView) convertView
			        .findViewById(R.id.ItemText);
//			holder.count = (TextView) convertView
//			         .findViewById(R.id.count_tv);
			holder.imageView = (ImageView) convertView
			        .findViewById(R.id.ItemImage);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.imageView.setBackgroundDrawable(null);
		if(list.get(position).get("info").equals("QQ") || list.get(position).get("info").equals("QQ+")
				|| list.get(position).get("info").equals("QQ++")){
			holder.imageView.setImageResource(R.drawable.qq);
		}else{
			holder.imageView.setImageResource(R.drawable.duomi);
		}
		holder.info.setText(list.get(position).get("info"));
		return convertView;
	}
	
	class ViewHolder {
		TextView info;
//		TextView count;
		ImageView imageView;
	}

}