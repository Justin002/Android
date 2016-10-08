package com.beyondsoft.ep2p.model.response;

/**
 * 
 * 新手标
 * @ClassName:NewStandarUniqueResponse 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: szit
 * @date: 2016年1月18日
 *
 */
public class NewStandarUniqueResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public String pid; //  标的id
        public String borStatus; //
        public String deadline; // 
        public double yearRate; //  0.0015
        public String isType;
        public String des;
        public String desc;
        public double borProgress ;
    }

}
