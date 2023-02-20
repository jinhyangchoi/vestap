<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>VESTAP ADMIN - 관리자 접속 이력</title>
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
									<select class="form-control" id="adminId">
										<option value="all" selected>전체</option>
										<c:if test="${not empty adminIdList}">
											<c:forEach var="i" items="${adminIdList }">
												<option value="${i.userId }">${i.userId }</option>
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
						<!-- <div class="card mb-3">
							<div class="card-header p-2">

								<div class="p-1 pl-3">접속 통계 그래프</div>

							</div>
							card-header
							<div class="card-body p-2" style="height: 350px">
								<div>
									<div id="lineChart"></div>
								</div>
							</div>
							card-body
						</div>
						//card
						
						<div class="card mb-3">
							<div class="card-header p-2">

								<div class="p-1 pl-3">사용자 권한별 접속 그래프</div>

							</div>
							card-header
							<div class="card-body p-2" style="height: 350px">
								<div id="BarChart"></div>
									
							</div>
							card-body
						</div> -->
						<!-- //card -->
						<input type="hidden" id="adminLogListPage" value="1">
						<input type="hidden" id="adminLogListLimit" value="${pageCnt }">
						<table class="table table-hover vestapTable text-center">
							<thead>
								<tr>
									<th style="width: 8%;">번호</th>
									<th style="width: 20%;">아이디&nbsp;<!-- <i class="icon-arrow-line-up01 vt-md id-up"></i><i class="icon-arrow-line-down01 vt-md id-down"></i> --></th>
									<th style="width: 20%;">이름&nbsp;<!-- <i class="icon-arrow-line-up01 vt-md name-up"></i><i class="icon-arrow-line-down01 vt-md name-down"></i> --></th>
									<th style="width: 20%">접속 IP</th>
									<th style="width: 12%">접속 일시</th>
									<th style="width: 20%">활동 로그</th>
								</tr>
							</thead>
							<tbody id="adminLogList">
								<c:if test="${not empty adminLogList}">
									<c:forEach var="i" items="${adminLogList }">
										<tr class="pg${i.page}"
										<c:if test="${i.page == 1 }">style="display:table-row"</c:if>
										<c:if test="${i.page != 1 }">style="display:none"</c:if>
										>
											<td>${i.no }</td>
											<td>${i.userId}</td>
											<td>${i.userNm}</td>
											<td>${i.accessIp}</td>
											<td>${i.logDate}</td>
											<td>
												<button type="button" class="btn btn-sm btn-blue" data="${i.userId}" onclick = "fn_viewRequest($(this));return false;">
													<i class="icon-file-list"></i>보기
												</button>
											</td>
											<%-- <td><button type="button" class="btn btn-sm btn-blue" data="${i.userId}" onclick="fn_updateUser($(this));return false;">
												<i class="icon-file-write-edit"></i>수정
											</button>
											<button type="button" class="btn btn-sm btn-red" data="${i.userId}" onclick="fn_deleteUser($(this));return false;">
												<i class="icon-file-remove"></i>삭제
											</button>
											</td> --%>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty adminLogList}">
									<tr><td colspan="6">이력이 존재하지 않습니다.</td></tr>
								</c:if>
							</tbody>
						</table>
						<div class="nav navbar p-0 mt-4">
							<nav class="form-inline ">
								<ul class="pagination previous disabled" >
									<li class="page-item"><a class="page-link" href="#" onclick="fn_previousPage('adminLogList');return false;"><i
											class="icon-arrow-caret-left"></i></a></li>
								</ul>
								<div><span id="adminLogListCurPage">1</span>/<span id="adminLogListLimitPage">${pageCnt}</span></div>
								<ul class="pagination next" >
									<li class="page-item"><a class="page-link" href="#" onclick="fn_nextPage('adminLogList');return false;"><i
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
			url: "/admin/admin/accesslog/DateList.do",
			data: dataSet,
			dataType: 'json',
			type:"get",
			success: function(result){
				var logList = result.adminLogList;
				$("#adminLogList").empty();
				$("#btn_download").empty();
				console.log(logList.length);
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
						html += '<td>'+ logList[i].userNm +'</td>';
						
						if(logList[i].accessIp){
							html += '<td>'+ logList[i].accessIp +'</td>';	
						}else{
							html += '<td>'+ ' ' +'</td>';
						}
						var d = new Date(logList[i].logDate);
						d = new Date(d.getTime() - 3000000);
						var date_format_str = d.getFullYear().toString()+"-"+((d.getMonth()+1).toString().length==2?(d.getMonth()+1).toString():"0"+(d.getMonth()+1).toString())+"-"+(d.getDate().toString().length==2?d.getDate().toString():"0"+d.getDate().toString())+" "+(d.getHours().toString().length==2?d.getHours().toString():"0"+d.getHours().toString())+":"+((parseInt(d.getMinutes()/5)*5).toString().length==2?(parseInt(d.getMinutes()/5)*5).toString():"0"+(parseInt(d.getMinutes()/5)*5).toString())+":00";
						html += '<td>'+ date_format_str +'</td>';
						html += '<td>';
						
						html += "<button type='button' class='btn btn-sm btn-blue' data='${i.userId}' onclick='fn_updateUser($(this));return false;'>"
						html += "<i class='icon-file-write-edit'></i>수정</button>"
						html += "<button type='button' class='btn btn-sm btn-red' data='${i.userId}' onclick='fn_deleteUser($(this));return false;'>"
						html += "<i class='icon-file-remove'></i>삭제</button></td>";	
						html+='</ tr>';
						$("#adminLogList").append(html);
			
					}
				}else{
					var html ='';
					html ='<tr><td colspan="6">이력이 존재하지 않습니다.</td></tr>';
					$("#adminLogList").append(html);
				}
				
				$("#btn_download").append('<button type="button" onClick="fn_downloadLog(\''+adminId+'\',\''+dateStart+'\',\''+dateEnd+'\')" class="btn btn-vestap btn-blue w-auto pl-2 pr-2"><i class="icon-download-disk"></i>데이터 다운로드</button>');
				
				//fn_logChart(logList, dateTotal);
				
				$("#adminLogListPage").val(1);
				$("#adminLogListPageLimit").val(page);
				$("#adminLogListCurPage").text("1");
				$("#adminLogListLimitPage").text(page);
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

		location.href = "/admin/admin/accesslog/downloadLogList.do?" + params;

	}
	
	function fn_viewRequest(tag){
		var data = tag.attr("data");
		var params  = "adminId="+ data;
		location.href = "/admin/admin/requestlog/list.do?" + params;
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
	
	function fn_deleteUser(tag){
		var data = tag.attr("data");
		var url = '/admin/admin/management/deleteAdmin.do';
		var dataSet = {
			"data" : data
		};
		
		var agent = navigator.userAgent.toLowerCase();

		if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
										
			if(confirm("[확인] 정말 삭제하시겠습니까?")) {
				$.ajax({
					url:url
					,data:dataSet
					,type:'get'
					,dataType:'json'
					,success:function(result){
						var chk = result.chk;
						if(chk>0){
							
							alert("[승인] 정상적으로 삭제 되었습니다.");
							location.reload();
							
						}else{
							
							alert("[경고] 삭제 실패");
							location.reload();
							
						}
					}
					,error:function(error){
						alert("[경고] 삭제 오류");
						location.reload();
					}
				});
			}
			
		} else {
			
			swal({
				  title: '정말 삭제하시겠습니까?',
				  text: "삭제된 사용자 정보는 되돌릴 수 없습니다.",
				  type: 'warning',
				  showCancelButton: true,
				  confirmButtonColor: '#3085d6',
				  cancelButtonColor: '#d33',
				  confirmButtonText: '삭제',
				  cancelButtonText: '취소'
				}).then(function(result){
				  if (result.value) {
					  $.ajax({
							url:url
							,data:dataSet
							,type:'get'
							,dataType:'json'
							,success:function(result){
								var chk = result.chk;
								if(chk>0){
									swal(
								      '삭제 완료',
								      '정상적으로 삭제 되었습니다.',
								      'success'
								    ).then(function(){ location.reload();});
								}else{
									swal(
								      '삭제 실패',
								      '잠시후 다시 시도바랍니다.',
								      'error'
									).then(function(){ location.reload();});
								}
							}
							,error:function(error){
								swal(
							      '삭제 오류',
							      '잠시후 다시 시도바랍니다.',
							      'error'
							    ).then(function(){ location.reload();});
							}
						})
				  }
				});
		}
	}

</script>
</body>
</html>