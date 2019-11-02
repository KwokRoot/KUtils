package com.kwok.util.commons;

import java.io.PrintWriter;
import java.io.StringWriter;

/*
 * 该类为异常处理类。
 * 也可使用 org.apache.commons.lang3.exception.ExceptionUtils。
 */
public class ExceptionUtil {

	/**
	 * @Description: 把异常的堆栈信息转为字符串
	 * @date: 2019年10月30日
	 * @author Kwok
	 */
	public static String exceptionStackTrace2String(final Throwable t) {
		//注：Exception 类是 Throwable 类的子类
		StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.close();
        return sw.toString();
	}
	
}
