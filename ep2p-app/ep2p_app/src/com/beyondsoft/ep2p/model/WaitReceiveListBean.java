package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class WaitReceiveListBean {

	private Integer tatol;
	@SerializedName("reList")
	private ArrayList<WaitReceiveInfoBean> waitReceiveList;

	public ArrayList<WaitReceiveInfoBean> getWaitReceiveList() {
		return waitReceiveList;
	}

	public void setWaitReceiveList(ArrayList<WaitReceiveInfoBean> waitReceiveList) {
		this.waitReceiveList = waitReceiveList;
	}

	public Integer getTatol() {
		return tatol;
	}

	public void setTatol(Integer tatol) {
		this.tatol = tatol;
	}
	
	
}
