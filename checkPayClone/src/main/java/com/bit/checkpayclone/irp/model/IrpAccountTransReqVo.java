package com.bit.checkpayclone.irp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IrpAccountTransReqVo {
	private String memb_cd, account_num, start_day, end_day, sort;
	private int offset;
}
