package com.beyondsoft.ep2p.widget;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.view.password.GridPasswordView;
import com.beyondsoft.ep2p.view.password.GridPasswordView.OnPasswordChangedListener;

/**
 * 
 * 支付输入密码Dialog
 * @ClassName:PayPasswrodDialog 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月23日
 *
 */
public class PayPasswrodDialog extends Dialog
{
    private GridPasswordView gpv_normal;
    private Button button1;
    private TextView app_dialog_title;
    private Context ctx;
    private ButtonOnClickListener listener;

    public interface ButtonOnClickListener
    {
        void onButton1Click(View v);
    }

    public PayPasswrodDialog(Context context)
    {
        super(context);
        this.ctx = context;
    }

    public PayPasswrodDialog(Context context, int theme)
    {
        super(context, theme);
        this.ctx = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {
        setContentView(R.layout.layout_buttom_input_pwd);
        app_dialog_title = (TextView) findViewById(R.id.tv_pass_title);
        gpv_normal = (GridPasswordView) findViewById(R.id.gpv_normal);
        gpv_normal.setOnPasswordChangedListener(new OnPasswordChangedListener()
        {
            
            @Override
            public void onMaxLength(String psw)
            {
                if (payPasswrodListener != null)
                {
                    payPasswrodListener.OnClick(psw);
                }
            }
            
            @Override
            public void onChanged(String psw)
            {
            	if(psw.length()==6){
            		button1.setEnabled(true);
            	}else{
            		button1.setEnabled(false);
            	}
            }
        });
        button1 = (Button) findViewById(R.id.bn_zf_pay);
        button1.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                if (listener != null)
                {
                    listener.onButton1Click(button1);
                    dismiss();
                }
            }
        });
        setCanceledOnTouchOutside(true);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#00000000")));
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        lp.width = (int) (0.8 * dm.widthPixels);
        dialogWindow.setAttributes(lp);
    }

    public void setButtonClickListener(ButtonOnClickListener listener)
    {
        this.listener = listener;
    }

    public void setTitle(String title)
    {
        if (app_dialog_title != null)
            app_dialog_title.setText(title);
    }


    public void hideTitle()
    {
        if (app_dialog_title != null)
        {
            app_dialog_title.setVisibility(View.GONE);
        }
    }

    public void setButtonText(String button1Text, String button2Text)
    {
        if (button1 != null)
        {
            button1.setText(button1Text);
        }
    }
    
    //申明接口对象  
    private PayPasswrodClickListener payPasswrodListener;

    //设置监听器 也就是实例化接口
    public void setOnPayPasswrodClickListener(PayPasswrodClickListener listener)
    {
        this.payPasswrodListener = listener;
    }
    //创建接口  
    public static interface PayPasswrodClickListener
    {
        public void OnClick(String payPasswrod);
    }
}
