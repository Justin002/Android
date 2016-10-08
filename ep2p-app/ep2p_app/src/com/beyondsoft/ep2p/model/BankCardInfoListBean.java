package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * 银行卡信息列表实体
 * 
 * @author hardy.zhou
 *
 */
public class BankCardInfoListBean {

	@SerializedName("list")
	private ArrayList<BankCardInfoBean> bankCardInfoBeanList;

	public ArrayList<BankCardInfoBean> getBankCardInfoBeanList() {
		return bankCardInfoBeanList;
	}

	public void setBankCardInfoBeanList(ArrayList<BankCardInfoBean> bankCardInfoBeanList) {
		this.bankCardInfoBeanList = bankCardInfoBeanList;
	}

}
