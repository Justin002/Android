package com.beyondsoft.ep2p.utils;

import android.content.Context;


public class Config {
	private static final EnvironmentType environmentType = EnvironmentType.DELVELOP;
	public static Context mContext;

	/**
	 * 主机的url
	 */
	public static String getDomainUrl() {
		String result = "";
		switch (environmentType) {
		case DELVELOP:
			result="http://183.15.254.86:8081/api";
			break;
		case UAT:
			result="http://183.15.254.53:8081/api";
			break;
		}
		return result;
	}
}