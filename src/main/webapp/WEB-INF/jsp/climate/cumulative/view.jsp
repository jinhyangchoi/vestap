<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP - 기후변화 취약성 - 누적현황보고</title>
<link href="/resources/billboard/billboard.css" rel="stylesheet">
<link href="/resources/billboard/insight.css" rel="stylesheet">
<style>
.fold-menu-sensitive,
.fold-menu-adapt{
	color: #273142;
}
.fold-menu-sensitive-01,
.fold-menu-sensitive-02{
	cursor:pointer;
}
.fold-open-sensitive-01{
	cursor:pointer;
}
.fold-open-sensitive-02{
	cursor:pointer;
	display: none;
}
.fold-open-sensitive{
	overflow: auto;
	height: 220px;
}
.fold-menu-adapt-01,
.fold-menu-adapt-02{
	cursor:pointer;
}
.fold-open-adapt-01{
	cursor:pointer;
	display:none;
}
.fold-open-adapt-02{
	cursor:pointer;
	display:none;
}
.fold-open-adapt{
	overflow: auto;
	height: 220px;
}
.cumulative_main_head a {
	color: #273142;
}
.textLeft{
	text-align: left;
}
/* 
.bb svg {
	font-family: "notokr/Meiryo", sans-serif, Arial, "nanumgothic", "Dotum";
}
 */
</style>

