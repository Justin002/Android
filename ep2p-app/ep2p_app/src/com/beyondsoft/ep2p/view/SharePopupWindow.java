package com.beyondsoft.ep2p.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.beyondsoft.ep2p.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMusic;

public class SharePopupWindow extends PopupWindow implements OnClickListener {

	private Context context;
	private UMImage image;
	private UMusic music;
 
	public SharePopupWindow(Context cx) {
		this.context = cx;
		image = new UMImage(context, "http://www.umeng.com/images/pic/social/integrated_3.png");
		music = new UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
	    music.setTitle("sdasdasd");
	    music.setThumb(new UMImage(context, "http://www.umeng.com/images/pic/social/chart_1.png"));
	}

	public void showWindow() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.show_pop_window, null);
		((TextView) view.findViewById(R.id.share_weixin_iv))
				.setOnClickListener(this);
		((TextView) view.findViewById(R.id.share_qq_iv))
				.setOnClickListener(this);
		((TextView) view.findViewById(R.id.share_sina_iv))
				.setOnClickListener(this);
		((TextView)view.findViewById(R.id.share_weifriend_iv)).setOnClickListener(this);
		// 设置SelectPicPopupWindow的View
		this.setContentView(view);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xe52980b9);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
	}
	
//	public PlatformActionListener getPlatformActionListener() {
//		return platformActionListener;
//	}
//
//	public void setPlatformActionListener(
//			PlatformActionListener platformActionListener) {
//		this.platformActionListener = platformActionListener;
//	}
//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.share_weixin_iv:
			weixin();
			break;
		case R.id.share_qq_iv:
			qq();
			break;
		case R.id.share_sina_iv:
			sina();
			break;
		case R.id.share_weifriend_iv:
			weixin_fridend();
			break;
		}
		this.dismiss();
	}

	private void qq() {
		 new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
         .withText("hello umeng")
         .withMedia(music)
         .withTitle("qqshare")
         .withTargetUrl("http://dev.umeng.com")
         .share();
	}
	
	private void weixin(){
		music.setTargetUrl("http://www.baidu.com");
        new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN)
        .setCallback(umShareListener)
        .withMedia(image)
        .withText("hello umeng")
        .share();
	}
	
	private void weixin_fridend(){
		 new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
         .withText("hello umeng")
         .withMedia(music)
         .share();
	}
	
	private void sina(){
		new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
        .withText("hello umeng video")
        .withMedia(image)
        .share();
	}
	
	UMShareListener umShareListener=new UMShareListener() {
		
		@Override
		public void onResult(SHARE_MEDIA platform) {
			// TODO Auto-generated method stub
			Toast.makeText(context,platform + "分享成功", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onError(SHARE_MEDIA platform, Throwable arg1) {
			// TODO Auto-generated method stub
			Toast.makeText(context,platform + "分享失败", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onCancel(SHARE_MEDIA platform) {
			// TODO Auto-generated method stub
			//Toast.makeText(context,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
		}
	};
}
