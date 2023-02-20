<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.core.Authentication"%>
<%@ page import="vestap.sys.sec.handler.VestapUserDetails" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP 기후변화 취약성 평가도구</title>
<style>
.login-fail {
	padding: 10px;
}
.desc-bold {
	font-weight: 800;
}
.new-img{
	width:460px;
	height:300px;
	margin:10px 10px auto;
}
p{
width: 100%;
}

.find-info{
	line-height: 14px;
	padding-top: 18px;
	text-align: center;
	color: #8e8e8e;
}
</style>
</head>
<body>
<%
Authentication auth = (Authentication)request.getUserPrincipal();

String userAuth = "";

if(auth != null) {
	
	Object tempAuth = auth.getAuthorities();
	
	VestapUserDetails customUserDetails = (VestapUserDetails) auth.getDetails();
	
	userAuth = tempAuth.toString();
	
	pageContext.setAttribute("userAuth", userAuth);
}
%>
<c:set var="userAuth" value="${pageScope.userAuth }"></c:set>
		<table class="offcanvas-table" >
    		<tr>
    			<td class="offcanvas-center col align-top p-5">
    			   	<div class="mainContents"><!-- mainContents -->
						
						<div class="contentsMdTitle mb-3">광역/기초 지자체 기후변화 취약성 평가도구에 오신 것을 환영합니다.</div>
						<div class="mb-3">VESTAP 은 Internet Explorer 10 이상의 버전에 최적화 되어있습니다. <br/>IE 10 미만의 버전에서는 일부 기능이 작동하지 않을 수 있습니다. Chrome 등 다른 브라우저를 이용해주시기 바랍니다.</div>
						<div class="card p-5 normal-text">
								<p>·&nbsp;&nbsp;<b class="desc-bold">기후변화 취약성 평가도구 시스템(VESTAP)</b>은 기후변화 영향에 대한 사전평가를 도모하고 이에 기반한 적응대책 수립을 활성화하기 위해 지자체의 기후변화 취약성 평가를 지원하고 있습니다.</p>
								<p>·&nbsp;&nbsp;광역/기초지자체의 기후변화 적응대책 세부시행 계획 수립 시 기후변화 현황 및 세부부문별 취약성에 대한  간접적 근거자료로 활용이 가능합니다.</p>
								<p>&nbsp;&nbsp; ※ 관련 법규 : 저탄소 녹색성장 기본법 시행령 제38조</p>
								<p>·&nbsp;&nbsp;본 도구에서 제공하는 취약정보는 기후변화 적응대책 세부시행 계획 시 참고 자료로 이용될 수 있으며, 사용에 대한 법적 의무 조항 및 사항은 없습니다.</p>
								<p>·&nbsp;&nbsp;본 도구는 기후변화 취약성을 분석하기 위한 지표기반 평가를 지원합니다. 취약성평가도구에서 도출된 평가 결과는 해당 지역의 상대적 순위를 나타낸 것이며, 취약성 평가  지역의 범위, 지표 및 가중치 구성 등에 따라 평가 결과는 달라질 수 있습니다.</p>
								<p>·&nbsp;&nbsp;본 도구에서 제공중인 취약성 평가의 결과는 각 유관기관에서 일괄 제공되는 자료를 활용 한 결과입니다.<br>따라서 본 도구의 <b>"사용자 정의 취약성 평가"</b>를 이용하여 해당 지자체의 사회경제 현황자료를 활용하고 지자체 특성에 맞는 취약성 평가 항목 및 지표로 재구성하셔서 본 도구를 활용하시길 권장합니다.</p>
								<p><b class="desc-bold">※ 문의</b></p>
								<p>·&nbsp;&nbsp;계정정보(ID 및 PW)와 전반적인 VESTAP 운영 문의 <br>: 044-415-7442, 7983 / jtpark@kei.re.kr (한국환경연구원 국가기후변화적응센터)</p>
								<p>·&nbsp;&nbsp;기타 오류사항 및 시스템 문의 지원 <br>: 070-7725-6736 ㈜ 선도소프트</p>
								<p><b class="desc-bold">※ VESTAP 주요기능</b></p>
								<p>·&nbsp;&nbsp;VESTAP은 DB갱신, 신규 평가기능 추가, 사용자 편의 레이아웃 적용 등을 수행하여 2018년 12월 신규 시스템을 오픈하였습니다. 많은 이용 부탁 드립니다. </p>
								<p>
								<a href="/resources/img/estimation.png" target="_blank"><img alt="" src="/resources/img/estimation.png" class="new-img"></a>
								<a href="/resources/img/comparison.png" target="_blank"><img alt="" src="/resources/img/comparison.png" class="new-img"></a>
								</p>
								<p>
								<a href="/resources/img/exposure.png" target="_blank"><img alt="" src="/resources/img/exposure.png" class="new-img"></a>
								<a href="/resources/img/cumulative.png" target="_blank"><img alt="" src="/resources/img/cumulative.png" class="new-img"></a>						
								</p>
						</div><!-- //boxArea -->
					</div><!-- //mainContents -->

    			</td>
				<td class="offcanvas-right-open active"><!-- *********************** offcanvas-right-open *********************** -->
					
					<c:if test="${userAuth eq null }">
					<form id="loginFrm" action="/loginProcess.do?${_csrf.parameterName}=${_csrf.token}" method="post">
					<div class="card active">
						<div class="card-header offcanvas-right-open-title">
				  			<i class="icon-pass-lock-solid"></i>Login
						</div>
						
						<ul class="list-group list-group-flush">
							<li class="list-group-item" style="height: 785px">

								<div class="boxArea text-left p-5" >
									<div class="mb-1">아이디</div>
									<input type="text" id="user-id" class="form-control" name="id" placeholder="id" value="">
									<div class="mb-1 mt-1">비밀번호</div>
										<input type="password" class="form-control" name="password" placeholder="password" value="">
									<br>
										<button class="btn btn-vestap btn-blue float-right" id="btn-login" onclick="linkSubmit();"><i class="icon-pass-lock-solid"></i>로그인</button>
									<br>
									</div><!-- //boxArea -->
								<div class="login-fail">
									<p>${exceptionName }</p>
									<div class="find-info">
										<a target="_blank" id="idinquiry" href="#void">아이디 찾기</a>
										<span class="bar" > | </span>
										<a target="_blank" id="pwinquiry" href="/pwInquiry.do?${_csrf.parameterName}=${_csrf.token}">비밀번호 찾기</a>
										<span class="bar" > | </span>
										<a target="_blank" id="userApplyInsertView" href="/user/userApplyInsertView.do">계정등록 요청</a>
									</div>
								</div>
							</li>
						</ul>
					</div><!-- //card -->
					</form>
					</c:if>
					<c:if test="${userAuth ne null }">
					<form id="logoutFrm" action="/logout.do?${_csrf.parameterName}=${_csrf.token}" method="post">
						<div class="card active">
							<div class="card-header offcanvas-right-open-title">
					  			<i class="icon-user-login-solid"></i>회원정보
							</div>
							
							<ul class="list-group list-group-flush">
								<li class="list-group-item" style="height: 785px">
									<sec:authorize access="isAuthenticated()">
									<div class="boxArea text-left p-5" >
										<div class="mb-1">
										 <sec:authentication property="principal"/> 님 반갑습니다.
										</div>
										<br>
											회원님의 등급은 
												<sec:authorize access="hasRole('ROLE_ADMIN')">
													ADMIN입니다.
												</sec:authorize>
												<sec:authorize access="hasRole('ROLE_WIDE')">
													WIDE입니다.
												</sec:authorize>
												<sec:authorize access="hasRole('ROLE_BASE')">
													BASE입니다.
												</sec:authorize>
										<br>
											<input type="hidden" name="msg" value="logout">
										<br>
											<button class="btn btn-vestap btn-blue float-right" id="btn-login" onclick="logout();"><i class="fas fa-sign-out-alt"></i>&nbsp;Logout</button>
										<br>
									</div><!-- //boxArea -->
									</sec:authorize>
								</li>
							</ul>
						</div><!-- //card -->	
					</form>
					</c:if>
    			</td>
			</tr>
		</table>
<script>
function linkSubmit() {
	$("#loginFrm").submit();
}

function logout() {
	$("#logoutFrm").submit();
}

function linkBoard() {
	console.log("board in!!");
	$("#boardFrm").submit();
}

function fn_survey(){
	//fn_mapImage();
	var url = '/survey.do?${_csrf.parameterName}=${_csrf.token}';
	//var url = '/member/base/climate/estimation/report.do?${_csrf.parameterName}=${_csrf.token}';
	
    var form = document.createElement("form");
    form.setAttribute("class","survey-form");
    form.setAttribute("method","post");        
    form.setAttribute("target","survey")
    form.setAttribute("action",url);     
    document.body.appendChild(form);                
  
    window.open('', 'survey','width=600,height=685, menubar=no,resizable=no,scrollbars=1, status=no');
    
    form.submit(); 
	
}
window.onload = function(){
	//fn_survey();
}
</script>

</body>
</html>