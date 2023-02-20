<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP - 비밀번호 찾기</title>
<style>
	.container{
		margin-top: 70px;
	}
	.step-2{
		display: none;
	}
</style>
</head>
<body>
	<div class="container">
		<table class="offcanvas-table">
			<tr>
				<td class="col align-top p-0">
					<div class="mainContents">
						<!-- mainContents -->
						<div class="navbar p-0 pb-3">
							<div class="contentsTitle">
								<i class="icon-file-roll"> </i>비밀번호 찾기
								<span class="normal-text ml-2">비밀번호를 찾고자 하는 아이디를 입력해 주세요.</span>
							</div>
						</div>
						<!-- //navbar -->
						<div class="contents">
							<form id="frm" name="frm" autocomplete="off" action="/user/pwInquiry/updateAction.do">
							<ul class="list-group list-group-flush step-1">
								<li class="list-group-item p-4">
									<p>사용자 아이디</p> <input type="text"
									class="form-control float-left" name="upUserId" id="upUserId" data="N"
									placeholder="아이디를 입력해 주세요.">
									<input type="hidden" class="form-control" name="upUserNm" id="upUserNm" />
								</li>
								<li class="list-group-item p-4">
									<div class='btnWrap'>
										<button type="button" class="btn btn-sm btn-blue" onclick="fn_idCheck();return false;">
											<i class="icon-user-add"></i>다 음
										</button>
									</div>
								</li>
							</ul>
							<ul class="list-group list-group-flush step-2">
								<li class="list-group-item p-4">
									<p>새 비밀번호</p> <input type="password"
									class="form-control float-left" name="newPw" id="newPw"
									placeholder="새 비밀번호를 입력해주세요.">
									
									<p class="emph_notice" style="display: none;"></p>
									<p>비밀번호 확인</p> <input type="password"
									class="form-control float-left" name="newPwChk" id="newPwChk"
									placeholder="새 비밀번호를 입력해주세요.">
									<p class="emph_notice_chk" style="display: none;"></p>
								</li>
								<li class="list-group-item p-4">
									<div class='btnWrap'>
										<button type="button" class="btn btn-sm btn-blue" onclick="fn_newPw();return false;">
											<i class="icon-user-add"></i>저장
										</button>
									</div>
								</li>
							</ul>
							</form>
						</div>					
					</div> <!-- //mainContents -->
	
				</td>
			</tr>
		</table>
	</div>
	<script>
	$(document).ready(function() { 
		$("#newPw").keyup(function(){
			var input = $(this).val();
			var inputLength = $(this).val().length;
			var num = input.search(/[0-9]/g);
			var eng = input.search(/[a-z]/ig);
			var spe = input.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
			
			if(inputLength < 9 || inputLength > 20){
				$(".emph_notice").css("display","block");
				$(".emph_notice").css("color","#ea4e43");
				$(".emph_notice").text("비밀번호는 8자~20자여야 합니다.");
			}else if(input.search(/\s/) != -1){
				$(".emph_notice").text("비밀번호는 공백이 없어야 합니다.");
			}else if(num < 0 || eng < 0 || spe < 0){
				$(".emph_notice").text("영문, 숫자, 특수문자를 혼합하여 입력해주세요.");
			}else{
				$(".emph_notice").css("color","#549b08");
				$(".emph_notice").text("비밀번호를 사용하셔도 좋습니다.");
			}
		});
		
		//비밀번호 확인
		$("#newPwChk").keyup(function(){
			var newPw = $("#newPw").val();
			var newPwChk = $(this).val();
			$(".emph_notice_chk").css("display","block");
			
			if(newPw == newPwChk){
				$(".emph_notice_chk").css("color","#549b08");
				$(".emph_notice_chk").text("비밀번호가 일치합니다.");
			}else{
				$(".emph_notice_chk").css("color","#ea4e43");
				$(".emph_notice_chk").text("비밀번호가 일치하지않습니다.");
				
			}
		});
	});
	
	function fn_idCheck(){
		var id = $("#upUserId").val();
		var url = '/user/pwInquiry/idCheck.do';
		var dataSet = {
				"userId" : id
			};
		var msg = '';
		var type = 'warning';
		var regExp = /^[A-Za-z0-9]*$/;
		
		if(id==null || id==''){
			msg = '아이디를 입력해 주세요.';
			fn_alert("경고", msg, type);
		}else if(id=="admin"){
			msg = '관리자 계정은 변경하실 수 없습니다.';
			fn_alert("경고", msg, type);
		}
		else if(id.match(regExp) != null){
			$.ajax({
				url:url
				,data:dataSet
				,type:'get'
				,dataType:'json'
				,success:function(result){
					var chk = result.chk;
					var userInfo = result.userInfo;
					var title = "";
					var msg = '';
					var type = '';
					var data = '';
					if(chk>0){
						//아이디 존재
						$(".step-1").css("display","none");
						$(".step-2").css("display","flex");
						$("#upUserNm").val(userInfo.userNm);
					}else{
						//아이디 존재안함
						title = "아이디 찾을수 없음";
						msg = '입력하신 아이디를 찾을 수 없습니다.';
						type = 'error';
						data = 'Y';
						fn_alert("사용자 ", msg, type);
					}
					
					$("#userId").attr("data",data);
				}
			});
		}else{
			msg = '아이디에 사용할 수 없는 문자가 포함되어 있습니다.';
			fn_alert("경고", msg, type);
		}
	}
	
		function fn_inquiry(){
			console.log("다음 킄ㄹ릭");
			
			console.log($("#upUserId").val());
			
			if(auth=='B'){
				$.ajax({
					url:'/member/base/user/info/sigunguList.do',
					dataType:'json',
					type:'get',
					data: dataSet,
					success:function(result){
						$("#"+select).empty();
						var list = result.list;
						for(var i in list){
							var html = "<option value='"+list[i].districtCd+"'>"+list[i].districtNm+"</option>";
							$("#"+select).append(html);
						}
						$("#"+select).css("display","block");
						
						if(mode =="update"){
							$("#upSigungu").val(sggCode);	
						}
					}
				});
			}else{
				$("#"+select).empty();
				$("#"+select).css("display","none");
			}
		}
		
		function fn_newPw(){
			
			var newPw = $("#newPw").val();
			var newPwChk = $("#newPwChk").val();
			
			if(newPw == newPwChk){
				
				var input = newPw;
				var inputLength = newPw.length;
				var num = input.search(/[0-9]/g);
				var eng = input.search(/[a-z]/ig);
				var spe = input.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
				var type = 'warning';
				
				if(inputLength < 9 || inputLength > 20){
					msg = "비밀번호는 8자~20자여야 합니다.";
					fn_alert("경고", msg, type);
				}else if(input.search(/\s/) != -1){
					msg = "비밀번호는 공백이 없어야 합니다.";
					fn_alert("경고", msg, type);
				}else if(num < 0 || eng < 0 || spe < 0){
					msg = "영문, 숫자, 특수문자를 혼합하여 입력해주세요.";
					fn_alert("경고", msg, type);
				}else{
					$("#frm").submit();
				}
				
			}else{
				var type = 'warning';
				var msg = '비밀번호를 확인 해주십시오.';
				fn_alert("경고", msg, type);
				
			}
		}
	</script>
</body>
</html>