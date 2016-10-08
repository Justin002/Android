package com.beyondsoft.ep2p.activity.mine.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.home.AuthenticationActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.AddBankCardInfoBean;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.BankCardInfoResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Config;
import com.beyondsoft.ep2p.view.ASelectWheelPopupWindow;
import com.beyondsoft.ep2p.view.ASelectWheelPopupWindow.OnComfirmListener;
import com.beyondsoft.ep2p.widget.CustomDialog;
import com.beyondsoft.ep2p.widget.CustomDialog.ButtonOnClickListener;
import com.beyondsoft.ep2p.view.BankCardNumEditText;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 添加银行卡页面
 * 
 * @author hardy.zhou
 *
 */
public class AddBankCardActivity extends BaseActivity implements OnClickListener {

	private TextView title_content_tv;

	private LinearLayout ll_bank, ll_region;

	private TextView tv_user_name;

	private TextView tv_bank_name,tv_bankcard_city;

	private Button bt_next_step;

	private EditText tv_open_bank;

	private BankCardNumEditText et_card_no;
	
	private LinearLayout ll_input_hint;
	private TextView tv_card_no_1, tv_card_no_2, tv_card_no_3, tv_card_no_4,tv_card_no_5;
	private ImageView iv_delete;
    private MyHandler myHandler;
	private String mBankcardNo;
	private String mBankcardName;
	private String mBelongingBank;
	private String mBelongingProvince;
	private String mOpenAddress;

	private static final int TAG_ADD_BANK_CARD = 100;

	private BaseService baseService;
    private AddBankCardInfoBean bankCardInfo = new AddBankCardInfoBean();
	private ASelectWheelPopupWindow sharePopupWindowLine;

