package com.beyondsoft.ep2p.api;

import com.android.volley.toolbox.StringRequest;
import com.beyondsoft.ep2p.utils.IDataConnectListener;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;

/**
 * @author Ivan.Lu
 * @description 更多接口
 */
public class MoreApi {
	
	
	/**退出登录
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest loginOut(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.URL_LOGINOUT,object, listener);
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
