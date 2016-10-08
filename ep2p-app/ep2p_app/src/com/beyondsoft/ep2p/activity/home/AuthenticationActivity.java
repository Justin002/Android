package com.beyondsoft.ep2p.activity.home;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.mine.activity.NonMainlandAuthActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.RealNameRZResponse;
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
 * 实名认证
 * 
 * @ClassName:AuthenticationActivity
 * @Description: 这里用一句话描述这个类的作用
 * @author: ssy
 * @date: 2015年12月21日
 *
 */
public class AuthenticationActivity extends BaseActivity implements OnClickListener {

	// Title
	private TextView tv_title;
	private ImageView iv_right_falg;

	private EditText real_name_et, id_card_et;
	private Button submit_auth_btn;
	private TextView tv_center_no_mainland;
	private int isfinish_Authentication = 11; // 认证完成
	private int isCertification = 0;//[10:未认证]
    private String mToken = "";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentication);
		initTitle();
		initView();
		initData();
		setListener();
	}

	private void initTitle() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("实名认证");
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setVisibility(View.GONE);
	}

	private void initView() {
		real_name_et = (EditText) findViewById(R.id.real_name_et);
		id_card_et = (EditText) findViewById(R.id.id_card_et);

		submit_auth_btn = (Button) findViewById(R.id.submit_auth_btn);
		submit_auth_btn.setOnClickListener(this);
		tv_center_no_mainland = (TextView) findViewById(R.id.tv_center_no_mainland);
		tv_center_no_mainland.setOnClickListener(this);
	}

	private void initData() {
	    isCertification =   getIntent().getIntExtra(Constants.IS_Certification_CHECK, 0);
//	    if(isCertification == 10){//安全中心：未认证
//	        submit_auth_btn.setEnabled(false);
//	    }
	    
	    mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        Logs.d("mToken==:" + mToken);
	}
	
    private String str_real_name_et = "", str_id_card_et="";
    private void setListener() {
        
        real_name_et.addTextChangedListener(new TextWatcher() {//真实姓名

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                str_real_name_et = s.toString().trim();
                
                if (!TextUtils.isEmpty(str_id_card_et) && !TextUtils.isEmpty(str_real_name_et)) {
                    submit_auth_btn.setEnabled(true);
                } else {
                    submit_auth_btn.setEnabled(false);
                }
            }
        });
        id_card_et.addTextChangedListener(new TextWatcher() {//身份证号

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                str_id_card_et = s.toString().trim();
                if (!TextUtils.isEmpty(str_id_card_et) && !TextUtils.isEmpty(str_real_name_et)) {
                    submit_auth_btn.setEnabled(true);
                } else {
                    submit_auth_btn.setEnabled(false);
                }
            }
        });
    }

    //实名认证（大陆）
    private void getRealNameData()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", mToken);
        params.put("sname", real_name_et.getText().toString().trim());//真实姓名
        params.put("identificationNo", id_card_et.getText().toString().trim());//身份证号码
        connection(registerService.getStringRequest(1, URL.API_REALNAMEDL, params,this));
    }

    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        RefreshDialog.stopProgressDialog();
        RealNameRZResponse mRealNameRZResponse = (RealNameRZResponse) StringRequest2
                .Json2Object(values.toString(),
                    new TypeToken<RealNameRZResponse>()
                    {
                    });
        
        if(mRealNameRZResponse.isStatus()){
            if(!TextUtils.isEmpty(mRealNameRZResponse.result.authenticationSuccess)){
//                MainHolder.Instance(mContext).setAuthenticationSuccess_redPacket(mRealNameRZResponse.result.authenticationSuccess);
                CommonUtils.addConfigInfo(this,Constants.KEY_RED_PACT_AUTNEN,mRealNameRZResponse.result.authenticationSuccess);
            }
            UserPersonalInfo.setIsAttestation("1");
            UserPersonalInfo.setSname(real_name_et.getText().toString().trim());
            CommonUtils.toastMsgShort(mContext, "实名认证成功");
        }
        
//      MainHolder.Instance(mContext).setAttestation(false);
      CommonUtils.addConfigInfo(mContext, Constants.IS_Certification_CHECK, false);
        //保存注册成功的标记
        MainHolder.Instance(mContext).setDLAuthentication(true);
        CommonUtils.addConfigInfo(mContext, Constants.IS_REGISTER_CHECK,
            isfinish_Authentication);
        Intent intent = new Intent(mContext, RegisterActivity.class);
        intent.putExtra(Constants.EP2P, isfinish_Authentication);
        pushActivity(intent);
//        pushActivityForResult(intent, 20);
        ActivityManager.getInstance().popAllActivity();
        finish();
    }
    @Override
    public void onErrorResponse(int tag, String error)
    {
        super.onErrorResponse(tag, error);
        RefreshDialog.stopProgressDialog();
        CommonUtils.toastMsgShort(mContext, error);
    }

    @Override
    public void onClick(View arg0)
    {
        switch (arg0.getId())
        {
            case R.id.submit_auth_btn ://提交认证
                if (TextUtils.isEmpty(real_name_et.getText().toString().trim()))
                {
                    CommonUtils.toastMsgShort(mContext, "真实姓名不能为空!");
                    return;
                }
                if (TextUtils.isEmpty(id_card_et.getText().toString().trim()))
                {
                    CommonUtils.toastMsgShort(mContext, "身份证号不能为空!");
                    return;
                }
                if (!StringUtils.isIDCard(id_card_et.getText().toString().trim()))
                {
                    CommonUtils.toastMsgShort(mContext, "身份证号不符合规范!");
                    return;
                }

                if (TextUtils.isEmpty(mToken))
                {
                    CommonUtils.toastMsgShort(mContext, mContext.getResources()
                            .getString(R.string.login_hint));
                    return;
                }
                getRealNameData();
                break;
            case R.id.tv_center_no_mainland :
//                CommonUtils.toastMsgShort(mContext, "非大陆通道");
                Intent intent = new Intent();
                intent.setClass(mContext, NonMainlandAuthActivity.class);
                pushActivity(intent);
                break;

            default :
                break;
        }
    }
}
