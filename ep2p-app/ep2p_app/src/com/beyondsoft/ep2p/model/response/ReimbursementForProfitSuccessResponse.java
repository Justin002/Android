package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ReimbursementForProfitSuccessResult;
import com.google.gson.annotations.SerializedName;

public class ReimbursementForProfitSuccessResponse {

	@SerializedName("result")
	private ReimbursementForProfitSuccessResult result;

	public ReimbursementForProfitSuccessResult getResult() {
		return result;
	}

	public void setResult(ReimbursementForProfitSuccessResult result) {
		this.result = result;
	}
	
	
}
