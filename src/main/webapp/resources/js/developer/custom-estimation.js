$(function() {
	
	/**
	 * 분야 선택 이벤트
	 */
	$(document).on("change", "#select-field", function() {
		
		$("#field-name").text($("select[id=select-field] option:selected").text() + " 분야 평가항목");
		
		$("#option-item").text("");
		
		fn_itemList(1);
	});
	
	
	$(document).on("click", ".close", function() {
		$("#estimation-btn").html("실행");
	});
	
	
	/**
	 * 항목 선택 이벤트
	 */
	$(document).on("click", ".item-info", function() {
		
		$(".item-info").removeClass("active");
		$(this).addClass("active");
		
		$("#option-item").text($(this).text());
	});
	
	
	
	/**
	 * 시도 선택 이벤트
	 */
	$(document).on("change", "#select-sd", function() {
		
		var val = $(this).val();
		
		if(val != "none") {
			
			fn_district(val);
			
			$("#option-sd").text($("#select-sd option:selected").text());
			
		} else {
			$("#option-sd").text("");
		}
		
		$("#option-sgg").text("");
	});
	
	
	
	/**
	 * 시군구 선택 이벤트
	 */
	$(document).on("change", "#select-sgg", function() {
		
		var val = $(this).val();
		
		if(val != "none") {
			
			$("#option-sgg").text($("#select-sgg option:selected").text());
			
		} else {
			$("#option-sgg").text("");
		}
	});
	
	/**
	 * 옵션 선택 이벤트
	 */
	$(document).on("change", ".estimation-option", function() {
		fn_option($(this).attr("id"));
	});
	
	$(document).on("change", "#select-sector", function() {
		fn_refIndicatorList();
	});
	
	$(document).on("change", "#select-sector-base", function() {
		fn_refIndicatorBase();
	});
	
	$(document).on("change", "#select-indicator-base", function() {
		
		if($(this).val() != "none") {
			fn_refIndicatorInfo($(this).val(), 1);
		}
	});
	
	//SECTION(과거관측자료, RCP4.5, RCP8.5) 선택 이벤트
	$(document).on("change", "#select-rcp", function() {
		
		$("#select-model").empty();
		$("#select-year").empty();
		
		fn_modelList();
	});

	//MODEL (HadGEM, MME5s, MKPRISM) 선택 이벤트
	$(document).on("change", "#select-model", function() {
		
		$("#select-year").empty();
		
		fn_yearList();
		//fn_yearList($("#select-rcp option:selected").val(), $(this).val());
	});
	
	$(document).on("click", "#estimation-btn", function() {
		
		if($(".item-info.active").length > 0) {
			$(".estimation-view").removeClass("d-none");
			fn_estimation($(".item-info.active").attr("id").replace("info-", ""), 1);
			moveSigungu("two");
		} else {
			fn_alert("경고", "취약성 평가를 실행 할 항목을 선택 해야 합니다.", "error");
		}
		
	});
	
	$(document).on("click", ".offcanvasCloseBtn", function() {
		
		$("#custom_esti_map > .ol-viewport > canvas").css("width", "100%");	
	});
});

function fn_itemClick(tag){
	var data = tag.attr('data');
	var check = data.substring(6)*1;
	$("#itemList a").removeClass('active');
	tag.addClass('active');
	console.log(data);
	$("#selectItem").val(data);
	fn_sectionList();
	offcanvasClose();
}

function fn_sectionList(){
	var item = $("#selectItem").val();
	var check = item.substring(6)*1;
	var dataSet = {"item" : item};
	
	$.ajax({
		url:'/member/base/custom/estimation/sectionList.do', 
		dataType :"json",
		type : "get",
		data : dataSet,
		success:function(result){
			$("#select-rcp").empty();
			var list = result.list;
			var html = '';
          	for(var i in list){
        	   if(check == 55 && list[i].rcpId =="RC001"){
        		   continue;
          		}
        	   html += "<option value='"+list[i].rcpId+"'>"+list[i].rcpNm+"</option>";
         	}
			$("#select-rcp").append(html);
			//fn_modelList();
         }
	});
}

