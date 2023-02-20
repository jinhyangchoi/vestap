<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div class="navbar brandTitle">
	<a class="navbar-brand" href="/member/base/main.do" target="_self"><img src="/resources/img/logo_white.svg" class="mx-auto d-block"></a> <span>기후변화취약성 평가도구 시스템</span>
</div>

<ul class="nav memberJoin p-0">
	<li class="login">
		<sec:authorize access="isAuthenticated()">
			<img src="/resources/img/userPic.png">
			<p>
				<sec:authentication property="details.user_nm"/>님이 로그인하셨습니다.
			</p>
			<span>
				최근 접속일 : <sec:authentication property="details.user_access"/>
			</span>
			<span>
				접속 만료일 : <sec:authentication property="details.user_expire"/>
			</span>
		</sec:authorize>
	</li>
	
	<li>
		<sec:authorize access="isAuthenticated()">
			<form id="logout" action="/logout.do?${_csrf.parameterName}=${_csrf.token}" method="post">
				<a href="#logout" onclick="logout();return false;"><i class="fas fa-sign-out-alt"></i></a>
			</form>
		</sec:authorize>
		
		<sec:authorize access="isAnonymous()">
			<form id="loginPage" action="/loginPage.do" method="post">
				<a href="#loginPage" onclick="loginPage();return false;"><i class="fas fa-sign-out-alt"></i></a>
			</form>
		</sec:authorize>
	</li>
</ul>

<script>
function logout() {
	$("#logout").submit();
}

function loginPage() {
	$("#loginPage").submit();
}
</script>


