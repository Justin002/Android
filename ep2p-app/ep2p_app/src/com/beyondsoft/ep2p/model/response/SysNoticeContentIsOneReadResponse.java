package com.beyondsoft.ep2p.model.response;




/**
 * 系统公告内容是否已读bean
 * @ClassName;SysNoticeContentIsAllReadResponse 
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月14日
 *
 */
public class SysNoticeContentIsOneReadResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;
    
    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public boolean  result; 
    }
}
