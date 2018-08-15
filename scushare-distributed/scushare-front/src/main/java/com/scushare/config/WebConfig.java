package com.scushare.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.druid.support.http.StatViewServlet;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan(
		basePackages="com.scushare.controller", 
		includeFilters=@ComponentScan.Filter(
				type=FilterType.ANNOTATION, 
				value=org.springframework.stereotype.Controller.class))
public class WebConfig implements WebMvcConfigurer {

	@Value("${local_data_path}")
	private String DataPath;
	
	@Bean
    public ViewResolver viewResolver() {
		System.out.println("path:" + DataPath);
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/META-INF/resources/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/statics/**").addResourceLocations("file:" + DataPath + "/statics/");
		registry.addResourceHandler("/scushare/fileData/**").addResourceLocations("file:" + DataPath + "/UPLOADED_FILE/");
		registry.addResourceHandler("/userChathead/**").addResourceLocations("file:" + DataPath + "/USER_CHATHEAD/");
	}
	
	//注册StatViewServlet查看druid状态
	@Bean
	public ServletRegistrationBean<StatViewServlet> statViewServletRegistrationBean(){
		return new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(), "/druid/*");
	}
	
	/**
     * 启用spring mvc 的注解
     */
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
