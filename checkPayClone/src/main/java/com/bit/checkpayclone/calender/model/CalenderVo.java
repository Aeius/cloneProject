package com.bit.checkpayclone.calender.model;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CalenderVo {
	private long member_id, total_budget;
	private Date budget_date;
	private Object category_budget;
	
}
