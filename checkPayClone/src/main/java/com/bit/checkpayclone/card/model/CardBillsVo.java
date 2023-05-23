package com.bit.checkpayclone.card.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CardBillsVo {
	private String org_name, org_code;
	private double charge_amt;
	private String charge_month, charge_day;
	private Timestamp paid_out_date;
}
