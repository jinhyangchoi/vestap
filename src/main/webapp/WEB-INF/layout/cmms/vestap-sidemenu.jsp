<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="accordion" id="menuAccordion">

	<div id="menu1" class="nav-element">
		<a href="/member/base/main.do" target="_self" class="btn nav-main"><i class="icon-dashboard"> </i>HOME</a>

	</div>
	<!-- //menu1 -->

	<div id="menu2" class="nav-element">

		<!-- * class= focus 적용하면 활성화 표시 (class=collapsed 추가 > /삭제 v)-->
		<button class="btn collapsed nav-climate" type="button" data-toggle="collapse" data-target="#subMenu2" aria-expanded="true" aria-controls="menu2">
			<i class="icon-file-news"> </i>기후변화취약성
		</button>

		<div id="subMenu2" class="collapse nav-climate" aria-labelledby="menu2" data-parent="#menuAccordion">
			<!-- class=show 적용하면 초기 펼침메뉴 보임 -->
			<ul class="navbar-nav">
				<!-- * class= focus 적용하면 활성화 표시 -->
				<li class="nav-estimation"><a href="/member/base/climate/estimation/view.do?${_csrf.parameterName}=${_csrf.token}">취약성평가</a></li>
				<li class="nav-comparison"><a href="/member/base/climate/comparison/view.do?${_csrf.parameterName}=${_csrf.token}">지자체간 비교분석</a></li>
				<li class="nav-exposure"><a href="/member/base/climate/exposure/view.do?${_csrf.parameterName}=${_csrf.token}">기후노출 세부정보</a></li>
				<li class="nav-cumulative"><a href="/member/base/climate/cumulative/view.do?${_csrf.parameterName}=${_csrf.token}">누적현황보고</a></li>
			</ul>
		</div>

	</div>
	<!-- //menu2 -->

	<div id="menu3" class="nav-element">

		<button class="btn collapsed nav-custom" type="button" data-toggle="collapse" data-target="#subMenu3" aria-expanded="false" aria-controls="menu3">
			<i class="icon-graph-keynote-alt"> </i>사용자정의 취약성
		</button>

		<div id="subMenu3" class="collapse nav-custom" aria-labelledby="menu3" data-parent="#menuAccordion">

			<ul class="navbar-nav">
				<li class="nav-indicator"><a href="/member/base/custom/indicator/list.do?${_csrf.parameterName}=${_csrf.token}">평가 지표 생성</a></li>
				<li class="nav-item"><a href="/member/base/custom/item/list.do?${_csrf.parameterName}=${_csrf.token}">평가 항목 생성</a></li>
				<li class="nav-estimation"><a href="/member/base/custom/estimation/view.do?${_csrf.parameterName}=${_csrf.token}">자체 취약성 평가</a></li>
			</ul>

		</div>
	</div>
	<!-- //menu3 -->

	<div id="menu4" class="nav-element">
		<button class="btn collapsed nav-dbinfo" type="button" data-toggle="collapse" data-target="#subMenu4" aria-expanded="false" aria-controls="menu4" onClick="fn_test();return false;">
			<i class="icon-file-db"> </i>DB정보
		</button>

		<div id="subMenu4" class="collapse nav-dbinfo" aria-labelledby="menu5" data-parent="#menuAccordion">

			<ul class="navbar-nav">
				<li class="nav-meta"><a href="/member/base/dbinfo/meta/list.do?${_csrf.parameterName}=${_csrf.token}">원시 자료 정보</a></li>
				<li class="nav-indicator"><a href="/member/base/dbinfo/indicator/list.do?${_csrf.parameterName}=${_csrf.token}">지표 자료 정보</a></li>
				<li class="nav-item"><a href="/member/base/dbinfo/item/list.do?${_csrf.parameterName}=${_csrf.token}">평가 항목 정보</a></li>
			</ul>

		</div>
	</div>
	<!-- //menu4 -->

	<div id="menu5" class="nav-element">

		<button class="btn collapsed nav-board" type="button" data-toggle="collapse" data-target="#subMenu5" aria-expanded="false" aria-controls="menu5" onClick="fn_test();return false;">
			<i class="icon-user-talk"> </i>열린마당
		</button>

		<div id="subMenu5" class="nav-board collapse" aria-labelledby="menu5" data-parent="#menuAccordion">

			<ul class="navbar-nav">
				<li class="nav-notice"><a href="/member/base/board/notice/list.do?${_csrf.parameterName}=${_csrf.token}">공지사항</a></li>
				<li class="nav-faq"><a href="/member/base/board/faq/list.do?${_csrf.parameterName}=${_csrf.token}">FAQ</a></li>
				<li class="nav-suggestion"><a href="/member/base/board/suggestion/list.do?${_csrf.parameterName}=${_csrf.token}">건의사항</a></li>
				<li class="nav-reference"><a href="/member/base/board/reference/list.do?${_csrf.parameterName}=${_csrf.token}">자료실</a></li>
			</ul>

		</div>
	</div>
	<!-- //menu5 -->
	<div id="menu12" class="nav-element">
