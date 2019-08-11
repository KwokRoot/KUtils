package com.kwok.util.commons;

public class LogUtilTest {

public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		for (int i = 1; i <= 3000; i++) {
			LogUtil.log("" + i + "\r\n");
		}
		
		//并发测试
		for (int i = 1; i <= 100; i++) {
			int k = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 1; j <= 100; j++) {
						LogUtil.log("" + k + k + k + "\r\n");
					}
				}
			}).start();
		}
		
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
	
}
