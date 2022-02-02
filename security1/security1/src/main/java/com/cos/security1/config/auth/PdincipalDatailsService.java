package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

// 시큐리티 설정에서 .loginProcessingUrl("/login")으로 걸어놨기 때문에
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 ioc 되어있는 loadUserbyUsername 함수가 실행 (그냥 규칙이다.)
@Service
public class PdincipalDatailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	// Authenrication타입 내부에 리턴된 UserDetails 가 들어가며 Authenrication을 자동 생성
	// 이후 시큐리티 세션에도 Authenrication을 넣어주며 세션 자동생성
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//매개변수로 받은 유저네임은 로그인페이지html에서 작성한 유저네임이다.
		//변경시 시큐리티컨피그 에서 .loginPage() 믿에 .usernameParmeter("변경이름") 을 작성해줘야한다.
		//받아온 이름으로 유저가 있는지 확인을 해야한다.
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		
		return null;
	}

}
