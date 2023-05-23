package com.bit.checkpayclone.admin.statistics.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminStatisticsMapper {
	
	List<MemberChart1DataVo> getMemberChart1Data();
	
	List<MemberChart2DataVo> getMemberChart2Data(String selectYear);
	
	List<AssetChart1DataVo> getAssetChart1Data(String selectYear, String amount_type);
	
	List<AssetChart2DataVo> getAssetChart2Data();
	
	List<BankChartDataVo> getBankChartData(String selectYear, String trans_type);
	
	List<CardChartDataVo> getCardChartData(String selectYear); 

	List<EfinChartDataVo> getEfinChartData(String selectYear, String trans_type);

	List<InvestChartDataVo> getInvestChartData(String selectYear, String currency_code);
	
	List<TelecomChartDataVo> getTelecomChartData(String selectYear);
	
	List<CapitalChartDataVo> getCapitalChartData(String selectYear, String currency_code);
}
