<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>VESTAP - 발송메일 내역 조회</title>
<style type="text/css">
	@charset "UTF-8";
	.pagination {margin-right: 5px;}
	.pagination.previous > li > .page-link, .pagination.next > li > .page-link { padding: 8px 6px; }
	.vestapTable{font-weight: 900;}
	
	#divSearchForm{width: 17%;}
	#divSearchArea1{padding-left: 0px;}
	#divSearchArea{padding-left: 20px;}
	#spanTitle1{width: 4%; font-weight:900;}
	#spanTitle2{width: 2%; font-weight:900;}
	#spanSelect1{width: 5%; padding-left: 5px;}
	#spanSelect2{width: 2%; padding-left: 5px;}
</style>
<script type="text/javaScript">

/* ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
/*
function linkPage(pageNo){
 	document.listFrm.pageIndex.value = pageNo;
 	document.listFrm.action = "<c:url value='/admin/ems/selectSndngMailList.do' />";
	document.listFrm.submit();
}
*/
/* ********************************************************
 * 조회 처리
 ******************************************************** */
function fn_search(){
	document.listFrm.pageIndex.value = 1;
	document.listFrm.action = "<c:url value='/admin/ems/selectSndngMailList.do' />";
   	document.listFrm.submit();
}
function fn_onKeyupSearch(){
	if (window.event.keyCode == 13) {
		 fn_search();
   }
}
/* ********************************************************
 * 등록 처리 함수
 ******************************************************** */
function fnRegist(){
	document.listFrm.action = "<c:url value='/admin/ems/insertSndngMailView.do' />";
   	document.listFrm.submit();
}
/* ********************************************************
 * 상세회면 처리 함수
 ******************************************************** */
function fn_Detail(mid){
	var searchCondition  = document.listFrm.searchCondition.value;
	var searchKeyword    = document.listFrm.searchKeyword.value;
	var searchResultCode = document.listFrm.searchResultCode.value;
	document.detailForm.detailFormSearchCondition.value  = searchCondition;
	document.detailForm.detailFormSearchKeyword.value    = searchKeyword;
	document.detailForm.detailFormSearchResultCode.value = searchResultCode;
	
// 	document.detailForm.action = "<c:url value='/admin/ems/selectSndngMailDetail.do?${_csrf.parameterName}=${_csrf.token}' />";
	document.detailForm.mssageId.value = mid;
   	document.detailForm.submit();
}
/* ********************************************************
 * 삭제 처리 함수
 ******************************************************** */
function fn_Delete(){
	var checkField = document.listFrm.checkField;
    var id = document.listFrm.checkId;
    var checkedIds = "";
    var checkedFildIds = "";
    var checkedCount = 0;
    if(checkField) {
        if(checkField.length > 1) {
            for(var i=0; i < checkField.length; i++) {
                if(checkField[i].checked) {
                    checkedIds += ((checkedCount==0? "" : ",") + id[i].value);
                    checkedCount++;
                }
            }
        } else {
            if(checkField.checked) {
                checkedIds = id.value;
            }
        }
    }
    if(checkedIds.length > 0) {
    	var ret = confirm("삭제하시겠습니까?");
    	if (ret == true) {
    		document.deleteForm.mssageId.value=checkedIds;
    		document.deleteForm.action = "<c:url value='/admin/ems/deleteSndngMailList.do' />";
    	    document.deleteForm.submit();
    	}
    }
}
/* ********************************************************
 * 모두선택 처리 함수
 ******************************************************** */
function fnCheckAll(){
	var checkField = document.listFrm.checkField;
    if(document.listFrm.checkAll.checked) {
        if(checkField) {
            if(checkField.length > 1) {
                for(var i=0; i < checkField.length; i++) {
                    checkField[i].checked = true;
                }
            } else {
                checkField.checked = true;
            }
        }
    } else {
        if(checkField) {
            if(checkField.length > 1) {
                for(var j=0; j < checkField.length; j++) {
                    checkField[j].checked = false;
                }
            } else {
                checkField.checked = false;
            }
        }
    }
}

