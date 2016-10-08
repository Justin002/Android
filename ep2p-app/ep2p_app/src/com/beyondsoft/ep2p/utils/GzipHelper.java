package com.beyondsoft.ep2p.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * gzip压缩解压工具类
 * 
 * @author hardy.zhou
 *
 */
public class GzipHelper {
	/**
	 * GZIP压缩
	 * 
	 * @param str
	 *            要压缩的字符串
	 * @return GZIP压缩后的byte数组
	 * @throws IOException
	 */
	public static byte[] compress(String str) throws IOException {

		if (str == null || str.length() == 0) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(str.getBytes("ISO-8859-1"));
			gzip.flush();
			gzip.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * GZIP解压缩
	 * 
	 * @param zipText
	 *            GZIP压缩过的字符串
	 * @return 解压后的字符串
	 * @throws IOException
	 */
	public static String decompress(String zipText) throws IOException {
		byte[] compressed = zipText.getBytes("ISO-8859-1");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(compressed);
		try {
			GZIPInputStream gunzip = new GZIPInputStream(in);
			byte[] buffer = new byte[256];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
			return out.toString("ISO-8859-1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
