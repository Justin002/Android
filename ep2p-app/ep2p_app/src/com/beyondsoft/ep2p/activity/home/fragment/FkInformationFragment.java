package com.beyondsoft.ep2p.activity.home.fragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.home.adapter.FkImgAdapter;
import com.beyondsoft.ep2p.model.response.FkRiskcontrolResponse;
import com.beyondsoft.ep2p.model.response.FkRiskcontrolResponse.RiskcontrolItem;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.MyHorizontalScrollView;
import com.beyondsoft.ep2p.view.MyHorizontalScrollView.CurrentImageChangeListener;
import com.beyondsoft.ep2p.view.MyHorizontalScrollView.OnItemClickListener;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.google.gson.reflect.TypeToken;


/**
 * 
 * 风控信息
 * @ClassName:ProjectInformationFragment 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月18日
 *
 */
public class FkInformationFragment extends BaseFragment
{
    private View mParent;
    private TextView tv_guarantee_jg, tv_guarantee_cn, tv_guarantee_zs;
    private List<RiskcontrolItem> checkedList = new ArrayList<RiskcontrolItem>();
    private MyHorizontalScrollView mMyHorizontalScrollView;
    private FkImgAdapter checkedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        mParent = inflater.inflate(R.layout.fragment_fk_information, null);
        return mParent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    /**
     * 初始化组件
     */
    private void initView()
    {
        tv_guarantee_jg = (TextView) mParent.findViewById(R.id.tv_guarantee_jg);
        tv_guarantee_cn = (TextView) mParent.findViewById(R.id.tv_guarantee_cn);
        tv_guarantee_zs = (TextView) mParent.findViewById(R.id.tv_guarantee_zs);

        mMyHorizontalScrollView = (MyHorizontalScrollView) mParent
                .findViewById(R.id.imgList);
    }

    private String mPid = "";

    private void initData()
    {
        mPid = MainHolder.Instance(mContext).getProjectInformation_Pid();
        getTrabsferProductData();
    }

    //查询风控信息
    private void getTrabsferProductData()
    {
//        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pid", mPid); //标的id 
        connection(registerService.getStringRequest(2, URL.API_EPLAN_RISKCONTROL, params,
            this, mContext));
    }

    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        RefreshDialog.stopProgressDialog();
        FkRiskcontrolResponse mTrabserProductInfoResponse = (FkRiskcontrolResponse) StringRequest2
                .Json2Object(values.toString(), new TypeToken<FkRiskcontrolResponse>()
                {
                });
        if (mTrabserProductInfoResponse != null)
        {
            String str_dbjg = StringUtils
                    .isTestNull(mTrabserProductInfoResponse.result.guaOrgName) + "";
            //            //替换
            //            StringBuffer str = new StringBuffer(str_dbjg);
            //            if(str_dbjg.length()>4){
            //                str.replace(0, str_dbjg.length()-4, "****"); 
            //            }
            //            str_dbjg = str.toString();
            
            
            tv_guarantee_jg.setText(getString_txt(R.string.txt_guarantee_jg) + Html2Text(str_dbjg));
            tv_guarantee_cn.setText(getString_txt(R.string.txt_guarantee_cn)
                +Html2Text(StringUtils.isTestNull(mTrabserProductInfoResponse.result.guaAcc))+ "");
            tv_guarantee_zs.setText(getString_txt(R.string.txt_guarantee_zs)
                + Html2Text(StringUtils.isTestNull(mTrabserProductInfoResponse.result.homeDesc))
                + "");

            checkedList = mTrabserProductInfoResponse.result.list;

            if (checkedList.size() > 0)
            {
                checkedAdapter = new FkImgAdapter(mContext, checkedList);
                //                checkedContack.setAdapter(checkedAdapter);

                //添加滚动回调
                mMyHorizontalScrollView
                        .setCurrentImageChangeListener(new CurrentImageChangeListener()
                        {
                            @Override
                            public void onCurrentImgChanged(int position,
                                    View viewIndicator)
                            {
//                                viewIndicator.setBackgroundColor(Color.parseColor("#AA024DA4"));
                            }
                        });
                //添加点击回调
                mMyHorizontalScrollView.setOnItemClickListener(new OnItemClickListener()
                {

                    @Override
                    public void onClick(View view, int position)
                    {
//                        view.setBackgroundColor(Color.parseColor("#AA024DA4"));
                    }
                });
                //设置适配器
                mMyHorizontalScrollView.initDatas(checkedAdapter);
            }
        }

    }

    @Override
    public void onErrorResponse(int tag, String error)
    {
        super.onErrorResponse(tag, error);
        RefreshDialog.stopProgressDialog();
        CommonUtils.toastMsgShort(mContext, error);
    }

    private String Html2Text(String inputString)
    {
        String htmlStr = inputString; // 含html标签的字符串      
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        java.util.regex.Pattern p_html1;
        java.util.regex.Matcher m_html1;

        try
        {
            String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>      
            String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>      
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式      
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签      

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签      

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签      

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签      

            textStr = htmlStr;
        }
        catch (Exception e)
        {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串      
    }
}
