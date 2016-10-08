package com.beyondsoft.ep2p.utils;

/**
 * 存放所有的url
 * 
 * @author Ivan.Lu
 */
public class URL {

	/** 注册 */
	public static final String URL_REGISTER = Config.getDomainUrl() + "/user/registeredApi/registered.api";
	/** 登录 */
	public static final String URL_LOGIN = Config.getDomainUrl() + "/user/loginApi/login.api";
	/** 退出登录 */
	public static final String URL_LOGINOUT = Config.getDomainUrl() + "/user/loginApi/loginout.api";
	/** 注册发送验证码 */
	public static final String URL_SEND_SMS_CODE = Config.getDomainUrl() + "/user/registeredApi/sendSmsCode.api";
	/** 添加银行卡发送验证码 */
	public static final String URL_SEND_BANK_CARD_SMS_CODE = Config.getDomainUrl() + "/securitycenter/bankCardApi/sendSmsCode.api";
	/** 银行卡列表 */
	public static final String URL_BANK_CARD_LIST = Config.getDomainUrl() + "/securitycenter/bankCardApi/selectBankList.api";
	/** 银行列表 */
	public static final String URL_BANK_LIST = Config.getDomainUrl() + "/securitycenter/bankCardApi/getSupportBank.api";
	/** 完善银行卡信息 */
	public static final String URL_PERFECTBANKINFO = Config.getDomainUrl() + "/securitycenter/bankCardApi/perfectBankInfo.api";
	/** 是否可提现 */
	public static final String URL_SELECTISWITHDRAWALS = Config.getDomainUrl() + "/securitycenter/bankCardApi/selectIsWithdrawals.api";
	/** 债权列表 */
	public static final String URL_CREDITOR_RIGHTS_LIST = Config.getDomainUrl() + "/transferApi/centerList.api";
	/** 卡券未使用列表 */
	public static final String URL_CARD_UNUSED_LIST = Config.getDomainUrl() + "/activity/activityApi/selectUserCardVolume.api";
	/** 卡券已使用列表 */
	public static final String URL_CARD_USED_LIST = Config.getDomainUrl() + "/activity/activityApi/selectUserCardVolumeHasExpired.api";
	/** 已投项目待收中列表 */
	public static final String URL_PROJECT_WAIT_LIST = Config.getDomainUrl() + "/personalCenter/userInvestmentApi/selectDueinBorrow.api";
	/** 已投项目待收中项目详情 */
	public static final String URL_PROJECT_WAIT_DETAIL = Config.getDomainUrl() + "/personalCenter/userInvestmentApi/selectDueInDetailList.api";
	/** 已投项目债权转让债权转让详情 */
	public static final String URL_CREDITOR_TRANSFER_DETAIL= Config.getDomainUrl() + "/receiptPlanApi/transferInfo.api";
	/** 已投项目债权转让转让确认*/
	public static final String URL_CREDITOR_TRANSFER= Config.getDomainUrl() + "/receiptPlanApi/transferOk.api";
	/** 已投项目招标中列表 */
	public static final String URL_PROJECT_BIDDING_LIST = Config.getDomainUrl() + "/personalCenter/userInvestmentApi/selectInviteTendersInfo.api";
	/** 已投项目转让列表 */
	public static final String URL_PROJECT_TRANSFER_LIST = Config.getDomainUrl() + "/personalCenter/userInvestmentApi/selectTransferInfo.api";
	/** 已投项目转让详情*/
	public static final String URL_PROJECT_TRANSFER_DETAIL = Config.getDomainUrl() + "/receiptPlanApi/transferClaimInfo.api";
	/** 已投项目已完结列表 */
	public static final String URL_PROJECT_FINISHED_LIST = Config.getDomainUrl() + "/personalCenter/userInvestmentApi/selectInvestmentInfo.api";
	/** 已投项目已结清项目详情 */
	public static final String URL_PROJECT_FINISHED_DETAIL = Config.getDomainUrl() + "/receiptPlanApi/transferProjectInfo.api";
	/** 待收查询_待收、已收列表 */
	public static final String URL_WAIT_RECEIVE_LIST = Config.getDomainUrl() + "/personalCenter/userInvestmentApi/selectPendingInList.api";
	/** 待收查询_待收、已收详情 */
	public static final String URL_WAIT_RECEIVE_DETAIL = Config.getDomainUrl() + "/receiptPlanApi/info.api";
	/** 还款待还款列表 */
	public static final String URL_REIMBURSEMENT_WAIT_LIST = Config.getDomainUrl() + "/activity/activityApi/selectPendingRepayment.api";
	/** 还款已结清列表 */
	public static final String URL_REIMBURSEMENT_DONE_LIST = Config.getDomainUrl() + "/activity/activityApi/selectAlreadyRepayment.api";
	/** 还款待还款详情 */
	public static final String URL_REIMBURSEMENT_WAIT_DETAIL = Config.getDomainUrl() + "/repaymentPlanApi/repaymentPlanInfo.api";
	/** 还款已结清还款详情 */
	public static final String URL_REIMBURSEMENT_DONE_DETAIL = Config.getDomainUrl() + "/repaymentPlanApi/repaidPlanInfo.api";
	/** 还款-待还款-当期还款 */
	public static final String URL_REIMBURSEMENT_FOR_PROFIT = Config.getDomainUrl() + "/repaymentPlanApi/currentRepayPlanInfo.api";
	/** 还款-待还款-当期还款-确认还款 */
	public static final String URL_DO_REIMBURSEMENT_FOR_PROFIT = Config.getDomainUrl() + "/repaymentPlanApi/confirmRepayPlan.api";
	/** 还款-待还款-提前还清所有 */
	public static final String URL_REIMBURSEMENT_FOR_ADVANCE = Config.getDomainUrl() + "/myinvest/preRepaymentApi/getPrepaymentInfo.api";
	/** 还款-待还款-提前还清所有_确认*/
	public static final String URL_DO_REIMBURSEMENT_FOR_ADVANCE = Config.getDomainUrl() + "/myinvest/preRepaymentApi/prepayment.api";
	/** 自动投标列表 */
	public static final String URL_AUTO_TENDER_LIST = Config.getDomainUrl() + "/personalCenter/autoTenderApi/queryAllAutoTender.api";
	/** 添加自动投标 */
	public static final String URL_ADD_AUTO_TENDER = Config.getDomainUrl() + "/personalCenter/autoTenderApi/addAutoTender.api";
	/** 修改自动投标 */
	public static final String URL_COMPLIC_AUTO_TENDER = Config.getDomainUrl() + "/personalCenter/autoTenderApi/editAutoTender.api";
	/** 删除自动投标 */
	public static final String URL_DELETE_AUTO_TENDER = Config.getDomainUrl() + "/personalCenter/autoTenderApi/deleteAutoTender.api";
	/** 交易记录列表 */
	public static final String URL_TRADING_LIST = Config.getDomainUrl() + "/capitalflow/capitalFlowApi/selectCapitalFlow.api";
	/** 充值回调 */
	public static final String URL_RECHARGE = Config.getDomainUrl() + "/recharge/llpayReturn/doPost.api";
	/** 新增充值记录 */
	public static final String URL_NEW_ADD_RECHARGE = Config.getDomainUrl() + "/rechargeOnlineApi/addRechargeOnline.api";
	/** 添加银行卡 */
	public static final String URL_ADD_BANK_CARD = Config.getDomainUrl() + "/securitycenter/bankCardApi/addBankCard.api";
	/** 校验验证码*/
	public static final String URL_VERIFY_SMS_CODE = Config.getDomainUrl() + "/send/sendSmsApi/validateSmsCode.api";
	/** 是否可提现*/
	public static final String URL_WITHDRAWAL_VALIDATE = Config.getDomainUrl() + "/securitycenter/bankCardApi/selectIsWithdrawals.api";
	/** 提现获取验证码*/
	public static final String URL_WITHDRAWAL_SMS_CODE = Config.getDomainUrl() + "/withdrawal/bizWithdrawalApi/sendVerifyCode.api";
	/** 客户提现*/
	public static final String URL_WITHDRAWAL_APPLICATION = Config.getDomainUrl() + "/withdrawal/bizWithdrawalApi/custWithdrawal.api";
	/** 重置登录密码 */
	public static final String MINE_RESETLOGINPASSWORD = Config.getDomainUrl() + "/securitycenter/bankCardApi/resetLoginPassWord.api";
	/** 修改登录密码 */
	public static final String MINE_UPDATELOGINPASSWORD = Config.getDomainUrl() + "/securitycenter/bankCardApi/updateLoginPwd.api";
	/** 修改交易密码 */
	public static final String MINE_UPDATEPAYPASSWORD = Config.getDomainUrl() + "/securitycenter/bankCardApi/updateTradPassWord.api";
	/** 找回密码--重置登录密码*/
	public static final String MINE_FORGETRESETLOGINPASSWORD = Config.getDomainUrl() + "/user/resetPwdApi/resetPwd.api";
	/** 重置登录密码短信验证码 */ 
	public static final String MINE_RESETLOGINPASSWORD_CODE = Config.getDomainUrl() + "/securitycenter/bankCardApi/sendUpdateLoginPwdSmsCode.api";
	/** 重置交易密码短信验证码 */
	public static final String MINE_RESETPAYPASSWORD_CODE = Config.getDomainUrl() + "/securitycenter/bankCardApi/sendRestTradPwdSmsCode.api";
	/** 忘记密码短信验证码 */
	public static final String MINE_FORGETPASSWORD_CODE = Config.getDomainUrl() + "/user/resetPwdApi/sendSmsCode.api";
	/**发送语音验证码 */
	public static final String MINE_GETVOICE_CODE = Config.getDomainUrl() + "/send/sendSmsApi/sendScheduledSMS.api";	
	/**验证登录密码 */
	public static final String MINE_VALIDATE_LOGIN_PWD = Config.getDomainUrl() + "/securitycenter/bankCardApi/validateLoginPassWord.api";
	/**验证原交易密码 */
	public static final String MINE_VALIDATE_PAY_PWD = Config.getDomainUrl() + "/securitycenter/bankCardApi/validataTradPassWord.api";
	/**我的推荐邀请码 */
	public static final String MINE_INVITECODE = Config.getDomainUrl() + "/securitycenter/bankCardApi/selectReferralCode.api";
	/**我的邀请列表*/
	public static final String MINE_INVITELIST = Config.getDomainUrl() + "/securitycenter/bankCardApi/selectMyReferralList.api";
	/**意见反馈*/
	public static final String MINE_FEEDBACK = Config.getDomainUrl() + "/securitycenter/bankCardApi/opinionFeedback.api";
	/**手机绑定*/
	public static final String MINE_PHONEBOUND = Config.getDomainUrl() + "/securitycenter/bankCardApi/bindPhone.api";
	/**获取手机绑定短信验证码*/
	public static final String MINE_PHONEBOUND_SMSCODE = Config.getDomainUrl() + "/securitycenter/bankCardApi/bindCustPhoneNoSmsCode.api";
	/**获取个人信息*/
	public static final String MINE_PERSONALINFO = Config.getDomainUrl() + "/user/customerApi/getCustomer.api";
	/**修改用户名*/
	public static final String MINE_MODIFYUSERNAME = Config.getDomainUrl() + "/securitycenter/bankCardApi/updateUserName.api";
	/**上传用户头像*/
	public static final String MINE_UPLOADHEADIMG = Config.getDomainUrl() + "/personalCenter/headProcessApi/uploadHeadImage.api";
	/**电台播放列表接口*/
	public static final String MINE_RADIOLIST = Config.getDomainUrl() + "/securitycenter/bankCardApi/radioList.api";
	/**电台人气列表*/
	//public static final String MINE_RADIOPOPULARITYLIST= Config.getDomainUrl() + "/securitycenter/bankCardApi/radioPopularityList.api";
	/**电台点攒*/
	public static final String MINE_RADIORAISE= Config.getDomainUrl() + "/securitycenter/bankCardApi/radioPraiseNum.api";
	/**查媒体播放路径接口*/
	public static final String MINE_FINDPATH= Config.getDomainUrl() + "/securitycenter/bankCardApi/selectRadioFilePath.api";
	/**资产统计*/
	public static final String MINE_PERSONALASSETS = Config.getDomainUrl() + "/personalCenter/personDateApi/getUserProperty.api";
	/**设置APP端设备ID*/
	public static final String JPUSH_REGISTRATION_ID = Config.getDomainUrl() + "/user/customerApi/setRegistrationID.api";
	/**电台上一首，下一首功能接口*/
	public static final String RADIO_NEXT = Config.getDomainUrl() + "/securitycenter/bankCardApi/radioPlayUpOrDown.api";
	/**电台收听累加收听人数接口*/
	public static final String DAALISTENNUM = Config.getDomainUrl() + "/securitycenter/bankCardApi/radioAddListenNum.api";
	/**电台收听获取时间配置*/
	public static final String LISTENTIME = Config.getDomainUrl() + "/securitycenter/bankCardApi/getListenTimeSysParams.api";
	/**电台收听累加积分*/
	public static final String LISTENADDPOINT = Config.getDomainUrl() + "/securitycenter/bankCardApi/radioListenAddPoint.api";
	
	
	
