package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.NewAddRechargeBean;
import com.beyondsoft.ep2p.model.response.BorrowerInformationResponse.Result;
import com.google.gson.annotations.SerializedName;

/**
 * 新增充值记录接口返回数据
 * 
 * @author hanxiaohui
 *
 */
public class NewAddRechargeResponse extends BaseResponse {

	@SerializedName("result")
	private NewAddRechargeBean newAddRechargeBean;

	public NewAddRechargeBean getNewAddRechargeBean() {
		return newAddRechargeBean;
	}

	public void setNewAddRechargeBean(NewAddRechargeBean newAddRechargeBean) {
		this.newAddRechargeBean = newAddRechargeBean;
	}
	
}
