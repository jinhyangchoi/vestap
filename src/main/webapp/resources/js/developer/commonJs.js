// 공통스크립트
/**
 * ==========================================================================================================================
 * 1. 게시판 공통 스크립트
 * - 공지사항, FAQ, 건의사항, 자료실등의 게시판 종류과 관계없이 공통적으로 사용 되는 스크립트
 * ==========================================================================================================================
 */

/**
 * 게시글 검색 input:text에서 Enter 키 입력시 이벤트 처리
 */
$(document).on("keydown", "#board-search", function(event) {
	
	var keyCode = event.keyCode ? event.keyCode : event.which;
	
	if(keyCode == 13) {
		linkSearch();
		return false;
	}
});

/**
 * 좌측 메뉴 열기 닫기
 */
$(document).on("click", ".on-offmenu", function() {
	if($(".offcanvas-left-open").is(":visible")) {
		$(".offcanvas-left-open").addClass("d-none");
		$(".onmenu-div").removeClass("d-none");
	} else {
		$(".offcanvas-left-open").removeClass("d-none");
		$(".onmenu-div").addClass("d-none");
	}
	findMap();
});

/**
 * 게시글 검색 시 이벤트 처리
 * @returns
 */
function linkSearch() {
	
	var word = $("#board-search").val();
	var trimWord = word.replace(/ /gi, "");
	
	if(word.length <= 30) {
		
		if(trimWord.length > 1) {
			
			$("input:hidden[name=category]").val($("#search-category option:selected").val());
			$("input:hidden[name=keyword]").val(word);
			$("input:hidden[name=page]").val("1");
			
			$("#listFrm").submit();
			
		} else {
			
			if(trimWord.length == 1) {
				alert("검색어는 최소 두 자 이상 입력 해야 합니다.");
			} else {
				
				if(word.length > 0) {
					alert("공백만 입력시 검색을 할 수 없습니다.");
				} else {
					alert("검색어를 입력해주세요.");
				}
			}
		}
		
	} else {
		alert("30자 이내로 검색어를 입력 해 주세요.");
	}
}
 

/**
 * Page 클릭 시 이벤트 처리
 * @param pageNo
 * @returns
 */
function linkPage(pageNo) {
	
	$("input:hidden[name=page]").val(pageNo);
	
	$("#listFrm").submit();
}

/**
 * 게시 글 클릭 시 본문 불러오기
 * @param url
 * @param idx
 * @returns
 */
