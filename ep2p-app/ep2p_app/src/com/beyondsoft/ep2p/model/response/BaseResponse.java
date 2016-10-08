package com.beyondsoft.ep2p.model.response;

import java.io.Serializable;

/**
 * @author Ivan.Lu
 * @description 返回Response基类,所有Response继承此类
 */
public class BaseResponse implements Serializable {
	/** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    private boolean status;
	private String code;
	private String message;
	private Extension extension;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}

	public class Extension implements Serializable{
		/** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        private String errorType;
		public String getErrorType() {
			return errorType;
		}
		public void setErrorType(String errorType) {
			this.errorType = errorType;
		}
	}
}
