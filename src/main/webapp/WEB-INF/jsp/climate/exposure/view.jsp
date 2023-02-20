<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>VESTAP - 기후변화 취약성 - 기후노출 정보</title>
<link href="/resources/css/loading/loading.css" rel="stylesheet">
<link href="/resources/billboard/billboard.css" rel="stylesheet">
<link href="/resources/billboard/insight.css" rel="stylesheet">
<link href="/resources/openlayers/ol.css" rel="stylesheet">
<style>
.fold-menu-item {
	cursor: pointer;
}

.fold-open-item {
	overflow: auto;
	height: 280px;
}

.fold-menu-indi {
	cursor: pointer;
}

.fold-open-indi {
	overflow: auto;
	height: 280px;
}
#lineChart{
	display: none;
}
#AreaRangeChart{
	display: none;
}
#initChart{
	width:100%;
	height:100%;
	display: block;
	line-height:316px;
	text-align: center;
	font-size: 12px;
}
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
<table class="offcanvas-table">
	<tr>
		<td class="offcanvas-left-open">
			<!-- *********************** offcanvas-left-open *********************** -->

			<div class="card">
				<div class="card-header offcanvas-left-open-title">
					<i class="icon-file-bookmark"></i>기후노출 세부정보<a href="javascript:void(0);" class="on-offmenu text-blue"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
				</div>
				<ul class="list-group list-group-flush">
					<li class="list-group-item sub-title border-top-0"><i
						class="icon-file-align"></i>취약성</li>
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
					<li class="list-group-item sub-title border-top-1 fold-menu-item"><i
						class="icon-file-align"></i>평가항목</li>
					<li class="list-group-item border-top-0 fold-open-item">
						<div class="col">
							<div class="mt-0 ">
								<span id="fieldName"> <c:forEach var="i"
										items="${fieldList }">
										<c:if test="${setField ne null and setField eq i.codeId }">${i.codeNm}</c:if>
									</c:forEach>
								</span> 취약성 평가항목
							</div>
							<hr class="mt-2 mb-3">
							<div class="toolTipUl">
								<input type="hidden" id="selectItem" value="${setItem}">
								<ul class="nav navbar-nav" id="itemList">
									<c:forEach var="i" items="${itemList }">
										<li><a href='#'
											class='<c:if test="${setItem ne null and setItem eq i.itemId }">active</c:if>'
											data='${i.itemId }'
											onclick='fn_itemClick($(this));return false;'>${i.itemNm }</a></li>
									</c:forEach>
								</ul>
							</div>
							<!-- //toolTipUl -->
						</div> <!-- //col -->
					</li>
					<li class="list-group-item sub-title border-top-1 fold-menu-indi"><i
						class="icon-file-align"></i>기후노출 부문 지표</li>
					<li class="list-group-item border-top-0 fold-open-indi">
						<div class="col">
							<div class="mt-0 ">
								<span id="itemName"> <c:forEach var="i"
										items="${itemList }">
										<c:if test="${setItem ne null and setItem eq i.itemId }">${i.itemNm}</c:if>
									</c:forEach>
								</span>
							</div>
							<hr class="mt-2 mb-3">
							<div class="toolTipUl ">
								<input type="hidden" id="selectIndi" value="">
								<ul class="nav navbar-nav" id="indiList">
									<c:if test="${empty indiList}">
										<li><a href='#'>제공되는 지표가 없습니다. </a></li>
									</c:if>
									<c:if test="${not empty indiList}">
									<c:forEach var="i" items="${indiList }">
										<li><a href='#' data='${i.indiId }'
											onclick='fn_indiClick($(this));return false;'>${i.indiNm }</a></li>
									</c:forEach>
									</c:if>
								</ul>
							</div>
						</div>
					</li>
				</ul>
			</div> <!-- //card -->
		</td>
		<td class="col align-top p-0">
			
			<div class="onmenu-div d-none">
				<a href="javascript:void(0);" class="text-blue on-offmenu"><i class="icon-arrow-caret-right"></i></a>
			</div>
			
			<div class="mainContents">
				<div class="card">
					<div class="card-header p-2">
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
									<option value='' disabled="disabled" style='font-weight:bold;'>- 10년 단위  -</option>
									<c:forEach var="i" items="${yearList }">
										<c:if test="${i.yearType eq 'YT001'}">
										<option value='${i.yearId}' <c:if test="${setYear ne null and setYear eq i.yearId }">selected</c:if>>${i.yearNm}</option>
										</c:if>
									</c:forEach>
									<option value='' disabled="disabled" style='font-weight:bold;'>- 30년 단위  -</option>
									<c:forEach var="i" items="${yearList }">
										<c:if test="${i.yearType eq 'YT003'}">
										<option value='${i.yearId}' <c:if test="${setYear ne null and setYear eq i.yearId }">selected</c:if>>${i.yearNm}</option>
										</c:if>
									</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-2 pl-0">
								<div class=" select_box">
									<select class="form-control" id="sidoList">
										<option value='' selected>전국</option>
										<c:forEach var="i" items="${sidoList }">
											<option value='${i.districtCd}'>${i.districtNm}</option>
										</c:forEach>
									</select>
								</div>
							</div>
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
						</div>
						<!-- row -->

					</div>
					<!-- card-header -->
					<div class="card-body p-2">
						<div id="expo_map" style="width: 100%; height: 860px;">
							<div class="ol-custom ol-unselectable ol-control ol-expo">
								<div id="map-legend" style="display: none; margin-bottom:10px">
									<div class='legend-title'><span>범례</span></div>
									<div class='legend-scale'>
										<ul class='legend-labels'>
											<li><span style='background: #80B1D3; border: 1px solid #999;'></span>white</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div> <!-- //mainContents -->

		</td>
		<td class="offcanvas-right-open">
			<!-- *********************** offcanvas-right-open *********************** -->

			<div class="card">
				<div class="card-header offcanvas-right-open-title">
					<i class="icon-file-bookmark"></i>시나리오 별 편차 그래프
					<button type="button" class="offcanvasCloseBtn close">
						<i class="icon-close-bold"></i>
					</button>
				</div>

				<ul class="list-group list-group-flush">
					<li class="list-group-item">
						<div class="pb-2 pl-2">주소 <span class="_address"></span></div>

						<div class="boxArea text-center p-2" style="height: 350px">
							<div id="lineChart"></div>
							<div id="initChart">지도를 선택하면 그래프가 표출됩니다.</div>
						</div> <!-- //boxArea -->

					</li>
					<li class="list-group-item offcanvas-right-open-title pt-4"><i
						class="icon-file-bookmark"></i>기후모델 별 편차 그래프</li>
					<li class="list-group-item">
						<div class="row pb-2">
							<div class="col-4 pl-3">
								<div class=" select_box">
									<select class="form-control" id="expoSectionList">
											<option value='RC002' selected>RCP 4.5</option>
											<option value='RC003'>RCP 8.5</option>
									</select>
								</div>
							</div>
							<div class="col-4 pl-0">
								<div class=" select_box">
									<select class="form-control" id="expoYearList">
										<c:forEach var="i" items="${expoYearList }" varStatus="is">
											<option value='${i.codeId}'
											<c:if test="${is.first}">selected</c:if>
											>${i.codeNm}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-4 pl-0">
								<div class=" select_box">
									<select class="form-control" id="varList">
										<c:forEach var="i" items="${varList }" varStatus="is">
											<option value='${i.codeId}'
											<c:if test="${is.first}">selected</c:if>
											>${i.codeNm}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div> <!-- row -->
						<div class="boxArea text-left p-2 mb-3" style="height: 340px">
							<div id="AreaRangeChart"></div>
							<span><br> * 위 그래프는 <b>서울특별시 종로구</b>를 기준으로 선택된 RCP, 연대, 변수값의 <br> 각 기후 모델별 월 평균 자료로 표현되었습니다.
							</span>
						</div> <!-- //boxArea -->

					</li>
				</ul>
			</div> <!-- //card -->

		</td>
	</tr>
