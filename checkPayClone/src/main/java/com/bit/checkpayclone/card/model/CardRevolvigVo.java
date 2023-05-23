package com.bit.checkpayclone.card.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CardRevolvigVo {
	private String org_name, req_date, is_revolving;
	private double min_pay_rate, agreed_pay_rate;
	private long min_pay_amt, agreed_pay_amt, remained_amt;
}
