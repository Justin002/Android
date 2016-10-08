
package com.beyondsoft.ep2p.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * Description: <br>
 * 字符处理类
 * 
 * @author Ivan.Lu
 */
public class StringUtils {
	/**
	 * 判断是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(String obj) {
		if (null == obj || "".equals(obj)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否为空
	 * 
	 * @param s
	 * @return
	 */

	public static boolean isEmpty(String s) {
		if (null == s)
			return true;
		if (s.length() == 0)
			return true;
		if (s.trim().length() == 0)
			return true;
		return false;
	}

	/**
	 * 数组是否为空
	 * 
	 * @param values
	 * @return
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 判断是否符合邮箱格式
	 * 
	 * @param strEmail
	 * @return
	 */
	public static boolean isEmail(String strEmail) {
		String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个数字是否是非负小数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDecimal(String str) {

		return Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(str)
				.matches();
	}

	/**
	 * 判断一个字符串是不是0或者0.0...
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isZero(String str) {
		return Pattern.compile("(0*\\.?0*)?0$").matcher(str).matches();
	}

	// 验证手机号
	public static boolean isMobileNO(String mobiles) {
		String telRegex = "^((13[0-9])|(15[^4,\\D])|(18[0,3-9]))\\d{8}$";
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	// 验证密码
	public static boolean isPasswordNo(String password) {
		String pwd = "^[0-9A-Za-z]{6,10}$";
		if (TextUtils.isEmpty(password)) {
			return false;
		} else
			return password.matches(pwd);

	}

	/**
	 * 判断是否为固定电话
	 * 
	 * @param phone
	 * @return
	 */
	// Pattern pattern =
	// Pattern.compile("^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$");
	public static boolean isTel(String tel) {
		// Pattern pattern = Pattern.compile("([0-9]{4})([0-9]{4})([0-9]{4})");
		Pattern pattern = Pattern
				.compile("^(0[0-9]{2,3}\\-)?([0-9]{6,7})+(\\-[0-9]{1,4})?$");
		Matcher m = pattern.matcher(tel.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 是否包含特殊字符
	 * 
	 * @param tel
	 * @return
	 */
	public static boolean isN(String tel) {
		// Pattern pattern =
		// Pattern.compile("^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$");
		// String regEx =
		// "[`~#$&*()=|{}':;',\\[\\].<>/?~！#￥……&*（）—|{}【】‘；：”“’。，、？]";

		String regEx = "[#$&*()|]";

		Pattern pattern = Pattern.compile(regEx);
		Matcher m = pattern.matcher(tel.trim());
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为特殊字符
	 * 
	 * @param pInput
	 * @return
	 */
	public static boolean isSpecialChar(String pInput) {
		if (pInput == null) {
			return false;
		}
		String regEx = ".*[&|'|>|<|\\\\|/].*$";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(Pattern.compile("[\\r|\\n]")
				.matcher(pInput).replaceAll(""));
		return matcher.matches();
	}

	public static String StringFilter(String str) {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~#$&*()=|{}':;',\\[\\].<>/?~！#￥……&*（）—|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 判断是否为手机号码
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		// Pattern pattern =
		// Pattern.compile("^((\\(\\d{3}\\))|(\\d{3}\\-))?13[0-9]\\d{8}|15[089]\\d{8}");
		Pattern pattern = Pattern.compile("([0-9]{3})([0-9]{4})([0-9]{4})");
		Matcher m = pattern.matcher(phone.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断输入是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		String strPattern = "[0-9]*";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(str.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 检查是不是生日类型 支持闰年,2月特殊判断等等
	 */
	public static boolean isBirthday(String birth) {
		// Pattern pt =
		// Pattern.compile("^((((1[6-9]|[2-9]\\d)\\d{2})(0?[13578]|1[02])(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})(0?[13456789]|1[012])(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})0?2(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))0?229))$");
		// Pattern pt =
		// Pattern.compile("((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))");
		Pattern pt = Pattern
				.compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29))$");
		if (pt.matcher(birth).matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断输入是否是数字或者字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumOrLetter(String str) {
		String strPattern = "^[A-Za-z0-9]+$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(str.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 匹配身份证
	 * 
	 * @param idCard
	 * @return true 如果匹配，false 不匹配
	 */
	public static boolean isIDCard(String idCard) {
		String pattern = "^\\d{10}|\\d{13}|\\d{15}|\\d{17}(\\d|x|X)$";
		return idCard.matches(pattern);
	}

	/**
	 * 判断字符长度
	 * 
	 * @param str
	 * @param maxLen
	 * @return
	 */
	public static boolean isLength(String str, int maxLen) {
		char[] cs = str.toCharArray();
		int count = 0;
		int last = cs.length;
		for (int i = 0; i < last; i++) {
			if (cs[i] > 255)
				count += 2;
			else
				count++;
		}
		if (count >= maxLen)
			return true;
		else
			return false;
	}

	/**
	 * 得到格式化时间
	 * 
	 * @param timeInSeconds
	 * @return
	 */
	public static String getFormatTimeMsg(int timeInSeconds) {
		int hours, minutes, seconds;
		hours = timeInSeconds / 3600;
		timeInSeconds = timeInSeconds - (hours * 3600);
		minutes = timeInSeconds / 60;
		timeInSeconds = timeInSeconds - (minutes * 60);
		seconds = timeInSeconds;

		String minStr = String.valueOf(minutes);
		String secStr = String.valueOf(seconds);

		if (minStr.length() == 1)
			minStr = "0" + minStr;
		if (secStr.length() == 1)
			secStr = "0" + secStr;

		return (minStr + "分" + secStr + "秒");
	}

	/**
	 * 剪切字符串
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String cutString(String str, int len) {
		if (!StringUtils.isNull(str)) {
			if (str.length() >= len)
				str = str.substring(0, len) + "...";
		}
		return str;
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param value
	 * @return
	 */
	public static int getLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}
	
	/**是否包含中文
	 * @param value
	 * @return
	 */
	public static boolean isChinese(String value){
		String chinese = "[\u0391-\uFFE5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				return true;
			} 
		}
		return false;
	}
	
	/**验证密码规则
	 * @param value
	 * @return
	 */
	public static boolean isPwd(String value){
		//String strPattern="^[0-9A-Za-z]{6,32}$";
		String strPattern="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(value);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 将时间戳转成日期字符
	 * 
	 * @param longtime
	 * @return
	 */
	public static String getLongTime2Date(String longtime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(Long.parseLong(longtime)));
	}

	/**
	 * 将时间戳转成日期字符
	 * 
	 * @param longtime
	 * @return
	 */
	public static String getLongTime2Date(long longtime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(longtime));
	}

	/**
	 * 将日期字符转成时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static long getDate2LongTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d = sdf.parse(time);
			return d.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * 将时间戳转成日期字符
	 * 
	 * @param longtime
	 * @return
	 */
	public static String getLongTime3Date(long longtime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return getChineseDate(sdf.format(new Date(longtime)));
	}

	/**
	 * 将日期字符转成时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static String getDate2ChineseTime(String time) {
		String[] timeSplit = time.split("-");
		String date = timeSplit[1] + "月" + timeSplit[2] + "日";
		return date;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(currentTime);
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回长时间字符串格式yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDateLongLen() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(currentTime);
	}

	/**
	 * 根据time获取现在时间
	 * 
	 * @return
	 */
	public static String getStringDateforPhoto(long time) {
		Date currentTime = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return formatter.format(currentTime);
	}

	/**
	 * 获取现在时间
	 * 
	 * @return
	 */
	public static String getStringDateforPhoto() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return formatter.format(currentTime);
	}

	/**
	 * 获取前天时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getBeforeYesterday() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 2);

		String beforeYesterday = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return beforeYesterday;
	}

	/**
	 * 获取昨天时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getYesterday() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String yesterDay = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return yesterDay;
	}

	/**
	 * 获取明天时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getTomorrow() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}

	/**
	 * 获取后天时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getDayAfterTomorrow() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 2);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}

	/**
	 * 判断时间是否属于本周
	 * 
	 * @return
	 */
	public static int getWeekOfDay(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		sdf.format(cal.getTime());
		String fileTime = sdf.format(cal.getTime());
		cal.setTime(new Date());
		String currentTime = sdf.format(cal.getTime());
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		// Logs.e("所在周星期一的日期是:"+imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		// Logs.e("所在周星期日的日期是:"+imptimeEnd);
		// Logs.e("文件的日期是:"+fileTime);
		// Logs.e("今天的日期是:"+currentTime);
		int result = fileTime.compareTo(currentTime);
		// Logs.e("比较结果:"+result);
		if (result != 0) {
			result = fileTime.compareTo(imptimeBegin);
			if (result == 0) {
				result = 2;
			}
			// Logs.i("比较结果:"+result);
		}
		return result;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDateLong() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		return formatter.format(currentTime);
	}

	/**
	 * 根据年和月算出该月有多少天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthOfDay(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DATE, 1);
		long time = c.getTimeInMillis();

		c.set(Calendar.MONTH, (month + 1));
		long nexttime = c.getTimeInMillis();

		long cha = nexttime - time;
		int s = (int) (cha / (24 * 60 * 60 * 1000));

		return s;
	}

	/**
	 * 返回指定格式的字符
	 * 
	 * @param list
	 * @return
	 */
	public static String getValue(HashMap<String, String> list) {
		String value;
		StringBuffer buffer = new StringBuffer();
		Iterator iterator = list.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			// buffer.append("'"+entry.getValue().toString()+"'");
			// buffer.append(",");
			buffer.append(entry.getValue().toString());
		}
		value = buffer.substring(0, buffer.lastIndexOf(","));
		return value;
	}

	public static int compareDate(String start, String end) {
		int result = 0;
		if (!"".equals(start) && !"".equals(end)) {
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date startDate = sFormat.parse(start);
				Date endDate = sFormat.parse(end);
				result = startDate.compareTo(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取文件的后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName) {
		if (fileName != null && fileName != "") {
			int typeIndex = fileName.lastIndexOf(".");
			if (typeIndex != -1) {
				String fileType = fileName.substring(typeIndex + 1)
						.toLowerCase(Locale.CHINA);
				return fileType;
			}
		}
		return null;
	}

	/**
	 * 返回指定的时间格式
	 * 
	 * @param date
	 * @return
	 */
	public static String getChineseDate(String date) {
		String[] split_str = date.split("-");
		String date_str = split_str[0] + "年" + split_str[1] + "月"
				+ split_str[2] + "日";
		return date_str;
	}

	/**
	 * 返回指定的日期
	 */
	public static String getSpecificDate(String date, int flag) {
		int sperator;
		if (isEmpty(date)) {
			return "";
		} else {
			if (0 == flag) {// 年
				String year = date.substring(0, 4);
				// System.out.println("年是:" + year);
				return year;
			} else if (1 == flag) {// 月
				sperator = date.indexOf("-");
				String month = date.substring(sperator + 1, sperator + 3);
				// System.out.println("月是:" + month);
				return month;
			} else if (2 == flag) {// 日
				sperator = date.lastIndexOf("-");
				String day = date.substring(sperator + 1, sperator + 3);
				// System.out.println("日是:" + day);
				return day;
			} else if (3 == flag) {// 时
				sperator = date.indexOf("T");
				String hour = date.substring(sperator + 1, sperator + 3);
				// System.out.println("时是:" + hour);
				return hour;
			} else if (4 == flag) {// 分
				sperator = date.indexOf(":");
				String minute = date.substring(sperator + 1, sperator + 3);
				// System.out.println("分是:" + minute);
				return minute;
			} else if (5 == flag) {// 秒
				sperator = date.lastIndexOf(":");
				String second = date.substring(sperator + 1, sperator + 3);
				// System.out.println("秒是:" + second);
				return second;
			} else {
				return date.replace('T', ' ');
			}
		}
	}

	/**
	 * 时间处理处理, 当时间小于10时,在前面机上0
	 * 
	 * @param time
	 *            具体数字
	 * @return 返回结果
	 */
	public static String parseTime(String time) {
		String result;
		int parameter = Integer.valueOf(time);
		if (parameter >= 10) {
			result = String.valueOf(parameter);
		} else {
			result = "0" + parameter;
		}
		return result;
	}

	/**
	 * Unicode 转 GBK
	 * 
	 * @param s
	 * @return
	 */
	public static String UnicodeToGBK2(String s) {
		String[] k = s.split(";");
		String rs = "";
		for (int i = 0; i < k.length; i++) {
			int strIndex = k[i].indexOf("&#");
			String newstr = k[i];
			if (strIndex > -1) {
				String kstr = "";
				if (strIndex > 0) {
					kstr = newstr.substring(0, strIndex);
					rs += kstr;
					newstr = newstr.substring(strIndex);
				}
				int m = Integer.parseInt(newstr.replace("&#", ""));
				char c = (char) m;
				rs += c;
			} else {
				rs += k[i];
			}
		}
		return rs;
	}

	public static String getTime(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(time);
			long times = d.getTime();
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
			return sdf2.format(new Date(times));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public static String getMD5(String content) {
		try {
			MessageDigest digester = MessageDigest.getInstance("MD5");
			digester.update(content.getBytes(Charset.forName("UTF-8")));
			byte[] m = digester.digest();
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0; i < m.length; i++) {
				sBuffer.append(Integer.toHexString(0xff & m[i]));
			}
			return sBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
    public static String isTestNull(String text)
    {
        return TextUtils.isEmpty(text) ? "" : text;
    }
    
    /** 密文显示手机号码
     * @param tel
     * @return
     */
    public static String getShowPhoneNum(String tel){
    	if(tel!=null){
    		String start=tel.substring(0,3);
    		String end=tel.substring(7,11);
    		return start+"****"+end;
    	}
    	return null;
    }
    
    /**显示金额保留2位数
     * @param d
     * @return
     */
    public static String formatMoney(Double d){
		String pattern = "#0.00";
		DecimalFormat formatter = new DecimalFormat(pattern);
		return formatter.format(d);
	}
    
//    public static String formatYear(String month_str){
//    	int month=Integer.parseInt(month_str);
//    	if(month%12==0){
//    		return month/12+"年";
//    	}else{
//    		return month%12+"个月";
//    	}
//    }
    
    public static String formatYear(String month_str){
    	return month_str+"个月";
    }
    
    /**是否包含非法字符
     * @param name
     * @return
     */
    public static boolean isUserName(String name){
    	String strPattern = "^[A-Za-z0-9-_\u4e00-\u9fa5]+$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(name.trim());
		if (m.matches()) {
			return true;
		}
		return false;
    }
    
    public static String getMusicTime(int progress){
    	return (progress / 60<10?"0"+progress / 60:progress / 60)+ ":" + (progress % 60<10?"0"+progress % 60:progress % 60);
    }
    
    //t1 发布时间 ，t2 系统时间
    public static long[] CountDown(String t1, String t2)
    {
        //        long [] Str = new long[]{};
        long[] times = null;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date d1 = df.parse(t1);
            Date d2 = df.parse(t2);

            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);

            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                * (1000 * 60 * 60))
                / (1000 * 60);
            long msecond = ((diff - days * (1000 * 60 * 60 * 24) - hours
                * (1000 * 60 * 60) - minutes * (1000 * 60))) / (1000);

            Logs.d("时间戳值:" + "" + days + "天" + hours + "小时" + minutes + "分" + msecond
                + "秒");
            //            timestamp = days * 24 + hours +":"+ minutes + ":" + msecond;

            //初始倒计时的时间
            times = new long[]{days * 24 + hours, minutes, msecond};
            return times;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return times;
        }
    }
}
