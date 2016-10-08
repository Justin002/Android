package com.beyondsoft.ep2p.model.response;

import java.util.List;

/**
 * 使用体验金响应的bean
 * @ClassName;UseExperienceResponse
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月7日
 *
 */
public class UseExperienceResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public List<ExperienceListInfo> experiences;
    }

    public class ExperienceListInfo
    {
        public String pid;
        public String expireTime;
        public String useStatus;
        public String expAmount;
        public String expTime;
    }

}
