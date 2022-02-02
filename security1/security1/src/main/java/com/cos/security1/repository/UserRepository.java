package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

//기본적인 crud 함수를 들고 있다.
//레파지토리 어노테이션이 없어도 ioc가 된다. JpaRepository를 상속했기 때문에
public interface UserRepository extends JpaRepository<User, Integer>{
	
	// findBy까지는 규칙 -> Username은 문법 
	// select * from user where username = 파라메터 username => 이렇게 호출이 된다.
	public User findByUsername(String username); //Jpa 쿼리메소드 검색
	
	//예제
	// select * from user where email = ?
//	public User findByEmail();
}
