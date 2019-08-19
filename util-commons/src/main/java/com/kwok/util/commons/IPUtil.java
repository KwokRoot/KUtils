package com.kwok.util.commons;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;
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

	
	/*
	 * 10进制长整型数值 转为 IP 地址。
	 */
	public static String tenRadixLongConvertIP(final Long srcVal) throws Exception{
    	
		ArrayList<String> xRadixValArray = new ArrayList<String>();
		
		xRadixValArray = tenRadixLongConvertXRadixArray(srcVal, 256);
    	
		while (xRadixValArray.size() < 4){
    		xRadixValArray.add(0, 0 + "");
    	}
    	
		return xRadixValArray.stream().collect(Collectors.joining("."));
    }
    
	
	/*
	 * 10 进制长整型数值 转为 X 进制数组
	 */
    private static ArrayList<String> tenRadixLongConvertXRadixArray(final Long srcVal, final long radix) throws Exception{
    	
    	if(radix <= 1){
    		throw new Exception("参数 radix 有误，radix > 1");
    	}
    	
    	ArrayList<String> xRadixValList = new ArrayList<String>();
    	long temp = srcVal;
    	xRadixValList.add(String.valueOf(temp % radix));
    	temp = temp / radix;
    	
    	while (temp > 0) {
    		xRadixValList.add(0, String.valueOf(temp % radix));
    		temp = temp / radix;
		}
    	
		return xRadixValList;
    }
	
	public static void main(String[] args) throws Exception {

		String ipStr = "61.87.255.255";
		
		try{
			
			System.out.println(IPFrom256RadixConvert10RadixLong(ipStr));
			
			System.out.println(tenRadixLongConvertIP(IPFrom256RadixConvert10RadixLong(ipStr)));
			
			/*
			 * 测试 10 进制长整型数值 转为 X 进制数组
			 */
			System.out.println(new BigInteger("987654321").toString(2));
			System.out.println(tenRadixLongConvertXRadixArray(Long.valueOf("987654321"), 2).stream().collect(Collectors.joining()));
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}

}

