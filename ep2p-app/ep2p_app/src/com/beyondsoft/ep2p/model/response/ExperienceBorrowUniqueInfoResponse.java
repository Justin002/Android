package com.beyondsoft.ep2p.model.response;


/**
 * 
 * 体验标详情
 * @ClassName;ExperienceBorrowUniqueInfoResponse
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月18日
 *
 */
public class ExperienceBorrowUniqueInfoResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public NewStandarUniqueInfo borrow;
    }

    public class NewStandarUniqueInfo
    {
        public String pid; //  标的id
        public String  borrowCode;// T1601061,
        public String borrowName; //体验标二：啦啦啦啦啦啦,
        public double borrowRate; //0.14,
        public double borrowMoney; //50000,
        public String  repaymentType; //3,
        public String  accrualType;// 2,
        public String borrowType; //5,
        public double startMoney;// 50,
        public double endMoney;// 50000,
        public int investRewardScale; //0,
        public String isEquitableAssignment; //2,
        public int  startValue;// 0,
        public int endValue; //0,
        public double alreadyMoney; //10000,
        public double  borrowProgress;// 0.2,
        public String deadline;// 7,
        public double  surplusMoney; //40000,
        public String  status;// 1,
        public String  createUser; //FFFFFFFFE1802C09!835AE4A65E624C04B079BC8BE55D8A,
        public String  borrowDesc; //体验标的修改测试,
        public String borDeadline; //7,
        public String tenderCount; //1,
        public String  borStatus; //5,
        public int  manageExpenseRate;// 0,
        public String  repaymentTypeName; //到期还本息,
        public String  accrualTypeName; //满标即计息,
        public String borrowTypeName; //体验标
        
    }
}
