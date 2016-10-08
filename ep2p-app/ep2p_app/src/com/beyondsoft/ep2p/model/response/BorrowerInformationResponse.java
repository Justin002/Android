package com.beyondsoft.ep2p.model.response;


public class BorrowerInformationResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public String custId; //  标的id

        public String privateCustName;// cesh*****,,//投资人姓名
        public String privatePhoneNo;// 139****8540,//  投资人电话
        public String age; //27,
        public String isMarriage; //1,//婚姻状况  
        public String ResReg; //江西赣州,//户籍
        public String sexName;
    }

}
