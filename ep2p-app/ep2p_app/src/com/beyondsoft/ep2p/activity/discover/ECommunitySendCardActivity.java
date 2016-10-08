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
import com.beyondsoft.ep2p.activity.discover.adapter.RedEnvelopDetailAdapter;
import com.beyondsoft.ep2p.activity.mine.adapter.ProjectBiddingAdapter;


/**
 * @author xiaoliang
 * @description e社区首页
 */
public class ECommunitySendCardActivity extends BaseActivity {
	 
	private TextView tv_title;
	private TextView iv_right_falg;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_sendcard);
		initTitle();
		initView();
		
	}
	
	private void initTitle() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("小e社区");
		iv_right_falg = (TextView) findViewById(R.id.title_right);
		iv_right_falg.setText("发表");
	}

	private void initView() {
		iv_right_falg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(ECommunitySendCardActivity.this,
						"已发表" , Toast.LENGTH_SHORT)
						.show();

					
			}
		});
		
	
	}



}
