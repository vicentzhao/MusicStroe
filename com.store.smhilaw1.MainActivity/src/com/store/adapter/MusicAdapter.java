package com.store.adapter;

import io.vov.vitamio.MediaPlayer;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.store.bean.SoftwareBean;
import com.store.smhilaw1.MainActivity1;
import com.store.smhilaw1.R;

public class MusicAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<SoftwareBean> list;
	private LayoutInflater inflater;
	private MediaPlayer myMp;

	public MusicAdapter(Context c, ArrayList<SoftwareBean> list,MediaPlayer mp) {
		mContext = c;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
		myMp = mp;
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

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.musicdetial_list_item, null);
			holder.name = (TextView) convertView
					.findViewById(R.id.musicchartername);
			holder.btn_play = (Button) convertView
			        .findViewById(R.id.btn_music_play);
//			holder.count = (TextView) convertView
//			         .findViewById(R.id.count_tv);
			holder.btn_down = (Button) convertView
			        .findViewById(R.id.btn_music_down);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(list.get(position).getName());
		holder.btn_play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 MainActivity1.setMusicPilot(list.get(position).getDownload_path());
			}
		});

		return convertView;
	}

	class ViewHolder {
		TextView name;
		Button   btn_play;
//		TextView count;
		Button  btn_down;
	}
}