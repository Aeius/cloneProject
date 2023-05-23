package com.bit.checkpayclone.admin.statistics.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.admin.member.model.AdminMemberMapper;
import com.bit.checkpayclone.admin.statistics.model.AdminStatisticsMapper;
import com.bit.checkpayclone.admin.statistics.model.AssetChart1DataVo;
import com.bit.checkpayclone.admin.statistics.model.AssetChart2DataVo;
import com.bit.checkpayclone.admin.statistics.model.BankChartDataVo;
import com.bit.checkpayclone.admin.statistics.model.CapitalChartDataVo;
import com.bit.checkpayclone.admin.statistics.model.CardChartDataVo;
import com.bit.checkpayclone.admin.statistics.model.EfinChartDataVo;
import com.bit.checkpayclone.admin.statistics.model.InvestChartDataVo;
import com.bit.checkpayclone.admin.statistics.model.TelecomChartDataVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminStatisticsService {
	final SqlSession sqlSession;
	
	/**
	 * 카드 업권 통계에 쓰이는 평균들의 합 변수
	 */
	double avg_sum = 0;
	
	/**
	 * 자산 통계 1에 쓰이는 변수
	 */
	int idx=0;
	
	public Map<String, Object> getMemberChart1Data(){
		Map<String, Object> result = new HashMap<>();
		
		result.put("data", sqlSession.getMapper(AdminStatisticsMapper.class).getMemberChart1Data());
		
		return result;
	}
	
	public Map<String, Object> getMemberChart2Data(String selectYear){
		Map<String, Object> result = new HashMap<>();

		result.put("data", sqlSession.getMapper(AdminStatisticsMapper.class).getMemberChart2Data(selectYear));
		
		return result;
	}
	
	public Map<String, Object> getAssetChart1Data(String selectYear){
		Map<String, Object> result = new HashMap<>();
		
		selectYear = (selectYear!=null&&!selectYear.equals(""))?selectYear:"2023";
		
		List<AssetChart1DataVo> depositData = sqlSession.getMapper(AdminStatisticsMapper.class).getAssetChart1Data(selectYear, "deposit");
		List<AssetChart1DataVo> withdrawData = sqlSession.getMapper(AdminStatisticsMapper.class).getAssetChart1Data(selectYear, null);
		List<AssetChart1DataVo> resultData = new ArrayList<>();
		
		Map<String, double[]> sumData = new HashMap<>();
		List<String> monthArr = new ArrayList();

		depositData.forEach((value)->{
			if(monthArr.indexOf(value.getYear_month())==-1) {
				monthArr.add(value.getYear_month());
			}
		});
		withdrawData.forEach((value)->{
			if(monthArr.indexOf(value.getYear_month())==-1) {
				monthArr.add(value.getYear_month());
			}
		});
		
		monthArr.forEach((value1)->{
			long withdrawValue = 0;
			long depositValue = 0;
			
			if(withdrawData.get(idx) != null && withdrawData.get(idx).getYear_month().equals(value1)) {
				withdrawValue = Math.round(Double.parseDouble(withdrawData.get(idx).getAmount_sum()));
			}
			if(depositData.get(idx) != null && depositData.get(idx).getYear_month().equals(value1)) {
				depositValue = Math.round(Double.parseDouble(depositData.get(idx).getAmount_sum()));
			}
			
			idx++;
			
			resultData.add(AssetChart1DataVo.builder().year_month(value1).amount_sum(String.valueOf(withdrawValue-depositValue)).build());
			
		});
		
		idx = 0;
		
		result.put("data", resultData);
		
		return result;
	}
	
	public Map<String, Object> getAssetChart2Data(){
		Map<String, Object> result = new HashMap<>();
		
		List<AssetChart2DataVo> resultData = sqlSession.getMapper(AdminStatisticsMapper.class).getAssetChart2Data();
		
		resultData.forEach((value)->{
			value.setBank_member_percent(Math.round(((double)value.getBank_member_count()/(double)value.getTotal_member_count())*100*100)/100.0);
			value.setCard_member_percent(Math.round(((double)value.getCard_member_count()/(double)value.getTotal_member_count())*100*100)/100.0);
			value.setCapital_loan_member_percent(Math.round(((double)value.getCapital_loan_member_count()/(double)value.getTotal_member_count())*100*100)/100.0);
			value.setEfin_member_percent(Math.round(((double)value.getEfin_member_count()/(double)value.getTotal_member_count())*100*100)/100.0);
			value.setInvest_member_percent(Math.round(((double)value.getInvest_member_count()/(double)value.getTotal_member_count())*100*100)/100.0);
			value.setTelecom_member_percent(Math.round(((double)value.getTelecom_member_count()/(double)value.getTotal_member_count())*100*100)/100.0);
		});
		
		result.put("data", resultData);
		
		return result;
	}
	
	/**
	 * 은행 업권 통계
	 * 1년간 평균 입출금 금액
	 * 
	 * @param selectYear : 선택 년도
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getBankChartData(String selectYear){
		Map<String, Object> result = new HashMap<>();
		
		selectYear = (selectYear!=null&&!selectYear.equals(""))?selectYear:"2023";
		
		List<BankChartDataVo> depositData = sqlSession.getMapper(AdminStatisticsMapper.class).getBankChartData(selectYear, "deposit");
		List<BankChartDataVo> withdrawData = sqlSession.getMapper(AdminStatisticsMapper.class).getBankChartData(selectYear, null);
		
		Map<String, Object> detailResult = new HashMap<>();
		
		detailResult.put("depositData", depositData);
		detailResult.put("withdrawData", withdrawData);
		
		result.put("data", detailResult);
		
		return result;
	}
	
	/**
	 * 카드 업권 통계
	 * 1년간 카테고리 별 지출 비율
	 * 
	 * @param selectYear : 선택 년도
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getCardChartData(String selectYear){
		Map<String, Object> result = new HashMap<>();
		
		selectYear = (selectYear!=null&&!selectYear.equals(""))?selectYear:"2023";
		
		List<CardChartDataVo> cardChartData = sqlSession.getMapper(AdminStatisticsMapper.class).getCardChartData(selectYear);
		
		List<CardChartDataVo> newCardChartData = new ArrayList<>();

		cardChartData.forEach((value1)->{
			double temp_avg = Double.parseDouble(value1.getApproved_amt_avg());
			
			cardChartData.forEach((value2)->{
				if(value1.getApproved_month().equals(value2.getApproved_month())) {
					avg_sum += Double.parseDouble(value2.getApproved_amt_avg());
				}
			});
			
			double percent = Double.parseDouble(String.format("%.2f",(temp_avg/avg_sum)*100.00));
			
			avg_sum = 0;
	
			newCardChartData.add(
					CardChartDataVo
					.builder()
					.approved_month(value1.getApproved_month())
					.catg_nm(value1.getCatg_nm())
					.approved_amt_avg(value1.getApproved_amt_avg())
					.approved_amt_percent(percent)
					.build()
					);
		});

		result.put("data", newCardChartData);
		
		return result;
	}
	
	public Map<String, Object> getEfinChartData(String selectYear){
		Map<String, Object> result = new HashMap<>();
		
		selectYear = (selectYear!=null&&!selectYear.equals(""))?selectYear:"2023";
		
		List<EfinChartDataVo> depositData = sqlSession.getMapper(AdminStatisticsMapper.class).getEfinChartData(selectYear, "deposit");
		List<EfinChartDataVo> withdrawData = sqlSession.getMapper(AdminStatisticsMapper.class).getEfinChartData(selectYear, null);
		
		Map<String, Object> detailResult = new HashMap<>();
		
		detailResult.put("depositData", depositData);
		detailResult.put("withdrawData", withdrawData);
		
		result.put("data", detailResult);
		
		return result;
	}
	
	public Map<String, Object> getInvestChartData(String selectYear){
		Map<String, Object> result = new HashMap<>();
		
		selectYear = (selectYear!=null&&!selectYear.equals(""))?selectYear:"2023";
		
		List<InvestChartDataVo> KRWData = sqlSession.getMapper(AdminStatisticsMapper.class).getInvestChartData(selectYear, "KRW"); 
		List<InvestChartDataVo> USDData = sqlSession.getMapper(AdminStatisticsMapper.class).getInvestChartData(selectYear, "USD"); 
		
		Map<String, Object> detailResult = new HashMap<>();
		
		detailResult.put("KRWData", KRWData);
		detailResult.put("USDData", USDData);
		
		result.put("data", detailResult);
		
		return result;
	}
	
	public Map<String, Object> getTelecomChartData(String selectYear){
		Map<String, Object> result = new HashMap<>();
		
		selectYear = (selectYear!=null&&!selectYear.equals(""))?selectYear:"2023";
		
		List<TelecomChartDataVo> resultData = sqlSession.getMapper(AdminStatisticsMapper.class).getTelecomChartData(selectYear);
		
		result.put("data", resultData);
		
		return result;
	}
	
	public Map<String, Object> getCapitalChartData(String selectYear){
		Map<String, Object> result = new HashMap<>();
		
		selectYear = (selectYear!=null&&!selectYear.equals(""))?selectYear:"2023";
		
		List<CapitalChartDataVo> KRWData = sqlSession.getMapper(AdminStatisticsMapper.class).getCapitalChartData(selectYear, "KRW"); 
		List<CapitalChartDataVo> USDData = sqlSession.getMapper(AdminStatisticsMapper.class).getCapitalChartData(selectYear, "USD"); 
		
		Map<String, Object> detailResult = new HashMap<>();
		
		detailResult.put("KRWData", KRWData);
		detailResult.put("USDData", USDData);
		
		result.put("data", detailResult);
		
		return result;
	}
}
