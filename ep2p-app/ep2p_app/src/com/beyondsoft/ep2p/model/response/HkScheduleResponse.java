package com.beyondsoft.ep2p.model.response;


import java.util.List;


public class HkScheduleResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
//        public int pageCount;//总条数
        public List<HkScheduleItem> result;
    }

    public class HkScheduleItem
    {
        public String pid; //  标的id
        public String borrowId; //FFFFFFFFEF0724CA!07C2632894FA479ABC182BAF9EDE79,
        public double capital; //4000000,  //金额
        public double interest; //399.99,//利息
        public String planindex; //24  //期数
    }

}
