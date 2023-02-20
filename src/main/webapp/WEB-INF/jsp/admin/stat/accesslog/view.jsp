<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>VESTAP ADMIN - 통계보기 - 접속 통계</title>
<link href="/resources/billboard/billboard.css" rel="stylesheet">
<link href="/resources/billboard/insight.css" rel="stylesheet">
  <script>
  var startday = '2000-01-01'
	  var startYear = '2000'
	  var today = $.datepicker.formatDate('yy-mm-dd', new Date());
	  var todayYear = $.datepicker.formatDate('yy', new Date());

  $( function() {               
    $( ".datepicker" ).datepicker({
		dateFormat: "yy-mm-dd",
		monthNamesShort: ["1","2","3","4","5","6","7","8","9","10","11","12"],
		dayNamesMin:["일","월","화","수","목","금","토"],
		showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
		,changeYear: true //콤보박스에서 년 선택 가능
		,yearRange: startYear+":"+todayYear
		,changeMonth: true //콤보박스에서 월 선택 가능   
		,dateFormat: "yy-mm-dd"
		,minDate: startday
		,maxDate: new Date(today)
    });
  } );
  </script>
</head>
<body>
		<table class="offcanvas-table">
			<tr>
				<td class="offcanvas-left-open">
					<!-- *********************** offcanvas-left-open *********************** -->

					<div class="card">
						<div class="card-header offcanvas-left-open-title">
							<i class="icon-file-bookmark"></i>접속통계<a href="javascript:void(0);" class="on-offmenu text-blue" id="menu-close"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
						</div>

						<ul class="list-group list-group-flush">
							
							<li class="list-group-item sub-title border-top-0"><i
								class="icon-file-align"></i>기간 설정</li>
							<li class="list-group-item">

								<div class="row mb-4">

									<div class="col">
										<div class="mb-1">시작 날짜</div>
										<input type="text" class="form-control datepicker mouse_cursor" min="2000-01-01" max="${now }"
											id="dateStart" placeholder="연도-월-일" readonly>
									</div>

								</div> <!-- //row -->
								<div class="row ">

									<div class="col">
										<div class="mb-1">끝 날짜</div>
										<input type="text" class="form-control datepicker mouse_cursor" min="2000-01-01"  max="${now }"
											id="dateEnd" placeholder="연도-월-일" readonly>
									</div>

								</div> <!-- //row -->
								
							</li>
							
							<li class="list-group-item sub-title border-top-0"><i
								class="icon-file-align"></i>표출 형태</li>
							<li class="list-group-item">

								<div class=" select_box">
									<select class="form-control" id="dateType">
										<option value="year" >년</option>
										<option value="month">월</option>
										<option value="day" selected>일</option>
									</select>
								</div> <!-- //row -->
								<div class="row mt-3">
									<div class="col pr-2">

										<button class="btn btn-vestap btn-blue w-100" type="button"
											id="button-search" onclick="fn_buttonSearch();">
											<i class="icon-search"></i>검색하기
										</button>

									</div>
									<div class="col pl-2">
										<button class="btn btn-vestap btn-blue w-100" type="button"
											id="button-reset" onclick="fn_btnReset();">
											<i class="icon-refresh-modify"></i>초기화
										</button>
									</div>
								</div> <!-- //row -->
							</li>
						</ul>
					</div> <!-- //card -->

				</td>
				<td class="col align-top p-0">
					
					<div class="onmenu-div d-none">
						<a href="javascript:void(0);" class="text-blue on-offmenu" id="menu-open"><i class="icon-arrow-caret-right"></i></a>
					</div>
					
					<div class="mainContents">
						<!-- mainContents -->
						<div class="card mb-3">
							<div class="card-header p-2">

								<div class="p-1 pl-3">접속 통계 그래프</div>

							</div>
							<!-- card-header -->
							<div class="card-body p-2" style="height: 350px">
								<div>
									<div id="lineChart"></div>
								</div>
							</div>
							<!-- card-body -->
						</div>
						<!-- //card -->
						
						<div class="card mb-3">
							<div class="card-header p-2">

								<div class="p-1 pl-3">사용자 권한별 접속 그래프</div>

							</div>
							<!-- card-header -->
							<div class="card-body p-2" style="height: 350px">
								<div id="BarChart"></div>
									
							</div>
							<!-- card-body -->
						</div>
						<!-- //card -->
					</div> <!-- //mainContents -->

				</td>
				<td class="offcanvas-right-open active" id="offcanvas-view">
					<!-- *********************** offcanvas-right-open *********************** -->

					<div class="card active">
						<div class="card-header offcanvas-right-open-title">
							<i class="icon-file-bookmark"></i>접속통계 상세정보
							<!-- <button type="button" class="offcanvasCloseBtn close">
								<i class="icon-close-bold"></i>
							</button> -->
						</div>

						<ul class="list-group list-group-flush">
							<li class="list-group-item sub-title border-top-0 "><i class="icon-file-align"></i>접속통계 데이터</li>
							<!-- 시간에 따른 취약성 지수 비교 그래프 -->
							<li class="list-group-item">

								<table class="table vestapTable smTable text-center">
									<thead>
										<tr>
											<th>번호</th>
											<th>날짜</th>
											<th>광역 지자체 <br>사용자 접속 횟수</th>
											<th>기초 지자체 <br>사용자 접속 횟수</th>
										</tr>
									</thead>
									<tbody id="logList">
										<tr>
											<td colspan="4">
												단위, 기간 선택 후 검색하기를 눌러주세요. 
											</td>
										</tr>
									</tbody>
								</table>

							</li>
						</ul>
							<input type="hidden" id="logListPage" value="1">
							<input type="hidden" id="logListLimit" value="1">
						<!-- pagination 왼쪽버튼 있을 경우 사용 -->
						<div class="nav navbar p-2 mt-2">
							<nav class="form-inline ">
								<ul class="pagination previous mb-0">
									<li class="page-item"><a class="page-link" href="#" onclick="fn_previousPage('logList');return false;"><i
													class="icon-arrow-caret-left"></i></a></li>
								</ul>
								<div><span class="curPage">1</span>/<span class="limitPage">1</span></div>
								<ul class="pagination next mb-0">
									<li class="page-item"><a class="page-link" href="#" onclick="fn_nextPage('logList');return false;"><i
													class="icon-arrow-caret-right"></i></a></li>
								</ul>
							</nav>
							<!-- //pagination -->
							<div id="btn_download">
								<!-- <button type="button"
									class="btn btn-vestap btn-blue w-auto pl-2 pr-2">
									<i class="icon-download-disk"></i>데이터 다운로드
								</button> -->
							</div>
						</div>
						<!-- //nav -->
					</div> <!-- //card -->

				</td>
			</tr>
		</table>
