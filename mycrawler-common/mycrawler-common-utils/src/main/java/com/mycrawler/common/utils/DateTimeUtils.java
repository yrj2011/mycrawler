package com.mycrawler.common.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
/**
 * 
* @ClassName: DateTimeUtils
* @Description:
* @author yangrenjiang
* @date 2017年6月26日 下午11:04:07
*
 */
public class DateTimeUtils {

	public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");

	/**
	 * YEAR_RANGE_SCOPE
	 */
	public final static int YEAR_RANGE_SCOPE = 4;

	public static final long MILLIS_PER_SECOND = 1000;

	/**
	 */
	public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;

	/**
	 */
	public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;

	/**
	 */
	public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

	/**
	 * SEMI_MONTH
	 */
	public final static int SEMI_MONTH = 1001;

	/**
	 * fields
	 */
	private static final int[][] FIELDS = { { Calendar.MILLISECOND }, { Calendar.SECOND }, { Calendar.MINUTE },
			{ Calendar.HOUR_OF_DAY, Calendar.HOUR }, { Calendar.DATE, Calendar.DAY_OF_MONTH, Calendar.AM_PM },
			{ Calendar.MONTH, DateTimeUtils.SEMI_MONTH }, { Calendar.YEAR }, { Calendar.ERA } };

	/**
	 * RANGE_WEEK_SUNDAY
	 */
	public final static int RANGE_WEEK_SUNDAY = 1;

	/**
	 * RANGE_WEEK_MONDAY
	 */
	public final static int RANGE_WEEK_MONDAY = 2;

	/**
	 * RANGE_WEEK_RELATIVE
	 */
	public final static int RANGE_WEEK_RELATIVE = 3;

	/**
	 * RANGE_WEEK_CENTER
	 */
	public final static int RANGE_WEEK_CENTER = 4;

	/**
	 * RANGE_MONTH_SUNDAY
	 */
	public final static int RANGE_MONTH_SUNDAY = 5;

	/**
	 * RANGE_MONTH_MONDAY
	 */
	public final static int RANGE_MONTH_MONDAY = 6;
	
	/** ISO_DATETIME_FORMAT */
	public final static String ISO_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/** ISO_DATETIME_FULL_FORMAT */
	public final static String ISO_DATETIME_FULL_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/** ISO_DATE_FORMAT */
	public final static String ISO_DATE_FORMAT = "yyyy-MM-dd";

	/** ISO_SHORT_DATE_FORMAT */
	public final static String ISO_SHORT_DATE_FORMAT = "yyyy-MM";

	/** ISO_TIME_FORMAT */
	public final static String ISO_TIME_FORMAT = "HH:mm:ss";

	public static String formatDateTime(Date value) {
		return formatDateTime(value, ISO_DATETIME_FORMAT);
	}

	public static String formatDateTime(Date value, String defaultFormat) {
		if (value == null) {
			return "";
		}
		String strFormatStyle = StringUtils.isBlank(defaultFormat) ? ISO_DATETIME_FORMAT : defaultFormat;
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat(strFormatStyle);
		return objSimpleDateFormat.format(value);
	}

	public static String formatDate(Timestamp value) {
		return formatDateTime(value, ISO_DATE_FORMAT);
	}

	public static String formatDate(Timestamp value, String defaultFormat) {
		if (value == null) {
			return "";
		}
		String strFormatStyle = StringUtils.isBlank(defaultFormat) ? ISO_DATE_FORMAT : defaultFormat;
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat(strFormatStyle);
		return objSimpleDateFormat.format(value);
	}

	public static String formatDate(java.sql.Date value) {
		return formatDate(value, ISO_DATE_FORMAT);
	}

	public static String formatDate(java.util.Date value) {
		return formatDate(value, ISO_DATE_FORMAT);
	}

	public static String formatDate(java.util.Date value, String defaultFormat) {
		if (value == null) {
			return "";
		}
		String strFormatStyle = StringUtils.isBlank(defaultFormat) ? ISO_DATE_FORMAT : defaultFormat;
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat(strFormatStyle);
		return objSimpleDateFormat.format(value);
	}

	public static String formatDate(java.sql.Date value, String defaultFormat) {
		if (value == null) {
			return "";
		}
		String strFormatStyle = StringUtils.isBlank(defaultFormat) ? ISO_DATE_FORMAT : defaultFormat;
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat(strFormatStyle);
		return objSimpleDateFormat.format(value);
	}

