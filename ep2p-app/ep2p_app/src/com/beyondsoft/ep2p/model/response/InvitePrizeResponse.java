package com.beyondsoft.ep2p.model.response;

/**
 * @author Ivan.Lu
 * @description 我的推荐邀请码
 */
public class InvitePrizeResponse extends BaseResponse {
	private Result result;
	public class Result{
		private String referralCode;

		public String getReferralCode() {
			return referralCode;
		}

		public void setReferralCode(String referralCode) {
			this.referralCode = referralCode;
		}
	}
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
}
