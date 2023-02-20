<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>VESTAP - DB정보 - 원시 자료 정보</title>
<link href="/resources/css/loading/loading.css" rel="stylesheet">
</head>
<body>
<div class="display" style="display: none;">
<div class="loading">
<div class="lds-css ng-scope">
<div class="lds-spinner" style="100%;height:100%"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
</div>
</div>
</div>
<form id="listFrm" action="/member/base/dbinfo/meta/list.do">

<input type="hidden" name="page" value="1">
<input type="hidden" name="keyword" value="${keyword }">
<input type="hidden" name="meta_offer" value="${meta_offer }">
<input type="hidden" name="meta_system" value="${meta_system }">

<table class="offcanvas-table">
	<tr>
		<td class="offcanvas-left-open"><!-- *********************** offcanvas-left-open *********************** -->
			
			<div class="card">
				
				<div class="card-header offcanvas-left-open-title">
					<i class="icon-file-bookmark"></i>원시 자료 조회<a href="javascript:void(0);" class="on-offmenu text-blue"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
				</div>
				
				<ul class="list-group list-group-flush">
				
					<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i>지표자료 검색</li>
					<li class="list-group-item">
						
						<div class="row mb-4">
							
							<div class="col"><div class="mb-1">제공 기관명</div>
							
								<div class=" select_box">
									<select class="form-control" id="select-offer">
										<option value="all">전체</option>
										<c:forEach items="${offerOrgList }" var="list">
											<option value="${list }"><c:out value="${list }"/></option>
										</c:forEach>
									</select>
								</div>
								
							</div>
							
						</div><!-- //row -->
						
						<div class="row mb-4">
							
							<div class="col"><div class="mb-1">제공 시스템명</div>
							
								<div class=" select_box">
									<select class="form-control" id="select-system">
										<option value="all">전체</option>
										<c:forEach items="${offerSystemList }" var="list">
											<option value="${list }"><c:out value="${list }"/></option>
										</c:forEach>
									</select>
								</div>
								
							</div>
							
						</div><!-- //row -->
						
						<div class="row mb-4">
							
							<div class="col"><div class="mb-1">원시자료 이름</div>
								<input type="text" class="form-control" id="search-keyword" placeholder="원시자료명을 입력하세요(2~20자)" maxlength="20">
							</div>
							
						</div><!-- //row -->
								
						<div class="row">
							<div class="col pr-2">
								
								<button class="btn btn-vestap btn-blue w-100" type="button" id="meta-search"><i class="icon-search"></i>검색하기</button>
								
							</div>
							<div class="col pl-2">
								
								<button class="btn btn-vestap btn-blue w-100" type="button" id="meta-init"><i class="icon-refresh-modify"></i>초기화</button>
								
							</div>
						</div><!-- //row -->
						
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
			</div><!-- //card -->
					
		</td>
		<td class="col align-top p-0">
			
			<div class="onmenu-div d-none">
				<a href="javascript:void(0);" class="text-blue on-offmenu"><i class="icon-arrow-caret-right"></i></a>
			</div>
			
			<div class="mainContents"><!-- mainContents -->
				
				<div class="navbar p-0 pb-3 mt-3">
					
					<div class="contentsTitle"><i class="icon-file-roll"></i>원시 자료
						<span class="normal-text ml-2">보다 신뢰도 높은 VESTAP을 위해 DB 메타정보를 제공합니다.</span>
					</div>
					
				</div><!-- //navbar -->
					
				<table class="table table-hover vestapTable mainTable">
					<thead>
						<tr>
							<th>번호</th>
							<th>원시 자료 명</th>
							<th>원시 자료 단위</th>
							<th>제공 기관</th>
							<th>제공 시스템</th>
							<th>기준 시점</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${fn:length(metaList) eq 0 }">
						<tr>
							<td colspan="6">원시 자료 목록이 없습니다.</td>
						</tr>
						</c:if>
						<c:forEach items="${metaList }" var="list">
						<tr class="offcanvas-select meta-list" id="meta-${list.meta_id }" onclick="fn_metaInfo('${list.meta_id}', 1);">
							<td><c:out value="${list.rnum }"/></td>
							<td class="search-nm"><c:out value="${list.meta_nm }"/></td>
							<td><c:out value="${list.meta_unit }"/></td>
							<td><c:out value="${list.meta_offer_org }"/></td>
							<td><c:out value="${list.meta_offer_system }"/></td>
							<td><c:out value="${list.meta_con_year }"/></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<nav class="form-inline mt-4 justify-content-center">
					<c:if test="${not empty pageInfo }">
						<ui:pagination paginationInfo="${pageInfo }" type="text" jsFunction="fn_page"/>
					</c:if>
				</nav><!-- //pagination -->
					
			</div><!-- //mainContents -->
			
		</td>
		<td class="offcanvas-right-open" id="offcanvas-view"><!-- *********************** offcanvas-right-open *********************** -->
			
			<div class="card">
				<div class="card-header offcanvas-right-open-title">
					<i class="icon-file-bookmark"></i>원시 자료 상세 정보
					<button type="button" class="offcanvasCloseBtn close">
						<i class="icon-close-bold"></i>
					</button>
				</div>
				<ul class="list-group list-group-flush">
					<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i><span id="info-meta-nm"></span></li>
					<li class="list-group-item p-0 pt-2">
						
						<nav>
							<div class="nav nav-tabs nav-justified" id="nav-tab"
								role="tablist">
								<a class="nav-item nav-link active" id="nav-tab1" data-toggle="tab" href="#nav1" role="tab" aria-controls="nav1" aria-selected="true">원시 자료 정보</a>
								<a class="nav-item nav-link " id="nav-tab2" data-toggle="tab" href="#nav2" role="tab" aria-controls="nav-2" aria-selected="false">관련 지표 정보</a>
							</div>
						</nav>
						
						<div class="tab-content" id="nav-tabContent">
							
							<div class="tab-pane fade show active p-3" id="nav1" role="tabpanel" aria-labelledby="nav-tab1">
								
								<table class="table table-bottom-line" id="meta-info-table">
									
									<colgroup>
										<col style="width: 140px;">
										<col style="width: *;">
									</colgroup>
									
									<tr>
										<th>원시자료명</th>
										<td id="meta-info-nm"></td>
									</tr>
									<tr>
										<th>제공기관명</th>
										<td id="meta-info-offer"></td>
									</tr>
									<tr>
										<th>제공부서명</th>
										<td id="meta-info-dept"></td>
									</tr>
									<tr>
										<th>제공시스템</th>
										<td id="meta-info-system"></td>
									</tr>
									<tr>
										<th>원시자료 단위</th>
										<td id="meta-info-base-unit"></td>
									</tr>
									<tr>
										<th>구축단위</th>
										<td id="meta-info-con-unit"></td>
									</tr>
									<tr>
										<th>기준 시점</th>
										<td id="meta-info-year"></td>
									</tr>
									<tr>
										<th>정보형태</th>
										<td id="meta-info-type"></td>
									</tr>
									<tr>
										<th>정보위치</th>
										<td id="meta-info-position"></td>
									</tr>
									<tr>
										<th>축적/해상도</th>
										<td id="meta-info-scale"></td>
									</tr>
									<tr>
										<th>단위</th>
										<td id="meta-info-unit"></td>
									</tr>
									
									<tr>
										<th>URL</th>
										<td id="meta-info-url"></td>
									</tr>
								</table>
								
							</div>
							<div class="tab-pane fade p-3" id="nav2" role="tabpanel" aria-labelledby="nav-tab2">
								
								<span class="h6 mb-3">관련 지표</span>
								
								<table class="table vestapTable text-center mt-3 mb-3 table-hover" id="meta-indi-table">
									<thead>
										<tr>
											<th>번호</th>
											<th>지표명</th>
											<th>지표구분</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								
								<div id="meta-indi-page" class="mt-2 mb-3 justify-content-center form-inline"></div>
								
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
		
		$("#select-offer option[value='" + "${meta_offer}" + "']").prop("selected", true);
		$("#select-system option[value='" + "${meta_system}" + "']").prop("selected", true);
		
		<c:if test="${activeOffcanvas ne null}">
			fn_metaInfo("${activeOffcanvas}", 1);
			offcanvasOpen();
		</c:if>
	});
	
</script>

<!-- custom vulnerability -->
<script src="/resources/js/developer/custom-page.js"></script>

<script src="/resources/js/developer/dbinfo-meta.js"></script>

</body>
</html>