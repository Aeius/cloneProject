package com.bit.checkpayclone.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.bank.model.BankTransReqVo;
import com.bit.checkpayclone.bank.service.BankService;

import lombok.RequiredArgsConstructor;

@RestController // json
@RequiredArgsConstructor
public class BankController {
	final BankService bankService;
	/*
	 * 수신계좌 목록 조회 API
	 * [get] /api/bank/memberact/{id}
	 * 작성자 : 박태영
	 */
	@GetMapping("/api/bank/memberact/{id}")
	public ResponseEntity<?> memberAccountList(@PathVariable("id") String id){
		return ResponseEntity.ok(bankService.memberAccount(id));
	}
	/*
	 * 수신계좌 거래내역 조회 API
	 * [POST] /api/bank/detail
	 * 보내는 값 	memb_cd, String account_num, Date start_date, Date end_date,
	 * 			String trans_type, String sort, int offset
	 * 작성자 : 박태영
	 */
	@PostMapping("/api/bank/depositTrans")
	public ResponseEntity<?> bankDepositTransList(@RequestBody BankTransReqVo bankTransReqVo) {
		return ResponseEntity.ok(bankService.BankDepositTransSelect(
				bankTransReqVo.getMemb_cd(),
				bankTransReqVo.getOrg_code(),
				bankTransReqVo.getAccount_num(),
				bankTransReqVo.getStart_date(),
				bankTransReqVo.getEnd_date(),
				bankTransReqVo.getTrans_type(),
				bankTransReqVo.getSort(),
				bankTransReqVo.getOffset()));
	}
	/*
	 * 투자 상품(펀드, 신탁) 상세 조회 API
	 * [get] /api/bank/memberact/{id}
	 * 작성자 : 박태영
	 */
	@GetMapping("/api/bank/investDetail/{memb_cd}/{account_num}")
	public ResponseEntity<?> bankInvestList(@PathVariable("memb_cd") String memb_cd, @PathVariable("account_num") String account_num){
		return ResponseEntity.ok(bankService.investDetail(memb_cd, account_num));
	}
	/*
	 * 투자 상품(펀드, 신탁) 거래내역 조회 API
	 * [POST] /api/bank/detail
	 * 보내는 값 	memb_cd, String account_num, Date start_date, Date end_date,
	 * 			String trans_type, String sort, int offset
	 * 작성자 : 박태영
	 */
	@PostMapping("/api/bank/investTrans")
	public ResponseEntity<?> bankInvestTransList(@RequestBody BankTransReqVo bankTransReqVo) {
		return ResponseEntity.ok(bankService.BankInvestTransSelect(
				bankTransReqVo.getMemb_cd(),
				bankTransReqVo.getOrg_code(),
				bankTransReqVo.getAccount_num(),
				bankTransReqVo.getStart_date(),
				bankTransReqVo.getEnd_date(),
				bankTransReqVo.getSort(),
				bankTransReqVo.getOffset()));
	}
	
	@GetMapping("/api/bank/bankcapitaldetail/{id}/{org_code}/{account_num}")
	public ResponseEntity<?> selectUserCapitalDetail(@PathVariable("id") String id,
			@PathVariable("org_code") String org_code,
			@PathVariable("account_num") String account_num){
		return ResponseEntity.ok(bankService.selectUserBankCapitalDetail(id,org_code,account_num));
	}
	
	/*
	 * 대출 거래내역 조회 API
	 * [POST] /api/bank/loanTrans
	 * 보내는 값 	memb_cd, String account_num, Date start_date, Date end_date,
	 * 			String sort, int offset
	 * 작성자 : 박태영
	 */
	@PostMapping("/api/bank/loanTrans")
	public ResponseEntity<?> bankLoanTransList(@RequestBody BankTransReqVo bankTransReqVo) {
		return ResponseEntity.ok(bankService.BankLoanTransSelect(
				bankTransReqVo.getMemb_cd(),
				bankTransReqVo.getOrg_code(),
				bankTransReqVo.getAccount_num(),
				bankTransReqVo.getStart_date(),
				bankTransReqVo.getEnd_date(),
				bankTransReqVo.getSort(),
				bankTransReqVo.getOffset()));
	}
}
