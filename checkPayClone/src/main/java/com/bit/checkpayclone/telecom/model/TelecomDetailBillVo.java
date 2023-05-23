package com.bit.checkpayclone.telecom.model;

import lombok.Builder;
import lombok.Getter;
import java.sql.Timestamp;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TelecomDetailBillVo {
	private String org_name_abb, type, telecom_num, charge_amt, crnt_month, pre_month, pre2_month, paid_amt, paid;
	Timestamp charge_date;
}
