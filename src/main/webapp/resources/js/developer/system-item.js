$(function() {
	
	var openOffset = $(".view-currposition").offset();
	
	$(window).scroll(function() {
		
		$(".view-currposition").css("margin-top", $(document).scrollTop() + "px");
	});
});


/**
 * 부문(기후노춣, 기후변화민감도, 적응능력) 의 지표 추가 버튼 클릭
 */
function fn_addIndicatorView(id) {
	
	$(".offcanvas-right-open").removeClass("active");
	$(".offcanvas-right-open .card").removeClass("active");
	
	$('#offcanvas-indicator').addClass('active');
	$('#offcanvas-indicator .card').addClass('active');
	
	$("#indicator-group-page").empty();
	$("input:hidden[name=indicatorGroup]").val(id);
	
	if(id == "indicator-exp") {
		$("#add-indicator-name").text("기후 노출 지표");
	} else if(id == "indicator-sen") {
		$("#add-indicator-name").text("기후 변화 민감도 지표");
	} else if(id == "indicator-adp") {
		$("#add-indicator-name").text("적응 능력 지표");
	}
	
	$("#indicator-item-list").empty();
	$("#indicator-group-select option[value=none]").prop("selected", true);
}

/**
 * 부문(기후노춣, 기후변화민감도, 적응능력) 의 항목 제거 버튼
 * @param id
 * @returns
 */
function fn_delIndicatorView(id) {
	
	$("#list-" + id + " input:checkbox:checked").each(function() {
		
		var name = $(this).attr("name").replace(id + "-chk-", "");
		
		$("#indi-list-" + name).remove();
		$("input:checkbox[id=" + name + "][value=" + name + "]").prop("checked", false);
		$("input:checkbox[id=" + name + "][value=" + name + "]").siblings("label").removeClass("text-primary text-success text-warning");
		$("input:checkbox[id=" + name + "][value=" + name + "]").siblings("label").addClass("text-base");
	});
	
	var len = $("#list-" + id + " > li").length;
	
	if(len < 1) {
		
		$("#list-" + id).append($('<li class="none-indicator"><div class="col-12 pl-0">지표추가를 클릭하여 지표를 추가 해야 합니다.</div></li>'));
	}
	
}

/**
 * offcanvas-right-open 패널의 지표 그룹 선택 이벤트
 */
$(document).on("change", "#indicator-group-select", function() {
	if($(this).val() != "none") {
		fn_groupIndicator($(this).val(), 1);
	}
});

/**
 * offcanvas-right-open 패널의 지표 그룹 선택 이벤트 함수
 * @param group_id
 * @param page
 * @returns
 */
function fn_groupIndicator(group_id, page) {
	
	$("#indicator-item-list").empty();
	$("#indicator-group-page").empty();
	$("#indicator-group-name").text($("#indicator-group-select option:selected").text());
	
	$.ajax({
		url: "/admin/system/item/indicator.do",
		data: {group_id: group_id, page: page},
		type: "get",
		dataType: "json",
		success: function(data) {
			
			var groupIndicatorList = data.groupIndicatorList;
			var pageInfo = data.pageInfo;
			var name = $("input:hidden[name=indicatorGroup]").val();
			
			var $li;
			var $div;
			var sectorStyle = "";
			
			for(var i = 0; i < groupIndicatorList.length; i++) {
				
				$li = $('<li></li>');
				$div = $('<div class="custom-control custom-checkbox"></div>');
				
				var $checkbox;
				var $label;
				
				if($("#indi-list-" + groupIndicatorList[i].indi_id).length> 0) {
					
					var sector = $("#indi-list-" + groupIndicatorList[i].indi_id).parents("ul").attr("id");
					
					if(sector == "list-indicator-exp") {
						sectorStyle = "text-primary";
					} else if(sector == "list-indicator-sen") {
						sectorStyle = "text-success";
					} else if(sector == "list-indicator-adp") {
						sectorStyle = "text-warning";
					}
					
					$checkbox = $('<input type="checkbox" class="custom-control-input group-indicator" '
							+ 'id="' + groupIndicatorList[i].indi_id + '" '
							+ 'name="' + name + '" '
							+ 'value="' + groupIndicatorList[i].indi_id + '" checked="checked">');
					
					$label = $('<label class="custom-control-label ' + sectorStyle + '" for="' + groupIndicatorList[i].indi_id + '">' + groupIndicatorList[i].indi_nm + '</label>');
					
				} else {
					
					$checkbox = $('<input type="checkbox" class="custom-control-input group-indicator" '
							+ 'id="' + groupIndicatorList[i].indi_id + '" '
							+ 'name="' + name + '" '
							+ 'value="' + groupIndicatorList[i].indi_id + '">');
					
					$label = $('<label class="custom-control-label text-base" for="' + groupIndicatorList[i].indi_id + '">' + groupIndicatorList[i].indi_nm + '</label>');
				}
				
				$div.append($checkbox);
				$div.append($label);
				
				$li.append($div);
				
				$("#indicator-item-list").append($li);
			}
			
			if(groupIndicatorList.length < 1) {
				
				$li = $('<li></li>');
				$div = $('<div class="col-12 text-base">지표 그룹에 지표가 없습니다.</div>');
				
				$li.append($div);
				
				$("#indicator-item-list").append($li);
			}
			
			fn_oneParameterPage("indicator-group-page", "fn_groupIndicator", group_id, pageInfo);
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
		},
		complete: function(data) {
			
		}
	});
}

/**
 * offcanvas-right-open 패널의 지표 선택 이벤트
 */
