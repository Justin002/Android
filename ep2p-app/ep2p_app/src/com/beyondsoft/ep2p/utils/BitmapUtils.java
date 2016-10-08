package com.beyondsoft.ep2p.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;

/**
 * Description: <br>
 * 图片处理
 */
public class BitmapUtils {

	/**
	 * 计算相应需要的大小，存在问题
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {

		int initialSize = 1;

		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));

		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			initialSize = lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			initialSize = 1;
		} else if (minSideLength == -1) {
			initialSize = lowerBound;
		} else {
			initialSize = upperBound;
		}

		int roundedSize;

		if (initialSize <= 8) {

			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	/**
	 * 创建带有影子的图片
	 * 
	 * @param originalImage
	 *            原图片
	 * @param scale
	 *            缩放比例
	 * @return
	 */
	public static Bitmap createReflectedImage(Bitmap originalImage,
			float reflectRatio, float scale) {

		int width = (int) (originalImage.getWidth() * scale);
		int height = (int) (originalImage.getHeight() * scale);

		final Rect srcRect = new Rect(0, 0, originalImage.getWidth(),
				originalImage.getHeight());
		final Rect dstRect = new Rect(0, 0, width, height);

		final int reflectionGap = 1;

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(int) (height + height * reflectRatio), Config.ARGB_8888);
		Canvas canvasRef = new Canvas(bitmapWithReflection);

		canvasRef.drawBitmap(originalImage, srcRect, dstRect, null);

		Matrix matrix = new Matrix();
		matrix.setTranslate(0, height + height + reflectionGap);
		matrix.preScale(scale, -scale);

		canvasRef.drawBitmap(originalImage, matrix, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, height, 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x80ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvasRef.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		originalImage.recycle();
		return bitmapWithReflection;
	}

