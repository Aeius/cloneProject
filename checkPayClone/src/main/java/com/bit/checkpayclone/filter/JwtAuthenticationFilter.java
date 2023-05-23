package com.bit.checkpayclone.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.bit.checkpayclone.admin.login.model.AdminLoginVo;
import com.bit.checkpayclone.security.JwtTokenProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtTokenProvider jwtTokenProvider;
   
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 //Request의 Header에서 token 값을 가져옴. 
		 String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		 	
		    // 토큰 형식 검사
		 	if (token != null && token.startsWith("Bearer ")) {
	            String jwtToken = token.substring(7);
	            
	            try {
	            	Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
	            	// SecurityContext 에 Authentication 객체를 저장
	            	SecurityContextHolder.getContext().setAuthentication(authentication);
	            	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	            	AdminLoginVo vo = (AdminLoginVo)principal;
	            	
	            }catch (ExpiredJwtException e) {
	            	throw new JwtException("토큰 기한 만료");
				}catch (IllegalArgumentException e) {
					throw new JwtException("jwt claims 비어있음.");
				}catch(SignatureException e){
					throw new JwtException("유효하지 않은 서명.");
	            }catch (MalformedJwtException e) {
	            	throw new JwtException("유효하지 않은 토큰");
	            }catch (UnsupportedJwtException e) {
	            	throw new JwtException("지원되지 않는 토큰");
				}catch (Exception e) {
	            	throw new JwtException("권한 없음");
				}
		
	        }
		 	filterChain.doFilter(request, response);
	}
}