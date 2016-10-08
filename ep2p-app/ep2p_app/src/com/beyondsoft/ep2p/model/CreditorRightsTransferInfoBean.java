package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class CreditorRightsTransferInfoBean implements Serializable{

	private String bizTransferFee;
	private double minValue;
	private double maxValue;
	private double currentInterest;
	private double currentAllInterest;
	private String currentExpireTime;
	private String transferId;
	private double surperCapital;
	private String borrowCode;
	private int surperDead;
	private int totalDead;
	private double alreadyBenxi;
	private double investCapital;
	public String getBizTransferFee() {
		return bizTransferFee;
	}
	public void setBizTransferFee(String bizTransferFee) {
		this.bizTransferFee = bizTransferFee;
	}
	public double getMinValue() {
		return minValue;
	}
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}
	public double getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
	public double getCurrentInterest() {
		return currentInterest;
	}
	public void setCurrentInterest(double currentInterest) {
		this.currentInterest = currentInterest;
	}
	public double getCurrentAllInterest() {
		return currentAllInterest;
	}
	public void setCurrentAllInterest(double currentAllInterest) {
		this.currentAllInterest = currentAllInterest;
	}
	public String getCurrentExpireTime() {
		return currentExpireTime;
	}
	public void setCurrentExpireTime(String currentExpireTime) {
		this.currentExpireTime = currentExpireTime;
	}
	public String getTransferId() {
		return transferId;
	}
	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	public double getSurperCapital() {
		return surperCapital;
	}
	public void setSurperCapital(double surperCapital) {
		this.surperCapital = surperCapital;
	}
	public String getBorrowCode() {
		return borrowCode;
	}
	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}
	public int getSurperDead() {
		return surperDead;
	}
	public void setSurperDead(int surperDead) {
		this.surperDead = surperDead;
	}
	public int getTotalDead() {
		return totalDead;
	}
	public void setTotalDead(int totalDead) {
		this.totalDead = totalDead;
	}
	public double getAlreadyBenxi() {
		return alreadyBenxi;
	}
	public void setAlreadyBenxi(double alreadyBenxi) {
		this.alreadyBenxi = alreadyBenxi;
	}
	public double getInvestCapital() {
		return investCapital;
	}
	public void setInvestCapital(double investCapital) {
		this.investCapital = investCapital;
	}
	
	
}
