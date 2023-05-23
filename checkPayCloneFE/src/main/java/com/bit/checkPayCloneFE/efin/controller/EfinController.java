package com.bit.checkPayCloneFE.efin.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.bit.checkPayCloneFE.efin.model.AccountVo;
import com.bit.checkPayCloneFE.efin.model.EfinVo;
import com.bit.checkPayCloneFE.util.TokenCall;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
@RequestMapping("/efin")
public class EfinController {
   
   
   
   @GetMapping("/prepaidList")
   public String prepaidList(Model model, HttpServletRequest req, HttpServletResponse cookResp) {
      RestTemplate restTemplate = new RestTemplate();
      MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
      
      HttpHeaders headers = new HttpHeaders();
      TokenCall tkc = new TokenCall();
      String token =tkc.getCookieId(req, "jwt");
      ResponseEntity<String> response = null;
      HttpEntity<MultiValueMap<String, String>> request=null;
      
      if(token==null)
         return "redirect:/login";
      String id = tkc.findTokenVal(token,"sub");
      try {
         headers.set("Authorization", "Bearer " + token); // 토큰을 헤더에 넣음
         request = new HttpEntity<>(parameters, headers);
//         response = restTemplate.exchange("http://localhost:8081/api/efin/prepaid/"+id, HttpMethod.GET, request, String.class);
         response = restTemplate.exchange("http://192.168.240.208:8081/api/efin/prepaid/"+id, HttpMethod.GET, request, String.class);
      }catch (HttpClientErrorException e) {
         token = tkc.tkcall(token, req, cookResp);
         if(token.equals("back"))
            return "redirect:/login";
         else
            e.printStackTrace();
            return "redirect:/main";
      }
      
      Gson gson = new Gson();
      List<EfinVo> efinList = new ArrayList();
      
      Map<String, Integer> orgNameList = new HashMap<>();
      JsonElement efinParseString = JsonParser.parseString(response.getBody());
      JsonArray jsonArr1 = (JsonArray) efinParseString;
      
      // 외화 합치기
        Map<String, Double> sumByCurrency= new HashMap<String, Double>();
        
      for (int i = 0; i < jsonArr1.size(); i++) {
         Map<String, Object> map = gson.fromJson(jsonArr1.get(i), Map.class);
         
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
						.total_balance_amt(Double.parseDouble("0"))
						.build()
						);
//						new EfinVo(map.get("account_id").toString()
//						,map.get("currency_code").toString()
//						,map.get("fob_name").toString()
//						,map.get("org_name_abb").toString()
//						,map.get("org_code").toString()
//						,map.get("fob_id").toString()
//						,Double.parseDouble(map.get("total_balance_amt").toString())));
            }
            else {
               String Currency_code= map.get("currency_code").toString();
               if(!Currency_code.equals("KRW")) {
                  Double balance_amt= (Double.parseDouble(map.get("total_balance_amt").toString()));
                  if (sumByCurrency.containsKey(map.get("currency_code").toString()))
                        sumByCurrency.put(Currency_code, sumByCurrency.get(Currency_code)+balance_amt);   // 현재값 + 들어온값
                  else 
                        sumByCurrency.put(Currency_code, balance_amt);   // 들어온값
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
//						new EfinVo(map.get("account_id").toString()
//						,map.get("currency_code").toString()
//						,map.get("fob_name").toString()
//						,map.get("org_name_abb").toString()
//						,map.get("org_code").toString()
//						,map.get("fob_id").toString()
//						,Double.parseDouble(map.get("total_balance_amt").toString())));
               if(orgNameList.containsKey(map.get("org_name_abb").toString()))
                  orgNameList.put(map.get("org_name_abb").toString(),orgNameList.get(map.get("org_name_abb").toString())+1);
               else
                  orgNameList.put(map.get("org_name_abb").toString(),1);
            }
         }
      }
      
      
      List<AccountVo> accountIdList = new ArrayList();
      List<AccountVo> orgList = new ArrayList();
      for(String key : orgNameList.keySet()) {
         try {
            headers.set("Authorization", "Bearer " + token); // 토큰을 헤더에 넣음
            request = new HttpEntity<>(parameters, headers);
//            response = restTemplate.exchange("http://localhost:8081/api/efin/prepaid/accountid/"+id+"/"+key, HttpMethod.GET, request, String.class);
            response = restTemplate.exchange("http://192.168.240.208:8081/api/efin/prepaid/accountid/"+id+"/"+key, HttpMethod.GET, request, String.class);
         }catch (HttpClientErrorException e) {
            token = tkc.tkcall(token, req, cookResp);
            if(token.equals("back"))
               return "redirect:/login";
            else
               e.printStackTrace();
               return "redirect:/main";
         }
         
         gson = new Gson();
         
         JsonElement orgNameParseString = JsonParser.parseString(response.getBody());
         JsonArray jsonArr2 = (JsonArray) orgNameParseString;
         
         for (int i2 = 0; i2 < jsonArr2.size(); i2++) {
            Map<String, Object> map = gson.fromJson(jsonArr2.get(i2), Map.class);
            if(map.get("account_id")==null) {
               continue;
            }
            accountIdList.add(new AccountVo(map.get("account_id").toString()
                  ,map.get("org_name_abb").toString()
                  ,map.get("org_code").toString()
                  ,map.get("sub_key").toString()
                  ));
         }
      }
      
      
      model.addAttribute("accountIdList",accountIdList);
      model.addAttribute("orgNameList",orgNameList);
      model.addAttribute("efinList", efinList);
      model.addAttribute("sumByCurrency", sumByCurrency);
      return "/efin/prepaidList";
   }
   
   
   @PostMapping("/prepaid/transactions")
	public String prepaidTransList(String org_code, String fob_id, Model model) {
		EfinVo efinVo = EfinVo.builder().org_code(org_code).fob_id(fob_id).build();
		model.addAttribute("efinVo", efinVo);
		return "/efin/P2100";
	}
   
   @PostMapping("/paid/transactions")
   public String paidTransList(String org_code, String sub_key, Model model) {
	   EfinVo efinVo = EfinVo.builder().org_code(org_code).sub_key(sub_key).build();
	   model.addAttribute("efinVo", efinVo);
	   return "/efin/P1200";
   }
   
   //--컨트롤러 추가는 이 위쪽으로---------
   @RequestMapping("/{path1}")
   public String newmapping(@PathVariable String path1) {
      return "efin/"+path1;
   }
}