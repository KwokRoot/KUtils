package com.kwok.util.commons;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateUtilTest {
	
	public static void main(String[] args) {
		
		//new DateUtilTest().DateUtilThreadSafeTest();
		
	}
	
	/**
	  * 多线程安全性测试
	  * 通过使用 ThreadLocal<SimpleDateFormat> 保证 SimpleDateFormat 线程安全。
	  * @date 2019年8月8日 下午5:46:27
	  * @author Kwok
	  **/
	public void DateUtilThreadSafeTest(){
		
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					String dateStr = DateUtil.getCurDateTime();
					System.out.println(DateUtil.strToDate(dateStr));
				}
			}).start();
		}
		
	}
	
	
	@Test
	public void getWeekYearTest(){
		
		// 例：2018-12-30 在日历中是 2019 年的第 1 周
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 11, 30);
		Date date = calendar.getTime();
		System.out.println(DateUtil.dateToStr(date));
		
		//获取给定日期是一年中的第几周
		System.out.println(DateUtil.getWeek(date)); //结果：1 
		
		//获取给定日期是按周计算的哪一年 
		System.out.println(calendar.getWeekYear()); //结果：2019
		
		//获取给定日期是哪一年
		System.out.println(calendar.get(Calendar.YEAR)); //结果：2018
	}
	
	
	@Test
	public void addDayTest(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 11, 30);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        System.out.println(DateUtil.dateToStr(calendar.getTime())); //结果：2019-01-06 15:30:22
	}
	
}

