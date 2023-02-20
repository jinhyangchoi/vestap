<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>VESTAP - 기후변화 취약성 - 지자체간 비교분석</title>
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
					<i class="icon-file-bookmark"></i>지자체간 비교분석<a
						href="javascript:void(0);" class="on-offmenu text-blue"><i
						class="icon-arrow-caret-left offmenu-icon"></i></a>
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
											<option value='${i.codeId}'
												<c:if test="${setField ne null and setField eq i.codeId }">selected</c:if>>${i.codeNm}</option>
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
								<div class="mt-0 ">
									<span id="fieldName"> <c:forEach var="i"
											items="${fieldList }">
											<c:if test="${setField ne null and setField eq i.codeId }">${i.codeNm}</c:if>
										</c:forEach>
									</span> 리스크 평가항목
								</div>
								<hr class="mt-2 mb-3">
								<div class="toolTipUl">
									<input type="hidden" id="selectItem" value="${setItem}">
									<ul class="nav navbar-nav" id="itemList">
										<c:forEach var="i" items="${itemList }">
											<li><a href='#'
												class='<c:if test="${setItem ne null and setItem eq i.itemId }">active</c:if>' data='${i.itemId }' onclick='fn_itemClick($(this));return false;'>${i.itemNm }</a></li>
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
				<a href="javascript:void(0);" class="text-blue on-offmenu"><i
					class="icon-arrow-caret-right"></i></a>
			</div>
			<div class="mainContents">
				<!-- mainContents -->
				<div class="card mb-3">
					<div class="card-header">
						<div class="row">
							<div class="col-3"><span style="line-height: 36px;">시나리오</span></div>
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
							<div class="col-3 pl-0">
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
							<div class="col-1 pl-0">
								<button type="button" class="btn btn-danger w-100" id="estimation-btn" style="font-size: 11pt; font-weight: bold;">실행</button>
							</div>
						</div>
						<!-- //ROW -->
						<hr class="resultView" style="display:none">
						<!-- //ROW -->
						<div class="row resultView" style="display:none">
							<div class="col-12">
								<b style="font-size: large;">[<span class="_sido"></span>&nbsp;<span class="_sigungu"></span>와&nbsp;
								<span class="_sidoC"></span>&nbsp;<span class="_sigunguC"></span>의&nbsp;<span class="_item"></span>&nbsp;평가 도출내역]</b>
							</div>
							<div class="col-6" style="font-size: 11pt;">
								- 평가지역 :&nbsp;<span class="_sido"></span><!-- 기준 시도 -->
								&nbsp;<span class="_sigungu"></span><!-- 기준 시군구 -->
								,
								&nbsp;<span class="_sidoC"></span><!-- 시도 -->
								&nbsp;<span class="_sigunguC"></span><!-- 시군구 -->
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
					<div class="card-header p-2">
						<div class="p-1 pl-3">
							<span>기준지역</span>
							<c:choose>
								<c:when test="${author eq 'B'}">
								<div class="col-3 pr-1 float-right">
								<div class=" select_box">
									<select class="form-control" id="sigunguList" disabled>
									<c:forEach var="i" items="${sigunguList }">
										<c:if test="${setSigungu eq i.districtCd}"><option value='${i.districtCd}'>${i.districtNm}</option></c:if>
									</c:forEach>
									</select>
								</div>
								</div>
								<div class="col-3 pr-1 float-right">
								<div class=" select_box">
									<select class="form-control" id="sidoList" disabled>
									<c:forEach var="i" items="${sidoList }">
										<c:if test="${setSido eq i.districtCd}"><option value='${i.districtCd}' selected >${i.districtNm}</option></c:if>
									</c:forEach>
									</select>
								</div>
								</div>
								</c:when>
								<c:when test="${author eq 'W'}">
								<div class="col-3 pr-1 float-right">
								<div class=" select_box">
									<select class="form-control" id="sidoList" disabled>
									<c:forEach var="i" items="${sidoList }">
										<c:if test="${setSido eq i.districtCd}"><option value='${i.districtCd}' selected >${i.districtNm}</option></c:if>
									</c:forEach>
									</select>
								</div>
								</div>
								</c:when>
								<c:when test="${author eq 'A'}">
								<div class="col-3 pr-1 float-right">
									<div class=" select_box">
									<select class="form-control" id="sigunguList">
									<option value='' selected>전체</option>
									<c:forEach var="i" items="${sigunguList }">
										<option value='${i.districtCd}'>${i.districtNm}</option>
									</c:forEach>
									</select>
									</div>
								</div>
								<div class="col-3 pr-1 float-right">
								<div class=" select_box">
									<select class="form-control" id="sidoList">
									<c:forEach var="i" items="${sidoList }">
										<option value='${i.districtCd}' <c:if test="${setSido eq i.districtCd}"> selected </c:if> >${i.districtNm}</option>
									</c:forEach>
									</select>
								</div>
								</div>
								</c:when>
							</c:choose>
						</div>
					</div>
					<!-- card-header -->
					<div class="card-body p-2" style="height: 390px">
						<div id="comp_base_map" style="width: 100%; height: 374px;">
							<div class="ol-custom ol-unselectable ol-control ol-comp-base">
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
					<!-- card-body -->
					<div class="card-header p-2 ">
						<div class="p-1 pl-3">
							<span>비교지역</span>
							<c:choose>
								<c:when test="${author eq 'B'}">
								<div class="col-3 pr-1 float-right">
									<div class=" select_box">
										<select class="form-control" id="sigunguCList">
									<c:forEach var="i" items="${sigunguList }">
										<c:if test="${setSigungu ne i.districtCd}"><option value='${i.districtCd}'>${i.districtNm}</option></c:if>
									</c:forEach>
									</select>
									</div>
								</div>
								<div class="col-3 pr-1 float-right">
								<div class=" select_box">
									<select class="form-control" id="sidoCList">
									<c:forEach var="i" items="${sidoList }">
										<option value='${i.districtCd}' >${i.districtNm}</option>
									</c:forEach>
									</select>
									</div>
								</div>
								</c:when>
								<c:when test="${author eq 'W'}">
								<div class="col-3 pr-1 float-right">
								<div class=" select_box">
									<select class="form-control" id="sidoCList">
									<c:forEach var="i" items="${sidoList }">
										<c:if test="${setSido ne i.districtCd}"><option value='${i.districtCd}' >${i.districtNm}</option></c:if>
									</c:forEach>
									</select>
									</div>
								</div>
								</c:when>
								<c:when test="${author eq 'A'}">
								<div class="col-3 pr-1 float-right">
									<div class=" select_box">
										<select class="form-control" id="sigunguCList">
									<option value='' selected>전체</option>
									</select>
									</div>
								</div>
								<div class="col-3 pr-1 float-right">
								<div class=" select_box">
									<select class="form-control" id="sidoCList">
									<c:forEach var="i" items="${sidoList }">
										<option value='${i.districtCd}' >${i.districtNm}</option>
									</c:forEach>
									</select>
									</div>
								</div>
								</c:when>
							</c:choose>							
						</div>
					</div>
					<!-- card-header -->
					<div class="card-body p-2" style="height: 390px">
						<div id="comp_comp_map" style="width: 100%; height: 374px;">
							<div class="ol-custom ol-unselectable ol-control ol-comp-comp">
								<div id="map-legend" style="display: none; margin-bottom:10px">
									<div class='legend-title'>범례 </div>
									<div class='legend-scale'>
										<ul class='legend-labels'>
											<li><span style='background: #80B1D3; border: 1px solid #999;'></span>white</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- card-body -->
				</div>
				<!-- //card -->
			</div> <!-- //mainContents -->
		</td>
		<td class="offcanvas-right-open" id="offcanvas-view">
			<!-- *********************** offcanvas-right-open *********************** -->

			<div class="card">
				<div class="card-header offcanvas-right-open-title">
					<i class="icon-file-bookmark"></i>취약성평가 결과 정보
					<button type="button" class="offcanvasCloseBtn close">
					<i class="icon-close-bold"></i>
				</button>
				</div>

				<ul class="list-group list-group-flush">
					<li class="list-group-item sub-title border-top-0"><i
						class="icon-file-align"></i>각 부문별 대용변수 비교 그래프</li>
					<!-- 시간에 따른 취약성 지수 비교 그래프 -->
					<li class="list-group-item">

						<div class="boxArea text-center p-3" style="height: 350px">
							<div id='BarChart'></div>
						</div> <!-- //boxArea -->

					</li>
					<li class="list-group-item sub-title border-top-0"><i
						class="icon-file-align"></i>취약성 평가 결과표</li>
					<li class="list-group-item">
					<input type="hidden" id="resultTotalPage" value="1">
					<input type="hidden" id="resultTotalLimit" value="1">
					<table class="table vestapTable smTable text-center">
						<thead>
							<tr>
								<th style="width:140px">행정구역 명칭</th>
								<th>취약성<br />종합지수</th>
								<th>기후노출<br />부문</th>
								<th>민감도<br />부문</th>
								<th>적응능력<br />부문</th>
							</tr>
						</thead>
						<tbody id="resultTotal">
						</tbody>
					</table>
					</li>
					</ul>
				<!-- //nav -->
			</div> <!-- //card -->
		</td>
	</tr>
