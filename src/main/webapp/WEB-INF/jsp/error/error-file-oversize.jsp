<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP - 오류</title>

<!-- Bootstrap core CSS -->
<link href="/resources/css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="/resources/css/jquery/jquery-ui.css" rel="stylesheet" type="text/css"><!-- jquery-ui core CSS -->

<!-- Font CSS -->
<link href="/resources/css/fonts/fonts.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,700" rel="stylesheet">
<link href="/resources/css/icon/epicicon.css" rel="stylesheet">
<link href="/resources/css/icon/fa-all.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="/resources/css/developer/style.css" rel="stylesheet">
<link href="/resources/css/developer/style-custom.css" rel="stylesheet">
<link href="/resources/css/developer/style-error.css" rel="stylesheet">

<script>
function fncGoAfterErrorPage(){
	//다른 error 페이지와 달리 go(-2) 를 한 이유는 back으로 했을때 첨부파일이 추가 되지 않는 문제가 발생한다.
	history.go(-2);
}
</script>

</head>
<body>

<div class="error-container">

	<div class="error-msg-container">
		<div class="error-header">
			<div class="error-logo error-justify"><img src="/resources/img/logo_white.svg" class="mx-auto d-block" alt="logo"></div>
			<div class="error-title error-justify">기후변화취약성 평가 도구 시스템</div>
		</div>
		<div class="error-content">
			<div class="error-code error-justify">첨부 파일 용량 제한</div>
			<div class="error-msg">
				<ul>
					<li><i class="icon-check-01"></i>파일 첨부는 100MB까지 지원합니다.</li>
					<li><i class="icon-check-01"></i>문제가 계속 발생한다면 관리자에게 문의 하세요.</li>
				</ul>
			</div>
		</div>
		<div class="error-footer">
			<ul>
				<li><a href="http://localhost:8080/member/base/main.do" class="error-justify"><i class="icon-home01"></i></a></li>
				<li><a href="javascript:fncGoAfterErrorPage();" class="error-justify"><i class="icon-arrow-caret-left"></i></a></li>
			</ul>
		</div>
	</div>

</div>

</body>
</html>

















