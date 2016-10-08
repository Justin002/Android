package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ReimbursementForProfitDetialResult {

	@SerializedName("repayInfoVo")
	private ReimbursementForProfitDetailBean reimbursementForProfitDetailBean;

	@SerializedName("repayments")
	private ArrayList<ReimbursementForProfitOverduInfoBean> reimbursementForProfitOverduList;

	public ArrayList<ReimbursementForProfitOverduInfoBean> getReimbursementForProfitOverduList() {
		return reimbursementForProfitOverduList;
	}

	public void setReimbursementForProfitOverduList(
			ArrayList<ReimbursementForProfitOverduInfoBean> reimbursementForProfitOverduList) {
		this.reimbursementForProfitOverduList = reimbursementForProfitOverduList;
	}
	
	public ReimbursementForProfitDetailBean getReimbursementForProfitDetailBean() {
		return reimbursementForProfitDetailBean;
	}

	public void setReimbursementForProfitDetailBean(ReimbursementForProfitDetailBean reimbursementForProfitDetailBean) {
		this.reimbursementForProfitDetailBean = reimbursementForProfitDetailBean;
	}
	
	
	
}
