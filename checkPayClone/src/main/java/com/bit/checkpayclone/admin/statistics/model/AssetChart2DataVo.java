package com.bit.checkpayclone.admin.statistics.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AssetChart2DataVo {
	private int bank_member_count, card_member_count, capital_loan_member_count, efin_member_count, invest_member_count, telecom_member_count, total_member_count;
	private double bank_member_percent, card_member_percent, capital_loan_member_percent, efin_member_percent, invest_member_percent, telecom_member_percent;
}
