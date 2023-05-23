package com.bit.checkpayclone.invest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.invest.model.InvestAccountDetailReqVo;
import com.bit.checkpayclone.invest.model.InvestAccountDetailResVo;
import com.bit.checkpayclone.invest.model.InvestAccountDetailVo;
import com.bit.checkpayclone.invest.model.InvestAccountProductsListResVo;
import com.bit.checkpayclone.invest.model.InvestAccountProductsListVo;
import com.bit.checkpayclone.invest.model.InvestAccountTransReqVo;
import com.bit.checkpayclone.invest.model.InvestProductsDetailListResVo;
import com.bit.checkpayclone.invest.model.InvestProductsDetailListVo;
import com.bit.checkpayclone.invest.model.InvestProductsDetailReqVo;
import com.bit.checkpayclone.invest.model.InvestProductsDetailResVo;
import com.bit.checkpayclone.invest.model.InvestProductsDetailVo;
import com.bit.checkpayclone.invest.model.InvestProductsResVo;
import com.bit.checkpayclone.invest.model.InvestProductsVo;
import com.bit.checkpayclone.invest.service.InvestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/invest")
@RequiredArgsConstructor
public class InvestController {
	final InvestService investService;
	
	@GetMapping("/account/list/{memb_cd}")
	public ResponseEntity<?> findUserInvestAccountList(@PathVariable String memb_cd){
		return ResponseEntity.ok(investService.findUserInvestAccountList(memb_cd));
	}
	
	@GetMapping("/product/list/{memb_cd}")
	public ResponseEntity<?> findUserInvestProductsList(@PathVariable String memb_cd){
		List<InvestProductsVo> temp = investService.findUserInvestProductsList(memb_cd);
		List<InvestProductsResVo> transData = new ArrayList<>();
		if(!temp.isEmpty()){
			for(int i=0; i<temp.size(); i++) {
				transData.add(
						InvestProductsResVo.builder()
							.prod_type(temp.get(i).getProd_type())
							.eval_amt(temp.get(i).getEval_amt())
							.difference(temp.get(i).getEval_amt()-temp.get(i).getPurchase_amt())
							.return_rate(
									(temp.get(i).getEval_amt()-temp.get(i).getPurchase_amt())/temp.get(i).getPurchase_amt()*100)
							.currency_code(temp.get(i).getCurrency_code())
							.build());
			}		
		}
		return ResponseEntity.ok(transData);
	};
	
	@GetMapping("/product/list/{memb_cd}/{prod_type}")
	public ResponseEntity<?> findUserInvestProductDetailList(@PathVariable String memb_cd, @PathVariable String prod_type){
		List<InvestProductsDetailListVo> temp = investService.findUserInvestProductDetailList(memb_cd, prod_type);
		List<InvestProductsDetailListResVo> transData = new ArrayList<>();
		if(!temp.isEmpty()) {
			for(int i=0; i<temp.size(); i++) {
				transData.add(
						InvestProductsDetailListResVo.builder()
							.org_code(temp.get(i).getOrg_code())
							.account_num(temp.get(i).getAccount_num())
							.prod_name(temp.get(i).getProd_name())
							.holding_num(temp.get(i).getHolding_num())
							.eval_amt(temp.get(i).getEval_amt())
							.difference(temp.get(i).getEval_amt()-temp.get(i).getPurchase_amt())
							.return_rate(
									(temp.get(i).getEval_amt()-temp.get(i).getPurchase_amt())/temp.get(i).getPurchase_amt()*100)
							.seq(temp.get(i).getSeq())
							.currency_code(temp.get(i).getCurrency_code())
							.build());
			}
		}
		return ResponseEntity.ok(transData);
	}
	
	@PostMapping("/product/detail")
	public ResponseEntity<?> findUserInvestProductDetail(@RequestBody InvestProductsDetailReqVo req){
		List<InvestProductsDetailVo> temp = investService.findUserInvestProductDetail(req.getMemb_cd(), req.getAccount_num(), req.getOrg_code(), req.getProd_type(), req.getSeq());
		List<InvestProductsDetailResVo> transData = new ArrayList<>();
		if(!temp.isEmpty()) {
			for(int i=0; i<temp.size(); i++) {
				transData.add(InvestProductsDetailResVo.builder()
						.eval_amt(temp.get(i).getEval_amt())
						.holding_num(temp.get(i).getHolding_num())
						.purchase_amt(temp.get(i).getPurchase_amt())
						.purchase_avg(temp.get(i).getPurchase_amt()/temp.get(i).getHolding_num())
						.return_rate(
									(temp.get(i).getEval_amt()-temp.get(i).getPurchase_amt())/temp.get(i).getPurchase_amt()*100)
						.currency_code(temp.get(i).getCurrency_code())
						.account_name(temp.get(i).getAccount_name())
						.prod_type(temp.get(i).getProd_type())
						.account_num(temp.get(i).getAccount_num())
						.org_code(temp.get(i).getOrg_code())
						.build());
			}
		}
		
		return ResponseEntity.ok(transData);
	}

