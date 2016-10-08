package com.beyondsoft.ep2p.activity.core;


import android.content.Context;

import com.beyondsoft.ep2p.model.TzSucceedInfoBean;
import com.beyondsoft.ep2p.model.TzZRSucceedInfoBean;


public class MainHolder
{
    private Context context;
    private static MainHolder instance;

    private MainHolder(Context context)
    {
        this.context = context;
    };

    public static MainHolder Instance(Context context)
    {
        if (instance == null)
            instance = new MainHolder(context);
        return instance;
    }

    //投资成功的数据bean
    public TzSucceedInfoBean mTzSucceedInfoBean;

    public TzSucceedInfoBean getmTzSucceedInfoBean()
    {
        return mTzSucceedInfoBean;
    }

    public void setmTzSucceedInfoBean(TzSucceedInfoBean mTzSucceedInfoBean)
    {
        this.mTzSucceedInfoBean = mTzSucceedInfoBean;
    }
    
    public String ProjectInformation2ejh_pid = ""; //项目描述【理财类型入口】
    public String getProjectInformation2ejh_pid()
    {
        return ProjectInformation2ejh_pid;
    }

    public void setProjectInformation2ejh_pid(String projectInformation2ejh_pid)
    {
        ProjectInformation2ejh_pid = projectInformation2ejh_pid;
    }

    public String ProjectInformation_Pid = ""; //项目信息pid 【首页入口】
    public String getProjectInformation_Pid()
    {
        return ProjectInformation_Pid;
    }

    public void setProjectInformation_Pid(String projectInformation_Pid)
    {
        ProjectInformation_Pid = projectInformation_Pid;
    }
    
    public String customId = ""; //借款人入参pid 

    public String getCustomId()
    {
        return customId;
    }

    public void setCustomId(String customId)
    {
        this.customId = customId;
    }
    
    public String tzjlPid = "";//投资记录的pid入参

    public String getTzjlPid()
    {
        return tzjlPid;
    }

    public void setTzjlPid(String tzjlPid)
    {
        this.tzjlPid = tzjlPid;
    }
    
    public MyHandler handle = null;
    public MyHandler getHandle()
    {
        return handle;
    }

    public void setHandle(MyHandler handle)
    {
        this.handle = handle;
    }
    
    public boolean  isHeadSetting = false;//头像是否设置成功

    public boolean isHeadSetting()
    {
        return isHeadSetting;
    }

    public void setHeadSetting(boolean isHeadSetting)
    {
        this.isHeadSetting = isHeadSetting;
    }
    
    
    public boolean isSystemRead = false;//系统公告是否已读  单条

    public boolean isSystemRead()
    {
        return isSystemRead;
    }

    public void setSystemRead(boolean isSystemRead)
    {
        this.isSystemRead = isSystemRead;
    }
    
    private boolean isLoginRegister = false;

    public boolean isLoginRegister()
    {
        return isLoginRegister;
    }

    public void setLoginRegister(boolean isLoginRegister)
    {
        this.isLoginRegister = isLoginRegister;
    }
    
    private boolean isDLAuthentication = false;//是否大陆实名认证

    public boolean isDLAuthentication()
    {
        return isDLAuthentication;
    }

    public void setDLAuthentication(boolean isDLAuthentication)
    {
        this.isDLAuthentication = isDLAuthentication;
    }
    
    public TzZRSucceedInfoBean mTzZRSucceedInfoBean;//转让专区  债权投资成功数据

    public TzZRSucceedInfoBean getmTzZRSucceedInfoBean()
    {
        return mTzZRSucceedInfoBean;
    }

    public void setmTzZRSucceedInfoBean(TzZRSucceedInfoBean mTzZRSucceedInfoBean)
    {
        this.mTzZRSucceedInfoBean = mTzZRSucceedInfoBean;
    }
    
    public String mborDeadline; // 借款期限

    public String getMborDeadline()
    {
        return mborDeadline;
    }

    public void setMborDeadline(String mborDeadline)
    {
        this.mborDeadline = mborDeadline;
    }
    
    public boolean isEarnings = false;//是否收益计算器按钮  立即投资  可点击

    public boolean isEarnings()
    {
        return isEarnings;
    }

    public void setEarnings(boolean isEarnings)
    {
        this.isEarnings = isEarnings;
    }
    
//    public boolean isRegistered = false; //是否领取注册红包  [false:没有领取红包，true：领取红包]
//    public boolean isAttestation = false;//是否领取认证红包
//
//    public boolean isRegistered()
//    {
//        return isRegistered;
//    }
//
//    public void setRegistered(boolean isRegistered)
//    {
//        this.isRegistered = isRegistered;
//    }
//
//    public boolean isAttestation()
//    {
//        return isAttestation;
//    }
//
//    public void setAttestation(boolean isAttestation)
//    {
//        this.isAttestation = isAttestation;
//    }
    
    public int SystemNotice_Index = 0;

    public int getSystemNotice_Index()
    {
        return SystemNotice_Index;
    }

    public void setSystemNotice_Index(int systemNotice_Index)
    {
        SystemNotice_Index = systemNotice_Index;
    }
    
}