$(document).on("click", ".group-indicator", function() {
	
	var title = $(this).siblings("label").text();
	var name = $(this).attr("name");
	var val = $(this).val();
	var len = $("#list-" + name + " > li").length;
	var indi_nm = "";
	
	if(name == "indicator-exp") {
		indi_nm = "기후 노출";
	} else if(name == "indicator-sen") {
		indi_nm = "기후 변화 민감도";
	} else if(name == "indicator-adp") {
		indi_nm = "적응 능력";
	}
	
	if($(this).prop("checked")) {
		
		if(len < 10) {
			
			if(len == 1) {
				$("#list-" + name + " .none-indicator").remove();
			}
			
			var $li = $('<li id="indi-list-' + val + '"></li>');
			var $con = $('<div class="row"></div>');
			var $chkCon = $('<div class="col-9 pl-3"></div>');
			var $valCon = $('<div class="col-3"></div>');
			var $chkEl = $('<div class="vestap-checkbox">'
					+ '<label>'
					+ '<input type="checkBox" name="' + name + '-chk-' + val + '">'
					+ '<span class="cr"><i class="cr-icon icon-check-01"></i></span>'
					+ '</label>'
					+ '</div>'
					+ '<div class="text-base pl-2">'
					+ title
					+ '</div>');
			
			var $valEl = $('<input type="text" class="form-control float-right indicator-weight" name="' + name + '-txt-' + val + '" placeholder="가중치(필수)">');
			
			$chkCon.append($chkEl);
			$valCon.append($valEl);
			
			$con.append($chkCon);
			$con.append($valCon);
			$li.append($con);
			
			$("#list-" + name).append($li);
			
			$(this).siblings("label").removeClass("text-success text-primary text-warning text-base");
			
			if(name == "indicator-exp") {
				$(this).siblings("label").addClass("text-primary");
			} else if(name == "indicator-sen") {
				$(this).siblings("label").addClass("text-success");
			} else if(name == "indicator-adp") {
				$(this).siblings("label").addClass("text-warning");
			}
			
		} else {
			fn_alert("경고", "각 부문은 최대 10개의 지표만 추가 할 수 있습니다.", "error");
			$(this).prop("checked", false);
		}
		
	} else {
		
		$(this).siblings("label").removeClass("text-success text-primary text-warning");
		$(this).siblings("label").addClass("text-base");
		
		$("#indi-list-" + val).remove();
		
		var $info = $('<li class="none-indicator"><div class="col-12 pl-0">지표추가를 클릭하여 지표를 추가 해야 합니다.</div></li>');
		
		if($("#list-indicator-exp > li").length < 1) {
			$("#list-indicator-exp").append($info);
		}
		
		if($("#list-indicator-sen > li").length < 1) {
			$("#list-indicator-sen").append($info);
		}
		
		if($("#list-indicator-adp > li").length < 1) {
			$("#list-indicator-adp").append($info);
		}
		
	}
});


/**
 * 항목명 중복 검사
 */
$(document).on("click", "#check-overlap", function() {
	fn_isItemName();
});

/**
 * 항목 변경 시 중복 검사 다시 하게 세팅
 */
$(document).on("keydown", "#item-name", function() {
	$("#check-overlap").prop("disabled", false);
});


/**
 * 항목명 중복 검사
 * @returns
 */
function fn_isItemName() {
	
	var itemName = $("#item-name").val().trim();
	var regText = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9| |(|)|\*]+$/;
	var params;
	
	if($("#system-form").attr("action").indexOf("/system/item/update.do") > -1) {
		var item_id = $(".item-info.active").attr("id").replace("info-", "");
		params = {itemName: itemName, item_id: item_id};
	} else {
		params = {itemName: itemName}
	}
	
	if(itemName.length > 0) {
		
		if(itemName.length < 30) {
			
			if(regText.test(itemName)) {
				
				$.ajax({
					url: "/admin/system/item/isItemName.do",
					data: params,
					type: "get",
					dataType: "json",
					success: function(data) {
						
						var isName = Number(data.isName);
						
						if(isName > 0) {
							fn_alert("경고", "이미 사용중인 항목명 입니다.", "error");
							$("#item-name").val("");
						} else {
							fn_alert("승인", "사용 가능한 항목명 입니다.", "success");
							$("#check-overlap").prop("disabled", true);
						}
					},
					beforeSend: function() {
						
					},
					error: function(xhr, status, error) {
						
					},
					complete: function(data) {
						
					}
				});
				
			} else {
				fn_alert("경고", "한글과 영문 또는 숫자만 입력 가능합니다.", "error");
			}
			
		} else {
			fn_alert("경고", "항목명은 30자 이내로 작성 해야 합니다.", "error");
		}
		
	} else {
		fn_alert("경고", "항목명을 입력 해 주세요.", "error");
	}
}

/**
 * 취소 버튼 클릭시 페이지 다시 로드
 */
$(document).on("click", "#custom-reload", function() {
	location.reload();
});

/**
 * 항목 생성 버튼 클릭시 insert
 */
