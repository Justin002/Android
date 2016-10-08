package com.beyondsoft.ep2p.model.response;


import java.util.List;


/**
 * 
 * 获取Banner   Bean
 * @Description;这里用一句话描述这个类的作用 
 * @date;2016年2月17日
 *
 */
public class HomeBannerResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;

    public class Result extends BaseResponse
    {
        /** 用一句话描述这个变量表示什么，取值范围是什么 */
        private static final long serialVersionUID = 1L;
        //        public int pageCount;//总条数
        public List<BannerItem> relist;
    }

    public class BannerItem
    {
        public String pid;
        public String advId;//栏位id
        public String titleName;//标题名称
        public String status;//状态
        public String fileId;//文件id
        public String createUser;//创建人
        public String createTime;//创建时间
        public String lastUpdateUser;//最后更新人
        public String lastUpdateTime;//最后更新时间
        public String url;//路径
        public String advOrder;//排序
        public String fileUrl;//文件URL
        public String fileName;// 文件名
    }

}
