package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class CardUsedListBean {

	private int pageCount;
	@SerializedName("list")
	private ArrayList<CardUsedInfoBean> cardUsedListBean;
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public ArrayList<CardUsedInfoBean> getCardUsedListBean() {
		return cardUsedListBean;
	}
	public void setCardUsedListBean(ArrayList<CardUsedInfoBean> cardUsedListBean) {
		this.cardUsedListBean = cardUsedListBean;
	}
	
}
