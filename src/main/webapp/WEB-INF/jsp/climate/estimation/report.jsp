<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
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
<script src="https://cdn.polyfill.io/v2/polyfill.min.js?features=requestAnimationFrame,Element.prototype.classList,URL"></script>
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
	<input class="btn btn-default float-right" type="button" value="인쇄하기" onclick="fn_print()" />
	<!-- <input class="btn btn-primary float-right mr-3" type="button" value="Word 다운로드" onclick="fn_export2Doc('contents','VESTAP_취약성평가_결과_보고서')" /> -->
	<input class="btn btn-primary word-export float-right mr-3" type="button" value="Word 다운로드">
	<!-- <div>
		<a class="btn word-export" href="javascript:void(0)">
			<span class="word-icon" style="font-size:24px; font-weight:bold; background-color: #0054a6; color: white; padding: 2px 5px; vertical-align: middle;">W</span>
			Word 다운로드
		</a>
	</div> -->
	<div class="contents" id="contents">
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
			<div class="col-5" >
				<div class="boxArea text-center p-3 mb-3" style="height: 350px" id="RadarParent">
					<div id="RadarChart"></div>
				</div>
			</div>
		</div>
		<div class="row mt-3">
			<div class="col-4">
				<div id="climateChart"></div>
			</div>
			<div class="col-4">
				<div id="sensChart"></div>
			</div>
			<div class="col-4">
				<div id="adaptChart"></div>	
			</div>
		</div>
		<div class="card mt-3">
			<div class="card-header">
				<div class="row">
					<div class="col-12" style="font-size: 11pt;">
						- 현재(2010년대)를 기준으로, ${info.itemNm}&nbsp;에 영향을 미치는 기후노출 대용변수를 종합하여 시군구 단위의 분포로 산출한 결과물
					</div>
					<br>
					<br>
					<div id="reportComment"class="col-12" style="font-size: 11pt;" >
						<br>
						<br>
						${info.itemNm} 
						<br>
					</div>
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
				<div id="esti_map" style="width: 100%; height: 1205px;">
					<img src= ${param.map } style="width:100%;" alt="map">
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
								<th style="width:120px">참조모델</th>
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
								<th style="width:120px">참조모델</th>
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
								<th style="width:120px">참조모델</th>
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
			
			<c:choose>
				<c:when test="${not empty info.sigunguNm}">
					<span class="float-right">* 실제 읍면동 데이터가 없는 경우, 시군구 데이터 가공없이 그대로 사용</span>
				</c:when>
				<c:otherwise>
					<span class="float-right">* 실제 시군구 데이터가 없는 경우, 시도 데이터 가공없이 그대로 사용</span>
				</c:otherwise>
			</c:choose>
			
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
			
			<c:choose>
				<c:when test="${not empty info.sigunguNm}">
					<span class="float-right">* 실제 읍면동 데이터가 없는 경우, 시군구 데이터 가공없이 그대로 사용</span>
				</c:when>
				<c:otherwise>
					<span class="float-right">* 실제 시군구 데이터가 없는 경우, 시도 데이터 가공없이 그대로 사용</span>
				</c:otherwise>
			</c:choose>
			
		</div>
	</div>
</div>
<input type="hidden" id="depth" value="${viewName.depth1}-${viewName.depth2}-${viewName.depth3}">
<input type="hidden" id="sido" value="${param.sido}">
<input type="hidden" id="sigungu" value="${param.sigungu}">
	<script type="text/javascript">
	var NEAR_COAST_SIGUNGU_CD = '${NEAR_COAST_SIGUNGU_CD}';
		var GEOSERVER_URL = '${GEOSERVER_URL}';
window.onload = function(){
	fn_estimation('do');
	getParentElement();
}

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
/* 
function fn_reloadMap(){
	moveSigungu("one");
	updateMap();
} */

