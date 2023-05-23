package com.bit.checkpayclone.bank.model;


import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BankTransReqVo {
	private String memb_cd;
	private String org_code;
	private String account_num;
	private Date start_date, end_date;
	private String trans_type, sort;
	private int offset;
	
	@Builder
	public BankTransReqVo(String memb_cd, String org_code, String account_num, Date start_date, Date end_date,
			String trans_type, String sort, int offset) {
		super();
		this.memb_cd = memb_cd;
		this.org_code = org_code;
		this.account_num = account_num;
		this.start_date = start_date;
		this.end_date = end_date;
		this.trans_type = trans_type;
		this.sort = sort;
		this.offset = offset;
	}
}
