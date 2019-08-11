package com.kwok.util.commons;

import java.util.Base64;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AESTest {

	private static String key;
	
	// 密码
	private static String pwd = "abc@123";
	
	private static String miContent;
	
	@Before
	public void generateKeyBefore(){
		if(key == null){
			System.out.println("--------- generateKeyTest ---------");
			
			// 生成AES密钥
			key = AES.generateKeyString();
			System.out.println("AES-KEY:" + key);
			
			System.out.println("--------- generateKeyTest END ---------\r\n");
		}
	}
	
	@Test
	public void byteAndByteParamTest(){
		System.out.println("--------- byteAndByteParamTest ---------");
		
		// 用AES加密内容
		byte[] miContentBytes = AES.encrypt(pwd.getBytes(), key.getBytes());
		System.out.println("AES加密后的内容:" + Base64.getEncoder().encodeToString(miContentBytes));
		System.out.println("AES解密后的内容:" + new String(AES.decrypt(miContentBytes, key.getBytes())));
		
		Assert.assertArrayEquals("byteAndByteParamTest", pwd.getBytes(), AES.decrypt(miContentBytes, key.getBytes()));
		
		System.out.println("--------- byteAndByteParamTest END ---------\r\n");
	}
	
	@Test
	public void StringAndByteParamTest(){
		System.out.println("--------- StringAndByteParamTest ---------");
		
		miContent = AES.encrypt(pwd, key.getBytes());
		System.out.println("AES加密后的内容:" + miContent);
		System.out.println("AES解密后的内容:" + AES.decrypt(miContent, key.getBytes()));
	
		Assert.assertEquals("StringAndByteParamTest", pwd, AES.decrypt(miContent, key.getBytes()));
		
		System.out.println("--------- StringAndByteParamTest END ---------\r\n");
	}

	@Test
	public void StringAndStringParamTest(){
		System.out.println("--------- StringAndStringParamTest ---------");
		
		// 用AES加密内容
		miContent = AES.encrypt(pwd, key);
		System.out.println("AES加密后的内容:" + miContent);
		System.out.println("AES解密后的内容:" + AES.decrypt(miContent, key));
		
		Assert.assertEquals("StringAndStringParamTest",  pwd, AES.decrypt(miContent, key));
		
		System.out.println("--------- StringAndStringParamTest END ---------\r\n");
	}
	
	@Test
	public void byte2hexAndhex2byteTest(){
		System.out.println("--------- byte2hexAndhex2byteTest ---------");
		
		miContent = AES.byte2hex(pwd.getBytes());
		System.out.println("AES加密后的内容:" + miContent);
		System.out.println("AES解密后的内容:" + new String(AES.hex2byte(miContent)));
		
		Assert.assertEquals("byte2hexAndhex2byteTest", pwd, new String(AES.hex2byte(miContent)));
		
		System.out.println("--------- byte2hexAndhex2byteTest END ---------\r\n");
	}
	
}
