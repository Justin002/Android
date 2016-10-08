package com.beyondsoft.ep2p.model.response;

import java.io.Serializable;

/**
 * @author Ivan.Lu
 * @description 积分成功类
 */
public class VipSuccessResponse extends BaseResponse {
	private Result result;
	public class Result implements Serializable{
		private String vipEndTime;
		private String dictContName;
		public String getVipEndTime() {
			return vipEndTime;
		}

		public void setVipEndTime(String vipEndTime) {
			this.vipEndTime = vipEndTime;
		}

		public String getDictContName() {
			return dictContName;
		}

		public void setDictContName(String dictContName) {
			this.dictContName = dictContName;
		}
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
}
