package com.ryx.credit.commons.scan;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * web配置，可将spring-mvc.xml中的配置到此
 * @author Administrator
 *
 */
//@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new InterceptorStack()).excludePathPatterns("/static/**", "/favicon.ico");
	}
	
}