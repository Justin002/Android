package com.beyondsoft.ep2p.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProjectWaitInfoBean implements Serializable{

	//pid
	private String pid;
	//用户id
	private String customerId;
	//转让id
	private String transfId;
	/*债券转让状态 ：（>0 表示多少天后可转让，=0表示债券可以转让 ，<0表示不可装让（
		  -1 ==>a、原项目的没有处于逾期状态 
		  -2 ==>b、原项目成功还款一期及以上 
	    -3 ==>c、原项目剩余还款期次2期及以上
	    -4 ==>d、当前处于VIP有效期内
	    -5 ==>e、不可是待收日当天及前两日（小于最近待收日前三天的可转）
	    -6 ==>f、原标必须设定为允许债权转让。
		））
	*/
	private Integer transfStatus;
	//借款编号或债券编号
	private String borrowCode;
	//借款名称
	private String borrowName;
	//投资金额
	private BigDecimal investmentAmount;
	//已收期数
	private Integer forgotNper;
	//借款期限
	private String borDeadline;
	//年利率
	private BigDecimal borrowApr;
	//加息收益
	private BigDecimal hike;
	//投资奖励
	private BigDecimal investmentReward;
	//获取非加息利息
	private BigDecimal interest;
	//已收本息
	private BigDecimal interestReceived;
	//待收本息
	private BigDecimal collectInterest;
	//投标状态
	private String tenderStatus;
	//协议模板id
	private String protocolId;
	//投资时间
	private String investmentTime;
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
	public String getTransfId() {
		return transfId;
	}
	public void setTransfId(String transfId) {
		this.transfId = transfId;
	}
	public Integer getTransfStatus() {
		return transfStatus;
	}
	public void setTransfStatus(Integer transfStatus) {
		this.transfStatus = transfStatus;
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
	public BigDecimal getInvestmentAmount() {
		return investmentAmount;
	}
	public void setInvestmentAmount(BigDecimal investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
	public Integer getForgotNper() {
		return forgotNper;
	}
	public void setForgotNper(Integer forgotNper) {
		this.forgotNper = forgotNper;
	}
	public String getBorDeadline() {
		return borDeadline;
	}
	public void setBorDeadline(String borDeadline) {
		this.borDeadline = borDeadline;
	}
	public BigDecimal getBorrowApr() {
		return borrowApr;
	}
	public void setBorrowApr(BigDecimal borrowApr) {
		this.borrowApr = borrowApr;
	}
	public BigDecimal getHike() {
		return hike;
	}
	public void setHike(BigDecimal hike) {
		this.hike = hike;
	}
	public BigDecimal getInvestmentReward() {
		return investmentReward;
	}
	public void setInvestmentReward(BigDecimal investmentReward) {
		this.investmentReward = investmentReward;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public BigDecimal getInterestReceived() {
		return interestReceived;
	}
	public void setInterestReceived(BigDecimal interestReceived) {
		this.interestReceived = interestReceived;
	}
	public BigDecimal getCollectInterest() {
		return collectInterest;
	}
	public void setCollectInterest(BigDecimal collectInterest) {
		this.collectInterest = collectInterest;
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
	
	

}
