package com.beyondsoft.ep2p.model.response;

/**
 * @author Ivan.Lu
 * @description 更新头像
 */
public class UpdateHeadResponse extends BaseResponse {
	private Result result;
	public class Result{
		private Customer customer;
		public class Customer{
			private String imageUrl;

			public String getImageUrl() {
				return imageUrl;
			}

			public void setImageUrl(String imageUrl) {
				this.imageUrl = imageUrl;
			}
		}
		public Customer getCustomer() {
			return customer;
		}
		public void setCustomer(Customer customer) {
			this.customer = customer;
		}
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
}
