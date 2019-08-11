package com.kwok.util.commons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date: 2019年8月8日
 * @author guohao
 */
public class HttpClientUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
			.setConnectTimeout(30000)
			.setSocketTimeout(50000)
			.build();
	
	/**
	  * 发送 GET 请求
	  * @date 2019年8月8日 下午8:25:25
	  * @author Kwok
	  **/
	public static String getRequest(String url) throws Exception{
		
		String _request_id = UUID.randomUUID().toString();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpRequest = new HttpGet(url);
		httpRequest.setConfig(REQUEST_CONFIG);
		String entityStr = null;
		try {
			logger.info(">>> 发送 HTTP GET 请求：请求id：{}，请求路径：{}", _request_id, url);
			CloseableHttpResponse response = httpclient.execute(httpRequest);
			logger.info(">>> 发送 HTTP GET 请求：请求id：{}，返回状态信息：{}", _request_id, response);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				entityStr = EntityUtils.toString(entity);
			}
			//logger.info(">>> 发送 HTTP GET 请求：请求id：{}，返回值：{}", _request_id, entityStr);
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
				logger.info(">>> 发送 HTTP GET 请求失败，请求id：{}，返回状态码：{}，异常：{}",  _request_id, response.getStatusLine().getStatusCode(), entityStr);
				throw new Exception("错误码：" + response.getStatusLine().getStatusCode() + "，错误信息：" + entityStr);
			}
			response.close();
		}catch (Exception e) {
			logger.info(">>> 发送 HTTP GET 请求失败，请求id：{}，异常：{}", _request_id, e.getMessage());
			throw new Exception("发送 HTTP GET 请求失败，异常：" + e.getMessage());
		}
		return entityStr;
	}
	
	/**
	  * 发送 POST 请求
	  * @date 2019年8月8日 下午8:25:25
	  * @author Kwok
	  **/
	public static String postRequest(String url, Map<String, Object> KeyValueParam) throws Exception{
		
		String _request_id = UUID.randomUUID().toString();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(KeyValueParam !=null && !KeyValueParam.isEmpty()){
			for (Entry<String, Object> entry: KeyValueParam.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
		}
		
		UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
		
		HttpPost httpRequest = new HttpPost(url);
		httpRequest.setEntity(paramEntity);
		httpRequest.setConfig(REQUEST_CONFIG);
		String entityStr = null;
		try {
			logger.info(">>> 发送 HTTP POST 请求：请求id：{}，请求路径：{}，请求参数：{}", _request_id, url, nvps);
			CloseableHttpResponse response = httpclient.execute(httpRequest);
			logger.info(">>> 发送 HTTP POST 请求：请求id：{}，返回状态信息：{}", _request_id, response);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				entityStr = EntityUtils.toString(entity);
			}
			//logger.info(">>> 发送 HTTP POST 请求：请求id：{}，返回值：{}", _request_id, entityStr);
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
				logger.info(">>> 发送 HTTP POST 请求失败，请求id：{}，返回状态码：{}，异常：{}", _request_id, response.getStatusLine().getStatusCode(), entityStr);
				throw new Exception("错误码：" + response.getStatusLine().getStatusCode() + "，错误信息：" + entityStr);
			}
			response.close();
		}catch (Exception e) {
			logger.info(">>> 发送 HTTP POST 请求失败，请求id：{}，异常：{}", _request_id, e.getMessage());
			throw new Exception("发送 HTTP POST 请求失败，异常：" + e.getMessage());
		}
		return entityStr;
	}
	
	/**
	  * 发送 HEAD 请求
	  * @param innerUsed Boolean 类型，是否内部使用该方法，当请求返回状态 非200 时，会影响日志输出级别。
	  * @date 2019年8月8日 下午8:25:25
	  * @author Kwok
	  **/
	private static Long _headRequest(String url, boolean innerUsed){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpHead httpRequest = new HttpHead(url);
		httpRequest.setConfig(REQUEST_CONFIG);
		long startTime = System.currentTimeMillis();
		
		try {
			CloseableHttpResponse response = httpclient.execute(httpRequest);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				return System.currentTimeMillis() - startTime;
			}else{
				if(innerUsed){
					logger.debug(">>> 发送 HTTP HEAD 请求失败，地址：{}，状态码：{}", url, response.getStatusLine().getStatusCode());
				}else{
					logger.error(">>> 发送 HTTP HEAD 请求失败，地址：{}，状态码：{}", url, response.getStatusLine().getStatusCode());
				}
				return -1L;
			}
		} catch (IOException e) {
			if(innerUsed){
				logger.debug(">>> 发送 HTTP HEAD 请求失败，地址：{}，异常：{}", url, e.getMessage());
			}else{
				logger.error(">>> 发送 HTTP HEAD 请求失败，地址：{}，异常：{}", url, e.getMessage());
			}
			return -1L;
		}
	}
	
	
	/**
	  * 发送 HEAD 请求
	  * 默认 _headRequest(String url, boolean innerUsed)方法的 innerUsed 参数为 true。
	  * @date 2019年8月8日 下午8:25:25
	  * @author Kwok
	  **/
	private static Long _headRequest(String url){
		return _headRequest(url, true);
	}
	
	
	/**
	  * 发送 HEAD 请求
	  * 默认 _headRequest(String url, boolean innerUsed)方法的 innerUsed 参数为 false。
	  * @date 2019年8月8日 下午8:25:25
	  * @author Kwok
	  **/
	public static Long headRequest(String url){
		return _headRequest(url, false);
	}
	
	
	/**
	  * 从给定的 请求URL 中选择最快的 URL
	  * @date 2019年8月8日 下午8:25:25
	  * @author Kwok
	  **/
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
	
	
	/**
	  * 从给定的 请求URL 中选择能用的 URL
	  * @date 2019年8月8日 下午8:25:25
	  * @author Kwok
	  **/
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
