package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ReimbursementForAdvanceDetailResult;
import com.google.gson.annotations.SerializedName;

public class ReimbursementForAdvanceDetailResponse {

	@SerializedName("result")
	private ReimbursementForAdvanceDetailResult result;

	public ReimbursementForAdvanceDetailResult getResult() {
		return result;
	}

	public void setResult(ReimbursementForAdvanceDetailResult result) {
		this.result = result;
	}
	
	
	
}
