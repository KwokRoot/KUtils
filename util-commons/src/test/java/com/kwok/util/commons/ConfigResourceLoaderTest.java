package com.kwok.util.commons;

import java.io.File;

public class ConfigResourceLoaderTest {

	public static void main(String[] args) {
		
		// 获取项目根路径
		System.out.println(new File("").getAbsolutePath());
		System.out.println(System.getProperty("user.dir", "."));
		
		// 获取文件分隔符
		System.out.println(File.separator);
		
		// 注：Thread.currentThread().getContextClassLoader().getResource("") 直接 Jar 包中运行返回 null。
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
		// 解决方案：先获取当前类的包路径，ClassLoader 加载包获取包的全路径，再通过字符串截取获取加载类的根路径。
		String THIS_CLASS_PATH = ConfigResourceLoader.class.getPackage().getName().replace(".", "/");
		System.out.println(THIS_CLASS_PATH);
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(THIS_CLASS_PATH));
		
		//ConfigResourceLoader.loadConfig("file:system.properties");
		//ConfigResourceLoader.loadConfig("classpath:system.properties");
		
		ConfigResourceLoader.loadConfig("system.properties");
		
	}

}
