package com.beyondsoft.ep2p.activity.discover;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;


/**
 * @author xiaoliang
 * @description 我的社区
 */
public class CommunityMyActivity extends BaseActivity {
	 
	private TextView tv_title;
	private ImageView iv_right_falg;
	
	private RelativeLayout redbag_rl;
	private TextView redbag_check;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_my);

		initTitle();
		initView();
		initData();
	}

	private void initTitle() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("我的社区");
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setVisibility(View.GONE);
	}

	private void initView() {
		
//		redbag_rl = (RelativeLayout) findViewById(R.id.redbag_rl);
//		
//		redbag_check = (TextView) findViewById(R.id.redbag_check);
		
	
	}

	private void initData() {
//		redbag_rl.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				
//						Toast.makeText(MyCommunityActivity.this,
//								"抽到100元红包！" , Toast.LENGTH_SHORT)
//								.show();
////						tv_year_rate.setText(cities[which]);
//					
//			}
//		});
//		
//		redbag_check.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				pushActivity(ReceiveRedenvelopeDetailActivity.class);
//						
//					
//			}
//		});
	}

}