</head>
<body>
	<table class="offcanvas-table">
		<tr>
			<td class="offcanvas-left-open">
				<!-- *********************** offcanvas-left-open *********************** -->

				<div class="card">
					<div class="card-header offcanvas-left-open-title">
						<i class="icon-file-bookmark"></i>누적현황보고<a href="javascript:void(0);" class="on-offmenu text-blue" id="menu-close"><i class="icon-arrow-caret-left offmenu-icon"></i></a>
					</div>

					<ul class="list-group list-group-flush">
						<li class="list-group-item sub-title border-top-0"><i
							class="icon-file-align"></i>지자체선택</li>
						<li class="list-group-item">

							<div class="row">
								<div class="col pr-2">

									<div class=" select_box">
										<select class="form-control" id="sidoList">
											<c:forEach var="i" items="${sidoList }">
												<option value='${i.districtCd}'
													<c:if test="${setSido eq i.districtCd}">selected</c:if>>${i.districtNm}</option>
											</c:forEach>
										</select>
									</div>

								</div>
								<div class="col pl-2">

									<div class=" select_box">
										<select class="form-control" id="sigunguList">
											<c:if test="${author eq 'W' or author eq 'A'}">
												<option value='' selected>전체</option>
											</c:if>
											<c:forEach var="i" items="${sigunguList }">
												<option value='${i.districtCd}'>${i.districtNm}</option>
											</c:forEach>
										</select>
									</div>

								</div>
							</div> <!-- //row -->

						</li>
						<li class="list-group-item sub-title border-top-0">
							<i class="icon-file-align"></i>
								누적현황 목록
							
						</li>
						<li class="list-group-item">

							<div class="row">
								<div class="col">
									<input type="text" class="form-control w-100 float-left" id="cumulative-search" placeholder="지표 검색" value="" onkeyup="fn_linkSearch();">
									<hr class="mt-5 mb-2" style="border-top:2px solid #BBBECD;">
									<div class="mt-0 fold-menu-sensitive">민감도 관련 현황 </div>
									<hr class="mt-2 mb-0" style="border-top:2px solid #BBBECD;">

									<div class="toolTipUl fold-open-sensitive">
										<hr class="m-2">
										<div class="mt-0 ml-2  fold-menu-sensitive-01">취약계층 분포 <i class="fas fa-caret-down p-1 pr-3" style="float: right;"></i></div>
										<hr class="m-2">
											<div class="toolTipUl fold-open-sensitive-01">
												<ul class="nav navbar-nav itemList" id="itemList_02_01">
													<c:forEach var="list" items="${list }">
														<c:if test="${list.sector2Id eq 'SED01'}">
															<li><a href="#" onclick="fn_itemClick($(this));return false;" data="${list.indiId }" class="toolTip ${list.indiId }">
																	${list.indiNm } </a></li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
											<%-- 현재 기타현황에 해당하는 데이터 없음. 나중에 데이터가 생기면 오픈
										<hr class=" mb-0 m-2">
											
										<div class="mt-0  pl-2 fold-menu-sensitive-02">기타 현황 <i class="fas fa-caret-down p-1 pr-3" style="float: right;"></i></div>
										<hr class=" mb-0 m-2">
											<div class="toolTipUl fold-open-sensitive-02">
												<ul class="nav navbar-nav itemList" id="itemList_02_02">
													<c:forEach var="list" items="${list }">
														<c:if test="${list.sector2Id eq 'SED02'}">
															<li><a href="#" onclick="fn_itemClick($(this));return false;" data="${list.indiId }" class="toolTip ${list.indiId } ">
																	${list.indiNm } </a></li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
											 --%>
									</div>
									<!-- //toolTipUl -->
									<hr class="mt-2 mb-2" style="border-top:2px solid #BBBECD;">
									<div class="mt-0 fold-menu-adapt">적응능력 관련 현황</div>
									<hr class="mt-2 mb-0" style="border-top:2px solid #BBBECD;">
									
									<div class="toolTipUl fold-open-adapt">
										<hr class="m-2">
										<div class="mt-0 ml-2  fold-menu-adapt-01">경제규모<i class="fas fa-caret-down p-1 pr-3" style="float: right;"></i></div>
										<hr class="m-2">
											<div class="toolTipUl fold-open-adapt-01">
												<ul class="nav navbar-nav itemList" id="itemList_03_01">
													<c:forEach var="list" items="${list }">
														<c:if test="${list.sector2Id eq 'SED03'}">
															<li><a href="#" onclick="fn_itemClick($(this));return false;" data="${list.indiId }" class="toolTip ${list.indiId }">
																	${list.indiNm } </a></li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										<hr class=" mb-0 m-2">
											
										<div class="mt-0  pl-2 fold-menu-adapt-02">인프라/대응인력<i class="fas fa-caret-down p-1 pr-3" style="float: right;"></i></div>
										<hr class=" mb-0 m-2">
											<div class="toolTipUl fold-open-adapt-02">
												<ul class="nav navbar-nav itemList" id="itemList_03_02">
													<c:forEach var="list" items="${list }">
														<c:if test="${list.sector2Id eq 'SED04'}">
															<li><a href="#" onclick="fn_itemClick($(this));return false;" data="${list.indiId }" class="toolTip ${list.indiId }">
																	${list.indiNm } </a></li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
									</div>
									
									<!-- //toolTipUl -->

									<button type="button" id="detailInfo-btn" class="btn btn-vestap btn-blue  w-100 mt-4" onclick="fn_cumulativeTotal()">
										<i class="icon-search-01"></i>세부정보 조회
									</button>

									</div><!-- //col -->
							</div> <!-- //row -->

						</li>
					</ul>
				</div> <!-- //card -->

			</td>
			<td class="col align-top p-0">
				<div class="onmenu-div d-none">
						<a href="javascript:void(0);" class="text-blue on-offmenu" id="menu-open"><i class="icon-arrow-caret-right"></i></a>
					</div>
				<div class="mainContents">
					<!-- mainContents -->
					<div class="card mb-3">
						<div class="card-header p-2">

							<div class="p-1 pl-3"><span class="_sido"></span>&nbsp;<span class="_sigungu"></span>의 누적현황 그래프</div>

						</div>
						<!-- card-header -->
						<div class="card-body p-2" style="height: 350px">
							<div style="height: 873px;">
								<div id="chart"></div>
							</div>
						</div>
						<!-- card-body -->
					</div>
					<!-- //card -->

					<div class="card mb-4">
						<div class="card-header p-2">
							<div class="p-1 pl-3">
								<span class="_sido"></span>&nbsp;<span class="_sigungu"></span>의
								누적현황 데이터
							</div>
						</div>
						<!-- card-header -->
						<div class="card-body p-2" style="height: 500px">
							<input type="hidden" id="cumulativePage" value="1">
							<input type="hidden" id="cumulativeLimit" value="1">
							<table class="table vestapTable smTable text-center" id="cumulative_main">
								<thead>
									<tr class="cumulative_main_head">
										<th>지표</th>
									</tr>
								</thead>
								<tbody  id="cumulative" class="cumulative_main_body">
									<td>지표를 선택해 주세요.</td>
								</tbody>
							</table>
							<div class="nav navbar p-2 mt-2">
								<nav class="form-inline">
										<ul class="pagination previous mb-0" >
										<!-- <li class="page-item"><a class="page-link" href="#" onclick="fn_previousTermPage('cumulative',10);return false;"><i
													class="icon-arrow-back"></i></a></li> -->
										<li class="page-item"><a class="page-link" href="#" onclick="fn_previousPage('cumulative');return false;"><i
													class="icon-arrow-caret-left"></i></a></li>
										</ul>
										<div><span class="curPage">1</span>/<span class="limitPage">1</span></div>
										<ul class="pagination next mb-0" >
											<li class="page-item"><a class="page-link" href="#" onclick="fn_nextPage('cumulative');return false;"><i
													class="icon-arrow-caret-right"></i></a></li>
											<!-- <li class="page-item"><a class="page-link" href="#" onclick="fn_nextTermPage('cumulative',10);return false;"><i
													class="icon-arrow-forward"></i></a></li> -->
										</ul>
								</nav>
								<!-- //pagination -->
								<div id="btn_download">
								<!-- 
								<button type="button" onClick="fn_datalist_download();"	class="btn btn-vestap btn-blue w-auto pl-2 pr-2">
									<i class="icon-download-disk"></i>데이터 다운로드
								</button>
								 -->
								</div>

							<!-- //nav -->
						</div>
						<!-- card-body -->
					</div>
					<!-- //card -->
				</div> <!-- //mainContents -->

			</td>
			<td class="offcanvas-right-open  active" id="offcanvas-view">
				<!-- *********************** offcanvas-right-open *********************** -->

				<div class="card active">
					<div class="card-header offcanvas-right-open-title">
						<i class="icon-file-bookmark"></i>누적현황정보
					</div>



					<ul class="list-group list-group-flush">
						<li class="list-group-item p-0 pt-2"><nav>
							<div class="nav nav-tabs nav-justified" id="nav-tab"
								role="tablist">
								<a class="nav-item nav-link" id="nav-tab1" data-toggle="tab" href="#nav1" role="tab" aria-controls="nav1" aria-selected="false">관련 취약성 정보</a> 
								<a class="nav-item nav-link active"	id="nav-tab2" data-toggle="tab" href="#nav2" role="tab" aria-controls="nav2" aria-selected="true">메타정보</a>
							</div>
							</nav>

							<div class="tab-content" id="nav-tabContent">
								<div class="tab-pane fade  p-3" id="nav1"
									role="tabpanel" aria-labelledby="nav-tab1">
									<div class="p-2">누적현황 데이터에서 지표명을 클릭하면 해당 누적현황정보가 표출됩니다.</div>
									<table
										class="table table-hover vestapTable smTable text-center">
										<thead>
											<tr>
												<th>리스크</th>
												<th>평가항목</th>
											</tr>
										</thead>
										<tbody id="cumulative-relation">
										</tbody>
									</table>
								</div>
								<div class="tab-pane fade show active p-3" id="nav2" role="tabpanel"
									aria-labelledby="nav-tab2">
									<div class="p-2">누적현황 데이터에서 지표명을 클릭하면 해당 누적현황정보가 표출됩니다.</div>
									<div class="tab-pane fade show active p-3" id="nav1"
										role="tabpanel" aria-labelledby="nav-tab1">
										<table class="table vestapTable smTable text-center">
											<tbody style="text-align: left;" id="cumulative-metaInfo">
											
											</tbody>
										</table>
									</div>
									<div class="tab-pane fade show active p-3" id="nav1"
										role="tabpanel" aria-labelledby="nav-tab1">
										<table
											class="table vestapTable smTable text-center">
											<thead  style="text-align: left;" id="cumulative-comment-head">
											
											</thead>
											<tbody style="text-align: left;" id="cumulative-comment">
											
											</tbody>
										</table>
									</div>
								</div>
								<!-- //tab-content --></li>
					</ul>
				</div> <!-- //card -->

			</td>
		</tr>
	</table>
	<input type="hidden" name="keyword" value="${keyword }">

	<script>
		$(document).ready(function(){
	        // 민감도 현황 - 취약계층 분포 지표 리스트 접펼
	        $(".fold-menu-sensitive-01").click(function(){
	            var submenu = $(".fold-open-sensitive-01");
	 
	            // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
	            if( submenu.is(":visible") ){
	                submenu.slideUp();
	                $(".fold-menu-sensitive-01 > i").removeClass("fa-caret-up");
	                $(".fold-menu-sensitive-01 > i").addClass("fa-caret-down");
	                
	            }else{
	                submenu.slideDown();
	                $(".fold-menu-sensitive-01 > i").removeClass("fa-caret-down");
	                $(".fold-menu-sensitive-01 > i").addClass("fa-caret-up");
	                
	            }
	        });
	        // 민감도 현황 - 기타현황 분포 지표 리스트 접펼
	        $(".fold-menu-sensitive-02").click(function(){
	            var submenu = $(".fold-open-sensitive-02");
	 
	            // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
	            if( submenu.is(":visible") ){
	                submenu.slideUp();
	                $(".fold-menu-sensitive-02 > i").removeClass("fa-caret-up");
	                $(".fold-menu-sensitive-02 > i").addClass("fa-caret-down");
	                
	            }else{
	                submenu.slideDown();
	                $(".fold-menu-sensitive-02 > i").removeClass("fa-caret-down");
	                $(".fold-menu-sensitive-02 > i").addClass("fa-caret-up");
	                
	            }
	        });
		     // 적응능력 현황 지표 리스트 접펼
	        $(".fold-menu-adapt-01").click(function(){
	            var submenu = $(".fold-open-adapt-01");
	 
	            // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
	            if( submenu.is(":visible") ){
	                submenu.slideUp();
	                $(".fold-menu-adapt-01 > i").removeClass("fa-caret-up");
	                $(".fold-menu-adapt-01 > i").addClass("fa-caret-down");
	                
	            }else{
	                submenu.slideDown();
	                $(".fold-menu-adapt-01 > i").removeClass("fa-caret-down");
	                $(".fold-menu-adapt-01 > i").addClass("fa-caret-up");
	                
	                //이때 i 의 아이콘을 바꿔줘야해 !! 
	            }
	        });
	        // 적응능력 현황 지표 리스트 접펼
	        $(".fold-menu-adapt-02").click(function(){
	            var submenu = $(".fold-open-adapt-02");
	 
	            // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
	            if( submenu.is(":visible") ){
	                submenu.slideUp();
	                $(".fold-menu-adapt-02 > i").removeClass("fa-caret-up");
	                $(".fold-menu-adapt-02 > i").addClass("fa-caret-down");
	                
	            }else{
	                submenu.slideDown();
	                $(".fold-menu-adapt-02 > i").removeClass("fa-caret-down");
	                $(".fold-menu-adapt-02 > i").addClass("fa-caret-up");
	                
	                //이때 i 의 아이콘을 바꿔줘야해 !! 
	            }
	        });
	    });
		$(document).ready(function() {

			//시도 목록 변경 시 시군구 목록 불러오기
			$("#sidoList").change(function() {
				fn_sigunguList($("#sidoList option:selected").val());
			});

			$("._sido").text($("#sidoList option:selected").text());
			$("._sigungu").text($("#sigunguList option:selected").text());
		});
		
		var selectedIndi = [];	//선택한 지표를 가지고 있을 배열

		//선택한 지표를 찾기 위한 코드
		function fn_findIndi(id){
			var idx = this.selectedIndi.indexOf(id);
			if(idx<0){
				return false;
			}else{
				return true;
			}
		}
		//지표를 선택하면 배열에 저장
		function fn_getIndi(index){
			if(this.selectedIndi.length>0){
				return this.selectedIndi[index];
			}
			return null;
		}
		//지표 선택된 개수를 세서 오류 표출
		function fn_pushIndi(id){
			if(this.selectedIndi.length>1){
				
				$("."+this.selectedIndi[0]).removeClass("active");
				
				this.selectedIndi.splice(0,1);
				this.selectedIndi.push(id);
				
				return true;
			}else{
				
				this.selectedIndi.push(id);
				return true;
			}
		}

		//지표 선택을 취소하면 배열에서 제거
		function fn_removeIndi(id){
			var idx = this.selectedIndi.indexOf(id);
			this.selectedIndi.splice(idx,1);
		}		
		
		//시도 목록에 따른 시군구 목록 변경
		function fn_sigunguList(sidoCode) {
			var userDist = $("#userDist").val();
			
			var dataSet = {
				"sidoCode" : sidoCode
			};
			$.ajax({
				url : '/member/base/climate/cumulative/sigunguList.do',
				dataType : 'json',
				type : 'get',
				data : dataSet,
				success : function(result) {
					$("#sigunguList").empty();
					var list = result.list;
					
					<c:if test="${author eq 'W' or author eq 'A'}">
					$("#sigunguList").append(
							"<option value='' selected>전체</option>");
					</c:if>
					for ( var i in list) {
						if (list[i].districtCd == userDist) {
							$("#sigunguList").append(
									"<option value='"+list[i].districtCd+"' selected>"
											+ list[i].districtNm + "</option>");
						} else {
							$("#sigunguList").append(
									"<option value='"+list[i].districtCd+"'>"
											+ list[i].districtNm + "</option>");
						}
					}
				}
			});
		}
		//지표 선택시 active 그대로
		function fn_itemClick(tag) {
			var data = tag.attr('data');
			if (tag.hasClass('active')) {
				tag.removeClass('active');
				fn_removeIndi(data);
			} else {
				tag.addClass('active');
				if(!fn_pushIndi(data)){
					tag.removeClass('active');
				};
			}
		}
		//누적현황정보 - 관련 취약성정보 - 바로가기
		function fn_relationGoBtn(cumuitemId){
			window.open('/member/base/dbinfo/item/list.do?${_csrf.parameterName}=${_csrf.token}&activeOffcanvas='+cumuitemId);
		}
		//오른쪽 누적현황정보 창
		function fn_cumulativeDetail(indiId) {
			var indi_id = indiId
			var sido = $("#sidoList option:selected").val();
			var sigungu = $("#sigunguList option:selected").val();

			if(sigungu == ""){
				sigungu = sido;
			}
			var sidoCodeLng = sigungu.length;
			
			var dataSet = {
					"indiId" : indiId,
					"sidoCode" : sigungu,
					"sidoCodeLng" : sidoCodeLng
				};
			
			$.ajax({
				url: "/member/base/climate/cumulative/cumulativeDetail.do",
				data: dataSet,
				dataType:'json',
				type:"get",
				success: function(result){
					var relation = result.relation;
					var metaInfo = result.metaInfo;
					var comment = result.comment;
					
					offcanvasOpen();
					
					$("#cumulative-metaInfo").empty();
					$("#cumulative-relation").empty();
					$("#cumulative-comment").empty();
					$("#cumulative-comment-head").empty();
					
					for(var i in relation){	
						var html = "";
						html += '<tr id="'+relation[i].itemId+'"  onClick="fn_relationGoBtn(\''+relation[i].itemId+'\');">';
						html += '<td>'+relation[i].codeKorNm+'</td>';
						html += '<td>'+relation[i].itemKorNm+'</td>';
						//html += '<td><button type="button" onClick="fn_relationGoBtn(\''+relation[i].itemId+'\');" class="btn btn-vestap btn-blue w-auto pl-2 pr-2">바로가기</button></td>';
						//location.href='/member/base/dbinfo/item/list.do?${_csrf.parameterName}=${_csrf.token}&CumuitemId=\''+relation[i].itemId+'\'
						$("#cumulative-relation").append(html);
					
					}
					for(var i in metaInfo){	
						$("#cumulative-metaInfo").append('<tr><th style="width: 100px">지표 명</th><td style="text-align: left;">'+metaInfo[i].indiNm+'</td></tr>');
						if(metaInfo[i].indiId == 'IC000018' || metaInfo[i].indiId == 'IC000032' ){
							$("#cumulative-metaInfo").append('<tr><th>단위</th><td style="text-align: left;">10억원</td></tr>');
							
						} else {
							$("#cumulative-metaInfo").append('<tr><th>단위</th><td style="text-align: left;">'+metaInfo[i].indiUnit+'</td></tr>');
							
						}
						
						$("#cumulative-metaInfo").append('<tr><th>부문</th><td style="text-align: left;">'+metaInfo[i].indiGroup+'</td></tr>');
						$("#cumulative-metaInfo").append('<tr><th>IPCC WG I</th><td style="text-align: left;">'+metaInfo[i].indiLarge+'</td></tr>');
						$("#cumulative-metaInfo").append('<tr><th>IPCC WG II</th><td style="text-align: left;">'+metaInfo[i].indiSmall+'</td></tr>');
						$("#cumulative-metaInfo").append('<tr><th>설명</th><td style="text-align: left;">'+metaInfo[i].indiConstructMeth+'</td></tr>');		/*아직 디비에 설명이 없으니까 임시로 .. 디비 들어가면 이걸 써 '+metaInfo[i].indiConstructMeth+' */
						$("#cumulative-metaInfo").append('<tr><th>상세 설명</th><td style="text-align: left;">'+metaInfo[i].indiConstructKor+'</td></tr>');
					}
					if(comment.length>0){
						$("#cumulative-comment-head").append('<tr><th>활용시 주의사항</th></tr>');
						for(var i in comment){
							$("#cumulative-comment").append('<tr><td style="text-align: left">'+comment[i].comment+'</td></tr>');
							
						}
					}
				}
			});
			
		}
		/* 엑셀 다운로드 */
		function fn_downloadCumulative(name1,name2, sido, sigungu, sidoCode) {	//name : 지표명

			var params = "getIndi1=" + name1 + "&getIndi2=" + name2
						+ "&sidoCode=" + sidoCode
						+ "&sido=" + sido + "&sigungu=" + sigungu;
			
			location.href = encodeURI("/member/base/climate/cumulative/downloadCumulative.do?" + params);
		}
		
		/* 누적현황데이터 띄우기*/
		function fn_cumulativeTotal() {
			var getIndi1;
			var getIndi2;
			var sido = $("#sidoList option:selected").val();
			var sigungu = $("#sigunguList option:selected").val();

			getIndi1 = fn_getIndi(0);
			getIndi2 = fn_getIndi(1);
			$("._sido").text($("#sidoList option:selected").text());
			$("._sigungu").text($("#sigunguList option:selected").text());

			if(sigungu == ""){
				sigungu = sido;
			}
			if(this.selectedIndi.length<1){
				
				fn_alert("경고", "지표 선택 후 조회 가능합니다.", "error");
				return false;
			}
			var dataSet = {
					"sidoCode" : sigungu,
					"getIndi1" : getIndi1,
					"getIndi2" : getIndi2
				};
			
			var page = 0;		//페이지 초기화
			var pageLimit = 8; //한페이지에 보여줄 개수
			
			$.ajax({
				url: "/member/base/climate/cumulative/cumulativeMain.do",
				data: dataSet,
				dataType:'json',
				type:"get",
				success: function(result){
					var list = result.list;
					var findNm = result.findNm;
					$(".cumulative_main_head").empty();
					$(".cumulative_main_body").empty();
					$("#btn_download").empty();
					$(".cumulative_main_head").append('<th>연도</th>');
						
						for(var i in findNm){	
							if(findNm[i].indiId == 'IC000018' || findNm[i].indiId == 'IC000032' ){
								$(".cumulative_main_head").append('<th><a href="#" class="offcanvas-select" onclick="fn_cumulativeDetail(\''+findNm[i].indiId+'\');return false;">'+findNm[i].indiNm+'&nbsp(10억원)</a></th>');
								
							} else {	
								$(".cumulative_main_head").append('<th><a href="#" class="offcanvas-select" onclick="fn_cumulativeDetail(\''+findNm[i].indiId+'\');return false;">'+findNm[i].indiNm+'&nbsp('+findNm[i].indiUnit+')</a></th>');
								
							}
							
						}
						if(list.length == 0){
							var html2='';
							html2='<tr><td colspan="3">해당 데이터가 없습니다.</td></ tr>';
							$(".cumulative_main_body").append(html2);
						} 
						for(var i in list){
								var html='';
								
								if(i%pageLimit==0) page++;
								
								if(page==1){
									html = '<tr class="pg'+page+'" style="display:table-row">';
								}else{
									html = '<tr class="pg'+page+'" style="display:none">';
								}

								if(getIndi1=='IC000018'){
									var indiFirst = fn_addComma(Math.round(list[i].indiFirst / 1000));
									
									if(indiFirst == null || indiFirst=="" || indiFirst == 'NaN'){
										html+='<td >'+list[i].indiYear+'</td>'+'<td>-</td>';
									} else {
										html+='<td >'+list[i].indiYear+'</td>'+'<td>'+indiFirst+'</td>';
										
									}

									if(getIndi2 != null){
										
										if(getIndi2=='IC000032'){
											var indiSecond = fn_addComma(Math.round(list[i].indiSecond / 1000));
										} else {
											var indiSecond = fn_addComma(list[i].indiSecond);
										}
										
										if(indiSecond == null || indiSecond=="" || indiSecond == 'NaN'){
											 html+='<td>-</td>';
										} else {
											 html+='<td>'+indiSecond+'</td>';
											
										}
										
									}

								} else if(getIndi1=='IC000032'){
									var indiFirst = fn_addComma(Math.round(list[i].indiFirst / 1000));
									
									if(indiFirst == null || indiFirst=="" || indiFirst == 'NaN'){
										html+='<td >'+list[i].indiYear+'</td>'+'<td>-</td>';
									} else {
										html+='<td >'+list[i].indiYear+'</td>'+'<td>'+indiFirst+'</td>';
										
									}
									
									if(getIndi2 != null){
										if(getIndi2=='IC000018'){
											var indiSecond = fn_addComma(Math.round(list[i].indiSecond / 1000));
										} else {
											var indiSecond = fn_addComma(list[i].indiSecond);
										}

										if(indiSecond == null || indiSecond=="" || indiSecond == 'NaN'){
											 html+='<td>-</td>';
										} else {
											 html+='<td>'+indiSecond+'</td>';
											
										}
									}

								} else {
									var indiFirst = fn_addComma(list[i].indiFirst);

									if(indiFirst == null || indiFirst=="" || indiFirst == 'NaN'){
										html+='<td >'+list[i].indiYear+'</td>'+'<td>-</td>';
									} else {
										html+='<td >'+list[i].indiYear+'</td>'+'<td>'+indiFirst+'</td>';
										
									}
									
									if(getIndi2 != null){
										if(getIndi2=='IC000018' || getIndi2=='IC000032'){
											var indiSecond = fn_addComma(Math.round(list[i].indiSecond / 1000));
										} else {
											var indiSecond = fn_addComma(list[i].indiSecond);
										}

										if(indiSecond == null || indiSecond=="" || indiSecond == 'NaN'){
											 html+='<td>-</td>';
										} else {
											 html+='<td>'+indiSecond+'</td>';
											
										}
									}
								}
								
							html+='</ tr>';
							$(".cumulative_main_body").append(html);

							
						}
						var dataName=[];
						for(var i in findNm){
							dataName[i] = findNm[i].indiNm +' ['+ findNm[i].indiUnit +']';
						}

						var sidott = $("#sidoList option:selected").text();
						var sigungutt = $("#sigunguList option:selected").text();
						
						$("#btn_download").append('<button type="button" onClick="fn_downloadCumulative(\''+getIndi1+'\',\''+getIndi2+'\',\''+sidott+'\',\''+sigungutt+'\',\''+sigungu+'\')" class="btn btn-vestap btn-blue w-auto pl-2 pr-2"><i class="icon-download-disk"></i>데이터 다운로드</button>');
						
						fn_cumulativeChart(findNm, list, getIndi1, getIndi2);
						
						$("#cumulativePage").val(1);
						$("#cumulativeLimit").val(page);
						$(".curPage").text("1");
						$(".limitPage").text(page);

						fn_cumulativeDetail(getIndi1);
					}
				})
		}

		$(document).ready(function() {
			$(".fold-open-sensitive a:first").trigger('click');
			//$(".fold-open-sensitive a:eq(1)").trigger('click');
			//$("#detailInfo-btn").trigger('click');
			
			indiId = fn_getIndi(0);
			fn_cumulativeTotal();
			fn_cumulativeDetail(indiId);
		});
		
		
		//이전페이지
		function fn_previousPage(id){
			var page = $("#"+id+"Page").val()*1;
			if(page>1){
			$("#"+id+" .pg"+page).css("display","none");
			page--;
			$("#"+id+" .pg"+page).css("display","table-row");
			}
			$("#"+id+"Page").val(page);
			$(".curPage").text('');
			$(".curPage").text(page);
		}
		//10개 이전 페이지
		function fn_previousTermPage(id, term){
			var page = $("#"+id+"Page").val()*1;
			if(page-term>1){
				$("#"+id+" .pg"+page).css("display","none");
				page-=term;
				$("#"+id+" .pg"+page).css("display","table-row");
			}else{
				$("#"+id+" .pg"+page).css("display","none");
				page = 1;
				$("#"+id+" .pg"+page).css("display","table-row");
			}
			$("#"+id+"Page").val(page);
			$(".curPage").text('');
			$(".curPage").text(page);
		}
		//다음페이지
		function fn_nextPage(id){
			var page = $("#"+id+"Page").val()*1;
			var limitPage =$("#"+id+"Limit").val()*1;
			if(page<limitPage){
			$("#"+id+" .pg"+page).css("display","none");
			page++;
			$("#"+id+" .pg"+page).css("display","table-row");
			}
			$("#"+id+"Page").val(page);
			$(".curPage").text('');
			$(".curPage").text(page);
		}
		//10개다음페이지
		function fn_nextTermPage(id, term){
			var page = $("#"+id+"Page").val()*1;
			var limitPage =$("#"+id+"Limit").val()*1;
			term*=1;
			page*=1;
			if(page+term>=limitPage){
				$("#"+id+" .pg"+page).css("display","none");
				page=limitPage;
				$("#"+id+" .pg"+page).css("display","table-row");
			}else{
				$("#"+id+" .pg"+page).css("display","none");
				page+=term;
				$("#"+id+" .pg"+page).css("display","table-row");
			}
			$("#"+id+"Page").val(page);
			
			$(".curPage").text('');
			$(".curPage").text(page);
		}
			
	
		//지표 검색
		function fn_linkSearch() {
			
			var word = $("#cumulative-search").val();
			var trimWord = word.replace(/ /gi, "");
			
			if(trimWord.length>1 || trimWord.length==0){
			var dataSet = {
					"keyword" : trimWord
				};
			$.ajax({
				url: "/member/base/climate/cumulative/keyword.do",
				data: dataSet,
				dataType:'json',
				type:"get",
				success: function(result) {
					var list = result.list;
					if(word.length <= 30) {
							$("#itemList_02_01").empty();
							$("#itemList_02_02").empty();
							$("#itemList_03_01").empty();
							$("#itemList_03_02").empty();
							for(var i in list){
								var isActive = '';
								if(fn_findIndi(list[i].indiId)){
									isActive = 'active';
								}								
								if(list[i].sector2Id == 'SED01'){
									$("#itemList_02_01").append('<li><a href="#" onclick="fn_itemClick($(this));return false;" data="'+list[i].indiId +'" class="toolTip '+isActive+'">'+
									list[i].indiNm +'</a></li>');
								}else if(list[i].sector2Id =='SED02'){
									$("#itemList_02_02").append('<li><a href="#" onclick="fn_itemClick($(this));return false;" data="'+list[i].indiId +'" class="toolTip '+isActive+'">'+
									list[i].indiNm +'</a></li>');
								}else if(list[i].sector2Id =='SED03'){
									$("#itemList_03_01").append('<li><a href="#" onclick="fn_itemClick($(this));return false;" data="'+list[i].indiId +'" class="toolTip '+isActive+'">'+
											list[i].indiNm +'</a></li>');
								}else if(list[i].sector2Id =='SED04'){
									$("#itemList_03_02").append('<li><a href="#" onclick="fn_itemClick($(this));return false;" data="'+list[i].indiId +'" class="toolTip '+isActive+'">'+
											list[i].indiNm +'</a></li>');
								}
							}
					} else {
						alert("30자 이내로 검색어를 입력 해 주세요.");
					}
				}
			
			});
		}
	}

		
	//누적현황 그래프 띄어주기위한 함수 (그래프 재로딩하는 함수)
	//문제점! 만약 지표가 1개일때랑 2개일때는 어떻게 표현할 것인지.
	function fn_cumulativeChart(name, col, code1, code2){
		var dataName=[];
		var colFirst=[];
		var colSecend=[];
		var colYear=[];
		var category =[];
		
		for(var i in name){
			if(name[i].indiId == 'IC000018' || name[i].indiId == 'IC000032' ){
				dataName[i] = name[i].indiNm +' (10억원)';
				
			} else {
				dataName[i] = name[i].indiNm +' ('+ name[i].indiUnit +')';
				
			}

			for(var j in col){
				 if(code1=='IC000018'){
					if(col[j].indiFirst == null){
						colFirst[j] = null;
					} else {
						colFirst[j] = Math.round(col[j].indiFirst/ 1000);
						
					}
					
					if(code2=='IC000032'){
						if(col[j].indiSecond == null){
							colSecend[j] = null;
						} else {
							colSecend[j] = Math.round(col[j].indiSecond/ 1000);
							
						}
					} else {
						if(col[j].indiSecond == null){
							colSecend[j] = null;
						} else {
							colSecend[j] = col[j].indiSecond;
							
						}
					}
					
				} else if(code1=='IC000032'){
					if(col[j].indiFirst == null){
						colFirst[j] = null;
					} else {
						colFirst[j] = Math.round(col[j].indiFirst/ 1000);
						
					}
					
					if(code2=='IC000018'){
						if(col[j].indiSecond == null){
							colSecend[j] = null;
						} else {
							colSecend[j] = Math.round(col[j].indiSecond/ 1000);
							
						}
					} else {
						if(col[j].indiSecond == null){
							colSecend[j] = null;
						} else {
							colSecend[j] = col[j].indiSecond;
							
						}
					}
					
				} else {
					if(col[j].indiFirst == null){
						colFirst[j] = null;
					} else {
						colFirst[j] = col[j].indiFirst;
						
					}
					
					if(code2=='IC000018' || code2=='IC000032'){
						if(col[j].indiSecond == null){
							colSecend[j] = null;
						} else {
							colSecend[j] = Math.round(col[j].indiSecond/ 1000);
							
						}
					} else {
						if(col[j].indiSecond == null){
							colSecend[j] = null;
						} else {
							colSecend[j] = col[j].indiSecond;
							
						}
					}
					
				} 
				colYear[j] = col[j].indiYear;
			
			}
		}
		colFirst.reverse();
		colSecend.reverse();
		colYear.reverse();		
	
		var column1=["data"];
		var column2=["data2"];
		var columns=[];
		
		if(code2 != null){
			//지표 두개 선택
			for(var i in colFirst){
				column1.push(colFirst[i]);
			}
			for(var i in colSecend){
				column2.push(colSecend[i]);
			}
			for(var i in colYear){
				category.push(colYear[i]);
			}
			
			columns.push(column1);
			columns.push(column2);
			
			var min1 = fn_dataMin(column1);
			var min2 = fn_dataMin(column2);
			
			chart.categories(category);
			chart.data.names({
				data : dataName[0],
				data2 : dataName[1]
				});
			chart.axis.labels({
				  y: dataName[0],
				  y2: dataName[1]
				});
			
			chart.config("axis.y2.show", true,true);
			chart.config("axis.y.min",min1);	
			chart.config("axis.y2.min",min2);	
			$('.bb-axis-y2').css("visibility","visible");	//y2가 두 지표를 선택했을때는 보여야함
			
		}else{
			for(var i in colFirst){
				column1.push(colFirst[i]);
			}
			for(var i in colYear){
				category.push(colYear[i]);
			}
			columns.push(column1);
			
			var min1 = fn_dataMin(column1);
			
			chart.categories(category);
			chart.data.names({
				data : dataName[0]
				});
			chart.axis.labels({
				  y: dataName[0]
				});
			
			chart.config("axis.y.min",min1);	
			chart.config("axis.y2.show", false,true);
			$('.bb-axis-y2').css("visibility","hidden");	//y2가 한 지표를 선택했을때는 안보여야함
		}
		
		fn_chartLoad(columns,code2);
	}
	
	function fn_chartLoad(columns,code){
			chart.load({
				 unload: ["data1", "data2"],
				 columns: columns
			});
		
	}
	
	function fn_dataMin(array){
		var min=1;//최저
		var max=0;//최고
		var standard=0;//기준점(그래프 min값)
		
		var check = 0;
		for(var i in array){
			temp = array[i];
			if(temp<11){ //10보다 작은수가 있으면 불통
				check++;
				if(min>temp)min=temp;
				if(max<temp)max=temp;
			}
		}
		
		
		if(check == array.length-1){
			if(min==max){
				
				if(min == 0){
					standard = 0.02;
				}else{
					standard = min;
				}
			}else{
				standard = (max - min)/10;
			}
			return standard;
		}else{
			return 0;
		}
	}

	//메뉴가 접펼할동안 그래프의 사이즈를 재설정해줌.
	$(document).ready(function() {
		$('#menu-close').click(function(){
			chart.resize({
				   width: 1080
				});
		});
		$('#menu-open').click(function(){
			chart.resize({
				   width: 753
				});
		});
	});
	</script>
<script src="/resources/billboard/d3.v5.min.js"></script>
<script src="/resources/billboard/billboard.js"></script>
<script>
var chart = bb.generate({
	  data: {
	    columns: [
			["data",0]
	    ],
	    colors: {
	    	data: "#1F83CE",
	      },
		axes: {
		  data1: "y",
		  data2: "y2"
		}
	  },
	  axis: {
			x: {
		      type: "category",
		      categories: [
		
		      ],
			  tick: {
			        rotate: 0,
			        multiline: false,
			        tooltip: true,
				      culling: {
				        max: 6
			      }
			  }
		    },
		   y: {
		           min: 0,	
		           label: {
		             position: "outer-top"
		           },
		           tick: {
		               format: function(x) { return d3.format(",")(x); }
		             }
		            
		    },
		    y2: {
		            show: true,
		            min: 0,
		            label: {
		            	position: "outer-top"
			           }, 
		          	tick: {
		               format: function(x) { return d3.format(",")(x); }
		             }
			}
		},
	    padding: {
	        bottom: 10
	    },
	  bindto: "#chart"
	});
	
</script>
</body>
</html>