	/**个人积分明细*/
	public static final String MINE_PERSONALPOINTDETAIL = Config.getDomainUrl() + "/exchange/exchangeApi/selectUserPointDetail.api";
	/**根据类型查询兑换所需的积分and值*/
	public static final String MINE_PERSONALEXCHANGE = Config.getDomainUrl() + "/exchange/exchangeApi/selectExchangeIntegral.api";
	/**兑换现金*/
	public static final String MINE_EXCHANGECASH = Config.getDomainUrl() + "/exchange/exchangeApi/exchangeCash.api";
	/**兑换VIP*/
	public static final String MINE_EXCHANGEVIP = Config.getDomainUrl() + "/exchange/exchangeApi/exchangeVIP.api";
	/**兑换加息劵*/
	public static final String MINE_EXCHANGETICKET = Config.getDomainUrl() + "/exchange/exchangeApi/exchangeInterestTicket.api";
	/**兑换话费*/
	public static final String MINE_EXCHANGETELEPHONE = Config.getDomainUrl() + "/exchange/exchangeApi/exchangeTelephoneFare.api";
	/**VIP购买*/
	public static final String MINE_BUYVIP = Config.getDomainUrl() + "/securitycenter/bankCardApi/shoppingVip.api";
	/**VIP购买期限选择*/
	public static final String MINE_VIPBUYSELECT = Config.getDomainUrl() + "/securitycenter/bankCardApi/getVipBuySelect.api";
	
	
	/**3.6.5E计划 散标投资列表接口 */
    public static final String EPLAN_LIST = Config.getDomainUrl() + "/eplan/eplanApi/getEplanList.api";
    /**3.6.6E计划 散标详情接口*/
    public static final String EPLAN_LIST_INFO = Config.getDomainUrl() + "/eplan/eplanApi/getEplanInfo.api";
    
