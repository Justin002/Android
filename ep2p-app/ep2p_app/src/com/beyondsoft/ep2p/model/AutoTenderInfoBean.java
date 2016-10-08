package com.beyondsoft.ep2p.model;

import java.io.Serializable;

/**
 * 自动投标实体
 * 
 * @author hanxiaohui
 *
 */
public class AutoTenderInfoBean implements Serializable {

	private static final long serialVersionUID = 8293133235008589557L;
	private String pid;
	private String customerId;
	private String amount;//固定金额   10000.00
	private String balanceratio;//余额比例 10%
	private String minborrowmonth;//最短投资月
	private String maxborrowmonth;//最长投资月
	private String minborrowrate;//最小收益率
	private String maxborrowrate;//最大收益率
	private String borrowType;//借款类型（e计划、e首房、e抵押；1,2,3）
	private int autostatus;//自动投标状态（0：未启用，1： 启用）
	private String createUser;//创建投标人
	private String createTime;//创建投标时间
	private String lastUpdateUser;//最后一次更新的人
	private String lastUpdateTime;//最后一次更新时间
	private int status;
	private String version;
	private int ranking;

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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBalanceratio() {
		return balanceratio;
	}

	public void setBalanceratio(String balanceratio) {
		this.balanceratio = balanceratio;
	}

	public String getMinborrowmonth() {
		return minborrowmonth;
	}

	public void setMinborrowmonth(String minborrowmonth) {
		this.minborrowmonth = minborrowmonth;
	}

	public String getMaxborrowmonth() {
		return maxborrowmonth;
	}

	public void setMaxborrowmonth(String maxborrowmonth) {
		this.maxborrowmonth = maxborrowmonth;
	}

	public String getMinborrowrate() {
		return minborrowrate;
	}

	public void setMinborrowrate(String minborrowrate) {
		this.minborrowrate = minborrowrate;
	}

	public String getMaxborrowrate() {
		return maxborrowrate;
	}

	public void setMaxborrowrate(String maxborrowrate) {
		this.maxborrowrate = maxborrowrate;
	}

	public String getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(String borrowType) {
		this.borrowType = borrowType;
	}

	public int getAutostatus() {
		return autostatus;
	}

	public void setAutostatus(int autostatus) {
		this.autostatus = autostatus;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

}
