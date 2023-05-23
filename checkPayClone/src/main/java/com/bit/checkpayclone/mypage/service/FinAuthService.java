package com.bit.checkpayclone.mypage.service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.member.model.MemberVo;
import com.bit.checkpayclone.member.service.MemberService;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinAuthService {
	final JavaMailSenderImpl mailSender;
	final PasswordEncoder passwordEncoder;
	final RedisSaveService redisSaveService;
	final MemberService memberService;
	final RedisTemplate<String, String> redisTemplate;
	
	final String emailSendCountKey = "email_send_count";
	final String emailReSendCountKey = "email_re_send_count";
	final String emailAuthNumKey = "email_auth_num";
	final String emailFailCountKey = "email_fail_count";
	
	@Autowired
	AsyncMailSend asyncMailSend;
	
		// 난수 발생
		public int makeRandomNumber() {
			// 난수의 범위 11111111 ~ 99999999 (8자리 난수)
			Random r = new Random();
			int checkNum = r.nextInt(88888888) + 11111111;
			return checkNum;
		}
		
		
		//--------------------------------아래는 양식별 이메일 메서드
		//step1-1.인증번호 이메일 송신
		public JsonObject authJoinEmail(String email, String sendType, String id, Integer limit, Long sendTimeOut, Long authCodeTimeOut) throws UnsupportedEncodingException {
			
			//변수세팅
			Calendar calendar = Calendar.getInstance();
			JsonObject result = new JsonObject();
			int num_trials = 0;
			
			
			int authNumber =makeRandomNumber();
			String authNum = Integer.toString(authNumber);
			
			
			long expire = -2;
			
			switch (sendType) {
			case "NOR": 
				num_trials= redisSaveService.EmailNormalRepoSaved(email,passwordEncoder.encode(authNum), limit, sendTimeOut, authCodeTimeOut); 
				expire = redisTemplate.getExpire(emailSendCountKey);
				break;
			case "RE": 
				num_trials= redisSaveService.EmailReSendRepoSaved(email,passwordEncoder.encode(authNum), limit, sendTimeOut, authCodeTimeOut); 
				expire = redisTemplate.getExpire(emailReSendCountKey+":"+email);
				break;
			case "FIN":
				num_trials= emailFinDBSaved(id, authNum, limit);
				
				//유효시간 세팅
		        long now = calendar.getTime().getTime();
		        calendar.add(Calendar.DATE, 1);
		        calendar.set(Calendar.HOUR_OF_DAY, 0);
		        calendar.set(Calendar.MINUTE, 0);
		        calendar.set(Calendar.SECOND, 0);
		        long tomorrow= calendar.getTime().getTime();
				expire = (tomorrow - now)/1000;
				break;
			}
			//시행횟수(이메일제한, 재전송 제한) 추가, 만료시간 추가
			result.addProperty("num_trials", num_trials);
			result.addProperty("send_expire", expire);
			
			if(num_trials == -1) {
				result.addProperty("result", false);
				return result;
			}
			
			String setFrom = ".com"; // email-config에 설정한 자신의 이메일 주소를 입력 
			String toMail = email;
			String title = "[COOCON] 이메일 인증 안내"; // 이메일 제목 
			String content = //html 형식으로 작성 !
					"<p style=\"color: gray;\">"
					+"안녕하세요, COOCON입니다.<br/>"  	 
	                +"COOCON은 고객님꼐서 사용하고 계신 체크페이 자산관리 서비스 운영사입니다.<br/>"  
	                +"고객님께서 개인정보열람을 위해 요청하신 인증번호를 전달드립니다.<br/>" 
	                +"아래 인증번호를 앱 화면에 입력 부탁드립니다<br/>" 
	                +"</p><br/>"
				    +"<div style=\""
				    + "width:auto;border: 1px solid black; border-radius: 3px; font-weight: bold;"
				    + "text-align: center; color: blue; padding-top: 15pt; padding-bottom: 15pt;"
				    + "\">"
				    + "인증 번호 : " + authNumber 
				    + "</div><br/>" 
				    +"<p style=\"color: gray;\">"
				    +"만약 이메일 요청을 하시지 않은 경우 고객센터</br>"
				    +"1522-5904로 전달 부탁드립니다."
				    +"</p>"; //이메일 내용 삽입
			asyncMailSend.mailSend(setFrom, toMail, title, content);
			result.addProperty("result", true);
			return result;
		}
		
		private int emailFinDBSaved(String id, String authNum, Integer limit) {
			Calendar calendar = Calendar.getInstance();
			MemberVo memberVo = memberService.finInfoSelectOne(id);
			
			
			//제한값, 오늘일자, 마지막 이메일 송신일자 세팅
			limit = limit==null?6:limit;
			int eml_auth_cnt = 0;
			String nowDay = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
			String eml_req_dt = nowDay;
			if(memberVo != null){
				eml_auth_cnt = memberVo.getEml_auth_cnt();
				eml_req_dt = memberVo.getEml_req_dt();
			}
			
			//제한값, 유효기간에 따라 작업 분할
			switch (getAnswerStatus(true, limit, eml_auth_cnt, !nowDay.equals(eml_req_dt))) {
			case "LO": case "TO": eml_auth_cnt = 0; //유효기간 지났을때 제한값 초기화
				break;
			case "LI": return -1; //유효기간 이내 제한값 초과시 작업
			}
			
			//DB 업데이트
			memberService.finAutoInsertInfoSetting(MemberVo.builder()
					.memb_cd(id)
					.eml_auth_no(authNum)
					.eml_auth_cnt(++eml_auth_cnt)
					.eml_req_dt("NOW")
					.upd_dttm("NOW")
					.build());
			
			//전송횟수 세팅
			return eml_auth_cnt; 
		}
		
		//step1-2.이메일 인증-------------------------------
		public JsonObject emailAuthentication(String email, String inputCode, String useType, String id, Integer limit, Long timeOut) throws UnsupportedEncodingException {	
			
			JsonObject resultObject = new JsonObject();
			Boolean authResult = null;
			Integer failCount = null;
			Long expire = null;
			
			//필수 아닌 값 디폴트
			limit = limit==null?5:limit;
			timeOut = timeOut==null?10*60:timeOut;
			
			switch (useType) {
			case "SIGN"://가입 시 사용
				//입력값 검사
				ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
				authResult = passwordEncoder.matches(inputCode, valueOperations.get(emailAuthNumKey+":"+email));
				
				if(!authResult) {
					valueOperations.setIfAbsent(emailFailCountKey+":"+email, String.valueOf(0), 10*60, TimeUnit.SECONDS);
					failCount = redisSaveService.EmailAuthFailRepoSaved(email, limit, timeOut);					
				}else {
					redisTemplate.expire(email+":"+emailFailCountKey, 0, TimeUnit.SECONDS);
					failCount = 0;
				}
				expire = redisTemplate.getExpire(emailFailCountKey+":"+email);
				break;
			case "VALID"://이메일 인증시 사용
				MemberVo memberVo = memberService.finInfoSelectOne(id);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				
				//1. 검증결과 
				authResult = inputCode.equals(memberVo.getEml_auth_no());
				
				//2.실패 횟수 
				failCount = memberVo.getEml_fail_cnt()==null 
							? 0 
							: memberVo.getEml_fail_cnt();
				
				//3.유효시간
				Timestamp fail_timestamp = memberVo.getFail_timestamp() == null
							? new Timestamp(System.currentTimeMillis() - 1000*60*60) //1시간전(= 적당한 10분 이전의 값)
							: memberVo.getFail_timestamp();
				 // 시간 차이 계산
		        long timeDifferenceMillis = timestamp.getTime() - fail_timestamp.getTime();
		        long timeDifferenceSeconds = TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMillis);
				
		        
		        
		        switch (getAnswerStatus(authResult, limit, failCount, timeDifferenceSeconds >= timeOut)) {
				case "LO":case "FO":
					failCount = 1;
	        		//DB 업데이트
	        		memberService.finAutoInsertInfoSetting(MemberVo.builder()
	        				.memb_cd(id)
	        				.eml_fail_cnt(failCount)
	        				.upd_dttm("NOW")
	        				.build());
					break;
				case "LI":
					failCount = -1;
					break;
				case "TI":case "TO":
					memberService.finAutoInsertInfoSetting(MemberVo.builder()
	        				.memb_cd(id)
	        				.eml_fail_cnt(0)
	        				.upd_dttm("NOW")
	        				.build());
					break;
				case "FI":
					memberService.finAutoInsertInfoSetting(MemberVo.builder()
	        				.memb_cd(id)
	        				.eml_fail_cnt(++failCount)
	        				.upd_dttm("NOW")
	        				.build());
					if(failCount >= limit) failCount = -1;
					break;
				}
		        expire = timeOut - timeDifferenceSeconds;
		        expire = expire < 0 ? timeOut : expire;
				break;
			}
			
			//오답 5번 이상이면 무조건 실패
			if(failCount == -1) {
				resultObject.addProperty("num_trials", failCount);
				resultObject.addProperty("fail_expire", expire);
				resultObject.addProperty("result", false);
			}else {
				//오답 횟수 반환 객체에 저장
				resultObject.addProperty("num_trials", failCount);
				resultObject.addProperty("fail_expire", expire);
				resultObject.addProperty("result", authResult);
			}
			return resultObject;
		}
		
		//step2-1.FIN 번호 (1)초기화진입단계, (2)초기화단계, (3)인증단계-------------------------------
		public JsonObject finAuthentication(String id, String step, String input, Integer limit) {
			JsonObject resultObject = new JsonObject();
			boolean result = false;
			switch (step) {
			case "READY"://(1)초기화진입단계
				System.out.println("레디 실행됨");
				memberService.finAutoInsertInfoSetting(MemberVo.builder()
        				.memb_cd(id)
        				.auth_yn('N')
        				.upd_dttm("NOW")
        				.build());
				result = true;
				break;
			case "INIT"://(2)초기화단계
				memberService.finAutoInsertInfoSetting(MemberVo.builder()
						.memb_cd(id)
						.auth_yn('I')
						.fin_no_fail_cnt(0)
						.fin_no(input)
						.upd_dttm("NOW")
						.build());
				result = true;
				break;
			case "AUTH"://(3)인증단계
				if(input == null) {
					resultObject.addProperty("result", false); 
					resultObject.addProperty("num_trials", -2);
					return resultObject;
					}
				//유효시간
				limit = limit == null ? 5 : limit;
				
				MemberVo memberVo = memberService.finInfoSelectOne(id);
				
				String auth_no = memberVo.getFin_no();//1
				Integer fail_cnt = memberVo.getFin_no_fail_cnt() == null//2
						? 0
						: memberVo.getFin_no_fail_cnt();
				
				switch (getAnswerStatus(auth_no.equals(input), 1, 0, true)) {
				//정답
				case "TO": case "TI":
					memberService.finAutoInsertInfoSetting(MemberVo.builder()
	        				.memb_cd(id)
	        				.fin_no_fail_cnt(0)
	        				.auth_yn('Y')
	        				.upd_dttm("NOW")
	        				.build());
					result = true;
					break;
				//오답
				case "FI": case "FO":
					if(++fail_cnt<limit) {
						memberService.finAutoInsertInfoSetting(MemberVo.builder()
		        				.memb_cd(id)
		        				.fin_no_fail_cnt(fail_cnt)
		        				.upd_dttm("NOW")
		        				.build());
					}else {
						fail_cnt = -1;
		        		memberService.finAutoInsertInfoSetting(MemberVo.builder()
		        				.memb_cd(id)
		        				.fin_no_fail_cnt(0)
		        				.auth_yn('N')
		        				.upd_dttm("NOW")
		        				.build());
					}
					result = false;
					break;
				}
				resultObject.addProperty("num_trials", fail_cnt);
				break;
			}
			resultObject.addProperty("result", result);
			
			return resultObject;
		}
		
		
		//검사결과, 오답횟수, 유효시간에 따른 상태를 반환하는 메서드
		//T, F = 정답, 오답
		//I, O = 유효시간 In, Out
		//L = 오류 limit 상태
		public String getAnswerStatus(boolean result, int limit, int current, boolean timeOver) {
			
			if(current>=limit) {//틀린횟수가 limit 이상
				if(timeOver) {
					return "LO"; //유효시간 종료
				}else {
					return "LI"; //유효시간 이내
				}
    		}
			
			if(result) { //틀린횟수 limit 이하일때의 정상적인 처리(정답, 오답)
				if(timeOver) {
					return "TO"; //유효시간 종료
				}else {
					return "TI"; //유효시간 이내
				}
			}else {
				if(timeOver) {
					return "FO"; //유효시간 종료
				}else {
					return "FI"; //유효시간 이내
				}
			}
		}
}
