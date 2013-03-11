package com.store.adapter;

import java.util.ArrayList;

import com.store.adapter.SoftwareListAdapter.ViewHolder;
import com.store.bean.SoftwareBean;
import com.store.smhilaw1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class RightGridViewAdapter extends BaseAdapter
{
	// 定义Context
	private Context		mContext;
	ArrayList<SoftwareBean> list;
	private LayoutInflater inflater;
	// 定义整型数组 即图片源
	private Integer[]	mImageIds	= 
	{ 
			R.drawable.grid_item, 
			R.drawable.grid_item, 
			R.drawable.grid_item, 
			R.drawable.grid_item, 
			R.drawable.grid_item, 
			R.drawable.grid_item
	};

	public RightGridViewAdapter(Context c,ArrayList<SoftwareBean> list)
	{
		mContext = c;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
	}

	// 获取图片的个数
	public int getCount()
	{
		return mImageIds.length;
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
		holder.info.setText(list.get(position).getInfo());
		return convertView;
	}
	
	class ViewHolder {
		TextView info;
//		TextView count;
		ImageView imageView;
	}

}