	/**
	 * 得到缩小的图片，这里缩小的是图片质量
	 * 
	 * @param dataBytes
	 * @param maxWidth
	 * @return
	 */
	public static Bitmap getCorrectBmp(byte dataBytes[], int inSampleSize,
			Bitmap.Config config) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = config;
		opts.inSampleSize = inSampleSize;
		opts.inJustDecodeBounds = false;
		Bitmap originalImage = BitmapFactory.decodeByteArray(dataBytes, 0,
				dataBytes.length, opts);
		return originalImage;
	}

	/**
	 * 得到圆角图片
	 * 
	 * @param bitmap
	 *            原图像
	 * @param scale
	 *            缩放比例
	 * @param roundPx
	 *            圆角像素
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float scale,
			float roundPx, Bitmap.Config config) {

		int width = (int) (bitmap.getWidth() * scale);
		int height = (int) (bitmap.getHeight() * scale);

		Bitmap output = null;
		output = Bitmap.createBitmap(width, height, config);
		Canvas canvas = new Canvas(output);

		final int color = 0xff000000;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(0, 0, width, height);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		// draw的方式缩放
		canvas.drawBitmap(bitmap, rect, rectF, paint);

		// Matrix的方式缩放
		// Matrix matrix = new Matrix();
		// matrix.postScale(scale, scale);
		// canvas.drawBitmap(bitmap, matrix, paint);

		return output;
	}

	/**
	 * 得到缩放后的图片
	 * 
	 * @param bitmap
	 * @param scale
	 * @return
	 */
	public static Bitmap getScaleBitmap(Bitmap bitmap, float scale) {
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap dstbmp = null;
		try {
			dstbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dstbmp;
	}

	/**
	 * 
	 * @param inStream
	 * @throws IOException
	 * @return
	 */
	public static byte[] readStream(InputStream inStream) throws IOException {

		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024]; // 用数据装
		int len = -1;
		int download = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
			download += len;
		}
		outstream.close();
		return outstream.toByteArray();
	}

	/**
	 * 得到手机data目录下的图片
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static Bitmap getBmpFromFile(Context context, String fileName) {
		try {
			FileInputStream imgInputStream = context.openFileInput(fileName);
			Bitmap bmp = BitmapFactory.decodeStream(imgInputStream);
			return bmp;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存图片到指定位置(jpg)
	 * 
	 * @param context
	 * @param bmp
	 * @param fileName
	 * @return
	 */
	public static void saveBmpToJPG(Bitmap bmp, String filePath,
			String fileName, int quality) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(filePath, fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fileOut = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, quality, fileOut);
			fileOut.flush();
			fileOut.close();
			if (Logs.IS_DEBUG)
				Log.i("debug", "保存文件: " + fileName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存图片到SDcard中
	 * 
	 * @param context
	 * @param bmp
	 * @param fileName
	 */
	public static void saveBmpToSdcard(Bitmap bmp, String directoryName,
			String fileName) {
		String filePath = "";
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_REMOVED)) {
			if (Logs.IS_DEBUG)
				Log.i("img", "sdcard");
			filePath = Environment.getExternalStorageDirectory()
					.getAbsolutePath()
					+ File.pathSeparator
					+ directoryName
					+ File.pathSeparator + fileName + ".png";
		} else {
			return;
			// if (Logs.IS_DEBUG)
			// Log.i("img", Environment.MEDIA_REMOVED);
			// filePath = Environment.getDataDirectory()
			// .getAbsolutePath() + File.pathSeparator + fileName + ".png";
		}
		File imageFile = new File(filePath);
		try {
			fileName = fileName + ".png";

			if (Logs.IS_DEBUG)
				Log.i("debug", "保存文件: " + fileName);

			FileOutputStream fileOut = new FileOutputStream(imageFile);
			bmp.compress(Bitmap.CompressFormat.PNG, 0, fileOut);
			fileOut.flush();
			fileOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将Bitmap转换为byte数组
	 * 
	 * @param bmp
	 * @return
	 */
	public static byte[] getBytesFromBitmap(Bitmap bmp) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
			return baos.toByteArray();

		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * 将Bitmap转换为byte数组
	 * 
	 * @param bmp
	 * @return
	 */
	public static byte[] getBytesFromBitmapForJPG(Bitmap bmp) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			return baos.toByteArray();

		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 根据filePath得到缩放的bitmap
	 * 
	 * @param context
	 * @param filePath
	 *            图片路径
	 * @param size
	 *            缩放后的图片大小 得到的图片大小在size和2*size之间
	 * @return
	 */
	public static Bitmap getBitmap(String filePath, int size) {
		Logs.d("getBitmap start");
		Bitmap bitmap = null;
		int degree = readPictureDegree(filePath);
		Logs.e("degree:" + degree);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPurgeable = true;
		options.inTempStorage = new byte[12 * 1024];
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(filePath, options);
		int width = options.outWidth;
		int height = options.outHeight;
		Logs.e("缩放前的宽:" + width);
		Logs.e("缩放前的高:" + height);
		/** 计算缩放比例 **/
		float scale = 0;
		// if (width < height) {
		// /** 宽小于高时，竖向 **/
		// scale = width / size;
		// } else {
		// scale = height / size;
		// }
		scale = (float) width / size;
		if (scale < 1) {
			scale = 1;
		}
		Logs.e("缩放前的比例是:" + scale);
		options.inJustDecodeBounds = false;
		options.inSampleSize = Math.round(scale);
		Logs.i("缩放后的比例是:" + Math.round(scale));
		bitmap = BitmapFactory.decodeFile(filePath, options);
		if (bitmap != null) {
			Logs.e("缩放后的宽:" + bitmap.getWidth());
			Logs.e("缩放后的高:" + bitmap.getHeight());
		}
		return bitmap;
	}

	/**
	 * 根据Stream得到缩放的bitmap
	 * 
	 * @param context
	 * @param filePath
	 *            图片路径
	 * @param size
	 *            缩放后的图片大小 得到的图片大小在size和2*size之间
	 * @return
	 */
	public static Bitmap getBitmap(InputStream is, int size) {
		Bitmap bitmap = null;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPurgeable = true;
		options.inTempStorage = new byte[12 * 1024];
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeStream(is, null, options);
		int width = options.outWidth;
		int height = options.outHeight;
		if (Logs.IS_DEBUG) {
			Logs.e("缩放前的宽:" + width);
			Logs.e("缩放前的高:" + width);
		}
		/** 计算缩放比例 **/
		int scale = 0;
		// if (width < height) {
		// /** 宽小于高时，竖向 **/
		// scale = width / size;
		// } else {
		// scale = height / size;
		// }
		scale = width / size;
		if (scale < 1) {
			scale = 1;
		}
		options.inJustDecodeBounds = false;
		options.inSampleSize = 2;
		bitmap = BitmapFactory.decodeStream(is, null, options);
		if (Logs.IS_DEBUG) {
			Logs.e("缩放后的宽:" + bitmap.getWidth());
			Logs.e("缩放后的高:" + bitmap.getHeight());
		}
		return bitmap;
	}

	/**
	 * 获得比特图
	 * 
	 * @param bytes
	 * @param size
	 * @return
	 */
	public static Bitmap getBitmap(byte[] bytes, int size) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPurgeable = true;
		options.inTempStorage = new byte[12 * 1024];
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		int width = options.outWidth;
		int height = options.outHeight;
		Log.i("img", "width:" + width + "height" + height);
		/** 计算缩放比例 **/
		int scale = 0;
		if (width < height) {
			/** 宽小于高时，竖向 **/
			scale = width / size;
		} else {
			scale = height / size;
		}
		if (scale < 1) {
			scale = 1;
		}
		if (scale < 1) {
			scale = 1;
		}
		if (Logs.IS_DEBUG)
			Log.i("TEST", "scale" + scale);
		options.inJustDecodeBounds = false;
		options.inSampleSize = scale;
		bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		return bitmap;
	}

	/**
	 * 获得视频缩略图
	 * 
	 * @param cr
	 * @param path
	 * @return
	 */
	public static Bitmap getVideoThumbnail(ContentResolver cr, String path) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		String whereClause = MediaColumns.DATA + "='" + path + "'";
		Logs.e("whereClause:" + whereClause);
		Cursor cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				new String[] { BaseColumns._ID }, whereClause, null,
				null);
		if (cursor == null || cursor.getCount() == 0) {
			return ThumbnailUtils.createVideoThumbnail(path,
					MediaStore.Video.Thumbnails.MINI_KIND);
		}
		cursor.moveToFirst();
		String videoId = cursor.getString(cursor
				.getColumnIndex(BaseColumns._ID));
		Logs.e("videoId:" + videoId);
		if (videoId == null) {
			return ThumbnailUtils.createVideoThumbnail(path,
					MediaStore.Video.Thumbnails.MINI_KIND);
		}
		cursor.close();
		long videoIdLong = Long.parseLong(videoId);
		bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, videoIdLong,
				Images.Thumbnails.MICRO_KIND, options);
		return bitmap;
	}

	/**
	 * 拷贝流
	 * 
	 * @param is
	 * @param os
	 */
	public static void copyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * 得到圆形图片
	 * 
	 * @param bitmap
	 *            原图像
	 * @return
	 */
	public static Bitmap getRoundBitmap(Bitmap bitmap) {

		int width = bitmap.getWidth();
		int radius = width / 2;
		Bitmap output = Bitmap.createBitmap(width, width, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff000000;
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		// canvas.drawCircle(width/2,width/2, width/2, paint);
		canvas.drawCircle(radius, radius, radius, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return output;
	}
	
	/** 
     * 得到圆角图片 
     *  
     * @param source 
     * @return 
     */  
    public static Bitmap getRoundConerImage(Bitmap source,float corners)  
    {  
        final Paint paint = new Paint();  
        paint.setAntiAlias(true);  
        Bitmap target = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Config.ARGB_8888);  
        Canvas canvas = new Canvas(target);  
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());  
        canvas.drawRoundRect(rect, corners,corners, paint);  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(source, 0, 0, paint);  
        return target;  
    }  

	/**
	 * 得到圆形图片
	 * 
	 * @param bitmap
	 *            原图像
	 * @return
	 */
	public static Bitmap getRoundBitmap() {

		Bitmap output = Bitmap.createBitmap(140, 140, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xffffffff;
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawCircle(70, 70, 70, paint);
		return output;
	}

	/**
	 * 得到有边框的图片
	 * 
	 * @param bitmap
	 *            原图像
	 * @return
	 */
	public static Bitmap getBorderBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth() + 20;
		int height = bitmap.getHeight() + 20;
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(bitmap, 10, 10, null);
		bitmap.recycle();
		bitmap = null;
		return output;
	}

	/**
	 * 读取图片属性:旋转的角度
	 * 
	 * @param path
	 *            图片的决定路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;

			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;

			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 * @param bitmap
	 * @return
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 计算bitmap的缩放比
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * 缩放bitmap
	 * 
	 * @param res
	 * @param resId
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth,
			int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * 把path中的bitmap转成字节数据
	 * 
	 * @param Path
	 * @param width
	 * @param height
	 * @return
	 */
	public static byte[] compressImageFromFile(String Path, float width,
			float height) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(Path, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		float hh = height;//
		float ww = width;//
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置采样率

		newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

		bitmap = BitmapFactory.decodeFile(Path, newOpts);

		int degree = BitmapUtils.readPictureDegree(Path);// 如果图片要旋转的放先转一下
		bitmap = BitmapUtils.rotaingImageView(degree, bitmap);

		// return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
		// 其实是无效的,大家尽管尝试
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stream.toByteArray();

	}
}
