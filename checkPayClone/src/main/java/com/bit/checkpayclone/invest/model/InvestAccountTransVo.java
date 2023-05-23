package com.bit.checkpayclone.invest.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestAccountTransVo {
	private Timestamp trans_dtime;
	private String trans_type_detail, prod_name;
	private double settle_amt;
	private String trans_type;
	private double base_amt, trans_num;
	private String prod_code, currency_code;
}
