<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<!-- 달력 사용하기 위한 환경 세팅(2019.10.02, 최진원) -->
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>VESTAP ADMIN - 지자체별 인벤토리 - 취약성평가 항목</title>

<link href="/resources/billboard/billboard.css" rel="stylesheet">
<link href="/resources/billboard/insight.css" rel="stylesheet">
</head>
<body>
<form id="listFrm" action="/admin/inventory/item/list.do">
<input type="hidden" name="page" value="1">
<input type="hidden" name="keyword" value="${keyword }">
<input type="hidden" name="field" value="${field }">
<input type="hidden" name="item_id" value="${item_id }">
<input type="hidden" name="sido" value="${sido }">
<input type="hidden" name="sigungu" value="${sigungu }">

<input type="hidden" name="startdate" value="${startdate }">
<input type="hidden" name="enddate" value="${enddate }">

	<table class="offcanvas-table">
		<tr>
			<td class="offcanvas-left-open">
				<!-- *********************** offcanvas-left-open *********************** -->

				<div class="card">
					<div class="card-header offcanvas-left-open-title">
						<i class="icon-file-bookmark"></i>취약성평가 항목<a href="javascript:void(0);" class="on-offmenu text-blue"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
					</div>

					<ul class="list-group list-group-flush">
						<li class="list-group-item sub-title border-top-0"><i
							class="icon-file-align"></i>평가항목 검색</li>
						<li class="list-group-item">

							<div class="row mb-4">

							<div class="col">
								<div class="mb-1">평가항목 이름</div>
								<input type="text" class="form-control" id="search-keyword" placeholder="항목명을 입력하세요(2자 이상)">
							</div>
							

							</div> <!-- //row -->
							<div class="row ">

								<div class="col">
									<div class="mb-1">평가 리스크/그룹</div>
									<div class=" select_box">
										<select class="form-control" id="select-field">
											<option value="all" selected>전체선택</option>
											<c:forEach items="${fieldList }" var="list">
												<option value="${list.code_id }"><c:out value="${list.code_nm }"/></option>
											</c:forEach>
										</select>
									</div>

								</div>

							</div> <!-- //row -->
						</li>
						<li class="list-group-item sub-title border-top-0"><i
							class="icon-file-align"></i>지자체 선택</li>
						<li class="list-group-item">

							<div class="row mb-4">
								<div class="col-6 pr-1">

									<div class=" select_box">
										<select class="form-control" id="sidoList">
											<option value='' selected>전국</option>
										<c:forEach items="${sidoList}" var="i">
											<option value='${i.districtCd}' <c:if test="${setSido eq i.districtCd}">selected</c:if> >${i.districtNm}</option>
										</c:forEach>
										</select>
									</div>

								</div>
								<!-- //col -->
								<div class="col-6 pl-1">
									<div class=" select_box">
										<select class="form-control" id="sigunguList">
										<c:if test="${not empty sigunguList}">
											<option value=''>전체</option>
										<c:forEach items="${sigunguList}" var="i">
											<option value='${i.districtCd}' <c:if test="${setSigungu eq i.districtCd}">selected</c:if> >${i.districtNm}</option>
										</c:forEach>
										</c:if>
										<c:if test="${empty sigunguList}">
											<option value=''>전체</option>
										</c:if>
									</select>
									</div>
								</div>
								<!-- //col -->
							</div> <!-- //row -->
							
							
							<!-- 검색 조건에 날짜 추가(2019.10.02, 최진원) -->
							<li class="list-group-item sub-title border-top-0">
								<i class="icon-file-align"></i>일자 선택</li>
							<li class="list-group-item">
							
							<div class="row mb-4">
								<div class="col-6">
									<input type="text" class="form-control" id="startdate" placeholder="시작일" autocomplete="off">  
								</div><!-- //col -->
								<div class="col-6">
									<input type="text" class="form-control" id="enddate" placeholder="종료일" autocomplete="off">
								</div><!-- //col -->
								
							</div> <!-- //row -->
							<!-- -------------------------------------------------------------------------- -->
							
							<div class="row">
								<div class="col pr-2">

									<button class="btn btn-vestap btn-blue w-100" type="button"
										id="item-search">
										<i class="icon-search"></i>검색하기
									</button>

								</div>
								<div class="col pl-2">

									<button class="btn btn-vestap btn-blue w-100" type="button"
										id="item-init">
										<i class="icon-refresh-modify"></i>초기화
									</button>

								</div>
							</div> <!-- //row -->
							<c:if test="${fn:length(keywordList) ne 0 }">
							<div class="row">
								<div class="col mt-3"
									><div class="mb-1">검색어</div>
									<div id="keyword-area"></div>
								</div>
							</div>
						</c:if>
						</li>
					</ul>
				</div> <!-- //card -->

			</td>
			<td class="col align-top p-0">
				
				<div class="onmenu-div d-none">
					<a href="javascript:void(0);" class="text-blue on-offmenu"><i class="icon-arrow-caret-right"></i></a>
				</div>
				
				<div class="mainContents">
					<!-- mainContents -->
					
					<div class="navbar p-0 pb-3">

						<div class="contentsTitle">
							<i class="icon-file-roll"> </i>취약성평가 항목 <span
								class="normal-text ml-2">사용자들이 업로드/관리하는 취약성평가 항목을 관리합니다.</span>
						</div>

					</div>
					<!-- //navbar -->
					
					<!-- 지역별 통계 UI표출 부분(2019.10.03~10.11, 최진원) -->
					<input type="hidden" id="eventAreaStatisticsListParentPage" value="1"> 
					<input type="hidden" id="eventAreaStatisticsListParentLimit" value="1">
					<ul class="list-group list-group-flush" style="margin-top: 10px;">
						<li class="list-group-item sub-title border-top-0" id="areaEventListBack"><i class="icon-file-align" id="areaEventList"></i>지자체별 통계</li>
						<li class="list-group-item p-4" id="eventAreaStatisticsListParent" style="min-height: 150px">
						</li>
						<li class="list-group-item p-4" id="eventAreaMoveList"  style="display:flex;">
							<nav class="form-inline justify-content-center" style="width:90%;">
								<ul class="pagination previous disabled">
									<li class="page-item"><a class="page-link" href="#" onclick="fn_previousPage('eventAreaStatisticsListParent'); return false;"><i class="icon-arrow-caret-left"></i></a></li>
								</ul>
								<div>
									<span id="eventAreaStatisticsListParentCurPage">1</span>/<span id="eventAreaStatisticsListParentLimitPage">1</span>
								</div>
								<ul class="pagination next">
									<li class="page-item"><a class="page-link" href="#" onclick="fn_nextPage('eventAreaStatisticsListParent'); return false;"><i class="icon-arrow-caret-right"></i></a></li>
								</ul>							
							</nav>
							<div id="btn_download" style="display:flex;">
								<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2"><i class="icon-download-disk"></i>데이터 다운로드</button>
							</div>
						</li>
					</ul>

					<br>

					<table class="table table-hover vestapTable mainTable">
					<thead>
						<tr>
							<th>번호</th>
							<th>항목명</th>
							<th>리스크/그룹</th>
							<th>등록(수정)날짜</th>
							<th>지자체</th>
						</tr>
					</thead>
					<tbody>
						
					<c:forEach items="${itemList }" var="list">
						
						<tr class="offcanvas-select item-list" id="item-${list.item_id }" onclick="fn_itemView('${list.item_id }', '${list.item_nm}', ${list.ce_weight }, ${list.cs_weight }, ${list.aa_weight });">
							<td><c:out value="${list.rnum }"/></td>
							<td class="search-nm"><c:out value="${list.item_nm }"/></td>
							<td><c:out value="${list.field_nm }"/></td>
							<td><c:out value="${list.item_regdate2 }"/></td>
							<td><c:out value="${list.district_nm }" /></td>
						</tr>
						
					</c:forEach>
					<c:if test="${fn:length(itemList) eq 0 }">
						
						<tr>
							<td colspan="5">항목이 없습니다.</td>
						</tr>
						
					</c:if>
						
					</tbody>
				</table>

					<!-- pagination 왼쪽버튼 있을 경우 사용 -->
						<nav class="form-inline justify-content-center">
							<c:if test="${not empty pageInfo }">
								<ui:pagination paginationInfo="${pageInfo }" type="text" jsFunction="fn_page"/>
							</c:if>
						</nav>
						<!-- //pagination -->


				</div> <!-- //mainContents -->

			</td>
			<td class="offcanvas-right-open" id="offcanvas-view">
			<!-- *********************** offcanvas-right-open *********************** -->
			
			<div class="card">
				<div class="card-header offcanvas-right-open-title">
					<i class="icon-file-bookmark"></i>평가항목 상세 정보
					<button type="button" class="offcanvasCloseBtn close">
						<i class="icon-close-bold"></i>
					</button>
				</div>
				<ul class="list-group list-group-flush">
					<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i><span class="indicator-title">기타 대기오염물질에 의한 건강 취약성</span></li>
					<li class="list-group-item p-0 pt-2">
						
						<nav>
							<div class="nav nav-tabs nav-justified" id="nav-tab"
								role="tablist">
								<a class="nav-item nav-link active" id="nav-tab1"
									data-toggle="tab" href="#nav1" role="tab"
									aria-controls="nav1" aria-selected="true">기본정보</a> 
								<a class="nav-item nav-link " id="nav-tab2" data-toggle="tab"
									href="#nav2" role="tab" aria-controls="nav-2"
									aria-selected="false">지표정보</a>
							</div>
						</nav>
						
						<div class="tab-content" id="nav-tabContent">
							<div class="tab-pane fade show active p-3" id="nav1" role="tabpanel" aria-labelledby="nav-tab1">
							
								<div class="boxArea text-center p-3 mb-3" style="height: 330px">
									<div id="RadarChart"></div>
								</div>
								<!-- //boxArea -->
								
								<h6>산출식</h6>
								<div class="boxArea p-3 mb-3" style="height: 140px">
									<p><span class="indicator-title">기타 대기오염물질에 의한 건강 취약성 </span>=</p>
									<p style="text-align: center;">
										(기후노출 지수 X <span id="ce_wei"></span>) + (민감도 지수 X <span id="cs_wei"></span>) - (적응능력 지수 X <span id="aa_wei"></span>)<br>───────────────────────────────<br>
										1
									</p>
									<p></p>
								</div>
							</div>
							<div class="tab-pane fade p-3" id="nav2" role="tabpanel" aria-labelledby="nav-tab2">
								
								
								
								<div class="select_box float-right mb-3">
									<select class="form-control" id="select-sector">
										<option value="SEC01" selected>기후노출</option>
										<option value="SEC02">민감도</option>
										<option value="SEC03">적응능력</option>
									</select>
								</div>
								
								<table class="table vestapTable smTable text-center" id="indicatorInfo-table">
									<thead>
										<tr>
											<th>지표명</th>
											<th>구축형태</th>
											<th>가중치</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								
								<span class="h6 mb-3">구축형태</span>
								<table class="table vestapTable smTable text-center mt-3">
									<tbody>
										<tr>
											<td><b style="font-size: large;">A</b><br>읍면동 통계<br>원시 자료</td>
											<td><b style="font-size: large;">B</b><br>시군구 통계<br>원시 자료</td>
											<td><b style="font-size: large;">C</b><br>시도 통계<br>원시 자료</td>
											<td><b style="font-size: large;">D</b><br>복합/기타<br>자료</td>
										</tr>
										<tr>
											<td><b style="font-size: large;">A`</b><br>읍면동 통계<br>가공 자료</td>
											<td><b style="font-size: large;">B`</b><br>시군구 통계<br>가공 자료</td>
											<td><b style="font-size: large;">C`</b><br>시도 통계<br>가공 자료</td>
											<td><b style="font-size: large;">E</b><br>기상/기후<br>원시 자료</td>
										</tr>
									</tbody>
								</table>
								
								<!-- pagination 왼쪽버튼 있을 경우 사용 -->
								<div class="nav navbar p-0 mt-4">
									<nav class="form-inline"></nav>
									<!-- //pagination -->
									
									<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2" onclick="downloadIndicatorInfo();"> <i class="icon-download-disk"></i>엑셀파일 다운로드</button>
								</div>
								<!-- //nav -->
							</div>
						</div>
						<!-- //tab-content -->
					</li>
				</ul>
			</div>
			<!-- //card -->
		</td>
		</tr>
	</table>
</form>


<script >
	

//11.23~
$(document).ready(function() {
	$("#sidoList").change(function() {
		fn_sigunguList();
	});
	
	var keyword = "${keyword}";
	var field = "${field}";
	var sido = "${sido}";
	var sigungu = "${sigungu}";
	var startdate = "${startdate}";
	var enddate = "${enddate}";
	
	initeventProcess(keyword, field, startdate, enddate);
	
	//달력 추가(시작일~종료일)설정(2019.10.02, 최진원)
	$.datepicker.setDefaults($.datepicker.regional['ko']); 
            $( "#startdate" ).datepicker({
                 changeMonth: true, 
                 changeYear: true,
                 nextText: '다음 달',
                 prevText: '이전 달', 
                 dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
                 dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
                 monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                 monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                 dateFormat: "yy-mm-dd",
                 maxDate: 0,                       // 선택할수있는 최소날짜, ( 0 : 오늘 이후 날짜 선택 불가)
                 onClose: function( selectedDate ) {    
                      //시작일(startDate) datepicker가 닫힐때
                      //종료일(endDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
                     $("#endDate").datepicker( "option", "minDate", selectedDate );
                 }    
 
            });
            $( "#enddate" ).datepicker({
                 changeMonth: true, 
                 changeYear: true,
                 nextText: '다음 달',
                 prevText: '이전 달', 
                 dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
                 dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
                 monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                 monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                 dateFormat: "yy-mm-dd",
                 maxDate: 0,                       // 선택할수있는 최대날짜, ( 0 : 오늘 이후 날짜 선택 불가)
                 onClose: function( selectedDate ) {    
                     // 종료일(endDate) datepicker가 닫힐때
                     // 시작일(startDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 시작일로 지정
                     $("#startDate").datepicker( "option", "maxDate", selectedDate );
                 }    
 
            });        
                        	
}); 
	
	//지역별 통계 리스트 -이전 페이지 이동(2019.10.20, 최진원)
	function fn_previousPage(id){
		//console.log("이전 체크");
		var page = $("#"+id+"Page").val()*1;
		if(page>1){
		$("#"+id+" .pg"+page).css("display","none");
		page--;
		$("#"+id+" .pg"+page).css("display","block");
		}
		$("#"+id+"Page").val(page);
		$("#"+id+"CurPage").text('');
		$("#"+id+"CurPage").text(page);
	};
	
	//지역별 통계 리스트 -다음 페이지 이동(2019.10.20, 최진원)
	function fn_nextPage(id){
		//console.log("다음 체크");
		var page = $("#"+id+"Page").val()*1;
		var limitPage = $("#"+id+"Limit").val()*1;
		if(page<limitPage){
		$("#"+id+" .pg"+page).css("display","none");
		page++;
		$("#"+id+" .pg"+page).css("display","block");
		}
		$("#"+id+"Page").val(page);
		$("#"+id+"CurPage").text('');
		$("#"+id+"CurPage").text(page);
	};
	
	
	
	//시군구->시도 리스트로 전환(되돌아가기 이벤트 호출)(2019.10.29, 최진원)
	function backArea(idx) {
		SidoList(idx)
	};

	function fn_downloadInventoryItem(keyword,field,district_cd1, district_cd2, startdate, enddate) {	//name : 지표명
		var sido = $("#sidoList option:selected").text();
		var sido_cd = $("#sidoList option:selected").val();
		
		if(sido =='전국') {
			district_cd1 = 0;
		}else {
			district_cd1 = sido_cd;
		}
		var params = "keyword="+ keyword + "&field=" + field + "&sido=" + district_cd1 + "&sigungu=" + district_cd2
					+ "&startdate=" + startdate + "&enddate=" + enddate;
	
		console.log("/admin/inventory/item/downloadInventoryItem.do?" + params);
		
		location.href = encodeURI("/admin/inventory/item/downloadInventoryItem.do?" + params);
		
	}


function fn_sigunguList(){
	var sidoCode = $("#sidoList option:selected").val();
	var dataSet = {"sidoCode" : sidoCode};
	if(sidoCode!=null && sidoCode!=''){
	$.ajax({
		url:'/admin/inventory/item/sigunguList.do',
		dataType:'json',
		type:'get',
		data:dataSet,
		success:function(result){
			$("#sigunguList").empty();
			var list = result.list;
			$("#sigunguList").append("<option value='' selected>전체</option>");
			for(var i in list){
				var html = "<option value='"+list[i].districtCd+"'>"+list[i].districtNm+"</option>";
				$("#sigunguList").append(html);
			}
		}
	});
	}else{
		$("#sigunguList").empty();
	}
}

 
$(document).ready(function() {
	
	
	var keywordArr = new Array();
	$("#btn_download").empty();
	
	<c:forEach items="${keywordList}" var="list" varStatus="status">
		
		keywordArr.push("${list}");
		
		var keyword = "${list}";
		
		var $con = $('<div class="mb-1" id="keyword-${status.count}"></div>');
		var $div = $('<div></div>');
		$div.append(keyword);
		$con.append($div);
		
		$div = $('<div></div>');
		var $a = $('<a href="javascript:fn_removeKeyword(\'' + keyword + '\', \'keyword-${status.count}\');" style="color: #087B98;"><i class="icon-close-bold"></i></a>');
		$div.append($a);
		$con.append($div);
		
		$("#keyword-area").append($con);
		
	</c:forEach>
	
	$(".mainTable > tbody > tr > td.search-nm").each(function() {
		
		var areaTxt = $(this).text();
		var areaTxt_lower = areaTxt.toLowerCase();
		var replaceArr = new Array();
		
		for(var i = 0; i < keywordArr.length; i++) {
			
			var keyword = keywordArr[i];
			var keyword_lower = keyword.toLowerCase();
			
			if(areaTxt_lower.indexOf(keyword_lower) > -1) {
				
				var sIndex = areaTxt_lower.indexOf(keyword_lower);
				var eIndex = sIndex + keyword_lower.length;
				
				replaceArr.push(areaTxt.substring(sIndex, eIndex));
			}
		}
		
		var resultTxt = areaTxt;
		
		if(replaceArr.length > 0) {
			
			for(var i = 0; i < replaceArr.length; i++) {
				
				var str = replaceArr[i]; 
				
				areaTxt = areaTxt.replace(str, '<b class="searchKeyword">' + str + '</b>');
			}
		}
		
		$(this).html(areaTxt);
	});
	
	
	<c:if test="${activeOffcanvas ne null}">
		$("input:hidden[name=item_id]").val("${activeOffcanvas}");
		fn_itemInfo("${activeOffcanvas}");
	</c:if>
	
	$("#select-field option[value=" + "${field}" + "]").prop("selected", true);
	
	//선택한 날짜 표시할 수 있도록 선언(2019.10.31, 최진원)
	$("#startdate").val("${startdate}");
	$("#enddate").val("${enddate}");
	
	//console.log("시작일 : " + $("#startdate").val());
	//console.log("종료일 : " + $("#enddate").val());
	
	//지자체 선택한 부분 표시할 수 있도록 선언(2019.11.01, 최진원)
	$("#sidoList option[value=" + "${setSido}" + "]").prop("selected", true);
	$("#sigunguList option[value=" + "${setSigungu}" + "]").prop("selected", true);
	
	//console.log("선택한 시도 : " + $("#sidoList").val());
	//console.log("선택한 시군구 : " + $("#sigunguList").val());
	
	$("#btn_download").append('<button type="button" onClick="fn_downloadInventoryItem(\''+"${keyword}"+'\',\''+"${field}"+'\',\''+"${sido}"+'\',\''+"${sigungu}"+'\',\''+"${startdate}"+'\',\''+"${enddate}"+'\',)" class="btn btn-vestap btn-blue w-auto pl-2 pr-2"><i class="icon-download-disk"></i>데이터 다운로드</button>');
});


</script>


	<!-- Chart lib -->
<script src="/resources/billboard/d3.v5.min.js"></script>

<script src="/resources/billboard/billboard.js" ></script>

<!-- custom vulnerability -->
<script src="/resources/js/developer/inventory-item.js"></script>

</body>
</html>