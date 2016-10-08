package com.beyondsoft.ep2p.model.response;

/**
 * 注册响应的bean
 * @ClassName:RegisteredResponse 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2016年1月7日
 *
 */
public class RegisteredResponse extends BaseResponse
{
    /** 用一句话描述这个变量表示什么，取值范围是什么 */
    private static final long serialVersionUID = 1L;
    public Result result = null;
    
    public class Result extends BaseResponse
    {
       public  String registeredSuccess;//:"恭喜注册成功，获得88元现金红包。",TODO 后台配置html  标签
    }

}
