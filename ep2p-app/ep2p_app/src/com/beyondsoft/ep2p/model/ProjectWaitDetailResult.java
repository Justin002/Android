package com.beyondsoft.ep2p.model;

import com.google.gson.annotations.SerializedName;

public class ProjectWaitDetailResult {

	@SerializedName("redueinm")
	private ProjectWaitDetailInfoBean projectWaitDetailInfoBean;

	public ProjectWaitDetailInfoBean getProjectWaitDetailInfoBean() {
		return projectWaitDetailInfoBean;
	}

	public void setProjectWaitDetailInfoBean(ProjectWaitDetailInfoBean projectWaitDetailInfoBean) {
		this.projectWaitDetailInfoBean = projectWaitDetailInfoBean;
	}
	
	
}
