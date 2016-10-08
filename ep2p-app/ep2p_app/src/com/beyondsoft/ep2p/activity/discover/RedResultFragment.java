package com.beyondsoft.ep2p.activity.discover;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.discover.adapter.RedEnvelopDetailAdapter;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.SelectReceiveRedDetailResponse.Result.RedDetailListItem;
import com.beyondsoft.ep2p.utils.ImageLoadOptions;

/**
 * @author xiaoliang
 * @description 红包结果界面
 */
public class RedResultFragment extends BaseFragment {
	private int type;

	private ImageView pinglun_img;
	private TextView man_tip_tv;
	private TextView tv_receiveamount;

	private View view;
	private TextView tv_detail_all;
	private ListView lv_content;
	private RedEnvelopDetailAdapter redEnvelopDetailAdapter;
	private ArrayList<RedDetailListItem> redDetailLIst;
	// private Handler mHandler = new Handler() {
	// public void handleMessage(android.os.Message msg) {
	// switch (msg.what) {
	// case 0: {
	// }
	// break;
	//
	// default:
	// break;
	// }
	// };
	// };

	// @Override
	// public void onAttach(Activity activity) {
	// super.onAttach(activity);
	// try {
	// mListener = (OnArticleSelectedListener) activity;
	// } catch (ClassCastException e) {
	// throw new ClassCastException(activity.toString()
	// + " must implement OnArticleSelectedListener");
	// }
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if ("200".equals(getArguments().getString("code")) || "702".equals(getArguments().getString("code"))) {// 成功抢到红包
			type = 0;
			view = inflater.inflate(R.layout.activity_receive_redenvelope_result, container, false);
		} else if ("703".equals(getArguments().getString("code"))) {// 抢光了
			type = 1;
			view = inflater.inflate(R.layout.activity_receive_redenvelope_result_finish, container, false);
		}
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		init(view, type);
	}

	@SuppressWarnings("unchecked")
	private void init(View view, int type) {
		DecimalFormat format = new DecimalFormat("0.00");
		if (type == 0) {// 成功抢到红包
			// CommonUtils.toastMsgLong(mContext, "*********");
			pinglun_img = (ImageView) view.findViewById(R.id.pinglun_img);
			man_tip_tv = (TextView) view.findViewById(R.id.man_tip_tv);
			tv_receiveamount = (TextView) view.findViewById(R.id.tv_receiveamount);

			if (pinglun_img.getTag() == null
					|| !UserPersonalInfo.getImageUrl().equals(pinglun_img.getTag().toString())) {
				imageLoader.displayImage(UserPersonalInfo.getImageUrl(), pinglun_img,
						ImageLoadOptions.getCircleOptions());
				pinglun_img.setTag(UserPersonalInfo.getImageUrl());
			}

			man_tip_tv.setText(UserPersonalInfo.getPhoneNo().substring(0, UserPersonalInfo.getPhoneNo().length() - 4)
					+ "****抢到的红包");
			if (TextUtils.isEmpty(getArguments().getString("receiveamount"))) {
				tv_receiveamount.setText("0.00元");
			} else {
				tv_receiveamount
						.setText(format.format(new BigDecimal(getArguments().getString("receiveamount"))) + "元");
			}
		}
		tv_detail_all = (TextView) view.findViewById(R.id.tv_detail_all);
		String alreadyReceiveAmount = getArguments().getString("alreadyreceiveamount");
		String alreadyReceiveNumber = getArguments().getString("alreadyreceivenumber");
		String repAmountTotal = getArguments().getString("repamounttotal");
		String repNumber = getArguments().getString("repnumber");
		tv_detail_all.setText("已领取" + alreadyReceiveNumber + "/" + repNumber + "，共"
				+ format.format(new BigDecimal(alreadyReceiveAmount)) + "/"
				+ format.format(new BigDecimal(repAmountTotal)) + "元");
		lv_content = (ListView) view.findViewById(R.id.lv_content);
		redDetailLIst = (ArrayList<RedDetailListItem>) getArguments().getSerializable("alreadyreceivelist");
		redEnvelopDetailAdapter = new RedEnvelopDetailAdapter(mContext, redDetailLIst, 1);
		lv_content.setAdapter(redEnvelopDetailAdapter);

	}

}
