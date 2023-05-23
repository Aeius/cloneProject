package com.bit.checkPayCloneFE.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class mypageContreller {
	
	@RequestMapping("/")
	public String main() {
		return "mypage/YY1000";
	}
	
	// 공지사항
	@RequestMapping("/notice")
	public String notice() {
		return "mypage/YY1400";
	}
	
	// 고객센터
	@RequestMapping("/service")
	public String service() {
		return "mypage/YY1700";
	}
	
	@RequestMapping("/terms/credit-info")
	public String TermsOfUseAndPolicies() {
		return "mypage/terms/credit_info/credit_info";
	}
	
	@RequestMapping("/{path1}")
	public String newmapping(@PathVariable String path1) {
		return "mypage/"+path1;
	}
	
	
}
