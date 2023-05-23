package com.bit.checkpayclone.irp.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IrpAccountTransVo {
	private Timestamp trans_dtime;
	private String trans_type;
	private double trans_amt;
}
