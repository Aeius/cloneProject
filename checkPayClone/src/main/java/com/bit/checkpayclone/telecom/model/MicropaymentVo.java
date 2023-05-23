package com.bit.checkpayclone.telecom.model;

import lombok.Getter;
import java.sql.Timestamp;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MicropaymentVo {
	private String org_name_abb, merchant_name, trans_type, trans_amt, summary;
	Timestamp trans_date;
}
