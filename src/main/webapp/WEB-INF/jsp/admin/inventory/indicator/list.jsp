<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>VESTAP ADMIN - 지자체별 인벤토리 - 평가지표 데이터</title>
</head>
<body>

<form id="listFrm" action="/admin/inventory/indicator/list.do">

<input type="hidden" name="page" value="1">
<input type="hidden" name="keyword" value="${keyword }">
<input type="hidden" name="field_id" value="${field_id }">
<input type="hidden" name="ipcc_1" value="${ipcc_1 }">
<input type="hidden" name="ipcc_2" value="${ipcc_2 }">
<input type="hidden" name="item_id" value="${item_id }">
<input type="hidden" name="indi_group" value="${indi_group }">
<input type="hidden" name="indi_id" value="${indi_id }">
<input type="hidden" name="sido" value="${sido }">
<input type="hidden" name="sigungu" value="${sigungu }">
<input type="hidden" name="district_info" value="${district_info }">
<input type="hidden" name="startdate" value="${startdate }">
<input type="hidden" name="enddate" value="${enddate }">

	<table class="offcanvas-table">
		<tr>
			<td class="offcanvas-left-open">
				<!-- *********************** offcanvas-left-open *********************** -->

				<div class="card">
					<div class="card-header offcanvas-left-open-title">
						<i class="icon-file-bookmark"></i>평가지표 데이터<a href="javascript:void(0);" class="on-offmenu text-blue"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
					</div>

					<ul class="list-group list-group-flush">
						<li class="list-group-item sub-title border-top-0"><i
							class="icon-file-align"></i>평가지표 검색</li>
						<li class="list-group-item">

							<div class="row mb-4">

								<div class="col">
									<div class="mb-1">평가지표 이름</div>
									<input type="text" class="form-control" id="search-keyword" placeholder="지표명을 입력하세요(2자 이상)" maxLength="20">
								</div>

							</div> <!-- //row -->
							<div class="row ">

								<div class="col"><div class="mb-1">평가 리스크/그룹</div>
							
								<div class=" select_box">
									<select class="form-control" id="select-field">
										<option value="all" selected>전체</option>
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
										<c:forEach var="i" items="${sidoList }">
											<option value='${i.districtCd}' <c:if test="${setSido eq i.districtCd}">selected</c:if> >${i.districtNm}</option>
										</c:forEach>
										</select>
									</div>

								</div>
								<!-- //col -->
								<div class="col-6 pl-1">
									<div class=" select_box">
										<select class="form-control" id="sigunguList">
										<c:if test="${not empty sigunguList }">
											<option value=''>전체</option>
										<c:forEach var="i" items="${sigunguList }">
											<option value='${i.districtCd}' <c:if test="${setSigungu eq i.districtCd}">selected</c:if> >${i.districtNm}</option>
										</c:forEach>
										</c:if>
										<c:if test="${empty sigunguList }">
											<option value=''>전체</option>
										</c:if>
									</select>
									</div>
								</div>
								<!-- //col -->
							</div> <!-- //row -->
							
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
							
							<div class="row">
								<div class="col pr-2">

								<button class="btn btn-vestap btn-blue w-100" type="button" id="indicator-search"><i class="icon-search"></i>검색하기</button>

								</div>
								<div class="col pl-2">

								<button class="btn btn-vestap btn-blue w-100" type="button" id="indicator-init"><i class="icon-refresh-modify"></i>초기화</button>
								
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
							<i class="icon-file-roll"> </i>평가지표 데이터 <span
								class="normal-text ml-2">사용자들이 업로드/관리하는 지표를 관리합니다.</span>
						</div>

					</div>
					<!-- //navbar -->


					<!-- 평가지표 데이터 지역별 통계 -->
					<input type="hidden" id="eventAreaStatisticsListParentPage" value="1"> 
					<input type="hidden" id="eventAreaStatisticsListParentLimit" value="1">
					<ul class="list-group list-group-flush" style="margin-top: 10px;">
						<li class="list-group-item sub-title border-top-0" id="areaEvenListBack"><i class="icon-file-align" id="areaEventList"></i>지자체별 통계
						</li>
						<li class="list-group-item p-4" id="eventAreaStatisticsListParent" style="min-height: 150px">
						</li>
						<li class="list-group-item p-4" id="eventAreaMoveList" style="display:flex;">
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
							<th>지표 이름</th>
							<th>등록(수정)날짜</th>
							<th>지자체</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${fn:length(indicatorList) eq 0 }">
						<tr>
							<td colspan="5">지표 목록이 없습니다.</td>
						</tr>
						</c:if>
						<c:forEach items="${indicatorList }" var="list">
						<tr class="offcanvas-select indicator-list" id="indi-${list.indi_id }" onclick="fn_indicatorInfo('${list.indi_id}','${list.user_dist }', 1);">
							<td><c:out value="${list.rnum }"/></td>
							<td class="search-nm"><c:out value="${list.indi_nm }"/></td>
							<td><c:out value="${list.item_regdate2 }"/></td>
							<td><c:out value="${list.district_nm }"/></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<nav class="form-inline mt-4 justify-content-center">
					<c:if test="${not empty pageInfo }">
						<ui:pagination paginationInfo="${pageInfo }" type="text" jsFunction="fn_page"/>
					</c:if>
				</nav>
					

				</div> <!-- //mainContents -->

			</td>
			<td class="offcanvas-right-open" id="offcanvas-view">
			<!-- *********************** offcanvas-right-open *********************** -->
			
			<div class="card">
				<div class="card-header offcanvas-right-open-title">
					<i class="icon-file-bookmark"></i><span id="info-indi-nm"></span>
					<button type="button" class="offcanvasCloseBtn close">
						<i class="icon-close-bold"></i>
					</button>
				</div>
				<ul class="list-group list-group-flush">
					<li class="list-group-item p-0 pt-2">
						<nav>
							<div class="nav nav-tabs nav-justified" id="nav-tab"
								role="tablist">
								<a class="nav-item nav-link active" id="nav-tab1"
									data-toggle="tab" href="#nav1" role="tab"
									aria-controls="nav1" aria-selected="true">지표정보</a> 
								<a class="nav-item nav-link " id="nav-tab2" data-toggle="tab"
									href="#nav2" role="tab" aria-controls="nav-2"
									aria-selected="false">관련정보</a>
							</div>
						</nav>
						<div class="tab-content" id="nav-tabContent">
							
							<div class="tab-pane fade show active p-3" id="nav1" role="tabpanel" aria-labelledby="nav-tab1">
								
								<table class="table table-bottom-line">
									<colgroup>
										<col style="width: 140px;">
										<col style="width: *;">
									</colgroup>
									<tr>
										<th>IPCC WG I</th>
										<td id="info-indi-ipcc1"></td>
									</tr>
									<tr>
										<th>IPCC WG II</th>
										<td id="info-indi-ipcc2"></td>
									</tr>
									<tr>
										<th>지표단위</th>
										<td id="info-indi-unit"></td>
									</tr>
									<tr>
										<th>지표자료 설명</th>
										<td id="info-indi-acct"></td>
									</tr>
									<tr>
										<th>지표 구축 /가공 방법</th>
										<td id="info-indi-cont"></td>
									</tr>
								</table>
								
								<table class="table vestapTable text-center" id="indicator-data-table">
									<thead>
										<tr>
											<th>순위</th>
											<th>행정구역 명칭</th>
											<th>값</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								
								<div class="nav navbar p-0 mt-4">
									<nav class="form-inline" id="indicator-data-page"></nav><!-- //pagination -->
									<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2" onclick="downloadIndicator();"> <i class="icon-download-disk"></i>엑셀파일 다운로드</button>
								</div><!-- //nav -->
								
							</div>
							<div class="tab-pane fade p-3" id="nav2" role="tabpanel" aria-labelledby="nav-tab2">
								<!-- 
								<span class="h6 mb-3">관련 원시 자료</span>
								
								<table class="table vestapTable text-center mt-3 mb-3" id="meta-list-table">
									<thead>
										<tr>
											<th>원시자료 명</th>
											<th>제공 기관</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								 -->
								<span class="h6 mb-3">취약성 평가 활용 항목</span>
								
								<table class="table table-hover vestapTable text-center mt-3" id="item-list-table">
									<thead>
										<tr>
											<th>리스크 명</th>
											<th>항목 명</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								
								<div id="item-list-page" class="mt-2 mb-3 justify-content-center form-inline"></div>
								
							</div>
						</div>
						<!-- //tab-content -->
					</li>
				</ul>
			</div>
			
		</td>
		</tr>
	</table>

