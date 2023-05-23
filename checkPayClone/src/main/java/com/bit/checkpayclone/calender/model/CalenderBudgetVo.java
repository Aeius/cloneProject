package com.bit.checkpayclone.calender.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CalenderBudgetVo {
	private String member_id;
	private String year_month;
	private Long total_budget;
	private String category_name;
	private Long category_budget;
}
