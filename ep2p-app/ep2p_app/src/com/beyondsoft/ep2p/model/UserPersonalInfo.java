package com.beyondsoft.ep2p.model;

import com.beyondsoft.ep2p.utils.Config;

/**
 * @author Ivan.Lu
 * @description 用户基本信息
 */
public class UserPersonalInfo {
	/** pid **/
	private static String pid;
	/**是否VIP   0：否、1：是**/
	private static String isVip;
	/**VIP级别**/
	private static String vipLevel;
	/** VIP级别名称 **/
	private static String vipLevelName;
	/** VIP时长 **/
	private static int vipTime;
	/** VIP显示图片 **/
	private static String vipIco;
	/**VIP当前级别最高经验值**/
	private static int experienceEnd;
	/**VIP当前级别最低经验值**/
	private static int experienceStart;
	/**VIP经验值**/
	private static int empiricalValue;
	/**可用积分**/
	private static int availablePoint;
	/** 可用金额 **/
	private static double availableBalance;
	/**卡券张数**/
	private static int cardVoucherCount;
	/**总资产**/
	private static double totalAssets;
	/**银行卡张数**/
	private static int bankCount;
	/** 绑定银行卡号 **/
	private static String bankcardnumber;
	/**0 首次支付 非0 不是首次支付**/
	private static int isFirstPay;
	/** 客户名称 **/
	private static String customerName;
	/** 客户真实姓名 **/
	private static String sname;
	/** email **/
	private static String email;
	/** 手机号码 **/
	private static String phoneNo;
	/** 客户头像 **/
	private static String imageUrl;
	/** 签到天数 **/
	private static int signday;
	/** 是否实名认证 1 已认证 2 未认证 **/
	private static String isAttestation;
	/** 是否绑定手机 1 已绑定 2 未绑定 **/
	private static String isBingPhone;
	/** 是否设置交易密码 1 已设置 2 未设置 **/
	private static String isSetTradePwd;
	/**身份证号**/
	private static String identificationNo;
	/**注册时间**/
	private static String registrationTime;
	/**充值总金额**/
	private static double rechargeAmount;
	/**提现总金额**/
	private static double withdrawAmount;
	/**总待收金额**/
	private static double dueAmount;
	/**普通资金**/
	private static double commonAmount;
	/**充值资金**/
	private static double rechargeDetaiAmount;
	/**冻结金额**/
	private static double freezeAmount;
	/**投标总额**/
	private static double tenderAmount;
	/**体验金**/
	private static double experienceAmount;
	/**系统当前时间**/
	private static long systemTime;
	/**提现手续费率**/
	private static String withdrawalFee;
	/**是否获取到用户信息**/
	private static boolean isGetUserInfo=false;
	
	public static String getPid() {
		return pid;
	}
	public static void setPid(String pid) {
		UserPersonalInfo.pid = pid;
	}
	public static String getIsVip() {
		return isVip;
	}
	public static void setIsVip(String isVip) {
		UserPersonalInfo.isVip = isVip;
	}
	public static String getVipLevel() {
		if(vipLevel==null||"".equals(vipLevel)){
			return "0";
		}
		return vipLevel;
	}
	public static void setVipLevel(String vipLevel) {
		UserPersonalInfo.vipLevel = vipLevel;
	}
	public static String getVipLevelName() {
		if(vipLevelName==null||"".equals(vipLevelName)){
			return "VIP";
		}
		return vipLevelName;
	}
	public static void setVipLevelName(String vipLevelName) {
		UserPersonalInfo.vipLevelName = vipLevelName;
	}
	public static int getVipTime() {
		return vipTime;
	}
	public static void setVipTime(int vipTime) {
		UserPersonalInfo.vipTime = vipTime;
	}
	public static String getVipIco() {
		return vipIco;
	}
	public static void setVipIco(String vipIco) {
		UserPersonalInfo.vipIco = vipIco;
	}
	public static int getExperienceEnd() {
		return experienceEnd;
	}
	public static void setExperienceEnd(int experienceEnd) {
		UserPersonalInfo.experienceEnd = experienceEnd;
	}
	public static int getExperienceStart() {
		return experienceStart;
	}
	public static void setExperienceStart(int experienceStart) {
		UserPersonalInfo.experienceStart = experienceStart;
	}
	public static int getEmpiricalValue() {
		return empiricalValue;
	}
	public static void setEmpiricalValue(int empiricalValue) {
		UserPersonalInfo.empiricalValue = empiricalValue;
	}
	public static int getAvailablePoint() {
		return availablePoint;
	}
	public static void setAvailablePoint(int availablePoint) {
		UserPersonalInfo.availablePoint = availablePoint;
	}
	public static double getAvailableBalance() {
		return availableBalance;
	}
	public static void setAvailableBalance(double availableBalance) {
		UserPersonalInfo.availableBalance = availableBalance;
	}
	public static int getCardVoucherCount() {
		return cardVoucherCount;
	}
	public static void setCardVoucherCount(int cardVoucherCount) {
		UserPersonalInfo.cardVoucherCount = cardVoucherCount;
	}
	public static double getTotalAssets() {
		return totalAssets;
	}
	public static void setTotalAssets(double totalAssets) {
		UserPersonalInfo.totalAssets = totalAssets;
	}
	public static int getBankCount() {
		return bankCount;
	}
	public static void setBankCount(int bankCount) {
		UserPersonalInfo.bankCount = bankCount;
	}
	public static int getIsFirstPay() {
		return isFirstPay;
	}
	public static void setIsFirstPay(int isFirstPay) {
		UserPersonalInfo.isFirstPay = isFirstPay;
	}
	public static String getCustomerName() {
		if(customerName==null||"".equals(customerName)){
			return phoneNo;
		}
		return customerName;
	}
	public static String getCustomerName2() {
		return customerName;
	}
	
