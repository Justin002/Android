package com.beyondsoft.ep2p.model.response;

public class RegisterJDResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        
        public String isAttestation; //是否实名认证    【1已认证，2未认证】
        public String isRegistered;//是否注册【1已注册，2未注册】
        public String isInvestment;//是否投资【1有投资，2没有投资】
    }

}
