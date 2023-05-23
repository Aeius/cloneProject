package com.bit.checkPayCloneFE.card.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.bit.checkPayCloneFE.card.model.CardBillsVo;
import com.bit.checkPayCloneFE.card.model.CardVo;
import com.bit.checkPayCloneFE.card.model.CardloanVo;
import com.bit.checkPayCloneFE.util.TokenCall;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
public class CardPageController {
	
	RestTemplate restTemplate = new RestTemplate();
	MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

	ResponseEntity<String> response = null;
	ResponseEntity<String> responseCardLong = null;
	ResponseEntity<String> responseCardShort = null;
	Gson gson = new Gson();
	
	public String cardTokenCall(HttpServletRequest req, HttpServletResponse cookResp) {
		TokenCall tkc = new TokenCall();
		// 쿠키에서 토큰 찾기
		String token = tkc.getCookieId(req, "jwt");

		// id find
		String id = tkc.findTokenVal(token, "sub");

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);

		if (token == null) {
			return "redirect:/login";
		}
		if(tkc.findTokenVal(token,"role").equals("ADMIN"))
	        return "redirect:/admin";
		try {
			headers.set("Authorization", "Bearer " + token); // 쿠키 헤더에 넣음
			response = restTemplate.exchange("http://192.168.240.208:8081/api/card/membercard/" + id, HttpMethod.GET, request, String.class);
//			response = restTemplate.exchange("http://localhost:8081/api/card/membercard/"+id, HttpMethod.GET, request, String.class);

			responseCardLong = restTemplate.exchange("http://192.168.240.208:8081/api/card/membercardloanlong/" + id, HttpMethod.GET, request, String.class);
//			responseCardLong = cardRestTemplate.exchange("http://localhost:8081/api/card/membercardloanlong/" + id, HttpMethod.GET, cardRequest, String.class);

			responseCardShort = restTemplate.exchange("http://192.168.240.208:8081/api/card/membercardloanshort/" + id, HttpMethod.GET, request, String.class);
//			responseCardShort = cardRestTemplate.exchange("http://localhost:8081/api/card/membercardloanshort/" + id, HttpMethod.GET, cardRequest, String.class);
		} catch (HttpClientErrorException e) {
			token = tkc.tkcall(token, req, cookResp);
			
			if (token.equals("back"))
				return "redirect:/login";
			else
				return "redirect:/main";
		}
		return token;
	}
	
	@GetMapping("/card")
	public String card(Model model, HttpServletRequest req, HttpServletResponse cookResp) {
		
		cardTokenCall(req, cookResp);
		
		List<CardVo> cardList01 = new ArrayList<CardVo>();// 01번대 (체크)
		List<CardVo> cardList02 = new ArrayList<CardVo>();// 02번대 (신용)
		JsonElement parseString = JsonParser.parseString(response.getBody());
		JsonArray jsonArr = (JsonArray) parseString;

		for (int i = 0; i < jsonArr.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr.get(i), Map.class);
			int type_num = Integer.parseInt(map.get("card_type").toString()); // 타입별로 분류
			if (type_num == 1) // 체크카드
			{
				cardList01.add(new CardVo(map.get("org_code").toString(), map.get("card_name").toString(),
						map.get("card_id").toString(), map.get("card_type").toString(), map.get("card_num").toString(),
						map.get("img_uri").toString(), (int) Double.parseDouble(map.get("sum").toString())));
			} else if (type_num == 2) // 신용카드
			{
				cardList02.add(new CardVo(map.get("org_code").toString(), map.get("card_name").toString(),
						map.get("card_id").toString(), map.get("card_type").toString(), map.get("card_num").toString(),
						map.get("img_uri").toString(), (int) Double.parseDouble(map.get("sum").toString())));
			}

		}
		model.addAttribute("card01", cardList01);
		model.addAttribute("card02", cardList02);

		List<CardloanVo> cardloanList = new ArrayList<>();
		JsonElement cardLongParseString = JsonParser.parseString(responseCardLong.getBody());
		JsonArray jsonArr2 = (JsonArray) cardLongParseString;
		for (int i = 0; i < jsonArr2.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr2.get(i), Map.class);
			cardloanList.add(new CardloanVo(map.get("memb_cd").toString(), map.get("org_code").toString(),
					(double) Double.parseDouble(map.get("int_rate").toString()),
					(long) Double.parseDouble(map.get("loan_amt").toString()),
					(long) Double.parseDouble(map.get("balance_amt").toString()),
					(long) Double.parseDouble(map.get("int_amt").toString()), map.get("loan_name").toString(),
					map.get("loan_type").toString(), map.get("repay_method").toString(), map.get("reg_dttm").toString(),
					map.get("exp_date").toString()));
		}

		JsonElement cardShortParseString = JsonParser.parseString(responseCardShort.getBody());
		JsonArray jsonArr3 = (JsonArray) cardShortParseString;
		for (int i = 0; i < jsonArr3.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr3.get(i), Map.class);
			cardloanList.add(new CardloanVo(map.get("memb_cd").toString(), map.get("org_code").toString(),
					map.get("loan_dtime").toString(), map.get("pay_due_date").toString(),
					map.get("reg_dttm").toString(), (long) Double.parseDouble(map.get("loan_amt").toString()),
					(long) Double.parseDouble(map.get("balance_amt").toString()),
					(double) Double.parseDouble(map.get("int_rate").toString()), map.get("descrption").toString()));
		}

		model.addAttribute("cardloanList", cardloanList);

		return "card/card";
	}

	@PostMapping("/card/payinfo/detail")
	public String cardPayInfo(String card_id, Model model) {
		CardVo cardVo = new CardVo(card_id);
		model.addAttribute("cardVo", cardVo);

		return "card/cardPayInfo";
	}

	@PostMapping("/card/loanlong")
	public String cardLoanLong(String loan_name, HttpServletRequest req, HttpServletResponse cookResp, Model model) {
		
		cardTokenCall(req, cookResp);
		
		List<CardloanVo> cardloanList = new ArrayList<>();
		JsonElement cardLongParseString = JsonParser.parseString(responseCardLong.getBody());
		JsonArray jsonArr = (JsonArray) cardLongParseString;
		for (int i = 0; i < jsonArr.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr.get(i), Map.class);
			if (map.get("loan_name").toString().equals(loan_name)) {
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
				
				model.addAttribute("period",diffDays);
				cardloanList.add(new CardloanVo(map.get("memb_cd").toString(), map.get("org_code").toString(),
						(double) Double.parseDouble(map.get("int_rate").toString()),
						(long) Double.parseDouble(map.get("loan_amt").toString()),
						(long) Double.parseDouble(map.get("balance_amt").toString()),
						(long) Double.parseDouble(map.get("int_amt").toString()), map.get("loan_name").toString(),
						map.get("loan_type").toString(), repay_method, map.get("reg_dttm").toString(),
						map.get("exp_date").toString()));
			}

		}
		model.addAttribute("cardloanLongList1", cardloanList);

		return "card/cardLoanLong";
	}

	@PostMapping("/card/loanshort")
	public String cardLoanShort(String loan_dtime, HttpServletRequest req, HttpServletResponse cookResp, Model model) {
		
		cardTokenCall(req, cookResp);

		List<CardloanVo> cardloanList = new ArrayList<>();
		JsonElement cardShortParseString = JsonParser.parseString(responseCardShort.getBody());
		JsonArray jsonArr = (JsonArray) cardShortParseString;
		for (int i = 0; i < jsonArr.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr.get(i), Map.class);
			if (map.get("loan_dtime").toString().equals(loan_dtime)) {
				cardloanList.add(new CardloanVo(map.get("memb_cd").toString(), map.get("org_code").toString(),
						map.get("loan_dtime").toString(), map.get("pay_due_date").toString(),
						map.get("reg_dttm").toString(), (long) Double.parseDouble(map.get("loan_amt").toString()),
						(long) Double.parseDouble(map.get("balance_amt").toString()),
						(double) Double.parseDouble(map.get("int_rate").toString()), map.get("descrption").toString()));
			}

		}
		model.addAttribute("cardloanShortList1", cardloanList);

		return "card/cardLoanShort";
	}

	// 결제 예정 금액
	@GetMapping("card/cardBills")
	public String cardBills() {
		return "card/cardBills";
	}
	
	// 청구 상세
	@PostMapping("card/cardBills/cardBillsInfo")
	public String payBillsInfo(String org_code, String year_month, String charge_amt, Model model) {
		CardBillsVo cardBillsVo = new CardBillsVo(org_code, year_month, charge_amt);
		model.addAttribute("cardbills", cardBillsVo);
		return "card/cardBillsInfo";
	}
	
	// 청구 결제 내역
	@PostMapping("card/cardBills/payBills")
	public String payBills(String org_code, String year_month, String charge_amt, Model model) {
		CardBillsVo cardBillsVo = new CardBillsVo(org_code, year_month, charge_amt);
		model.addAttribute("cardbills", cardBillsVo);
		return "card/cardPayBills";
	}
	
	// 청구 결제 내역 상세
	@GetMapping("card/cardBills/payBillsDetail")
	public String payBillsDetail(String org_code, String year_month, String charge_amt, Model model) {
		CardBillsVo cardBillsVo = new CardBillsVo(org_code, year_month, charge_amt);
		model.addAttribute("cardbills", cardBillsVo);
		return "card/cardPayBillsDetail";
	}
	
	// 청구 할부 상세
	@PostMapping("card/cardBills/cardBillsHalInfo")
	public String payBillsHalInfo(String org_code, String year_month, String charge_amt, Model model) {
		CardBillsVo cardBillsVo = new CardBillsVo(org_code, year_month, charge_amt);
		model.addAttribute("cardbills", cardBillsVo);
		return "card/cardBillsHal";
	}
	
	// 카드 포인트
	@PostMapping("card/cardBills/cardPoint")
	public String cardPoint(String org_code, String year_month, String charge_amt, Model model) {
		CardBillsVo cardBillsVo = new CardBillsVo(org_code, year_month, charge_amt);
		model.addAttribute("cardbills", cardBillsVo);
		return "card/cardPoint";
	}
	
	// 카드 리볼빙
	@PostMapping("card/cardBills/cardRevolving")
	public String cardRevolving(String org_code, String year_month, String charge_amt, Model model) {
		CardBillsVo cardBillsVo = new CardBillsVo(org_code, year_month, charge_amt);
		model.addAttribute("cardbills", cardBillsVo);
		return "card/cardRevolving";
	}
	
}