</form>

<script>

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
		
		if("${field_id}" != "all") {
			fn_getItem("${field_id}", "${item_id}");
		}
		
		if("${ipcc_1}" != "all") {
			fn_getIpcc("${ipcc_1}", "${ipcc_2}");
		}
		
		$("#select-field option[value=" + "${field_id}" + "]").prop("selected", true);
		$("#select-group option[value=" + "${indi_group}" + "]").prop("selected", true);
		$("#ipcc-1 option[value=" + "${ipcc_1}" + "]").prop("selected", true);
		$("#ipcc-2 option[value=" + "${ipcc_2}" + "]").prop("selected", true);
		
		<c:if test="${activeOffcanvas ne null}">
			$("input:hidden[name=indi_id]").val("${activeOffcanvas}");
			fn_indicatorInfo("${activeOffcanvas}", 1);
			offcanvasOpen();
		</c:if>
		
		$("#startdate").val("${startdate}");
		$("#enddate").val("${enddate}");
		
		var field = "${field_id}";
		var startdate = "${startdate}";
		var enddate = "${enddate}";
		var keyword = "${keyword}";
		
		initeventProcess(keyword,field, startdate, enddate);
		
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
        
        $("#btn_download").append('<button type="button" onClick="fn_downloadInvenIndi(\''+"${keyword}"+'\',\''+"${field_id}"+'\',\''+"${sido}"+'\',\''+"${sigungu}"+'\',\''+"${startdate}"+'\',\''+"${enddate}"+'\',)" class="btn btn-vestap btn-blue w-auto pl-2 pr-2"><i class="icon-download-disk"></i>데이터 다운로드</button>');
        
	});
	
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
	
	function fn_downloadInvenIndi(keyword, field, district_cd1, district_cd2, startdate, enddate) {
		var sido = $("#sidoList option:selected").text();
		var sido_cd = $("#sidoList option:selected").val();
				
		if(sido == '전국'){
			district_cd1 = 0;	
			
		}else{
			district_cd1 = sido_cd;
		}
		
		var params = "keyword="+ keyword + "&field=" + field + "&sido=" + district_cd1 + "&sigungu=" + district_cd2
		+ "&startdate=" + startdate + "&enddate=" + enddate;
		
		location.href = encodeURI("/admin/inventory/indicator/downloadInventoryindicator.do?" + params);
	}
	
</script>

<!-- custom vulnerability -->
<script src="/resources/js/developer/custom-page.js"></script>

<script src="/resources/js/developer/inventory-indicator.js"></script>

</body>
</html>