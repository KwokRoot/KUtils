package com.kwok.util.commons;

import java.text.DecimalFormat;
import java.util.stream.Stream;

/**
 * IP 工具类。
 * @author Kwok
 */
public class IPUtil {

	/*
	 * IP 地址作为 4位256进制数值 转为 10进制长整型数值。
	 */
	public static Long IPFrom256RadixConvert10RadixLong(String ipStr) throws Exception {

		String[] ipStrArray = ipStr.split("\\.");
		
		if (ipStrArray.length != 4) {
			throw new Exception("IP 格式有误，传入 IP 参数：'" + ipStr + "'");
		}
		
		for (int i = 0; i < ipStrArray.length; i++) {
			try{
				if(Integer.valueOf(ipStrArray[i]) > 255){
					throw new Exception("IP 某区段值大于 255");
				}
			}catch (Exception e) {
				throw new Exception("IP 格式有误，传入 IP 参数：'" + ipStr + "'，异常：" + e.getMessage());
			}
		}
		
		int[] ipNumArray = Stream.of(ipStrArray).mapToInt(Integer::valueOf).toArray();;
		
		DecimalFormat df = new DecimalFormat("0");
		return Long.valueOf(
				df.format(Math.pow(256, 3) * ipNumArray[0] 
				+ Math.pow(256, 2) * ipNumArray[1]
				+ Math.pow(256, 1) * ipNumArray[2] 
				+ ipNumArray[3]));
	}

	public static void main(String[] args) throws Exception {

		String ipStr = "61.087.255.255";
		try{
			System.out.println(IPFrom256RadixConvert10RadixLong(ipStr));
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}

}