$(document).on("click", "#system-insert", function() {
	
	var areaNm = "";
	var pattern = /^[-]?\d+(?:[.]\d+)?$/;
	
	/** 분야 선택 */
	if(!fn_select_field()) {
		fn_alert("경고", "리스크를 선택 해야 합니다.", "error");
		return false;
	}
	
	/** 항목 설명 입력 */
	if(!fn_input_account()) {
		fn_alert("경고", "항목에 대한 요약 설명을 입력 해야 합니다.", "error");
		return false;
	}
	
	/** 취약성 평가 산출식 입력 */
	if(!fn_estimation_weight_input()) {
		fn_alert("경고", "취약성 평가 산출식을 입력 해야 합니다.", "error");
		return false;
	}
	
	/** 취약성 평가 산출식 Numeric 타입 */
	if(!fn_estimation_weight_isNaN()) {
		fn_alert("경고", "취약성 평가 산출식은 숫자만 입력 가능합니다.", "error");
		return false;
	}
	
	/** 취약성 평가 산출식 계산 */
	if(!fn_estimation_weight_cal()) {
		fn_alert("경고", "취약성 평가 산출식 세가지의 합은 1 이 되어야 합니다.", "error");
		return false;
	}
	
	/** 부문별 지표 추가 여부 */
	if(($("#list-indicator-exp > li").length - $("#list-indicator-exp > li.none-indicator").length) < 1) {
		fn_alert("경고", "기후 노출 부문에 지표를 추가 해야 합니다.", "error");
		return false;
	}
	
	if(($("#list-indicator-sen > li").length - $("#list-indicator-sen > li.none-indicator").length) < 1) {
		fn_alert("경고", "기후 변화 민감도 부문에 지표를 추가 해야 합니다.", "error");
		return false;
	}
	
	if(($("#list-indicator-adp > li").length - $("#list-indicator-adp > li.none-indicator").length) < 1) {
		fn_alert("경고", "적응 능력 부문에 지표를 추가 해야 합니다.", "error");
		return false;
	}
	
	/** 부문별 지표 가중치 입력 여부 */
	if(!fn_indicator_weight_input("indicator-exp")) {
		fn_alert("경고", "기후 노출 부문의 지표에 가중치가 없거나 숫자가 아닙니다.", "error");
		return false;
	}
	
	if(!fn_indicator_weight_input("indicator-sen")) {
		fn_alert("경고", "기후 변화 민감도 부문의 지표에 가중치가 없거나 숫자가 아닙니다.", "error");
		return false;
	}
	
	if(!fn_indicator_weight_input("indicator-adp")) {
		fn_alert("경고", "적응 능력 부문의 지표에 가중치가 없거나 숫자가 아닙니다.", "error");
		return false;
	}
	
	if(!fn_indicator_weight_cal("indicator-exp")) {
		fn_alert("경고", "기후 노출 부문의 지표 가중치의 합은 1 이 되어야 합니다.", "error");
		return false;
	}
	
	if(!fn_indicator_weight_cal("indicator-sen")) {
		fn_alert("경고", "기후 변화 민감도 부문의 지표 가중치의 합은 1 이 되어야 합니다.", "error");
		return false;
	}
	
	if(!fn_indicator_weight_cal("indicator-adp")) {
		fn_alert("경고", "적응 능력 부문의 지표 가중치의 합은 1 이 되어야 합니다.", "error");
		return false;
	}
	
	/** 항목명 중복 검사 */
	if(!$("#check-overlap").prop("disabled")) {
		fn_alert("경고", "항목명 중복 검사를 해야 합니다.", "error");
		return false;
	}
	
	var agent = navigator.userAgent.toLowerCase();
	
	if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
		
		if(confirm("[확인] 항목을 등록 하겠습니까?")) {
			$("#system-form").submit();
		}
		
	} else {
		
		swal({
			text: "항목을 등록 하겠습니까?",
			type: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: '확인',
			cancelButtonText: '취소'
		}).then(function(result) {
			if (result.value) {
				$("#system-form").submit();
			}
		});
	}
});

/**
 * 지표 가중치 합이 1이 되는지 확인
 * @param element
 * @returns
 */
function fn_indicator_weight_cal(element) {
	
	var total = 0;
	var isInfo = true;
	
	$("#list-" + element + " input.indicator-weight").each(function() {
		total += Number($(this).val()) * 100;
		console.log(typeof $(this).val());
		console.log(Math.round($(this).val(),3));
		console.log("값 : " +$(this).val() + "100 곱한 값 : "+ Number($(this).val()) * 100 + "  / 합 : " + total);
	});
	
	if(total != 100) {
		console.log("1 아닐때 값 : " + total);
		isInfo = false;
	}
	
	return isInfo;
}

/**
 * 지표 가중치 입력 && Numeric 타입인지 확인
 * @param element
 * @returns
 */
function fn_indicator_weight_input(element) {
	
	var pattern = /^[-]?\d+(?:[.]\d+)?$/;
	var isInfo = true;
	
	$("#list-" + element + " input.indicator-weight").each(function() {
		
		var val = $(this).val();
		
		if(val.length > 0) {
			
			if(!pattern.test(val)) {
				isInfo = false;
			}
			
		} else {
			isInfo = false;
		}
	});
	
	return isInfo;
}

/**
 * 분야 선택 여부
 * @returns
 */
function fn_select_field() {
	
	var isInfo = true;
	
	if($("select[id=item-field] option:selected").val() == "none") {
		isInfo = false;
	}
	
	return isInfo;
}

/**
 * 요약 설명 입력 여부
 * @returns
 */
function fn_input_account() {
	
	var isInfo = true;
	
	if($("input:text[name=item-account]").val().trim().length < 1) {
		isInfo = false;
	}
	
	return isInfo;
}

/**
 * 취약성 평가 산출식 입력 여부
 * @returns
 */
function fn_estimation_weight_input() {
	
	var isInfo = true;
	
	if($("input:text[name=climate-val-1]").val().length < 1 && isInfo) {
		isInfo = false;
	}
	
	if($("input:text[name=climate-val-2]").val().length < 1 && isInfo) {
		isInfo = false;
	}
	
	if($("input:text[name=climate-val-3]").val().length < 1 && isInfo) {
		isInfo = false;
	}
	
	return isInfo;
}

/**
 * 취약성 평가 산출식이 Numeric 타입인지 확인
 * @returns
 */
