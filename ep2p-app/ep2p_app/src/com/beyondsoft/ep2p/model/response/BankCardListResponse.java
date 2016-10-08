package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.BankCardInfoListBean;
import com.google.gson.annotations.SerializedName;

/**
 * 银行卡列表接口返回数据
 * 
 * @author hardy.zhou
 *
 */
public class BankCardListResponse extends BaseResponse {

	@SerializedName("result")
	private BankCardInfoListBean bankCardInfoListBean;

	public BankCardInfoListBean getBankCardInfoListBean() {
		return bankCardInfoListBean;
	}

	public void setBankCardInfoListBean(BankCardInfoListBean bankCardInfoListBean) {
		this.bankCardInfoListBean = bankCardInfoListBean;
	}

}
