package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class WaitReceiveInfoBean implements Serializable{

	//待收明细id
		private String brPid;
		//标的PID
		private String bPid;
		//转让PID
		private String tranPid;
		//标的名称
		private String borrowName;
		//标的编码
		private String borrowCode;
		//转让编码
		private String transferCode;
		//借款类型
		private String borrowType;
		//还款类型
		private String repaymentType;
		//标的总期次
		private String borDeadline;
		//借款利率
		private double borrowRate;
		//待收实际本金
		private double capital;
		//待收本金
		private double interestMoney;
		//还款期次
		private Integer planIndex;
		//还款时间
		private String expireTime;
		//查询类型   1、待收      2、已结清
		private String selectType;
		public String getBrPid() {
			return brPid;
		}
		public void setBrPid(String brPid) {
			this.brPid = brPid;
		}
		public String getbPid() {
			return bPid;
		}
		public void setbPid(String bPid) {
			this.bPid = bPid;
		}
		public String getTranPid() {
			return tranPid;
		}
		public void setTranPid(String tranPid) {
			this.tranPid = tranPid;
		}
		public String getBorrowName() {
			return borrowName;
		}
		public void setBorrowName(String borrowName) {
			this.borrowName = borrowName;
		}
		public String getBorrowCode() {
			return borrowCode;
		}
		public void setBorrowCode(String borrowCode) {
			this.borrowCode = borrowCode;
		}
		public String getTransferCode() {
			return transferCode;
		}
		public void setTransferCode(String transferCode) {
			this.transferCode = transferCode;
		}
		public String getBorrowType() {
			return borrowType;
		}
		public void setBorrowType(String borrowType) {
			this.borrowType = borrowType;
		}
		public String getRepaymentType() {
			return repaymentType;
		}
		public void setRepaymentType(String repaymentType) {
			this.repaymentType = repaymentType;
		}
		public String getBorDeadline() {
			return borDeadline;
		}
		public void setBorDeadline(String borDeadline) {
			this.borDeadline = borDeadline;
		}
		public double getBorrowRate() {
			return borrowRate;
		}
		public void setBorrowRate(double borrowRate) {
			this.borrowRate = borrowRate;
		}
		public double getCapital() {
			return capital;
		}
		public void setCapital(double capital) {
			this.capital = capital;
		}
		public double getInterestMoney() {
			return interestMoney;
		}
		public void setInterestMoney(double interestMoney) {
			this.interestMoney = interestMoney;
		}
		public Integer getPlanIndex() {
			return planIndex;
		}
		public void setPlanIndex(Integer planIndex) {
			this.planIndex = planIndex;
		}
		public String getExpireTime() {
			return expireTime;
		}
		public void setExpireTime(String expireTime) {
			this.expireTime = expireTime;
		}
		public String getSelectType() {
			return selectType;
		}
		public void setSelectType(String selectType) {
			this.selectType = selectType;
		}

		
}
