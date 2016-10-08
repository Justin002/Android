package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.CardUsedListBean;
import com.google.gson.annotations.SerializedName;

public class CardUsedListResponse extends BaseResponse{

	@SerializedName("result")
	private CardUsedListBean cardUsedListBean;

	public CardUsedListBean getCardUsedListBean() {
		return cardUsedListBean;
	}

	public void setCardUsedListBean(CardUsedListBean cardUsedListBean) {
		this.cardUsedListBean = cardUsedListBean;
	}
	
	
}
