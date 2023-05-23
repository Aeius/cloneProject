package com.bit.checkpayclone.admin.login.model;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AdminLoginVo implements UserDetails{ 
	
	private String id;
	private String password;
	private ROLE role;
	
private static final String ROLE_PREFIX = "ROLE_";
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 ROLE checkRole = getRole();
		 Collection<GrantedAuthority> authorities = new ArrayList<>();
		 authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + checkRole));
		return authorities;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return getId();
	}
	
}
