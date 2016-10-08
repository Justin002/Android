package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ProjectFinishedDetailInfoBean;
import com.google.gson.annotations.SerializedName;

public class ProjectFinishedDetailResponse {

	@SerializedName("result")
	private ProjectFinishedDetailInfoBean projectFinishedDetailInfoBean;

	public ProjectFinishedDetailInfoBean getProjectFinishedDetailInfoBean() {
		return projectFinishedDetailInfoBean;
	}

	public void setProjectFinishedDetailInfoBean(ProjectFinishedDetailInfoBean projectFinishedDetailInfoBean) {
		this.projectFinishedDetailInfoBean = projectFinishedDetailInfoBean;
	}
	
	
}
