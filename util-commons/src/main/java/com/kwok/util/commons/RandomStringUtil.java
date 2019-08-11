package com.kwok.util.commons;

import java.util.Random;

public class RandomStringUtil {

	//字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
    public static final String DIGIT_VERIFY_CODES = "23456789";
    public static final String LETTER_VERIFY_CODES = "ABCDEFGHJKLMNPQRSTUVWXYZ";
    
    /**
     * 使用默认字符源生成验证码
     * @param verifySize    验证码长度
     * @return
     */
    public static String generateVerifyCode(int verifySize){
    	
        int digitCodesLen = DIGIT_VERIFY_CODES.length();
        int letterCodesLen = LETTER_VERIFY_CODES.length();
        
        Random rand = new Random();
        
        StringBuilder verifyCode = new StringBuilder(verifySize);
        
        for(int i = 0; i < verifySize; i++){
			if (i % 2 == 0) {
            	verifyCode.append(LETTER_VERIFY_CODES.charAt(rand.nextInt(letterCodesLen-1)));
            }else{
            	verifyCode.append(DIGIT_VERIFY_CODES.charAt(rand.nextInt(digitCodesLen-1)));
            }
        	
        }
        
        return verifyCode.toString();
    	
    }

    public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(generateVerifyCode(8));
		}
	}
    
}
