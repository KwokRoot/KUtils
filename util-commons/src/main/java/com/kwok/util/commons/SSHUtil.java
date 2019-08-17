package com.kwok.util.commons;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.security.Security;
import java.util.Base64;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.kwok.util.commons.CommonsResult.ResultCode;

public class SSHUtil {

	private final static Logger logger = LoggerFactory.getLogger(SSHUtil.class);

	public static String host; 
	public static int port; 
	public static String user;
	
	/**
	 * 配置文件中登录密码使用 Base64 编码
	 */
	public static String password;
	
	static {
		Properties systemProperties = SystemProperties.getSystemProperties();
		host = systemProperties.getProperty("ssh.host", "127.0.0.1");
		port = Integer.valueOf(systemProperties.getProperty("ssh.port", "22"));
		user = systemProperties.getProperty("ssh.user", "root");
		password = new String(Base64.getDecoder().decode(systemProperties.getProperty("ssh.password", "")));
	}
	

	/**
	  * 
	  * @Description 获取一个SSH Session 对象
	  * @date 2019年8月8日 下午8:40:58
	  * @author Kwok
	  **/
	public static Session getSSHSession(String user, String host, int port, String password) throws Exception {
		
		/*
		 * 出现 Session.connect: java.security.NoSuchAlgorithmException: Algorithm DH not available 异常
		 * 
		 * pom 中加入：
		 * <dependency>
				<groupId>org.bouncycastle</groupId>
				<artifactId>bcprov-jdk15on</artifactId>
				<version>1.62</version>
		   </dependency>
		 */
		//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());  
		
		JSch jsch = new JSch();
		Session session = null;
		try {
			// 根据用户名、主机ip、端口号获取一个Session对象
			session = jsch.getSession(user, host, port);
			// 设置密码
			session.setPassword(password);
			// 为Session对象设置properties
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			// 设置SSH连接超时时间 ms
			session.setTimeout(6000);
			// 建立连接
			session.connect();
		} catch (JSchException e) {
			logger.error("连接错误，请检查 用户名:{}，主机:{}，端口:{}，密码:{} 是否有误，异常：{}", user, host, port, password, e.getMessage());
			e.printStackTrace();
			session.disconnect();
			throw new Exception("连接错误，请检查用户名、主机、端口、密码是否有误，异常：" + e.getMessage());
		}
		
		return session;
	}

	/**
	  * 获取一个配置文件配置的默认 SSH Session 对象
	  * @date 2019年8月8日 下午8:58:01
	  * @author Kwok
	  **/
	public static Session getDefaultSSHSession() throws Exception{
		return getSSHSession(user, host, port, password);
	}
	
	public static CommonsResult<String> execSSHCommand(String command, String resultIncludeStr, int outTimeMS) throws Exception {
		Session session = getSSHSession(user, host, port, password);
		CommonsResult<String> result = execSSHCommand(session, command, resultIncludeStr, outTimeMS);
		session.disconnect();
		return result;
	}
	
	
	public static CommonsResult<String> execSSHCommand(Session session, String command, String resultIncludeStr, int outTimeMS) {
		
		CommonsResult<String> result = CommonsResult.CreateInstance();
		StringBuilder sb = new StringBuilder();
		try {
			ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
			channelExec.setCommand(command);
			
			ByteArrayOutputStream errOS = new ByteArrayOutputStream();
			channelExec.setErrStream(errOS);// 通道连接错误信息提示
			
			channelExec.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
			String msg;
			long startTime = System.currentTimeMillis();
			while ((msg = in.readLine()) != null ) {
				
				if(System.currentTimeMillis() - startTime > outTimeMS){
					logger.error("执行超时，outTimeMS 值为：" + outTimeMS + " ms");
					in.close();
					channelExec.disconnect();
					result.setData(sb.toString());
					result.setStatus(ResultCode.err);
					result.setMessage("匹配 '" + resultIncludeStr + "' 失败，异常：执行超时，outTimeMS 值为：" + outTimeMS + " ms");
					return result;
				}
				
				logger.info("执行命令 {} 返回行信息：{}", command, msg);
				sb.append(msg + "@#$"); // 换行符：@#$
				
				if(msg.toLowerCase().contains(resultIncludeStr.toLowerCase())){
					in.close();
					channelExec.disconnect();
					result.setData(sb.toString());
					result.setStatus(ResultCode.ok);
					result.setMessage("匹配 '" + resultIncludeStr + "' 成功");
					return result;
				}
			}
			
			String errmsg = new String(errOS.toByteArray());
			sb.append(errmsg);
			in.close();
			channelExec.disconnect();
			
			logger.info("执行命令 {}，结果：{}", command, sb.toString());
			result.setData(sb.toString());
			result.setStatus(ResultCode.err);
			result.setMessage("匹配 '" + resultIncludeStr + "' 失败");
		
		} catch (Exception e) {
			logger.error("执行命令 {} 出现错误，异常：{}", command, e.getMessage());
			result.setData(sb.toString());
			result.setStatus(ResultCode.err);
			result.setMessage("匹配 '" + resultIncludeStr + "' 失败，执行命令 " + command + " 出现错误，异常：" + e.getMessage());
			result.setExtraMessage(CommonsResult.exceptionStackTrace2String(e));
		}
		
		return result;
	}

	
	public static void main(String[] args) {
		
		//使用 Base64 编码登录密码
		System.out.println(Base64.getEncoder().encodeToString("root".getBytes()));
		
		System.out.println("--------- SSH 配置信息 ---------");
		System.out.println(host);
		System.out.println(port);
		System.out.println(user);
		System.out.println(password);
		System.out.println("--------- SSH 配置信息 END ---------");
		
		try {
			Session session = getSSHSession("root", "192.168.3.91", 22, "root");
			//Session session = getDefaultSSHSession();
			System.out.println(JSON.toJSONString(execSSHCommand(session, "ping baidu.com", "hello", 6000), true));
			session.disconnect();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	
}
