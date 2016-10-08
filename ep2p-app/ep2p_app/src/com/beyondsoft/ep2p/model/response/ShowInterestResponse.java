package com.beyondsoft.ep2p.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.beyondsoft.ep2p.model.response.ShowRedResponse.Result;
import com.beyondsoft.ep2p.model.response.ShowRedResponse.Result.RedListItem;

/**
 * @description 展示加息卷
 */
public class ShowInterestResponse extends BaseResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Result result;

	public class Result {
		private String investNumber;
		private double investValue;
		private String noAlreadyReceiveNumber;
		public ArrayList<InterestListItem> listParConRel;

		public class InterestListItem implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String pid;
			private String activityId;
			private String condId;
			private String condName;

			public String getPid() {
				return pid;
			}

			public void setPid(String pid) {
				this.pid = pid;
			}

			public String getActivityId() {
				return activityId;
			}

			public void setActivityId(String activityId) {
				this.activityId = activityId;
			}

			public String getCondId() {
				return condId;
			}

			public void setCondId(String condId) {
				this.condId = condId;
			}

			public String getCondName() {
				return condName;
			}

			public void setCondName(String condName) {
				this.condName = condName;
			}
		}

		private String validity;
		private String pid;
		private String startDate;
		
		public String getInvestNumber() {
			return investNumber;
		}
		public void setInvestNumber(String investNumber) {
			this.investNumber = investNumber;
		}
		public double getInvestValue() {
			return investValue;
		}
		public void setInvestValue(double investValue) {
			this.investValue = investValue;
		}
		public String getNoAlreadyReceiveNumber() {
			return noAlreadyReceiveNumber;
		}
		public void setNoAlreadyReceiveNumber(String noAlreadyReceiveNumber) {
			this.noAlreadyReceiveNumber = noAlreadyReceiveNumber;
		}
		public ArrayList<InterestListItem> getListParConRel() {
			return listParConRel;
		}
		public void setListParConRel(ArrayList<InterestListItem> listParConRel) {
			this.listParConRel = listParConRel;
		}
		public String getValidity() {
			return validity;
		}
		public void setValidity(String validity) {
			this.validity = validity;
		}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		
		
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}
