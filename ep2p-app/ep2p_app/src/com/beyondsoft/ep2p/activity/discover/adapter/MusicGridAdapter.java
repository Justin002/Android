package com.beyondsoft.ep2p.activity.discover.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.discover.service.SongInfo;

/**
 * 红包详情
 * 
 * @author xiaoliang
 * 
 */
public class MusicGridAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	List<SongInfo> soninfo;

	public MusicGridAdapter(Context context, List<SongInfo> soninfo) {
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
		this.soninfo = soninfo;
	}

	/**
	 * 播放歌曲索引
	 */
	private int playIndexPosition = -1;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder holder = null;
		if (convertView == null) {
			view = inflater.inflate(R.layout.item_radiostation_grid, null);

			holder = new ViewHolder();
			holder.title = (TextView) view.findViewById(R.id.tv_title);
			holder.img_icon_play = (ImageView) view
					.findViewById(R.id.img_icon_play);
			holder.img_icon_pause = (ImageView) view
					.findViewById(R.id.img_icon_pause);

			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		holder.title.setText(soninfo.get(position).getDisplayName());
//		holder.img_icon_play.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				holder.img_icon_play.setVisibility(View.GONE);
//				holder.img_icon_pause.setVisibility(View.VISIBLE);
//
//			}
//		});
//		holder.img_icon_pause.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				holder.img_icon_play.setVisibility(View.GONE);
//				holder.img_icon_pause.setVisibility(View.VISIBLE);
//
//			}
//		});

		return view;
	}

	@Override
	public int getCount() {
		return soninfo.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	class ViewHolder {
		TextView title;
		ImageView img_icon_play;
		ImageView img_icon_pause;
	}

}
