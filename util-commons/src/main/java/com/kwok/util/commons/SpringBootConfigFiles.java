package com.kwok.util.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @date: 2019年7月8日
 * @author Kwok
 */
public class SpringBootConfigFiles {

	private static Map<String, Properties> pathStr_properties_map = new HashMap<String, Properties>();

	public static Properties getSystemProperties(){
		return getSystemProperties(null);
	}
	
	public static Properties getSystemProperties(String pathStr){
		
		if(pathStr == null || "".equals(pathStr)){
			pathStr = "system.properties";
		}
		
		if (pathStr_properties_map.get(pathStr) == null) {
			pathStr_properties_map.put(pathStr, SpringBootConfigLoader.getPropertiesResource(pathStr));
		}
		
		return pathStr_properties_map.get(pathStr);
	}
	
	
	public static void main(String[] args) {
		System.out.println(getSystemProperties("").get("logdir"));
	}
	
}