<script>
	/* 천단위 콤마 찍기 */
	function fn_addComma(num) {
		  var regexp = /\B(?=(\d{3})+(?!\d))/g;
		  return num.toString().replace(regexp, ',');
	}
	//검색하기 버튼
	function fn_buttonSearch(){
		var dateStart = $("#dateStart").val();	//시작날짜
		var dateEnd = $("#dateEnd").val();	//끝날짜
		var dateType = $("#dateType").val();	//단위 선택 (년, 월, 일)
		var dateTotal = [];	//날짜
		
		if(dateStart > dateEnd){
			fn_alert("경고", "끝날짜는 시작날짜보다 커야합니다.", "info");
			return false;
		}
		if(dateStart == ""){		
			dateStart = "null";
		}
		if(dateEnd == ""){
			dateEnd = "null";
		}
		var page = 0;
		var pageLimit = 10;
		var dataSet = {
				"type" : dateType,
				"startDate" : dateStart,
				"endDate" : dateEnd
		};
		$.ajax({
			url: "/admin/stat/accesslog/logDateList.do",
			data: dataSet,
			dataType: 'json',
			type:"get",
			success: function(result){
				var logList = result.logList;
				$("#logList").empty();
				$("#btn_download").empty();
				
				for(var i in logList){
					var html = '';
					var datelist = [];
					if(i%pageLimit == 0) page++;
					if(page==1){
						html = '<tr class="pg'+page+'" style="display:table-row">';
					}else{
						html = '<tr class="pg'+page+'" style="display:none">';
					}
					
					html += '<td>'+logList[i].rnum+'</td>'
					if(logList[i].logMonth == null){
						datelist[i] = logList[i].logYear
						
					}else if(logList[i].logDay == null){
						datelist[i] = logList[i].logYear + '-'+logList[i].logMonth
						
					}else {
						datelist[i] = logList[i].logYear + '-'+logList[i].logMonth + '-'+logList[i].logDay
					
					}
						html += '<td>'+ datelist[i] +'</td>'
						dateTotal.push(datelist[i]);	//년도를 계속 넣어줘 차트에서 받아오기 위해
						
					if(logList[i].sumWide == null){
						html += '<td>-</td>'
					}else {
						var sumWide = fn_addComma(logList[i].sumWide);
						html += '<td>'+ sumWide +'</td>'
					}
					
					if(logList[i].sumBase == null){
						html += '<td>-</td>'
					}else {
						var sumBase = fn_addComma(logList[i].sumBase);
						html += '<td>'+sumBase+'</td>'
					}
					html+='</ tr>';
					$("#logList").append(html);
		
				}
				$("#btn_download").append('<button type="button" onClick="fn_monthlyStat()" class="btn btn-vestap btn-green w-auto pl-2 pr-2"><i class="icon-download-disk"></i>월별 누적통계</button>');
				$("#btn_download").append('<button type="button" onClick="fn_downloadStat(\''+dateType+'\',\''+dateStart+'\',\''+dateEnd+'\')" class="btn btn-vestap btn-blue w-auto pl-2 pr-2"><i class="icon-download-disk"></i>데이터 다운로드</button>');
				
				fn_logChart(logList, dateTotal);
				
				$("#logListPage").val(1);
				$("#logListLimit").val(page);
				$(".curPage").text("1");
				$(".limitPage").text(page);
			}
		});
		 
	}
	//초기화 버튼
	function fn_btnReset(){
		//시작 날짜 초기상태 : 연도- 월 - 일
		$('#dateStart').val('');
		//끝날짜 초기상태 : 연도- 월 - 일
		$('#dateEnd').val('');
		//단위 선택 초기상태 : 년
		$('#dateType').val('day');
	
	}
	function fn_downloadStat(dateType, dateStart, dateEnd){
		var params = "type="+ dateType + "&startDate=" + dateStart + "&endDate=" + dateEnd;

		location.href = "/admin/stat/accesslog/downloadLogList.do?" + params;

	}
	
	function fn_monthlyStat(){
		var now = new Date();
		var year = now.getFullYear();
		var month = now.getMonth()+1;
		var day = now.getDate();
		
		var hours = now.getHours(); // 시
		console.log(year + " - " + month + " - " +day + " / " + hours + " 시");
		var params = "year="+ year + "&month=" + month + "&day=" + day + "&hours=" + hours;
		
		location.href = "/admin/stat/accesslog/downloadMonthLog.do?" + params;
	}
	//이전페이지
	function fn_previousPage(id){
		var page = $("#"+id+"Page").val()*1;
		if(page>1){
		$("#"+id+" .pg"+page).css("display","none");
		page--;
		$("#"+id+" .pg"+page).css("display","table-row");
		}
		$("#"+id+"Page").val(page);
		$(".curPage").text('');
		$(".curPage").text(page);
	}
	//다음페이지
	function fn_nextPage(id){
		var page = $("#"+id+"Page").val()*1;
		var limitPage =$("#"+id+"Limit").val();
		if(page<limitPage){
		$("#"+id+" .pg"+page).css("display","none");
		page++;
		$("#"+id+" .pg"+page).css("display","table-row");
		}
		$("#"+id+"Page").val(page);
		$(".curPage").text('');
		$(".curPage").text(page);
	}

	function fn_logChart(logList, dateTotal){
		var listWide = [];
		var listBase = [];
		var listTotal = [];
		var date = [];
		var category = [];
		/* 첫번째 line 그래프 */
		for(var i in logList){
			if(logList[i].sumWide == null){
				listWide[i] = 0;
			} else{
				listWide[i] = logList[i].sumWide;
			}
			
			if(logList[i].sumBase == null){
				listBase[i] = 0;
			} else{
				listBase[i] = logList[i].sumBase;
			}
			listTotal[i] = listWide[i] + listBase[i];
		}
		for(var i in dateTotal){
			date[i] = dateTotal[i];
		}

		for(var i in date){
			category.push(date[i]);
		}
		
		var columnT1=["전체 사용자"];
		var columnT=[];

		listTotal.reverse();	//배열 거꾸로 정렬
		for(var i in listTotal){
			columnT1.push(listTotal[i]);
		}
		columnT.push(columnT1);
		category.reverse();
		
		lineChart.categories(category);
		lineChart.load({
			columns: columnT
		});
		
		/* 2번째 bar 그래프 */
		var column1=["광역지자체 사용자"];
		var column2=["기초지자체 사용자"];
		var columns=[];

		listWide.reverse();
		listBase.reverse();
		for(var i in listWide){
			column1.push(listWide[i]);
		}
		for(var i in listBase){
			column2.push(listBase[i]);
		}
		columns.push(column1);
		columns.push(column2);
		//밑의 bar 차트

		BarChart.categories(category);
		BarChart.load({
			columns: columns
			
		});
		
	}

	//메뉴가 접펼할동안 그래프의 사이즈를 재설정해줌.
	$(document).ready(function() {
		$('#button-search').trigger('click');
		$('#menu-close').click(function(){
			lineChart.resize({
				   width: 1080
				});
			BarChart.resize({
				   width: 1080
				});
		});
		$('#menu-open').click(function(){
			lineChart.resize({
				   width: 753
				});
			BarChart.resize({
				   width: 753
				});
		});
	});
</script>
<script src="/resources/billboard/d3.v5.min.js"></script>
<script src="/resources/billboard/billboard.js"></script>
<script>

var lineChart = bb.generate({
  data: {
    columns: [
		["전체 사용자", 0]
    ],
    colors: {
    	"전체 사용자": "#1F83CE",
      }
  },
  padding: {
      bottom: 20
  },
  axis: {
	    x: {
		      type: "category",
		      categories: [
		
		      ],
			  tick: {
			        rotate: 0,
			        multiline: false,
			        tooltip: true,
				      culling: {
				        max: 6
			      }
			  }
		    }
	  },
  bindto: "#lineChart"
});

var BarChart = bb.generate({
  data: {
    columns: [
		["광역지자체 사용자", 0],
		["기초지자체 사용자", 0]
    ],
    type: "bar"
  },
  bar: {
    width: {
      ratio: 0.5
    }
  },
  padding: {
      bottom: 20
  },
  axis: {
	    x: {
		      type: "category",
		      categories: [
		
		      ],
			  tick: {
			        rotate: 0,
			        multiline: false,
			        tooltip: true,
				      culling: {
				        max: 6
			      }
			  }
	    },
	  },
  bindto: "#BarChart"
});

</script>
</body>
</html>