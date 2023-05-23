package com.bit.checkPayCloneFE.admin.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {

	@GetMapping("/")
	public String index() {
		
		return "/admin/member/index";
	}
	
	@GetMapping("/{member_id}")
	public String detail(@PathVariable("member_id") String member_id) {
		
		return "/admin/member/detail";
	}
}
