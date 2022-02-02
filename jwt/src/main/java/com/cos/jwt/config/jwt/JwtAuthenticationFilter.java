package com.cos.jwt.config.jwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

// 스프링 시큐리티에 UsernamePasswordAuthenticationFilter가 있음.
// /login 요청해서 username와 password를 전송하면 (post)
// UsernamePasswordAuthenticationFilter 가 동작함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;
	
	//1번 실행
	// /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter 로그인 시도중");
		
		// 1.username, password 를 받는다
		try {
			//request 안의 데이터 확인
//			BufferedReader br = request.getReader();
//			String input = null;
//			while((input = br.readLine()) != null) {
//				System.out.println(input);
//			}
			
			//제이슨으로 파싱할때
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);
			System.out.println("제이슨파싱" + user.toString());
			
			//토큰 만들기
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			
			System.out.println("JwtAuthenticationFilter : 토큰생성완료");
			
			// PrincipalDetailsService의 loadUserByUsername() 함수가 실행된다.
			//db의 아이디와 비밀번호가 일치한다는 뜻
			Authentication authentication = 
					authenticationManager.authenticate(authenticationToken);

			// 아래 내용이 찍힌다는건 로그인이 되었다는뜻
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			System.out.println("로그인 되었나?" + principalDetails.getUser().getUsername()); //로그인이 정상일시 뜸
			
			//authentication를 리턴하면 세션에 저장된다.
			//리턴해주는 이유는 권한 관리를 시큐리티가 해주기 때문에 편하기 때문에
			//궅이 jwt 토큰을 사용하며 세션을 만들 이규가 없음, 근데 권한처리 하나 때문에 세션에 넣어준다.
			return authentication;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//오류시
		return null;
	}
	
	//2번 실행 (attemptAuthentication 실행 후 로그인이 정상적이면 아래 실행)
	// 여기서 jwt 토큰을 만들어서 리쿼스트 요청 사용자에게 응답해준다.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication : 실행됨");
		
		PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();
		
		//RSA 방식이 아닌 Hash 암호화 방식
		//HMAC512는 서버키를 알고 있어야한다.
		String jwtToken = JWT.create()
				.withSubject("cos토큰") // 토큰이름
				.withExpiresAt(new Date(System.currentTimeMillis()+(60000*10))) //만료시간 : 현재시간 + 60000 * 10 (10분)
				.withClaim("id", principalDetailis.getUser().getId())
				.withClaim("username", principalDetailis.getUser().getUsername())
				.sign(Algorithm.HMAC512("cos")); //내 서버만 아는 고유값
		
		response.addHeader("Authorization","Bearer "+jwtToken);
	}
	
}
