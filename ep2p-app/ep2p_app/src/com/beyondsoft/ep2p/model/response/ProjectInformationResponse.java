package com.beyondsoft.ep2p.model.response;

public class ProjectInformationResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public String pid; //  标的id
        public String  payment; //工资,//用户来源
        public String  borrowUse;// 购物, //  借款用途
        public String  hostageInfo; //我的e抵押0001,,//抵押信息     
        public String  homeDesc; //天地房产,//房产信息 
    }

}
