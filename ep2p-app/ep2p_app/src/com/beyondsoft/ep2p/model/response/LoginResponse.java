package com.beyondsoft.ep2p.model.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse {
	@SerializedName("result")
	private Result result;
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public class Result{
		private String token;
		private long systemTime;
		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public long getSystemTime() {
			return systemTime;
		}

		public void setSystemTime(long systemTime) {
			this.systemTime = systemTime;
		}
	}
}
