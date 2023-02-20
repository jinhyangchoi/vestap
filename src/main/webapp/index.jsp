<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>VESTAP - 기후변화취약성평가도구시스템</title>
<style>
.main-justify {
	display: flex;
	flex-direction: row;
	justify-content: flex-start;
	align-items: center;
}

.main-container {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: #087B98;
}

.main-msg-container {
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -150px;
	margin-left: -350px;
	width: 700px;
	height: 300px;
}

.main-header {
	width: 100%;
	height: 70px;
}

.main-header > div {
	float: left;
	height: 100%;
}

.main-logo {
	width: 200px;
}

.main-logo > img {
	height: 30px;
}

.main-title {
	width: calc(100% - 200px);
	font-size: 25px;
	font-weight: 500;
	color: #FFFFFF;
	padding-left: 25px;
}

.main-content {
	width: 100%;
	height: 200px;
	border-top: 1px solid #FFFFFF;
	border-bottom: 1px solid #FFFFFF;
}

.main-content > div {
	width: 100%;
}

.main-code {
	font-size: 40px;
	font-weight: 500;
	color: #FFFFFF;
	padding-left: 25px;
}

.main-msg {
	padding: 10px 25px;
	font-size: 12pt;
	color: #FFFFFF;
	border-bottom: 1px solid #FFFFFF;
}

.main-msg > ul {
	list-style: none;
	padding: 0;
	padding-left: 25px;
}

.main-msg > ul > li {
	margin-bottom: 5px;
}

.main-footer {
	width: 100%;
	height: 30px;
	text-align: right;
}

.main-footer > ul {
	display: inline-block;
	height: 100%;
	list-style: none;
	padding: 0;
	margin: 0;
}

.main-footer > ul > li {
	float: left;
	display: table;
	height: 100%;
	border: 1px solid #FFFFFF;
	border-top: 0 !important;
	border-radius: 0 0 4px 4px;
	margin-left: -1px;
}

.main-footer > ul > li > a {
	display: table-cell;
	height: 100%;
	color: #FFFFFF;
	font-size: 12pt;
	padding-left: 15px;
	padding-right: 15px;
	margin: 0;
	vertical-align: middle;
	border-radius: 0 0 4px 4px;
	transition-property: background-color;
	transition-duration: .5s;
}


.main-footer > ul > li > a:hover {
	background-color: #045D74;
}
</style>
</head>
<body>

<div class="main-container">

	<div class="main-msg-container">
		<div class="main-header">
			<div class="main-logo main-title main-justify">VESTAP</div>
			<div class="main-title main-justify">기후변화취약성 평가도구 시스템</div>
		</div>
		<div class="main-content">
			<div class="main-code main-justify">서버에 접속 중 입니다</div>
			<br>
			<div class="main-title main-justify">잠시만 기다려주세요</div>
		</div>
	</div>
</div>
<script>
	window.onload = function () {
		var form = document.createElement("form");
		form.setAttribute("name","reloadForm");
		form.setAttribute("method","get");
		form.setAttribute("action","//localhost:1010/loginPage.do");
// 		form.setAttribute("action","//210.113.102.223:9080/loginPage.do");
		//form.setAttribute("action","//210.113.102.129:9080/loginPage.do");
		//form.setAttribute("action","//vestap.kei.re.kr/loginPage.do");
		
		form.setAttribute("style","display:none");
		document.body.appendChild(form);
		
		var btn = document.createElement("button");
		btn.setAttribute("id","reload");
		form.appendChild(btn);
		
		document.reloadForm.reload.click();
	}

</script>
</body>
</html>

