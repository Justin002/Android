package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.CreditorRightsListBean;
import com.google.gson.annotations.SerializedName;

/**
 * 债权列表接口返回数据
 * 
 * @author hardy.zhou
 *
 */
public class CreditorRightsListResponse extends BaseResponse {

	@SerializedName("result")
	private CreditorRightsListBean creditorRightsListBean;

	public CreditorRightsListBean getCreditorRightsListBean() {
		return creditorRightsListBean;
	}

	public void setCreditorRightsListBean(CreditorRightsListBean creditorRightsListBean) {
		this.creditorRightsListBean = creditorRightsListBean;
	}

}
