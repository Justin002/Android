package com.beyondsoft.ep2p.utils;

/**
 * Description: <br>
 * 远程数据连接监听器
 * 
 * @author Ivan.Lu
 */
public interface IDataConnectListener {

	/**
	 * 远程返回的数据处理
	 * 
	 * @param tag
	 *            连接类型 用于区分不同的连接类型
	 * @param values
	 *            返回的数据
	 */
	public void onResponse(int tag, Object values);

	/**
	 * 远程返回的错误信息
	 * 
	 * @param tag
	 *            连接类型
	 * @param errorEntry
	 *            返回的错误信息
	 */
	public void onErrorResponse(int tag, String error);
}