</table>
<script type="text/javascript">
$(document).on("click", ".close", function() {$("#estimation-btn").html("실행");});	
$(document).on("click", "#estimation-btn", function() {fn_compEstimation()});
$(document).ready(function() { 
	//리스크 선택창 변경 시 항목 리스트 변경
	$("#fieldList").change(function() {
		$("#fieldName").html($("#fieldList option:selected").text());
		fn_itemList();
	});
	
	//기후모델 변경 시 RCP변경
	$("#scenModelList").change(function() {
		fn_sectionList()
	});
	
	//RCP 변경 시 취약성평가 진행
	$("#scenSectionList").change(function() {fn_yearList()});
	
	<c:if test="${author eq 'A'}">
	//시도 목록 변경 시 시군구 목록 불러오기
	$("#sidoList").change(function(){
		fn_sigunguList();
	});
	
	$("#sigunguList").change(function(){
		fn_sigunguListChange();
	});
	</c:if>
	<c:if test="${author ne 'W'}">
	//시도 목록 변경 시 시군구 목록 불러오기
	$("#sidoCList").change(function(){
		fn_sigunguCList();
	});
	</c:if>
	<c:if test="${author eq 'B'}">
		moveSigungu("one");//기준지역 
		setTimeout(function(){
			moveSigungu("three");//비교지역
		},500);
	</c:if>
	<c:if test="${author ne 'B'}">
		moveSido("one");//기준지역 
		setTimeout(function(){
			moveSido("three");//비교지역
		},500);
	</c:if>
	
});

