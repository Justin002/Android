package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.AutoTenderInfoBean;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.widget.PayHintDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.PayPasswrodClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.inputmethod.InputMethodManager;

public abstract class BaseAutoTenderComplicActivity extends BaseActivity {

	protected static final String TAG = "BaseSetupActivity";
	private static final int TAG_COMPLIC_AUTO_TENDER = 600;
	protected static final int TAG_VALIDATE_PAY_PASSWOED = 400;
	private int input_count = 3;
	private GestureDetector mDetector;
	protected AutoTenderInfoBean autoTenderInfoBean;
	protected Intent intent;

	BaseService baseService;
	SecurityCenterService securityCenterService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		baseService = new BaseService();
		securityCenterService = new SecurityCenterService();
		// 创建
		mDetector = new GestureDetector(this, new SimpleOnGestureListener() {
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

				// e1: 开始的点的数据
				// e2: 结束的点的数据
				// velocityX:
				// velocityY:

				float x1 = e1.getX();
				float x2 = e2.getX();

				float y1 = e1.getY();
				float y2 = e2.getY();
				Log.d(TAG, "velocityX : " + velocityX);
				// 速率的过滤
				if (Math.abs(velocityX) < 80) {
					return false;
				}

				// 如果用户是垂直方向滑动不做操作
				if (Math.abs(y1 - y2) > Math.abs(x1 - x2)) {
					return false;
				}

				// 如果从右往左滑动(x1 > x2),下一步操作
				if (x1 > x2) {
					// 下一步操作
					doNext();
				} else {
					// 如果从左往右滑动,上一步操作
					doPre();
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	private void doPre() {
		// 页面跳转 (不同点)--->交给具体的子类实现
		// Intent intent = new Intent(this, SjfdSetup1Activity.class);
		// startActivity(intent);
		if (performPre()) {
			// 流程中断
			return;
		}

		// enterAnim: 进入的activity的动画
		// exitAnim: 退出的activity的动画
		overridePendingTransition(R.anim.pre_enter, R.anim.pre_exit);

		finish();
	}

	private void doNext() {
		// 页面跳转 (不同点)--->交给具体的子类实现
		// Intent intent = new Intent(this, SjfdSetup3Activity.class);
		// startActivity(intent);
		if (performNext()) {
			return;
		}

		// (共同点) --->父类实现
		// 动画
		// enterAnim: 进入的activity的动画
		// exitAnim: 退出的activity的动画
		overridePendingTransition(R.anim.next_enter, R.anim.next_exit);

		// 销毁
		finish();
	}

	protected void doUpdateAutoTenderRequest(Intent intent) {
		this.intent = intent;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("amount", AutoTenderActivity.checkedItemBean.getAmount());
		params.put("balanceratio", AutoTenderActivity.checkedItemBean.getBalanceratio());
		params.put("minborrowmonth", AutoTenderActivity.checkedItemBean.getMinborrowmonth());
		params.put("maxborrowmonth", AutoTenderActivity.checkedItemBean.getMaxborrowmonth());
		params.put("minborrowrate", AutoTenderActivity.checkedItemBean.getMinborrowrate());
		params.put("maxborrowrate", AutoTenderActivity.checkedItemBean.getMaxborrowrate());
		params.put("borrowType", AutoTenderActivity.checkedItemBean.getBorrowType());
		params.put("autostatus", AutoTenderActivity.checkedItemBean.getAutostatus());
		params.put("version", AutoTenderActivity.checkedItemBean.getVersion());
		params.put("pid", AutoTenderActivity.checkedItemBean.getPid());
		connection(
				baseService.getStringRequest(TAG_COMPLIC_AUTO_TENDER, URL.URL_COMPLIC_AUTO_TENDER, params, mContext));
	}

	/**
	 * 执行上一步的操作
	 * 
	 * @return 如果返回true 中断流程
	 */
	protected abstract boolean performPre();

	/**
	 * 执行下一步的操作
	 * 
	 * @return 如果返回true 中断流程
	 */
	protected abstract boolean performNext();

	protected void showKeyBoard() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 300);
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_COMPLIC_AUTO_TENDER:
//			CommonUtils.toastMsgShort(mContext, "chenggong!");
			startActivity(intent);
			break;
		case TAG_VALIDATE_PAY_PASSWOED:
//			CommonUtils.toastMsgShort(mContext, "验证密码成功!");
			complicAutoTender();
			break;

		default:
			break;
		}
	}

	protected abstract void complicAutoTender();

//	// 申明接口对象
//	private OnClickListener Listener;
//
//	// 设置监听器 也就是实例化接口
//	public void complicAutoTender(OnClickListener listener) {
//		this.Listener = listener;
//	}
//
//	// 创建接口
//	public static interface OnClickListener {
//		public void OnClick(View v, int flag);
//	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
		switch (tag) {
		case TAG_COMPLIC_AUTO_TENDER:
			CommonUtils.toastMsgShort(mContext, "shibai!");
			break;
		case TAG_VALIDATE_PAY_PASSWOED:
			CommonUtils.toastMsgShort(mContext, "验证密码失败!");
			if (input_count <= 1) {
				CommonUtils.toastMsgShort(mContext,
						"交易密码错误超过上限，为保护账户安全，系统禁止交易N分钟！");
			} else {
				// 密码输入错误提示
				input_count--;
				inputPassWord();
			}
			break;

		default:
			break;
		}
	}

	//弹出验证交易密码
	public void showPasswordDialog() {
		// 输入密码
		final HashMap<String, Object> params = new HashMap<String, Object>();
		// params.put("token", CommonUtils.getStringByKey(mContext,
		// Constants.EP2P_TOKEN));
		final PayPasswrodDialog clearDialog = new PayPasswrodDialog(mContext);
		clearDialog.show();
		clearDialog.setOnPayPasswrodClickListener(new PayPasswrodClickListener() {

			@Override
			public void OnClick(String PayPasswrod) {
				params.put("tradPassWord", PayPasswrod);
				params.put("flag", 1);

			}
		});
		clearDialog.setButtonClickListener(new PayPasswrodDialog.ButtonOnClickListener() {
			@Override
			public void onButton1Click(View v) {
				clearDialog.dismiss();

				connection(securityCenterService.validatePayPassword(TAG_VALIDATE_PAY_PASSWOED, params, mContext));

			}

		});
		showKeyBoard();
	}

	public void inputPassWord() {
		PayHintDialog payclearDialog = new PayHintDialog(mContext);
		payclearDialog.show();
		payclearDialog.setDescri("交易密码错误，还可输入" + input_count + "次");
		payclearDialog.setButtonClickListener(new PayHintDialog.ButtonOnClickListener() {

			@Override
			public void onButton2Click(View v) {
				CommonUtils.toastMsgShort(mContext, "重试");
			}

			@Override
			public void onButton1Click(View v) {
				CommonUtils.toastMsgShort(mContext, "忘记密码");
			}
		});
	}
}
