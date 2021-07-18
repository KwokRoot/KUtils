package com.kwok.util.iniconfig;

import com.alibaba.fastjson.JSON;

/**
 * 解析 Ini 类型的配置文件工具类。
 * @see com.kwok.util.iniconfig.Ini
 * @author Kwok
 * 2021-07-18
 */
public class Test_Ini {

	public static void main(String[] args) throws Exception {
		
		Ini ini = Ini.fromResourcePath("com/kwok/util/iniconfig/shiro.ini");
		System.out.println(ini.getSections());
		System.out.println(JSON.toJSONString(ini.entrySet()));
		
	}

}
