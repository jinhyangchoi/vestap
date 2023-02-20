<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP 취약성평가 결과 보고서</title>
<link href="/resources/css/loading/loading.css" rel="stylesheet">
<link href="/resources/billboard/billboard.css" rel="stylesheet">
<link href="/resources/billboard/insight.css" rel="stylesheet">
<link href="/resources/openlayers/ol.css" rel="stylesheet">
<style type="text/css">
.container{background-color: white;margin-top: 20px; width: 1200px;  max-width: none !important;}
.contents{width:100%;padding:60px 45px;}
.page{min-height: 1720px;}
.page-last{}
</style>
</head>
<body>
<div class="display">
<div class="loading">
<div class="lds-css ng-scope">
<div class="lds-spinner" style="100%;height:100%"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
</div>
</div>
</div>
<div class="container">
	<input class="btn btn-primary float-right" type="button" value="인쇄하기" onclick="fn_print()" />
	<div class="contents">
		<!-- page-1 -->
		<div class="page">
		<span class="h4 mb-3" style="font-weight: bold;">VESTAP 취약성평가 결과 보고서</span>
		<div class="card mt-3">
			<div class="card-header">
				<div class="row">
					<div class="col-12">
						<b style="font-size: large;">[${info.sidoNm}&nbsp;${info.sigunguNm}의&nbsp;${info.itemNm}&nbsp;평가 도출내역]</b>
					</div>
					<div class="col-6" style="font-size: 11pt;">
						<br>
						- 평가지역 :&nbsp;${info.sidoNm}<!-- 시도 -->
						&nbsp;${info.sigunguNm}<!-- 시군구 -->
						<br><br>
						- 평가항목 : ${info.itemNm}<!-- 평가항목 -->
					</div>
					<div class="col-6" style="font-size: 11pt;">
						<br>
						- 평가 리스크 : ${info.fieldNm}<!-- 리스크 -->
						<br><br>
						- 적용 기후모델 :
						&nbsp;${info.modelNm}<!-- 기후모델 -->
						&nbsp;${info.sectionNm}<!-- 시나리오 -->
						&nbsp;${info.yearNm}<!-- 연대 -->
					</div>
				</div>
			</div>
		</div>
		<br>
		<span class="h5 mb-3">· 취약성평가 종합지수</span>
		<div class="row mt-3" >
			<div class="col-7">
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
			</div>
			<div class="col-5">
				<div class="boxArea text-center p-3 mb-3" style="height: 350px">
					<div id="RadarChart"></div>
				</div>
			</div>
		</div>	
		</div>
		<hr>
		<!-- page-2 -->
		<div class="page">
		<span class="h5 mt-3">· 취약성평가 결과 지도</span>
		<div class="card mt-3">
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
		</div><!-- /.card -->
		</div>
		<hr>
		<!-- page-3 -->
		<div class="page">
		<div class="row">
			<div class="col-12">
				<br>
				<span class="h5 mb-3">· 산출식</span>
				<br>
				<div class="boxArea p-3 mt-3 mb-3" style="font-size: 11pt; font-weight: bold" >
					<br>
					<p>${info.itemNm }&nbsp;=</p>
					<br>
					<p style="text-align: center;">
						(기후노출 지수 X <span class="_clim"></span>) + (민감도 지수 X <span class="_sens"></span>) - (적응능력 지수 X <span class="_adap"></span>)</p>
						<hr>
					<p style="text-align: center;">1</p>
					<br>
				</div>
			</div>
			<div class="col-12">
				<br>
				<span class="h5 mb-3">· 구축형태</span>
				<table class="table  vestapTable smTable text-center mt-3">
					<tbody>
						<tr>
							<td><b style="font-size: large;">A</b><br>읍면동 통계<br>원시 자료</td>
							<td><b style="font-size: large;">B</b><br>시군구 통계<br>원시 자료</td>
							<td><b style="font-size: large;">C</b><br>시도 통계<br>원시 자료</td>
							<td></td>
						</tr>
						<tr>
							<td><b style="font-size: large;">A`</b><br>시군구 통계<br>가공 자료</td>
							<td><b style="font-size: large;">B`</b><br>시도 통계<br>가공 자료</td>
							<td><b style="font-size: large;">D</b><br>복합/기타<br>자료</td>
							<td><b style="font-size: large;">E</b><br>기상/기후<br>원시 자료</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
			<div class="row">
				<div class="col-12">
				<p class="h5 mt-2" style="line-height: 36px;">· 산출에 사용된 기초자료</p>
				</div>
				<div class="col-4">
					<p class="h6 text-center" style="line-height: 36px;">기후노출 부문</p>
					<table class="table vestapTable smTable text-center">
						<thead>
							<tr>
								<th>지표명</th>
								<th style="width:80px">구축형태</th>
								<th style="width:50px">가중치</th>
							</tr>
						</thead>
						<tbody id="resultDetailCE">
						</tbody>
					</table>
				</div>
				<div class="col-4">
					<p class="h6 text-center" style="line-height: 36px;">기후변화 민감도 부문</p>
					<table class="table vestapTable smTable text-center">
						<thead>
							<tr>
								<th>지표명</th>
								<th style="width:80px">구축형태</th>
								<th style="width:50px">가중치</th>
							</tr>
						</thead>
						<tbody id="resultDetailCS">
						</tbody>
					</table>
				</div>
				<div class="col-4">
					<p class="h6 text-center" style="line-height: 36px;">적응능력 부문</p>
					<table class="table vestapTable smTable text-center">
						<thead>
							<tr>
								<th>지표명</th>
								<th style="width:80px">구축형태</th>
								<th style="width:50px">가중치</th>
							</tr>
						</thead>
						<tbody id="resultDetailAA">
						</tbody>
					</table>
				</div>
			</div>
			</div>
			<hr>
			<!-- page-4 -->
			<div class="page">
			<div class="row">
				<div class="col-12">
				<p class="h5 mt-2" style="line-height: 36px;">· 산출에 사용된 기초자료 [기후노출 부문]</p>
				</div>
			</div>
			<br>
			<table class="table vestapTable smTable text-center">
				<thead id="resultIndiHeaderCE">
					<tr>
						<th style="width:100px">행정구역 명칭</th>
					</tr>
				</thead>
				<tbody id="resultIndiBodyCE">
				</tbody>
			</table>
			</div>
			<hr>
			<!-- page-5 -->
			<div class="page">
			<div class="row">
				<div class="col-12">
				<p class="h5 mt-2" style="line-height: 36px;">· 산출에 사용된 기초자료 [기후변화 민감도 부문]</p>
				</div>
			</div>
			<br>
			<table class="table vestapTable smTable text-center">
				<thead id="resultIndiHeaderCS">
					<tr>
						<th style="width:100px">행정구역 명칭</th>
					</tr>
				</thead>
				<tbody id="resultIndiBodyCS">
				</tbody>
			</table>
			</div>
			<hr>
			<!-- page-6 -->
			<div class="page-last">
			<div class="row">
				<div class="col-12">
				<p class="h5 mt-2" style="line-height: 36px;">· 산출에 사용된 기초자료 [적응능력 부문]</p>
				</div>
			</div>
			<br>
			<table class="table vestapTable smTable text-center">
				<thead id="resultIndiHeaderAA">
					<tr>
						<th style="width:100px">행정구역 명칭</th>
					</tr>
				</thead>
				<tbody id="resultIndiBodyAA">
				</tbody>
			</table>
		</div>
	</div>
