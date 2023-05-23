package com.bit.checkpayclone.irp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.irp.model.IrpAccountDetailReqVo;
import com.bit.checkpayclone.irp.model.IrpAccountTransReqVo;
import com.bit.checkpayclone.irp.service.IrpService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/irp")
@RequiredArgsConstructor
public class IrpController {
	final IrpService irpService;
	
	/*
	 * 은행 or 증권에서 IRP계좌 리스트 조회 API		
	 */
	@GetMapping("/account/list/{memb_cd}/{org}")
	public ResponseEntity<?> findUserIrpAccountList(@PathVariable String memb_cd, @PathVariable String org){
		return ResponseEntity.ok(irpService.findUserIrpAccountList(memb_cd, org));
	}
	
	/*
	 * 은행 or 증권에서 IRP 계좌 상세 조회 API		
	 */
	@PostMapping("/account/detail")
	public ResponseEntity<?> findUserIrpAccountDetail(@RequestBody IrpAccountDetailReqVo req){
		return ResponseEntity.ok(irpService.findUserIrpAccountDetail(req.getMemb_cd(), req.getOrg_code(), req.getAccount_num()));
	}

	@PostMapping("/account/trans")
	public ResponseEntity<?> findUserIrpAccountTrans(@RequestBody IrpAccountTransReqVo req){
		return ResponseEntity.ok(irpService.findUserIrpAccountTrans(req.getMemb_cd(), req.getAccount_num(), req.getStart_day(), req.getEnd_day(), req.getSort(), req.getOffset()));
	}
	/*
	 * IRP 계좌에서의 개인운용상품 조회 API
	 */
	@PostMapping("/account/products")
	public ResponseEntity<?> findUserIrpAccountProducts(@RequestBody IrpAccountDetailReqVo req){
		return ResponseEntity.ok(irpService.findUserIrpAccountProducts(req.getMemb_cd(), req.getOrg_code(), req.getAccount_num()));
	}

	
}
