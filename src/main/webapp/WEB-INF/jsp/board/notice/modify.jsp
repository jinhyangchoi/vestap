<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP - 열린마당 - 공지사항 수정</title>

<style type="text/css">
.title-group > div {
	width: *;
	float: right;
}

.tb-w-8 {
	width: 8% !important;
}

label > input[type=file] {
	display: none;
}
</style>

<!-- CkEditor -->
<script  src="/resources/ckeditor/ckeditor.js"></script>

</head>
<body>

<form id="board-insert" action="/admin/board/notice/update.do?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">

<input type="hidden" name="idx" value="${idx }">

<table class="offcanvas-table">
	<tr>
		<td class="col align-top p-0">
			<div class="mainContents"><!-- mainContents -->
				<div class="card">
					<div class="card-header p-2">
						
						<div class="row">
							<div class="col-8 p-1 pl-4">공지사항 수정</div>
							
							<div class="col-4 pl-0 text-right">
								<button type="button" class="btn btn-vestap btn-outline-blue w-auto pl-2 pr-2 mr-1" onclick="fn_historyBack();"><i class="icon-close-bold"></i>취소</button>
								<button type="button" class="btn btn-vestap btn-outline-blue w-auto pl-2 pr-2" onclick="fn_submit();"><i class="icon-add-bold"></i>저장</button>
							</div>
							
						</div>
					</div><!-- card-header -->
					<div class="card-body p-2">
						<input type="text" class="form-control" id="bbs_title" name="bbs_title" placeholder="제목을 입력 해 주세요" value="${content.bbs_title }">
					</div>
					<div class="card-body p-2">
						<textarea name="bbs_content" id="content" rows="10" cols="80">${content.bbs_content }</textarea>
						<script>CKEDITOR.replace('content');</script>
					</div>
				</div>
				
				
				
				
				<div class="card mt-lg-3">
					<div class="card-header p-2">
						
						<div class="row">
							<div class="col-8 p-1 pl-4">첨부파일 등록 (최대 5개 / 파일 형식 : hwp, pptx, pdf, xlsx, txt, jpg, png, gif, jpeg)</div>
							
							<div class="col-4 pl-0 text-right">
								<label for="add-file" class="btn btn-vestap btn-blue mb-0 mr-1"><i class="icon-search-01"></i>파일첨부<input type="file" name="uploadFile" class="upload-hidden1" id="add-file" multiple="multiple"></label>
							</div>
							
						</div>
					</div>
					
					<div class="card-body p-2" id="std-file-area">
						<c:forEach items="${files }" var="list">
							<div class="row add-file-element">
								<div class="col-10 p-1 pl-4"><span class="file-name">${list.org_file_nm }</span></div>
								<div class="col-2 p-1 pl-0 pr-4 text-right">
									<span>
										<a href="javascript:void(0);" class="pt-1 modify-file" style="display: inline-block;">
											<i class="icon-close-bold"></i>
										</a>
									</span>
								</div>
								<input type="hidden" name="stdName" value="${list.std_file_nm }">
							</div>
							
							<input type="hidden" name="matchName" value="${list.org_file_nm }">
						</c:forEach>
					</div>
					
					<div class="card-body p-2" id="add-file-area"></div>
					
				</div>
				
			</div>
		</td>
	</tr>
</table>

</form>
</body>
</html>