package com.beyondsoft.ep2p.model.request;

/**
 * @author Ivan.Lu
 * @description 登录请求类
 */
public class LoginRequest {
	/**登录名**/
	private String loginName;
	/**登录密码**/
	private String password;
	/**是否免验证登录     no:需要验证      yes:免验证**/
	private String loginFlag;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLoginFlag() {
		return loginFlag;
	}
	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}
	
}
