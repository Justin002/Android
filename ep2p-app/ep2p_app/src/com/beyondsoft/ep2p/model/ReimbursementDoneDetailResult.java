package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ReimbursementDoneDetailResult {


	@SerializedName("repayInfoVo")
	private ReimbursementDoneDetailsBean reimbursementDoneDetails;

	public ReimbursementDoneDetailsBean getReimbursementDoneDetails() {
		return reimbursementDoneDetails;
	}

	public void setReimbursementDoneDetails(ReimbursementDoneDetailsBean reimbursementDoneDetails) {
		this.reimbursementDoneDetails = reimbursementDoneDetails;
	}
	
	

	


	

}
