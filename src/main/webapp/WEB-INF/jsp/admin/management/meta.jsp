<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP - 메타데이터 관리</title>
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
					
					<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i>메타데이터 관리</li>
					<li class="list-group-item">
						
						<div class="row">
							<div class="col">
								<div class="input-group">
									<span class="remove-keyword">
										<input type="text" class="form-control meta-keyword" placeholder="원시자료  검색" maxlength="10">
										<span><i class="fas fa-times"></i></span>
									</span>
									<div class="input-group-append">
										<button type="button" class="btn btn-outline-secondary form-control" onclick="metaFindKeyword();">검색</button>
									</div>
								</div>
							</div>
						</div>
						
						<hr>
						
						<div class="row">
							<div class="col">
							
								<div class="toolTipUl">
									
									<ul class="nav navbar-nav" id="meta-list">
										
										<c:forEach items="${metaList }" var="list">
										
										<li>
											<a href="javascript:void(0);" id="info-${list.meta_id }" class="offcanvas-select meta-info"><c:out value="${list.meta_nm }"/></a>
										</li>
										
										</c:forEach>
										
										<c:if test="${fn:length(metaList) eq 0 }">
										
										<li>
											<a href="javascript:void(0);">시스템 원시자료가 없습니다.</a>
										</li>
										
										</c:if>
										
									</ul>
									
								</div><!-- //toolTipUl -->
								
								<hr class="mt-3 mb-3">
								
								<div id="meta-page" class="mt-2 mb-3 justify-content-center form-inline">
									<c:if test="${not empty metaPage }">
										<ui:pagination paginationInfo="${metaPage }" type="text" jsFunction="fn_MetaIndicatorList"/>
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
			
			<form id="system-form-update" action="/admin/management/meta/update.do?${_csrf.parameterName}=${_csrf.token}" method="post" >
			
			<input type="hidden" name="meta_id" value="">
			
			<div class="mainContents meta-view-category " id="meta-update-view"><!-- mainContents -->
				
				<div class="contentsMdTitle mb-3">원시자료 수정</div>
				
				<div class="card">
					<div class="card-header p-2">
						
						<div class="p-1 pl-1">원시자료</div>
					</div><!-- card-header -->
					<div class="card-body p-3">
						
						<div class="row mb-3">
							
							<div class="col-6">
								<div class="mb-1">원시자료명</div>
								<div class="row mb-3">
									<div class="col-11">
										<input type="text" class="form-control" name="meta_nm"  placeholder="원시자료명을 입력 해 주세요.(30자 이내)" maxlength="30" readonly="readonly">
									</div>
								</div>
								
							</div>
							<div class="col-6 pl-1">
								
								<div class="mb-1">제공기관명</div>
								<input type="text" class="form-control" name="meta_offer_org"  placeholder="제공기관명을 입력해주세요" maxLength="50">
								
							</div>
						</div><!-- row -->
						<div class="row mb-3">
							
							<div class="col-6">
								<div class="mb-1">제공부서명</div>
								<div class="row mb-3">
									<div class="col-11">
										<input type="text" class="form-control" name="meta_offer_dept"  placeholder="제공부서명을 입력 해 주세요." maxlength="30" >
									</div>
								</div>
								
							</div>
							<div class="col-6 pl-1">
								
								<div class="mb-1">제공시스템</div>
								<input type="text" class="form-control" name="meta_offer_system"  placeholder="제공시스템을 입력해주세요" maxLength="50">
								
							</div>
						</div><!-- row -->
						<div class="row mb-3">
							
							<div class="col-6">
								<div class="mb-1">원시자료단위</div>
								<div class="row mb-3">
									<div class="col-11">
										<input type="text" class="form-control" name="meta_base_unit"  placeholder="원시자료단위를 입력 해 주세요." maxlength="30" >
									</div>
								</div>
								
							</div>
							<div class="col-6 pl-1">
								
								<div class="mb-1">구축단위</div>
								<input type="text" class="form-control" name="meta_con_unit"  placeholder="구축단위를 입력해주세요" maxLength="50">
								
							</div>
						</div><!-- row -->
						<div class="row mb-3">
							
							<div class="col-6">
								<div class="mb-1">기준 시점</div>
								<div class="row mb-3">
									<div class="col-11">
										<input type="text" class="form-control" name="meta_con_year"  placeholder="기준시점을 입력 해 주세요." maxlength="30" >
									</div>
								</div>
								
							</div>
							<div class="col-6 pl-1">
								
								<div class="mb-1">정보형태</div>
								<input type="text" class="form-control" name="meta_data_type"  placeholder="정보형태를 입력해주세요" maxLength="50">
								
							</div>
						</div><!-- row -->
						<div class="row mb-3">
							
							<div class="col-6">
								<div class="mb-1">정보위치</div>
								<div class="row mb-3">
									<div class="col-11">
										<input type="text" class="form-control" name="meta_position"  placeholder="정보위치를 입력 해 주세요." maxlength="30">
									</div>
								</div>
								
							</div>
							<div class="col-6 pl-1">
								
								<div class="mb-1">축적/해상도</div>
								<input type="text" class="form-control" name="meta_scale"  placeholder="측척/해상도를 입력해주세요" maxLength="50">
								
							</div>
						</div><!-- row -->
						<div class="row mb-3">
							
							<div class="col-6">
								<div class="mb-1">단위</div>
								<div class="row mb-3">
									<div class="col-11">
										<input type="text" class="form-control" name="meta_unit"  placeholder="단위를 입력 해 주세요." maxlength="30" >
									</div>
								</div>
								
							</div>
							<div class="col-6 pl-1">
								
								<div class="mb-1">URL</div>
								<input type="text" class="form-control" name="meta_url"  placeholder="URL를 입력해주세요" >
								
							</div>
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
	</tr>
</table>

<script>

function indicatorUpload() {
	location.href="/admin/management/meta/indicatorUpload.do";
	
}
</script>

<!-- custom vulnerability -->
<script src="/resources/js/developer/custom-page.js"></script>

<script src="/resources/js/developer/system-indicator-meta.js"></script>

</body>
</html>