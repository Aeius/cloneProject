package com.bit.checkpayclone.invest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestProductsDetailResVo {
	private double eval_amt, holding_num, purchase_amt, purchase_avg, return_rate;
	private String currency_code, account_name, prod_type, account_num, org_code;
	
	
	@Builder
	public InvestProductsDetailResVo(double eval_amt, double holding_num, double purchase_amt, double purchase_avg,
			double return_rate, String currency_code, String account_name, String prod_type, String account_num,
			String org_code) {
		super();
		this.eval_amt = eval_amt;
		this.holding_num = holding_num;
		this.purchase_amt = purchase_amt;
		this.purchase_avg = purchase_avg;
		this.return_rate = return_rate;
		this.currency_code = currency_code;
		this.account_name = account_name;
		this.prod_type = prod_type;
		this.account_num = account_num;
		this.org_code = org_code;
	}
	
}
