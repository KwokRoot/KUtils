package com.kwok.util.commons;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date: 2019年7月8日
 * @author Kwok
 */
public class SystemProperties {

	private static Properties properties;

	private final static Logger logger = LoggerFactory.getLogger(SystemProperties.class);
	
	public static Properties getSystemProperties(){
		return getSystemProperties(null);
	}
	
	public static Properties getSystemProperties(String pathStr){
		
		if(pathStr == null || "".equals(pathStr)){
			pathStr = "system.properties";
		}
		
		if (properties == null) {
			properties = SpringBootConfigLoader.getPropertiesResource(pathStr);
			if(properties != null){
				logger.info(">>> 加载系统配置文件 '" + pathStr + "' 成功");
			}
		}
		return properties;
	}
	
	public static void main(String[] args) {
		System.out.println(getSystemProperties("").get("logdir"));
	}
	
}
