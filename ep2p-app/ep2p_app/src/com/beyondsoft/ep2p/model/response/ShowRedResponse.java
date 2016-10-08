package com.beyondsoft.ep2p.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 展示红包
 */
public class ShowRedResponse extends BaseResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Result result;
	public class Result {
		private String startDate;
		private String alreadyReceiveNumber;
		private String alreadyReceiveAmount;
		private String repAmountTotal;
		private String noAlreadyReceiveNumber;
		private String repNumber;
		public ArrayList<RedListItem> listParConRel;
			
			public class RedListItem implements  Serializable{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				private String  pid;
				private String activityId;
				private String  condId;
				private String  condName;
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
		private String endDate;
		private String pid;
		private String receiveAmount;
		public ArrayList<AlreadyReceiveRedListItem> alreadyReceiveList;
		
			public class AlreadyReceiveRedListItem implements  Serializable{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				private String  pid;
				private String  getTime;
				private String  useStatus;
				private String  imageUrl;
				private String  phoneNo;
				private String  remark;
				private String  getAmount;
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
				public String getUseStatus() {
					return useStatus;
				}
				public void setUseStatus(String useStatus) {
					this.useStatus = useStatus;
				}
				public String getImageUrl() {
					return imageUrl;
				}
				public void setImageUrl(String imageUrl) {
					this.imageUrl = imageUrl;
				}
				public String getPhoneNo() {
					return phoneNo;
				}
				public void setPhoneNo(String phoneNo) {
					this.phoneNo = phoneNo;
				}
				public String getRemark() {
					return remark;
				}
				public void setRemark(String remark) {
					this.remark = remark;
				}
				public String getGetAmount() {
					return getAmount;
				}
				public void setGetAmount(String getAmount) {
					this.getAmount = getAmount;
				}
				
			}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
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

		public String getRepAmountTotal() {
			return repAmountTotal;
		}

		public void setRepAmountTotal(String repAmountTotal) {
			this.repAmountTotal = repAmountTotal;
		}

		public String getNoAlreadyReceiveNumber() {
			return noAlreadyReceiveNumber;
		}

		public void setNoAlreadyReceiveNumber(String noAlreadyReceiveNumber) {
			this.noAlreadyReceiveNumber = noAlreadyReceiveNumber;
		}

		public String getRepNumber() {
			return repNumber;
		}

		public void setRepNumber(String repNumber) {
			this.repNumber = repNumber;
		}

		public ArrayList<RedListItem> getListParConRel() {
			return listParConRel;
		}

		public void setListParConRel(ArrayList<RedListItem> listParConRel) {
			this.listParConRel = listParConRel;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getReceiveAmount() {
			return receiveAmount;
		}

		public void setReceiveAmount(String receiveAmount) {
			this.receiveAmount = receiveAmount;
		}

		public ArrayList<AlreadyReceiveRedListItem> getAlreadyReceiveList() {
			return alreadyReceiveList;
		}

		public void setAlreadyReceiveList(ArrayList<AlreadyReceiveRedListItem> alreadyReceiveList) {
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
