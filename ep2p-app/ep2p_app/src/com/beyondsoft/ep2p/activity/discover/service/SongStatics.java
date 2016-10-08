package com.beyondsoft.ep2p.activity.discover.service;

import java.io.File;

import android.os.Environment;



public class SongStatics {
	

	/**
	 * 临时目录
	 */
	public final static String PATH_TEMP = Environment
			.getExternalStorageDirectory() + File.separator + "haplayer";

	/**
	 * Logcat日志目录
	 */
	public final static String PATH_LOGCAT = PATH_TEMP + File.separator
			+ "logcat";

	/**
	 * 全局异常日志目录
	 */
	public final static String PATH_CRASH = PATH_TEMP + File.separator
			+ "crash";

	/**
	 * 歌曲目录
	 */
	public final static String PATH_MP3 = PATH_TEMP + File.separator + "mp3";

	/**
	 * 歌词目录
	 */
	public final static String PATH_KSC = PATH_TEMP + File.separator + "ksc";
	/**
	 * 歌手写真目录
	 */
	public final static String PATH_ARTIST = PATH_TEMP + File.separator
			+ "artist";
	/**
	 * 专辑图
	 */
	public final static String PATH_ALBUM = PATH_TEMP + File.separator
			+ "album";
	/**
	 * 缓存
	 */
	public final static String PATH_CACHE = PATH_TEMP + File.separator
			+ "cache";
	/**
	 * 图片缓存
	 */
	public final static String PATH_CACHE_IMAGE = PATH_TEMP + File.separator
			+ "cache" + File.separator + "image";

	/**
	 * 皮肤
	 */
	public final static String PATH_SKIN = PATH_TEMP + File.separator + "skin";

	/**
	 * 启动界面
	 */
	public final static String PATH_SPLASH = PATH_TEMP + File.separator
			+ "splash";
	/**
	 * 应用启动图片
	 */
	public final static String PATH_SPLASH_JPG = PATH_SPLASH + File.separator
			+ "splash.jpg";

	/**
	 * 应用启动文件
	 */
	public final static String PATH_SPLASH_TXT = PATH_SPLASH + File.separator
			+ "splash_readme.txt";

	/**
	 * 应用更新的apk包存放路径
	 */
	public final static String PATH_APK = PATH_TEMP + File.separator + "apk";
	/**
	 * EasyTouch
	 */
	public final static String PATH_EasyTouch = PATH_TEMP + File.separator
			+ "easytouch";

	/***
	 * ------------------------------------应用基本配置-----------------------------
	 **/
	/**
	 * 皮肤数据
	 */
//	public static SkinInfo skinInfo;
	/**
	 * 皮肤默认的id
	 */
	public static String skinID = "hp19910420";
	/**
	 * 皮肤默认的idkeyt
	 */
	public static String skinID_KEY = "skinID_KEY";
	/**
	 * 应用是否是第一次启动
	 */
	public static boolean isFrist = true;
	/**
	 * 应用是否是第一次启动key
	 */
	public static String isFrist_KEY = "isFrist_KEY";
	/**
	 * 是否是第一次点击显示桌面歌词key
	 */
	public static String isFristSettingDesLrc_KEY = "isFristSettingDesLrc_KEY";
	/**
	 * 是否是第一次点击显示桌面歌词
	 */
	public static boolean isFristSettingDesLrc = true;

	/**
	 * 应用是否在wifi下联网
	 */
	public static boolean isWifi = true;
	/**
	 * 应用是否在wifi下联网key
	 */
	public static String isWifi_KEY = "isWifi_KEY";

	/**
	 * 歌曲播放模式
	 */
	public static int playModel = 0; // 0是 顺序播放 1是随机播放 2是循环播放 3是单曲播放
	/**
	 * 歌曲播放模式key
	 */
	public static String playModel_KEY = "playModel_KEY";
	/**
	 * 是否显示桌面歌词
	 */
	public static boolean showDesktopLyrics = false;
	/**
	 * 是否显示桌面歌词key
	 */
	public static String showDesktopLyrics_KEY = "showDesktopLyrics_KEY";

	/**
	 * 桌面歌词是否可以移动
	 */
	public static String desktopLyricsIsMove_KEY = "desktopLyricsIsMove_KEY";
	/**
	 * 桌面歌词是否可以移动
	 */
	public static boolean desktopLyricsIsMove = true;

	/**
	 * 歌词窗口x坐标
	 */
	public static String LRCX_KEY = "LRCX_KEY";
	public static int LRCX = 0;

	/**
	 * 歌词窗口y坐标
	 */
	public static String LRCY_KEY = "LRCY_KEY";
	public static int LRCY = 0;

	/**
	 * 是否显示锁屏歌词
	 */
	public static boolean showLockScreen = false;
	/**
	 * 是否显示锁屏歌词key
	 */

	public static String showLockScreen_KEY = "showLockScreen_KEY";

	/**
	 * 是否线控
	 */
	public static boolean isWire = true;

	/**
	 * 是否线控key
	 */
	public static String isWire_KEY = "isWire_KEY";
	/**
	 * 是否开启辅助操控
	 */
	public static boolean isEasyTouch = false;
	/**
	 * 是否开启辅助操控key
	 */
	public static String isEasyTouch_KEY = "isEasyTouch_KEY";
	/**
	 * 是否开启问候音
	 */
	public static boolean isSayHello = true;
	/**
	 * 是否开启问候音key
	 */
	public static String isSayHello_KEY = "isSayHello_KEY";
	/**
	 * 音质索引
	 */
	public static int soundIndex = 0;
	/**
	 * 音质索引key
	 */
	public static String soundIndex_KEY = "soundIndex_KEY";

	/**
	 * 标题颜色索引
	 */
	public static int colorIndex = 0;
	/**
	 * 标题颜色key
	 */
	public static String colorIndex_KEY = "colorIndex_KEY";

	/**
	 * 播放列表类型
	 */
	public static int playListType = 0;
	/**
	 * 播放列表类型key
	 */
	public static String playListType_KEY = "playListType_KEY";
	/**
	 * 播放歌曲id
	 */
	public static String playInfoID = "";
	/**
	 * 播放歌曲id key
	 */
	public static String playInfoID_KEY = "playInfoID_KEY";
	

	
}