function fn_modelList(){
	var item = $("#selectItem").val();
	var section = $("#select-rcp option:selected").val();
	
	var dataSet = {"item":item,"section" : section};
	
	$.ajax({
		 url:'/member/base/custom/estimation/modelList.do',
		 dataType :"json",
		 type : "get",
		 data : dataSet,
		 success:function(result){
			 $("#select-model").empty();
			var list = result.list;
			var html = '';
          	for(var i in list){
        	   html += "<option value='"+list[i].mdlId+"'>"+list[i].mdlNm+"</option>";
         	}
			$("#select-model").append(html);
           fn_yearList();
         }
	});
}

function fn_yearList(){
	var item = $("#selectItem").val();
	var check = item.substring(6)*1;
	var section = $("#select-rcp option:selected").val();
	var model = $("#select-model option:selected").val();
	var dataSet = {
			"item" : item,
			"model" : model,
			"section" : section
			};
	$.ajax({
		 url:'/member/base/custom/estimation/yearList.do',
		 dataType :"json",
		 type : "get",
		 data : dataSet,
		 success:function(result){
			$("#select-year").empty();
			var list = result.list;
			var html = '';
           	for(var i in list){
           		if(check == 55 && list[i].yearId =="YC002"){
         		   continue;
           		}
        	   html += "<option value='"+list[i].yearId+"'>"+list[i].yearNm+"</option>";
           }
           	$("#select-year").append(html);
         }
	});
}

