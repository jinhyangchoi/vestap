<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP - 기후변화 취약성 - 취약성평가</title>
<link href="/resources/css/loading/loading.css" rel="stylesheet">
<link href="/resources/billboard/billboard.css" rel="stylesheet">
<link href="/resources/billboard/insight.css" rel="stylesheet">
<link href="/resources/openlayers/ol.css" rel="stylesheet">
</head>
<body>
<div class="display" style="display: none;">
<div class="loading">
<div class="lds-css ng-scope">
<div class="lds-spinner" style="100%;height:100%"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
</div>
</div>
</div>
<table class="offcanvas-table">
	<tr>
		<td class="offcanvas-left-open">
			<!-- *********************** offcanvas-left-open *********************** -->

			<div class="card">
				<div class="card-header offcanvas-left-open-title">
					<i class="icon-file-bookmark"></i>취약성평가
					<a href="javascript:void(0);" class="on-offmenu text-blue"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
				</div>

				<ul class="list-group list-group-flush">
					<li class="list-group-item sub-title border-top-0"><i
						class="icon-file-align"></i>리스크</li>
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
					<li class="list-group-item sub-title border-top-1"><i
						class="icon-file-align"></i>평가항목</li>
					<li class="list-group-item">

						<div class="row">
							<div class="col">

								<div class="mt-1"><span id="fieldName">
								<c:forEach var="i" items="${fieldList }">
									<c:if test="${setField ne null and setField eq i.codeId }">${i.codeNm}</c:if>
								</c:forEach>
								</span> 리스크 평가항목</div>
								<hr class="mt-2 mb-3">

								<div class="toolTipUl">
									<input type="hidden" id="selectItem" value="${setItem}">
									<ul class="nav navbar-nav" id="itemList">
									<c:forEach var="i" items="${itemList }">
										<li><a href='#' class='<c:if test="${setItem ne null and setItem eq i.itemId }">active</c:if>' data='${i.itemId }' onclick='fn_itemClick($(this));return false;'>${i.itemNm }</a></li>
									</c:forEach>
									</ul>

								</div>
								<!-- //toolTipUl -->
							</div>
							<!-- //col -->
						</div> <!-- //row -->

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
				<div class="card">
					<div class="card-header">
						<div class="row">
							<div class="col-3">
								<div class=" select_box">
									<select class="form-control" id="scenModelList">
									<c:forEach var="i" items="${modelList }" varStatus="is">
										<option value='${i.mdlId}' <c:if test="${setModel ne null and setModel eq i.mdlId }">selected</c:if>>${i.mdlNm}</option>
									</c:forEach>
									</select>
								</div>
							</div>
							<!-- //col -->
							<div class="col-2 pl-0">
								<div class=" select_box">
									<select class="form-control" id="scenSectionList">
									<c:forEach var="i" items="${sectionList }">
										<option value='${i.rcpId}' <c:if test="${setSection ne null and setSection eq i.rcpId }">selected</c:if>>${i.rcpNm}</option>
									</c:forEach>
									</select>
								</div>
							</div>
							<!-- //col -->
							<div class="col-2 pl-0">
								<div class=" select_box">
									<select class="form-control" id="scenYearList">
									<c:forEach var="i" items="${yearList }">
										<option value='${i.yearId}' <c:if test="${setYear ne null and setYear eq i.yearId }">selected</c:if>>${i.yearNm}</option>
									</c:forEach>
									</select>
								</div>
							</div>
							<!-- //col -->
							<div class="col-2 pl-0">
								<div class=" select_box">
									<select class="form-control" id="sidoList">
									<c:forEach var="i" items="${sidoList }">
										<option value='${i.districtCd}' <c:if test="${setSido eq i.districtCd}">selected</c:if> >${i.districtNm}</option>
									</c:forEach>
									</select>
								</div>
							</div>
							<!-- //col -->
							<div class="col-2 pl-0">
								<div class=" select_box">
									<select class="form-control" id="sigunguList">
									<c:if test="${author eq 'W' or author eq 'A'}">
										<option value='' selected>전체</option>
									</c:if>
									<c:forEach var="i" items="${sigunguList }">
										<option value='${i.districtCd}' <c:if test="${setSigungu eq i.districtCd}">selected</c:if> >${i.districtNm}</option>
									</c:forEach>
									</select>
								</div>
							</div>
							<!-- //col -->
							<div class="col-1 pl-0">
								<button type="button" class="btn btn-danger w-100" id="estimation-btn" style="font-size: 11pt; font-weight: bold;">실행</button>
							</div>
							<!-- //col -->
						</div>
						<hr class="resultView" style="display:none">
						<!-- //ROW -->
						<div class="row resultView" id="resultView" style="display:none">
						
							<div class="col-12">
								<b style="font-size: large;">[<span class="_sido"></span>&nbsp;<span class="_sigungu"></span>의&nbsp;<span class="_item"></span>&nbsp;평가 도출내역]</b>
							</div>
							<div class="col-6" style="font-size: 11pt;">
								- 평가지역 :&nbsp;<span class="_sido"></span><!-- 시도 -->
								&nbsp;<span class="_sigungu"></span><!-- 시군구 -->
								<br> 
								- 평가항목 : <span class="_item"></span><!-- 평가항목 -->
							</div>
							<div class="col-6" style="font-size: 11pt;">
								- 평가 리스크 : <span class="_field"></span><!-- 리스크 -->
								<br> 
								- 적용 기후모델 :
								&nbsp;<span class="_model"></span><!-- 기후모델 -->
								&nbsp;<span class="_section"></span><!-- 시나리오 -->
								&nbsp;<span class="_year"></span><!-- 연대 -->
							</div>
						</div>

					</div>
					<div class="card-body p-2">
						<div id="esti_map" style="width: 100%; height: 860px;">
							<div class="ol-custom ol-unselectable ol-control ol-climate-esti ol-esti-map">
								<div id="map-legend" style="display: none; margin-bottom:10px">
									<div class='legend-title'>범례</div>
									<div class='legend-scale'>
										<ul class='legend-labels'>
											<li><span style='background: #80B1D3; border: 1px solid #999;'></span>white</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- //boxArea -->

				</div>
				<!-- //mainContents -->
			</div>
		</td>
		<td class="offcanvas-right-open " id="offcanvas-view">
			<!-- *********************** offcanvas-right-open *********************** -->

			<div class="card ">
				<div class="card-header offcanvas-right-open-title">
					<i class="icon-file-bookmark"></i>취약성평가 결과 정보
					<button type="button" class="offcanvasCloseBtn close">
						<i class="icon-close-bold"></i>
					</button>
				</div>

				<ul class="list-group list-group-flush">
					<li class="list-group-item sub-title border-top-0 no-after"><i
						class="icon-file-align"></i><span class="_item"></span>
						<button type="button" class="btn btn-sm btn-blue float-right" onClick="fn_printReport();return false;">
							<i class="icon-file-roll"></i>보고서 출력
						</button></li>
					<li class="list-group-item p-0 pt-2">

						<nav>
							<div class="nav nav-tabs nav-justified" id="nav-tab"
								role="tablist">
								<a class="nav-item nav-link " id="nav-tab1" data-toggle="tab"
									href="#nav1" role="tab" aria-controls="nav1"
									aria-selected="true">상세보기</a> <a
									class="nav-item nav-link active" id="nav-tab2"
									data-toggle="tab" href="#nav2" role="tab" aria-controls="nav-2"
									aria-selected="false">종합지수</a> <a class="nav-item nav-link"
									id="nav-tab3" data-toggle="tab" href="#nav3" role="tab"
									aria-controls="nav-3" aria-selected="false">기초자료 정보</a>
							</div>
						</nav>

						<div class="tab-content" id="nav-tabContent">
							<div class="tab-pane fade p-3" id="nav1" role="tabpanel" aria-labelledby="nav-tab1">
								<span class="h6 mb-3">산출식</span>
								<div class="boxArea p-3 mt-3 mb-3">
									<p><span class="_item"></span>&nbsp;=</p>
									<p style="text-align: center;">
										(기후노출 지수 X <span class="_clim"></span>) + (민감도 지수 X <span class="_sens"></span>) - (적응능력 지수 X <span class="_adap"></span>)</p>
										<hr>
									<p style="text-align: center;">1</p>
								</div>
								<span class="h6 mt-3" style="line-height: 36px;">산출에 사용된 기초자료</span>
								<div class="select_box float-right mb-3" style="width:120px;">
									<select class="form-control" id="sectorList">
										<c:forEach var="i" items="${sectorList }">
										<option value='${i.codeId}'>${i.codeNm}</option>
										</c:forEach>
									</select>
								</div>
								<table class="table table-hover vestapTable smTable text-center">
									<thead>
										<tr>
											<th>지표명</th>
											<th style="width:80px">구축형태</th>
											<th style="width:50px">가중치</th>
										</tr>
									</thead>
									<tbody id="resultDetail">
									</tbody>
								</table>
								<span class="h6 mb-3">구축형태</span>
								<table class="table  vestapTable smTable text-center mt-3">
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
								<div class="boxArea text-center p-3 mb-3" style="height: 350px">
									<div id="RadarChart"></div>
								</div>
								<!-- //boxArea -->
								<input type="hidden" id="resultTotalPage" value="1">
								<input type="hidden" id="resultTotalLimit" value="1">
								<table class="table table-hover vestapTable smTable text-center">
									<thead>
										<tr>
											<th>순위</th>
											<th style="width:100px">행정구역<br />명칭</th>
											<th>취약성<br />종합지수</th>
											<th>기후노출<br />부문</th>
											<th>민감도<br />부문</th>
											<th>적응능력<br />부문</th>
											<th>방사형<br />그래프</th>
										</tr>
									</thead>
									<tbody id="resultTotal">
									</tbody>
								</table>

								<!-- pagination 왼쪽버튼 있을 경우 사용 -->
								<div class="nav navbar p-0 mt-4">
									<nav class="form-inline ">
										<ul class="pagination previous disabled" >
											<li class="page-item"><a class="page-link" href="#" onclick="fn_previousPage('resultTotal');return false;"><i
													class="icon-arrow-caret-left"></i></a></li>
										</ul>
										<div><span id="resultTotalCurPage">1</span>/<span id="resultTotalLimitPage">1</span></div>
										<ul class="pagination next" >
											<li class="page-item"><a class="page-link" href="#" onclick="fn_nextPage('resultTotal');return false;"><i
													class="icon-arrow-caret-right"></i></a></li>
										</ul>
									</nav>
									<!-- //pagination -->

									<button type="button"
										class="btn btn-vestap btn-blue w-auto pl-2 pr-2" onclick="fn_excelDownload('total')">
										<i class="icon-download-disk"></i>엑셀파일 다운로드
									</button>
								</div>
								<!-- //nav -->
							</div>
							<div class="tab-pane fade p-3" id="nav3" role="tabpanel" aria-labelledby="nav-tab3">
								<div class="row">
									<div class="col-5">
										<div class=" select_box  ">
											<select class="form-control" id="infoSectorList">
												<c:forEach var="i" items="${sectorList }">
												<option value='${i.codeId}'>${i.codeNm}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-7">
										<div class=" select_box ">
											<select class="form-control" id="indiList">
											</select>
										</div>
									</div>
								</div>
								<div class="row">
									<br>
								</div>
								<input type="hidden" id="resultIndiInfoPage" value="1">
								<input type="hidden" id="resultIndiInfoLimit" value="1">
								<table class="table vestapTable smTable text-center">
									<thead>
										<tr>
											<th>번호</th>
											<th>행정구역 명칭</th>
											<th style="width:220px" id ="indiInfo">지표명</th>
											<th style="width:80px">연도</th>
										</tr>
									</thead>
									<tbody id="resultIndiInfo">
									</tbody>
									<tfoot id="resultIndiAlert">
									</tfoot>
								</table>
								<!-- pagination 왼쪽버튼 있을 경우 사용 -->
								<div class="nav navbar p-0 mt-4">
									<nav class="form-inline ">
										<ul class="pagination previous disabled" >
											<li class="page-item"><a class="page-link" href="#" onclick="fn_previousPage('resultIndiInfo');return false;"><i
													class="icon-arrow-caret-left"></i></a></li>
										</ul>
										<div><span id="resultIndiInfoCurPage">1</span>/<span id="resultIndiInfoLimitPage">1</span></div>
										<ul class="pagination next" >
											<li class="page-item"><a class="page-link" href="#" onclick="fn_nextPage('resultIndiInfo');return false;"><i
													class="icon-arrow-caret-right"></i></a></li>
										</ul>
									</nav>
									<!-- //pagination -->

									<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2" onclick="fn_excelDownload('indi')">
										<i class="icon-download-disk"></i>엑셀파일 다운로드
									</button>
									
								</div>
								<!-- //nav -->
							</div>
						</div> <!-- //tab-content -->

					</li>
				</ul>
			</div> <!-- //card -->

		</td>
	</tr>
