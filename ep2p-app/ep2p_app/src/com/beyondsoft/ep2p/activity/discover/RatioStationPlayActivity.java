package com.beyondsoft.ep2p.activity.discover;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.database.RadioDao;
import com.beyondsoft.ep2p.model.response.RatioStationListResponse;
import com.beyondsoft.ep2p.model.response.RatioStationListResponse.Result.RadioListItem;
import com.beyondsoft.ep2p.model.response.ThumUp;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.ImageLoadOptions;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.Player;
import com.beyondsoft.ep2p.utils.Player.onMusicProgressListener;
import com.beyondsoft.ep2p.utils.Player.onMusicTimeListener;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.view.LongTimePopupWindow;
import com.beyondsoft.ep2p.view.SharePopupWindow;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * @author Ivan.Lu
 * @description 电台播放界面
 */
public class RatioStationPlayActivity extends BaseFragmentActivity  implements OnClickListener,OnCheckedChangeListener,OnSeekBarChangeListener
{
	private static final int THUMB_UP=1;
	private static final int NEXT=2;
	private static final int PRE=3;
	private static final int LISTENTIME=4;
	private static final int LISTENTADD=5;
	private static final int LISTENTADDPOINT=6;
	private SeekBar play_seekBar;
	private TextView start_time_tv;
	private TextView end_time_tv;
	private TextView radio_title_tv;
	private TextView radio_date_tv;
	private TextView listener_tv;
	private ImageView iv_right_falg;
	private CheckBox pause_button;//播放
	private TextView thumb_up_tv;
	private ImageView pre_button;//上一首
	private ImageView next_button;//下一首
	private ImageView conver_bg_iv;
	private ImageView blur_conver_bg_iv;
	private RelativeLayout thumb_up_layout;
	private ImageLoader imageLoader=ImageLoader.getInstance();
	private Player mPlayer;
	private RadioDao mRadioDao;
	private RadioListItem mRadioListItem;
	private SecurityCenterService securityCenterService;
	/**防止连续切换节目**/
	private boolean isLoading;
	private LongTimePopupWindow longTimePopupWindow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ratio_station_play);
		mRadioDao=new RadioDao();
		mRadioListItem=(RadioListItem) getIntent().getSerializableExtra("radioInfo");
		securityCenterService=new SecurityCenterService();
		mPlayer=Player.getInstance(this);
		mPlayer.setHandle(mHandler);
		init();
		initListener();
		initData();
	}

	private void init() {
		((RelativeLayout)findViewById(R.id.title_layout)).setBackgroundColor(getResources().getColor(R.color.transparent));
		conver_bg_iv=(ImageView) findViewById(R.id.conver_bg_iv);
		blur_conver_bg_iv=(ImageView) findViewById(R.id.blur_conver_bg_iv);
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setVisibility(View.GONE);
		//iv_right_falg.setImageResource(R.drawable.discover_w_fenxiang);
		play_seekBar=(SeekBar) findViewById(R.id.play_seekBar);
		radio_title_tv=(TextView) findViewById(R.id.radio_title_tv);
		radio_date_tv=(TextView) findViewById(R.id.radio_date_tv);
		pause_button=(CheckBox) findViewById(R.id.pause_button);
		pre_button=(ImageView) findViewById(R.id.pre_button);
		next_button=(ImageView) findViewById(R.id.next_button);
		thumb_up_tv=(TextView) findViewById(R.id.thumb_up_tv);
		listener_tv=(TextView) findViewById(R.id.listener_tv);
		thumb_up_layout=(RelativeLayout) findViewById(R.id.thumb_up_layout);
		start_time_tv=(TextView) findViewById(R.id.start_time_tv);
		end_time_tv=(TextView) findViewById(R.id.end_time_tv);
	}

	private void initListener() {
		pre_button.setOnClickListener(this);
		next_button.setOnClickListener(this);
		//iv_right_falg.setOnClickListener(this);
		thumb_up_layout.setOnClickListener(this);
		pause_button.setOnCheckedChangeListener(this);
		play_seekBar.setOnSeekBarChangeListener(this);
		mPlayer.setOnMusicTimeListener(new onMusicTimeListener() {
			
			@Override
			public void onMusicTime(String musicLength,int musicProgressLength) {
				// TODO Auto-generated method stub
				end_time_tv.setText(musicLength);
				play_seekBar.setMax(musicProgressLength);
			}
		});
		mPlayer.setOnMusicProgressListener(new onMusicProgressListener() {
			
			@Override
			public void onMusicProgress(int progress) {
				// TODO Auto-generated method stub
				play_seekBar.setProgress(progress);
				mHandler.obtainMessage(0, progress/1000, 0).sendToTarget();
			}
		});
	}
	
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				int progress=msg.arg1;
				start_time_tv.setText(StringUtils.getMusicTime(progress));
				break;
			case 1:
				isLoading=false;
				CommonUtils.toastMsgShort(RatioStationPlayActivity.this,"播放错误!");
				start_time_tv.setText("00:00");
				end_time_tv.setText("00:00");
				break;
			case 2:
				pause_button.setChecked(false);
				break;
			case 3:
				isLoading=false;
				sendBroadcast(new Intent("com.beyondsoft.ep2p.startplay"));
				mRadioDao.saveRadioInfo(gson.toJson(mRadioListItem),RatioStationPlayActivity.this);
				HashMap<String,Object> hashMap=new HashMap<String, Object>();
				hashMap.put("radioId", mRadioListItem.getPid());
				connection(securityCenterService.addListenNum(LISTENTADD, hashMap, RatioStationPlayActivity.this));
				break;
			case 4:
				//达到收听时长
				Logs.d("达到收听时长");
				connection(securityCenterService.listenAddPoint(LISTENTADDPOINT, null, RatioStationPlayActivity.this));
				break;
			}
		}
	};
	
	private void initData(){
		if(!"".equals(CommonUtils.getStringByKey(this, Constants.EP2P_TOKEN))){
			connection(securityCenterService.listenTime(LISTENTIME,null, this));   
        }
		if(mRadioListItem==null){
			mRadioListItem=mRadioDao.getRadioInfo(this);
			}
		imageLoader.displayImage(mRadioListItem.getProgramType(),conver_bg_iv,ImageLoadOptions.getPlayDetailTopOptions());
		imageLoader.displayImage(mRadioListItem.getProgramType(),blur_conver_bg_iv,ImageLoadOptions.getRadioPlayBlurOptions(this));
		radio_title_tv.setText(mRadioListItem.getProgramTitle());
		radio_date_tv.setText(mRadioListItem.getCreateTime());
		listener_tv.setText(mRadioListItem.getListenNum()+"人");
		thumb_up_tv.setText(mRadioListItem.getPraiseNum()+"人");	
		if(mPlayer.isPlaying()){
			if(!mPlayer.getPlayUrl().equals(mRadioListItem.getPictureFileId())){
				mPlayer.setPlayUrl(mRadioListItem.getPictureFileId());
			}else{
				pause_button.setChecked(true);
				reStorePlayState();
			}
		}else{
			if(!mRadioListItem.getPictureFileId().equals(mPlayer.getPlayUrl())){
				mPlayer.setPlayUrl(mRadioListItem.getPictureFileId());
			}else{
				pause_button.setChecked(false);
				reStorePlayState();
			}
		}
	}
	
	private void reStorePlayState(){
		start_time_tv.setText(mPlayer.getStartTimeHint());
		end_time_tv.setText(mPlayer.getEndTimeHint());
		play_seekBar.setMax(mPlayer.getDuration());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.thumb_up_layout:
			HashMap<String,Object> hashMap=new HashMap<String, Object>();
			hashMap.put("radioId", mRadioListItem.getPid());
			connection(new SecurityCenterService().radioPraiseNum(THUMB_UP, hashMap, this));
			thumb_up_layout.setBackgroundColor(getResources().getColor(R.color.e_text_bg_huise_black));
			break;
			
		case R.id.title_right:
			SharePopupWindow sharePopupWindowLine=new SharePopupWindow(mContext);
			sharePopupWindowLine.showWindow();
			sharePopupWindowLine.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
				}
			});
			sharePopupWindowLine.showAtLocation(findViewById(R.id.title_right),Gravity.BOTTOM,0,0);
			break;
			
		case R.id.next_button:
			if(mRadioListItem!=null&&!isLoading){
				isLoading=true;
				HashMap<String,String> nextHashMap=new HashMap<String, String>();
				nextHashMap.put("radioId", mRadioListItem.getPid());
				nextHashMap.put("radioFlag", "2");
				connection(securityCenterService.nextRadion(NEXT, nextHashMap, this));
			}
			break;
		case R.id.pre_button:
			if(mRadioListItem!=null&&!isLoading){
				isLoading=true;
				HashMap<String,String> preHashMap=new HashMap<String, String>();
				preHashMap.put("radioId", mRadioListItem.getPid());
				preHashMap.put("radioFlag","1");
				connection(securityCenterService.nextRadion(PRE, preHashMap, this));
			}
			break;
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		switch (tag) {
		case THUMB_UP:
//			ThumbUpPopupWindow thumb_up = new ThumbUpPopupWindow(RatioStationPlayActivity.this);
//			thumb_up.showWindow();
//			thumb_up.setOnDismissListener(new OnDismissListener() {
//				@Override
//				public void onDismiss() {
//					thumb_up_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(
//							R.drawable.play_station_thump_up_icon, 0, 0, 0);
//				}
//			});
//			thumb_up.showAtLocation(findViewById(R.id.title_content_tv),Gravity.TOP, 0, 0);
			ThumUp thumUp=gson.fromJson(values.toString(),ThumUp.class);
			thumb_up_tv.setText(thumUp.getResult().getPraiseNum()+"人");
			mRadioListItem.setPraiseNum(thumUp.getResult().getPraiseNum());
			mRadioDao.saveRadioInfo(gson.toJson(mRadioListItem),RatioStationPlayActivity.this);
			setResult(Activity.RESULT_OK, new Intent().putExtra("item", mRadioListItem));
			break;
			
		case NEXT:
		case PRE:
			RatioStationListResponse ratioStationListResponse=gson.fromJson(values.toString(), RatioStationListResponse.class);
			if(ratioStationListResponse.getResult().getList()!=null){
				mRadioListItem=ratioStationListResponse.getResult().getList().get(0);
				initData();
			}else{
				CommonUtils.toastMsgShort(this,"已经没有更多节目收听!");
				isLoading=false;
			}
			break;
		case LISTENTIME:
			try {
				JSONObject jsonObject=new JSONObject(values.toString());		
				int time=Integer.parseInt(jsonObject.getJSONObject("result").get("systemListenTime").toString());
				CommonUtils.addConfigInfo(this,Constants.PLAYLISTENTIME, time*60);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case LISTENTADD:
			try {
				JSONObject jsonObject=new JSONObject(values.toString());		
				int count=Integer.parseInt(jsonObject.getJSONObject("result").get("listenNum").toString());
				listener_tv.setText(count+"人");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case LISTENTADDPOINT:
			try {
				JSONObject jsonObject=new JSONObject(values.toString());
				int score=Integer.parseInt(jsonObject.getJSONObject("result").get("pointValue").toString());
				if(score>0){
					int time=CommonUtils.getIntByKey3(this,Constants.PLAYLISTENTIME)/60;
					int num=CommonUtils.getIntByKey(this,Constants.PLAYCURRENTLISTENNUM);
					if(num<0){
						num=time;
						CommonUtils.addConfigInfo(this,Constants.PLAYCURRENTLISTENNUM,num);
					}else{
						num=num+time;
						CommonUtils.addConfigInfo(this,Constants.PLAYCURRENTLISTENNUM,num);
					}
					if(!CommonUtils.getBooleanByKey(mContext, Constants.PLAYDIALOG)){
						  if(longTimePopupWindow==null){
						    	longTimePopupWindow = new LongTimePopupWindow(RatioStationPlayActivity.this,num,score);
								longTimePopupWindow.showWindow();
								longTimePopupWindow.setOnDismissListener(new OnDismissListener() {
									@Override
									public void onDismiss() {
										longTimePopupWindow=null;
									}
								});
								longTimePopupWindow.showAtLocation(findViewById(R.id.title_content_tv),Gravity.TOP, 0, 0);
						    }else{
						    	longTimePopupWindow.updateData(num, score);
						    }
					}
				}else{
					Logs.e("收听达到上限,不加分!");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
		if(tag==NEXT||tag==PRE){
			isLoading=false;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(isChecked){
			mPlayer.play();
		}else{
			mPlayer.pause();
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		int currentProgress=progress/1000;
		start_time_tv.setText(StringUtils.getMusicTime(currentProgress));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		mPlayer.setTracking(true);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		mPlayer.seekTo(seekBar.getProgress());
		mPlayer.setTracking(false);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("radioInfo", mRadioListItem);
	}
}
