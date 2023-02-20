<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>VESTAP - DB정보 - 지표 자료 정보</title>
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

<form id="listFrm" action="/member/base/dbinfo/indicator/list.do">

<input type="hidden" name="page" value="1">
<input type="hidden" name="keyword" value="${keyword }">
<input type="hidden" name="field_id" value="${field_id }">
<input type="hidden" name="ipcc_1" value="${ipcc_1 }">
<input type="hidden" name="ipcc_2" value="${ipcc_2 }">
<input type="hidden" name="item_id" value="${item_id }">
<input type="hidden" name="indi_group" value="${indi_group }">
<input type="hidden" name="indi_id" value="${indi_id }">

<table class="offcanvas-table">
	<tr>
		<td class="offcanvas-left-open"><!-- *********************** offcanvas-left-open *********************** -->
			
			<div class="card">
				
				<div class="card-header offcanvas-left-open-title">
					<i class="icon-file-bookmark"></i>지표 자료 조회<a href="javascript:void(0);" class="on-offmenu text-blue"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
				</div>
				
				<ul class="list-group list-group-flush">
				
					<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i>지표자료 검색</li>
					<li class="list-group-item">
						
						<div class="row mb-4">
							<!-- <div class="col"><div class="mb-1">지표 / 시나리오 그룹</div> -->
							<div class="col"><div class="mb-1">구분</div>
								<div class=" select_box">
									<select class="form-control" id="select-group">
										<option value="all" selected>전체선택</option>
										<option value="IG001">민감도</option>
										<option value="IG002">적응능력</option>
										<!-- <option value="IG003">기후재해통계/배출량</option>
										<option value="IG007">사용자정의 지표</option> -->
										<option value="SG001">기후노출</option>
										<option value="IG008">기타</option>
										<!-- <option value="SG002">대기환경 시나리오</option> -->
										<%-- <c:forEach items="${indicatorGroupList }" var="list">
											<option value="${list.code_id }"><c:out value="${list.code_nm }"/></option>
										</c:forEach> --%>
									</select>
								</div>
							</div>
						</div><!-- //row -->
						
						<div class="row mb-4">
							
							<div class="col"><div class="mb-1">취약성 평가 항목</div>
							
								<div class=" select_box">
									<select class="form-control" id="select-field">
										<option value="all" selected>전체</option>
										<c:forEach items="${fieldList }" var="list">
											<option value="${list.code_id }"><c:out value="${list.code_nm }"/></option>
										</c:forEach>
									</select>
								</div>
								
							</div>
							
						</div><!-- //row -->
						
						
						<div class="row mb-4">
							
							<div class="col"><div class="mb-1">취약성 평가 항목</div>
							
								<div class=" select_box">
									<select class="form-control" id="select-item">
										<option value="none" selected>리스크를 선택 해야 합니다.</option>
									</select>
								</div>
								
							</div>
							
						</div><!-- //row -->
						<div class="row mb-4">

							<div class="col"><div class="mb-1">IPCC WG</div>
								
								<div class="row">
									<div class="col pr-2">
										
										<div class=" select_box">
											<select class="form-control" id="ipcc-1">
												<option value="all">IPCC 선택</option>
												<c:forEach items="${ipccList }" var="list">
													<option value="${list.code_id }"><c:out value="${list.code_nm }"/></option>
												</c:forEach>
											</select>
										</div>
										
									</div>
									<div class="col pl-2">
										<div class=" select_box">
											<select class="form-control" id="ipcc-2">
												<option value="none" selected>IPCC 대분류 우선 선택</option>
											</select>
										</div>
									</div>
								</div><!-- //row -->
							</div>
						</div><!-- //row -->
						
						<div class="row mb-4">
							
							<div class="col"><div class="mb-1">지표이름</div>
								<input type="text" class="form-control" id="search-keyword" placeholder="지표명을 입력하세요(2~20자)" maxlength="20">
							</div>
							
						</div><!-- //row -->
								
						<div class="row">
							<div class="col pr-2">
								
								<button class="btn btn-vestap btn-blue w-100" type="button" id="indicator-search"><i class="icon-search"></i>검색하기</button>
								
							</div>
							<div class="col pl-2">
								
								<button class="btn btn-vestap btn-blue w-100" type="button" id="indicator-init"><i class="icon-refresh-modify"></i>초기화</button>
								
							</div>
						</div><!-- //row -->
						
						<c:if test="${fn:length(keywordList) ne 0 }">
							<div class="row">
								<div class="col mt-3"
									><div class="mb-1">검색어</div>
									<div id="keyword-area"></div>
								</div>
							</div>
						</c:if>
						
					</li>
				</ul>
			</div><!-- //card -->
					
		</td>
		<td class="col align-top p-0">
			
			<div class="onmenu-div d-none">
				<a href="javascript:void(0);" class="text-blue on-offmenu"><i class="icon-arrow-caret-right"></i></a>
			</div>
			
			<div class="mainContents"><!-- mainContents -->
				
				<div class="navbar p-0 pb-3 mt-3">
					
					<div class="contentsTitle"><i class="icon-file-roll"></i>지표 자료
						<span class="normal-text ml-2">보다 신뢰도 높은 VESTAP을 위해 DB 메타정보를 제공합니다.</span>
					</div>
					
				</div><!-- //navbar -->
					
				<table class="table table-hover vestapTable mainTable">
					<thead>
						<tr>
							<th>번호</th>
							<th>세부지표명</th>
							<!-- <th>지표/시나리오그룹</th> -->
							<th>구분</th>
							<th>IPCC WG I</th>
							<th>IPCC WG II</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${fn:length(indicatorList) eq 0 }">
						<tr>
							<td colspan="5">지표 목록이 없습니다.</td>
						</tr>
						</c:if>
						<c:forEach items="${indicatorList }" var="list">
						<tr class="offcanvas-select indicator-list" id="indi-${list.indi_id }" onclick="fn_indicatorInfo('${list.indi_id}', 1);">
							<td><c:out value="${list.rnum }"/></td>
							<td class="search-nm"><c:out value="${list.indi_nm }"/></td>
							<td><c:out value="${list.indi_group_nm }"/></td>
							<td><c:out value="${list.ipcc_large_nm }"/></td>
							<td><c:out value="${list.ipcc_small_nm }"/></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<nav class="form-inline mt-4 justify-content-center">
					<c:if test="${not empty pageInfo }">
						<ui:pagination paginationInfo="${pageInfo }" type="text" jsFunction="fn_page"/>
					</c:if>
				</nav>
					
			</div><!-- //mainContents -->
			
		</td>
		<td class="offcanvas-right-open" id="offcanvas-view"><!-- *********************** offcanvas-right-open *********************** -->
			
			<div class="card">
				<div class="card-header offcanvas-right-open-title">
					<i class="icon-file-bookmark"></i>지표 자료 상세 정보
					<button type="button" class="offcanvasCloseBtn close">
						<i class="icon-close-bold"></i>
					</button>
				</div>
				<ul class="list-group list-group-flush">
					<li class="list-group-item sub-title border-top-0"><i class="icon-file-align"></i><span id="info-indi-nm"></span></li>
					<li class="list-group-item p-0 pt-2">
						
						<nav>
							<div class="nav nav-tabs nav-justified" id="nav-tab"
								role="tablist">
								<a class="nav-item nav-link active" id="nav-tab1" data-toggle="tab" href="#nav1" role="tab" aria-controls="nav1" aria-selected="true">지표정보</a>
								<a class="nav-item nav-link " id="nav-tab2" data-toggle="tab" href="#nav2" role="tab" aria-controls="nav-2" aria-selected="false">관련정보</a>
							</div>
						</nav>
						
						<div class="tab-content" id="nav-tabContent">
							
							<div class="tab-pane fade show active p-3" id="nav1" role="tabpanel" aria-labelledby="nav-tab1">
								
								<table class="table table-bottom-line">
									<colgroup>
										<col style="width: 140px;">
										<col style="width: *;">
									</colgroup>
									<tr>
										<th>지표/시나리오 그룹</th>
										<td id="info-indi-group"></td>
									</tr>
									<tr>
										<th>IPCC WG I</th>
										<td id="info-indi-ipcc1"></td>
									</tr>
									<tr>
										<th>IPCC WG II</th>
										<td id="info-indi-ipcc2"></td>
									</tr>
									<tr>
										<th>지표단위</th>
										<td id="info-indi-unit"></td>
									</tr>
									<tr>
										<th>구축 형태</th>	<!-- 지표구축/가공방법 -> 구축형태 -->
										<td id="info-indi-cont"></td>
									</tr>
									<tr>
										<th>지표자료 설명</th>
										<td id="info-indi-acct"></td>
									</tr>
									<tr>
										<th>지표구축/가공방법</th> <!-- 구축 방법 -> 지표구축/가공방법 -->
										<td id="info-indi-meth"></td>
									</tr>
									<tr>
										<th>산식</th>
										<td id="info-indi-method"></td>
									</tr>
								</table>
								
								<div class="row mb-4" id="select-model-div">
									<div class="col">
										<div class="mb-1">Model 선택</div>
										<div class="select_box">
											<select class="form-control" id="select-model">
												
												<c:forEach items="${scenList }" var="list">
													<option value="${list.option_id }"><c:out value="${list.option_nm }"/>
												</c:forEach>
												
											</select>
										</div>
									</div>
								</div>
								
								<div class="row mb-4" id="select-option-div">
									<div class="col">
										<div class="row">
											<div class="col pr-2">
												<div class="mb-1">RCP 선택</div>
												<div class="select_box">
													<select class="form-control" id="select-rcp">
														
														<c:forEach items="${rcpList }" var="list">
															<option value="${list.option_id }"><c:out value="${list.option_nm }"/>
														</c:forEach>
														
													</select>
												</div>
											</div>
											<div class="col pr-2">
												<div class="mb-1">년도 선택</div>
												<div class="select_box">
													<select class="form-control" id="select-year">
														
														<c:forEach items="${yearList }" var="list">
															<option value="${list.option_id }"><c:out value="${list.option_nm }"/>
														</c:forEach>
														
													</select>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div class="row mb-4">
									
									<div class="col">
										
										<div class="mb-1">행정구역 선택</div>
										
										<div class="row">
											<div class="col pr-2">
												
												<div class=" select_box">
													<select class="form-control" id="district-sd">
														<c:forEach items="${sdList }" var="list">
															<option value="${list.district_cd }"><c:out value="${list.district_nm }"/></option>
														</c:forEach>
													</select>
													
													<select class="form-control d-none" id="district-sd-ski">
													</select>
												</div>
												
											</div>
											<div class="col pl-2">
												<div class=" select_box">
													<select class="form-control" id="district-sgg">
														<option value="all">전체</option>
														<c:forEach items="${sggList }" var="list">
															<option value="${list.district_cd }"><c:out value="${list.district_nm }"/></option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div><!-- //row -->
									</div>
								</div><!-- //row -->
								
								<table class="table vestapTable text-center" id="indicator-data-table">
									<thead>
										<tr>
											<th>순위</th>
											<th>행정구역 명칭</th>
											<th>값</th>
											<th>년도</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								
								<div class="nav navbar p-0 mt-4">
									<nav class="form-inline" id="indicator-data-page"></nav><!-- //pagination -->
									<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2" onclick="downloadIndicator();"> <i class="icon-download-disk"></i>엑셀파일 다운로드</button>
								</div><!-- //nav -->
								
							</div>
							<div class="tab-pane fade p-3" id="nav2" role="tabpanel" aria-labelledby="nav-tab2">
								
								<span class="h6 mb-3">관련 원시 자료</span>
								
								<table class="table vestapTable text-center mt-3 mb-3 table-hover" id="meta-list-table">
									<thead>
										<tr>
											<th>원시자료 명</th>
											<th>제공 기관</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								
								<span class="h6 mb-3">관련 취약성 평가 항목</span>
								
								<table class="table vestapTable text-center mt-3 table-hover" id="item-list-table">
									<thead>
										<tr>
											<th>번호</th>
											<th>항목 명칭</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								
								<div id="item-list-page" class="mt-2 mb-3 justify-content-center form-inline"></div>
								
							</div>
						</div>
						<!-- //tab-content -->
					</li>
				</ul>
			</div>
			
		</td>
	</tr>