</table>
<script type="text/javascript">
$(document).on("click", ".close", function() {$("#estimation-btn").html("실행");});	
$(document).on("click", "#estimation-btn", function() {fn_estimation()});
$(document).ready(function() {
	//리스크 선택창 변경 시 항목 리스트 변경
	$("#fieldList").change(function() {
		$("#fieldName").html($("#fieldList option:selected").text());
		fn_itemList();
		offcanvasClose();
	});
	
	//기후모델 변경 시 RCP변경
	$("#scenModelList").change(function() {
		fn_sectionList()
		offcanvasClose();
		});
	
	//RCP 변경 시 연도변경
	$("#scenSectionList").change(function() {fn_yearList()});
	
	//시도 목록 변경 시 시군구 목록 불러오기
	$("#sidoList").change(function(){
		fn_sigunguList();
		offcanvasClose();
	});
	
	$("#sigunguList").change(function(){
		offcanvasClose();
	});
	
	//상세보기 > 부문 변경
	$("#sectorList").change(function(){
		fn_detailList();
	});

	//기초자료 정보 > 부문 변경
	$("#infoSectorList").change(function(){
		fn_indiList();
		fn_indi();
	});
	
	//기초자료 정보 > 지표 변경
	$("#indiList").change(function(){
		fn_indi();
	});
	<c:if test="${exe ne null}">
	fn_estimation('${exe}');
	</c:if>
});

