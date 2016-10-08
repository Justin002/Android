package com.beyondsoft.ep2p.utils;
import android.content.Context;
import android.graphics.Bitmap;

import com.beyondsoft.ep2p.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ImageLoadOptions {

	/**显示圆型默认图**/
	public static DisplayImageOptions getCircleOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				 .showImageOnLoading(R.drawable.empty_head_bg)
				// // 设置图片Uri为空或是错误的时候显示的图片
				 .showImageForEmptyUri(R.drawable.empty_head_bg)
				// // 设置图片加载/解码过程中错误时候显示的图片
				 .showImageOnFail(R.drawable.empty_head_bg)
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				// 设置图片下载前的延迟
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
				 .displayer(new RoundedBitmapDisplayer(120))//是否设置为圆角，弧度为多少
				//.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
		
		return options;
	}
	
	/**显示其他默认图**/
	public static DisplayImageOptions getOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				 .showImageOnLoading(R.drawable.layout_round_white)
				// // 设置图片Uri为空或是错误的时候显示的图片
				 .showImageForEmptyUri(R.drawable.layout_round_white)
				// // 设置图片加载/解码过程中错误时候显示的图片
				 .showImageOnFail(R.drawable.layout_round_white)
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				// 设置图片下载前的延迟
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				//.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
		
		return options;
	}
	
	/**显示人气封面默认图**/
	public static DisplayImageOptions getPopRadioOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				 .showImageOnLoading(R.drawable.radio_default_big)
				// // 设置图片Uri为空或是错误的时候显示的图片
				 .showImageForEmptyUri(R.drawable.radio_default_big)
				// // 设置图片加载/解码过程中错误时候显示的图片
				 .showImageOnFail(R.drawable.radio_default_big)
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				// 设置图片下载前的延迟
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				//.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
		
		return options;
	}
	
	/**显示播放封面默认图**/
	public static DisplayImageOptions getPlayDetailTopOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				 .showImageOnLoading(R.drawable.cover_default_bg)
				// // 设置图片Uri为空或是错误的时候显示的图片
				 .showImageForEmptyUri(R.drawable.cover_default_bg)
				// // 设置图片加载/解码过程中错误时候显示的图片
				 .showImageOnFail(R.drawable.cover_default_bg)
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				// 设置图片下载前的延迟
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
				//.displayer(new BlurBitmapDisplayer(context, 24))//是否设置为圆角，弧度为多少
				//.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
		
		return options;
	}
	
	/**显示列表封面第一张默认图**/
	public static DisplayImageOptions getRadioListBlurOptions(Context context) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				 .showImageOnLoading(R.drawable.more_radio_list_top_bg)
				// // 设置图片Uri为空或是错误的时候显示的图片
				 .showImageForEmptyUri(R.drawable.more_radio_list_top_bg)
				// // 设置图片加载/解码过程中错误时候显示的图片
				 .showImageOnFail(R.drawable.more_radio_list_top_bg)
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				// 设置图片下载前的延迟
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
				.displayer(new BlurBitmapDisplayer(context,25))//是否设置为圆角，弧度为多少
				//.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
		
		return options;
	}
	
	/**显示播放封面模糊默认图**/
	public static DisplayImageOptions getRadioPlayBlurOptions(Context context) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				 .showImageOnLoading(R.drawable.play_radio_blur_bg)
				// // 设置图片Uri为空或是错误的时候显示的图片
				 .showImageForEmptyUri(R.drawable.play_radio_blur_bg)
				// // 设置图片加载/解码过程中错误时候显示的图片
				 .showImageOnFail(R.drawable.play_radio_blur_bg)
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				// 设置图片下载前的延迟
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
				.displayer(new BlurBitmapDisplayer(context,25))//是否设置为圆角，弧度为多少
				//.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
		
		return options;
	}
	
	   /**显示首页Banner默认图**/
    public static DisplayImageOptions getHomeBannerPageOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // // 设置图片在下载期间显示的图片
                 .showImageOnLoading(R.drawable.page_img_loop1_03)
                // // 设置图片Uri为空或是错误的时候显示的图片
                 .showImageForEmptyUri(R.drawable.page_img_loop1_03)
                // // 设置图片加载/解码过程中错误时候显示的图片
                 .showImageOnFail(R.drawable.page_img_loop1_03)
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)
                // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
                // .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)//设置图片的解码配置
                // 设置图片下载前的延迟
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // 。preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                //.displayer(new FadeInBitmapDisplayer(100))// 淡入
                .build();
        
        return options;
    }
}
