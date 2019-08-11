package com.kwok.util.commons;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Description: 返回结果的封装类
 * @date: 2018年12月25日
 * @author Kwok
 */
public class CommonsResult<T> {

	public enum ResultCode {
		ok(0), err(-1), info(1);

		public int code;
		
		private ResultCode(int code) {
			this.code = code;
		}
	}
	
	private int status;
	private String message;
	private String extraMessage;
	private T data;
	
	protected CommonsResult(){}
	
	public static <T> CommonsResult<T> CreateInstance(){
		return new CommonsResult<T>();
	}
	
	public void setStatus(ResultCode resultCode){
		this.status = resultCode.code; 
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExtraMessage() {
		return extraMessage;
	}
	
	public void setExtraMessage(String extraMessage) {
		this.extraMessage = extraMessage;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	/**
	  * 转换异常时的堆栈信息为字符串
	  * @date 2019年8月8日 下午5:55:17
	  * @author Kwok
	  **/
	public static String exceptionStackTrace2String(Exception e) {
		//注：Exception 类是 Throwable 类的子类
		StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.close();
        return sw.toString();
	}
	
}
