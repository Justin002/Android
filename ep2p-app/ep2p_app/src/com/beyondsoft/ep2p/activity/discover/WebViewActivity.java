package com.beyondsoft.ep2p.activity.discover;

import java.text.BreakIterator;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.view.RefreshDialog;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends BaseActivity {

	private WebView videoPlay;
	private TextView title_content_tv;
	private String title;
	private String bbs_url_result;
	private String bbs_url;
	private boolean stop=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_detail_play);
		initComp();
	}
	protected void initComp() {
		videoPlay = (WebView)findViewById(R.id.activity_play_video_web);
		title_content_tv=(TextView) findViewById(R.id.title_content_tv);
		 title = getIntent().getStringExtra("title");
		 bbs_url = getIntent().getStringExtra("bbs_url");
		 bbs_url_result = getIntent().getStringExtra("bbs_url_result");
		title_content_tv.setText(title);
		WebSettings st = videoPlay.getSettings();
		st.setJavaScriptEnabled(true);
		videoPlay.loadUrl(bbs_url_result);
		RefreshDialog.startProgressDialog(mContext, "");
		videoPlay.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
	         }
	
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				if(!stop){
					view.loadUrl(bbs_url);
					RefreshDialog.stopProgressDialog();
					stop=true;
				}else{
					view.loadUrl(null);
				}
				
			}
		});

		
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		videoPlay.stopLoading();
		videoPlay.clearCache(true);
		videoPlay.clearHistory();
		videoPlay.clearFormData();
		videoPlay.destroyDrawingCache();
		videoPlay.destroy();

	}

}
