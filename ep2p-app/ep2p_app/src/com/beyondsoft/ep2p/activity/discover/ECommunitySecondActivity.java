package com.beyondsoft.ep2p.activity.discover;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.discover.adapter.CommunityFirstListAdapter;
import com.beyondsoft.ep2p.activity.discover.adapter.CommunitySecondListAdapter;
import com.beyondsoft.ep2p.activity.discover.adapter.RedEnvelopDetailAdapter;
import com.beyondsoft.ep2p.activity.mine.adapter.ProjectBiddingAdapter;


/**
 * @author xiaoliang
 * @description e社区2级
 */
public class ECommunitySecondActivity extends BaseActivity {
	 
	private TextView tv_title;
	private ImageView iv_right_falg;
	
	private ListView lv_content;
	private CommunitySecondListAdapter redEnvelopDetailAdapter;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
			}
				break;

			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_second);

		initTitle();
		initView();
		initData();
	}

	private void initTitle() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("投资交流");
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setVisibility(View.VISIBLE);
		iv_right_falg.setImageResource(R.drawable.e_add_shequ);
	}

	private void initView() {
		
		lv_content = (ListView) findViewById(R.id.lv_content);
		redEnvelopDetailAdapter = new CommunitySecondListAdapter(this, mHandler,1);
		lv_content.setAdapter(redEnvelopDetailAdapter);
		lv_content.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(position);
				pushActivity(ECommunityCardDetailActivity.class);
				
			}
			
		});
		iv_right_falg.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				pushActivity(ECommunitySendCardActivity.class);

					
			}
		});
	
	}

	private void initData() {
//		redbag_rl.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				
//						Toast.makeText(ReceiveRedenvelopeDetailActivity.this,
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
//				pushActivity(NewerNoviceActivity.class);
//						
//					
//			}
//		});
	}

}