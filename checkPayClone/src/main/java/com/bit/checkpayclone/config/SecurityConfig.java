package com.bit.checkpayclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.bit.checkpayclone.filter.JwtAuthenticationFilter;
import com.bit.checkpayclone.filter.JwtExceptionFilter;
import com.bit.checkpayclone.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig  {
	
	private final JwtTokenProvider jwtTokenProvider;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception  {

			http
			.csrf().disable()
			.httpBasic().disable();

	        http.authorizeRequests()
	        .antMatchers("/api/mypage/email-authentication").permitAll()
	        .antMatchers("/api/login/**").permitAll()			//어드민 로그인
	        .antMatchers("/api/member/login/**").permitAll() 	//사용자 로그인
	        .antMatchers("/api/member/join/**").permitAll() 	//사용자 회원가입
	        .antMatchers("/api/card/membercard/**").hasAnyRole("USER")
	        .antMatchers("/api/admin/**").hasAnyRole("ADMIN")
	        .anyRequest().hasAnyRole("USER");
	        
	        
	        http.sessionManagement()	
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //->세션사용안함.
	        .and()
	        //JwtToken필터 적용.
	        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
	        //AccessDeniedException 처리
	        .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);
	        return http.build();


    }
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}
