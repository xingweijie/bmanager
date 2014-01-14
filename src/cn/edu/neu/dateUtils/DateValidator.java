package cn.edu.neu.dateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 判断时间字符串是否合法
 * 
 * @author Neoh
 * 
 */
public class DateValidator {
	public static boolean isValid(String datetime)  {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DefaultFormat.getDefaultDateformat());
		try {
			if (null != simpleDateFormat.parse(datetime)) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
	}

	public static boolean isValid(String datetime,
			SimpleDateFormat simpleDateFormat) {
		try {
			if (null != simpleDateFormat.parse(datetime)) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
	}
}
