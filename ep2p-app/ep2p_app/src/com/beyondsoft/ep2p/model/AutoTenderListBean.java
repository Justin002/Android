package com.beyondsoft.ep2p.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * 自动投标列表实体
 * @author hanxiaohui
 *
 */
public class AutoTenderListBean implements Serializable{

	@SerializedName("autolist")
	private ArrayList<AutoTenderInfoBean> autoTenderInfoBeanList;

	public ArrayList<AutoTenderInfoBean> getAutoTenderInfoBeanList() {
		return autoTenderInfoBeanList;
	}

	public void setAutoTenderInfoBeanList(ArrayList<AutoTenderInfoBean> autoTenderInfoBeanList) {
		this.autoTenderInfoBeanList = autoTenderInfoBeanList;
	}
}
