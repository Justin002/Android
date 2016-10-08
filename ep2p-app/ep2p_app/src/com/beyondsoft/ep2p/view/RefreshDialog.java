package com.beyondsoft.ep2p.view;


import android.content.Context;


/**
 * ssy
 * 刷新数据dailog工具类
 */
public class RefreshDialog
{
    private static CustomerProgressDialog customerProgressDialog = null;

    /**
     * @Description 开始刷新对话框为普通Activity使用
     * @param context
     *            isTop 【false：置顶，true：不置顶】
     * @see
     */
    public static void startProgressDialog(Context mContext, String msg, boolean isTop)
    {
        if (customerProgressDialog == null)
        {
            customerProgressDialog = CustomerProgressDialog.createDialog(mContext);
            customerProgressDialog.setMessage(msg);
            customerProgressDialog.setCancelable(isTop);
        }
        if (!customerProgressDialog.isShowing())
        {
            customerProgressDialog.show();
            customerProgressDialog.setCancelable(isTop);
        }
    }

    public static void startProgressDialog(Context mContext, String msg)
    {
        if (customerProgressDialog == null)
        {
            customerProgressDialog = CustomerProgressDialog.createDialog(mContext);
            customerProgressDialog.setMessage(msg);
            customerProgressDialog.setCancelable(false);
        }
        if (!customerProgressDialog.isShowing())
        {
            customerProgressDialog.show();
            customerProgressDialog.setCancelable(false);
        }
    }

    /**
     * 停止刷新对话框
     * 
     * @Description 停止刷新对话框
     * @see
     * @since
     */
    public static void stopProgressDialog()
    {
        if (customerProgressDialog != null && customerProgressDialog.isShowing())
        {
            customerProgressDialog.dismiss();
            customerProgressDialog = null;
        }
    }

}
