package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class ReimbursementForAdvanceDetailInfoBean implements Serializable{

	private String borrowId;
	private String borrowCode;
	private String borrowName;
	private double borrowMoney;
	private String borDeadline;
	private String borrowTime;
	private String repaymentType;
	private String repaymentTypeName;
	private double borrowRate;
	private double manageExpenseRate;
	private double capital;
	private double interest;
	private double managementCost;
	private double latefee;
	private double prepaymentFee;
	private double paymentAmount;
	private double prepaymentInterest;
	private double prepaymentManage;
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
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
	public String getBorDeadline() {
		return borDeadline;
	}
	public void setBorDeadline(String borDeadline) {
		this.borDeadline = borDeadline;
	}
	public String getBorrowTime() {
		return borrowTime;
	}
	public void setBorrowTime(String borrowTime) {
		this.borrowTime = borrowTime;
	}
	public String getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
	public String getRepaymentTypeName() {
		return repaymentTypeName;
	}
	public void setRepaymentTypeName(String repaymentTypeName) {
		this.repaymentTypeName = repaymentTypeName;
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
	public double getCapital() {
		return capital;
	}
	public void setCapital(double capital) {
		this.capital = capital;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getManagementCost() {
		return managementCost;
	}
	public void setManagementCost(double managementCost) {
		this.managementCost = managementCost;
	}
	public double getLatefee() {
		return latefee;
	}
	public void setLatefee(double latefee) {
		this.latefee = latefee;
	}
	public double getPrepaymentFee() {
		return prepaymentFee;
	}
	public void setPrepaymentFee(double prepaymentFee) {
		this.prepaymentFee = prepaymentFee;
	}
	public double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public double getPrepaymentInterest() {
		return prepaymentInterest;
	}
	public void setPrepaymentInterest(double prepaymentInterest) {
		this.prepaymentInterest = prepaymentInterest;
	}
	public double getPrepaymentManage() {
		return prepaymentManage;
	}
	public void setPrepaymentManage(double prepaymentManage) {
		this.prepaymentManage = prepaymentManage;
	}
	
	
}
