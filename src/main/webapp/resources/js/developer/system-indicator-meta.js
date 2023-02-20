
$(document).on("click", "#system-reload", function() {
	location.reload();
});


//평가지표 클릭시 
$(document).on("click", ".meta-info", function() {
	
	var indi_id = $(this).attr("id").replace("info-", "");
	
	$(".meta-info").removeClass("active");
	$("#meta-update-view").addClass("active");
	$(this).addClass("active");
	
	if($("#meta-update-view").hasClass("active")) {
		
		
		fn_metaCategory("meta-update-view");
	}
});

//평가지표 데이터 조회
function fn_metaCategory(id) {
	
	var len = $(".meta-info.active").length;
	
	if(id == "meta-update-view") {
		
		if(len < 1) {
			fn_alert("경고", "수정 할 원시자료를 선택 해야 합니다.", "error");
			return false;
		}
	}
	
	$(".meta-view-category").removeClass("active");
	$("#" + id).addClass("active");
	
	/** 초기화 */
	
	var meta_id = $(".meta-info.active").attr("id").replace("info-", "");
	
	$("input:hidden[name=meta_id]").val(meta_id);
	
	$.ajax({
		url: "/admin/management/meta/metaUpdateData.do",
		type: "get",
		data: {meta_id: meta_id},
		dataType: "json",
		success: function(data) {
			
			var metaListInfo = data.metaListInfo;
			/** 기본 정보 */
			$("#meta-update-view input:text[name=meta_nm]").val(metaListInfo.meta_nm);
			$("#meta-update-view input:text[name=meta_offer_org]").val(metaListInfo.meta_offer_org);
			$("#meta-update-view input:text[name=meta_offer_dept]").val(metaListInfo.meta_offer_dept);
			$("#meta-update-view input:text[name=meta_offer_system]").val(metaListInfo.meta_offer_system);
			$("#meta-update-view input:text[name=meta_base_unit]").val(metaListInfo.meta_base_unit);
			$("#meta-update-view input:text[name=meta_con_unit]").val(metaListInfo.meta_con_unit);
			$("#meta-update-view input:text[name=meta_con_year]").val(metaListInfo.meta_con_year);
			$("#meta-update-view input:text[name=meta_data_type]").val(metaListInfo.meta_data_type);
			$("#meta-update-view input:text[name=meta_position]").val(metaListInfo.meta_position);
			$("#meta-update-view input:text[name=meta_scale]").val(metaListInfo.meta_scale);
			$("#meta-update-view input:text[name=meta_unit]").val(metaListInfo.meta_unit);
			$("#meta-update-view input:text[name=meta_url]").val(metaListInfo.meta_url);
			
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


$(document).on("click", "#system-update", function() {
	
	var isValue = true;
	var isInfo = true;
	var areaNm = "";
	
	
	if($("#meta-update-view input:text[name=meta_offer_org]").val().length < 1) {
		isInfo = false;
		areaNm = "제공기관명을 입력 해야 합니다..";
	}
	
	if($("#meta-update-view input:text[name=meta_con_year]").val().length < 1) {
		isInfo = false;
		areaNm = "기준시점을 입력 해야 합니다..";
	}
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
		
});



$(document).on("keyup", ".meta-keyword", function(e) {
	
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
	fn_MetaIndicatorList(1);
});

$(document).ready(function() {
	$(".remove-keyword").css("width", "calc(100% - " + ($(".input-group-append").width() - 4) + "px)");
});


function findKeyword() {
	
	var keyword = $(".meta-keyword").val();
	
	if(keyword.replace(/\s/gi, "").length > 1) {
		fn_MetaIndicatorKeywordList($(".meta-keyword").val(), 1);
	} else {
		alert("검색어는 2자 이상 입력 해야 합니다.");
	}
}



//원시자료 지표 검색
function metaFindKeyword() {
	
	var keyword = $(".meta-keyword").val();
	
	if(keyword.replace(/\s/gi, "").length > 1) {
		fn_MetaIndicatorKeywordList($(".meta-keyword").val(), 1);
	} else {
		alert("검색어는 2자 이상 입력 해야 합니다.");
	}
}



function fn_MetaIndicatorList(page) {
	
	$("#meta-list").empty();
	$("#meta-page").empty();
	
	$.ajax({
		url: "/admin/management/meta/fieldIndicator.do",
		type: "get",
		data: {page: page},
		dataType: "json",
		success: function(data) {
			
			var metaList = data.metaList;
			var metaPage = data.metaPage;
			
			for(var i = 0; i < metaList.length; i++) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);" id="info-' + metaList[i].meta_id + '" class="offcanvas-select meta-info">' + metaList[i].meta_nm + '</a>');
				
				$liTag.append($aTag);
				
				$("#meta-list").append($liTag);
			}
			
			fn_noneParameterPage("meta-page", "fn_MetaIndicatorList", metaPage);
			
			if(metaList.length < 1) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);">해당 원시자료가 없습니다.</a>');
				
				$liTag.append($aTag);
				
				$("#meta-list").append($liTag);
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $liTag = $('<li></li>');
			var $aTag = $('<a href="javascript:void(0);">원시자료 불러오기 실패</a>');
			
			$liTag.append($aTag);
			
			$("#meta-list").append($liTag);
		},
		complete: function(data) {
			
		}
	});
}

function fn_MetaIndicatorKeywordList(keyword, page) {
	
	$("#meta-list").empty();
	$("#meta-page").empty();
	
	$.ajax({
		url: "/admin/management/meta/fieldIndicator.do",
		type: "get",
		data: {keyword: keyword, page: page},
		dataType: "json",
		success: function(data) {
			
			var metaList = data.metaList;
			var metaPage = data.metaPage;
			
			for(var i = 0; i < metaList.length; i++) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);" id="info-' + metaList[i].meta_id + '" class="offcanvas-select meta-info">' + metaList[i].meta_nm + '</a>');
				
				$liTag.append($aTag);
				
				$("#meta-list").append($liTag);
			}
			
			fn_oneParameterPage("meta-page", "fn_MetaIndicatorKeywordList", keyword, metaPage);
			
			if(metaList.length < 1) {
				
				var $liTag = $('<li></li>');
				var $aTag = $('<a href="javascript:void(0);">해당 원시자료가 없습니다.</a>');
				
				$liTag.append($aTag);
				
				$("#meta-list").append($liTag);
			}
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
			var $liTag = $('<li></li>');
			var $aTag = $('<a href="javascript:void(0);">원시자료 불러오기 실패</a>');
			
			$liTag.append($aTag);
			
			$("#meta-list").append($liTag);
		},
		complete: function(data) {
			
		}
	});
	
}