function fn_climateOption(mdl_id, rcp_id) {
	
	var params;
	var $element;
	
	//SECTION(과거관측자료, RCP4.5, RCP8.5) 선택 이벤트
	if(rcp_id == null) {
		
		params = {mdl_id: mdl_id};
		$element = $("#select-rcp");
		
	}
	//MODEL (HadGEM, MME5s, MKPRISM) 선택 이벤트
	else {
		
		params = {mdl_id: mdl_id, rcp_id: rcp_id};
		$element = $("#select-year");
	}
	
	$.ajax({
		url: "/member/base/custom/estimation/climateOption.do",
		type: "get",
		data: params,
		dataType: "json",
		success: function(data) {
			
			var optionList = data.optionList;
			var $option;
			var temp_rcp;
			
			for(var i = 0; i < optionList.length; i++) {
				
				if(rcp_id == null && i == 0) {
					temp_rcp = optionList[i].option_id;
				}
				
				$option = $('<option value="' + optionList[i].option_id + '">' + optionList[i].option_nm + '</option>');
				$element.append($option);
			}
			
			if(rcp_id == null) {
				fn_climateOption(mdl_id, temp_rcp);
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			$element.append($('<option value="none">option call failed</option>'));
		},
		complete: function(data) {
			
		}
	});
}

function fn_refIndicatorInfo(indi_id, page) {
	
	if($("#select-rcp option:selected").val() == "none") {
		fn_alert("경고", "RCP를 선택 해야 합니다.", "error");
		return false;
	}
	
	if($("#select-year option:selected").val() == "none") {
		fn_alert("경고", "연대를 선택 해야 합니다.", "error");
		return false;
	}
	
	if($("#select-sd option:selected").val() == "none") {
		fn_alert("경고", "시/도를 선택 해야 합니다.", "error");
		return false;
	}
	
	if($("#select-sgg option:selected").val() == "none") {
		fn_alert("경고", "시/군/구를 선택 해야 합니다.", "error");
		return false;
	}
	
	var mdl_id = $("#select-model option:selected").val();
	var scen_year = $("#select-year option:selected").val();
	var scen_section = $("#select-rcp option:selected").val();
	var district_info;
	
	if($("#select-sgg option:selected").val() == "all") {
		district_info = $("#select-sd option:selected").val();
	} else {
		district_info = $("#select-sgg option:selected").val();
	}
	
	$("#ref-indicator-table > tbody").empty();
	$("#ref-indicator-table > tfoot").remove();
	
	$.ajax({
		url: "/member/base/custom/estimation/refIndicatorInfo.do",
		type: "get",
		data: {page: page, indi_id: indi_id, mdl_id: mdl_id, scen_year: scen_year, scen_section: scen_section, district_info: district_info},
		dataType: "json",
		success: function(data) {
			
			var indicatorInfoList = data.indicatorInfoList;
			var indicatorInfo = data.indicatorInfo;
			var pageInfo = data.pageInfo;
			var msg = data.msg;
			var $tr;
			var $td;
			
			for(var i = 0; i < indicatorInfoList.length; i++) {
				
				$tr = $('<tr></tr>');
				
				$td = $('<td>' + indicatorInfoList[i].rnum + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + indicatorInfoList[i].district_nm + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + fn_addComma(Number(indicatorInfoList[i].indi_val)) + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + indicatorInfoList[i].indi_year + '</td>');
				$tr.append($td);
				
				$("#ref-indicator-table > tbody").append($tr);
			}
			
			if(msg != "N") {
				$("#ref-indicator-table").append($('<tfoot><tr><td colspan="4">' + msg + '</td></tr></tfooy>'));
			}
			
			$("#base-indicator-nm").text(indicatorInfo.indi_nm + "(" + indicatorInfo.indi_unit + ")");
			
			fn_oneParameterPage("ref-indicator-page", "fn_refIndicatorInfo", indi_id, pageInfo);
			
			if(indicatorInfoList.length < 1) {
				$("#ref-indicator-table > tbody").append($('<tr><td colspan="4">해당 지표의 정보가 없습니다.</td></tr>'));
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			$("#ref-indicator-table > tbody").append($('<tr><td colspan="4">지표 정보 호출 불가</td></tr>'));
		},
		complete: function(data) {
			
		}
	});
}

function fn_refIndicatorBase() {
	
	var item_id = $(".item-info.active").attr("id").replace("info-", "");
	var sector_id = $("#select-sector-base option:selected").val();
	var mdl_id = $("#select-model option:selected").val();
	var scen_year = $("#select-year option:selected").val();
	var scen_section = $("#select-rcp option:selected").val();
	
	$("#select-indicator-base").empty();
	
	$.ajax({
		url: "/member/base/custom/estimation/refIndicator.do",
		type: "get",
		data: {item_id: item_id, sector_id: sector_id, mdl_id: mdl_id, scen_year: scen_year, scen_section: scen_section},
		dataType: "json",
		success: function(data) {
			
			var referenceIndiList = data.referenceIndiList;
			
			var $option;
			
			var indi_base_info;
			
			for(var i = 0; i < referenceIndiList.length; i++) {
				
				if(i == 0) {
					indi_base_info = referenceIndiList[i].indi_id;
				}
				
				$option = $('<option value="' + referenceIndiList[i].indi_id + '">' + referenceIndiList[i].indi_nm + '</option>');
				$("#select-indicator-base").append($option);
			}
			
			if(referenceIndiList.length > 0) {
				fn_refIndicatorInfo(indi_base_info, 1);
			} else {
				$("#ref-indicator-table > tbody").empty();
				$("#ref-indicator-table > tbody").append($('<tr><td colspan="4">데이터가 없습니다.</td></tr>'));
			}
		},
		beforeSend: function() {
			$(".display").css("display", "block");
		},
		error: function(xhr, status, error) {
			
		},
		complete: function(data) {
			$(".display").css("display", "none");
		}
	});
}

function fn_refIndicatorList() {
	
	var item_id = $(".item-info.active").attr("id").replace("info-", "");
	var sector_id = $("#select-sector option:selected").val();
	var mdl_id = $("#select-model option:selected").val();
	var scen_year = $("#select-year option:selected").val();
	var scen_section = $("#select-rcp option:selected").val();
	
	$("#ref-indi-table > tbody").empty();
	
	$.ajax({
		url: "/member/base/custom/estimation/refIndicator.do",
		type: "get",
		data: {item_id: item_id, sector_id: sector_id, mdl_id: mdl_id, scen_year: scen_year, scen_section: scen_section},
		dataType: "json",
		success: function(data) {
			
			var referenceIndiList = data.referenceIndiList;
			
			var $tr;
			var $td;
			
			for(var i = 0; i < referenceIndiList.length; i++) {
				
				$tr = $('<tr></tr>');
				
				$td = $('<td>' + referenceIndiList[i].indi_nm + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + referenceIndiList[i].indi_construct + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + referenceIndiList[i].indi_val_weight + '</td>');
				$tr.append($td);
				
				$("#ref-indi-table > tbody").append($tr);
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
		},
		complete: function(data) {
			
		}
	});
}

function fn_estimation22(item_id, page) {
	offcanvasOpen();
	
	$(".map-view").append('<div id="custom_esti_map" style="width: 100%; position: relative;"><div class="ol-custom ol-unselectable ol-control ol-custom-esti-map">'
			+ '<div id="map-legend" style="display: none; margin-bottom:10px"><div class="legend-title">범례 TEST</div><div class="legend-scale">'
			+ '<ul class="legend-labels"><li><span style="background: #80B1D3; border: 1px solid #999;"></span>white</li></ul>'
			+ '</div></div></div></div>');
	
	alert("aaa");
	findMap();
}

var vulAssessmentArr;

function fn_estimation(item_id, page) {
	
	if($("#select-model option:selected").val() == "none") {
		return false;
	}
	
	if($("#select-rcp option:selected").val() == "none") {
		return false;
	}
	
	if($("#select-year option:selected").val() == "none") {
		return false;
	}
	
	if($("#select-sd option:selected").val() == "none") {
		return false;
	}
	
	if($("#select-sgg option:selected").val() == "none") {
		return false;
	}
	
	var mdl_id = $("#select-model option:selected").val();
	var scen_year = $("#select-year option:selected").val();
	var scen_section = $("#select-rcp option:selected").val();
	var district_info;
	
	if($("#select-sgg option:selected").val() == "all") {
		district_info = $("#select-sd option:selected").val();
	} else {
		district_info = $("#select-sgg option:selected").val();
	}
	
	$("#estimation-page").empty();
	$("#ref-indi-table > tbody").empty();
	$("#select-indicator-base").empty();
	
	$("#option-title").text("[" + $("#select-sd option:selected").text() + " " + $("#select-sgg option:selected").text() + " 의 " + $(".item-info.active").text() + " 평가 도출 내역]");
	$(".item-title").text($(".item-info.active").text());
	
	$.ajax({
		url: "/member/base/custom/estimation/vulAssessment.do",
		type: "get",
		data: {page: page, item_id: item_id, mdl_id: mdl_id, scen_year: scen_year, scen_section: scen_section, district_info: district_info},
		dataType: "json",
		success: function(data) {
			
			var referenceIndiList = data.referenceIndiList;
			var vulAssessmentList = data.vulAssessmentList;
			var sectorWeightList = data.sectorWeightList;
			var indicatorInfo = data.indicatorInfo;
			var pageInfo = data.pageInfo;
			var totalCount = data.totalCount;
			var indi_id = "none";
			var layer_val = new Array();
			var layer_cd = new Array();
			
			$("._clim").text(sectorWeightList.ce_weight);
			$("._sens").text(sectorWeightList.cs_weight);
			$("._adap").text(sectorWeightList.aa_weight);
			
			var $tr;
			var $td;
			var $option;
			
			for(var i = 0; i < referenceIndiList.length; i++) {
				
				$tr = $('<tr></tr>');
				
				$td = $('<td>' + referenceIndiList[i].indi_nm + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + referenceIndiList[i].indi_construct + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + referenceIndiList[i].indi_val_weight + '</td>');
				$tr.append($td);
				
				$("#ref-indi-table > tbody").append($tr);
				
				/**
				 * 첫 검색 시 refenceIndiList의 첫번째 인덱스의 지표 정보를 조회 하므로 첫번째 인덱스의 지표를 선택 한다.
				 */
				if(i == 0) {
					
					indi_id = referenceIndiList[i].indi_id;
					$option = $('<option value="' + referenceIndiList[i].indi_id + '" selected>' + referenceIndiList[i].indi_nm + '</option>');
					$("#base-indicator-nm").text(indicatorInfo.indi_nm + "(" + indicatorInfo.indi_unit + ")");
					
				} else {
					
					$option = $('<option value="' + referenceIndiList[i].indi_id + '">' + referenceIndiList[i].indi_nm + '</option>');
				}
				
				$("#select-indicator-base").append($option);
			}
			
			$("#select-sector option[value=SEC01]").prop("selected", true);
			$("#select-sector-base option[value=SEC01]").prop("selected", true);
			
			//offcanvasOpen();
			
			/**
			 * IE 에서 offcanvas-right-open 이 visible 되었을때에도
			 * 지도가 줄어들지 않는 이유는
			 * 추측: map-viewer 클래스로 지정해 둔 div의 width 속성값이
			 * IE에서는 offcanvas-right-open이 열린 뒤에도 줄어든 map-viewer 크기를 반영하지 않는다.(크롬에서는 잘 됨)
			 * 따라서 원래 크기대로 지도를 그려주어 생기는 문제로 추측된다.
			 * 그래서 브라우저가 IE일때를 위해 체크하여 크기를 조정해준다.
			 */
			var agent = navigator.userAgent.toLowerCase();
			
			if ((navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') > -1) || (agent.indexOf("msie") > -1)) {
				
				if($(".offcanvas-right-open").hasClass("active")) {
					
					$("#custom_esti_map > .ol-viewport > canvas").css("width", $(".map-viewer").width() + "px");
					$("#custom_esti_map > .ol-viewport > canvas").css("height", "700px");
					
					updateMap();
					
				} else {
					
					offcanvasOpen();
					
					var width = ($(".map-viewer").width() - $(".offcanvas-right-open").width());
					
					$("#custom_esti_map > .ol-viewport > canvas").css("width", width + "px");
					$("#custom_esti_map > .ol-viewport > canvas").css("height", "700px");
					
					updateMap();
					
				}
			} else {
				
				offcanvasOpen();
				
				$("#custom_esti_map > .ol-viewport > canvas").css("height", "700px");
				
				updateMap();
			}
			
			$("#estimation-btn").html($('<i class="icon-arrow-caret-right" style="font-size: 16pt;"></i>'));
			
			vulAssessmentArr = new Array();
			
			for(var i = 0; i < vulAssessmentList.length; i++) {
				
				//페이징 처리를 위한 array push
				var obj = new Object();
				
				obj.rnum = vulAssessmentList[i].rnum;
				obj.district_nm = vulAssessmentList[i].district_nm;
				obj.ce_val = vulAssessmentList[i].ce_val;
				obj.cs_val = vulAssessmentList[i].cs_val;
				obj.aa_val = vulAssessmentList[i].aa_val;
				obj.ci_val = vulAssessmentList[i].ci_val;
				
				vulAssessmentArr.push(obj);
				
				layer_cd.push(vulAssessmentList[i].district_cd);
				layer_val.push(vulAssessmentList[i].ci_val);
			}
			/*
			pagination = new paginationInfo();
			
			pagination.setPageSize(5);
			pagination.setCurrentPageNo(1);
			pagination.setRecordCountPerPage(5);
			pagination.totalRecordCount(Number(totalCount));
			*/
			fn_estimationPage(0, 5);
			
			fn_oneParameterPage("estimation-page", "fn_pageInfo", totalCount, pageInfo);
			
			//paginationValue = pageInfo;
			
			addVector(layer_cd,layer_val);
			
			if(indi_id != "none") {
				fn_refIndicatorInfo(indi_id, 1);
			}
		},
		beforeSend: function() {
			$(".display").css("display","block");
		},
		error: function(xhr, status, error) {
			$("#total-value-table > tbody").append($('<tr><td colspan="7">취약성평가 중 문제가 발생했습니다.</td><tr>'));
		},
		complete: function(data) {
			$(".display").css("display","none");
		}
	});
}


/*
var pagination;
var paginationValue;

function paginationInfo() {
	
	var pageSize = 0;
	var currentPageNo = 0;
	var recordCountPerPage = 0;
	var totalRecordCount = 0;
	var firstRecordIndex = 0;
	var firstPageNoOnPageList = 0;
	var lastPageNoOnPageList = 0;
	var totalPageCount = 0;
	
	this.setPageSize = function(size) {
		pageSize = size;
	};
	
	this.setCurrentPageNo = function(pageNo) {
		currentPageNo = pageNo;
	};
	
	this.setRecordCountPerPage = function(limit) {
		recordCountPerPage = limit;
	};
	
	this.totalRecordCount = function(total) {
		totalRecordCount = total;
	}
	
	this.getFirstRecordIndex = function() {
		return (currentPageNo - 1) * recordCountPerPage;
	}
	
	this.getRecordCountPerPage = function() {
		return recordCountPerPage;
	}
	
	this.getFirstPageNoOnPageList = function() {
		firstPageNoOnPageList = ((currentPageNo - 1) / pageSize) * pageSize + 1;
		return firstPageNoOnPageList;
	}
	
	this.getLastPageNoOnPageList = function() {
		lastPageNoOnPageList = firstPageNoOnPageList + pageSize - 1;
		
		if (lastPageNoOnPageList > totalRecordCount) {
			lastPageNoOnPageList = totalRecordCount;
		}
		
		return lastPageNoOnPageList;
	}
	
	this.getTotalPageCount = function() {
		totalPageCount = ((totalRecordCount - 1) / recordCountPerPage) + 1;
		return totalPageCount;
	}
	
	this.getPageSize = function() {
		return pageSize;
	}
	
	this.getCurrentPageNo = function() {
		return currentPageNo;
	}
}
*/
function fn_pageInfo(totalCount, page) {
	/*
	pagination.setCurrentPageNo(page);
	
	paginationValue.currentPageNo = page;
	
	if(page > paginationValue.lastPageNoOnPageList) {
		paginationValue.firstPageNoOnPageList = pagination.getFirstPageNoOnPageList();
		paginationValue.lastPageNoOnPageList = pagination.getLastPageNoOnPageList();
	}
	
	if(page < paginationValue.firstPageNoOnPageList) {
		paginationValue.lastPageNoOnPageList = paginationValue.firstPageNoOnPageList - 1;
		paginationValue.firstPageNoOnPageList = paginationValue.firstPageNoOnPageList - 5;
	}
	
	if(paginationValue.lastPageNoOnPageList > paginationValue.lastPageNo) {
		paginationValue.lastPageNoOnPageList = paginationValue.lastPageNo;
	}
	
	fn_estimationPage(pagination.getFirstRecordIndex(), pagination.getFirstRecordIndex() + pagination.getRecordCountPerPage());
	
	console.log(page + " /" + paginationValue.firstPageNoOnPageList + " /" + paginationValue.lastPageNoOnPageList);
	console.log(pagination.getFirstRecordIndex() + " /" + pagination.getRecordCountPerPage());
	*/
	
	$("#estimation-page").empty();
	
	$.ajax({
		url: "/member/base/custom/estimation/page.do",
		type: "get",
		data: {page: page, totalCount: totalCount},
		dataType: "json",
		success: function(data) {
			
			var pageInfo = data.pageInfo;
			
			fn_estimationPage((page * 5) - 5, page * 5);
			fn_oneParameterPage("estimation-page", "fn_pageInfo", totalCount, pageInfo);
			
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
		},
		complete: function(data) {
			
		}
	});
}

function fn_estimationPage(firstIndex, lastIndex) {
	
	$("#total-value-table > tbody").empty();
	
	var $tr;
	var $td;
	
	if(lastIndex > vulAssessmentArr.length) {
		lastIndex = vulAssessmentArr.length;
	}
	
	if(vulAssessmentArr != null && vulAssessmentArr.length > 0) {
		
		for(var i = firstIndex; i < lastIndex; i++) {
			
			if(i == 0) {
				fn_redrawChart(vulAssessmentArr[i].district_nm, vulAssessmentArr[i].ce_val, vulAssessmentArr[i].cs_val, vulAssessmentArr[i].aa_val);
			}
			
			$tr = $('<tr onclick="fn_redrawChart(\'' + vulAssessmentArr[i].district_nm + '\', ' + vulAssessmentArr[i].ce_val + ', ' + vulAssessmentArr[i].cs_val + ', ' + vulAssessmentArr[i].aa_val + ')"></tr>');
			$td = $('<td>' + vulAssessmentArr[i].rnum + '</td>');
			$tr.append($td);
			
			$td = $('<td>' + vulAssessmentArr[i].district_nm + '</td>');
			$tr.append($td);
			
			$td = $('<td>' + vulAssessmentArr[i].ci_val + '</td>');
			$tr.append($td);
			
			$td = $('<td>' + vulAssessmentArr[i].ce_val + '</td>');
			$tr.append($td);
			
			$td = $('<td>' + vulAssessmentArr[i].cs_val + '</td>');
			$tr.append($td);
			
			$td = $('<td>' + vulAssessmentArr[i].aa_val + '</td>');
			$tr.append($td);
			
			$td = $('<td><i class="icon-graph-pie i-24"></i></td>');
			$tr.append($td);
			
			$("#total-value-table > tbody").append($tr);
		}
		
	} else {
		$("#total-value-table > tbody").append($('<tr><td colspan="7">취약성평가 결과가 없습니다</td><tr>'));
	}
}


/**
 * 항목 불러오기
 * @param page
 * @returns
 */
function fn_itemList(page) {
	
	$("#item-list").empty();
	$("#item-page").empty();
	
	var field_id = $("select[id=select-field] option:selected").val();
	
	$.ajax({
		url: "/member/base/custom/item/fieldItem.do",
		type: "get",
		data: {page: page, field_id: field_id},
		dataType: "json",
		success: function(data) {
			
			var itemList = data.itemList;
			var pageInfo = data.pageInfo;
			
			for(var i = 0; i < itemList.length; i++) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);" id="info-' + itemList[i].item_id + '" data="'+itemList[i].item_id+'" class="offcanvas-item item-info" onclick="fn_itemClick($(this));return false;">' + itemList[i].item_nm + '</a>');
				
				$liTag.append($aTag);
				
				$("#item-list").append($liTag);
			}
			
			fn_noneParameterPage("item-page", "fn_itemList", pageInfo);
			
			if(itemList.length < 1) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);">해당 분야에 항목이 없습니다.</a>');
				
				$liTag.append($aTag);
				
				$("#item-list").append($liTag);
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $liTag = $('<li></li>');
			var $aTag = $('<a href="javascript:void(0);">항목 불러오기 실패</a>');
			
			$liTag.append($aTag);
			
			$("#item-list").append($liTag);
		},
		complete: function(data) {
			
		}
	});
}


/**
 * 행정구역 불러오기
 * @param district_cd
 * @returns
 */
function fn_district(district_cd) {
	
	$("#select-sgg").empty();
	
	$.ajax({
		url: "/member/base/custom/estimation/district.do",
		type: "get",
		data: {district_cd: district_cd},
		dataType: "json",
		success: function(data) {
			
			var districtList = data.districtList;
			var userAuth = data.userAuth;
			
			var $option;
			
			if(userAuth == "W" || userAuth == "A") {
				
				$option = $('<option value="all" selected>전체</option>');
				$("#select-sgg").append($option);
			}
			
			for(var i = 0; i < districtList.length; i++) {
				
				if(i == 0 && userAuth == "B") {
					$option = $('<option value="' + districtList[i].district_cd + '" selected>' + districtList[i].district_nm + '</option>');
				} else {
					$option = $('<option value="' + districtList[i].district_cd + '">' + districtList[i].district_nm + '</option>');
				}
				
				if(i == 0) {
					$("#option-sgg").text(districtList[i].district_nm);
				}
				
				$("#select-sgg").append($option);
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $option = $('<option value="none">시군구 불러오기 실패</option>');
			$("#select-sgg").append($option);
		},
		complete: function(data) {
			
		}
	});
}

/**
 * 선택 옵션 세팅
 * @param id
 * @returns
 */
function fn_option(id) {
	
	var orgID = id.replace("select-", "");
	
	$("#option-" + orgID).text($("#" + id + " option:selected").text());
}


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


function fn_downloadEstimation() {
	
	if($("#select-model option:selected").val() == "none") {
		return false;
	}
	
	if($("#select-rcp option:selected").val() == "none") {
		fn_alert("경고", "RCP를 선택 해야 합니다.", "error");
		return false;
	}
	
	if($("#select-year option:selected").val() == "none") {
		fn_alert("경고", "연대를 선택 해야 합니다.", "error");
		return false;
	}
	
	if($("#select-sd option:selected").val() == "none") {
		fn_alert("경고", "시/도를 선택 해야 합니다.", "error");
		return false;
	}
	
	if($("#select-sgg option:selected").val() == "none") {
		fn_alert("경고", "시/군/구를 선택 해야 합니다.", "error");
		return false;
	}
	
	if($(".item-info.active").length < 1) {
		fn_alert("경고", "항목을 선택 해야 합니다.", "error");
		return false;
	}
	
	var item_id = $(".item-info.active").attr("id").replace("info-", "");
	var mdl_id = $("#select-model option:selected").val();
	var scen_year = $("#select-year option:selected").val();
	var scen_section = $("#select-rcp option:selected").val();
	var district_info;
	
	if($("#select-sgg option:selected").val() == "all") {
		district_info = $("#select-sd option:selected").val();
	} else {
		district_info = $("#select-sgg option:selected").val();
	}
	
	var params = "item_id=" + item_id + "&scen_year=" + scen_year
				+ "&scen_section=" + scen_section + "&district_info=" + district_info
				+ "&rcp=" + $("#select-rcp option:selected").text()
				+ "&model=" + $("#select-model option:selected").text()
				+ "&field=" + $("#select-field option:selected").text()
				+ "&year=" + $("#select-year option:selected").text()
				+ "&mdl_id=" + mdl_id;
	
	location.href = encodeURI("/member/base/custom/estimation/downloadEstimation.do?" + params);
}


function fn_downloadRefIndicatorInfo() {
	
	if($("#select-model option:selected").val() == "none") {
		fn_alert("경고", "Model을 선택 해야 합니다.", "error");
		return false;
	}
	
	if($("#select-rcp option:selected").val() == "none") {
		fn_alert("경고", "RCP를 선택 해야 합니다.", "error");
		return false;
	}
	
	if($("#select-year option:selected").val() == "none") {
		fn_alert("경고", "연대를 선택 해야 합니다.", "error");
		return false;
	}
	
	if($("#select-sd option:selected").val() == "none") {
		fn_alert("경고", "시/도를 선택 해야 합니다.", "error");
		return false;
	}
	
	if($("#select-sgg option:selected").val() == "none") {
		fn_alert("경고", "시/군/구를 선택 해야 합니다.", "error");
		return false;
	}
	
	var mdl_id = $("#select-model option:selected").val();
	var indi_id = $("#select-indicator-base option:selected").val();
	var scen_year = $("#select-year option:selected").val();
	var scen_section = $("#select-rcp option:selected").val();
	var district_info;
	
	if($("#select-sgg option:selected").val() == "all") {
		district_info = $("#select-sd option:selected").val();
	} else {
		district_info = $("#select-sgg option:selected").val();
	}
	
	var params = "indi_id=" + indi_id + "&scen_year=" + scen_year + "&scen_section=" + scen_section + "&district_info=" + district_info + "&mdl_id=" + mdl_id;
	
	location.href = encodeURI("/mamber/base/custom/estimation/downloadIndicatorData.do?" + params);
	
}
