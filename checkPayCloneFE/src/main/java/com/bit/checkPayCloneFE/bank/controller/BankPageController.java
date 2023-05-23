package com.bit.checkPayCloneFE.bank.controller;

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

import com.bit.checkPayCloneFE.bank.model.BankReqVo;
import com.bit.checkPayCloneFE.bank.model.BankVo;
import com.bit.checkPayCloneFE.bank.model.IrpVo;
import com.bit.checkPayCloneFE.capital.model.CapitalVo;
import com.bit.checkPayCloneFE.util.TokenCall;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
public class BankPageController {
	
	@PostMapping("/bank/deposittrans")
	public String depositDetail(String org_code, String account_num, Model model) {// 수신계좌 거래내역페이지
		BankReqVo bankReqVo = new BankReqVo(org_code, account_num);
		model.addAttribute("bankReqVo", bankReqVo);
		return "/bank/depositTrans";
	}
	@PostMapping("/bank/investdetail")
	public String investDetail(String org_code, String account_num, Model model) {// 투자상품 상세 페이지
		BankReqVo bankReqVo = new BankReqVo(org_code, account_num);
		model.addAttribute("bankReqVo", bankReqVo);
		return "/bank/investDetail";
	}
	@PostMapping("/bank/investtrans")
	public String investTrans(String org_code, String account_num, Model model) {// 투자상품 거래내역페이지
		BankReqVo bankReqVo = new BankReqVo(org_code, account_num);
		model.addAttribute("bankReqVo", bankReqVo);
		return "/bank/investTrans";
	}
	
	@PostMapping("/bank/irpdetail")
	public String irpDetail(String org_code, String account_num, Model model) {// IRP 상세 페이지
		BankReqVo bankReqVo = new BankReqVo(org_code, account_num);
		model.addAttribute("bankReqVo", bankReqVo);
		return "/bank/irpDetail";
	}
	
	@PostMapping("/bank/irptrans")
	public String irpTrans(String org_code, String account_num, Model model) {// IRP 상세 페이지
		BankReqVo bankReqVo = new BankReqVo(org_code, account_num);
		model.addAttribute("bankReqVo", bankReqVo);
		return "/bank/irpTrans";
	}
	
	@PostMapping("/bank/irpeachproducts")
	public String irpEachProducts(String org_code, String account_num, Model model) {// IRP 상세 페이지
		BankReqVo bankReqVo = new BankReqVo(org_code, account_num);
		model.addAttribute("bankReqVo", bankReqVo);
		return "/bank/irpEachProd";
	}
	
	
	@GetMapping("/bank")
	public String bank(Model model, HttpServletRequest req, HttpServletResponse cookResp) {
		RestTemplate restTemplate = new RestTemplate();
	    MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
	    HttpHeaders headers = new HttpHeaders();
		TokenCall tkc = new TokenCall();
		// 쿠키에서 토큰 찾기
		String token =tkc.getCookieId(req, "jwt");
		// id find
		String id = tkc.findTokenVal(token,"sub");
		
		if(token==null) {
//			return "redirect:/login?err=ture";
			return "redirect:/login";
		}
	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);
	    ResponseEntity<String> response = null;
	    ResponseEntity<String> response2 = null;
//	    ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/api/bank/memberact/"+id, HttpMethod.GET, request, String.class);
		try {
			headers.set("Authorization", "Bearer " + token); // 쿠키 헤더에 넣음 
	      	response = restTemplate.exchange("http://192.168.240.208:8081/api/bank/memberact/"+id, HttpMethod.GET, request, String.class);
//			response= restTemplate.exchange("http://localhost:8081/api/bank/memberact/" + id,HttpMethod.GET, request, String.class);
	      	response2 = restTemplate.exchange("http://192.168.240.208:8081/api/irp/account/list/"+id+"/b", HttpMethod.GET, request, String.class);
//	      	response = restTemplate.exchange("http://localhost:8081/api/irp/account/list/"+id+"/b", HttpMethod.GET, request, String.class);
		}catch (HttpClientErrorException e) {
			token=tkc.tokenCall(token,req,cookResp);	
			// 쿠키안 토큰 권한 X
			if(token.equals("back"))
				return "redirect:/login";
			else
				return "redirect:/main";
		}
	    
