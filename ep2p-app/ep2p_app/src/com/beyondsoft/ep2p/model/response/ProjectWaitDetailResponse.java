package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.ProjectWaitDetailResult;
import com.google.gson.annotations.SerializedName;

public class ProjectWaitDetailResponse {

	
	@SerializedName("result")
	private ProjectWaitDetailResult projectWaitDetailResult;

	public ProjectWaitDetailResult getProjectWaitDetailResult() {
		return projectWaitDetailResult;
	}

	public void setProjectWaitDetailResult(ProjectWaitDetailResult projectWaitDetailResult) {
		this.projectWaitDetailResult = projectWaitDetailResult;
	}
	
	
}
