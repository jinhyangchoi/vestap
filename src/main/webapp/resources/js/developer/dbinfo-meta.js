$(function() {
	
	$(document).on("click", "#meta-init", function() {
		location.href = "/member/base/dbinfo/meta/list.do";
	});
	
	$(document).on("click", "#meta-search", function() {
		fn_dbinfo_submit();
	});
	
	$(document).on("keydown", "#search-keyword", function(key) {
		
		if(key.keyCode == 13) {
			fn_dbinfo_submit();
		}
	});
	
	$(document).on("change", "#select-offer", function() {
		fn_offerSystem($(this).val());
	});
});

function fn_metaInfo(meta_id, page) {
	
	fn_metaView(meta_id);
	
	fn_metaIndicator(meta_id, page);
}

function fn_metaIndicator(meta_id, page) {
	
	
	$.ajax({
		url: "/member/base/dbinfo/meta/metaIndicator.do",
		type: "get",
		data: {meta_id: meta_id, page: page},
		dataType: "json",
		success: function(data) {

			$("#meta-indi-table > tbody").empty();
			
			var indicatorList = data.indicatorList;
			var pageInfo = data.pageInfo;
			var $tr;
			var $td;
			
			for(var i = 0; i < indicatorList.length; i++) {
				
				$tr = $('<tr onclick="window.open(\'/member/base/dbinfo/indicator/list.do?activeOffcanvas=' + indicatorList[i].indi_id + '\')"></tr>');
				
				$td = $('<td>' + indicatorList[i].rnum + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + indicatorList[i].indi_nm + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + indicatorList[i].indi_type + '</td>');
				$tr.append($td);
				
				$("#meta-indi-table > tbody").append($tr);
			}
			
			if(indicatorList.length < 1) {
				$("#meta-indi-table > tbody").append($('<tr><td colspan="3">해당 지표 목록이 없습니다.</td></tr>'));
			}
			
			fn_oneParameterPage("meta-indi-page", "fn_metaIndicator", meta_id, pageInfo);

			$(".display").css("display","none");
		},
		beforeSend: function() {

			$(".display").css("display","block");
			
		},
		error: function(xhr, status, error) {
			$("#meta-indi-table > tbody").append($('<tr><td colspan="3">해당 지표 목록을 볼러 올 수 없습니다.</td></tr>'));
		},
		complete: function(data) {
			
		}
	});
}

function fn_metaView(meta_id) {
	
	$("#meta-info-table > tr > td").empty();
	
	$.ajax({
		url: "/member/base/dbinfo/meta/metaInfo.do",
		type: "get",
		data: {meta_id: meta_id},
		dataType: "json",
		success: function(data) {
			
			var metaInfo = data.metaInfo;
			
			$("#info-meta-nm").text(metaInfo.meta_nm);
			$("#meta-info-nm").text(metaInfo.meta_nm);
			$("#meta-info-offer").text(metaInfo.meta_offer_org);
			$("#meta-info-dept").text(metaInfo.meta_offer_dept);
			$("#meta-info-system").text(metaInfo.meta_offer_system);
			$("#meta-info-base-unit").text(metaInfo.meta_base_unit);
			$("#meta-info-con-unit").text(metaInfo.meta_con_unit);
			$("#meta-info-year").text(metaInfo.meta_con_year);
			$("#meta-info-type").text(metaInfo.meta_data_type);
			$("#meta-info-position").text(metaInfo.meta_position);
			$("#meta-info-scale").text(metaInfo.meta_scale);
			$("#meta-info-unit").text(metaInfo.meta_unit);
			
			if(metaInfo.meta_url.length > 40) {
				
				var temp = metaInfo.meta_url.substring(0, 40) + "...";
				$("#meta-info-url").html($('<a href="' + metaInfo.meta_url + '" target="_blank">' + temp + '</a>'));
				
			} else {
				
				$("#meta-info-url").html($('<a href="' + metaInfo.meta_url + '" target="_blank">' + metaInfo.meta_url + '</a>'));
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

function fn_offerSystem(meta_offer) {
	
	$("#select-system").empty();
	
	$.ajax({
		url: "/member/base/dbinfo/meta/getSystem.do",
		type: "get",
		data: {meta_offer: meta_offer},
		dataType: "json",
		success: function(data) {
			
			var offerSystemList = data.offerSystemList;
			var $option;
			
			$option = $('<option value="all">전체</option>');
			$("#select-system").append($option);
			
			for(var i = 0; i < offerSystemList.length; i++) {
				
				if(offerSystemList[i] != null) {
					
					$option = $('<option value="' + offerSystemList[i] + '">' + offerSystemList[i] + '</option>');
					$("#select-system").append($option);
				}
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			$("#select-system").append($('<option value="all">제공시스템 호출 불가</option>'));
		},
		complete: function(data) {
			
		}
	});
}

function fn_dbinfo_submit() {
	
	var keyword = $("#search-keyword").val().trim();
	var meta_offer = $("#select-offer option:selected").val();
	var meta_system = $("#select-system option:selected").val();
	
	if(keyword.length > 1) {
		
		$("input:hidden[name=keyword]").val(keyword);
	}
	
	$("input:hidden[name=meta_offer]").val(meta_offer);
	$("input:hidden[name=meta_system]").val(meta_system);
	
	$("#listFrm").submit();
}

function fn_page(page) {
	
	$("input:hidden[name=page]").val(page);
	
	$("#listFrm").submit();
}