	private static final int REQUEST_BANK = 100;
	private static final int REQUEST_REGION = 200;
	private static final int REQUEST_VALIDATION = 300;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_bank_card);
		baseService = new BaseService();
		initView();
		initData();
		setListener();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		myHandler = new MyHandler();
		title_content_tv = (TextView) findViewById(R.id.title_content_tv);
		title_content_tv.setText(R.string.add_bank_card);
		ll_bank = (LinearLayout) findViewById(R.id.ll_bank);
		ll_bank.setOnClickListener(this);
		tv_user_name = (TextView) findViewById(R.id.tv_user_name);
		tv_bank_name = (TextView) findViewById(R.id.tv_bank_name);
		tv_bankcard_city = (TextView) findViewById(R.id.tv_bankcard_city);
		ll_region = (LinearLayout) findViewById(R.id.ll_region);
		ll_region.setOnClickListener(this);
		bt_next_step = (Button) findViewById(R.id.bt_next_step);
		bt_next_step.setOnClickListener(this);
		et_card_no = (BankCardNumEditText) findViewById(R.id.et_card_no);
		tv_open_bank = (EditText) findViewById(R.id.et_open_bank);
		ll_input_hint = (LinearLayout) findViewById(R.id.ll_input_hint);
		ll_input_hint.setOnClickListener(this);
		tv_card_no_1 = (TextView) findViewById(R.id.tv_card_no_1);
		tv_card_no_2 = (TextView) findViewById(R.id.tv_card_no_2);
		tv_card_no_3 = (TextView) findViewById(R.id.tv_card_no_3);
		tv_card_no_4 = (TextView) findViewById(R.id.tv_card_no_4);
		tv_card_no_5 = (TextView) findViewById(R.id.tv_card_no_5);
		iv_delete = (ImageView) findViewById(R.id.iv_delete);
		iv_delete.setOnClickListener(this);
	}
	
	private void initData(){
		String sname = UserPersonalInfo.getSname();
		if(null!=sname&&""!=sname)
		{
			String firstNmae = sname.substring(0, 1);
			String lastName = "******************".substring(0, sname.length()-1);
			tv_user_name.setText(firstNmae+lastName);
		}else
		{
			CustomDialog topUpDialog = new CustomDialog(mContext);
			topUpDialog.setButtonClickListener(new ButtonOnClickListener() {

				@Override
				public void onButton2Click(View v) {

					startActivity(new Intent(mContext, AuthenticationActivity.class));
				}

				@Override
				public void onButton1Click(View v) {
					
				}
			});
			topUpDialog.show();
			topUpDialog.setTitle("提示");
			topUpDialog.setDescri("为了您的资金安全，请先进行实名登记");
			topUpDialog.setButtonText("取消", "去认证");
		}
		
	}

	/**
	 * 设置监听器
	 */
	private void setListener() {
		et_card_no.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String cardNo = s.toString().trim();
				setCardNoState(cardNo);
			}
		});
		
		tv_open_bank.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				String info = s.toString().trim();
				if(null!=info&&""!=info)
				{
					if(et_card_no.getText().toString().length()>7)
					{
						bt_next_step.setEnabled(true);
					}else
					{
						bt_next_step.setEnabled(false);
					}
				}else
				{
					bt_next_step.setEnabled(false);
				}
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				
			}});
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

	/**
	 * 设置卡号输入框状态
	 * 
	 * @param cardNo
	 */
	private void setCardNoState(String cardNo) {
		if (null != cardNo && !"".equals(cardNo = cardNo.trim())) {
			cardNo = cardNo.replaceAll(" ", "");
			int length = cardNo.length();
			if (length <= 4) {
				tv_card_no_1.setText(cardNo.substring(0, length));
				tv_card_no_2.setText("");
				tv_card_no_3.setText("");
				tv_card_no_4.setText("");
				tv_card_no_5.setText("");
			} else if (length <= 8) {
				tv_card_no_1.setText(cardNo.substring(0, 4));
				tv_card_no_2.setText(cardNo.substring(4, length));
				tv_card_no_3.setText("");
				tv_card_no_4.setText("");
				tv_card_no_5.setText("");
			} else if (length <= 12) {
				tv_card_no_1.setText(cardNo.substring(0, 4));
				tv_card_no_2.setText(cardNo.substring(4, 8));
				tv_card_no_3.setText(cardNo.substring(8, length));
				tv_card_no_4.setText("");
				tv_card_no_5.setText("");
			} else if(length <= 16){
				tv_card_no_1.setText(cardNo.substring(0, 4));
				tv_card_no_2.setText(cardNo.substring(4, 8));
				tv_card_no_3.setText(cardNo.substring(8, 12));
				tv_card_no_4.setText(cardNo.substring(12,length));
				tv_card_no_5.setText("");
			}else{
				tv_card_no_1.setText(cardNo.substring(0, 4));
				tv_card_no_2.setText(cardNo.substring(4, 8));
				tv_card_no_3.setText(cardNo.substring(8, 12));
				tv_card_no_4.setText(cardNo.substring(12, 16));
				tv_card_no_5.setText(cardNo.substring(16, length));
			}
			
			iv_delete.setVisibility(View.VISIBLE);
			ll_input_hint.setVisibility(View.VISIBLE);
		} else {
			tv_card_no_1.setText("");
			tv_card_no_2.setText("");
			tv_card_no_3.setText("");
			tv_card_no_4.setText("");
			tv_card_no_5.setText("");
			ll_input_hint.setVisibility(View.GONE);
			iv_delete.setVisibility(View.INVISIBLE);
		}

	}
	
	//验证银行卡格式
	 public boolean luhmCheck(String bankno){
         if (bankno.length() < 16 || bankno.length() > 19) {
             return false;
             }
 //        if (strBin.indexOf(bankno.substring(0, 2)) == -1) {
 //        return "银行卡号开头6位不符合规范";
 //        }
         
         int lastNum=Integer.parseInt(bankno.substring(bankno.length()-1,bankno.length()));//取出最后一位（与luhm进行比较）
         
         String first15Num=bankno.substring(0,bankno.length()-1);//前15或18位
         //System.out.println(first15Num);
         char[] newArr = new char[first15Num.length()];    //倒叙装入newArr
         char[] tempArr = first15Num.toCharArray();
         for(int i = 0; i < tempArr.length ; i++){
             newArr[tempArr.length-1-i] = tempArr[i];
         }
         //System.out.println(newArr);
         
         
         int[] arrSingleNum = new int[newArr.length]; //奇数位*2的积 <9
        int[] arrSingleNum2 = new int[newArr.length];//奇数位*2的积 >9
         int[] arrDoubleNum=new int[newArr.length];  //偶数位数组
         
         for(int j = 0;j < newArr.length; j++){
             if((j+1)%2==1){//奇数位
                 if((int)(newArr[j]-48)*2<9)
                     arrSingleNum[j]=(int)(newArr[j]-48)*2;
                 else
                     arrSingleNum2[j]=(int)(newArr[j]-48)*2;
             }
             else //偶数位
                 arrDoubleNum[j]=(int)(newArr[j]-48);
         }
         
         int[] arrSingleNumChild = new int[newArr.length]; //奇数位*2 >9 的分割之后的数组个位数
         int[] arrSingleNum2Child = new int[newArr.length];//奇数位*2 >9 的分割之后的数组十位数
         
         for(int h=0;h<arrSingleNum2.length;h++){
            arrSingleNumChild[h] = (arrSingleNum2[h])%10;
             arrSingleNum2Child[h] = (arrSingleNum2[h])/10;
         }    
         
         int sumSingleNum=0; //奇数位*2 < 9 的数组之和
         int sumDoubleNum=0; //偶数位数组之和
         int sumSingleNumChild=0; //奇数位*2 >9 的分割之后的数组个位数之和
         int sumSingleNum2Child=0; //奇数位*2 >9 的分割之后的数组十位数之和
        int sumTotal=0;
        for(int m=0;m<arrSingleNum.length;m++){
            sumSingleNum=sumSingleNum+arrSingleNum[m];
         }
         
        for(int n=0;n<arrDoubleNum.length;n++){
            sumDoubleNum=sumDoubleNum+arrDoubleNum[n];
        }
         
         for(int p=0;p<arrSingleNumChild.length;p++){
             sumSingleNumChild=sumSingleNumChild+arrSingleNumChild[p];
             sumSingleNum2Child=sumSingleNum2Child+arrSingleNum2Child[p];
        }      
         
        sumTotal=sumSingleNum+sumDoubleNum+sumSingleNumChild+sumSingleNum2Child;
        
        //计算Luhm值
         int k= sumTotal%10==0?10:sumTotal%10;        
        int luhm= 10-k;
        
       if(lastNum == luhm){
            return true;//验证通过
       }
        else{
            return false;
        }      
    }

	private void setPopwindowsOut(Context cx, ArrayList<String> selectTime, OnComfirmListener mlistener, String title) {
		// 传入上下文，数据，选择按钮监听器
		sharePopupWindowLine = new ASelectWheelPopupWindow(cx, selectTime, mlistener, title);
		sharePopupWindowLine.showWindow();
		sharePopupWindowLine.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
			}
		});
		sharePopupWindowLine.showAtLocation(findViewById(R.id.title_content_tv), Gravity.TOP, 0, 0);

	}

	private class MyNianhuaOnComfirmListener implements OnComfirmListener {
		@Override
		public void onArticleSelected(String ms) {
			tv_bank_name.setText(ms);
		}
	}

	/**
	 * 执行添加银行卡请求
	 */
	private void doAddBankCardRequest() {

		mBankcardNo = et_card_no.getText().toString();
		mBankcardName = tv_user_name.getText().toString();
		mBelongingBank = tv_bank_name.getText().toString();
		mBelongingProvince = "广东省";
		mOpenAddress = "深圳市高新区支行";

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("bankcardNo", mBankcardNo);
		params.put("bankcardName", mBankcardName);
		params.put("belongingBank", mBelongingBank);
		params.put("belongingProvince", mBelongingProvince);
		params.put("openAddress", mOpenAddress);
		connection(baseService.getStringRequest(TAG_ADD_BANK_CARD, Config.getDomainUrl() + "/securitycenter/bankCardApi/addBankCard.api", params, mContext));
	}

	private void getBnakCardInfo(){
		bankCardInfo.setBankcardName(UserPersonalInfo.getSname());;
		bankCardInfo.setBankcardNo(et_card_no.getText().toString().trim());
		bankCardInfo.setBelongingBank(tv_bank_name.getText().toString().trim());
		bankCardInfo.setBelongingProvince(tv_bankcard_city.getText().toString().trim());
		bankCardInfo.setOpenAddress(tv_open_bank.getText().toString().trim());
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_ADD_BANK_CARD: {
			Intent intent = new Intent();
			intent.setClass(mContext, BankCardValidationActivity.class);
			intent.putExtra(BankCardValidationActivity.PARAMS_PHONE_NUM, "13632980415");
			startActivity(intent);
		}
			break;
		default:
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
		switch (tag) {
		case TAG_ADD_BANK_CARD: {
			CommonUtils.toastMsgShort(mContext, "添加银行卡失败");
		}
			break;
		default:
			break;
		}
	}

	//处理myThread传过来的json数据
	class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			String values = bundle.getString("satate");
			BankCardInfoResponse response = gson.fromJson(values.toString(), BankCardInfoResponse.class);
			if(response.getResult()!=null){
				tv_bank_name.setText(response.getResult().getBank());
				tv_bankcard_city.setText(response.getResult().getCity());
			}
		}
	}
	
	//自定义后台线程调用第三方接口
	class MyThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				String cardNo = et_card_no.getText().toString().trim().replaceAll(" ", "");
				String data = "key=1a36f44c8ccd4d90b3cb6c41e0a428a1&card="+cardNo;
				String url = "http://apis.haoservice.com/lifeservice/bankcard/query?"+data;
				URL mUrl = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
				conn.setRequestMethod("GET");
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(10000);
                
                int responseCode = conn.getResponseCode();
                System.out.println("获取返回值"+responseCode);
                if(responseCode==200){
                	InputStream is = conn.getInputStream();
                    String state = getStringFromInputStream(is);
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("satate", state);
                    message.setData(bundle);
                    myHandler.sendMessage(message);
                }
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				CommonUtils.toastMsgShort(mContext, "当前无网络服务");
				e.printStackTrace();
			}
		}
		
		private  String getStringFromInputStream(InputStream is) throws IOException
                 {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        // 一定要写len=is.read(buffer)
        // 如果while((is.read(buffer))!=-1)则无法将数据写入buffer中
        while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
        os.close();
        return state;
}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case REQUEST_BANK: {
			// TODO
			String cityNname = data.getStringExtra("cityName");
			if(""!=cityNname&&null!=cityNname)
			tv_bankcard_city.setText(cityNname);
		}
			break;
		case REQUEST_REGION: {
			// TODO
		}
			break;
		case REQUEST_VALIDATION: {

		}
			break;
		
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_bank: {
			ArrayList<String> selectTime = new ArrayList<String>();
			selectTime.add("中国银行");
			selectTime.add("中国工商银行");
			selectTime.add("中国农业银行");
			selectTime.add("中国建设银行");
			selectTime.add("交通银行");
			selectTime.add("广东发展银行");
			selectTime.add("上海浦东发展银行");
			selectTime.add("中国民生银行");
			selectTime.add("中国邮政储蓄银行");
			// 传入上下文，数据，选择按钮监听器,标题
			setPopwindowsOut(mContext, selectTime, new MyNianhuaOnComfirmListener(), "选择银行");

		}
			break;
		case R.id.ll_region: {
			Intent intent = new Intent();
			//intent.setClass(mContext, SelectRegionActivity.class);
			intent.setClass(mContext, SelectLoactionActivity.class);
			startActivityForResult(intent, REQUEST_REGION);
		}
			break;
		case R.id.bt_next_step: {
			getBnakCardInfo();
			Intent intent = new Intent();
			intent.setClass(mContext, BankCardValidationActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("bank_card_info", bankCardInfo);
			intent.putExtras(bundle);
			startActivity(intent);
		}
			break;
		case R.id.iv_delete: {
			setCardNoState("");
			et_card_no.setText("");
		}
			break;
		case R.id.ll_input_hint:{
			String cardNo = et_card_no.getText().toString().trim();
			if(luhmCheck(cardNo.replaceAll(" ", "")))
			{
				setCardNoState("");
				MyThread myThread = new MyThread();
				myThread.start();
			}else
			{
				Toast.makeText(mContext, "银行卡号不符合规则", 3000).show();
				setCardNoState("");
				et_card_no.setText("");
			}
		}
			
		default:
			break;
		}

	}

}