<%-- 		<a href="/member/base/user/info/view.do?${_csrf.parameterName}=${_csrf.token}" class="btn collapsed nav-dbinfo"> <i class="icon-user-groupline"> </i>사용자 정보 --%>
		<a href="/member/base/user/info/view.do?${_csrf.parameterName}=${_csrf.token}" class="btn nav-info-view"> <i class="icon-user-groupline"> </i>사용자 정보
		</a>
	</div>
	<!-- 관리자만 보이는 메뉴 -->
	<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
		<hr>
		<span class="float-right pr-3">ADMIN</span>

		<div id="menu6" class="nav-admin">

			<!-- * class= focus 적용하면 활성화 표시 (class=collapsed 추가 > /삭제 v)-->
			<button class="btn nav-admin-inventory collapsed" type="button" data-toggle="collapse" data-target="#subMenu6" aria-expanded="true" aria-controls="menu6" onClick="fn_test();return false;">
				<i class="icon-file-browser"> </i>지자체별 인벤토리
			</button>

			<div id="subMenu6" class="collapse nav-admin-inventory " aria-labelledby="menu6" data-parent="#menuAccordion">
				<!-- class=show 적용하면 초기 펼침메뉴 보임 -->

				<ul class="navbar-nav">
					<!-- * class= focus 적용하면 활성화 표시 -->
					<li class="nav-admin-inventory-item"><a href="/admin/inventory/item/list.do">취약성평가 항목</a></li>
					<li class="nav-admin-inventory-indicator"><a href="/admin/inventory/indicator/list.do">평가지표 데이터</a></li>
				</ul>

			</div>
		</div>
		<!-- //menu6 -->

		<div id="menu7" class="nav-admin">
			<a href="/admin/climate/estimation/view.do" class="btn nav-admin-climate-estimation"> <i class="icon-graph-keynote-alt"> </i>전국단위 취약성평가
			</a>
		</div>
		<!-- //menu7 -->

		<div id="menu13" class="nav-admin">
			<a href="/admin/climate/cumulative/view.do" class="btn nav-admin-cumulative-view"> <i class="icon-file-news"> </i>전국단위 누적현황보고
			</a>
		</div>

		<div id="menu11" class="nav-admin">
			<button class="btn collapsed nav-admin-system" type="button" data-toggle="collapse" data-target="#subMenu11" aria-expanded="false" aria-controls="menu11" onClick="fn_test();return false;">
				<i class="icon-graph-keynote-alt"> </i>전국 사용자정의 취약성
			</button>

			<div id="subMenu11" class="collapse nav-admin-system" aria-labelledby="menu11" data-parent="#menuAccordion">

				<ul class="navbar-nav">
					<li class="nav-admin-system-indicator"><a href="/admin/system/indicator/list.do?${_csrf.parameterName}=${_csrf.token}">평가 지표 관리</a></li>
					<li class="nav-admin-system-item"><a href="/admin/system/item/list.do?${_csrf.parameterName}=${_csrf.token}">평가 항목 관리</a></li>
					<li class="nav-admin-system-estimation"><a href="/admin/system/estimation/view.do?${_csrf.parameterName}=${_csrf.token}">취약성 평가</a></li>
				</ul>

			</div>
		</div>
		
		<div id="menu16" class="nav-admin">
