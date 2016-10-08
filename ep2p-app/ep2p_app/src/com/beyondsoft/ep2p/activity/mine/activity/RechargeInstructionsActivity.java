package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;
import java.util.List;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.discover.componet.ListViewComponent;
import com.beyondsoft.ep2p.activity.discover.componet.ListViewComponent.IListViewComponent;
import com.beyondsoft.ep2p.activity.mine.adapter.AutoTenderAdapter;
import com.beyondsoft.ep2p.activity.mine.adapter.RechargeInstructionsAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.view.gesture.PreferencesUtils;
import com.beyondsoft.ep2p.widget.PayHintDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author hanxiaohui
 * @description 充值-说明
 */
public class RechargeInstructionsActivity extends BaseActivity  {

	private TextView tv_title;
	private ListViewComponent listcomp;
	private int pageNum = 1;
	private int pageSize = 7;
	public List<RechargeInstructions> listDataResource;
	private RechargeInstructionsAdapter mRechargeInstructions;
	
	private int[] imageids = new int[]{R.drawable.nonghang_icon,R.drawable.shangpu_icon,R.drawable.jiaotong_icon,R.drawable.gongshang_icon,R.drawable.youzheng_icon,R.drawable.guangfa_icon,R.drawable.minsheng_icon,R.drawable.pingan_icon,R.drawable.zhaoshang_icon,R.drawable.zhonghang_icon,R.drawable.jianhang_icon,R.drawable.guangda_icon,R.drawable.xingye_icon,R.drawable.zhongxin_icon,R.drawable.huaxia_icon};
	private String[] banknames = new String[]{"中国农业银行","上海浦东发展银行","交通银行","中国工商银行","中国邮政储蓄银行","广东发展银行","中国民生银行","平安银行（深发展）","招商银行","中国银行","中国建设银行","中国光大银行","兴业银行","中信银行","华夏银行"};
	private String[] singles = new String[]{"5万","5万","5万","5万","5000","5万","5万","5万","1万","5万","5万","5万","5万","5万","5万",};
	private String[] onedays = new String[]{"5万","5万","5万","5万","5000","5万","5万","5万","1万","5万","5万","5万","5万","5万","5万",};

	public class RechargeInstructions {
		public int imageid = 0;

		public String bankname = "";

		public String single = "";

		public String oneday = "";

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge_instructions);

		initTitle();
		initView();
		initData();
	}


	private void initTitle() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("说明");
		
	}

	
	private void initView() {
		listcomp = new ListViewComponent(this, findViewById(R.id.rl_recharge_instructions));
		listcomp.listview.setDivider(new ColorDrawable(Color.parseColor("#C8C7CC")));
		listcomp.listview.setDividerHeight(this.dip2px(1));
		listcomp.setListener(new IListViewComponent() {

			@Override
			public void onRefersh() {
				pageNum = 1;
				query(true);
			}

			@Override
			public void nextPage() {
				// listcomp.addFooterView();
				// pageNum++;
				// query(false);
			}
		});

	}

	private void initData() {
		
		pageNum = 1;
		pageSize = 20;
		// res = new GetUserCollectionResponse();
		listDataResource = new ArrayList<RechargeInstructions>();
		mRechargeInstructions = new RechargeInstructionsAdapter(this, listDataResource);
		listcomp.setAdapter(mRechargeInstructions);
	
		// listcomp.removeFooterView();
		// listcomp.doRefersh();
		query(true);
	}

	private void query(final boolean clear) {

		listcomp.onComplete();

		listDataResource.clear();

		for (int i = 0; i < imageids.length; i++) {
			RechargeInstructions at = new RechargeInstructions();
			at.imageid = imageids[i];
			at.bankname = banknames[i];
			at.single = singles[i];
			at.oneday = onedays[i];
			listDataResource.add(at);
		}
		mRechargeInstructions.setList(listDataResource);
	}

}
