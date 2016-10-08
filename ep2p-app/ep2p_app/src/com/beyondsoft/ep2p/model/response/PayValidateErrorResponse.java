package com.beyondsoft.ep2p.model.response;

/**
 * @author Ivan.Lu
 * @description 交易密码错误类
 */
public class PayValidateErrorResponse extends BaseResponse {
	private Result result;
	
	
	
	

	public class Result{
		private int num;
		private int time;
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public long getTime() {
			return time;
		}
		public void setTime(int time) {
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
