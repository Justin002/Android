package com.beyondsoft.ep2p.model.response;


import java.util.List;


/**
 * 转让专区List响应的bean
 * @ClassName;TransferResponse
 * @Description; 这里用一句话描述这个类的作用 
 * @author; ssy
 * @date; 2016年1月7日
 *
 */
public class TransferResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public int pageCount;//总条数
        public List<TransferItem> list;
    }

    public class TransferItem
    {
        public String transferId; //1, 债权转让id
        public String transferCode;// zq15121001,债权转让编号
        public String receiptPlanId; //1,收款计划id
        public String borrowId; //借款id
        public String borrowCode;// "JK-151209-0005",
        public String borrowName; //windows world,借款名称
        public double yearRate;// 0.1345, 年利率
        public double successAmount; //1600,转让价格（成交价格）
//        public double interest; //36.48, 收益
//        public int returnedDate; //-10, 回款天数
        public String transferStatus;// 2 转让状态 1:转让中 2:已转让3：失败4：撤销
        
        public double projectValue; //项目价值
        public int timeRemaining; //剩余期限   6个月
    }

}
