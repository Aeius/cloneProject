package com.bit.checkpayclone.calender.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CalenderCardAndAccountVo {
	private String trans_memo, approved_num, merchant_name, org_name, prod_name, account_num, trans_type;
	private double trans_amt, balance_amt;
	private Timestamp trans_dtime; 
	private String card_name;
	private double approved_amt;
	private String catg_nm;
	private Timestamp approved_dtime;
	private double modified_amt;
	private int total_install_cnt;
	private String country_code, currency_code;
	private double krw_amt;
	private String status, org_code, card_id;
	private double seq;
	
}
