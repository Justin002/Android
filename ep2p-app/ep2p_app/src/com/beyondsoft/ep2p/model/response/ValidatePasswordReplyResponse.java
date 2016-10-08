package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ValidatePasswordReplyBean;
import com.google.gson.annotations.SerializedName;

public class ValidatePasswordReplyResponse extends BaseResponse{

	@SerializedName("result")
	private ValidatePasswordReplyBean passwordBean;

	public ValidatePasswordReplyBean getPasswordBean() {
		return passwordBean;
	}

	public void setPasswordBean(ValidatePasswordReplyBean passwordBean) {
		this.passwordBean = passwordBean;
	}
	
	
}
