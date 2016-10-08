package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ReimbursementDoneDetailResult;
import com.beyondsoft.ep2p.model.ReimbursementWaitDetailResult;
import com.google.gson.annotations.SerializedName;

public class ReimbursementDoneDetailResponse extends BaseResponse{

	@SerializedName("result")
	private ReimbursementDoneDetailResult result;

	public ReimbursementDoneDetailResult getResult() {
		return result;
	}

	public void setResult(ReimbursementDoneDetailResult result) {
		this.result = result;
	}
	
	


	
}
