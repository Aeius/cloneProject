package com.bit.checkpayclone.invest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestProductsDetailListResVo {
	private String org_code, account_num, prod_name, prod_type, currency_code;
	private double holding_num, eval_amt, difference, return_rate;
	private int seq;
	
	
	@Builder
	public InvestProductsDetailListResVo(String org_code, String account_num, String prod_name, double holding_num,
			double eval_amt, double difference,double return_rate,int seq, String prod_type, String currency_code) {
		this.org_code = org_code;
		this.account_num = account_num;
		this.prod_name = prod_name;
		this.prod_type = prod_type;
		this.holding_num = holding_num;
		this.eval_amt = eval_amt;
		this.difference = difference;
		this.return_rate = return_rate;
		this.seq = seq;
		this.currency_code = currency_code;
	}
	

}
