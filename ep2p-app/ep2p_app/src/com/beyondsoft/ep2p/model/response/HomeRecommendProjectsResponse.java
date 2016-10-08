package com.beyondsoft.ep2p.model.response;


import java.util.List;


/**
 * 
 * 首页推荐项目
 * @ClassName:HomeRecommendProjectsResponse 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2016年1月19日
 *
 */
public class HomeRecommendProjectsResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public long systime;
        public List<HomeRecommendProjectsItem> list;
    }

    public class HomeRecommendProjectsItem
    {
        public String pid; //   //借款id
        public String borrowCode; //借款编号
        public double borrowMoney; //100,借款金额
        public String borrowType;// 3,/借款类型代码
        public int isJiaxiTicket; //1是否允许使用加息劵  1 是 2否 
        public double investRewardScale;// 0,/加息奖励值
        public double borrowProgress; //0,投资进度
        public int borDeadline; //1,借款期限
        public String borrowTypeName; //e计划,借款类型名称
        public String accrualTypeName; //投标即计息,计息类型名称
        public double borrowRate; //0.15//年华利率
        public String publishTime;// "2016-01-12 16:38:17"
        public String borStatus;// 状态
        /**
         * 0    已撤销
        1   待招标
        2   招标中
        3   已流标
        4   已满标
        5   还款中
        6   逾期还款
        7   逾期垫付
        8   已结清
         */
    }

}
