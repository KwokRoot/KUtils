package com.kwok.util.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Description: SpringBoot 加载自定义的配置文件
 * 1.默认从 jar 包所在根路径、classpath 路径下加载配置文件
 * 2.jar 包所在根路径 优先级 高于 classpath 路径
 * 3.注：配置文件只加载一次，没有实现互补式加载！
 * @date: 2019年7月8日
 * @author Kwok
 */
public class SpringBootConfigLoader {

	private final static Logger logger = LoggerFactory.getLogger(SpringBootConfigLoader.class);
	
	public static InputStream getFileResourceAsStream(String fileName){
		
		if(StringUtils.isBlank(fileName)) return null;
		
		boolean isClassPath = false;
		if(fileName.toLowerCase().startsWith("classpath:")){
			isClassPath = true;
			fileName = fileName.substring(10);
		}
		InputStream io = null;
		String filePath = null;
		File file = new File(fileName);
		
		if (!isClassPath && file.exists()) {
			logger.info(">>> " + getJarPath() + " 目录下发现 " + fileName + " 文件");
			filePath = file.getAbsolutePath();
			try {
				io = new FileInputStream(filePath);
			} catch (FileNotFoundException e) {
				logger.error(">>> 没有发现文件" + filePath);
			}
		}else{
			if(!isClassPath){
				logger.info(">>> " + getJarPath() + " 目录下没有发现 " + fileName + " 文件");
			}
			URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
			if(url != null){
				logger.info(">>> " + Thread.currentThread().getContextClassLoader().getResource("").getPath() + " 目录下发现 " + fileName + " 文件");
				filePath = url.getPath();
				io = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			}else{
				logger.info(">>> " + Thread.currentThread().getContextClassLoader().getResource("").getPath() + " 目录下没有发现 " + fileName + " 文件");
			}
		}
		return io;
	}
	
	public static String getFileResourceAsString(String fileName){
		InputStream io = getFileResourceAsStream(fileName);
		if (io != null) {
			try {
				return IOUtils.toString(io, Charset.defaultCharset());
			} catch (IOException e) {
				logger.error(">>> IO 流转字符串失败，异常：" + e.getMessage());
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
	
	public static Properties getPropertiesResource(String fileName){
		
		Properties properties = new Properties();
		InputStream io = getFileResourceAsStream(fileName);
		if(io!=null){
			try {
				properties.load(io);
				logger.info(">>> 加载 " + fileName + " 配置文件成功");
				logger.info(">>> " + fileName + " 配置项：\r\n" + JSON.toJSONString(properties, SerializerFeature.PrettyFormat, SerializerFeature.MapSortField));
			} catch (IOException e) {
				logger.error(">>> 加载 " + fileName + " 配置文件失败！");
			}
			try {
				io.close();
			} catch (IOException e) {}
		}else{
			logger.error(">>> 没有发现 " + fileName + " 配置文件！");
		} 
		
		return properties;
	}

	public static String getJarPath(){
		return new File("").getAbsolutePath() + File.separator;
	}
	
	public static String getClassPath(){
		return Thread.currentThread().getContextClassLoader().getResource("").getPath();
	}
	
	public static void main(String[] args) {
		
		//System.out.println(getJarPath());
		//System.out.println(getClassPath());
		System.out.println(getFileResourceAsString("classpath:system.properties"));
		//System.out.println(getPropertiesResource("system.properties"));
	}
	
}
