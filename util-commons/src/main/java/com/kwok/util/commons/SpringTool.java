package com.kwok.util.commons;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 1.通过该类即可在普通工具类里获取 Spring 管理的 Bean。
 * 2.通过 @Configuration 或 @Component 注解初始化 SpringTool 类，注入 ApplicationContext 对象。
 * 3.或者 Spring.xml 中配置：<bean id="springTool" class="com.devops.cms.util.SpringTool" lazy-init="false"/> 初始化 SpringTool 类。
 */
@Configuration
public class SpringTool implements ApplicationContextAware {
	
	public static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringTool.applicationContext = context;
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> claz){  
        return applicationContext.getBean(claz);  
    } 
	
}
