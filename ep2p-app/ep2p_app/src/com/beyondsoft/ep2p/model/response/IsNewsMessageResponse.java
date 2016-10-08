package com.beyondsoft.ep2p.model.response;


public class IsNewsMessageResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;
    
    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        
        public int flag = 0;//flag =1 有未读消息  0没有未读消息
    }

}
