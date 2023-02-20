<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP - 사용자 정보</title>
<style>
	.contents{
		line-height: 14px;
		width: 45%;
		margin: 0 auto;
		padding: 32px 29px 32px;
		background: #fff;
	}
	.btnWrap{
		width: 100%;
		padding: 20px 0 20px;
		text-align: center;
	}
	.btn-sm{
	width: 15%;
	}
label > input[type=file] { display: none; }
.borad-file > a { display: inline-block; width: 100%; padding: 5px 0; }
.borad-file > a > span { display: inline-block; width: 50%; }
.borad-file > a > span:last-child { text-align: center; width: 100%; }

.divUserApply_Ele { height: 95px; }
.divUserApply_Ele p span { color:#eb4b3d; font-weight: 900; }
.divUserApply_Ele_60 { height:60%; }
.divUserApply_Ele_60 span span { color:#eb4b3d; font-weight: 900; padding-right: 5px;}
.divUserApply_Ele_70 { height:70%; }
.divUserApply_Ele_70_div { padding-top: 10px; }
.divUserApply_Ele_70 p { padding-bottom: 0px; }
.divUserApply_Ele_70 p span { color:#eb4b3d; font-weight: 900; }
#emailLabel1 {padding-right: 20px; padding-left: 20px;}
#emailLabel2 {padding-right: 50px;}
#emailLabel3 {color:#eb4b3d; font-weight: 900;}
.divUserApply_Ele_File { height:150%; }
.divUserApply_Ele_File #add-file-area { height:48px; }
.divUserApply_Ele_Date { padding-top: 20px; }
.divUserApply_Ele_Date p { padding-top: 0px; }
.divUserApply_Ele_Date p label { color:#eb4b3d; font-weight: 900; }

.divUserApply_Ele_TermsBox1{font-weight: 900; }
.divUserApply_Ele_TermsBox1 p span{ color:#eb4b3d; font-weight: 900; }
.divUserApply_Ele_TermsBox2 {padding-top: 10px;}
.divUserApply_Ele_TermsBox2 p span{color:#eb4b3d; }
.agree_Label1{padding-left: 20px;}
.agree_Label2{padding-left: 20px;}
.agree_Label3{color:#eb4b3d; font-weight: 900;}

.purpose_Label1{padding-left:20px;}
.purpose_Label2{padding-left:20px;}
.purpose_Label3{padding-left:20px;}
.purpose_Label4{padding-left:20px;}
/* .purpose_Label5{position:fixed; width: 100%;} */
.purpose_Label5{position:unset; width: 100%;}
.purpose_Label5 input[type="text"]{width:100%;}
</style>
</head>

<body>
	<table class="offcanvas-table">
		<tr>
			<td class="col align-top p-0">
				
				<div class="mainContents"><!-- mainContents -->
					<div class="navbar p-0 pb-3">	<!-- navbar -->
						<div class="contentsTitle">
							<i class="icon-file-roll"> </i>회원정보 변경
							<span class="normal-text ml-2">안전한 패스워드로 내정보를 보호하세요</span>
						</div>
						<!-- <div class="btn-toolbar mt-3" role="toolbar" aria-label="Toolbar with button groups">
							<button type="button"
								class="btn btn-vestap btn-blue w-auto pl-2 pr-2 float-right" onclick="fn_openWrite();return false;">
								<i class="icon-add-bold"></i>정보 수정
							</button>
						</div> -->
					</div>
					<!-- //navbar -->
					<div class="contents">
						<form name="userInfo" id="userInfo" action="/member/base/user/info/updateAction.do?${_csrf.parameterName}=${_csrf.token}" autocomplete="off" method="post" enctype="multipart/form-data">
						<input type="hidden" name="userListLimit" id="userListLimit" value="${userListLimit}" />
						<input type="hidden" name="userListPage" id="userListPage" value="${userListPage}" />
						<input type="hidden" name="recordCountPerPage" id="recordCountPerPage" value="" />
						
						<c:set var="userDistFull" value="${userInfo.userDist}"/>
						<c:set var="userDistSido" value="${fn:substring(userDistFull, 0, 2)}"/>
						<c:set var="userDistSigungu" value="${fn:substringAfter(userDistFull, userDistSido)}"/>
						
						<c:set var="userDistrictFull" value="${userInfo.userDistrict}"/>
						<c:set var="userDistrictSido" value="${fn:substring(userDistrictFull, 0, 2)}"/>
						<c:set var="userDistrictSigungu" value="${fn:substringAfter(userDistrictFull, userDistrictSido)}"/>
						
						<input type="hidden" name="userDist" id="userDist" value="${userInfo.userDist}" />
						<input type="hidden" name="userDistFull" id="userDistFull" value="${userInfo.userDist}" />
						<input type="hidden" name="userDistSido" id="userDistSido" value="${userDistSido}" />
						<input type="hidden" name="userDistSigungu" id="userDistSigungu" value="${userDistSigungu}" />
						
						<input type="hidden" name="userDistrict" id="userDistrict" value="" />
						<input type="hidden" name="userDistrictFull" id="userDistrictFull" value="${userDistrictFull}" />
						<input type="hidden" name="userDistrictSido" id="userDistrictSido" value="${userDistrictSido}" />
						<input type="hidden" name="userDistrictSigungu" id="userDistrictSigungu" value="${userDistrictSigungu}" />
						
						<input type="hidden" name="useYnBefore" id="useYnBefore" value="${userInfo.useYn}" />
						<input type="hidden" name="aprvDt" id="aprvDt" value="${userInfo.aprvDt}" />
						<input type="hidden" name="adminChk" id="adminChk" value="${adminChk}" />
						
						<ul class="list-group list-group-flush">
<!-- 							<li class="list-group-item"> -->
								<!-- <button type="button" class="btn btn-sm btn-blue float-left" onclick="fn_init();return false;">
									<i class="icon-refresh-edit"></i>초기화
								</button>
								<button type="button" class="btn btn-sm btn-blue float-right" onclick="fn_updateUserInfoChk();return false;">
									<i class="icon-user-add"></i>사용자 변경
								</button> -->
<!-- 							</li> -->
							<li class="list-group-item p-4">
								<div class="divUserApply_Ele">
									<p><span>*</span>    아이디</p>
									<input type="text" class="form-control float-left" name="userId" id="userId" readonly="readonly" value="${userInfo.userId }" onchange="fn_idReset(); return false;" placeholder="아이디를 입력해 주세요.">
									<!--
									<button type="button"
										class="btn btn-vestap btn-outline-blue w-25 mb-3" onclick="fn_userIdCheck();return false;">
										<i class="icon-search"></i>중복검사
									</button>
									-->
								</div>
								
								<div class="divUserApply_Ele">
									<p><span>*</span>    패스워드</p>
									<input type="text" class="form-control float-left <sec:authorize access="hasAnyRole('ROLE_ADMIN')">w-65</sec:authorize>" readonly="readonly" name="userPw" id="userPw" data="N" placeholder="새 비밀번호 필요시 입력"  maxlength="20" value="" readOnly>
									<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
										<button type="button" class="btn btn-vestap btn-outline-blue w-35" onclick="fn_newUserPw(); return false;">
											새 패스워드 발급
										</button>
									</sec:authorize>
								
<!-- 								<p>패스워드 확인</p> -->
<!-- 								<input type="password" class="form-control float-left" name="userPwChk" id="userPwChk" placeholder="새 패스워드 확인"> -->
									<p class="fn_userPw_onKeyUp_effect" style="display: inline-block;"></p>
								</div>
								
								<div class="divUserApply_Ele">
									<p><span>*</span>    이메일</p>
									<input type="text" class="form-control float-left" name="userEmail" id="userEmail" value="${userInfo.userEmail }" placeholder="이메일을 입력해주세요." maxlength="30" value="" onKeyUp="fn_userEmail_onKeyUp();">
									<p class="fn_userEmail_onKeyUp_effect" style="display: inline-block;"></p>
								</div>
								
								<div class="divUserApply_Ele_70">
									<span>*</span>    이메일 수신동의 여부
									<label id="emailLabel1"><input type='radio' name='emlRcptnAgreYn' value = 'Y' <c:if test="${userInfo.emlRcptnAgreYn eq 'Y'}">checked="checked"</c:if>/>동의</label>
									<label id="emailLabel2"><input type='radio' name='emlRcptnAgreYn' value = 'N' <c:if test="${userInfo.emlRcptnAgreYn == null || userInfo.emlRcptnAgreYn eq 'N'}">checked="checked"</c:if>/>미동의 </label>
									<label id="emailLabel3">※ 동의하지 않을 경우, 신청결과를 메일로 확인하실 수 없습니다.</label>
								</div>
								
								<div class="divUserApply_Ele">
									<p><span>*</span>    이름</p>
									<input type="text" class="form-control float-left" name="userNm" id="userNm" value="${userInfo.userNm }" placeholder="이름을 입력해 주세요." maxlength="30" value="" onKeyUp="fn_userNm_onKeyUp();">
									<p class="fn_userNm_onKeyUp_effect" style="display: inline-block;"></p>
								</div>
								
								<div class="divUserApply_Ele">
									<p><span>*</span>    소속</p>
									<input type="text" class="form-control float-left" name="userOrgNm" id="userOrgNm" value="${userInfo.userOrgNm }" placeholder="소속을 입력해주세요." maxlength="50" value="" onKeyUp="fn_userOrgNm_onKeyUp();">
									<p class="fn_userOrgNm_onKeyUp_effect" style="display: inline-block;"></p>
								</div>
								
								<div class="divUserApply_Ele">
									<input type="hidden" name="userGovYn" id="userGovYn" />
									<p><span>*</span>    접속권한</p>
									
									<!-- 공무원이 아닌 민간인 사람이 자신의 신분을 변경할 수 없게 제한하기 위해 조건 -->
									<sec:authorize access="hasAnyRole('ROLE_WIDE', 'ROLE_BASE')">
										<c:choose>
											<c:when test="${userInfo.userGovYn eq 'Y'}">
												<select class="form-control" id="userGovYnList">
													<option value="Y" selected="selected">공무원</option>
												</select>
											</c:when>
											
											<c:otherwise>
												<select class="form-control" id="userGovYnList">
													<option value="N" selected="selected">민간</option>
												</select>
											</c:otherwise>
										</c:choose>
									</sec:authorize>
									
									<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
										<div class="nav navbar p-0 mt-4 justify-content-center">
											<select class="form-control" id="userGovYnList" onChange="fn_userGovYnList_onChange();">
												<option value="N" <c:if test="${userInfo.userGovYn eq 'N'}">selected="selected"</c:if>>민간</option>
												<option value="Y" <c:if test="${userInfo.userGovYn eq 'Y'}">selected="selected"</c:if>>공무원</option>
											</select>
										</div>
									</sec:authorize>
								</div>
								
								<div class="divUserApply_Ele">
									<p><span>*</span>    지자체구분</p>
									<div class="select_box mb-3">
										<select class="form-control" name="userAuth" id="userAuth">
											<option value="W" <c:if test="${userInfo.userAuth eq 'W'}">selected="selected"</c:if>>시도 사용자</option>
											<option value="B" <c:if test="${userInfo.userAuth eq 'B'}">selected="selected"</c:if>>시군구 사용자</option>
										</select>
									</div>
								</div>
								
								<div class="divUserApply_Ele">
									<p><span>*</span>    행정구역</p>
									<div class="row mb-3">
										<div class="col pr-2">
											<div class=" select_box">
												<select class="form-control" name="sido" id="sido">
													<c:forEach var="i" items="${sidoList }">
														<option value='${i.districtCd}' <c:if test="${i.districtCd == userDistSido}"> selected</c:if>> ${i.districtNm}</option>
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
										<label class="agree_Label1"><input type="radio" name="securityAgreeYn" value="Y" <c:if test="${userInfo.securityAgreeYn eq 'Y'}">checked="checked"</c:if>/>동의</label>
										<label class="agree_Label2"><input type="radio" name="securityAgreeYn" value="N" <c:if test="${userInfo.securityAgreeYn eq 'N'}">checked="checked"</c:if>/>미동의</label>
										<label class="agree_Label3">※ 동의하지 않을 경우, 신청결과를 메일로 확인하실 수 없습니다.</label>
									</p>
								</div>
								
								<div class="divUserApply_Ele_70">
									<p><span>*</span>    사용목적
										<label class="purpose_Label1"><input type="radio" name="purposeUseCd" value="jy" <c:if test="${userInfo.purposeUseCd eq 'jy'}">checked="checked"</c:if> />정책연구</label>
										<label class="purpose_Label2"><input type="radio" name="purposeUseCd" value="hn" <c:if test="${userInfo.purposeUseCd eq 'hn'}">checked="checked"</c:if> />학술논문</label>
										<label class="purpose_Label3"><input type="radio" name="purposeUseCd" value="jt" <c:if test="${userInfo.purposeUseCd eq 'jt'}">checked="checked"</c:if> />정보탐색</label>
										<label class="purpose_Label4"><input type="radio" name="purposeUseCd" value="et" <c:if test="${userInfo.purposeUseCd eq 'et'}">checked="checked"</c:if> />기타</label>
										<label class="purpose_Label5">
											<input type="text"  name="purposeUseNm" width="100%" placeholder="연구책임자, 연도, 연구과제명, 발행처, 발행년도, 보고서명" maxlength="100" title="연구책임자, 연도, 연구과제명, 발행처, 발행년도, 보고서명" value="${userInfo.purposeUseNm}"/>
											<span>※ 관련 연구과제가 있는 경우 : 연구책임자, 연도, 연구과제명, 발행처를 기재    || 관련 연구과제가 없는 경우 : 발행처, 발행년도, 보고서명을 기재</span>
										</label>
									</p>
								</div>
										
								<div class="divUserApply_Ele">
								<p style="padding-top: 0px;"><span>*</span>사용상태</p>
								<input type="hidden" name="useYn" id="useYn" />
								<sec:authorize access="hasAnyRole('ROLE_WIDE', 'ROLE_BASE')">
									<c:choose>
										<c:when test="${userInfo.useYn eq 'Y'}">
											<select class="form-control" id="useYnList">
												<option value="Y" <c:if test="${userInfo.useYn eq 'Y'}">selected="selected"</c:if>>사용</option>
											</select>
										</c:when>
										
										<c:otherwise>
											<select class="form-control" id="useYnList">
												<option value="N" <c:if test="${userInfo.useYn eq 'N'}">selected="selected"</c:if>>미사용</option>
											</select>
										</c:otherwise>
									</c:choose>
								</sec:authorize>
								
								<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
									<div class="nav navbar p-0 mt-4 justify-content-center">
										<select class="form-control" id="useYnList">
											<option value="D" <c:if test="${userInfo.useYn eq 'D'}">selected="selected"</c:if>>대기</option>
											<option value="N" <c:if test="${userInfo.useYn eq 'N'}">selected="selected"</c:if>>미사용</option>
											<option value="Y" <c:if test="${userInfo.useYn eq 'Y'}">selected="selected"</c:if>>사용</option>
										</select>
									</div>
								</sec:authorize>
								</div>
								
								<div class="divUserApply_Ele_70">
									<input type="hidden" name="emlSndngYn" id="emlSndngYn" value="" />
									<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
										<div class="divUserApply_Ele_70_div">
											<select class="form-control" id="emlSndngYnList" onChange="fn_emlSndngYnList(); return false;">
												<option value="N" <c:if test="${userInfo.emlRcptnAgreYn == null || userInfo.emlRcptnAgreYn eq 'N'}">selected="selected"</c:if>>메일 미발송</option>
												<option value="Y" <c:if test="${userInfo.emlRcptnAgreYn eq 'Y'}">selected="selected"</c:if>>메일 발송</option>
											</select>
										</div>
									</sec:authorize>
								</div>
								
								<div class="borad-file" style="padding-top: 20px;">
									<i class="icon-file-attach"></i>공문
									<input type="hidden" name="fileIdx" value="${userInfo.fileIdx }">
									<input type="hidden" name="orgFileNm" value="${userInfo.orgFileNm}" >
									<input type="hidden" name="stdFileNm" value="${userInfo.stdFileNm}" >
									
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
										
										<div class="card-body p-2" id="std-file-area">
											<div class="row add-file-element">
												<div class="col-10 p-1 pl-4">
													<c:choose>
														<c:when test="${userInfo.orgFileNm == null || userInfo.orgFileNm eq ''}">
															<span>첨부파일이 존재하지 않습니다.</span>
														</c:when>
														<c:otherwise>
															<a href="/member/base/user/info/download.do?userId=${userInfo.userId}&orgFileNm=${userInfo.orgFileNm}&stdFileNm=${userInfo.stdFileNm}&fileIdx=${userInfo.fileIdx}">
																<span><i class="icon-file-attach"></i>${userInfo.orgFileNm}</span>
															</a>
															<span>
																<a href="#fn_fileAtchDelete" onClick="javascript:fn_fileAtchDelete();" class="pt-1 modify-file" style="display: inline-block;">
																	<i class="icon-close-bold"></i>
																</a>
															</span>
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</div>
										<div class="card-body p-2" id="add-file-area"></div>
									</div>
								</div>
								
								<div class="divUserApply_Ele_Date">
									<p style="padding-top: 0px;">
										최초승인일 : 
										<c:choose>
											<c:when test="${userInfo.aprvDt == null || userInfo.aprvDt eq ''}"><label id="Date_Label">승인이력 없음</label></c:when>
											<c:otherwise><label id="Date_Label">${userInfo.aprvDt}</label></c:otherwise>
										</c:choose>
									</p>
									<p style="padding-top: 0px;">
										최초만료일  :
										<c:choose>
											<c:when test="${userInfo.userExpire == null || userInfo.userExpire eq ''}"><label id="Date_Label">승인이력 없음</label></c:when>
											<c:otherwise><label id="Date_Label">${userInfo.userExpire}</label></c:otherwise>
										</c:choose>
									</p>
								</div>
							</li>
						</ul>
						
						<div class='btnWrap' style="text-align: center; padding-top: 0px;">
							<button type="button" class="btn btn-sm btn-blue" data="ethree" onclick="fn_updateUserInfoChk();return false;">
								<i class="icon-file-write-edit"></i>저 장
							</button>
							<button type="button" class="btn btn-sm btn-red" data="ethree" onclick="fn_moveList();return false;">
								<i class="icon-file-remove"></i>취 소
							</button>
						</div>
						</form>
					</div> <!-- //contents -->
				</div> <!-- //mainContents -->

			</td>
		</tr>
	</table>
	
	<script>
	$(document).ready(function() {
		
		$("input[name='emlRcptnAgreYn']:radio").change(function () {
	        //라디오 버튼 값을 가져온다.
	        var emlRcptnAgreYn = this.value;
	        console.log(emlRcptnAgreYn);
	        if(emlRcptnAgreYn=="N"){
	        	$("#emlSndngYnList").val("N").attr("selected", "selected");
	        }
		});
		
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
// 		fn_rd_purposeUseNm();
		var userDist = '${userInfo.userDist}';
		var userDistSido ="${userDistSido}";
		var userDistSigungu = $("#userDistSigungu").val();
		
		fn_rd_userAuth();
		if (userDist.length > 2) {
			//시군구 사용자
			var sidoCode = userDist.substring(0, 2);
			$("#sido").val(sidoCode);
			fn_sigunguList("update", userDistSigungu);

		} else {
			//시도 사용자
			var sidoCode = userDist;
			$("#sido").val(sidoCode);
		}
		
		if(userDistSigungu !=""){
			$("#sigungu").val(userDistSigungu);
		}
		
		//시도 목록 변경 시 시군구 목록 불러오기
		$("#sido").change(function() {
			fn_sigunguList("update", userDistSigungu);
		});

		$("#userAuth").change(function() {
			var userAuth = $("#userAuth option:selected").val();
			if(userAuth =="W") {
				$('#sigungu').attr('style', "display:none;");
			} else {
				$('#sigungu').attr('style', "display:show;");
			}
			fn_sigunguList("update", userDistSigungu);
		});
		/* 
		$("#userPw").keyup(function() {
			var input = $(this).val();
			var inputLength = $(this).val().length;
			var num = input.search(/[0-9]/g);
			var eng = input.search(/[a-z]/ig);
			var spe = input.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

			if (inputLength < 9 || inputLength > 20) {
				$(".fn_userPw_onKeyUp_effect").css("display", "block");
				$(".fn_userPw_onKeyUp_effect").css("color", "#ea4e43");
				$(".fn_userPw_onKeyUp_effect").text("패스워드는 8자~20자여야 합니다.");
			} else if (input.search(/\s/) != -1) {
				$(".fn_userPw_onKeyUp_effect").text("패스워드는 공백이 없어야 합니다.");
			}
			 else if (num < 0 || eng < 0 || spe < 0) {
				$(".fn_userPw_onKeyUp_effect").text("영문, 숫자, 특수문자를 혼합하여 입력해주세요.");
			} else {
				$(".fn_userPw_onKeyUp_effect").css("color", "#549b08");
				$(".fn_userPw_onKeyUp_effect").text("패스워드를 사용하셔도 좋습니다.");
			}
		});
 		*/
	});
	
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
			$("#userEmail").val("${userInfo.userEmail }");
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
			$("#userNm").val("${userInfo.userNm }");
		}
    	
    	if ( userNm!="" && engChk.test(userNm) == true) {
			$(".fn_userNm_onKeyUp_effect").css("display", "show");
			$(".fn_userNm_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userNm_onKeyUp_effect").text("이름에 영문이 존재합니다.");
			$("#userNm").val("${userInfo.userNm }");
		}
    	
    	if ( userNm!="" && spcChk.test(userNm) == true) {
			$(".fn_userNm_onKeyUp_effect").css("display", "show");
			$(".fn_userNm_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userNm_onKeyUp_effect").text("이름에 특수문자가 존재합니다.");
			$("#userNm").val("${userInfo.userNm }");
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
			$("#userOrgNm").val("${userInfo.userOrgNm }");
		}
    	
    	if ( userOrgNm!="" && spcChk.test(userOrgNm) == true) {
			$(".fn_userOrgNm_onKeyUp_effect").css("display", "show");
			$(".fn_userOrgNm_onKeyUp_effect").css("color", "#ea4e43");
			$(".fn_userOrgNm_onKeyUp_effect").text("소속에 특수문자가 존재합니다.");
			$("#userOrgNm").val("${userInfo.userOrgNm }");
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
	
	function fn_rd_userAuth(){
		var userAuth = $("#userAuth option:selected").val();
		if(userAuth =="W") {
			$('#sigungu').attr('style', "display:none;");
		} else {
			$('#sigungu').attr('style', "display:show;");
		}
		fn_sigunguList("update", userDistSigungu);
	}
	 
	function fn_sigunguList(mode,userDistSigungu) {
		var userAuth, sidoCode, select;
		var userAuth = $("#userAuth option:selected").val();
// 		var userDistSigungu = userDistSigungu;
		if (mode == "update") {
			sidoCode = $("#sido option:selected").val();
			select = "sigungu";
		}
		
// 		console.log("userDist["+$("#userDist"));
		
		var dataSet = {
			"sidoCode" : sidoCode
		};

		if (userAuth == 'A' || userAuth == 'B') {
			$.ajax({
				url : '/member/base/user/info/sigunguList.do',
				dataType : 'json',
				type : 'get',
				data : dataSet,
				success : function(result) {
					$("#" + select).empty();
					var list = result.list;
					if(userAuth =="W") {
						$("#sigungu").css("display", "none");
					} else {
						$("#sigungu").css("display", "show");
					}

					for ( var i in list) {
						var html;
// 						html = "<option value='" + list[i].districtCd + "'>" + list[i].districtNm + "</option>";
						if( userDistSigungu==list[i].districtCd.substring(2,5)){
							html = "<option value='" + list[i].districtCd.substring(2,5) + "' selected>" + list[i].districtNm + "</option>";
						} else {
							html = "<option value='" + list[i].districtCd.substring(2,5) + "'>" + list[i].districtNm + "</option>";
						}
						$("#" + select).append(html);
					}
					
					if (select=="sigungu"){
						$('#sigungu').attr('style', "display:show;");
					} else {
						$('#sigungu').attr('style', "display:none;");
					}
					
				}
			});
		} else {
			$("#" + select).empty();
			$("#" + select).css("display", "none");
		}
	}
	
	function fn_checkEnroll() {
		var userId = $("#userId").val();
		var userIdCheck = $("#userId").attr("data");
		var userNm = $("#userNm").val();
		
		var userPw = $("#userPw").val();
		var userPwChk = $("#userPwChk").val();
		var userPwCheck = $("#userPw").attr("data");
		
		var userAuth = $("#userAuth option:selected").val();

		var msg = '';
		var type = 'warning';
		if (userId == null || userId == '') {
			msg = '아이디를 입력해 주세요.';
		} else if (userNm == null || userNm == '') {
			msg = '사용자 이름를 입력해 주세요.';
// 		}
// 		else if (userPw !=undefined) {
// 			 if (userPw != userPwChk) {
// 			 	msg = '패스워드를 확인해주세요.';
// 			 }
		} else if (userPw =="") {
			return true;
		} else {
			return true;
		}
		
		if(msg !=""){
			fn_alert("경고", msg, type);
			return false;
		} else {
			return true;
		}
	}
	
	function fn_updateUserInfoChk() {
		if (fn_checkEnroll()) {
			
			var userPw = $("#userPw").val();
			var userPwChk = $("#userPwChk").val();

			if(userPw !=""){
				
				var input = userPw;
				var inputLength = userPw.length;
				var num = input.search(/[0-9]/g);
				var eng = input.search(/[a-z]/ig);
				var spe = input.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
				var type = 'warning';
				var msg = "";
				
// 				if (userPw != userPwChk) {
// 	 				msg = '패스워드를 확인 해주십시오.';
// 				} else if (inputLength < 9 || inputLength > 20) {
// 					msg = "패스워드는 8자~20자여야 합니다.";
// 				} else if (input.search(/\s/) != -1) {
// 					msg = "패스워드는 공백이 없어야 합니다.";
// 				} else if (num < 0 || eng < 0 || spe < 0) {
// 					msg = "영문, 숫자, 특수문자를 혼합하여 입력해주세요.";
// 				}
				
				if(msg !="") {
					fn_alert("경고", msg, type);
					return false;
				}
			}
			fn_updateAction();
		}
	}

	function fn_updateAction(header, board, idx) {
		var userGovYn  = $("#userGovYnList option:selected").val();
		var useYn      = $("#useYnList option:selected").val();
		var emlSndngYn = $("#emlSndngYnList option:selected").val();
		var emlRcptnAgreYn = $("input[name='emlRcptnAgreYn']:checked").val();
		
		$("#userGovYn").val(userGovYn);
		$("#useYn").val(useYn);
		$("#emlSndngYn").val(emlSndngYn);
		$("#emlRcptnAgreYn").val(emlRcptnAgreYn);
		
		var selectedSido     = $("#sido").val();
		var selectedSigungu  = $("#sigungu").val();
		var selectedUserAuth = $("#userAuth").val();
		
		if (selectedUserAuth =="W"){
			$("#userDist").val(selectedSido);
		} else {
			$("#userDist").val(selectedSido+""+selectedSigungu);
		}
		
 		console.log("userDist:["+$("#userDist").val()); 
		
		var agent = navigator.userAgent.toLowerCase();

		if ((navigator.appName == "Netscape" && navigator.userAgent.search("Trident") != -1) || (agent.indexOf("msie") != -1)) {

			if (confirm("[확인] 사용자 정보를 수정 하시겠습니까?")) {
				//location.href= header + '/board/' + board + '/updateAction.do';
				//location.href= '/member/base/user/info/updateAction.do';
				$("#userInfo").submit();
			}

		} else {
			swal({
				text : "사용자 정보를 수정 하시겠습니까?",
				type : 'warning',
				showCancelButton : true,
				confirmButtonColor : '#3085d6',
				cancelButtonColor : '#d33',
				confirmButtonText : '확인',
				cancelButtonText : '취소'
			}).then(function(result) {
				if (result.value) {
					$("#userInfo").submit();
				}
			});
		}
	}
	
	function fn_newUserPw() {
		var url = '/admin/user/management/pwChange.do';
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
	
	function fn_fileAtchDelete(){
		$("input:hidden[name=matchName]").remove();
		$("#add-file-area").empty();
		$("#add-file").val("");
		$("#add-file-area > .add-file-element").remove();
		$("#std-file-area > .add-file-element").remove();
	}
	
	function fn_emlSndngYnList(){
		var emlSndngYn = $("#emlSndngYnList option:selected").val();
		var emlRcptnAgreYn = $("input[name='emlRcptnAgreYn']:checked").val();
		
		if(emlRcptnAgreYn=="N"){
			console.log("메일 수신동의를 거부한 상태입니다.");
			$("#emlSndngYnList").val("N").attr("selected", "selected");
		}
	}
	
	function fn_moveList(){
		var adminChk = document.userInfo.adminChk.value;
		if(adminChk ==false || adminChk =="false"){
			document.userInfo.action = "<c:url value='/member/base/main.do'/>";
		} else {
			document.userInfo.action = "<c:url value='/admin/user/management/list.do'/>";
		}
		document.userInfo.submit();
	}
	
	function fn_rd_purposeUseNm() {
		var purposeUseNm = "${userInfo.purposeUseNm}";
		console.log("fn_rd_purposeUseNm:"+purposeUseNm);
		if(purposeUseNm !=""){
			$(".purpose_Label5").css("position","unset");
		}
	}
	
	</script>
	<script>
	window.onload = function () {
		fn_rd_purposeUseNm();
	}
	
	
	</script>
</body>
</html>