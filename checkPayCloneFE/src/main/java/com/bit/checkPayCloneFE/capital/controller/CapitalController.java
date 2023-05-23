package com.bit.checkPayCloneFE.capital.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.bit.checkPayCloneFE.capital.model.CapitalVo;
import com.bit.checkPayCloneFE.util.TokenCall;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
public class CapitalController {
	
	@GetMapping("/capital")
	public String capital(Model model, HttpServletRequest req, HttpServletResponse cookResp) {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
		HttpHeaders headers = new HttpHeaders();
		TokenCall tkc = new TokenCall();
		// 쿠키에서 토큰 찾기
		String token =tkc.getCookieId(req, "jwt");
		// id find
		String id = tkc.findTokenVal(token,"sub");
		ResponseEntity<String> responseCapital = null;
		HttpEntity<MultiValueMap<String, String>> request=null;
		if(token==null)
			return "redirect:/login";
		
		try {
			headers.set("Authorization", "Bearer " + token); // 토큰을 헤더에 넣음 
			request = new HttpEntity<>(parameters, headers);
			responseCapital = restTemplate.exchange("http://192.168.240.208:8081/api/capital/membercapital/" + id, HttpMethod.GET, request, String.class);
//			responseCapital = cardRestTemplate.exchange("http://localhost:8081/api/capital/membercapital/" + id, HttpMethod.GET, request, String.class);
		}catch (HttpClientErrorException e) {
			token = tkc.tkcall(token, req, cookResp);
			if(token.equals("back"))
				return "redirect:/login";
			else
				return "redirect:/main";
		}
		
		Gson gson = new Gson();
		
		List<CapitalVo> capitalList = new ArrayList();
		
		JsonElement capitalParseString = JsonParser.parseString(responseCapital.getBody());
		JsonArray jsonArr4 = (JsonArray) capitalParseString;
		long capitalKrwSum=0;
		Map<String, Long> foreignMoney = new HashMap<>();
		long krwCnt=0;
		for (int i = 0; i < jsonArr4.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr4.get(i), Map.class);
			long balace_amt = (long) Double.parseDouble(map.get("balance_amt").toString());
			if(map.get("currency_code").equals("KRW")) {
				capitalKrwSum+=balace_amt;
				krwCnt++;
			}else {
				foreignMoney.put(map.get("currency_code").toString(), foreignMoney.get(map.get("currency_code").toString())==null?balace_amt:foreignMoney.get(map.get("currency_code").toString())+balace_amt);
			}
			
			capitalList.add(new CapitalVo().builder()
					.org_code(map.get("org_code").toString())
					.account_num(map.get("account_num").toString())
					.prod_name(map.get("prod_name").toString())
					.last_offered_rate(Double.parseDouble(map.get("last_offered_rate").toString()))
					.balance_amt(balace_amt)
					.currency_code(map.get("currency_code").toString())
                    .build());
		}
		model.addAttribute("foreignMoney", foreignMoney);
		model.addAttribute("capitalList", capitalList);
		model.addAttribute("capitalKrwSum", capitalKrwSum);
		model.addAttribute("krwCnt", krwCnt);
		return "/capital/capital";
	}
	
	
	@PostMapping("/capital/loandetail")
	public String loandetail(String org_code, String account_num, Model model, HttpServletRequest req, HttpServletResponse cookResp) {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
		HttpHeaders headers = new HttpHeaders();
		TokenCall tkc = new TokenCall();
		// 쿠키에서 토큰 찾기
		String token =tkc.getCookieId(req, "jwt");
		// id find
		String id = tkc.findTokenVal(token,"sub");
		ResponseEntity<String> responseCapital = null;
		HttpEntity<MultiValueMap<String, String>> request=null;
		if(token==null)
			return "redirect:/login";
		try {
			headers.set("Authorization", "Bearer " + token); // 토큰을 헤더에 넣음 
			request = new HttpEntity<>(parameters, headers);
			responseCapital = restTemplate.exchange("http://192.168.240.208:8081/api/capital/capitaldetail/"+id+"/"+org_code+"/"+account_num, HttpMethod.GET, request, String.class);
//			responseCapital = cardRestTemplate.exchange("http://localhost:8081/api/capital/capitaldetail/"+id+"/"+org_code+"/"+account_num, HttpMethod.GET, request, String.class);
		}catch (HttpClientErrorException e) {
			token = tkc.tkcall(token, req, cookResp);
			if(token.equals("back"))
				return "redirect:/login";
			else
				return "redirect:/main";
		}
		
		Gson gson = new Gson();
		
		JsonElement capitalParseString = JsonParser.parseString(responseCapital.getBody());
		JsonArray jsonArr = (JsonArray) capitalParseString;
		for (int i = 0; i < jsonArr.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr.get(i), Map.class);
			Date exp_date=null;
			Date now=new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			try {
				exp_date = formatter.parse(map.get("exp_date").toString());
			} catch (ParseException e) {
				e.printStackTrace();
				
			}
			long diffSec = (exp_date.getTime() - now.getTime()) / 1000; //초 차이
			long diffDays = diffSec / (24*60*60); //일자수 차이
			
			String repay_method = map.get("repay_method").toString();
				if (repay_method.equals("01")) repay_method = "만기일시상환";
				else if (repay_method.equals("02")) repay_method = "원금균등분할상환";
				else if (repay_method.equals("03")) repay_method = "거치식-원금균등분할상환";
				else if (repay_method.equals("04")) repay_method = "원리금균등분할상환";
				else if (repay_method.equals("05")) repay_method = "거치식-원리금균등분할상환";
				else if (repay_method.equals("06")) repay_method = "만기지정상환-원금균등분할상환";
				else if (repay_method.equals("07")) repay_method = "만기지정상환-원리금균등분할상환";
				else if (repay_method.equals("08")) repay_method = "한도거래";
				else if (repay_method.equals("09")) repay_method = "기타(직접산정)";
				else if (repay_method.equals("10")) repay_method = "거치식 만기지정상환-원금균등분할상환";
				else if (repay_method.equals("11")) repay_method = "거치식 만기지정상환-원리금균등분할상환";
				else if (repay_method.equals("12")) repay_method = "혼합상환방식";
				
				model.addAttribute("period",diffDays);
				model.addAttribute("capitalDetail", new CapitalVo().builder()
					.org_code(org_code)
					.prod_name(map.get("prod_name").toString())
					.org_name(map.get("org_name").toString())
					.account_num(map.get("account_num").toString())
					.balance_amt((long) Double.parseDouble(map.get("balance_amt").toString()))
					.currency_code(map.get("currency_code").toString())
					.last_offered_rate(Double.parseDouble(map.get("last_offered_rate").toString()))
					.next_repay_date(map.get("next_repay_date").toString())
					.loan_principal((long) Double.parseDouble(map.get("loan_principal").toString()))
					.repay_method(repay_method)
					.issue_date(map.get("issue_date").toString())
					.exp_date(map.get("exp_date").toString())
					.repay_date(map.get("repay_date").toString())
	                .build());
		}
		return "/capital/loandetail";
	}
	
	@PostMapping("/capital/transhistory")
	public String transhistory(String org_code, String account_num,String prod_name, String org_name, Model model) {
		model.addAttribute("org_code", org_code);
		model.addAttribute("account_num", account_num);
		model.addAttribute("prod_name", prod_name);
		model.addAttribute("org_name", org_name);
		return "/capital/transhistory";
	}
	
	
}