</table>
<script type="text/javascript">
	$(document).ready(function() {
		//리스크 선택창 변경 시 항목 리스트 변경
		$("#fieldList").change(function() {
			$("#fieldName").html($("#fieldList option:selected").text());
			fn_itemList($("#fieldList option:selected").val());
			$(".fold-open-item").slideDown();
		});

		$(".fold-menu-item").click(function() {
			var submenu = $(".fold-open-item");
			if (submenu.is(":visible")) {
				submenu.slideUp();
			} else {
				submenu.slideDown();
			}
		});
		$(".fold-menu-indi").click(function() {
			var submenu = $(".fold-open-indi");
			if (submenu.is(":visible")) {
				submenu.slideUp();
			} else {
				submenu.slideDown();
			}
		});
		
		//시도 목록 변경 시 시군구 목록 불러오기
		$("#sidoList").change(function(){
			fn_sigunguList($("#sidoList option:selected").val());
		});
		
		$("#expoSectionList").change(function() {
			fn_ensemble();
		});
		
		$("#expoYearList").change(function() {
			fn_ensemble();
		});
		
		$("#varList").change(function() {
			fn_ensemble();
		});
		
		//fn_ensemble();
		
	});

	function fn_itemClick(tag) {
		var data = tag.attr('data');
		$("#itemList a").removeClass('active');
		tag.addClass('active');
		$("#selectItem").val(data);
		$("#itemName").text(tag.text());
		fn_indiList();
		$(".fold-open-indi").slideDown();
	}

	function fn_itemList(fieldCode) {
		var field = fieldCode;
		var url = '/member/base/climate/estimation/itemList.do';
		var dataSet = {
			"field" : field
		};
		$.ajax({
			url : url,
			dataType : "json",
			type : "get",
			data : dataSet,
			success : function(result) {
				$("#itemList").empty();
				var list = result.list;
				for ( var i in list) {
					var html = '';
					html += "<li><a href='#' data='"
							+ list[i].itemId
							+ "' onclick='fn_itemClick($(this));return false;'>"
							+ list[i].itemNm + "</a></li>";
					$("#itemList").append(html);
				}
			}
		});
	}

	function fn_indiClick(tag) {
		var data = tag.attr('data');
		var text = tag.text();
		$("#indiList a").removeClass('active');
		tag.addClass('active');
		$("#selectIndi").val(data);
		changeLayer(data);
		fn_exposure();
		//fn_label(text);
	}

	function fn_indiList() {
		var item = $("#selectItem").val();
		var url = '/member/base/climate/exposure/indiList.do';
		var dataSet = {
			"item" : item
		};
		$.ajax({
			url : url,
			data : dataSet,
			type : 'get',
			dataType : 'json',
			success : function(result) {
				$("#indiList").empty();
				var list = result.list;
				if(list.length>0){
					for ( var i in list) {
						var html = '';
						html = "<li><a href='#' data='"
								+ list[i].indiId
								+ "' onclick='fn_indiClick($(this));return false;'>"
								+ list[i].indiNm + "</a></li>";
						$("#indiList").append(html);
					}
				}else{
					var html = '';
					html = "<li><a href='#'>제공되는 지표가 없습니다. </a></li>";
					$("#indiList").append(html);
				}
			}
		});
	}
	

	function fn_sigunguList(sidoCode){
		var userDist = $("#userDist").val();
		var dataSet = {"sidoCode" : sidoCode};
		$.ajax({
			url:'/member/base/climate/exposure/sigunguList.do',
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
					if(list[i].districtCd == userDist){
						$("#sigunguList").append("<option value='"+list[i].districtCd+"' selected>"+list[i].districtNm+"</option>");
					}else{
						$("#sigunguList").append("<option value='"+list[i].districtCd+"'>"+list[i].districtNm+"</option>");
					}
				}
				
			}
		});
	}
	
	function fn_exposure(){
		offcanvasOpen();
		lineChart.resize({
			   width: 448,
			   height: 320
			});
		lineRangeChart.resize({
			 	width: 448,
			   height: 320
			});
	}
	
	function fn_ensemble(){
		var section = $("#expoSectionList option:selected").val();
		var year = $("#expoYearList option:selected").val();
		var variable = $("#varList option:selected").val();
		var url ='';
		var dataSet = {
				"section" : section
				,"year" : year
				,"variable" : variable
		};
		url = "/member/base/climate/exposure/ensembleRange.do";
		$.ajax({
			url : url,
			data : dataSet,
			type : "get",
			dataType : "json",
			beforeSend:function(){},
			success:function(result){
				var list = result.list;
				var data1 = "data1";
				var column =[data1];
				for(var i in list){
					var data = [list[i].maxAvg,list[i].avgAvg,list[i].minAvg];
					column.push(data);
				}
				lineRangeChart.data.names({data1: "전체", data2 : "MME5s", data3 : "GRIMs", data4 : "HadGEM3-RA", data5 : "RegCM_v4", data6 : "SNU-MM_v3", data7 : "WRF_v3.4"});
				lineRangeChart.load({
					//colors: {data1: "rgba(128,128,128,0.2)"},
					//colors: {data1 : "#5D5D5D"},
					columns: 
					          [column],
					types: {data1: "area-line-range"}
				});
				
			},
			complete:function(){},
			error:function(error){}
		});
		url = "/member/base/climate/exposure/ensembleData.do";
		$.ajax({
			url : url,
			data : dataSet,
			type : "get",
			dataType : "json",
			beforeSend:function(){},
			success:function(result){
				var list = result.list;
				var columns = [];
				
				for(var i=1;i<7;i++){
					eval("var column"+i+" = ['data"+(i+1)+"']");
					for(var j in list){
						eval("column"+i+".push(list["+j+"].cm00"+i+")");
					}
					eval("columns.push(column"+i+")");
				}
				//lineRangeChart.data.names({data1: "전체", data2 : "MME5s", data3 : "GRIMs", data4 : "HadGEM3-RA", data5 : "RegCM_v4", data6 : "SNU-MM_v3", data7 : "WRF_v3.4"});
				//lineRangeChart.data.colors({data1 : "#D4D4D4", data2 : "#ff0000", data3 : "#ffC000", data4 : "#ffff00", data5 : "#92D050", data6 : "#00B0F0", data7 : "#0070C0"});
				lineRangeChart.load({
					colors: {data1 : "#5D5D5D",data2 : "#ff0000", data3 : "#ffC000", data4 : "#ffff00", data5 : "#92D050", data6 : "#00B0F0", data7 : "#0070C0",},
					columns: columns,
					types: {data2: "line", data3: "line", data4: "line", data5: "line", data6: "line", data7: "line"}
				});
			},
			complete:function(){
				if(variable=='VC001'){
					lineRangeChart.axis.labels({
						  y: "(mm)"
						});
				}else{
					lineRangeChart.axis.labels({
						  y: "(℃)"
						});
				}
			},
			error:function(error){}
		});		
		
		/* if(variable=='VC001'){
			lineRangeChart.axis.labels({
				  y: "(mm)"
				});
		}else{
			lineRangeChart.axis.labels({
				  y: "(℃)"
				});
		} */
	}
	
	function fn_showChart(){
		$("#lineChart").css('display','block');
		$("#initChart").css('display','none');
		
		$("#AreaRangeChart").css('display','block');
		fn_exposure();
	}

	function fn_label(text){
		var str = ["횟수","기온","강수"];
		if (text.indexOf(str[0]) != -1) {
			lineChart.axis.labels({
				  y: "(회)"
				});
		}
		else if (text.indexOf(str[1]) != -1) {
			lineChart.axis.labels({
				  y: "(℃)"
				});
		}
		else if (text.indexOf(str[2]) != -1) {
			lineChart.axis.labels({
				  y: "(mm)"
				});
		}
	}
	function fn_xlabel(year){
		var year = $("#scenYearList option:selected").val();
		if(year.substring(2) > 200){
			lineChart.categories([
			        "21~50년",
			        "31~60년",
			        "41~70년",
			        "51~80년",
			        "61~90년",
			        "71~100년"
			      ]); 
		}else{
			lineChart.categories([
			        "21~30년",
			        "31~40년",
			        "41~50년",
			        "51~60년",
			        "61~70년",
			        "71~80년",
			        "81~90년",
			        "91~100년",
			      ]); 
		}
		
		
		
	}
	
	function fn_lineRedraw(val){
		lineChart.data.names({
			data1 : "RCP4.5",
			data2 : "RCP8.5"
		});
		lineChart.load({
			columns : [
			           ["data1", val[0], val[1], val[2], val[3], val[4], val[5], val[6], val[7]],
			           ["data2", val[8], val[9], val[10], val[11], val[12], val[13], val[14], val[15]]
			           ]
		});
		
		var year = $("#scenYearList option:selected").val();
		fn_xlabel(year);
	}
	
	function fn_lineRedraw_new(val){
		lineChart.data.names({
			data1 : "RCP4.5",
			data2 : "RCP8.5"
		});
		lineChart.load({
			columns : [
			           ["data1", val[0], val[1], val[2], val[3], val[4], val[5]], 
			           ["data2", val[6], val[7], val[8], val[9], val[10], val[11]]
			           ]
		});
	}
	
