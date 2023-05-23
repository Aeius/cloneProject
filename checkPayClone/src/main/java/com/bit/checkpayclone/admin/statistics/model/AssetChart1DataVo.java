package com.bit.checkpayclone.admin.statistics.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AssetChart1DataVo {
	private String year_month,amount_sum;

	@Builder
	public AssetChart1DataVo(String year_month, String amount_sum) {
		super();
		this.year_month = year_month;
		this.amount_sum = amount_sum;
	}
}
