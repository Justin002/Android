package com.beyondsoft.ep2p.activity;

import java.util.Random;

import com.beyondsoft.ep2p.BaseWebActivity;
import com.beyondsoft.ep2p.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 简单web浏览页面
 * 
 * @author hardy.zhou
 *
 */
public class SimpleWebActivity extends BaseWebActivity implements OnClickListener {

	public static final String TITLE = "title";
	public static final String URL = "url";

	private ImageView iv_back;
	private TextView tv_title;

	private String mTitle;
	private String mUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		iv_back = (ImageView) findViewById(R.id.title_left_btn);
		iv_back.setVisibility(View.VISIBLE);
		iv_back.setOnClickListener(this);

		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(mTitle);

		mWebView.loadUrl(mUrl);
		mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		Intent intent = getIntent();
		mTitle = intent.getStringExtra(TITLE);
		mUrl = intent.getStringExtra(URL);
		if (mUrl.indexOf("?") > 0) {
			mUrl = mUrl + "&random=" + new Random().nextInt(100000);
		} else {
			mUrl = mUrl + "?random=" + new Random().nextInt(100000);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn: {
			onBackPressed();
		}
			break;
		default:
			break;
		}

	}
}