//종합지수
function fn_estimationTotal(exe){
	var field = '${param.field}';
	var item = '${param.item}';
	var model = '${param.model}';
	var section = '${param.section}';
	var year = '${param.year}';
	var sido = '${param.sido}';
	var sigungu = '${param.sigungu}';
	var onlyCoastNear = false;
	if(field === 'FC005'){
		onlyCoastNear = true;
	}
	if(onlyCoastNear){
		var coastArr = NEAR_COAST_SIGUNGU_CD.split(",")
		if(sigungu != ''){
			if(coastArr.indexOf(sigungu) == -1){
				var label = $("#sigunguList option:selected").text();
				fn_alert("경고", "평가대상지역[<b>"+label+"</b>]이 아닙니다.", "warning")
				return
			}
		}else{
			var exist = false
			for(var i in coastArr){
				if(coastArr[i].toString().substr(0,2) === sido.toString().substr(0,2)){
					exist = true
					break
				}
			}
			if(exist === false){
				var label = $("#sidoList option:selected").text();
				fn_alert("경고", "평가대상지역[<b>"+label+"</b>]이 아닙니다.", "warning")
				return
			}
		}
	}
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
			,onlyCoastNear : onlyCoastNear
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
		/* fn_reloadMap();$('#map-legend').show(); */
		},
		success:function(result){
			$("#resultTotal").empty();
			
			var list = result.list;
			var layer_val = new Array();
			var layer_cd = new Array();
			var comment = '';
			var last = '';
			var indi_wei_list = result.radarList;
			console.log(list);
			if(sigungu!='' && sigungu!=null){
				var radar_indiList = new Array();
				radar_indiList.push("x");
				
				/*기초 종합지수창*/
				for(var i in list){
					var html ='';
					var climVal = new Array();
					climVal.push("data1");
					var sensVal = new Array();
					sensVal.push("data2");
					var adptVal = new Array();
					adptVal.push("data3");
					
					var climCount = 0;
					var sensCount = 0;
					var adaptCount = 0;
					
					//지표별 방사형그래프그리기 위해 데이터 배열에 삽입
					for(var j in indi_wei_list){
						
						if(indi_wei_list[j].districtCd == list[i].districtCd) {
							//지표명 리스트 추가
							if(!radar_indiList.includes(indi_wei_list[j].indiNm)){
								radar_indiList.push(indi_wei_list[j].indiNm);	
							}
							
							//기후노출 데이터 삽입
							if(indi_wei_list[j].sectorId =="SEC01"){
								climVal.push(indi_wei_list[j].weightGnrlValue);
								sensVal.push('0');
								adptVal.push('0');
								
								climCount++;
							}//민감도 데이터 삽입
							else if(indi_wei_list[j].sectorId =="SEC02"){
								climVal.push('0');
								sensVal.push(indi_wei_list[j].weightGnrlValue);
								adptVal.push('0');
								
								sensCount++;
							}
							//적응능력 데이터 삽입
							else if(indi_wei_list[j].sectorId =="SEC03"){
								climVal.push('0');
								sensVal.push('0');
								adptVal.push(indi_wei_list[j].weightGnrlValue);
								
								adaptCount++;
							} 
						}
					}
					
					climCnt = climCount;
					sensCnt = sensCount;
					adaptCnt = adaptCount;
					
					if(i==0){
						totalSggNm = list[i].emdNm;
						totalClimValue = list[i].climValue; 
						totalSensValue = list[i].sensValue;
						totalAdaptValue = list[i].adapValue;
						//지표별 방사형그래프
						fn_radarDraw(list[i].emdNm,list[i].climValue,list[i].sensValue,list[i].adapValue);
						
						//세부지표별 방사형 그래프
						fn_indiChartDraw(radar_indiList,climVal, sensVal,adptVal);
					}
					//html = '<tr onclick="fn_radarRedraw(\''+list[i].emdNm+'\','+list[i].climValue+','+list[i].sensValue+','+list[i].adapValue+')">';
					html = '<tr onclick="fn_radarRedraw(\''+Object.values(radar_indiList)+'\',\''+Object.values(climVal)+'\',\''+Object.values(sensVal)+'\',\''+Object.values(adptVal)+'\',\''+ list[i].emdNm + '\','+ list[i].climValue + ',' + list[i].sensValue + ',' + list[i].adapValue +');return false;">';
					html+= '<td>'+list[i].no+'</td><td>'+list[i].emdNm+'</td><td>'+list[i].estiValue+'</td><td>'+list[i].climValue+'</td><td>'+list[i].sensValue+'</td><td>'+list[i].adapValue+'</td><td><i class="icon-graph-pie i-24"></i></td></tr>';
					$("#resultTotal").append(html);
					layer_val.push(list[i].estiValue);
					layer_cd.push(list[i].districtCd);
				}
				
				comment = list[0].emdNm + ", " + list[1].emdNm + ", " + list[2].emdNm;
				last = list[list.length-1].emdNm + ", " + list[list.length-2].emdNm + ", " + list[list.length-3].emdNm;
			}else{
				var radar_indiList = new Array();
				radar_indiList.push("x");
				
				/*광역 종합지수창*/
				for(var i in list){
					var html ='';
					var climVal = new Array();
					climVal.push("data1");
					var sensVal = new Array();
					sensVal.push("data2");
					var adptVal = new Array();
					adptVal.push("data3");
					
					var climCount = 0;
					var sensCount = 0;
					var adaptCount = 0;
					
					//지표별 방사형그래프그리기 위해 데이터 배열에 삽입
					for(var j in indi_wei_list){
						
						if(indi_wei_list[j].districtCd == list[i].districtCd) {
							//지표명 리스트 추가
							if(!radar_indiList.includes(indi_wei_list[j].indiNm)){
								radar_indiList.push(indi_wei_list[j].indiNm);	
							}
							
							//기후노출 데이터 삽입
							if(indi_wei_list[j].sectorId =="SEC01"){
								climVal.push(indi_wei_list[j].weightGnrlValue);
								sensVal.push('0');
								adptVal.push('0');
								
								climCount++;
							}//민감도 데이터 삽입
							else if(indi_wei_list[j].sectorId =="SEC02"){
								climVal.push('0');
								sensVal.push(indi_wei_list[j].weightGnrlValue);
								adptVal.push('0');
								
								sensCount++;
							}
							//적응능력 데이터 삽입
							else if(indi_wei_list[j].sectorId =="SEC03"){
								climVal.push('0');
								sensVal.push('0');
								adptVal.push(indi_wei_list[j].weightGnrlValue);
								
								adaptCount++;
							} 
						}
					}
					
					climCnt = climCount;
					sensCnt = sensCount;
					adaptCnt = adaptCount;
					
					if(i==0){
						//지표별 방사형그래프
						fn_radarDraw(list[i].sggNm,list[i].climValue,list[i].sensValue,list[i].adapValue);
						
						//세부지표별 방사형 그래프
						fn_indiChartDraw(radar_indiList,climVal, sensVal,adptVal);
						
					}
					html = '<tr onclick="fn_radarRedraw(\''+Object.values(radar_indiList)+'\',\''+Object.values(climVal)+'\',\''+Object.values(sensVal)+'\',\''+Object.values(adptVal)+'\',\''+ list[i].sggNm + '\','+ list[i].climValue + ',' + list[i].sensValue + ',' + list[i].adapValue +');return false;">';
					html+= '<td>'+list[i].no+'</td><td>'+list[i].sggNm+'</td><td>'+list[i].estiValue+'</td><td>'+list[i].climValue+'</td><td>'+list[i].sensValue+'</td><td>'+list[i].adapValue+'</td><td><i class="icon-graph-pie i-24"></i></td></tr>';
					$("#resultTotal").append(html);
					layer_val.push(list[i].estiValue);
					layer_cd.push(list[i].districtCd);
					
				}
				
				comment = list[0].sggNm + ", " + list[1].sggNm + ", " + list[2].sggNm;
				last = list[list.length-1].sggNm + ", " + list[list.length-2].sggNm + ", " + list[list.length-3].sggNm;
			}
			
			$('#reportComment').html(" - "+ $('#reportComment').text()+"과 관련된 취약성평가 종합지수가 상대적으로 높은 지역은 <b>"+ comment + "(3개)</b> 순으로 도출되었으며, 상대적으로 취약성이 낮은 지역은  <b>" + last + "(3개)</b> 순으로  나타남<br/>" );
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
			//addVector(layer_cd,layer_val);
			//fn_mapLegend();
		}
	});
}

