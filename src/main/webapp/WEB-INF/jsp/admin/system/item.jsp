<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP - 전국 사용자 정의 항목 생성</title>

<style type="text/css">
.option-fixed {
	position: fixed;
	top: 0;
}
</style>

</head>
<body>

<input type="hidden" name="viewCategory" value="insert">

<table class="offcanvas-table">
	<tr>
		<td class="offcanvas-left-open"><!-- *********************** offcanvas-left-open *********************** -->
		
		<div class="card">
			<div class="card-header offcanvas-left-open-title">
				<i class="icon-file-bookmark"></i>평가 항목 관리<a href="javascript:void(0);" class="on-offmenu text-blue"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
			</div>
						
			<ul class="list-group list-group-flush">
				<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i>리스크</li>
				<li class="list-group-item">
					
					<div class="row">
						<div class="col">
						
							<div class="select_box">
								<select class="form-control" id="select-field">
									<c:forEach items="${fieldList }" var="list">
										<option value="${list.code_id }"><c:out value="${list.code_nm }"/></option>
									</c:forEach>
								</select>
							</div>
							
						</div><!-- //col -->
					</div><!-- //row -->
					
				</li>
				<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i>전국 사용자정의 평가항목</li>
				<li class="list-group-item">
					
					<div class="row">
						<div class="col">
							<div class="input-group">
								<span class="remove-keyword">
									<input type="text" class="form-control item-keyword" placeholder="항목 검색" maxlength="10">
									<span><i class="fas fa-times"></i></span>
								</span>
								<div class="input-group-append">
									<button type="button" class="btn btn-outline-secondary form-control" onclick="findKeyword();">검색</button>
								</div>
							</div>
						</div>
					</div>
					
					<hr>
					
					<div class="row">
						<div class="col">
						
							<div class="mt-1" id="field-name"> 건강 부문 리스크 평가항목</div>
							<hr class="mt-2 mb-3">
							
							<div class="toolTipUl">
								
								<ul class="nav navbar-nav" id="item-list">
									
									<c:forEach items="${itemList }" var="list">
										
									<li>
										<a href="javascript:void(0);" id="info-${list.item_id }" class="offcanvas-item item-info"><c:out value="${list.item_nm }"/></a>
									</li>
									
									</c:forEach>
									
									<c:if test="${fn:length(itemList) eq 0 }">
										
									<li>
										<a href="javascript:void(0);">사용자 정의 항목이 없습니다.</a>
									</li>
									
									</c:if>
									
								</ul>
								
							</div><!-- //toolTipUl -->
							
							<hr class="mt-3 mb-3">
								
							<div id="item-page" class="mt-2 mb-3 justify-content-center form-inline">
								<c:if test="${not empty pageInfo }">
									<ui:pagination paginationInfo="${pageInfo }" type="text" jsFunction="fn_itemList"/>
								</c:if>
							</div>
							
							<button class="btn btn-vestap btn-blue w-100 mt-5" type="button" onclick="fn_itemCategory('item-insert-view');"><i class="icon-file-add"></i>평가항목 생성</button>
							<button class="btn btn-vestap btn-blue w-100 mt-3" type="button" onclick="fn_itemCategory('item-update-view');"><i class="icon-refresh-edit"></i>평가항목 수정</button>
							<button class="btn btn-vestap btn-blue w-100 mt-3" type="button" onclick="fn_deleteItem();"><i class="icon-del-trash-line"></i>평가항목 삭제</button>
							
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
		
		<form id="system-form" action="/admin/system/item/insert.do?${_csrf.parameterName}=${_csrf.token}">
		
		<input type="hidden" name="item_id" value="">
		
		<div class="mainContents item-view-category active" id="item-insert-view"><!-- mainContents -->
			
			<div class="navbar p-0 pb-3 mt-3">
				<div class="contentsTitle"><i class="icon-file-roll"></i>평가항목 생성/수정</div>
			</div>
			
			<div class="card mb-4">
				<div class="card-header p-2">
					
						<div class="p-1 pl-2">평가항목</div>
					
				</div><!-- card-header -->
				<div class="card-body p-3">
					
					<div class="row mb-3">
						<div class="col-12">
						
							<div class="mb-1">취약성평가 항목 명칭</div>
							<div class="row mb-3">
								<div class="col-8">
									<input type="text" class="form-control" id="item-name" name="item-name" placeholder="항목명을 입력 하세요.(30자 이내)" maxlength="30">
								</div>
								<div class="col-4 pl-0">
									<button class="btn btn-vestap btn-blue w-100 p-0" type="button" id="check-overlap"><i class="icon-check-double-02"></i>중복검사</button>
								</div>
							</div>
							
						</div>
					</div>
					
					<div class="row mb-3">
						
						<div class="col-6">
						
							<div class="mb-1">리스크 선택</div>
							<div class="select_box">
								<select class="form-control" id="item-field" name="item-field">
									<option value="none" selected>부문을 선택해 주세요</option>
									<c:forEach items="${fieldList }" var="list">
										<option value="${list.code_id }"><c:out value="${list.code_nm }"/></option>
									</c:forEach>
								</select>
							</div>
							
						</div>
						<div class="col-6 pl-1">
							
							<div class="mb-1">취약성평가 항목 요약 설명(50자 내)</div>
							<input type="text" class="form-control" id="item-account" name="item-account" placeholder="평가항목에 대한 요약내용을 입력하세요.(50자 이내)" maxlength="50">
							
						</div>
					</div><!-- row -->
					<div class="row">
						<div class="col-6">
						
							<div class="mb-1">템플릿 복사 평가 &lt;리스크&gt; 선택</div>
							<div class="select_box">
								<select class="form-control" id="copy-template-field">
									<option value="none" selected>부문을 선택해 주세요</option>
									<c:forEach items="${fieldList }" var="list">
										<option value="${list.code_id }"><c:out value="${list.code_nm }"/></option>
									</c:forEach>
								</select>
							</div>
							
						</div>
						<div class="col-6 pl-1">
						
							<div class="mb-1">템플릿 복사 평가 &lt;항목&gt; 선택</div>
							<div class="select_box">
								<select class="form-control" id="copy-template-item">
									<option value="none" selected>기존 취약성평가 항목</option>
								</select>
							</div>
							
						</div>
					</div><!-- row -->
				</div><!-- card-body -->		
			</div><!-- //card -->
			<div class="card mb-4">
				<div class="card-header p-2">
					<div class="p-1 pl-2">취약성 평가 산출식</div>
				</div><!-- card-header -->
				<div class="card-body p-3">
					
					<div class="row mb-1">
						<div class="col-2">
							<div class="display-table">
								<div>
									<h5 class="text-center text-base wrap">취약성 <span>평가(값) = </span></h5>
								</div>
							</div>
						</div>
						<div class="col-10" class="media-row-div">
							
							<div class="row">
								<div class="col-4">
									<div class="boxArea text-center">
										<div class="form-inline" style="display: inline-block;">
											( 기후노출 지수 X  <input type="text" class="form-control w-50 ml-2 mr-2 text-center climate-val" id="climate-val-1" name="climate-val-1" maxlength="4"> )
										</div>
									</div><span style="position: absolute; top: 12px; right: -7px; font-size: 24px;">+</span>
								</div>
								
								<div class="col-4">
									<div class="boxArea text-center">
										<div class="form-inline" style="display: inline-block;">
											( 민감도 지수 X  <input type="text" class="form-control w-50 ml-2 mr-2 text-center climate-val" id="climate-val-2" name="climate-val-2" maxlength="4"> )
										</div>
									</div><span style="position: absolute; top: 12px; right: -7px; font-size: 24px;">-</span>
								</div>
								
								<div class="col-4">
									<div class="boxArea text-center">
										<div class="form-inline" style="display: inline-block;">
											( 적응능력 지수 X  <input type="text" class="form-control w-50 ml-2 mr-2 text-center climate-val" id="climate-val-3" name="climate-val-3" maxlength="4"> )
										</div>
									</div>
								</div>
							</div>
							
							<hr class="whiteLine">
							
							<h5 class="text-center mb-0">1</h5>
							
						</div>
					</div><!-- row -->
					
				</div><!-- card-body -->
			</div><!-- //card -->
			
			<div class="row">
				<div class="col-12 mb-3">
				
					<div class="card">
						<div class="card-header p-2">
							<div class="row">
								<div class="p-1 pl-4 col-4">기후 노출</div>
								<div class="col-8 text-right">
									<button class="btn btn-vestap btn-outline-blue" type="button" onclick="fn_delIndicatorView('indicator-exp');">
										<i class="icon-del-trash-line"></i>지표제거
									</button>
									<button class="btn btn-vestap btn-outline-primary offcanvas-indicator" type="button" onclick="fn_addIndicatorView('indicator-exp');">
										<i class="icon-add-bold"></i>지표추가
									</button>
								</div>
							</div>
						</div><!-- card-header -->
						<div class="card-body p-3">
							
							<ul class="nav navbar-nav checkbox-input indi-list" id="list-indicator-exp">
								<li class="none-indicator">
									<div class="col-12 pl-0">
										지표추가를 클릭하여 지표를 추가 해야 합니다.
									</div>
								</li>
							</ul>
							
						</div><!-- card-body -->		
					</div><!-- //card -->
					
				</div>
				<div class="col-12 mb-3">
				
					<div class="card">
						<div class="card-header p-2">
							<div class="row">
								<div class="p-1 pl-4 col-4">기후 변화 민감도</div>
								<div class="col-8 text-right">
									<button class="btn btn-vestap btn-outline-blue" type="button" onclick="fn_delIndicatorView('indicator-sen');">
										<i class="icon-del-trash-line"></i>지표제거
									</button>
									<button class="btn btn-vestap btn-outline-success offcanvas-indicator" type="button" onclick="fn_addIndicatorView('indicator-sen');">
										<i class="icon-add-bold"></i>지표추가
									</button>
								</div>
							</div>
						</div><!-- card-header -->
						<div class="card-body p-3">
							
							<ul class="nav navbar-nav checkbox-input indi-list" id="list-indicator-sen">
								<li class="none-indicator">
									<div class="col-12 pl-0">
										지표추가를 클릭하여 지표를 추가 해야 합니다.
									</div>
								</li>
							</ul>
							
						</div><!-- card-body -->		
					</div><!-- //card -->
					
				</div>
				<div class="col-12 mb-3">
				
					<div class="card">
						<div class="card-header p-2">
							<div class="row">
								<div class="p-1 pl-4 col-4">적응 능력</div>
								<div class="col-8 text-right">
									<button class="btn btn-vestap btn-outline-blue" type="button" onclick="fn_delIndicatorView('indicator-adp');">
										<i class="icon-del-trash-line"></i>지표제거
									</button>
									<button class="btn btn-vestap btn-outline-warning offcanvas-indicator" type="button" onclick="fn_addIndicatorView('indicator-adp');">
										<i class="icon-add-bold"></i>지표추가
									</button>
								</div>
							</div>
						</div><!-- card-header -->
						<div class="card-body p-3">
							
							<ul class="nav navbar-nav checkbox-input indi-list" id="list-indicator-adp">
								<li class="none-indicator">
									<div class="col-12 pl-0">
										지표추가를 클릭하여 지표를 추가 해야 합니다.
									</div>
								</li>
							</ul>
							
						</div><!-- card-body -->		
					</div><!-- //card -->
					
				</div>
				
			</div><!-- row -->
			
			<div class="mt-3 text-center">
				<button class="btn btn-vestap btn-gray" type="button" id="system-reload"><i class="icon-close-bold"></i>취소하기</button>
				<button class="btn btn-vestap btn-blue mr-3" type="button" id="system-insert"><i class="icon-file-write-add"></i>등록하기</button>
			</div>
			
		</div><!-- //mainContents -->
		</form>
	</td>
	
	<td class="offcanvas-right-open" id="offcanvas-item">
		<div class="card">
			<div class="card-header offcanvas-right-open-title">
				<i class="icon-file-bookmark"></i>항목 데이터
				<button type="button" class="offcanvasCloseBtn close"><i class="icon-close-bold"></i></button>
			</div>
			
			<ul class="list-group list-group-flush">
				<li class="list-group-item p-3">
					
					<table id="item-list-table" class="table table-bottom-line">
						<colgroup>
							<col width="40%">
							<col width="60%">
						</colgroup>
						<tbody></tbody>
					</table>
					
				</li>
				<li class="list-group-item sub-title border-top-0 view-indicator" id="view-indicator-exp-tag">
					<!-- <a href="javascript:void(0);" class="text-light view-indicator" id="view-indicator-exp-tag" style="color: #273142 !important; width: 100%; border: 1px solid red; display: inline-block;"> -->
						<i class="icon-file-align"><span class="base">9</span></i> 기후 노출 부문
					<!-- </a> -->
				</li>
				<li class="d-none" id="view-indicator-exp">
					<ul class="list-group list-group-flush p-0 d-none">
						
						<li>
							<table id="item-indicator-exp" class="table table-hover vestapTable lgTable text-center border-0 m-0">
								<thead>
									<tr>
										<th>지표명</th>
										<th>지표 가중치</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</li>
						
					</ul>
				</li>
				<li class="list-group-item sub-title border-top-0 view-indicator" id="view-indicator-sen-tag">
					<!-- <a href="javascript:void(0);" class="text-light view-indicator" id="view-indicator-sen-tag" style="color: #273142 !important;"> -->
						<i class="icon-file-align"><span class="base">9</span></i> 기후 변화 민감도 부문
					<!-- </a> -->
				</li>
				<li class="d-none" id="view-indicator-sen">
					<ul class="list-group list-group-flush d-none">
						<li>
							<table id="item-indicator-sen" class="table table-hover vestapTable lgTable text-center border-0 m-0">
								<thead>
									<tr>
										<th>지표명</th>
										<th>지표 가중치</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</li>
					</ul>
				</li>
				<li class="list-group-item sub-title border-top-0 view-indicator" id="view-indicator-adp-tag">
						<i class="icon-file-align"><span class="base">9</span></i> 적응 능력 부문
				</li>
				<li class="d-none" id="view-indicator-adp">
					<ul class="list-group list-group-flush d-none">
						<li>
							<table id="item-indicator-adp" class="table table-hover vestapTable lgTable text-center border-0 m-0">
								<thead>
									<tr>
										<th>지표명</th>
										<th>지표 가중치</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</td>
	
	<td class="offcanvas-right-open" id="offcanvas-indicator"><!-- *********************** offcanvas-right-open *********************** -->
		
		<div class="card view-currposition">
			
			<input type="hidden" name="indicatorGroup" value="none">
			
			<div class="card-header offcanvas-right-open-title">
				<i class="icon-file-bookmark"></i><span id="add-indicator-name">기후노출 지표</span>
				<button type="button" class="offcanvasCloseBtn close"><i class="icon-close-bold"></i></button>
			</div>
			<ul class="list-group list-group-flush">
				<li class="list-group-item">
					
					<div class="col">
						<div class="select_box">
							<select class="form-control" id="indicator-group-select">
								<option value="none" selected="selected">지표 그룹을 선택 해야 합니다.</option>
								<c:forEach items="${indicatorGroupList }" var="list">
									<option value="${list.code_id }"><c:out value="${list.code_nm }"/></option>
								</c:forEach>
							</select>
						</div>
					</div><!-- //col -->
				</li>
				<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i><span id="indicator-group-name">지표 그룹을 선택 해야 합니다.</span></li>
				<li class="list-group-item p-3">
					<ul class="nav navbar-nav" id="indicator-item-list">
					</ul>
				</li>
				<li class="list-group-item p-3">
					<div id="indicator-group-page" class="mt-2 mb-3 justify-content-center form-inline"></div>
				</li>
			</ul>
		</div><!-- //card -->
			
		</td>
	</tr>
</table>
<!-- custom vulnerability -->
<script src="/resources/js/developer/custom-page.js"></script>
<script src="/resources/js/developer/system-item.js"></script>
</body>
</html>