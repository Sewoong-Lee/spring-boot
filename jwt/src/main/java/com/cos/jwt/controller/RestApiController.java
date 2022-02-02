package com.cos.jwt.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwt.model.User;
import com.cos.jwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
//@CrossOrigin  // CORS 허용 
public class RestApiController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// 모든 사람이 접근 가능
	@GetMapping("/home")
	public String home(){
		return "<h1>home</h1>";
	}
	
	@PostMapping("/token")
	public String token(){
		return "<h1>token</h1>";
	}
	
	@PostMapping("join")
	public String join(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER"); //롤 기본 설정
		userRepository.save(user);
		return "회원가입완료";
	}
	
	// user, manager, admin 접근 가능
	@GetMapping("user")
	public String user() {
		
		return "<h1>user</h1>";
	}
	
	// manager, admin 접근 가능
	@GetMapping("manager")
	public String manager() {
		
		return "<h1>manager</h1>";
	}
	
	// admin 접근 가능
	@GetMapping("admin")
	public String admin() {
		
		return "<h1>admin</h1>";
	}
}
