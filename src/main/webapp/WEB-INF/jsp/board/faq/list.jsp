<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP - 열린마당 - FAQ</title>

<style type="text/css">
.notice-w-150 { width: 150px; }
.vestapTable th,
.vestapTable td:not(.t-overflow) {text-align: center;}
.pagination {margin-right: 5px;}
.pagination.previous > li > .page-link, .pagination.next > li > .page-link { padding: 8px 6px; }

.borad-file > a {
	display: inline-block;
	width: 100%;
	padding: 5px 0;
}

.borad-file > a > span {
	display: inline-block;
	width: 50%;
}

.borad-file > a > span:last-child {
	text-align: right;
}
</style>


</head>
<body>

<table class="offcanvas-table">
	<tr>
		<td class="col align-top p-0">
			
			<div class="mainContents"><!-- mainContents -->
			
				<div class="navbar p-0 pb-3">
					
					<div class="contentsTitle"><i class="icon-help"> </i>FAQ</div>
					
					<form class="form-inline mt-4">
						<div class="col pr-2">
							<div class="select_box">
								<select class="form-control" id="search-category" style="width:90px">
									<option value="bbs_title" selected="selected">글 제목</option>
									<option value="bbs_content">글 내용</option>
									<option value="bbs_writer">작성자</option>
								</select>
							</div>
						</div>
						<div class="input-group search">
							<input type="text" class="form-control w-300px" id="board-search" placeholder="검색어를 입력해주세요" maxlength="30">
							<div class="input-group-append">
								<button class="btn btn-outline-secondary" type="button" id="button-addon2" onclick="linkSearch();"><i class="icon-search"></i></button>
							</div>
						</div>
					</form>
					
				</div><!-- //navbar -->
				
				<table class="table table-hover vestapTable mainTable">
					<colgroup>
						<col class="notice-w-100">
						<col style="width: *;">
						<col class="notice-w-100">
						<col class="notice-w-150">
						<col class="notice-w-100">
					</colgroup>
					<thead>
						<tr>
							<th>번호</th>
							<th>FAQ</th>
							<th>등록일</th>
							<th>작성자</th>
							<th>조회수</th>
						</tr>
					</thead>
					<tbody>
					
					<c:if test="${fn:length(boardList) eq 0 }">
						<tr>
							<td colspan="5">등록 된 FAQ가 없습니다.</td>
						</tr>
					</c:if>
					
					<c:forEach items="${boardList }" var="list">
						<tr class="offcanvas-select" onclick="javascript:linkContent('/member/base/board/faq/content.do', '${list.bbs_idx}');">
							<td><c:out value="${list.RNUM }"/></td>
							<td class="t-overflow category-title search-nm"><c:out value="${list.bbs_title }"/></td>
							<td><c:out value="${list.bbs_regdate }"/></td>
							<td class="category-writer"><c:out value="${list.bbs_writer }"/></td>
							<td><c:out value="${list.bbs_hit }"/></td>
						</tr>
					</c:forEach>
					
					</tbody>
				</table>
				
				<sec:authorize access="hasAnyRole('ROLE_WIDE', 'ROLE_BASE')">
					<!-- pagination -->
					<nav class="form-inline mt-4 justify-content-center">
						<c:if test="${not empty pageInfo }">
							<ui:pagination paginationInfo="${pageInfo }" type="text" jsFunction="linkPage"/>
						</c:if>
					</nav><!-- //pagination -->
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<div class="nav navbar p-0 mt-4 justify-content-center">
						<!-- pagination -->
						<nav class="form-inline">
							<c:if test="${not empty pageInfo }">
								<ui:pagination paginationInfo="${pageInfo }" type="text" jsFunction="linkPage"/>
							</c:if>
						</nav><!-- //pagination -->
						<!-- <button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2" onclick="location.href='/admin/board/faq/write.do'"><i class="icon-add-bold"></i>FAQ 등록</button> -->
					</div>
				</sec:authorize>
				
			</div><!-- //mainContents -->

  			</td>
  			<td class="offcanvas-right-open"><!-- *********************** offcanvas-right-open *********************** -->

			<div class="card notice board-content" style="height: 1450px"><!-- 리스트 수가 20개이거나 20개나 안될 경우 높이 입니다 -->
				<div class="card-header offcanvas-right-open-title">
					<i class="icon-file-doc-check"></i><span id="bbs_title" class="bbs_view">VESTAP 에서 사용된_ DB 지표 갱신 항목</span>
					<button type="button" class="offcanvasCloseBtn close"><i class="icon-close-bold"></i></button>
				</div>
				
				<ul class="list-group list-group-flush">
					<li class="list-group-item">
						
						<span class="borad-dete mr-4"><i class="icon-user-login-line"></i>작성자<span class="borad-dete-val bbs_view" id="bbs_writer">관리자</span></span>
						<span class="borad-dete"><i class="icon-alarm02"></i>작성일<span class="borad-dete-val bbs_view" id="bbs_regdate">2013-03-01</span></span>
						
					</li>
					<li class="list-group-item p-4">
						
						<div class="borad-contents bbs_view" id="bbs_content"></div>
						<div class="borad-file"></div>
						
					</li>
					
					<!-- 관리자 접속시 --><%-- 
					<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="list-group-item p-4">
						
						<span class="borad-dete float-right">
							<span class="borad-dete-val bbs_view" id="bbs_management">
								<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2 mr-1" onclick="location.href='/admin/board/faq/delete.do'"><i class="icon-folder-delete-01"></i>삭제</button>
								<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2" onclick=""><i class="icon-refresh-modify"></i>수정</button>
							</span>
						</span>
						
					</li>
					</sec:authorize> --%>
				</ul>
			</div><!-- //card -->
				
			</td>
	</tr>