function fn_estimation(exe){

	var item = $("#selectItem").val();
	
	if(fn_checkOption()){
		if(item=='TL000054'){
			fn_estimationWhole(exe);
		}else{
			fn_estimationTotal(exe);
			fn_reloadMap();
		}
		fn_estimationDetail();
	}
}

function fn_reloadMap(){
	moveSigungu("one");
	updateMap();
}

function fn_checkOption(){
	var item = $("#selectItem").val();
	
	var msg = '';
	var type = 'warning';
	if(item==null || item==''){
		msg = '항목을 선택해 주세요.';
	}else{
		return true;
	}
	
	fn_alert("경고", msg, type);
	return false;
}

function fn_indi(){
	var item = $("#selectItem").val();
	if(item=='TL000054'){
		fn_indiDataWhole();
	}else{
		fn_indiData();
	}
	
}

function fn_resultViewSetting(){
	//기본정보 창 세팅
	$("._field").text($("#fieldList option:selected").text());
	$("._item").text($("#itemList a.active").text());
	$("._model").text($("#scenModelList option:selected").text());
	$("._section").text($("#scenSectionList option:selected").text());
	$("._year").text($("#scenYearList option:selected").text());
	$("._sido").text($("#sidoList option:selected").text());
	$("._sigungu").text($("#sigunguList option:selected").text());
}

