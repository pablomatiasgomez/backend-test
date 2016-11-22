package com.wifiesta.restaurant;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = { "com.wifiesta.restaurant" })
@PropertySource(value = { "classpath:conf/data.properties" })
@EnableWebMvc
public class AppConfig {

}
