<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>VESTAP ADMIN - 통계보기 - 취약성평가 조회 통계</title>
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
							<i class="icon-file-bookmark"></i>취약성평가 조회 통계<a href="javascript:void(0);" class="on-offmenu text-blue" id="menu-close"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
						</div>

						<ul class="list-group list-group-flush">
							<li class="list-group-item sub-title border-top-0"><i
								class="icon-file-align"></i>리스크 선택</li>
								
							<li class="list-group-item">
								<div class="row">
									<div class="col">
		
										<div class=" select_box">
											<select class="form-control" id="fieldList">
											<c:forEach var="i" items="${fieldList }">
												<option value='${i.codeId}'<c:if test="${setField ne null and setField eq i.codeId }">selected</c:if>>${i.codeNm}</option>
											</c:forEach>
											</select>
										</div>
		
									</div>
									<!-- //col -->
								</div> <!-- //row -->
							</li>
							
							<li class="list-group-item sub-title border-top-0"><i
								class="icon-file-align"></i>기간 설정</li>
							<li class="list-group-item">

								<div class="row mb-4">

									<div class="col">
										<div class="mb-1">시작 날짜</div>
										<input type="text" class="form-control datepicker mouse_cursor" min="2000-01-01" max="${now }" id="dateStart" placeholder="연도-월-일" readonly>
									</div>

								</div> <!-- //row -->
								<div class="row ">

									<div class="col">
										<div class="mb-1">끝 날짜</div>
										<input type="text" class="form-control datepicker mouse_cursor"  min="2000-01-01"  max="${now }" id="dateEnd" placeholder="연도-월-일" readonly>
									</div>

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
							<!-- 
							<li class="list-group-item sub-title border-top-0"><i
								class="icon-file-align"></i>표출 형태</li>
							<li class="list-group-item">

								<div class=" select_box">
									<select class="form-control" id="dateType">
										<option value="year" >년</option>
										<option value="month">월</option>
										<option value="day" selected>일</option>
									</select>
								</div>
								
							</li> -->
						</ul>
					</div> <!-- //card -->

				</td>
				<td class="col align-top p-0">
					
					<div class="onmenu-div d-none">
						<a href="javascript:void(0);" class="text-blue on-offmenu"  id="menu-open"><i class="icon-arrow-caret-right"></i></a>
					</div>
					
					<div class="mainContents">
						<!-- mainContents -->
						<div class="card mb-3">
							<div class="card-header p-2">

								<div class="p-1 pl-3">취약성평가 조회 그래프</div>

							</div>
							<!-- card-header -->
							<div class="card-body p-2" style="height: 810px">
								<div>
									<div id="RotatedAxis"></div>
								</div>
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
							
						</div>

						<ul class="list-group list-group-flush">
							<li class="list-group-item sub-title border-top-0" id="titleData"><i
								class="icon-file-align"></i>접속통계 데이터</li>
							<!-- 시간에 따른 취약성 지수 비교 그래프 -->
							<li class="list-group-item">

								<table class="table vestapTable smTable text-center">
									<colgroup>
										<col width="55%">
										<col width="15%">
										<col width="15%">
										<col width="15%">
									</colgroup>
									<thead>
										<tr>
											<th>취약성평가 항목</th>
											<th>전체<br>사용자</th>
											<th>광역<br>지자체</th>
											<th>기초<br>지자체</th>
										</tr>
									</thead>
									<tbody id="estimationStatList">
										<tr>
											<td colspan="4">
												단위, 기간 선택 후 검색하기를 눌러주세요. 
											</td>
										</tr>
										
									</tbody>
								</table>

							</li>
						</ul>
							<input type="hidden" id="estimationStatListPage" value="1">
							<input type="hidden" id="estimationStatListLimit" value="1">
						<!-- pagination 왼쪽버튼 있을 경우 사용 -->
						<div class="nav navbar p-2 mt-2">
							<nav class="form-inline ">
								<ul class="pagination previous mb-0">
									<li class="page-item"><a class="page-link" href="#" onclick="fn_previousPage('estimationStatList');return false;"><i
													class="icon-arrow-caret-left"></i></a></li>
								</ul>
								<div><span class="curPage">1</span>/<span class="limitPage">1</span></div>
								<ul class="pagination next mb-0">
									<li class="page-item"><a class="page-link" href="#" onclick="fn_nextPage('estimationStatList');return false;"><i
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
function fn_buttonSearch(){
	var dateStart = $("#dateStart").val();	//시작날짜
	var dateEnd = $("#dateEnd").val();	//끝날짜
	//var dateType = $("#dateType").val();	//단위 선택 (년, 월, 일)
	var fieldList = $('#fieldList').val();	//리스크 선택값

	if(dateStart > dateEnd){
		fn_alert("경고", "끝날짜는 시작날짜보다 커야합니다.", "error");
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
			//"type" : dateType,
			"startDate" : dateStart,
			"endDate" : dateEnd,
			"fieldId" : fieldList
	};
	$.ajax({
		url: "/admin/stat/estimationlog/List.do",
		data : dataSet,
		dataType: 'json',
		type:"get",
		success: function(result){
			var list = result.list;				//리스크 항목에 해당하는 각 취약성항목의 개별 통계값
			var totalList = result.totalList;	//리스크 항목 전체의 값을 sum한 리스트
			$("#estimationStatList").empty();
			$("#btn_download").empty();

			if(dateStart == "null"){		
				dateStart = "2000-01-01";
			}
			if(dateEnd == "null"){
				dateEnd = "${now}";
			}
			var fieldNm = $("#fieldList option:selected").text();
			var titleText = fieldNm + " ( 기간 : "+dateStart + " ~ " + dateEnd +")";
			//var titleText = fieldNm + " ( 기간 : "+dateStart + " ~ " + dateEnd + " / 단위 : "+$("#dateType option:selected").text()+")";
			$("#titleData").text(titleText);	//리스트 타이틀을 검색한 내용으로 변경해줘
			
			for(var i in totalList){
			var totalHtml = '';
			totalHtml += '<tr style="color:#273142"><td>'+fieldNm+' </td>';
			totalHtml += '<td>'+totalList[i].totalSum+'</td>';
			totalHtml += '<td>'+totalList[i].totalWide+'</td>';
			totalHtml += '<td>'+totalList[i].totalBase+'</td></tr>';
			$("#estimationStatList").append(totalHtml);
			}
			
			for(var i in list){
				var html = '';
				if(i%pageLimit == 0) page++;
				if(page==1){
					html = '<tr class="pg'+page+'" style="display:table-row">';
				}else{
					html = '<tr class="pg'+page+'" style="display:none">';
				}
				html += '<td>'+list[i].itemNm+'</td>';
				html += '<td>'+list[i].sumTotal+'</td>';
				html += '<td>'+list[i].sumWide+'</td>';
				html += '<td>'+list[i].sumBase+'</td>';
				html += '</tr>';
				$("#estimationStatList").append(html);
			}
			fn_logChart(list);	//그래프
			$("#btn_download").append('<button type="button" onClick="fn_downloadStat(\''+dateStart+'\',\''+dateEnd+'\',\''+fieldList+'\',\''+titleText+'\',\''+fieldNm+'\')" class="btn btn-vestap btn-blue w-auto pl-2 pr-2"><i class="icon-download-disk"></i>데이터 다운로드</button>');
			
			$("#estimationStatListPage").val(1);
			$("#estimationStatListLimit").val(page);
			$(".curPage").text("1");
			$(".limitPage").text(page);
		}
	});
}
	/* 취약성평가 통계 결과 리스크 엑셀다운로드 */
	function fn_downloadStat(dateStart, dateEnd, fieldList, titleText, fieldNm){

		var params = "startDate=" + dateStart + "&endDate=" + dateEnd +
					"&fieldId="+ fieldList + "&titleText=" + titleText + "&fieldNm=" + fieldNm;
		
		location.href = encodeURI("/admin/stat/estimationlog/downloadLogList.do?" + params);
	
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

	//초기화 버튼
	function fn_btnReset(){
		//시작 날짜 초기상태 : 연도- 월 - 일
		$('#dateStart').val('');
		//끝날짜 초기상태 : 연도- 월 - 일
		$('#dateEnd').val('');
		//단위 선택 초기상태 : 년
		//$('#dateType').val('day');
		//리스크 항목 초기상태 : 건강부문
		$('#fieldList').val('FC001');
	}
	//그래프를 로딩해줌
	function fn_logChart(list){
		
		var column1=["광역지자체 사용자"];
		var column2=["기초지자체 사용자"];
		var columns=[];
		var category = [];

		for(var i in list){
			column1.push(list[i].sumWide);
			column2.push(list[i].sumBase);
			category.push(list[i].itemNm);
		}
		columns.push(column1);
		columns.push(column2);
		
		chart.categories(category);
		chart.load({
			columns: columns
		});
	}

	//메뉴가 접펼할동안 그래프의 사이즈를 재설정해줌.
	$(document).ready(function() {
		$('#button-search').trigger('click');
		$('#menu-close').click(function(){
			chart.resize({
				   width: 1080
				});
		});
		$('#menu-open').click(function(){
			chart.resize({
				   width: 753
				});
		});
	});
</script>
<script src="/resources/billboard/d3.v5.min.js"></script>
<script src="/resources/billboard/billboard.js"></script>
<script>
// Script
	var chart = bb.generate({
	  data: {
	    columns: [
		["광역지자체 사용자", 0],
		["기초지자체 사용자", 0]
	    ],
	    types: {
	      "광역지자체 사용자": "bar",
	      "기초지자체 사용자": "bar"
	    }
	  },
	   size: {
	        height: 790
	    },
	  axis: {
		    x: {
			      type: "category",
			      categories: [
			
			      ] ,
			      tick: {
			          width: 150
			        }
			    },
	    rotated: true
	  },
	  bar : {
		  width : {
			  max : 15
		  }
	  },
	  bindto: "#RotatedAxis"
	});
</script>
</body>
</html>