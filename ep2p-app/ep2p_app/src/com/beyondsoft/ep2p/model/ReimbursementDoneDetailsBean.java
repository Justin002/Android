package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class ReimbursementDoneDetailsBean implements Serializable{

	private String borrowCode;
	private String borrowName;
	private double borrowMoney;
	private double borrowRate;
	private double manageExpenseRate;
	private String deadline;
	private String repaymentType;
	private double latefee;
	private double repaidCapital;
	private double repaidInterest;
	private double advanceRepayInterest;
	private double repaidManagementCost;
	private double advanceRepayManagementCost;
	private double repaidPenalty;
	private double prepaymentFee;
	
	
	public double getRepaidCapital() {
		return repaidCapital;
	}
	public void setRepaidCapital(double repaidCapital) {
		this.repaidCapital = repaidCapital;
	}
	public String getBorrowCode() {
		return borrowCode;
	}
	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	public double getBorrowMoney() {
		return borrowMoney;
	}
	public void setBorrowMoney(double borrowMoney) {
		this.borrowMoney = borrowMoney;
	}
	public double getBorrowRate() {
		return borrowRate;
	}
	public void setBorrowRate(double borrowRate) {
		this.borrowRate = borrowRate;
	}
	public double getManageExpenseRate() {
		return manageExpenseRate;
	}
	public void setManageExpenseRate(double manageExpenseRate) {
		this.manageExpenseRate = manageExpenseRate;
	}
	
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
	public double getLatefee() {
		return latefee;
	}
	public void setLatefee(double latefee) {
		this.latefee = latefee;
	}
	
	public double getRepaidInterest() {
		return repaidInterest;
	}
	public void setRepaidInterest(double repaidInterest) {
		this.repaidInterest = repaidInterest;
	}
	public double getAdvanceRepayInterest() {
		return advanceRepayInterest;
	}
	public void setAdvanceRepayInterest(double advanceRepayInterest) {
		this.advanceRepayInterest = advanceRepayInterest;
	}
	public double getRepaidManagementCost() {
		return repaidManagementCost;
	}
	public void setRepaidManagementCost(double repaidManagementCost) {
		this.repaidManagementCost = repaidManagementCost;
	}
	public double getAdvanceRepayManagementCost() {
		return advanceRepayManagementCost;
	}
	public void setAdvanceRepayManagementCost(double advanceRepayManagementCost) {
		this.advanceRepayManagementCost = advanceRepayManagementCost;
	}
	public double getRepaidPenalty() {
		return repaidPenalty;
	}
	public void setRepaidPenalty(double repaidPenalty) {
		this.repaidPenalty = repaidPenalty;
	}
	public double getPrepaymentFee() {
		return prepaymentFee;
	}
	public void setPrepaymentFee(double prepaymentFee) {
		this.prepaymentFee = prepaymentFee;
	}
	
	
}
