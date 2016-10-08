package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class CardUsedInfoBean implements Serializable{

	private String pid;
	private int cardType;
	private double cardQuota;
	private double cardValidity;
	private String cardDesc;
	
	public double getCardValidity() {
		return cardValidity;
	}
	public void setCardValidity(double cardValidity) {
		this.cardValidity = cardValidity;
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
