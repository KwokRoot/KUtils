package com.kwok.util.commons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 时间格式化工具
 */
public class DateUtil {
	
    // 显示日期的格式
    public static final String yyyy = "yyyy";

    // 显示日期的格式
    public static final String yyyyMM = "yyyyMM";
    
    // 显示日期的格式
    public static final String yyyyMMdd = "yyyyMMdd";

    //显示日期时间的格式
    public static final String yyyyMMddHH = "yyyyMMddHH";

    //显示日期时间的格式
    public static final String yyyyMMddHHmm = "yyyyMMddHHmm";

    // 显示日期时间的格式
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    // 显示日期的格式
    public static final String yyyy_MM = "yyyy-MM";

    // 显示日期的格式
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    
    // 显示日期时间的格式
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    // 显示日期时间的格式
    public static final String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss:SSS";
    
    // 显示简体中文日期的格式
    public static final String yyyy_MM_dd_zh = "yyyy年MM月dd日";

    // 显示简体中文日期时间的格式
    public static final String yyyy_MM_dd_HH_mm_zh = "yyyy年MM月dd日HH时mm分";
    
    // 显示简体中文日期时间的格式
    public static final String yyyy_MM_dd_HH_mm_ss_zh = "yyyy年MM月dd日HH时mm分ss秒";

