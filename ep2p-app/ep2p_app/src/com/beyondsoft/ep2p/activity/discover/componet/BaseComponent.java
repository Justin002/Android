package com.beyondsoft.ep2p.activity.discover.componet;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.beyondsoft.ep2p.BaseActivity;

public abstract class BaseComponent {
	protected BaseActivity activity;
	protected View view;
	private ViewGroup root;
	private View _view;

	public BaseComponent(BaseActivity activity, int resId) {
		this.activity = activity;
		view = activity.getLayoutInflater().inflate(onCreate(), null);
		this._view = view;
		root = (ViewGroup) activity.findViewById(resId);
		root.addView(view);
		initComp();
		initListener();
	}

	public BaseComponent(BaseActivity activity, View v) {
		if (v instanceof ViewGroup) {
			this.activity = activity;
			view = activity.getLayoutInflater().inflate(onCreate(), null);
			this._view = view;
			root = (ViewGroup) v;
			root.addView(view);
			initComp();
			initListener();
			initData();
		}
	}

	public BaseComponent(BaseActivity activity) {
		this.activity = activity;
		view = activity.getLayoutInflater().inflate(onCreate(), null);
		this._view = view;
		_view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		initComp();
		initListener();
		initData();
	}

	public abstract int onCreate();

	public abstract void initComp();

	public abstract void initListener();

	public abstract void initData();

	public View findViewById(int id) {
		return view.findViewById(id);
	}

	public ViewGroup getRoot() {
		return root;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public boolean isDisplay() {
		return isDisplay;
	}

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public View getView() {
		return _view;
	}

	private int offset = -1;
	private boolean isDisplay = false;

}
