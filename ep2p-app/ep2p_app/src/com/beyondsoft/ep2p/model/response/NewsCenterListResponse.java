package com.beyondsoft.ep2p.model.response;

import java.util.List;

/**
 * 消息中心响应的bean
 * @ClassName;NewsCenterListResponse
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月7日
 *
 */
public class NewsCenterListResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public int pageCount;
        public List<NewsCenterList> data;
      
    }
    
    public class NewsCenterList extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public String pid; //
        public String sendContent; //
        public int isRead; //// 是否已读  0:未读  1：已读   
        public String createTime; //
        public String msgTitle;
    }

}
