package com.beyondsoft.ep2p.model.response;

/**
 * e计划，散标投资详情响应的bean
 * @ClassName;CFPTypeResponse
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月7日
 *
 */
public class CFPTypeDetailsResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public String pid; //  标的id
        public String borDeadline; // 借款期限
        public String borrowCode; //借款编号
        public double borrowProgress; // 完成进度 
        public String surplusMoney; //未投金额  
        public String borrowMoney; // 借款金额 
        public String accrualType; //计息方式
        public String accrualTypeName; //计息方式名称
        public String repaymentType; //还款方式
        public String repaymentTypeName; //还款方式名称
        public String borrowStatus; //
        public double investRewardScale; //
        public String borrowRate; //
        public String borrowName; //
        public String borrowTypeName; // "e计划",
        public String borrowType; // 
        public String  customId;// "customId": "FFFFFFFFCD1D77F1!538A910FA0F442ACA8F4A4B128CDF5",//首页进去传到借款人接口入参
        public String  borStatus;// "2",
        public String isJiaxiTicket;
        public String securityType ="";//保障方式
    }

    
   
}
