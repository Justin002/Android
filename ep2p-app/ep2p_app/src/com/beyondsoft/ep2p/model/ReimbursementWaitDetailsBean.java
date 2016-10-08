package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class ReimbursementWaitDetailsBean implements Serializable{

	private String borrowCode;
	private String borrowName;
	private double borrowMoney;
	private double borrowRate;
	private double manageExpenseRate;
	private String deadline;
	private String repaymentType;
	private double waitRepayMoney;
	private double capital;
	private double interest;
	private double managementCost;
	private double latefee;
	private String waitRepayTime;
	private String currentPlanindex;
	private String maxPlanindex;
	
	
	

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
	public double getWaitRepayMoney() {
		return waitRepayMoney;
	}
	public void setWaitRepayMoney(double waitRepayMoney) {
		this.waitRepayMoney = waitRepayMoney;
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
	public String getWaitRepayTime() {
		return waitRepayTime;
	}
	public void setWaitRepayTime(String waitRepayTime) {
		this.waitRepayTime = waitRepayTime;
	}
	public String getCurrentPlanindex() {
		return currentPlanindex;
	}
	public void setCurrentPlanindex(String currentPlanindex) {
		this.currentPlanindex = currentPlanindex;
	}
	public String getMaxPlanindex() {
		return maxPlanindex;
	}
	public void setMaxPlanindex(String maxPlanindex) {
		this.maxPlanindex = maxPlanindex;
	}
	
	
}
