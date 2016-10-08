package com.beyondsoft.ep2p.activity.home.fragment;


import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.model.response.BorrowerInformationResponse;
import com.beyondsoft.ep2p.model.response.ProjectInformationResponse;
import com.beyondsoft.ep2p.model.response.TrabserProductInfoResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.google.gson.reflect.TypeToken;


/**
 * 
 * 项目信息
 * @ClassName:ProjectInformationFragment 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月18日
 *
 */
public class ProjectInformationFragment extends BaseFragment
{

    private Activity mContext;
    private View mParent;

    private TextView tv_borrower_phone, tv_borrower_name;
    private TextView tv_borrower_sex, tv_borrower_marital;
    private TextView tv_borrower_age, tv_borrower_hj;
    private TextView tv_borrower_yt, tv_borrower_ly, tv_borrower_dy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        mParent = inflater.inflate(R.layout.fragment_project_information, null);
        return mParent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView();
        initData();
    }

    /**
     * 初始化组件
     */
    private void initView()
    {
        tv_borrower_phone = (TextView) mParent.findViewById(R.id.tv_borrower_phone);
        tv_borrower_name = (TextView) mParent.findViewById(R.id.tv_borrower_name);

        tv_borrower_sex = (TextView) mParent.findViewById(R.id.tv_borrower_sex);
        tv_borrower_marital = (TextView) mParent.findViewById(R.id.tv_borrower_marital);

        tv_borrower_age = (TextView) mParent.findViewById(R.id.tv_borrower_age);
        tv_borrower_hj = (TextView) mParent.findViewById(R.id.tv_borrower_hj);

        tv_borrower_yt = (TextView) mParent.findViewById(R.id.tv_borrower_yt);
        tv_borrower_ly = (TextView) mParent.findViewById(R.id.tv_borrower_ly);
        tv_borrower_dy = (TextView) mParent.findViewById(R.id.tv_borrower_dy);

    }

   private  String mPid = "",mCustomId = "";
    private void initData()
    {
        mPid = MainHolder.Instance(mContext).getProjectInformation_Pid();
        mCustomId = MainHolder.Instance(mContext).getCustomId();
        Logs.d("项目信息pid="+mPid);
        getXMProductData();
        
        getCustomerByPidData();
    }
    
    //获取借款人基本信息
    private void getCustomerByPidData()
    {
//        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pid", mCustomId); //标的id 
        connection(registerService.getStringRequest(1, URL.API_HOME_CustomerByPid, params,
            this,mContext));
    }
    //获取项目信息
    private void getXMProductData()
    {
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pid", mPid); //标的id 
        connection(registerService.getStringRequest(2, URL.API_HOME_BorrowOtherInfo, params,
            this,mContext));
    }
   
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        switch (tag)
        {
            case 1 : //借款人 基本信息
                BorrowerInformationResponse mBorrowerInformationResponse = (BorrowerInformationResponse) StringRequest2
                .Json2Object(values.toString(),
                    new TypeToken<BorrowerInformationResponse>()
                    {
                    }); 
                if(mBorrowerInformationResponse!=null){
                    tv_borrower_phone.setText(getString_txt(R.string.txt_borrower_phone)+
                        StringUtils.isTestNull(mBorrowerInformationResponse.result.privatePhoneNo));
                    tv_borrower_name.setText(getString_txt(R.string.txt_borrower_name)+
                        StringUtils.isTestNull(mBorrowerInformationResponse.result.privateCustName));
                    
                    tv_borrower_sex.setText(getString_txt(R.string.txt_borrower_sex)+"男");
                    tv_borrower_marital.setText(getString_txt(R.string.txt_borrower_marital)+"未婚");
                    //isMarriage 1
                    
                    tv_borrower_age.setText(getString_txt(R.string.txt_borrower_age)+
                        StringUtils.isTestNull(mBorrowerInformationResponse.result.age));
                    tv_borrower_hj.setText(getString_txt(R.string.txt_borrower_hj)+
                        StringUtils.isTestNull(mBorrowerInformationResponse.result.ResReg));
                }
                getXMProductData();
                break;
            case 2 : //项目信息
                RefreshDialog.stopProgressDialog();
                ProjectInformationResponse mProjectInformationResponse = (ProjectInformationResponse) StringRequest2
                .Json2Object(values.toString(),
                    new TypeToken<ProjectInformationResponse>()
                    {
                    });  
                if(mProjectInformationResponse!=null){
                    tv_borrower_yt.setText(getString_txt(R.string.txt_borrower_yt)+
                        StringUtils.isTestNull(mProjectInformationResponse.result.borrowUse));
                    tv_borrower_ly.setText(getString_txt(R.string.txt_borrower_ly)+
                        StringUtils.isTestNull(mProjectInformationResponse.result.payment));
                    tv_borrower_dy.setText(getString_txt(R.string.txt_borrower_dy)+
                        StringUtils.isTestNull(mProjectInformationResponse.result.hostageInfo));
                }
                break;
            default :
                break;
        }

    }
    @Override
    public void onErrorResponse(int tag, String error)
    {
        super.onErrorResponse(tag, error);
        RefreshDialog.stopProgressDialog();
        CommonUtils.toastMsgShort(mContext, error);
    }

}