</div>
<input type="hidden" id="depth" value="${viewName.depth1}-${viewName.depth2}-${viewName.depth3}">
<input type="hidden" id="sido" value="${param.sido}">
<input type="hidden" id="sigungu" value="${param.sigungu}">
<script type="text/javascript">
$(document).ready(function() {
	fn_estimation('do');
});

function fn_estimation(exe){

	var item = '${param.item}';
	
	if(item=='TL000054'){
		fn_estimationWhole(exe);
		fn_estimationDetail();
		fn_indiDataWhole();
	}else{
		fn_estimationTotal(exe);
		fn_estimationDetail();
		fn_indiData();
	}
	
}

function fn_reloadMap(){
	moveSigungu("one");
	updateMap();
} 

//종합지수
function fn_estimationTotal(exe){
	var field = '${param.field}';
	var item = '${param.item}';
	var model = '${param.model}';
	var section = '${param.section}';
	var year = '${param.year}';
	var sido = '${param.sido}';
	var sigungu = '${param.sigungu}';
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
		complete: function(){$(".display").css("display","none");
		fn_reloadMap();$('#map-legend').show();
		},
		success:function(result){
			$("#resultTotal").empty();
			
			var list = result.list;
			var layer_val = new Array();
			var layer_cd = new Array();
			
			if(sigungu!='' && sigungu!=null){
				/*기초 종합지수창*/
				for(var i in list){
					var html ='';
					if(i==0){
						fn_radarRedraw(list[i].emdNm,list[i].climValue,list[i].sensValue,list[i].adapValue);
					}
					html = '<tr onclick="fn_radarRedraw(\''+list[i].emdNm+'\','+list[i].climValue+','+list[i].sensValue+','+list[i].adapValue+')">';
					html+= '<td>'+list[i].no+'</td><td>'+list[i].emdNm+'</td><td>'+list[i].estiValue+'</td><td>'+list[i].climValue+'</td><td>'+list[i].sensValue+'</td><td>'+list[i].adapValue+'</td><td><i class="icon-graph-pie i-24"></i></td></tr>';
					$("#resultTotal").append(html);
					layer_val.push(list[i].estiValue);
					layer_cd.push(list[i].districtCd);
				}
			}else{
				/*광역 종합지수창*/
				for(var i in list){
					var html ='';
					if(i==0){
						fn_radarRedraw(list[i].sggNm,list[i].climValue,list[i].sensValue,list[i].adapValue);
					}
					html = '<tr onclick="fn_radarRedraw(\''+list[i].sggNm+'\','+list[i].climValue+','+list[i].sensValue+','+list[i].adapValue+')">';
					html+= '<td>'+list[i].no+'</td><td>'+list[i].sggNm+'</td><td>'+list[i].estiValue+'</td><td>'+list[i].climValue+'</td><td>'+list[i].sensValue+'</td><td>'+list[i].adapValue+'</td><td><i class="icon-graph-pie i-24"></i></td></tr>';
					$("#resultTotal").append(html);
					layer_val.push(list[i].estiValue);
					layer_cd.push(list[i].districtCd);
				}
			}
			//changeVectorLayer(layer_cd,layer_val);
			addVector(layer_cd,layer_val);
			fn_mapLegend();
		}
	});
}