/* function fn_mapLegend(){
	$('#map-legend').show();
} */

//data1 중요
function fn_radarDraw(name, col1, col2, col3){
	chart = bb.generate({
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
	
	chart.data.names({
		 data1 : name
		});
	chart.load({
	    columns: [
	       ["data1", col1, col2, col3]]});
}

function fn_radarRedraw(indiList,climList,sensList,adaptList,name,climValue,sensValue,adapValue){
	
	if(typeof(climList) == 'string' ||typeof(indiList) =='string'){
		indiList = indiList.split(',');
		climList = climList.split(',');
		sensList = sensList.split(',');
		adaptList = adaptList.split(',');
	}
	
	fn_radarDraw(name, climValue, sensValue, adapValue);
	
	fn_indiChartDraw(indiList,climList, sensList,adaptList);
	
}

//상세보기
function fn_estimationDetail(){
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
	$.ajax({
		url:'/member/base/climate/estimation/resultDetail.do',
		dataType:'json',
		type:'get',
		data:dataSet,
		success:function(result){
			$("#resultDetail").empty();
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
				var mdlNm = '사회경제';
				if(list[i].mdlNm!=null){
					mdlNm = list[i].mdlNm;
				}
				tag.append('<tr class="'+list[i].sectorId+'"><td>'+list[i].indiNm+'</td><td>'+mdlNm+'</td><td><b>'+list[i].indiConstSign+'</b></td><td>'+list[i].indiValWeight+'</td></tr>');
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

function fn_classDisplay(className, display){
	for (var i = 0; i < className.length; i++) {
		className[i].style.display = display;
		}
}

function fn_print(){
	/* fn_reloadMap(); */
	var btn = document.getElementsByClassName("btn");
	fn_classDisplay(btn,'none');
	setTimeout(function(){
		window.print();
		fn_classDisplay(btn,'block');
	},500);
}

function fn_export2Doc(element,filename){
	var btn = document.getElementsByClassName("btn");
	fn_classDisplay(btn,'none');
	var server = 'http://210.113.102.223';
	var preHtml = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:w='urn:schemas-microsoft-com:office:word' xmlns='http://www.w3.org/TR/REC-html40'><head><meta charset='utf-8'><title>"+filename+"</title>"
 	+"<link href='"+server+"/resources/css/bootstrap/bootstrap.css' rel='stylesheet'>"
	+"<link href='"+server+"/resources/css/developer/style.css' rel='stylesheet'>" 
	+"<link href='"+server+"/resources/css/developer/style-custom.css' rel='stylesheet'>" 
	+"<style> body {font-family:'돋움' !important;} table {border: 1px solid gray; text-align:center} th,td {border: 1px solid gray; font-size:11pt !important;} span,p {font-weight:bold !important; line-height:24px !important;} b {font-size:14pt !important;} </style>"
	+"</head><body>";
	
    var postHtml = "</body></html>";
    var radarp = document.getElementById('RadarParent');
    var radar = document.getElementById('RadarChart');
    while(radarp.hasChildNodes()) {
    	radarp.removeChild( radarp.firstChild );
    	}
    var html = preHtml+document.getElementById(element).innerHTML+postHtml;

    var blob = new Blob(['\ufeff', html], {
        type: 'application/msword'
    });
    
    // Specify link url
    var url = 'data:application/vnd.ms-word;charset=utf-8,' + encodeURIComponent(html);
    
    // Specify file name
    filename = filename?filename+'.doc':'document.doc';
    
    // Create download link element
    var downloadLink = document.createElement("a");

    document.body.appendChild(downloadLink);
    
    if(navigator.msSaveOrOpenBlob ){
        navigator.msSaveOrOpenBlob(blob, filename);
    }else{
        // Create a link to the file
        downloadLink.href = url;
        
        // Setting the file name
        downloadLink.download = filename;
        
        //triggering the function
        downloadLink.click();
    }
    
    document.body.removeChild(downloadLink);
    radarp.appendChild(radar);
    fn_classDisplay(btn,'block');
}

function fn_indiChartDraw(names,col1,col2,col3){
	indiList = names;
	climate = col1;
	sens = col2 ;
	adapt = col3;
	
	if(typeof(col1) == 'string' ||typeof(names) =='string'){
		names = names.split(',');
		col1 = col1.split(',');
		col2 = col2.split(',');
		col3 = col3.split(',');
	}
	
	var climValue = new Array();
	var climIndi = new Array();
	var sensValue = new Array();
	var sensIndi = new Array();
	var adaptValue = new Array();
	var adaptIndi = new Array();
	climValue.push("data1");
	climIndi.push("x");
	sensValue.push("data2");
	sensIndi.push("x");
	adaptValue.push("data3");
	adaptIndi.push("x");
	
	//기후노출 지표명, 지표데이터 리스트삽입
	for(var i = 1; i< climCnt+1; i++){
		climIndi.push(indiList[i]);
		climValue.push(climate[i]);
	}
	
	//민감도 지표명, 지표데이터 리스트삽입
	for(var i = climCnt+1; i< climCnt+sensCnt+1; i++){
		sensIndi.push(indiList[i]);
		sensValue.push(sens[i]);
	}
	
	//적응능력 지표명, 지표데이터 리스트삽입
	for(var i = climCnt+sensCnt+1; i< adapt.length; i++){
		adaptIndi.push(indiList[i]);
		adaptValue.push(adapt[i]);
	}
	
	
	if(climCnt < 3){
		climIndi.shift();
		climValue.shift();
		climValue.splice(0,0,"기후노출");
		
		fn_climBarDraw(climIndi, climValue);
	}else{
		fn_climRadarDraw(climIndi, climValue);
		chart.unload({
			   ids: ["data2", "data3"],
			   done: function() {
			      chart.load({
				    columns:[
				      climIndi,
				      climValue
				  ]
				});
			   }
		 });
	}
	
	if(sensCnt < 3){
		sensIndi.shift();
		sensValue.shift();
		sensValue.splice(0,0,"민감도");
		
		
		fn_sensBarDraw(sensIndi, sensValue);
	}else{
		fn_sensRadarDraw(sensIndi, sensValue);
		chart.unload({
			   ids: ["data1", "data3"],
			   done: function() {
			      chart.load({
				    columns:[
				      sensIndi,
				      sensValue
				  ]
				});
			   }
		 });
		
	}
	
	if(adaptCnt < 3){
		adaptIndi.shift();
		adaptValue.shift();
		adaptValue.splice(0,0,"적응능력");
		
		fn_adaptBarDraw(adaptIndi, adaptValue);
	}else{
		fn_adaptRadarDraw(adaptIndi, adaptValue);
		chart.unload({
			   ids: ["data1", "data2"],
			   done: function() {
			      chart.load({
				    columns:[
				      adaptIndi,
				      adaptValue
				  ]
				});
			   }
		 });
		
	}
	
	//이걸 왜하는거엿지 ??? 
	for(var i=0; i<names.length; i++){
		
		if(names[i].length > '9'){
			var str = names[i];
			var str2 = str.replace(/(.{9})/g,"$1\n");
			
			var removed = names.splice(i, 1,str2);
			var last = names.length;
		}
		
	}
	
	for(var i=0; i<names.length; i++){
		if(names[i].indexOf("\n") > 0){
			var str = names[i];
			var str2 = str.replace(/\n+/g, "");
			
			var removed = names.splice(i, 1,str2);
		}
		
	}
	
}
function fn_climBarDraw(indiList, value){
	var columnName = value[0];
	chart = bb.generate({
		  data: {
		    columns: [
			value
		    ],
		    type: "bar", // for ESM specify as: bar()
		    colors:{
		    	"기후노출" : "#1f83ce",
		    	"민감도" : "#00c73c",
		    	"적응능력" : "#fa7171"
		    }
		  },
		  axis:{
			x: {
				type: "category"
			}  
		  },
		  y:{
			max: 0.9,
			label: {
				position: "outer-top"
			}
		  },
		  bar: {
		    width: {
		      ratio: 0.3
		    }
		  },
		  bindto: "#climateChart"
		});
	
	chart.categories(indiList);
	chart.axis.max({
		  y: 0.9
		}); 
}
function fn_climRadarDraw(indiList, climate){
	
	var columns = new Array();
	columns.push(indiList);
	
	chart = bb.generate({
		padding: {
		    top: 5
		  },
		  data: {
		    x: "x",
		    colors: {
		        data1: "#1f83ce",
		        data2: "#00c73c",
		        data3: "#fa7171"
		        },
	        names: {
	        	data1 : "기후노출",
	        	data2 : "민감도",
	        	data3 : "적응능력"
	          },
		    columns: [
				columns
		    ],
		    type: "radar"
		  },
		  radar: {
			  level: {
			      depth: 5
			    },
			  axis: {
			      max: 0.5,
			    },
		    size: {
		        ratio: 0.9
		      }
		  },
		  bindto: "#climateChart"
		});
}
function fn_sensBarDraw(indiList,value){
	var columnName = value[0];
	
	chart = bb.generate({
		  data: {
		    columns: [
			value
		    ],
		    type: "bar", // for ESM specify as: bar()
		    colors:{
		    	"기후노출" : "#1f83ce",
		    	"민감도" : "#00c73c",
		    	"적응능력" : "#fa7171"
		    }
		  },
		  axis:{
			x: {
				type: "category"
			}  
		  },
		  y:{
			max: 0.9,
			label: {
				position: "outer-top"
			}
		  },
		  bar: {
		    width: {
		      ratio: 0.3
		    }
		  },
		  bindto: "#sensChart"
		});
	
	chart.categories(indiList);
	chart.axis.max({
		  y: 0.9
		}); 
}
function fn_sensRadarDraw(indiList, climate){
	
	var columns = new Array();
	columns.push(indiList);
	
	chart = bb.generate({
		padding: {
		    top: 5
		  },
		  data: {
		    x: "x",
		    colors: {
		        data1: "#1f83ce",
		        data2: "#00c73c",
		        data3: "#fa7171"
		        },
	        names: {
	        	data1 : "기후노출",
	        	data2 : "민감도",
	        	data3 : "적응능력"
	          },
		    columns: [
				columns
		    ],
		    type: "radar"
		  },
		  radar: {
			  level: {
			      depth: 5
			    },
			  axis: {
			      max: 0.5,
			    },
		    size: {
		        ratio: 0.9
		      }
		  },
		  bindto: "#sensChart"
		});
}
function fn_adaptBarDraw(indiList, value){
	chart = bb.generate({
		padding: {
		    top: 5
		  },
		  data: {
		    x: "x",
		    colors: {
		        data1: "#1f83ce",
		        data2: "#00c73c",
		        data3: "#fa7171"
		        },
	        names: {
	        	data1 : "기후노출",
	        	data2 : "민감도",
	        	data3 : "적응능력"
	          },
		    columns: [
				columns
		    ],
		    type: "radar"
		  },
		  radar: {
			  level: {
			      depth: 5
			    },
			  axis: {
			      max: 0.5,
			    },
		    size: {
		        ratio: 0.9
		      }
		  },
		  bindto: "#adaptChart"
		});
}
function fn_adaptRadarDraw(indiList, climate){
	
	var columns = new Array();
	columns.push(indiList);
	
	chart = bb.generate({
		padding: {
		    top: 5
		  },
		  data: {
		    x: "x",
		    colors: {
		        data1: "#1f83ce",
		        data2: "#00c73c",
		        data3: "#fa7171"
		        },
	        names: {
	        	data1 : "기후노출",
	        	data2 : "민감도",
	        	data3 : "적응능력"
	          },
		    columns: [
				columns
		    ],
		    type: "radar"
		  },
		  radar: {
			  level: {
			      depth: 5
			    },
			  axis: {
			      max: 0.5,
			    },
		    size: {
		        ratio: 0.9
		      }
		  },
		  bindto: "#adaptChart"
		});
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
/* var climateChart = bb.generate({
	padding: {
	    top: 5
	  },
	  data: {
	    x: "x",
	    colors: {
	        data1: "#1f83ce"
	        },
        names: {
        	data1 : "기후노출"
          },
	    columns: [
			columns
	    ],
	    type: "radar"
	  },
	  radar: {
		  level: {
		      depth: 5
		    },
		  axis: {
		      max: 0.5,
		      text: {
		        }
		    },
	    size: {
	        ratio: 0.9
	      }
	  },
	  bindto: "#climateChart"
	}); 
var sensChart = bb.generate({
	padding: {
	    top: 5
	  },
	  data: {
	    x: "x",
	    colors: {
	        data2: "#00c73c"
	        },
        names: {
        	data2 : "민감도"
          },
	    columns: [
			columns
	    ],
	    type: "radar"
	  },
	  radar: {
		  level: {
		      depth: 5
		    },
		  axis: {
		      max: 0.5,
		      text: {
		        }
		    },
	    size: {
	        ratio: 0.9
	      }
	  },
	  bindto: "#sensChart"
	});
var adaptChart = bb.generate({
	padding: {
	    top: 5
	  },
	  data: {
	    x: "x",
	    colors: {
	        data3: "#fa7171"
	        },
        names: {
        	data3 : "적응능력"
          },
	    columns: [
			columns
	    ],
	    type: "radar"
	  },
	  radar: {
		  level: {
		      depth: 5
		    },
		  axis: {
		      max: 0.5,
		      text: {
		        }
		    },
	    size: {
	        ratio: 0.9
	      }
	  },
	  bindto: "#adaptChart"
	});*/
</script>
<!-- <script src="/resources/openlayers/ol.js" type="text/javascript"></script>
<script type="text/javascript" src="/resources/openlayers/FileSaver.min.js"></script>
<script type="text/javascript" src="/resources/openlayers/map.js"></script> -->
<!-- <script src="/resources/js/html2canvas/html2canvas-9.js"></script>
<script src="/resources/js/html2canvas/bluebird.min.js"></script>
<script src="/resources/js/html2canvas/es6-promise.auto.js"></script> -->



<!-- <script src="http://html2canvas.hertzen.com/dist/html2canvas.js"></script> -->

<script src="/resources/js/html2canvas/es6-promise.auto.js"></script>
<script src="/resources/js/html2canvas/html2canvas.js"></script>
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/es6-promise/4.1.1/es6-promise.auto.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/es6-promise/4.1.1/es6-promise.min.js"></script> -->

 <script type="text/javascript"> 	
	function getParentElement(){
		var agent = navigator.userAgent.toLowerCase();
		var pElement = null
		if ( (navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
		     // ie일 경우
			console.log("1.IE");
			pElement = opener.document.getElementById("esti_map");
		}else{
		     // ie가 아닐 경우
			console.log("1.NOT IE");
			pElement = opener.document.getElementById("esti_map");
		}
		
		html2canvas(pElement).then(function(canvas){
			var imageData = null;
			if ( (navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
			     // ie일 경우
			     var blob = canvas.msToBlob(); 
			     imageData = URL.createObjectURL(blob);
			}else{
			     // ie가 아닐 경우
				imageData = canvas.toDataURL("image/png");
				var ctx = canvas.getContext('2d');
				// 이미지 데이터를 캔버스에 그린다.
				var img = new Image(); // 이미지 객체를 생성하고
				img.onload = function () { // 이미지가 로드된 후에
				  ctx.drawImage(img, 0, 0); // 복사할 캔버스의 컨텍스트를 가져와 drawImage를 호출해 다시 그려준다.
				};
			}
			
			img.src = imageData;
			img.setAttribute("style","width:100%;height:100%");
						
			//document.getElementById("esti_map").appendChild(img);
		});
	}
   </script>
<!-- word다운로드 js -->
<script src="/resources/js/jquery/filesaver.min.js"></script>
<script src="/resources/js/bootstrap/bootstrap.min.js"></script>
<script>
$(document).on("click", ".word-export", function() {
	$(".contents").wordExport();
	});


if (typeof jQuery !== "undefined" && typeof saveAs !== "undefined") {
    (function($) {
        $.fn.wordExport = function(fileName) {
            fileName = typeof fileName !== 'undefined' ? fileName : "VESTAP_취약성평가_결과_보고서";
            var static = {
                mhtml: {
                    top: "Mime-Version: 1.0\nContent-Base: " + location.href + "\nContent-Type: Multipart/related; boundary=\"NEXT.ITEM-BOUNDARY\";type=\"text/html\"\n\n--NEXT.ITEM-BOUNDARY\nContent-Type: text/html; charset=\"utf-8\"\nContent-Location: " + location.href + "\n\n<!DOCTYPE html>\n<html>\n_html_</html>",
                    head: "<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n<style>\n_styles_\n</style>\n</head>\n",
                    body: "<body>_body_</body>"
                }
            };
            var options = {
                maxWidth: 624
            };
            // Clone selected element before manipulating it
            var markup = $(this).clone();

            // Remove hidden elements from the output
            markup.each(function() {
                var self = $(this);
                
                var $radarC = self.find("svg").parent();
                $radarC.empty();
                
                if (self.is(':hidden'))
                    self.remove();
                
            });

            // Embed all images using Data URLs
            var images = Array();
            var img = markup.find('img');
            for (var i = 0; i < img.length; i++) {
                // Calculate dimensions of output image
                var w = Math.min(img[i].width, options.maxWidth);
                var h = img[i].height * (w / img[i].width);
                // Create canvas for converting image to data URL
                var canvas = document.createElement("CANVAS");
                canvas.width = w;
                canvas.height = h;
                // Draw image to canvas
                var context = canvas.getContext('2d');
                context.drawImage(img[i], 0, 0, w, h);
                // Get data URL encoding of image
                var uri = canvas.toDataURL("image/png");
                $(img[i]).attr("src", img[i].src);
                img[i].width = w;
                img[i].height = h;
                // Save encoded image to array
                images[i] = {
                    type: uri.substring(uri.indexOf(":") + 1, uri.indexOf(";")),
                    encoding: uri.substring(uri.indexOf(";") + 1, uri.indexOf(",")),
                    location: $(img[i]).attr("src"),
                    data: uri.substring(uri.indexOf(",") + 1)
                };
            }

            // Prepare bottom of mhtml file with image data
            var mhtmlBottom = "\n";
            for (var i = 0; i < images.length; i++) {
                mhtmlBottom += "--NEXT.ITEM-BOUNDARY\n";
                mhtmlBottom += "Content-Location: " + images[i].location + "\n";
                mhtmlBottom += "Content-Type: " + images[i].type + "\n";
                mhtmlBottom += "Content-Transfer-Encoding: " + images[i].encoding + "\n\n";
                mhtmlBottom += images[i].data + "\n\n";
            }
            mhtmlBottom += "--NEXT.ITEM-BOUNDARY--";

            //TODO: load css from included stylesheet
            var styles = " body {font-family:'돋움' !important;} table {border: 1px solid gray; text-align:center} th,td {border: 1px solid gray; font-size:11pt !important;} span,p {font-weight:bold !important; line-height:24px !important;} b {font-size:14pt !important;} ";

            // Aggregate parts of the file together
            
            var fileContent = static.mhtml.top.replace("_html_",static.mhtml.head.replace("_styles_", styles) + static.mhtml.body.replace("_body_", markup.html())) + mhtmlBottom;
            
            // Create a Blob with the file contents
            var blob = new Blob([fileContent], {
                type: "application/msword;charset=utf-8"
            });
            saveAs(blob, fileName + ".doc");
            
        };
    })(jQuery);
} else {
    if (typeof jQuery === "undefined") {
        console.error("jQuery Word Export: missing dependency (jQuery)");
    }
    if (typeof saveAs === "undefined") {
        console.error("jQuery Word Export: missing dependency (FileSaver.js)");
    }
}
	
</script>
</body>
</html>