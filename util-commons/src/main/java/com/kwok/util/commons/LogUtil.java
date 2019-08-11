package com.kwok.util.commons;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class LogUtil {
	
	private static Properties systemProperties = SystemProperties.getSystemProperties();
	
	public static void log(String logStr){
		
		String logdir = systemProperties.getProperty("logdir");
		
		File file = new File(logdir + DateUtil.dateToStr(new Date(), DateUtil.yyyy_MM_dd) + ".txt");
		
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(file, true);
			fw.write(logStr);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
