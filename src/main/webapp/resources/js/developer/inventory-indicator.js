$(function() {
	
	$(document).on("change", "#select-field", function() {
		fn_getItem($(this).val(), "all");
	});
	
	$(document).on("change", "#ipcc-1", function() {
		fn_getIpcc($(this).val(), "all");
	});
	
	$(document).on("click", "#indicator-init", function() {
		location.href = "/admin/inventory/indicator/list.do";
	});
	
	$(document).on("click", "#indicator-search", function() {
		fn_dbinfo_submit();
	});
	
	$(document).on("keydown", "#search-keyword", function(key) {
		
		if(key.keyCode == 13) {
			fn_dbinfo_submit();
		}
	});
	
	$(document).on("change", "#district-sd", function() {
		fn_districtSgg($(this).val());
		//fn_indicatorView($(".indicator-list.active").attr("id").replace("indi-", ""), $(this).val(), 1);
		fn_indicatorView($("input:hidden[name=indi_id]").val(), $(this).val(), 1);
	});
	
	$(document).on("change", "#district-sgg", function() {
		//fn_indicatorView($(".indicator-list.active").attr("id").replace("indi-", ""), $(this).val(), 1);
		fn_indicatorView($("input:hidden[name=indi_id]").val(), $(this).val(), 1);
	});
	
});

$(document).ready(function() {
	
	$("#sidoList").change(function() {
		fn_sigunguList();
	});
});

function initeventProcess(keyword, field, start, end){
	var sido = $("#sidoList option:selected").text();
	var sido_cd = $("#sidoList option:selected").val();
	var field_id = field;
	var startdate = start;
	var enddate = end;
	
	
	if(sido == '전국'){
		
		sidoList(keyword, field, 0, startdate, enddate);
	}else{
		SigunguList(keyword, field, sido_cd, startdate, enddate);
	}
	
	
}

function sidoList(keyword,field, sido, startdate, enddate){
	
	var data = {
			"sido" : sido,
			"field" : field,
			"startdate" : startdate,
			"enddate" : enddate,
			"keyword" : keyword
	};
	
	$.ajax({
		
		url: '/admin/inventory/indicator/AreaStatisticsList.do',
		dataType: 'json',
		type: 'get',
		data: data,
		success: function(data){
			
			var totalList = data.list;
			
			var page = 0;
			var pageLimit = 9;
			
			var tbody = "";
    		tbody+="<div class=\"row event-card\"  id=\"eventAreaStatisticsList\" >";
    		
    		var backKey="<i class=\"icon-file-align\"></i>지자체별 통계";
			$("#areaEventListBack").html(backKey);
			$("#areaEventListBack").css('cursor','default');
			
			for (var i = 0; i < totalList.length; i++) {
				
				if(i%pageLimit==0) page++;
				//시도별 결과 리스트에 클릭할 시에 상세 시도별로 결과 리스트를 출력시키도록 함(SigunguList함수를 타도록 선언)
				tbody += "<div class=\"col-2 mt-0 cursor-pointer pg"+page+" \" ";
				if(page==1){
					//console.log("시도-테스트3-1");
					tbody+=" style=\"display:block; margin-bottom:15px;\">";
				}else{
					//console.log("시도-테스트3-2");
					tbody+=" style=\"display:none; margin-bottom:15px;\">";
				}
				tbody += "<div class=\"card\">";
				tbody += "<div align='center' class=\"card-header park-card-header\">" + totalList[i].district_nm_union
						+ "</div>";
				tbody += "<ul class=\"list-group list-group-flush\">";
				tbody += "<li class=\"list-group-item park-card\"><p align='center' style='color:#04B404; font-size:20px;'>"
						+ totalList[i].total + "</p>";
				
						
				tbody +="</li>";
				tbody += "</ul>";
				tbody += "</div>";
				tbody += "</div>";
			} 
			
			tbody+="</div>";
    		
    	 	var eid = 'eventAreaStatisticsListParent';
    		
    		$("#"+eid).html(tbody);
    		
    		$("#"+eid+"Page").val(1);
    		$("#"+eid+"Limit").val(page);
    		$("#"+eid+"CurPage").text("1");
    		$("#"+eid+"LimitPage").text(page);
		}
	});
}

