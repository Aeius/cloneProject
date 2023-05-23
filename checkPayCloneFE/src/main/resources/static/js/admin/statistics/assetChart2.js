/*
자산 통계
자산 연동 비율
*/

/**
 * 차트 보여주기
 */
function getAssetChart2(){
	var chart = null;
    var chartData = getAssetChart2Data();
    
    var colors = ['#07db88', '#e9d737', '#fd8b8b', '#07dbd7', '#ca07db', '#642109cf'];

    /* [S] 차트 파이 타입 */
        chart = Highcharts.chart('assetChart2', {
            title: {
                text: '자산 연동 비율',
                verticalAlign: 'bottom'
            },// 타이틀 숨김
            exporting: { enabled: false },// 우측상단 햄버거메뉴 숨김
            credits: { enabled: false },// highchart 워터마크 숨김
            tooltip: { enabled: false },// 하이차트 기본툴팁 숨김
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie',
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true, // 선택모션
                    slicedOffset: 10, // 슬라이스간격
                    cursor: 'pointer',
                    colors: colors, // 색상지정 [은행, 카드]
                    colorByPoint: true, // 색상지정
                    point: {
                        events: {
                            click: function () {
                                //말풍선 카테고리 정보
                                $('.highcharts-data-label').css({ 'opacity': '1', 'transition': 'all .5s ease' });
                                $('.highcharts-data-label .highcharts-label-box').css({ 'stroke-width': '1px', 'transition': 'all 1s ease' });
                                //말풍선 개별 설정
                                if (this.name === '은행') {
                                    $('.highcharts-data-label').hide();
                                    $('.highcharts-data-label-color-0').show();
                                    if (this.selected === false) {
                                        $('.highcharts-data-label-color-0 .highcharts-label-box').css({ 'stroke-width': '3px' });
                                    } else {
                                        $('.highcharts-data-label:not(.highcharts-data-label-color-0)').show();
                                    }
                                }
                                else if (this.name === '카드') {
                                    $('.highcharts-data-label').hide();
                                    $('.highcharts-data-label-color-1').show();
                                    if (this.selected === false) {
                                        $('.highcharts-data-label-color-1 .highcharts-label-box').css({ 'stroke-width': '3px' });
                                    } else {
                                        $('.highcharts-data-label:not(.highcharts-data-label-color-1)').show();
                                    }
                                }
                                else if (this.name === '대출') {
                                    $('.highcharts-data-label').hide();
                                    $('.highcharts-data-label-color-2').show();
                                    if (this.selected === false) {
                                        $('.highcharts-data-label-color-2 .highcharts-label-box').css({ 'stroke-width': '3px' });
                                    } else {
                                        $('.highcharts-data-label:not(.highcharts-data-label-color-2)').show();
                                    }
                                }else if(this.name === '선불페이'){
									$('.highcharts-data-label').hide();
                                    $('.highcharts-data-label-color-3').show();
                                    if (this.selected === false) {
                                        $('.highcharts-data-label-color-3 .highcharts-label-box').css({ 'stroke-width': '3px' });
                                    } else {
                                        $('.highcharts-data-label:not(.highcharts-data-label-color-3)').show();
                                    }
								}else if(this.name === '통신'){
									$('.highcharts-data-label').hide();
                                    $('.highcharts-data-label-color-4').show();
                                    if (this.selected === false) {
                                        $('.highcharts-data-label-color-4 .highcharts-label-box').css({ 'stroke-width': '3px' });
                                    } else {
                                        $('.highcharts-data-label:not(.highcharts-data-label-color-4)').show();
                                    }
								}else if(this.name === '증권'){
									$('.highcharts-data-label').hide();
                                    $('.highcharts-data-label-color-5').show();
                                    if (this.selected === false) {
                                        $('.highcharts-data-label-color-5 .highcharts-label-box').css({ 'stroke-width': '3px' });
                                    } else {
                                        $('.highcharts-data-label:not(.highcharts-data-label-color-5)').show();
                                    }
								}
                            }// click
                        }// events
                    },// point
                },// pie
            },// plotOptions
            series: [{
                size: '54%',// 크기
                startAngle: 30,// 각도 시작
                endAngle: 390,// 각도 끝
                center: ['50%', '50%'], //pie 위치
                dataLabels: {// 말풍선 설정
                    distance: 1, //말풍선과 그래프의 간격
                    color: '#000',
                    backgroundColor: '#fff',
                    borderWidth: 1,
                    borderRadius: 15,
                    //padding: 2,
                    connectorPadding: -40,
                    overflow: 'inherit',
                    style: {
                        fontFamily: 'notoSans',
                        fontSize: '12px',
                        fontWeight: '500',
                    },
                },
                //말풍선 개별 설정
                data: [
					{
	                    name: '은행',
	                    y: chartData[0],// 100퍼센트 비율
	                    sliced: false,
	                    selected: false,
	                    dataLabels: {
	                        useHTML: true,
	                        format: '<p class="chart_label_box rate_loan"><b> {point.name}</b> <b style="color:'+colors[0]+';"> {point.percentage:1.2f}%</b></p>',
	                        borderColor: colors[0],
	                    },
	                }, {
	                    name: '카드',
	                    y: chartData[1],// 100퍼센트 비율
	                    sliced: false,
	                    selected: false,
	                    dataLabels: {
	                        useHTML: true,
	                        format: '<p class="chart_label_box rate_card"><b> {point.name}</b> <b style="color:'+colors[1]+';"> {point.percentage:1.2f}%</b></p>',
	                        borderColor: colors[1],
	                    },
	                },
	                {
	                    name: '대출',
	                    y: chartData[2],// 100퍼센트 비율
	                    sliced: false,
	                    selected: false,
	                    dataLabels: {
	                        useHTML: true,
	                        format: '<p class="chart_label_box rate_card"><b> {point.name}</b> <b style="color:'+colors[2]+';"> {point.percentage:1.2f}%</b></p>',
	                        borderColor: colors[2],
	                    },
	                },
	                {
	                    name: '선불페이',
	                    y: chartData[3],// 100퍼센트 비율
	                    sliced: false,
	                    selected: false,
	                    dataLabels: {
	                        useHTML: true,
	                        format: '<p class="chart_label_box rate_card"><b> {point.name}</b> <b style="color:'+colors[3]+';"> {point.percentage:1.2f}%</b></p>',
	                        borderColor: colors[3],
	                    },
	                },
	                {
	                    name: '통신',
	                    y: chartData[4],// 100퍼센트 비율
	                    sliced: false,
	                    selected: false,
	                    dataLabels: {
	                        useHTML: true,
	                        format: '<p class="chart_label_box rate_card"><b> {point.name}</b> <b style="color:'+colors[4]+';"> {point.percentage:1.2f}%</b></p>',
	                        borderColor: colors[4],
	                    },
	                },
	                {
	                    name: '증권',
	                    y: chartData[5],// 100퍼센트 비율
	                    sliced: false,
	                    selected: false,
	                    dataLabels: {
	                        useHTML: true,
	                        format: '<p class="chart_label_box rate_card"><b> {point.name}</b> <b style="color:'+colors[5]+';"> {point.percentage:1.2f}%</b></p>',
	                        borderColor: colors[5],
	                    },
	                }
                ]// data
            }]// series
        });

    return chart;
}

/**
 * 차트 관련 데이터 조회
 */
function getAssetChart2Data() {
    var chartData = null; 
    
    $.ajax({
        url: baseUrl + '/api/admin/statistics/assetChart2Data',
        type: 'get',
        async: false,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        },
        success: function (result) {
			var data = result.data[0];
			
           	obj = [
				data.bank_member_percent,
				data.card_member_percent,
				data.capital_loan_member_percent,   
				data.efin_member_percent,   
				data.telecom_member_percent,   
				data.invest_member_percent
			   ];
			   
			    // 객체를 배열로 변환
    		chartData = Object.values(obj);
        }, error: function (jqXHR) {
            adminErrorHandler(jqXHR);
        }
    });
    
    return chartData;
}