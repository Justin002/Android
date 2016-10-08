package com.beyondsoft.ep2p.model.response;

/**
 * @author Ivan.Lu
 * @description 验证交易密码类
 */
public class ValidatePayPwdResponse extends BaseResponse{
	
	private Result result;
	
	public class Result{
		private String key;
		private int num;
		private long time;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public long getTime() {
			return time;
		}
		public void setTime(long time) {
			this.time = time;
		}
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
