<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP - 회원가입 요청</title>

<style>
	.container {margin-top: 70px; }
	.step-2 {display: none; }
	label > input[type=file] {display: none; }
	.divUserApply_Ele {height: 95px; }
	.divUserApply_Ele_70 {height:70%; }
	.divUserApply_Ele_File {height:150%; }
	.divUserApply_Ele_File #add-file-area{height:48px; }
	.divUserApply_Ele p span{color:#eb4b3d; font-weight: 900; }
	.divUserApply_Ele_70 p span{color:#eb4b3d; font-weight: 900; }
	
	.divUserApply_Ele_TermsBox1{font-weight: 900; }
	.divUserApply_Ele_TermsBox1 p span{ color:#eb4b3d; font-weight: 900; }
	.divUserApply_Ele_TermsBox2 {padding-top: 10px;}
	.divUserApply_Ele_TermsBox2 p span{color:#eb4b3d; }
	.agree_Label1{padding-left: 20px;}
	.agree_Label2{padding-left: 20px;}
	.agree_Label3{color:#eb4b3d; font-weight: 900; padding-left: 280px;}
	
	.purpose_Label1{padding-left:20px;}
	.purpose_Label2{padding-left:20px;}
	.purpose_Label3{padding-left:20px;}
	.purpose_Label4{padding-left:20px;}
	/* .purpose_Label5{position:fixed; width: 100%;} */
	.purpose_Label5{position:unset; width: 100%;}
	.purpose_Label5 input[type="text"]{width:100%;}
	
	#userId{ime-mode:disabled; }
	#userNm{ime-mode:active; }
	#userOrgNm{ime-mode:active; }
	#userEmail{ime-mode:disabled; }
	.swal2-popup .swal2-content{text-align:center !important; }
	#divUserApply_Ele_Terms_box{text-align:center !important; }
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
								<i class="icon-file-roll"> </i>회원가입 요청 <span class="normal-text ml-2">아이디 발급을 위해 사용자 정보를 입력해주세요.</span>
							</div>
						</div>
						<!-- //navbar -->
						<div class="contents">
						
							<form name="frm" id="frm" action="/user/userApplyInsert.do?${_csrf.parameterName}=${_csrf.token}"  method="post" enctype="multipart/form-data">
								<input type="hidden" id="csrf" value="${_csrf.parameterName}=${_csrf.token}">
								<input type="hidden" id="adminChk" value="${adminChk}">
								
								<ul class="list-group list-group-flush">
									<li class="list-group-item p-4">
										<div class="divUserApply_Ele">
											<p><span>*</span>    아이디</p>
											<input type="text" class="form-control float-left" name="userId" id="userId" placeholder="희망 아이디를 입력해주세요." maxlength="20" value="" onKeyUp="fn_userId_onKeyUp();">
											<p class="fn_userId_onKeyUp_effect" style="display: inline-block;"></p>
										</div>
										
										<div class="divUserApply_Ele">
											<p><span>*</span>    패스워드</p>
											<input type="text" class="form-control float-left w-65" name="userPw" id="userPw" placeholder="비밀번호 발급을 클릭하세요." maxlength="20" value="" readOnly>
											<button type="button" class="btn btn-vestap btn-outline-blue w-35" onclick="fn_newUserPw(); return false;">
												새 패스워드 발급
											</button>
<!-- 										<p class="emph_notice" style="display: none;"></p> -->
											<p class="fn_userPw_onKeyUp_effect" style="display: inline-block;"></p>
										</div>
										
										<div class="divUserApply_Ele">
											<p><span>*</span>    이메일</p>
											<input type="text" class="form-control float-left" name="userEmail" id="userEmail" placeholder="이메일을 입력해주세요." maxlength="30" value="" onKeyUp="fn_userEmail_onKeyUp();">
											<p class="fn_userEmail_onKeyUp_effect" style="display: inline-block;"></p>
										</div>
										
										<div class="divUserApply_Ele_70">
											<p><span>*</span>    이메일 수신동의 여부
												<label style="padding-right: 20px;padding-left: 20px;"><input type="radio" name="emlRcptnAgreYn" value = 'Y' <c:if test="${userInfo.emlRcptnAgreYn eq 'Y'}">checked="checked"</c:if>/>동의</label>
												<label style="padding-right: 50px;"><input type="radio" name="emlRcptnAgreYn" value="N" <c:if test="${userInfo.emlRcptnAgreYn == null || userInfo.emlRcptnAgreYn eq 'N'}">checked="checked"</c:if>/>미동의 <span style="color:#eb4b3d; font-weight: 900; padding-left: 50px;">※ 동의하지 않을 경우, 신청결과를 메일로 확인하실 수 없습니다.</span></label>
											</p>
										</div>
										
										<div class="divUserApply_Ele">
											<p><span>*</span>    이름</p>
											<input type="text" class="form-control float-left" name="userNm" id="userNm" placeholder="이름을 입력해주세요." maxlength="30" value="" onKeyUp="fn_userNm_onKeyUp();">
											<p class="fn_userNm_onKeyUp_effect" style="display: inline-block;"></p>
										</div>
										
										<div class="divUserApply_Ele">
											<p><span>*</span>    소속</p>
											<input type="text" class="form-control float-left" name="userOrgNm" id="userOrgNm" placeholder="소속을 입력해주세요." maxlength="50" value="" onKeyUp="fn_userOrgNm_onKeyUp();">
											<p class="fn_userOrgNm_onKeyUp_effect" style="display: inline-block;"></p>
										</div>
										
										<div class="divUserApply_Ele">
											<p><span>*</span>    접속권한</p>
											<input type="hidden" class="form-control" name="userGovYn" id="userGovYn" />
											<select class="form-control" id="userGovYnList" onChange="fn_userGovYnList_onChange();">
												<option value="N">비공무원</option>
												<option value="Y">공무원</option>
											</select>
										</div>
										
										<div class="divUserApply_Ele">
											<p><span>*</span>    지자체구분</p>
											<div class="select_box mb-3">
												<select class="form-control" name="userAuth" id="userAuth">
													<option value="W">시도 사용자</option>
													<option value="B">시군구 사용자</option>
												</select>
											</div>
										</div>
										
										<div class="divUserApply_Ele">
											<p><span>*</span>    행정구역</p>
											<div class="row mb-3">
												<input type="hidden" class="form-control" name="userDist" id="userDist" />
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
														<select class="form-control" name="sigungu" id="sigungu"></select>
													</div>
												</div>
											</div>
										</div>
										
										<div class="divUserApply_Ele_TermsBox1">
											<p><span>*</span>    운영방침 및 데이터 보안 서약</p>
											<textarea name="userSecu" class="userSecuTextarea"  cols="75" rows="10"  style="width:100%" title="게시판소개수정">
■ 운영방침
1. 기후변화 취약성 평가도구 시스템(VESTAP)의 운영목적은 광역/기초지자체의 기후변화 적응대책 세부시행 계획 수립 시 취약성을 분석하기 위한 지표기반 평가를 지원합니다. 따라서 개인(교수,직원,학생)의 연구목적으로 사용하는 것을 제한합니다. 단, 국가 R&D, 사업 등 공공의 목적을 위한 내용일 경우 신청 여부를 심사하여 ID와 PW를 발급합니다.
2. 외부 사용자는 계정신청 시 데이터 보안서약서 및 개인정보 수집동의서를 필수로 첨부합니다.
3. 자료 이용은 신청자에 한하여 자유롭게 이용할 수 있으며 자료를 사용하여 수행된 분석임을 아래와 같이 명기해야 합니다.

   ① 관련 연구과제가 있는 경우: 연구책임자, 연도, 연구과제명, 발행처
      표기 예: 홍길동, 2021, 「취약성평가도구 정책과제」, 한국환경연구원
   ② 관련 연구과제가 없는 경우: 발행처, 발행년도, 보고서명
      표기 예: 한국환경연구원, 2021, ‘취약성평가도구 의식 조사’

4. 3개월 이상 사용되지 않는 계정에 대해서는 최초 등록된 사용자 정보에 의하여 확인 절차를 거친 후 계정을 임의로 삭제합니다.

■ 데이터 보안 서약
기후변화 취약성 평가도구 시스템(이하 “VESTAP”)의 자료 이용자는 자료를 활용함에 있어 다음과 같은 사항을 준수해야 함을 고지하오니 동의하여 주시기 바랍니다. 자료 이용자는 아래 사항에 동의하는 것을 거부할 수 있는 권리가 있으나 거부할 경우 자료를 제공받지 못할 수 있습니다.

 1. 자료, 연구 생성물, 연구결과 산출물을 승인받은 목적 외의 용도로 이용하거나 불법적인 용도로 이용하지 않습니다.
 2. 자료에 대한 저작권과 지적 소유권은 한국환경연구원 국가기후변화적응센터에서 가지고 있습니다. 제공 자료에 대하여 승인을 얻은 연구자가 아닌 제3자에게 열람하게 하거나 제공(이전), 대여, 판매하지 아니하며, 제공 자료에 대한 보안 및 관리를 철저히 합니다.
 3. 제공 자료를 개인을 식별할 수 있는 형태로 변경하는 등 개인정보를 침해하는 어떠한 행위도 하지 않겠으며, 재식별 가능성이 있을 경우에는 자료이용을 중지하고, 연구종료 후에는 반드시 자료를 폐기하겠습니다.
 4. 자료의 이용 및 그에 따른 연구로 인하여 발생하는 모든 책임은 연구자가 부담합니다.
 5. VESTAP은 상기 제1호에서 제4호까지를 준수하지 않은 자에 대하여 자료 제공을 제한하고 이 사실을 통지할 수 있습니다.

본인은 위 사항을 충분히 숙지하였으며, 
자료제공 및 보안유지를 위한 VESTAP의 업무 처리에 동의합니다.</textarea>
										</div>
										<div class="divUserApply_Ele_TermsBox2">
											<p><span>*</span>    운영방침 및 데이터 보안 서약 동의 여부
												<label class="agree_Label1"><input type="radio" name="securityAgreeYn" value="Y"/>동의</label>
												<label class="agree_Label2"><input type="radio" name="securityAgreeYn" value="N" checked="checked"/>미동의</label>
												<label class="agree_Label3">※ 동의하지 않을 경우, 신청결과를 메일로 확인하실 수 없습니다.</label>
											</p>
										</div>
										
										<div class="divUserApply_Ele_70">
											<p><span>*</span>    사용목적
												<label class="purpose_Label1"><input type="radio" name="purposeUseCd" value="jy" checked="checked" />정책연구</label>
												<label class="purpose_Label2"><input type="radio" name="purposeUseCd" value="hn" />학술논문</label>
												<label class="purpose_Label3"><input type="radio" name="purposeUseCd" value="jt" />정보탐색</label>
												<label class="purpose_Label4"><input type="radio" name="purposeUseCd" value="et" />기타(직접입력)</label>
												<label class="purpose_Label5">
													<input type="text"  id="purposeUseNm" name="purposeUseNm" width="100%" placeholder="연구책임자, 연도, 연구과제명, 발행처, 발행년도, 보고서명" maxlength="100" title="연구책임자, 연도, 연구과제명, 발행처, 발행년도, 보고서명"/>
													<span>※ 관련 연구과제가 있는 경우 : 연구책임자, 연도, 연구과제명, 발행처를 기재    || 관련 연구과제가 없는 경우 : 발행처, 발행년도, 보고서명을 기재</span>
												</label>
											</p>
										</div>
										
										<%-- <sec:authorize access="hasAnyRole('ROLE_WIDE', 'ROLE_BASE')">
											<input type="hidden" name="useYn" id="useYn" value="D"/>
										</sec:authorize> --%>
										<input type="hidden" name="useYn" id="useYn" value="D"/>
										
										<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
										
											<div class="divUserApply_Ele_70">
												<input type="hidden" name="emlSndngYn" id="emlSndngYn" value="" />
												<select class="form-control" id="emlSndngYnList" onChange="fn_emlSndngYnList(); return false;" style="margin-bottom: 16px;">
													<option value="N">메일 미발송</option>
													<option value="Y">메일 발송</option>
												</select>
											</div>
											
											<div class="divUserApply_Ele">
												<input type="hidden" name="useYn" id="useYn" />
												<p>사용상태</p>
												<select class="form-control" id="useYnList">
													<option value="D">대기</option>
													<option value="N">미사용</option>
													<option value="Y" selected="selected">사용</option>
												</select>
											</div>
											
										</sec:authorize>
										
										<div class="borad-file">
											<i class="icon-file-attach"></i>공문
		 									<div class="card mt-lg-3">
												<div class="card-header p-2">
													<div class="row">
														<div class="col-8 p-1 pl-4">첨부파일 등록 (파일 형식 : hwp, pptx, pdf, xlsx, txt, jpg, png, gif, jpeg)</div>
														<div class="col-4 pl-0 text-right">
															<label for="add-file" class="btn btn-vestap btn-blue mb-0 mr-1"><i class="icon-search-01"></i>파일첨부
															<input type="file" name="uploadFileOne" class="upload-hidden1" id="add-file" multiple="multiple"></label>
														</div>
													</div>
												</div>
												
												<div class="card-body p-2" id="add-file-area">
													<span style="padding-left: 7px;">첨부파일이 존재하지 않습니다.</span>
												</div>
											</div>
											<span style="color:#eb4b3d; font-weight: 900;">※ 공문 전자 발송은 필수 입니다. 첨부파일 외 공문 전자발송이 없을시 사용에 제한이 있을 수 있습니다. </span>
										</div>
									</li>
									
									<li class="list-group-item p-4">
										<div class='btnWrap' style="text-align: center;">
											<button type="button" class="btn btn-sm btn-blue" onclick="fn_userApplyIdDualCheck(); return false;">
												<i class="icon-user-add"></i>신 청
											</button>
											<button type="button" class="btn btn-sm btn-red" onclick="fn_userMoveHome(); return false;">
												<i class="icon-file-remove"></i>취 소
											</button>
										</div>
									</li>
								</ul>

							</form>
						</div>
					</div>
					<!-- //mainContents -->
				</td>
			</tr>
		</table>
	</div>

<script type="text/javascript">
	$(document).ready(function() {
		
		fn_userGovYnList_onChange();
		
		$("input[name='purposeUseCd']:radio").change(function () {
		    //라디오 버튼 값을 가져온다.
		    var purposeUseCd = this.value;
		    console.log(purposeUseCd);
		    /* if(purposeUseCd == "et") {
		    	console.log("기타를 선택");
		    	$(".purpose_Label5").css("position","unset");
		    } else {
		    	$(".purpose_Label5").css("position","fixed");
		    } */
		});
		
		$("input[name='emlRcptnAgreYn']:radio").change(function () {
		    //라디오 버튼 값을 가져온다.
		    var emlRcptnAgreYn = this.value;
		    
		    $("#emlRcptnAgreYn").val("N");
		    if(emlRcptnAgreYn=="N"){
		    	$("#emlSndngYnList").val("N").attr("selected", "selected");
		    }
		});
		
		//시도 목록 변경 시 시군구 목록 불러오기
		$("#sido").change(function(){
			fn_sigunguList("insert");
		});
		
		$("#userAuth").change(function(){
			fn_sigunguList("insert");
		});
		
		$("#sigungu").css("display","none");
		$("#upSigungu").css("display","none");
	});
	
	function fn_newUserPw() {
		
		var url = '/user/newPw.do';
		$.ajax({
			url : url,
			type : 'get',
			dataType : 'json',
			success : function(result) {

				var value = result.value;
				data = 'Y';
				
				fn_alert("승인", "비밀번호 발급 완료(비밀번호를 잊어버리지 않도록 주의해주세요.)", "success");
				
				$("#userPw").val(value);
				$("#userPw").attr("data", data);
				fn_userPw_onKeyUp();
			}
		});
	}
	
	function fn_userId_onKeyUp() {
		
		var numChk = /[0-9]/;	// 숫자 
    	var engChk = /[a-zA-Z]/;	// 영문 
    	var spcChk = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
    	var korChk = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크
    	
		var userId = $("#userId").val();
		
    	if(userId!="") {
    		$(".fn_userId_onKeyUp_effect").text("");
    	}
    	
		if ( userId!="" && spcChk.test(userId) == true) {
			$(".fn_userId_onKeyUp_effect").css("display", "show");
			$(".fn_userId_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userId_onKeyUp_effect").text("아이디에 특수문자가 존재합니다.");
			$("#userId").val("");
		}
		
		if (korChk.test(userId) == true) {
			$(".fn_userId_onKeyUp_effect").css("display", "show");
			$(".fn_userId_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userId_onKeyUp_effect").text("아이디에 한글이 존재합니다.");
			$("#userId").val("");
		}
		
		if ( (spcChk.test(userId) == false) && (korChk.test(userId) == false) ){
// 			$(".fn_userId_onKeyUp_effect").css("color", "#34a853");
// 			$(".fn_userId_onKeyUp_effect").text("잘 하고 있습니다.");
		}
	}
	
	function fn_userPw_onKeyUp() {
		
		var userPw = $("#userPw").val();
		var inputLength = userPw.length;
		var num = userPw.search(/[0-9]/g);
		var eng = userPw.search(/[a-z]/ig);
		var spe = userPw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
		
		if (userPw == null || userPw =="") {
			$(".fn_userPw_onKeyUp_effect").css("display", "block");
			$(".fn_userPw_onKeyUp_effect").css("color", "#ea4335");
			$(".fn_userPw_onKeyUp_effect").text("새 패스워드 발급버튼을 클릭해주세요.");
		} else if (inputLength<8 || inputLength>20) {
			$(".fn_userPw_onKeyUp_effect").css("display", "block");
			$(".fn_userPw_onKeyUp_effect").css("color", "#ea4335");
			$(".fn_userPw_onKeyUp_effect").text("비밀번호는 8자~20자여야 합니다.");
		} else if (userPw.search(/\s/) != -1) {
			$(".fn_userPw_onKeyUp_effect").css("color", "#ea4335");
			$(".fn_userPw_onKeyUp_effect").text("비밀번호는 공백이 없어야 합니다.");
		} else if (num < 0 || eng < 0 || spe < 0) {
			$(".fn_userPw_onKeyUp_effect").css("color", "#ea4335");
			$(".fn_userPw_onKeyUp_effect").text("영문, 숫자, 특수문자를 혼합하여 입력해주세요.");
		} else {
			$(".fn_userPw_onKeyUp_effect").css("color", "#549b08");
			$(".fn_userPw_onKeyUp_effect").text("비밀번호를 사용하셔도 좋습니다.");
		}
	}
	
	function fn_userEmail_onKeyUp() {
		
		var numChk = /[0-9]/;	// 숫자 
    	var engChk = /[a-zA-Z]/;	// 영문 
    	var spcChk = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
    	var korChk = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크
    	
		var userEmail = $("#userEmail").val();
    	
    	if (userEmail!="") {
    		$(".fn_userEmail_onKeyUp_effect").text("");
    	}
    	
    	if ( userEmail!="" && korChk.test(userEmail) == true) {
			$(".fn_userEmail_onKeyUp_effect").css("display", "show");
			$(".fn_userEmail_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userEmail_onKeyUp_effect").text("이메일에 한글이 존재합니다.");
			$("#userEmail").val("");
		}
	}
	
	function fn_userNm_onKeyUp() {
		
		var numChk = /[0-9]/;	// 숫자 
    	var engChk = /[a-zA-Z]/;	// 영문 
    	var spcChk = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
    	var korChk = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크
    	
    	var userNm = $("#userNm").val();
    	
    	if(userNm!="") {
    		$(".fn_userNm_onKeyUp_effect").text("");
    	}
    	
    	if ( userNm!="" && numChk.test(userNm) == true) {
			$(".fn_userNm_onKeyUp_effect").css("display", "show");
			$(".fn_userNm_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userNm_onKeyUp_effect").text("이름에 숫자가 존재합니다.");
			$("#userNm").val("");
		}
    	
    	if ( userNm!="" && engChk.test(userNm) == true) {
			$(".fn_userNm_onKeyUp_effect").css("display", "show");
			$(".fn_userNm_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userNm_onKeyUp_effect").text("이름에 영문이 존재합니다.");
			$("#userNm").val("");
		}
    	
    	if ( userNm!="" && spcChk.test(userNm) == true) {
			$(".fn_userNm_onKeyUp_effect").css("display", "show");
			$(".fn_userNm_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userNm_onKeyUp_effect").text("이름에 특수문자가 존재합니다.");
			$("#userNm").val("");
		}
	}
	
	function fn_userOrgNm_onKeyUp() {
		
		var numChk = /[0-9]/;	// 숫자 
    	var engChk = /[a-zA-Z]/;	// 영문 
    	var spcChk = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
    	var korChk = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크
    	
		var userOrgNm = $("#userOrgNm").val();
    	
    	if (userOrgNm!="") {
    		$(".fn_userOrgNm_onKeyUp_effect").text("");
    	}
    	
    	if ( userOrgNm!="" && engChk.test(userOrgNm) == true) {
			$(".fn_userOrgNm_onKeyUp_effect").css("display", "show");
			$(".fn_userOrgNm_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userOrgNm_onKeyUp_effect").text("소속에 영문이 존재합니다.");
			$("#userOrgNm").val("");
		}
    	
    	if ( userOrgNm!="" && spcChk.test(userOrgNm) == true) {
			$(".fn_userOrgNm_onKeyUp_effect").css("display", "show");
			$(".fn_userOrgNm_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userOrgNm_onKeyUp_effect").text("소속에 특수문자가 존재합니다.");
			$("#userOrgNm").val("");
		}
	}
	
	function fn_userGovYnList_onChange() {
		var userGovYn = $("#userGovYnList option:selected").val();
		if (userGovYn == "Y") {
			$("#dfUploadFileOneYn").text("*");
		} else {
			$("#dfUploadFileOneYn").text("  ");
		}
	}
	
	function fn_sigunguList(mode,sggCode) {
		
		var userAuth = $("#userAuth option:selected").val();
		var sidoCode = $("#sido option:selected").val();
		var select = "sigungu";
		
		var dataSet = {"sidoCode" : sidoCode};
		
		if (userAuth == 'B') {
			$.ajax({
				url : '/user/sigunguList.do',
				dataType : 'json',
				type : 'get',
				data : dataSet,
				success : function(result) {
					$("#" + select).empty();
					var list = result.list;
					for ( var i in list) {
						var html = "<option value='" + list[i].districtCd + "'>" + list[i].districtNm + "</option>";
						$("#" + select).append(html);
					}
					$("#" + select).css("display", "block");

					if (mode == "update") {
						$("#upSigungu").val(sggCode);
					}
				}
			});
		} else {
			$("#" + select).empty();
			$("#" + select).css("display", "none");
		}
	}
	
	function fn_userApplyIdDualCheck() {
		
		//userId체크
		var userId = $("#userId").val();
		var inputLength = userId.length;
		if (inputLength<6 || inputLength>20){
			$('#userId').focus();
			fn_alert("사용자 ", "죄송합니다. 아이디는 6에서 20글자 사이여야 합니다.", "warning");
			$(".fn_userId_onKeyUp_effect").css("display", "show");
			$(".fn_userId_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userId_onKeyUp_effect").text("아이디는 6에서 20글자 사이여야 합니다.");
			return false;
		}
		
		var url = '/user/userIdCheck.do';
		var dataSet = {
			"userId" : userId
		};
		
		var msg = "";
		var type = "warning";
		var regExp = /^[A-Za-z0-9]*$/;
		
		var userPw    = $("#userPw").val();
		var userEmail = $("#userEmail").val();
		var userNm    = $("#userNm").val();
		var userOrgNm = $("#userOrgNm").val();
		var userGovYn = $("#userGovYnList option:selected").val();
		var addFile   = $("#add-file-area span").text();
		var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
		
		if (userId == null || userId =="") {
			fn_alert("사용자 ", "아이디를 입력해 주세요.", "warning");
			$('#userId').focus();
			return false;
			
		} else if (userPw == null || userPw == "") {
			$('#userPw').focus();
			$(".fn_userPw_onKeyUp_effect").css("display", "show");
			$(".fn_userPw_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userPw_onKeyUp_effect").text("새 패스워드 발급버튼을 클릭해주세요.");
			fn_alert("사용자 ", "패스워드를 입력해 주세요.", "warning");
			return false;
			
		} else if (userEmail == null || userEmail == "") {
			$('#userEmail').focus();
			fn_alert("사용자 ", "이메일을 입력해 주세요.", "warning");
			return false;
			
		} else if (userEmail != null || userEmail != "") {
			
			if(exptext.test(userEmail)==false){
				$('#userEmail').focus();
				//이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우
				$(".fn_userEmail_onKeyUp_effect").css("display", "show");
				$(".fn_userEmail_onKeyUp_effect").css("color", "#ea4e43");
				$(".fn_userEmail_onKeyUp_effect").text("이 메일형식이 올바르지 않습니다.");
				
				fn_alert("사용자 ", "이 메일형식이 올바르지 않습니다.", "warning");
				document.frm.userEmail.focus();
				return false;
			}
			
		} else if (userNm == null || userNm == "") {
			$('#userNm').focus();
			$(".fn_userNm_onKeyUp_effect").css("display", "show");
			$(".fn_userNm_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userNm_onKeyUp_effect").text("이름을 입력해 주세요.");
			fn_alert("사용자 ", "이름을 입력해 주세요.", "warning");
			return false;
			
		} else if (userOrgNm == null || userOrgNm == "") {
			$('#userOrgNm').focus();
			$(".fn_userOrgNm_onKeyUp_effect").css("display", "show");
			$(".fn_userOrgNm_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userOrgNm_onKeyUp_effect").text("소속을 입력해 주세요.");
			fn_alert("사용자 ", "소속을 입력해 주세요.", "warning");
			return false;
		}
		
		if (userGovYn=='Y'){
			console.log(addFile);
			if(addFile==("첨부파일이 존재하지 않습니다.")) {
				$('#add-file-area').focus();
				fn_alert("사용자 ", "공문 첨부파일이 존재하지 않습니다.", "warning");
				return false;
			}/* else{
				fn_alert("공문 ", "첨부파일 외 공문 전자발송이 없을시 사용에 제한이 있을 수 있습니다.", "warning");
				return true;
			} */
		}
		
		$.ajax({
			url : url,
			data : dataSet,
			type : 'get',
			dataType : 'json',
			success : function(result) {
				
				var chk = result.chk;
				var title = "";
				var msg = '';
				var type = '';
				var data = '';
				
				if (chk > 0) {
					// 중복
					title = "아이디 중복";
					msg = userId + ' 은/는 이미 사용 중인 아이디 입니다.';
					type = 'error';
					data = 'N';
					
					$(".fn_userId_onKeyUp_effect").css("display", "show");
					$(".fn_userId_onKeyUp_effect").css("color", "#ea4e43");
					$(".fn_userId_onKeyUp_effect").text("이미 사용 중인 아이디 입니다.");
					$('#userId').focus();
					
				} else {
					/*
						// 사용가능
						title = "아이디 사용가능";
						msg = userId + ' 은/는 사용 가능한 아이디 입니다.';
						type = 'success';
						data = 'Y';
	 				*/
					if (fn_userApplyEmailCheck() == true) {
						fn_userApplyInsertAction();
					}
				}
				
				if(msg !='') {
					fn_alert("사용자 ", msg, type);
					$("#userId").attr("data", data);
					return "fail";
				}
				
			}
		});
	}

	function fn_userApplyEmailCheck() {
		
		var email = document.getElementById("userEmail").value;
		var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
		
		if(exptext.test(email)==false){
			//이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우			
			alert("이 메일형식이 올바르지 않습니다.");
			document.frm.userEmail.focus();
			return false;
		} else {
			return true;
		}
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
	
	function fn_userApplyInsertAction(){
		
		var securityAgreeYn = $("input[name='securityAgreeYn']:checked").val();
		if(securityAgreeYn =="N"){
			fn_alert("사용자 ", "운영방침 및 데이터보안 서약 동의를 하지 않으셨습니다.", "warning");
			return false;
		}
		var purposeUseCd = $("input[name='purposeUseCd']:checked").val();
		var purposeUseNm = $("input[name='purposeUseNm']").val();
		if(purposeUseCd == "et") {
			if( purposeUseNm =="") {
				fn_alert("사용자", "기타로 선택하신경우 사유를 입력해야 합니다.", "warning");
				return false;
			}
		}
		
		var adminChk        = $("#adminChk").val();
		var userId          = $("#userId").val();
		var userPw          = $("#userPw").val();
		var userNm          = $("#userNm").val();
		
		var userGovYn       = $("#userGovYnList option:selected").val();
		var userAuth        = $("#userAuth option:selected").val();
		var sidoCode        = $("#sidoList option:selected").val();
		var sigunguCode     = $("#sigunguList option:selected").val();
		
		$("#userGovYn").val(userGovYn);
		$("#userAuth").val(userAuth);
		$("#sidoCode").val(sidoCode);
		$("#sigunguCode").val(sigunguCode);
		
		var userDist = '';
		if(userAuth=='B'){
			userDist = $("#sigungu option:selected").val();
		} else {
			userDist = $("#sido option:selected").val();
		}
		$("#userDist").val(userDist);
		
		var url = '/user/userApplyInsert.do';
		var dataSet = new FormData(document.getElementById('frm'));
		$.ajax({
			url:url
			,data:dataSet
			,type:'post'
			,dataType:'json'
			,enctype: 'multipart/form-data'
			,processData: false
			,contentType: false
			,success:function(result){
				console.log("result\n"+result);
				
				if(result.resultYn !="1"){
					
					if(result.beforeInsertCheck=="fail"){
						// 중복
						title = "신청시간 오류";
						msg = '현재PC에서 신청시간이 5분 지나지 않았습니다.';
						type = 'error';
						data = 'N';
						fn_alert("사용자 ", msg, type);
					}
					/* 
					 else {
							// 중복
							title = "아이디 중복";
							msg = userId + ' 은/는 이미 사용 중인 아이디 입니다.';
							type = 'error';
							data = 'N';
							fn_alert("사용자 ", msg, type);
					}
					 */
				} else {
					//공백특수문자처리함으로 소스수정 시, 글자 틀어질 수 있음
// 					fn_alert("기억해두세요", "신청 아이디:"+result.userApplyVO.userId+"<br>신청 패스워드:"+result.userPwReq+"<br>신청결과는 이메일로 안내됩니다.", "success");
					
					console.log(adminChk);
					if(adminChk ==false || adminChk =="false"){
						fn_u_userApply_confirm(adminChk, "기억해주세요", "신청 아이디:"+result.userApplyVO.userId+"<br>신청 패스워드:"+result.userPwReq+"<br>신청결과는 이메일로 안내됩니다. <br>신청정보를 확인하고 메인페이지로 이동하시겠습니까?", "success");
					} else {
						fn_u_userApply_confirm(adminChk, "기억해주세요", "신청 아이디:"+result.userApplyVO.userId+"<br>신청 패스워드:"+result.userPwReq+"<br>회원관리 목록 페이지로 이동하시겠습니까?", "success");
					}
				}
			}
			,error:function(error){
				// 중복
				title = "실패";
				msg = userId + ' 입력에 실패하였습니다.';
				type = 'error';
				data = 'N';
				fn_alert("사용자 ", msg, type);
			}
		});
	}
	
	function fn_emlSndngYnList(){
		console.log("fn_emlSndngYnList");
		var emlSndngYn = $("#emlSndngYnList option:selected").val();
		var emlRcptnAgreYn = $("input[name='emlRcptnAgreYn']:checked").val();
		$("#emlSndngYn").val(emlSndngYn);
		
		if(emlRcptnAgreYn=="N"){
			console.log("메일 수신동의를 거부한 상태입니다.");
			$("#emlSndngYnList").val("N").attr("selected", "selected");
		}
	}
	
</script>
</body>
</html>