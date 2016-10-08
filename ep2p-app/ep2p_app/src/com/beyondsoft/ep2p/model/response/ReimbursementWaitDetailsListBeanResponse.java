package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ReimbursementWaitDetailResult;
import com.google.gson.annotations.SerializedName;

public class ReimbursementWaitDetailsListBeanResponse extends BaseResponse{

	@SerializedName("result")
	private ReimbursementWaitDetailResult reimbursementWaitDetailResult;

	public ReimbursementWaitDetailResult getReimbursementWaitDetailResult() {
		return reimbursementWaitDetailResult;
	}

	public void setReimbursementWaitDetailResult(ReimbursementWaitDetailResult reimbursementWaitDetailResult) {
		this.reimbursementWaitDetailResult = reimbursementWaitDetailResult;
	}
	
	
	
}