</script>
</head>

<body>

<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>

<table class="offcanvas-table">
	<tbody>	
		<tr>
			<td class="col align-top p-0">			
				<DIV class="mainContents"><!-- mainContents -->
				<form name="listFrm" id="listFrm" action="<c:url value='/admin/ems/selectSndngMailList.do?${_csrf.parameterName}=${_csrf.token}'/>" method="GET">
					<input name="pageIndex" type="hidden"   value="<c:out value='${pageInfo.currentPageNo}'/>"/>
					<input name="page"      type="hidden" value="1">
					
					<!-- navbar S -->
					<div class="navbar p-0">
						<div class="contentsTitle"><i class="icon-file-roll"> </i>메일 발송내역 조회</div>
						<%-- 
						<div class="form-inline mt-4">
							<div class="col pr-2">
								<div class="select_box">
									<select name="searchCondition" class="form-control" title="검색부문" id = "searchCondition">
									    <option  value="">--선택하세요--</option>
									    <option <c:if test="${param.searchCondition =='1'}"> selected </c:if> value="1">제목</option>
									    <option <c:if test="${param.searchCondition =='2'}"> selected </c:if> value="2">내용</option>
									    <option <c:if test="${param.searchCondition =='3'}"> selected </c:if> value="3">보낸이</option>
									</select>
								</div>
							</div>
							<div class="input-group search">
								<input type="text" name="searchKeyword" class="form-control w-300px" id="board-search" placeholder="검색어를 입력해주세요" maxlength="20" size="20" value="${param.searchKeyword}" tabindex="2" title="제목" onkeyup="fn_onKeyupSearch();">
								
								<div class="input-group-append">
									<button class="btn btn-outline-secondary" type="button" id="button-addon2" onclick="fn_search(); return false;" ><i class="icon-search"></i></button>
								</div>
							</div>
						</div>
						 --%>
					</div>
					<!-- //navbar E -->
					
					<!-- searchArea S -->
					<div class="form-inline mt-4 pb-3" id="searchArea" style="width:100%;">
						
						<div id="divSearchArea1">
							<span id="spanSelect1">
								<select name="searchCondition" class="form-control" title="검색부문" id = "searchCondition">
								    <option  value="">--선택하세요--</option>
								    <option value="1" <c:if test="${searchCondition =='1'}"> selected </c:if> >수신자 아이디</option>
								    <option value="2" <c:if test="${searchCondition =='2'}"> selected </c:if> >수신자 이름</option>
								    <option value="3" <c:if test="${searchCondition =='3'}"> selected </c:if> >수신자 메일</option>
								    <option value="4" <c:if test="${searchCondition =='4'}"> selected </c:if> >내용</option>
								</select>
							</span>
						</div>
						
						<div class="search-form" id="divSearchForm">
							<input type="text" name="searchKeyword" class="form-control" id="board-search" placeholder="검색어를 입력하세요(2~20자)" maxlength="20" onkeyup="fn_onkeyupSearch();" style="width:100%;" value="${searchKeyword}">
						</div>
						
						<div id="divSearchArea">
							<span id="spanTitle2">상태</span>
							<span id="spanSelect2">
								<select class="form-control" name="searchResultCode" id="searchResultCode">
									<option value="" selected="">전체</option>
									<option value="1" <c:if test="${searchResultCode eq '1'}"> selected </c:if> >성공</option>
									<option value="2" <c:if test="${searchResultCode eq '2'}"> selected </c:if> >실패</option>
								</select>
							</span>
						</div>
						
						<div id="divSearchArea">
							<span style="width: 60%;">
								<span class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups" style="/*display:contents;*/">
									<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2 float-right" onclick="fn_search();return false;">
										<i class="icon-search"></i>조회하기
									</button>
								</span>
							</span>
						</div>
					</div>
					<!-- //searchArea E -->
						
				    <table class="table table-hover vestapTable mainTable">
						<colgroup>
							<col width="5%">
							<col width="5%">
							<col width="5%">
							<col width="5%">
							<col width="15%">
							<col width="15%">
							<col width="15%">
							<col width="50%">
							<col width="15%">
						</colgroup>
						<thead>
							<tr>
								<th><input type="checkbox" name="checkAll" class="check2" onClick="javascript:fnCheckAll();" title="전체선택" id="checkAll" /></th>
								<th>순번</th>
								<th>상태</th>
								<th>발신자</th>
								<th>수신자 아이디</th>
								<th>수신자 이름</th>
								<th>수신자 메일</th>
								<th>제목</th>
								<th>발송일시</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(resultList) == 0}">
								<tr>
									<td colspan="9">등록 된 공지사항이 없습니다.</td>
								</tr>
							</c:if>
							
							<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
								<tr style="cursor:hand;">
									<td>
										<input type="checkbox" name="checkField" class="check2" title="선택">
										<input name="checkId" type="hidden" value="<c:out value='${resultInfo.mssageId}'/>" title=""/>
										<input name="checkFileId" type="hidden" value="<c:out value='${resultInfo.atchFileId}'/>" title=""/>
									</td>
									<td>${resultInfo.RNUM}</td>
									
									<c:choose>
										<c:when test="${resultInfo.sndngResultNm eq '실패'}">
											<td style="color: #ea4335;">${resultInfo.sndngResultNm}</td>
										</c:when>
										<c:otherwise>
											<td>${resultInfo.sndngResultNm}</td>
										</c:otherwise>
									</c:choose>
									<td>${resultInfo.sndr}</td>
									<td>${resultInfo.rcverId}</td>
									<td>${resultInfo.rcverNm}</td>
									<td>${resultInfo.rcver}</td>
									<td><a href="#fnDetail" onclick="fn_Detail('${resultInfo.mssageId}'); return false;">${resultInfo.sj}</a></td>
									<td>${resultInfo.sndngDe}</td>
								</tr>
							</c:forEach>
							<!-- 목록 조회 끝-->
						</tbody>
				    </table>
					
					<!-- 페이징 시작 -->
					<div class="nav navbar p-0 mt-4">
						<!-- pagination -->
						<nav class="form-inline">
							<c:if test="${not empty pageInfo }">
								<ui:pagination paginationInfo="${pageInfo }" type="text" jsFunction="linkPage"/>
							</c:if>
						</nav><!-- //pagination -->
						<div class="btn_fn_Delete">
							<button type="button" class="btn btn-sm btn-red" onclick="fn_Delete();return false;"><i class="icon-file-remove"></i>삭제</button>
						</div>
					</div>
					<!-- 페이징 끝 -->
				</form>
				
				<!-- 상세조회할 발송메일ID를 담는 폼 -->
				<form name="detailForm" action="<c:url value='/admin/ems/selectSndngMailDetail.do?${_csrf.parameterName}=${_csrf.token}'/>" method="post">
					<input name="mssageId"        type="hidden" value=""/>
					<input name="startRow"        type="hidden" value="<c:out value='${startRow}'/>"/>
					<input name="endRow"          type="hidden" value="<c:out value='${endRow}'/>"/>
					<input name="currentPageNo"   type="hidden" value="<c:out value='${pageInfo.currentPageNo}'/>"/>
					<input name="detailFormSearchCondition" type="hidden" value=""/>
					<input name="detailFormSearchKeyword"   type="hidden" value=""/>
					<input name="detailFormSearchResultCode"   type="hidden" value=""/>
					<input type="submit" id="invisible" class="invisible"/>
				</form>

				<!-- 삭제할 발송메일ID(여러 ID를 ,로 묶어 만들어진 데이터)를 담는 폼 -->
				<form name="deleteForm" action="<c:url value='/admin/ems/deleteSndngMailList.do?${_csrf.parameterName}=${_csrf.token}'/>" method="post">
					<input name="mssageId" type="hidden" value=""/>
					<input type="submit" class="invisible"/>
				</form>
				</DIV>
			</td>
		</tr>
	</tbody>
</table>

</body>
</html>