	public static String formatTime(Time value, String defaultFormat) {
		if (value == null) {
			return "";
		}
		String strFormatStyle = StringUtils.isBlank(defaultFormat) ? ISO_TIME_FORMAT : defaultFormat;
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat(strFormatStyle);
		return objSimpleDateFormat.format(value);
	}

	public static String formatTime(Time value) {
		return formatTime(value, ISO_TIME_FORMAT);
	}

	public static String formatShortDate(Timestamp value) {
		return formatDateTime(value, ISO_SHORT_DATE_FORMAT);
	}

	public static String formatShortDate(Timestamp value, String defaultFormat) {
		if (value == null) {
			return "";
		}
		String strFormatStyle = StringUtils.isBlank(defaultFormat) ? ISO_SHORT_DATE_FORMAT : defaultFormat;
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat(strFormatStyle);
		return objSimpleDateFormat.format(value);
	}
	public static boolean isSameDay(Date dateSource, Date dateDesti) {
		if (dateSource == null || dateDesti == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar objCalendarSource = Calendar.getInstance();
		objCalendarSource.setTime(dateSource);
		Calendar objCalendarDesti = Calendar.getInstance();
		objCalendarDesti.setTime(dateDesti);
		return isSameDay(objCalendarSource, objCalendarDesti);
	}

	public static boolean isSameDay(Calendar calSource, Calendar calDesti) {
		if (calSource == null || calDesti == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return calSource.get(Calendar.ERA) == calDesti.get(Calendar.ERA) && calSource.get(Calendar.YEAR) == calDesti.get(Calendar.YEAR)
				&& calSource.get(Calendar.DAY_OF_YEAR) == calDesti.get(Calendar.DAY_OF_YEAR);
	}

	public static boolean isSameInstant(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return date1.getTime() == date2.getTime();
	}

	public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return cal1.getTime().getTime() == cal2.getTime().getTime();
	}

	public static boolean isSameLocalTime(Calendar calSource, Calendar calDesti) {
		return calSource.equals(calDesti);
	}

	public static Date parseDate(String value, String[] parsePatterns) throws ParseException {
		if (value == null || parsePatterns == null) {
			throw new IllegalArgumentException("Date and Patterns must not be null");
		}

		SimpleDateFormat parser = null;
		ParsePosition pos = new ParsePosition(0);
		for (int i = 0; i < parsePatterns.length; i++) {
			if (i == 0) {
				parser = new SimpleDateFormat(parsePatterns[0]);
			} else {
				parser.applyPattern(parsePatterns[i]);
			}
			pos.setIndex(0);
			Date date = parser.parse(value, pos);
			if (date != null && pos.getIndex() == value.length()) {
				return date;
			}
		}
		throw new ParseException("Unable to parse the date: " + value, -1);
	}
	
	public static Date parseDate(String value, String parsePattern) throws ParseException {
		return parseDate(value , new String[]{parsePattern});
	}
	
	public static Date parseDate(String value) throws ParseException {
		return parseDate(value , new String[]{DateTimeUtils.ISO_DATETIME_FULL_FORMAT , DateTimeUtils.ISO_DATETIME_FORMAT , DateTimeUtils.ISO_DATE_FORMAT});
	}

	public static java.sql.Date nowAsDate() {
		Calendar objCurrentCalendar = Calendar.getInstance();
		StringBuffer sbDateString = new StringBuffer();
		int iYear = objCurrentCalendar.get(Calendar.YEAR);
		int iMonth = objCurrentCalendar.get(Calendar.MONTH) + 1;
		int iDate = objCurrentCalendar.get(Calendar.DATE);
		sbDateString.append(iYear);
		sbDateString.append("-");
		sbDateString.append(iMonth);
		sbDateString.append("-");
		sbDateString.append(iDate);
		try {
			return java.sql.Date.valueOf(sbDateString.toString());
		} catch (Exception e) {
			return new java.sql.Date(System.currentTimeMillis());
		}
	}

	public static Time nowAsTime() {
		Calendar objCurrentCalendar = Calendar.getInstance();
		StringBuffer sbTimeString = new StringBuffer();
		int iHourOfDay = objCurrentCalendar.get(Calendar.HOUR_OF_DAY);
		int iMinute = objCurrentCalendar.get(Calendar.MINUTE);
		int iSecond = objCurrentCalendar.get(Calendar.SECOND);
		sbTimeString.append(iHourOfDay);
		sbTimeString.append(":");
		sbTimeString.append(iMinute);
		sbTimeString.append(":");
		sbTimeString.append(iSecond);
		return java.sql.Time.valueOf(sbTimeString.toString());
	}

	public static Timestamp nowAsTimestamp() {
		Calendar objCurrentCalendar = Calendar.getInstance();
		return new Timestamp(objCurrentCalendar.getTimeInMillis());
	}

	public static Timestamp addYears(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	public static Timestamp addMonths(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	public static Timestamp addWeeks(Date date, int amount) {
		return add(date, Calendar.WEEK_OF_YEAR, amount);
	}

	public static Timestamp addDays(Date date, int amount) {
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}

	public static Timestamp addHours(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}

	public static Timestamp addMinutes(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	public static Timestamp addSeconds(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	public static Timestamp addMilliseconds(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	public static Timestamp add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return new Timestamp(c.getTimeInMillis());
	}

	public static Date round(Date date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar gval = Calendar.getInstance();
		gval.setTime(date);
		modify(gval, field, true);
		return gval.getTime();
	}

	public static Calendar round(Calendar date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar rounded = (Calendar) date.clone();
		modify(rounded, field, true);
		return rounded;
	}

	public static Date round(Object date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (date instanceof Date) {
			return round((Date) date, field);
		} else if (date instanceof Calendar) {
			return round((Calendar) date, field).getTime();
		} else {
			throw new ClassCastException("Could not round " + date);
		}
	}

	public static Date truncate(Date date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar gval = Calendar.getInstance();
		gval.setTime(date);
		modify(gval, field, false);
		return gval.getTime();
	}

	public static Calendar truncate(Calendar date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar truncated = (Calendar) date.clone();
		modify(truncated, field, false);
		return truncated;
	}

	public static Date truncate(Object date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (date instanceof Date) {
			return truncate((Date) date, field);
		} else if (date instanceof Calendar) {
			return truncate((Calendar) date, field).getTime();
		} else {
			throw new ClassCastException("Could not truncate " + date);
		}
	}

	private static void modify(Calendar val, int field, boolean round) {
		if (val.get(Calendar.YEAR) > 280000000) {
			throw new ArithmeticException("Calendar value too large for accurate calculations");
		}

		if (field == Calendar.MILLISECOND) {
			return;
		}
		Date date = val.getTime();
		long time = date.getTime();
		boolean done = false;

		int millisecs = val.get(Calendar.MILLISECOND);
		if (!round || millisecs < 500) {
			time = time - millisecs;
			if (field == Calendar.SECOND) {
				done = true;
			}
		}

		int seconds = val.get(Calendar.SECOND);
		if (!done && (!round || seconds < 30)) {
			time = time - (seconds * 1000L);
			if (field == Calendar.MINUTE) {
				done = true;
			}
		}
		int minutes = val.get(Calendar.MINUTE);
		if (!done && (!round || minutes < 30)) {
			time = time - (minutes * 60000L);
		}

		if (date.getTime() != time) {
			date.setTime(time);
			val.setTime(date);
		}
		boolean roundUp = false;
		for (int i = 0; i < FIELDS.length; i++) {
			for (int j = 0; j < FIELDS[i].length; j++) {
				if (FIELDS[i][j] == field) {
					if (round && roundUp) {
						if (field == DateTimeUtils.SEMI_MONTH) {
							if (val.get(Calendar.DATE) == 1) {
								val.add(Calendar.DATE, 15);
							} else {
								val.add(Calendar.DATE, -15);
								val.add(Calendar.MONTH, 1);
							}
						} else {
							val.add(FIELDS[i][0], 1);
						}
					}
					return;
				}
			}
			int offset = 0;
			boolean offsetSet = false;

			if (field == DateTimeUtils.SEMI_MONTH && FIELDS[i][0] == Calendar.DATE) {
				offset = val.get(Calendar.DATE) - 1;
				if (offset >= 15) {
					offset -= 15;
				}
				roundUp = offset > 7;
				offsetSet = true;
			} else if (field == Calendar.AM_PM && FIELDS[i][0] == Calendar.HOUR_OF_DAY) {
				offset = val.get(Calendar.HOUR_OF_DAY);
				if (offset >= 12) {
					offset -= 12;
				}
				roundUp = offset > 6;
				offsetSet = true;
			}

			if (!offsetSet) {
				int min = val.getActualMinimum(FIELDS[i][0]);
				int max = val.getActualMaximum(FIELDS[i][0]);
				offset = val.get(FIELDS[i][0]) - min;
				roundUp = offset > ((max - min) / 2);
			}
			if (offset != 0) {
				val.set(FIELDS[i][0], val.get(FIELDS[i][0]) - offset);
			}
		}
		throw new IllegalArgumentException("The field " + field + " is not supported");

	}

	public static Timestamp getFirstOfWeek(Calendar cal, int week) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}

	public static Timestamp getLastOfWeek(Calendar cal, int week) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return new Timestamp(cal.getTimeInMillis());
	}

	public static int getCurrentWeek() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getCurrentWeek(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getCurrentWeek(Timestamp t1) {
		Calendar cal;
		if (t1 == null) {
			cal = Calendar.getInstance();
		} else {
			cal = new GregorianCalendar();
			cal.setTime(t1);
		}
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getWeekOfYear(Calendar cal, Timestamp ts) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		cal.setTimeInMillis(ts.getTime());
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getCurrentYear() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		return calendar.get(Calendar.YEAR);
	}

	public static int getYear(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int getCurrentMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getCurrentMonth(Timestamp t1) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(t1);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getCurrentMonth(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static Timestamp getTimestamp(Timestamp timestamp, int day, int hour, int minute) {
		Calendar calendar = new GregorianCalendar();
		if (timestamp != null) {
			calendar.setTimeInMillis(timestamp.getTime());
		} else {
			calendar = Calendar.getInstance();
		}
		calendar.add(Calendar.DATE, day);
		calendar.set(Calendar.HOUR, hour);
		calendar.set(Calendar.MINUTE, minute);
		return new Timestamp(calendar.getTimeInMillis());
	}

	public static int compare(Calendar c1, Calendar c2, int what) {
		int number = 0;
		switch (what) {
		case Calendar.YEAR:
			number = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
			break;
		case Calendar.MONTH:
			int years = compare(c1, c2, Calendar.YEAR);
			number = 12 * years + (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
			break;
		case Calendar.DATE:
			number = (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / (1000 * 60 * 60 * 24));
			break;
		default:
			number = (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / (1000 * 60 * 60 * 24));
			break;
		}
		return number;
	}

	public static Timestamp getLastOfYear(Timestamp t1) {
		Calendar a = new GregorianCalendar();
		a.setTime(t1);
		a.set(Calendar.MONTH, 11);
		a.set(Calendar.DATE, 1);// ����������Ϊ12�µ�һ��
		a.roll(Calendar.DATE, -1);// ���ڻع�һ�죬Ҳ���ǵ������һ��
		return new Timestamp(a.getTimeInMillis());
	}

	public static Timestamp getFirstOfYear(Timestamp t1) {
		Calendar a = new GregorianCalendar();
		a.setTime(t1);
		a.set(Calendar.MONTH, 0);
		a.set(Calendar.DATE, 1);// ����������Ϊ12�µ�һ��
		return new Timestamp(a.getTimeInMillis());
	}

	/*
	 */
	public static Timestamp getLastOfMonth(Timestamp t1) {
		Calendar a = new GregorianCalendar();
		a.setTime(t1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		// int MaxDate = a.get(Calendar.DATE);
		return new Timestamp(a.getTimeInMillis());
	}

	public static Timestamp getFirstOfMonth(Timestamp t1) {
		Calendar a = new GregorianCalendar();
		a.setTime(t1);
		a.set(Calendar.DATE, 1);// ����������Ϊ���µ�һ��
		// int MaxDate = a.get(Calendar.DATE);
		// System.out.println("�����������:"+MaxDate);
		return new Timestamp(a.getTimeInMillis());
	}

	public static int compare(Timestamp t1, Timestamp t2, int what) {

		Calendar c1 = Calendar.getInstance();
		c1.setTime(t1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(t2);

		int number = 0;
		switch (what) {
		case Calendar.YEAR:
			number = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
			break;
		case Calendar.MONTH:
			int years = compare(c1, c2, Calendar.YEAR);
			number = 12 * years + (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
			break;
		case Calendar.DATE:
			number = (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / (1000 * 60 * 60 * 24));
			break;
		default:
			number = (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / (1000 * 60 * 60 * 24));
			break;
		}
		return number;
	}

	public static boolean after(Timestamp t1, Timestamp t2) {
		if (t1 == null || t2 == null) {
			return false;
		}

		return t1.after(t2);
	}

	public static boolean equals(Timestamp t1, Timestamp t2) {
		if (t1 == null && t2 == null) {
			return true;
		} else {
			if (t1 == null) {
				return t2.equals(t1);
			} else {
				return t1.equals(t2);
			}
		}
	}

	public static boolean before(Timestamp t1, Timestamp t2) {
		if (t1 == null || t2 == null) {
			return false;
		}

		return t1.before(t2);
	}

	public static Timestamp stringConvertTimestamp(String time) {
		if (null == time || "".equals(time)) {
			return null;
		}
		if (time.length() == 10) {// yyyy-MM-dd
			time = time + " 00:00:00.000000000";
		} else if (time.length() == 16) {// yyyy-MM-dd hh:mm
			time = time + ":00.000000000";
		} else if (time.length() == 19) {// yyyy-MM-dd hh:mm:dd
			time = time + ".000000000";
		}
		return Timestamp.valueOf(time);
	}

	public static Date stringConvertDate(String date) {
		if (null == date || "".equals(date)) {
			return null;
		}
		if (date.length() == 10) {// yyyy-MM-dd
			date = date + " 00:00:00";
		} else if (date.length() == 16) {// yyyy-MM-dd hh:mm
			date = date + ":00";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date objDate;
		try {
			objDate = formatter.parse(date);
		} catch (ParseException e) {
			objDate = null;
		}
		return objDate;
	}

	public static int compareDate(String date1, String date2, int what) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date objDate1 = df.parse(date1);
		Date objDate2 = df.parse(date2);
		return compareDate(objDate1, objDate2, what);
	}

	public static int compareDate(Date date1, Date date2, int what) {
		Calendar objCalendar1 = Calendar.getInstance();
		objCalendar1.setTime(date1);
		Calendar objCalendar2 = Calendar.getInstance();
		objCalendar2.setTime(date2);
		int iResult = compare(objCalendar1, objCalendar2, what);
		if (iResult > 0) {
			return 1;
		} else if (iResult < 0) {
			return -1;
		} else {
			return 0;
		}
	}
 
	public static int compareCurrentDate(Date currentDate) {
		return compareDate(currentDate, nowAsDate(), Calendar.DATE);
	}
 
	public static long diffCurrentDate(Date date) {
		long oldTime = date.getTime();
		long nowTime = new Date().getTime();
		return nowTime - oldTime;
	}
	 
	public static int compareCurrentDate(String currentDate) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date objDate = df.parse(currentDate);
		return compareDate(objDate, nowAsDate(), Calendar.DATE);

	}
 
	public static int getMonth(Date date) {
		if (null == date) {
			throw new IllegalArgumentException("The date is null");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.MONTH);
	}

	public static Date getEndOfTheDay(Date date) {
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(date);
		endCal.set(Calendar.HOUR_OF_DAY, 23);
		endCal.set(Calendar.MINUTE, 59);
		endCal.set(Calendar.SECOND, 59);
		endCal.set(Calendar.MILLISECOND, 999);

		date = endCal.getTime();
		return date;
	}

	public static Date getStartOfTheDay(Date date) {
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(date);
		endCal.set(Calendar.HOUR_OF_DAY, 00);
		endCal.set(Calendar.MINUTE, 00);
		endCal.set(Calendar.SECOND, 00);
		endCal.set(Calendar.MILLISECOND, 000);

		date = endCal.getTime();
		return date;
	}
	
	public static boolean containOnlyDayPart(Date date) {
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(date);
		if (endCal.get(Calendar.HOUR_OF_DAY) == 0 && endCal.get(Calendar.MINUTE) == 0 && endCal.get(Calendar.SECOND) == 0
				&& endCal.get(Calendar.MILLISECOND) == 0) {
			return true;
		}
		return false;
	}
}
