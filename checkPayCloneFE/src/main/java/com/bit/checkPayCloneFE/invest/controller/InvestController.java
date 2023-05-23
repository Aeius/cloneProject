package com.bit.checkPayCloneFE.invest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/invest")
public class InvestController {
	@GetMapping("")
	public String index() {
		return "invest/D1000";
	}
	
	@GetMapping("/account")
	public String account() {
		return "invest/D1100";
	}
	@GetMapping("/irp/account")
	public String IRPaccount() {
		return "invest/D1103";
	}
	
	@GetMapping("/accountHis")
	public String accountHis() {
		return "invest/D1110";
	}
	
	@GetMapping("/irp/accountHis")
	public String IRPaccountHis() {
		return "invest/D1124";
	}
	
	@GetMapping("/irp/products")
	public String IRPproducts() {
		return "invest/D1150";
	}
	
	@GetMapping("/product")
	public String list() {
		return "invest/D1200";
	}
	
	@GetMapping("/stock")
	public String stock() {
		return "invest/D1300";
	}
}
