package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ProjectFinishListBean;
import com.google.gson.annotations.SerializedName;

public class ProjectFinishListResponse {

	@SerializedName("result")
	private ProjectFinishListBean projectFinishListBean;

	public ProjectFinishListBean getProjectFinishListBean() {
		return projectFinishListBean;
	}

	public void setProjectFinishListBean(ProjectFinishListBean projectFinishListBean) {
		this.projectFinishListBean = projectFinishListBean;
	}
	
	
}
