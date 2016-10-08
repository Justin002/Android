package com.beyondsoft.ep2p.model.response;

import java.util.List;

import com.beyondsoft.ep2p.utils.Config;

/**
 *我的投资排行
 */
public class MyRankingListResponse extends BaseResponse {

    private static final long serialVersionUID = 1L;
    public Result result = null;
    
	public class Result extends BaseResponse {
        public List<MyRankingListItem> list;
    }

    public class MyRankingListItem
    {
    	public int pid;//序号
    	public String page ;//
    	public String limit ;//
    	public String investmentAmount ;//
    	public String createTime ;//
    	public String customerName ;//
    	private String imageUrl ;//
		public String getImageUrl() {
			return Config.getDomainUrl()+"/"+imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
    	
    }
	



}
