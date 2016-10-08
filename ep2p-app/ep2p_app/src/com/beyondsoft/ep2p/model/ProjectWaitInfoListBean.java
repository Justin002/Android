package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ProjectWaitInfoListBean {

	private int tatol;
	@SerializedName("reList")
	private ArrayList<ProjectWaitInfoBean> projectWaitInfoList;
    
	public int getTatol() {
		return tatol;
	}
	public void setTatol(int tatol) {
		this.tatol = tatol;
	}
	public ArrayList<ProjectWaitInfoBean> getProjectWaitInfoList() {
		return projectWaitInfoList;
	}
	public void setProjectWaitInfoList(ArrayList<ProjectWaitInfoBean> projectWaitInfoList) {
		this.projectWaitInfoList = projectWaitInfoList;
	}
	
	
}
