package com.beyondsoft.ep2p.activity.discover.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.RatioStationListResponse.Result.RadioListItem;
import com.beyondsoft.ep2p.utils.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * @author Ivan.Lu
 * @description 电台适配器
 */
public class MusicListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<RadioListItem> mList;
	private Context context;
	private ImageLoader imageLoader=ImageLoader.getInstance();
	public MusicListAdapter(Context context) {
		this.context=context;
		inflater = LayoutInflater.from(context);
	}
	
	public void addData(List<RadioListItem> list){
		if(mList==null){
			mList=list;
		}else {
			mList.addAll(list);
		}
		this.notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View contentView, ViewGroup parent) {
		  final ViewHolder viewHolder;
		  int type = getItemViewType(position);
		  RadioListItem radioListItem=(RadioListItem) getItem(position);
	        if (contentView == null){
	            viewHolder = new ViewHolder();
	            if(type==0){
	            	contentView = inflater.inflate(R.layout.radio_list_top_view, null);
	            }else{
	            	contentView = inflater.inflate(R.layout.item_radiostation_list, null);	 
	            }	         
	            viewHolder.cover_iv= (ImageView) contentView .findViewById(R.id.cover_iv);
	            viewHolder.cover_bg_iv=(ImageView) contentView.findViewById(R.id.cover_bg_iv);
	            viewHolder.radio_list_title_tv=(TextView) contentView.findViewById(R.id.radio_list_title_tv);
	            viewHolder.radio_listen_num_tv=(TextView) contentView.findViewById(R.id.radio_listen_num_tv);
	            viewHolder.favour_tv=(TextView) contentView.findViewById(R.id.favour_tv);
	            viewHolder.create_time_tv=(TextView) contentView.findViewById(R.id.create_time_tv);
	            viewHolder.blur_view=(FrameLayout) contentView.findViewById(R.id.blur_view);
	            contentView.setTag(viewHolder);
	        }
	        else {
	            viewHolder = (ViewHolder) contentView.getTag();
	        }
            viewHolder.radio_list_title_tv.setText(radioListItem.getProgramTitle());
            viewHolder.radio_listen_num_tv.setText(radioListItem.getListenNum()+"人");
            viewHolder.favour_tv.setText(radioListItem.getPraiseNum()+"人");
            viewHolder.create_time_tv.setText(radioListItem.getCreateTime().split(" ")[0]);
            if(!radioListItem.getPictureFileId().equals(viewHolder.cover_iv.getTag().toString())){
            	viewHolder.cover_iv.setTag(radioListItem.getPictureFileId());
            	imageLoader.displayImage(radioListItem.getProgramType(), viewHolder.cover_iv,ImageLoadOptions.getPopRadioOptions());    	
            }
            if(viewHolder.cover_bg_iv!=null){
            	if(!radioListItem.getPictureFileId().equals(viewHolder.cover_bg_iv.getTag().toString())){
            		viewHolder.cover_bg_iv.setTag(radioListItem.getPictureFileId());
            		imageLoader.displayImage(radioListItem.getProgramType(), viewHolder.cover_bg_iv,ImageLoadOptions.getRadioListBlurOptions(context));          		
            	}
            }
	        return contentView;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if(position==0){
			return 0;
		}else{
			return 1;
		}
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getCount() {
		 return mList==null?0:mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		FrameLayout blur_view;
		ImageView cover_bg_iv;
		ImageView cover_iv;
		TextView radio_list_title_tv;
		TextView radio_listen_num_tv;
		TextView favour_tv;
		TextView create_time_tv;
	}
	
	public RadioListItem getRadioItem(int position){
		return mList.get(position);
	}
	
	public void refresh(RadioListItem radioListItem){
		int size=mList.size();
		for (int i = 0; i <size; i++) {
			if(radioListItem.getPid().equals(mList.get(i).getPid())){
				mList.set(i, radioListItem);
				break;
			}
		}
		this.notifyDataSetChanged();
	}
	
	public int count(){
		return mList==null?0:mList.size();
	}
}
