package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.R.color;
import com.beyondsoft.ep2p.activity.mine.adapter.AutoTenderAdapter;
import com.beyondsoft.ep2p.model.AutoTenderInfoBean;
import com.beyondsoft.ep2p.model.AutoTenderListBean;
import com.beyondsoft.ep2p.model.response.AutoTenderListResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.beyondsoft.ep2p.view.swipemenulistview.SwipeMenu;
import com.beyondsoft.ep2p.view.swipemenulistview.SwipeMenuCreator;
import com.beyondsoft.ep2p.view.swipemenulistview.SwipeMenuItem;
import com.beyondsoft.ep2p.view.swipemenulistview.SwipeMenuListView;
import com.beyondsoft.ep2p.view.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.beyondsoft.ep2p.widget.CustomDialog;

/**
 * @author hanxiaohui
 * @description 自主投标
 */
public class AutoTenderActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private TextView wifi_load_again;
	private ImageView iv_right_falg;
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_auto_tender_null_bg;
	private SwipeMenuListView auto_tender_lv;
	private TextView footer ;
	public List<AutoTenderInfoBean> listDataResource = new ArrayList<AutoTenderInfoBean>();
	private AutoTenderAdapter mAdapter;

	public ArrayList<String> projectlist = null;

	private static final int TAG_AUTO_TENDER_LIST = 100;
	private static final int TAG_DELETE_AUTO_TENDER = 200;
	private static final int TAG_COMPLIC_AUTO_TENDER = 300;
	public static AutoTenderInfoBean checkedItemBean;

	BaseService baseService;
	SecurityCenterService securityCenterService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_tender);
		baseService = new BaseService();
		securityCenterService = new SecurityCenterService();
		initTitle();
		initView();
		initData();

	}
	@Override
	protected void onStart() {
		super.onStart();
		doGetAutoTenderRequest(TAG_AUTO_TENDER_LIST);
	}
	private void doGetAutoTenderRequest(int statue) {
		if(statue != TAG_COMPLIC_AUTO_TENDER){
			RefreshDialog.startProgressDialog(mContext, "");
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		connection(baseService.getStringRequest(TAG_AUTO_TENDER_LIST, URL.URL_AUTO_TENDER_LIST, params, mContext));
	}

	private void doDeleteAutoTenderRequest(int position) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		AutoTenderInfoBean autoTenderInfoBean = listDataResource.get(position);
		params.put("pid", autoTenderInfoBean.getPid());
		connection(baseService.getStringRequest(TAG_DELETE_AUTO_TENDER, URL.URL_DELETE_AUTO_TENDER, params, mContext));
	}
	

	private void doUpdateAutoTenderRequest() {
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
		connection(baseService.getStringRequest(TAG_COMPLIC_AUTO_TENDER, URL.URL_COMPLIC_AUTO_TENDER, params, mContext));
	}

	private void initTitle() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("自动投标");
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setImageResource(R.drawable.mine_auto_tender_add_icon);
		iv_right_falg.setVisibility(View.VISIBLE);
		iv_right_falg.setOnClickListener(this);

	}

	private void initView() {
		wifi_load_again = (TextView) findViewById(R.id.wifi_load_again);
		wifi_load_again.setOnClickListener(this);
		ll_wifi_off = (LinearLayout) findViewById(R.id.ll_wifi_off);
		ll_auto_tender_null_bg = (LinearLayout) findViewById(R.id.ll_auto_tender_null_bg);
		auto_tender_lv = (SwipeMenuListView) findViewById(R.id.auto_tender_lv);
		auto_tender_lv.setDivider(new ColorDrawable(Color.parseColor("#EBEFF1")));
		auto_tender_lv.setDividerHeight(this.dip2px(17));
		
		footer = new TextView(mContext);
		LinearLayout footerParent = new LinearLayout(mContext);
		
		int left, top, right, bottom;
		left = right = 0;
		top = bottom = 17;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(left, top, right, bottom);
		
		footer.setText("您还可以添加" + (3 - listDataResource.size()) + "个备选的自动投标设置");
		footer.setTextColor(Color.parseColor("#666666"));
		footer.setLayoutParams(params);
		footer.setGravity(Gravity.CENTER);
		footerParent.setGravity(Gravity.CENTER);
		footerParent.addView(footer);
		auto_tender_lv.addFooterView(footerParent);
		
	}

	private void initData() {
		doGetAutoTenderRequest(TAG_AUTO_TENDER_LIST);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_right: {
			startActivity(new Intent(this, AutoTenderAddMoneyActivity.class));
		}
			break;
		case R.id.wifi_load_again:{
			doGetAutoTenderRequest(TAG_AUTO_TENDER_LIST);
		}
			break;
		default:
			break;
		}

	}
	
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}

	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_AUTO_TENDER_LIST: {
			RefreshDialog.stopProgressDialog();
			AutoTenderListResponse autoTenderListResponse = gson.fromJson(values.toString(),AutoTenderListResponse.class);
			AutoTenderListBean autoTenderListBean = autoTenderListResponse.getAutoTenderListBean();
			ArrayList<AutoTenderInfoBean> autoTenderInfoBeanList = autoTenderListBean.getAutoTenderInfoBeanList();
//			CommonUtils.toastMsgShort(mContext, "chenggong" + autoTenderInfoBeanList.size());
			listDataResource = autoTenderInfoBeanList;
			if (listDataResource.size() <= 0) {
//				pushActivity(AutoTenderNullActivity.class);
//				finish();
				ll_wifi_off.setVisibility(View.GONE);
				ll_auto_tender_null_bg.setVisibility(View.VISIBLE);
				auto_tender_lv.setVisibility(View.GONE);
			}else{
				ll_wifi_off.setVisibility(View.GONE);
				ll_auto_tender_null_bg.setVisibility(View.GONE);
				auto_tender_lv.setVisibility(View.VISIBLE);
			}
			if (listDataResource.size() >= 3) {
				iv_right_falg.setVisibility(View.GONE);
			} else {
				iv_right_falg.setVisibility(View.VISIBLE);
			}
			
			footer.setText("您还可以添加" + (3 - listDataResource.size()) + "个备选的自动投标设置");
			
			mAdapter = new AutoTenderAdapter(this, listDataResource);
			auto_tender_lv.setAdapter(mAdapter);

			// step 1. create a MenuCreator
			SwipeMenuCreator creator = new SwipeMenuCreator() {

				@Override
				public void create(SwipeMenu menu) {
					// create "open" item
					SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
					// set item background
					openItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
					// set item width
					openItem.setWidth(dp2px(90));
					// set item title
					openItem.setTitle("删除");
					// set item title fontsize
					openItem.setTitleSize(18);
					// set item title font color
					openItem.setTitleColor(Color.WHITE);
					// add to menu
					menu.addMenuItem(openItem);
				}
			};
			// set creator
			auto_tender_lv.setMenuCreator(creator);

			// step 2. listener item click event
			auto_tender_lv.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
					switch (index) {
					case 0:

						CustomDialog customDialog = new CustomDialog(mContext);

						customDialog.setButtonClickListener(new CustomDialog.ButtonOnClickListener() {

							@Override
							public void onButton2Click(View v) {
							}

							@Override
							public void onButton1Click(View v) {
								doDeleteAutoTenderRequest(position);
								// 把数据源里面相应数据删除
								doGetAutoTenderRequest(TAG_AUTO_TENDER_LIST);

							}
						});
						customDialog.show();
						customDialog.setTitle("确认删除该自动投标");
						customDialog.hideDescri();
						customDialog.hideView();
						customDialog.setButtonText("确认", "取消");
						break;
					}
					return false;
				}
			});

		}

			mAdapter.setOnClickListener(new AutoTenderAdapter.OnClickListener() {

				@Override
				public void OnClick(View v, int flag) {
					switch (v.getId()) {

					case R.id.iv_mine_auto_tender_item_title_right: {
						AutoTenderInfoBean autoTenderInfoBean = listDataResource.get(flag);
						checkedItemBean = autoTenderInfoBean;
						startActivity(new Intent(mContext, AutoTenderComplicMoneyActivity.class));
					}
						break;

					 case R.id.mine_auto_tender_item_on_off:{
						 AutoTenderInfoBean autoTenderInfoBean = listDataResource.get(flag);
						 checkedItemBean = autoTenderInfoBean;
							if (checkedItemBean.getAutostatus() == 0) {
								checkedItemBean.setAutostatus(1);
								doUpdateAutoTenderRequest();
							} else {
								checkedItemBean.setAutostatus(0);
								doUpdateAutoTenderRequest();
							}
						 
					 }
					 break;

					default:
						break;
					}
				}
			});
			break;

		case TAG_DELETE_AUTO_TENDER: {
//			CommonUtils.toastMsgShort(mContext, "删除成功！");
			doGetAutoTenderRequest(TAG_AUTO_TENDER_LIST);
		}
			break;
		case TAG_COMPLIC_AUTO_TENDER:{
//			CommonUtils.toastMsgShort(mContext, "开关控制成功！");
			doGetAutoTenderRequest(TAG_COMPLIC_AUTO_TENDER);
			if(checkedItemBean.getAutostatus() == 0){
				CommonUtils.toastMsgShortForAutoTender(this,"自动投标关闭成功");
			}else{
				CommonUtils.toastMsgShortForAutoTender(this,"自动投标开启成功");
			}
		}
			
		break;
		default:
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
		RefreshDialog.stopProgressDialog();
		switch (tag) {
		case TAG_AUTO_TENDER_LIST: {
			CommonUtils.toastMsgShort(mContext, error);
			ll_wifi_off.setVisibility(View.VISIBLE);
			auto_tender_lv.setVisibility(View.GONE);
			ll_auto_tender_null_bg.setVisibility(View.GONE);
		}

			break;
		case TAG_DELETE_AUTO_TENDER: {
			CommonUtils.toastMsgShort(mContext, "删除失败！" + error);
		}
			break;
		case TAG_COMPLIC_AUTO_TENDER: {
			CommonUtils.toastMsgShort(mContext, "开关控制失败！" + error);
		}
		break;
		default:
			break;
		}
	}

}
