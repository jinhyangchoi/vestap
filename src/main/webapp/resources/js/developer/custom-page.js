/**
 * 변수가 page를 제외 하고 없는 pageInfo
 * @param pageInfo
 * @returns
 */
function fn_noneParameterPage(elementID, func_nm, pageInfo) {
	
	var $ul;
	var $li;
	var $a;
	
	$ul = $('<ul class="pagination previous"></ul>');
	$li = $('<li class="page-item"></li>');
	
	$a = $('<a class="page-link" href="javascript:' + func_nm + '(1);"><i class="icon-arrow-back"></i></a>');
	
	$li.append($a);
	$ul.append($li);
	
	$li = $('<li class="page-item"></li>');
	
	if(pageInfo.firstPageNoOnPageList - 1 < pageInfo.firstPageNo) {
		$a = $('<a class="page-link" href="javascript:void(0);"><i class="icon-arrow-caret-left"></i></a>');
	} else {
		$a = $('<a class="page-link" href="javascript:' + func_nm + '(' + (pageInfo.firstPageNoOnPageList - 1) + ');"><i class="icon-arrow-caret-left"></i></a>');
	}
	
	$li.append($a);
	$ul.append($li);
	
	$("#" + elementID).append($ul);
	
	$ul = $('<ul class="pagination"></ul>');
	
	for(var i = pageInfo.firstPageNoOnPageList; i <= pageInfo.lastPageNoOnPageList; i++) {
		
		$li = $('<li class="page-item"></li>');
		
		if(pageInfo.currentPageNo == i) {
			$li.addClass("active");
		}
		
		$a = $('<a class="page-link" href="javascript:' + func_nm + '(' + i + ');">' + i + '</a>');
		
		$li.append($a);
		$ul.append($li);
	}
	
	$("#" + elementID).append($ul);
	
	$ul = $('<ul class="pagination next"></ul>');
	$li = $('<li class="page-item"></li>');
	
	if(pageInfo.lastPageNoOnPageList + 1 > pageInfo.lastPageNo) {
		$a = $('<a class="page-link" href="javascript:void(0);"><i class="icon-arrow-caret-right"></i></a>');
	} else {
		$a = $('<a class="page-link" href="javascript:' + func_nm + '(' + (pageInfo.lastPageNoOnPageList + 1) + ');"><i class="icon-arrow-caret-right"></i></a>');
	}
	
	
	$li.append($a);
	$ul.append($li);
	
	$li = $('<li class="page-item"></li>');
	
	$a = $('<a class="page-link" href="javascript:' + func_nm + '(' + pageInfo.lastPageNo + ');"><i class="icon-arrow-forward"></i></a>');
	
	$li.append($a);
	$ul.append($li);
	
	$("#" + elementID).append($ul);
}

/**
 * 변수가 하나인 pageInfo
 * @param elementID
 * @param func_nm
 * @param param
 * @param pageInfo
 * @returns
 */
function fn_oneParameterPage(elementID, func_nm, param, pageInfo) {
	
	$("#" + elementID).empty();
	
	var $ul;
	var $li;
	var $a;
	
	$ul = $('<ul class="pagination previous"></ul>');
	$li = $('<li class="page-item"></li>');
	
	$a = $('<a class="page-link" href="javascript:' + func_nm + '(\'' + param + '\', 1);"><i class="icon-arrow-back"></i></a>');
	
	$li.append($a);
	$ul.append($li);
	
	$li = $('<li class="page-item"></li>');
	
	if(pageInfo.firstPageNoOnPageList - 1 < pageInfo.firstPageNo) {
		$a = $('<a class="page-link" href="javascript:void(0);"><i class="icon-arrow-caret-left"></i></a>');
	} else {
		$a = $('<a class="page-link" href="javascript:' + func_nm + '(\'' + param + '\', ' + (pageInfo.firstPageNoOnPageList - 1) + ');"><i class="icon-arrow-caret-left"></i></a>');
	}
	
	$li.append($a);
	$ul.append($li);
	
	$("#" + elementID).append($ul);
	
	$ul = $('<ul class="pagination"></ul>');
	
	for(var i = pageInfo.firstPageNoOnPageList; i <= pageInfo.lastPageNoOnPageList; i++) {
		
		$li = $('<li class="page-item"></li>');
		
		if(pageInfo.currentPageNo == i) {
			$li.addClass("active");
		}
		
		$a = $('<a class="page-link" href="javascript:' + func_nm + '(\'' + param + '\', ' + i + ');">' + i + '</a>');
		
		$li.append($a);
		$ul.append($li);
	}
	
	$("#" + elementID).append($ul);
	
	$ul = $('<ul class="pagination next"></ul>');
	$li = $('<li class="page-item"></li>');
	
	if(pageInfo.lastPageNoOnPageList + 1 > pageInfo.lastPageNo) {
		$a = $('<a class="page-link" href="javascript:void(0);"><i class="icon-arrow-caret-right"></i></a>');
	} else {
		$a = $('<a class="page-link" href="javascript:' + func_nm + '(\'' + param + '\', ' + (pageInfo.lastPageNoOnPageList + 1) + ');"><i class="icon-arrow-caret-right"></i></a>');
	}
	
	
	$li.append($a);
	$ul.append($li);
	
	$li = $('<li class="page-item"></li>');
	
	$a = $('<a class="page-link" href="javascript:' + func_nm + '(\'' + param + '\', ' + pageInfo.lastPageNo + ');"><i class="icon-arrow-forward"></i></a>');
	
	$li.append($a);
	$ul.append($li);
	
	$("#" + elementID).append($ul);
}

