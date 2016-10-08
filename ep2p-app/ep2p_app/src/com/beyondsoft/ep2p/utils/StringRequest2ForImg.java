
package com.beyondsoft.ep2p.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.BitmapFactory;

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
public class StringRequest2ForImg extends StringRequest {

	private static final String PROTOCOL_CHARSET = "utf-8";
	private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);
	private String requestBody;
	private Context mContext;
	public static Gson g = new Gson();

	public StringRequest2ForImg(final int tag,String url, Object object, final IDataConnectListener listener,Context... context) {
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
						listener.onErrorResponse(tag,message);
						Logs.d("返回的错误数据:" + decryptStr);
						break;
					case Constants.CODE_200:
						Logs.d("返回的数据:" + decryptStr);
						listener.onResponse(tag, decryptStr);
						break;
					case Constants.CODE_905:
						Logs.d("返回的错误数据:Token过期啦!");
						listener.onErrorResponse(tag, "Token过期啦!");
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
			this.requestBody =object.toString();
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
		if(requestBody!=null){
			return BitmapUtils.getBytesFromBitmapForJPG(BitmapFactory.decodeFile(requestBody));
		}
		return null;
		
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
