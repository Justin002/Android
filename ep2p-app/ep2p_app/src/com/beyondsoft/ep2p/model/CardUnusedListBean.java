package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class CardUnusedListBean {

	private int pageCount;
	@SerializedName("list")
	private ArrayList<CardUnusedInfoBean> cardUnusedListBean;
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public ArrayList<CardUnusedInfoBean> getCardUnusedListBean() {
		return cardUnusedListBean;
	}
	public void setCardUnusedListBean(ArrayList<CardUnusedInfoBean> cardUnusedListBean) {
		this.cardUnusedListBean = cardUnusedListBean;
	}
	
	
}
