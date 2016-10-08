package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class ReimbursementForProfitSuccessInfoBean implements Serializable{

	private double capital;
	private double interest;
	private double managementCost;
	private double latefee;
	private String repaidTime;
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
	public String getRepaidTime() {
		return repaidTime;
	}
	public void setRepaidTime(String repaidTime) {
		this.repaidTime = repaidTime;
	}
	
	
}
