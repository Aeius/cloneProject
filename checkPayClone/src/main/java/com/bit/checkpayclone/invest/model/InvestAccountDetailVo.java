package com.bit.checkpayclone.invest.model;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestAccountDetailVo {
	private String account_name, org_name, account_num;
	private double withholdings_amt, credit_loan_amt, mortgage_amt, eval_amt, purchase_amt;
	private String is_tax_benefits;
	private Date base_date;
	private String encrypt_an, currency_code;
}
