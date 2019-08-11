package com.kwok.util.commons;

import javax.servlet.http.HttpServletRequest;

/**
 * 判断请求是否为 Ajax 请求。
 * @author Kwok
 */
public class WebUtil {

	public static boolean isAjaxRequest(HttpServletRequest request) {
    	String requestType = request.getHeader("X-Requested-With"); 
    	if(requestType == null){
    		return false;
    	}
		return true;
	}
	
}