function fn_resultViewSettingWhole(){
	//기본정보 창 세팅
	$("._field").text($("#fieldList option:selected").text());
	$("._item").text($("#itemList a.active").text());
	$("._model").text($("#scenModelList option:selected").text());
	$("._section").text($("#scenSectionList option:selected").text());
	$("._year").text($("#scenYearList option:selected").text());
	$("._sido").text("전국");
	$("._sigungu").text("");
}

function fn_resultHideView(){
	$(".resultView").hide();
}

function fn_resultShowView(){
	$(".resultView").show();
}

var stack_check;
function fn_itemClick(tag){
	var data = tag.attr('data');
	var check = data.substring(6)*1;
	$("#itemList a").removeClass('active');
	tag.addClass('active');
	$("#selectItem").val(data);
	offcanvasClose();
	if(check > 47 && check != 55){
		
		$("#scenModelList").val("CM001").attr("disabled","disabled");
		fn_alert("경고", "해당 항목은 신규항목으로 HadGEM3-RA 모델을 지원하지 않습니다.", "info");
		fn_sectionList();
		
	}else if(check == 55){
		
		$("#scenModelList").val("CM003").attr("disabled","disabled");
		fn_alert("경고", "해당 항목은 HadGEM3-RA 모델만 지원합니다.", "info");
		fn_sectionList();
		
	}else{
		$("#scenModelList").removeAttr("disabled");
		if(stack_check == 55 ){
			fn_sectionList();
		}
	}
	stack_check = check;
}

function fn_itemList(){
	var field = $("#fieldList option:selected").val();
	var dataSet = {"field" : field};
	$.ajax({
		 url:'/member/base/climate/estimation/itemList.do',
		 dataType :"json",
		 type : "get",
		 data : dataSet,
		 success:function(result){
			 $("#itemList").empty();
			 $("#selectItem").val("");
			var list = result.list;
           for(var i in list){
        	   $("#itemList").append("<li><a href='#' data='"+list[i].itemId+"' onclick='fn_itemClick($(this));return false;'>"+list[i].itemNm+"</a></li>");
           }
         }
	});
}

function fn_sectionList(){
	var item = $("#selectItem").val();
	var check = item.substring(6)*1;
	var model = $("#scenModelList option:selected").val();
	var dataSet = {"model" : model};
	$.ajax({
		 url:'/member/base/climate/estimation/sectionList.do',
		 dataType :"json",
		 type : "get",
		 data : dataSet,
		 success:function(result){
			 $("#scenSectionList").empty();
			var list = result.list;
			var html = '';
          	for(var i in list){
        	   if(check == 55 && list[i].rcpId =="RC001"){
        		   continue;
          		}
        	   html += "<option value='"+list[i].rcpId+"'>"+list[i].rcpNm+"</option>";
         	}
			$("#scenSectionList").append(html);
           fn_yearList();
         }
	});
}

function fn_yearList(){
	var item = $("#selectItem").val();
	var check = item.substring(6)*1;
	var model = $("#scenModelList option:selected").val();
	var section = $("#scenSectionList option:selected").val();
	var dataSet = {
			"model" : model,
			"section" : section
			};
	$.ajax({
		 url:'/member/base/climate/estimation/yearList.do',
		 dataType :"json",
		 type : "get",
		 data : dataSet,
		 success:function(result){
			$("#scenYearList").empty();
			var list = result.list;
			var html = '';
           	for(var i in list){
           		if(check == 55 && list[i].yearId =="YC002"){
         		   continue;
           		}
        	   html += "<option value='"+list[i].yearId+"'>"+list[i].yearNm+"</option>";
           }
           	$("#scenYearList").append(html);
         }
	});
}

function fn_sigunguList(){
	var sidoCode = $("#sidoList option:selected").val();
	var dataSet = {"sidoCode" : sidoCode};
	$.ajax({
		url:'/member/base/climate/estimation/sigunguList.do',
		dataType:'json',
		type:'get',
		data:dataSet,
		success:function(result){
			$("#sigunguList").empty();
			var list = result.list;
			<c:if test="${author eq 'W' or author eq 'A'}">
			$("#sigunguList").append("<option value='' selected>전체</option>");
			</c:if>
			for(var i in list){
				var html = "<option value='"+list[i].districtCd+"'>"+list[i].districtNm+"</option>";
				$("#sigunguList").append(html);
			}
		}
	});
}

