<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>사용자 정의 취약성 평가</title>

<link href="/resources/billboard/billboard.css" rel="stylesheet">
<link href="/resources/billboard/insight.css" rel="stylesheet">
<link href="/resources/css/loading/loading.css" rel="stylesheet">
<link href="/resources/openlayers/ol.css" rel="stylesheet">
</head>
<body>
<div class="display" style="display: none;">
<div class="loading">
<div class="lds-css ng-scope">
<div class="lds-spinner" style="width: 100%; height:100%"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
</div>
</div>
</div>
<table class="offcanvas-table">
	<tr>
		<td class="offcanvas-left-open"><!-- *********************** offcanvas-left-open *********************** -->
			
			<div class="card">
				<div class="card-header offcanvas-left-open-title">
					<i class="icon-file-bookmark"></i>사용자정의 취약성평가<a href="javascript:void(0);" class="on-offmenu text-blue"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
				</div>
				
				<ul class="list-group list-group-flush">
					
					<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i>리스크</li>
					<li class="list-group-item">
						
						<div class="row">
							<div class="col">
							
								<div class=" select_box">
									<select class="form-control estimation-option" id="select-field">
										<c:forEach items="${fieldList }" var="list">
											<option value="${list.code_id }"><c:out value="${list.code_nm }"/></option>
										</c:forEach>
									</select>
								</div>
								
							</div><!-- //col -->
						</div><!-- //row -->
						
					</li>
					<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i>평가항목</li>
					<li class="list-group-item">
						
						<div class="row">
							<div class="col">
							
								<div class="mt-1" id="field-name">건강 분야 평가항목</div>
								<hr class="mt-2 mb-3">
								
								<div class="toolTipUl">
									<input type="hidden" id="selectItem" value="${setItem}">
									<ul class="nav navbar-nav" id="item-list">
										
										<c:forEach items="${itemList }" var="list">
										
										<li>
											<a href="javascript:void(0);" id="info-${list.item_id }" class="item-info" data="${list.item_id }" onclick='fn_itemClick($(this));return false;'><c:out value="${list.item_nm }"/></a>
										</li>
										
										</c:forEach>
										
										<c:if test="${fn:length(itemList) eq 0 }">
											
										<li>
											<a href="javascript:void(0);">사용자 정의 항목이 없습니다.</a>
										</li>
										
										</c:if>
										
									</ul>
									
								</div><!-- //toolTipUl -->
								
								<hr class="mt-3 mb-3">
								
								<div id="item-page" class="mt-2 mb-3 justify-content-center form-inline">
									<c:if test="${not empty pageInfo }">
										<ui:pagination paginationInfo="${pageInfo }" type="text" jsFunction="fn_itemList"/>
									</c:if>
								</div>
							</div><!-- //col -->
						</div><!-- //row -->
						
					</li>
				</ul>
			</div><!-- //card -->
			
		</td>
		<td class="col align-top p-0 offcanvas-content">
			
			<div class="onmenu-div d-none">
				<a href="javascript:void(0);" class="text-blue on-offmenu"><i class="icon-arrow-caret-right"></i></a>
			</div>
			
			<div class="mainContents"><!-- mainContents -->
				<div class="card">
					<div class="card-header">
						<div class="row">
							<div class="col-2">
								<div class=" select_box">
									<select class="form-control estimation-option" id="select-rcp">
										<c:forEach items="${rcpList }" var="list">
											<option value="${list.option_id }"><c:out value="${list.option_nm }"/>
										</c:forEach>
									</select>
								</div>
							</div>
							<!-- //col -->
							<div class="col-3 pl-0">
								<div class=" select_box">
									<select class="form-control estimation-option" id="select-model">
										<c:forEach items="${scenList }" var="list">
											<option value="${list.option_id }"><c:out value="${list.option_nm }"/>
										</c:forEach>
									</select>
								</div>
							</div>
							<!-- //col -->
							<div class="col-2 pl-0">
								<div class=" select_box">
									<select class="form-control estimation-option" id="select-year">
										<c:forEach items="${yearList }" var="list">
											<option value="${list.option_id }"><c:out value="${list.option_nm }"/>
										</c:forEach>
									</select>
								</div>
							</div>
							<!-- //col -->
							<div class="col-2 pl-0">
								<div class=" select_box">
									<select class="form-control estimation-option" id="select-sd">
										<c:forEach items="${sdList }" var="list">
											<option value="${list.district_cd }"><c:out value="${list.district_nm }"/>
										</c:forEach>
									</select>
								</div>
							</div>
							<!-- //col -->
							<div class="col-2 pl-0">
								<div class=" select_box">
									<select class="form-control estimation-option" id="select-sgg">
									</select>
								</div>
							</div>
							<div class="col-1 pl-0">
								<button type="button" class="btn btn-danger w-100" id="estimation-btn" style="font-size: 11pt; font-weight: bold;">실행</button>
							</div>
							<!-- //col -->
						</div>
						<!-- //ROW -->
						<div class="row">
							<br>
						</div>
						<div class="row estimation-view d-none">
							<div class="col-12">
								<b style="font-size: large;" id="option-title"></b>
							</div>
							<div class="col-6" style="font-size: 12pt;">
								- 평가지역2 : <span id="option-sd"></span> <span id="option-sgg"></span><br> - 평가항목 : <span id="option-item"></span>
							</div>
							<div class="col-6" style="font-size: 12pt;">
								- 평가 분야 : <span id="option-field"></span> 분야<br> - 적용 기후모델 : <span id="option-model"></span> <span id="option-rcp"></span> <span id="option-year"></span>
							</div>
						</div>
						
					</div>
					<div class="card-body p-2 map-viewer">
						
						<div id="custom_esti_map" style="width: 100%;">
							<div class="ol-custom ol-unselectable ol-control ol-custom-esti-map">
								<div id="map-legend" style="display: none; margin-bottom:10px">
									<div class='legend-title'>범례</div>
									<div class='legend-scale'>
										<ul class='legend-labels'>
											<li>분석을 실행해야 합니다.</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						
					</div><!-- //boxArea -->
					
				</div><!-- //mainContents -->
			</div>
		</td>
		<td class="offcanvas-right-open" id="offcanvas-view"><!-- *********************** offcanvas-right-open *********************** -->
			
			<div class="card">
				<div class="card-header offcanvas-right-open-title">
					<i class="icon-file-bookmark"></i>취약성평가 결과 정보
					<button type="button" class="offcanvasCloseBtn close"><i class="icon-close-bold"></i></button>
				</div>
				
				<ul class="list-group list-group-flush">
					<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i><span class="item-title"></span></li>
					<li class="list-group-item p-0 pt-2">
					
						<nav>
							<div class="nav nav-tabs nav-justified" id="nav-tab" role="tablist">
								<a class="nav-item nav-link " id="nav-tab1" data-toggle="tab" href="#nav1" role="tab" aria-controls="nav1" aria-selected="true">상세보기</a>
								<a class="nav-item nav-link active" id="nav-tab2" data-toggle="tab" href="#nav2" role="tab" aria-controls="nav-2" aria-selected="false">종합지수</a>
								<a class="nav-item nav-link" id="nav-tab3" data-toggle="tab" href="#nav3" role="tab" aria-controls="nav-3" aria-selected="false">기초자료 정보</a>
							</div>
						</nav>
					
						<div class="tab-content" id="nav-tabContent">
							<div class="tab-pane fade p-3" id="nav1" role="tabpanel" aria-labelledby="nav-tab1">
								<span class="h6 mb-3">산출식</span>
								<div class="boxArea p-3 mt-3 mb-3" style="height: 140px">
									<p><span class="item-title"></span> =</p>
									<p style="text-align: center;">
										(기후노출 지수 X <span class="_clim"></span>) + (민감도 지수 X <span class="_sens"></span>) - (적응능력 지수 X <span class="_adap"></span>)<br>───────────────────────────────<br>
										1
									</p>
									<p></p>
								</div>
								<div class="row">
									<br>
								</div>
								<span class="h6 mt-2  mb-2">산출에 사용된 기초자료</span>
								<div class=" select_box float-right mb-3">
									<select class="form-control" id="select-sector">
										<option value="SEC01">기후노출</option>
										<option value="SEC02">민감도</option>
										<option value="SEC03">적응능력</option>
									</select>
								</div>
								<div class="row">
									<br>
								</div>
								<table class="table vestapTable smTable text-center" id="ref-indi-table">
									<thead>
										<tr>
											<th>지표명</th>
											<th>구축형태</th>
											<th>가중치</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								<div class="row">
									<br>
								</div>
								<span class="h6 mb-3">구축형태</span>
								<table class="table vestapTable smTable text-center mt-3">
									<tbody>
										<tr>
											<td><b style="font-size: large;">A</b><br>읍면동 통계<br>원시 자료</td>
											<td><b style="font-size: large;">B</b><br>시군구 통계<br>원시 자료</td>
											<td><b style="font-size: large;">C</b><br>시도 통계<br>원시 자료</td>
											<td><b style="font-size: large;">A`</b><br>읍면동 통계<br>가공 자료</td>
										</tr>
										<tr>
											<td><b style="font-size: large;">B`</b><br>시군구 통계<br>가공 자료</td>
											<td><b style="font-size: large;">C`</b><br>시도 통계<br>가공 자료</td>
											<td><b style="font-size: large;">D`</b><br>복합/기타<br>자료</td>
											<td><b style="font-size: large;">E</b><br>기상/기후<br>원시 자료</td>
										</tr>
									</tbody>
								</table>
								
								
							</div>
							<div class="tab-pane fade  show active p-3" id="nav2" role="tabpanel" aria-labelledby="nav-tab2">
							
								<div class="boxArea text-center p-3 mb-3" style="height: 330px">
									<div id="RadarChart"></div>
								</div><!-- //boxArea -->
							
								<table class="table table-hover vestapTable smTable text-center" id="total-value-table">
									<thead>
										<tr>
											<th>순위</th>
											<th>행정구역<br />명칭</th>
											<th>취약성<br />종합지수</th>
											<th>기후노출<br />부문</th>
											<th>민감도<br />부문</th>
											<th>적응능력<br />부문</th>
											<th>방사형<br />그래프</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								
								<!-- pagination 왼쪽버튼 있을 경우 사용 -->
								<div class="nav navbar p-0 mt-4">
									<nav class="form-inline " id="estimation-page"></nav><!-- //pagination -->
									
									<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2" onclick="fn_downloadEstimation();"><i class="icon-download-disk"></i>엑셀파일 다운로드</button>
								</div><!-- //nav -->
							
							
							</div>
							<div class="tab-pane fade p-3" id="nav3" role="tabpanel" aria-labelledby="nav-tab3">
								<div class="row">
									<div class="col-5">
										<div class=" select_box  ">
											<select class="form-control" id="select-sector-base">
												<option value="SEC01">기후노출</option>
												<option value="SEC02">민감도</option>
												<option value="SEC03">적응능력</option>
											</select>
										</div>
									</div>
									<div class="col-7">
										<div class=" select_box ">
											<select class="form-control" id="select-indicator-base">
												<option value="none" selected>기초자료를 선택해주세요</option>
											</select>
										</div>
									</div>
								</div>
								<div class="row">
									<br>
								</div>
								<table class="table vestapTable smTable text-center" id="ref-indicator-table">
									<thead>
										<tr>
											<th>번호</th>
											<th>행정구역 명칭</th>
											<th id="base-indicator-nm"></th>
											<th>연도</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								<!-- pagination 왼쪽버튼 있을 경우 사용 -->
								<div class="nav navbar p-0 mt-4">
									<nav class="form-inline " id="ref-indicator-page">
									</nav>
									<!-- //pagination -->
									
									<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2" onclick="fn_downloadRefIndicatorInfo();">
										<i class="icon-download-disk"></i>엑셀파일 다운로드
									</button>
								</div>
							</div>
						</div><!-- //tab-content -->
					</li>
				</ul>
			</div><!-- //card -->
			
		</td>
	</tr>
