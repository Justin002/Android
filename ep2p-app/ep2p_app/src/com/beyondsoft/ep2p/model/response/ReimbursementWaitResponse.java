package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ReimbursementWaitListBean;
import com.google.gson.annotations.SerializedName;

public class ReimbursementWaitResponse {

	@SerializedName("result")
	private ReimbursementWaitListBean ReibuersementWaitListbean;

	public ReimbursementWaitListBean getReibuersementWaitListbean() {
		return ReibuersementWaitListbean;
	}

	public void setReibuersementWaitListbean(ReimbursementWaitListBean reibuersementWaitListbean) {
		ReibuersementWaitListbean = reibuersementWaitListbean;
	}
	
	
}
