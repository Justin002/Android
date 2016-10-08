package com.beyondsoft.ep2p.service;

import com.android.volley.toolbox.StringRequest;
import com.beyondsoft.ep2p.api.LoginApi;
import com.beyondsoft.ep2p.utils.IDataConnectListener;

/**
 * @author Ivan.Lu
 * @description 登录业务层
 */
public class LoginService {
	private LoginApi loginApi;
	public LoginService(){
		loginApi=new LoginApi();
	}
	
	/** 登录
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest login(int tag, Object object,
			IDataConnectListener listener) {
		return loginApi.login(tag, object, listener);
	}
	
	/**设置APP端设备ID
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest setRegistrationID(int tag, Object object,
			IDataConnectListener listener) {
		return loginApi.setRegistrationID(tag, object, listener);
	}
}