</table>

<form id="listFrm" action="/member/base/board/faq/list.do">
	
	<input type="hidden" name="page" value="1">
	<input type="hidden" name="category" value="bbs_title">
	<input type="hidden" name="keyword" value="${keyword }">
	
</form>

<script type="text/javascript">
$(document).ready(function() {
	var setCategory = "${setCategory}";
	var setKeyword = "${setKeyword}";
	if(setKeyword == null || setKeyword == ""){
		$('#board-search').val();
	}	else {
		$('#board-search').val("${setKeyword}");
	}
	if(setCategory == null || setCategory == ""){
		$('#search-category').val("bbs_title");
	}	else {
		$('#search-category').val("${setCategory}");
	}

	var categoryName = "";
	if(setCategory == "bbs_title"){
		categoryName = "category-title";
	} else if(setCategory == "bbs_writer"){
		categoryName = "category-writer";
	}
	
	var keywordArr = new Array();
	
	<c:forEach items="${keywordList}" var="list" varStatus="status">
		
		keywordArr.push("${list}");
		
	</c:forEach>
	
	$(".mainTable > tbody > tr > td.search-nm").each(function() {
		
		var areaTxt = $(this).text();
		var areaTxt_lower = areaTxt.toLowerCase();
		var replaceArr = new Array();
		
		for(var i = 0; i < keywordArr.length; i++) {
			
			var keyword = keywordArr[i];
			var keyword_lower = keyword.toLowerCase();
			
			if(areaTxt_lower.indexOf(keyword_lower) > -1) {
				
				var sIndex = areaTxt_lower.indexOf(keyword_lower);
				var eIndex = sIndex + keyword_lower.length;
				
				replaceArr.push(areaTxt.substring(sIndex, eIndex));
			}
		}
		
		var resultTxt = areaTxt;
		
		if(replaceArr.length > 0) {
			
			for(var i = 0; i < replaceArr.length; i++) {
				
				var str = replaceArr[i];
				
				areaTxt = areaTxt.replace(str, '<b class="searchKeyword">' + str + '</b>');
			}
		}
		
		$(this).html(areaTxt);
	});
});
$(document).ready(function() {
	<c:if test="${mainIdx ne null}">
	
	linkContent("/member/base/board/faq/content.do", "${mainIdx}");
	offcanvasOpen();
	
	</c:if>
});
</script>

</body>
</html>