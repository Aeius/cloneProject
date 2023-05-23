package com.bit.checkpayclone.admin.login.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	final String rtk = "RTK";
	final RedisTemplate<String, String> redisTemplate;
	
	 //로그인시 호출됨 -> Redis에 refreshToken의 value값을가진 key들 저장하는 메서드 호출
	 public void saveTokenInfo(String id, String accessToken, String refreshToken) {
		 saveRefreshToken(id,refreshToken);
	 }
	 
	 /**
	  * 아래는 생성관련 메서드
	  * */
	 public void saveRefreshToken(String id,String refreshToken) {
		 ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
		 valueOperations.set(id+":"+rtk, refreshToken);
		 
		 //지금은 1분 - 이후 실사용시 바꾸기
		 redisTemplate.expire(id+":"+rtk, 60*60*24*3, TimeUnit.SECONDS); //만료시간과 일치시킴 60*60*24*3 -> 3일후 폐기 id -> rtk 찾기
	 }
	 
	 /**
	  * 로그인한 사용자의 ip주소 redis에 저장 (필터에서 저장)
	  * */
	 public void saveUserIp(String id,String ip) {
		 ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
		 valueOperations.set(id+":ip", ip);		//	"U000000001U00000001:ip" : "127.123.124.11" <- 이런식으로 저장.
		 //지금은 1분 - 이후 실사용시 바꾸기
		 redisTemplate.expire(id+":ip", 60*60*24*3, TimeUnit.SECONDS); //rtk와 똑같은 시간으로 설정.
	 }
	 
	 
	 
	 /**
	  * is~ 토큰 및 사용자 정보를 Redis에서 존재유무판별하는 메서드
	  * */
	//Redis에서 id값에 해당하는 refreshToken이 존재하는 판별 -> 있으면 이미 로그인하고 있다는 뜻
	 public boolean isRefreshToken(String id) {
		 ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
		 return valueOperations.get(id+":RTK") != null;
	 }
	 
	 //Redis에서 로그인을 시도한 ip가 저장된 ip주소와 일치하는지 판별 -> 참이면 로그인 성공 시켜줘야한다.
	 public boolean isAccessByUserIp(String id,String ip) {
		 ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
		 if(ip.equals(valueOperations.get(id+":ip"))) {
			 return true;
		 }
		 return false;
	 }
	 
	 
	 //Redis에서 refreshToken 가져오기
	 public boolean getRefreshToken(String id, String rtk) {
		 ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
		 if(rtk.equals(valueOperations.get(id+":RTK"))) {
			 return true;
		 }
		 return false;
		
	 }
	 
	 /**
	  * 아래는 삭제관련 메서드
	  * */
	 //로그아웃으로 토큰 값 Redis에서 id에 해당하는 key 모두삭제
	 public void tokenRemoveByLogout(String id,String rtk) {
		 ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
		 //로그아웃 시 해당 사용자아이디에 해당하는 rtk, ip가 존재할 시 삭제
		 if(getRefreshToken(id,rtk)) {
			 redisTemplate.expire(id+":RTK", 0, TimeUnit.SECONDS);	//바로 폐기
			 redisTemplate.expire(id+":ip", 0, TimeUnit.SECONDS);	//바로 폐기
		 }
			 
	 }
	 
	 
	 //클라이언트 ip주소 가져오기
	 public static String getClientIp(HttpServletRequest request) {
		    String clientIp = null;
		    boolean isIpInHeader = false;

		    List<String> headerList = new ArrayList<>();
		    headerList.add("X-Forwarded-For");
		    headerList.add("HTTP_CLIENT_IP");
		    headerList.add("HTTP_X_FORWARDED_FOR");
		    headerList.add("HTTP_X_FORWARDED");
		    headerList.add("HTTP_FORWARDED_FOR");
		    headerList.add("HTTP_FORWARDED");
		    headerList.add("Proxy-Client-IP");
		    headerList.add("WL-Proxy-Client-IP");
		    headerList.add("HTTP_VIA");    
		    headerList.add("IPV6_ADR");

		    for (String header : headerList) {
		        clientIp = request.getHeader(header);
		        if (StringUtils.hasText(clientIp) && !clientIp.equals("unknown")) {
		            isIpInHeader = true;
		            break;
		        }
		    }
		    if (!isIpInHeader) {
		        clientIp = request.getRemoteAddr();
		    }
		    return clientIp;
		}
		 
		 
}