function SigunguList(keyword, field, sido, startdate, enddate) {
	var localName = name;
	
	//$("#eventAreaStatisticsListParent").html;
	
	//var startdate = start;
	//var enddate = end;
	
	var data = {
			//"LOCAL" : localName,
			"sido" : sido,
			"field" : field,
			"startdate" : startdate,
			"enddate" : enddate,
			"keyword" : keyword
			};
	
	//시군구별 지역표출 선언
	$.ajax({
		url: '/admin/inventory/indicator/eventDetailAreaStatisticsList.do',
		dataType: 'json',
		type: 'get',
		data: data,
		success:function(data){
			
			var eventDetailAreaStatisticsList_Total = data.list;

    		var page = 0;
    		var pageLimit = 9;

    		var tbody = "";
    		tbody+="<div class=\"row event-card\"  id=\"eventDetailAreaStatisticsList\" >";
			
    			var backKey="<i id='areaBackKey' class=\"icon-arrow-caret-left\"></i>시,군,구별 상세통계";
    			//뒤로 가기 아이콘이 있는 부분을 클릭 시에 시도리스트로 전환
    			//$("#areaEventListBack").attr('onclick','javascript:backArea("0");');
    			$("#areaEventListBack").html(backKey);
    			$("#areaEventListBack").css('cursor','default');
    			

				//console.log("시군구-테스트2");
    			for (var i = 0; i < eventDetailAreaStatisticsList_Total.length; i++) {
    				
    				if(i%pageLimit==0) page++;
    				tbody += "<div class=\"col-4 mt-0 cursor-pointer pg"+page+" \"";
    				if(page==1){
    					//console.log("시군구-테스트3-1");
    					tbody+=" style=\"display:block; margin-bottom:15px;\">";
    				}else{
    					//console.log("시군구-테스트3-2");
    					tbody+=" style=\"display:none; margin-bottom:15px;\">";
    				}
    				tbody += "<div class=\"card\">";
    				tbody += "<div align='center' class=\"card-header park-card-header\">" + eventDetailAreaStatisticsList_Total[i].district_nm_union
    						+ "</div>";
    				tbody += "<ul class=\"list-group list-group-flush\">";
    				tbody += "<li class=\"list-group-item park-card\"><p align='center' style='color:#013ADF; font-size:20px;'>"
    						+ eventDetailAreaStatisticsList_Total[i].total + "</p>";
    				
    						
    				tbody +="</li>";
    				tbody += "</ul>";
    				tbody += "</div>";
    				tbody += "</div>";
    			} 
    		tbody+="</div>";
    		
    	 	var eid = 'eventAreaStatisticsListParent';
    		
    		$("#"+eid).html(tbody);
    		
    		$("#"+eid+"Page").val(1);
    		$("#"+eid+"Limit").val(page);
    		$("#"+eid+"CurPage").text("1");
    		$("#"+eid+"LimitPage").text(page);
		},
		error:function(e){  
            alert(e.responseText);  
		}
	});
	
	
	
};

