package com.beyondsoft.ep2p.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * 债权列表实体
 * 
 * @author hardy.zhou
 *
 */
public class CreditorRightsListBean {

	private int pageCount;
	@SerializedName("list")
	private ArrayList<CreditorRightsInfoBean> creditorRightsInfoBeanList;

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public ArrayList<CreditorRightsInfoBean> getCreditorRightsInfoBeanList() {
		return creditorRightsInfoBeanList;
	}

	public void setCreditorRightsInfoBeanList(ArrayList<CreditorRightsInfoBean> creditorRightsInfoBeanList) {
		this.creditorRightsInfoBeanList = creditorRightsInfoBeanList;
	}

}
