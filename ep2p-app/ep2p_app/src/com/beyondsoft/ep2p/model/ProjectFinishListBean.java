package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ProjectFinishListBean {

	private int tatol;
	
	@SerializedName("reList")
	private ArrayList<ProjectFinishInfoBean> projectFinishList;

	public ArrayList<ProjectFinishInfoBean> getProjectFinishList() {
		return projectFinishList;
	}

	public void setProjectFinishList(ArrayList<ProjectFinishInfoBean> projectFinishList) {
		this.projectFinishList = projectFinishList;
	}

	public int getTatol() {
		return tatol;
	}

	public void setTatol(int tatol) {
		this.tatol = tatol;
	}

	

	
	
	
}