function fn_estimationWhole(exe){
	var field = '${param.field}';
	var item = '${param.item}';
	var model = '${param.model}';
	var section = '${param.section}';
	var year = '${param.year}';
	var url = '';
	var dataSet = {
			"field" : field
			,"item" : item
			,"model" : model
			,"section" : section
			,"year" : year
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
			
			var list = result.list;
			var layer_val = new Array();
			var layer_cd = new Array();

			/*광역 종합지수창*/
			for(var i in list){
				var html ='';
				if(i==0){
					fn_radarRedraw(list[i].sggNm,list[i].climValue,list[i].sensValue,list[i].adapValue);
				}
				html = '<tr onclick="fn_radarRedraw(\''+list[i].sggNm+'\','+list[i].climValue+','+list[i].sensValue+','+list[i].adapValue+')">';
				html+= '<td>'+list[i].no+'</td><td>'+list[i].sggNm+'</td><td>'+list[i].estiValue+'</td><td>'+list[i].climValue+'</td><td>'+list[i].sensValue+'</td><td>'+list[i].adapValue+'</td><td><i class="icon-graph-pie i-24"></i></td></tr>';
				$("#resultTotal").append(html);
				layer_val.push(list[i].estiValue);
				layer_cd.push(list[i].districtCd);
			}
			addVector(layer_cd,layer_val);
			fn_mapLegend();
		}
	});
}

function fn_mapLegend(){
	$('#map-legend').show();
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
	var item = '${param.item}';
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
			$('._clim').text(info.ceWeight);	
			$('._sens').text(info.csWeight);	
			$('._adap').text(info.aaWeight);			
			for(var i in list){
				var tag = null;
				if(list[i].sectorId == 'SEC01'){
					tag = $("#resultDetailCE");
				}else if(list[i].sectorId == 'SEC02'){
					tag = $("#resultDetailCS");
				}else if(list[i].sectorId == 'SEC03'){
					tag = $("#resultDetailAA");
				}
				tag.append('<tr class="'+list[i].sectorId+'"><td>'+list[i].indiNm+'</td><td><b>'+list[i].indiConstSign+'</b></td><td>'+list[i].indiValWeight+'</td></tr>');
				$("#indiList").append('<option class="'+list[i].sectorId+'" value="'+list[i].indiId+'" >'+list[i].indiNm+'</option>');
			}
		}		
	});
}

