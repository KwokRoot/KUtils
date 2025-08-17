package com.kwok.util.commons;


import cn.hutool.http.HttpConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * 说明：HttpClientUtil 依赖于 hutool-http 模块
 *
 * @Author: Kwok
 * @Date: 2025/7/29
 */
public class HttpClientUtil2 {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil2.class);

	private static final HttpConfig httpConfig = HttpConfig.create()
			.setMaxRedirectCount(3)
			.setConnectionTimeout(3000)
			.setReadTimeout(60000);


	public static String getRequest(String url) throws Exception {
		return getRequest(url, null);
	}

	public static String getRequest(String url, String _request_id) throws Exception{

		if (_request_id == null || _request_id.trim().isEmpty()){
			_request_id = UUID.randomUUID().toString();
		}

		HttpRequest request = HttpUtil.createRequest(Method.GET, url);
		request.setConfig(httpConfig);
		String entityStr = null;
		long startTime = System.currentTimeMillis();
		try {
			logger.info(">>> 发送 HTTP GET 请求：请求id：{}，请求路径：{}", _request_id, url);
			HttpResponse response = request.execute();
			long spendTime = System.currentTimeMillis() - startTime;
			logger.info(">>> 发送 HTTP GET 请求：请求id：{}，返回状态信息：{}，耗时：{} ms", _request_id, response, spendTime);
			entityStr = response.body();
			if (!response.isOk()) {
				throw new Exception("错误码：" + response.getStatus() + "，错误信息：" + entityStr);
			}
			response.close();
		}catch (Exception e) {
			long spendTime = System.currentTimeMillis() - startTime;
			logger.info(">>> 发送 HTTP GET 请求失败，请求id：{}，异常：{}，耗时：{} ms", _request_id, e.getMessage(), spendTime);
			throw new Exception("发送 HTTP GET 请求失败，异常：" + e.getMessage());
		}

		return entityStr;
	}


	public static String postRequest(String url, Map<String, Object> KeyValueParam) throws Exception{
		return postRequest(url, KeyValueParam, null);
	}

	public static String postRequest(String url, Map<String, Object> KeyValueParam, String _request_id) throws Exception{

		if (_request_id == null || _request_id.trim().isEmpty()){
			_request_id = UUID.randomUUID().toString();
		}

		HttpRequest request = HttpUtil.createRequest(Method.POST, url);
		request.setConfig(httpConfig);
		request.form(KeyValueParam);
		String entityStr = null;

		long startTime = System.currentTimeMillis();
		try {
			logger.info(">>> 发送 HTTP POST 请求：请求id：{}，请求路径：{}，请求参数：{}", _request_id, url, KeyValueParam);
			HttpResponse response = request.execute();
			long spendTime = System.currentTimeMillis() - startTime;
			logger.info(">>> 发送 HTTP POST 请求：请求id：{}，返回状态信息：{}，耗时：{} ms", _request_id, response, spendTime);
			entityStr = response.body();
			if(!response.isOk()){
				throw new Exception("错误码：" + response.getStatus() + "，错误信息：" + entityStr);
			}
			response.close();
		}catch (Exception e) {
			long spendTime = System.currentTimeMillis() - startTime;
			logger.info(">>> 发送 HTTP POST 请求失败，请求id：{}，异常：{}，耗时：{} ms", _request_id, e.getMessage(), spendTime);
			throw new Exception("发送 HTTP POST 请求失败，异常：" + e.getMessage());
		}

		return entityStr;
	}
	
	public static Long _headRequest(String url, boolean innerUsed){
		HttpRequest request = HttpUtil.createRequest(Method.HEAD, url);
		request.setConfig(httpConfig);

		long startTime = System.currentTimeMillis();
		try {
			HttpResponse response = request.execute();
			if(response.isOk()){
				return System.currentTimeMillis() - startTime;
			}else{
				if(innerUsed){
					logger.debug(">>> 发送 HTTP HEAD 请求失败，地址：{}，状态码：{}", url, response.getStatus());
				}else{
					logger.error(">>> 发送 HTTP HEAD 请求失败，地址：{}，状态码：{}", url, response.getStatus());
				}
				return -1L;
			}
		} catch (Exception e) {
			if(innerUsed){
				logger.debug(">>> 发送 HTTP HEAD 请求失败，地址：{}，异常：{}", url, e.getMessage());
			}else{
				logger.error(">>> 发送 HTTP HEAD 请求失败，地址：{}，异常：{}", url, e.getMessage());
			}
			return -1L;
		}

	}
	
	public static Long _headRequest(String url){
		return _headRequest(url, true);
	}
	
	public static Long headRequest(String url){
		return _headRequest(url, false);
	}
	
	public static String selectFastURL(String... urls){
		
		Map<String, Long> map = new LinkedHashMap<>();
		for (int i = 0, len = urls.length; i < len; i++) {
			if(!map.containsKey(urls[i])){
				map.put(urls[i], _headRequest(urls[i]));
			}
		}
		
		String returnUrl = null;
		long min = 5000;
		StringBuffer sb = new StringBuffer();
		for (Entry<String, Long> entry : map.entrySet()) {
			sb.append("[路径：" + entry.getKey() + " 耗时：" + entry.getValue() + " ms]");
			if(entry.getValue() != -1L && entry.getValue() < min){
				returnUrl = entry.getKey();
				min = entry.getValue();
			}
		}
		sb.append("[优选路径：" + returnUrl + "]");
		logger.debug(">>> {}", sb.toString());
		return returnUrl;
	}
	
	public static String selectEnableURL(String... urls){
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0, len = urls.length; i < len; i++) {
			if(_headRequest(urls[i]) != -1L){
				sb.append("[路径：" + urls[i] + " ok]");
				logger.debug(">>> {}", sb.toString());
				return urls[i];
			}else{
				sb.append("[路径：" + urls[i] + " fail]");
			}
		}
		logger.debug(">>> {}", sb.toString());
		return null;
	}
	
	
	public static void main(String[] args) throws Exception {
		
		System.out.println(getRequest("http://www.baidu.com"));
		
		System.out.println(selectFastURL("http://baidu.com", "http://www.sina.cn" ,"http://www.163.com"));

		System.out.println(selectEnableURL("http://baidu.com", "http://www.sina.cn" ,"http://www.163.com"));
		
	}
	
}
