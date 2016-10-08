
package com.beyondsoft.ep2p.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.beyondsoft.ep2p.common.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Ivan.lu
 * @description
 */
public class StringRequest2 extends StringRequest {

	private static final String PROTOCOL_CHARSET = "utf-8";
	private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);
	private String requestBody;
//	/**交易密码验证参数**/
	private String mTranKey;
	private static Context mContext;
	public static Gson g = new Gson();

	public StringRequest2(final int tag,final String url, Object object, final IDataConnectListener listener,Context... context) {
		super(Method.POST, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				try {
					arg0 = GzipHelper.decompress(arg0);
					String decryptStr = SecurityUtil.decrypt(SysConfig.passwordSecretKey, arg0);
					JSONObject jsonObject = new JSONObject(decryptStr);
					String code=jsonObject.optString("code");
					String message = jsonObject.optString("message");
					switch (code) {
					case Constants.CODE_900:
						if(URL.MINE_VALIDATE_PAY_PWD.equals(url)){
							listener.onErrorResponse(tag,decryptStr);						
						}else{
							listener.onErrorResponse(tag,message);
						}
						Logs.d("返回的错误数据:" + decryptStr);
						break;
					case Constants.CODE_200:
						Logs.d("返回的数据:" + decryptStr);
						listener.onResponse(tag, decryptStr);
						break;
					case Constants.CODE_905:
						Logs.d("返回的错误数据:Token过期啦!");
						listener.onErrorResponse(tag, "Token过期,请重新登录!");
						break;
					case Constants.CODE_701:
						Logs.d("返回的数据:" + decryptStr);
						listener.onResponse(tag, decryptStr);
						break;
					case Constants.CODE_702:
						Logs.d("返回的数据:" + decryptStr);
						listener.onResponse(tag, decryptStr);
						break;
					case Constants.CODE_703:
						Logs.d("返回的数据:" + decryptStr);
						listener.onResponse(tag, decryptStr);
						break;
					case Constants.CODE_704:
						Logs.d("返回的数据:" + decryptStr);
						listener.onResponse(tag, decryptStr);
						break;
					case Constants.CODE_801:
						Logs.d("返回的数据:" + decryptStr);
						listener.onResponse(tag, decryptStr);
						break;
					case Constants.CODE_802:
						Logs.d("返回的数据:" + decryptStr);
						listener.onResponse(tag, decryptStr);
						break;
					case Constants.CODE_803:
						Logs.d("返回的数据:" + decryptStr);
						listener.onResponse(tag, decryptStr);
						break;
					case Constants.CODE_804:
						Logs.d("返回的数据:" + decryptStr);
						listener.onResponse(tag, decryptStr);
						break;
					case Constants.CODE_805:
						Logs.d("返回的数据:" + decryptStr);
						listener.onResponse(tag, decryptStr);
						break;
					default:
						listener.onErrorResponse(tag,message);
						break;
					}
				} catch (JSONException e) {
					e.printStackTrace();
					listener.onErrorResponse(tag, "Json解析错误");
				} catch (Exception e) {
					e.printStackTrace();
					listener.onErrorResponse(tag, "其他错误");
				}
			}

		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				String errorMsg;
				errorMsg = arg0.getMessage();
				if (arg0.networkResponse == null || arg0.networkResponse.statusCode == 500) {
					errorMsg = "无网络或服务器异常!";
				}
				listener.onErrorResponse(tag, errorMsg);
			}
		});
		if (object != null) {
			this.requestBody = JsonUtils.parseObj2Json(object);
			try {
				JSONObject jsonObject= new JSONObject(this.requestBody);
				mTranKey=jsonObject.optString("tranPKey");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		if(context.length<1){
			mContext=(Context) listener;
		}else{
			mContext=context[0];
		}
		Logs.d("请求的url:" + url);
		setTag(tag);
	}

	@Override
	public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
		// return super.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1,
		// 1.0f));
		return super.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, // 默认超时时间，应设置一个稍微大点儿的
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // 默认最大尝试次数
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		Logs.d("request:" + requestBody);
		if (requestBody == null) {
			return null;
		}
		String encryptRequestBody = SecurityUtil.encrypt(SysConfig.passwordSecretKey, requestBody);
		try {
			return GzipHelper.compress(encryptRequestBody);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getBodyContentType() {
		return PROTOCOL_CONTENT_TYPE;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept-Encoding", SecurityUtil.encrypt(SysConfig.passwordSecretKey, "gzip"));
		headers.put("token", SecurityUtil.encrypt(SysConfig.passwordSecretKey, CommonUtils.getStringByKey(mContext,Constants.EP2P_TOKEN)));
		if(mTranKey!=null&&!"".equals(mTranKey)){
			headers.put("tranPKey",SecurityUtil.encrypt(SysConfig.passwordSecretKey,mTranKey));
		}
		return headers;
	}

	public static <T> T Json2Object(Object values, TypeToken<T> token) {
		try {
			return g.fromJson(values.toString(), token.getType());
		} catch (Exception ex) {
			ex.printStackTrace();
			Logs.d(values + " 无法转换为 " + token.getRawType().getName() + " 对象!" + ex);
			return null;
		}

	}
}
