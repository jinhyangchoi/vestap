$(function() {
	
	$(document).on("change", "#select-field", function() {
		fn_getItem($(this).val(), "all");
	});
	
	$(document).on("change", "#ipcc-1", function() {
		fn_getIpcc($(this).val(), "all");
	});
	
	$(document).on("click", "#indicator-init", function() {
		location.href = "/member/base/dbinfo/indicator/list.do";
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
		fn_indicatorView($("input:hidden[name=indi_id]").val(), $(this).val(), 1);
	});
	
	$(document).on("change", "#district-sd-ski", function() {
		fn_indicatorView($("input:hidden[name=indi_id]").val(), $(this).val(), 1);
	});
	
	$(document).on("change", "#district-sgg", function() {
		fn_indicatorView($("input:hidden[name=indi_id]").val(), $(this).val(), 1);
	});
	
	
	$(document).on("change", "#select-model", function() {
		fn_climateOption($(this).val(), null);
	});
	
	$(document).on("change", "#select-rcp", function() {
		fn_climateOption($("#select-model option:selected").val(), $(this).val());
	});
	
	$(document).on("change", "#select-year", function() {
		fn_indicatorView($("input:hidden[name=indi_id]").val(), $("#district-sgg option:selected").val(), 1);
	});
});

function fn_climateOption(mdl_id, rcp_id) {
	
	var params;
	var $element;
	
	if(rcp_id == null) {
		
		params = {mdl_id: mdl_id};
		$element = $("#select-rcp");
		
		$("#select-rcp").empty();
		$("#select-year").empty();
		
	} else {
		
		params = {mdl_id: mdl_id, rcp_id: rcp_id};
		$element = $("#select-year");
		
		$("#select-year").empty();
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
			} else {
				
				fn_indicatorView($("input:hidden[name=indi_id]").val(), $("#district-sgg option:selected").val(), 1);
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

function fn_districtSgg(district_info) {
	
	$("#district-sgg").empty();
	
	$.ajax({
		url: "/member/base/dbinfo/indicator/districtSgg.do",
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
	
	$("#listFrm").submit();
}

function fn_indicatorInfo(indi_id, page) {
	
	$("input:hidden[name=indi_id]").val(indi_id);
	var sd = $("#district-sd option:selected").val();
	var sgg = $("#district-sgg option:selected").val();
	var district_info;
	
	if(sgg == "all") {
		district_info = sd;
	} else {
		if(sgg != "none") {
			district_info = sgg;
		}
	}
	
	if(indi_id == "IC000395" || indi_id == "IC000398" || indi_id == "IC000399" || indi_id == "IC000400"
			|| indi_id == "IC000401" || indi_id == "SC002048" || indi_id == "SC002050") {
		district_info = null;
	}
	
	fn_indicatorView(indi_id, district_info, page);
	
	fn_indicatorItem(indi_id, 1);
}

function fn_indicatorView(indi_id, district_info, page) {
	
	if(district_info == "all") {
		//district_info = $("#district-sd option:selected").val();
		
		if(indi_id == "IC000395" || indi_id == "IC000398" || indi_id == "IC000399" || indi_id == "IC000400"
			|| indi_id == "IC000401" || indi_id == "SC002048" || indi_id == "SC002050") {
			district_info = $("#district-sd-ski option:selected").val();
		} else {
			district_info = $("#district-sd option:selected").val();
		}
	}
	
	var mdl_id = $("#select-model option:selected").val();
	var rcp_id = $("#select-rcp option:selected").val();
	var year_id = $("#select-year option:selected").val();
	var header = indi_id.substring(0, 2);
	var params = {page: page, indi_id: indi_id, mdl_id: mdl_id, rcp_id: rcp_id, year_id: year_id};
	
	if(district_info != null) {
		params.district_info = district_info;
	}
	
	$.ajax({
		url: "/member/base/dbinfo/indicator/indicatorInfo.do",
		type: "get",
		data: params,
		dataType: "json",
		success: function(data) {
			
			$("#indicator-data-table > tbody").empty();
			$("#indicator-data-table > tfoot").remove();
			$("#meta-list-table > tbody").empty();
			$("#info-indi-nm").empty();
			$("#info-indi-group").empty();
			$("#info-indi-ipcc1").empty();
			$("#info-indi-ipcc2").empty();
			$("#info-indi-unit").empty();
			$("#info-indi-cont").empty();
			$("#info-indi-acct").empty();
			$("#info-indi-meth").empty();
			$("#info-indi-method").empty();
			
			var indicatorListInfo = data.indicatorListInfo;
			var indicatorDataList = data.indicatorDataList;
			var metaList = data.metaList;
			var pageInfo = data.pageInfo;
			var msg = data.msg;
			var districtList_sd = data.districtList_sd;
			var districtList_sgg = data.districtList_sgg;
			var isSki = data.isSki;
			
			if(district_info == null) {
				district_info = data.district_info;
			}
			
			if(header == "SC") {
				$("#select-model-div").removeClass("d-none");
				$("#select-option-div").removeClass("d-none");
			} else {
				$("#select-model-div").addClass("d-none");
				$("#select-option-div").addClass("d-none");
			}
			console.log(indicatorListInfo);
			$("#info-indi-group").text(indicatorListInfo.indi_group_nm);
			$("#info-indi-nm").text(indicatorListInfo.indi_nm);
			$("#info-indi-ipcc1").text(indicatorListInfo.ipcc_large_nm);
			$("#info-indi-ipcc2").text(indicatorListInfo.ipcc_small_nm);
			$("#info-indi-unit").text(indicatorListInfo.indi_unit);
			$("#info-indi-cont").text(indicatorListInfo.indi_construct_nm);
			$("#info-indi-acct").text(indicatorListInfo.indi_account);
			$("#info-indi-meth").html(indicatorListInfo.indi_construct_meth);
			if(indicatorListInfo.indi_deduction){
				console.log(indicatorListInfo.indi_deduction);
				$("#info-indi-method").html(indicatorListInfo.indi_deduction);
			}
			
			var $tr;
			var $td;
			
			for(var i = 0; i < indicatorDataList.length; i++) {
				
				$tr = $('<tr></tr>');
				
				$td = $('<td>' + indicatorDataList[i].rnum + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + indicatorDataList[i].district_nm + '</td>');
				$tr.append($td);
				
				if(indicatorDataList[i].indi_val==null){
					$td = $('<td>데이터 없음</td>');
					$tr.append($td);
				}else{
					$td = $('<td>' + fn_addComma(Number(indicatorDataList[i].indi_val).toFixed(2)) + '</td>');
					$tr.append($td);
				}
				/*$td = $('<td>' + fn_addComma(Number(indicatorDataList[i].indi_val).toFixed(2)) + '</td>');
				$tr.append($td);*/
				
				$td = $('<td>' + indicatorDataList[i].indi_year + '</td>');
				$tr.append($td);
				
				$("#indicator-data-table > tbody").append($tr);
			}
			
			if(indicatorDataList.length < 1) {
				$("#indicator-data-table > tbody").append($('<tr><td colspan="4">현재 선택한 지역에 대한 지표 정보가 없습니다.</td></tr>'));
			}
			
			if(msg != "N") {
				$("#indicator-data-table").append($('<tfoot><tr><td colspan="4">' + msg + '</td></tr></tfooy>'));
			}
			
			fn_oneParameterPage("indicator-data-page", "fn_indicatorInfo", indi_id, pageInfo);
			
			for(var i = 0; i < metaList.length; i++) {
				
				$tr = $('<tr onclick="window.open(\'/member/base/dbinfo/meta/list.do?activeOffcanvas=' + metaList[i].meta_id + '\')"></tr>');
				
				//$td = $('<td><a href="/member/base/dbinfo/meta/list.do?activeOffcanvas=' + metaList[i].meta_id + '" target="_blank">' + metaList[i].meta_nm + '</a></td>');
				$td = $('<td>' + metaList[i].meta_nm + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + metaList[i].meta_offer_org + '</td>');
				$tr.append($td);
				
				$("#meta-list-table > tbody").append($tr);
			}
			
			/**
			 * 시도 select
			 */
			if(isSki) {
				
				$("#district-sd-ski").empty();
				$("#district-sd-ski").removeClass("d-none");
				$("#district-sd").addClass("d-none");
				
				var ski_sd = district_info.substring(0, 2);
				
				for(var i = 0; i < districtList_sd.length; i++) {
					
					if(districtList_sd[i].district_cd == ski_sd) {
						$("#district-sd-ski").append('<option value="' + districtList_sd[i].district_cd + '" selected="selected">' + districtList_sd[i].district_nm + '</option>');
					} else {
						$("#district-sd-ski").append('<option value="' + districtList_sd[i].district_cd + '">' + districtList_sd[i].district_nm + '</option>');
					}
				}
				
				
				$("#district-sgg").empty();
				
				var $option;
				
				$option = $('<option value="all">전체</option>');
				$("#district-sgg").append($option);
				
				for(var i = 0; i < districtList_sgg.length; i++) {
					
					if(districtList_sgg[i].district_cd == district_info) {
						$option = $('<option value="' + districtList_sgg[i].district_cd + '" selected="selected">' + districtList_sgg[i].district_nm + '</option>');
					} else {
						$option = $('<option value="' + districtList_sgg[i].district_cd + '">' + districtList_sgg[i].district_nm + '</option>');
					}
					
					$("#district-sgg").append($option);
				}
				
			} else {
				$("#district-sd").removeClass("d-none");
				$("#district-sd-ski").addClass("d-none");
			}
			$(".display").css("display","none");
		},
		beforeSend: function() {
			$(".display").css("display","block");
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
		url: "/member/base/dbinfo/indicator/getIpcc.do",
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
		url: "/member/base/dbinfo/indicator/getItem.do",
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
		url: "/member/base/dbinfo/indicator/indicatorItem.do",
		type: "get",
		data: {indi_id: indi_id, page: page},
		dataType: "json",
		success: function(data) {
			
			var itemList = data.itemList;
			var pageInfo = data.pageInfo;
			var $tr;
			var $td;
			
			for(var i = 0; i < itemList.length; i++) {
				
				$tr = $('<tr onclick="window.open(\'/member/base/dbinfo/item/list.do?activeOffcanvas=' + itemList[i].item_id + '\')"></tr>');
				
				$td = $('<td>' + itemList[i].rnum + '</td>');
				$tr.append($td);
				
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
	
	var indi_id = $("input:hidden[name=indi_id]").val();
	var sd = $("#district-sd option:selected").val();
	var sgg = $("#district-sgg option:selected").val();
	var mdl_id = $("#select-model option:selected").val();
	var rcp_id = $("#select-rcp option:selected").val();
	var year_id = $("#select-year option:selected").val();
	var district_info;
	
	if(sgg == "all") {
		district_info = sd;
	} else {
		if(sgg != "none") {
			district_info = sgg;
		}
	}
	
	var params = "indi_id=" + indi_id + "&district_info=" + district_info + "&mdl_id=" + mdl_id + "&rcp_id=" + rcp_id + "&year_id=" + year_id;
	
	location.href = "/member/base/dbinfo/indicator/downloadIndicator.do?" + params;
}