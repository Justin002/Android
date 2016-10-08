package com.beyondsoft.ep2p.model.response;

/**
 * 项目描述bean
 * @ClassName;TrabserProductInfoResponse 
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月14日
 *
 */
public class TrabserProductInfoResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;
    
    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public String borrowId; //FFFFFFFFEE96CD51!0A9662B389B94824AAD8AFEEFCDB12,
        public String projectDescription; //项目描述项目描述项目描述项目描述项目描述项目描述项目描述
    }

}
