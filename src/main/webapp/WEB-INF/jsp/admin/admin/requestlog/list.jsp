<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>VESTAP ADMIN - 관리자 작업 이력</title>
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
							<i class="icon-file-bookmark"></i>작업이력<a href="javascript:void(0);" class="on-offmenu text-blue" id="menu-close"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
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
								class="icon-file-align"></i>관리자</li>
							<li class="list-group-item">

								<div class=" select_box">
									<select class="form-control" id="adminId">
										<option value="all">전체</option>
										<c:if test="${not empty adminIdList}">
											<c:forEach var="i" items="${adminIdList }">
												<option value="${i.userId }" <c:if test="${adminId ne null and adminId eq i.userId }">selected</c:if>>${i.userId }</option>
											</c:forEach>
										</c:if>
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
						<!-- //card -->
						<input type="hidden" id="requestLogListPage" value="1">
						<input type="hidden" id="requestLogListLimit" value="${pageCnt }">
						<table class="table table-hover vestapTable text-center">
							<thead>
								<tr>
									<th style="width: 8%;">번호</th>
									<th style="width: 10%;">아이디<!-- &nbsp;<i class="icon-arrow-line-up01 vt-md id-up"></i><i class="icon-arrow-line-down01 vt-md id-down"></i> --></th>
									<th style="width: 12%">접근 시간</th>
									<!-- <th style="width: 30%">작업 URL</th> -->
									<!-- <th style="width: 8%">메뉴 1</th> -->
									<th style="width: 16%">메뉴 1</th>
									<th style="width: 16%">메뉴 2</th>
									<th style="width: 8%">구분</th>
									<th style="width: 30%">상세내역</th>
									<!-- <th style="width: 8%">메뉴 5</th> -->
								</tr>
							</thead>
							<tbody id="requestLogList">
								<c:if test="${not empty requestLogList}">
									<c:forEach var="i" items="${requestLogList }">
										<tr class="pg${i.page}"
										<c:if test="${i.page == 1 }">style="display:table-row"</c:if>
										<c:if test="${i.page != 1 }">style="display:none"</c:if>
										>
											<td>${i.no }</td>
											<td>${i.userId}</td>
											<td>${i.logDate}</td>
											<%-- <td>${i.reqUrl}</td> --%><!-- 작업 URL -->
											<td>${i.urlDepth2}</td>
											<td>${i.urlDepth3}</td>
											<td>${i.urlDepth4}</td>
											<td>
												${i.updateParam}
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty requestLogList}">
									<tr><td colspan="6">이력이 존재하지 않습니다.</td></tr>
								</c:if>
							</tbody>
						</table>
						<div class="nav navbar p-0 mt-4">
							<nav class="form-inline ">
								<ul class="pagination previous disabled" >
									<li class="page-item"><a class="page-link" href="#" onclick="fn_previousPage('requestLogList');return false;"><i
											class="icon-arrow-caret-left"></i></a></li>
								</ul>
								<div><span id="requestLogListCurPage">1</span>/<span id="requestLogListLimitPage">${pageCnt}</span></div>
								<ul class="pagination next" >
									<li class="page-item"><a class="page-link" href="#" onclick="fn_nextPage('requestLogList');return false;"><i
											class="icon-arrow-caret-right"></i></a></li>
								</ul>
							</nav>
							<div id="btn_download">
								<button type="button" onClick="fn_downloadLog('all','null','null')" class="btn btn-vestap btn-blue w-auto pl-2 pr-2">
									<i class="icon-download-disk"></i>다운로드
								</button>
								<!-- <button type="button"
									class="btn btn-vestap btn-blue w-auto pl-2 pr-2">
									<i class="icon-download-disk"></i>데이터 다운로드
								</button> -->
							</div>
						</div>
					</div> <!-- //mainContents -->

				</td>
				
				
			</tr>
		</table>
