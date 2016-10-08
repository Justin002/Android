package com.beyondsoft.ep2p.service;

import java.util.HashMap;

import android.content.Context;

import com.android.volley.toolbox.StringRequest;
import com.beyondsoft.ep2p.api.SecurityCenterApi;
import com.beyondsoft.ep2p.utils.IDataConnectListener;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;

/**
 * @author Ivan.Lu
 * @description 安全中心业务层
 */
public class SecurityCenterService {
	private SecurityCenterApi securityCenterApi;
	public SecurityCenterService(){
		securityCenterApi=new SecurityCenterApi();
	}
	
	/**重置登录密码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest resetLoginPassword(int tag, Object object,IDataConnectListener listener) {
		return securityCenterApi.resetLoginPassword(tag, object, listener);
	}
	
	/**找回密码-重置登录密码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest resetForgetLoginPassword(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.resetForgetLoginPassword(tag, object, listener);
	}
	
	/**修改登录密码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest updateLoginPassword(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.updateLoginPassword(tag, object, listener);
	}
	
	/**修改交易密码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest updatePayPassword(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.updatePayPassword(tag, object, listener);
	}
	
	/**获取重置登录密码验证码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getResetLoginPasswordCode(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.getResetLoginPasswordCode(tag, object, listener);
	}
	
	/**获取重置交易密码验证码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getResetPayPasswordCode(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.getResetPayPasswordCode(tag, object, listener);
	}
	
	/**获取语音验证码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getVoiceCode(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.getVoiceCode(tag, object, listener);
	}
	
	/**验证登录密码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest validateLoginPassword(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.validateLoginPassword(tag, object, listener);
	}
	
	/**获取我的推荐邀请码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getInviteCode(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.getInviteCode(tag, object, listener);
	}
	
	/**获取我的邀请列表
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getInviteList(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.getInviteList(tag, object, listener);
	}
	
	/**验证短信Code
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest validteSMSCode(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.validteSMSCode(tag, object, listener);
	}
	
	/**验证原交易密码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest validatePayPassword(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.validatePayPassword(tag, object, listener);
	}
	
	/**忘记密码短信验证码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getForgetPasswordCode(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.getForgetPasswordCode(tag, object, listener);
	}
	
	/**手机绑定
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest boundPhone(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.boundPhone(tag, object, listener);
	}
	
	/**手机绑定短信验证码
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getBoundPhoneSMSCode(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.getBoundPhoneSMSCode(tag, object, listener);
	}
	
	/**获取个人信息
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getPersonalInfo(int tag,IDataConnectListener listener,Context context) {
		return securityCenterApi.getPersonalInfo(tag,null, listener,context);
	}
	
	/**修改用户名
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest modifyUserName(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.modifyUserName(tag, object, listener);
	}
	
	/**上传头像
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest upLoadHeadImg(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.upLoadHeadImg(tag, object, listener);
	}
	
	/**电台播放列表接口
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getRadioList(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.getRadioList(tag, object, listener);
	}
	
	/**电台人气列表
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getRadioPopList(int tag,IDataConnectListener listener) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", 1);
		params.put("pageSize", 7);
		return securityCenterApi.getRadioList(tag, params, listener);
	}
	
	/**电台点赞
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest radioPraiseNum(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.radioPraiseNum(tag, object, listener);
	}
	
	/**查媒体播放路径接口
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest selectRadioFilePath(int tag, Object object,
			IDataConnectListener listener) {
		HashMap<String,String> hashMap=new HashMap<String, String>();
		hashMap.put("programId",object.toString());
		return securityCenterApi.selectRadioFilePath(tag, hashMap, listener);
	}
	
	/**电台上一首，下一首
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest nextRadion(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.nextRadion(tag,object, listener);
	}
	
	/**电台收听累加收听人数
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest addListenNum(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.addListenNum(tag, object, listener);
	}
	
	/**电台收听获取时间
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest listenTime(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.listenTime(tag, object, listener);
	}
	
	/**电台收听累加积分
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest listenAddPoint(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.listenAddPoint(tag, object, listener);
	}
	
	/**个人资产统计
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getPersonalAssets(int tag,IDataConnectListener listener) {
		return securityCenterApi.getPersonalAssets(tag,null,listener);
	}
	
	/**个人积分明细
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getUserPointDetailList(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.getUserPointDetailList(tag, object, listener);
	}
	
	/**根据类型查询兑换所需的积分and值
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest getExchangeIntegral(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.getExchangeIntegral(tag, object, listener);
	}
	
	/**兑换现金
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest exchangeCash(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.exchangeCash(tag, object, listener);
	}
	
	/**兑换VIP
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest exchangeVIP(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.exchangeVIP(tag, object, listener);
	}
	
	/**兑换加息劵
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest exchangeInterestTicket(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.exchangeInterestTicket(tag, object, listener);
	}
	
	/**兑换话费
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest exchangeTelephoneFare(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.exchangeTelephoneFare(tag, object, listener);
	}
	
	/**VIP购买
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest buyVip(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.buyVip(tag, object, listener);
	}
	
	/**VIP购买期限选择
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest buyVipSelect(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.buyVipSelect(tag, object, listener);
	}
	
	/**新手指引
	 * @param tag
	 * @param object
	 * @param listener
	 * @return
	 */
	public StringRequest newNoticeGuide(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.newNoticeGuide(tag, object, listener);
	}
	/**
	 * 获取BBS 地址
	 */
	
	public StringRequest getBBSUrl(int tag, Object object,
			IDataConnectListener listener) {
		return securityCenterApi.getBBSUrl(tag, object, listener);
	}
}
