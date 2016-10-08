package com.beyondsoft.ep2p.utils;

/**
 * @author Ivan.lu
 * @description
 */
public class Logs {
	public final static String LOGTAG = "debug";
	public final static boolean IS_DEBUG=true;

	public static void v(String tag, String msg) {
		if (IS_DEBUG)
			android.util.Log.v(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (IS_DEBUG)
			android.util.Log.e(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (IS_DEBUG)
			android.util.Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (IS_DEBUG)
			android.util.Log.d(tag, msg);
	}

	public static void v(String msg) {
		if (IS_DEBUG)
			android.util.Log.v(LOGTAG, msg);
	}

	public static void e(String msg) {
		if (IS_DEBUG)
			android.util.Log.e(LOGTAG, msg);
	}

	public static void i(String msg) {
		if (IS_DEBUG)
			android.util.Log.i(LOGTAG, msg);
	}

	public static void d(String msg) {
		if (IS_DEBUG)
			android.util.Log.d(LOGTAG, msg);
	}
}
