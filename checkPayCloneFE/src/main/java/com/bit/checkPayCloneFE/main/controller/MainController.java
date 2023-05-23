package com.bit.checkPayCloneFE.main.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.bit.checkPayCloneFE.bank.model.BankVo;
import com.bit.checkPayCloneFE.capital.model.CapitalVo;
import com.bit.checkPayCloneFE.card.model.CardVo;
import com.bit.checkPayCloneFE.card.model.CardloanVo;
import com.bit.checkPayCloneFE.efin.model.EfinVo;
import com.bit.checkPayCloneFE.invest.model.InvestVo;
import com.bit.checkPayCloneFE.telecom.model.TelecomVo;
import com.bit.checkPayCloneFE.util.TokenCall;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
public class MainController {
	
	@GetMapping("/main")
	public String index(Model model, HttpServletRequest req, HttpServletResponse cookResp) {
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
		HttpHeaders headers = new HttpHeaders();
		TokenCall tkc = new TokenCall();
		// 쿠키에서 토큰 찾기
		String token =tkc.getCookieId(req, "jwt");
		
		// id find
		String id = tkc.findTokenVal(token,"sub");
		
		HttpEntity<MultiValueMap<String, String>> request=null;
		ResponseEntity<String> response=null ;
		ResponseEntity<String> responseCard = null;
		ResponseEntity<String> responseCardLong = null;
		ResponseEntity<String> responseCardShort = null;
		ResponseEntity<String> responseCapital = null;
		ResponseEntity<String> responseAsset = null;
		ResponseEntity<String> responseBankIrp = null;
		ResponseEntity<String> responseEfin = null;
		ResponseEntity<String> responseInvest = null;
		ResponseEntity<String> responseTelecom = null;
		if(token==null)
			return "redirect:/login";
		
		try {
			headers.set("Authorization", "Bearer " + token); // 토큰을 헤더에 넣음 
			request = new HttpEntity<>(parameters, headers);
			response = restTemplate.exchange("http://192.168.240.208:8081/api/bank/memberact/"+id, HttpMethod.GET, request, String.class);
			
			responseCard = restTemplate.exchange("http://192.168.240.208:8081/api/card/membercard/" + id, HttpMethod.GET, request, String.class);
			
			responseCardLong = restTemplate.exchange("http://192.168.240.208:8081/api/card/membercardloanlong/" + id, HttpMethod.GET, request, String.class);
			
			responseCardShort = restTemplate.exchange("http://192.168.240.208:8081/api/card/membercardloanshort/" + id, HttpMethod.GET, request, String.class);

			responseCapital = restTemplate.exchange("http://192.168.240.208:8081/api/capital/membercapital/" + id, HttpMethod.GET, request, String.class);
			
			responseAsset = restTemplate.exchange("http://192.168.240.208:8081/api/asset/main/" + id, HttpMethod.GET, request, String.class);
			
			responseBankIrp = restTemplate.exchange("http://192.168.240.208:8081/api/irp/account/list/"+id+"/b", HttpMethod.GET, request, String.class);
			
			responseEfin = restTemplate.exchange("http://192.168.240.208:8081/api/efin/prepaid/"+id, HttpMethod.GET, request, String.class);

			responseInvest = restTemplate.exchange("http://192.168.240.208:8081/api/invest/stock/list/"+id, HttpMethod.GET, request, String.class);
			
			responseTelecom = restTemplate.exchange("http://192.168.240.208:8081/api/telecom/bill/"+id, HttpMethod.GET, request, String.class);
			
			
		}catch (HttpClientErrorException e) {
			token = tkc.tkcall(token, req, cookResp);
			if(token.equals("back"))
				return "redirect:/login";
			else
				return "redirect:/main";
		}
		
		Gson gson = new Gson();

		List<BankVo> accList1000 = new ArrayList();	// 1000번대 (수신상품)
		List<BankVo> accList2000 = new ArrayList();	// 2000번대 (투자상품)+IRP
		List<BankVo> accList3000 = new ArrayList();	// 3000번대 (대출상품)
		JsonElement parseString = JsonParser.parseString(response.getBody());
		JsonArray jsonArr = (JsonArray) parseString;
		JsonElement IRPparseString = JsonParser.parseString(responseBankIrp.getBody());
        JsonArray IRPjsonArr = (JsonArray) IRPparseString;

		for (int i = 0; i < jsonArr.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr.get(i), Map.class);
			int type_num = Integer.parseInt(map.get("account_type").toString()); // 타입별로 분류
			
			if (type_num < 2000) {
				accList1000.add(new BankVo().builder()
						.currency_code(map.get("currency_code").equals("KRW")?"원":map.get("currency_code").toString())
	                    .org_code(map.get("org_code").toString())
	                    .org_name(map.get("org_name").toString())
	                    .prod_name(map.get("prod_name").toString())
	                    .account_type(map.get("account_type").toString())
	                    .account_num(map.get("account_num").toString())
	                    .is_consent(map.get("is_consent").toString())
	                    .balance_amt(Double.parseDouble(map.get("balance_amt").toString()))
	                    .build());
			} else if (type_num < 3000) {
				accList2000.add(new BankVo().builder()
						.currency_code(map.get("currency_code").equals("KRW")?"원":map.get("currency_code").toString())
	                    .org_code(map.get("org_code").toString())
	                    .org_name(map.get("org_name").toString())
	                    .prod_name(map.get("prod_name").toString())
	                    .account_type(map.get("account_type").toString())
	                    .account_num(map.get("account_num").toString())
	                    .is_consent(map.get("is_consent").toString())
	                    .balance_amt(Double.parseDouble(map.get("balance_amt").toString()))
	                    .build());
			} else if (type_num < 4000) {
				accList3000.add(new BankVo().builder()
						.currency_code(map.get("currency_code").equals("KRW")?"원":map.get("currency_code").toString())
	                    .org_code(map.get("org_code").toString())
	                    .org_name(map.get("org_name").toString())
	                    .prod_name(map.get("prod_name").toString())
	                    .account_type(map.get("account_type").toString())
	                    .account_num(map.get("account_num").toString())
	                    .is_consent(map.get("is_consent").toString())
	                    .balance_amt(Double.parseDouble(map.get("balance_amt").toString()))
	                    .last_offered_rate(Double.parseDouble(String.format("%.2f", Double.parseDouble(map.get("last_offered_rate").toString()))))
	                    .build());
			}
		}
		model.addAttribute("bankAccList1000", accList1000);
		model.addAttribute("bankAccList2000", accList2000);
		model.addAttribute("bankAccList3000", accList3000);
        List<BankVo> accList2001 = new ArrayList(accList2000);	// 2000번대 (IRP 추가)
        for(int i=0;i<IRPjsonArr.size();i++) {
        	Map<String, Object> map = gson.fromJson(IRPjsonArr.get(i), Map.class);
        	accList2001.add(new BankVo().builder()
        			.org_code(map.get("org_code").toString())
        			.prod_name(map.get("prod_name").toString())
        			.account_num(map.get("account_num").toString())
        			.eval_amt(Double.parseDouble(map.get("eval_amt").toString()))
        			.build());
        }
        model.addAttribute("bankIrpList", accList2001);
        // irp+2000 for문으로 irp 추가해서 모델에 싣다
        
