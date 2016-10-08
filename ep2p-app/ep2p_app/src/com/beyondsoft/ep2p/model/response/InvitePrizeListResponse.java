package com.beyondsoft.ep2p.model.response;

import java.util.List;

/**
 * @author Ivan.Lu
 * @description 我的邀请列表
 */
public class InvitePrizeListResponse extends BaseResponse {
	private Result result;
	public class Result{
		private List<InviteList> list;
		public class InviteList{
			private String customerName;
			private String createTime;
			public String getCustomerName() {
				return customerName;
			}
			public void setCustomerName(String customerName) {
				this.customerName = customerName;
			}
			public String getCreateTime() {
				return createTime;
			}
			public void setCreateTime(String createTime) {
				this.createTime = createTime;
			}
		}
		public List<InviteList> getList() {
			return list;
		}
		public void setList(List<InviteList> list) {
			this.list = list;
		}
	}
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
}