function fn_resultHideView(){
	$(".resultView").hide();
}

function fn_resultShowView(){
	$(".resultView").show();
}

<c:choose>
<c:when test="${author ne 'A'}">
function fn_compEstimation(){
	if(fn_checkOption()){
	fn_estimationComparison();
	updateMap();
	}
}
</c:when>
<c:otherwise>
function fn_compEstimation(){
	if(fn_checkOption()){
	if(fn_chkDistrict()){
		fn_estimationComparison();
	}else{
		
		fn_alert("경고", "기준 지역과 비교 지역이 동일하여 진행할 수 없습니다.", "warning");
	}
	}
}
</c:otherwise>
</c:choose>

function fn_checkOption(){
	var item = $("#selectItem").val();
	if(item=='TL000054'){
		fn_alert("경고", "-전국 표출 전용-해당 항목은 비교분석을 진행할 수 없습니다.", "warning");
		return false;
	}
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

function fn_resultViewSetting(){
	//기본정보 창 세팅
	$("._field").text($("#fieldList option:selected").text());
	$("._item").text($("#itemList a.active").text());
	$("._model").text($("#scenModelList option:selected").text());
	$("._section").text($("#scenSectionList option:selected").text());
	$("._year").text($("#scenYearList option:selected").text());
	$("._sido").text($("#sidoList option:selected").text());
	$("._sigungu").text($("#sigunguList option:selected").text());
	$("._sidoC").text($("#sidoCList option:selected").text());
	$("._sigunguC").text($("#sigunguCList option:selected").text());
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
		 url:'/member/base/climate/comparison/itemList.do',
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
		 url:'/member/base/climate/comparison/sectionList.do',
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
		 url:'/member/base/climate/comparison/yearList.do',
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
<c:if test="${author eq 'A'}">
function fn_sigunguListChange(){
	var sigunguCode = $("#sigunguList option:selected").val();
	var sidoCode = $("#sidoCList option:selected").val();
	var dataSet = {"sidoCode" : sidoCode};
	if(sigunguCode!=null && sigunguCode!=''){
		$.ajax({
			url:'/member/base/climate/comparison/sigunguList.do',
			dataType:'json',
			type:'get',
			data:dataSet,
			success:function(result){
				$("#sigunguCList").empty();
				var list = result.list;
				for(var i in list){
					if(sidoCode != list[i].districtCd){
					var html = "<option value='"+list[i].districtCd+"'>"+list[i].districtNm+"</option>";
					$("#sigunguCList").append(html);
					}
				}
			}
		});
	}else{
		$("#sigunguCList").empty();
		$("#sigunguCList").append("<option value='' selected>전체</option>");
	}
}

function fn_sigunguList(){
	var sidoCode = $("#sidoList option:selected").val();
	var dataSet = {"sidoCode" : sidoCode};
	$.ajax({
		url:'/member/base/climate/comparison/sigunguList.do',
		dataType:'json',
		type:'get',
		data:dataSet,
		success:function(result){
			$("#sigunguList").empty();
			var list = result.list;
			<c:if test="${author eq 'A'}">
			$("#sigunguList").append("<option value='' selected>전체</option>");
			</c:if>
			for(var i in list){
				var html = "<option value='"+list[i].districtCd+"'>"+list[i].districtNm+"</option>";
				$("#sigunguList").append(html);
			}
			fn_sigunguCList();
		}
	});
}

function fn_sigunguCList(){
	var sidoCode = $("#sidoCList option:selected").val();
	var dataSet = {"sidoCode" : sidoCode};
	var sigunguCode = $("#sigunguList option:selected").val();
	if(sigunguCode!=null && sigunguCode!=''){
		$.ajax({
			url:'/member/base/climate/comparison/sigunguList.do',
			dataType:'json',
			type:'get',
			data:dataSet,
			success:function(result){
				$("#sigunguCList").empty();
				var list = result.list;
				for(var i in list){
					var html = "<option value='"+list[i].districtCd+"'>"+list[i].districtNm+"</option>";
					$("#sigunguCList").append(html);
				}
			}
		});
	}else{
		$("#sigunguCList").empty();
		$("#sigunguCList").append("<option value='' selected>전체</option>");
	}
}

function fn_chkDistrict(){
	var sido = $("#sidoList option:selected").val();
	var sidoC = $("#sidoCList option:selected").val();
	var sigungu = $("#sigunguList option:selected").val();
	var sigunguC = $("#sigunguCList option:selected").val();
	if(sido == sidoC && sigungu == sigunguC){
		return false;
	}
	return true;
}

</c:if>


<c:if test="${author ne 'A'}">

function fn_sigunguList(){
	var sidoCode = $("#sidoList option:selected").val();
	var dataSet = {"sidoCode" : sidoCode};
	$.ajax({
		url:'/member/base/climate/comparison/sigunguList.do',
		dataType:'json',
		type:'get',
		data:dataSet,
		success:function(result){
			$("#sigunguList").empty();
			var list = result.list;
			<c:if test="${author eq 'A'}">
			$("#sigunguList").append("<option value='' selected>전체</option>");
			</c:if>
			for(var i in list){
				var html = "<option value='"+list[i].districtCd+"'>"+list[i].districtNm+"</option>";
				$("#sigunguList").append(html);
			}
		}
	});
}

function fn_sigunguCList(){
	var sidoCode = $("#sidoCList option:selected").val();
	var dataSet = {"sidoCode" : sidoCode};
	$.ajax({
		url:'/member/base/climate/comparison/sigunguList.do',
		dataType:'json',
		type:'get',
		data:dataSet,
		success:function(result){
			$("#sigunguCList").empty();
			var list = result.list;
			<c:if test="${author eq 'A'}">
			$("#sigunguCList").append("<option value='' selected>전체</option>");
			</c:if>
			for(var i in list){
				var html = "<option value='"+list[i].districtCd+"'>"+list[i].districtNm+"</option>";
				$("#sigunguCList").append(html);
			}
		}
	});
}
</c:if>

//취약성평가
<c:choose>
<c:when test="${author eq 'B'}">
function fn_estimationComparison(){
	var field = $("#fieldList option:selected").val();
	var item = $("#selectItem").val();
	var model = $("#scenModelList option:selected").val();
	var section = $("#scenSectionList option:selected").val();
	var year = $("#scenYearList option:selected").val();
	var sido = $("#sidoList option:selected").val();
	var sigungu = $("#sigunguList option:selected").val();
	var sidoC = $("#sidoCList option:selected").val();
	var sigunguC = $("#sigunguCList option:selected").val();
	var url = '';
	var dataSet = {
			"field" : field
			,"item" : item
			,"model" : model
			,"section" : section
			,"year" : year
			,"sido" : sido
			,"sigungu" : sigungu
			,"sidoC" : sidoC
			,"sigunguC" : sigunguC
	};
	var page = 0;
	var pageLimit = 5;
	//취약성평가 기초 비교
 	if(item!=null && item!=''
			&& model!=null  && model!='' 
			&& section!=null  && section!='' 
			&& year!=null  && year!='' 
			&& sido!=null  && sido!='' 
			&& sidoC!=null  && sidoC!='' 
			&& sigungu!=null  && sigungu!='' 
			&& sigunguC!=null && sigunguC!=''){
		url = '/member/base/climate/comparison/resultSgg.do';
		$.ajax({
			url:url,
			dataType:'json',
			type:'get',
			data:dataSet,
			beforeSend: function(){$(".display").css("display","block");},
			complete: function(){
				$(".display").css("display","none");	
				moveSigungu("one");//기준지역 
				setTimeout(function(){
					moveSigungu("three");//비교지역
				},500);
				},
			success:function(result){
				var list = result.list;
				$("#resultTotal").empty();
				
				var dataA = [],firstDataA=false;
				var dataB = [],firstDataB=false;
				var dataC = [],firstDataC=false;
				var base_val = new Array();
				var comp_val = new Array();
				var min = new Array();
				var max = new Array();

				//기본정보 창 세팅
				fn_resultViewSetting();
				fn_resultShowView();

				//기초 종합지수창
				for(var i in list){
					var districtNm = list[i].sdNm+' '+list[i].sggNm;
					var dataType = '';
					var districtCd = list[i].districtCd; 
					(sigungu == districtCd)?dataType='A':(districtCd!='')?dataType='B':dataType='C';
					if(!firstDataA && dataType=='A'){
						dataA.push(dataType);
						dataA.push(districtNm);
						dataA.push(list[i].climValue);
						dataA.push(list[i].sensValue);
						dataA.push(list[i].adapValue);
						firstDataA = true;
					}
					if(!firstDataB && dataType=='B'){
						dataB.push(dataType);
						dataB.push(districtNm);
						dataB.push(list[i].climValue);
						dataB.push(list[i].sensValue);
						dataB.push(list[i].adapValue);
						firstDataB = true;
					}
					if(!firstDataC && dataType=='C'){
						dataC.push(dataType);
						dataC.push(districtNm);
						dataC.push(list[i].climValue);
						dataC.push(list[i].sensValue);
						dataC.push(list[i].adapValue);
						firstDataC = true;
					}
					var html ='';
					if(i%pageLimit==0) page++;
					html = '<tr class="pg'+page+'"';
					if(page==1){
						html+=' style="display:table-row">';
					}else{
						html+=' style="display:none">';
					}
					html+='<td>'+districtNm+'</td><td>'+list[i].estiValue+'</td><td>'+list[i].climValue+'</td><td>'+list[i].sensValue+'</td><td>'+list[i].adapValue+'</td></tr>';
					$("#resultTotal").append(html);
					
					if(list[i].minValue!=null){
						console.log(districtNm+" / min: "+list[i].minValue+" / max : "+list[i].maxValue);
						min.push(list[i].minValue);
						max.push(list[i].maxValue);
					}
					if(sido == districtCd){
						base_val.push(districtCd, list[i].estiValue);
					}else if(sidoC == districtCd){
						comp_val.push(districtCd, list[i].estiValue);
					}
					else if(sigungu == districtCd ){
						base_val.push(districtCd, list[i].estiValue);
					}else if(sigunguC == districtCd){
						comp_val.push(districtCd, list[i].estiValue);
					}
				}
				
				offcanvasOpen();
				$("#estimation-btn").html($('<i class="icon-arrow-caret-right" style="font-size: 16pt;"></i>'));
				
				fn_barRedraw_init(dataA);
				fn_barRedraw_init(dataB);
				fn_barRedraw_init(dataC);
				addVectorComparison(base_val,comp_val, min, max);
			}
		});
	}
}
</c:when>
<c:when test="${author eq 'W'}">
function fn_estimationComparison(){
	var field = $("#fieldList option:selected").val();
	var item = $("#selectItem").val();
	var model = $("#scenModelList option:selected").val();
	var section = $("#scenSectionList option:selected").val();
	var year = $("#scenYearList option:selected").val();
	var sido = $("#sidoList option:selected").val();
	var sidoC = $("#sidoCList option:selected").val();
	var url = '';
	var dataSet = {
			"field" : field
			,"item" : item
			,"model" : model
			,"section" : section
			,"year" : year
			,"sido" : sido
			,"sidoC" : sidoC
	};
	var page = 0;
	var pageLimit = 5;
	//취약성평가 기초 비교
 	if(item!=null && item!=''
			&& model!=null  && model!='' 
			&& section!=null  && section!='' 
			&& year!=null  && year!='' 
			&& sido!=null  && sido!='' 
			&& sidoC!=null  && sidoC!='' ){
		url = '/member/base/climate/comparison/resultSd.do';
		$.ajax({
			url:url,
			dataType:'json',
			type:'get',
			data:dataSet,
			beforeSend: function(){$(".display").css("display","block");},
			complete: function(){$(".display").css("display","none");
				moveSido("one");//기준지역 
				setTimeout(function(){
				moveSido("three");//비교지역
				},500);
			},
			success:function(result){
				var list = result.list;
				$("#resultTotal").empty();

				var dataA = [],firstDataA=false;
				var dataB = [],firstDataB=false;
				var dataC = [],firstDataC=false;
				var base_val = new Array();
				var comp_val = new Array();
				var min = new Array();
				var max = new Array();

				//기본정보 창 세팅
				fn_resultViewSetting();
				fn_resultShowView();
				
				//기초 종합지수창
				for(var i in list){
					var districtNm = list[i].sdNm;
					var dataType = '';
					var districtCd = list[i].districtCd; 
					(sido == districtCd)?dataType='A':(districtCd!='')?dataType='B':dataType='C';
					if(!firstDataA && dataType=='A'){
						dataA.push(dataType);
						dataA.push(districtNm);
						dataA.push(list[i].climValue);
						dataA.push(list[i].sensValue);
						dataA.push(list[i].adapValue);
						firstDataA = true;
					}
					if(!firstDataB && dataType=='B'){
						dataB.push(dataType);
						dataB.push(districtNm);
						dataB.push(list[i].climValue);
						dataB.push(list[i].sensValue);
						dataB.push(list[i].adapValue);
						firstDataB = true;
					}
					if(!firstDataC && dataType=='C'){
						dataC.push(dataType);
						dataC.push(districtNm);
						dataC.push(list[i].climValue);
						dataC.push(list[i].sensValue);
						dataC.push(list[i].adapValue);
						firstDataC = true;
					}
					var html ='';
					if(i%pageLimit==0) page++;
					html = '<tr class="pg'+page+'"';
					if(page==1){
						html+=' style="display:table-row">';
					}else{
						html+=' style="display:none">';
					}
					html+='<td>'+districtNm+'</td><td>'+list[i].estiValue+'</td><td>'+list[i].climValue+'</td><td>'+list[i].sensValue+'</td><td>'+list[i].adapValue+'</td></tr>';
					$("#resultTotal").append(html);

					if(list[i].minValue!=null){
						console.log(districtNm+" / min: "+list[i].minValue+" / max : "+list[i].maxValue);
						min.push(list[i].minValue);
						max.push(list[i].maxValue);
					}
					
					if(sido == districtCd){
						base_val.push(districtCd, list[i].estiValue);
					}else if(sidoC == districtCd){
						comp_val.push(districtCd, list[i].estiValue);
					}
					
				}
				
				offcanvasOpen();
				$("#estimation-btn").html($('<i class="icon-arrow-caret-right" style="font-size: 16pt;"></i>'));
				
				fn_barRedraw_init(dataA);
				fn_barRedraw_init(dataB);
				fn_barRedraw_init(dataC);
				addVectorComparison(base_val,comp_val, min, max);
			}
		});
	}
}
</c:when>
<c:otherwise>
function fn_estimationComparison(){
	var field = $("#fieldList option:selected").val();
	var item = $("#selectItem").val();
	var model = $("#scenModelList option:selected").val();
	var section = $("#scenSectionList option:selected").val();
	var year = $("#scenYearList option:selected").val();
	var sido = $("#sidoList option:selected").val();
	var sigungu = $("#sigunguList option:selected").val();
	var sidoC = $("#sidoCList option:selected").val();
	var sigunguC = $("#sigunguCList option:selected").val();
	var ajaxType = '';
	var url = '';
	var dataSet = {
			"field" : field
			,"item" : item
			,"model" : model
			,"section" : section
			,"year" : year
			,"sido" : sido
			,"sigungu" : sigungu
			,"sidoC" : sidoC
			,"sigunguC" : sigunguC
	};
	var page = 0;
	var pageLimit = 5;
	//취약성평가 기초 비교
 	if(item!=null && item!=''
			&& model!=null  && model!='' 
			&& section!=null  && section!='' 
			&& year!=null  && year!='' 
			&& sido!=null  && sido!='' 
			&& sidoC!=null  && sidoC!='' ){
 		
 			if(sigungu!=null  && sigungu!='' 
 				&& sigunguC!=null && sigunguC!=''){
 				//시군구 세팅
 				ajaxType = 'SGG';
 				url = '/member/base/climate/comparison/resultSgg.do';
 			}else{
 				//시도 세팅
 				ajaxType = 'SD';
 				url = '/member/base/climate/comparison/resultSd.do';
 			}
		$.ajax({
			url:url,
			dataType:'json',
			type:'get',
			data:dataSet,
			beforeSend: function(){$(".display").css("display","block");},
			complete: function(){$(".display").css("display","none");
			if(ajaxType=='SGG'){
				moveSigungu("one");//기준지역 
				setTimeout(function(){
					moveSigungu("three");//비교지역
				},500);
			}else{
				moveSido("one");//기준지역 
				setTimeout(function(){
				moveSido("three");//비교지역
				},500);
			}
			},
			success:function(result){
				var list = result.list;
				$("#resultTotal").empty();

				var dataA = [],firstDataA=false;
				var dataB = [],firstDataB=false;
				var dataC = [],firstDataC=false;
				var base_val = new Array();
				var comp_val = new Array();
				var min = new Array();
				var max = new Array();


				//기본정보 창 세팅
				fn_resultViewSetting();
				fn_resultShowView();
				
				//기초 종합지수창
				for(var i in list){
					var districtNm ='';
					var distCd = '';
					if(ajaxType==='SGG'){
						districtNm = list[i].sdNm+' '+list[i].sggNm;
						distCd = sigungu;
					}else{
						districtNm = list[i].sdNm;
						distCd = sido;
					}
					var dataType = '';
					var districtCd = list[i].districtCd; 
					(distCd == districtCd)?dataType='A':(districtCd!='')?dataType='B':dataType='C';
					if(!firstDataA && dataType=='A'){
						dataA.push(dataType);
						dataA.push(districtNm);
						dataA.push(list[i].climValue);
						dataA.push(list[i].sensValue);
						dataA.push(list[i].adapValue);
						firstDataA = true;
					}
					if(!firstDataB && dataType=='B'){
						dataB.push(dataType);
						dataB.push(districtNm);
						dataB.push(list[i].climValue);
						dataB.push(list[i].sensValue);
						dataB.push(list[i].adapValue);
						firstDataB = true;
					}
					if(!firstDataC && dataType=='C'){
						dataC.push(dataType);
						dataC.push(districtNm);
						dataC.push(list[i].climValue);
						dataC.push(list[i].sensValue);
						dataC.push(list[i].adapValue);
						firstDataC = true;
					}
					var html ='';
					if(i%pageLimit==0) page++;
					html = '<tr class="pg'+page+'"';
					if(page==1){
						html+=' style="display:table-row">';
					}else{
						html+=' style="display:none">';
					}
					html+='<td>'+districtNm+'</td><td>'+list[i].estiValue+'</td><td>'+list[i].climValue+'</td><td>'+list[i].sensValue+'</td><td>'+list[i].adapValue+'</td></tr>';
					$("#resultTotal").append(html);
					
					
					if(list[i].minValue!=null){
						console.log(districtNm+" / min: "+list[i].minValue+" / max : "+list[i].maxValue);
						min.push(list[i].minValue);
						max.push(list[i].maxValue);
					}
					if(sido == districtCd){
						base_val.push(districtCd, list[i].estiValue);
					}else if(sidoC == districtCd){
						comp_val.push(districtCd, list[i].estiValue);
					}
					else if(sigungu == districtCd ){
						base_val.push(districtCd, list[i].estiValue);
					}else if(sigunguC == districtCd){
						comp_val.push(districtCd, list[i].estiValue);
					}
				}
				
				offcanvasOpen();
				$("#estimation-btn").html($('<i class="icon-arrow-caret-right" style="font-size: 16pt;"></i>'));
				
				fn_barRedraw_init(dataA);
				fn_barRedraw_init(dataB);
				fn_barRedraw_init(dataC);
				addVectorComparison(base_val,comp_val, min, max);
			}
		});
	}
}
</c:otherwise>
</c:choose>

function fn_barRedraw_init(data){
	var dataType = data[0];
	var distNm = data[1];
	var val1 = data[2];
	var val2 = data[3];
	var val3 = data[4];
	if(dataType=='A'){
		barChart.data.names({ data1 : distNm });
		barChart.load({  columns: [["data1", val1, val2, val3]] });
	}else if(dataType=='B'){
		barChart.data.names({ data2 : distNm });
		barChart.load({  columns: [["data2", val1, val2, val3]] });
	}else{
		barChart.data.names({ data3 : distNm });
		barChart.load({  columns: [["data3", val1, val2, val3]] });
	}
}

function fn_previousPage(id){
	var page = $("#"+id+"Page").val();
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
	var page = $("#"+id+"Page").val();
	var limitPage =$("#"+id+"Limit").val();
	if(page<limitPage){
	$("#"+id+" .pg"+page).css("display","none");
	page++;
	$("#"+id+" .pg"+page).css("display","table-row");
	}
	$("#"+id+"Page").val(page);
	$("#"+id+"CurPage").text('');
	$("#"+id+"CurPage").text(page);
}
</script>
<script src="/resources/billboard/d3.v5.min.js"></script>
<script src="/resources/billboard/billboard.js" type="text/javascript"></script>
<script type="text/javascript">
var barChart = bb.generate({
	data: { 
		colors: { 
			data1: "#007bff", 
			data2: "#fd7e14",
			data3: "#20c997"
		},
		names:{
			data1: "",
			data2: "",
			data3: ""
		},
	    columns: [ //기후노출 민감도 적응능력
			["data1", 0.2, 0.3, 0.4],
			["data2", 0.5, 0.4, 0.1],
			["data3", 0.0, 0.0, 0.0]
	    ],
	    type: "bar"
	  },
	  bar: {
	    width: {
	      ratio: 0.5
	    }
	  },
	  axis: {
		    x: {
		      type: "category",
		      categories: [
		        "기후노출",
		        "민감도",
		        "적응능력"			       
		      ]
		    },
	  },
	  bindto: "#BarChart"
});
</script>
<script src="/resources/openlayers/ol.js" type="text/javascript"></script>
<script type="text/javascript"
	src="/resources/openlayers/FileSaver.min.js"></script>
<script type="text/javascript" src="/resources/openlayers/proj4.js"></script>
<script type="text/javascript" src="/resources/openlayers/map.js"></script>
</body>
</html>