package com.derek.dpasswords.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtil {
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static Date getDateFromDateString(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT, Locale.getDefault());

		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getDateStringFromDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT, Locale.getDefault());
		String dateString = sdf.format(date);

		return dateString;
	}

	public static Date getDateFromDateString(String dateString, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());

		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getDateStringFromDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String dateString = sdf.format(date);

		return dateString;
	}
}
