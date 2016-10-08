package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.WaitReceiveListBean;
import com.google.gson.annotations.SerializedName;

public class WaitReceiveListResponse {

	@SerializedName("result")
	private WaitReceiveListBean waitReceiveListBean;

	public WaitReceiveListBean getWaitReceiveListBean() {
		return waitReceiveListBean;
	}

	public void setWaitReceiveListBean(WaitReceiveListBean waitReceiveListBean) {
		this.waitReceiveListBean = waitReceiveListBean;
	}
	
	
}
