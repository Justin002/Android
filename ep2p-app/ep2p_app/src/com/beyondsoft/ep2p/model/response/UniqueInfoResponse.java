package com.beyondsoft.ep2p.model.response;



/**
 * 3.6.7体验标详情响应的bean
 * @ClassName;UniqueInfoResponse
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月7日
 *
 */
public class UniqueInfoResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public ExperienceInfo borrow;
    }

    public class ExperienceInfo
    {
        public String pid;
        public String borrowCode;
        public String borrowName;
        public String borrowRate;
        public String borrowMoney;
        public String repaymentType;
        public String accrualType;
        public String borrowType;
        public String startMoney;
        public String isEquitableAssignment;
        public String alreadyMoney;
        public String borrowProgress;
        public String deadline;
        public String surplusMoney;
        public String status;
        public String createUser;
        public String borrowDesc;
        public String borDeadline;
        public String tenderCount;
        public String borStatus;
        public String manageExpenseRate;
        public String repaymentTypeName;
    }

}
