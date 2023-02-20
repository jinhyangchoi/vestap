
$(function() {
	
	$(document).on("click", "#item-search", function() {
		
		var keyword = $("#search-keyword").val().trim();
		var field = $("#select-field option:selected").val();
		
		if(keyword.length > 1) {
			
			$("input:hidden[name=keyword]").val(keyword);
		}
		
		$("input:hidden[name=field]").val(field);
		
		$("#listFrm").submit();
	});

	$(document).on("click", "#item-init", function() {
		
		$("input:hidden[name=keyword]").val("");
		$("input:hidden[name=field]").val("");
		
		$("#listFrm").submit();
	});
	
	$(document).on("change", "#select-sector", function() {
		fn_itemIndicator($(this).val(), $("input:hidden[name=item_id]").val());
	});
	
	$(document).on("keydown", "#search-keyword", function(key) {
		
		if(key.keyCode == 13) {
			
			var keyword = $(this).val().trim();
			var field = $("#select-field option:selected").val();
			
			if(keyword.length > 1) {
				
				$("input:hidden[name=keyword]").val(keyword);
			}
			
			$("input:hidden[name=field]").val(field);
			
			$("#listFrm").submit();
		}
	});
});

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
	
	fn_itemIndicator("SEC01", item_id);
}

function fn_itemIndicator(sector_id, item_id) {
	
	$.ajax({
		url: "/member/base/dbinfo/item/indicatorInfo.do",
		type: "get",
		data: {sector_id: sector_id, item_id: item_id},
		dataType: "json",
		success: function(data) {

			$("#indicatorInfo-table > tbody").empty();
			
			var indicatorInfoList = data.indicatorInfoList;
			var $tr;
			var $td;
			
			for(var i = 0; i < indicatorInfoList.length; i++) {
				
				$tr = $('<tr onclick="window.open(\'/member/base/dbinfo/indicator/list.do?activeOffcanvas=' + indicatorInfoList[i].indi_id + '\')"></tr>');
				
				$td = $('<td>' + indicatorInfoList[i].indi_nm + '</td>');
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
			$(".display").css("display","none");
		},
		beforeSend: function() {
			$(".display").css("display","block");
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

function fn_page(page) {
	
	$("input:hidden[name=page]").val(page);
	
	$("#listFrm").submit();
}

function downloadIndicatorInfo() {
	
	var item_id = $(".item-list.active").attr("id").replace("item-", "");
	
	location.href = "/member/base/dbinfo/item/downloadIndicatorInfo.do?item_id=" + item_id;
}


function fn_itemInfo(item_id) {
	
	$.ajax({
		url: "/member/base/dbinfo/item/itemInfo.do",
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