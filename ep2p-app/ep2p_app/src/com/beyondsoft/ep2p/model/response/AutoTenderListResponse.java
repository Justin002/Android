package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.AutoTenderListBean;
import com.google.gson.annotations.SerializedName;

/**
 * 自动投标列表接口返回数据
 * 
 * @author hanxiaohui
 *
 */
public class AutoTenderListResponse extends BaseResponse {

	@SerializedName("result")
	private AutoTenderListBean autoTenderListBean;

	public AutoTenderListBean getAutoTenderListBean() {
		return autoTenderListBean;
	}

	public void setAutoTenderListBean(AutoTenderListBean autoTenderListBean) {
		this.autoTenderListBean = autoTenderListBean;
	}

}
