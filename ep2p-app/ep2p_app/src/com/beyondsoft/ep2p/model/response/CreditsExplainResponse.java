package com.beyondsoft.ep2p.model.response;

import java.util.List;

public class CreditsExplainResponse extends BaseResponse {

	private Result result;
	public class Result {
		private List<Detail> list;
		public class Detail {
			/** 积分明细pid **/
			private String pid;
			/** 积分类型 **/
			private String pointType;
			private String customerId;
			private String status;
			/** 发生时间 **/
			private String happenTime;
			/**2，3，4，5 表示消耗，其他是获得**/
			private String pointGetType;
			/** 获得and使用积分 **/
			private int pointValue;
			private int availablePoint;

			public String getPid() {
				return pid;
			}

			public void setPid(String pid) {
				this.pid = pid;
			}

			public String getPointType() {
				return pointType;
			}

			public void setPointType(String pointType) {
				this.pointType = pointType;
			}

			public String getCustomerId() {
				return customerId;
			}

			public void setCustomerId(String customerId) {
				this.customerId = customerId;
			}

			public String getStatus() {
				return status;
			}

			public void setStatus(String status) {
				this.status = status;
			}

			public String getHappenTime() {
				return happenTime;
			}

			public void setHappenTime(String happenTime) {
				this.happenTime = happenTime;
			}
			
			public String getPointGetType() {
				return pointGetType;
			}

			public void setPointGetType(String pointGetType) {
				this.pointGetType = pointGetType;
			}

			public int getPointValue() {
				return pointValue;
			}

			public void setPointValue(int pointValue) {
				this.pointValue = pointValue;
			}

			public int getAvailablePoint() {
				return availablePoint;
			}

			public void setAvailablePoint(int availablePoint) {
				this.availablePoint = availablePoint;
			}
		}
		public List<Detail> getList() {
			return list;
		}
		public void setList(List<Detail> list) {
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
