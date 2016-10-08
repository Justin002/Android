package com.beyondsoft.ep2p.model.response;


import java.util.List;

import com.google.gson.annotations.SerializedName;


/**
 *新手指引列表
 */
public class NewVoiceGuideResponse extends BaseResponse
{
    private static final long serialVersionUID = 100000L;
    public Result result;
 
    public class Result{
    	private int pageCount;//总条数
    	private List<GuideListItem> result;
    	public class GuideListItem{
            private String pid;
            private String titleName;
            private String coluOrder;
            private int isReading;
            private String isCustomFile;
            private String content;
            private String createTime;
            private String filePid;
            private String fileName;
            private String fileUrl;
            
			public String getPid() {
				return pid;
			}
			public void setPid(String pid) {
				this.pid = pid;
			}
			public String getTitleName() {
				return titleName;
			}
			public void setTitleName(String titleName) {
				this.titleName = titleName;
			}
			public String getColuOrder() {
				return coluOrder;
			}
			public void setColuOrder(String coluOrder) {
				this.coluOrder = coluOrder;
			}
			public String getIsCustomFile() {
				return isCustomFile;
			}
			public void setIsCustomFile(String isCustomFile) {
				this.isCustomFile = isCustomFile;
			}
			public int getIsReading() {
				return isReading;
			}
			public void setIsReading(int isReading) {
				this.isReading = isReading;
			}
			public String getContent() {
				return content;
			}
			public void setContent(String content) {
				this.content = content;
			}
			public String getCreateTime() {
				return createTime;
			}
			public void setCreateTime(String createTime) {
				this.createTime = createTime;
			}
			public String getFilePid() {
				return filePid;
			}
			public void setFilePid(String filePid) {
				this.filePid = filePid;
			}
			public String getFileName() {
				return fileName;
			}
			public void setFileName(String fileName) {
				this.fileName = fileName;
			}
			public String getFileUrl() {
				return fileUrl;
			}
			public void setFileUrl(String fileUrl) {
				this.fileUrl = fileUrl;
			}
    		
        }
		public List<GuideListItem> getResult() {
			return result;
		}
		public void setResult(List<GuideListItem> result) {
			this.result = result;
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
