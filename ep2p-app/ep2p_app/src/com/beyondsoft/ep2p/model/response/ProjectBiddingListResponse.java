package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ProjectBiddingListBean;
import com.google.gson.annotations.SerializedName;

public class ProjectBiddingListResponse extends BaseResponse{

	@SerializedName("result")
	private ProjectBiddingListBean projectBiddinglistBean;

	public ProjectBiddingListBean getProjectBiddinglistBean() {
		return projectBiddinglistBean;
	}

	public void setProjectBiddinglistBean(ProjectBiddingListBean projectBiddinglistBean) {
		this.projectBiddinglistBean = projectBiddinglistBean;
	}
	
	
}
