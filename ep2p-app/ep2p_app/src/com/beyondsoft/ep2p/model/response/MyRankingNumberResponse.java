package com.beyondsoft.ep2p.model.response;

import java.util.List;

/**
 *我的投资排行位置
 */
public class MyRankingNumberResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	public Result result = null;
	public class Result extends BaseResponse {
		public int list ;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	
}
