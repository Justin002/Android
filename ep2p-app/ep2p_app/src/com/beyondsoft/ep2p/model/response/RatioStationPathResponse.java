package com.beyondsoft.ep2p.model.response;

import com.beyondsoft.ep2p.model.RatioStationPathDetailBean;
import com.google.gson.annotations.SerializedName;

/**
 *电台列表
 */
public class RatioStationPathResponse extends BaseResponse{
		private static final long serialVersionUID = -1542573057437121227L;
		
		@SerializedName("path")
		private RatioStationPathDetailBean stationPathDetailBean;

		public RatioStationPathDetailBean getStationPathDetailBean() {
			return stationPathDetailBean;
		}

		public void setStationPathDetailBean(RatioStationPathDetailBean stationPathDetailBean) {
			this.stationPathDetailBean = stationPathDetailBean;
		}
		

		
}

