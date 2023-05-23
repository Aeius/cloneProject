package com.bit.checkpayclone.mypage.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisSaveService {
	final RedisTemplate<String, String> redisTemplate;
	
	// 이메일 인증 관련
	final String emailAuthNumKey = "email_auth_num";
	final String emailSendCountKey = "email_send_count";
	final String emailFailCountKey = "email_fail_count";
	final String emailReSendCountKey = "email_re_send_count";
	
	//Email값 Redis에 저장하는 메서드
	//sendType="NOR" 일때만 실행(일반전송)
	public int EmailNormalRepoSaved(String email, String authKey, Integer limit, Long sendTimeOut, Long authCodeTimeOut) {		
		ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
		
		//디폴트 값 세팅
		if(limit == null)limit = 6;
		if(authCodeTimeOut == null)authCodeTimeOut = (long)10*60+10;
		
		int resultCount = 0;
		
		if(redisTemplate.getExpire(emailSendCountKey) > 0) {
			resultCount = countPlusOverNotIfHashSaved(emailSendCountKey, email, limit);
		}else {
			//자동삭제주기 세팅
			LocalDate tomorrow = LocalDate.now().plusDays(1);
			int year = tomorrow.getYear();
			int month = tomorrow.getMonthValue();
			int day = tomorrow.getDayOfMonth();
			LocalDateTime expirationTime = LocalDateTime.of(year, month, day, 0, 0);
			long ttlSeconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), expirationTime);
			if(sendTimeOut == null)sendTimeOut = (long)ttlSeconds; //디폴트 값 다음날 0시 0분
			
			resultCount = countPlusOverNotIfHashSaved(emailSendCountKey, email, limit, sendTimeOut, TimeUnit.SECONDS);
		}
		
		if(resultCount != -1) {
			valueOperations.set(emailAuthNumKey+":"+email, authKey);
			redisTemplate.expire(emailAuthNumKey+":"+email, authCodeTimeOut, TimeUnit.SECONDS);
		}

		return resultCount;
	}
	
	//이메일 인증 실패 카운트 저장
	public int EmailAuthFailRepoSaved(String email, Integer limit, Long timeOut) {
		ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
		
		//디폴트값 세팅
		if(limit == null)limit = 5;
		if(timeOut == null)timeOut = (long)60*10;
		
		int resultCount = countPlusOverNotIfValueSaved(emailFailCountKey, email, limit, timeOut, TimeUnit.SECONDS);

		return resultCount;
		
	}
	
	// sendType="RE" 일때만 실행(이메일 재전송 카운트 저장)
	public int EmailReSendRepoSaved(String email, String authKey, Integer limit, Long reSendTimeOut, Long authCodeTimeOut) {
		ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
		
		// 디폴트값 세팅
		if (limit == null)limit = 2;
		if (reSendTimeOut == null)reSendTimeOut = (long) 60*10;
		if (authCodeTimeOut == null)authCodeTimeOut = (long) 60*10+10;

		int resultCount = countPlusOverNotIfValueSaved(emailReSendCountKey, email, limit, reSendTimeOut, TimeUnit.SECONDS);
		
		if(resultCount != -1) {
			valueOperations.set(emailAuthNumKey+":"+email, authKey);
			redisTemplate.expire(emailAuthNumKey+":"+email, authCodeTimeOut, TimeUnit.SECONDS);
//			redisTemplate.expire(emailFailCountKey+":"+email, 0, TimeUnit.SECONDS);
		}

		return resultCount;
	}
	
	

	//-------------공용 메서드 --------------//
	
	public int countPlusOverNotIfValueSaved(String key, String feild, int limit, long timeOut, TimeUnit timeUnit) {
		ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
		valueOperations.setIfAbsent(key+":"+feild, String.valueOf(0));
		
		//현재 카운트 값
		int currentCount = Integer.parseInt(valueOperations.get(key+":"+feild));
		
		//최대 전송 수를 넘으면 -1 반환
		if(currentCount >= limit) return -1;
		
		// 값저장
		valueOperations.set(key+":"+feild, String.valueOf(++currentCount));
		
		//만료 시점 설정
		redisTemplate.expire(key+":"+feild, timeOut, timeUnit);

		return currentCount;
	}
	
	
	public int countPlusOverNotIfHashSaved(String key, String feild, int limit, long timeOut, TimeUnit timeUnit) {
		HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
		hashOperations.putIfAbsent(key, feild, String.valueOf(0));
		
		//현재 카운트 값
		int currentCount = Integer.parseInt((String)hashOperations.get(key, feild));
		
		//최대 전송 수를 넘으면 -1 반환
		if(currentCount >= limit) return -1;
		
		// 값저장
		hashOperations.put(key, feild, String.valueOf(++currentCount));
		
		//만료 시점 설정
		redisTemplate.expire(key, timeOut, TimeUnit.SECONDS);

		return currentCount;
	}
	
	public int countPlusOverNotIfHashSaved(String key, String feild, int limit) {
		HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
		hashOperations.putIfAbsent(key, feild, String.valueOf(0));
		
		//현재 카운트 값
		int currentCount = Integer.parseInt((String)hashOperations.get(key, feild));
		
		//최대 전송 수를 넘으면 -1 반환
		if(currentCount >= limit) return -1;
		
		// 값저장
		hashOperations.put(key, feild, String.valueOf(++currentCount));

		return currentCount;
	}
	
}
