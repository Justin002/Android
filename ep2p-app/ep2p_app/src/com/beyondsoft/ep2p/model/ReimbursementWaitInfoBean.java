package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class ReimbursementWaitInfoBean implements Serializable{

	private String pid;
	private String borrowCode;
	private String expireTime;
	private String receiptPalnStatus;
	private String receiptPalnStatusName;
	private String planindex;
	private int borDeadline;
	private double borTotalAmount;
	private String borrowId;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getBorrowCode() {
		return borrowCode;
	}
	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getReceiptPalnStatus() {
		return receiptPalnStatus;
	}
	public void setReceiptPalnStatus(String receiptPalnStatus) {
		this.receiptPalnStatus = receiptPalnStatus;
	}
	public String getReceiptPalnStatusName() {
		return receiptPalnStatusName;
	}
	public void setReceiptPalnStatusName(String receiptPalnStatusName) {
		this.receiptPalnStatusName = receiptPalnStatusName;
	}
	public String getPlanindex() {
		return planindex;
	}
	public void setPlanindex(String planindex) {
		this.planindex = planindex;
	}
	public int getBorDeadline() {
		return borDeadline;
	}
	public void setBorDeadline(int borDeadline) {
		this.borDeadline = borDeadline;
	}
	public double getBorTotalAmount() {
		return borTotalAmount;
	}
	public void setBorTotalAmount(double borTotalAmount) {
		this.borTotalAmount = borTotalAmount;
	}
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	
	
}