    /**使用体验金*/
    public static final String API_USE_EXPERIENCE = Config.getDomainUrl() + "/experienceBorrowApi/useExperience.api";
    /**体验金加入成功*/
    public static final String API_EXPERIENCE_INVEST = Config.getDomainUrl() + "/experienceBorrowApi/investExperience.api";
    
    /**发现--收益计算器*/
    public static final String URL_EXECALCULATOR = Config.getDomainUrl() + "/otherinfo/calculatorApi/execCalculator.api";
    /**新手指引列表*/
    public static final String URL_GETCONTENTBYTAG = Config.getDomainUrl() + "/content/columnContentApi/getContentByTag.api";
    /**新手指引内容*/
    public static final String URL_GETCONTENTBYKEY = Config.getDomainUrl() + "/content/columnContentApi/getContentByKey.api";
    /**电台播放列表*/
    public static final String URL_RADIOLIST = Config.getDomainUrl() + "/securitycenter/bankCardApi/radioList.api";
    /**我的投资排行*/
    public static final String URL_MY_RANKING_LIST = Config.getDomainUrl() + "/securitycenter/bankCardApi/selectMyRankingList.api";
    /**我投资的排行位置*/
    public static final String URL_MY_RANGING_NUMBER = Config.getDomainUrl() + "/securitycenter/bankCardApi/selectMyRankingNumber.api";
    
