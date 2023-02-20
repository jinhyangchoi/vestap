<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP - 지표데이터 관리</title>
<link href="/resources/css/loading/loading.css" rel="stylesheet">
</head>
<body>
<div class="display" style="display: none;">
<div class="loading">
<div class="lds-css ng-scope">
<div class="lds-spinner" style="100%;height:100%"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
</div>
</div>
</div>
<table class="offcanvas-table">
	<tr>
		<td class="offcanvas-left-open"><!-- *********************** offcanvas-left-open *********************** -->

			<div class="card">
				<div class="card-header offcanvas-left-open-title">
					<i class="icon-file-bookmark"></i>데이터 관리<a href="javascript:void(0);" class="on-offmenu text-blue"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
				</div>
				
				<ul class="list-group list-group-flush">
					
					<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i>지표데이터 관리</li>
					<li class="list-group-item">
						
						<div class="row">
							<div class="col">
								<div class="input-group">
									<span class="remove-keyword">
										<input type="text" class="form-control indicator-keyword" placeholder="지표 검색" maxlength="10">
										<span><i class="fas fa-times"></i></span>
									</span>
									<div class="input-group-append">
										<button type="button" class="btn btn-outline-secondary form-control" onclick="dataFindKeyword();">검색</button>
									</div>
								</div>
							</div>
						</div>
						
						<hr>
						
						<div class="row">
							<div class="col">
							
								<div class="toolTipUl">
									
									<ul class="nav navbar-nav" id="indicator-list">
										
										<c:forEach items="${indicatorList }" var="list">
										
										<li>
											<a href="javascript:void(0);" id="info-${list.indi_id }" class="offcanvas-select indicator-info"><c:out value="${list.indi_nm }"/></a>
										</li>
										
										</c:forEach>
										
										<c:if test="${fn:length(indicatorList) eq 0 }">
										
										<li>
											<a href="javascript:void(0);">시스템 정의 지표가 없습니다.</a>
										</li>
										
										</c:if>
										
									</ul>
									
								</div><!-- //toolTipUl -->
								
								<hr class="mt-3 mb-3">
								
								<div id="indicator-page" class="mt-2 mb-3 justify-content-center form-inline">
									<c:if test="${not empty indicatorPage }">
										<ui:pagination paginationInfo="${indicatorPage }" type="text" jsFunction="fn_DataIndicatorList"/>
									</c:if>
								</div>
							</div><!-- //col -->
						</div><!-- //row -->
						
					</li>
				</ul>
			
			</div><!-- //card -->
				
			</td>
			<td class="col align-top p-0">
			
			<div class="onmenu-div d-none">
				<a href="javascript:void(0);" class="text-blue on-offmenu"><i class="icon-arrow-caret-right"></i></a>
			</div>
			
			<form id="system-form-update" action="/admin/management/data/update.do?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
			
			<input type="hidden" name="indi_id" value="">
			
			<div class="mainContents indicator-view-category " id="indicator-update-view"><!-- mainContents -->
				
				<div class="contentsMdTitle mb-3">평가 지표 수정</div>
				
				<div class="card">
					<div class="card-header p-2">
						
						<div class="p-1 pl-1">평가 지표</div>
					</div><!-- card-header -->
					<div class="card-body p-3">
						
						<div class="row mb-3">
							
							<div class="col-6">
								<div class="mb-1">지표명</div>
								<div class="row mb-3">
									<div class="col-8">
										<input type="text" class="form-control" name="indicator-name" id="indicator-name-update" placeholder="지표명을 입력 해 주세요.(30자 이내)" maxlength="30" readonly="readonly">
									</div>
								</div>
								
							</div>
							<div class="col-6 pl-1">
								
								<div class="mb-1">지표 설명(50자 내)</div>
								<input type="text" class="form-control" name="indicator-expn" placeholder="지표에 대한 요약 설명을 입력하세요.(50자 이내)" maxLength="50">
								
							</div>
						</div><!-- row -->
						<div class="row mb-3">
							<div class="col-6">
								<div class="mb-1">지표 구축/가공방법</div>
								<div class="select_box">
									<select class="form-control" id="select-construct" name="indicator-cont">
										<option value="VT001">읍면동 통계 원시 자료</option>
										<option value="VT002">시군구 통계 원시 자료</option>
										<option value="VT004">시군구 통계 가공 자료</option>
										<option value="VT006">복합/기타 자료</option>
										<option value="VT007">기상/기후 원시 자료</option>
									</select>
								</div>
								
							</div>
							<div class="col-6 pl-1">
							
								<div class="mb-1">지표단위</div>
								<input type="text" class="form-control" name="indicator-unit" placeholder="지표단위를 입력하세요.(30자 이내)" maxlength="30">
								
							</div>
						</div><!-- row -->
						<div class="row mb-3">
							<div class="col-6">
								<div class="mb-1">IPCC WG</div>
								<div class="row mb-3">
									<div class="col-6">
										
										<div class="select_box">
											<select class="form-control" id="ipcc-select-1-update" name="ipcc-select-1">
											
											<c:forEach items="${ipccList_1 }" var="list">
												<option value="${list.code_id }"><c:out value="${list.code_nm }"/></option>
											</c:forEach>
											
											</select>
										</div>
												
									</div>
									<div class="col-6 pl-1">
										
										<div class="select_box">
											<select class="form-control" id="ipcc-select-2-update" name="ipcc-select-2">
											
											<c:forEach items="${ipccList_2 }" var="list">
												<option value="${list.code_id }"><c:out value="${list.code_nm }"/></option>
											</c:forEach>
											
											</select>
										</div>
												
									</div>
								</div>
							</div>
							<div class="col-6 pl-1">
								
								<div class="mb-1">등록/수정연도</div>
								<input type="text" class="form-control" name="indicator-update-year" placeholder="등록/수정 연도를 입력해주세요" maxlength="10" numberOnly="true">
										
							</div>
						</div><!-- row -->
						<div class="row mb-3">
							
							<div class="col-12">
								<div class="mb-1">구축방법</div>
								<div class="row mb-3">
									<div class="col-12">
										<textarea type="text" class="form-control" name="indicator-construct-meth"  placeholder="구축방법을 입력 해 주세요." ></textarea>
									</div>
								</div>
								
							</div>
						</div><!-- row -->
						<div class="row mb-3">
							
							<div class="col-12">
								<div class="mb-1">산정식</div>
								<div class="row mb-3">
									<div class="col-12">
										<textarea type="text" class="form-control" name="indicator-deduction"  placeholder="산정식을 입력 해 주세요." ></textarea>
									</div>
								</div>
								
							</div>
						</div><!-- row -->
						<div class="row mb-3">
							<div class="col-6">
								<div class="mb-1">해상도</div>
								<div class="select_box">
									<select class="form-control" id="select-construct" name="indicator-space">
										<option value="SPA01">읍면동</option>
										<option value="SPA02">시군구</option>
										<option value="SPA03">시도</option>
									</select>
								</div>
								
							</div>
							<div class="col-6 pl-1">
							
								<div class="mb-1">최신년도</div>
								<input type="text" class="form-control" name="indicator-year" placeholder="최신년도"  readonly="readonly">
								
							</div>
						</div><!-- row -->
						<div class="card">
									<div class="card-header p-2">
										<div class="row">
											<div class="p-1 pl-4 col-6">지표값 입력</div>
											
											<div class="col-6 text-right">
												
												<div class="btn-group">
													<label class="btn btn-green mb-0 indicator-select">
														<input type="radio" class="for-label" name="indicatorSelect" value="fu" checked="checked">파일업로드
													</label>
												</div>
											</div>
											
										</div>
									</div><!-- card-header -->
									<div id="fu-indicator-view" class="card-body p-3 ">
										<div class="row img-box">
											<div class="col-12">
												<div class="mb-1">업로드 파일</div>
											</div>
											<div class="col-12">
												
												<div class="input-group filebox">
													<input type="text" class="upload-name1 form-control mr-2" id="indicator-file-info" value="" placeholder="파일을 첨부 해야 합니다." readonly="readonly">
													<label for="indicator-upload-file" class="btn btn-vestap btn-blue"><i class="icon-search-01"></i>파일첨부</label>
													<input type="file" id="indicator-upload-file" name="indicatorUploadFile" class="for-label">
												</div>
													
											</div>
											
											<div class="col-12 mt-3">
												<div class="col-12"><a href="#" onclick="indicatorUpload();">[업로드 템플릿] 엑셀 템플릿 파일 다운로드</a></div>
												<div class="col-12">- 다운로드 받은 템플릿 파일을 수정 할 경우 정상적으로 입력 되지 않습니다.</div>
												<div class="col-12">- 다운로드 받은 템플릿 파일의 지표값만 입력 가능 합니다.</div>
											</div>
										</div>
									</div>
								</div>
								<br/>
						<span class="h6 mb-3">관련 원시 자료</span>
						<div class="row mb-3">
								<table class="table vestapTable text-center mt-3 mb-3 table-hover" id="meta-list-table">
									<thead>
										<tr>
											<th>원시자료 명</th>
											<th>제공 기관</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
						</div><!-- row -->
					</div><!-- card-body -->
				</div><!-- //card -->
				
				<div class="text-center mt-4">
					<button class="btn btn-vestap btn-gray" type="button" id="system-reload"><i class="icon-close-bold"></i>취소하기</button>
					<button class="btn btn-vestap btn-blue mr-2" type="button" id="system-update"><i class="icon-file-write-add"></i>수정하기</button>
				</div>
						
			</div>
			
			</form>
			
			</td>
		</td>
	</tr>
