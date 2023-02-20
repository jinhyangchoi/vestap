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

/**
 * [지표 추가 페이지]
 * 지표명 중복 검사
 */
$(document).on("click", "#check-overlap", function() {
	fn_isIndiName();
});

$(document).on("keydown", "#indicator-name", function() {
	$("#check-overlap").prop("disabled", false);
});

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

function fn_isIndiName() {
	
	/** 지표명을 가져오면서 앞 뒤 공백을 제거 해준다.(공백만 입력 시 자동으로 길이는 0이 됨) */
	var indiName = $("#indicator-name").val().trim();
	var regText = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9| |\*]+$/;
	
	if(indiName.length > 0) {
		
		if(indiName.length < 30) {
		
			if(regText.test(indiName)) {
				
				$.ajax({
					url: "/member/base/custom/indicator/isIndiName.do",
					data: {indiName: indiName},
					type: "get",
					dataType: "json",
					success: function(data) {
						
						var isName = Number(data.isName);
						
						if(isName > 0) {
							
							fn_alert("경고", "이미 사용중인 지표명입니다.", "error");
							
							$("#indicator-name").val("");
						} else {
							
							fn_alert("승인", "사용 가능한 지표명입니다.", "success");
							
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
			fn_alert("경고", "지표명은 30자 이내로 작성 해야 합니다.", "error");
		}
		
	} else {
		fn_alert("경고", "지표명을 입력 해 주세요.", "error");
	}
}

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

$(document).on("click", "#custom-reload", function() {
	location.reload();
});


$(document).on("click", "#custom-insert", function() {
	
	var enterForm = $("#indicator-insert-view input:radio[name=indicatorSelect]:checked").val();
	var pattern = /^[-]?\d+(?:[.]\d+)?$/;
	var isValue = true;
	var isInfo = true;
	var checkPattern = true;
	var areaNm = "";
	
	/**
	 * 지표값은 모두 필수 입력이므로 확인 한다.
	 */
	if(enterForm == "sf") {
		
		$("#indicator-insert-view .indicator-value").each(function() {
			
			var val = $(this).val();
			
			if(val.length < 1) {
				isValue = false;
				areaNm = $(this).siblings("div").text();
				return false;
			}
			
			if(!pattern.test(val)) {
				checkPattern = false;
				areaNm = $(this).siblings("div").text();
				return false;
			}
		});
	}
	
	if($("#indicator-insert-view input:text[name=indicator-expn]").val().length < 1) {
		isInfo = false;
		areaNm = "지표에 대한 요약 설명을 입력 해야 합니다..";
	}
	
	if($("#indicator-insert-view input:text[name=indicator-unit]").val().length < 1) {
		isInfo = false;
		areaNm = "지표 단위를 입력 해야 합니다.";
	}
	
	if($("#check-overlap").prop("disabled")) {
		
		if(isValue) {
			
			if(checkPattern) {
				
				if(isInfo) {
					
					if(enterForm == "fu") {
						
						if($.trim($("#indicator-upload-file").val()).length > 0) {
							
							var agent = navigator.userAgent.toLowerCase();
							
							if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
								
								if(confirm("[확인] 지표를 등록 하겠습니까?")) {
									$("#custom-form").submit();
								}
								
							} else {
								
								swal({
									text: "지표를 등록 하겠습니까?",
									type: 'warning',
									showCancelButton: true,
									confirmButtonColor: '#3085d6',
									cancelButtonColor: '#d33',
									confirmButtonText: '확인',
									cancelButtonText: '취소'
								}).then(function(result) {
									if (result.value) {
										
										$("#custom-form").submit();
									}
								});
							}
							
						} else {
							fn_alert("경고", "파일을 첨부해야 합니다.", "error");
						}
						
					} else {
						
						var agent = navigator.userAgent.toLowerCase();
						
						if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
							
							if(confirm("[확인] 지표를 등록 하겠습니까?")) {
								$("#custom-form").submit();
							}
							
						} else {
							
							swal({
								text: "지표를 등록 하겠습니까?",
								type: 'warning',
								showCancelButton: true,
								confirmButtonColor: '#3085d6',
								cancelButtonColor: '#d33',
								confirmButtonText: '확인',
								cancelButtonText: '취소'
							}).then(function(result) {
								if (result.value) {
									
									$("#custom-form").submit();
								}
							});
						}
					}
					
				} else {
					fn_alert("경고", areaNm, "error");
				}
				
			} else {
				
				fn_alert("경고", "지표값에 숫자 이외의 값은 입력 할 수 없습니다.[" + areaNm + "]", "error");
			}
			
		} else {
			fn_alert("경고", "지표값은 모두 입력 해야 합니다.[" + areaNm + "]", "error");
		}
		
	} else {
		fn_alert("경고", "지표명 중복 검사를 해야 합니다.", "error");
	}
});

$(document).on("change", "#select-field", function() {
	fn_IndicatorList(1);
});

function fn_IndicatorList(page) {
	
	$("#indicator-list").empty();
	$("#indicator-page").empty();
	
	$.ajax({
		url: "/member/base/custom/indicator/fieldIndicator.do",
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
			
			fn_noneParameterPage("indicator-page", "fn_IndicatorList", indicatorPage);
			
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

$(document).on("click", ".indicator-info", function() {
	
	var indi_id = $(this).attr("id").replace("info-", "");
	
	$(".indicator-info").removeClass("active");
	$(this).addClass("active");
	
	fn_IndicatorInfo(indi_id, 1);
	
	if($("#indicator-update-view").hasClass("active")) {
		
		$("#check-overlap-update").prop("disabled", false);
		
		fn_indicatorCategory("indicator-update-view");
	}
});

function fn_IndicatorInfo(indi_id, page) {
	
	$("#indicator-info-table > tbody").empty();
	$("#indicator-list-table > tbody").empty();
	$("#indicator-info-page").empty();
	
	$.ajax({
		url: "/member/base/custom/indicator/indicatorData.do",
		type: "get",
		data: {indi_id: indi_id, page: page},
		dataType: "json",
		success: function(data) {
			
			var indicatorInfoList = data.indicatorInfoList;
			var indicatorListInfo = data.indicatorListInfo;
			var indicatorPageInfo = data.indicatorPageInfo;
			
			var $tr = $('<tr></tr>');
			var $td;
			
			/**
			 * 지표 기본 정보
			 */
			$td = $('<th>지표명</th>');
			$tr.append($td);
			$td = $('<td>' + indicatorListInfo.indi_nm + '</td>');
			$tr.append($td);
			$("#indicator-list-table > tbody").append($tr);
			
			$tr = $('<tr></tr>');
			$td = $('<th>지표설명</th>');
			$tr.append($td);
			$td = $('<td>' + indicatorListInfo.indi_account + '</td>');
			$tr.append($td);
			$("#indicator-list-table > tbody").append($tr);
			
			$tr = $('<tr></tr>');
			$td = $('<th>지표구축/가공방법</th>');
			$tr.append($td);
			$td = $('<td>' + indicatorListInfo.indi_construct_nm + '</td>');
			$tr.append($td);
			$("#indicator-list-table > tbody").append($tr);
			
			$tr = $('<tr></tr>');
			$td = $('<th>지표단위</th>');
			$tr.append($td);
			$td = $('<td>' + indicatorListInfo.indi_unit + '</td>');
			$tr.append($td);
			$("#indicator-list-table > tbody").append($tr);
			
			$tr = $('<tr></tr>');
			$td = $('<th>지표 IPCC 1</th>');
			$tr.append($td);
			$td = $('<td>' + indicatorListInfo.ipcc_large_nm + '</td>');
			$tr.append($td);
			$("#indicator-list-table > tbody").append($tr);
			
			$tr = $('<tr></tr>');
			$td = $('<th>지표 IPCC 2</th>');
			$tr.append($td);
			$td = $('<td>' + indicatorListInfo.ipcc_small_nm + '</td>');
			$tr.append($td);
			$("#indicator-list-table > tbody").append($tr);
			
			/**
			 * 지역별 지표값
			 */
			$tr = $('<tr></tr>');
			
			for(var i = 0; i < indicatorInfoList.length; i++) {
				
				$td = $('<td>' + indicatorInfoList[i].district_nm + '</td>');
				$tr.append($td);
				
				$td = $('<td style="border-right: 1px solid #50617B;">' + fn_addComma(Number(indicatorInfoList[i].indi_val)) + '</td>');
				$tr.append($td);
				
				if(i % 2 != 0) {
					
					$("#indicator-info-table > tbody").append($tr);
					$tr = $('<tr></tr>');
				}
			}
			
			if(indicatorInfoList.length % 2 != 0) {
				$td = $('<td></td>');
				$tr.append($td);
				$td = $('<td></td>');
				$tr.append($td);
				$("#indicator-info-table > tbody").append($tr);
			}
			
			fn_oneParameterPage("indicator-info-page", "fn_IndicatorInfo", indi_id, indicatorPageInfo);
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			var $tr = $('<tr></tr>');
			var $td = $('<td colspan="지표 값 불러오기에 실패 했습니다."></td>');
		},
		complete: function(data) {
			
		}
	});
}

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
	
	var indi_id = $(".indicator-info.active").attr("id").replace("info-", "");
	
	$("input:hidden[name=indi_id]").val(indi_id);
	
	$.ajax({
		url: "/member/base/custom/indicator/indicatorUpdateData.do",
		type: "get",
		data: {indi_id: indi_id},
		dataType: "json",
		success: function(data) {
			
			var indicatorDataList = data.indicatorDataList;
			var indicatorListInfo = data.indicatorListInfo;
			
			/** 지표값 */
			for(var i = 0; i < indicatorDataList.length; i++) {
				
				var $container = $('<div class="col-6 mb-3"></div>');
				var $div = $('<div class="mb-1">' + indicatorDataList[i].district_nm + '</div>');
				var $input = $('<input type="text" class="form-control indicator-value"'
						+ ' name="' + indicatorDataList[i].district_cd + '"'
						+ ' placeholder="' + indicatorDataList[i].district_nm + ' 지역에 대한 지표값을 입력 하세요."'
						+ ' value="' + indicatorDataList[i].indi_val + '">');
				
				$container.append($div);
				$container.append($input);
				
				$("#update-indicator-value").append($container);
			}
			
			/** 기본 정보 */
			$("#indicator-update-view input:text[name=indicator-name]").val(indicatorListInfo.indi_nm);
			$("#indicator-update-view input:text[name=indicator-expn]").val(indicatorListInfo.indi_account);
			$("#indicator-update-view #select-construct option[value=" + indicatorListInfo.indi_construct + "]").prop("selected", true);
			$("#indicator-update-view input:text[name=indicator-unit]").val(indicatorListInfo.indi_unit);
			
			$("#indicator-update-view select[name=ipcc-select-1] option[value=" + indicatorListInfo.ipcc_large + "]").prop("selected", true);
			
			fn_ipcc_update(indicatorListInfo.ipcc_large, indicatorListInfo.ipcc_small);
			
			
		},
		beforeSend: function() {
			
		},
		error: function(xhr, status, error) {
			
		},
		complete: function(data) {
			
		}
	});
}


$(document).on("click", "#check-overlap-update", function() {
	fn_isIndiName_update();
});

$(document).on("keydown", "#indicator-name-update", function() {
	$("#check-overlap-update").prop("disabled", false);
});

/**
 * fn_idIndiName() 함수와 다른점은
 * update 시 지표명 중복검사는 자기자신은 검사 하면 안되므로 indi_id를 이용하여 자기 자신은 빼고 검색 해 준다.
 * @returns
 */
function fn_isIndiName_update() {
	
	var indi_id = $(".indicator-info.active").attr("id").replace("info-", "");
	
	/** 지표명을 가져오면서 앞 뒤 공백을 제거 해준다.(공백만 입력 시 자동으로 길이는 0이 됨) */
	var indiName = $("#indicator-name-update").val().trim();
	var regText = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9| |\*]+$/;
	
	if(indiName.length > 0) {
		
		if(indiName.length < 30) {
			
			if(regText.test(indiName)) {
				
				$.ajax({
					url: "/member/base/custom/indicator/isIndiName.do",
					data: {indiName: indiName, indi_id: indi_id},
					type: "get",
					dataType: "json",
					success: function(data) {
						
						var isName = Number(data.isName);
						
						if(isName > 0) {
							
							fn_alert("경고", "이미 사용중인 지표명 입니다.", "error");
							
							$("#indicator-name-update").val("");
							
						} else {
							
							fn_alert("승인", "사용가능한 지표명 입니다.", "success");
							
							$("#check-overlap-update").prop("disabled", true);
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
			fn_alert("경고", "지표명은 30자 이내로 작성 해야 합니다.", "error");
		}
		
	} else {
		fn_alert("경고", "지표명을 입력 해 주세요.", "error");
	}
}

$(document).on("click", "#custom-update", function() {
	
	var enterForm = $("#indicator-update-view input:radio[name=indicatorSelect]:checked").val();
	var pattern = /^[-]?\d+(?:[.]\d+)?$/;
	var isValue = true;
	var isInfo = true;
	var checkPattern = true;
	var areaNm = "";
	
	/**
	 * 지표값은 모두 필수 입력이므로 확인 한다.
	 */
	$("#indicator-update-view .indicator-value").each(function() {
		
		var val = $(this).val();
		
		if(val.length < 1) {
			isValue = false;
			areaNm = $(this).siblings("div").text();
			return false;
		}
		
		if(!pattern.test(val)) {
			checkPattern = false;
			areaNm = $(this).siblings("div").text();
			return false;
		}
	});
	
	if($("#indicator-update-view input:text[name=indicator-expn]").val().length < 1) {
		isInfo = false;
		areaNm = "지표에 대한 요약 설명을 입력 해야 합니다..";
	}
	/*
	if($("#indicator-update-view input:text[name=indicator-cont]").val().length < 1) {
		isInfo = false;
		areaNm = "지표구축/가공방법을 입력 해야 합니다.";
	}
	*/
	if($("#indicator-update-view input:text[name=indicator-unit]").val().length < 1) {
		isInfo = false;
		areaNm = "지표 단위를 입력 해야 합니다.";
	}
	
	if($("#check-overlap-update").prop("disabled")) {
		
		if(isValue) {
			
			if(checkPattern) {
				
				if(isInfo) {
					
					var agent = navigator.userAgent.toLowerCase();
					
					if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
						
						if(confirm("[확인] 수정사항을 저장 하겠습니까?")) {
							$("#custom-form-update").submit();
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
								$("#custom-form-update").submit();
							}
						});
					}
					
				} else {
					fn_alert("경고", areaNm, "error");
				}
				
			} else {
				fn_alert("경고", "지표값에 숫자 이외의 값은 입력 할 수 없습니다.[" + areaNm + "]", "error");
			}
			
		} else {
			fn_alert("경고", "지표값은 모두 입력 해야 합니다.[" + areaNm + "]", "error");
		}
		
	} else {
		fn_alert("경고", "지표명 중복 검사를 해야 합니다.", "error");
	}
});

function fn_deleteIndicator() {
	
	var len = $(".indicator-info.active").length;
	
	if(len > 0) {
		
		var agent = navigator.userAgent.toLowerCase();
		
		if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
			
			if(confirm("[확인] 정말 지표를 삭제 하겠습니까?")) {
				
				var indi_id = $(".indicator-info.active").attr("id").replace("info-", "");
				
				location.href="/member/base/custom/indicator/delete.do?indi_id=" + indi_id;
			}
			
		} else {
			
			swal({
				text: "정말 지표를 삭제 하겠습니까?",
				type: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: '확인',
				cancelButtonText: '취소'
			}).then(function(result) {
				if (result.value) {
					
					var indi_id = $(".indicator-info.active").attr("id").replace("info-", "");
					
					location.href="/member/base/custom/indicator/delete.do?indi_id=" + indi_id;
				}
			});
		}
		
	} else {
		fn_alert("경고", "삭제 할 지표를 선택 해야 합니다.", "error");
	}
}







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
	fn_IndicatorList(1);
});

$(document).ready(function() {
	$(".remove-keyword").css("width", "calc(100% - " + ($(".input-group-append").width() - 4) + "px)");
});


function findKeyword() {
	
	var keyword = $(".indicator-keyword").val();
	
	if(keyword.replace(/\s/gi, "").length > 1) {
		fn_IndicatorKeywordList($(".indicator-keyword").val(), 1);
	} else {
		alert("검색어는 2자 이상 입력 해야 합니다.");
	}
}

function fn_IndicatorKeywordList(keyword, page) {
	
	$("#indicator-list").empty();
	$("#indicator-page").empty();
	
	$.ajax({
		url: "/member/base/custom/indicator/fieldIndicator.do",
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
			
			fn_oneParameterPage("indicator-page", "fn_IndicatorKeywordList", keyword, indicatorPage);
			
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