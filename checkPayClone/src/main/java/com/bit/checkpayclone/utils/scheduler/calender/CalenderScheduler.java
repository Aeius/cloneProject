package com.bit.checkpayclone.utils.scheduler.calender;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bit.checkpayclone.asset.service.AssetService;
import com.bit.checkpayclone.calender.service.CalenderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CalenderScheduler {


	final CalenderService calenderService;
	final AssetService assetService;
	
	@Scheduled(cron = "0 0 0 1 * *")
	public void budgetDeleteOldData() {
		calenderService.deleteCalenderBudgetOldData();
		log.info("The old data from the tb_a_member_month_budget table has been successfully deleted");
	}
	
	@Scheduled(cron = "0 0 0 1 * *")
	public void calenderStarMemoDeleteOldData() {
		calenderService.deleteCalenderStarMemoOldData();
		log.info("The old data from the tb_a_member_calendar table has been successfully deleted");
	}
	
	@Scheduled(cron = "0 0 0 1 * *")
	public void deleteCalenderBudgetDetOldData() {
		calenderService.deleteCalenderBudgetDetOldData();
		log.info("The old data from the tb_a_member_month_budget_det table has been successfully deleted");
	}

	@Scheduled(cron = "0 15 10 1 * ?")
	public void updateMemberLastBalance() {
		assetService.updateMemberLastBalance();
		log.info("insert Last Balance into tb_a_month_asset table");
	}
}
