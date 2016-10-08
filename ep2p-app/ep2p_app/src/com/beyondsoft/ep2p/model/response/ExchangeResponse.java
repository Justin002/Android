package com.beyondsoft.ep2p.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ivan.Lu
 * @description 兑换
 */
public class ExchangeResponse extends BaseResponse {
	private Result result;

	public class Result implements Serializable {
		private List<ExchangeDetail> list;
		/** 加息劵有效期 **/
		private String interestTicketValidity;
		public class ExchangeDetail implements Serializable {
			private String pid;
			private String dictContCode;
			private String dictContName;
			/** 兑换物品所需的积分 **/
			private String dictContDesc;
			private String dictId;
			/** 加息劵有效期 **/
			private String interestTicketValidity;

			public String getPid() {
				return pid;
			}

			public void setPid(String pid) {
				this.pid = pid;
			}

			public String getDictContCode() {
				return dictContCode;
			}

			public void setDictContCode(String dictContCode) {
				this.dictContCode = dictContCode;
			}

			public String getDictContName() {
				return dictContName;
			}

			public void setDictContName(String dictContName) {
				this.dictContName = dictContName;
			}

			public String getDictContDesc() {
				return dictContDesc;
			}

			public void setDictContDesc(String dictContDesc) {
				this.dictContDesc = dictContDesc;
			}

			public String getDictId() {
				return dictId;
			}

			public void setDictId(String dictId) {
				this.dictId = dictId;
			}

			public String getInterestTicketValidity() {
				return "有效期 "+interestTicketValidity;
			}

			public void setInterestTicketValidity(String interestTicketValidity) {
				this.interestTicketValidity = interestTicketValidity;
			}
		}

		public String getInterestTicketValidity() {
			return interestTicketValidity;
		}

		public void setInterestTicketValidity(String interestTicketValidity) {
			this.interestTicketValidity = interestTicketValidity;
		}

		public List<ExchangeDetail> getList() {
			return list;
		}

		public void setList(List<ExchangeDetail> list) {
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
