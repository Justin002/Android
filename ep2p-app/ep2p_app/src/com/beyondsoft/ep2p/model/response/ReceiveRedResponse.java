package com.beyondsoft.ep2p.model.response;

import java.util.ArrayList;

import com.beyondsoft.ep2p.model.response.SelectReceiveRedDetailResponse.Result.RedDetailListItem;

/**
 * @description 领取红包
 */
public class ReceiveRedResponse extends BaseResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Result result;

	public class Result {
		private String startDate;
		private String receiveAmount;
		private String alreadyReceiveNumber;
		private String alreadyReceiveAmount;
		private String repNumber;
		private String noAlreadyReceiveNumber;
		private String repAmountTotal;
		private String endDate;
		public ArrayList<RedDetailListItem> alreadyReceiveList;//已领取明细

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getReceiveAmount() {
			return receiveAmount;
		}

		public void setReceiveAmount(String receiveAmount) {
			this.receiveAmount = receiveAmount;
		}

		public String getAlreadyReceiveNumber() {
			return alreadyReceiveNumber;
		}

		public void setAlreadyReceiveNumber(String alreadyReceiveNumber) {
			this.alreadyReceiveNumber = alreadyReceiveNumber;
		}

		public String getAlreadyReceiveAmount() {
			return alreadyReceiveAmount;
		}

		public void setAlreadyReceiveAmount(String alreadyReceiveAmount) {
			this.alreadyReceiveAmount = alreadyReceiveAmount;
		}

		public String getRepNumber() {
			return repNumber;
		}

		public void setRepNumber(String repNumber) {
			this.repNumber = repNumber;
		}

		public String getNoAlreadyReceiveNumber() {
			return noAlreadyReceiveNumber;
		}

		public void setNoAlreadyReceiveNumber(String noAlreadyReceiveNumber) {
			this.noAlreadyReceiveNumber = noAlreadyReceiveNumber;
		}

		public String getRepAmountTotal() {
			return repAmountTotal;
		}

		public void setRepAmountTotal(String repAmountTotal) {
			this.repAmountTotal = repAmountTotal;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public ArrayList<RedDetailListItem> getAlreadyReceiveList() {
			return alreadyReceiveList;
		}

		public void setAlreadyReceiveList(ArrayList<RedDetailListItem> alreadyReceiveList) {
			this.alreadyReceiveList = alreadyReceiveList;
		}
		
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
}
