package com.beyondsoft.ep2p.model.response;

import java.io.Serializable;
import java.util.ArrayList;

import com.beyondsoft.ep2p.utils.Config;

/**
 * @description 加息卷明细
 */
public class SelectReceiveInterestDetailResponse extends BaseResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Result result;

	public class Result {
		private String investNumber;
		private String alreadyReceiveNumber;
		public ArrayList<InterestDetailListItem> alreadyReceiveList;

		public class InterestDetailListItem implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String pid;
			private String getTime;
			private double investAwardValue;
			private String invAwaDesc;
			private String phoneNo;
			private String imageUrl;

			public String getPid() {
				return pid;
			}

			public void setPid(String pid) {
				this.pid = pid;
			}

			public String getGetTime() {
				return getTime;
			}

			public void setGetTime(String getTime) {
				this.getTime = getTime;
			}

			public double getInvestAwardValue() {
				return investAwardValue;
			}

			public void setInvestAwardValue(double investAwardValue) {
				this.investAwardValue = investAwardValue;
			}

			public String getInvAwaDesc() {
				return invAwaDesc;
			}

			public void setInvAwaDesc(String invAwaDesc) {
				this.invAwaDesc = invAwaDesc;
			}

			public String getPhoneNo() {
				return phoneNo;
			}

			public void setPhoneNo(String phoneNo) {
				this.phoneNo = phoneNo;
			}

			public String getImageUrl() {
				return Config.getDomainUrl()+"/"+imageUrl;
			}

			public void setImageUrl(String imageUrl) {
				this.imageUrl = imageUrl;
			}
			
		}

		public String getInvestNumber() {
			return investNumber;
		}

		public void setInvestNumber(String investNumber) {
			this.investNumber = investNumber;
		}

		public String getAlreadyReceiveNumber() {
			return alreadyReceiveNumber;
		}

		public void setAlreadyReceiveNumber(String alreadyReceiveNumber) {
			this.alreadyReceiveNumber = alreadyReceiveNumber;
		}

		public ArrayList<InterestDetailListItem> getAlreadyReceiveList() {
			return alreadyReceiveList;
		}

		public void setAlreadyReceiveList(ArrayList<InterestDetailListItem> alreadyReceiveList) {
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
