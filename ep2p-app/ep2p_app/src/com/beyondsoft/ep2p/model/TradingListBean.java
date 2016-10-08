package com.beyondsoft.ep2p.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * 自动投标列表实体
 * 
 * @author hanxiaohui
 *
 */

public class TradingListBean implements Serializable {

	@SerializedName("list")
	private ArrayList<TradingInfoBean> tradingInfoBeanList;

	public ArrayList<TradingInfoBean> getTradingInfoBeanList() {
		return tradingInfoBeanList;
	}

	public void setTradingInfoBeanList(ArrayList<TradingInfoBean> tradingInfoBeanList) {
		this.tradingInfoBeanList = tradingInfoBeanList;
	}

}
