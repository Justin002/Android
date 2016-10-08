package com.beyondsoft.ep2p.model.response;


public class ImmediatelyInvestResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public String isJiaxiTicket; //"1",  // 是否允许使用加息劵 1是 2否
        public String isTimesInvest = ""; //"1",//是否倍数投资
        public double surplusMoney; //4300,,//未投金额
        public int alreadyMoney; //700,//已投金额
        public String pid; //"FFFFFFFFEE96CD51!0A9662B389B94824AAD8AFEEFCDB12",
        public int startMoney; //100  //起投金额

        public String accrualType; // 计息类型  
        public String borDeadline;// 借款期限 
        public double borrowRate;// 年华利率 
        public double investRewardScale;// 0.1; 投资奖励比例
        public int endMoney; //100,//投资上限  
        public String borrowCode; // "C1601111",
    }

    // "systime": 1457127853686,  有返回 目前没有用到

}
