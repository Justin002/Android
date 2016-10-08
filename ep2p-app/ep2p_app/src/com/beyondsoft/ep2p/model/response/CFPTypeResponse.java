package com.beyondsoft.ep2p.model.response;


import java.util.List;

import cn.jpush.android.api.d;


/**
 * e计划，散标投资List响应的bean
 * @ClassName;CFPTypeResponse
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月7日
 *
 */
public class CFPTypeResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;
    
    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public long systime;//: 1457452013409, //系统时间
        public int pageCount;//总条数
        public List<CFPTypeItem> list;
    }

    public class CFPTypeItem
    {
        public String pid; //  标的id
        public double borrowRate;// 年化利率    
        public String borDeadline; // 借款期限
        public String borStatus;// 借款状态{0   已撤销1   待招标2   招标中3   已流标4   已满标5   还款中6   逾期还款7   逾期垫付8   已结清}
        public double borrowProgress;// 完成进度
        public String borrowName; //借款名称
        public String accrualType; //计息方式
        public String accrualTypeName; //计息方式名称
        public double borrowMoney; //借款金额
        public String borrowCode; //借款编号
        public String borrowType; //标的类型（1;e首房,2;e抵押,3;e计划）
        public double investRewardScale; //投资奖励比例    
        
        public String customerId;
        public String phoneNo;
        public String customerStatus;
        public String homeTotal;
        public String borrowTime;
        public String borrowApr;
        public String repaymentType;
        public String startMoney;
        public double endMoney;
        public String isTimesInvest;
        public String isJiaxiTicket;
        public String isEquitableAssignment;
        public String startValue;
        public String endValue;
        public String operTime;
        public String deadline;
        public String surplusMoney;
        public String borrowPassword;
        public String borrowStatus;
        public String borrowDesc;
        public String applyTime;
        public String approveTime;
        public String createTime;
        public String createUser;
        public String lastUpdateTime;
        public String borrowTypeName;// "borrowTypeName": "e计划",
        public String repaymentTypeName;// "repaymentTypeName": "等额本息",
        public String borrowTag;
        public String publishTime;// "publishTime": "2016-01-13 16:49:04",
        public String borStatusName;// "borStatusName": "待招标",
        public String manageExpenseRate;// "manageExpenseRate": 10
       
    }

}
