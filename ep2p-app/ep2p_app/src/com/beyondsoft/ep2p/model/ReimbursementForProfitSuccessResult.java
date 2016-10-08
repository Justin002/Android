package com.beyondsoft.ep2p.model;

import com.google.gson.annotations.SerializedName;

public class ReimbursementForProfitSuccessResult {

	private String borrowCode;
	private boolean repayResult;
	private boolean lastRepay;
	
	@SerializedName("repayPlan")
	private ReimbursementForProfitSuccessInfoBean bean;

	
	
	public boolean isLastRepay() {
		return lastRepay;
	}

	public void setLastRepay(boolean lastRepay) {
		this.lastRepay = lastRepay;
	}

	public String getBorrowCode() {
		return borrowCode;
	}

	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}

	

	public boolean isRepayResult() {
		return repayResult;
	}

	public void setRepayResult(boolean repayResult) {
		this.repayResult = repayResult;
	}

	public ReimbursementForProfitSuccessInfoBean getBean() {
		return bean;
	}

	public void setBean(ReimbursementForProfitSuccessInfoBean bean) {
		this.bean = bean;
	}
	
	
	
}
