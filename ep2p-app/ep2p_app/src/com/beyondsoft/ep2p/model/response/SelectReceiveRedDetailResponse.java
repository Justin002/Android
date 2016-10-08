package com.beyondsoft.ep2p.model.response;

import java.io.Serializable;
import java.util.ArrayList;

import com.beyondsoft.ep2p.utils.Config;

/**
 * @description 红包明细
 */
public class SelectReceiveRedDetailResponse extends BaseResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Result result;

	public class Result {
		private String alreadyReceiveNumber;//已领取数
		private String alreadyReceiveAmount;//已领取总额
		private String repNumber;// 总数量
		private String repAmountTotal;//红包总额
		public ArrayList<RedDetailListItem> alreadyReceiveList;//已领取明细

		public class RedDetailListItem implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String pid;
			private String getTime;
			private String useStatus;
			private String imageUrl;
			private String phoneNo;
			private String remark;
			private String getAmount;

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

			public String getUseStatus() {
				return useStatus;
			}

			public void setUseStatus(String useStatus) {
				this.useStatus = useStatus;
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

		public String getRepAmountTotal() {
			return repAmountTotal;
		}

		public void setRepAmountTotal(String repAmountTotal) {
			this.repAmountTotal = repAmountTotal;
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
