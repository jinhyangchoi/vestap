<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>VESTAP - DB정보 - 평가항목 정보</title>

<link href="/resources/css/loading/loading.css" rel="stylesheet">
<link href="/resources/billboard/billboard.css" rel="stylesheet">
<link href="/resources/billboard/insight.css" rel="stylesheet">
</head>
<body>
<div class="display" style="display: none;">
<div class="loading">
<div class="lds-css ng-scope">
<div class="lds-spinner" style="100%;height:100%"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
</div>
</div>
</div>

<form id="listFrm" action="/member/base/dbinfo/item/list.do">

<input type="hidden" name="page" value="1">
<input type="hidden" name="keyword" value="${keyword }">
<input type="hidden" name="field" value="${field }">
<input type="hidden" name="item_id" value="${item_id }">

<table class="offcanvas-table">
	<tr>
		<td class="offcanvas-left-open">
			<!-- *********************** offcanvas-left-open *********************** -->
			
			<div class="card">
				<div class="card-header offcanvas-left-open-title">
					<i class="icon-file-bookmark"></i>평가항목 정보<a href="javascript:void(0);" class="on-offmenu text-blue"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
				</div>
				
				<ul class="list-group list-group-flush">
					<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i>평가항목 검색</li>
					<li class="list-group-item">
						
						<!-- //row -->
						<div class="row mb-4">
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
						</div>
						
						<div class="row mb-4">
							
							<div class="col">
								<div class="mb-1">평가항목 이름</div>
								<input type="text" class="form-control" id="search-keyword" placeholder="항목명을 입력하세요(2~20자)" maxlength="20">
							</div>
							
						</div>
						
						<!-- //row -->
						<div class="row">
							<div class="col pr-2">
								
								<button class="btn btn-vestap btn-blue w-100" type="button" id="item-search">
									<i class="icon-search"></i>검색하기
								</button>
								
							</div>
							<div class="col pl-2">
								
								<button class="btn btn-vestap btn-blue w-100" type="button" id="item-init">
									<i class="icon-refresh-modify"></i>초기화
								</button>
								
							</div>
						</div>
						<!-- //row -->
						
						<c:if test="${fn:length(keywordList) ne 0 }">
							<div class="row">
								<div class="col mt-3">
									<div class="mb-1">검색어</div>
									<div id="keyword-area"></div>
								</div>
							</div>
						</c:if>
						
					</li>
				</ul>
			</div>
			<!-- //card -->
			
		</td>
		<td class="col align-top p-0">
			
			<div class="onmenu-div d-none">
				<a href="javascript:void(0);" class="text-blue on-offmenu"><i class="icon-arrow-caret-right"></i></a>
			</div>
			
			<div class="mainContents">
				<!-- mainContents -->
				
				<div class="navbar p-0 pb-3">
					
					<div class="contentsTitle">
						<i class="icon-file-roll"> </i>평가 항목 <span class="normal-text ml-2">보다 신뢰도 높은 VESTAP을 위해 DB 메타정보를 제공합니다.</span>
					</div>
					
				</div>
				<!-- //navbar -->
				
				<table class="table table-hover vestapTable mainTable">
					<thead>
						<tr>
							<th>번호</th>
							<th>항목명</th>
							<th>리스크/그룹</th>
						</tr>
					</thead>
					<tbody>
						
					<c:forEach items="${itemList }" var="list">
						
						<tr class="offcanvas-select item-list" id="item-${list.item_id }" onclick="fn_itemView('${list.item_id }', '${list.item_nm}', ${list.ce_weight }, ${list.cs_weight }, ${list.aa_weight });">
							<td><c:out value="${list.rnum }"/></td>
							<td class="search-nm"><c:out value="${list.item_nm }"/></td>
							<td><c:out value="${list.field_nm }"/></td>
						</tr>
						
					</c:forEach>
					<c:if test="${fn:length(itemList) eq 0 }">
						
						<tr>
							<td colspan="3">항목이 없습니다.</td>
						</tr>
						
					</c:if>
						
					</tbody>
				</table>
				
				<nav class="form-inline justify-content-center">
					<c:if test="${not empty pageInfo }">
						<ui:pagination paginationInfo="${pageInfo }" type="text" jsFunction="fn_page"/>
					</c:if>
				</nav>
				<!-- //pagination -->
				
			</div>
			<!-- //mainContents -->
			
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
									aria-controls="nav1" aria-selected="true">기본정보</a> <a
									class="nav-item nav-link " id="nav-tab2" data-toggle="tab"
									href="#nav2" role="tab" aria-controls="nav-2"
									aria-selected="false">지표정보</a>
							</div>
						</nav>
						
						<div class="tab-content" id="nav-tabContent">
							<div class="tab-pane fade show active p-3" id="nav1" role="tabpanel" aria-labelledby="nav-tab1">
								
								<h6>산출식</h6>
								<div class="boxArea p-3 mb-3" style="height: 140px">
									<p><span class="indicator-title"></span> =</p>
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
								
								<table class="table table-hover vestapTable smTable text-center vestap-link-table" id="indicatorInfo-table">
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

<script>
	

$(document).ready(function() {
	
	var keywordArr = new Array();
	
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
		$("#select-field option[value=" + "${field}" + "]").prop("selected", true);
	});
	
	<c:if test="${activeOffcanvas ne null}">
		$("input:hidden[name=item_id]").val("${activeOffcanvas}");
		fn_itemInfo("${activeOffcanvas}");
	</c:if>
});

</script>


<!-- Chart lib -->
<script src="/resources/billboard/d3.v5.min.js"></script>

<script src="/resources/billboard/billboard.js" ></script>

<!-- custom vulnerability -->
<script src="/resources/js/developer/dbinfo-item.js"></script>

</body>
</html>