package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class ReimbursementForProfitOverduInfoBean implements Serializable{

	private double capital;
	private double interest;
	private double managementCost;
	private double latefee;
	private String currentPlanindex;
	private String maxPlanindex;
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
