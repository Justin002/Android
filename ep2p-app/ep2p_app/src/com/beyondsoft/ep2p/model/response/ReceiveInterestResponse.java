package com.beyondsoft.ep2p.model.response;

/**
 * @description 领取加息卷
 */
public class ReceiveInterestResponse extends BaseResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Result result;

	public class Result {
		private String alreadyReceiveNumber;
		private String investNumber;
		private String validity;
		private String noAlreadyReceiveNumber;
		private String startDate;
		private String endDate;
		public String getAlreadyReceiveNumber() {
			return alreadyReceiveNumber;
		}
		public void setAlreadyReceiveNumber(String alreadyReceiveNumber) {
			this.alreadyReceiveNumber = alreadyReceiveNumber;
		}
		public String getInvestNumber() {
			return investNumber;
		}
		public void setInvestNumber(String investNumber) {
			this.investNumber = investNumber;
		}
		public String getValidity() {
			return validity;
		}
		public void setValidity(String validity) {
			this.validity = validity;
		}
		public String getNoAlreadyReceiveNumber() {
			return noAlreadyReceiveNumber;
		}
		public void setNoAlreadyReceiveNumber(String noAlreadyReceiveNumber) {
			this.noAlreadyReceiveNumber = noAlreadyReceiveNumber;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
}
