package com.bit.checkpayclone.irp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IrpAccountDetailReqVo {
	private String memb_cd, org_code, account_num;
}
