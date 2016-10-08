package com.beyondsoft.ep2p.model.response;


public class SaveBorrowResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        
        public boolean result; //true,
        public String msg; //投资成功 ,   "msg": "交易密码校验失败!"
        public int   num;//": 4,
        public String t;//": "",
        public String errorCode;// "tradePwdError",
//        public ResultData result;
      
    }
    public class ResultData
    {
    }
}
