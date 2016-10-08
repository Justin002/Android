package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class TransferDetailInfoBean implements Serializable{

	private int alreadyDead;
	private String transferId;
	private String borrowId;
	private double transferFee;
	private double transferAmount;
	private String transferCode;
	private double surperCapital;
	private String borrowCode;
	private int totalDead;
	private double alreadyBenxi;
	private double investCapital;
	
	public String getTransferId() {
		return transferId;
	}
	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	
	public String getTransferCode() {
		return transferCode;
	}
	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}
	
	public String getBorrowCode() {
		return borrowCode;
	}
	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}
	public int getAlreadyDead() {
		return alreadyDead;
	}
	public void setAlreadyDead(int alreadyDead) {
		this.alreadyDead = alreadyDead;
	}
	public double getTransferFee() {
		return transferFee;
	}
	public void setTransferFee(double transferFee) {
		this.transferFee = transferFee;
	}
	public double getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(double transferAmount) {
		this.transferAmount = transferAmount;
	}
	public double getSurperCapital() {
		return surperCapital;
	}
	public void setSurperCapital(double surperCapital) {
		this.surperCapital = surperCapital;
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
