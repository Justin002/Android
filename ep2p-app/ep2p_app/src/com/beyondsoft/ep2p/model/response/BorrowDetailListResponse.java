package com.beyondsoft.ep2p.model.response;


import java.util.List;


public class BorrowDetailListResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public int total;
        public List<NewStandardInformation> result;
    }

    public class NewStandardInformation
    {
        public String privateName;//et**,
        public String investmentTime;//2016-01-18 16:08:52,
        public double investmentAmount;//10000
    }

}
