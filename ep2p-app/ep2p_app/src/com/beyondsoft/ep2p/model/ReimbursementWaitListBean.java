package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ReimbursementWaitListBean {

	@SerializedName("list")
	private ArrayList<ReimbursementWaitInfoBean> ReimbursementWaitList;

	public ArrayList<ReimbursementWaitInfoBean> getReimbursementWaitList() {
		return ReimbursementWaitList;
	}

	public void setReimbursementWaitList(ArrayList<ReimbursementWaitInfoBean> reimbursementWaitList) {
		ReimbursementWaitList = reimbursementWaitList;
	}
	
	
}