function fn_sigunguList(){
	var sidoCode = $("#sidoList option:selected").val();
	var dataSet = {"sidoCode" : sidoCode};
	if(sidoCode!=null && sidoCode!=''){
	$.ajax({
		url:'/admin/climate/estimation/sigunguList.do',
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
function fn_districtSgg(district_info) {
	
	$("#district-sgg").empty();
	
	$.ajax({
		url: "/admin/inventory/indicator/districtSgg.do",
		type: "get",
		data: {district_info: district_info},
		dataType: "json",
		success: function(data) {
			
			var sggList = data.sggList;
			var $option;
			
			$option = $('<option value="all">전체</option>');
			$("#district-sgg").append($option);
			
			for(var i = 0; i < sggList.length; i++) {
				$option = $('<option value="' + sggList[i].district_cd + '">' + sggList[i].district_nm + '</option>');
				$("#district-sgg").append($option);
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $option;
			
			$option = $('<option value="none">시군구 불러오기 실패</option>');
			$("#district-sgg").append($option);
		},
		complete: function(data) {
			
		}
	});
}

function fn_page(page) {
	
	$("input:hidden[name=page]").val(page);
	
	$("#listFrm").submit();
}

function fn_dbinfo_submit() {
	
	var keyword = $("#search-keyword").val().trim();
	var field = $("#select-field option:selected").val();
	var item = $("#select-item option:selected").val();
	var ipcc_1 = $("#ipcc-1 option:selected").val();
	var ipcc_2 = $("#ipcc-2 option:selected").val();
	var indi_group = $("#select-group option:selected").val();
	var sido = $("#sidoList option:selected").val();
	var sigungu = $("#sigunguList option:selected").val();
	var startdate = $("#startdate").val();
	var enddate = $("#enddate").val();
	
	console.log(startdate);
	console.log(enddate);
	
	if(ipcc_2 == "none") {
		ipcc_2 = "all";
	}
	
	if(keyword.length > 1) {
		
		$("input:hidden[name=keyword]").val(keyword);
	}
	
	$("input:hidden[name=field_id]").val(field);
	$("input:hidden[name=item_id]").val(item);
	$("input:hidden[name=ipcc_1]").val(ipcc_1);
	$("input:hidden[name=ipcc_2]").val(ipcc_2);
	$("input:hidden[name=indi_group]").val(indi_group);
	$("input:hidden[name=sido]").val(sido);
	$("input:hidden[name=sigungu]").val(sigungu);
	$("input:hidden[name=startdate]").val(startdate);
	$("input:hidden[name=enddate]").val(enddate);
	
	
	$("#listFrm").submit();
	initeventProcess(field);
}

function fn_indicatorInfo(indi_id,district_info, page ) {
	
	$("input:hidden[name=indi_id]").val(indi_id);
	$("input:hidden[name=district_info]").val(district_info);
	//var sd = $("#sidoList option:selected").val();
	//var sgg = $("#sigunguList option:selected").val();
	
	fn_indicatorView(indi_id, page, district_info);
	
	fn_indicatorItem(indi_id, 1);
}

function fn_indicatorView(indi_id, page, district_info) {
	$("#indicator-data-table > tbody").empty();
	$("#meta-list-table > tbody").empty();
	$("#info-field-nm").empty();
	$("#info-indi-nm").empty();
	$("#info-indi-ipcc1").empty();
	$("#info-indi-ipcc2").empty();
	$("#info-indi-unit").empty();
	$("#info-indi-cont").empty();
	$("#info-indi-acct").empty();
	$("#info-indi-meth").empty();
	
	$.ajax({
		url: "/admin/inventory/indicator/indicatorInfo.do",
		type: "get",
		data: {page: page, indi_id: indi_id, district_info: district_info},
		dataType: "json",
		success: function(data) {
			
			var indicatorListInfo = data.indicatorListInfo;
			var indicatorListInfoItemNm = data.indicatorListInfoItemNm;
			var indicatorDataList = data.indicatorDataList;
			var metaList = data.metaList;
			var pageInfo = data.pageInfo;

			if(indicatorListInfo.field_nm == null){
				$("#info-field-nm").text("아직 사용되지 않은 지표입니다.");
			}else{
			$("#info-field-nm").text(indicatorListInfo.field_nm);
			}
			$("#info-indi-nm").text(indicatorListInfo.indi_nm);
			$("#info-indi-ipcc1").text(indicatorListInfo.ipcc_large_nm);
			$("#info-indi-ipcc2").text(indicatorListInfo.ipcc_small_nm);
			$("#info-indi-unit").text(indicatorListInfo.indi_unit);
			$("#info-indi-cont").text(indicatorListInfo.indi_construct_nm);
			$("#info-indi-acct").text(indicatorListInfo.indi_account);
			
			var $tr;
			var $td;
			
			for(var i = 0; i < indicatorDataList.length; i++) {
				
				$tr = $('<tr></tr>');
				
				$td = $('<td>' + indicatorDataList[i].rnum + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + indicatorDataList[i].district_nm + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + indicatorDataList[i].indi_val + '</td>');
				$tr.append($td);
				
				$("#indicator-data-table > tbody").append($tr);
			}
			
			fn_twoParameterPage("indicator-data-page", "fn_indicatorInfo", indi_id, district_info, pageInfo);
			
			/*
			for(var i = 0; i < metaList.length; i++) {
				
				$tr = $('<tr></tr>');
				
				$td = $('<td><a href="/admin/inventory/meta/list.do?activeOffcanvas=' + metaList[i].meta_id + '" target="_blank">' + metaList[i].meta_nm + '</a></td>');
				$tr.append($td);
				
				$td = $('<td>' + metaList[i].meta_offer_org + '</td>');
				$tr.append($td);
				
				$("#meta-list-table > tbody").append($tr);
			}*/
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			$("#indicator-data-table > tbody").append($('<tr><td colspan="3">지표 정보를 불러 올 수 없습니다.</td></td>'));
			$("#meta-list-table > tbody").append($('<tr><td colspan="2">원시 자료를 불러 올 수 없습니다.</td></td>'));
		},
		complete: function(data) {
			
		}
	});
	
}

function fn_getIpcc(ipcc_id, ipcc_2) {
	
	$("#ipcc-2").empty();
	
	$.ajax({
		url: "/admin/inventory/indicator/getIpcc.do",
		type: "get",
		data: {ipcc_id: ipcc_id},
		dataType: "json",
		success: function(data) {
			
			var ipccList = data.ipccList;
			var $option;
			
			$option = $('<option value="all">전체</option>');
			$("#ipcc-2").append($option);
			
			for(var i = 0; i < ipccList.length; i++) {
				
				$option = $('<option value="' + ipccList[i].code_id + '">' + ipccList[i].code_nm + '</option>');
				
				$("#ipcc-2").append($option);
			}
			
			$("#ipcc-2 option[value=" + ipcc_2 + "]").prop("selected", true);
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $option = $('<option value="none">IPCC 소분류를 불러올 수 없습니다.</option>');
			
			$("#ipcc-2").append($option);
		},
		complete: function(data) {
			
		}
	});
}

function fn_getItem(field_id, item_id) {
	
	$("#select-item").empty();
	
	$.ajax({
		url: "/admin/inventory/indicator/getItem.do",
		type: "get",
		data: {field_id: field_id},
		dataType: "json",
		success: function(data) {
			
			var itemList = data.itemList;
			var $option;
			
			$option = $('<option value="all">전체</option>');
			$("#select-item").append($option);
			
			for(var i = 0; i < itemList.length; i++) {
				
				$option = $('<option value="' + itemList[i].item_id + '">' + itemList[i].item_nm + '</option>');
				
				$("#select-item").append($option);
			}
			
			$("#select-item option[value=" + item_id + "]").prop("selected", true);
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $option = $('<option value="none">평가 항목을 불러올 수 없습니다.</option>');
			
			$("#select-item").append($option);
		},
		complete: function(data) {
			
		}
	});
}

function fn_indicatorItem(indi_id, page) {
	$("#item-list-table > tbody").empty();
	
	$.ajax({
		url: "/admin/inventory/indicator/indicatorItem.do",
		type: "get",
		data: {indi_id: indi_id, page: page},
		dataType: "json",
		success: function(data) {
			
			var itemList = data.itemList;
			var pageInfo = data.pageInfo;
			var $tr;
			var $td;
			
			for(var i = 0; i < itemList.length; i++) {
				
				$tr = $('<tr  onclick="window.open(\'/admin/inventory/item/list.do?activeOffcanvas=' + itemList[i].item_id + '\')"></tr>');
				
				$td = $('<td>' + itemList[i].field_nm_kor + '</td>');
				$tr.append($td);
				//<a href="/admin/inventory/item/list.do?activeOffcanvas=' + itemList[i].item_id + '" target="_blank">
				$td = $('<td>' + itemList[i].item_nm + '</td>');
				$tr.append($td);
				
				$("#item-list-table > tbody").append($tr);
			}
			
			if(itemList.length < 1) {
				$("#item-list-table > tbody").append($('<tr><td colspan="2">항목 정보가 없습니다.</td></tr>'));
			}
			
			fn_oneParameterPage("item-list-page", "fn_indicatorItem", indi_id, pageInfo);
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			$("#item-list-table > tbody").append($('<tr><td colspan="2">항목 정보를 불러 올 수 없습니다.</td></tr>'));
		},
		complete: function(data) {
			
		}
	});
}

function downloadIndicator() {
	
	//var indi_id = $(".indicator-list.active").attr("id").replace("indi-", "");
	var indi_id = $("input:hidden[name=indi_id]").val();
	var district_info = $("input:hidden[name=district_info]").val();
	
	var params = "indi_id=" + indi_id + "&district_info=" + district_info;
	
	location.href = "/admin/inventory/indicator/downloadIndicator.do?" + params;
}