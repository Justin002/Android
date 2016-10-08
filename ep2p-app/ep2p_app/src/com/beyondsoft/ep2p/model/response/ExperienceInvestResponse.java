package com.beyondsoft.ep2p.model.response;


/**
 * 体验金加入成功响应的bean
 * @ClassName;UseExperienceResponse
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月7日
 *
 */
public class ExperienceInvestResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public String amount; //体验金额
        public String borDeadline;//期限
        public String borrowRate; //年化率
        public boolean  investExperienceResult; //true,
        public double interest;// 583.33,//预期收益
        public String borrowCode; //T1601201
        
        public int num;//": 4,  ,错误输入的总数
        
    }

}
