package com.bit.checkPayCloneFE.capital.model;

import com.bit.checkPayCloneFE.bank.model.BankVo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CapitalVo {
	private String org_code,memb_cd,account_num,org_name,currency_code,prod_name,repay_method;
	private double last_offered_rate;
	private long balance_amt,loan_principal;
	private String next_repay_date,issue_date,exp_date,repay_date;
	

	@Builder
	public CapitalVo(String org_code,String memb_cd, String account_num, String org_name, String currency_code, String prod_name,
			String repay_method, double last_offered_rate, long balance_amt, long loan_principal,
			String next_repay_date, String issue_date, String exp_date, String repay_date) {
		super();
		this.org_code = org_code;
		this.memb_cd = memb_cd;
		this.account_num = account_num;
		this.org_name = org_name;
		this.currency_code = currency_code;
		this.prod_name = prod_name;
		this.repay_method = repay_method;
		this.last_offered_rate = last_offered_rate;
		this.balance_amt = balance_amt;
		this.loan_principal = loan_principal;
		this.next_repay_date = next_repay_date;
		this.issue_date = issue_date;
		this.exp_date = exp_date;
		this.repay_date = repay_date;
	}
	
	
}