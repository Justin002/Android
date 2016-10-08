package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ReimbursementDoneListBean;
import com.google.gson.annotations.SerializedName;

public class ReimbursementDoneReponse {

	@SerializedName("result")
	private ReimbursementDoneListBean reimbursementDoneListBean;

	public ReimbursementDoneListBean getReimbursementDoneListBean() {
		return reimbursementDoneListBean;
	}

	public void setReimbursementDoneListBean(ReimbursementDoneListBean reimbursementDoneListBean) {
		this.reimbursementDoneListBean = reimbursementDoneListBean;
	}
	
	
}