</table>

</form>

<script>
	
	$(document).ready(function() {
		
		var keywordArr = new Array();
		
		<c:forEach items="${keywordList}" var="list" varStatus="status">
			
			keywordArr.push("${list}");
			
			var keyword = "${list}";
			
			var $con = $('<div class="mb-1" id="keyword-${status.count}"></div>');
			var $div = $('<div></div>');
			$div.append(keyword);
			$con.append($div);
			
			$div = $('<div></div>');
			var $a = $('<a href="javascript:fn_removeKeyword(\'' + keyword + '\', \'keyword-${status.count}\');" style="color: #087B98;"><i class="icon-close-bold"></i></a>');
			$div.append($a);
			$con.append($div);
			
			$("#keyword-area").append($con);
			
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
		
		if("${field_id}" != "all") {
			fn_getItem("${field_id}", "${item_id}");
		}
		
		if("${ipcc_1}" != "all") {
			fn_getIpcc("${ipcc_1}", "${ipcc_2}");
		}
		
		$("#select-field option[value=" + "${field_id}" + "]").prop("selected", true);
		$("#select-group option[value=" + "${indi_group}" + "]").prop("selected", true);
		$("#ipcc-1 option[value=" + "${ipcc_1}" + "]").prop("selected", true);
		$("#ipcc-2 option[value=" + "${ipcc_2}" + "]").prop("selected", true);
		
		<c:if test="${activeOffcanvas ne null}">
			$("input:hidden[name=indi_id]").val("${activeOffcanvas}");
			fn_indicatorInfo("${activeOffcanvas}", 1);
			offcanvasOpen();
		</c:if>
	});
	
</script>

<!-- custom vulnerability -->
<script src="/resources/js/developer/custom-page.js"></script>

<script src="/resources/js/developer/dbinfo-indicator.js"></script>

</body>
</html>