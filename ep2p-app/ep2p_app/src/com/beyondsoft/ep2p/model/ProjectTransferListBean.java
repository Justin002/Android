package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ProjectTransferListBean {

	@SerializedName("reList")
	private ArrayList<ProjectTransferInfoBean> projectTransferList;
	
	private int tatol;

	public ArrayList<ProjectTransferInfoBean> getProjectTransferList() {
		return projectTransferList;
	}

	public void setProjectTransferList(ArrayList<ProjectTransferInfoBean> projectTransferList) {
		this.projectTransferList = projectTransferList;
	}

	public int getTatol() {
		return tatol;
	}

	public void setTatol(int tatol) {
		this.tatol = tatol;
	}


	
	
	
}
