/*
회원 통계
월별 가입자 통계
*/

/**
 * 차트 보여주기
 */
function getMemberChart2() {
    var chart = null;
    var chartData = getMemberChart2Data();

    chart = Highcharts.chart('memberChart2', {

        title: {
            text: '월별 가입자 현황',
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
                text: '가입자 수'
            }
        },

        xAxis: {
            categories: ['1월', '2월', '3월', '4월', '5월', '6월',
                '7월', '8월', '9월', '10월', '11월', '12월']
        },
        tooltip: {
            crosshairs: true,
            shared: true
        },
        series: [{
            name: '월별 가입자 수',
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
function getMemberChart2Data() {
    var chartData = null;

    $.ajax({
        url: baseUrl + '/api/admin/statistics/memberChart2Data',
        type: 'get',
        data: {
            selectYear: $('#memberChart2Data_selectYear').val()
        },
        async: false,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        },
        success: function (result) {
            var data = result.data;

            var obj = {
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
                
            data.map((value, index) => {
                var dataMonth = value.reg_date.substr(4, 2)+'월';
                var memberCount = value.member_count;

                obj[dataMonth] += memberCount;
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