package com.beyondsoft.ep2p.model;

import com.google.gson.annotations.SerializedName;

public class ReimbursementForAdvanceDetailResult {

	@SerializedName("repaymentVo")
	private ReimbursementForAdvanceDetailInfoBean bean;

	public ReimbursementForAdvanceDetailInfoBean getBean() {
		return bean;
	}

	public void setBean(ReimbursementForAdvanceDetailInfoBean bean) {
		this.bean = bean;
	}
	
	
}
