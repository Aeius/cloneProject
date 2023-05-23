/*
선불/페이 업권 통계
월별 평균 선불/페이 입출금 변동 추이
*/

/**
 * 차트 보여주기
 */
function getEfinChart(){
	var chart = null;
    var chartData = getEfinChartData();
    
    chart = Highcharts.chart('efinChart', {
	  chart: {
	    type: 'area'
	  },
	  title: {
	    text: '월별 평균 선불/페이 입출금 변동 추이'
	  },
      exporting: { enabled: false },// 우측상단 햄버거메뉴 숨김
      credits: { enabled: false },// highchart 워터마크 숨김
      tooltip: { enabled: false },// 하이차트 기본툴팁 숨김
	  xAxis: {
	    labels: {
	      formatter: function () {
	        return this.value; // clean, unformatted number for year
	      }
	    },
	    accessibility: {
	      rangeDescription: '월별 평균 선불/페이 입출금 변동 추이'
	    },
	    categories: ['1월', '2월', '3월', '4월', '5월', '6월',
                '7월', '8월', '9월', '10월', '11월', '12월']
	  },
	  yAxis: {
	    title: {
	      text: '평균 금액'
	    },
	    labels: {
	      formatter: function () {
	        return this.value / 10000 + ' KRW';
	      }
	    }
	  },
	  tooltip: {
	    pointFormat: '해당 월에 평균적으로 <b>{point.y:,.0f}</b> 원 만큼 {series.name} 하였습니다.'
	  },
	  plotOptions: {
	    area: {
	      marker: {
	        enabled: false,
	        symbol: 'circle',
	        radius: 2,
	        states: {
	          hover: {
	            enabled: true
	          }
	        }
	      }
	    }
	  },
	  series: [{
	    name: '입금',
	    data: chartData[0],
	    color: '#ff9800'
	  }, {
	    name: '출금',
	    data: chartData[1],
	    color: '#07dbb0'
	  }]
	});
	
	return chart;
}

function getEfinChartData(){
	var obj = [];
	var chartData = null;

	$.ajax({
        url: baseUrl + '/api/admin/statistics/efinChartData',
        type: 'get',
        data: {
            selectYear: $('#efinChartData_selectYear').val()
        },
        async: false,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        },
        success: function (result) {
			var data = result.data;
            
            var depositData = data.depositData;
            var withdrawData = data.withdrawData;

            /*
            입금, 출금 배열의 원소들에 데이터 저장 전 월 갯수 만큼 0 저장
            */
		   var depositDataObj = {
                    '01월': 0,
                    '02월': 0,
                    '03월': 0,
                    '04월': 0,
                    '05월': 0,
                    '06월': 0,
                    '07월': 0,
                    '08월': 0,
                    '09월': 0,
                    '10월': 0,
                    '11월': 0,
                    '12월': 0
                };
		   var withdrawDataObj = {
                    '01월': 0,
                    '02월': 0,
                    '03월': 0,
                    '04월': 0,
                    '05월': 0,
                    '06월': 0,
                    '07월': 0,
                    '08월': 0,
                    '09월': 0,
                    '10월': 0,
                    '11월': 0,
                    '12월': 0
                };;
		   
			/*
            입금, 출금 배열의 원소들에 데이터 저장
            */
			depositData.map((value, index)=>{
				var month = value.trans_month.substr(4,2);
				
				depositDataObj[month+'월'] = Number(value.trans_amt_avg);
		   });
		   withdrawData.map((value, index)=>{
			   var month = value.trans_month.substr(4,2);
			   
				withdrawDataObj[month+'월'] = Number(value.trans_amt_avg);
		   });
				
			var seriesData = [
				Object.values(depositDataObj),
				Object.values(withdrawDataObj)
			];
		
			chartData = seriesData;
        }, error: function (jqXHR) {
            adminErrorHandler(jqXHR);
        }
    });
	
	return chartData;
}