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
    
    // 24 * 60 * 60 * 1000 = 86400000
	public static final long oneDayMs = 86400000;
    // 60 * 60 * 1000 = 3600000‬
	public static final long oneHourMs = 3600000;
    // 60 * 1000 = 60000‬
    public static final long oneMinuteMs = 60000;
    
    private static final String TIME_S_EN = "s";
    private static final String TIME_M_EN = "m";
    private static final String TIME_H_EN = "h";
    private static final String TIME_D_EN = "d";

    private static final String TIME_S_CN = "秒";
    private static final String TIME_M_CN = "分";
    private static final String TIME_H_CN = "时";
    private static final String TIME_D_CN = "天";
    
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

    private static ThreadLocal<SimpleDateFormat> yyyyMM_DateTimeFormat = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(yyyyMM);
        }
    };
    
    private static ThreadLocal<SimpleDateFormat> yyyyMMdd_DateTimeFormat = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(yyyyMMdd);
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
		} else if (formatStr.equalsIgnoreCase(yyyyMM)) {
			return yyyyMM_DateTimeFormat.get();
		} else if (formatStr.equalsIgnoreCase(yyyyMMdd)) {
			return yyyyMMdd_DateTimeFormat.get();
		} else if (formatStr.equalsIgnoreCase(yyyyMMddHHmm)) {
			return yyyyMMddHHmm_DateTimeFormat.get();
		} else if (formatStr.equalsIgnoreCase(yyyyMMddHHmmss)) {
			return yyyyMMddHHmmss_DateTimeFormat.get();
		} else if (formatStr.equalsIgnoreCase(utc_date)) {
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
    
    public static Calendar dateToCalendar(Date date){
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
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
	 * @param days     int
	 * @return long
	 */
	public static long addDay(final long oldTime, final int days) {
	
	    final Calendar c = Calendar.getInstance();
	    c.setTimeInMillis(oldTime);
	
	    return addDay(c, days);
	}

	/**
	 * 在传入时间基础上加一定天数
	 *
	 * @param date Date
	 * @param days  int
	 * @return long
	 */
	public static Date addDay(Date date, int days) {
	    Calendar startDT = Calendar.getInstance();
	    startDT.setTime(date);
	    startDT.add(Calendar.DAY_OF_MONTH, days);
	    return startDT.getTime();
	}

	/**
	 * 在传入时间基础上加一定天数
	 *
	 * @param oldTime Calendar
	 * @param days     int
	 * @return long
	 */
	public static long addDay(final Calendar oldTime, final int days) {
	    int year, month, date, hour, minute, second;
	    year = oldTime.get(Calendar.YEAR);
	    month = oldTime.get(Calendar.MONTH);
	    date = oldTime.get(Calendar.DATE);
	    hour = oldTime.get(Calendar.HOUR_OF_DAY);
	    minute = oldTime.get(Calendar.MINUTE);
	    second = oldTime.get(Calendar.SECOND);
	    final Calendar cal = new GregorianCalendar(year, month, date + days,
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
	public static long addYear(final long oldTime, final int years) {
	
	    final Calendar c = Calendar.getInstance();
	    c.setTimeInMillis(oldTime);
	
	    return addYear(c, years);
	}

	/**
     * 获取当前日期yyyy的形式
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
    
    public static String getCuryyyyMMddHHmmss() {
        return dateToStr(Calendar.getInstance().getTime(), yyyyMMddHHmmss);
    }
    
    public static long getCurMMDDHHmmss() {
        return Long.parseLong(dateToStr(Calendar.getInstance().getTime(), yyyyMMddHHmmss));
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
    
    /**
     * 得到当前日期的年和月如200509
     *
     * @return String
     */
    public static String getCurYearMonth() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        String DATE_FORMAT = "yyyyMM";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * 获得系统当前月份的天数
     * @return
     */
    public static int getCurentMonthDays() {
        Date date = Calendar.getInstance().getTime();
        return getMonthDay(date);
    }
    
    
    /**
     * 获取当天 00:00:00 时间戳
     * @return
     */
    public static long getCurDayStarttime() {
        return strToDate(getCuryyyy_MM_dd() + " 00:00:00").getTime();
    }

    /**
     * 获取当天 23:59:59 时间戳
     * @return
     */
    public static long getCurDayEndTime() {
        return strToDate(getCuryyyy_MM_dd() + " 23:59:59").getTime();
    }
    
    /**
	 * 获取当前整时时间戳
	 */
	public static long getCurHourStartTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTimeInMillis();
	}

	/**
     * 获取本周周一零时时间戳
     * @return
     */
    public static long getCurMondayStartTime() {
    	
    	Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
    
    /**
     * 获取本周周天23:59:59.999时间戳
     */
    public static long getCurSundayEndTime() {
    	Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); 
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
    
    /**
     * 上月开始时间
     */
    public static Date getLastMonthStartTime() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.MONTH, -1);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 上月结束时间
     */
    public static Date getLastMonthEndTime() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.MONTH, -1);
    	calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
    
    
    /**
	 * 获取给定日期的零时时间戳
	 *
	 * @return
	 */
	public static long getCurDayStartTime(Date curDate) {
	    return strToDate(dateToStr(curDate, yyyy_MM_dd) + " 00:00:00").getTime();
	}

	/**
	 * 获取给定日期的结束时间戳
	 *
	 * @return
	 */
	public static long getCurDayEndTime(Date curDate) {
		return strToDate(dateToStr(curDate, yyyy_MM_dd) + " 23:59:59").getTime();
	}

	/**
	 * 获取给定时间前整时时间戳
	 */
	public static long getCurHourStartTime(Date curDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTimeInMillis();
	}

	/**
	 * 获取给定时间的周一零时时间戳
	 *
	 * @return
	 */
	public static long getCurMondayStartTime(Date curDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTimeInMillis();
	    
	}

	/**
	 * 获取给定时间的周天23:59:59.999时间戳
	 * 
	 */
	public static long getCurSundayEndTime(Date curDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); 
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	    return calendar.getTimeInMillis();
	}

	/**
	 * 获取给定时间的上月开始时间
	 */
	public static Date getLastMonthStartTime(Date curDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}

	/**
	 * 获取给定时间的上月结束时间
	 */
	public static Date getLastMonthEndTime(Date curDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	    return calendar.getTime();
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
     * 获取给定日期的天
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(date);
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取给定日期的月份，1-12月
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(date);
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取给定日期的年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(date);
        return now.get(Calendar.YEAR);
    }

    /**
     * 获取给定日期的年份+月份,如200505
     *
     * @return
     */
    public static String getYearMonthOfDate(Date date) {
        return dateToStr(date, yyyyMM);
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
     * 获得指定日期月份的天数
     *
     * @return
     */
    public static int getMonthDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    public static int getWeek(Date date){
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    
    
    /**
      * 获取给定日期是按周计算的哪一年 
      * 例：2018-12-30 在日历中是 2019 年的第 1 周
      * @author Kwok
      **/
    public static int getWeekYear(Date date){
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getWeekYear();
    }

    
    /**
     * 方便的把时间换算成毫秒数
     * 
     * 支持几个单位, s(秒), m(分), h(时), d(天)
     * 
     * 比如:
     * 
     * 100s -> 100000 <br>
     * 2分 -> 120000 <br>
     * 3h -> 10800000 <br>
     * 
     * @param tstr
     *            时间字符串
     * @return 毫秒数
     */
    public static long toMillis(String tstr) {
        if (tstr == null || tstr.trim().length() == 0) {
            return 0;
        }
        tstr = tstr.toLowerCase();
        String tl = tstr.substring(0, tstr.length() - 1);
        String tu = tstr.substring(tstr.length() - 1);
        if (TIME_S_EN.equals(tu) || TIME_S_CN.equals(tu)) {
            return 1000 * Long.valueOf(tl);
        }
        if (TIME_M_EN.equals(tu) || TIME_M_CN.equals(tu)) {
            return oneMinuteMs * Long.valueOf(tl);
        }
        if (TIME_H_EN.equals(tu) || TIME_H_CN.equals(tu)) {
            return oneHourMs * Long.valueOf(tl);
        }
        if (TIME_D_EN.equals(tu) || TIME_D_CN.equals(tu)) {
            return oneDayMs * Long.valueOf(tl);
        }
        return Long.valueOf(tstr);
    }
    
}
