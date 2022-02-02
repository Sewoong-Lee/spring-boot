package com.cos.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); //내 서버가 응답을 할때 제이슨을 자바스크립트에서 처리할 수 있게 설정
		config.addAllowedOrigin("*"); // 모든 ip의 응답을 허용 하겠다.
		config.addAllowedHeader("*"); //모든 헤더의 응답을 허
		config.addAllowedMethod("*"); // 모든 post, get, put, patch 요청을 허용 하겠다.
		
		source.registerCorsConfiguration("/api/**", config);
		
		return new CorsFilter(source);
	}
}
