<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>VESTAP ADMIN - 관리자 계정 관리</title>
<style>
	.vt-md{
	vertical-align: middle;
	}
</style>
</head>
<body>
	<table class="offcanvas-table">
		<tr>
			<td class="col align-top p-0">

				<div class="mainContents">
					<!-- mainContents -->

					<div class="navbar p-0 pb-3">

						<div class="contentsTitle">
							<i class="icon-file-roll"> </i>관리자 관리 <span
								class="normal-text ml-2">관리자 정보를 관리합니다.</span>

						</div>

						<div class="btn-toolbar mt-3" role="toolbar" aria-label="Toolbar with button groups">
							<button type="button"
								class="btn btn-vestap btn-blue w-auto pl-2 pr-2 float-right" onclick="fn_openWrite();return false;">
								<i class="icon-add-bold"></i>계정 추가
							</button>
						</div>

					</div>
					
					<!-- 관리자 검색 기능 추가시 풀기 -->
					<!-- <div class="navbar p-0 pb-3" style="width:50%;">
						<div class="search-form w-75">
							<input type="text" class="form-control" id="search-user" placeholder="이름을 입력하세요(2~20자)" maxlength="20">
						</div>
						<div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
							<button type="button" class="btn btn-vestap btn-blue w-auto pl-2 pr-2 float-right" onclick="fn_search();return false;">
							<i class="icon-search"></i>조회하기
							</button>
						</div>
					</div> -->
					<!-- //관리자 검색 기능 추가시 풀기 -->
					
					<!-- //navbar -->
					<input type="hidden" id="userListPage" value="1">
					<input type="hidden" id="userListLimit" value="${pageCnt}">
					<table class="table table-hover vestapTable text-center">
						<thead>
							<tr>
								<th style="width: 8%;">번호</th>
								<th style="width: 20%;">아이디&nbsp;<i class="icon-arrow-line-up01 vt-md id-up"></i><i class="icon-arrow-line-down01 vt-md id-down"></i></th>
								<th style="width: 20%;">이름&nbsp;<i class="icon-arrow-line-up01 vt-md name-up"></i><i class="icon-arrow-line-down01 vt-md name-down"></i></th>
								<th style="width: 12%">구분</th>
								<th style="width: 20%">최근 접속일</th>
								<th style="width: 20%">관리 버튼</th>
							</tr>
						</thead>
						<tbody id="adminList">
							<c:if test="${not empty adminList}">
							<c:forEach var="i" items="${adminList }">
								<tr class="pg_${i.page}"
								<c:if test="${i.page == 1 }">style="display:table-row"</c:if>
								<c:if test="${i.page != 1 }">style="display:none"</c:if>
								>
									<td>${i.no }</td>
									<td>${i.userId}</td>
									<td>${i.userNm}</td>
									<td>${i.userAuth}</td>
									<td>${i.userAccess}</td>
									<td><button type="button" class="btn btn-sm btn-blue" data="${i.userId}" onclick="fn_updateUser($(this));return false;">
											<i class="icon-file-write-edit"></i>수정
										</button>
										<button type="button" class="btn btn-sm btn-red" data="${i.userId}" onclick="fn_deleteUser($(this));return false;">
											<i class="icon-file-remove"></i>삭제
										</button></td>
								</tr>
							</c:forEach>
							</c:if>
							<c:if test="${empty adminList}">
								<tr><td colspan="6">관리자가 존재하지 않습니다.</td></tr>
							</c:if>
						</tbody>
					</table>
					<div class="nav navbar p-0 mt-4">
						<nav class="form-inline ">
							<ul class="pagination previous disabled" >
								<li class="page-item"><a class="page-link" href="#" onclick="fn_previousPage('userList');return false;"><i
										class="icon-arrow-caret-left"></i></a></li>
							</ul>
							<div><span id="userListCurPage">1</span>/<span id="userListLimitPage">${pageCnt}</span></div>
							<ul class="pagination next" >
								<li class="page-item"><a class="page-link" href="#" onclick="fn_nextPage('userList');return false;"><i
										class="icon-arrow-caret-right"></i></a></li>
							</ul>
						</nav>
					</div>


				</div> <!-- //mainContents -->

			</td>
			<td class="offcanvas-right-open" id="writeArea">
				<!-- *********************** offcanvas-right-open *********************** -->

				<div class="card" >
					<div class="card-header offcanvas-right-open-title">
						<i class="icon-file-bookmark"></i>관리자 추가
						<button type="button" class="offcanvasCloseBtn close">
							<i class="icon-close-bold"></i>
						</button>
					</div>
					<form name="enroll">
					<ul class="list-group list-group-flush" style="height: 785px">
						<li class="list-group-item">

							<button type="button" class="btn btn-sm btn-blue float-left" onclick="fn_init();return false;">
								<i class="icon-refresh-edit"></i>초기화
							</button>
							<button type="button" class="btn btn-sm btn-blue float-right" onclick="fn_enroll();return false;">
								<i class="icon-user-add"></i>관리자 등록
							</button>

						</li>
						<li class="list-group-item p-4">
							<p>사용자 아이디</p> <input type="text"
							class="form-control float-left w-75" name="userId" id="userId" data="N"
							placeholder="아이디를 입력해 주세요." value="" onchange="fn_idReset();return false;">
							<button type="button"
								class="btn btn-vestap btn-outline-blue w-25 mb-3" onclick="fn_idCheck();return false;">
								<i class="icon-search"></i>중복검사
							</button>
							<p>사용자 이름</p> <input type="text"
							class="form-control float-left mb-3" name="userNm" id="userNm"
							placeholder="이름을 입력해 주세요.">

							<p>사용자 비밀번호</p> <input type="text"
							class="form-control float-left w-75 mb-3" readonly="readonly" name="userPw" id="userPw" data="N"
							placeholder="비밀번호 발급을 클릭하세요.">
							<button type="button"
								class="btn btn-vestap btn-outline-blue w-25 mb-3" onclick="fn_newPw();return false;">
								비밀번호 발급
							</button>

							<div class="mb-3">접속 권한</div>
							<div class="select_box mb-3">
								<select class="form-control" name="userAuth" id="userAuth">
									<option value="A">사이트 관리자</option>
									<!-- <option value="W">시도 사용자</option>
									<option value="B">시군구 사용자</option> -->
								</select>
							</div>
							<%-- <div class="mb-3">행정구역</div>
							<div class="row mb-3">
								<div class="col pr-2">
									<div class=" select_box">
										<select class="form-control" name="sido" id="sido">
											<c:forEach var="i" items="${sidoList }">
												<option value='${i.districtCd}'>${i.districtNm}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col pl-2">
									<div class=" select_box">
										<select class="form-control" name="sigungu" id="sigungu">
										</select>
									</div>
								</div>
							</div> --%>
						</li>
						<li class="list-group-item p-4"></li>
					</ul>
					</form>
				</div>
				<!-- //card -->
			</td>
			<td class="offcanvas-right-open" id="updateArea">
				<!-- *********************** offcanvas-right-open *********************** -->

				<div class="card" >
					<div class="card-header offcanvas-right-open-title">
						<i class="icon-file-bookmark"></i>사용자 변경
						<button type="button" class="offcanvasCloseBtn close">
							<i class="icon-close-bold"></i>
						</button>
					</div>
					<form name="updateFrm" id="updateFrm" action="/admin/user/management/updateAction.do">
					<ul class="list-group list-group-flush" style="height: 785px">
						<li class="list-group-item">

							<button type="button" class="btn btn-sm btn-blue float-left" onclick="fn_init();return false;">
								<i class="icon-refresh-edit"></i>초기화
							</button>
							<button type="button" class="btn btn-sm btn-blue float-right" onclick="fn_updateAction();return false;">
								<i class="icon-user-add"></i>사용자 변경
							</button>

						</li>
						<li class="list-group-item p-4">
							<p>사용자 아이디</p> <input type="text"
							class="form-control float-left w-75" name="upUserId" id="upUserId" data="N"
							placeholder="아이디를 입력해 주세요." value="" onchange="fn_idReset();return false;">
							<button type="button"
								class="btn btn-vestap btn-outline-blue w-25 mb-3" onclick="fn_idCheck();return false;">
								<i class="icon-search"></i>중복검사
							</button>
							<p>사용자 이름</p> <input type="text"
							class="form-control float-left mb-3" name="upUserNm" id="upUserNm"
							placeholder="이름을 입력해 주세요.">

							<p>사용자 비밀번호</p> <input type="text"
							class="form-control float-left w-75 mb-3" readonly="readonly" name="upUserPw" id="upUserPw" data="N"
							placeholder="비밀번호 발급을 클릭하세요.">
							<button type="button"
								class="btn btn-vestap btn-outline-blue w-25 mb-3" onclick="fn_newPw();return false;">
								비밀번호 발급
							</button>

							<div class="mb-3">접속 권한</div>
							<div class="select_box mb-3">
								<select class="form-control" name="upUserAuth" id="upUserAuth">
									<option value="A">사이트 관리자</option>
									<!-- <option value="W">시도 사용자</option>
									<option value="B">시군구 사용자</option> -->
								</select>
							</div>
							<%-- <div class="mb-3">행정구역</div>
							<div class="row mb-3">
								<div class="col pr-2">
									<div class=" select_box">
										<select class="form-control" name="upSido" id="upSido">
											<c:forEach var="i" items="${sidoList }">
												<option value='${i.districtCd}'>${i.districtNm}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col pl-2">
									<div class=" select_box">
										<select class="form-control" name="upSigungu" id="upSigungu">
										</select>
									</div>
								</div>
							</div> --%>
						</li>
						<li class="list-group-item p-4"></li>
					</ul>
					</form>
				</div>
				<!-- //card -->
			</td>
		</tr>
	</table>
	<script >
	
	$(document).ready(function() { 
		//시도 목록 변경 시 시군구 목록 불러오기
		//사용자 추가
		$("#sido").change(function(){
			fn_sigunguList("insert");
		});
		
		$("#userAuth").change(function(){
			fn_sigunguList("insert");
		});
		
		//사용자 변경
		$("#upSido").change(function(){
			fn_sigunguList("update");
		});
		
		$("#upUserAuth").change(function(){
			fn_sigunguList("update");
		});
		
		
		$("#sigungu").css("display","none");
		$("#upSigungu").css("display","none");
		
		//아이디 정렬
		$(".id-up").click(function(){
			fn_sort("id", "up");
			
		});
		$(".id-down").click(function(){
			fn_sort("id", "down");
		});
		
		//이름 정렬
		$(".name-up").click(function(){
			fn_sort("name", "up");
			
		});
		$(".name-down").click(function(){
			fn_sort("name", "down");
		});
		
		
	});

	function fn_previousPage(id){
		var page = $("#"+id+"Page").val()*1;
		if(page>1){
		$("#"+id+" .pg_"+page).css("display","none");
		page--;
		$("#"+id+" .pg_"+page).css("display","table-row");
		}
		$("#"+id+"Page").val(page);
		$("#"+id+"CurPage").text('');
		$("#"+id+"CurPage").text(page);
	}

	function fn_nextPage(id){
		var page = $("#"+id+"Page").val()*1;
		var limitPage = $("#"+id+"Limit").val()*1;
		if(page<limitPage){
		$("#"+id+" .pg_"+page).css("display","none");
		page++;
		$("#"+id+" .pg_"+page).css("display","table-row");
		}
		$("#"+id+"Page").val(page);
		$("#"+id+"CurPage").text('');
		$("#"+id+"CurPage").text(page);
	}
	
	function fn_openWrite(){
		$('.offcanvas-right-open').removeClass('active');
		$('.offcanvas-right-open .card').removeClass('active');
		$('#writeArea').addClass('active');
		$('#writeArea .card').addClass('active');
	}
	
	function fn_openUpdate(){
		$('.offcanvas-right-open').removeClass('active');
		$('.offcanvas-right-open .card').removeClass('active');
		$('#updateArea').addClass('active');
		$('#updateArea .card').addClass('active');
	}
	
	function fn_init(){
		var id = $("#userId").val("");
		var idCheck = $("#userId").attr("data",'N');
		var name = $("#userNm").val("");
		var pw = $("#userPw").val("");
		var pwCheck = $("#userPw").attr("data",'N');
		var auth = $("#userAuth option:eq(0)").attr("selected","selected");
		var sido = $("#sido option:eq(0)").attr("selected","selected");
		var sigungu = $("#sigungu").empty();
		$("#sigungu").css("display","none");
	}
	
	function fn_idReset(){
		$("#userId").attr("data","N");
	}
	
	function fn_enroll(){
		if(fn_checkEnroll()){
			var id = $("#userId").val();
			var name = $("#userNm").val();
			var pw = $("#userPw").val();
			var auth = $("#userAuth option:selected").val();
			var dist = '00000000';
			
			var url = '/admin/admin/management/enroll.do';
			var dataSet = {
					"userId":id
					,"userNm":name
					,"userPw":pw
					,"userAuth":auth
					,"userDist":dist
			};
			$.ajax({
				url:url
				,data:dataSet
				,type:'get'
				,dataType:'json'
				,success:function(result){
					
					var chk = result.chk;
					var agent = navigator.userAgent.toLowerCase();
					
					if(chk>0){
						
						if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
							alert("[승인] 사용자 등록 완료");
							location.reload();
						} else {
							swal('사용자 등록 완료','사용자 등록이 성공적으로 수행되었습니다.','success').then(function(){ location.reload();});
						}
						
					}else{
						
						if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
							alert("[경고] 사용자 등록 실패");
							location.reload();
						} else {
							swal('사용자 등록 실패','사용자 등록에 실패하였습니다.','error').then(function(){ location.reload();});
						}
					}
				}
				,error:function(error){
					fn_alert("경고", "사용자 등록 오류", "error");
				}
			});
			}
		}
	
	function fn_checkEnroll(){
		var id = $("#userId").val();
		var idCheck = $("#userId").attr("data");
		var name = $("#userNm").val();
		var pwCheck = $("#userPw").attr("data");
		var auth = $("#userAuth option:selected").val();
		
		var msg = '';
		var type = 'warning';
		if(id==null || id==''){
			msg = '아이디를 입력해 주세요.';
		}else if(idCheck != 'Y'){
			msg = '중복검사를 진행해주세요.';
		}else if(pwCheck != 'Y'){
			msg = '비밀번호를 발급해주세요.';
		}else{
			return true;
		}
		
		fn_alert("경고", msg, type);
		return false;
	}
	
	function fn_idCheck(){
		var id = $("#userId").val();
		var url = '/admin/admin/management/idCheck.do';
		var dataSet = {
				"userId" : id
			};
		var msg = '';
		var type = 'warning';
		var regExp = /^[A-Za-z0-9]*$/;
		if(id==null || id==''){
			msg = '아이디를 입력해 주세요.';
		}else if(id.match(regExp) != null){
		$.ajax({
			url:url
			,data:dataSet
			,type:'get'
			,dataType:'json'
			,success:function(result){
				var chk = result.chk;
				var title = "";
				var msg = '';
				var type = '';
				var data = '';
				if(chk>0){
					//중복
					title = "아이디 중복";
					msg = id+' 은/는 이미 사용 중인 아이디 입니다.';
					type = 'error';
					data = 'N';
				}else{
					//사용가능
					title = "아이디 사용가능";
					msg = id+' 은/는 사용 가능한 아이디 입니다.';
					type = 'success';
					data = 'Y';
				}
				
				fn_alert("사용자 ", msg, type);
				$("#userId").attr("data",data);
			}
		});
		}else{
			msg = '아이디에 사용할 수 없는 문자가 포함되어 있습니다.';
		}
		fn_alert("경고", msg, type);
	}
	
	function fn_newPw(){
		var url = '/admin/user/management/newPw.do';
		$.ajax({
			url:url
			,type:'get'
			,dataType:'json'
			,success:function(result){
				
				var value = result.value;
				data = 'Y';
				
				fn_alert("승인", "비밀번호 발급 완료(비밀번호를 잊어버리지 않도록 주의해주세요.)", "success");
				
				$("#userPw").val(value);
				$("#userPw").attr("data",data);
			}
		});
	}
	
	function fn_sigunguList(mode,sggCode){
		var auth, sidoCode, select;
		if(mode == "insert"){
			auth = $("#userAuth option:selected").val();
			sidoCode = $("#sido option:selected").val();
			select = "sigungu";
			
		}else if(mode =="update"){
			auth = $("#upUserAuth option:selected").val();
			sidoCode = $("#upSido option:selected").val();
			select = "upSigungu";
		}
		var dataSet = {"sidoCode" : sidoCode};
		
		if(auth=='B'){
			$.ajax({
				url:'/admin/user/management/sigunguList.do',
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
	
	/* function fn_updateAction(){
		
		$('#updateFrm').submit();
	} */
	function fn_updateUser(tag){
		var data = tag.attr("data");
		var url = '/admin/user/management/pwChange.do';
		var dataSet = {
			"data" : data
		};
		
		var agent = navigator.userAgent.toLowerCase();

		if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
										
			if(confirm(data+" 사용자의 비밀번호를 재 발급하시겠습니까?")) {
				$.ajax({
					url:url
					,data:dataSet
					,type:'get'
					,dataType:'json'
					,success:function(result){
						
						var value = result.value;
						var chk = result.chk;
						
						if(chk>0){
							alert("[승인] 변경된 비밀번호 : <b>"+value+"</b>");
						}else{
							alert("[경고] 변경 실패");
						}
					}
					,error:function(error){
						alert("[경고] 수정 오류");
					}
				});
			}
			
		} else {
			
			swal({
				  title: '비밀번호 재 발급',
				  text: data+" 사용자의 비밀번호를 재 발급하시겠습니까?",
				  type: 'warning',
				  showCancelButton: true,
				  confirmButtonColor: '#3085d6',
				  cancelButtonColor: '#d33',
				  confirmButtonText: '재발급',
				  cancelButtonText: '취소'
				}).then(function(result) {
				  if (result.value) {
					  $.ajax({
							url:url
							,data:dataSet
							,type:'get'
							,dataType:'json'
							,success:function(result){
								
								var value = result.value;
								var chk = result.chk;
								if(chk>0){
									swal({
										  type: 'success',
										  title: '비밀번호 변경 완료',
										  html: '변경된 비밀번호 : <b>'+value+'</b>',
										  allowOutsideClick:false,
										  allowEscapeKey:false
										});		
								}else{
									swal(
								      '변경 실패',
								      '잠시후 다시 시도바랍니다.',
								      'error'
								    );
								}
							}
							,error:function(error){
								swal(
							      '수정 오류',
							      '잠시후 다시 시도바랍니다.',
							      'error'
							    );
							}
						});
				  }
				});
		}
	}

	function fn_deleteUser(tag){
		var data = tag.attr("data");
		var url = '/admin/admin/management/deleteAdmin.do';
		var dataSet = {
			"data" : data
		};
		
		var agent = navigator.userAgent.toLowerCase();

		if((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {
										
			if(data == 'admin'){
				alert("대표 관리자는 삭제하실 수 없습니다.");
			}else{
				if(confirm("[확인] 정말 삭제하시겠습니까?")) {
					$.ajax({
						url:url
						,data:dataSet
						,type:'get'
						,dataType:'json'
						,success:function(result){
							var chk = result.chk;
							if(chk>0){
								
								alert("[승인] 정상적으로 삭제 되었습니다.");
								location.reload();
								
							}else{
								
								alert("[경고] 삭제 실패");
								location.reload();
								
							}
						}
						,error:function(error){
							alert("[경고] 삭제 오류");
							location.reload();
						}
					});
				}
			}
			
		} else {
			if(data == 'admin'){
				swal('관리자 삭제 실패','대표 관리자는 삭제하실 수 없습니다.','error').then(function(){ location.reload();});
			}else{
				swal({
					  title: '정말 삭제하시겠습니까?',
					  text: "삭제된 사용자 정보는 되돌릴 수 없습니다.",
					  type: 'warning',
					  showCancelButton: true,
					  confirmButtonColor: '#3085d6',
					  cancelButtonColor: '#d33',
					  confirmButtonText: '삭제',
					  cancelButtonText: '취소'
					}).then(function(result){
					  if (result.value) {
						  $.ajax({
								url:url
								,data:dataSet
								,type:'get'
								,dataType:'json'
								,success:function(result){
									var chk = result.chk;
									if(chk>0){
										swal(
									      '삭제 완료',
									      '정상적으로 삭제 되었습니다.',
									      'success'
									    ).then(function(){ location.reload();});
									}else{
										swal(
									      '삭제 실패',
									      '잠시후 다시 시도바랍니다.',
									      'error'
										).then(function(){ location.reload();});
									}
								}
								,error:function(error){
									swal(
								      '삭제 오류',
								      '잠시후 다시 시도바랍니다.',
								      'error'
								    ).then(function(){ location.reload();});
								}
							})
					  }
					});
			}
		}
	}
	
	function fn_search(){
		var id = $("#search-user").val();
		console.log(id);
		var dataSet = {
			id : id	
		};
		var url = '/admin/user/management/searchAction.do';
		$.ajax({
			url:url
			,data:dataSet
			,type:'get'
			,dataType:'json'
			,success:function(result){
				console.log(result);
				var list = result.userList;
				var pageCnt = result.pageCnt;
				$("#userListLimit").val(pageCnt);
				$("#userListLimitPage").text(pageCnt);
				var html =null;
				$("#userList").empty();
				if(list.length > 0){
					for(var i=0;i<list.length;i++){
						if(list[i].page == 1){
							html += '<tr class="pg_'+list[i].page+'" style="display:table-row">';	
						}else{
							html += '<tr class="pg_'+list[i].page+'" style="display:none">';
						}
						html += '<td>'+ list[i].no +'</td>';
						html += '<td>'+ list[i].userId +'</td>';
						html += '<td>'+ list[i].userNm +'</td>';
						html += '<td>'+ list[i].userDist +'</td>';
						html += '<td>'+ list[i].userAuth +'</td>';
						html += '<td>';
						html += '<button type="button" class="btn btn-sm btn-blue" data="'+list[i].userId+'" onclick="fn_updateUser($(this));return false;">';
						html += '<i class="icon-file-write-edit"></i>수정</button>';
						html += '<button type="button" class="btn btn-sm btn-red" data="'+list[i].userId+'" onclick="fn_deleteUser($(this));return false;">';
						html += '<i class="icon-file-remove"></i>삭제</button></td>';
						html += '</tr>';
					}
				}
				$("#userList").append(html);
				
			}
			,error:function(error){
				/* swal(
			      '수정 오류',
			      '잠시후 다시 시도바랍니다.',
			      'error'
			    ); */
			}
		});
	}
	
	function fn_sort(category, type){
		var dataSet = {
			category : category,
			type : type
		};
		var url = '/admin/user/management/sort.do';
		$.ajax({
			url:url
			,data:dataSet
			,type:'get'
			,dataType:'json'
			,success:function(result){
				console.log(result);
				var list = result.userList;
				var pageCnt = result.pageCnt;
				$("#userListPage").val("1");
				$("#userListCurPage").text('');
				$("#userListCurPage").text("1");
				$("#userListLimit").val(pageCnt);
				$("#userListLimitPage").text(pageCnt);
				var html =null;
				$("#userList").empty();
				if(list.length > 0){
					for(var i=0;i<list.length;i++){
						if(list[i].page == 1){
							html += '<tr class="pg_'+list[i].page+'" style="display:table-row">';	
						}else{
							html += '<tr class="pg_'+list[i].page+'" style="display:none">';
						}
						html += '<td>'+ list[i].no +'</td>';
						html += '<td>'+ list[i].userId +'</td>';
						html += '<td>'+ list[i].userNm +'</td>';
						html += '<td>'+ list[i].userDist +'</td>';
						html += '<td>'+ list[i].userAuth +'</td>';
						html += '<td>';
						html += '<button type="button" class="btn btn-sm btn-blue" data="'+list[i].userId+'" onclick="fn_updateUser($(this));return false;">';
						html += '<i class="icon-file-write-edit"></i>수정</button>';
						html += '<button type="button" class="btn btn-sm btn-red" data="'+list[i].userId+'" onclick="fn_deleteUser($(this));return false;">';
						html += '<i class="icon-file-remove"></i>삭제</button></td>';
						html += '</tr>';
					}
				}
				$("#userList").append(html);
				
			}
			,error:function(error){
			}
		});
	}
	
	</script>
</body>
</html>