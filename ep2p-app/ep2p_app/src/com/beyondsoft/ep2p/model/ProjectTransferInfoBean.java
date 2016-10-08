package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class ProjectTransferInfoBean implements Serializable {

	    //pid
		private String pid;
		//用户id
		private String customerId;
		//借款编号
		private String borrowCode;
		//借款名称
		private String borrowName;
		//转让编号
		private String tranderCode;
		//转让期数
		private Integer timeRemaining;
		//项目价值
		private double projectValue;
		//项目转让价格
		private double successAmount;
		//状态信息
		private int trandStatus;
		//合同编号
		private String protocolId;
		//转让时间
		private String investmentTime;
		//标的id
		private String borrowId;
		
		
		public String getBorrowId() {
			return borrowId;
		}
		public void setBorrowId(String borrowId) {
			this.borrowId = borrowId;
		}
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
		public String getTranderCode() {
			return tranderCode;
		}
		public void setTranderCode(String tranderCode) {
			this.tranderCode = tranderCode;
		}
		public Integer getTimeRemaining() {
			return timeRemaining;
		}
		public void setTimeRemaining(Integer timeRemaining) {
			this.timeRemaining = timeRemaining;
		}
		public double getProjectValue() {
			return projectValue;
		}
		public void setProjectValue(double projectValue) {
			this.projectValue = projectValue;
		}
		public double getSuccessAmount() {
			return successAmount;
		}
		public void setSuccessAmount(double successAmount) {
			this.successAmount = successAmount;
		}
	
		public int getTrandStatus() {
			return trandStatus;
		}
		public void setTrandStatus(int trandStatus) {
			this.trandStatus = trandStatus;
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
		
		
		

}
