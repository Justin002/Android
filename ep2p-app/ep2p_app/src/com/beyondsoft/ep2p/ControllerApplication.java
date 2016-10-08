
package com.beyondsoft.ep2p;

import java.io.File;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.PlatformConfig;

/**
 * Description: 初始化网络和ImageLoader
 * 
 * @author Ivan.Lu
 * 
 */
public class ControllerApplication extends Application {
	private RequestQueue mRequestQueue;
	private long timeInBackground=Long.MIN_VALUE;;
	@Override
	public void onCreate() {
		super.onCreate();
		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
		initImageLoader(getApplicationContext());
		initUMShare();
	}

	private void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"ep2p/Cache");// 获取到缓存的目录地址
		// 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				// 线程池内加载的数量
				.threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				//.writeDebugLogs() // Remove for release app				
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// 全局初始化此配置
	}

	public RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		getRequestQueue().add(req);
	}

	/**
	 * Adds the specified request to the global queue using the Default TAG.
	 * 
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req) {

		getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important to
	 * specify a TAG so that the pending/ongoing requests can be cancelled.
	 * 
	 * @param tag
	 */
	public void cancelRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
	
	public void saveTimeInBackground()
	{
		if(timeInBackground < 0)
		{
			timeInBackground = System.currentTimeMillis();
		}
	}
	
	public void resetTimeInBackground()
	{
		timeInBackground = Long.MIN_VALUE;
	}
	
	public boolean isTimeOutInBackground()
	{
		return (timeInBackground > 0) && (System.currentTimeMillis() - timeInBackground > 10*60*1000);
//		return (timeInBackground > 0) && (System.currentTimeMillis() - timeInBackground > 5*1000);
	}
	
	public boolean isAppOnForeground()
	{
		ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = getApplicationContext().getPackageName();

		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null)
		{
			return false;
		}

		for (RunningAppProcessInfo appProcess : appProcesses)
		{
			if (appProcess.processName.equals(packageName) && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 初始化友盟分享	 
	 **/
	private void initUMShare(){
		PlatformConfig.setWeixin("wx37f67e537891e930","966c33b183799987114a64150ba6d0e5");
		PlatformConfig.setSinaWeibo("1573694845", "61877efa6bd3cae0f14538a784145ac1");
		PlatformConfig.setQQZone("1105121503", "p4NeQvMopF8bX7G1");
	}
}