function fn_estimation_weight_isNaN() {
	
	var pattern = /^[-]?\d+(?:[.]\d+)?$/;
	var weight = 0;
	var isInfo = true;
	
	var val_1 = $("input:text[name=climate-val-1]").val();
	var val_2 = $("input:text[name=climate-val-2]").val();
	var val_3 = $("input:text[name=climate-val-3]").val();
	
	if(!pattern.test(val_1)) {
		isInfo = false;
	}
	
	if(!pattern.test(val_2)) {
		isInfo = false;
	}
	
	if(!pattern.test(val_3)) {
		isInfo = false;
	}
	
	return isInfo;
}


/**
 * 취약성 평가 산출식의 합이 1이 되는지 확인
 * @returns
 */
function fn_estimation_weight_cal() {
	
	var isInfo = true;
	
	var val_1 = $("input:text[name=climate-val-1]").val();
	var val_2 = $("input:text[name=climate-val-2]").val();
	var val_3 = $("input:text[name=climate-val-3]").val();
	
	var total = (Number(val_1) * 100) + (Number(val_2) * 100) + (Number(val_3) * 100);
	
	if(total != 100) {
		isInfo = false;
	}
	
	return isInfo;
}

/**
 * 항목 리스트 불러오기
 * @param page
 * @returns
 */
