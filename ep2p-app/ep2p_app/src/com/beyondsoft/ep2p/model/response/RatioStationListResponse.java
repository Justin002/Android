package com.beyondsoft.ep2p.model.response;


import java.io.Serializable;
import java.util.List;

import com.beyondsoft.ep2p.utils.Config;


/**
 *电台列表
 */
public class RatioStationListResponse extends BaseResponse
{
    private static final long serialVersionUID = 100000L;
    public Result result;
 
    public class Result implements Serializable{
    	private int pageCount;
    	private List<RadioListItem> list;
    	public class RadioListItem implements Serializable{
            private String pid;
            private String programTitle;
            private String pictureFileId;
            private String programType;
            private String createTime;
            private int listenNum;
            private int praiseNum;
    		public String getPid() {
    			return pid;
    		}
    		public void setPid(String pid) {
    			this.pid = pid;
    		}
    		public String getProgramTitle() {
    			return programTitle;
    		}
    		public void setProgramTitle(String programTitle) {
    			this.programTitle = programTitle;
    		}
    		public String getPictureFileId() {
    			return Config.getDomainUrl()+"/"+pictureFileId;
    			//return pictureFileId;
    		}
    		public void setPictureFileId(String pictureFileId) {
    			this.pictureFileId = pictureFileId;
    		}
    		public String getProgramType() {
    			return Config.getDomainUrl()+"/"+programType;
    		}
    		public void setProgramType(String programType) {
    			this.programType = programType;
    		}
    		public String getCreateTime() {
    			return createTime;
    		}
    		public void setCreateTime(String createTime) {
    			this.createTime = createTime;
    		}
    		public int getListenNum() {
    			return listenNum;
    		}
    		public void setListenNum(int listenNum) {
    			this.listenNum = listenNum;
    		}
    		public int getPraiseNum() {
    			return praiseNum;
    		}
    		public void setPraiseNum(int praiseNum) {
    			this.praiseNum = praiseNum;
    		}    
        }
		public List<RadioListItem> getList() {
			return list;
		}
		public void setList(List<RadioListItem> list) {
			this.list = list;
		}
		public int getPageCount() {
			return pageCount;
		}
		public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
		}
    }

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
