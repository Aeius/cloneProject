package com.bit.checkPayCloneFE.admin.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminNoticeController {

	@GetMapping("/admin/notice")
	public String index() {
		return "/admin/notice/index";
	}
	
	@GetMapping("/admin/notice/detail")
	public String detail(int noticeNum) {
		return "/admin/notice/detail";
	}
	
	@GetMapping("/admin/notice/add")
	public String add() {
		return "/admin/notice/add";
	}
}
