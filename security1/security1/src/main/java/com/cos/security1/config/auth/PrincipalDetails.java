package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

//시큐리티가 /login을 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티가 가지고있는 세션을 만들어준다. (Security ContexHolder) 키값에 저장
// 들어갈 수 있는 오브젝트 => Authenrication 타입 객체
// Authenrication 안에 User 장보가 있어야 됨.
// User 오브젝트 타입 => UserDetails 타입 객체

// 시큐리티 세션 영역 => Authenrication타입 => UserDetails(PrincipalDetails)타입

public class PrincipalDetails implements UserDetails{
	
	private User user; //콤포지션
	
	//PrincipalDetails 생성자 생성
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	//해당 User의 권한을 리턴하는곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//권한을 스트링으로 바로 넘길 수 없어 형변환
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override //너 계정 만료됬니?
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override //너 계정 잠겼니?
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override //너 계정 비밀번호 일정기간 지났니?
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override //너 계정 활성화 되어있니?
	public boolean isEnabled() {
		//우리 사이트에서 1년동안 회원이 로그인을 안하면 휴면계정으로 전환 하기로 했다면?
		// User에 로그인날짜 정의가 되어있어야 하고
		//그 데이터를 가지고 와서 현재시간 - 로그인 시간으로 계산 후 일정기간 초과시 리턴 펄스 
		return true;
	}

}
