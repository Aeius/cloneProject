package com.bit.checkpayclone.admin.statistics.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.admin.notice.service.AdminNoticeService;
import com.bit.checkpayclone.admin.statistics.service.AdminStatisticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/statistics")
public class AdminStatisticsController {
	final AdminStatisticsService adminStatisticsService;
	
	@GetMapping("/memberChart1Data")	
	public Map<String, Object> getMemberChart1Data(){
		return adminStatisticsService.getMemberChart1Data();
	}
	
	@GetMapping("/memberChart2Data")	
	public Map<String, Object> getMemberChart2Data(String selectYear){
		return adminStatisticsService.getMemberChart2Data(selectYear);
	}
	
	@GetMapping("/assetChart1Data")	
	public Map<String, Object> getAssetChart1Data(String selectYear){
		return adminStatisticsService.getAssetChart1Data(selectYear);
	}
	
	@GetMapping("/assetChart2Data")	
	public Map<String, Object> getAssetChart2Data(){
		return adminStatisticsService.getAssetChart2Data();
	}

	@GetMapping("/bankChartData")	
	public Map<String, Object> getBankChartData(String selectYear){
		return adminStatisticsService.getBankChartData(selectYear);
	}
	
	@GetMapping("/cardChartData")	
	public Map<String, Object> getCardChartData(String selectYear){
		return adminStatisticsService.getCardChartData(selectYear);
	}
	
	@GetMapping("/efinChartData")	
	public Map<String, Object> getEfinChartData(String selectYear){
		return adminStatisticsService.getEfinChartData(selectYear);
	}
	
	@GetMapping("/investChartData")	
	public Map<String, Object> getInvestChartData(String selectYear){
		return adminStatisticsService.getInvestChartData(selectYear);
	}
	
	@GetMapping("/telecomChartData")	
	public Map<String, Object> getTelecomChartData(String selectYear){
		return adminStatisticsService.getTelecomChartData(selectYear);
	}
	
	@GetMapping("/capitalChartData")	
	public Map<String, Object> getCapitalChartData(String selectYear){
		return adminStatisticsService.getCapitalChartData(selectYear);
	}
}
