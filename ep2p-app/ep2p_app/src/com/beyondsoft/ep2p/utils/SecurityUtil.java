/*
 **************************************************************************
 * 版权声明：
 * 本软件为大展信息科技（深圳）有限公司开发研制。未经本公司正式书面同意，
 * 其他任何个人、团体不得使用、复制、修改或发布本软件. 
 **************************************************************************
 * 程序描述:
 * 加密解密工具
 * 
 **************************************************************************
 * 修改历史:  
 * Date:       	    by:    		    Reason:  
 *           
 * 2015年7月14日     Simon.Hoo		Initial Version.
 *************************************************************************
 */
package com.beyondsoft.ep2p.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Description：<br>
 * 加密解密工具
 * 
 * @author Simon.Hoo
 * @date 2015年7月14日
 * @version v1.0.0
 */
public class SecurityUtil {

	/**
	 * 选择加密算法
	 * 
	 * JDK6支持以下任意一种算法 PBEWITHMD5ANDDES PBEWITHMD5ANDTRIPLEDES
	 * PBEWITHSHAANDDESEDE PBEWITHSHA1ANDRC2_40 PBKDF2WITHHMACSHA1
	 */
	public static final String ALGORITHM = "PBEWithMD5AndDES";

	/**
	 * 密钥
	 */
	public static final String SALT = "88224400";

	/**
	 * 定义迭代次数为1000次
	 */
	private static final int ITERATIONCOUNT = 1000;

	/**
	 * Description: <br>
	 * 获取加密算法中使用的盐值,解密中使用的盐值必须与加密中使用的相同才能完成操作. 盐长度必须为8字节
	 * 
	 * @author Simon.Hoo
	 * @date 2011年4月22日
	 * @version v1.0.0
	 * @return byte[] 盐值
	 * @throws Exception
	 */
	public static byte[] getSalt() throws Exception {
		// 实例化安全随机数
		SecureRandom random = new SecureRandom();
		// 产出盐
		return random.generateSeed(8);
	}

	/**
	 * Description: <br>
	 * 产出盐
	 * 
	 * @author Simon.Hoo
	 * @date 2011年4月22日
	 * @version v1.0.0
	 * @return byte[] 盐值
	 */
	public static byte[] getStaticSalt() {
		return SALT.getBytes();
	}

	/**
	 * Description: <br>
	 * 根据PBE密码生成一把密钥
	 * 
	 * @author Simon.Hoo
	 * @date 2011年4月22日
	 * @version v1.0.0
	 * @param password
	 *            生成密钥时所使用的密码
	 * @return PBE算法密钥
	 */
	private static Key getPBEKey(String password) {
		// 实例化使用的算法
		SecretKeyFactory keyFactory;
		SecretKey secretKey = null;
		try {
			keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			// 设置PBE密钥参数
			PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
			// 生成密钥
			secretKey = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return secretKey;
	}

	/**
	 * Description: <br>
	 * 加密明文字符串
	 * 
	 * @author Simon.Hoo
	 * @date 2011年4月22日
	 * @version v1.0.0
	 * @param encryPassword
	 *            生成密钥时所使用的密码(加密的密码，这里用用户名来代替)
	 * @param strVal
	 *            待加密的明文字符串
	 * @param salt
	 *            盐值
	 * @return 加密后的密文字符串
	 */
	public static String encrypt(String encryPassword, String strVal, byte[] salt) {

		Key key = getPBEKey(encryPassword);
		byte[] encipheredData = null;
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATIONCOUNT);
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);

			cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

			encipheredData = cipher.doFinal(strVal.getBytes());
		} catch (Exception e) {

		}
		return bytesToHexString(encipheredData);
	}

	/**
	 * Description: <br>
	 * 加密明文字符串
	 * 
	 * @author Simon.Hoo
	 * @date 2015年5月21日
	 * @version v1.0.0
	 * @param encryPassword
	 *            生成密钥时所使用的密码(加密的密码，这里用用户名来代替)
	 * @param strVal
	 *            待加密的明文字符串
	 * @return 加密后的密文字符串
	 */
	public static String encrypt(String encryPassword, String strVal) {
		Key key = getPBEKey(encryPassword);
		byte[] encipheredData = null;
		PBEParameterSpec parameterSpec = new PBEParameterSpec(getStaticSalt(), ITERATIONCOUNT);
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
			encipheredData = cipher.doFinal(strVal.getBytes("UTF-8"));
		} catch (Exception e) {

		}
		return bytesToHexString(encipheredData);
	}

	/**
	 * Description: <br>
	 * 解密密文字符串
	 * 
	 * @author Simon.Hoo
	 * @date 2011年4月22日
	 * @version v1.0.0
	 * @param decryptPassword
	 *            生成密钥时所使用的密码(解密的密码)
	 * @param encryptedVal
	 *            待解密的明文字符串
	 * @param salt
	 *            盐值(如需解密,该参数需要与加密时使用的一致)
	 * @return 解密后明文
	 */
	public static String decrypt(String decryptPassword, String encryptedVal, byte[] salt) {
		Key key = getPBEKey(decryptPassword);
		byte[] passDec = null;
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATIONCOUNT);
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
			passDec = cipher.doFinal(hexStringToBytes(encryptedVal));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(passDec);
	}

	/**
	 * Description: <br>
	 * 解密密文字符串
	 * 
	 * @author Simon.Hoo
	 * @date 2011年4月22日
	 * @version v1.0.0
	 * @param decryptPassword
	 *            生成密钥时所使用的密码(解密的密码)
	 * @param encryptedVal
	 *            待解密的明文字符串
	 * @return 解密后明文
	 */
	public static String decrypt(String decryptPassword, String encryptedVal) {
		String decStr = "";
		try {
			Key key = getPBEKey(decryptPassword);
			byte[] passDec = null;
			PBEParameterSpec parameterSpec = new PBEParameterSpec(getStaticSalt(), ITERATIONCOUNT);
			try {
				Cipher cipher = Cipher.getInstance(ALGORITHM);
				cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
				passDec = cipher.doFinal(hexStringToBytes(encryptedVal));
			} catch (Exception e) {
				e.printStackTrace();
			}
			decStr = new String(passDec, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decStr;
	}

	/**
	 * 
	 * Description: <br>
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @author Simon.Hoo
	 * @date 2011年4月22日
	 * @version v1.0.0
	 * @param src
	 *            字节数组
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Description: <br>
	 * 将十六进制字符串转换为字节数组
	 * 
	 * @author Simon.Hoo
	 * @date 2011年4月22日
	 * @version v1.0.0
	 * @param hexString
	 *            十六进制字符串
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Description: <br>
	 * 字符转字节数组
	 * 
	 * @author Simon.Hoo
	 * @date 2011年4月22日
	 * @version v1.0.0
	 * @param c
	 * @return
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static void main(String[] args) {
		System.out.println(SecurityUtil.encrypt("simon", "123456"));
	}
}
