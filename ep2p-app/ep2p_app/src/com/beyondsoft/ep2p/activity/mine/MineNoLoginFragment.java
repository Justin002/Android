package com.beyondsoft.ep2p.activity.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;

/**
 * @author Ivan.Lu
 * @description "我的"-未登录
 */
public class MineNoLoginFragment extends BaseFragment
{
	public static final int LOGIN_REQUESTCODE=1;
	private Button login_btn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_mine_no_login_fragment, container,false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		init();
		initListener();
	}
	
	private void init(){
		login_btn=(Button) getActivity().findViewById(R.id.login_btn);
	}
	
	private void initListener(){
		login_btn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pushActivityForResult(LoginActivity.class,LOGIN_REQUESTCODE);
			}
		});
	}
}