//기초자료 정보
function fn_indiData(){
	var item = '${param.item}';
	var model = '${param.model}';
	var section = '${param.section}';
	var year = '${param.year}';
	var sido = '${param.sido}';
	var sigungu = '${param.sigungu}';
	var dataSet = {
			"item" : item
			,"model" : model
			,"section" : section
			,"year" : year
			,"sido" : sido
			,"sigungu" : sigungu
	};
	if(sigungu!='' && sigungu!=null){
		url = '/member/base/climate/estimation/selectEmdIndiData.do';
	}else{
		url = '/member/base/climate/estimation/selectSggIndiData.do';
	}
	$.ajax({
		url:url,
		dataType:'json',
		type:'get',
		data:dataSet,
		success:function(result){
			$("#resultIndiInfo").empty();
			var header = result.header;
			var body = result.body;
			if(sigungu!='' && sigungu!=null){
				for(var i in header){
					var tag = null;
					if(header[i].sectorId == 'SEC01'){
						tag = $("#resultIndiHeaderCE>tr");
					}else if(header[i].sectorId == 'SEC02'){
						tag = $("#resultIndiHeaderCS>tr");
					}else if(header[i].sectorId == 'SEC03'){
						tag = $("#resultIndiHeaderAA>tr");
					}
					tag.append('<th data="'+header[i].indiId+'">'+header[i].indiNm+'('+header[i].indiUnit+')</th>');
				}
				for(var i in body){
					if(i<body.length/header.length){
					var tagb = null;
						tagb = $("#resultIndiBodyCE");
						tagb.append('<tr class="tr_'+(i*1+1)+'"><td>'+body[i].emdNm+'</td></tr>');
						tagb = $("#resultIndiBodyCS");
						tagb.append('<tr class="tr_'+(i*1+1)+'"><td>'+body[i].emdNm+'</td></tr>');
						tagb = $("#resultIndiBodyAA");
						tagb.append('<tr class="tr_'+(i*1+1)+'"><td>'+body[i].emdNm+'</td></tr>');
					}
				}
				for(var i in header){
					var rowCE=1;
					var rowCS=1;
					var rowAA=1;
					for(var j in body){
						var tagb = null;
						if(header[i].sectorId == 'SEC01' && header[i].indiId == body[j].indiId){
							tagb = $("#resultIndiBodyCE .tr_"+rowCE);
							tagb.append('<td>'+body[j].indiVal+'</td>');
							rowCE++;
						}else if(header[i].sectorId == 'SEC02' && header[i].indiId == body[j].indiId){
							tagb = $("#resultIndiBodyCS .tr_"+rowCS);
							tagb.append('<td>'+body[j].indiVal+'</td>');
							rowCS++;
						}else if(header[i].sectorId == 'SEC03' && header[i].indiId == body[j].indiId){
							tagb = $("#resultIndiBodyAA .tr_"+rowAA);
							tagb.append('<td>'+body[j].indiVal+'</td>');
							rowAA++;
						}
					}
				}
			}else{
				for(var i in header){
					var tag = null;
					if(header[i].sectorId == 'SEC01'){
						tag = $("#resultIndiHeaderCE>tr");
					}else if(header[i].sectorId == 'SEC02'){
						tag = $("#resultIndiHeaderCS>tr");
					}else if(header[i].sectorId == 'SEC03'){
						tag = $("#resultIndiHeaderAA>tr");
					}
					tag.append('<th data="'+header[i].indiId+'">'+header[i].indiNm+'('+header[i].indiUnit+')</th>');
				}
				for(var i in body){
					if(i<body.length/header.length){
					var tagb = null;
						tagb = $("#resultIndiBodyCE");
						tagb.append('<tr class="tr_'+(i*1+1)+'"><td>'+body[i].sggNm+'</td></tr>');
						tagb = $("#resultIndiBodyCS");
						tagb.append('<tr class="tr_'+(i*1+1)+'"><td>'+body[i].sggNm+'</td></tr>');
						tagb = $("#resultIndiBodyAA");
						tagb.append('<tr class="tr_'+(i*1+1)+'"><td>'+body[i].sggNm+'</td></tr>');
					}
				}
				for(var i in header){
					var rowCE=1;
					var rowCS=1;
					var rowAA=1;
					for(var j in body){
						var tagb = null;
						if(header[i].sectorId == 'SEC01' && header[i].indiId == body[j].indiId){
							tagb = $("#resultIndiBodyCE .tr_"+rowCE);
							tagb.append('<td>'+body[j].indiVal+'</td>');
							rowCE++;
						}else if(header[i].sectorId == 'SEC02' && header[i].indiId == body[j].indiId){
							tagb = $("#resultIndiBodyCS .tr_"+rowCS);
							tagb.append('<td>'+body[j].indiVal+'</td>');
							rowCS++;
						}else if(header[i].sectorId == 'SEC03' && header[i].indiId == body[j].indiId){
							tagb = $("#resultIndiBodyAA .tr_"+rowAA);
							tagb.append('<td>'+body[j].indiVal+'</td>');
							rowAA++;
						}
					}
				}
			}
		}
	});
}

