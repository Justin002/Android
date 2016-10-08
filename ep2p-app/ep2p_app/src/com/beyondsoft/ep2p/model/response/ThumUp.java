package com.beyondsoft.ep2p.model.response;

/**
 * @author Ivan.Lu
 * @description 点赞返回类
 */
public class ThumUp extends BaseResponse {
	
	private static final long serialVersionUID = -2563369946686625437L;
	private ThumpUpData result;
	public class ThumpUpData{
		private int praiseNum;
		public int getPraiseNum() {
			return praiseNum;
		}

		public void setPraiseNum(int praiseNum) {
			this.praiseNum = praiseNum;
		}
	}
	public ThumpUpData getResult() {
		return result;
	}
	public void setResult(ThumpUpData result) {
		this.result = result;
	}
}
