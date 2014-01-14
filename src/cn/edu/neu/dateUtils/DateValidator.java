package cn.edu.neu.dateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * �ж�ʱ���ַ����Ƿ�Ϸ�
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
