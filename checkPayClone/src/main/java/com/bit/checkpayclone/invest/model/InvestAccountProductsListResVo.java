package com.bit.checkpayclone.invest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestAccountProductsListResVo {
	private String prod_name;
	private double kr_eval_amt_sum;
	private String org_name, account_num;
	private double holding_num, eval_amt;
	private String currency_code;
	private double return_rate, diffrerence;
	private int seq;
	private String encrypt_an, prod_type;
	
	@Builder
	public InvestAccountProductsListResVo(String prod_name, double kr_eval_amt_sum, String org_name, String account_num,
			double holding_num, double eval_amt, String currency_code, double return_rate, double diffrerence, int seq,
			String encrypt_an, String prod_type) {
		this.prod_name = prod_name;
		this.kr_eval_amt_sum = kr_eval_amt_sum;
		this.org_name = org_name;
		this.account_num = account_num;
		this.holding_num = holding_num;
		this.eval_amt = eval_amt;
		this.currency_code = currency_code;
		this.return_rate = return_rate;
		this.diffrerence = diffrerence;
		this.seq = seq;
		this.encrypt_an = encrypt_an;
		this.prod_type = prod_type;
	}
	
	
}
