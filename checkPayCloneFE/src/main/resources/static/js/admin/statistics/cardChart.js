/*
카드 업권 통계
1년간 카테고리 별 지출 비율
*/

/**
 * 차트 보여주기
 */
function getCardChart(){
	var chart = null;
    var chartData = getCardChartData();
    
    chart = Highcharts.chart('cardChart', {
	  chart: {
	    type: 'bar'
	  },
	  title: {
	    text: '월별 카테고리 별 지출 비율'
	  },
      exporting: { enabled: false },// 우측상단 햄버거메뉴 숨김
      credits: { enabled: false },// highchart 워터마크 숨김
      tooltip: {
	    pointFormat: '<b>{series.name}</b> 에서 해당 월에 전체 비중의 <b>{point.y:,.0f}</b>% 만큼 지출하였습니다.'
	  },
	  xAxis: {
	    categories: chartData.categories
	  },
	  yAxis: {
	    min: 0,
	    title: {
	      text: '평균 지출 비율'
	    }
	  },
	  legend: {
	    reversed: true
	  },
	  plotOptions: {
	    series: {
	      stacking: 'normal',
	      dataLabels: {
	        enabled: true
	      }
	    }
	  },
	  series: chartData.series
	});
	
	return chart;
}

function getCardChartData(){
	var obj = [];
	var chartData = null;

	$.ajax({
        url: baseUrl + '/api/admin/statistics/cardChartData',
        type: 'get',
        data: {
            selectYear: $('#cardChartData_selectYear').val()
        },
        async: false,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        },
        success: function (result) {
            var data = result.data;

            var monthArr = [];
            var cateArr = [];
            var dataObj = [];
            var series = [];

			if(data.length>0){
					/*
				월, 카테고리를 배열로 분류
				*/
	            data.map((value, index) => {
					var month = value.approved_month+'월';
					
					if(monthArr.indexOf(month)===-1){
						monthArr.push(month);
					}
					if(cateArr.indexOf(value.catg_nm)===-1){
						cateArr.push(value.catg_nm);
						cateArr[value.catg_nm] = [];
					}
					
	            });
	            
	            /*
	            분류한 카테고리 배열의 원소들을 월의 갯수 만큼 0으로 초기화
	            */
	            cateArr.map((value2,index)=>{
					monthArr.map((value1,index)=>{
						cateArr[value2].push(null); 
					});
				});
				
				/*
				분류한 카테고리 배열에 맞게 데이터 저장
				*/
				data.map((value, index) => {
					var month = value.approved_month+'월';
					var catg_nm = value.catg_nm;
	
					cateArr[catg_nm][monthArr.indexOf(month)] = Number(value.approved_amt_percent)!=0?Number(value.approved_amt_percent):null;
	            });
	            
	            dataObj = cateArr;
	               
				dataObj.map((value, index)=>{
					series.push({
						name:value,
						data:dataObj[value]
					});
				});
			}

			chartData = {
				series:series,
				categories:monthArr
			};
        }, error: function (jqXHR) {
            adminErrorHandler(jqXHR);
        }
    });
	
	return chartData;
}