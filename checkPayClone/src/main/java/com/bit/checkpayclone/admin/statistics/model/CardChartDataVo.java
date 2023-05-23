package com.bit.checkpayclone.admin.statistics.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CardChartDataVo {
	private String approved_month,catg_nm,approved_amt_avg;
	private double approved_amt_percent;
	
	@Builder
	public CardChartDataVo(String approved_month, String catg_nm, String approved_amt_avg, double approved_amt_percent) {
		this.approved_month=approved_month;
		this.catg_nm=catg_nm;
		this.approved_amt_avg=approved_amt_avg;
		this.approved_amt_percent=approved_amt_percent;
	}
}
