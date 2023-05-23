package com.bit.checkpayclone.invest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestProductsVo {
	private String prod_type;
	private double eval_amt, purchase_amt;
	private String currency_code;
}
