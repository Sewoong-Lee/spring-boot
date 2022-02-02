package com.cos.jwt.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.jwt.model.User;
import com.cos.jwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//http://localhost:8081/login => 그냥은 동작을 안함
//직접 PrincipalDetailsService 를 실행해주는 필터를 만들어야 한다.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService : 진입");
		User userEntity = userRepository.findByUsername(username);
		System.out.println("PrincipalDetailsService" + userEntity.toString());
		return new PrincipalDetails(userEntity);
	}
	
}
