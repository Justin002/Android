package com.beyondsoft.ep2p.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Handler;

import com.beyondsoft.ep2p.common.Constants;

/**
 * @author Ivan.Lu
 * @description 播放音乐
 */
public class Player implements OnCompletionListener,OnPreparedListener,OnErrorListener,OnSeekCompleteListener,OnBufferingUpdateListener{
	private static Player mPlayer=null;
	private static Context mContext;
	private Handler mHandler;
	private MediaPlayer mediaPlayer;
	private onMusicTimeListener onListener;
	private boolean isTracking;
	private onMusicProgressListener musicProgressListener;
	/**记录播放进度**/
	private Timer timer;
	private Timer listenTime;
	private String mPlayUrl="";
	
	public static Player getInstance(Context context){
		mContext=context;
		if(mPlayer==null){
			mPlayer=new Player();
		}
		return mPlayer;
	}
	
	private Player(){
		initMediaPlayer();
	}
	
	private void initMediaPlayer(){
		mediaPlayer=new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnSeekCompleteListener(this);
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnErrorListener(this);
	}
	
	public void setPlayUrl(String musicUrl){
		try {
			cancelTimer();
			mPlayUrl=musicUrl;
			mediaPlayer.reset();
			mediaPlayer.setDataSource(musicUrl);
			mediaPlayer.prepareAsync();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setTracking(boolean tracking){
		isTracking=tracking;
	}
	
	/**
	 * 启动定时器
	 */
	private void startTimer(){
		if(timer==null){
			timer=new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(musicProgressListener!=null){
						if(isTracking){
							return;
						}
						musicProgressListener.onMusicProgress(mediaPlayer.getCurrentPosition());
					}
				}
			},0,50);
		}
		if(listenTime==null){
			listenTime=new Timer();
			listenTime.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(mediaPlayer.isPlaying()){
						if(!"".equals(CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN))){
							int time=CommonUtils.getIntByKey2(mContext,Constants.PLAYCURRENTLISTENTIME);
							if(time==CommonUtils.getIntByKey3(mContext, Constants.PLAYLISTENTIME)){
								mHandler.sendEmptyMessage(4);
								CommonUtils.deleteConfigInfo(mContext, Constants.PLAYCURRENTLISTENTIME);
								time=0;
							}
							Logs.d("收听时间:"+time);
							CommonUtils.addConfigInfo(mContext, Constants.PLAYCURRENTLISTENTIME, time+1);
				        }					
					}
				}
			}, 0,1000);
		}
	}
	
	/**
	 * 取消定时器
	 */
	private void cancelTimer(){
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if(listenTime!=null){
			listenTime.cancel();
			listenTime=null;
		}
	}
	
	public void play(){
		if(mediaPlayer!=null){
			if(!mediaPlayer.isPlaying()){
				startTimer();
				mediaPlayer.start();		
			}
		}
	}
	
	public void pause(){
		if(mediaPlayer!=null){
			if(mediaPlayer.isPlaying()){
				cancelTimer();
				mediaPlayer.pause();			
			}
		}
	}
	
	public void stop(){
		if(mediaPlayer!=null){
			mediaPlayer.stop();
		}
	}
	
	public void destory(){
		if(mediaPlayer!=null){
			cancelTimer();
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
	
	public String getPlayUrl(){
		return mPlayUrl;
	}
	
	public void setHandle(Handler handler){
		mHandler=handler;
	}
	
	public interface onMusicTimeListener{
		void onMusicTime(String musicLength,int musicProgressLength);
	}
	
	public interface onMusicProgressListener{
		void onMusicProgress(int progress);
	}
	
	public void setOnMusicTimeListener(onMusicTimeListener musicTimeListener){
		onListener=musicTimeListener;
	}
	
	public void setOnMusicProgressListener(onMusicProgressListener listener){
		musicProgressListener=listener;
	}
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Logs.e("Music播放完毕");
		cancelTimer();
		mHandler.sendEmptyMessage(2);
	}
	
	
	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		int musicTime=mp.getDuration()/1000;
		if(onListener!=null){
			onListener.onMusicTime(StringUtils.getMusicTime(musicTime),mp.getDuration());
		}
		mediaPlayer.start();
		Logs.d("Music准备好了");
		startTimer();
		mHandler.sendEmptyMessage(3);
	}
	
	public String getStartTimeHint(){
		if(mediaPlayer==null){
			return "00:00";
		}
		return StringUtils.getMusicTime(mediaPlayer.getCurrentPosition()/1000);
	}
	
	public String getEndTimeHint(){
		if(mediaPlayer==null){
			return "00:00";
		}
		return StringUtils.getMusicTime(mediaPlayer.getDuration()/1000);
	}
	
	public boolean isPlaying(){
		if(mediaPlayer==null){
			return false;
		}
		return mediaPlayer.isPlaying();
	}
	
	public int getDuration(){
		if(mediaPlayer==null){
			return 0;
		}
		return mediaPlayer.getDuration();
	}
	
	public void seekTo(int progress){
		if(mediaPlayer!=null){
			mediaPlayer.seekTo(progress);		
		}
	}
	
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		cancelTimer();
		mediaPlayer.reset();
		mHandler.sendEmptyMessage(1);
		return false;
	}
	
	@Override
	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Logs.d("onSeekComplete");
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		//Logs.e("onBufferingUpdate:"+percent);
	}

}
