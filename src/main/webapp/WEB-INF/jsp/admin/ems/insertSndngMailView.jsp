<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- <link rel="stylesheet" href="<c:url value='/css/egovframework/com/com.css' />" type="text/css"> --%>
<%-- <link href="<c:url value='/css/egovframework/com/button.css' />" rel="stylesheet" type="text/css"> --%>
<title>VESTAP - 발송메일 등록</title>
<script type="text/javascript" src="<c:url value='/js/EgovMultiFile.js'/>"></script>
<%-- <script type="text/javascript" src="<c:url value="/validator.do"/>"></script> --%>
<%-- <validator:javascript formName="admSndngMailVO" staticJavascript="false" xhtml="true" cdata="false"/> --%>

<script type="text/javaScript" language="javascript">
/* ********************************************************
 * 등록 처리 함수
 ******************************************************** */
function insertSndngMail(form) {
	if(confirm('저장하시겠습니까?')){
		/* 
		var fileStreCours = document.admSndngMailVO.egovComFileUploader.value;
		
		if (fileStreCours != null && fileStreCours != "") {
			document.admSndngMailVO.fileStreCours.value = fileStreCours;
		}
		 */
		form.action = "<c:url value='/admin/ems/insertVestapSndngMail.do'/>";
		form.submit();
	}
}
/* ********************************************************
 * 뒤로 처리 함수
 ******************************************************** */
function fnBack(){
	document.admSndngMailVO.action = "<c:url value='/admin/ems/backSndngMailRegist.do'/>";
	document.admSndngMailVO.submit();
}
/* ********************************************************
 * 초기화
 ******************************************************** */
function fnInit(){
	var closeYn = document.admSndngMailVO.closeYn.value;
	if (closeYn == "Y") {
		window.close();
	}
	document.admSndngMailVO.recptnPerson.focus();
}
</script>
</head>
<body onLoad="fnInit();">

<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>

  <form:form name="admSndngMailVO" method="post" enctype="multipart/form-data">
  	  <input type="hidden" name="posblAtchFileNumber" value="1" />	<!-- 파일첨부 개수 -->
  	  <input type="hidden" name="link" value="${resultInfo.link}" />
  	  <input type="hidden" name="closeYn" value="${closeYn}" />
  	  <input type="hidden" name="fileStreCours" value="" />
	  <table width="700">
	    <tr>
	      <td>
			<table width="700" cellpadding="8" class="table-search" border="0">
			 <tr>
			  <td width="700"class="title_left"><h1 class="title_left">
			   <img src="<c:url value='/images/egovframework/com/cmm/icon/tit_icon.gif' />" width="16" height="16" hspace="3" style="vertical-align: middle" alt="제목아이콘이미지">&nbsp;발송메일 등록</h1></td>
			 </tr>
			</table>
		  </td>
		</tr>
		<tr>
		  <td>
			<table width="700" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="받는사람, 제목, 파일첨부 및 발신 내용을 입력하여 발송메일을 등록한다.">
			<CAPTION style="display: none;">발송메일 등록</CAPTION>
			  <tr>
			    <th scope="row" width="100" height="23" class="required_text" nowrap ><label for="recptnPerson">받는사람</label><img src="<c:url value='/images/egovframework/com/cmm/icon/required.gif' />" width="15" height="15" alt="필수입력표시"></th>
			    <td width="600" nowrap>
			      <input name="recptnPerson" id="recptnPerson" type="text" size="74" value="${resultInfo.recptnPerson}"  maxlength="60" style="ime-mode: disabled;" tabindex="1" title="<spring:message code="sym.ems.receiver" />">
			    </td>
			  </tr>
			  <tr>
			    <th scope="row" width="100" height="23" class="required_text" nowrap ><label for="sj">제목</label><img src="<c:url value='/images/egovframework/com/cmm/icon/required.gif' />" width="15" height="15" alt="필수입력표시"></th>
			    <td width="600" nowrap>
			      <input name="sj" id="sj" type="text" size="74" value="${resultInfo.sj}"  maxlength="250" tabindex="2" title="<spring:message code="sym.ems.title" />">
			    </td>
			  </tr>
			  <tr>
			    <th scope="row" width="100" height="23" class="required_text"><label for="egovComFileUploader">파일첨부&nbsp;&nbsp;&nbsp;</label></th>
			    <td width="600">
			      <table width="600" cellspacing="0" cellpadding="0" border="0" align="center" class="UseTable">
				    <tr>
				      <td width="600">
				      <input name="file_1" id="egovComFileUploader" type="file" tabindex="3" title="<spring:message code="sym.log.atchFile" />" onchange="this.select(); document.getElementById('egovComFileUploader').value=document.selection.createRange().text.toString();" /></td>
				    </tr>
				    <tr>
				      <td width="600">
				        <div id="egovComFileList"></div>
				      </td>
				    </tr>
				  </table>
			    </td>
			  </tr>
			  <tr>
			    <th scope="row" width="100" height="23" class="required_text" ><label for="emailCn">발신 내용&nbsp;&nbsp;&nbsp;</label></th>
			    <td width="600">
			      <textarea id="emailCn" name="emailCn" cols="75" rows="25" tabindex="4"title="<spring:message code="sym.ems.content" />"></textarea>
			    </td>
			  </tr>
			</table>
		  </td>
		</tr>
		<tr>
		  <td>
			<table border="0" cellspacing="0" cellpadding="0" align="right">
			  <tr>
			    <td><img src="<c:url value='/images/egovframework/com/cmm/btn/bu2_left.gif' />" width="8" height="20" alt="버튼이미지"></td>
			    <td style="background-image:URL(<c:url value='/images/egovframework/com/cmm/btn/bu2_bg.gif'/>);" class="text_left" nowrap><a href="#noscript" onclick="fnBack(); return false;">뒤로</a>
			    </td>
			    <td><img src="<c:url value='/images/egovframework/com/cmm/btn/bu2_right.gif' />" width="8" height="20" alt=""></td>
			    <td>&nbsp;&nbsp;</td>
			    <td><span class="button"><input type="submit" value="등록" onclick="insertSndngMail(document.admSndngMailVO); return false;"></span></td>

			  </tr>
			</table>
		  </td>
		</tr>
	  </table>
  </form:form>
  <script type="text/javascript">
	   var maxFileNum = document.admSndngMailVO.posblAtchFileNumber.value;
	   if(maxFileNum==null || maxFileNum==""){
	       maxFileNum = 3;
	   }
	   var multi_selector = new MultiSelector( document.getElementById( 'egovComFileList' ), maxFileNum );
	   multi_selector.addElement( document.getElementById( 'egovComFileUploader' ) );
  </script>
</body>
</html>


