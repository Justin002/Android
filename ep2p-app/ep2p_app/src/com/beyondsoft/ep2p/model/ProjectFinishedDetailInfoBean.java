package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class ProjectFinishedDetailInfoBean implements Serializable{

	private String borrowName;
	private double borrowMoney;
	private int transferDeadline;
	private String deadLine;
	private double investmentReward;
	private String repaymentTypeName;
	private double borrowRate;
	private String borrowId;
	private double interest;
	private int receivedDeadline;
	private double totalAmount;
	private String borrowCode;
	private int repamentDeadline;
	private String investmentTime;
	private String deadline;
	private double hike;
	private double investmentAmount;
	private Brt brt;
	public class Brt{
		private String pid;
		private double successAmount;
		private String transferCode;
		private double residualPrincipal;
		private double fee;
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public double getSuccessAmount() {
			return successAmount;
		}
		public void setSuccessAmount(double successAmount) {
			this.successAmount = successAmount;
		}
		public String getTransferCode() {
			return transferCode;
		}
		public void setTransferCode(String transferCode) {
			this.transferCode = transferCode;
		}
		public double getResidualPrincipal() {
			return residualPrincipal;
		}
		public void setResidualPrincipal(double residualPrincipal) {
			this.residualPrincipal = residualPrincipal;
		}
		public double getFee() {
			return fee;
		}
		public void setFee(double fee) {
			this.fee = fee;
		}
		
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

	public int getTransferDeadline() {
		return transferDeadline;
	}

	public void setTransferDeadline(int transferDeadline) {
		this.transferDeadline = transferDeadline;
	}

	public String getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
	}

	public double getInvestmentReward() {
		return investmentReward;
	}

	public void setInvestmentReward(double investmentReward) {
		this.investmentReward = investmentReward;
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

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public int getReceivedDeadline() {
		return receivedDeadline;
	}

	public void setReceivedDeadline(int receivedDeadline) {
		this.receivedDeadline = receivedDeadline;
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

	public int getRepamentDeadline() {
		return repamentDeadline;
	}

	public void setRepamentDeadline(int repamentDeadline) {
		this.repamentDeadline = repamentDeadline;
	}

	public String getInvestmentTime() {
		return investmentTime;
	}

	public void setInvestmentTime(String investmentTime) {
		this.investmentTime = investmentTime;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public double getHike() {
		return hike;
	}

	public void setHike(double hike) {
		this.hike = hike;
	}

	public double getInvestmentAmount() {
		return investmentAmount;
	}

	public void setInvestmentAmount(double investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	public Brt getBrt() {
		return brt;
	}

	public void setBrt(Brt brt) {
		this.brt = brt;
	}
	
	
}
