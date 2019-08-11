package com.kwok.util.commons;

import com.alibaba.fastjson.JSON;
import com.kwok.util.commons.CommonsResult.ResultCode;

public class CommonsResultTest {

	public static void main(String[] args) {
		
		new CommonsResultTest().CreateInstanceTest();
		
	}
	
	
	public void CreateInstanceTest(){
		
		CommonsResult<Integer> result = CommonsResult.CreateInstance();
		
		try{
			int i = 10/0;
			result.setStatus(ResultCode.ok);
			result.setMessage("success");
			result.setData(i);
			System.out.println(JSON.toJSONString(result));
		}catch (Exception e) {
			result.setStatus(ResultCode.err);
			result.setMessage(e.getMessage());
			result.setData(-1);
			result.setExtraMessage(CommonsResult.exceptionStackTrace2String(e));
			System.out.println(JSON.toJSONString(result, true));
		}
		
		
	}
	
}
