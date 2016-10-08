package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ProjectTransferListBean;
import com.google.gson.annotations.SerializedName;

public class ProjectTransferLlistResponse {

	@SerializedName("result")
	private ProjectTransferListBean projectTransferListBean;

	public ProjectTransferListBean getProjectTransferListBean() {
		return projectTransferListBean;
	}
	public void setProjectTransferListBean(ProjectTransferListBean projectTransferListBean) {
		this.projectTransferListBean = projectTransferListBean;
	}
}