    /**系统公告*/
    public static final String API_SYSTEM_NOTICE = Config.getDomainUrl() + "/content/columnContentApi/getContentByTag.api";
    public static final String API_SYSTEM_NOTICE_CONTENT = Config.getDomainUrl() + "/content/columnContentApi/getContentByKey.api";
    //系统公告全部已读
    public static final String API_SYSTEM_NOTICE_CONTENT_ISALLREAD = Config.getDomainUrl() + "/content/sysNoticeReadRecordApi/readAllMessage.api";
    //系统公告单条已读
    public static final String API_SYSTEM_NOTICE_CONTENT_ISREAD = Config.getDomainUrl() + "/content/sysNoticeReadRecordApi/readMessage.api";
    
    /** 转让专区[债权列表] */
    public static final String API_TRABSFER_LIST = Config.getDomainUrl() + "/transferApi/list.api";
    public static final String API_TRABSFER_LIST_INFO = Config.getDomainUrl() + "/transferApi/info.api";
    public static final String API_TRABSFER_LIST_BUY = Config.getDomainUrl() + "/transferApi/buy.api";
   
    /**消息中心*/
    public static final String API_NEWS_MESSAGE_LIST = Config.getDomainUrl() + "/securitycenter/bankCardApi/selectMsgList.api";
    /**标记消息为已读接口[单条已读]*/
    public static final String API_NEWS_MESSAGE_SIGNREAD = Config.getDomainUrl() + "/securitycenter/bankCardApi/signMessageRead.api";
    /**标记消息为已读接口[所有未读都更改为已读]*/
    public static final String API_NEWS_MESSAGE_ALLSIGNREAD = Config.getDomainUrl() + "/securitycenter/bankCardApi/signMessageAllRead.api";
    
