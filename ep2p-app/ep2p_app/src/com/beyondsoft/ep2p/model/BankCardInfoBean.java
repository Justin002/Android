package com.beyondsoft.ep2p.model;

import java.io.Serializable;

/**
 * 银行卡信息
 * 
 * @author hardy.zhou
 *
 */
public class BankCardInfoBean implements Serializable {

	private static final long serialVersionUID = 2505844923766188793L;

	private String pid;
	private String belongingBank;
	private String bankcardNo;
	private String belongingProvince;
	private String openAddress;
	private String customerId;
	private String status;
	private String createTime;
	private int index;
	
	private String isWithdrawals;//可提现
	private String quickPayment;//快捷支付

	
	
	public String getIsWithdrawals() {
		return isWithdrawals;
	}

	public void setIsWithdrawals(String isWithdrawals) {
		this.isWithdrawals = isWithdrawals;
	}

	public String getQuickPayment() {
		return quickPayment;
	}

	public void setQuickPayment(String quickPayment) {
		this.quickPayment = quickPayment;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getBelongingBank() {
		return belongingBank;
	}

	public void setBelongingBank(String belongingBank) {
		this.belongingBank = belongingBank;
	}

	public String getBankcardNo() {
		return bankcardNo;
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}

	public String getBelongingProvince() {
		return belongingProvince;
	}

	public void setBelongingProvince(String belongingProvince) {
		this.belongingProvince = belongingProvince;
	}

	public String getOpenAddress() {
		return openAddress;
	}

	public void setOpenAddress(String openAddress) {
		this.openAddress = openAddress;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
