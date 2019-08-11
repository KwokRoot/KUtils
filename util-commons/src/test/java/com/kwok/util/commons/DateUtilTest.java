package com.kwok.util.commons;

public class DateUtilTest {
	
	public static void main(String[] args) {
		
		new DateUtilTest().DateUtilThreadSafeTest();
		
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
	
}