	@GetMapping("/stock/list/{memb_cd}")
	public ResponseEntity<?> findUserInvestStockDetailList(@PathVariable String memb_cd){
		List<InvestProductsDetailListVo> temp = investService.findUserInvestStockList(memb_cd);
		List<InvestProductsDetailListResVo> transData = new ArrayList<>();
		if(!temp.isEmpty()) {
			for(int i=0; i<temp.size(); i++) {
				transData.add(
						InvestProductsDetailListResVo.builder()
							.org_code(temp.get(i).getOrg_code())
							.account_num(temp.get(i).getAccount_num())
							.prod_name(temp.get(i).getProd_name())
							.holding_num(temp.get(i).getHolding_num())
							.eval_amt(temp.get(i).getEval_amt())
							.difference(temp.get(i).getEval_amt()-temp.get(i).getPurchase_amt())
							.return_rate(
									(temp.get(i).getEval_amt()-temp.get(i).getPurchase_amt())/temp.get(i).getPurchase_amt()*100)
							.seq(temp.get(i).getSeq())
							.currency_code(temp.get(i).getCurrency_code())
							.prod_type(temp.get(i).getProd_type())
							.build());
			}
		}
		return ResponseEntity.ok(transData);
	}
	
	@PostMapping("/account/detail")
	public ResponseEntity<?> findUserInvestAccountDetail(@RequestBody InvestAccountDetailReqVo req){;
		InvestAccountDetailVo temp = investService.findUserInvestAccountDetail(req.getMemb_cd(), req.getAccount_num(), req.getOrg_code());
		InvestAccountDetailResVo transData = InvestAccountDetailResVo.builder()
													.account_name(temp.getAccount_name())
													.org_name(temp.getOrg_name())
													.account_num(temp.getAccount_num())
													.total_balance(temp.getWithholdings_amt()+temp.getEval_amt()-temp.getCredit_loan_amt()-temp.getMortgage_amt())
													.withholdings_amt(temp.getWithholdings_amt())
													.credit_loan_amt(temp.getCredit_loan_amt())
													.mortgage_amt(temp.getMortgage_amt())
													.eval_amt(temp.getEval_amt())
													.return_rate((temp.getEval_amt()-temp.getPurchase_amt())/temp.getPurchase_amt()*100)
													.is_tax_benefits(temp.getIs_tax_benefits())
													.base_date(temp.getBase_date())
													.encrypt_an(temp.getEncrypt_an())
													.currency_code(temp.getCurrency_code())
													.build();
		return ResponseEntity.ok(transData);
	}
	
	@PostMapping("/account/product")
	public ResponseEntity<?> findUserInvestAccountProductsList(@RequestBody InvestAccountDetailReqVo req){
		List<InvestAccountProductsListVo> temp = investService.findUserInvestAccountProductsList(req.getMemb_cd(), req.getAccount_num(), req.getOrg_code());
		List<InvestAccountProductsListResVo> transData = new ArrayList<>();
		Double krw = 0.0;
		if(!temp.isEmpty()) {
			for(int i=0; i<temp.size(); i++) {
				if(temp.get(i).getCurrency_code().equals("KRW"))
					krw+=temp.get(i).getEval_amt();
			}
			for(int i=0; i<temp.size(); i++) {
				transData.add(InvestAccountProductsListResVo.builder()
						.prod_name(temp.get(i).getProd_name())
						.kr_eval_amt_sum(krw+temp.get(i).getWithholdings_amt())
						.org_name(temp.get(i).getOrg_name())
						.account_num(temp.get(i).getAccount_num())
						.holding_num(temp.get(i).getHolding_num())
						.eval_amt(temp.get(i).getEval_amt())
						.currency_code(temp.get(i).getCurrency_code())
						.return_rate((temp.get(i).getEval_amt()-temp.get(i).getPurchase_amt())/temp.get(i).getPurchase_amt()*100)
						.diffrerence(temp.get(i).getEval_amt()-temp.get(i).getPurchase_amt())
						.seq(temp.get(i).getSeq())
						.encrypt_an(temp.get(i).getEncrypt_an())
						.prod_type(temp.get(i).getProd_type())
						.build());
			}
		}
		return ResponseEntity.ok(transData);
	}
	
	@PostMapping("/account/trans")
	public ResponseEntity<?> findUserInvestAccountTransList(@RequestBody InvestAccountTransReqVo req){
		return ResponseEntity.ok(investService.findUserInvestAccountTransList(
					req.getMemb_cd(), 
					req.getAccount_num(), 
					req.getOrg_code(), 
					req.getStart_day(), 
					req.getEnd_day(), 
					req.getSort()
				));
	}
}
