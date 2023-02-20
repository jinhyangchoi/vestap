<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	pageContext.setAttribute("crlf", "\r\n");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>VESTAP - 발송메일 상세 조회</title>
<style>
table.table-register tr {border-top: 1px solid #B0C2D4;}
tr .clsTrFinal{border-bottom: 1px solid #B0C2D4;}
</style>
<script type="text/javaScript">
	/* ********************************************************
	 * 삭제 처리 함수
	 ******************************************************** */
	function fn_Delete() {
		var ret = confirm("삭제하시겠습니까?");
		if (ret == true) {
			document.detailForm.action = "<c:url value='/admin/ems/deleteSndngMail.do'/>";
			document.detailForm.target = "";
			document.detailForm.submit();
		}
	}
	/* ********************************************************
	 * 뒤로 처리 함수
	 ******************************************************** */
	function fn_moveList() {
// 		document.detailForm.action = "<c:url value='/admin/ems/backSndngMailDetail.do'/>";
		document.detailForm.action = "<c:url value='/admin/ems/selectSndngMailList.do'/>";
		document.detailForm.target = "";
		document.detailForm.submit();
	}

</script>
</head>

<body>
	<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
	<div class="mainContents">

		<!-- mainContents -->
		<div class="navbar p-0 pb-3">
			<!-- navbar -->
			<div class="contentsTitle">
				<i class="icon-file-roll"> </i>발송메일 상세조회<span class="normal-text ml-2">관리자 발송메일 확인</span>
			</div>
		</div>
		<!-- //navbar -->
		<div class="contents">
			<form name="detailForm" action="<c:url value='/admin/ems/deleteSndngMail.action?${_csrf.parameterName}=${_csrf.token}'/>" method="GET">
				<input name="mssageId"         type="hidden" value="${resultInfo.mssageId}" />
				<input name="startRow"         type="hidden" value="${resultInfo.startRow}" />
				<input name="endRow"           type="hidden" value="${resultInfo.endRow}" />
				<input name="pageIndex"        type="hidden" value="${resultInfo.pageIndex}" />
				<input name="page"             type="hidden" value="${resultInfo.pageIndex}" />
				<input name="searchCondition"  type="hidden" value="${resultInfo.detailFormSearchCondition}" />
				<input name="searchKeyword"    type="hidden" value="${resultInfo.detailFormSearchKeyword}" />
				<input name="searchResultCode" type="hidden" value="${resultInfo.detailFormSearchResultCode}" />
				
				<ul class="list-group list-group-flush">
					<li class="list-group-item p-4">
						<table width="100%">
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="보내는 사람, 받는 사람, 제목, 발신 내용, 발송 결과, XML메일보기, 첨부파일에 대한 정보를 가진 발송메일을 상세조회한다.">
									<CAPTION style="display: none;">발송메일 상세조회</CAPTION>
									<tr>
										<th scope="row" width="10%" height="23" class="required_text" nowrap style="padding: 20px;">보내는사람&nbsp;&nbsp;</th>
										<td width="90%" nowrap>${resultInfo.sndr}</td>
									</tr>
									<tr>
										<th scope="row" width="10%" height="23" class="required_text" nowrap style="padding: 20px;">받는사람&nbsp;&nbsp;</th>
										<td width="90%" nowrap>${resultInfo.rcverId}</td>
									</tr>
									<tr>
										<th scope="row" width="10%" height="23" class="required_text" nowrap style="padding: 20px;">제목&nbsp;&nbsp;</th>
										<td width="90%" nowrap>${resultInfo.sj}</td>
									</tr>
									<tr>
										<th scope="row" width="10%" height="23" class="required_text" style="padding: 20px;">발신 내용&nbsp;&nbsp;</th>
										<td width="90%" nowrap>
											<c:out value="${resultInfo.emailCn}" escapeXml="false" />
										</td>
									</tr>
									<tr>
										<th scope="row" width="10%" height="23" class="required_text" style="padding: 20px;">발신 시간&nbsp;&nbsp;</th>
										<td width="90%" nowrap>
											<c:out value="${resultInfo.sndngDe}" escapeXml="false" />
										</td>
									</tr>
									<tr class="clsTrFinal">
										<th scope="row" width="10%" height="23" class="required_text" style="padding: 20px;">발송 결과&nbsp;&nbsp;</th>
										<td width="90%" nowrap>${resultInfo.sndngResultNm}</td>
									</tr>
								</table>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td height="10"></td>
									</tr>
								</table>
							</td>
						</tr>
						
						<tr>
							<td>
								<table border="0" cellspacing="0" cellpadding="0" align="right">
									<tr>
										<td>
											<button type="button" class="btn btn-sm btn-red" data="ethree" onclick="fn_Delete();return false;">
												<i class="icon-file-write-edit"></i>삭제
											</button>
											<button type="button" class="btn btn-sm btn-blue" data="ethree" onclick="fn_moveList();return false;">
												<i class="icon-file-remove"></i>목록
											</button>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					</li>
				</ul>
			</form>
		</div>
	</div>
</body>
</html>