</table>

<script>
$(document).ready(function() {
	
	<c:if test="${fileActive ne null}">
		
		var code = "${fileActive}";
		var msg = "asdasdasd";
		
		if(code == "code01") {
			
			msg = "행정구역 코드가 없습니다. 템플릿 파일이 변경 되었을 수 있습니다.";
			
		} else if(code == "code02") {
			
			msg = "템플릿 파일이 변경 되었을 수 있습니다.";
			
		} else if(code == "code03") {
			
			msg = "행정구역 코드가 없습니다. 템플릿 파일이 변경 되었을 수 있습니다.";
			
		} else if(code == "code04") {
			
			msg = "지표값은 숫자형식으로 입력해야 합니다. 지표값이 입력 되지 않았을 수 있습니다.";
			
		} else if(code == "code05") {
			
			msg = "템플릿 파일이 변경 되어 입력 할 수 없습니다.";
		}
		
		fn_alert("경고", msg, "error");
		
	</c:if>
});

$(document).on("keyup", "input:text[numberOnly]", function() {$(this).val( $(this).val().replace(/[^-0-9]/gi,"") );});

function indicatorUpload() {
	var item_id=$("#indicator-update-view [name=indicator-space]").val();
	
	 
	location.href="/admin/management/data/indicatorUpload.do?indicator-space=" + item_id;
	
}
</script>

<!-- custom vulnerability -->
<script src="/resources/js/developer/custom-page.js"></script>

<script src="/resources/js/developer/system-indicator-data.js"></script>

</body>
</html>