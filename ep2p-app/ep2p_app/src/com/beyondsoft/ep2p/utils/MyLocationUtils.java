package com.beyondsoft.ep2p.utils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;


import android.content.Context;

public class MyLocationUtils {
	
	 private String tempcoor = "bd09ll";

	    private LocationClient mLocationClient;

	    private BDLocationListener mBaiduLocationListener;

	    private LocationMode tempMode = LocationMode.Hight_Accuracy;

	    private MyLocationListener mLocationListener;

	    public MyLocationUtils(Context context) {
	        mLocationClient = new LocationClient(context);
	        mBaiduLocationListener = new BaiduLocationListener();
	        mLocationClient.registerLocationListener(mBaiduLocationListener);
	        initLocation();
	    }

	    public MyLocationUtils(Context context, BDLocationListener mBDLocationListener) {
	        mLocationClient = new LocationClient(context);
	        mBaiduLocationListener = mBDLocationListener;
	        mLocationClient.registerLocationListener(mBaiduLocationListener);
	        initLocation();
	    }

	    /**
	     * 实现实位回调监听
	     */
	    public class BaiduLocationListener implements BDLocationListener {

	        @Override
	        public void onReceiveLocation(BDLocation location) {
	            Logs.d("" + location.getLocType());
	            if (mLocationListener != null) {
	                if (location.getCity() != null) {
	                    mLocationListener.onReceiveLocation(location.getCity());
	                }

	            }
	        }
	    }

	    /**
	     * 设置定位回调监听
	     */
	    public void setLocationListener(MyLocationListener locationListener) {
	        mLocationListener = locationListener;
	    }

	    public interface MyLocationListener {
	        public void onReceiveLocation(String city);
	    }

	    private void initLocation() {
	        LocationClientOption option = new LocationClientOption();
	        option.setLocationMode(tempMode);// 设置定位模式
	        option.setCoorType(tempcoor);// 返回的定位结果是百度经纬度，默认值gcj02
	        option.setScanSpan(1000);// 设置发起定位请求的间隔时间为1000ms
	        option.setIsNeedAddress(true);
	        mLocationClient.setLocOption(option);
	    }

	    public void startLoaction() {
	        mLocationClient.start();
	    }

	    /**
	     * 停止定位
	     */
	    public void stopLocation() {
	        mLocationClient.stop();
	    }
}
