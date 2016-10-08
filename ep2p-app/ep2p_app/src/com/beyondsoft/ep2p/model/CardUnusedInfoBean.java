package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class CardUnusedInfoBean implements Serializable{

	private String pid;
	private int cardType;
	private int useStatus;
	private double cardQuota;
	private String cardDesc;
	
	public int getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(int useStatus) {
		this.useStatus = useStatus;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public int getCardType() {
		return cardType;
	}
	public void setCardType(int cardType) {
		this.cardType = cardType;
	}
	
	public double getCardQuota() {
		return cardQuota;
	}
	public void setCardQuota(double cardQuota) {
		this.cardQuota = cardQuota;
	}
	public String getCardDesc() {
		return cardDesc;
	}
	public void setCardDesc(String cardDesc) {
		this.cardDesc = cardDesc;
	}
	
	
}
