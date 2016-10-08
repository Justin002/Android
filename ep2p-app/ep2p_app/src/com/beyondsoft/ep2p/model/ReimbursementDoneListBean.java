package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ReimbursementDoneListBean {

	@SerializedName("list")
	private ArrayList<ReimbursementDoneInfoBean> ReimbursementDoneList;

	public ArrayList<ReimbursementDoneInfoBean> getReimbursementDoneList() {
		return ReimbursementDoneList;
	}

	public void setReimbursementDoneList(ArrayList<ReimbursementDoneInfoBean> reimbursementDoneList) {
		ReimbursementDoneList = reimbursementDoneList;
	}
	
	
}
