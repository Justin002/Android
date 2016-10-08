package com.beyondsoft.ep2p.model.response;


import java.util.List;


/**
 * 系统公告bean
 * @ClassName;SysNoticeResponse 
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月14日
 *
 */
public class SysNoticeResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;
    
    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public int pageCount;//总条数
        public List<SysNoticeItem> result;
    }

    public class SysNoticeItem
    {
        public String  pid; //36B19CBE!275705F0316546799106D8AEB2B8CF81,
        public String  titleName; //阿斯顿发生地方,
        public String  coluOrder; //2016-01-13 10;09;15,
        public String  isCustomFile; //1,
        public String  isReading;// 1,
        public String  content; //阿斯顿发发生的发生,
        public String  createTime; //2016-01-13 10;09;15,
        public String  filePid; //78BE158!8A4BEE7FCF3B4223AE38FB00A5DF406C,
        public String  fileName; //u=2741094767,3761590495&fm=116&gp=0,
        public String  fileUrl; //upload/content/2016/01/13/650d7c41-c084-4c84-85d7-5767dd863d60.jpg
       
    }

}
