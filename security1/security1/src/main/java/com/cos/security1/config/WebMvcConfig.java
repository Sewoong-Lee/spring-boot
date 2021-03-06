package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //ioc등록
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override //해당 뷰 리졸버 머스테치를 재설정
	public void configureViewResolvers(ViewResolverRegistry registry) {
	      MustacheViewResolver resolver = new MustacheViewResolver();

	      resolver.setCharset("UTF-8");
	      resolver.setContentType("text/html;charset=UTF-8");
	      resolver.setPrefix("classpath:/templates/");
	      resolver.setSuffix(".html");

	      registry.viewResolver(resolver); //레지스터로 뷰 리졸버 등록
	}
}