    /**计划 项目描述*/
    public static final String API_EPLAN_PRODUCT = Config.getDomainUrl() + "/eplan/eplanApi/getEplanProduct.api";
    
    /**获取新手标[新手专享页面]*/
    public static final String API_NEWSTANDAR_UNIQUE= Config.getDomainUrl() + "/newStandardApi/unique.api";
    /**获取新手标详情[新手专享页面]*/
    public static final String API_NEWSTANDAR_UNIQUE_INFO= Config.getDomainUrl() + "/newStandardApi/info.api";
    
    /**获取体验标[新手专享页面]*/
    public static final String API_EXPERIENCEBORROW_UNIQUE= Config.getDomainUrl() + "/experienceBorrowApi/unique.api";
    /**获取体验标详情[新手专享页面]*/
    public static final String API_EXPERIENCEBORROW_UNIQUE_INFO= Config.getDomainUrl() + "/experienceBorrowApi/info.api";
    
    
    /**查询风控信息*/
    public static final String API_EPLAN_RISKCONTROL= Config.getDomainUrl() + "/eplan/eplanApi/getRiskControl.api";
    /**立即加入*/
    public static final String API_EPLAN_NOWBORROW= Config.getDomainUrl() + "/eplan/eplanApi/nowBorrow.api";
    
