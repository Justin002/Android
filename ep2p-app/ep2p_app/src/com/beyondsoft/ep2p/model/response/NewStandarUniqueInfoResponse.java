package com.beyondsoft.ep2p.model.response;


/**
 * 
 * 新手标详情
 * @ClassName;NewStandarUniqueInfoResponse
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月18日
 *
 */
public class NewStandarUniqueInfoResponse extends BaseResponse
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
        public String borrowCode; //X20151211001,
        public String borrowName; //我的新手标0002,
        public double borrowRate; //0.0015,
        public String borrowMoney; //200000,
        public String repaymentType;// 1,
        public String accrualType; //2,
        public String borrowType; //4,
        public double startMoney; //1000,
        public double endMoney; //10000,
        public String isEquitableAssignment; //1,
        public double alreadyMoney;// 0,
        public double borrowProgress; //0,
        public String deadline; //1/,
        public double surplusMoney;// 200000,
        public String status; //1,
        public String createUser; ///FFFFFFFFE1802C09!835AE4A65E624C04B079BC8BE55D8A,
        public String borrowDesc; //我的新手标0002,
        public String borDeadline; //5,
        public String tenderCount; //0,
        public String borStatus; //3,
        public String repaymentTypeName; //等额本息,
        public String accrualTypeName;// 满标即计息,
        public String borrowTypeName;// 新手标,
        public String joinCondition; //我的新手标0002
    }
}
