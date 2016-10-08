
package com.beyondsoft.ep2p;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.mine.activity.GesturePasswordActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.IDataConnectListener;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.view.gesture.LockUtil;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Description: FragmentActivity基类,所有FragmentActivity继承它
 * 
 * @author Ivan.Lu
 */
public class BaseFragmentActivity extends FragmentActivity implements IDataConnectListener {

	private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0x8001;
	private static final String FILDIR = "ep2p/IMG";
	protected Uri imageUri;
	protected RequestQueue mRequestQueue;
	protected Gson gson;
	private File imgFile;
	private int mWidth;
	private int mHeight;
	private ControllerApplication controllerApplication;
	protected BaseFragmentActivity mContext;
	protected ImageLoader imageLoader=ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		ActivityManager.getInstance().pushActivity2(this);
		controllerApplication = (ControllerApplication) getApplication();
		mRequestQueue = controllerApplication.getRequestQueue();
		gson = new Gson();
		mContext = this;
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ImageView back_btn = (ImageView) findViewById(R.id.title_left_btn);
		if (back_btn != null) {
			back_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		}
	}

	public void setTitle(String title) {
		TextView titleView = (TextView) findViewById(R.id.title_content_tv);
		if (titleView != null) {
			titleView.setText(title);
		}
	}

	public void setRightTitle(String title) {
		TextView rightView = (TextView) findViewById(R.id.title_right);
		if (rightView != null) {
			rightView.setText(title);
		}
	}

	public void connection(StringRequest request) {
		mRequestQueue.add(request);
	}

	public void disconnect(Object tag) {
		mRequestQueue.cancelAll(tag);
	}

	public void pushActivity(Class<?> class1) {
		startActivity(new Intent(this, class1));
	}

	public void pushActivity(Intent intent) {
		startActivity(intent);
	}

	public void pushActivityForResult(Intent intent, int requestCode) {
		startActivityForResult(intent, requestCode);
	}

	public int dip2px(float dipValue) {
		final float scale = this.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public int px2dip(float pxValue) {
		final float scale = this.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	protected void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha;
		getWindow().setAttributes(lp);
	}

	/**
	 * 打开照相机
	 */
	protected void getImageFromCamera(int width, int height) {
		mWidth = width;
		mHeight = height;
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			File dirFile = new File(Environment.getExternalStorageDirectory() + File.separator + FILDIR);
			if (!dirFile.exists())
				dirFile.mkdirs();
			String fileName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			imgFile = new File(dirFile, fileName);
			imageUri = Uri.fromFile(imgFile);
			intent.putExtra(ImageColumns.ORIENTATION, 0);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
		} else {
			Toast.makeText(getApplicationContext(), "没有SD卡",Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 打开相册
	 * 
	 * @param absPath
	 */
	protected void openImage(String absPath) {
		if (!StringUtils.isEmpty(absPath)) {
			File file = new File(absPath);
			if (file.exists()) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Uri uri = Uri.fromFile(file);
				intent.setDataAndType(uri, "image/*");
				startActivity(intent);
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA && resultCode == Activity.RESULT_OK) {
			String sdState = Environment.getExternalStorageState();
			if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
				return;
			}
			cropImageUri();
		} else if (requestCode == 11 && resultCode == Activity.RESULT_OK) {
			TakePhotoCallBack(imgFile.getAbsolutePath());
		} else if (requestCode == 12 && resultCode == Activity.RESULT_OK) {
			TakePhotoCallBack(imgFile.getAbsolutePath());
		}
	}

	private void cropImageUri() {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(imageUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", mWidth);
		intent.putExtra("outputY", mHeight);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("return-data", false);
		startActivityForResult(intent, 11);
	}

	/**
	 * 照相完后的回调
	 */
	protected void TakePhotoCallBack(String photoAbsolutePath) {
		
	}

	/**
	 * 从相册获取图片
	 * 
	 * @param width
	 * @param height
	 */
	protected void getImageFromPhoto(int width, int height) {
		mWidth = width;
		mHeight = height;
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			File dirFile = new File(Environment.getExternalStorageDirectory() + File.separator + FILDIR);
			if (!dirFile.exists())
				dirFile.mkdirs();
			String fileName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			imgFile = new File(dirFile, fileName);
			imageUri = Uri.fromFile(imgFile);
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", width);
			intent.putExtra("outputY", height);
			intent.putExtra("return-data", false);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, 12);
		} else {
			Toast.makeText(getApplicationContext(), "没有SD卡",Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		if("Token过期,请重新登录!".equals(error)){
			sendBroadcast(new Intent(Constants.UPDATEDATA));
			CommonUtils.toastMsgShort(this, error);
			LockUtil.clearPwd(this);
			LockUtil.setPwdStatus(this,false);
			CommonUtils.deleteConfigInfo(this, Constants.EP2P_TOKEN);
			CommonUtils.deleteConfigInfo(this, Constants.PLAYLISTENTIME);
			CommonUtils.deleteConfigInfo(this, Constants.PLAYCURRENTLISTENTIME);
			CommonUtils.deleteConfigInfo(this, Constants.PLAYCURRENTLISTENNUM);
			CommonUtils.deleteConfigInfo(this, Constants.PLAYDIALOG);	
			Intent intent=new Intent(this,LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
		}else{
			CommonUtils.toastMsgShort(this, error);
		}
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		JPushInterface.onResume(this);
		if(LockUtil.getPwdStatus(this)){
			if (controllerApplication.isTimeOutInBackground()) 
			{
				Intent intent=new Intent(this, GesturePasswordActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		}
		controllerApplication.resetTimeInBackground();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		if(!controllerApplication.isAppOnForeground()){
			controllerApplication.saveTimeInBackground();
		}
	}
}
