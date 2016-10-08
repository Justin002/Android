package com.beyondsoft.ep2p.model.response;

public class IncomeCaculatorResponse extends BaseResponse {
	private Result result;
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public class Result{
		private String interest;

		public String getInterest() {
			return interest;
		}

		public void setInterest(String interest) {
			this.interest = interest;
		}
	}
}
