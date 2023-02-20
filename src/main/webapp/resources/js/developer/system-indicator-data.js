/**
 * [지표 추가 페이지]
 * 직접입력 / 파일업로드 선택 이벤트
 */
$(document).on("click", ".indicator-select", function() {
	
	$(this).removeClass("btn-outline-green");
	$(this).addClass("btn-green");
	
	$(this).siblings("label").removeClass("btn-green");
	$(this).siblings("label").addClass("btn-outline-green");
	
	$(".indicator-view").removeClass("focus");
	$("#" + $("input:radio[name=indicatorSelect]:checked").val() + "-indicator-view").addClass("focus");
	
	fn_resizeOffcanvasTable();
});
//첨부파일 변경시 
$(document).on("change", "input:file[name=indicatorUploadFile]", function() {
	
	var fileValue = $(this).val().split("\\");
	var fileName = fileValue[fileValue.length - 1];
	var fileEx = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	
	if(fileEx == "xlsx") {
		
		$("#indicator-file-info").val(fileName);
		
	} else {
		
		fn_alert("경고", "xlsx 확장자의 엑셀 파일만 업로드 할 수 있습니다.", "error");
		
		$("input:file[name=indicatorUploadFile]").val("");
	}
});


$(document).on("change", "#ipcc-select-1", function() {
	
	var ipcc = $(this).val();
	
	$.ajax({
		url: "/member/base/custom/indicator/ipccDepth.do",
		data: {ipcc: ipcc},
		type: "get",
		dataType: "json",
		success: function(data) {
			
			var ipccList = data.ipccList;
			
			$("#ipcc-select-2").empty();
			
			for(var i = 0; i < ipccList.length; i++) {
				
				var $op = $('<option value="' + ipccList[i].code_id + '">' + ipccList[i].code_nm + '</option>');
				
				$("#ipcc-select-2").append($op);
			}
			
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
		},
		complete: function(data) {
			
		}
	});
});

/**
 * ipcc-select-1, ipcc-select-2 를 class로 만들고 생성,수정 동일하게 사용하기 보다
 * 서로 다른 id값으로 주고 생성에서 선택했던 ipcc-select와 수정에서 선택한 요소가 각각 따로 동작되게 한다.
 */
$(document).on("change", "#ipcc-select-1-update", function() {
	
	var ipcc = $(this).val();
	
	$.ajax({
		url: "/member/base/custom/indicator/ipccDepth.do",
		data: {ipcc: ipcc},
		type: "get",
		dataType: "json",
		success: function(data) {
			
			var ipccList = data.ipccList;
			
			$("#ipcc-select-2-update").empty();
			
			for(var i = 0; i < ipccList.length; i++) {
				
				var $op = $('<option value="' + ipccList[i].code_id + '">' + ipccList[i].code_nm + '</option>');
				
				$("#ipcc-select-2-update").append($op);
			}
			
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
		},
		complete: function(data) {
			
		}
	});
});

