package com.bit.checkpayclone.admin.login.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bit.checkpayclone.admin.login.model.AdminLoginVo;
import com.bit.checkpayclone.admin.login.service.AdminLoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class AdminLoginController {

	final AdminLoginService adminLoginService;

	@PostMapping("/")
	public ResponseEntity<?> Login(@RequestBody AdminLoginVo vo, HttpServletRequest request) {
		Map<String, Object> jwt = adminLoginService.adminLogin(vo,request);
		return ResponseEntity.ok(jwt);
	}

	@PostMapping("/refresh")
	public ResponseEntity<String> tokenRefresh(@RequestParam Map<String, Object> map) {
		String id = (String)map.get("id");
		String rtk = (String)map.get("rtk");
		String jwt = adminLoginService.reissuedToken(id,rtk);
		return ResponseEntity.ok(jwt);
	}

	@PostMapping("/logout")
	public void adminLogout(@RequestParam Map<String, Object> map) {
		String rtk = (String) map.get("rtk");
		adminLoginService.adminLogout(rtk);
	}

}
