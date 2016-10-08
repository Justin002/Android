package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class NewAddRechargeBean implements Serializable{

	private static final long serialVersionUID = 3455187657814282716L;

	private String noOrder;
	private boolean addRechargeResult;
	public String getNoOrder() {
		return noOrder;
	}
	public void setNoOrder(String noIrder) {
		this.noOrder = noIrder;
	}
	public boolean isAddRechargeResult() {
		return addRechargeResult;
	}
	public void setAddRechargeResult(boolean addRechargeResult) {
		this.addRechargeResult = addRechargeResult;
	}
	
}
