package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class WaitReceiveDetialInfoBean implements Serializable{

	private String expireTime;
	private double netInterest;
	private String borrowId;
	private String transferId;
	private double netHike;
	private double totalAmount;
	private String borrowCode;
	private double lateFee;
	private String playType;
	private double capital;
	private String deadline;
	private String planIndex;
	private String borrowName;
	
	
	
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public double getNetInterest() {
		return netInterest;
	}
	public void setNetInterest(double netInterest) {
		this.netInterest = netInterest;
	}
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	public String getTransferId() {
		return transferId;
	}
	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	public double getNetHike() {
		return netHike;
	}
	public void setNetHike(double netHike) {
		this.netHike = netHike;
	}
	
	
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getBorrowCode() {
		return borrowCode;
	}
	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}
	public double getLateFee() {
		return lateFee;
	}
	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}
	public String getPlayType() {
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	public double getCapital() {
		return capital;
	}
	public void setCapital(double capital) {
		this.capital = capital;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getPlanIndex() {
		return planIndex;
	}
	public void setPlanIndex(String planIndex) {
		this.planIndex = planIndex;
	}
	
	
}
