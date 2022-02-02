package com.cos.jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFilter3  implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터 3");
		
		//매개변수로 받은 request, response를 다운캐스트
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		//요청이 포스트면
		//토큰이 맞으면 진행하게끔
		//토큰 : cos 를 만들어줘야함 아이디와 패스워드가 정확하게 들어와서 로그인이 완료되면 토큰을 만들어주고 응답해준다.
		//요청할 때마다 헤더에 Authorization 에 토큰을 가지고온다.
		//그때 토큰이 넘어온게 이 토큰이 내가 만든 토큰이 맞는지만 검증하면댐 (RSA,HS256)
		if(req.getMethod().equals("POST")) {
			String headerAuth = req.getHeader("Authorization");
			System.out.println(headerAuth);
			
			if(headerAuth.equals("cos")) {
				chain.doFilter(req, res); //필터체인 등록 (이걸 안하면 여기서 끝남)
			}else {
				PrintWriter out = res.getWriter();
				out.println("인증안댐");
			}
		}
		
	}

}
