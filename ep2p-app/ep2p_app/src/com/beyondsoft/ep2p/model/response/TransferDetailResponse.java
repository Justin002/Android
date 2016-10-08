package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.TransferDetailInfoBean;
import com.google.gson.annotations.SerializedName;

public class TransferDetailResponse {

	@SerializedName("result")
	private TransferDetailInfoBean transferDetailInfoBean;

	public TransferDetailInfoBean getTransferDetailInfoBean() {
		return transferDetailInfoBean;
	}

	public void setTransferDetailInfoBean(TransferDetailInfoBean transferDetailInfoBean) {
		this.transferDetailInfoBean = transferDetailInfoBean;
	}
	
	
}
