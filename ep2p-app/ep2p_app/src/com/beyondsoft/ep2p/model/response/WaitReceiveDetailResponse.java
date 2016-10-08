package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.WaitReceiveDetialInfoBean;
import com.google.gson.annotations.SerializedName;

public class WaitReceiveDetailResponse {

	@SerializedName("result")
	private WaitReceiveDetialInfoBean waitReceiveDetialInfoBean;

	public WaitReceiveDetialInfoBean getWaitReceiveDetialInfoBean() {
		return waitReceiveDetialInfoBean;
	}

	public void setWaitReceiveDetialInfoBean(WaitReceiveDetialInfoBean waitReceiveDetialInfoBean) {
		this.waitReceiveDetialInfoBean = waitReceiveDetialInfoBean;
	}
	
	
}
