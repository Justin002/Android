package com.beyondsoft.ep2p.model.response;


import java.util.List;


/**
 * 转让专区List详情响应的bean
 * @ClassName;TransferInfoResponse
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月7日
 *
 */
public class TransferInfoResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;

        public double availableAmount; //14650,// 可用余额，（登陆才有该字段）
        public double lixi;// 2522,  //还款计划总利息
        public long sysDate;// 1453846140845, // 系统时间  倒计时需要
        public long expireDate;// 1453844109000,   // 债权失效时间
        public double benjin; //105399.89, // 还款计划总本金
        public int qixian; //5, // 还款计划期限
        public String qixiDate;
        public double benxi; //107921.89// 还款计划总本息
        public TransferInfo transferModel;
        public List<ReceiptPlanList> receiptPlanList;
        
    }

    public class TransferInfo
    {
        public String pid;
        public String transferCode;// zq15121001,债权转让编号
        public double residualPrincipal; //2000, // 剩余本金
        public int timeRemaining; //10,// 剩余期次
        public double projectValue; //5000, //项目价格
        public double fee; //60,  // 手续费
        public double interestToday; //50,// 当期至今利息
        public float profitRate;// 0.1345, 年化率
        public String successTime;// 2015-12-11 11:38:25.0,
        public String status; //2, // 债权状态  1转让中  2已转让
        public String statusName; //已成交, // 债权状态名称
        public String createTime; //2015-12-10 16:35:07.0,
        public String deadline;// 6,
        public String interestData;// Jan 25, 2016 11:00:11 AM,  // 起息日[过时]
        public String repayType;  // 还款方式   //1,
        public String repayTypeName;  //还款方式名称 //等额本息,
        public String borrowId; //借款id
        public String borrowName; //windows world,借款名称
        public double successAmount; //1600,转让价格（成交价格）
        public String borrowCode; // 借款编号
        public String createUserName; // 转让人名称
        public String org; // 担保机构 //张氏集团旗下担保公司

    }

    public class ReceiptPlanList
    {
        public double capital; //1691.48, // 本金
        public String rDate; //2016-05-04, // 还款时间
//        public double netInterest;// 0 // 利息
        public double netInterest;// 利息
    }

}
