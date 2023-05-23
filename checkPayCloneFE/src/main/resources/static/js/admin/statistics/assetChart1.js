/*
자산 통계
월별 평균 자산 변동 추이
*/

/**
 * 차트 보여주기
 */
function getAssetChart1() {
	var chart = null;
    var chartData = getAssetChart1Data();

    chart = Highcharts.chart('assetChart1', {

        title: {
            text: '월별 회원 평균 자산 변동',
            align: 'center',
            verticalAlign: 'bottom'
        },
        chart: {
            width: 500,
            height: 300
        },
        exporting: { enabled: false },// 우측상단 햄버거메뉴 숨김
        credits: { enabled: false },// highchart 워터마크 숨김
        tooltip: { enabled: false },// 하이차트 기본툴팁 숨김
        yAxis: {
            title: {
                text: '평균 자산'
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
            name: '평균 자산',
            data: chartData,
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
function getAssetChart1Data() {
    var chartData = null;

	$.ajax({
        url: baseUrl + '/api/admin/statistics/assetChart1Data',
        type: 'get',
        data: {
            selectYear: $('#assetChart1Data_selectYear').val()
        },
        async: false,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        },
        success: function (result) {
			var data = result.data;
			
			var seriesData = {
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
			
			data.map((value, index)=>{
				var month = value.year_month.substr(4,2)+'월';

				seriesData[month] = Number(value.amount_sum);
			});

			chartData = Object.values(seriesData);
        }, error: function (jqXHR) {
            adminErrorHandler(jqXHR);
        }
    });

    return chartData;
}