//종합지수
function fn_estimationTotal(exe){
	var field = $("#fieldList option:selected").val();
	var item = $("#selectItem").val();
	var model = $("#scenModelList option:selected").val();
	var section = $("#scenSectionList option:selected").val();
	var year = $("#scenYearList option:selected").val();
	var sido = $("#sidoList option:selected").val();
	var sigungu = $("#sigunguList option:selected").val();
	var url = '';
	var dataSet = {
			"field" : field
			,"item" : item
			,"model" : model
			,"section" : section
			,"year" : year
			,"sido" : sido
			,"sigungu" : sigungu
			,"exe" : exe
	};
	var page = 0;
	var pageLimit = 5;
	//기초취약성평가
	if(sigungu!='' && sigungu!=null){
		url = '/member/base/climate/estimation/resultEmdTotal.do';
	}else{
		url = '/member/base/climate/estimation/resultSggTotal.do';
	}
	$.ajax({
		url:url,
		dataType:'json',
		type:'get',
		data:dataSet,
		beforeSend: function(){$(".display").css("display","block");},
		complete: function(){$(".display").css("display","none");fn_reloadMap();$('#map-legend').show();},
		success:function(result){
			$("#resultTotal").empty();
			offcanvasOpen();
			
			$("#estimation-btn").html($('<i class="icon-arrow-caret-right" style="font-size: 16pt;"></i>'));
			var list = result.list;
			var layer_val = new Array();
			var layer_cd = new Array();
			//기본정보 창 세팅
			 fn_resultViewSetting();
			 fn_resultShowView();
			
			if(sigungu!='' && sigungu!=null){
				/*기초 종합지수창*/
				for(var i in list){
					var html ='';
					if(i==0){
						fn_radarRedraw(list[i].emdNm,list[i].climValue,list[i].sensValue,list[i].adapValue);
					}
					if(i%pageLimit==0) page++;
					html = '<tr class="pg'+page+'" onclick="fn_radarRedraw(\''+list[i].emdNm+'\','+list[i].climValue+','+list[i].sensValue+','+list[i].adapValue+')"';
					if(page==1){
						html+=' style="display:table-row">';
					}else{
						html+=' style="display:none">';
					}
					html+='<td>'+list[i].no+'</td><td>'+list[i].emdNm+'</td><td>'+list[i].estiValue+'</td><td>'+list[i].climValue+'</td><td>'+list[i].sensValue+'</td><td>'+list[i].adapValue+'</td><td><i class="icon-graph-pie i-24"></i></td></tr>';
					$("#resultTotal").append(html);
					layer_val.push(list[i].estiValue);
					layer_cd.push(list[i].districtCd);
				}
			}else{
				/*광역 종합지수창*/
				for(var i in list){
					var html = '';
					if(i==0){
						fn_radarRedraw(list[i].sggNm,list[i].climValue,list[i].sensValue,list[i].adapValue);
					}
					if(i%pageLimit==0) page++;
					html +='<tr class="pg'+page+'" '
					if(page==1){
						html+=' style="display:table-row">';
					}else{
						html+=' style="display:none">';
					}
					html+='<td>'+list[i].no+'</td><td onclick="fn_subEstimation(\''+list[i].districtCd+'\');return false;" >'+list[i].sggNm+'</td><td>'+list[i].estiValue+'</td><td>'+list[i].climValue+'</td><td>'+list[i].sensValue+'</td><td>'+list[i].adapValue+'</td><td onclick="fn_radarRedraw(\''+list[i].sggNm+'\','+list[i].climValue+','+list[i].sensValue+','+list[i].adapValue+');return false;"><i class="icon-graph-pie i-24"></i></td></tr>';
					$("#resultTotal").append(html);
					layer_val.push(list[i].estiValue);
					layer_cd.push(list[i].districtCd);
				}
			}
			$("#resultTotalPage").val(1);
			$("#resultTotalLimit").val(page);
			$("#resultTotalCurPage").text("1");
			$("#resultTotalLimitPage").text(page);
			//changeVectorLayer(layer_cd,layer_val);
			addVector(layer_cd,layer_val);
		}
	});
}

function fn_estimationWhole(exe){
	var field = $("#fieldList option:selected").val();
	var item = $("#selectItem").val();
	var model = $("#scenModelList option:selected").val();
	var section = $("#scenSectionList option:selected").val();
	var year = $("#scenYearList option:selected").val();
	var sido = $("#sidoList option:selected").val();
	var sigungu = $("#sigunguList option:selected").val();
	var url = '';
	var dataSet = {
			"field" : field
			,"item" : item
			,"model" : model
			,"section" : section
			,"year" : year
			,"sido" : sido
			,"sigungu" : sigungu
			,"exe" : exe
	};
	var page = 0;
	var pageLimit = 5;
	url = '/member/base/climate/estimation/resultWhole.do';
	$.ajax({
		url:url,
		dataType:'json',
		type:'get',
		data:dataSet,
		beforeSend: function(){$(".display").css("display","block");},
		complete: function(){$(".display").css("display","none");resetZoom();$('#map-legend').show();},
		success:function(result){
			$("#resultTotal").empty();
			offcanvasOpen();
			
			$("#estimation-btn").html($('<i class="icon-arrow-caret-right" style="font-size: 16pt;"></i>'));
			var list = result.list;
			var layer_val = new Array();
			var layer_cd = new Array();
			//기본정보 창 세팅
			 fn_resultViewSettingWhole();
			 fn_resultShowView();
			
			/*광역 종합지수창*/
			for(var i in list){
				var html = '';
				if(i==0){
					fn_radarRedraw(list[i].sggNm,list[i].climValue,list[i].sensValue,list[i].adapValue);
				}
				if(i%pageLimit==0) page++;
				html +='<tr class="pg'+page+'"  onclick="fn_radarRedraw(\''+list[i].sggNm+'\','+list[i].climValue+','+list[i].sensValue+','+list[i].adapValue+');return false;"'
				if(page==1){
					html+=' style="display:table-row">';
				}else{
					html+=' style="display:none">';
				}
				html+='<td>'+list[i].no+'</td><td>'+list[i].sggNm+'</td><td>'+list[i].estiValue+'</td><td>'+list[i].climValue+'</td><td>'+list[i].sensValue+'</td><td>'+list[i].adapValue+'</td><td><i class="icon-graph-pie i-24"></i></td></tr>';
				$("#resultTotal").append(html);
				layer_val.push(list[i].estiValue);
				layer_cd.push(list[i].districtCd);
			}
			$("#resultTotalPage").val(1);
			$("#resultTotalLimit").val(page);
			$("#resultTotalCurPage").text("1");
			$("#resultTotalLimitPage").text(page);
			addVector(layer_cd,layer_val);
		}
	});
}

