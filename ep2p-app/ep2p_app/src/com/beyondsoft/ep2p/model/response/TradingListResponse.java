package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.TradingListBean;
import com.google.gson.annotations.SerializedName;

/**
 * 交易记录列表接口返回数据
 * 
 * @author hanxiaohui
 *
 */

public class TradingListResponse extends BaseResponse {
	
	@SerializedName("result")
	
	private TradingListBean tradingListBean;

	public TradingListBean getTradingListBean() {
		return tradingListBean;
	}

	public void setTradingListBean(TradingListBean tradingListBean) {
		this.tradingListBean = tradingListBean;
	}
	
	
	
}
