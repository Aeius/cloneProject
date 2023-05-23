package com.bit.checkpayclone.invest.model;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestAccountDetailResVo {
	private String account_name, org_name, account_num;
	private double total_balance, withholdings_amt, credit_loan_amt, mortgage_amt, eval_amt, return_rate;
	private String is_tax_benefits;
	private Date base_date;
	private String encrypt_an, currency_code;

	@Builder
	public InvestAccountDetailResVo(String account_name, String org_name, String account_num, double total_balance,
			double withholdings_amt, double credit_loan_amt, double mortgage_amt, double eval_amt, double return_rate,
			String is_tax_benefits, Date base_date, String encrypt_an, String currency_code) {
		this.account_name = account_name;
		this.org_name = org_name;
		this.account_num = account_num;
		this.total_balance = total_balance;
		this.withholdings_amt = withholdings_amt;
		this.credit_loan_amt = credit_loan_amt;
		this.mortgage_amt = mortgage_amt;
		this.eval_amt = eval_amt;
		this.return_rate = return_rate;
		this.is_tax_benefits = is_tax_benefits;
		this.base_date = base_date;
		this.encrypt_an = encrypt_an;
		this.currency_code = currency_code;
	}




}
