package com.bit.checkPayCloneFE.telecom.model;

import lombok.Getter;

@Getter
public class TelecomVo {
	
	private String org_name_abb, charge_date,org_code;

	public TelecomVo(String org_name_abb, String charge_date) {
		super();
		this.org_name_abb = org_name_abb;
		this.charge_date = charge_date;
	}
	
	public TelecomVo(String org_name_abb, String charge_date, String org_code) {
		super();
		this.org_name_abb = org_name_abb;
		this.org_code = org_code;
	}
}