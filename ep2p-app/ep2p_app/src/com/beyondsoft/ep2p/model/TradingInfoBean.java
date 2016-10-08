package com.beyondsoft.ep2p.model;

import java.io.Serializable;

/**
 * 交易记录实体
 * 
 * @author hanxiaohui
 *
 */

public class TradingInfoBean implements Serializable{
	
	private static final long serialVersionUID = 2011612114198946646L;
	private String happenTimes;//创建日期
	private String accountBalance;// 账户总额
	private String businessType;// 交易类型
	private double fundValue;// 收入
	private double expenditure;// 支出  
	public String getHappenTimes() {
		return happenTimes;
	}
	public void setHappenTime(String happenTimes) {
		this.happenTimes = happenTimes;
	}
	public String getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public double getFundValue() {
		return fundValue;
	}
	public void setFundValue(double fundValues) {
		this.fundValue = fundValues;
	}
	public double getExpenditure() {
		return expenditure;
	}
	public void setExpenditure(double expenditure) {
		this.expenditure = expenditure;
	}
	
	
}
