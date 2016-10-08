package com.beyondsoft.ep2p.api;

import android.content.Context;

import com.android.volley.toolbox.StringRequest;
import com.beyondsoft.ep2p.utils.IDataConnectListener;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.StringRequest2ForImg;
import com.beyondsoft.ep2p.utils.URL;

/**
 * @author Ivan.Lu
 * @description 安全中心接口
 */
public class SecurityCenterApi {
	
	
	/**重置登录密码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest resetLoginPassword(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_RESETLOGINPASSWORD,object, listener);
	}
	
	/**找回密码-重置登录密码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest resetForgetLoginPassword(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_FORGETRESETLOGINPASSWORD,object, listener);
	}
	
	/**修改登录密码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest updateLoginPassword(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_UPDATELOGINPASSWORD,object, listener);
	}
	
	/**修改交易密码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest updatePayPassword(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_UPDATEPAYPASSWORD,object, listener);
	}
	
	/**获取重置登录密码验证码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getResetLoginPasswordCode(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_RESETLOGINPASSWORD_CODE,object, listener);
	}
	
	/**获取重置交易密码验证码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getResetPayPasswordCode(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_RESETPAYPASSWORD_CODE,object, listener);
	}
	
	/**获取语音验证码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getVoiceCode(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_GETVOICE_CODE,object, listener);
	}
	
	/**验证登录密码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest validateLoginPassword(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_VALIDATE_LOGIN_PWD,object, listener);
	}
	
	/**获取我的推荐邀请码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getInviteCode(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_INVITECODE,object, listener);
	}
	
	/**获取我的邀请列表
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getInviteList(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_INVITELIST,object, listener);
	}
	
	/**验证短信Code
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest validteSMSCode(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.URL_VERIFY_SMS_CODE,object, listener);
	}
	
	/**验证原交易密码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest validatePayPassword(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_VALIDATE_PAY_PWD,object, listener);
	}
	
	/**忘记密码短信验证码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getForgetPasswordCode(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_FORGETPASSWORD_CODE,object, listener);
	}
	
	/**手机绑定
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest boundPhone(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_PHONEBOUND,object, listener);
	}
	
	/**手机绑定
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getBoundPhoneSMSCode(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_PHONEBOUND_SMSCODE,object, listener);
	}
	
	/**获取个人信息
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getPersonalInfo(int tag, Object object,
			IDataConnectListener listener,Context context) {
		return new StringRequest2(tag, URL.MINE_PERSONALINFO,object, listener,context);
	}
	
	/**修改用户名
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest modifyUserName(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_MODIFYUSERNAME,object, listener);
	}
	
	/**修改用户名
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest upLoadHeadImg(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2ForImg(tag, URL.MINE_UPLOADHEADIMG,object, listener);
	}
	
	/**电台播放列表
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getRadioList(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_RADIOLIST,object, listener);
	}
	
	/**电台人气列表
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 *//*
	public StringRequest getRadioPopList(int tag, Object object,
			IDataConnectListener listener,Context context) {
		return new StringRequest2(tag, URL.MINE_RADIOPOPULARITYLIST,object, listener,context);
	}*/
	
	/**电台点赞
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest radioPraiseNum(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_RADIORAISE,object, listener);
	}
	
	/**查媒体播放路径接口
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest selectRadioFilePath(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_FINDPATH,object, listener);
	}
	
	/**电台上一首，下一首
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest nextRadion(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.RADIO_NEXT,object, listener);
	}
	
	/**电台收听累加收听人数
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest addListenNum(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.DAALISTENNUM,object, listener);
	}
	
	/**电台收听获取时间
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest listenTime(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.LISTENTIME,object, listener);
	}
	
	/**电台收听累加积分
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest listenAddPoint(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.LISTENADDPOINT,object, listener);
	}
	
	/**个人资产
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getPersonalAssets(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_PERSONALASSETS,object, listener);
	}
	
	/**个人积分明细
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getUserPointDetailList(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_PERSONALPOINTDETAIL,object, listener);
	}
	
	/**根据类型查询兑换所需的积分and值
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getExchangeIntegral(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_PERSONALEXCHANGE,object, listener);
	}
	
	/**兑换现金
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest exchangeCash(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_EXCHANGECASH,object, listener);
	}
	
	/**兑换VIP
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest exchangeVIP(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_EXCHANGEVIP,object, listener);
	}
	
	/**兑换加息劵
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest exchangeInterestTicket(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_EXCHANGETICKET,object, listener);
	}
	
	/**兑换话费
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest exchangeTelephoneFare(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_EXCHANGETELEPHONE,object, listener);
	}
	
	/**VIP购买
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest buyVip(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_BUYVIP,object, listener);
	}
	
	/**VIP购买期限选择
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest buyVipSelect(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.MINE_VIPBUYSELECT,object, listener);
	}
	/**新手指引
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest newNoticeGuide(int tag, Object object,
			IDataConnectListener listener) {
		return new StringRequest2(tag, URL.URL_GETCONTENTBYTAG,object, listener);
	}
	/**
	 * bbs 地址
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */

	public StringRequest getBBSUrl(int tag, Object object, IDataConnectListener listener) {
		// TODO Auto-generated method stub
		return new StringRequest2(tag, URL.API_GETBBSURl,object, listener);
	}
}
