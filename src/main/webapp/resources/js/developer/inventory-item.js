
$(function() {
	
	$(document).on("click", "#item-search", function() {
		
		var keyword = $("#search-keyword").val().trim();
		var field = $("#select-field option:selected").val();
		var sido = $("#sidoList option:selected").val();
		var sigungu = $("#sigunguList option:selected").val();
		//검색 조건에서 선택한 시작일, 종료일 추가(2019.10.30, 최진원)
		var startdate = $("#startdate").val();
		var enddate = $("#enddate").val();
		
		if(keyword.length > 1) {
			
			$("input:hidden[name=keyword]").val(keyword);
		}

		$("input:hidden[name=sido]").val(sido);
		$("input:hidden[name=sigungu]").val(sigungu);
		$("input:hidden[name=field]").val(field);
		//검색 조건에서 선택한 시작일, 종료일 추가(2019.10.30, 최진원)
		$("input:hidden[name=startdate]").val(startdate);
		$("input:hidden[name=enddate]").val(enddate);
		
		$("#listFrm").submit();
		initeventProcess();
	});

	$(document).on("click", "#item-init", function() {
		
		$("input:hidden[name=keyword]").val("");
		$("input:hidden[name=field]").val("");
		//검색 조건에 시작일, 종료일 초기화(2019.10.30, 최진원)
		$("input:hidden[name=startdate]").val("");
		$("input:hidden[name=enddate]").val("");
		
		$("#listFrm").submit();
	});
	
	$(document).on("change", "#select-sector", function() {
		//fn_itemIndicator($(this).val(), $(".item-list.active").attr("id").replace("item-", ""));
		fn_itemIndicator($(this).val(), $("input:hidden[name=item_id]").val());
	});
	
	$(document).on("keydown", "#search-keyword", function(key) {
		
		if(key.keyCode == 13) {
			
			var keyword = $(this).val().trim();
			var field = $("#select-field option:selected").val();
			//검색 조건에서 선택한 시작일, 종료일 추가(2019.10.30, 최진원)
			var startdate = $("#startdate").val();
			var enddate = $("#enddate").val();
			
			if(keyword.length > 1) {
				
				$("input:hidden[name=keyword]").val(keyword);
			}
			
			$("input:hidden[name=field]").val(field);
			//검색 조건에서 선택한 시작일, 종료일 추가(2019.10.30, 최진원)
			$("input:hidden[name=startdate]").val(startdate);
			$("input:hidden[name=enddate]").val(enddate);
			
			$("#listFrm").submit();
		}
	});
});
//메뉴 접속 시에 초기 시작(2019.10.11, 최진원)
function initeventProcess(keyword, field, start,end) {
	var sido = $("#sidoList option:selected").text();
	var sido_cd = $("#sidoList option:selected").val();
	
	var startdate = start;
	var enddate = end;
	
	console.log("keyword : " + keyword);
	
	if(sido == '전국'){
		SidoList(keyword, field, 0, startdate,enddate);	
	}else{
		SigunguList(keyword, field, sido_cd, startdate, enddate);
	}
	
}


