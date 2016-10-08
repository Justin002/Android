package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ReimbursementForProfitDetialResult;
import com.google.gson.annotations.SerializedName;

public class ReimbursementForProfitDetailResponse {

	@SerializedName("result")
	private ReimbursementForProfitDetialResult reimbursementForProfitDetialResult;

	public ReimbursementForProfitDetialResult getReimbursementForProfitDetialResult() {
		return reimbursementForProfitDetialResult;
	}

	public void setReimbursementForProfitDetialResult(
			ReimbursementForProfitDetialResult reimbursementForProfitDetialResult) {
		this.reimbursementForProfitDetialResult = reimbursementForProfitDetialResult;
	}
	
	
}
