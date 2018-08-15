package com.scushare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations= {"classpath:/dubbo-front.xml"})
public class DubboConfig {

}
