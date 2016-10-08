package com.beyondsoft.ep2p.model.response;


/**
 * @author Ivan.Lu
 * @description 用户基本信息
 */
public class PersonalInfoResponse {
	private Result result;
	public class Result{
		private long systemTime;
		private CustomerVo customerVo;
		public class CustomerVo{
			/**pid**/
			private String pid;
			/**是否VIP   0：否、1：是**/
			private String isVip;
			/**VIP级别**/
			private String vipLevel;
			/** VIP级别名称**/
			private String vipLevelName;
			/**VIP时长**/
			private int vipTime;
			/**VIP显示图片**/
			private String vipIco;
			/**VIP当前级别最高经验值**/
			private int experienceEnd;
			/**VIP当前级别最低经验值**/
			private int experienceStart;
			/**VIP经验值**/
			private int empiricalValue;
			/**可用积分**/
			private int availablePoint;
			/**可用金额**/
			private double availableBalance;
			/**卡券张数**/
			private int cardVoucherCount;
			/**总资产**/
			private double totalAssets;
			/**银行卡张数**/
			private int bankCount;
			/**0 首次支付 非0 不是首次支付**/
			private int isFirstPay;
			/**客户名称**/
			private String customerName;
			/**客户真实姓名**/
			private String sname;
			/**email**/
			private String email;
			/**手机号码**/
			private String phoneNo;
			/**客户头像**/
			private String imageUrl;
			/**签到天数**/
			private int signday;
			/**是否实名认证 1 已认证 2 未认证**/
			private String isAttestation;
			/**是否绑定手机 1 已绑定 2 未绑定**/
			private String isBingPhone;
			/**是否设置交易密码 1 已设置 2 未设置**/
			private String isSetTradePwd;
			/***身份证号*/
			private String identificationNo;
			/**注册时间**/
			private String registrationTime;
			/**充值总金额**/
			private double rechargeAmount;
			/**提现总金额**/
			private double withdrawAmount;
			/**总待收金额**/
			private double dueAmount;
			/**冻结金额**/
			private double freezeAmount;
			/**投标总额**/
			private double tenderAmount;
			/**体验金**/
			private double experienceAmount;
			/**普通资金**/
			private double commonAmount;
			/**充值资金**/
			private double rechargeDetaiAmount;
			/**提现手续费率**/
			private String withdrawalFee;
			
			
			public double getCommonAmount() {
				return commonAmount;
			}
			public void setCommonAmount(double commonAmount) {
				this.commonAmount = commonAmount;
			}
			public double getRechargeDetaiAmount() {
				return rechargeDetaiAmount;
			}
			public void setRechargeDetaiAmount(double rechargeDetaiAmount) {
				this.rechargeDetaiAmount = rechargeDetaiAmount;
			}
			public String getPid() {
				return pid;
			}
			public void setPid(String pid) {
				this.pid = pid;
			}
			public String getIsVip() {
				return isVip;
			}
			public void setIsVip(String isVip) {
				this.isVip = isVip;
			}
			public String getVipLevel() {
				return vipLevel;
			}
			public void setVipLevel(String vipLevel) {
				this.vipLevel = vipLevel;
			}
			public String getVipLevelName() {
				return vipLevelName;
			}
			public void setVipLevelName(String vipLevelName) {
				this.vipLevelName = vipLevelName;
			}
			public int getVipTime() {
				return vipTime;
			}
			public void setVipTime(int vipTime) {
				this.vipTime = vipTime;
			}
			public String getVipIco() {
				return vipIco;
			}
			public void setVipIco(String vipIco) {
				this.vipIco = vipIco;
			}
			public int getExperienceEnd() {
				return experienceEnd;
			}
			public void setExperienceEnd(int experienceEnd) {
				this.experienceEnd = experienceEnd;
			}
			public int getExperienceStart() {
				return experienceStart;
			}
			public void setExperienceStart(int experienceStart) {
				this.experienceStart = experienceStart;
			}
			public int getEmpiricalValue() {
				return empiricalValue;
			}
			public void setEmpiricalValue(int empiricalValue) {
				this.empiricalValue = empiricalValue;
			}
			public int getAvailablePoint() {
				return availablePoint;
			}
			public void setAvailablePoint(int availablePoint) {
				this.availablePoint = availablePoint;
			}
			public double getAvailableBalance() {
				return availableBalance;
			}
			public void setAvailableBalance(double availableBalance) {
				this.availableBalance = availableBalance;
			}
			public int getCardVoucherCount() {
				return cardVoucherCount;
			}
			public void setCardVoucherCount(int cardVoucherCount) {
				this.cardVoucherCount = cardVoucherCount;
			}
			public double getTotalAssets() {
				return totalAssets;
			}
			public void setTotalAssets(double totalAssets) {
				this.totalAssets = totalAssets;
			}
			public int getBankCount() {
				return bankCount;
			}
			public void setBankCount(int bankCount) {
				this.bankCount = bankCount;
			}
			public int getIsFirstPay() {
				return isFirstPay;
			}
			public void setIsFirstPay(int isFirstPay) {
				this.isFirstPay = isFirstPay;
			}
			public String getCustomerName() {
				return customerName;
			}
			public void setCustomerName(String customerName) {
				this.customerName = customerName;
			}
			public String getSname() {
				return sname;
			}
			public void setSname(String sname) {
				this.sname = sname;
			}
			public String getEmail() {
				return email;
			}
			public void setEmail(String email) {
				this.email = email;
			}
			public String getPhoneNo() {
				return phoneNo;
			}
			public void setPhoneNo(String phoneNo) {
				this.phoneNo = phoneNo;
			}
			public String getImageUrl() {
				return imageUrl;
			}
			public void setImageUrl(String imageUrl) {
				this.imageUrl = imageUrl;
			}
			public int getSignday() {
				return signday;
			}
			public void setSignday(int signday) {
				this.signday = signday;
			}
			public String getIsAttestation() {
				return isAttestation;
			}
			public void setIsAttestation(String isAttestation) {
				this.isAttestation = isAttestation;
			}
			public String getIsBingPhone() {
				return isBingPhone;
			}
			public void setIsBingPhone(String isBingPhone) {
				this.isBingPhone = isBingPhone;
			}
			public String getIsSetTradePwd() {
				return isSetTradePwd;
			}
			public void setIsSetTradePwd(String isSetTradePwd) {
				this.isSetTradePwd = isSetTradePwd;
			}
			public String getIdentificationNo() {
				return identificationNo;
			}
			public void setIdentificationNo(String identificationNo) {
				this.identificationNo = identificationNo;
			}
			public String getRegistrationTime() {
				return registrationTime;
			}
			public void setRegistrationTime(String registrationTime) {
				this.registrationTime = registrationTime;
			}
			public double getRechargeAmount() {
				return rechargeAmount;
			}
			public void setRechargeAmount(double rechargeAmount) {
				this.rechargeAmount = rechargeAmount;
			}
			public double getWithdrawAmount() {
				return withdrawAmount;
			}
			public void setWithdrawAmount(double withdrawAmount) {
				this.withdrawAmount = withdrawAmount;
			}
			public double getDueAmount() {
				return dueAmount;
			}
			public void setDueAmount(double dueAmount) {
				this.dueAmount = dueAmount;
			}
			public double getFreezeAmount() {
				return freezeAmount;
			}
			public void setFreezeAmount(double freezeAmount) {
				this.freezeAmount = freezeAmount;
			}
			public double getTenderAmount() {
				return tenderAmount;
			}
			public void setTenderAmount(double tenderAmount) {
				this.tenderAmount = tenderAmount;
			}
			public double getExperienceAmount() {
				return experienceAmount;
			}
			public void setExperienceAmount(double experienceAmount) {
				this.experienceAmount = experienceAmount;
			}
			public String getWithdrawalFee() {
				return withdrawalFee;
			}
			public void setWithdrawalFee(String withdrawalFee) {
				this.withdrawalFee = withdrawalFee;
			}
		}
		public long getSystemTime() {
			return systemTime;
		}
		public void setSystemTime(long systemTime) {
			this.systemTime = systemTime;
		}
		public CustomerVo getCustomerVo() {
			return customerVo;
		}
		public void setCustomerVo(CustomerVo customerVo) {
			this.customerVo = customerVo;
		}
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
}
