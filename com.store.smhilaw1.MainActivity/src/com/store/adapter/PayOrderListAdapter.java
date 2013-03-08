package com.store.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.store.bean.OrderBean;
import com.store.bean.PayOrderBean;
import com.store.smhilaw1.R;

public class PayOrderListAdapter extends BaseAdapter
{
	// 定义Context
	private Context		mContext;
	ArrayList<PayOrderBean> list;
	private LayoutInflater inflater;
	private Fragment fragment;
	// 定义整型数组 即图片源

	public PayOrderListAdapter(Context c,Fragment fragment,ArrayList<PayOrderBean> list)
	{
		mContext = c;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
		this.fragment = fragment;
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
			convertView = inflater.inflate(R.layout.pay_order_list_item, null);
			holder.name = (TextView) convertView
			        .findViewById(R.id.name);
			holder.version = (TextView) convertView
			         .findViewById(R.id.verson);
//			holder.button = (Button) convertView
//			        .findViewById(R.id.order);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(list.get(position).getName());
		holder.version.setText(list.get(position).getPrice());
//		holder.button.setOnClickListener((OnClickListener) fragment);
//		holder.button.setTag(list.get(position).getSid());
		return convertView;
	}
	
	class ViewHolder {
		TextView name;
		TextView version;
//		Button button;
	}

}