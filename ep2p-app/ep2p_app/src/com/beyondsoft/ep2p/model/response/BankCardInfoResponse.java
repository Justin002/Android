package com.beyondsoft.ep2p.model.response;

import java.io.Serializable;

public class BankCardInfoResponse implements Serializable{

	private String reason;
	
	private Result result = null;
	
	
	
	public String getReason() {
		return reason;
	}



	public void setReason(String reason) {
		this.reason = reason;
	}



	public Result getResult() {
		return result;
	}



	public void setResult(Result result) {
		this.result = result;
	}



	public class Result
	{
		private String card;
		private String province;
		private String city;
		private String bank;
		private String type;
		private String cardname;
		private String tel;
		public String getCard() {
			return card;
		}
		public void setCard(String card) {
			this.card = card;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getBank() {
			return bank;
		}
		public void setBank(String bank) {
			this.bank = bank;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getCardname() {
			return cardname;
		}
		public void setCardname(String cardname) {
			this.cardname = cardname;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		
		
	}
}
