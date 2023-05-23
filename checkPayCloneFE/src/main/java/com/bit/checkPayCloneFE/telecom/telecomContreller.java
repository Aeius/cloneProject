package com.bit.checkPayCloneFE.telecom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit.checkPayCloneFE.card.model.CardVo;
import com.bit.checkPayCloneFE.telecom.model.TelecomVo;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/telecom")
public class telecomContreller {
	
	@GetMapping("/")
	public String main() {
		return "telecom/O1000";
	}
	
	@GetMapping("/details")
	public String detail() {
		return "telecom/O1100";
	}
	
	@PostMapping("/bill")
	public String billmapping(String org_name_abb, String charge_date, Model model) {
		TelecomVo telecomVo = new TelecomVo(org_name_abb, charge_date);
		model.addAttribute("org_name_abb1", telecomVo);
		return "telecom/O1110.html";
	}
}
