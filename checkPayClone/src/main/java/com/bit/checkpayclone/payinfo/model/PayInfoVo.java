package com.bit.checkpayclone.payinfo.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PayInfoVo {
	private String approved_num, merchant_name, merchant_regno, org_name, card_name;
	private double approved_amt;
	private String catg_nm;
	private Timestamp approved_dtime, trans_dtime;
	private double modified_amt;
	private int total_install_cnt;
	private String country_code, currency_code;
	private double krw_amt;
	private String status, org_code, card_id;
	private double seq;
}
