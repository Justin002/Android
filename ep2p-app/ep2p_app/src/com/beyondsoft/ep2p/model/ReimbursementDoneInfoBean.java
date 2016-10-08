package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class ReimbursementDoneInfoBean implements Serializable{

	private String pid;
	private String borrowCode;
	private String borStatus;
	private String borDeadline;
	private String borrowMoney;
	
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
	public String getBorStatus() {
		return borStatus;
	}
	public void setBorStatus(String borStatus) {
		this.borStatus = borStatus;
	}
	public String getBorDeadline() {
		return borDeadline;
	}
	public void setBorDeadline(String borDeadline) {
		this.borDeadline = borDeadline;
	}
	public String getBorrowMoney() {
		return borrowMoney;
	}
	public void setBorrowMoney(String borrowMoney) {
		this.borrowMoney = borrowMoney;
	}
	
	
}
