package com.beyondsoft.ep2p.service;

import com.android.volley.toolbox.StringRequest;
import com.beyondsoft.ep2p.api.MoreApi;
import com.beyondsoft.ep2p.utils.IDataConnectListener;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;

/**
 * @author Ivan.Lu
 * @description 更多业务层
 */
public class MoreService {
	
	private MoreApi moreApi;
	
	public MoreService(){
		moreApi=new MoreApi();
	}
	
	/**退出登录
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest loginOut(int tag, Object object,
			IDataConnectListener listener) {
		return moreApi.loginOut(tag, object, listener);
	}
	
	/**意见反馈
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest feedback(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_FEEDBACK,object, listener);
	}
}