<%-- 			<a href="/admin/management/data/list.do?${_csrf.parameterName}=${_csrf.token}" class="btn nav-admin-management-dataMn"> <i class="icon-file-news"> </i>데이터 관리 --%>
<!-- 			</a> -->
			
			<button class="btn nav-admin-management collapsed" type="button" data-toggle="collapse" data-target="#subMenu16" aria-expanded="false" aria-controls="menu16" onClick="fn_test();return false;">
				<i class="icon-file-db"> </i>데이터 관리
			</button>
			
			<div id="subMenu16" class="nav-admin-management collapse" aria-labelledby="menu16" data-parent="#menuAccordion">
				<ul class="navbar-nav">
					<li class="nav-admin-management-data"><a href="/admin/management/data/list.do?${_csrf.parameterName}=${_csrf.token}">지표데이터 관리</a></li>
					<li class="nav-admin-management-meta"><a href="/admin/management/meta/list.do?${_csrf.parameterName}=${_csrf.token}">메타데이터 관리</a></li>
				</ul>

			</div>
		</div>

		<div id="menu8" class="nav-admin">
			<button class="btn nav-admin-board collapsed" type="button" data-toggle="collapse" data-target="#subMenu8" aria-expanded="false" aria-controls="menu8" onClick="fn_test();return false;">
				<i class="icon-user-talk"> </i>열린마당 관리
			</button>

			<div id="subMenu8" class="nav-admin-board collapse" aria-labelledby="menu8" data-parent="#menuAccordion">
				<ul class="navbar-nav">
					<li class="nav-admin-board-notice"><a href="/admin/board/notice/list.do?${_csrf.parameterName}=${_csrf.token}">공지사항</a></li>
					<li class="nav-admin-board-faq"><a href="/admin/board/faq/list.do?${_csrf.parameterName}=${_csrf.token}">FAQ</a></li>
					<li class="nav-admin-board-suggestion"><a href="/admin/board/suggestion/list.do?${_csrf.parameterName}=${_csrf.token}">건의사항</a></li>
					<li class="nav-admin-board-reference"><a href="/admin/board/reference/list.do?${_csrf.parameterName}=${_csrf.token}">자료실</a></li>
				</ul>

			</div>
		</div>
		<!-- //menu8 -->

		<div id="menu9" class="nav-admin">

			<button class="btn nav-admin-stat collapsed" type="button" data-toggle="collapse" data-target="#subMenu9" aria-expanded="false" aria-controls="menu9" onClick="fn_test();return false;">
				<i class="icon-graph-chart"> </i>통계보기
			</button>

			<div id="subMenu9" class="nav-admin-stat collapse" aria-labelledby="menu9" data-parent="#menuAccordion">

				<ul class="navbar-nav">
					<li class="nav-admin-stat-accesslog"><a href="/admin/stat/accesslog/view.do?${_csrf.parameterName}=${_csrf.token}">접속통계</a></li>
					<li class="nav-admin-stat-estimationlog"><a href="/admin/stat/estimationlog/view.do?${_csrf.parameterName}=${_csrf.token}">취약성평가 조회 통계</a></li>
				</ul>

			</div>
		</div>
		<!-- //menu9 -->

		<div id="menu10" class="nav-admin">
			<a href="/admin/user/management/list.do?${_csrf.parameterName}=${_csrf.token}" class="btn nav-admin-user-management">
				<i class="icon-user-groupline"></i>회원관리
			</a>
		</div>

		<div id="menu15" class="nav-admin">
			<a href="/admin/ems/selectSndngMailList.do?${_csrf.parameterName}=${_csrf.token}" class="btn nav-admin-ems-selectSndngMailList <c:if test="${depth3 eq 'selectSndngMailDetail'}">focus</c:if>">
				<i class="icon-file-news"></i>메일 발송내역 관리
			</a>
		</div>

		<div id="menu14" class="nav-admin">
			<button class="btn nav-admin-admin collapsed" type="button" data-toggle="collapse" data-target="#subMenu14" aria-expanded="false" aria-controls="menu14" onClick="fn_test();return false;">
				<i class="icon-user-groupline"> </i>관리자 계정 관리
			</button>

			<div id="subMenu14" class="nav-admin-admin collapse" aria-labelledby="menu14" data-parent="#menuAccordion">
				<ul class="navbar-nav">
					<li class="nav-admin-admin-management"><a href="/admin/admin/management/list.do?${_csrf.parameterName}=${_csrf.token}">관리자 계정 목록</a></li>
					<li class="nav-admin-admin-accesslog"><a href="/admin/admin/accesslog/list.do?${_csrf.parameterName}=${_csrf.token}">접속 이력</a></li>
					<li class="nav-admin-admin-requestlog"><a href="/admin/admin/requestlog/list.do?${_csrf.parameterName}=${_csrf.token}">작업 이력</a></li>
				</ul>
			</div>
		</div>

		<%-- <div id="menu11" class="nav-admin">
				<button class="btn collapsed nav-admin-system" type="button" data-toggle="collapse" data-target="#subMenu11" aria-expanded="false" aria-controls="menu11"><i class="icon-graph-keynote-alt"> </i>전국 사용자정의 취약성</button>
		
				<div id="subMenu11" class="collapse nav-admin-system" aria-labelledby="menu11" data-parent="#menuAccordion">
				
					<ul class="navbar-nav">
						<li class="nav-admin-indicator"><a href="/admin/system/indicator/list.do?${_csrf.parameterName}=${_csrf.token}">평가 지표 관리</a></li>
						<li class="nav-admin-item"><a href="/admin/system/item/list.do?${_csrf.parameterName}=${_csrf.token}">평가 항목 관리</a></li>
						<li class="nav-admin-estimation"><a href="/admin/system/estimation/view.do?${_csrf.parameterName}=${_csrf.token}">자체 취약성 평가</a></li>
					</ul>
					
				</div>
			</div> --%>
		<!-- //menu7 -->

	</sec:authorize>
