package com.beyondsoft.ep2p.utils;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.PersonalInfoResponse;

/**
* @author Ivan.Lu
* @date 2012-9-17
* @version V1.0 
* @description 共用的工具
*/
public class CommonUtils
{
	private static SharedPreferences mShareConfig;
	private static Toast mToast=null;
	private static Toast mToastStyle=null;
	/**
	 * 判断网络是否畅通
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} 
		else 
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) 
			{
				for (int i = 0; i < info.length; i++) 
				{
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断是否有SD卡
	 * @return
	 */
	public static boolean avaiableSDCard(){  
		
        String status = Environment.getExternalStorageState();  
          
        if(status.equals(Environment.MEDIA_MOUNTED)){  
            return true;  
        }  
        else {  
            return false;  
        }  
    }
	
	/**
	 * 获取设备的ID号
	 * @return
	 */
	public static String getDeviceId(Context context)
	{
		TelephonyManager telephonyManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
	/**
	 * 获取UUID码
	 * @return
	 */
	public static String getRandomUUID()
	{
	    String uuidRaw = UUID.randomUUID().toString();
	    return uuidRaw.replaceAll("-", "");
	}
	
	/**
	 * 添加公共信息
	 * @param key
	 * @param value
	 */
	public static void addConfigInfo(Context context,String key,String value)
	{ 
		if(!StringUtils.isNull(key) && !StringUtils.isNull(value))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			Editor conEdit = mShareConfig.edit();
			conEdit.putString(key, value);
			conEdit.commit();
		}
	}
	
