package com.kwok.util.commons;

import java.io.File;
import java.io.FilenameFilter;
import java.util.stream.Stream;

/*
 * 通用工具包组合，仅在IDE中提示 commons 包中有哪些工具类。
 * 添加工具类后需手动更新。
 */
@SuppressWarnings("rawtypes")
public class CommonsUtils {

	public static AES AES;
	public static CommonsResult CommonsResult;
	public static DateUtil DateUtil;
	public static ExceptionUtil ExceptionUtil;
	public static HttpClientUtil HttpClientUtil;
	public static IPUtil IPUtil;
	public static JedisFactory JedisFactory;
	public static LogUtil LogUtil;
	public static NativeCache NativeCache;
	public static NetworkUtil NetworkUtil;
	public static RandomStringUtil RandomStringUtil;
	public static ScheduleLock ScheduleLock;
	public static SimpleBeanUtil SimpleBeanUtil;
	public static SpringBootConfigFiles SpringBootConfigFiles;
	public static SpringBootConfigLoader SpringBootConfigLoader;
	public static SpringTool SpringTool;
	public static SSHUtil SSHUtil;
	public static SystemProperties SystemProperties;
	public static VerifyCodeUtil VerifyCodeUtil;
	public static WebUtil WebUtil;
	
	public static void main(String[] args) {
		
		String classPathStr = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String currnetPathStr = classPathStr.concat("com.kwok.util.commons").replace(".", "/");
		
		//System.out.println(currnetPathStr);
		
		
		File file = new File(currnetPathStr);
		File[] files = file.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if(name.contains("$") || name.contains("CommonsUtils")){
					return false;
				}
				return true;
			}
		});
		
		Stream.of(files).forEach(x -> {
			String className = x.getName().substring(0, x.getName().indexOf(".class"));
			System.out.println("public static " + className + " " + className + ";");
		});
		
	}
	
}
