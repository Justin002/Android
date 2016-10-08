package com.beyondsoft.ep2p.model.response;


import java.util.List;


/**
 * 
 * 风控信息
 * @ClassName:FkRiskcontrolResponse 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2016年1月18日
 *
 */
public class FkRiskcontrolResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        public String pid; //FFFFFFFFEF0724CA!EF855D5053E54722A035D4172E92B1,
        public String homeDesc; //8541抵押贷001,
        public String riskDesc; //8540抵押贷001
        public String guaAcc; //8540抵押贷001,
        public String guaOrgName; //张氏集团旗下担保公司,
        public List<RiskcontrolItem> list;
    }

    public class RiskcontrolItem
    {
        public String pid; //  标的id3D57E553!96B7FCE392CD491CBD243CFB6879BFDC,
        public String fileId; //78BE158!BDAF5918E5D744FB844FBEF70FBD65A5,
        public String borrowId;// FFFFFFFFEF0724CA!EF855D5053E54722A035D4172E92B1,
        public String status;// 1,
        public String createUser; //FFFFFFFFE1802C09!835AE4A65E624C04B079BC8BE55D8A,
        public String createTime; //2015-12-24 16:30:01,
        public String lastUpdateTime; //,
        public String borFileRelType; //7,
        public String fileName; //6b2d663cce712362f23e63ad18f83af9,
        public String fileSize; //94553,
        public String fileType; //jpg,
        public String fileUrl; //upload/business/2015/12/24/30bc98a3-a929-4838-9262-a125231d48bc.jpg
    }

}