	public static String getCustomerName3() {
		if(customerName==null||"".equals(customerName)){
			return "请设置用户名";
		}
		return customerName;
	}
	public static void setCustomerName(String customerName) {
		UserPersonalInfo.customerName = customerName;
	}
	public static String getSname() {
		return sname;
	}
	public static void setSname(String sname) {
		UserPersonalInfo.sname = sname;
	}
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		UserPersonalInfo.email = email;
	}
	public static String getPhoneNo() {
		return phoneNo;
	}
	public static void setPhoneNo(String phoneNo) {
		UserPersonalInfo.phoneNo = phoneNo;
	}
	public static String getImageUrl() {
		return Config.getDomainUrl()+"/"+imageUrl;
	}
	public static void setImageUrl(String imageUrl) {
		UserPersonalInfo.imageUrl = imageUrl;
	}
	public static int getSignday() {
		return signday;
	}
	public static void setSignday(int signday) {
		UserPersonalInfo.signday = signday;
	}
	public static String getIsAttestation() {
		return isAttestation;
	}
	public static void setIsAttestation(String isAttestation) {
		UserPersonalInfo.isAttestation = isAttestation;
	}
	public static String getIsBingPhone() {
		return isBingPhone;
	}
	public static void setIsBingPhone(String isBingPhone) {
		UserPersonalInfo.isBingPhone = isBingPhone;
	}
	public static String getIsSetTradePwd() {
		return isSetTradePwd;
	}
	public static void setIsSetTradePwd(String isSetTradePwd) {
		UserPersonalInfo.isSetTradePwd = isSetTradePwd;
	}
	public static String getIdentificationNo() {
		return identificationNo;
	}
	public static void setIdentificationNo(String identificationNo) {
		UserPersonalInfo.identificationNo = identificationNo;
	}
	public static String getRegistrationTime() {
		return registrationTime;
	}
	public static void setRegistrationTime(String registrationTime) {
		UserPersonalInfo.registrationTime = registrationTime;
	}
	public static double getRechargeAmount() {
		return rechargeAmount;
	}
	public static void setRechargeAmount(double rechargeAmount) {
		UserPersonalInfo.rechargeAmount = rechargeAmount;
	}
	public static double getWithdrawAmount() {
		return withdrawAmount;
	}
	public static void setWithdrawAmount(double withdrawAmount) {
		UserPersonalInfo.withdrawAmount = withdrawAmount;
	}
	public static double getDueAmount() {
		return dueAmount;
	}
	public static void setDueAmount(double dueAmount) {
		UserPersonalInfo.dueAmount = dueAmount;
	}
	public static double getFreezeAmount() {
		return freezeAmount;
	}
	public static void setFreezeAmount(double freezeAmount) {
		UserPersonalInfo.freezeAmount = freezeAmount;
	}
	public static double getTenderAmount() {
		return tenderAmount;
	}
	public static void setTenderAmount(double tenderAmount) {
		UserPersonalInfo.tenderAmount = tenderAmount;
	}
	public static double getExperienceAmount() {
		return experienceAmount;
	}
	public static void setExperienceAmount(double experienceAmount) {
		UserPersonalInfo.experienceAmount = experienceAmount;
	}
	public static long getSystemTime() {
		return systemTime;
	}
	public static void setSystemTime(long systemTime) {
		UserPersonalInfo.systemTime = systemTime;
	}
	public static String getBankcardnumber() {
		return bankcardnumber;
	}
	public static void setBankcardnumber(String bankcardnumber) {
		UserPersonalInfo.bankcardnumber = bankcardnumber;
	}
	public static double getCommonAmount() {
		return commonAmount;
	}
	public static void setCommonAmount(double commonAmount) {
		UserPersonalInfo.commonAmount = commonAmount;
	}
	public static double getRechargeDetaiAmount() {
		return rechargeDetaiAmount;
	}
	public static void setRechargeDetaiAmount(double rechargeDetaiAmount) {
		UserPersonalInfo.rechargeDetaiAmount = rechargeDetaiAmount;
	}
	public static String getWithdrawalFee() {
		return withdrawalFee;
	}
	public static void setWithdrawalFee(String withdrawalFee) {
		UserPersonalInfo.withdrawalFee = withdrawalFee;
	}
	public static boolean isGetUserInfo() {
		return isGetUserInfo;
	}
	public static void setGetUserInfo(boolean isGetUserInfo) {
		UserPersonalInfo.isGetUserInfo = isGetUserInfo;
	}
}
