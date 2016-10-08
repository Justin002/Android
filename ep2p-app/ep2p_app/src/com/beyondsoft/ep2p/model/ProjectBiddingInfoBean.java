package com.beyondsoft.ep2p.model;

import java.io.Serializable;


public class ProjectBiddingInfoBean implements Serializable{
	   
	    //pid
		private String pid;
		//用户id
		private String customerId;
		//借款编号
		private String borrowCode;
		//借款名称
		private String borrowName;
		//投资金额
		private double investmentAmount;
		//加息
		private double hike;
		//投资奖励
		private double investmentReward;
		//利息
		private double interest;
		//投标状态
		private String tenderStatus;
		//协议模板id
		private String protocolId;
		//投资时间
		private String investmentTime;
	//投资利率
		private double borrowRate;
		//借款期限
		private String borDeadline;
		//借款金额
		private double borrowMoney;
		//总投资金额
		private double allinvsetMoney;
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public String getCustomerId() {
			return customerId;
		}
		public void setCustomerId(String customerId) {
			this.customerId = customerId;
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
		public double getInvestmentAmount() {
			return investmentAmount;
		}
		public void setInvestmentAmount(double investmentAmount) {
			this.investmentAmount = investmentAmount;
		}
		public double getHike() {
			return hike;
		}
		public void setHike(double hike) {
			this.hike = hike;
		}
		public double getInvestmentReward() {
			return investmentReward;
		}
		public void setInvestmentReward(double investmentReward) {
			this.investmentReward = investmentReward;
		}
		public double getInterest() {
			return interest;
		}
		public void setInterest(double interest) {
			this.interest = interest;
		}
		public String getTenderStatus() {
			return tenderStatus;
		}
		public void setTenderStatus(String tenderStatus) {
			this.tenderStatus = tenderStatus;
		}
		public String getProtocolId() {
			return protocolId;
		}
		public void setProtocolId(String protocolId) {
			this.protocolId = protocolId;
		}
		public String getInvestmentTime() {
			return investmentTime;
		}
		public void setInvestmentTime(String investmentTime) {
			this.investmentTime = investmentTime;
		}
		
		public double getBorrowRate() {
			return borrowRate;
		}
		public void setBorrowRate(double borrowRate) {
			this.borrowRate = borrowRate;
		}
		public String getBorDeadline() {
			return borDeadline;
		}
		public void setBorDeadline(String borDeadline) {
			this.borDeadline = borDeadline;
		}
		public double getBorrowMoney() {
			return borrowMoney;
		}
		public void setBorrowMoney(double borrowMoney) {
			this.borrowMoney = borrowMoney;
		}
		public double getAllinvsetMoney() {
			return allinvsetMoney;
		}
		public void setAllinvsetMoney(double allinvsetMoney) {
			this.allinvsetMoney = allinvsetMoney;
		}
		
		

}