//통계별 출력(시도별)(2019.10.20, 최진원)
//시도별 리스트 확인
function SidoList(keyword, field, idx, start, end) {
		$("#eventAreaStatisticsListParent").html;
		
		var startdate = start;
		var enddate = end;
		
		var data = {
			"SIDO" : idx,
			"field" : field,
			"startdate" : startdate,
			"enddate" : enddate,
			"keyword" : keyword
		};

		//시도별 지역표출 선언
		$.ajax({
			url: '/admin/inventory/item/eventAreaStatisticsList.do',
			dataType: 'json',
			type: 'get',
			data: data,
			success:function(data){
				var eventAreaStatisticsList_Total = data.eventAreaStatisticsList_Total;

        		var page = 0;
        		var pageLimit = 9;

        		var tbody = "";
        		tbody+="<div class=\"row event-card\"  id=\"eventAreaStatisticsList\" >";

					
        			var backKey="<i class=\"icon-file-align\"></i>지자체별 통계";
        			$("#areaEventListBack").html(backKey);
        			$("#areaEventListBack").css('cursor','default');
        			

        		
    				//console.log("시도-테스트2");
        			for (var i = 0; i < eventAreaStatisticsList_Total.length; i++) {
        				
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
        				tbody += "<div align='center' class=\"card-header park-card-header\">" + eventAreaStatisticsList_Total[i].district_nm_union
        						+ "</div>";
        				tbody += "<ul class=\"list-group list-group-flush\">";
        				tbody += "<li class=\"list-group-item park-card\"><p align='center' style='color:#04B404; font-size:20px;'>"
        						+ eventAreaStatisticsList_Total[i].total + "</p>";
        				
        						
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
}

//시도별 클릭 시 해당 시군구별 통계 표출(2019.10.28, 최진원)
function SigunguList(keyword, field, sido, start, end) {
	var sido = sido;
	
	$("#eventAreaStatisticsListParent").html;
	
	var startdate = start;
	var enddate = end;
	
	var data = {
			"sido" : sido,
			"field" : field,
			"startdate" : startdate,
			"enddate" : enddate,
			"keyword" : keyword
			};
	
	//시군구별 지역표출 선언
	$.ajax({
		url: '/admin/inventory/item/eventDetailAreaStatisticsList.do',
		dataType: 'json',
		type: 'get',
		data: data,
		success:function(data){
			
			var eventDetailAreaStatisticsList_Total = data.eventDetailAreaStatisticsList_Total;
			//console.log("시군구리스트 확인 : " + eventDetailAreaStatisticsList_Total);
			//console.log("시군구 배열 길이 : " + eventDetailAreaStatisticsList_Total.length);

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
    					
    					tbody+=" style=\"display:block; margin-bottom:15px;\">";
    				}else{
    					
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
/**
 * 항목 목록 클릭 시 상세보기
 * @param item_id
 * @returns
 */
function fn_itemView(item_id, item_nm, ce_wei, cs_wei, aa_wei) {

	$("input:hidden[name=item_id]").val(item_id);
	$("#ce_wei").text(ce_wei);
	$("#cs_wei").text(cs_wei);
	$("#aa_wei").text(aa_wei);
	$("#select-sector").val("SEC01").prop("selected", true);
	
	/**
	 * 차트를 그려 준 후 패널을 열게 되면 차트가 보이지 않으므로
	 * 패널을 오픈 한 다음 차트를 그려준다.
	 */
	offcanvasOpen();
	
	$(".indicator-title").text(item_nm);
	
	fn_redrawChart(item_nm, ce_wei, cs_wei, aa_wei);
	
	fn_itemIndicator("SEC01", item_id);
}

function fn_itemIndicator(sector_id, item_id) {
	
	$("#indicatorInfo-table > tbody").empty();
	
	$.ajax({
		url: "/admin/inventory/item/indicatorInfo.do",
		type: "get",
		data: {sector_id: sector_id, item_id: item_id},
		dataType: "json",
		success: function(data) {
			
			var indicatorInfoList = data.indicatorInfoList;
			var $tr;
			var $td;
			
			for(var i = 0; i < indicatorInfoList.length; i++) {
				
				$tr = $('<tr></tr>');
				/*<a href="/member/base/dbinfo/indicator/list.do?activeOffcanvas=' + indicatorInfoList[i].indi_id + '" target="_blank">*/
				$td = $('<td>' + indicatorInfoList[i].indi_nm + '</a></td>');
				$tr.append($td);
				
				$td = $('<td>' + indicatorInfoList[i].indi_construct + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + indicatorInfoList[i].indi_val_weight + '</td>');
				$tr.append($td);
				
				$("#indicatorInfo-table > tbody").append($tr);
			}
			
			if(indicatorInfoList.length < 1) {
				
				$tr = $('<tr></tr>');
				$td = $('<td colspan="3">해당 항목에 지표가 없습니다.</td>');
				
				$("#indicatorInfo-table > tbody").append($tr.append($td));
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $tr = $('<tr></tr>');
			var $td = $('<td colspan="3">지표 정보를 불러 올 수 없습니다.</td>');
			
			$("#indicatorInfo-table > tbody").append($tr.append($td));
		},
		complete: function(data) {
			
		}
	});
}

/**
 * 방사형 차트 다시 그리기
 * @param name
 * @param val_1
 * @param val_2
 * @param val_3
 * @returns
 */
function fn_redrawChart(name, val_1, val_2, val_3) {
	
	chart.data.names({
		data1 : name
	});
	
	chart.load({
		columns: [
			["data1", val_1, val_2, val_3]
		]
	});
}

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


function fn_page(page) {
	
	$("input:hidden[name=page]").val(page);
	
	$("#listFrm").submit();
}

function downloadIndicatorInfo() {
	
	var item_id = $(".item-list.active").attr("id").replace("item-", "");
	
	location.href = "/admin/inventory/item/downloadIndicatorInfo.do?item_id=" + item_id;
}


function fn_itemInfo(item_id) {
	
	$.ajax({
		url: "/admin/inventory/item/itemInfo.do",
		type: "get",
		data: {item_id: item_id},
		dataType: "json",
		success: function(data) {
			
			var itemInfo = data.itemInfo;
			
			fn_itemView(itemInfo.item_id, itemInfo.item_nm, itemInfo.ce_weight, itemInfo.cs_weight, itemInfo.aa_weight);
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
		},
		complete: function(data) {
			
		}
	});
}

