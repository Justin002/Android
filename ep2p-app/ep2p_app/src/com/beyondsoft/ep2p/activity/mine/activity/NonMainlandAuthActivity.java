package com.beyondsoft.ep2p.activity.mine.activity;

import java.io.File;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.BitmapUtils;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.HeadSelectPopupWindow;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.beyondsoft.ep2p.view.HeadSelectPopupWindow.HeadSelectPopupWindowOnClickListener;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 非大陆实名认证页面
 * 
 * @author hardy.zhou
 *
 */
public class NonMainlandAuthActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title;

	private EditText et_real_name, et_id_card;
	private ImageView iv_card_pic_1, iv_card_pic_2;
	private Button bt_submit;
	private ImageView iv_current;
	private HeadSelectPopupWindow window;
	private String mToken = "";
	   
    private File positivefile = null;
    private File backfile = null;
    private int positivefile_value = 0,backfile_value = 0;
    private StringBuilder sb_realname  = new StringBuilder();
    private StringBuilder sb_idCard  = new StringBuilder();
    private int isExists = 0;//[1:正反材料选择完成，2：重复选择]
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_non_mainland_auth);
		initView();
		setListener();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.non_mainland_auth);
		et_real_name = (EditText) findViewById(R.id.et_real_name);
		et_id_card = (EditText) findViewById(R.id.et_id_card);
		iv_card_pic_1 = (ImageView) findViewById(R.id.iv_card_pic_1);
		iv_card_pic_2 = (ImageView) findViewById(R.id.iv_card_pic_2);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		iv_card_pic_1.setOnClickListener(this);
		iv_card_pic_2.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
		
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        Logs.d("mToken==:" + mToken);
	}
	
    private String str_real_name_et = "", str_id_card_et="";
    private void setListener() {
        
        et_real_name.addTextChangedListener(new TextWatcher() {//真实姓名

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                str_real_name_et = s.toString().trim();
//                sb_realname.append(str_real_name_et);
//                str_real_name_et =  sb_realname.toString().trim();
                
                if (!TextUtils.isEmpty(str_id_card_et) && !TextUtils.isEmpty(str_real_name_et) && positivefile != null && backfile != null) {
                    bt_submit.setEnabled(true);
                } else {
                    bt_submit.setEnabled(false);
                }
            }
        });
        
        et_id_card.addTextChangedListener(new TextWatcher() {//身份证号

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                str_id_card_et = s.toString().trim();
//                sb_idCard.append(str_id_card_et);
//                str_id_card_et =  sb_idCard.toString().trim();
                
                if (!TextUtils.isEmpty(str_id_card_et) && !TextUtils.isEmpty(str_real_name_et) && positivefile != null && backfile != null) {
                    bt_submit.setEnabled(true);
                } else {
                    bt_submit.setEnabled(false);
                }
            }
        });
    }

    protected void TakePhotoCallBack(String photoAbsolutePath)
    {
        if (positivefile_value == 1)
        {
            positivefile = new File(photoAbsolutePath);
            Logs.d("Image=====1:" + photoAbsolutePath);
            Str_Append();
        }

        if (backfile_value == 2)
        {
            backfile = new File(photoAbsolutePath);
            Logs.d("Image=====2:" + photoAbsolutePath);
            Str_Append();
        }
        isExists++;
        iv_current.setImageBitmap(BitmapUtils.getRoundConerImage(
            BitmapUtils.getBitmap(photoAbsolutePath, iv_current.getWidth()), 20));
        iv_current.setTag(photoAbsolutePath);
    }
    
    private void Str_Append(){
        
        if(positivefile != null && backfile != null && isExists==1){
            sb_realname.append(str_real_name_et+" ");
            str_real_name_et = sb_realname.toString().trim();
            et_real_name.setText(str_real_name_et);
            
            sb_idCard.append(str_id_card_et+" ");
            str_id_card_et = sb_idCard.toString().trim();
            et_id_card.setText(str_id_card_et);
        }
       
    }

	//实名认证（非大陆）
    private void getRealNameFdlData()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", mToken);
        params.put("sname", et_real_name.getText().toString().trim());//真实姓名
        params.put("identificationNo", et_id_card.getText().toString().trim());//身份证号码
        params.put("positivefile", positivefile );//正面文件 file
        params.put("backfile", backfile);//反面文件file
        connection(registerService.getStringRequest(1, URL.API_REALNAMENODL, params,this));
    }
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        RefreshDialog.stopProgressDialog();
        View view = LayoutInflater.from(this).inflate(R.layout.toast_non_mainland_submit_success, null);
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, dip2px(-75));
        toast.setView(view);
        toast.show();
    }
    @Override
    public void onErrorResponse(int tag, String error)
    {
        super.onErrorResponse(tag, error);
        RefreshDialog.stopProgressDialog();
        CommonUtils.toastMsgShort(mContext, error);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_submit :
                // CommonUtils.ToastMsgShort(mContext, "提交非大陆实名认证");

                if (TextUtils.isEmpty(et_real_name.getText().toString().trim()))
                {
                    CommonUtils.toastMsgShort(mContext, "真实姓名不能为空!");
                    return;
                }
                if (TextUtils.isEmpty(et_id_card.getText().toString().trim()))
                {
                    CommonUtils.toastMsgShort(mContext, "身份证号不能为空!");
                    return;
                }
                if (!StringUtils.isIDCard(et_id_card.getText().toString().trim()))
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
                
                if (positivefile != null && backfile != null)
                {
                    getRealNameFdlData();
                }else{
                    CommonUtils.toastMsgShort(mContext, "请选择有效的正反身份材料!");
                }
                break;
            case R.id.iv_card_pic_1 :
                iv_current = iv_card_pic_1;
                positivefile_value = 1;
                backfile_value = 0;
                window = new HeadSelectPopupWindow(mContext);
                window.showWindow();
                window.setOnDismissListener(new OnDismissListener()
                {

                    @Override
                    public void onDismiss()
                    {
                        Logs.d("Image=====onDismiss:");
                        backgroundAlpha(1.0f);
                    }
                });
                window.setButtonClickListener(new HeadSelectPopupWindowOnClickListener()
                {

                    @Override
                    public void onButtonClick1(View v)
                    {
                        Logs.d("Image=====onButtonClick1:");
                        getImageFromPhoto(iv_current.getWidth(), iv_current.getHeight());
                    }

                    @Override
                    public void onButtonClick2(View v)
                    {
                        Logs.d("Image=====onButtonClick2:");
                        getImageFromCamera(iv_current.getWidth(), iv_current.getHeight());
                    }
                });

                window.showAtLocation(findViewById(R.id.iv_card_pic_1), Gravity.BOTTOM,
                    0, 0);
                backgroundAlpha(0.5f);

                break;
            case R.id.iv_card_pic_2 :
                iv_current = iv_card_pic_2;
                positivefile_value = 0;
                backfile_value = 2;
                window = new HeadSelectPopupWindow(mContext);
                window.showWindow();
                window.setOnDismissListener(new OnDismissListener()
                {

                    @Override
                    public void onDismiss()
                    {
                        backgroundAlpha(1.0f);
                    }
                });
                window.setButtonClickListener(new HeadSelectPopupWindowOnClickListener()
                {

                    @Override
                    public void onButtonClick1(View v)
                    {
                        getImageFromPhoto(iv_current.getWidth(), iv_current.getHeight());
                    }

                    @Override
                    public void onButtonClick2(View v)
                    {
                        getImageFromCamera(iv_current.getWidth(), iv_current.getHeight());
                    }
                });

                window.showAtLocation(findViewById(R.id.iv_card_pic_2), Gravity.BOTTOM,
                    0, 0);
                backgroundAlpha(0.5f);

                break;
            default :
                break;
        }
    }
}
