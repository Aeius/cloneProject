package com.bit.checkPayCloneFE.util;

import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TokenCall {
	public ResponseEntity<String> callAPI(String URL,HttpServletRequest req, HttpServletResponse cookResp){
		
		// 쿠키에서 토큰 찾기
		String token =getCookieId(req, "jwt");
				
		// id find
		String id = findTokenVal(token,"sub");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<MultiValueMap<String, String>> request=null;
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
		HttpHeaders headers = new HttpHeaders();
		
		headers.set("Authorization", "Bearer " + token);
		request = new HttpEntity<>(parameters, headers);
		
		return restTemplate.exchange(URL, HttpMethod.GET, request, String.class); 
	}
	
	
	public String tkcall(String token, HttpServletRequest req, HttpServletResponse cookResp){
		token=tokenCall(token,req,cookResp);
		if(token.equals("back")) {
			return "redirect:/login";
		}
		return token;
	}
	
	public String tokenCall(String token, HttpServletRequest req, HttpServletResponse cookResp) {
		// 토큰 분리
		if (token != null) {
			ResponseEntity<String> response = null;
			try {
				String id = findTokenVal(token,"sub");
				String role = findTokenVal(token,"role");
				String rtk = getCookieId(req, "rtk");
				RestTemplate restTemplate = new RestTemplate();
		//		String baseUrl="http://localhost:8080";
				String baseUrl = "http://192.168.240.208:8081";
				
				MultiValueMap<String, Object> parameters = new LinkedMultiValueMap();
				parameters.add("id", id);
				parameters.add("rtk", rtk);
				HttpHeaders headers = new HttpHeaders();
				HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(parameters, headers);
	
				// 리프레시 토큰을 통한 갱신
				response = restTemplate.exchange(baseUrl + "/api/login/refresh", HttpMethod.POST, request,String.class);
			} catch (HttpClientErrorException e) {
				
			}
			if(response.getBody()==null) {
				deleteCookie(req, cookResp, "jwt");
				deleteCookie(req, cookResp, "rtk");
				return "back";
			}
			if(response!=null && response.getBody().length()>2) {
				setCookie(cookResp, response.getBody(),"jwt");
				return response.getBody();
			}
			else {
				deleteCookie(req, cookResp, "jwt");
				deleteCookie(req, cookResp, "rtk");
				return "back";
			}
		}
		return token;
	}


	// 쿠키 설정
	public void setCookie(HttpServletResponse cookResp, String token, String cName) {
		Cookie tokenCookie = new Cookie(cName, token);
		tokenCookie.setMaxAge(60 * 60 * 24 *2);
		tokenCookie.setValue(token);
		tokenCookie.setPath("/");
		cookResp.addCookie(tokenCookie);
	}

	// 쿠키에서 토큰 찾기
	public String getCookieId(HttpServletRequest req, String cName) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(cName)) {
					return c.getValue();
				}
			}
		}
		return null;
	}

	// 쿠키 삭제
	public void deleteCookie(HttpServletRequest req, HttpServletResponse cookResp, String cName) {
		int maxAge = 0;
		for (Cookie cookie : req.getCookies()) {
			if (cName.equals(cookie.getName())) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				cookResp.addCookie(cookie);
			}
		}
	}
	
	//토큰에서 cName 값 decode
	public String findTokenVal(String token,String cName){
		if(token==null) return "tokenIsNull";
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		JsonElement parseString = JsonParser.parseString(payload);
		JsonObject jobj = parseString.getAsJsonObject();
		return jobj.get(cName).getAsString();
	}
}
