package com.scushare.utils;

import org.springframework.context.ConfigurableApplicationContext;

public class SpringContextUtil {
	private static ConfigurableApplicationContext context = null;
	
	public static void setContext(ConfigurableApplicationContext configurableApplicationContext) {
		context = configurableApplicationContext;
	}
	
	public static ConfigurableApplicationContext getContext() {
		return context;
	}
	
	public static String getProperty(String property) {
		if(context == null) {
			return null;
		}
		return context.getEnvironment().getProperty(property);
	}
}
