package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.CreditorRightsTransferInfoBean;
import com.google.gson.annotations.SerializedName;

public class CreditorRightsTransferResponse {

	@SerializedName("result")
	private CreditorRightsTransferInfoBean creditorRightsTransferInfoBean;

	public CreditorRightsTransferInfoBean getCreditorRightsTransferInfoBean() {
		return creditorRightsTransferInfoBean;
	}

	public void setCreditorRightsTransferInfoBean(CreditorRightsTransferInfoBean creditorRightsTransferInfoBean) {
		this.creditorRightsTransferInfoBean = creditorRightsTransferInfoBean;
	}
	
	
}
