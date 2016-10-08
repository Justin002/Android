
package com.beyondsoft.ep2p;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.IDataConnectListener;
import com.beyondsoft.ep2p.view.gesture.LockUtil;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Description: Fragment基类,所有Fragment继承它
 * 
 * @author Ivan.Lu
 */
public class BaseFragment extends Fragment implements IDataConnectListener {

    protected String TAG = "";
	protected RequestQueue mRequestQueue;
	protected Gson gson;
	protected BaseFragmentActivity mContext;
	protected ImageLoader imageLoader=ImageLoader.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = this.getTag();
		gson = new Gson();
		mContext = (BaseFragmentActivity) getActivity();
		ControllerApplication controllerApplication = (ControllerApplication) mContext.getApplication();
		mRequestQueue = controllerApplication.getRequestQueue();
	}

	public void connection(StringRequest request) {
		mRequestQueue.add(request);
	}

	public void disconnect(Object tag) {
		mRequestQueue.cancelAll(tag);
	}

	public int dip2px(float dipValue) {
		final float scale = this.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public int px2dip(float pxValue) {
		final float scale = this.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public String getString_txt(int id) {
		return mContext.getResources().getString(id);
	}

	@Override
	public void onResponse(int tag, Object values) {
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		if("Token过期,请重新登录!".equals(error)){
			getActivity().sendBroadcast(new Intent(Constants.UPDATEDATA));
			CommonUtils.toastMsgShort(getActivity(), error);
			LockUtil.clearPwd(getContext());
			LockUtil.setPwdStatus(getContext(),false);
			CommonUtils.deleteConfigInfo(getContext(), Constants.EP2P_TOKEN);
			CommonUtils.deleteConfigInfo(getContext(), Constants.PLAYLISTENTIME);
			CommonUtils.deleteConfigInfo(getContext(), Constants.PLAYCURRENTLISTENTIME);
			CommonUtils.deleteConfigInfo(getContext(), Constants.PLAYCURRENTLISTENNUM);
			CommonUtils.deleteConfigInfo(getContext(), Constants.PLAYDIALOG);	
			Intent intent=new Intent(getActivity(),LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
		}else{
			CommonUtils.toastMsgShort(getActivity(), error);
		}
	}

	public void pushActivity(Class<?> class1) {
		startActivity(new Intent(mContext, class1));
	}

	public void pushActivity(Intent intent) {
		startActivity(intent);
	}

	public void pushActivityForResult(Intent intent, int requestCode) {
		mContext.startActivityForResult(intent, requestCode);
	}

	public void pushActivityForResult(Class<?> class1, int requestCode) {
		mContext.startActivityForResult(new Intent(getActivity(), class1), requestCode);
	}

}
