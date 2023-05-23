package com.bit.checkPayCloneFE.admin.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLoginController {

	@GetMapping("/admin")
	public String redirectLoginPage() {
		return "redirect:/admin/login";
	}
	
	@GetMapping("/admin/login")
	public String login() {
		return "/admin/login/login";
	}
}
