package com.beyondsoft.ep2p.service;

import java.util.HashMap;

import android.content.Context;

import com.android.volley.toolbox.StringRequest;
import com.beyondsoft.ep2p.utils.IDataConnectListener;
import com.beyondsoft.ep2p.utils.StringRequest2;

/**
 * 接口服务类
 * 
 * @author hardy.zhou
 *
 */
public class BaseService {

	public StringRequest getStringRequest(int tag, String url, HashMap<String, Object> params,
			IDataConnectListener listener,Context... context) {
		return new StringRequest2(tag, url, params, listener,context);
	}
	
	public StringRequest getStringRequest(int tag, String url, Object object,
			IDataConnectListener listener,Context... context) {
		return new StringRequest2(tag, url,object, listener,context);
	}
}
