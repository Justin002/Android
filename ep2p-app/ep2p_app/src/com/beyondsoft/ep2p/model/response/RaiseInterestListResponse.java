package com.beyondsoft.ep2p.model.response;

import java.util.List;

public class RaiseInterestListResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
//        public String borrowId;
        public List<RaiseInterestListItem> list;
    }

    public class RaiseInterestListItem
    {
        public String pid; //  标的id
        public double cardQuota;
        public int cardValidity;
        //        "cardQuota": 0.01,
        //        "cardValidity": 144

    }

}
