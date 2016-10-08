package com.beyondsoft.ep2p.utils;

import java.io.Serializable;

/**
 * Description: <br>
 * 系统配置参数
 * 
 * @author Simon.Hoo
 * @date 2015年5月21日
 * @version v1.0.0
 * @since 1.6
 */
public class SysConfig implements Serializable {
	private static final long serialVersionUID = -3368037047476194773L;

	/**
	 * 上传根目录
	 */
	// private String uploadRoot;

	/**
	 * 密码加密用的密钥
	 */
	public static String passwordSecretKey = "FIRSTESCF";

	/**
	 * 新用户是否使用默认密码
	 */
	// private boolean newPasswordAsDefault = ;

	/**
	 * 用户是否重置为默认密码
	 */
	// private boolean resetPasswordAsDefault;

	/**
	 * 用户默认密码
	 */
	// private String userDefautlPassword;

	// public String getUploadRoot() {
	// return uploadRoot;
	// }
	//
	// public void setUploadRoot(String uploadRoot) {
	// this.uploadRoot = uploadRoot;
	// }

	public String getPasswordSecretKey() {
		return passwordSecretKey;
	}

	public void setPasswordSecretKey(String passwordSecretKey) {
		SysConfig.passwordSecretKey = passwordSecretKey;
	}

	// public boolean isNewPasswordAsDefault() {
	// return newPasswordAsDefault;
	// }
	//
	// public void setNewPasswordAsDefault(boolean newPasswordAsDefault) {
	// this.newPasswordAsDefault = newPasswordAsDefault;
	// }
	//
	// public boolean isResetPasswordAsDefault() {
	// return resetPasswordAsDefault;
	// }
	//
	// public void setResetPasswordAsDefault(boolean resetPasswordAsDefault) {
	// this.resetPasswordAsDefault = resetPasswordAsDefault;
	// }
	//
	// public String getUserDefautlPassword() {
	// return userDefautlPassword;
	// }
	//
	// public void setUserDefautlPassword(String userDefautlPassword) {
	// this.userDefautlPassword = userDefautlPassword;
	// }

}
