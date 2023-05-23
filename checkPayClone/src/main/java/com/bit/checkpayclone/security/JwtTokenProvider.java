package com.bit.checkpayclone.security;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.bit.checkpayclone.admin.login.model.AdminLoginVo;
import com.bit.checkpayclone.admin.login.model.ROLE;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

		private final CustomUsersDetailsService customUsersDetailsService;
		
	 	@Value("${jwt.atksecret}")
	    private String atkKey;

	 	
	 	@Value("${jwt.rtksecret}")
	 	private String rtkKey;
	    // 토큰 유효시간
	    private long tokenValidTime = 60 * 30;	//30분 60 * 30
	    
	    //secretKey를 Base64로 인코딩
	    @PostConstruct
	    protected void init() {
	    	atkKey = Base64.getEncoder().encodeToString(atkKey.getBytes());
	    }

	    //accessToken 발급 및 재발급 시 사용
	    public Map<String, Object> generateToken(AdminLoginVo info) {
	    	Map<String, Object> claims = new HashMap<>();
	    	boolean isAdmin = info.getAuthorities().stream().allMatch(value -> value.getAuthority().equals("ROLE_"+ROLE.ADMIN));
	    	if (isAdmin) {
	            claims.put("role","ADMIN");
	        } else {
	            claims.put("role","USER");
	        }
	    	
	    	return doGenerateJwtToken(claims,info.getUsername());
	    }
	    
	    //accessToken 생성
	    public Map<String, Object> doGenerateJwtToken(Map<String,Object> claims,String info) {
	    	Map<String, Object> map = new HashMap<>();
	    	map.put("atk", Jwts.builder()
	    			.setHeaderParam("typ","JWT")
	                .setClaims(claims)
	                .setSubject(info)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() +1000 * tokenValidTime))
	                .signWith(SignatureAlgorithm.HS256, atkKey)
	                .compact());
	    	map.put("role", (String)claims.get("role"));
	    	return map;
	    }
	    
	    //리프레시 토큰 발급
	    public Map<String, Object> generateRToken(AdminLoginVo info) {
	    	Map<String, Object> claims = new HashMap<>();
	    	boolean isAdmin = info.getAuthorities().stream().allMatch(value -> value.getAuthority().equals("ROLE_"+ROLE.ADMIN));
	    	if (isAdmin) {
	            claims.put("role","ADMIN");
	        } else {
	            claims.put("role","USER");
	        }
	    	
	    	return createRefreshToken(claims,info.getUsername());
	    }
	    
	    //리프레시 토큰 생성
	    public Map<String, Object> createRefreshToken(Map<String,Object> claims,String info) {
	    	Map<String, Object> map = new HashMap<>();
	    	map.put("rtk", Jwts.builder()
	    			.setHeaderParam("typ","JWT")
	                .setClaims(claims)
	                .setSubject(info)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3))// 60 * 60 * 24 * 3(3일)
	                .signWith(SignatureAlgorithm.HS256, rtkKey)
	                .compact());
	    	map.put("role", (String)claims.get("role"));
	        return map;
	    }
	    
	    
	    // JWT 토큰에서 인증 정보 조회
	    public Authentication getAuthentication(String token) {
	        UserDetails userDetails = customUsersDetailsService.loadUserByUsername(this.getUserPk(token));
	        
	        if(userDetails!=null) {
		        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	        }

	        return null;
	    }
	    
	    
	    /**
	     * 미사용
	     * */
	    // 사용자 아이디로 인증 정보 조회
	    public Authentication findUserInfo(String id) {
	        UserDetails userDetails = customUsersDetailsService.loadUserByUsername(id);
	        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	    }
	    
	    
	    // accessToken에서 사용자 정보 추출
	    public String getUserPk(String token) {
	        return Jwts.parser().setSigningKey(atkKey).parseClaimsJws(token).getBody().getSubject();
	    }
	    
	    // refreshToken에서 사용자 아이디 추출
	    public String getUserIdByRTK(String token) {
	    	return Jwts.parser().setSigningKey(rtkKey).parseClaimsJws(token).getBody().getSubject();
	    }
	    
	    
	    
	    // Request의 Header에서 token 값을 가져옴.
	    public String resolveToken(HttpServletRequest request) {
	        return request.getHeader(HttpHeaders.AUTHORIZATION);
	    }
	    
	    
	    /**
	     * 미사용
	     * */
	    public boolean validateToken(String jwtToken,HttpServletResponse response) throws UnsupportedJwtException, IOException{
	    	 try {
	             Jws<Claims> claims = Jwts.parser().setSigningKey(atkKey).parseClaimsJws(jwtToken);
	             return !claims.getBody().getExpiration().before(new Date());
	         } catch (ExpiredJwtException e) {
	        	
	        	 return false;
	         }
	    }
	    
	    /**
	     * 미사용
	     * */
	    // accessToken 헤더 설정 -> filter에서 사용
	    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
	        response.setHeader("authorization", "bearer "+ accessToken);
	    }
	  
	   
}