function fn_mapLegend(){
	$('#map-legend').show();
}

function fn_subEstimation(districtCd){
	var url = "/member/base/subEstimation.do";
	var dataSet = {"districtCd":districtCd};
	$.ajax({
		url:url
		,data:dataSet
		,type:"get"
		,dataType:"json"
		,success:function(){
			window.open('/member/base/climate/estimation/view.do?${_csrf.parameterName}=${_csrf.token}&exe=');
		}
	});
	
	
}

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

function fn_nextPage(id){
	var page = $("#"+id+"Page").val()*1;
	var limitPage = $("#"+id+"Limit").val()*1;
	if(page<limitPage){
	$("#"+id+" .pg"+page).css("display","none");
	page++;
	$("#"+id+" .pg"+page).css("display","table-row");
	}
	$("#"+id+"Page").val(page);
	$("#"+id+"CurPage").text('');
	$("#"+id+"CurPage").text(page);
}
//data1 중요
function fn_radarRedraw(name,col1,col2,col3){
chart.data.names({
		 data1 : name
		});
	chart.load({
	    columns: [
	       ["data1", col1, col2, col3]]});
}

//상세보기
function fn_estimationDetail(){
	var item = $("#selectItem").val();
	var sector = $("#sectorList option:selected").val(); 
	var dataSet = {
			"item" : item
	};
	$.ajax({
		url:'/member/base/climate/estimation/resultDetail.do',
		dataType:'json',
		type:'get',
		data:dataSet,
		success:function(result){
			$("#resultDetail").empty();
			$("#indiList").empty();
			var info = result.info;
			var list = result.list;
			var instead = [];
			$('._clim').text(info.ceWeight);	
			$('._sens').text(info.csWeight);	
			$('._adap').text(info.aaWeight);			
			for(var i in list){
				$("#resultDetail").append('<tr class="'+list[i].sectorId+'" onClick="fn_indiLink($(this))" data="'+list[i].indiId+'" style="display:none"><td>'+list[i].indiNm+'</td><td><b>'+list[i].indiConstSign+'</b></td><td>'+list[i].indiValWeight+'</td></tr>');
				$("#indiList").append('<option class="'+list[i].sectorId+'" value="'+list[i].indiId+'" >'+list[i].indiNm+'</option>');
			}
			fn_detailList(sector);
			fn_indiList(sector);
			
			for(var i in list){
				if(list[i].indi_space!='SPA01'){
					instead.push(list[i].indiNm);
				}
			}
			
			if(item=='TL000054'){
				fn_indiDataWhole();
			}else{
				fn_indiData();
			}

			if(instead.length>0){
				fn_alert("알림", "이 항목의 일부 지표는 상위 행정구역 데이터로 평가되어 결과에 반영되지 않았습니다.", "info");
			}
		}		
	});
}

//상세보기 > 부문별 지표보기
function fn_detailList(){
	var sectorCode = $("#sectorList option:selected").val();
	$("#resultDetail tr").css("display","none");
	$("#resultDetail ."+sectorCode).css("display","table-row");
}

//지표상세보기 연결
function fn_indiLink(tag){
	var data = tag.attr("data");
	window.open('/member/base/dbinfo/indicator/list.do?activeOffcanvas='+data);	
}

//기초자료 정보 > 부문별 지표 목록
function fn_indiList(){
	var sectorCode = $("#infoSectorList option:selected").val();
	$("#indiList option").css("display","none");
	$("#indiList ."+sectorCode).css("display","table-row");
	$("#indiList ."+sectorCode).selected();
	
}