    //UTC 日期时间的格式 "2018-08-08T12:00:00.000Z"  注：北京时区为东八区，领先 UTC时间 8个小时，+ 08:00 即北京时间为：2018-08-08 20:00:00.000
    public static final String utc_date = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    
    private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_DateTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(yyyy_MM_dd);
        }
    };
    
    private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_HH_mm_ss_DateTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        }
    };


    private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_zh_DateTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(yyyy_MM_dd_zh);
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_HH_mm_ss_zh_DateTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss_zh);
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyyMMddHHmm_DateTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(yyyyMMddHHmm);
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyyMMddHHmmss_DateTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(yyyyMMddHHmmss);
        }
    };

    private static ThreadLocal<SimpleDateFormat> utc_date_DateTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(utc_date);
        }
    };
    
    private static SimpleDateFormat getDateFormat(String formatStr) {
		if (formatStr.equalsIgnoreCase(yyyy_MM_dd)) {
			return yyyy_MM_dd_DateTimeFormat.get();
		} else if (formatStr.equalsIgnoreCase(yyyy_MM_dd_HH_mm_ss)) {
			return yyyy_MM_dd_HH_mm_ss_DateTimeFormat.get();
		} else if (formatStr.equalsIgnoreCase(yyyy_MM_dd_zh)) {
			return yyyy_MM_dd_zh_DateTimeFormat.get();
		} else if (formatStr.equalsIgnoreCase(yyyy_MM_dd_HH_mm_ss_zh)) {
			return yyyy_MM_dd_HH_mm_ss_zh_DateTimeFormat.get();
		} else if (formatStr.equalsIgnoreCase(yyyyMMddHHmm)) {
			return yyyyMMddHHmm_DateTimeFormat.get();
		} else if (formatStr.equalsIgnoreCase(yyyyMMddHHmmss)) {
			return yyyyMMddHHmmss_DateTimeFormat.get();
		}else if (formatStr.equalsIgnoreCase(utc_date)) {
			return utc_date_DateTimeFormat.get();
		} else {
			return new SimpleDateFormat(formatStr);
		}
    }

    /**
     * 字符串转日期 字符串格式:"yyyy-MM-dd HH:mm:ss"的形式
     *
     * @param str
     * @return
     */
    public static Date strToDate(String str) {
        return strToDate(str, yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 字符串转日期
     *
     * @param str
     * @param style
     * @return
     */
    public static Date strToDate(String str, String style) {
        try {
            if (str == null || str.equals("")) {
                return null;
            }
            DateFormat sdf = getDateFormat(style);
            Date d = sdf.parse(str);
            return d;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * long 转换Str
     *
     * @param date
     * @return
     */
    public static String dateToStr(long date, String style) {
        return dateToStr(new Date(date), style);
    }

    /**
     * long 转换Str
     *
     * @param date
     * @return
     */
    public static String dateToStr(long date) {
        return dateToStr(new Date(date), yyyy_MM_dd_HH_mm_ss);
    }

    public static String dateToStr(Date date) {
        DateFormat df = getDateFormat(yyyy_MM_dd_HH_mm_ss);
        return df.format(date);
    }
    
    /**
     * 将Date转换成字符串
     *
     * @param date
     * @param style
     * @return
     */
    public static String dateToStr(Date date, String style) {
        DateFormat df = getDateFormat(style);
        return df.format(date);
    }

    /**
     * 获取当前日期yyyy-MM-dd的形式
     *
     * @return
     */
    public static String getCuryyyy_MM_dd() {
        return dateToStr(Calendar.getInstance().getTime(), yyyy_MM_dd);
    }

    /**
     * 获取当前日期yyyyMMdd的形式
     *
     * @return
     */

    public static String getCuryyyy() {
        return dateToStr(Calendar.getInstance().getTime(), yyyy);
    }

    public static String getCuryyyyMMdd() {
        return dateToStr(Calendar.getInstance().getTime(), yyyyMMdd);
    }

    public static int getCuryyyyMMddInteger() {
        return Integer.parseInt(getCuryyyyMMdd());
    }

    /**
     * 获取当前日期yyyyMMddHHmm的形式
     *
     * @return
     */
    public static String getCuryyyyMMddHHmm() {
        return dateToStr(Calendar.getInstance().getTime(), yyyyMMddHHmm);
    }

    public static long getCurMMDDHHmmss() {
        return Long.parseLong(dateToStr(Calendar.getInstance().getTime(), yyyyMMddHHmmss));
    }

    /**
     * 获取当前日期yyyy年MM月dd日的形式
     *
     * @return
     */
    public static String getCuryyyyMMddzh() {
        return dateToStr(new Date(), yyyy_MM_dd_zh);
    }

    /**
     * 获取当前日期时间yyyy-MM-dd HH:mm:ss的形式
     *
     * @return
     */
    public static String getCurDateTime() {
        return dateToStr(new Date(), yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
     *
     * @return
     */
    public static String getCurDateTimezh() {
        return dateToStr(new Date(), yyyy_MM_dd_HH_mm_ss_zh);
    }

    public static Date getInternalDateByYear(Date d, int years) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.YEAR, years);
        return now.getTime();
    }

    public static Date getInternalDateBySec(Date d, int sec) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.SECOND, sec);
        return now.getTime();
    }

    public static Date getInternalDateByMin(Date d, int min) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.MINUTE, min);
        return now.getTime();
    }

    public static Date getInternalDateByHour(Date d, int hours) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.HOUR_OF_DAY, hours);
        return now.getTime();
    }

    public static Date getInternalDateByDay(Date d, int days) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.DAY_OF_YEAR, days);
        return now.getTime();
    }
    
    /**
     * 将 时间 fromStr 从fromStyle格式转换到toStyle的格式
     *
     * @param fromStr
     * @param fromStyle
     * @param toStyle
     * @return
     */
    public static String strToStr(String fromStr, String fromStyle,
                                  String toStyle) {
        Date d = strToDate(fromStr, fromStyle);
        return dateToStr(d, toStyle);
    }

    /**
     * 比较两个"yyyy-MM-dd HH:mm:ss"格式的日期，之间相差多少毫秒,time2-time1
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long compareDateStr(String time1, String time2) {
        Date d1 = strToDate(time1);
        Date d2 = strToDate(time2);
        return d2.getTime() - d1.getTime();
    }

    /**
     * 比较两个"yyyy-MM-dd HH:mm:ss"格式的日期，之间相差多少毫秒,time2-time1
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long compareDateStr(Date time1, Date time2) {
        return time2.getTime() - time1.getTime();
    }

    /**
     * 获取Date中的分钟
     *
     * @param d
     * @return
     */
    public static int getMin(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MINUTE);
    }

    /**
     * 获取Date中的小时(24小时)
     *
     * @param d
     * @return
     */
    public static int getHour(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取Date中的秒
     *
     * @param d
     * @return
     */
    public static int getSecond(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.SECOND);
    }

    /**
     * 获取Date中的毫秒
     *
     * @param d
     * @return
     */
    public static int getMilliSecond(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MILLISECOND);
    }

    /**
     * 获取xxxx-xx-xx的日
     *
     * @param d
     * @return
     */
    public static int getDay(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取月份，1-12月
     *
     * @param d
     * @return
     */
    public static int getMonth(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取19xx,20xx形式的年
     *
     * @param d
     * @return
     */
    public static int getYear(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.YEAR);
    }

    /**
     * 得到d 的年份+月份,如200505
     *
     * @return
     */
    public static String getYearMonthOfDate(Date d) {
        return dateToStr(d, yyyyMM);
    }

    /**
     * 得到上个月的年份+月份,如200505
     *
     * @return
     */
    public static String getYearMonthOfLastMonth() {

        return dateToStr(new Date(addMonth(new Date().getTime(), -1)), yyyyMM);
    }

    /**
     * 得到当前日期的年和月如200509
     *
     * @return String
     */
    public static String getCurYearMonth() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        String DATE_FORMAT = "yyyyMM";
        SimpleDateFormat sdf = new SimpleDateFormat(
                DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * 获得系统当前月份的天数
     *
     * @return
     */
    public static int getCurentMonthDays() {
        Date date = Calendar.getInstance().getTime();
        return getMonthDay(date);
    }

    /**
     * 获得指定日期月份的天数
     *
     * @return
     */
    public static int getMonthDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    /**
     * 在传入时间基础上加一定月份数
     *
     * @param oldTime Calendar
     * @param months  int
     * @return long
     */
    public static long addMonth(final Calendar oldTime, final int months) {
        int year, month, date, hour, minute, second;
        year = oldTime.get(Calendar.YEAR);
        month = oldTime.get(Calendar.MONTH);
        date = oldTime.get(Calendar.DATE);
        hour = oldTime.get(Calendar.HOUR_OF_DAY);
        minute = oldTime.get(Calendar.MINUTE);
        second = oldTime.get(Calendar.SECOND);
        final Calendar cal = new GregorianCalendar(year, month + months, date,
                hour, minute, second);
        return cal.getTime().getTime();
    }

    /**
     * 在传入时间基础上加一定月份数
     *
     * @param oldTime Date
     * @param months  int
     * @return long
     */
    public static Date addMonth(Date oldTime, final int months) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(oldTime);
        startDT.add(Calendar.MONTH, months);
        return startDT.getTime();
    }

    /**
     * 在传入时间基础上加一定天数
     *
     * @param oldTime long
     * @param day     int
     * @return long
     */
    public static long addDay(final long oldTime, final int day) {

        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(oldTime);

        return addDay(c, day);
    }

    /**
     * 在传入时间基础上加一定天数
     *
     * @param date Date
     * @param day  int
     * @return long
     */
    public static Date addDay(Date date, int day) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, day);
        return startDT.getTime();
    }

    /**
     * 在传入时间基础上加一定天数
     *
     * @param oldTime Calendar
     * @param day     int
     * @return long
     */
    public static long addDay(final Calendar oldTime, final int day) {
        int year, month, date, hour, minute, second;
        year = oldTime.get(Calendar.YEAR);
        month = oldTime.get(Calendar.MONTH);
        date = oldTime.get(Calendar.DATE);
        hour = oldTime.get(Calendar.HOUR_OF_DAY);
        minute = oldTime.get(Calendar.MINUTE);
        second = oldTime.get(Calendar.SECOND);
        final Calendar cal = new GregorianCalendar(year, month, date + day,
                hour, minute, second);
        return cal.getTime().getTime();
    }

    /**
     * 在传入时间基础上加一定月份数
     *
     * @param oldTime long
     * @param months  int
     * @return long
     */
    public static long addMonth(final long oldTime, final int months) {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(oldTime);
        return addMonth(c, months);
    }


    /**
     * 获取当天零时时间戳
     *
     * @return
     */
    public static long getCurDayStarttime() {
        return strToDate(getCuryyyy_MM_dd() + " 00:00:00").getTime();
    }


    /**
     * 在传入时间基础上加一定年数
     *
     * @param oldTime Calendar
     * @param years   int
     * @return long
     */
    public static long addYear(final Calendar oldTime, final int years) {
        int year, month, date, hour, minute, second;
        year = oldTime.get(Calendar.YEAR);
        month = oldTime.get(Calendar.MONTH);
        date = oldTime.get(Calendar.DATE);
        hour = oldTime.get(Calendar.HOUR_OF_DAY);
        minute = oldTime.get(Calendar.MINUTE);
        second = oldTime.get(Calendar.SECOND);
        final Calendar cal = new GregorianCalendar(year + years, month, date,
                hour, minute, second);
        return cal.getTime().getTime();
    }

    /**
     * 在传入时间基础上加一定年数
     *
     * @param oldTime long
     * @param day     int
     * @return long
     */
    public static long addYear(final long oldTime, final int day) {

        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(oldTime);

        return addYear(c, day);
    }
    
}
