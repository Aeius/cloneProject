package com.bit.checkPayCloneFE.card.model;

import lombok.Getter;

@Getter
public class CardloanVo {
	private String memb_cd,org_code;
	private double int_rate;
	private long loan_amt,balance_amt,int_amt;
	private String loan_name,loan_type,repay_method,reg_dttm,exp_date,loan_dtime,pay_due_date, descrption;
	private Boolean isLong;
	
	public CardloanVo(String memb_cd, String org_code, double int_rate, long loan_amt, long balance_amt, long int_amt,
			String loan_name, String loan_type, String repay_method, String reg_dttm, String exp_date) {
		this.memb_cd = memb_cd;
		this.org_code = org_code;
		this.int_rate = int_rate;
		this.loan_amt = loan_amt;
		this.balance_amt = balance_amt;
		this.int_amt = int_amt;
		this.loan_name = loan_name;
		this.loan_type = loan_type;
		this.repay_method = repay_method;
		this.reg_dttm = reg_dttm;
		this.exp_date = exp_date;
		isLong = true;
	}
	
	public CardloanVo(String memb_cd, String org_code, String loan_dtime, String pay_due_date, String reg_dttm,
			long loan_amt, long balance_amt, double int_rate, String descrption) {
		this.memb_cd = memb_cd;
		this.org_code = org_code;
		this.loan_dtime = loan_dtime;
		this.pay_due_date = pay_due_date;
		this.reg_dttm = reg_dttm;
		this.loan_amt = loan_amt;
		this.balance_amt = balance_amt;
		this.int_rate = int_rate;
		this.descrption = descrption;
		isLong = false;
	}

	
}
