/*
회원 통계
회원 활동 통계
*/

/*
 * 차트 보여주기
 */
function getMemberChart1() {
    var chart = null;
    var chartData = getMemberChart1Data();
    
    var colors = ['#07db88', '#e9d737', '#fd8b8b'];

    /* [S] 차트 파이 타입 */
        chart = Highcharts.chart('memberChart1', {
            title: {
                text: '사용자 주요 행위 이력',
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
                    colors: colors, // 색상지정 [자산조회, 자산인증]
                    colorByPoint: true, // 색상지정
                    point: {
                        events: {
                            click: function () {
                                //말풍선 카테고리 정보
                                $('.highcharts-data-label').css({ 'opacity': '1', 'transition': 'all .5s ease' });
                                $('.highcharts-data-label .highcharts-label-box').css({ 'stroke-width': '1px', 'transition': 'all 1s ease' });
                                //말풍선 개별 설정
                                if (this.name === '자산조회') {
                                    $('.highcharts-data-label').hide();
                                    $('.highcharts-data-label-color-0').show();//자산조회
                                    if (this.selected === false) {
                                        $('.highcharts-data-label-color-0 .highcharts-label-box').css({ 'stroke-width': '3px' });
                                    } else {
                                        $('.highcharts-data-label:not(.highcharts-data-label-color-0)').show();
                                    }
                                }
                                else if (this.name === '자산인증') {
                                    $('.highcharts-data-label').hide();
                                    $('.highcharts-data-label-color-1').show();//자산인증
                                    if (this.selected === false) {
                                        $('.highcharts-data-label-color-1 .highcharts-label-box').css({ 'stroke-width': '3px' });
                                    } else {
                                        $('.highcharts-data-label:not(.highcharts-data-label-color-1)').show();
                                    }
                                }
                                else if (this.name === '자산인증') {
                                    $('.highcharts-data-label').hide();
                                    $('.highcharts-data-label-color-2').show();//자산인증
                                    if (this.selected === false) {
                                        $('.highcharts-data-label-color-2 .highcharts-label-box').css({ 'stroke-width': '3px' });
                                    } else {
                                        $('.highcharts-data-label:not(.highcharts-data-label-color-2)').show();
                                    }
                                }
                            }// click
                        }// events
                    },// point
                },// pie
            },// plotOptions
            series: [{
                innerSize: '50%', //도넛모양
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
                data: [{
                    name: '자산조회',
                    y: chartData[0],// 100퍼센트 비율
                    sliced: false,
                    selected: false,
                    dataLabels: {
                        useHTML: true,
                        format: '<p class="chart_label_box rate_loan"><b> {point.name}</b> <b style="color:'+colors[0]+'"> {point.percentage:1.2f}%</b></p>',
                        borderColor: colors[0],
                    },
                }, {
                    name: '자산인증',
                    y: chartData[1],// 100퍼센트 비율
                    sliced: false,
                    selected: false,
                    dataLabels: {
                        useHTML: true,
                        format: '<p class="chart_label_box rate_card"><b> {point.name}</b> <b style="'+colors[1]+'"> {point.percentage:1.2f}%</b></p>',
                        borderColor: colors[1],
                    },
                }, {
                    name: '자산인증',
                    y: chartData[2],// 100퍼센트 비율
                    sliced: false,
                    selected: false,
                    dataLabels: {
                        useHTML: true,
                        format: '<p class="chart_label_box rate_acc"><b> {point.name}</b> <b style="'+colors[2]+'"> {point.percentage:1.2f}%</b></p>',
                        borderColor: colors[2],
                    },
                }]// data
            }]// series
        });

    return chart;
}

/**
 * 차트 관련 데이터 조회
 */
function getMemberChart1Data() {
    var chartData = null;

    $.ajax({
            url: baseUrl + '/api/admin/statistics/memberChart1Data',
            type: 'get',
            async: false,
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
            success: function (result) {
                var data = result.data;

                var obj = [];

                data.map((value, index) => {
                    obj[index] = value.action_type_percent
                });

                // 객체를 배열로 변환
                chartData = Object.values(obj);
            }, error: function (jqXHR) {
                if (jqXHR.status == 401) {
                    tokenCall2();
                }
            }
        });

    return chartData;
}