	/**
	 * 添加公共信息
	 * @param key
	 * @param value
	 */
	public static void addConfigInfo(Context context,String key,int value)
	{ 
		if(!StringUtils.isNull(key))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			Editor conEdit = mShareConfig.edit();
			conEdit.putInt(key, value);
			conEdit.commit();
		}
	}
	
	/**
	 * 添加公共信息
	 * @param key
	 * @param value
	 */
	public static void addConfigInfo(Context context,String key,boolean value)
	{ 
		if(!StringUtils.isNull(key))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			Editor conEdit = mShareConfig.edit();
			conEdit.putBoolean(key, value);
			conEdit.commit();
		}
	}
	/**
	 * 添加公共信息
	 * @param key
	 * @param value
	 */
	public static void addConfigInfo(Context context,String key,long value)
	{ 
		if(!StringUtils.isNull(key))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			Editor conEdit = mShareConfig.edit();
			conEdit.putLong(key, value);
			conEdit.commit();
		}
	}
	/**
	 * 删除公共信息
	 * @param key
	 * @param value
	 */
	public static void deleteConfigInfo(Context context,String key)
	{ 
		if(!StringUtils.isNull(key))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			Editor conEdit = mShareConfig.edit();
			conEdit.remove(key);
			conEdit.commit();
		}
	}
	
	/**
	 * 根据key得到信息
	 * @param key
	 * @param value
	 */
	public static String getStringByKey(Context context,String key)
	{ 
		String value = null;
		if(!StringUtils.isNull(key))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			if(null != mShareConfig){
				value = mShareConfig.getString(key, "");
			}
		}
		return value;
	}
	
	/**
	 * 根据key得到信息
	 * @param key
	 * @param value
	 */
	public static String getStringByKey22(Context context,String key)
	{ 
		String value = null;
		if(!StringUtils.isNull(key))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			if(null != mShareConfig){
				value = mShareConfig.getString(key, "http://183.15.245.57:8081/api");
			}
		}
		return value;
	}
	
	/**
	 * 根据key得到信息
	 * @param key
	 * @param value
	 */
	public static int getIntByKey(Context context,String key)
	{ 
		int value=0;
		if(!StringUtils.isNull(key))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			if(null != mShareConfig){
				value = mShareConfig.getInt(key,-1);
			}
		}
		return value;
	}
	
	/**
	 * 根据key得到信息
	 * @param key
	 * @param value
	 */
	public static int getIntByKey2(Context context,String key)
	{ 
		int value=0;
		if(!StringUtils.isNull(key))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			if(null != mShareConfig){
				value = mShareConfig.getInt(key,0);
			}
		}
		return value;
	}
	
	/**
	 * 根据key得到信息
	 * @param key
	 * @param value
	 */
	public static int getIntByKey3(Context context,String key)
	{ 
		int value=0;
		if(!StringUtils.isNull(key))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			if(null != mShareConfig){
				value = mShareConfig.getInt(key,300);
			}
		}
		return value;
	}
	
	/**
	 * 根据key得到信息
	 * @param key
	 * @param value
	 */
	public static long getLongByKey(Context context,String key)
	{ 
		long value=0;
		if(!StringUtils.isNull(key))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			if(null != mShareConfig){
				value = mShareConfig.getLong(key,0);
			}
		}
		return value;
	}
	
	/**
	 * 根据key得到信息
	 * @param key
	 * @param value
	 */
	public static boolean getBooleanByKey(Context context,String key)
	{ 
		boolean value = false;
		if(!StringUtils.isNull(key))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			if(null != mShareConfig){
				value = ((Boolean)mShareConfig.getBoolean(key, false)).booleanValue();
			}
		}
		return value;
	}
	
	/**
	 * 根据key得到信息
	 * @param key
	 * @param value
	 */
	public static boolean getBooleanByKeyRem(Context context,String key)
	{ 
		boolean value = false;
		if(!StringUtils.isNull(key))
		{
			mShareConfig = context.getSharedPreferences(Constants.EP2P, Context.MODE_PRIVATE);
			if(null != mShareConfig){
				value = ((Boolean)mShareConfig.getBoolean(key, true)).booleanValue();
			}
		}
		return value;
	}
	
	/**
	 * 弹出消息提示框
	 * @param context
	 * @param msg
	 * @param gravity
	 */
	public static void toastMsg(Context context, String msg, int gravity) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(gravity, 0, 0);
		toast.show();
	}

	
	/** 弹出消息提示框
	 * @param context
	 * @param msg
	 */
	public static void toastMsgShort(Context context,String msg){
		if(mToast==null){
			mToast=Toast.makeText(context,msg, Toast.LENGTH_SHORT);
		}
		else{
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
	
	/** 弹出消息提示框
	 * @param context
	 * @param msg
	 */
	public static void toastMsgLong(Context context,String msg){
		if(mToast==null){
			mToast=Toast.makeText(context,msg, Toast.LENGTH_LONG);
		}
		else{
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_LONG);
		}
		mToast.show();
	}
	
	/** 弹出消息提示框
	 * @param context
	 * @param msg
	 */
	public static void toastMsgShortForStyle(Context context,String msg){
		View view=LayoutInflater.from(context).inflate(R.layout.feedback_submit_success,null);
		TextView textView=(TextView) view.findViewById(R.id.info_tv);
		textView.setText(msg);
		if(mToastStyle==null){
			mToastStyle=new Toast(context);
		}
		mToastStyle.setDuration(Toast.LENGTH_SHORT);
		mToastStyle.setGravity(Gravity.CENTER, 0,0);
		mToastStyle.setView(view);
		mToastStyle.show();
	}
	/** 弹出消息提示框
	 * @param context
	 * @param msg
	 */
	public static void toastMsgShortForAutoTender(Context context,String msg){
		View view=LayoutInflater.from(context).inflate(R.layout.autotender_on_off,null);
		TextView textView=(TextView) view.findViewById(R.id.info_tv);
		textView.setText(msg);
		if(mToastStyle==null){
			mToastStyle=new Toast(context);
		}
		mToastStyle.setDuration(Toast.LENGTH_SHORT);
		mToastStyle.setGravity(Gravity.CENTER, 0,0);
		mToastStyle.setView(view);
		mToastStyle.show();
	}
	
	/** 弹出消息提示框
	 * @param context
	 * @param msg
	 */
	public static void toastMsgLongForStyle(Context context,String msg){
		View view=LayoutInflater.from(context).inflate(R.layout.feedback_submit_success,null);
		TextView textView=(TextView) view.findViewById(R.id.info_tv);
		textView.setText(msg);
		if(mToastStyle==null){
			mToastStyle=new Toast(context);
		}
		mToastStyle.setDuration(Toast.LENGTH_LONG);
		mToastStyle.setGravity(Gravity.CENTER, 0,0);
		mToastStyle.setView(view);
		mToastStyle.show();
	}
	
	/** 弹出消息提示框
	 * @param context
	 * @param msg
	 */
	public static void toastMsgShortForStyle(Context context,String msg,int gravity){
		View view=LayoutInflater.from(context).inflate(R.layout.feedback_submit_success,null);
		TextView textView=(TextView) view.findViewById(R.id.info_tv);
		textView.setText(msg);
		if(mToastStyle==null){
			mToastStyle=new Toast(context);
		}
		mToastStyle.setDuration(Toast.LENGTH_SHORT);
		mToastStyle.setGravity(Gravity.CENTER, 0,gravity);
		mToastStyle.setView(view);
		mToastStyle.show();
	}
	
	/** 弹出消息提示框
	 * @param context
	 * @param msg
	 */
	public static void toastMsgLongForStyle(Context context,String msg,int gravity){
		View view=LayoutInflater.from(context).inflate(R.layout.feedback_submit_success,null);
		TextView textView=(TextView) view.findViewById(R.id.info_tv);
		textView.setText(msg);
		if(mToastStyle==null){
			mToastStyle=new Toast(context);
		}
		mToastStyle.setDuration(Toast.LENGTH_LONG);
		mToastStyle.setGravity(Gravity.CENTER, 0,0);
		mToastStyle.setView(view);
		mToastStyle.show();
	}
	
	/**
	 * 判断SDCard是否存在,并可写
	 * 
	 * @return
	 */
	public static boolean checkSDCard() {
		String flag = Environment.getExternalStorageState();
		if (android.os.Environment.MEDIA_MOUNTED.equals(flag)) {
			return true;
		}
		return false;
	}
	
	public static void canelToast(){
		if(mToast!=null){
			mToast.cancel();
		}
	}
	
	public static void canelToastForStyle(){
		if(mToastStyle!=null){
			mToastStyle.cancel();
		}
	}
	
	public static boolean checkDeviceHasNavigationBar(Activity activity){
		boolean hasBackKey = KeyCharacterMap .deviceHasKey(KeyEvent.KEYCODE_BACK);  
        return hasBackKey;
	}
	
	/**设置用户基本信息
	 * @param personalInfoResponse
	 */
	public static void setUserInfo(PersonalInfoResponse personalInfoResponse){
		UserPersonalInfo.setAvailablePoint(personalInfoResponse.getResult().getCustomerVo().getAvailablePoint());
		UserPersonalInfo.setCardVoucherCount(personalInfoResponse.getResult().getCustomerVo().getCardVoucherCount());
		UserPersonalInfo.setTotalAssets(personalInfoResponse.getResult().getCustomerVo().getTotalAssets());
		UserPersonalInfo.setBankCount(personalInfoResponse.getResult().getCustomerVo().getBankCount());
		UserPersonalInfo.setAvailableBalance(personalInfoResponse.getResult().getCustomerVo().getAvailableBalance());
		UserPersonalInfo.setCustomerName(personalInfoResponse.getResult().getCustomerVo().getCustomerName());
		UserPersonalInfo.setEmail(personalInfoResponse.getResult().getCustomerVo().getEmail());
		UserPersonalInfo.setImageUrl(personalInfoResponse.getResult().getCustomerVo().getImageUrl());
		UserPersonalInfo.setIsAttestation(personalInfoResponse.getResult().getCustomerVo().getIsAttestation());
		UserPersonalInfo.setIsBingPhone(personalInfoResponse.getResult().getCustomerVo().getIsBingPhone());
		UserPersonalInfo.setIsSetTradePwd(personalInfoResponse.getResult().getCustomerVo().getIsSetTradePwd());
		UserPersonalInfo.setPhoneNo(personalInfoResponse.getResult().getCustomerVo().getPhoneNo());
		UserPersonalInfo.setPid(personalInfoResponse.getResult().getCustomerVo().getPid());
		UserPersonalInfo.setSignday(personalInfoResponse.getResult().getCustomerVo().getSignday());
		UserPersonalInfo.setSname(personalInfoResponse.getResult().getCustomerVo().getSname());
		UserPersonalInfo.setVipIco(personalInfoResponse.getResult().getCustomerVo().getVipIco());
		UserPersonalInfo.setVipLevelName(personalInfoResponse.getResult().getCustomerVo().getVipLevelName());
		UserPersonalInfo.setVipTime(personalInfoResponse.getResult().getCustomerVo().getVipTime());
		UserPersonalInfo.setIdentificationNo(personalInfoResponse.getResult().getCustomerVo().getIdentificationNo());
		UserPersonalInfo.setIsFirstPay(personalInfoResponse.getResult().getCustomerVo().getIsFirstPay());	
		UserPersonalInfo.setRechargeAmount(personalInfoResponse.getResult().getCustomerVo().getRechargeAmount());
		UserPersonalInfo.setWithdrawAmount(personalInfoResponse.getResult().getCustomerVo().getWithdrawAmount());
		UserPersonalInfo.setDueAmount(personalInfoResponse.getResult().getCustomerVo().getDueAmount());
		UserPersonalInfo.setFreezeAmount(personalInfoResponse.getResult().getCustomerVo().getFreezeAmount());
		UserPersonalInfo.setTenderAmount(personalInfoResponse.getResult().getCustomerVo().getTenderAmount());
		UserPersonalInfo.setExperienceAmount(personalInfoResponse.getResult().getCustomerVo().getExperienceAmount());
		UserPersonalInfo.setRegistrationTime(personalInfoResponse.getResult().getCustomerVo().getRegistrationTime());
		UserPersonalInfo.setIsVip(personalInfoResponse.getResult().getCustomerVo().getIsVip());
		UserPersonalInfo.setVipLevel(personalInfoResponse.getResult().getCustomerVo().getVipLevel());
		UserPersonalInfo.setExperienceEnd(personalInfoResponse.getResult().getCustomerVo().getExperienceEnd());
		UserPersonalInfo.setExperienceStart(personalInfoResponse.getResult().getCustomerVo().getExperienceStart());
		UserPersonalInfo.setEmpiricalValue(personalInfoResponse.getResult().getCustomerVo().getEmpiricalValue());
		UserPersonalInfo.setSystemTime(personalInfoResponse.getResult().getSystemTime());
		UserPersonalInfo.setCommonAmount(personalInfoResponse.getResult().getCustomerVo().getCommonAmount());
		UserPersonalInfo.setRechargeDetaiAmount(personalInfoResponse.getResult().getCustomerVo().getRechargeDetaiAmount());
		UserPersonalInfo.setWithdrawalFee(personalInfoResponse.getResult().getCustomerVo().getWithdrawalFee());
		UserPersonalInfo.setGetUserInfo(true);
	}
}
