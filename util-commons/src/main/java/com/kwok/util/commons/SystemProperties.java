package com.kwok.util.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 加载系统路径 或 类加载路径下的 'system.properties' 配置文件。
 * 注：系统路径下不存在 'system.properties' 配置文件时，再在类路径下加载。
 * @author Kwok
 */
public class SystemProperties {

	private static  Logger logger= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static Properties properties;
	
	private static void init() {
		
		properties = new Properties();
		InputStream io = null;
		String path = null;
		
		try {
			path = System.getProperty("user.dir") + File.separator + "system.properties";
			io = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			logger.log(Level.INFO ,">>> " + System.getProperty("user.dir") + " 目录下未发现 'system.properties' 配置文件");
		}

		if (io == null) {
			io = SystemProperties.class.getClassLoader().getResourceAsStream("system.properties");
		}else{
			logger.log(Level.INFO ,">>> " + System.getProperty("user.dir") + " 目录下发现 'system.properties' 配置文件");
		}

		try {
			properties.load(io);
			logger.log(Level.INFO ,">>> " + SystemProperties.class.getClassLoader().getResource("") + " 目录下发现 'system.properties' 配置文件");
		} catch (Exception e) {
			logger.log(Level.SEVERE ,">>> " + SystemProperties.class.getClassLoader().getResource("") + " 目录下未发现 'system.properties' 配置文件");
		}

	}
	
	public static Properties getSystemProperties(){
		if(properties == null){
			init();
		}
		return properties;
	}
	
	public static String getSystemProperties(String key){
		return getSystemProperties().getProperty(key);
	}
	
	public static String getSystemProperties(String key, String defaultValue){
		return getSystemProperties().getProperty(key, defaultValue);
	}
	
	public static void main(String[] args) {
		System.out.println(getSystemProperties("system.name"));
	}
	
}