function fn_ipcc_update(ipcc_large, ipcc_small) {
	
	$.ajax({
		url: "/member/base/custom/indicator/ipccDepth.do",
		data: {ipcc: ipcc_large},
		type: "get",
		dataType: "json",
		success: function(data) {
			
			var ipccList = data.ipccList;
			
			$("#ipcc-select-2-update").empty();
			
			for(var i = 0; i < ipccList.length; i++) {
				
				var $op;
				
				if(ipccList[i].code_id == ipcc_small) {
					$op = $('<option value="' + ipccList[i].code_id + '" selected="selected">' + ipccList[i].code_nm + '</option>');
				} else {
					$op = $('<option value="' + ipccList[i].code_id + '">' + ipccList[i].code_nm + '</option>');
				}
				
				
				$("#ipcc-select-2-update").append($op);
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

$(document).on("click", "#system-reload", function() {
	location.reload();
});


//평가지표 클릭시 
$(document).on("click", ".indicator-info", function() {
	
	var indi_id = $(this).attr("id").replace("info-", "");
	
	$(".indicator-info").removeClass("active");
	$("#indicator-update-view").addClass("active");
	$(this).addClass("active");
	
	if($("#indicator-update-view").hasClass("active")) {
		
		$("#check-overlap-update").prop("disabled", false);
		
		fn_indicatorCategory("indicator-update-view");
	}
});

//평가지표 데이터 조회
function fn_indicatorCategory(id) {
	
	var len = $(".indicator-info.active").length;
	
	if(id == "indicator-update-view") {
		
		if(len < 1) {
			fn_alert("경고", "수정 할 지표를 선택 해야 합니다.", "error");
			return false;
		}
	}
	
	$(".indicator-view-category").removeClass("active");
	$("#" + id).addClass("active");
	
	/** 초기화 */
	$("#indicator-update-view #update-indicator-value").empty();
	$("#indicator-update-view input:checkbox[name=fieldSelect]").prop("checked", false);
	$("#indicator-update-view input:text[name=indicator-name]").val("");
	$("#indicator-update-view input:text[name=indicator-expn]").val("");
	$("#select-construct option:eq(0)").prop("selected", true);
	$("#indicator-update-view input:text[name=indicator-unit]").val("");
	$("#indicator-update-view textarea[name=indicator-construct-meth]").val("");
	$("#indicator-update-view textarea[name=indicator-deduction]").val("");
	$("#indicator-update-view input:text[name=indicator-year]").val("");
	$("#meta-list-table > tbody").empty();
	
	var indi_id = $(".indicator-info.active").attr("id").replace("info-", "");
	
	$("input:hidden[name=indi_id]").val(indi_id);
	
	$.ajax({
		url: "/admin/management/data/indicatorUpdateData.do",
		type: "get",
		data: {indi_id: indi_id},
		dataType: "json",
		success: function(data) {
			
			var indicatorListInfo = data.indicatorListInfo;
			var indicatorMetaList = data.indicatorMetaList;
			/** 기본 정보 */
			$("#indicator-update-view input:text[name=indicator-name]").val(indicatorListInfo.indi_nm);
			$("#indicator-update-view input:text[name=indicator-expn]").val(indicatorListInfo.indi_account);
			$("#indicator-update-view #select-construct option[value=" + indicatorListInfo.indi_construct + "]").prop("selected", true);
			$("#indicator-update-view input:text[name=indicator-unit]").val(indicatorListInfo.indi_unit);
			$("#indicator-update-view select[name=ipcc-select-1] option[value=" + indicatorListInfo.ipcc_large + "]").prop("selected", true);
			$("#indicator-update-view textarea[name=indicator-construct-meth]").val(indicatorListInfo.indi_construct_meth);
			$("#indicator-update-view textarea[name=indicator-deduction]").val(indicatorListInfo.indi_deduction);
			$("#indicator-update-view select[name=indicator-space] option[value=" + indicatorListInfo.indi_space + "]").prop("selected", true);
			$("#indicator-update-view input:text[name=indicator-year]").val(indicatorListInfo.indi_year);
			fn_ipcc_update(indicatorListInfo.ipcc_large, indicatorListInfo.ipcc_small);

			if(indicatorMetaList.length>0){
				for(var i = 0; i < indicatorMetaList.length; i++) {
				
				$tr = $('<tr></tr>');
				
				$td = $('<td>' + indicatorMetaList[i].meta_nm + '</td>');
				$tr.append($td);
				
				$td = $('<td>' + indicatorMetaList[i].meta_offer_org + '</td>');
				$tr.append($td);
				
				$("#meta-list-table > tbody").append($tr);
				}
			}else {
				$("#meta-list-table > tbody").append($('<tr><td colspan="2">원시 자료를 불러 올 수 없습니다.</td></td>'));
			}
			
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			$("#meta-list-table > tbody").append($('<tr><td colspan="2">원시 자료를 불러 올 수 없습니다.</td></td>'));
		},
		complete: function(data) {
			
		}
	});
}



$(document).on("keydown", "#indicator-name-update", function() {
	$("#check-overlap-update").prop("disabled", false);
});

/**
 * fn_idIndiName() 함수와 다른점은
 * update 시 지표명 중복검사는 자기자신은 검사 하면 안되므로 indi_id를 이용하여 자기 자신은 빼고 검색 해 준다.
 * @returns
 */

$(document).on("click", "#system-update", function() {
	
	var enterForm = $("#indicator-update-view input:radio[name=indicatorSelect]:checked").val();
	var isValue = true;
	var isInfo = true;
	var areaNm = "";
	
	
	if($("#indicator-update-view input:text[name=indicator-expn]").val().length < 1) {
		isInfo = false;
		areaNm = "지표에 대한 요약 설명을 입력 해야 합니다..";
	}
	
	if($("#indicator-update-view input:text[name=indicator-update-year]").val().length < 1) {
		isInfo = false;
		areaNm = "등록/수정연도를 입력 해야 합니다..";
	}
	
	if($("#indicator-update-view input:text[name=indicator-unit]").val().length < 1) {
		isInfo = false;
		areaNm = "지표 단위를 입력 해야 합니다.";
	}
				
	if($.trim($("#indicator-upload-file").val()).length > 0) {
				if(isInfo) {
					
					var agent = navigator.userAgent.toLowerCase();
					
					if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
						
						if(confirm("[확인] 수정사항을 저장 하겠습니까?")) {
							$(".display").css("display","block");
							$("#system-form-update").submit();
						}
						
					} else {
						
						swal({
							text: "수정사항을 저장 하겠습니까?",
							type: 'warning',
							showCancelButton: true,
							confirmButtonColor: '#3085d6',
							cancelButtonColor: '#d33',
							confirmButtonText: '확인',
							cancelButtonText: '취소'
						}).then(function(result) {
							if (result.value) {
								$(".display").css("display","block");
								$("#system-form-update").submit();
							}
						});
					}
					
				} else {
					fn_alert("경고", areaNm, "error");
				}
	}else {
					fn_alert("경고", "파일을 첨부해야 합니다.", "error");
				}
		
});



$(document).on("keyup", ".indicator-keyword", function(e) {
	
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
	fn_DataIndicatorList(1);
});

$(document).ready(function() {
	$(".remove-keyword").css("width", "calc(100% - " + ($(".input-group-append").width() - 4) + "px)");
});


function findKeyword() {
	
	var keyword = $(".indicator-keyword").val();
	
	if(keyword.replace(/\s/gi, "").length > 1) {
		fn_DataIndicatorKeywordList($(".indicator-keyword").val(), 1);
	} else {
		alert("검색어는 2자 이상 입력 해야 합니다.");
	}
}



//데이터 관리 지표 검색
function dataFindKeyword() {
	
	var keyword = $(".indicator-keyword").val();
	
	if(keyword.replace(/\s/gi, "").length > 1) {
		fn_DataIndicatorKeywordList($(".indicator-keyword").val(), 1);
	} else {
		alert("검색어는 2자 이상 입력 해야 합니다.");
	}
}



function fn_DataIndicatorList(page) {
	
	$("#indicator-list").empty();
	$("#indicator-page").empty();
	
	$.ajax({
		url: "/admin/management/data/fieldIndicator.do",
		type: "get",
		data: {page: page},
		dataType: "json",
		success: function(data) {
			
			var indicatorList = data.indicatorList;
			var indicatorPage = data.indicatorPage;
			
			for(var i = 0; i < indicatorList.length; i++) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);" id="info-' + indicatorList[i].indi_id + '" class="offcanvas-select indicator-info">' + indicatorList[i].indi_nm + '</a>');
				
				$liTag.append($aTag);
				
				$("#indicator-list").append($liTag);
			}
			
			fn_noneParameterPage("indicator-page", "fn_DataIndicatorList", indicatorPage);
			
			if(indicatorList.length < 1) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);">해당 분야에 지표가 없습니다.</a>');
				
				$liTag.append($aTag);
				
				$("#indicator-list").append($liTag);
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $liTag = $('<li></li>');
			var $aTag = $('<a href="javascript:void(0);">지표 불러오기 실패</a>');
			
			$liTag.append($aTag);
			
			$("#indicator-list").append($liTag);
		},
		complete: function(data) {
			
		}
	});
}

function fn_DataIndicatorKeywordList(keyword, page) {
	
	$("#indicator-list").empty();
	$("#indicator-page").empty();
	
	$.ajax({
		url: "/admin/management/data/fieldIndicator.do",
		type: "get",
		data: {keyword: keyword, page: page},
		dataType: "json",
		success: function(data) {
			
			var indicatorList = data.indicatorList;
			var indicatorPage = data.indicatorPage;
			
			for(var i = 0; i < indicatorList.length; i++) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);" id="info-' + indicatorList[i].indi_id + '" class="offcanvas-select indicator-info">' + indicatorList[i].indi_nm + '</a>');
				
				$liTag.append($aTag);
				
				$("#indicator-list").append($liTag);
			}
			
			fn_oneParameterPage("indicator-page", "fn_DataIndicatorKeywordList", keyword, indicatorPage);
			
			if(indicatorList.length < 1) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);">해당 분야에 지표가 없습니다.</a>');
				
				$liTag.append($aTag);
				
				$("#indicator-list").append($liTag);
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $liTag = $('<li></li>');
			var $aTag = $('<a href="javascript:void(0);">지표 불러오기 실패</a>');
			
			$liTag.append($aTag);
			
			$("#indicator-list").append($liTag);
		},
		complete: function(data) {
			
		}
	});
	
}