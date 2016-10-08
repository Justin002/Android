package com.beyondsoft.ep2p.model.response;

/**
 * @description bbs url
 */
public class GetBbsUrlResponse extends BaseResponse {
	private Result result;
	public class Result{
		private String bbsAddress;
		private String bbsResult;

		public String getBbsUrl() {
			return bbsAddress;
		}

		public String getBbsAddress() {
			return bbsAddress;
		}

		public void setBbsAddress(String bbsAddress) {
			this.bbsAddress = bbsAddress;
		}

		public String getBbsResult() {
			return bbsResult;
		}

		public void setBbsResult(String bbsResult) {
			this.bbsResult = bbsResult;
		}

		public void setBbsUrl(String bbsAddress) {
			this.bbsAddress = bbsAddress;
		}
	}
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
}
