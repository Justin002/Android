package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ProjectBiddingListBean {

	private int tatol;
	@SerializedName("reList")
	private ArrayList<ProjectBiddingInfoBean> projectBiddingInfoList;
	
	
	public int getTatol() {
		return tatol;
	}
	public void setTatol(int tatol) {
		this.tatol = tatol;
	}
	public ArrayList<ProjectBiddingInfoBean> getProjectBiddingInfoList() {
		return projectBiddingInfoList;
	}
	public void setProjectBiddingInfoList(ArrayList<ProjectBiddingInfoBean> projectBiddingInfoList) {
		this.projectBiddingInfoList = projectBiddingInfoList;
	}
	
	
}
