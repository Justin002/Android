package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ReimbursementWaitDetailResult {

	
	@SerializedName("repayInfoVo")
	private ReimbursementWaitDetailsBean reimbursementWaitDetailsBean;

	public ReimbursementWaitDetailsBean getReimbursementWaitDetailsBean() {
		return reimbursementWaitDetailsBean;
	}

	public void setReimbursementWaitDetailsBean(ReimbursementWaitDetailsBean reimbursementWaitDeatilsBean) {
		this.reimbursementWaitDetailsBean = reimbursementWaitDeatilsBean;
	}
	
	
	
}
