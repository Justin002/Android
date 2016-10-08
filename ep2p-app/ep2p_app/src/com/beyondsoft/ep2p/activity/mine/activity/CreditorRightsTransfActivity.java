package com.beyondsoft.ep2p.activity.mine.activity;

import java.text.DecimalFormat;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.CreditorRightsTransferInfoBean;
import com.beyondsoft.ep2p.model.response.CreditorRightsTransferResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.widget.CreditRightTranferInstructionsDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 债权转让页面
 * 
 * @author hardy.zhou
 *
 */
public class CreditorRightsTransfActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private TextView tv_cerditor_rights_transf_agreement;
	private EditText et_creditright_transfer_price;
	private ImageView title_right_image;
	private Button bt_ensure_transf;
	private BaseService baseService;
	private static final int FLAT_CREDITOR_TRANSFER_DETAIL = 100;
    private CreditorRightsTransferInfoBean bean;
    
    private TextView tv_borrowCode;
    private TextView tv_investCapital;
    private TextView tv_qici;
    private TextView tv_alreadyBenxi;
    private TextView tv_surperCapital;
    private TextView tv_currentInterest;
    private TextView tv_currentAllInterest;
    private TextView tv_youxiaoqujian;
    private TextView tv_maxValue;
    
    private String borrowId;
    private String transferId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creditor_rights_transf);
		baseService = new BaseService();
		initView();
		doGetCreditorTransferDetailInfo();
		setListener();
	}
	
	private void doGetCreditorTransferDetailInfo(){
		Intent intent = getIntent();
		borrowId = intent.getStringExtra("borrowId");
		transferId = intent.getStringExtra("transferId");
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("borrowId", borrowId);
		params.put("transferId", transferId);
		connection(baseService.getStringRequest(FLAT_CREDITOR_TRANSFER_DETAIL, URL.URL_CREDITOR_TRANSFER_DETAIL, params, mContext));
	};

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.creditor_rights_transf);
		//tv_cerditor_rights_transf_agreement = (TextView) findViewById(R.id.tv_cerditor_rights_transf_agreement);
		//tv_cerditor_rights_transf_agreement.setOnClickListener(this);
		title_right_image = (ImageView)findViewById(R.id.title_right_image);
		et_creditright_transfer_price = (EditText) findViewById(R.id.et_creditright_transfer_price);
		title_right_image.setOnClickListener(this);
		bt_ensure_transf = (Button) findViewById(R.id.bt_ensure_transf);
		bt_ensure_transf.setOnClickListener(this);
		
	    tv_borrowCode = (TextView) findViewById(R.id.tv_borrowCode);
	    tv_investCapital = (TextView) findViewById(R.id.tv_investCapital);
	    tv_qici = (TextView) findViewById(R.id.tv_qici);
	    tv_alreadyBenxi = (TextView) findViewById(R.id.tv_alreadyBenxi);
	    tv_surperCapital = (TextView) findViewById(R.id.tv_surperCapital);
	    tv_currentInterest = (TextView) findViewById(R.id.tv_currentInterest);
	    tv_currentAllInterest = (TextView) findViewById(R.id.tv_currentAllInterest);
	    tv_youxiaoqujian = (TextView) findViewById(R.id.tv_youxiaoqujian);
	    tv_maxValue = (TextView) findViewById(R.id.tv_maxValue);
	    tv_maxValue.setOnClickListener(this);
	}
	
	private void initData(){
		DecimalFormat df = new DecimalFormat("########0.00");
		tv_borrowCode.setText(bean.getBorrowCode());
		tv_investCapital.setText(df.format(bean.getInvestCapital())+"元");
		tv_qici.setText(bean.getSurperDead()+"/"+bean.getTotalDead());
		tv_alreadyBenxi.setText(df.format(bean.getAlreadyBenxi())+"元");
		tv_surperCapital.setText(df.format(bean.getSurperCapital())+"元");
		tv_currentInterest.setText(df.format(bean.getCurrentInterest())+"元");
		tv_currentAllInterest.setText(df.format(bean.getCurrentAllInterest())+"元");
		tv_youxiaoqujian.setText("有效区间：￥"+df.format(bean.getMinValue())+ "~ ￥"+df.format(bean.getMaxValue()));
		
	}
	
	private void setListener() {
		et_creditright_transfer_price.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String money = s.toString();
				if (money != null & !"".equals(money = money.trim())) {
					bt_ensure_transf.setEnabled(true);
				} else {
					bt_ensure_transf.setEnabled(false);
				}
			}
		});
	}

	 // 隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);

    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    
    @Override
    public void onResponse(int tag, Object values) {
    	// TODO Auto-generated method stub
    	super.onResponse(tag, values);
    	switch(tag){
    	case FLAT_CREDITOR_TRANSFER_DETAIL:
    		
    		CreditorRightsTransferResponse response = gson.fromJson(values.toString(), CreditorRightsTransferResponse.class);
    		if(response!=null){
    			CreditorRightsTransferInfoBean infoBean =  response.getCreditorRightsTransferInfoBean();
    			if(infoBean!=null){
    				bean=infoBean;
    				initData();
    			}
    		}
    		
    		break;
    		default:
    			break;
    	}
    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_ensure_transf: {
			String price_str = et_creditright_transfer_price.getText().toString();
			double price = Double.parseDouble(price_str);
			double tansferFee = Double.parseDouble(bean.getBizTransferFee());
			DecimalFormat df = new DecimalFormat("########0.00");
			if(bean.getMinValue()<=price&&price<=bean.getMaxValue())
			{
				Intent intent = new Intent();
				intent.putExtra("surperCapital", df.format(bean.getSurperCapital()));
				intent.putExtra("transferPrice", df.format(price));
				intent.putExtra("tansferFee",df.format(tansferFee*price));
				intent.putExtra("transferId", transferId);
				intent.putExtra("borrowId", borrowId);
				intent.putExtra("amount", et_creditright_transfer_price.getText().toString());
				intent.setClass(mContext, CreditorRightsTransfNextActivity.class);
				startActivity(intent);
			}
			else
			{
				CommonUtils.toastMsgShort(mContext, "转让价格只能在"+bean.getMinValue()+"~"+bean.getMaxValue()+"之间");
			}
		}
			break;
		case R.id.title_right_image: {
			CreditRightTranferInstructionsDialog dialog = new CreditRightTranferInstructionsDialog(mContext);
			dialog.show();
		}
			break;
		case R.id.tv_maxValue:{
			et_creditright_transfer_price.setText(""+bean.getMaxValue());
			break;
		}
			default:
			break;
		}

	}

}