function fn_indiDataWhole(){
	var item = '${param.item}';
	var model = '${param.model}';
	var section = '${param.section}';
	var year = '${param.year}';
	var dataSet = {
			"item" : item
			,"model" : model
			,"section" : section
			,"year" : year
	};
		url = '/member/base/climate/estimation/selectWholeIndiData.do';
	$.ajax({
		url:url,
		dataType:'json',
		type:'get',
		data:dataSet,
		success:function(result){
			$("#resultIndiInfo").empty();
			var header = result.header;
			var body = result.body;
				for(var i in header){
					var tag = null;
					if(header[i].sectorId == 'SEC01'){
						tag = $("#resultIndiHeaderCE>tr");
					}else if(header[i].sectorId == 'SEC02'){
						tag = $("#resultIndiHeaderCS>tr");
					}else if(header[i].sectorId == 'SEC03'){
						tag = $("#resultIndiHeaderAA>tr");
					}
					tag.append('<th data="'+header[i].indiId+'">'+header[i].indiNm+'('+header[i].indiUnit+')</th>');
				}
				for(var i in body){
					if(i<body.length/header.length){
					var tagb = null;
						tagb = $("#resultIndiBodyCE");
						tagb.append('<tr class="tr_'+(i*1+1)+'"><td>'+body[i].sggNm+'</td></tr>');
						tagb = $("#resultIndiBodyCS");
						tagb.append('<tr class="tr_'+(i*1+1)+'"><td>'+body[i].sggNm+'</td></tr>');
						tagb = $("#resultIndiBodyAA");
						tagb.append('<tr class="tr_'+(i*1+1)+'"><td>'+body[i].sggNm+'</td></tr>');
					}
				}
				for(var i in header){
					var rowCE=1;
					var rowCS=1;
					var rowAA=1;
					for(var j in body){
						var tagb = null;
						if(header[i].sectorId == 'SEC01' && header[i].indiId == body[j].indiId){
							tagb = $("#resultIndiBodyCE .tr_"+rowCE);
							tagb.append('<td>'+body[j].indiVal+'</td>');
							rowCE++;
						}else if(header[i].sectorId == 'SEC02' && header[i].indiId == body[j].indiId){
							tagb = $("#resultIndiBodyCS .tr_"+rowCS);
							tagb.append('<td>'+body[j].indiVal+'</td>');
							rowCS++;
						}else if(header[i].sectorId == 'SEC03' && header[i].indiId == body[j].indiId){
							tagb = $("#resultIndiBodyAA .tr_"+rowAA);
							tagb.append('<td>'+body[j].indiVal+'</td>');
							rowAA++;
						}
					}
				}
		}
	});
}

function fn_print(){
	fn_reloadMap();
	setTimeout(function(){
		window.print();
	},500);
}
</script>
<script src="/resources/billboard/d3.v5.min.js"></script>
<script src="/resources/billboard/billboard.js" type="text/javascript"></script>
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
<script src="/resources/openlayers/ol.js" type="text/javascript"></script>
<script type="text/javascript" src="/resources/openlayers/FileSaver.min.js"></script>
<script type="text/javascript" src="/resources/openlayers/map.js"></script>
<script type="text/javascript" src="/resources/js/html2canvas/html2canvas.js"></script>
</body>
</html>