package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ProjectWaitInfoListBean;
import com.google.gson.annotations.SerializedName;

public class ProjectWaitListResponse extends BaseResponse{

	@SerializedName("result")
	private ProjectWaitInfoListBean projectWaitInfoListBean;

	public ProjectWaitInfoListBean getProjectWaitInfoListBean() {
		return projectWaitInfoListBean;
	}

	public void setProjectWaitInfoListBean(ProjectWaitInfoListBean projectWaitInfoListBean) {
		this.projectWaitInfoListBean = projectWaitInfoListBean;
	}
	
	
}