package com.bit.checkpayclone.calender.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateBudgetVo {
	private String member_id;
	private Long total_budget;
	private String year_month;
	private String[] category_name;
	private Long[] category_budget;
	
	@Builder
	public UpdateBudgetVo(String member_id, Long total_budget, String year_month, String[] category_name,
			Long[] category_budget) {
		this.member_id = member_id;
		this.total_budget = total_budget;
		this.year_month = year_month;
		this.category_name = category_name;
		this.category_budget = category_budget;
	}
	
}
