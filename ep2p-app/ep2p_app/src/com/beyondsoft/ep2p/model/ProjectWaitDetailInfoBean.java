package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class ProjectWaitDetailInfoBean implements Serializable{

	//主键借款信息主键
		private String pid;
		//用户ID
		private String customerId;
		//借款编码
		private String borrowCode;
		//借款编码转让的就是转让编码
		private String borrowOtherCode;
		//借款名称
		private String borrowName;
		//借款金额
		private float borrowMoney;
		//借款利率
		private float borrowRate;
		//借款期限
		private String borDeadline;
		//还款类型
		private String repaymentType;
		private String repaymentTypeName;
		//转让id
		private String transferId;
		//投资金额
		private float intementMoney;
		//投资利息
		private float receivableInterest;
		//投资奖励
		private float awardAmount;
		//加息收益
		private float receivableHike;
		//投资时间
		private String investmentTime;
		//已收金额
		private float receivedMoney;
		//待收金额
		private float dueinMoney;
		//已收期数
		private Integer receivedWek;
		/*债券转让状态 ：（>0 表示多少天后可转让，=0表示债券可以转让 ，<0表示不可装让（
		  -1 ==>a、原项目的没有处于逾期状态 
		  -2 ==>b、原项目成功还款一期及以上 
	    -3 ==>c、原项目剩余还款期次2期及以上
	    -4 ==>d、当前处于VIP有效期内
	    -5 ==>e、不可是待收日当天及前两日（小于最近待收日前三天的可转）
	    -6 ==>f、原标必须设定为允许债权转让。
		））
		*/
		private Integer tranferStatus;
		//合同id
		private String borAgrId;
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
		public String getBorrowOtherCode() {
			return borrowOtherCode;
		}
		public void setBorrowOtherCode(String borrowOtherCode) {
			this.borrowOtherCode = borrowOtherCode;
		}
		public String getBorrowName() {
			return borrowName;
		}
		public void setBorrowName(String borrowName) {
			this.borrowName = borrowName;
		}
		public float getBorrowMoney() {
			return borrowMoney;
		}
		public void setBorrowMoney(float borrowMoney) {
			this.borrowMoney = borrowMoney;
		}
		public float getBorrowRate() {
			return borrowRate;
		}
		public void setBorrowRate(float borrowRate) {
			this.borrowRate = borrowRate;
		}
		public String getBorDeadline() {
			return borDeadline;
		}
		public void setBorDeadline(String borDeadline) {
			this.borDeadline = borDeadline;
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
		public String getTransferId() {
			return transferId;
		}
		public void setTransferId(String transferId) {
			this.transferId = transferId;
		}
		public float getIntementMoney() {
			return intementMoney;
		}
		public void setIntementMoney(float intementMoney) {
			this.intementMoney = intementMoney;
		}
		public float getReceivableInterest() {
			return receivableInterest;
		}
		public void setReceivableInterest(float receivableInterest) {
			this.receivableInterest = receivableInterest;
		}
		public float getAwardAmount() {
			return awardAmount;
		}
		public void setAwardAmount(float awardAmount) {
			this.awardAmount = awardAmount;
		}
		public float getReceivableHike() {
			return receivableHike;
		}
		public void setReceivableHike(float receivableHike) {
			this.receivableHike = receivableHike;
		}
		public String getInvestmentTime() {
			return investmentTime;
		}
		public void setInvestmentTime(String investmentTime) {
			this.investmentTime = investmentTime;
		}
		public float getReceivedMoney() {
			return receivedMoney;
		}
		public void setReceivedMoney(float receivedMoney) {
			this.receivedMoney = receivedMoney;
		}
		public float getDueinMoney() {
			return dueinMoney;
		}
		public void setDueinMoney(float dueinMoney) {
			this.dueinMoney = dueinMoney;
		}
		public Integer getReceivedWek() {
			return receivedWek;
		}
		public void setReceivedWek(Integer receivedWek) {
			this.receivedWek = receivedWek;
		}
		public Integer getTranferStatus() {
			return tranferStatus;
		}
		public void setTranferStatus(Integer tranferStatus) {
			this.tranferStatus = tranferStatus;
		}
		public String getBorAgrId() {
			return borAgrId;
		}
		public void setBorAgrId(String borAgrId) {
			this.borAgrId = borAgrId;
		}

		
		
		
}
