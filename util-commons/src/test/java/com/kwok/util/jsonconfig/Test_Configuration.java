package com.kwok.util.jsonconfig;

/**
 * 解析 json 类型的配置文件工具类。
 * @see com.kwok.util.jsonconfig.Configuration
 * @author Kwok
 */
public class Test_Configuration {

	public static void main(String[] args) {

		Configuration configuration = Configuration.from("{\"name\": \"kwok\", \"age\": 18, \"work\": \"${work}\", \"hobby\": [{\"name\": \"music\", \"example\": [\"老鼠爱大米\", \"偏偏喜欢你\", \"一生有你\"] }, {\"name\": \"sport\", \"example\": [\"蹦蹦\", \"跳跳\"] }, {\"name\": \"program\", \"example\": [\"Java\", \"Javascript\", \"VB.NET\"] } ] }");
		
		//从系统属性中取值，@see com.kwok.util.jsonconfig.StrUtil.VARIABLE_PATTERN
		System.setProperty("work", "码农");
		System.out.println(configuration.getConfiguration("").beautify());

		System.out.println(configuration.getString("hobby[2].example[0]", "no"));

		System.out.println(configuration.getConfiguration("hobby").getConfiguration("[0]").getList("example"));
	
	}
}
