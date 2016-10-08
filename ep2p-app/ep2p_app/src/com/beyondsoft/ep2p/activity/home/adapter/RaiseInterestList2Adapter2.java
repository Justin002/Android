package com.beyondsoft.ep2p.activity.home.adapter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.RaiseInterestListResponse.RaiseInterestListItem;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


public class RaiseInterestList2Adapter2 extends BaseAdapter{
	
	private Context mContext;
	private List<RaiseInterestListItem> titles;
	private LayoutInflater mInflater;
	
	private HashMap<Integer,Boolean> isSelected;
	
	private OnCheckBoxCheckedLsitener onCheckBoxCheckedLsitener;
	 private String bl_falg = "#,##0.00";
//	private int mPosition =0;
	private ViewHolder holder;
	
	@SuppressLint("UseSparseArrays")
	public RaiseInterestList2Adapter2(Context mContext, List<RaiseInterestListItem> titles) {
		super();
		this.mContext = mContext;
		this.titles = titles;
		mInflater=LayoutInflater.from(mContext);
		
		isSelected=new HashMap<Integer, Boolean>();
		initData();
	}

	public void initData(){
	
		for (int i = 0; i < titles.size(); i++) {
			isSelected.put(i, false);
		}
	}
	
	@Override
	public int getCount() {
		return titles.size();
	}

	@Override
	public Object getItem(int position) {
		return titles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final int mPosition=position;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.layout_raise_interest_item,null);
			
			holder.tv_image_ui = (ImageView) convertView
                    .findViewById(R.id.tv_image_ui);
			holder.tv_experience = (TextView) convertView
                    .findViewById(R.id.tv_experience);
			holder.tv_only_experience = (TextView) convertView
                    .findViewById(R.id.tv_only_experience);
			holder.tv_experience_date = (TextView) convertView
                    .findViewById(R.id.tv_experience_date);
			holder.cb=(CheckBox)convertView.findViewById(R.id.bn_experience_sy);
			
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		
		RaiseInterestListItem ListItem = (RaiseInterestListItem) getItem(position);

        if (StringUtils.isTestNull(ListItem.cardQuota + "").equals(""))
        {
            holder.tv_experience.setText("+0.00%");
        }
        else
        {
            Double mNhllProgress = ListItem.cardQuota * 100;//目标进度
            BigDecimal bd = new BigDecimal(mNhllProgress);
            mNhllProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//取2位小数[取值条件：四舍五入]
            holder.tv_experience.setText("+"
                + new DecimalFormat(bl_falg).format(mNhllProgress) + "%");//保留后面两位，不足用0代替
        }

        holder.tv_experience_date.setText("还有"
            + StringUtils.isTestNull(ListItem.cardValidity + "") + "天到期");
		
		holder.cb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RaiseInterestList2Adapter2.this.onCheckBoxCheckedLsitener.getChecked(mPosition);
			
				for (Integer p : isSelected.keySet()) {
					isSelected.put(p, false);
				}
				isSelected.put(mPosition,!holder.cb.isChecked());
				Logs.d("选中状态:"+holder.cb.isChecked());
				if((position+1 -getCount())==0){
				    Logs.d("最后一个选中状态:"+holder.cb.isChecked());
				    isSelected.put(mPosition,holder.cb.isChecked());  
				}
				
				RaiseInterestList2Adapter2.this.notifyDataSetChanged();
			}
		});
		holder.cb.setChecked(isSelected.get(mPosition));
		return convertView;
	}
	
	public class ViewHolder{
	    ImageView tv_image_ui;
        TextView tv_experience;
        TextView tv_only_experience;//体验金使用说明
        TextView tv_experience_date; //有效时间
		CheckBox cb;
	}
	
	public interface OnCheckBoxCheckedLsitener{
		void getChecked(int position);
	}
	public void setOnCheckBoxCheckedLsitener(OnCheckBoxCheckedLsitener onCheckBoxCheckedLsitener){
		this.onCheckBoxCheckedLsitener=onCheckBoxCheckedLsitener;
	}
	
    @Override  
    public boolean areAllItemsEnabled() {  
        return false;  
    }  
      
    @Override  
    public boolean isEnabled(int position) {  
        return false;  
    } 

}
