/*
대출 통계
월별 대출 잔액 변동 추이
*/

/**
 * 차트 보여주기
 */
function getTelecomChart() {
	var chart = null;
    var chartData = getTelecomChartData();

    chart = Highcharts.chart('telecomChart', {
		  chart: {
		    type: 'column'
		  },
		  title: {
		    text: '월별 평균 청구 금액'
		  },
	      exporting: { enabled: false },// 우측상단 햄버거메뉴 숨김
	      credits: { enabled: false },// highchart 워터마크 숨김
	      tooltip: { enabled: false },// 하이차트 기본툴팁 숨김
		  xAxis: {
		    categories: [
		      '1월',
		      '2월',
		      '3월',
		      '4월',
		      '5월',
		      '6월',
		      '7월',
		      '8월',
		      '9월',
		      '10월',
		      '11월',
		      '12월'
		    ],
		    crosshair: true
		  },
		  yAxis: {
		    min: 0,
		    title: {
		      text: '평균 청구 금액'
		    }
		  },
		  tooltip: {
		    pointFormat: '해당 월에 평균적으로 <b>{point.y:,.0f}</b> 원 만큼 청구하였습니다.'
		  },
		  plotOptions: {
		    column: {
		      pointPadding: 0.2,
		      borderWidth: 0
		    }
		  },
		  series: [{
		    name: '평균 청구 금액',
		    data: chartData
		  }]
		});

    return chart;
}

/**
 * 차트 관련 데이터 조회
 */
function getTelecomChartData() {
    var chartData = null;

	$.ajax({
        url: baseUrl + '/api/admin/statistics/telecomChartData',
        type: 'get',
        data: {
            selectYear: $('#telecomChartData_selectYear').val()
        },
        async: false,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        },
        success: function (result) {
			var data = result.data;
			
			var dataObj = {
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
			
			data.map((value,index)=>{
				var month = value.charge_month.substr(4,2)+'월';
				
				dataObj[month] = Number(value.charge_amt_avg);
			});
			
			chartData = Object.values(dataObj);
        }, error: function (jqXHR) {
            adminErrorHandler(jqXHR);
        }
    });

    return chartData;
}