function linkContent(url, idx) {
	
	/** "/member/base/board/notice/content.do"
	 * 의 형태로 url이 들어오면 가운데 depth 인 notice만 가져온다
	 */
	var index1 = url.indexOf("/", 1);
	var index2 = url.indexOf("/", index1 + 1);
	var index3 = url.indexOf("/", index2 + 1);
	var index4 = url.indexOf("/", index3 + 1);
	var board = url.substring(index3 + 1, index4);
	var header = "/admin";

	if(board == "suggestion") {
		header = "/member/base";
	}
	
	$.ajax({
		url: url,
		data: {idx: idx},
		type: "get",
		dataType: "json",
		success: function(data) {
			
			var content = data.content;
			var files = data.files;
			var userAuth = data.userAuth;
			var name = data.name;
			
			if(board == 'suggestion'  && userAuth == 'A' ){
				header = "/admin";
			}
			
			$(".bbs_view").empty();
			$(".borad-file").empty();
			$("#bbs_management").empty();
			
			$("#bbs_title").append(content.bbs_title);
			$("#bbs_writer").append(content.bbs_writer);
			$("#bbs_regdate").append(content.bbs_regdate);
			$("#bbs_content").append(content.bbs_content);

			if( board == 'suggestion' && userAuth == 'A' && content.bbs_use =='Y'){ //건의사항에서 관리자 일때는 리스트가 다 보이지만 수정삭제 못함. 
				$("#bbs_management").append('<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2 mr-1" onclick="location.href=\'' + header + '/board/' + board + '/RefWrite.do?idx=' + idx + '\'">'
						+ '<i class="icon-folder-delete-01"></i>답글달기</button>');
				if(content.bbs_writer == name){					
					$("#bbs_management").append('<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2 mr-1" onclick="fn_deleteBoardList(\''+header+'\',\''+board+'\',\''+idx+'\')">'
							+ '<i class="icon-folder-delete-01"></i>삭제</button>');
					$("#bbs_management").append('<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2" onclick="location.href=\'' + header + '/board/' + board + '/modify.do?idx=' + idx + '\'">'
							+ '<i class="icon-refresh-modify"></i>수정</button>');
								}
			} else if(name==content.bbs_writer && content.bbs_use =='Y'){	//건의 사항 아닌 게시판이나 건의사항의 글쓴이는 자기 글만 보이게 되어 있으므로 다른 조건을 붙여주지 않아도 수정삭제가능
				$("#bbs_management").append('<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2 mr-1" onclick="fn_deleteBoardList(\''+header+'\',\''+board+'\',\''+idx+'\')">'
						+ '<i class="icon-folder-delete-01"></i>삭제</button>');
				
				$("#bbs_management").append('<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2" onclick="location.href=\'' + header + '/board/' + board + '/modify.do?idx=' + idx + '\'">'
						+ '<i class="icon-refresh-modify"></i>수정</button>');
			
			}
		
			$(".borad-file").append('<i class="icon-file-attach"></i>첨부파일 목록<br><br>');
			
			for(var i = 0; i < files.length; i++) {
				$(".borad-file").append('<a href="/member/base/board/' + board + '/download.do?std_file_nm=' + files[i].std_file_nm + '&bbs_idx=' + files[i].bbs_idx + '">'
						+ '<span><i class="icon-file-attach"></i>  ' + files[i].org_file_nm + '</span><span>' + files[i].file_hit + ' downloads</span></a><br>');
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
function fn_deleteBoardList(header,board,idx){
	
	var agent = navigator.userAgent.toLowerCase();

	if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
									
		if(confirm("[확인] 정말 게시글을 삭제 하겠습니까?")) {
			location.href= header + '/board/' + board + '/delete.do?idx=' + idx;
		}
		
	} else {
		
		swal({
			text: "정말 게시글을 삭제 하겠습니까?",
			type: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: '확인',
			cancelButtonText: '취소'
		}).then(function(result) {
			if (result.value) {
				
				location.href= header + '/board/' + board + '/delete.do?idx=' + idx;
				
			}
		});
	}
}
/**
 * 게시글 클릭 시 본문 보여주기
 * @returns
 */
function offcanvasOpen() {
	
	$('.offcanvas-right-open').addClass('active');
	$('.offcanvas-right-open .card').addClass('active');
	
	findMap();
}

/**
 * 본문 닫기 버튼 클릭 시 본문 없애기
 * @returns
 */
function offcanvasClose() {
	
	$('.offcanvas-right-open').removeClass('active');
	$('.offcanvas-right-open .card').removeClass('active');
	$('.offcanvas-select').removeClass('active');
	$('.offcanvas-item').removeClass('active');
	$('.offcanvas-estimation').removeClass('active');
	findMap();
}

function fn_historyBack() {
	
	if(confirm("취소 하시면 게시글은 저장되지 않고 이전 페이지로 이동합니다.")) {
		history.back();
	}
}

function fn_validate() {
	var bbs_title = $("#bbs_title").val();
	
	if(bbs_title.length < 1){
		
		fn_alert("경고", "제목을 입력하여 주십시오.", "warning");
		CKEDITOR.instances.content.focus();
		return false;
		
	} else if (CKEDITOR.instances.content.getData().length <= 0) {
		
		fn_alert("경고", "내용을 입력하여 주십시오.", "warning");
		CKEDITOR.instances.content.focus();
		return false;
		
	}else if (CKEDITOR.instances.content.getData().length > 4000) {
		
		fn_alert("경고", "내용은 4000자까지 입력 가능합니다.", "warning");
		CKEDITOR.instances.content.focus();
		return false;
	}
	return true;
}

function fn_submit() {
	
	if (fn_validate()) {
		
		$("#board-insert").submit();
	}
}

$(document).on("change", "input:file[name=uploadFile]", function() {
	var num = $("#add-file-area > .add-file-element").length;
	var cnt = $("#std-file-area > .add-file-element").length;
	var files = $("#add-file").get(0).files;
	if((files.length + num + cnt) < 6) {
		for(var i = 0; i < files.length; i++) {
			var fileNamed = files[i].name;
			var fileName = fileNamed.slice(fileNamed.lastIndexOf(".") + 1).toLowerCase();
			if(fileName != "hwp" && fileName != "pptx" &&  fileName != "pdf" &&  fileName != "xlsx" &&  fileName != "txt"
				 &&  fileName != "jpg" &&  fileName != "png" &&  fileName != "gif" &&  fileName != "jpeg") {
				$("#add-file-area").empty();
				$("#add-file").val("");
				fn_alert("경고", "첨부파일은 (hwp, pptx, pdf, xlsx, txt, jpg, png, gif, jpeg) 형식만 등록 가능합니다.", "info");
				return false;
			} else {
			var $con = $('<div class="row add-file-element"></div>');
			var $name = $('<div class="col-10 p-1 pl-4"><span class="file-name">' + files[i].name + '</span></div>');
			var $btn = $('<div class="col-2 p-1 pl-0 pr-4 text-right"><span><a href="javascript:void(0);" class="pt-1 del-file" style="display: inline-block;"><i class="icon-close-bold"></i></a></span></div>');
			$con.append($name);
			$con.append($btn);
			$("#add-file-area").append($con);
			$("#board-insert").append('<input type="hidden" name="matchName" value="' + files[i].name + '">');
			}
		}
	} else {
		$("#add-file-area").empty();
		$("#add-file").val("");
		fn_alert("경고", "파일은 최대 5개까지만 업로드 할 수 있습니다.", "info");
		return false;
	}
});

$(document).on("click", ".del-file", function() {
	
	var fileName = $(this).parents(".add-file-element > div > span.file-name").text();
	fileName = $(this).parents("div").siblings("div").children("span.file-name").text();
	
	$("input:hidden[name=matchName]").each(function() {
		
		if($(this).val() == fileName) {
			$(this).remove();
		}
	});
	
	$(this).parents(".add-file-element").remove();
	
	var num = $("#add-file-area > .add-file-element").length;
	
	/**
	 * 첨부한 파일 목록을 모두 지웠으므로 file 객체를 초기화 한다.
	 */
	if(num < 1) {
		$("#add-file").val("");
	}
});

$(document).on("click", ".modify-file", function() {
	
	if($(this).hasClass("modify-file-element")) {
		
		$(this).removeClass("modify-file-element");
		$(this).html('<i class="icon-close-bold"></i>');
		
		var stdName = $(this).parents(".add-file-element").children("input:hidden[name=stdName]").val();
		
		$("input:hidden[name=deleteStdFile]").each(function() {
			
			if($(this).val() == stdName) {
				$(this).remove();
			}
		});
		
	} else {
		
		$("#board-insert").append('<input type="hidden" name="deleteStdFile" value="' + $(this).parents(".add-file-element").children("input:hidden[name=stdName]").val() + '">');
		$(this).addClass("modify-file-element");
		$(this).html('삭제취소');
	}
});



/**
 * ============================================================================================================
 * 공통 스크립트
 * ============================================================================================================
 */

/**
 * offcavans table 이 화면 크기보다 작아질때 화면의 100%로 맞춰준다.
 */
function fn_resizeOffcanvasTable() {
	
	var height = $(window).height() - 70;
	var table = $(".offcanvas-table").height();
	
	if(height < table) {
		$(".offcanvas-table").css("height", height + "px");
	}
}


$( document ).ready(function() {
	
	/**
	 * 우측 Popup
	 */
	$(document).on("click", ".offcanvas-select", function() {
		$(".offcanvas-select").removeClass("active");
		$(this).addClass("active");
		offcanvasOpen();
	});
	
	$(document).on("click", ".offcanvasCloseBtn", function() {
		offcanvasClose();
	});
	
	// 테이블 TR 링크
    $('.trHref').click(function(){
        window.location = $(this).attr('href');
        return false;
    });

	// 날짜표시
	 $(".date-picker").datepicker({
	 	dateFormat: 'yy-mm-dd',
	    changeMonth: true,
	    changeYear: true,
		dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
     	dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'], 
     	monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
     	monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
	 });
	 $(".date-picker").on("change", function () {
	    var id = $(this).attr("id");
	    var val = $("label[for='" + id + "']").text();
	    $("#msg").text(val + " changed");
	});


	$(document).ready(function(){	// 값이 변경되면
	  var fileTarget = $('.filebox .upload-hidden1');
	
	    fileTarget.on('change', function(){
	        if(window.FileReader){	// modern browser
	            var filename = $(this)[0].files[0].name;
	        } else {	// old IE
	            var filename = $(this).val().split('/').pop().split('\\').pop();	// 파일명만 추출
	        }
			// 추출한 파일명 삽입
	        $(this).siblings('.upload-name1').val(filename);

	    });
	}); 
	$(document).ready(function(){	// 값이 변경되면
	  var fileTarget = $('.filebox .upload-hidden2');
	
	    fileTarget.on('change', function(){
	        if(window.FileReader){	// modern browser
	            var filename = $(this)[0].files[0].name;
	        } else {	// old IE
	            var filename = $(this).val().split('/').pop().split('\\').pop();	// 파일명만 추출
	        }
			// 추출한 파일명 삽입
	        $(this).siblings('.upload-name2').val(filename);

	    });
	}); 
	$(document).ready(function(){	// 값이 변경되면
	  var fileTarget = $('.filebox .upload-hidden3');
	
	    fileTarget.on('change', function(){
	        if(window.FileReader){	// modern browser
	            var filename = $(this)[0].files[0].name;
	        } else {	// old IE
	            var filename = $(this).val().split('/').pop().split('\\').pop();	// 파일명만 추출
	        }
			// 추출한 파일명 삽입
	        $(this).siblings('.upload-name3').val(filename);

	    });
	}); 

    $('.bright-Toggle').click(function () {
		$('.bright-Toggle').toggleClass('active');
	});

	// $( ".resizable" ).resizable({ grid: [10000, 1] }); // vertical

// *************************** 20181015 추가 *************************** 
    $('.offcanvas-select').click(function () {
    	$('.normal-text').addClass('hidden');
	});
    $('.offcanvasCloseBtn').click(function () {
    	$('.normal-text').removeClass('hidden');
	});

    


//	$(".toolTip").hover( 
//	      function(){ 
//	            $(".toolTipBox").addClass("active");
//	      }, 
//	      function(){
//	            $(".toolTipBox").removeClass("active"); 
//	      } 
//	 );
    
    
    $(document).on("click", ".icon-remove-bold", function() {
		$(this).removeClass("icon-remove-bold");
		$(this).addClass("icon-add-bold");
		
		var $list = $(this).parent().next();
//		$list.hide();
		if ($list.is(":visible")) {
			$list.slideUp();
		} else {
			$list.slideDown();
		}
		
	});
    
    $(document).on("click", ".icon-add-bold", function() {
		$(this).removeClass("icon-add-bold");
		$(this).addClass("icon-remove-bold");
		
		var $list = $(this).parent().next();
//		$list.show();
		if ($list.is(":visible")) {
			$list.slideUp();
		} else {
			$list.slideDown();
		}
	});

});

/**********************************************************************************/
function findMap(){
	var mapArray = ["esti_map","comp_base_map","comp_comp_map","expo_map","whole_esti_map","custom_esti_map","system_esti_map"];
	for(var i in mapArray){
		var map_id=$("#"+mapArray[i]).attr('id');
		if(map_id!=null){
			updateMap();
		}		
	}
}

function fn_removeKeyword(removeStr, removeId) {
	
	var keyword = $("input:hidden[name=keyword]").val();
	
	keyword = keyword.replace(removeStr, "").trim();
	keyword = keyword.replace("  ", " ");
	
	$("input:hidden[name=keyword]").val(keyword);
	$("#" + removeId).remove();
}
/* 천단위 콤마 찍기 */
function fn_addComma(num) {
	
	var regexp = /\B(?=(\d{3})+(?!\d))/g;
	
	if(num == undefined){
		return "";
		
	} else {
		
		return num.toString().replace(regexp, ',');
	}
}

/**
 * alert / sweetalert
 * @param title
 * @param text
 * @param type
 * @returns
 */
function fn_alert(title, text, type) {
	
	/**
	 * 익스플로러의 경우 일반 alert, 기타 다른 브라우저의 경우 sweetalert
	 * 따라서, 현재 접속한 브라우저의 종류를 판별한다.
	 */
	var agent = navigator.userAgent.toLowerCase();
	
	if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
		
		alert("[" + title + "] " + text);
		
	} else {
		
		swal({
			title: title,
			html: text,
			type: type
		});
	}
}

$(document).on("change", "input:file[name=uploadFileOne]", function() {
	$("#std-file-area").empty();
	$("#add-file-area").empty();
	var num = $("#add-file-area > .add-file-element").length;
	var cnt = $("#std-file-area > .add-file-element").length;
	var files = $("#add-file").get(0).files;
	
	if((files.length + num + cnt) < 2) {
		
		for(var i = 0; i < files.length; i++) {
			var fileNamed = files[i].name;
			var fileName = fileNamed.slice(fileNamed.lastIndexOf(".") + 1).toLowerCase();
			if(fileName != "hwp" && fileName != "pptx" &&  fileName != "pdf" &&  fileName != "xlsx" &&  fileName != "txt" &&  fileName != "jpg" &&  fileName != "png" &&  fileName != "gif" &&  fileName != "jpeg") {
				$("#add-file-area").empty();
				$("#add-file").val("");
				fn_alert("경고", "첨부파일은 (hwp, pptx, pdf, xlsx, txt, jpg, png, gif, jpeg) 형식만 등록 가능합니다.", "info");
				return false;
			} else {
				var $con = $('<div class="row add-file-element"></div>');
				var $name = $('<div class="col-10 p-1 pl-4"><span class="file-name">' + files[i].name + '</span></div>');
				var $btn = $('<div class="col-2 p-1 pl-0 pr-4 text-right"><span><a href="javascript:void(0);" class="pt-1 del-file" style="display: inline-block;"><i class="icon-close-bold"></i></a></span></div>');
				
				$con.append($name);
				$con.append($btn);
				
				$("#add-file-area").append($con);
//				$("input:hidden[name=matchName]").remove();
				$("#userInfo").append('<input type="hidden" name="matchName" value="' + files[i].name + '">');
			}
		}
		
	} else {
		
		fn_alert("경고", "파일은 최대 1개까지만 업로드 할 수 있습니다.", "info");
		return false;
	}
});

//2021 function fn_alert(title, text, type) { 
function fn_u_userApply_confirm(adminChk, title, text, type) {

	var agent = navigator.userAgent.toLowerCase();

	if ((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {

		if (confirm(text)) {
			location.href = '/loginPage.do';
		}

	} else {

		swal({
			html : text,
			type : type,
			showCancelButton : true,
			confirmButtonColor : '#3085d6',
			cancelButtonColor : '#d33',
			confirmButtonText : '확인',
			cancelButtonText : '취소'
		}).then(function(result) {

			if (result.value && adminChk ==false || adminChk =="false") {
				location.href = '/loginPage.do';
			} else {
				location.href = '/admin/user/management/list.do';
			}
		});
	}
}
