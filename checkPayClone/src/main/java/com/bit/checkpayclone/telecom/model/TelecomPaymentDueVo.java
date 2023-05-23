package com.bit.checkpayclone.telecom.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TelecomPaymentDueVo {
	private String org_name, type, charge_date, mgmt_id;
}
