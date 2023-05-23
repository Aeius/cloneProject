/*
증권 통계
월별 평균 투자 금액 변동 추이
*/

/**
 * 차트 보여주기
 */
function getInvestChart() {
	var chart = null;
    var chartData = getInvestChartData();

    chart = Highcharts.chart('investChart', {

        title: {
            text: '월별 평균 투자 금액 변동 추이',
            align: 'center',
            verticalAlign: 'bottom'
        },
        exporting: { enabled: false },// 우측상단 햄버거메뉴 숨김
        credits: { enabled: false },// highchart 워터마크 숨김
        tooltip: { enabled: false },// 하이차트 기본툴팁 숨김
        yAxis: {
            title: {
                text: '평균 투자 금액'
            }
        },
        xAxis: {
            categories: ['1월','2월','3월','4월','5월','6월',
            	'7월','8월','9월','10월','11월','12월']
        },
        tooltip: {
            crosshairs: true,
            shared: true
        },
        series: [{
            name: '평균 투자 금액',
            data: chartData[0],
            color: '#FF0000'
        }],

        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                chartOptions: {
                    legend: {
                        layout: 'horizontal',
                        align: 'center',
                        verticalAlign: 'bottom'
                    }
                }
            }]
        }

    });

    return chart;
}

/**
 * 차트 관련 데이터 조회
 */
function getInvestChartData() {
    var chartData = null;

	$.ajax({
        url: baseUrl + '/api/admin/statistics/investChartData',
        type: 'get',
        data: {
            selectYear: $('#investChartData_selectYear').val()
        },
        async: false,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        },
        success: function (result) {
			var data = result.data;
			
			var KRWData = data.KRWData;
			var USDData = data.USDData;
	
			var KRWDataObj = {
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
                
            var USDDataObj = {
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
                
            KRWData.map((value, index)=>{
				var month = value.trans_month.substr(4,2);
				
				KRWDataObj[month+'월'] = Number(value.trans_amt_avg);
		   });
		   
		   USDData.map((value, index)=>{
				var month = value.trans_month.substr(4,2);
				
				USDDataObj[month+'월'] = Number(value.trans_amt_avg);
		   });
		   
		   var seriesData = [
				Object.values(KRWDataObj),
				Object.values(USDDataObj)
			];
		
			chartData = seriesData;
                
        }, error: function (jqXHR) {
            if (jqXHR.status == 401) {
                tokenCall2();
            }
        }
    });

    return chartData;
}