</div>
<!-- //accordion -->

<input type="hidden" id="depth" value="${viewName.depth1}-${viewName.depth2}-${viewName.depth3}">
<input type="hidden" name="depth1" id="depth1" value="${depth1}" />
<input type="hidden" name="depth2" id="depth2" value="${depth2}" />
<input type="hidden" name="depth3" id="depth3" value="${depth3}" />

<script>
	
	$(document).ready(function() {
		
		var depth1 = "${viewName.depth1}";
		var depth2 = "${viewName.depth2}";
		var depth3 = "${viewName.depth3}";
		console.log(depth1 + " / " + depth2 + " / " + depth3);
		depth1==''?$("a.nav-main").addClass("focus"):true;
		
		$(".nav-element > button").removeClass("focus");
		$(".nav-element > div").removeClass("show");
		$(".nav-element > div > ul.navbar-nav > li").removeClass("focus");
		
		$(".nav-admin > button").removeClass("focus");
		$(".nav-admin > div").removeClass("show");
		$(".nav-admin > div > ul.navbar-nav > li").removeClass("focus");
		$(".focus").removeClass("focus");
		
		if(depth1=="admin"){
			//관리자
			$(".nav-" + depth1 + " button.nav-admin-" + depth2).addClass("focus");
			$(".nav-" + depth1 + " div.nav-admin-" + depth2).addClass("show");
			$(".nav-" + depth1 + " ul.navbar-nav > li.nav-admin-"+depth2+ "-" + depth3).addClass("focus");
			$("a.nav-admin-"+depth2+ "-" + depth3).addClass("focus");
			
		}else{
			//일반메뉴
			$("button.nav-" + depth1).addClass("focus");
			$("div.nav-" + depth1).addClass("show");
			$(".nav-" + depth1 + " ul.navbar-nav > li.nav-" + depth2).addClass("focus");
			
			$("a.nav-"+depth2+ "-" + depth3).addClass("focus");
		}
	});
	
	function fn_test(){
		$(".focus").removeClass("focus");
	
	}
</script>