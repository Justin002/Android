package com.beyondsoft.ep2p.model.response;

import java.util.List;


/**
 * @author Ivan.Lu
 * @description VIP 购买期限
 */
public class BuyVipDuringResponse extends BaseResponse {
	
	private Result result;
	public class Result{
		private List<BuySelect> buySelect;
		
		public class BuySelect{
			private String pid;
			private String dictContCode;
			private String dictContName;
			private String dictContDesc;
			private String dictId;
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
		}

		public List<BuySelect> getBuySelect() {
			return buySelect;
		}

		public void setBuySelect(List<BuySelect> buySelect) {
			this.buySelect = buySelect;
		}
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
}