//기초자료 정보
function fn_indiData(){
	var model = $("#scenModelList option:selected").val();
	var section = $("#scenSectionList option:selected").val();
	var year = $("#scenYearList option:selected").val();
	var sido = $("#sidoList option:selected").val();
	var sigungu = $("#sigunguList option:selected").val();
	var indi = $("#indiList option:selected").val();
	var type = null;
	var dataSet = {
			"model" : model
			,"section" : section
			,"year" : year
			,"sido" : sido
			,"sigungu" : sigungu
			,"indi" : indi
	};
	var page = 0;
	var pageLimit = 10;
	if(sigungu!='' && sigungu!=null){
		url = '/member/base/climate/estimation/resultEmdData.do';
		type = 'EMD';
	}else{
		url = '/member/base/climate/estimation/resultSggData.do';
		type = 'SGG';
	}
	$.ajax({
		url:url,
		dataType:'json',
		type:'get',
		data:dataSet,
		success:function(result){
			$("#resultIndiInfo").empty();
			$("#resultIndiAlert").empty();
			var list = result.list;
			var info = result.info;
				$("#indiInfo").text(info.indiNm+'('+info.indiUnit+')');
				if(list.length == 0){
					$("#resultIndiInfo").append('<tr><td class="text-center" colspan ="4">로데이터가 존재하지 않습니다.</td></tr>');
				}
			if(type=='EMD'){
				for(var i in list){
					if(i%pageLimit==0) page++;
					if(page==1){
						$("#resultIndiInfo").append('<tr class="pg'+page+'" style="display:table-row"><td>'+list[i].no+'</td><td>'+list[i].emdNm+'</td><td>'+list[i].indiVal+'</td><td>'+list[i].indiYear+'</td></tr>');
					}else{
						$("#resultIndiInfo").append('<tr class="pg'+page+'" style="display:none"><td>'+list[i].no+'</td><td>'+list[i].emdNm+'</td><td>'+list[i].indiVal+'</td><td>'+list[i].indiYear+'</td></tr>');
					}
				}
			}else{
				for(var i in list){
					if(i%pageLimit==0) page++;
					if(page==1){
						$("#resultIndiInfo").append('<tr class="pg'+page+'" style="display:table-row"><td>'+list[i].no+'</td><td>'+list[i].sggNm+'</td><td>'+list[i].indiVal+'</td><td>'+list[i].indiYear+'</td></tr>');
					}else{
						$("#resultIndiInfo").append('<tr class="pg'+page+'" style="display:none"><td>'+list[i].no+'</td><td>'+list[i].sggNm+'</td><td>'+list[i].indiVal+'</td><td>'+list[i].indiYear+'</td></tr>');
					}
				}
			}
			var footerInfo = null;
			if(info.indiSpace=="SPA02"){
				footerInfo='* 실제 읍면동 데이터가 없는 경우, 시군구 데이터 가공없이 그대로 사용'
			}else if(type=='EMD' && info.indiSpace=="SPA03"){
				footerInfo='* 실제 읍면동 데이터가 없는 경우, 시도 데이터 가공없이 그대로 사용'
			}else if(type=='SGG' && info.indiSpace=="SPA03"){
				footerInfo='* 실제 시군구 데이터가 없는 경우, 시도 데이터 가공없이 그대로 사용'
			}else{
			}
			if(footerInfo!=null){
				$("#resultIndiAlert").append('<tr ><td colspan="4">'+footerInfo+'</td></tr>');
			}
			$("#resultIndiInfoPage").val(1);
			$("#resultIndiInfoLimit").val(page);
			$("#resultIndiInfoCurPage").text("1");
			$("#resultIndiInfoLimitPage").text(page);
		}
	});
}

function fn_indiDataWhole(){
	var model = $("#scenModelList option:selected").val();
	var section = $("#scenSectionList option:selected").val();
	var year = $("#scenYearList option:selected").val();
	var indi = $("#indiList option:selected").val();
	var dataSet = {
			"model" : model
			,"section" : section
			,"year" : year
			,"indi" : indi
	};
	var page = 0;
	var pageLimit = 10;
		url = '/member/base/climate/estimation/resultWholeData.do';
	$.ajax({
		url:url,
		dataType:'json',
		type:'get',
		data:dataSet,
		success:function(result){
			$("#resultIndiInfo").empty();
			var list = result.list;
			var info = result.info;
			$("#indiInfo").text(info.indiNm+'('+info.indiUnit+')');
			if(list.length == 0){
				$("#resultIndiInfo").append('<tr><td class="text-center" colspan ="4">로데이터가 존재하지 않습니다.</td></tr>');
			}
			for(var i in list){
				if(i%pageLimit==0) page++;
				if(page==1){
					$("#resultIndiInfo").append('<tr class="pg'+page+'" style="display:table-row"><td>'+list[i].no+'</td><td>'+list[i].sggNm+'</td><td>'+list[i].indiVal+'</td><td>'+list[i].indiYear+'</td></tr>');
				}else{
					$("#resultIndiInfo").append('<tr class="pg'+page+'" style="display:none"><td>'+list[i].no+'</td><td>'+list[i].sggNm+'</td><td>'+list[i].indiVal+'</td><td>'+list[i].indiYear+'</td></tr>');
				}

			}
			$("#resultIndiInfoPage").val(1);
			$("#resultIndiInfoLimit").val(page);
			$("#resultIndiInfoCurPage").text("1");
			$("#resultIndiInfoLimitPage").text(page);
		}
	});
}