/**
 * 변수가 두개인 pageInfo
 * @param elementID
 * @param func_nm
 * @param param
 * @param pageInfo
 * @returns
 */
function fn_twoParameterPage(elementID, func_nm, param1, param2, pageInfo) {
	
	$("#" + elementID).empty();
	
	var $ul;
	var $li;
	var $a;
	
	$ul = $('<ul class="pagination previous"></ul>');
	$li = $('<li class="page-item"></li>');
	
	$a = $('<a class="page-link" href="javascript:' + func_nm + '(\'' + param1 +'\',\''+ param2 +'\', 1);"><i class="icon-arrow-back"></i></a>');
	
	$li.append($a);
	$ul.append($li);
	
	$li = $('<li class="page-item"></li>');
	
	if(pageInfo.firstPageNoOnPageList - 1 < pageInfo.firstPageNo) {
		$a = $('<a class="page-link" href="javascript:void(0);"><i class="icon-arrow-caret-left"></i></a>');
	} else {
		$a = $('<a class="page-link" href="javascript:' + func_nm + '(\'' +  param1 +'\',\''+ param2 + '\', ' + (pageInfo.firstPageNoOnPageList - 1) + ');"><i class="icon-arrow-caret-left"></i></a>');
	}
	
	$li.append($a);
	$ul.append($li);
	
	$("#" + elementID).append($ul);
	
	$ul = $('<ul class="pagination"></ul>');
	
	for(var i = pageInfo.firstPageNoOnPageList; i <= pageInfo.lastPageNoOnPageList; i++) {
		
		$li = $('<li class="page-item"></li>');
		
		if(pageInfo.currentPageNo == i) {
			$li.addClass("active");
		}
		
		$a = $('<a class="page-link" href="javascript:' + func_nm + '(\'' +  param1 +'\',\''+ param2 + '\', ' + i + ');">' + i + '</a>');
		
		$li.append($a);
		$ul.append($li);
	}
	
	$("#" + elementID).append($ul);
	
	$ul = $('<ul class="pagination next"></ul>');
	$li = $('<li class="page-item"></li>');
	
	if(pageInfo.lastPageNoOnPageList + 1 > pageInfo.lastPageNo) {
		$a = $('<a class="page-link" href="javascript:void(0);"><i class="icon-arrow-caret-right"></i></a>');
	} else {
		$a = $('<a class="page-link" href="javascript:' + func_nm + '(\'' +  param1 +'\',\''+ param2 + '\', ' + (pageInfo.lastPageNoOnPageList + 1) + ');"><i class="icon-arrow-caret-right"></i></a>');
	}
	
	
	$li.append($a);
	$ul.append($li);
	
	$li = $('<li class="page-item"></li>');
	
	$a = $('<a class="page-link" href="javascript:' + func_nm + '(\'' +  param1 +'\',\''+ param2 + '\', ' + pageInfo.lastPageNo + ');"><i class="icon-arrow-forward"></i></a>');
	
	$li.append($a);
	$ul.append($li);
	
	$("#" + elementID).append($ul);
}