</table>

<script>
$(document).ready(function() {
	
	$("#select-model option[value=" + "${mdl}" + "]").prop("selected", true);
	$("#select-rcp option[value=" + "${rcp}" + "]").prop("selected", true);
	$("#select-sd option[value=" + "${sd_id}" + "]").prop("selected", true);
	
	$("#option-sd").text($("#select-sd option:selected").text());
	$("#option-sgg").text($("#select-sgg option:selected").text());
	$("#option-field").text($("#select-field option:selected").text());
	$("#option-model").text($("#select-model option:selected").text());
	$("#option-rcp").text($("#select-rcp option:selected").text());
	$("#option-year").text($("#select-year option:selected").text());
	
	fn_district("${sd_id}");
	
	var agent = navigator.userAgent.toLowerCase();
	
	if ((navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') > -1) || (agent.indexOf("msie") > -1)) {
		
		$("#custom_esti_map > .ol-viewport > canvas").css("width", $(".map-viewer").width() + "px");
		$("#custom_esti_map > .ol-viewport > canvas").css("height", "700px");
		
		updateMap();
	}
	
	
});
</script>

<!-- Chart lib -->
<script src="/resources/billboard/d3.v5.min.js"></script>

<script src="/resources/billboard/billboard.js"></script>

<!-- custom vulnerability -->
<script src="/resources/js/developer/custom-page.js"></script>

<script src="/resources/js/developer/custom-estimation.js"></script>

<!-- map lib -->
<script src="/resources/openlayers/ol.js"></script>
<script src="/resources/openlayers/FileSaver.min.js"></script>
<script src="/resources/openlayers/map.js"></script>

</body>
</html>