function fn_printReport(){
	var field = $("#fieldList option:selected").val();
	var item = $("#selectItem").val();
	var model = $("#scenModelList option:selected").val();
	var section = $("#scenSectionList option:selected").val();
	var year = $("#scenYearList option:selected").val();
	var sido = $("#sidoList option:selected").val();
	var sigungu = $("#sigunguList option:selected").val();
	var url = '/member/base/climate/estimation/report.do?${_csrf.parameterName}=${_csrf.token}';
	
    var form = document.createElement("form");      
    form.setAttribute("method","post");        
    form.setAttribute("target","report")
    form.setAttribute("action",url);     
    document.body.appendChild(form);                
  
    var input_field = document.createElement("input");   
    input_field.setAttribute("type","hidden");           
    input_field.setAttribute("name","field");             
    input_field.setAttribute("value",field);             
    form.appendChild(input_field);   
    
    var input_item = document.createElement("input");   
    input_item.setAttribute("type","hidden");          
    input_item.setAttribute("name","item");               
    input_item.setAttribute("value",item);             
    form.appendChild(input_item); 
    
    var input_model = document.createElement("input");   
    input_model.setAttribute("type","hidden");          
    input_model.setAttribute("name","model");               
    input_model.setAttribute("value",model);             
    form.appendChild(input_model); 
    
    var input_section = document.createElement("input");   
    input_section.setAttribute("type","hidden");          
    input_section.setAttribute("name","section");               
    input_section.setAttribute("value",section);             
    form.appendChild(input_section); 
    
    var input_year = document.createElement("input");   
    input_year.setAttribute("type","hidden");          
    input_year.setAttribute("name","year");               
    input_year.setAttribute("value",year);             
    form.appendChild(input_year); 
    
    var input_sido = document.createElement("input");   
    input_sido.setAttribute("type","hidden");          
    input_sido.setAttribute("name","sido");               
    input_sido.setAttribute("value",sido);             
    form.appendChild(input_sido); 
    
    var input_sigungu = document.createElement("input");   
    input_sigungu.setAttribute("type","hidden");          
    input_sigungu.setAttribute("name","sigungu");               
    input_sigungu.setAttribute("value",sigungu);             
    form.appendChild(input_sigungu); 
    
    window.open('', 'report','width=1800,height=1000, resizable=1,scrollbars=1');
    
    form.submit();   
}

function fn_excelDownload(type){
	var field = $("#fieldList option:selected").val();
	var fieldNm = $("#fieldList option:selected").text();
	var item = $("#selectItem").val();
	var itemNm = $("#itemList a.active").text();
	var model = $("#scenModelList option:selected").val();
	var modelNm = $("#scenModelList option:selected").text();
	var section = $("#scenSectionList option:selected").val();
	var sectionNm = $("#scenSectionList option:selected").text();
	var year = $("#scenYearList option:selected").val();
	var yearNm = $("#scenYearList option:selected").text();
	var sido = $("#sidoList option:selected").val();
	var sigungu = $("#sigunguList option:selected").val();
	var sidoNm = $("#sidoList option:selected").text();
	var sigunguNm = $("#sigunguList option:selected").text();
	var indi = $("#indiList option:selected").val();
	var indiNm = $("#indiList option:selected").text();
	var url = '/member/base/climate/estimation/excelDownload.do';
	var params = "field=" + field
	+ "&fieldNm=" + fieldNm
	+ "&item=" + item
	+ "&itemNm=" + itemNm
	+ "&indi=" + indi
	+ "&indiNm=" + indiNm
	+ "&model=" + model
	+ "&modelNm=" + modelNm
	+ "&section=" + section
	+ "&sectionNm=" + sectionNm
	+ "&year=" + year
	+ "&yearNm=" + yearNm
	+ "&sido=" + sido
	+ "&sigungu=" + sigungu
	+ "&sidoNm=" + sidoNm
	+ "&sigunguNm=" + sigunguNm
	+ "&type=" + type;
	location.href = encodeURI(url+"?" + params);
}

function fn_resultDownload(){
	var url = '/member/base/climate/estimation/resultDownload.do';
	location.href = encodeURI(url);
}
</script>
<script src="/resources/billboard/d3.v5.min.js"></script>
<script src="/resources/billboard/billboard.js" type="text/javascript"></script>
<script src="/resources/openlayers/ol.js" type="text/javascript"></script>
<script type="text/javascript" src="/resources/openlayers/FileSaver.min.js"></script>
<script type="text/javascript" src="/resources/openlayers/map.js"></script>
<script type="text/javascript">
var chart = bb.generate({
	  data: {
	    x: "x",
	    colors: {
	        data1: "#1f83ce"
	        },
        names: {
            data1: "",
          },
	    columns: [
		["x", "기후노출", "민감도", "적응능력"],
		["data1", 0, 0, 0]
	    ],
	    type: "radar",
	    labels: true
	  },
	  radar: {
	    axis: {
	      max: 0.5
	    },
	    level: {
	      depth: 5
	    },
	    direction: {
	      clockwise: false
	    }
	  },
	  bindto: "#RadarChart"
	});
</script>
