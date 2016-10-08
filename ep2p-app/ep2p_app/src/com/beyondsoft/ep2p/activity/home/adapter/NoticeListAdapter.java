package com.beyondsoft.ep2p.activity.home.adapter;


import java.util.List;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.SysNoticeResponse.SysNoticeItem;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.TimeUtil;


public class NoticeListAdapter<T> extends BaseAdapter
{
    private Context mContext;
    private List<T> message;
    private LayoutInflater mInflater;

    public NoticeListAdapter(Context ct, List<T> mList)
    {
        super();
        this.mContext = ct;
        this.mInflater = (LayoutInflater) ct
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.message = mList;
    }

    public void ClearListData()
    {
        if (message.size() > 0 && message != null)
        {
            message.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return message.size() > 0 ? message.size() : 0;
    }

    @Override
    public Object getItem(int arg0)
    {
        return message.get(arg0);
    }

    @Override
    public long getItemId(int arg0)
    {
        return arg0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int arg0, View contentView, ViewGroup arg2)
    {
        ViewHolder mViewHolder;
        if (contentView == null)
        {
            mViewHolder = new ViewHolder();
            contentView = mInflater.inflate(R.layout.layout_notice_listview_item, null);

            mViewHolder.iv_image_falg = (ImageView) contentView
                    .findViewById(R.id.iv_image_falg);
            mViewHolder.tv_stitle = (TextView) contentView.findViewById(R.id.tv_stitle);
            mViewHolder.tv_stime = (TextView) contentView.findViewById(R.id.tv_stime);
            mViewHolder.tv_notice_context = (TextView) contentView
                    .findViewById(R.id.tv_notice_context);

            contentView.setTag(mViewHolder);
        }
        else
        {
            mViewHolder = (ViewHolder) contentView.getTag();
        }
        SysNoticeItem noticeItem = (SysNoticeItem) getItem(arg0);

        //灰的是已读    亮的是未读 【是否已读  0:未读  1：已读】
        //【灰色是已读没有黄色图标，亮的未读并且有黄色图标】
        if (!TextUtils.isEmpty(noticeItem.isReading)&&noticeItem.isReading.equals("0"))
        {
            mViewHolder.iv_image_falg.setVisibility(View.VISIBLE);
        }
        else
        {//已读
            mViewHolder.iv_image_falg.setVisibility(View.GONE);
            mViewHolder.tv_stitle.setTextColor(mContext.getResources().getColor(
                R.color.e_text_bg_huise_black));
        }

        //        mViewHolder.iv_image_falg.setBackground(background);//#TODO 网络图片显示noticeItem.fileUrl
        mViewHolder.tv_stitle.setText(StringUtils.isTestNull(noticeItem.titleName));
//        mViewHolder.tv_stime.setText(""
//            + TimeUtil.getDescriptionTimeFromTimestamp(TimeUtil.stringToLong(
//                StringUtils.isTestNull(noticeItem.createTime), "yyyy-MM-dd HH:MM:SS"))); //转化为   刚刚  
        if(!TextUtils.isEmpty(noticeItem.createTime)){
            mViewHolder.tv_stime.setText(""+TimeUtil.get2Time2(TimeUtil.stringToLong((noticeItem.createTime),"yyyy-MM-dd HH:MM:SS")));
        }
        
        if(!TextUtils.isEmpty(noticeItem.content)){
            mViewHolder.tv_notice_context.setText(Html2Text(noticeItem.content));
//          mViewHolder.tv_notice_context.setText(Html.fromHtml(noticeItem.content).toString());
        }
        return contentView;
    }

    class ViewHolder
    {
        ImageView iv_image_falg;//图标
        TextView tv_stitle; //标题
        TextView tv_stime; //时间
        TextView tv_notice_context; //内容
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
