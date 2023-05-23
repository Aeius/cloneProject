package com.bit.checkpayclone.telecom.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TelecomBillVo {
	private String org_code, current_total, org_name_abb, payment, charge_amt, charge_date, charge_month; 
}