		List<CardVo> cardList = new ArrayList();

		JsonElement cardParseString = JsonParser.parseString(responseCard.getBody());

		JsonArray jsonArr1 = (JsonArray) cardParseString;
		for (int i = 0; i < jsonArr1.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr1.get(i), Map.class);
			cardList.add(new CardVo(
					map.get("org_code").toString(), map.get("card_name").toString(),
					map.get("card_id").toString(), map.get("card_type").toString(), map.get("card_num").toString(),
					map.get("img_uri").toString(),
					(int) Double.parseDouble(map.get("sum").toString())));
		}

		model.addAttribute("cardIdList", cardList);
		
		List<CardloanVo> cardloanList = new ArrayList();

		JsonElement cardLongParseString = JsonParser.parseString(responseCardLong.getBody());
		JsonArray jsonArr2 = (JsonArray) cardLongParseString;
		for (int i = 0; i < jsonArr2.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr2.get(i), Map.class);
			cardloanList.add(new CardloanVo(
						map.get("memb_cd").toString(),
						map.get("org_code").toString(),
						(double) Double.parseDouble(map.get("int_rate").toString()),
						(long) Double.parseDouble(map.get("loan_amt").toString()),
						(long) Double.parseDouble(map.get("balance_amt").toString()),
						(long) Double.parseDouble(map.get("int_amt").toString()),
						map.get("loan_name").toString(),map.get("loan_type").toString(),
						map.get("repay_method").toString(),map.get("reg_dttm").toString(),
						map.get("exp_date").toString()
						));
		}

		JsonElement cardShortParseString = JsonParser.parseString(responseCardShort.getBody());
		JsonArray jsonArr3 = (JsonArray) cardShortParseString;
		for (int i = 0; i < jsonArr3.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr3.get(i), Map.class);
			cardloanList.add(new CardloanVo(
					map.get("memb_cd").toString(),
					map.get("org_code").toString(),
					map.get("loan_dtime").toString(),
					map.get("pay_due_date").toString(),
					map.get("reg_dttm").toString(),
					(long) Double.parseDouble(map.get("loan_amt").toString()),
					(long) Double.parseDouble(map.get("balance_amt").toString()),
					(double) Double.parseDouble(map.get("int_rate").toString()),
					map.get("descrption").toString()
					));
		}
		model.addAttribute("cardloanList", cardloanList);


		List<CapitalVo> capitalList = new ArrayList();
		List<CapitalVo> capitalForignList = new ArrayList();
		
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
				capitalList.add(new CapitalVo().builder()
						.org_code(map.get("org_code").toString())
						.account_num(map.get("account_num").toString())
						.prod_name(map.get("prod_name").toString())
						.last_offered_rate(Double.parseDouble(map.get("last_offered_rate").toString()))
						.balance_amt(balace_amt)
						.currency_code(map.get("currency_code").toString())
						.build());
			}else {
				foreignMoney.put(map.get("currency_code").toString(), foreignMoney.get(map.get("currency_code").toString())==null?balace_amt:foreignMoney.get(map.get("currency_code").toString())+balace_amt);
				capitalForignList.add(new CapitalVo().builder()
						.org_code(map.get("org_code").toString())
						.account_num(map.get("account_num").toString())
						.prod_name(map.get("prod_name").toString())
						.last_offered_rate(Double.parseDouble(map.get("last_offered_rate").toString()))
						.balance_amt(balace_amt)
						.currency_code(map.get("currency_code").toString())
						.build());
			}
			
		}
		model.addAttribute("foreignMoney", foreignMoney);
		model.addAttribute("capitalList", capitalList);
		model.addAttribute("capitalForignList", capitalForignList);
		model.addAttribute("capitalKrwSum", capitalKrwSum);
		model.addAttribute("krwCnt", krwCnt);
		
		List<EfinVo> efinList = new ArrayList();
		Map<String, Integer> orgNameList = new HashMap<>();
		JsonElement efinParseString = JsonParser.parseString(responseEfin.getBody());
		JsonArray jsonArr5 = (JsonArray) efinParseString;
		
		// 외화 합치기
        Map<String, Double> efinsumByCurrency= new HashMap<String, Double>();
        
		for (int i = 0; i < jsonArr5.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr5.get(i), Map.class);
			
			if(map.get("account_id")==null) {
				continue;
			}
			else {
				if(map.get("total_balance_amt")==null) {
					efinList.add(
							EfinVo.builder()
							.account_id(map.get("account_id").toString())
							.currency_code(map.get("currency_code").toString())
							.fob_name(map.get("fob_name").toString())
							.org_name_abb(map.get("org_name_abb").toString())
							.org_code(map.get("org_code").toString())
							.fob_id(map.get("fob_id").toString())
							.total_balance_amt(Double.parseDouble(map.get("total_balance_amt").toString()))
							.build()
							);
//							new EfinVo(map.get("account_id").toString()
//							,map.get("currency_code").toString()
//							,map.get("fob_name").toString()
//							,map.get("org_name_abb").toString()
//							,map.get("org_code").toString()
//							,map.get("fob_id").toString()
//							,Double.parseDouble(map.get("total_balance_amt").toString())));
				}
				else {
					String Currency_code= map.get("currency_code").toString();
					if(!Currency_code.equals("KRW")) {
						Double balance_amt= (Double.parseDouble(map.get("total_balance_amt").toString()));
						if (efinsumByCurrency.containsKey(map.get("currency_code").toString()))
							efinsumByCurrency.put(Currency_code, efinsumByCurrency.get(Currency_code)+balance_amt);	// 현재값 + 들어온값
						else 
							efinsumByCurrency.put(Currency_code, balance_amt);	// 들어온값
					}
					efinList.add(
						EfinVo.builder()
						.account_id(map.get("account_id").toString())
						.currency_code(map.get("currency_code").toString())
						.fob_name(map.get("fob_name").toString())
						.org_name_abb(map.get("org_name_abb").toString())
						.org_code(map.get("org_code").toString())
						.fob_id(map.get("fob_id").toString())
						.total_balance_amt(Double.parseDouble(map.get("total_balance_amt").toString()))
						.build()
						);
					if(orgNameList.containsKey(map.get("org_name_abb").toString()))
						orgNameList.put(map.get("org_name_abb").toString(),orgNameList.get(map.get("org_name_abb").toString())+1);
					else
						orgNameList.put(map.get("org_name_abb").toString(),1);
				}
			}
		}
		model.addAttribute("orgNameList",orgNameList);
		model.addAttribute("efinList", efinList);
		model.addAttribute("sumByCurrency", efinsumByCurrency);
		
		
		JsonElement assetParseString = JsonParser.parseString(responseAsset.getBody());
		Map<String,Object> assetMap = gson.fromJson(assetParseString, Map.class);
		model.addAttribute("assetSum", (long) Double.parseDouble(assetMap.get("sum").toString()));
		
		// [S] Invest  
		List<InvestVo> investList = new ArrayList();
		
		JsonElement investParseString = JsonParser.parseString(responseInvest.getBody());
		JsonArray InveJsonArr = (JsonArray) investParseString;
		Map<String, Object> inveMap= new HashMap<>();
		
		for (int i = 0; i < InveJsonArr.size(); i++) {
			Map<String, Object> map = gson.fromJson(InveJsonArr.get(i), Map.class);
				investList.add(new InvestVo().builder()
						.org_code(map.get("org_code").toString())
						.account_num(map.get("account_num").toString())
						.prod_name(map.get("prod_name").toString())
						.prod_type(map.get("prod_type").toString())
						.currency_code(map.get("currency_code").equals("KRW")?"원":map.get("currency_code").toString())
						.holding_num(Double.parseDouble(map.get("holding_num").toString()))
						.eval_amt(Double.parseDouble(map.get("eval_amt").toString()))
						.difference(Double.parseDouble(map.get("difference").toString()))
						.return_rate(Double.parseDouble(map.get("return_rate").toString()))
						.seq((int)(Double.parseDouble(map.get("seq").toString())))
						.build());
				
				
				
		}
		model.addAttribute("investList",investList);
		// [E] Invest  
		
		// [S] Telecom  
		Map<String, String> TelecomNameList = new HashMap<>();
		JsonElement TelecomParseString = JsonParser.parseString(responseTelecom.getBody());
		JsonArray TelecomJsonArr = (JsonArray) TelecomParseString;
		
		for (int i = 0; i < TelecomJsonArr.size(); i++) {
			Map<String, Object> map = gson.fromJson(TelecomJsonArr.get(i), Map.class);
				TelecomNameList.put(map.get("org_name_abb").toString(),map.get("org_code").toString());
		}
		model.addAttribute("telecomNameList",TelecomNameList);
		model.addAttribute("now_time",new Timestamp(System.currentTimeMillis()));
		// [E] Telecom
		
		
		return "/main/main";
	}

	@GetMapping("/main/mainchart")
	public String mainchart(Model model, HttpServletRequest req, HttpServletResponse cookResp) {

		TokenCall tkc = new TokenCall();
		RestTemplate restTemplate = new RestTemplate();
		

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);
//	      ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/api/bank/memberact/"+id, HttpMethod.GET, request, String.class);
		String token =tkc.getCookieId(req, "jwt");
		String id = tkc.findTokenVal(token,"sub");
		
		if(token==null) {
			return "redirect:/login";
		}
	
		if(tkc.findTokenVal(token,"role").equals("ADMIN")) 
			return "redirect:/admin";
		if(token==null)
			return "redirect:/login";
		
		ResponseEntity<String> response =null; 
		ResponseEntity<String> responseCard = null;
		ResponseEntity<String> responseCapital = null;
		ResponseEntity<String> responseCardLong = null;
		ResponseEntity<String> responseCardShort = null;
		ResponseEntity<String> responseAsset = null;
		ResponseEntity<String> responseEfin = null;
		ResponseEntity<String> responseBankIrp = null;
		ResponseEntity<String> responseInvest = null;
		
		try {
			headers.set("Authorization", "Bearer " + token); // 쿠키 헤더에 넣음 
			response= restTemplate.exchange("http://192.168.240.208:8081/api/bank/memberact/" + id,HttpMethod.GET, request, String.class);
			
			responseCard = restTemplate.exchange("http://192.168.240.208:8081/api/card/membercard/" + id, HttpMethod.GET, request, String.class);
			
			responseCardLong = restTemplate.exchange("http://192.168.240.208:8081/api/card/membercardloanlong/" + id, HttpMethod.GET, request, String.class);
			
			responseCardShort = restTemplate.exchange("http://192.168.240.208:8081/api/card/membercardloanshort/" + id, HttpMethod.GET, request, String.class);
			
			responseCapital = restTemplate.exchange("http://192.168.240.208:8081/api/capital/membercapital/" + id, HttpMethod.GET, request, String.class);
			
			responseAsset = restTemplate.exchange("http://192.168.240.208:8081/api/asset/main/" + id, HttpMethod.GET, request, String.class);
			
			responseEfin = restTemplate.exchange("http://192.168.240.208:8081/api/efin/prepaid/"+id, HttpMethod.GET, request, String.class);
			
			responseBankIrp = restTemplate.exchange("http://192.168.240.208:8081/api/irp/account/list/"+id+"/b", HttpMethod.GET, request, String.class);
			
			responseInvest = restTemplate.exchange("http://192.168.240.208:8081/api/invest/stock/list/"+id, HttpMethod.GET, request, String.class);
		}catch (HttpClientErrorException e) {
			token = tkc.tkcall(token, req, cookResp);
			if(token.equals("back"))
				return "redirect:/login";
			else
				return "redirect:/main";
		}
		
		
		Gson gson = new Gson();
		List<BankVo> accList1000 = new ArrayList();// 1000번대 (수신상품)
		List<BankVo> accList2000 = new ArrayList();// 2000번대 (투자상품)
		List<BankVo> accList3000 = new ArrayList();// 3000번대 (대출상품)
		JsonElement parseString = JsonParser.parseString(response.getBody());
		JsonArray jsonArr = (JsonArray) parseString;
		JsonElement IRPparseString = JsonParser.parseString(responseBankIrp.getBody());
        JsonArray IRPjsonArr = (JsonArray) IRPparseString;
		for (int i = 0; i < jsonArr.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr.get(i), Map.class);
			int type_num = Integer.parseInt(map.get("account_type").toString()); // 타입별로 분류
			if (type_num < 2000) {
				accList1000.add(new BankVo().builder()
		        .currency_code(map.get("currency_code").toString())
	                    .org_code(map.get("org_code").toString())
	                    .org_name(map.get("org_name").toString())
	                    .prod_name(map.get("prod_name").toString())
	                    .account_type(map.get("account_type").toString())
	                    .account_num(map.get("account_num").toString())
	                    .is_consent(map.get("is_consent").toString())
	                    .balance_amt(Double.parseDouble(map.get("balance_amt").toString()))
	                    .build());
			} else if (type_num < 3000) {
				accList2000.add(new BankVo().builder()
		        .currency_code(map.get("currency_code").toString())
	                    .org_code(map.get("org_code").toString())
	                    .org_name(map.get("org_name").toString())
	                    .prod_name(map.get("prod_name").toString())
	                    .account_type(map.get("account_type").toString())
	                    .account_num(map.get("account_num").toString())
	                    .is_consent(map.get("is_consent").toString())
	                    .balance_amt(Double.parseDouble(map.get("balance_amt").toString()))
	                    .build());
			} else if (type_num < 4000) {
				accList3000.add(new BankVo().builder()
		        .currency_code(map.get("currency_code").toString())
	                    .org_code(map.get("org_code").toString())
	                    .org_name(map.get("org_name").toString())
	                    .prod_name(map.get("prod_name").toString())
	                    .account_type(map.get("account_type").toString())
	                    .account_num(map.get("account_num").toString())
	                    .is_consent(map.get("is_consent").toString())
	                    .balance_amt(Double.parseDouble(map.get("balance_amt").toString()))
	                    .build());
			}
		}
		model.addAttribute("bankAccList1000", accList1000);
		model.addAttribute("bankAccList2000", accList2000);
		model.addAttribute("bankAccList3000", accList3000);
		List<BankVo> accList2001 = new ArrayList(accList2000);	// 2000번대 (IRP 추가)
        for(int i=0;i<IRPjsonArr.size();i++) {
        	Map<String, Object> map = gson.fromJson(IRPjsonArr.get(i), Map.class);
        	accList2001.add(new BankVo().builder()
        			.org_code(map.get("org_code").toString())
        			.prod_name(map.get("prod_name").toString())
        			.account_num(map.get("account_num").toString())
        			.eval_amt(Double.parseDouble(map.get("eval_amt").toString()))
        			.build());
        }
        model.addAttribute("bankIrpList", accList2001);

		List<CardVo> cardList = new ArrayList();

		JsonElement cardParseString = JsonParser.parseString(responseCard.getBody());

		JsonArray jsonArr1 = (JsonArray) cardParseString;
		for (int i = 0; i < jsonArr1.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr1.get(i), Map.class);
			cardList.add(new CardVo(map.get("org_code").toString(), map.get("card_name").toString(),
					map.get("card_id").toString(), map.get("card_type").toString(), map.get("card_num").toString(),
					map.get("img_uri").toString(),
					(int) Double.parseDouble(map.get("sum").toString())));
		}

		model.addAttribute("cardIdList", cardList);
		
		List<CardloanVo> cardloanList = new ArrayList();
		
		JsonElement cardLongParseString = JsonParser.parseString(responseCardLong.getBody());
		JsonArray jsonArr2 = (JsonArray) cardLongParseString;
		for (int i = 0; i < jsonArr2.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr2.get(i), Map.class);
			cardloanList.add(new CardloanVo(
						map.get("memb_cd").toString(),
						map.get("org_code").toString(),
						(double) Double.parseDouble(map.get("int_rate").toString()),
						(long) Double.parseDouble(map.get("loan_amt").toString()),
						(long) Double.parseDouble(map.get("balance_amt").toString()),
						(long) Double.parseDouble(map.get("int_amt").toString()),
						map.get("loan_name").toString(),map.get("loan_type").toString(),
						map.get("repay_method").toString(),map.get("reg_dttm").toString(),
						map.get("exp_date").toString()
						));
		}

		JsonElement cardShortParseString = JsonParser.parseString(responseCardShort.getBody());
		JsonArray jsonArr3 = (JsonArray) cardShortParseString;
		for (int i = 0; i < jsonArr3.size(); i++) {
			Map<String, Object> map = gson.fromJson(jsonArr3.get(i), Map.class);
			cardloanList.add(new CardloanVo(
					map.get("memb_cd").toString(),
					map.get("org_code").toString(),
					map.get("loan_dtime").toString(),
					map.get("pay_due_date").toString(),
					map.get("reg_dttm").toString(),
					(long) Double.parseDouble(map.get("loan_amt").toString()),
					(long) Double.parseDouble(map.get("balance_amt").toString()),
					(double) Double.parseDouble(map.get("int_rate").toString()),
					map.get("descrption").toString()
					));
		}
		
			model.addAttribute("cardloanList", cardloanList);
		
			List<CapitalVo> capitalList = new ArrayList();
			List<CapitalVo> capitalForignList = new ArrayList();
			
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
					capitalList.add(new CapitalVo().builder()
							.org_code(map.get("org_code").toString())
							.account_num(map.get("account_num").toString())
							.prod_name(map.get("prod_name").toString())
							.last_offered_rate(Double.parseDouble(map.get("last_offered_rate").toString()))
							.balance_amt(balace_amt)
							.currency_code(map.get("currency_code").toString())
							.build());
				}else {
					foreignMoney.put(map.get("currency_code").toString(), foreignMoney.get(map.get("currency_code").toString())==null?balace_amt:foreignMoney.get(map.get("currency_code").toString())+balace_amt);
					capitalForignList.add(new CapitalVo().builder()
							.org_code(map.get("org_code").toString())
							.account_num(map.get("account_num").toString())
							.prod_name(map.get("prod_name").toString())
							.last_offered_rate(Double.parseDouble(map.get("last_offered_rate").toString()))
							.balance_amt(balace_amt)
							.currency_code(map.get("currency_code").toString())
							.build());
				}
				
			}
			model.addAttribute("foreignMoney", foreignMoney);
			model.addAttribute("capitalList", capitalList);
			model.addAttribute("capitalForignList", capitalForignList);
			model.addAttribute("capitalKrwSum", capitalKrwSum);
			model.addAttribute("krwCnt", krwCnt);	
			
			List<EfinVo> efinList = new ArrayList();
			Map<String, Integer> orgNameList = new HashMap<>();
			JsonElement efinParseString = JsonParser.parseString(responseEfin.getBody());
			JsonArray jsonArr5 = (JsonArray) efinParseString;
			
			// 외화 합치기
	        Map<String, Double> efinsumByCurrency= new HashMap<String, Double>();
	        
			for (int i = 0; i < jsonArr5.size(); i++) {
				Map<String, Object> map = gson.fromJson(jsonArr5.get(i), Map.class);
				
				if(map.get("account_id")==null) {
					continue;
				}
				else {
					String Currency_code= map.get("currency_code").toString();
					
					if(!Currency_code.equals("KRW")) {
						Double balance_amt= (Double.parseDouble(map.get("total_balance_amt").toString()));
						if (efinsumByCurrency.containsKey(map.get("currency_code").toString()))
							efinsumByCurrency.put(Currency_code, efinsumByCurrency.get(Currency_code)+balance_amt);	// 현재값 + 들어온값
						else 
							efinsumByCurrency.put(Currency_code, balance_amt);	// 들어온값
					}
					
					efinList.add(
							EfinVo.builder()
							.account_id(map.get("account_id").toString())
							.currency_code(map.get("currency_code").toString())
							.fob_name(map.get("fob_name").toString())
							.org_name_abb(map.get("org_name_abb").toString())
							.org_code(map.get("org_code").toString())
							.fob_id(map.get("fob_id").toString())
							.total_balance_amt(Double.parseDouble(map.get("total_balance_amt").toString()))
							.build()
							);
					if(orgNameList.containsKey(map.get("org_name_abb").toString()))
						orgNameList.put(map.get("org_name_abb").toString(),orgNameList.get(map.get("org_name_abb").toString())+1);
					else
						orgNameList.put(map.get("org_name_abb").toString(),1);

				}
			}
		model.addAttribute("orgNameList",orgNameList);
		model.addAttribute("efinList", efinList);
		model.addAttribute("sumByCurrency", efinsumByCurrency);
		
		// [S] Invest  
		List<InvestVo> investList = new ArrayList();
		
		JsonElement investParseString = JsonParser.parseString(responseInvest.getBody());
		JsonArray InveJsonArr = (JsonArray) investParseString;
		Map<String, Object> inveMap= new HashMap<>();
		
		for (int i = 0; i < InveJsonArr.size(); i++) {
			Map<String, Object> map = gson.fromJson(InveJsonArr.get(i), Map.class);
				investList.add(new InvestVo().builder()
						.org_code(map.get("org_code").toString())
						.account_num(map.get("account_num").toString())
						.prod_name(map.get("prod_name").toString())
						.prod_type(map.get("prod_type").toString())
						.currency_code(map.get("currency_code").equals("KRW")?"원":map.get("currency_code").toString())
						.holding_num(Double.parseDouble(map.get("holding_num").toString()))
						.eval_amt(Double.parseDouble(map.get("eval_amt").toString()))
						.difference(Double.parseDouble(map.get("difference").toString()))
						.return_rate(Double.parseDouble(map.get("return_rate").toString()))
						.seq((int)(Double.parseDouble(map.get("seq").toString())))
						.build());
				
				
				
		}
		model.addAttribute("investList",investList);
		// [E] Invest
			
			
		JsonElement assetParseString = JsonParser.parseString(responseAsset.getBody());
		Map<String,Object> map = gson.fromJson(assetParseString, Map.class);
		model.addAttribute("assetSum", (long) Double.parseDouble(map.get("sum").toString()));
		
		return "/main/mainchart";
	}
}