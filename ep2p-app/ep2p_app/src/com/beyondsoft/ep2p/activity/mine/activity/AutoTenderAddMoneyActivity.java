package com.beyondsoft.ep2p.activity.mine.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.view.ASelectWheelPopupWindow;
import com.beyondsoft.ep2p.view.ASelectWheelPopupWindow.OnComfirmListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author hanxiaohui
 * @description 自主投标添加功能的第一个页面
 */

public class AutoTenderAddMoneyActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

	private TextView tv_title;
	private Button btn_money;
	private Button btn_proportion;
	private Button btn_next;
	private ImageView iv_money;
	private ImageView iv_proportion;

	private EditText et_money;
	private EditText et_proportion;

	private InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_tender_add_01);

		initView();
		// initData();
		initListener();
	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("添加");
		btn_money = (Button) findViewById(R.id.btn_auto_tender_add_011);
		btn_proportion = (Button) findViewById(R.id.btn_auto_tender_add_012);
		btn_next = (Button) findViewById(R.id.btn_auto_tender_add_next_01);
		et_money = (EditText) findViewById(R.id.et_auto_tender_add_money);
//		et_money.setEnabled(false);
		et_proportion = (EditText) findViewById(R.id.et_auto_tender_add_proportion);
		et_proportion.setEnabled(false);
		hideSoftInputMethod(et_proportion);
		iv_money = (ImageView) findViewById(R.id.iv_auto_tender_auto_round_011);
		iv_proportion = (ImageView) findViewById(R.id.iv_auto_tender_auto_round_012);

		iv_money.setImageResource(R.drawable.radiobutton_icon_on);
		btn_money.setEnabled(false);
		
		btn_next.setEnabled(false);
		
		imm = (InputMethodManager) AutoTenderAddMoneyActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	private void initListener() {

		btn_money.setOnClickListener(this);
		btn_proportion.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		et_money.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > 2) {
						s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
						et_money.setText(s);
						et_money.setSelection(s.length());
					}
				}
				if (s.toString().trim().substring(0).equals(".")) {
					s = "0" + s;
					et_money.setText(s);
					et_money.setSelection(2);
				}

				if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						et_money.setText(s.subSequence(0, 1));
						et_money.setSelection(1);
						return;
					}
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				String cardNo = s.toString();
				if(!TextUtils.isEmpty(cardNo)){
					btn_next.setEnabled(true);
				}
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String cardNo = s.toString();
				if(!TextUtils.isEmpty(cardNo)){
					btn_next.setEnabled(true);
				}else{
					btn_next.setEnabled(false);
				}
				
			}
		});
		et_proportion.setOnClickListener(this);
		et_proportion.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				String cardNo = s.toString();
				if(!TextUtils.isEmpty(cardNo)){
					btn_next.setEnabled(true);
				}
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String cardNo = s.toString();
				if(!TextUtils.isEmpty(cardNo)){
					btn_next.setEnabled(true);
				}else{
					btn_next.setEnabled(false);
				}
				
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_auto_tender_add_011:{
			// 关闭软键盘
			imm.hideSoftInputFromWindow(et_money.getWindowToken(), 0);
			et_proportion.setText("");
			et_money.clearFocus();
			et_proportion.clearFocus();
			iv_money.setImageResource(R.drawable.radiobutton_icon_on);
			iv_proportion.setImageResource(R.drawable.radiobutton_icon_off);
			btn_money.setEnabled(false);
			btn_proportion.setEnabled(true);
			et_money.setEnabled(true);
			et_proportion.setEnabled(false);
			et_money.requestFocus();
			// 打开软键盘
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);}
			break;
		case R.id.btn_auto_tender_add_012:{
			et_money.setText("");
			et_money.clearFocus();
			et_proportion.clearFocus();
			iv_money.setImageResource(R.drawable.radiobutton_icon_off);
			iv_proportion.setImageResource(R.drawable.radiobutton_icon_on);
			btn_money.setEnabled(true);
			btn_proportion.setEnabled(false);
			et_money.setEnabled(false);
			et_proportion.setEnabled(true);
			et_proportion.requestFocus();

			class MyProportionOnComfirmListener implements OnComfirmListener {

				@Override
				public void onArticleSelected(String ms) {
					et_proportion.setText(ms);
					CharSequence text = et_proportion.getText();
					if (text instanceof Spannable) {
						Spannable spanText = (Spannable) text;
						Selection.setSelection(spanText, text.length());
					}
				}

			}

			ArrayList<String> ds = new ArrayList<String>();
			ds.add("10%");
			ds.add("20%");
			ds.add("30%");
			ds.add("40%");
			ds.add("50%");
			ds.add("60%");
			ds.add("70%");
			ds.add("80%");
			ds.add("90%");
			ds.add("100%");
			// 传入上下文，数据，选择按钮监听器,标题
			setPopwindowsOut(this, ds, new MyProportionOnComfirmListener(), "选择余额比例");
		}
			break;
		case R.id.et_auto_tender_add_proportion:{
			et_money.setText("");
			et_money.clearFocus();
			et_proportion.clearFocus();
			iv_money.setImageResource(R.drawable.radiobutton_icon_off);
			iv_proportion.setImageResource(R.drawable.radiobutton_icon_on);
			btn_money.setEnabled(true);
			btn_proportion.setEnabled(false);
			et_proportion.requestFocus();
			
			class MyProportionOnComfirmListener implements OnComfirmListener {
				
				@Override
				public void onArticleSelected(String ms) {
					et_proportion.setText(ms);
					CharSequence text = et_proportion.getText();
					if (text instanceof Spannable) {
						Spannable spanText = (Spannable) text;
						Selection.setSelection(spanText, text.length());
					}
				}
				
			}
			
			ArrayList<String> ds = new ArrayList<String>();
			ds.add("10%");
			ds.add("20%");
			ds.add("30%");
			ds.add("40%");
			ds.add("50%");
			ds.add("60%");
			ds.add("70%");
			ds.add("80%");
			ds.add("90%");
			ds.add("100%");
			// 传入上下文，数据，选择按钮监听器,标题
			setPopwindowsOut(this, ds, new MyProportionOnComfirmListener(), "选择余额比例");
		}
			break;
		case R.id.btn_auto_tender_add_next_01:
			Intent intent = new Intent(this, AutoTenderAddParameterActivity.class);
			if(!et_money.getText().toString().trim().equals("") && !et_money.getText().toString().trim().equals("0")){
				intent.putExtra("amount", et_money.getText().toString().trim());
				intent.putExtra("balanceratio", "0%");
				startActivity(intent);
			}else if(!et_proportion.getText().toString().trim().equals("")&& !et_proportion.getText().toString().trim().equals("0")){
				intent.putExtra("amount", "0");
				intent.putExtra("balanceratio", et_proportion.getText().toString().trim());
				startActivity(intent);
			}else{
				CommonUtils.toastMsgShort(mContext, "投资金额或余额比例至少有一个不为空！");
			}
			break;

		default:
			break;
		}

	}

	private ASelectWheelPopupWindow sharePopupWindowLine;

	private void setPopwindowsOut(Context cx, ArrayList<String> ds, OnComfirmListener mlistener, String title) {
		// 传入上下文，数据，选择按钮监听器
		sharePopupWindowLine = new ASelectWheelPopupWindow(cx, ds, mlistener, title);
		sharePopupWindowLine.showWindow();
		sharePopupWindowLine.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
			}
		});
		sharePopupWindowLine.showAtLocation(findViewById(R.id.title_content_tv), Gravity.TOP, 0, 0);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
	
	 // 隐藏系统键盘
    public void hideSoftInputMethod(EditText ed){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
         
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if(currentVersion >= 16){
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        }
        else if(currentVersion >= 14){
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
         
        if(methodName == null){
            ed.setInputType(InputType.TYPE_NULL);  
        }
        else{
            Class<EditText> cls = EditText.class;  
            Method setShowSoftInputOnFocus;  
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);  
                setShowSoftInputOnFocus.invoke(ed, false); 
            } catch (NoSuchMethodException e) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  
        }  
    }

}
