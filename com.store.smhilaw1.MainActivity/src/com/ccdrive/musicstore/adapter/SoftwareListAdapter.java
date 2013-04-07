package com.ccdrive.musicstore.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccdrive.musicstore.R;
import com.ccdrive.musicstore.bean.SoftwareBean;

public class SoftwareListAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<SoftwareBean> list;
	private LayoutInflater inflater;

	public SoftwareListAdapter(Context c, ArrayList<SoftwareBean> list) {
		mContext = c;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.software_list_item, null);
			holder.name = (TextView) convertView
					.findViewById(R.id.name_tv);
			holder.info = (TextView) convertView
			        .findViewById(R.id.info_tv);
//			holder.count = (TextView) convertView
//			         .findViewById(R.id.count_tv);
			holder.imageView = (ImageView) convertView
			        .findViewById(R.id.shop_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(list.get(position).getName());
		holder.info.setText(list.get(position).getInfo());
//		holder.count.setText(list.get(position).getCount());
		if(list.get(position).getName().equals("pptv")){
			holder.imageView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.pptv));
		}else if(list.get(position).getName().equals("推箱子")){
			holder.imageView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.tuixiangzi));
		}else if(list.get(position).getName().equals("酷狗音乐")){
			holder.imageView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.kugou));
		}
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView info;
//		TextView count;
		ImageView imageView;
	}
}