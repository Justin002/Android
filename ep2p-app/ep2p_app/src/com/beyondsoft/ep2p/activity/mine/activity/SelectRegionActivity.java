package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.adapter.CityInfoListAdapter;
import com.beyondsoft.ep2p.activity.mine.bean.CityInfoBean;
import com.beyondsoft.ep2p.view.SideBar;
import com.beyondsoft.ep2p.view.SideBar.OnTouchingLetterChangedListener;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 选择属地页面
 * 
 * @author hardy.zhou
 *
 */
public class SelectRegionActivity extends BaseActivity implements OnClickListener, OnTouchingLetterChangedListener {

	private TextView tv_title;

	private ListView lv_city_list;

	private List<CityInfoBean> mCityInfoBeanList;
	private CityInfoListAdapter mCityInfoListAdapter;
	private Handler mHandler;
	private EditText et_search;

	private SideBar sideBar;
	private String letterStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
	private List<CityInfoBean> tempCityInfoBeanList;

	private List<Integer> letterPositionList = new ArrayList<Integer>();
	private Map<Integer, Integer> letterMap = new HashMap<Integer, Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_region);
		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.region);
		et_search = (EditText) findViewById(R.id.et_search_edit);

		sideBar = (SideBar) findViewById(R.id.sb_sidebar);
		sideBar.setOnTouchingLetterChangedListener(this);

		lv_city_list = (ListView) findViewById(R.id.lv_city_list);

		myHandler();
		mCityInfoListAdapter = new CityInfoListAdapter(mContext, mHandler);
		lv_city_list.setAdapter(mCityInfoListAdapter);

		et_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				Message message = mHandler.obtainMessage();
				message.what = 2;
				mHandler.sendMessage(message);
			}
		});
		loadContactData();
	}

	private void loadContactData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				mCityInfoBeanList = getContactList();
				tempCityInfoBeanList = mCityInfoBeanList;
				dealData(tempCityInfoBeanList);
				Message message = mHandler.obtainMessage();
				message.what = 1;
				mHandler.sendMessage(message);
			}
		}).start();
	}

	private void myHandler() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					break;
				case 1:
					mCityInfoListAdapter.setData(tempCityInfoBeanList, letterPositionList);
					break;
				case 2:
					String content = et_search.getText().toString();
					tempCityInfoBeanList = filterContactList(content);
					dealData(tempCityInfoBeanList);
					mCityInfoListAdapter.setData(tempCityInfoBeanList, letterPositionList);
					break;
				case 3: {
				}
					break;
				default:
					break;
				}
			}
		};
	}

	/**
	 * 
	 * 过滤联系人列表
	 * 
	 * @param searchStr
	 * @return
	 */
	private List<CityInfoBean> filterContactList(String searchStr) {
		List<CityInfoBean> filterCityInfoList = new ArrayList<CityInfoBean>();
		if (null != mCityInfoBeanList && null != searchStr && !"".equals(searchStr.trim())) {
			for (CityInfoBean cityInfoBean : mCityInfoBeanList) {
				String name = cityInfoBean.getName();
				String telphone = cityInfoBean.getTelphone();
				if (name.contains(searchStr.trim()) || telphone.contains(searchStr.trim())) {
					filterCityInfoList.add(cityInfoBean);
				}
			}
		} else {
			filterCityInfoList = mCityInfoBeanList;
		}
		return filterCityInfoList;
	}

	public void dealData(List<CityInfoBean> cityInfoBeanList) {
		letterMap.clear();
		letterPositionList.clear();
		int index = 0;
		List<String> letterList = new ArrayList<String>();
		if (null != cityInfoBeanList) {
			for (int i = 0; i < cityInfoBeanList.size(); i++) {
				CityInfoBean cityInfoBean = cityInfoBeanList.get(i);
				String letter = cityInfoBean.getLetter();
				if (letterList.contains(letter)) {
				} else {
					letterList.add(letter);
					index = letterStr.indexOf(letter);
					letterPositionList.add(i);
					if (!letterMap.containsKey(index)) {
						letterMap.put(index, i);
					}
				}
			}
		}
	}

	/**
	 * 获取电话联系人列表
	 * 
	 * @return
	 */
	private List<CityInfoBean> getContactList() {
		List<CityInfoBean> cityInfoBeanList = new ArrayList<CityInfoBean>();

		try {
			String[] selectCol = new String[] { ContactsContract.Contacts.DISPLAY_NAME,
					ContactsContract.Contacts.HAS_PHONE_NUMBER, BaseColumns._ID };
			String[] selPhoneCols = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER,
					ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
					ContactsContract.CommonDataKinds.Phone.SORT_KEY_ALTERNATIVE };

			String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOT NULL) AND ("
					+ ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND (" + ContactsContract.Contacts.DISPLAY_NAME
					+ " != ''))";

			ContentResolver phoneContentResolver = this.getContentResolver();
			Cursor contactCursor = phoneContentResolver.query(ContactsContract.Contacts.CONTENT_URI, selectCol, select,
					null, ContactsContract.Contacts.SORT_KEY_ALTERNATIVE + " COLLATE LOCALIZED ASC");
			while (contactCursor.moveToNext()) {
				String contactId = contactCursor.getString(contactCursor.getColumnIndex(BaseColumns._ID));
				Cursor fieldPhones = phoneContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						selPhoneCols, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null,
						null);
				while (fieldPhones.moveToNext()) {
					CityInfoBean cityInfoBean = new CityInfoBean();
					String telphone = fieldPhones
							.getString(fieldPhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					String name = fieldPhones
							.getString(fieldPhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
					String letter = fieldPhones
							.getString(fieldPhones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.SORT_KEY_ALTERNATIVE))
							.substring(0, 1).toUpperCase();
					cityInfoBean.setName(name);
					cityInfoBean.setTelphone(telphone);
					cityInfoBean.setLetter(letter);
					cityInfoBeanList.add(cityInfoBean);
				}
				try {
					if (fieldPhones != null) {
						fieldPhones.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				if (contactCursor != null) {
					contactCursor.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityInfoBeanList;
	}

	@Override
	public void onTouchingLetterChanged(int s) {
		if (letterMap.containsKey(s)) {
			int position = letterMap.get(s);
			if (position >= 0 && position < tempCityInfoBeanList.size()) {
				lv_city_list.setSelection(position);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
