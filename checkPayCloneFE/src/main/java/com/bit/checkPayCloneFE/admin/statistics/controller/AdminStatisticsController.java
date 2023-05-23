package com.bit.checkPayCloneFE.admin.statistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/statistics")
public class AdminStatisticsController {
	
	@GetMapping("/")
	public String index() {
		return "/admin/statistics/index";
	}
}
