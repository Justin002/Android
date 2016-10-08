package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class AddBankCardInfoBean implements Serializable{

	private String bankcardName;
	private String bankcardNo;
	private String belongingBank;
	private String belongingProvince;
	private String openAddress;
	public String getBankcardName() {
		return bankcardName;
	}
	public void setBankcardName(String bankcardName) {
		this.bankcardName = bankcardName;
	}
	public String getBankcardNo() {
		return bankcardNo;
	}
	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	public String getBelongingBank() {
		return belongingBank;
	}
	public void setBelongingBank(String belongingBank) {
		this.belongingBank = belongingBank;
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
	
	
}