</script>
<script src="/resources/billboard/d3.v5.min.js"></script>
<script src="/resources/billboard/billboard.js" type="text/javascript"></script>
<script src="/resources/openlayers/ol.js" type="text/javascript"></script>
<script type="text/javascript" src="/resources/openlayers/FileSaver.min.js"></script>
<script type="text/javascript" src="/resources/openlayers/proj4.js"></script>
<script type="text/javascript" src="/resources/openlayers/map.js"></script>
<script type="text/javascript">
var lineChart = bb.generate({
	  data: {
	  	names: {
			data1 : "RCP4.5",
			data2 : "RCP8.5"
			},
	    columns: [
		["data1", 0, 0, 0],
		["data2", 0, 0, 0]
	    ]
	  },
	  axis: {
		    x: {
		      type: "category",
		      tick:{
		            rotate: -20,
		            autorotate: true,
		            multiline: false,
		            culling: false,
		            fit: true
		          },
		    },
		    y: {
		           //min: 0,	
		           label: {
		             position: "outer-top"
		           },
		           tick: {
		               format: function(x) { return d3.format(",")(x); }
		             }
		            
		    }
	  },
	  bindto: "#lineChart"
	});

var lineRangeChart = bb.generate({
	  data: {
		//x: "x",
		names: {
			data1 : "전체",
			/* data2 : "MME5s", 
			data3 : "GRIMs", 
			data4 : "HadGEM3-RA", 
			data5 : "RegCM_v4", 
			data6 : "SNU-MM_v3", 
			data7 : "WRF_v3.4" */
			},
	    columns: [
		["data1", 0, 0, 0]
		//["x", "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
		//["data1", [150, 140, 110],[155, 130, 115],[160, 135, 120],[135, 120, 110],[180, 150, 130],[160, 135, 120],[135, 120, 110],[180, 150, 130],[160, 135, 120],[135, 120, 110],[180, 150, 130],[199, 160, 125]]
		],
	    types: {
	      data1: "area-line-range"
	    }
	  },
	  axis: {
		    x: {
		      type: "category" ,
		      categories: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ] 
		    },
		    y: {
		           min: 0,	
		           label: {
		             position: "outer-top"
		           },
		           tick: {
		               format: function(x) { return d3.format(",")(x); }
		             }
		            
		    }
	  },
	  bindto: "#AreaRangeChart"
	});
</script>
</body>
</html>