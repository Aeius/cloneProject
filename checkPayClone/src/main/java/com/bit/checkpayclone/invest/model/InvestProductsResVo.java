package com.bit.checkpayclone.invest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestProductsResVo {
	private String prod_type;
	private double eval_amt, difference, return_rate;
	private String currency_code;
	
		
	@Builder
	public InvestProductsResVo(String prod_type, double eval_amt, double difference,double return_rate, String currency_code) {
		this.prod_type = prod_type;
		this.eval_amt = eval_amt;
		this.difference = difference;
		this.return_rate = return_rate;
		this.currency_code = currency_code;
	}

	
}