<script>
	
	$(document).ready(function() {
		
		/* fn_getRefCode();
		
		fn_separate(); */
	});
	
	/* 천단위 콤마 찍기 */
	function fn_addComma(num) {
		  var regexp = /\B(?=(\d{3})+(?!\d))/g;
		  return num.toString().replace(regexp, ',');
	}
	//검색하기 버튼
	function fn_buttonSearch(){
		var dateStart = $("#dateStart").val();	//시작날짜
		var dateEnd = $("#dateEnd").val();	//끝날짜
		var adminId = $("#adminId").val();	//관리자 계정 선택
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
				"adminId" : adminId,
				"startDate" : dateStart,
				"endDate" : dateEnd
		};
		$.ajax({
			url: "/admin/admin/requestlog/DateList.do",
			data: dataSet,
			dataType: 'json',
			type:"get",
			success: function(result){
				var logList = result.requestLogList;
				$("#requestLogList").empty();
				$("#btn_download").empty();
				console.log(logList.length);
				console.log(logList);
				if(logList.length > 0){
					for(var i in logList){
						var html = '';
						if(i%pageLimit == 0) page++;
						if(page==1){
							html = '<tr class="pg'+page+'" style="display:table-row">';
						}else{
							html = '<tr class="pg'+page+'" style="display:none">';
						}
						
						html += '<td>'+logList[i].no+'</td>';
						html += '<td>'+ logList[i].userId +'</td>';
						html += '<td>'+ logList[i].logDate +'</td>';
						html += '<td>'+ logList[i].reqUrl +'</td>';
						html += '<td>'+ logList[i].urlDepth2 +'</td>';
						html += '<td>'+ logList[i].urlDepth3 +'</td>';
						html += '<td>'+ logList[i].urlDepth4 +'</td>';
						html+='</ tr>';
						$("#requestLogList").append(html);
			
					}
				}else{
					var html ='';
					html ='<tr><td colspan="6">이력이 존재하지 않습니다.</td></tr>';
					$("#requestLogList").append(html);
				}
				
				$("#btn_download").append('<button type="button" onClick="fn_downloadLog(\''+adminId+'\',\''+dateStart+'\',\''+dateEnd+'\')" class="btn btn-vestap btn-blue w-auto pl-2 pr-2"><i class="icon-download-disk"></i>데이터 다운로드</button>');
				
				//fn_logChart(logList, dateTotal);
				
				$("#requestLogListPage").val(1);
				$("#requestLogListPageLimit").val(page);
				$("#requestLogListCurPage").text("1");
				$("#requestLogListLimitPage").text(page);
			},
		     error:function(request,status,error){
		         //console.log("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
		         console.log("code = "+ request.status + " error = " + error); // 실패 시 처리
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
		$('#adminId').val('all');
	
	}
	function fn_downloadLog(id, dateStart, dateEnd){
		var params = "adminId="+ id + "&startDate=" + dateStart + "&endDate=" + dateEnd;

		location.href = "/admin/admin/requestlog/downloadRequestLogList.do?" + params;

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
		$("#"+id+"CurPage").text('');
		$("#"+id+"CurPage").text(page);
	}
	//다음페이지
	function fn_nextPage(id){
		var page = $("#"+id+"Page").val()*1;
		var limitPage =$("#"+id+"Limit").val();
		console.log(page + " /" + limitPage);
		if(page<limitPage){
		$("#"+id+" .pg"+page).css("display","none");
		page++;
		$("#"+id+" .pg"+page).css("display","table-row");
		}
		$("#"+id+"Page").val(page);
		$("#"+id+"CurPage").text('');
		$("#"+id+"CurPage").text(page);
	}
	
	function fn_separate(){
		var arrList = $("#requestLogList");
		
		$("#requestLogList tr").each(function(index, value){
			var map = $(this).find('td').eq(6).text();
			map = map.trim();
			var html = '';
			
			
			if(map.length > 0){
				var mapSplit = map.split('&');	
				for(var i in mapSplit){
					if(mapSplit[i].indexOf('csrf') < 0 ){
						console.log(mapSplit[i]);
						
						var params = mapSplit[i].split('=');
						var key = params[0];
						var value = params[1];
						
						if(key== 'adminId' || key == 'userId'){
							html += '계정 : ';
						}else if(key == 'userNm'){
							html += '이름 : ';
						}else if(key == 'startDate'){
							html += '시작일 : ';
						}
						else if(key == 'endDate'){
							html += '종료일 : ';
						}else if(key == 'sidoCode'){
							html += '시도 : ';
						}else if(key == 'item_id' ){
							html += '항목 명 : ';
						}
						else if(key == 'keyword'){
							html += '검색어 : ';
						}else{
							html += key + ': ';
						}
						
						if(value == 'null'){
							html += '선택없음';
						}else if(value == 'all'){
							html += '전체';
						}
						else{
							html += fn_matching(value);	
						}
						html += ' ';
					}
				}
				console.log("-----------------------------------------");
				$(this).find('td').eq(6).html(html);
			}
			
			
		});
	}
	
	
	var ref_code = new Array();
	function fn_getRefCode(){
		//var refcode = "${refCode}";
		
		$.ajax({
			
			url: '/admin/admin/requestlog/refcode.do',
			dataType: 'json',
			type: 'get',
			async:false,
			success: function(data){
				var list = data.refCode;
	    		for(var i = 0; i< list.length; i++){
	    			var map = {
		    				codeId : list[i].codeId,
		    				codeNm : list[i].codeNm
		    		}	
	    			ref_code.push(map);	
	    		}
	    		
	    				
			},
		     error:function(request,status,error){
		         console.log("code = "+ request.status + " error = " + error); // 실패 시 처리
	        }
		});
		
		
		return ref_code;
	}
	
	function fn_matching(value){
		
		for(var i = 0; i<ref_code.length; i++){
			if(value == ref_code[i].codeId){
				value = ref_code[i].codeNm;
				break;
			}
		}
		
		return value;
	}
</script>
</body>
</html>