package com.kwok.util.commons;

import java.io.File;

public class VerifyCodeUtilTest {

	public static void main(String[] args) {
		
		VerifyCodeUtilTest verifyCodeUtilTest = new VerifyCodeUtilTest();
		
		verifyCodeUtilTest.generateVerifyCodeTest();
		
		//生成验证码文件在 'D://opt/work/image' 目录中
		//verifyCodeUtilTest.outputImageTest();
		
    }
	
	
	/**
	  * 生成验证码字符串测试
	  * @date 2019年8月8日 下午6:22:45
	  * @author Kwok
	  **/
	public void generateVerifyCodeTest(){
		for(int i = 4; i < 9; i++){
            System.out.println(VerifyCodeUtil.generateVerifyCode(i));
        }
	}
	
	
	/**
	  * 生成验证码文件测试 
	  * @date 2019年8月8日 下午6:23:31
	  * @author Kwok
	  **/
	public void outputImageTest(){
		File dir = new File("D://opt/work/image");
        int w = 200, h = 80;
        for(int i = 0; i < 10; i++){
            String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
            File file = new File(dir, verifyCode + ".jpg");
            VerifyCodeUtil.outputImage(w, h, file, verifyCode);
        }
	}
}
