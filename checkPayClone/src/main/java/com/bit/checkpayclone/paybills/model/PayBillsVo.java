package com.bit.checkpayclone.paybills.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PayBillsVo {
	private String org_name, org_code;
	private double charge_amt;
	private String charge_month, charge_day;
	private Timestamp paid_out_date,paid_dtime;
	private String card_name;
	private double paid_amt;
	private String currency_code, merchant_name;
	private double credit_fee_amt, total_install_cnt, cur_install_cnt, balance_amt;
	private String prod_type, paid_dt, merchant_regno;
	
	private long install_sum;
}
