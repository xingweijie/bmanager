package cn.edu.neu.dateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date, long, String 之间的转换
 * 
 * @author Neoh
 * 
 */
public class DateTransfer {

	public static Date toDate(long datetime) {
		return new Date(datetime);
	}

	public static Date toDate(String datetime) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DefaultFormat.getDefaultDateformat());
		return simpleDateFormat.parse(datetime);
	}

	public static Date toDate(String datetime, SimpleDateFormat simpleDateFormat)
			throws ParseException {
		return simpleDateFormat.parse(datetime);
	}

	public static long toLong(Date datetime) {
		return datetime.getTime();
	}

	public static long toLong(String datetime) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DefaultFormat.getDefaultDateformat());
		return simpleDateFormat.parse(datetime).getTime();
	}

	public static long toLong(String datetime, SimpleDateFormat simpleDateFormat)
			throws ParseException {
		return simpleDateFormat.parse(datetime).getTime();
	}
}