    /**立即投资接口*/
    public static final String API_EPLAN_SAVEBORROW= Config.getDomainUrl() + "/eplan/eplanApi/saveBorrow.api";
   
    /**是否有新消息中心数据*/
    public static final String API_ISNEWMESSAGE = Config.getDomainUrl() + "/securitycenter/bankCardApi/isNewMessage.api";
    
    /**推荐项目【首页】*/
    public static final String API_HOME_RecommendProjects = Config.getDomainUrl() + "/eplan/eplanApi/appRecommendProjects.api";
    /**查询项目信息【首页入口】*/
    public static final String API_HOME_BorrowOtherInfo = Config.getDomainUrl() + "/eplan/eplanApi/getBorrowOtherInfo.api";
    /**查询借款人基本信息【首页入口】*/
    public static final String API_HOME_CustomerByPid = Config.getDomainUrl() + "/eplan/eplanApi/getCustomerByPid.api";
    /**查询投资记录信息【首页入口】*/
    public static final String API_HOME_BORROWDETAILLIST = Config.getDomainUrl() + "/eplan/eplanApi/getBorrowDetailList.api";
    /**查询还款计划信息【首页入口】*/
    public static final String API_HOME_REPAYMENTBYBORROWID = Config.getDomainUrl() + "/eplan/eplanApi/selectRepaymentsByBorrowId.api";
    /**新手任务进度信息接口*/
    public static final String API_CUSTOMER_TASKSCHEDULE = Config.getDomainUrl() + "/user/customerApi/getTaskSchedule.api";
    /**实名认证 大陆*/
    public static final String API_REALNAMEDL = Config.getDomainUrl() + "/activity/activityApi/realNameAuthenticationMainland.api";
    /**实名认证 非大陆*/
    public static final String API_REALNAMENODL = Config.getDomainUrl() + "/activity/activityApi/realNameAuthenticationNonMainland.api";
    /**广告、banner获取接口【首页】*/
    public static final String API_HOMEPAGE_BANNER = Config.getDomainUrl() + "/homepage/homePageApi/getAlimama.api";
    /**立即投资，加息券列表接口*/
    public static final String API_SELECTUSERINTERST_TICKET = Config.getDomainUrl() + "/activity/activityApi/selectUserInterestTicket.api";
    /**我的社区 */
    public static final String API_GETBBSURl=Config.getDomainUrl()+"/user/loginApi/getBbsLoginResult.api";
    /**展示红包接口 */
    public static final String API_SHOWRED=Config.getDomainUrl()+"/activity/receiveActictyApi/showRed.api";
    /**查询红包明细接口 */
    public static final String API_SELECTR_RED_DETAIL=Config.getDomainUrl()+"/activity/receiveActictyApi/selectReceiveRedDetail.api";
    /**领红包接口 */
    public static final String API_RECEIVE_RED=Config.getDomainUrl()+"/activity/receiveActictyApi/receiveRed.api";
    /**展示加息劵接口*/
    public static final String API_SHOW_INTEREST=Config.getDomainUrl()+"/activity/receiveActictyApi/showInterest.api";
    /**查询加息劵明细接口*/
    public static final String API_SELECT_INTEST_DETAIL=Config.getDomainUrl()+"/activity/receiveActictyApi/selectReceiveInterestDetail.api";
    /**领加息劵接口*/
    public static final String API_RECEIVE_INTEREST=Config.getDomainUrl()+"/activity/receiveActictyApi/receiveInterest.api";
}
