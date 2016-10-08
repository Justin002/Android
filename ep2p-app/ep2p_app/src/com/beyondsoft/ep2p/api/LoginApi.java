package com.beyondsoft.ep2p.api;

import com.android.volley.toolbox.StringRequest;
import com.beyondsoft.ep2p.utils.IDataConnectListener;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;

public class LoginApi {
	
	
	/** 登录api
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest login(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.URL_LOGIN,object, listener);
	}
	
	/**设置APP端设备ID
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest setRegistrationID(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.JPUSH_REGISTRATION_ID,object, listener);
	}
}