	    Gson gson = new Gson();
	    List<BankVo> accList1001 = new ArrayList();	//1000번대 (수신상품)
        List<BankVo> accList1002 = new ArrayList();	//1002,3번대 (예적금)
        List<BankVo> accList2000 = new ArrayList();	//2000번대 (투자상품)
        List<BankVo> accList3000 = new ArrayList();	//3000번대 (대출상품)
        List<IrpVo> irp = new ArrayList();			//irp (퇴직연금)
        JsonElement parseString = JsonParser.parseString(response.getBody());
        JsonArray jsonArr = (JsonArray) parseString;
        // IRP
        JsonElement IRPparseString = JsonParser.parseString(response2.getBody());
        JsonArray IRPjsonArr = (JsonArray) IRPparseString;
        // 화폐 합치기
        Map<String, Double> sumByCurrency= new HashMap<String, Double>();
        for(int i=0;i<IRPjsonArr.size();i++) {
        	Map<String, Object> map = gson.fromJson(IRPjsonArr.get(i), Map.class);
        	irp.add(new IrpVo(map.get("org_code").toString(),
        			map.get("prod_name").toString(),
        			map.get("account_num").toString(),
        			Double.parseDouble(map.get("eval_amt").toString())
        			));
        }
        
        for(int i=0;i<jsonArr.size();i++) {
            Map<String, Object> map = gson.fromJson(jsonArr.get(i), Map.class);
            int type_num = Integer.parseInt(map.get("account_type").toString()); // 타입별로 분류
            String Currency_code= map.get("currency_code").toString();
            if(type_num <3000 && !(Currency_code.equals("KRW")) ){
//            	Long balance_amt= Long.parseLong((Double.parseDouble(map.get("balance_amt").toString())));
            	Double balance_amt= (Double.parseDouble(map.get("balance_amt").toString()));
            	if (sumByCurrency.containsKey(map.get("currency_code").toString()))
            	{
            		sumByCurrency.put(Currency_code, sumByCurrency.get(Currency_code)+balance_amt);	// 현재값 + 들어온값
            	}
            	else 
            	{
            		sumByCurrency.put(Currency_code, balance_amt);	// 들어온값
            	}
            }
            
            //map.containsKey("KEY1")
            if(type_num <1002) // 입출금 1001
            {
            	accList1001.add(new BankVo().builder()
            				.currency_code(map.get("currency_code").equals("KRW")?"원":map.get("currency_code").toString())
            				.org_code(map.get("org_code").toString())
        					.org_name(map.get("org_name").toString())
        					.prod_name(map.get("prod_name").toString())
        					.account_type(map.get("account_type").toString())
        					.account_num(map.get("account_num").toString())
        					.is_consent(map.get("is_consent").toString())
        					.balance_amt(Double.parseDouble(map.get("balance_amt").toString()))
        					.build());
            	
            }
            else if (type_num <1004) // 예적금 1002, 1003
            {
            	accList1002.add(new BankVo().builder()
            			.currency_code(map.get("currency_code").equals("KRW")?"원":map.get("currency_code").toString())
        				.org_code(map.get("org_code").toString())
    					.org_name(map.get("org_name").toString())
    					.prod_name(map.get("prod_name").toString())
    					.account_type(map.get("account_type").toString())
    					.account_num(map.get("account_num").toString())
    					.is_consent(map.get("is_consent").toString())
    					.balance_amt(Double.parseDouble(map.get("balance_amt").toString()))
    					.build());
            }
            else if (type_num <3000) // 투자상품 2000~3000
            {
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
            }
            else if (type_num < 4000) // 대출 3100, 3150, 3170 등등
            {
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
         model.addAttribute("bankAccList1001",accList1001);
         model.addAttribute("bankAccList1002",accList1002);
         model.addAttribute("bankAccList2000",accList2000);
         model.addAttribute("bankAccList3000",accList3000);
         model.addAttribute("irpList",irp);
         model.addAttribute("sumByCurrency", sumByCurrency);
	    return "/bank/bank";
	}

	@PostMapping("/bank/bankloandetail")
	public String bankloandetail(String org_code, String account_num, Model model, HttpServletRequest req, HttpServletResponse cookResp) {
		
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
			responseCapital = restTemplate.exchange("http://192.168.240.208:8081/api/bank/bankcapitaldetail/"+id+"/"+org_code+"/"+account_num, HttpMethod.GET, request, String.class);
//			responseCapital = restTemplate.exchange("http://localhost:8081/api/bank/bankcapitaldetail/"+id+"/"+org_code+"/"+account_num, HttpMethod.GET, request, String.class);
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
		String minus = "";
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
				minus = map.get("is_minus").toString();
				
		}
		if ( minus.equals("N"))
			return "/capital/loandetail";
		else 
			return "/bank/minusdetail";
	}
	
	
}