function fn_itemList(page) {
	
	$("#item-list").empty();
	$("#item-page").empty();
	
	var field_id = $("select[id=select-field] option:selected").val();
	
	$.ajax({
		url: "/admin/system/item/fieldItem.do",
		type: "get",
		data: {page: page, field_id: field_id},
		dataType: "json",
		success: function(data) {
			
			var itemList = data.itemList;
			var pageInfo = data.pageInfo;
			
			for(var i = 0; i < itemList.length; i++) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);" id="info-' + itemList[i].item_id + '" class="offcanvas-item item-info">' + itemList[i].item_nm + '</a>');
				
				$liTag.append($aTag);
				
				$("#item-list").append($liTag);
			}
			
			fn_noneParameterPage("item-page", "fn_itemList", pageInfo);
			
			if(itemList.length < 1) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);">해당 리스크에 항목이 없습니다.</a>');
				
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
 * 우측 지표 보기 창 열기
 */
$(document).on("click", ".offcanvas-indicator", function() {
	
	$(".offcanvas-right-open").removeClass("active");
	$(".offcanvas-right-open .card").removeClass("active");
	
	$('#offcanvas-indicator').addClass('active');
	$('#offcanvas-indicator .card').addClass('active');
});

/**
 * 우측 항목 상세보기 창 열기
 */
$(document).on("click", ".offcanvas-item", function() {
	
	$(".offcanvas-right-open").removeClass("active");
	$(".offcanvas-right-open .card").removeClass("active");
	
	$('#offcanvas-item').addClass('active');
	$('#offcanvas-item .card').addClass('active');
	
	$(".offcanvas-item").removeClass("active");
	$(this).addClass("active");
	
	if($("input:hidden[name=viewCategory]").val() == "update") {
		$("#check-overlap").prop("disabled", false);
		fn_itemCategory('item-update-view');
	}
});

/**
 * 좌측 분야 선택 시 항목 불러오기
 */
$(document).on("change", "#select-field", function() {
	
	$("#field-name").text($("select[id=select-field] option:selected").text() + " 리스크 평가항목");
	
	fn_itemList(1);
});

/**
 * 항목 클릭 시 상세보기
 */
$(document).on("click", ".item-info", function() {
	
	var item_id = $(this).attr("id").replace("info-", "");
	
	$(".item-info").removeClass("active");
	$(this).addClass("active");
	
	fn_itemInfo(item_id);
});

/**
 * 항목 클릭시 상세보기
 * @param item_id
 * @returns
 */
function fn_itemInfo(item_id) {
	
	$("#item-list-table > tbody").empty();
	$("#item-indicator-exp > tbody").empty();
	$("#item-indicator-sen > tbody").empty();
	$("#item-indicator-adp > tbody").empty();
	
	$.ajax({
		url: "/admin/system/item/itemInfo.do",
		type: "get",
		data: {item_id: item_id},
		dataType: "json",
		success: function(data) {
			
			var itemIndicatorList = data.itemIndicatorList;
			var itemListInfo = data.itemListInfo;
			
			var $tr;
			var $td;
			var $div;
			
			$tr = $('<tr></tr>');
			$td = $('<th class="text-left">항목명</th>');
			$tr.append($td);
			$td = $('<td>' + itemListInfo.item_nm + '</td>');
			$tr.append($td);
			$("#item-list-table > tbody").append($tr);
			//$("#item-name").val(itemListInfo.item_nm);
			
			$tr = $('<tr></tr>');
			$td = $('<th class="text-left">항목 요약 설명</th>');
			$tr.append($td);
			$td = $('<td>' + itemListInfo.item_account + '</td>');
			$tr.append($td);
			$("#item-list-table > tbody").append($tr);
			
			$tr = $('<tr></tr>');
			$td = $('<th class="text-left">리스크</th>');
			$tr.append($td);
			$td = $('<td>' + itemListInfo.field_nm + '</td>');
			$tr.append($td);
			$("#item-list-table > tbody").append($tr);
			
			
			/*switch (itemListInfo.field_id){
				case "FC001" :
					$("#item-field").val("FC001").prop("selected", true);
					break;
				case "FC002" :
					$("#item-field").val("FC002").prop("selected", true);
					break;
				case "FC003" :
					$("#item-field").val("FC003").prop("selected", true);
					break;
				case "FC004" :
					$("#item-field").val("FC004").prop("selected", true);
					break;
				case "FC005" :
					$("#item-field").val("FC005").prop("selected", true);
					break;
				case "FC006" :
					$("#item-field").val("FC006").prop("selected", true);
					break;
				case "FC007" :
					$("#item-field").val("FC007").prop("selected", true);
					break;
				default :
			}*/
			
			$tr = $('<tr></tr>');
			$td = $('<th class="text-left">기후 노출 가중치</th>');
			$tr.append($td);
			$td = $('<td>' + itemListInfo.ce_weight + '</td>');
			$tr.append($td);
			$("#item-list-table > tbody").append($tr);
			//$("#climate-val-1").val(itemListInfo.ce_weight);
			
			$tr = $('<tr></tr>');
			$td = $('<th class="text-left">기후 변화 민감도 가중치</th>');
			$tr.append($td);
			$td = $('<td>' + itemListInfo.cs_weight + '</td>');
			$tr.append($td);
			$("#item-list-table > tbody").append($tr);
			//$("#climate-val-2").val(itemListInfo.cs_weight);
			
			$tr = $('<tr></tr>');
			$td = $('<th class="text-left">적응 능력 가중치</th>');
			$tr.append($td);
			$td = $('<td>' + itemListInfo.aa_weight + '</td>');
			$tr.append($td);
			$("#item-list-table > tbody").append($tr);
			//$("#climate-val-3").val(itemListInfo.aa_weight);
			
			var expCnt = 0;
			var senCnt = 0;
			var adpCnt = 0;
			
			for(var i = 0; i < itemIndicatorList.length; i++) {
				
				$div = $('<div class="v-toolTipBox"><p><i class="icon-file-certificate-02"></i>' + itemIndicatorList[i].indi_nm + '</p></div>');
				$tr = $('<tr class="details-indicator" id="details-' + itemIndicatorList[i].indi_id + '"></tr>');
				$td = $('<td class="text-left"><div class="v-toolTip">' + itemIndicatorList[i].indi_nm + '</div></td>');
				$td.find("div").append($div);
				$tr.append($td);
				$td = $('<td>' + itemIndicatorList[i].indi_val_weight + '</td>');
				$tr.append($td);
				
				if(itemIndicatorList[i].sector_id == "SEC01") {
					
					$("#item-indicator-exp > tbody").append($tr);
					expCnt++;
					
				} else if(itemIndicatorList[i].sector_id == "SEC02") {
					
					$("#item-indicator-sen > tbody").append($tr);
					senCnt++;
					
				} else if(itemIndicatorList[i].sector_id == "SEC03") {
					
					$("#item-indicator-adp > tbody").append($tr);
					adpCnt++;
				}
			}
			
			$("#view-indicator-exp-tag > .icon-file-align > .base").text(expCnt);
			$("#view-indicator-sen-tag > .icon-file-align > .base").text(senCnt);
			$("#view-indicator-adp-tag > .icon-file-align > .base").text(adpCnt);
			
			if(expCnt < 1) {
				
				$tr = $('<tr></tr>');
				$td = $('<td colspan="2">지표가 없습니다.</td>');
				$tr.append($td);
				$("#item-indicator-exp > tbody").append($tr);
				
			} else {
				$("#view-indicator-exp-tag").addClass("after-content-add");
			}
			
			if(senCnt < 1) {
				
				$tr = $('<tr></tr>');
				$td = $('<td colspan="2">지표가 없습니다.</td>');
				$tr.append($td);
				$("#item-indicator-sen > tbody").append($tr);
				
			} else {
				$("#view-indicator-sen-tag").addClass("after-content-add");
			}
			
			if(adpCnt < 1) {
				
				$tr = $('<tr></tr>');
				$td = $('<td colspan="2">지표가 없습니다.</td>');
				$tr.append($td);
				$("#item-indicator-adp > tbody").append($tr);
				
			} else {
				$("#view-indicator-adp-tag").addClass("after-content-add");
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $tr = $('<tr></tr>');
			var $td = $('<td colspan="2">항목 정보를 불러 오는데 실패 했습니다.</td>');
			$tr.append($td);
			$("#item-list-table > tbody").append($tr);
			$("#item-indicator-exp > tbody").append($tr);
			$("#item-indicator-sen > tbody").append($tr);
			$("#item-indicator-adp > tbody").append($tr);
		},
		complete: function(data) {
			
		}
	});
}

/**
 * 부문별 지표 상세보기
 */
$(document).on("click", ".view-indicator", function() {
	
	var id = $(this).attr("id").replace("-tag", "");
	
	if($("#" + id).is(":visible")) {
		
		$("#" + id).addClass("d-none");
		$("#" + id + " > ul").addClass("d-none");
		$("#" + id + " > ul").slideUp();
		$(this).addClass("after-content-add");
		
	} else {
		
		$("#" + id).removeClass("d-none");
		$("#" + id + " > ul").removeClass("d-none");
		$("#" + id + " > ul").slideDown();
		$(this).removeClass("after-content-add");
	}
});

/**
 * 지표 상세보기 툴팁박스
 */
$(document).on("click", ".details-indicator", function() {
	
	$(".details-indicator").removeClass("active");
	$(this).addClass("active");
	
	var id = $(this).attr("id").replace("details-", "");
	
	if($("#details-" + id + " > td > .v-toolTip > .v-toolTipBox").hasClass("active")) {
		
		$(".v-toolTipBox").removeClass("active");
		
	} else {
		
		$(".v-toolTipBox").removeClass("active");
		$(this).find(".v-toolTipBox").addClass("active");
		fn_getIndicatorInfo(id);
	}
});

/**
 * 지표 상세보기 툴팁 박스 정보 불러오기
 * @param indi_id
 * @returns
 */
function fn_getIndicatorInfo(indi_id) {
	
	$("#details-" + indi_id + " > td > .v-toolTip > .v-toolTipBox").empty();;
	
	$.ajax({
		url: "/admin/system/indicator/indicatorData.do",
		type: "get",
		data: {indi_id: indi_id, page: 1},
		dataType: "json",
		success: function(data) {
			
			var indicatorListInfo = data.indicatorListInfo;
			
			var $table;
			var $tbody;
			var $tr;
			var $td;
			
			$table = $('<table class="tool-box-table"><colgroup><col width="20%"><col width="80%"></colgroup></table>');
			$tbody = $('<tbody></tbody>');
			
			$tr = $('<tr class="p-1"></tr>');
			$td = $('<th colspan="2" class="text-primary details-info-title">' + indicatorListInfo.indi_nm + '</th>');
			$tr.append($td);
			$tbody.append($tr);
			
			
			$tr = $('<tr></tr>');
			$td = $('<th class="text-success">지표 설명</th>');
			$tr.append($td);
			$td = $('<td class="text-left">' + indicatorListInfo.indi_account + '</td>');
			$tr.append($td);
			$tbody.append($tr);
			
			$tr = $('<tr></tr>');
			$td = $('<th class="text-success">구축/가공</th>');
			$tr.append($td);
			$td = $('<td class="text-left">' + indicatorListInfo.indi_construct_nm + '</td>');
			$tr.append($td);
			$tbody.append($tr);
			
			$tr = $('<tr></tr>');
			$td = $('<th class="text-success">단위</th>');
			$tr.append($td);
			$td = $('<td class="text-left">' + indicatorListInfo.indi_unit + '</td>');
			$tr.append($td);
			$tbody.append($tr);
			
			$tr = $('<tr></tr>');
			$td = $('<th class="text-success">대분류</th>');
			$tr.append($td);
			$td = $('<td class="text-left">' + indicatorListInfo.ipcc_large_nm + '</td>');
			$tr.append($td);
			$tbody.append($tr);
			
			$tr = $('<tr></tr>');
			$td = $('<th class="text-success">소분류</th>');
			$tr.append($td);
			$td = $('<td class="text-left">' + indicatorListInfo.ipcc_small_nm + '</td>');
			$tr.append($td);
			$tbody.append($tr);
			
			$table.append($tbody);
			$("#details-" + indi_id + " > td > .v-toolTip > .v-toolTipBox").append($table);
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $div;
			
			$div = $('<p><i class="icon-file-certificate-02"></i>지표 정보 상세보기</p>');
			$("#details-" + indi_id + " > td > .v-toolTip > .v-toolTipBox").append($div);
			
			$div = $('<div class="text-warning">사용자 정의 항목 상세보기 > 지표</div>');
			$("#details-" + indi_id + " > td > .v-toolTip > .v-toolTipBox").append($div);
			
			$div = $('<div class="text-danger">지표 정보 불러오기에 실패 했습니다.</div>');
			$("#details-" + indi_id + " > td > .v-toolTip > .v-toolTipBox").append($div);
		},
		complete: function(data) {
			
		}
	});
}

/**
 * 항목 생성 / 수정 버튼 클릭시 본문 내용 변경
 * @param id
 * @returns
 */
function fn_itemCategory(id) {
	
	$("input:hidden[name=viewCategory]").val(id.replace("item-", "").replace("-view", ""));
	
	var len = $(".item-info.active").length;
	
	/** 초기화 */
	$("input:text[name=item-name]").val("");
	$("input:text[name=item-account]").val("");
	$("select[name=item-field] option[value=none]").prop("selected", true);
	$("select[id=copy-template-field] option[value=none]").prop("selected", true);
	$("select[id=copy-template-item] option[value=none]").prop("selected", true);
	$("#climate-val-1").val("");
	$("#climate-val-2").val("");
	$("#climate-val-3").val("");
	
	/**
	 * 지표추가를 통하여 우측 canvas에 지표를 선택 하였을 경우
	 * 항목 생성 버튼 클릭 시 선택 한 지표를 초기화 시켜주기 위하여
	 * 해당 지표그룹내의 지표를 다시 로드 해 준다.
	 */
	if($("#indicator-group-select option:selected").val() != "none") {
		fn_groupIndicator($("#indicator-group-select option:selected").val(), 1);
	}
	
	$("#list-indicator-exp").empty();
	$("#list-indicator-sen").empty();
	$("#list-indicator-adp").empty();
	
	var $info = $('<li class="none-indicator"><div class="col-12 pl-0">지표추가를 클릭하여 지표를 추가 해야 합니다.</div></li>');
	$("#list-indicator-exp").append($info);
	$info = $('<li class="none-indicator"><div class="col-12 pl-0">지표추가를 클릭하여 지표를 추가 해야 합니다.</div></li>');
	$("#list-indicator-sen").append($info);
	$info = $('<li class="none-indicator"><div class="col-12 pl-0">지표추가를 클릭하여 지표를 추가 해야 합니다.</div></li>');
	$("#list-indicator-adp").append($info);
	
	if(id == "item-update-view") {
		
		if(len < 1) {
			
			fn_alert("경고", "수정 할 항목를 선택 해야 합니다.", "error");
			return false;
			
		} else {
			
			$("#system-form").attr("action", "/admin/system/item/update.do?${_csrf.parameterName}=${_csrf.token}");
			
			$("input:hidden[name=item_id]").val($(".item-info.active").attr("id").replace("info-", ""));
			
			var item_id = $(".item-info.active").attr("id").replace("info-", "");
			
			fn_setUpdateValue(item_id);
		}
		
	} else {
		offcanvasClose();
		$("#system-form").attr("action", "/admin/system/item/insert.do?${_csrf.parameterName}=${_csrf.token}");
	}
}

/**
 * 항목 수정 시 값 세팅
 * @returns
 */
function fn_setUpdateValue(item_id) {
	
	$.ajax({
		url: "/admin/system/item/itemInfo.do",
		type: "get",
		data: {item_id: item_id},
		dataType: "json",
		success: function(data) {
			
			var itemIndicatorList = data.itemIndicatorList;
			var itemListInfo = data.itemListInfo;
			
			$("#item-name").val(itemListInfo.item_nm);
			$("select[name=item-field] option[value=" + itemListInfo.field_id + "]").prop("selected", true);
			$("#item-account").val(itemListInfo.item_account);
			$("#climate-val-1").val(itemListInfo.ce_weight);
			$("#climate-val-2").val(itemListInfo.cs_weight);
			$("#climate-val-3").val(itemListInfo.aa_weight);
			
			$(".none-indicator").remove();
			
			for(var i = 0; i < itemIndicatorList.length; i++) {
				
				var sector = itemIndicatorList[i].sector_id;
				var name = "";
				
				if(sector == "SEC01") {
					name = "indicator-exp";
				} else if(sector == "SEC02") {
					name = "indicator-sen";
				} else if(sector == "SEC03") {
					name = "indicator-adp";
				}
				
				var $li = $('<li id="indi-list-' + itemIndicatorList[i].indi_id + '"></li>');
				var $con = $('<div class="row"></div>');
				var $chkCon = $('<div class="col-9 pl-3"></div>');
				var $valCon = $('<div class="col-3"></div>');
				var $chkEl = $('<div class="vestap-checkbox">'
						+ '<label>'
						+ '<input type="checkBox" name="' + name + '-chk-' + itemIndicatorList[i].indi_id + '">'
						+ '<span class="cr"><i class="cr-icon icon-check-01"></i></span>'
						+ '</label>'
						+ '</div>'
						+ '<div class="text-base pl-2">'
						+ itemIndicatorList[i].indi_nm
						+ '</div>');
				
				var $valEl = $('<input type="text" class="form-control float-right indicator-weight" '
						+ 'name="' + name + '-txt-' + itemIndicatorList[i].indi_id + '" placeholder="가중치(필수)" value="' + itemIndicatorList[i].indi_val_weight + '" maxlength="5">');
				
				$chkCon.append($chkEl);
				$valCon.append($valEl);
				
				$con.append($chkCon);
				$con.append($valCon);
				$li.append($con);
				
				$("#list-" + name).append($li);
			}
			
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $tr = $('<tr></tr>');
			var $td = $('<td colspan="2">항목 정보를 불러 오는데 실패 했습니다.</td>');
			$tr.append($td);
			$("#item-list-table > tbody").append($tr);
			$("#item-indicator-exp > tbody").append($tr);
			$("#item-indicator-sen > tbody").append($tr);
			$("#item-indicator-adp > tbody").append($tr);
		},
		complete: function(data) {
			
		}
	});
}

/**
 * 항목 수정 시 값 세팅
 * @returns
 */
function fn_setTemplateUpdateValue(item_id) {
	
	$.ajax({
		url: "/admin/system/item/itemInfo.do",
		type: "get",
		data: {item_id: item_id},
		dataType: "json",
		success: function(data) {
			
			var itemIndicatorList = data.itemIndicatorList;
			var itemListInfo = data.itemListInfo;
			
			$(".none-indicator").remove();
			
			for(var i = 0; i < itemIndicatorList.length; i++) {
				
				var sector = itemIndicatorList[i].sector_id;
				var name = "";
				
				if(sector == "SEC01") {
					name = "indicator-exp";
				} else if(sector == "SEC02") {
					name = "indicator-sen";
				} else if(sector == "SEC03") {
					name = "indicator-adp";
				}
				
				var $li = $('<li id="indi-list-' + itemIndicatorList[i].indi_id + '"></li>');
				var $con = $('<div class="row"></div>');
				var $chkCon = $('<div class="col-9 pl-3"></div>');
				var $valCon = $('<div class="col-3"></div>');
				var $chkEl = $('<div class="vestap-checkbox">'
						+ '<label>'
						+ '<input type="checkBox" name="' + name + '-chk-' + itemIndicatorList[i].indi_id + '">'
						+ '<span class="cr"><i class="cr-icon icon-check-01"></i></span>'
						+ '</label>'
						+ '</div>'
						+ '<div class="text-base pl-2">'
						+ itemIndicatorList[i].indi_nm
						+ '</div>');
				
				var $valEl = $('<input type="text" class="form-control float-right indicator-weight" '
						+ 'name="' + name + '-txt-' + itemIndicatorList[i].indi_id + '" placeholder="가중치(필수)" value="' + itemIndicatorList[i].indi_val_weight + '" maxlength="5">');
				
				$chkCon.append($chkEl);
				$valCon.append($valEl);
				
				$con.append($chkCon);
				$con.append($valCon);
				$li.append($con);
				
				$("#list-" + name).append($li);
			}
			
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $tr = $('<tr></tr>');
			var $td = $('<td colspan="2">항목 정보를 불러 오는데 실패 했습니다.</td>');
			$tr.append($td);
			$("#item-list-table > tbody").append($tr);
			$("#item-indicator-exp > tbody").append($tr);
			$("#item-indicator-sen > tbody").append($tr);
			$("#item-indicator-adp > tbody").append($tr);
		},
		complete: function(data) {
			
		}
	});
}

/**
 * 항목 삭제
 * @returns
 */
function fn_deleteItem() {
	
	var len = $(".item-info.active").length;
	
	if(len > 0) {
		
		var item_id = $(".item-info.active").attr("id").replace("info-", "");
		
		var agent = navigator.userAgent.toLowerCase();

		if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
										
			if(confirm("[확인] 정말 항목을 삭제 하겠습니까?")) {
				location.href="/admin/system/item/delete.do?item_id=" + item_id;
			}
			
		} else {
			
			swal({
				text: "정말 항목을 삭제 하겠습니까?",
				type: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: '확인',
				cancelButtonText: '취소'
			}).then(function(result) {
				if (result.value) {
					location.href="/admin/system/item/delete.do?item_id=" + item_id;
				}
			});
		}
		
	} else {
		
		fn_alert("경고", "삭제 할 항목을 선택 해야 합니다.", "error");
	}
}

/**
 * 템플릿 > 분야 이벤트
 */
$(document).on("change", "#copy-template-field", function() {
	
	var field_id = $(this).val();
	
	$("#copy-template-item").empty();
	
	/**
	 * UPDATE일때는 선택한 수정 항목에 지표를 바꿀기 위함이므로 지표 정보만 초기화 한다.
	 */
	if($("input:hidden[name=viewCategory]").val() != "update") {
		fn_template();
	}
	
	$.ajax({
		url: "/admin/system/item/templateItem.do",
		type: "get",
		data: {field_id: field_id},
		dataType: "json",
		success: function(data) {
			
			var itemList = data.itemList;
			
			var $option = $('<option value="none" selected>기존 취약성평가 항목</option>');
			$("#copy-template-item").append($option);
			
			for(var i = 0; i < itemList.length; i++) {
				
				$option = $('<option value="' + itemList[i].item_id + '">' + itemList[i].item_nm + '</option>');
				$("#copy-template-item").append($option);
			}
			
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $option = $('<option value="none" selected>기존 취약성평가 항목</option>');
			$("#copy-template-item").append($option);
			
			$option = $('<option value="none" selected>항목 불러오기 실패</option>');
			$("#copy-template-item").append($option);
		},
		complete: function(data) {
			
		}
	});
});

/**
 * 템플릿 > 항목 이벤트
 */
$(document).on("change", "#copy-template-item", function() {
	
	var item_id = $(this).val();
	
	if($("input:hidden[name=viewCategory]").val() == "update") {
		fn_updateTemplate();
	} else {
		fn_template();
	}
	
	/**
	 * 지표추가를 통하여 우측 canvas에 지표를 선택 하였을 경우
	 * 템플릿 생성 시 선택 한 지표를 초기화 시켜주기 위하여
	 * 해당 지표그룹내의 지표를 다시 로드 해 준다.
	 */
	if($("#indicator-group-select option:selected").val() != "none") {
		fn_groupIndicator($("#indicator-group-select option:selected").val(), 1);
	}
	
	if($("input:hidden[name=viewCategory]").val() == "update") {
		fn_setTemplateUpdateValue(item_id);
	} else {
		fn_setUpdateValue(item_id);
	}
});

function fn_updateTemplate() {
	$("#list-indicator-exp").empty();
	$("#list-indicator-sen").empty();
	$("#list-indicator-adp").empty();
}

function fn_template() {

	$("input:text[name=item-name]").val("");
	$("input:text[name=item-account]").val("");
	$("select[name=item-field] option[value=none]").prop("selected", true);
	$("#climate-val-1").val("");
	$("#climate-val-2").val("");
	$("#climate-val-3").val("");
	
	$("#list-indicator-exp").empty();
	$("#list-indicator-sen").empty();
	$("#list-indicator-adp").empty();
	
	var $info = $('<li class="none-indicator"><div class="col-12 pl-0">지표추가를 클릭하여 지표를 추가 해야 합니다.</div></li>');
	$("#list-indicator-exp").append($info);
	$info = $('<li class="none-indicator"><div class="col-12 pl-0">지표추가를 클릭하여 지표를 추가 해야 합니다.</div></li>');
	$("#list-indicator-sen").append($info);
	$info = $('<li class="none-indicator"><div class="col-12 pl-0">지표추가를 클릭하여 지표를 추가 해야 합니다.</div></li>');
	$("#list-indicator-adp").append($info);
	
	$("#custom-form").attr("action", "/admin/system/item/insert.do?${_csrf.parameterName}=${_csrf.token}");
}





$(document).on("keyup", ".item-keyword", function(e) {
	
	if(e.keyCode == 13) {
		
		findKeyword();
		
	} else {
		
		if($(this).val().length > 0) {
			$("span.remove-keyword span").css("display", "block");
		} else {
			$("span.remove-keyword span").css("display", "none");
		}
	}
});

$(document).on("click", "span.remove-keyword span", function() {
	$(this).prev('input').val('').trigger('change').focus();
	$("span.remove-keyword span").css("display", "none");
	fn_itemList(1);
});

$(document).ready(function() {
	$(".remove-keyword").css("width", "calc(100% - " + ($(".input-group-append").width() - 4) + "px)");
});


function findKeyword() {
	
	var keyword = $(".item-keyword").val();
	
	if(keyword.replace(/\s/gi, "").length > 1) {
		fn_itemKeywordList($(".item-keyword").val(), 1);
	} else {
		alert("검색어는 2자 이상 입력 해야 합니다.");
	}
}

function fn_itemKeywordList(keyword, page) {
	
	$("#item-list").empty();
	$("#item-page").empty();
	
	var field_id = $("select[id=select-field] option:selected").val();
	
	$.ajax({
		url: "/admin/system/item/fieldItem.do",
		type: "get",
		data: {keyword: keyword, page: page, field_id: field_id},
		dataType: "json",
		success: function(data) {
			
			var itemList = data.itemList;
			var pageInfo = data.pageInfo;
			
			for(var i = 0; i < itemList.length; i++) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);" id="info-' + itemList[i].item_id + '" class="offcanvas-item item-info">' + itemList[i].item_nm + '</a>');
				
				$liTag.append($aTag);
				
				$("#item-list").append($liTag);
			}
			
			fn_oneParameterPage("item-page", "fn_itemKeywordList", keyword, pageInfo);
			
			if(itemList.length < 1) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);">해당 리스크에 항목이 없습니다.</a>');
				
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