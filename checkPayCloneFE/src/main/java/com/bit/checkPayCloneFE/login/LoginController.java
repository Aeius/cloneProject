package com.bit.checkPayCloneFE.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	
	@GetMapping("/")
	public String init() {
		return "login/login";
	}
	
	@GetMapping("/login")
	public String index() {
//	public String index(HttpServletRequest req) {
//		Cookie[] cookies =req.getCookies();
//	      for(Cookie c : cookies) {
//	    	  System.out.println(c.getName()+":"+c.getValue());
//	      }
		return "login/login";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "login/signup";
	}
	
}
