package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.CardUnusedListBean;
import com.google.gson.annotations.SerializedName;

public class CardUnusedListRresponse extends BaseResponse{
	
	@SerializedName("result")
	private CardUnusedListBean cardUnusedListBean;

	public CardUnusedListBean getCardUnusedListBean() {
		return cardUnusedListBean;
	}

	public void setCardUnusedListBean(CardUnusedListBean cardUnusedListBean) {
		this.cardUnusedListBean = cardUnusedListBean;
	}
	
	
}
