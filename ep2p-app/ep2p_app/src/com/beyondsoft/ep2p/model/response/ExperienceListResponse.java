package com.beyondsoft.ep2p.model.response;


import java.util.List;


public class ExperienceListResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public String borrowId;
        public List<ExperienceListItem> experiences;
    }

    public class ExperienceListItem
    {
        public String pid; //  标的id
        public String expireTime; //2016-02-03 15:32:41,
        public String useStatus; //2,
        public double expAmount; //10000,
        public String expTime; //2016-02-03 15:32:41.0

    }

}
