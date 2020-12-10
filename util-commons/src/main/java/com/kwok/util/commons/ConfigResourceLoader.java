package com.kwok.util.commons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @Description: 配置文件加载类
 * @date: 2020年12月10日
 * @author Kwok
 */
public class ConfigResourceLoader {

	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static String CONFIG_DIR = "config";
	
	private final static String FILE_SEPARATOR = System.getProperty("file.separator");
	private final static String PROJECT_DIR = System.getProperty("user.dir", ".");
	
	private final static String THIS_CLASS_PATH = ConfigResourceLoader.class.getPackage().getName().replace(".", "/");
	
	private final static String FILE_PATH_PREFIX = "file:";
	private final static String CLASS_PATH_PREFIX = "classpath:";
	/**
	 * 0:在项目、类路径下查找
	 * 1:仅项目根路径下查找
	 * 2:仅类类路径下查找
	 */
	private static int flag = 0;
	
	/**
	 * 加载配置文件：
	 * 以 `file:`开头配置文件名，只在当前项目根目录下的 config 目录 及 项目根目录中加载。 
	 * 以 `classpath:`开头配置文件名，只在当前类加载根路径下 config 目录 及 类加载根路径下加载。
	 * 直接文件名加载优先级：根目录下的 config 目录 > 项目根目录 > 类加载根路径下 config 目录 > 类加载根路径。
	 * 注：项目根目录为 Jar 包所在目录，类加载根路径为 Jar 包内根目录。
	 */
	public static InputStream loadConfig(String fileName) {
		
		if (fileName == null || Objects.equals("", fileName.trim())) return null;
		
		if(fileName.startsWith(FILE_PATH_PREFIX)){
			fileName = fileName.substring(FILE_PATH_PREFIX.length());
			flag = 1;
		}else if(fileName.startsWith(CLASS_PATH_PREFIX)){
			fileName = fileName.substring(CLASS_PATH_PREFIX.length());
			flag = 2;
		}
		
		String usedFileName = CONFIG_DIR + FILE_SEPARATOR + fileName;
		InputStream inputStream = null;
		switch (flag) {
		case 1:
			inputStream = loadConfigAtFilePath(usedFileName);
			if(inputStream != null){
				return inputStream;
			}
			inputStream = loadConfigAtFilePath(fileName);
			if(inputStream != null){
				return inputStream;
			}
			break;
		case 2:
			inputStream = loadConfigAtClassPath(usedFileName);
			if(inputStream != null){
				return inputStream;
			}
			inputStream = loadConfigAtClassPath(fileName);
			if(inputStream != null){
				return inputStream;
			}
			break;
		case 0:
			
		default:
			inputStream = loadConfigAtFilePath(usedFileName);
			if(inputStream != null){
				return inputStream;
			}
			inputStream = loadConfigAtFilePath(fileName);
			if(inputStream != null){
				return inputStream;
			}
			inputStream = loadConfigAtClassPath(usedFileName);
			if(inputStream != null){
				return inputStream;
			}
			inputStream = loadConfigAtClassPath(fileName);
			if(inputStream != null){
				return inputStream;
			}
		}
		return inputStream;
	}
	
	
	private static InputStream loadConfigAtFilePath(String fileName) {
		
		String basePath = PROJECT_DIR + FILE_SEPARATOR;
		String path = basePath + fileName;
		
		try {
			InputStream inputStream = new FileInputStream(path);
			logger.info(">>> At '" + basePath + "' dir find '" + fileName + "' file.");
			return inputStream;
		} catch (FileNotFoundException e) {
			logger.warning(">>> At '" + basePath + "' dir not find '" + fileName + "' file.");
			return null;
		}
	}
	
	
	private static InputStream loadConfigAtClassPath(String fileName) {
		
		String basePath = null;
		URL url = Thread.currentThread().getContextClassLoader().getResource("");
		if(url == null){
			basePath = Thread.currentThread().getContextClassLoader().getResource(THIS_CLASS_PATH).getPath();
			basePath = basePath.substring(0, basePath.length() - THIS_CLASS_PATH.length());
		}else{
			basePath = url.getPath();
		}
		
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		if (inputStream != null) {
			logger.info(">>> At '" + basePath + "' dir find '" + fileName + "' file.");
		}else{
			logger.warning(">>> At '" + basePath + "' dir not find '" + fileName + "' file.");
		}
		
		return inputStream;
	}

}
