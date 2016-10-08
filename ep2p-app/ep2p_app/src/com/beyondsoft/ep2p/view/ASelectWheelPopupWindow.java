package com.beyondsoft.ep2p.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.view.wheel.MyWheelViewAdapter1;
import com.beyondsoft.ep2p.view.wheel.OnWheelChangedListener;
import com.beyondsoft.ep2p.view.wheel.WheelView;

public class ASelectWheelPopupWindow extends PopupWindow implements
		OnClickListener, OnWheelChangedListener {
	public interface OnComfirmListener {
		public void onArticleSelected(String ms);
	}

	private Context context;

	/*
	 * private ShareParams shareParams; private PlatformActionListener
	 * platformActionListener;
	 */
	public ASelectWheelPopupWindow(Context cx, ArrayList<String> ds,
			OnComfirmListener mlistener,String title) {
		this.context = cx;
		this.provincelist = ds;
		this.mlistener = mlistener;
		this.title = title;
	}
	
	public ASelectWheelPopupWindow(Context cx, ArrayList<String> ds,
			OnComfirmListener mlistener,String title,String selectValue) {
		this.context = cx;
		this.provincelist = ds;
		this.mlistener = mlistener;
		this.title = title;
		mSelectValue=selectValue;
	}

	private WheelView mViewProvince;
	private TextView cancel_tv, done_tv,_title;

	private MyWheelViewAdapter1 mAdapter1;
	private ArrayList<String> provincelist = new ArrayList<String>();
	private String mGetString;
	private OnComfirmListener mlistener;
	private String title;
	private String mSelectValue;

	public void showWindow() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.discover_w_wheel, null);
		// 设置SelectPicPopupWindow的View
		this.setContentView(view);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		this.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		this.setFocusable(true);

		mViewProvince = (WheelView) view
				.findViewById(R.id.fragment_search_by_grade_whileview);

		cancel_tv = (TextView) view
				.findViewById(R.id.fragment_search_by_grade_cancel);
		done_tv = (TextView) view
				.findViewById(R.id.fragment_search_by_grade_ok);
		_title	= (TextView) view
				.findViewById(R.id.fragment_search_title);
		_title.setText(title);
		cancel_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ASelectWheelPopupWindow.this.dismiss();
			}
		});

		done_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mlistener.onArticleSelected(mGetString);
				ASelectWheelPopupWindow.this.dismiss();
			}
		});

		// 添加change事件
		mViewProvince.addChangingListener(this);
		// 设置可见条目数量
		mViewProvince.setVisibleItems(5);
		// mViewProvince.setWheelBackground(R.drawable.rec_wheelview_bg);
		// mViewProvince.setWheelForeground(R.drawable.rec_wheelview_fg);
		mAdapter1 = new MyWheelViewAdapter1((Activity) context, provincelist);
		mViewProvince.setViewAdapter(mAdapter1);
		if(mSelectValue!=null){
			for (int i = 0; i < provincelist.size(); i++) {
				if(mSelectValue.equals(provincelist.get(i))){
					mViewProvince.setCurrentItem(i);
					mGetString =(String) provincelist.get(i);
					break;
				}
			}
		}else{
			mViewProvince.setCurrentItem(0);
			mGetString =(String) provincelist.get(0);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			System.out.println("eee");
			int pCurrent = mViewProvince.getCurrentItem();
			mGetString = (String) provincelist.get(pCurrent);
		}
	}
}
